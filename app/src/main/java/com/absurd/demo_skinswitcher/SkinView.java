package com.absurd.demo_skinswitcher;

import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by 段泽全 on 2017/10/24.
 * Github：https://github.com/mr-absurd
 * Emile:4884280@qq.com
 */

public class SkinView {
    private View view;
    private List<SkinEntity> entities;

    public SkinView(View view, List<SkinEntity> entities) {
        this.view = view;
        this.entities = entities;
    }

    public void apply() {
        for (SkinEntity entity : entities) {

            Log.v("TAG", "---------getAttrName------------>>" + entity.getAttrName());
            Log.v("TAG", "---------getAttrId------------>>" + entity.getAttrId());
            Log.v("TAG", "---------getAttrType------------>>" + entity.getAttrType());
            Log.v("TAG", "---------getAttrValue------------>>" + entity.getAttrValue());
        }
    }
}
