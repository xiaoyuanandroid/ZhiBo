package com.zxx.diamondlive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;
import com.zxx.diamondlive.adapter.MyLiveRecycler;
import com.zxx.diamondlive.bean.DeleteResponseBean;
import com.zxx.diamondlive.bean.MyLiveReposeBean;
import com.zxx.diamondlive.listener.OnItemClickListener;
import com.zxx.diamondlive.network.RetrofitManager;
import com.zxx.diamondlive.network.api.DeleteLiveApi;
import com.zxx.diamondlive.network.api.MyListApi;
import com.zxx.diamondlive.utils.ToastUtils;
import com.zxx.diamondlive.view.SwipeRefreshView;

import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLiveActivity extends BaseActivity {

    @BindView(R.id.my_live_recycler)
    RecyclerView myLiveRecycler;
    @BindView(R.id.swipe)
    SwipeRefreshView swipe;
    private int page = 1;
    private long user_id;
    private MyLiveRecycler adapter;

    @Override
    protected void initTitleBar(HeaderBuilder builder) {
        builder.setTitle("我的直播");
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_my_live;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLiveRecycler.setLayoutManager(new LinearLayoutManager(this));
        user_id = getIntent().getLongExtra("user_id", 0);
        initListener();
    }

    private void initListener() {
        initPullRefresh();
    }

    private void initPullRefresh() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLiveList();
            }
        });
    }

    //获取我的直播
    public void getLiveList() {
        MyListApi myListApi = RetrofitManager.getTestRetrofit().create(MyListApi.class);
        FormBody formBody = new FormBody.Builder()
                .add("uid", user_id + "")
                .add("page", String.valueOf(page))
                .build();
        Call<MyLiveReposeBean> createApiLive = myListApi.getMyList(formBody);
        createApiLive.enqueue(new Callback<MyLiveReposeBean>() {
            @Override
            public void onResponse(Call<MyLiveReposeBean> call, Response<MyLiveReposeBean> response) {
                final List<MyLiveReposeBean.ResultBean.ListBean> list = response.body().getResult().getList();
                if (response.body().getError_code() != 0 || list == null) {
                    ToastUtils.showShort("请求失败");
                    return;
                }
                swipe.setRefreshing(false);
                if (adapter == null) {
                    adapter = new MyLiveRecycler(list);
                } else {
                    adapter.refreshData(list);
                }
                MyLiveActivity.this.myLiveRecycler.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Intent intent = new Intent(MyLiveActivity.this, CameraActivity.class);
                        intent.putExtra("live_id", list.get(i).getId());
                        startActivity(intent);
                    }
                });
                adapter.setOnItemLongClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        isDelete(list, i);
                        adapter.refreshData(list);
                    }
                });
            }

            @Override
            public void onFailure(Call<MyLiveReposeBean> call, Throwable t) {
                ToastUtils.showShort("请求失败" + t);
            }
        });
    }

    //确认删除
    public void isDelete(final List<MyLiveReposeBean.ResultBean.ListBean> list, final int position) {
        new AlertDialog.Builder(MyLiveActivity.this).setCancelable(true)
                .setTitle("确定删除?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteLive(list.get(position).getId());
                        getLiveList();
                    }
                }).show();
    }

    //删除直播
    private void deleteLive(long live_id) {
        DeleteLiveApi deleteLiveApi = RetrofitManager.getTestRetrofit().create(DeleteLiveApi.class);
        FormBody body = new FormBody.Builder()
                .add("live_id", String.valueOf(live_id))
                .build();
        Call<DeleteResponseBean> liveCall = deleteLiveApi.deleteLive(body);
        liveCall.enqueue(new Callback<DeleteResponseBean>() {
            @Override
            public void onResponse(Call<DeleteResponseBean> call, Response<DeleteResponseBean> response) {
                if (response.body().getError_code() == 0) {
                    ToastUtils.showShort("删除成功");
                } else {
                    ToastUtils.showShort("删除失败");
                }
            }
            @Override
            public void onFailure(Call<DeleteResponseBean> call, Throwable t) {
                ToastUtils.showShort("删除失败" + t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLiveList();
    }
}
