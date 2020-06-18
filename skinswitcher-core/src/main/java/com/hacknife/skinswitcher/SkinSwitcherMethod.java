package com.hacknife.skinswitcher;

public class SkinSwitcherMethod {
    int MethodId;
    Object[] args;

    public int getMethodId() {
        return MethodId;
    }

    public SkinSwitcherMethod setMethodId(int methodId) {
        MethodId = methodId;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public SkinSwitcherMethod setArgs(Object... args) {
        this.args = args;
        return this;
    }

}
