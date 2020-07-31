package com.tyytogether.Enums;

public enum HeaderEnum {

    INNER_HEADER_TOKEN("tokenInfo");

    private String info;

    HeaderEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
