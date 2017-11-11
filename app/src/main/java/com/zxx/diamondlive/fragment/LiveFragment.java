package com.zxx.diamondlive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.fragment.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class LiveFragment extends BaseFragment {

    @BindView(R.id.tab_live)
    TabLayout tabLive;
    @BindView(R.id.vp_live)
    ViewPager vpLive;
    Unbinder unbinder;
    private ArrayList<Fragment> listFragment;


    @Override
    protected int getContentResId() {
        return R.layout.frg_live;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        final String[] title = new String[]{"精选", "热门"};
        Live_Selection_Fragment live_selection_fragment = new Live_Selection_Fragment();
        Live_Hot_Fragment live_hot_fragment = new Live_Hot_Fragment();
        listFragment = new ArrayList<>();
        listFragment.add(live_selection_fragment);
        listFragment.add(live_hot_fragment);
        vpLive.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });
        tabLive.setupWithViewPager(vpLive);
    }
}
