package com.absurd.demo_skinswitcher;

/**
 * Created by 段泽全 on 2017/10/24.
 * Github：https://github.com/mr-absurd
 * Emile:4884280@qq.com
 */

public class SkinEntity {
    private String attrName;
    private String attrValue;
    private String attrId;
    private String attrType;

    public SkinEntity(String attrName, String attrValue, String attrId, String attrType) {
        this.attrName = attrName;
        this.attrValue = attrValue;
        this.attrId = attrId;
        this.attrType = attrType;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }
}
