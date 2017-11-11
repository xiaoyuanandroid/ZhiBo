package com.zxx.diamondlive.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    private static final int STARTLOGIN = 1;
    private static final int STARTMAIN = 2;
    SharedPreferences sp;
    Context mContext = this;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == STARTLOGIN){
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
            }else if (msg.what == STARTMAIN){
                Intent intent = new Intent(mContext, LiveHostActivity.class);
                startActivity(intent);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("user",MODE_PRIVATE);
        final String username = sp.getString("user_name", "");
        final String password = sp.getString("password", "");
        if (username.equals("") || password.equals("")){
            handler.sendEmptyMessageDelayed(STARTLOGIN,3000);
        }else{
            handler.sendEmptyMessageDelayed(STARTMAIN,3000);
        }
    }


    @Override
    protected void initTitleBar(HeaderBuilder builder) {
        builder.goneToolbar();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_welcome;
    }
}
