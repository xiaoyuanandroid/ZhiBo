package com.zxx.diamondlive.listener;

import com.zxx.diamondlive.viewholder.CommonViewHolder;

/**
 * @author xiaoyuan
 */
public interface OnMultiTypeItemClickListeners<T> {
    void onItemClick(CommonViewHolder viewHolder, T data, int position, int viewType);
}
