package com.zxx.diamondlive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.bean.Live;
import com.zxx.diamondlive.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class Selection_Recy_Ada extends RecyclerView.Adapter<MySelectionAda> {
    Context mContext;
    List<Live.ResultBean.ListBean> mList;
    OnItemClickListener listener;

    public Selection_Recy_Ada(Context context,List<Live.ResultBean.ListBean> list) {
        mContext = context;
        mList = list;
    }
    public void refresh(List<Live.ResultBean.ListBean> list){
        mList = list;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public MySelectionAda onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_item, parent,false);
        MySelectionAda mySelectionAda = new MySelectionAda(view);
        return mySelectionAda;
    }

    @Override
    public void onBindViewHolder(MySelectionAda holder, final int position) {
        if (!TextUtils.isEmpty(mList.get(position).getUser().getUser_data().getAvatar())){
            Glide.with(mContext).load(mList.get(position)
                    .getUser().getUser_data().getAvatar())
                    .error(R.mipmap.ic_my_avatar)
                    .into(holder.ivPhotoLiveItem);
        }else{
            Glide.with(mContext).load(R.mipmap.ic_my_avatar)
                    .into(holder.ivPhotoLiveItem);
        }
        if (!TextUtils.isEmpty(mList.get(position)
                .getData().getPic())){
            Glide.with(mContext).load(mList.get(position)
                    .getData().getPic())
                    .placeholder(R.mipmap.login_bg)
                    .error(R.mipmap.login_bg)
                    .into(holder.ivPicLiveItem);
        }else{
            Glide.with(mContext).load(R.mipmap.login_bg)
                    .into(holder.ivPicLiveItem);
        }
        holder.tvNameLiveItem.setText(mList.get(position).getData().getLive_name());
        holder.tvDescLiveItem.setText(mList.get(position).getUser()
                .getUser_data().getUser_name());
        if (mList.get(position).getData().getStatus() == 0){
            holder.tvStatusLiveItem.setText("直播");
        }else{
            holder.tvStatusLiveItem.setText("录播");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

class MySelectionAda extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_photo_live_item)
    ImageView ivPhotoLiveItem;
    @BindView(R.id.tv_name_live_item)
    TextView tvNameLiveItem;
    @BindView(R.id.tv_desc_live_item)
    TextView tvDescLiveItem;
    @BindView(R.id.iv_pic_live_item)
    ImageView ivPicLiveItem;
    @BindView(R.id.tv_status_live_item)
    TextView tvStatusLiveItem;
    public MySelectionAda(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
