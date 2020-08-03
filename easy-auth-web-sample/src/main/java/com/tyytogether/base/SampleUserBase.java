package com.tyytogether.base;

public class SampleUserBase extends UserBase{
    String someInfo;

    public SampleUserBase(String someInfo, String id, String role){
        this.someInfo = someInfo;
        super.setId(id);
        super.setRole(role);
    }

    public String getSomeInfo() {
        return someInfo;
    }

    public void setSomeInfo(String someInfo) {
        this.someInfo = someInfo;
    }
}
