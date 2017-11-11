package com.zxx.diamondlive.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.PlayActivity;
import com.zxx.diamondlive.adapter.GiftGridViewAdapter;
import com.zxx.diamondlive.adapter.GiftViewPagerAdapter;
import com.zxx.diamondlive.adapter.Play_Recycler_Hor;
import com.zxx.diamondlive.adapter.Play_Recycler_Ver;
import com.zxx.diamondlive.bean.ChatContent;
import com.zxx.diamondlive.bean.Gift;
import com.zxx.diamondlive.fragment.base.BaseFragment;
import com.zxx.diamondlive.heart.HeartLayout;
import com.zxx.diamondlive.utils.TimeUtil;
import com.zxx.diamondlive.utils.ToastUtils;
import com.zxx.diamondlive.view.MyGridView;
import com.zxx.diamondlive.view.MyViewPager;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class Play_Function_Fragment extends BaseFragment {


    @BindView(R.id.play_ll_person_info)
    LinearLayout playLlPersonInfo;
    @BindView(R.id.play_recycler_hor)
    RecyclerView playRecyclerHor;
    @BindView(R.id.play_tv_number)
    TextView playTvNumber;
    @BindView(R.id.play_recycler_ver)
    RecyclerView playRecyclerVer;
    @BindView(R.id.play_iv_room_down_chat)
    ImageView playIvRoomDownChat;
    @BindView(R.id.play_iv_room_down_gift)
    ImageView playIvRoomDownGift;
    @BindView(R.id.play_iv_room_down_music)
    ImageView playIvRoomDownMusic;
    @BindView(R.id.play_iv_room_down_close)
    ImageView playIvRoomDownClose;
    @BindView(R.id.play_tv_date)
    TextView playTvDate;
    @BindView(R.id.giftParent)
    LinearLayout giftParent;
    @BindView(R.id.play_avatar_iv)
    CircleImageView playAvatarIv;
    @BindView(R.id.play_live_name_tv)
    TextView playLiveNameTv;
    @BindView(R.id.play_user_name_tv)
    TextView playUserNameTv;
    @BindView(R.id.play_chronometer)
    Chronometer playChronometer;
    @BindView(R.id.heart_layout)
    HeartLayout heartLayout;

    private PopupWindow chatPop;
    private View chatPopView;
    private PopupWindow giftPop;
    private static final int GIFTCOLUMNS = 5;//礼物列表的列数
    private static final int PAGESIZE = 15;//礼物列表展示个数
    private View giftPopView;
    private List<ImageView> dotViewLists = new ArrayList<>();
    private ArrayList<ChatContent> chatContents;
    private Play_Recycler_Ver chatContentAdapter;

    private GiftControl giftControl;
    private String live_name;
    private String avatar;
    private String user_name;
    private long user_id;
    private InputMethodManager imm;
    private String my_name;
    private String roomId = "32224024002562";
    private long my_user_id;
    private String my_avatar;
    private String EMPassword = "111111";
    private PlayActivity  playActivity;

    private Handler handler = new Handler() {
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
                    } else if (msg.obj instanceof Gift.GiftListBean) {
                        Gift.GiftListBean gift = (Gift.GiftListBean) msg.obj;
                        GiftModel giftModel = new GiftModel();
                        giftModel.setGiftId(gift.getGiftName());
                        giftModel.setGiftName(gift.getGiftName());//礼物名字
                        giftModel.setGiftCount(1);
                        giftModel.setGiftPic(gift.getGiftPic());
                        giftModel.setSendUserName(my_name);
                        giftModel.setSendGiftTime(System.currentTimeMillis());
                        giftModel.setCurrentStart(false);
                        giftModel.setSendUserId(String.valueOf(my_user_id));
                        giftModel.setSendUserPic(my_avatar);
                        giftModel.setHitCombo(0);
                        giftControl.loadGift(giftModel);
                    }else{
                        //更改本地UI
                        heartLayout.addHeart(randomColor());
                    }
                    break;
                case SEND_MESSAGE_FAILED:
                    ToastUtils.showShort("发送失败");
                    break;
                case RECEIVE_MSG:
                    List<EMMessage> ms = (List<EMMessage>) msg.obj;
                    for (EMMessage m : ms) {
                        int type = m.getIntAttribute("type", 1);
                        if (type == 0) {//聊天
                            String content = m.getStringAttribute("message", "");
                            String user_name = m.getStringAttribute("user_name", "");
                            if (TextUtils.isEmpty(content) || TextUtils.isEmpty(user_name)) {
                                return;
                            }
                            ChatContent receive_content = new ChatContent(user_name, content);
                            chatContents.add(0, receive_content);
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
                            handler.sendEmptyMessage(SEND_MESSAGE_SUCCESS);
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
    private static final int REG_SUCCESS = 1;
    private static final int REG_FAILED = 2;
    private boolean joinChatRoomSuccess = false;
    private static final int SEND_MESSAGE_SUCCESS = 3;
    private static final int SEND_MESSAGE_FAILED = 4;
    private static final int RECEIVE_MSG = 5;
    private String chat_content;
    private Timer mTimer;
    private static final int LOGIN_SUCCESS = 6;
    private static final int LOGIN_FAILED = 7;
    private ClipboardManager mClipboardManager;

    @Override
    protected int getContentResId() {
        return R.layout.frg_play_function;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         playActivity = (PlayActivity) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mClipboardManager = (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
        //监听消息
        initData();
        initView();
    }

    private void initView() {
        //初始化点赞图片
        //每500毫秒生成一个心
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (heartLayout == null){
                    return;
                }
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addHeart(randomColor());
                    }
                });
            }
        }, 500, 500);

        playChronometer.start();
        //获取直播名
        live_name = ((PlayActivity) getActivity()).getLive_name();
        //获取直播用户信息
        avatar = ((PlayActivity) getActivity()).getAvatar();
        user_name = ((PlayActivity) getActivity()).getUser_name();
        user_id = ((PlayActivity) getActivity()).getUser_id();
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(getActivity()).load(avatar)
                    .error(R.mipmap.ic_my_avatar)
                    .into(playAvatarIv);
        } else {
            Glide.with(getActivity()).load(R.mipmap.ic_my_avatar).into(playAvatarIv);
        }
        if (!TextUtils.isEmpty(live_name)) {
            playLiveNameTv.setText(live_name);
        }
        if (!TextUtils.isEmpty(user_name)) {
            playUserNameTv.setText(user_name);
        }
        giftControl = new GiftControl(getActivity().getApplication());
        giftControl.setGiftLayout(false, giftParent, 3);
    }

    private void initData() {
        //获取用户自己的信息
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        my_name = sp.getString("user_name", "");
        my_user_id = sp.getLong("user_id", 0L);
        my_avatar = sp.getString("avatar", "");

        if (my_user_id == 0L) {
            return;
        }
        //注册环信账号
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(String.valueOf(my_user_id), EMPassword);
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
        playTvDate.setText(TimeUtil.getNowDate("yyyy/MM/dd"));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(this.avatar);
        }
        playRecyclerHor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        playRecyclerHor.setAdapter(new Play_Recycler_Hor(list));

        playRecyclerVer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));

        chatContents = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ChatContent chatContent = new ChatContent("小源", "今天天气好晴朗");
            chatContents.add(chatContent);
        }
        chatContentAdapter = new Play_Recycler_Ver(chatContents);
        playRecyclerVer.setAdapter(chatContentAdapter);
        initChatPop();
        initGiftPop();
    }

    //登录环信
    private void loginEM() {
        EMClient.getInstance().login(String.valueOf(my_user_id), EMPassword, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                handler.sendEmptyMessage(LOGIN_SUCCESS);
            }
            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
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

    //弹出对话气泡窗口
    private void initChatPop() {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        chatPop = new PopupWindow(getActivity());
        chatPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        chatPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        chatPopView = LayoutInflater.from(getActivity()).inflate(R.layout.chat_pop, null);
        chatPop.setContentView(chatPopView);
        chatPop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        chatPop.setOutsideTouchable(true);
        chatPop.setFocusable(true);
        chatPop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        chatPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //处理发送按钮的点击事件
        final EditText et_chat = chatPopView.findViewById(R.id.et_chat_pop);
        Button bt_chat_send = chatPopView.findViewById(R.id.bt_chat_pop);

        bt_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(et_chat.getWindowToken(), 0);
                chat_content = et_chat.getText().toString();
                if (TextUtils.isEmpty(chat_content)) {
                    Toast.makeText(getActivity(), "消息内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!joinChatRoomSuccess) {
                    return;
                }

                EMMessage message = EMMessage.createTxtSendMessage(chat_content, roomId);
                message.setChatType(EMMessage.ChatType.ChatRoom);
                message.setAttribute("type", 0);
                message.setAttribute("user_name", my_name);
                message.setAttribute("message", chat_content);
                EMClient.getInstance().chatManager().sendMessage(message);
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        ChatContent chatContent = new ChatContent(my_name, chat_content);
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

    //礼物气泡窗口
    private void initGiftPop() {
        //礼物列表的GridView
        ArrayList<MyGridView> myPageList = new ArrayList<>();
        giftPop = new PopupWindow(getActivity());
        giftPop.setOutsideTouchable(true);
        giftPop.setFocusable(true);
        giftPop.setBackgroundDrawable(new ColorDrawable(0x80000000));
        giftPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        giftPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        giftPopView = LayoutInflater.from(getActivity()).inflate(R.layout.gift_pop, null);
        giftPop.setContentView(giftPopView);

//        给礼物列表设置显示动画
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(getActivity(), R.anim.option_entry_from_bottom);
        giftPopView.startAnimation(animationSet);

        final List<Gift.GiftListBean> datas = readJson();
        final int pageCount = (int) Math.ceil(datas.size() * 1.0 / PAGESIZE);
        for (int i = 0; i < pageCount; i++) {
            //每个页面都创建一个GridView
            MyGridView gridView = new MyGridView(getActivity());
            gridView.setNumColumns(GIFTCOLUMNS);
            gridView.setAdapter(new GiftGridViewAdapter(getActivity(), datas, i, PAGESIZE));
            myPageList.add(gridView);
            final int currentPage = i;

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    position = position + currentPage * PAGESIZE;
                    final Gift.GiftListBean gift = datas.get(position);
                    //发送礼物消息
                    EMMessage message = EMMessage.createTxtSendMessage(gift.getGiftName(), roomId);
                    message.setChatType(EMMessage.ChatType.ChatRoom);
                    message.setAttribute("type", 2);
                    message.setAttribute("user_name", my_name);
                    message.setAttribute("user_id", my_user_id);
                    message.setAttribute("avatar", my_avatar);
                    message.setAttribute("gift_pic", gift.getGiftPic());
                    message.setAttribute("gift_id", gift.getGiftName());
                    message.setAttribute("gift_count", 1);
                    EMClient.getInstance().chatManager().sendMessage(message);

                    message.setMessageStatusCallback(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Message msg = Message.obtain();
                            msg.obj = gift;
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
                }
            });
        }

        MyViewPager giftPager = giftPopView.findViewById(R.id.vp_gift_pop);
        final GiftViewPagerAdapter myViewAdapter = new GiftViewPagerAdapter(myPageList);
        giftPager.setAdapter(myViewAdapter);

//        给ViewPager设置小圆点
        final LinearLayout ll_cursor = giftPopView.findViewById(R.id.ll_gift_pop_cursor);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.emoji_cursor_1);
        for (int i = 0; i < myPageList.size(); i++) {
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
            layoutParams.leftMargin = 5;
            iv.setLayoutParams(layoutParams);

            if (i == 0) {
                iv.setImageResource(R.mipmap.emoji_cursor_2);
            } else {
                iv.setImageResource(R.mipmap.emoji_cursor_1);
            }
            ll_cursor.addView(iv);
            dotViewLists.add(iv);
        }

        giftPager.addOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pageCount; i++) {
                    //选中的页面改变小圆点为选中状态，反之为未选中
                    if ((position % pageCount) == i) {
                        ImageView selectedIv = (ImageView) ll_cursor.getChildAt(i);
                        selectedIv.setImageResource(R.mipmap.emoji_cursor_2);
                    } else {
                        ImageView selectedIv = (ImageView) ll_cursor.getChildAt(i);
                        selectedIv.setImageResource(R.mipmap.emoji_cursor_1);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.play_iv_room_down_chat, R.id.play_iv_room_down_gift, R.id.play_iv_room_down_music, R.id.play_iv_room_down_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_iv_room_down_chat:
                if (chatPop == null || chatPopView == null) {
                    return;
                }
                chatPop.showAtLocation(chatPopView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.play_iv_room_down_gift:
                giftPop.showAtLocation(giftPopView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.play_iv_room_down_music:
                //发送消息
                EMMessage message = EMMessage.createTxtSendMessage("点赞", roomId);
                message.setChatType(EMMessage.ChatType.ChatRoom);
                message.setAttribute("type", 3);
                EMClient.getInstance().chatManager().sendMessage(message);
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                       handler.sendEmptyMessage(SEND_MESSAGE_SUCCESS);
                    }
                    @Override
                    public void onError(int code, String error) {
                        handler.sendEmptyMessage(SEND_MESSAGE_FAILED);
                    }
                    @Override
                    public void onProgress(int progress, String status) {
                    }
                });
                break;
            case R.id.play_iv_room_down_close:
                ((PlayActivity) (getActivity())).showCloseDialog();
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText("http://pili-live-hls.teseedu.com/oalive/"+playActivity.live_id+".m3u8");
                break;
        }
    }
    /**
     * 随机颜色
     * @return
     */
    private int randomColor() {
        return Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
    }
    private List<Gift.GiftListBean> readJson() {
        List<Gift.GiftListBean> datas = new ArrayList<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("gift.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            Gson gosn = new Gson();
            Gift gift = gosn.fromJson(inputStreamReader, Gift.class);
            datas = gift.getGiftList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
        EMClient.getInstance().logout(true);
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
        leaveChatRoom(); //退出聊天室
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
