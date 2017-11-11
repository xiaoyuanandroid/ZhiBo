package com.zxx.diamondlive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.bean.MyLiveReposeBean;
import com.zxx.diamondlive.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class MyLiveRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {
    List<MyLiveReposeBean.ResultBean.ListBean> list;
    private Context mContext;
    OnItemClickListener onItemClickListener;
    OnItemClickListener onItemLongClickListener;

    public MyLiveRecycler(List<MyLiveReposeBean.ResultBean.ListBean> list) {
        this.list = list;
    }
    public void refreshData(List<MyLiveReposeBean.ResultBean.ListBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_item, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new MySelectionAda(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MySelectionAda) {
            if (!TextUtils.isEmpty(list.get(position).getUser().getUser_data().getAvatar())) {
                Glide.with(mContext).load(list.get(position)
                        .getUser().getUser_data().getAvatar())
                        .error(R.mipmap.ic_my_avatar)
                        .into(((MySelectionAda) holder).ivPhotoLiveItem);
            } else {
                Glide.with(mContext).load(R.mipmap.ic_my_avatar)
                        .into(((MySelectionAda) holder).ivPhotoLiveItem);
            }
            if (!TextUtils.isEmpty(list.get(position)
                    .getData().getPic())) {
                Glide.with(mContext).load(list.get(position)
                        .getData().getPic())
                        .placeholder(R.mipmap.login_bg)
                        .error(R.mipmap.login_bg)
                        .into(((MySelectionAda) holder).ivPicLiveItem);
            } else {
                Glide.with(mContext).load(R.mipmap.login_bg)
                        .into(((MySelectionAda) holder).ivPicLiveItem);
            }
            ((MySelectionAda) holder).tvNameLiveItem.setText(list.get(position).getData().getLive_name());
            ((MySelectionAda) holder).tvDescLiveItem.setText(list.get(position).getUser()
                    .getUser_data().getUser_name());
            if (list.get(position).getData().getStatus() == 0) {
                ((MySelectionAda) holder).tvStatusLiveItem.setText("直播");
            } else {
                ((MySelectionAda) holder).tvStatusLiveItem.setText("录播");
            }
            holder.itemView.setTag(position);
        }
    }
    @Override
    public int getItemCount() {
        return list.size() != 0 ? list.size() : 0;
    }
    @Override
    public void onClick(View view) {
        if(onItemClickListener!= null) {
            onItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(onItemLongClickListener!= null) {
            onItemLongClickListener.onItemClick(view, (int) view.getTag());
        }
        return false;
    }
}
