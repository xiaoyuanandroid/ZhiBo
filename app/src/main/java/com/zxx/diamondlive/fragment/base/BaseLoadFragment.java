package com.zxx.diamondlive.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxx.diamondlive.listener.OnRefreshListener;
import com.zxx.diamondlive.view.PublicLoadLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * date: Created on 16/5/24.
 */
public abstract class BaseLoadFragment extends UmengFragment implements OnRefreshListener {
    private PublicLoadLayout mRootLayout;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootLayout = PublicLoadLayout.createPage(getContext(), getContentResId(), this);
        unbinder = ButterKnife.bind(this, mRootLayout);

        return mRootLayout;
    }

    public void goneLoading(){
        mRootLayout.goneLoading();
    }

    public void showNoData(){
        mRootLayout.showNoData();
    }

    public void showNetError(){
        mRootLayout.showNetError();
    }

    public void showLoading(){
        try {
            mRootLayout.showLoading();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected <VG extends View> VG getViewById(int resId){
        return (VG) mRootLayout.findViewById(resId);
    }

    protected abstract int getContentResId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
