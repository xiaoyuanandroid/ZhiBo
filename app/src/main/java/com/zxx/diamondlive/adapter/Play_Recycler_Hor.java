package com.zxx.diamondlive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zxx.diamondlive.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class Play_Recycler_Hor extends RecyclerView.Adapter<Play_Recycler_Hor.MyViewHolder> {
    ArrayList<String> avatars;

    public Play_Recycler_Hor(ArrayList<String> avatars) {
        this.avatars = avatars;
    }

    private Context mContext;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.play_user_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (!TextUtils.isEmpty(avatars.get(position))) {
            Glide.with(mContext).load(avatars.get(position))
                    .error(R.mipmap.play_img)
                    .into(holder.playIvUser);
        }else{
            Glide.with(mContext).load(R.mipmap.play_img)
                    .into(holder.playIvUser);
        }
    }

    @Override
    public int getItemCount() {
        return avatars != null ? avatars.size() : 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.play_iv_user)
        CircleImageView playIvUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
