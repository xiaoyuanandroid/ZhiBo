package com.zxx.diamondlive.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * date: Created xiaoyuan on 16/11/05.
 */
public abstract class BaseFragment extends UmengFragment {
    private View mRootLayout;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootLayout = inflater.inflate(getContentResId(), null);
        unbinder = ButterKnife.bind(this, mRootLayout);
        return mRootLayout;
    }

    protected abstract int getContentResId();

    protected <VG extends View> VG getViewById(int resId){
        return (VG) mRootLayout.findViewById(resId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
