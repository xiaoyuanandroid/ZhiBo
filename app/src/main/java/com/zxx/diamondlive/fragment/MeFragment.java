package com.zxx.diamondlive.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.LoginActivity;
import com.zxx.diamondlive.activity.MyLiveActivity;
import com.zxx.diamondlive.fragment.base.BaseFragment;
import com.zxx.diamondlive.utils.ActivityCollector;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.ivBg)
    ImageView ivBg;
    @BindView(R.id.mine_avatar)
    CircleImageView mineAvatar;
    @BindView(R.id.mine_user_name)
    TextView mineUserName;
    @BindView(R.id.img_header_click_tips)
    ImageView imgHeaderClickTips;
    @BindView(R.id.layout_user_name)
    LinearLayout layoutUserName;
    @BindView(R.id.mine_sign)
    TextView mineSign;
    @BindView(R.id.layout_user_info)
    LinearLayout layoutUserInfo;
    @BindView(R.id.my_live)
    RelativeLayout myLive;
    @BindView(R.id.my_video)
    RelativeLayout myVideo;
    @BindView(R.id.my_fans)
    RelativeLayout myFans;
    @BindView(R.id.my_follow)
    RelativeLayout myFollow;
    @BindView(R.id.my_money)
    RelativeLayout myMoney;
    @BindView(R.id.setting)
    RelativeLayout setting;
    @BindView(R.id.quit)
    RelativeLayout quit;
    private SharedPreferences sp;
    private String avatar;
    private String sign;
    private String user_name;
    private long user_id;
    private String phone;
    private String password;
    private SharedPreferences.Editor edit;

    @Override
    protected int getContentResId() {
        return R.layout.frg_me;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        edit = sp.edit();
        avatar = sp.getString("avatar", "");
        sign = sp.getString("sign", "");
        user_name = sp.getString("user_name", "");
        user_id = sp.getLong("user_id", 0);
        phone = sp.getString("phone", "");
        password = sp.getString("password", "");
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(getActivity()).load(avatar).into(mineAvatar);
        }
        if (!TextUtils.isEmpty(sign)){
            mineSign.setText(sign);
        }
        if (!TextUtils.isEmpty(user_name)){
            mineUserName.setText(user_name);
        }
    }

    @OnClick({R.id.my_live,R.id.my_video,R.id.my_fans,R.id.my_follow,R.id.my_money,R.id.setting,R.id.quit})
    public void mineItemClick(View view){
          switch (view.getId()){
              case R.id.my_live:
                  Intent intent = new Intent(getActivity(), MyLiveActivity.class);
                  intent.putExtra("user_id",user_id);
                  startActivity(intent);
                  break;
              case R.id.my_video:

                  break;
              case R.id.my_fans:

                  break;
              case R.id.my_follow:

                  break;
              case R.id.my_money:

                  break;
              case R.id.setting:

                  break;
              case R.id.quit:
                  ActivityCollector.finishAll();
                  Intent quit = new Intent(getActivity(), LoginActivity.class);
                  startActivity(quit);
                  edit.clear();
                  edit.commit();
                  break;
          }
    }
}
