package com.zxx.diamondlive.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;
import com.zxx.diamondlive.fragment.Play_Empty_Fragment;
import com.zxx.diamondlive.fragment.Play_Function_Fragment;
import com.zxx.diamondlive.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PlayActivity extends BaseActivity{

    @BindView(R.id.vp_play)
    ViewPager vpPlay;
    private List<Fragment> listFragment;
    private Context mContext;
    KSYTextureView mVideoView = null;

    private int mVideoWidth = 0;
    private int mVideoHeight = 0;

    boolean useHwCodec = false;
    private int status;
    private String avatar;
    private String live_name;
    private String user_name;
    private long user_id;
    public long live_id;
    private String roomId = "25861179899905";


    @Override
    protected void initTitleBar(HeaderBuilder builder) {
        builder.goneToolbar();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_play;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        status = intent.getIntExtra("status", 1);
        avatar = intent.getStringExtra("avatar");
        live_name = intent.getStringExtra("live_name");
        user_name = intent.getStringExtra("user_name");
        user_id = intent.getLongExtra("user_id", 0L);
        live_id = intent.getLongExtra("live_id", 0L);
        initVariable();
        initData();
        initTextureVideo();
    }
    public String getAvatar() {
        return avatar;
    }

    public String getLive_name() {
        return live_name;
    }

    public String getUser_name() {
        return user_name;
    }
    public long getUser_id() {
        return user_id;
    }

    private void initTextureVideo() {
        mContext = this.getApplicationContext();
        useHwCodec = getIntent().getBooleanExtra("HWCodec", false);

        mVideoView = (KSYTextureView) findViewById(R.id.texture_view);
        mVideoView.setKeepScreenOn(true);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setScreenOnWhilePlaying(true);
        try {
            if (status == 0){
                mVideoView.setDataSource(Constants.PLAY_URL+live_id);//这改成你的地址
            }else if (status == 1){
                mVideoView.setDataSource("rtmp://live.hkstv.hk.lxdns.com/live/hks");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mVideoView.prepareAsync();
    }

    private void initVariable() {
        Play_Empty_Fragment play_empty_fragment = new Play_Empty_Fragment();
        Play_Function_Fragment play_function_fragment = new Play_Function_Fragment();
        listFragment = new ArrayList<>();
        listFragment.add(play_empty_fragment);
        listFragment.add(play_function_fragment);
    }

    private void initData() {
        vpPlay.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        vpPlay.setCurrentItem(1);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    };


    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            mVideoWidth = mVideoView.getVideoWidth();
            mVideoHeight = mVideoView.getVideoHeight();
            // Set Video Scaling Mode
            mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            //start player
            mVideoView.start();

        }
    };

    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();
                    if (mVideoView != null)
                        mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                }
            }
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            Toast.makeText(mContext, "OnCompletionListener, play complete.", Toast.LENGTH_LONG).show();
            videoPlayEnd();
        }
    };

    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            videoPlayEnd();
            return false;
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoPlayEnd();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mVideoView != null) {
            mVideoView.runInBackground(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.runInForeground();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showCloseDialog();
        }

        return super.onKeyDown(keyCode, event);
    }
    //确定退出Dialog
    public void showCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("确定结束观看？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                videoPlayEnd();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
    private void videoPlayEnd() {
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }
        finish();
    }

}
