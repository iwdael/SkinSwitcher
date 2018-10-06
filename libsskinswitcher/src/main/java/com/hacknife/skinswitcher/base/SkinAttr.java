package com.hacknife.skinswitcher.base;
import com.hacknife.skinswitcher.util.ResourceTypeUtil;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : SkinSwitcher
 */

  class SkinAttr {
    private String attrName;
    private String attrValue;
    private ResourceType attrType;

    public SkinAttr(String attrName, String attrValue, String attrType) {
        this.attrName = attrName;
        this.attrValue = attrValue;
        this.attrType = ResourceTypeUtil.getType(attrType);
    }

    public String getAttrName() {
        return attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public ResourceType getAttrType() {
        return attrType;
    }
}
