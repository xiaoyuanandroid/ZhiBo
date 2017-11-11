package com.zxx.diamondlive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;
import com.zxx.diamondlive.fragment.MyFragmentTabHost;
import com.zxx.diamondlive.fragment.TestFragment;
import com.zxx.diamondlive.view.TabIndicator;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements MyFragmentTabHost.OnTabClickListener, TabHost.OnTabChangeListener {
    MyFragmentTabHost mTabHost;
    RadioGroup mTabRg;
    private int position;
    public static String POSITION = "position";
    boolean flag = false;
    public static long OPEN_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHeaderBuilder.goneToolbar();
        initViews();
        OPEN_TIME = System.currentTimeMillis();
        position = getIntent().getIntExtra("position", 0);
        initTab();
        setListener();
    }

    void initViews() {
        mTabHost = getViewById(R.id.tabhost);
        mTabRg = getViewById(R.id.tab_rg_menu);
    }

    private void setListener() {
        mTabRg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_rb_1:
                        mTabHost.setCurrentTab(0);
                        break;
                    case R.id.tab_rb_2:
                        mTabHost.setCurrentTab(1);
                        break;
                    case R.id.tab_rb_3:
                        mTabHost.setCurrentTab(2);
                        break;
                    case R.id.tab_rb_4:
                        mTabHost.setCurrentTab(3);
                        break;
                    case R.id.tab_rb_5:
                        mTabHost.setCurrentTab(4);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void initTab() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_content);
        mTabHost.getTabWidget().setStripEnabled(false);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setOnTabClickListener(this);
        mTabHost.getTabWidget().setDividerDrawable(null);

        // 首页
        TabHost.TabSpec learnSpec = mTabHost.newTabSpec(getString(R.string.test));
        TabIndicator learnTabView = new TabIndicator(this);
        learnTabView.setView(R.string.test, R.drawable.tab_selector_offline, true);
        learnSpec.setIndicator(learnTabView);

        // 发现
        TabHost.TabSpec downloadSpec = mTabHost.newTabSpec(getString(R.string.test));
        TabIndicator downloadView = new TabIndicator(this);
        downloadView.setView(R.string.test, R.drawable.tab_selector_offline, false);
        downloadSpec.setIndicator(downloadView);

        // 发布
        TabHost.TabSpec writeSpec = mTabHost.newTabSpec(getString(R.string.test));
        TabIndicator writeView = new TabIndicator(this);
        writeView.setView(R.string.test, R.drawable.tab_selector_offline, false);
        writeSpec.setIndicator(writeView);

        // 消息
        TabHost.TabSpec mesSpec = mTabHost.newTabSpec(getString(R.string.test));
        TabIndicator mesView = new TabIndicator(this);
        mesView.setView(R.string.test, R.drawable.tab_selector_offline, false);
        mesSpec.setIndicator(mesView);

        // 我的
        TabHost.TabSpec mineSpec = mTabHost.newTabSpec(getString(R.string.test));
        TabIndicator mineView = new TabIndicator(this);
        mineView.setView(R.string.test, R.drawable.tab_selector_offline, false);
        mineSpec.setIndicator(mineView);

        Bundle b = new Bundle();
        b.putString("key", "1");

        mTabHost.addTab(learnSpec, TestFragment.class, b);
        mTabHost.addTab(downloadSpec, TestFragment.class, b, true);// true or false 表示是否加载网络
        mTabHost.addTab(writeSpec, TestFragment.class, b);
        mTabHost.addTab(mesSpec, TestFragment.class, b);
        mTabHost.addTab(mineSpec, TestFragment.class, b);
        mTabHost.setCurrentTab(position);
        switch (position) {
            case 0:
                mTabRg.check(R.id.tab_rb_1);
                break;
            case 1:
                mTabRg.check(R.id.tab_rb_2);
                break;
            case 2:
                mTabRg.check(R.id.tab_rb_3);
                break;
            case 3:
                mTabRg.check(R.id.tab_rb_4);
                break;
            case 4:
                mTabRg.check(R.id.tab_rb_5);
                break;
        }
    }

    @Override
    protected void initTitleBar(HeaderBuilder builder) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onTabChanged(String tabId) {
    }

    @Override
    public boolean checkLogin(String tabId) {
        return true;
    }

    @Override
    public void setCurrentTabByTag(String tabId) {
        mTabHost.setCurrentTabByTag(tabId);
    }

    @Override
    public void setTabViewBageTips(int pos, int newMsgCount) {
    }

    @Override
    public void setCurrentTabByIndex(int pos) {
        mTabHost.setCurrentTab(pos);
    }

    @Override
    public void onBackPressed() {
        exitBy2Click(); // 调用双击退出函数,此方法不严谨
    }

    private static Boolean isExit = false;

    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        flag = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
