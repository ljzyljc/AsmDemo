package com.jackie.time

import com.quinn.hunter.transform.RunVariant


public class TimingHunterExtension {

    public RunVariant runVariant = RunVariant.ALWAYS;
    public List<String> whitelist = new ArrayList<>();
    public List<String> blacklist = new ArrayList<>();
    public boolean duplcatedClassSafeMode = false;

    @Override
    public String toString() {
        return "TimingHunterExtension{" +
                "runVariant=" + runVariant +
                ", whitelist=" + whitelist +
                ", blacklist=" + blacklist +
                ", duplcatedClassSafeMode=" + duplcatedClassSafeMode +
                '}';
    }
}