package com.hacknife.example.adapter;

import com.hacknife.example.R;


import android.view.ViewGroup;

import com.hacknife.example.adapter.viewholder.MessageViewHolder;
import com.hacknife.example.adapter.base.BaseRecyclerViewAdapter;
import com.hacknife.example.adapter.base.BaseRecyclerViewHolder;
import com.hacknife.example.entity.Message;
import com.hacknife.skinswitcher.SkinSwitcher;

import com.hacknife.skinswitcher.SkinSwitcherFactory;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class MessageAdapter extends BaseRecyclerViewAdapter<Message, BaseRecyclerViewHolder> {
    private SkinSwitcherFactory factory = new SkinSwitcherFactory();


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(SkinSwitcher.setFactory(parent.getContext(), factory).inflate(R.layout.item_message, null));
    }

}
