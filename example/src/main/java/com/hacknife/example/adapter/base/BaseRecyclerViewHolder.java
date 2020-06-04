package com.hacknife.example.adapter.base;


import android.view.View;

import com.hacknife.example.BR;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
public abstract class BaseRecyclerViewHolder<E, B extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected B binding;
    protected OnRecyclerListener<E> onRecyclerListener;
    protected int position;
    protected E entity;
    protected int size;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bindData(E e, int position, int size, OnRecyclerListener<E> onRecyclerListener) {
        this.onRecyclerListener = onRecyclerListener;
        this.position = position;
        this.size = size;
        if (e == null) return;
        this.entity = e;
        bindData(e);
        if (onRecyclerListener != null) onRecyclerListener.selectCallback(selectCallback(e));
    }

    protected int selectCallback(E e) {
        return -1;
    }

    public void bindData(E entity) {
        binding.setVariable(BR.entity, entity);
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerListener != null) onRecyclerListener.onClick(entity, position, v);
    }

    @Override
    public boolean onLongClick(View v) {
        if (onRecyclerListener != null) return onRecyclerListener.onLongClick(entity, position, v);
        return false;
    }
}
