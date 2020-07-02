package com.jackie.hunter.transform;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.ide.common.internal.WaitableExecutor;
import com.android.ide.common.workers.WorkerExecutorFacade;
import com.google.common.io.Files;
import com.jackie.hunter.transform.asm.BaseWeaver;
import com.jackie.hunter.transform.asm.ClassLoaderHelper;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/11
 */
public class HunterTransform extends Transform {

    protected Project project;
    //并发执行
    private WaitableExecutor waitableExecutor;
    private Logger logger;
    //emptyRun字段用于是否要执行修改字节码，true不修改
    private boolean emptyRun;
    protected BaseWeaver bytecodeWeaver;

    public HunterTransform(Project project){
        this.project = project;
        this.logger = project.getLogger();
        this.waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool();
    }


    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        long startTime = System.currentTimeMillis();

        RunVariant runVariant = getRunVariant();
        //如果设置了RELEASE或者DEBUG,这样只要切换debug和release就可以达到用不用修改字节码的目的了
        if("debug".equals(transformInvocation.getContext().getVariantName())) {
            emptyRun = runVariant == RunVariant.RELEASE || runVariant == RunVariant.NEVER;
        } else if("release".equals(transformInvocation.getContext().getVariantName())) {
            emptyRun = runVariant == RunVariant.DEBUG || runVariant == RunVariant.NEVER;
        }

        //是否支持增量编译
        boolean isIncremental = transformInvocation.isIncremental();
        if (!isIncremental){
            transformInvocation.getOutputProvider().deleteAll();
        }

        URLClassLoader urlClassLoader = ClassLoaderHelper.getClassLoader(transformInvocation.getInputs()
                , transformInvocation.getReferencedInputs(), project);
        this.bytecodeWeaver.setClassLoader(urlClassLoader);

        boolean flagForCleanDexBuilderFolder = false;

        for (TransformInput input : transformInvocation.getInputs()){
            //jar包
            for (JarInput jarInput: input.getJarInputs()){
                Status status = jarInput.getStatus();
                File dest = transformInvocation.getOutputProvider().getContentLocation(
                        jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                if (isIncremental && !emptyRun){
//                    NOTCHANGED: 当前文件不需处理，甚至复制操作都不用；
//                    ADDED、CHANGED: 正常处理，输出给下一个任务；
//                    REMOVED: 移除outputProvider获取路径对应的文件。
                    switch (status){
                        case NOTCHANGED:
                            break;
                        case ADDED:
                        case CHANGED:
                            transformJar(jarInput.getFile(),dest,status);
                            break;
                        case REMOVED:
                            if (dest.exists()){
                                FileUtils.forceDelete(dest);
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    //Forgive me!, Some project will store 3rd-party aar for serveral copies in dexbuilder folder,,unknown issue.
                    if(inDuplcatedClassSafeMode() & !isIncremental && !flagForCleanDexBuilderFolder) {
                        cleanDexBuilderFolder(dest);
                        flagForCleanDexBuilderFolder = true;
                    }
                    transformJar(jarInput.getFile(),dest,status);
                }

            }

            // 文件夹 Input
            for(DirectoryInput directoryInput : input.getDirectoryInputs()) {
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY);
                FileUtils.forceMkdir(dest);
                if(isIncremental && !emptyRun) {
                    String srcDirPath = directoryInput.getFile().getAbsolutePath();
                    String destDirPath = dest.getAbsolutePath();
                    Map<File, Status> fileStatusMap = directoryInput.getChangedFiles();
                    for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                        Status status = changedFile.getValue();
                        File inputFile = changedFile.getKey();
                        String destFilePath = inputFile.getAbsolutePath().replace(srcDirPath, destDirPath);
                        File destFile = new File(destFilePath);
                        switch (status) {
                            case NOTCHANGED:
                                break;
                            case REMOVED:
                                if(destFile.exists()) {
                                    //noinspection ResultOfMethodCallIgnored
                                    destFile.delete();
                                }
                                break;
                            case ADDED:
                            case CHANGED:
                                try {
                                    FileUtils.touch(destFile);
                                } catch (IOException e) {
                                    //maybe mkdirs fail for some strange reason, try again.
                                    Files.createParentDirs(destFile);
                                }
                                transformSingleFile(inputFile, destFile, srcDirPath);
                                break;
                        }
                    }
                } else {
                    transformDir(directoryInput.getFile(), dest);
                }

            }
        }



        // 等待所有的并发执行完毕
        waitableExecutor.waitForTasksWithQuickFail(true);
        long costTime = System.currentTimeMillis() - startTime;
        logger.warn(getName() + " cost time: "+ costTime + "ms");

    }


    private void transformSingleFile(final File inputFile, final File outputFile, final String srcBaseDir) {

        waitableExecutor.execute(() -> {
            bytecodeWeaver.weaveSingleClassToFile(inputFile, outputFile, srcBaseDir);
            return null;
        });

    }

    private void transformDir(final File inputDir, final File outputDir) throws IOException{
        if (emptyRun){
            FileUtils.copyDirectory(inputDir, outputDir);
            return ;
        }

        final String inputDirPath = inputDir.getAbsolutePath();
        final String outputDirPath = outputDir.getAbsolutePath();
        if (inputDir.isDirectory()) {
            for (final File file : com.android.utils.FileUtils.getAllFiles(inputDir)) {
                waitableExecutor.execute(() -> {
                    String filePath = file.getAbsolutePath();
                    File outputFile = new File(filePath.replace(inputDirPath, outputDirPath));
                    bytecodeWeaver.weaveSingleClassToFile(file, outputFile, inputDirPath);
                    return null;
                });
            }
        }
    }

    private void transformJar(final File srcJar, final File destJar, Status status){

        waitableExecutor.execute(() -> {
            if (emptyRun){
                FileUtils.copyFile(srcJar,destJar);
                return null;
            }
            bytecodeWeaver.weaveJar(srcJar, destJar);
            return null;
        });

    }

    private void cleanDexBuilderFolder(File dest) {
        waitableExecutor.execute(() -> {
            try {
                String dexBuilderDir = replaceLastPart(dest.getAbsolutePath(), getName(), "dexBuilder");
                //intermediates/transforms/dexBuilder/debug
                File file = new File(dexBuilderDir).getParentFile();
                project.getLogger().warn("clean dexBuilder folder = " + file.getAbsolutePath());
                if(file.exists() && file.isDirectory()) {
                    com.android.utils.FileUtils.deleteDirectoryContents(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private String replaceLastPart(String originString, String replacement, String toreplace) {
        int start = originString.lastIndexOf(replacement);
        StringBuilder builder = new StringBuilder();
        builder.append(originString.substring(0, start));
        builder.append(toreplace);
        builder.append(originString.substring(start + replacement.length()));
        return builder.toString();
    }



    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    protected RunVariant getRunVariant() {
        return RunVariant.ALWAYS;
    }

    protected boolean inDuplcatedClassSafeMode(){
        return false;
    }

}
