package com.zxx.diamondlive.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zxx.diamondlive.view.MyGridView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class GiftViewPagerAdapter extends PagerAdapter {
    ArrayList<MyGridView> gridViewList;

    public GiftViewPagerAdapter(ArrayList<MyGridView> gridViewList) {
        this.gridViewList = gridViewList;
    }

    @Override
    public int getCount() {
        return gridViewList != null?gridViewList.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(gridViewList.get(position));
        return gridViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(gridViewList.get(position));
    }
}
