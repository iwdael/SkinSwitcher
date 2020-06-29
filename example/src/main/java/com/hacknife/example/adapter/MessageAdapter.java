package com.hacknife.example.adapter;

import com.hacknife.example.R;


import android.util.Log;
import android.view.LayoutInflater;
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


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("dzq","===========================>>"+parent.getContext());

        return new MessageViewHolder(SkinSwitcher.setFactory2(parent.getContext(), SkinSwitcherFactory.class).inflate(R.layout.item_message, null));
    }

}
