package com.zxx.diamondlive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.bean.ChatContent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class Play_Recycler_Ver extends RecyclerView.Adapter<Play_Recycler_Ver.ViewHolder> {

    private Context mContext;
    private ArrayList<ChatContent> datas;

    public Play_Recycler_Ver(ArrayList<ChatContent> datas) {
        this.datas = datas;
    }
    public void RefreshData(ArrayList<ChatContent> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.play_message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.playMessageTvName.setText(datas.get(position).getUserName());
        holder.playMessageTvDesc.setText(datas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return datas==null? 0:datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.play_message_tv_name)
        TextView playMessageTvName;
        @BindView(R.id.play_message_tv_desc)
        TextView playMessageTvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
