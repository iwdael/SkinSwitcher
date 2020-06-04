package com.hacknife.example.adapter.viewholder;

import android.view.View;

import com.hacknife.example.entity.Message;
import com.hacknife.example.adapter.base.BaseRecyclerViewHolder;
import com.hacknife.example.databinding.ItemMessageBinding;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public class MessageViewHolder extends BaseRecyclerViewHolder<Message, ItemMessageBinding> {
    public MessageViewHolder(View itemView) {
        super(itemView);
    }
}