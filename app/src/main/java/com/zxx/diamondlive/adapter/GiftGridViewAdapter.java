package com.zxx.diamondlive.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.bean.Gift;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class GiftGridViewAdapter extends BaseAdapter {
    private Context context;
    List<Gift.GiftListBean> datas;
    int page;
    int pageSize;

    public GiftGridViewAdapter(Context context, List<Gift.GiftListBean> datas, int page, int pageSize) {
        this.context = context;
        this.datas = datas;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public int getCount() {
        return datas.size() > (page + 1) * pageSize ? pageSize : (datas.size() - page * pageSize);
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i + page * pageSize);
    }

    @Override
    public long getItemId(int i) {
        return i + page * pageSize;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.gift_pop_gv_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        //重新确定position因为拿到的重视数据源，数据源是分页加载到每页的GridView上的
        final int pos = i+page*pageSize;
        final ViewHolder finalHolder = holder;
        Glide.with(context).load(datas.get(pos).getGiftPic())
                .into(finalHolder.ivGiftItem);

        String name = datas.get(pos).getGiftName();
        holder.tvGiftItemName.setText(name.substring(0,name.indexOf(".")));
        holder.tvGiftItemMoney.setText(datas.get(pos).getGiftPrice()+"");
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_gift_item)
        ImageView ivGiftItem;
        @BindView(R.id.tv_gift_item_name)
        TextView tvGiftItemName;
        @BindView(R.id.tv_gift_item_money)
        TextView tvGiftItemMoney;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
