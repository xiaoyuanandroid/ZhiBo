package com.zxx.diamondlive.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.utils.ActivityCollector;

import butterknife.ButterKnife;

/**
 * date: Created xiaoyuan on 16/11/05.
 */
public abstract class BaseActivity extends ToolBarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        //全屏显示
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setRootContent();
        ButterKnife.bind(this);
    }

    private void setRootContent(){
        if (getContentResId() > 0){
            addContent(getContentResId());
        } else if (getFragment() != null){
            addFragment(getFragment());
        } else {
            throw new NullPointerException("必须实现getContentResId()或者getFragment()方法");
        }
    }

    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.public_layout, fragment);
        transaction.commitAllowingStateLoss();
    }

    protected int getContentResId(){
        return 0;
    }

    protected Fragment getFragment(){
        return null;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
