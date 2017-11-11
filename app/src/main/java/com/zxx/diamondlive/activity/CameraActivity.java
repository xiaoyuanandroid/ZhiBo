package com.zxx.diamondlive.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.capture.camera.CameraTouchHelper;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyProFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.gles.GLRender;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;
import com.zxx.diamondlive.adapter.Play_Recycler_Ver;
import com.zxx.diamondlive.bean.ChatContent;
import com.zxx.diamondlive.bean.LiveReposeBean;
import com.zxx.diamondlive.bean.UpdateStatusBean;
import com.zxx.diamondlive.heart.HeartLayout;
import com.zxx.diamondlive.network.RetrofitManager;
import com.zxx.diamondlive.network.api.CreateApi;
import com.zxx.diamondlive.network.api.UpdateStatusApi;
import com.zxx.diamondlive.utils.Constants;
import com.zxx.diamondlive.utils.ToastUtils;
import com.zxx.diamondlive.view.VerticalSeekBar;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftModel;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zxx.diamondlive.R.id.camera_start_live_bt;
import static com.zxx.diamondlive.R.id.switch_cam;

public class CameraActivity extends BaseActivity {

    @BindView(R.id.camera_preview)
    GLSurfaceView mCameraPreview;
    @BindView(R.id.exposure_seekBar)
    VerticalSeekBar mExposureSeekBar;
    @BindView(R.id.chronometer)
    Chronometer mChronometer;
    @BindView(switch_cam)
    ImageView mSwitchCameraView;
    @BindView(R.id.flash)
    ImageView mFlashView;
    @BindView(R.id.exposure)
    ImageView mExposureView;
    @BindView(R.id.url)
    TextView mUrlTextView;
    @BindView(R.id.debuginfo)
    TextView mDebugInfoTextView;
    @BindView(R.id.backoff)
    ImageView mDeleteView;
    @BindView(R.id.click_to_capture_screenshot)
    TextView mCaptureSceenShot;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.click_to_switch_beauty)
    TextView clickToSwitchBeauty;
    @BindView(R.id.camera_create_live_et)
    EditText cameraCreateLiveEt;
    @BindView(camera_start_live_bt)
    Button cameraStartLiveBt;
    @BindView(R.id.zhibo_chat)
    RecyclerView zhiboChat;
    @BindView(R.id.zhibo_heartLayout)
    HeartLayout zhiboHeartLayout;
    @BindView(R.id.camera_chat_iv)
    ImageView cameraChatIv;
    @BindView(R.id.zhibo_giftParent)
    LinearLayout zhiboGiftParent;

    private KSYStreamer mStreamer;
    private static final String TAG = "CameraActivity";
    private boolean mIsFlashOpened = false;
    private boolean mRecording = false;
    private boolean mIsFileRecording = false;
    private ButtonObserver mObserverButton;
    private boolean mHWEncoderUnsupported;
    private boolean mSWEncoderUnsupported;
    private Timer mTimer;
    private boolean mPrintDebugInfo = false;
    private Handler mMainHandler;
    private String mDebugInfo = "";
    private String mRecordUrl = "/sdcard/rec_test.mp4";
    private final static int PERMISSION_REQUEST_CAMERA_AUDIOREC = 1;
    private static final String START_RECORDING = "开始录制";
    private static final String STOP_RECORDING = "停止录制";
    private static final String START_BEAUTY = "开启美颜";
    private static final String STOP_BEAUTY = "关闭美颜";
    private boolean startBeauty = false;
    private boolean mAutoStart = false;
    private View bottom_view;
    private boolean isNewLive = true;
    private long live_id;//直播id
    private View chatView;
    private Play_Recycler_Ver chatContentAdapter;
    private ArrayList<ChatContent> chatContents;
    private InputMethodManager imm;
    private long user_id;
    private String avatar;
    private String user_name;
    private String roomId = "25861179899905";
    private boolean joinChatRoomSuccess = false;

    private static final int REG_SUCCESS = 1;
    private static final int REG_FAILED = 2;
    private static final int SEND_MESSAGE_SUCCESS = 3;
    private static final int SEND_MESSAGE_FAILED = 4;
    private static final int RECEIVE_MSG = 5;
    private static final int LOGIN_SUCCESS = 6;
    private static final int LOGIN_FAILED = 7;
    private Timer timer;
    private String EMPassword = "111111";
    private GiftControl giftControl;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REG_FAILED:
                    ToastUtils.showShort("注册失败");
                    break;
                case REG_SUCCESS:
                    loginEM();
                    break;
                case SEND_MESSAGE_SUCCESS:
                    if (msg.obj instanceof ChatContent) {
                        chatContents.add(0, (ChatContent) msg.obj);
                        chatContentAdapter.RefreshData(chatContents);
                        zhiboChat.scrollToPosition(0);
                    }
                    break;
                case SEND_MESSAGE_FAILED:
                    ToastUtils.showShort("发送失败");
                    break;
                case RECEIVE_MSG:
                    List<EMMessage> ms = (List<EMMessage>) msg.obj;
                    for (EMMessage m : ms) {
                        int type = m.getIntAttribute("type", 1);
                        if (type == 1) {//聊天
                            String content = m.getStringAttribute("content", "");
                            String user_name = m.getStringAttribute("user_name", "");
                            if (TextUtils.isEmpty(content) || TextUtils.isEmpty(user_name)) {
                                return;
                            }
                            ChatContent receive_content = new ChatContent(user_name, content);
                            chatContents.add(0, receive_content);
                            zhiboChat.scrollToPosition(0);
                            chatContentAdapter.RefreshData(chatContents);
                        } else if (type == 2) {//礼物
                            String user_name = m.getStringAttribute("user_name", "");
                            Long user_id = m.getLongAttribute("user_id", 0);
                            String avatar = m.getStringAttribute("avatar", "");
                            String gift_pic = m.getStringAttribute("gift_pic", "");
                            String gift_id = m.getStringAttribute("gift_id", "");
                            int gift_count = m.getIntAttribute("gift_count", 1);
                            if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(avatar) || TextUtils.isEmpty(gift_pic) ||
                                    TextUtils.isEmpty(gift_id) || user_id == 0) {
                                return;
                            } else {
                                GiftModel giftModel = new GiftModel();
                                giftModel.setGiftId(gift_id);
                                giftModel.setGiftName(gift_id);//礼物名字
                                giftModel.setGiftCount(gift_count);
                                giftModel.setGiftPic(gift_pic);
                                giftModel.setSendUserName(user_name);
                                giftModel.setSendGiftTime(System.currentTimeMillis());
                                giftModel.setCurrentStart(false);
                                giftModel.setSendUserId(String.valueOf(user_id));
                                giftModel.setSendUserPic(avatar);
                                giftModel.setHitCombo(0);
                                giftControl.loadGift(giftModel);
                            }
                        } else if (type == 3) {//点赞
                            zhiboHeartLayout.addHeart(randomColor());
                        }
                    }
                    break;
                case LOGIN_SUCCESS:
                    joinChatRoom();
                    break;
                case LOGIN_FAILED:
                    ToastUtils.showShort("登录失败");
                    break;

            }
        }
    };

    @Override
    protected void initTitleBar(HeaderBuilder builder) {
        builder.goneToolbar();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        chatView = findViewById(R.id.camera_chat_view);
        live_id = intent.getLongExtra("live_id", 0L);
        //获取用户自己的信息
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        user_name = sp.getString("user_name", "");
        user_id = sp.getLong("user_id", 0L);
        avatar = sp.getString("avatar", "");
        giftControl = new GiftControl(getApplicationContext());
        giftControl.setGiftLayout(false, zhiboGiftParent, 3);
        if (live_id != 0L) {
            isNewLive = false;
            cameraCreateLiveEt.setVisibility(View.GONE);
        }
        mExposureSeekBar.setProgress(50);
        mExposureSeekBar.setSecondaryProgress(50);
        mExposureSeekBar.setOnSeekBarChangeListener(getVerticalSeekListener());
        mMainHandler = new Handler();
        mObserverButton = new ButtonObserver();
        mCaptureSceenShot.setOnClickListener(mObserverButton);
        mDeleteView.setOnClickListener(mObserverButton);
        mSwitchCameraView.setOnClickListener(mObserverButton);
        mFlashView.setOnClickListener(mObserverButton);
        mExposureView.setOnClickListener(mObserverButton);
        cameraChatIv.setOnClickListener(mObserverButton);
        // 创建KSYStreamer实例
        mStreamer = new KSYStreamer(this);
        initData();
        bottom_view = findViewById(R.id.bar_bottom);
        bottom_view.setVisibility(View.GONE);

    }

    private void handleEncodeError() {
        int encodeMethod = mStreamer.getVideoEncodeMethod();
        if (encodeMethod == StreamerConstants.ENCODE_METHOD_HARDWARE) {
            mHWEncoderUnsupported = true;
            if (mSWEncoderUnsupported) {
                mStreamer.setEncodeMethod(
                        StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                Log.e(TAG, "Got HW encoder error, switch to SOFTWARE_COMPAT mode");
            } else {
                mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
                Log.e(TAG, "Got HW encoder error, switch to SOFTWARE mode");
            }
        } else if (encodeMethod == StreamerConstants.ENCODE_METHOD_SOFTWARE) {
            mSWEncoderUnsupported = true;
            if (mHWEncoderUnsupported) {
                mStreamer.setEncodeMethod(
                        StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                Log.e(TAG, "Got SW encoder error, switch to SOFTWARE_COMPAT mode");
            } else {
                mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_HARDWARE);
                Log.e(TAG, "Got SW encoder error, switch to HARDWARE mode");
            }
        }
    }


    private VerticalSeekBar.OnSeekBarChangeListener getVerticalSeekListener() {
        VerticalSeekBar.OnSeekBarChangeListener listener = new VerticalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(VerticalSeekBar seekBar, int progress, boolean fromUser) {
                Camera.Parameters parameters = mStreamer.getCameraCapture().getCameraParameters();
                if (parameters != null) {
                    int minValue = parameters.getMinExposureCompensation();
                    int maxValue = parameters.getMaxExposureCompensation();
                    int range = 100 / (maxValue - minValue);
                    parameters.setExposureCompensation(progress / range - maxValue);
                }
                mStreamer.getCameraCapture().setCameraParameters(parameters);
            }

            @Override
            public void onStartTrackingTouch(VerticalSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(VerticalSeekBar seekBar) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean enable) {

            }
        };
        return listener;
    }

    private void initData() {
        // 设置预览View
        mStreamer.setDisplayPreview(mCameraPreview);
        // 设置推流url（需要向相关人员申请，测试地址并不稳定！）
//        mStreamer.setUrl("rtmp://test.uplive.ksyun.com/live/{streamName}");
//        mStreamer.setUrl("rtmp://121.42.26.175:1935/mytv/1234");
        // 设置预览分辨率, 当一边为0时，SDK会根据另一边及实际预览View的尺寸进行计算
        mStreamer.setPreviewResolution(480, 0);
        // 设置推流分辨率，可以不同于预览分辨率（不应大于预览分辨率，否则推流会有画质损失）
        mStreamer.setTargetResolution(480, 0);
        // 设置预览帧率
        mStreamer.setPreviewFps(15);
        // 设置推流帧率，当预览帧率大于推流帧率时，编码模块会自动丢帧以适应设定的推流帧率
        mStreamer.setTargetFps(15);
        // 设置视频码率，分别为初始平均码率、最高平均码率、最低平均码率，单位为kbps，另有setVideoBitrate接口，单位为bps
        mStreamer.setVideoKBitrate(600, 800, 400);
        // 设置音频采样率
        mStreamer.setAudioSampleRate(44100);
        // 设置音频码率，单位为kbps，另有setAudioBitrate接口，单位为bps
        mStreamer.setAudioKBitrate(48);
        /**
         * 设置编码模式(软编、硬编)，请根据白名单和系统版本来设置软硬编模式，不要全部设成软编或者硬编,白名单可以联系金山云商务:
         * StreamerConstants.ENCODE_METHOD_SOFTWARE
         * StreamerConstants.ENCODE_METHOD_HARDWARE
         */
        mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
        // 设置屏幕的旋转角度，支持 0, 90, 180, 270
        mStreamer.setRotateDegrees(0);
        // 设置开始预览使用前置还是后置摄像头
        mStreamer.setCameraFacing(CameraCapture.FACING_FRONT);
        mStreamer.setOnInfoListener(mOnInfoListener);
        mStreamer.setOnErrorListener(mOnErrorListener);
        //水印
        mStreamer.showWaterMarkTime(0.03f, 0.01f, 0.35f, Color.WHITE, 1.0f);
        clickToSwitchBeauty.setOnClickListener(mObserverButton);
        CameraTouchHelper cameraTouchHelper = new CameraTouchHelper();
        cameraTouchHelper.setCameraCapture(mStreamer.getCameraCapture());
        mCameraPreview.setOnTouchListener(cameraTouchHelper);
    }

    private void setCameraAntiBanding50Hz() {
        Camera.Parameters parameters = mStreamer.getCameraCapture().getCameraParameters();
        if (parameters != null) {
            parameters.setAntibanding(Camera.Parameters.ANTIBANDING_50HZ);
            mStreamer.getCameraCapture().setCameraParameters(parameters);
        }
    }

    private KSYStreamer.OnInfoListener mOnInfoListener = new KSYStreamer.OnInfoListener() {
        @Override
        public void onInfo(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_INIT_DONE:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_INIT_DONE");
                    setCameraAntiBanding50Hz();
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_STREAM_SUCCESS:
                    Log.d(TAG, "KSY_STREAMER_OPEN_STREAM_SUCCESS");
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();
                    beginInfoUploadTimer();
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_FILE_SUCCESS:
                    Log.d(TAG, "KSY_STREAMER_OPEN_FILE_SUCCESS");
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_RECORD_STOPPED:
                    Log.d(TAG, "KSY_STREAMER_FILE_RECORD_STOPPED");
                    mIsFileRecording = false;
                    stopChronometer();
                    break;
                case StreamerConstants.KSY_STREAMER_FRAME_SEND_SLOW:
                    Log.d(TAG, "KSY_STREAMER_FRAME_SEND_SLOW " + msg1 + "ms");
                    Toast.makeText(CameraActivity.this, "Network not good!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_RAISE:
                    Log.d(TAG, "BW raise to " + msg1 / 1000 + "kbps");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_DROP:
                    Log.d(TAG, "BW drop to " + msg1 / 1000 + "kpbs");
                    break;
                default:
                    Log.d(TAG, "OnInfo: " + what + " msg1: " + msg1 + " msg2: " + msg2);
                    break;
            }
        }
    };

    private KSYStreamer.OnErrorListener mOnErrorListener = new KSYStreamer.OnErrorListener() {
        @Override
        public void onError(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    mStreamer.stopCameraPreview();
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_WRITE_FAILED:
                    stopRecord();
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN: {
                    handleEncodeError();
                    if (mRecording) {
                        stopStream();
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startStream();
                            }
                        }, 3000);
                    }
                    if (mIsFileRecording) {
                        stopRecord();
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startRecord();
                            }
                        }, 50);
                    }
                }
                break;
                default:
                    if (mStreamer.getEnableAutoRestart()) {
                        mRecording = false;
                        stopChronometer();
                    } else {
                        stopStream();
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startStream();
                            }
                        }, 3000);
                    }
                    break;
            }
        }
    };

    private void beginInfoUploadTimer() {
        if (mPrintDebugInfo && mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updateDebugInfo();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDebugInfoTextView.setText(mDebugInfo);
                        }
                    });
                }
            }, 100, 1000);
        }
    }

    //update debug info
    private void updateDebugInfo() {
        if (mStreamer == null) return;
        mDebugInfo = String.format(Locale.getDefault(),
                "RtmpHostIP()=%s DroppedFrameCount()=%d \n " +
                        "ConnectTime()=%d DnsParseTime()=%d \n " +
                        "UploadedKB()=%d EncodedFrames()=%d \n" +
                        "CurrentKBitrate=%d Version()=%s",
                mStreamer.getRtmpHostIP(), mStreamer.getDroppedFrameCount(),
                mStreamer.getConnectTime(), mStreamer.getDnsParseTime(),
                mStreamer.getUploadedKBytes(), mStreamer.getEncodedFrames(),
                mStreamer.getCurrentUploadKBitrate(), KSYStreamer.getVersion());
    }

    private void startRecord() {
        if (mIsFileRecording) {
            return;
        }
        //录制开始成功后会发送StreamerConstants.KSY_STREAMER_OPEN_FILE_SUCCESS消息
        mStreamer.startRecord(mRecordUrl);
        mIsFileRecording = true;
    }

    private void onSwitchCamera() {
        mStreamer.switchCamera();
    }

    private void onFlashClick() {
        if (mIsFlashOpened) {
            mStreamer.toggleTorch(false);
            mIsFlashOpened = false;
        } else {
            mStreamer.toggleTorch(true);
            mIsFlashOpened = true;
        }
    }

    private void stopStream() {
        // stop stream
        mStreamer.stopStream();
        if (live_id != 0L) {
            updateStatus(live_id, 1);
            finish();
        }
        mRecording = false;
        stopChronometer();
    }

    private void startStream() {
        Log.d("aaa live id", String.valueOf(live_id));
        if (live_id != 0L) {
            mStreamer.setUrl(Constants.LIVE_URL+live_id);
            mStreamer.startStream();
            updateStatus(live_id, 0);
        }
        mRecording = true;
    }

    private void stopChronometer() {
        if (mRecording || mIsFileRecording) {
            return;
        }
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
    }

    private void stopRecord() {
        //录制结束为异步接口，录制结束后，
        //会发送StreamerConstants.KSY_STREAMER_FILE_RECORD_STOPPED消息，在这里再处理UI恢复工作
        mStreamer.stopRecord();
    }

    private class ButtonObserver implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.click_to_switch_beauty:
                    if (startBeauty) {
                        startBeauty = false;
                        clickToSwitchBeauty.setText(START_BEAUTY);
                    } else {
                        startBeauty = true;
                        clickToSwitchBeauty.setText(STOP_BEAUTY);
                    }
                    onBeautyChecked(startBeauty);
                    break;
                case switch_cam:
                    onSwitchCamera();
                    break;
                case R.id.backoff:
                    onBackoffClick();
                    break;
                case R.id.flash:
                    onFlashClick();
                    break;
                case R.id.click_to_capture_screenshot:
                    onCaptureScreenShotClick();
                    break;
                case R.id.exposure:
                    onExposureClick();
                    break;
                case R.id.camera_chat_iv:
                    initChatPop();
                    break;
                default:
                    break;
            }
        }
    }

    private void onBeautyChecked(boolean startBeauty) {
        if (startBeauty) {
            mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(),
                    ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO);
            List<ImgFilterBase> filters = mStreamer.getImgTexFilterMgt().getFilter();
            if (filters != null && !filters.isEmpty()) {
                final ImgFilterBase filter = filters.get(0);
                filter.setGrindRatio(99 / 100.f);
                filter.setWhitenRatio(80 / 100.f);

                if (filter instanceof ImgBeautyProFilter) {
                    float val = 50 / 50.f - 1.0f;
                    filter.setRuddyRatio(val);
                }
            }
        } else {
            mStreamer.getImgTexFilterMgt().setFilter((ImgFilterBase) null);
        }
    }

    private void onBackoffClick() {
        new AlertDialog.Builder(CameraActivity.this).setCancelable(true)
                .setTitle("结束直播?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        mChronometer.stop();
                        stopStream();
                        CameraActivity.this.finish();
                    }
                }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mRecording) {
                    onBackoffClick();
                } else {
                    finish();
                }

                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 曝光度调节
     */
    private void onExposureClick() {
        if (mExposureSeekBar.getVisibility() == View.VISIBLE) {
            mExposureSeekBar.setVisibility(View.GONE);
        } else {
            mExposureSeekBar.setVisibility(View.VISIBLE);
        }
    }

    private void onCaptureScreenShotClick() {
        mStreamer.requestScreenShot(new GLRender.ScreenShotListener() {
            @Override
            public void onBitmapAvailable(Bitmap bitmap) {
                BufferedOutputStream bos = null;
                try {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    final String filename = "/sdcard/screenshot" + dateFormat.format(date) + ".jpg";

                    bos = new BufferedOutputStream(new FileOutputStream(filename));
                    if (bitmap != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(CameraActivity.this, "保存截图到 " + filename,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (bos != null) try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void onShootClick() {
        if (mRecording) {
            stopStream();
        } else {
            startStream();
        }
    }

    private void onRecordClick() {
        if (mIsFileRecording) {
            stopRecord();
        } else {
            startRecord();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 一般可以在onResume中开启摄像头预览
        mStreamer.startCameraPreview();
        // 调用KSYStreamer的onResume接口
        mStreamer.onResume();
        // 如果onPause中切到了DummyAudio模块，可以在此恢复
        mStreamer.setUseDummyAudioCapture(false);
        startCameraPreviewWithPermCheck(true);
        mStreamer.setEnableAudioLowDelay(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mStreamer.onPause();
        // 一般在这里停止摄像头采集
        mStreamer.stopCameraPreview();
        // 如果希望App切后台后，停止录制主播端的声音，可以在此切换为DummyAudio采集，
        // 该模块会代替mic采集模块产生静音数据，同时释放占用的mic资源
        mStreamer.setUseDummyAudioCapture(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
        EMClient.getInstance().logout(true);
        leaveChatRoom();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        mStreamer.setOnLogEventListener(null);
        mStreamer.release();
    }

    private void startCameraPreviewWithPermCheck(boolean request) {
        int cameraPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int audioPerm = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (cameraPerm != PackageManager.PERMISSION_GRANTED ||
                audioPerm != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || !request) {
                Log.e(TAG, "No CAMERA or AudioRecord permission, please check");
                Toast.makeText(this, "No CAMERA or AudioRecord permission, please check",
                        Toast.LENGTH_LONG).show();
            } else {
                String[] permissions = {Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions,
                        PERMISSION_REQUEST_CAMERA_AUDIOREC);
            }
        } else {
            mStreamer.startCameraPreview();
            if (mAutoStart) {
                mAutoStart = false;
                startStream();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA_AUDIOREC: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStreamer.startCameraPreview();
                    if (mAutoStart) {
                        mAutoStart = false;
                        startStream();
                    }
                } else {
                    Log.e(TAG, "No CAMERA or AudioRecord permission");
                    Toast.makeText(this, "No CAMERA or AudioRecord permission",
                            Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    //创建直播
    @OnClick(camera_start_live_bt)
    public void startLive(View view) {
        //注册环信
        regEM();
        if (isNewLive) {//新建的直播
            createLive();
        } else {
            cameraStartLiveBt.setVisibility(View.GONE);
            onShootClick();
        }
        bottom_view.setVisibility(View.VISIBLE);
        initChatView();
    }

    //注册环信
    private void regEM() {
        //注册环信账号
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(String.valueOf(user_id), EMPassword);
                } catch (HyphenateException e) {
                    int errorCode = e.getErrorCode();
                    if (errorCode == EMError.EM_NO_ERROR) {//没有错误
                        handler.sendEmptyMessage(REG_SUCCESS);
                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {//该用户已经存在
                        handler.sendEmptyMessage(REG_SUCCESS);
                    } else {
                        handler.sendEmptyMessage(REG_FAILED);
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //登录环信
    private void loginEM() {
        EMClient.getInstance().login(String.valueOf(user_id), EMPassword, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                handler.sendEmptyMessage(LOGIN_SUCCESS);
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
                Log.d("aaaTAB", "登录失败" + message);
                handler.sendEmptyMessage(LOGIN_FAILED);
            }
        });
    }

    //加入聊天室
    private void joinChatRoom() {
        EMClient.getInstance().chatroomManager().joinChatRoom(roomId, new EMValueCallBack<EMChatRoom>() {
            @Override
            public void onSuccess(EMChatRoom value) {
                EMClient.getInstance().chatManager().addMessageListener(msgListener);
                joinChatRoomSuccess = true;
            }

            @Override
            public void onError(int error, String errorMsg) {
            }
        });
    }

    //离开聊天室
    private void leaveChatRoom() {
        EMClient.getInstance().chatroomManager().leaveChatRoom(roomId);
    }

    //创建直播
    private void createLive() {
        String live_name = cameraCreateLiveEt.getText().toString().trim();
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        long user_id = sp.getLong("user_id", 0);
        //生成随机数，作为直播类型
        Random random = new Random();
        int live_type = random.nextInt(2) + 1;
        //封面图片的数组
        String[] pics = new String[]{
                "http://img1.imgtn.bdimg.com/it/u=2376399524,1027148780&fm=214&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=963833047,3937484028&fm=26&gp=0.jpg",
                "http://img0.imgtn.bdimg.com/it/u=241544545,2518283365&fm=214&gp=0.jpg",
                "http://www.az-meter.com/d/imgs/bd24704890.jpg",
                "http://img3.imgtn.bdimg.com/it/u=614690089,4034631071&fm=214&gp=0.jpg",
                "http://pic2016.5442.com:82/2016/0615/27/2.jpg!960.jpg",
                "http://pic1.5442.com/2015/0814/03/01.jpg!960.jpg",
                "http://c.hiphotos.baidu.com/zhidao/pic/item/7a899e510fb30f24d8689e34ce95d143ad4b0312.jpg"
        };
        int picIndex = random.nextInt(pics.length);
        Log.d("aaa:index", picIndex + "");
        Log.d("aaa:live_type", live_type + "");
        CreateApi createApi = RetrofitManager.getTestRetrofit().create(CreateApi.class);
        FormBody body = new FormBody.Builder()
                .add("uid", String.valueOf(user_id))
                .add("pic", pics[picIndex])
                .add("live_name", live_name)
                .add("live_type", String.valueOf(live_type))
                .build();
        Call<LiveReposeBean> createCall = createApi.createLive(body);
        createCall.enqueue(new Callback<LiveReposeBean>() {
            @Override
            public void onResponse(Call<LiveReposeBean> call, Response<LiveReposeBean> response) {
                LiveReposeBean bean = response.body();
                if (bean.getResult() == null || bean.getError_code() != 0) {
                    ToastUtils.showShort(bean.getError_msg());
                    return;
                }
                cameraCreateLiveEt.setVisibility(View.GONE);
                cameraStartLiveBt.setVisibility(View.GONE);
                live_id = bean.getResult().getId();
                //创建成功
                onShootClick();
            }

            @Override
            public void onFailure(Call<LiveReposeBean> call, Throwable t) {
                ToastUtils.showShort("创建失败");
            }
        });
    }

    //改变直播状态
    private void updateStatus(long live_id, final int status) {
        UpdateStatusApi statusApi = RetrofitManager.getTestRetrofit().create(UpdateStatusApi.class);
        FormBody body = new FormBody.Builder()
                .add("live_id", live_id + "")
                .add("status", status + "")
                .build();
        Call<UpdateStatusBean> updateStatus = statusApi.updateStatus(body);
        updateStatus.enqueue(new Callback<UpdateStatusBean>() {
            @Override
            public void onResponse(Call<UpdateStatusBean> call, Response<UpdateStatusBean> response) {
                List<UpdateStatusBean.ResultBean> result = response.body().getResult();
                if (result == null || result.size() == 0) {
                    return;
                }
                String preStatus;
                if (status == 0) {
                    preStatus = "直播";
                } else {
                    preStatus = "录播";
                }
                ToastUtils.showShort("直播状态已更改为" + preStatus);
            }

            @Override
            public void onFailure(Call<UpdateStatusBean> call, Throwable t) {
                ToastUtils.showShort("更改直播失败" + t);
            }
        });
    }

    //初始化弹幕，点赞
    private void initChatView() {
        chatView.setVisibility(View.VISIBLE);
        zhiboChat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        chatContents = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ChatContent chatContent = new ChatContent("小源", "今天天气好晴朗");
            chatContents.add(chatContent);
        }
        zhiboChat.scrollToPosition(0);
        chatContentAdapter = new Play_Recycler_Ver(chatContents);
        zhiboChat.setAdapter(chatContentAdapter);
        //初始化点赞图片
        //每500毫秒生成一个心
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (zhiboHeartLayout == null) {
                    return;
                }
                zhiboHeartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (zhiboHeartLayout != null) {
                            zhiboHeartLayout.addHeart(randomColor());
                        }
                    }
                });
            }
        }, 500, 500);
    }

    //弹出对话气泡窗口
    private void initChatPop() {
        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        PopupWindow chatPop = new PopupWindow(this);
        chatPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        chatPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View chatPopView = LayoutInflater.from(this).inflate(R.layout.chat_pop, null);
        chatPop.setContentView(chatPopView);
        chatPop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        chatPop.setOutsideTouchable(true);
        chatPop.setFocusable(true);
        chatPop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        chatPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        chatPop.showAtLocation(chatPopView, Gravity.BOTTOM, 0, 0);
        //处理发送按钮的点击事件
        final EditText et_chat = chatPopView.findViewById(R.id.et_chat_pop);
        Button bt_chat_send = chatPopView.findViewById(R.id.bt_chat_pop);

        bt_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(et_chat.getWindowToken(), 0);
                final String chat_content = et_chat.getText().toString();
                if (TextUtils.isEmpty(chat_content)) {
                    Toast.makeText(CameraActivity.this, "消息内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!joinChatRoomSuccess) {
                    return;
                }

                EMMessage message = EMMessage.createTxtSendMessage(chat_content, roomId);
                message.setChatType(EMMessage.ChatType.ChatRoom);
                message.setAttribute("type", 1);
                message.setAttribute("user_name", user_name);
                message.setAttribute("content", chat_content);
                EMClient.getInstance().chatManager().sendMessage(message);
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        ChatContent chatContent = new ChatContent(user_name, chat_content);
                        Message msg = Message.obtain();
                        msg.obj = chatContent;
                        msg.what = SEND_MESSAGE_SUCCESS;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(int code, String error) {
                        handler.sendEmptyMessage(SEND_MESSAGE_FAILED);
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                    }
                });
                et_chat.setText("");
            }
        });
    }

    /**
     * 随机颜色
     *
     * @return
     */
    private int randomColor() {
        return Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
    }

    EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            Message message = Message.obtain();
            message.obj = messages;
            message.what = RECEIVE_MSG;
            handler.sendMessage(message);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };
}
