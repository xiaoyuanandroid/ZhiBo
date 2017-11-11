package com.zxx.diamondlive.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.PlayActivity;
import com.zxx.diamondlive.adapter.Hot_Recy_Ada;
import com.zxx.diamondlive.bean.Live;
import com.zxx.diamondlive.fragment.base.BaseNetFragment;
import com.zxx.diamondlive.listener.OnItemClickListener;
import com.zxx.diamondlive.network.RetrofitManager;
import com.zxx.diamondlive.network.api.LiveApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class Live_Hot_Fragment extends BaseNetFragment<Live> {

    @BindView(R.id.recycler_hot)
    RecyclerView recyclerHot;
    @BindView(R.id.hot_refresh)
    MaterialRefreshLayout hotRefresh;
    private Hot_Recy_Ada adapter;
    private List<Live.ResultBean.ListBean> list;
    private int type = 1;
    private int page = 1;
    private final int REFRESH = 0;
    private final int LOAD = 1;
    private int STATE = REFRESH; //默认为下拉刷新
    private List<Live.ResultBean.ListBean> newList = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.frg_hot;
    }

    @Override
    protected void initViews() {
        recyclerHot.setLayoutManager(new LinearLayoutManager(getActivity()));
        initRefresh();
    }

    @Override
    protected void loadData() {
        LiveApi liveApi = RetrofitManager.getTestRetrofit().create(LiveApi.class);
        FormBody formBody = new FormBody.Builder()
                .add("type", type+"")
                .add("page", page+"")
                .build();
        Call<Live> liveCall = liveApi.postLive(formBody);
        liveCall.enqueue(new Callback<Live>() {
            @Override
            public void onResponse(Call<Live> call, Response<Live> response) {
                if (response.body().getResult().getList() == null){
                    return;
                }
                goneLoading();
                hotRefresh.finishRefresh();
                hotRefresh.finishRefreshLoadMore();
                list = response.body().getResult().getList();
                if (adapter == null){
                    newList = list;
                    adapter = new Hot_Recy_Ada(getActivity(),newList);
                    recyclerHot.setAdapter(adapter);
                }else{
                    if (STATE == REFRESH){
                        newList = list;
                    }else{
                        newList.addAll(newList.size(),list);
                    }
                    adapter.refresh(newList);
                }
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Live.ResultBean.ListBean listBean = newList.get(i);
                        Intent intent = new Intent(getActivity(), PlayActivity.class);
                        intent.putExtra("avatar",listBean.getUser().getUser_data().getAvatar());
                        intent.putExtra("live_name",listBean.getData().getLive_name());
                        intent.putExtra("user_name",listBean.getUser().getUser_data().getUser_name());
                        intent.putExtra("status",listBean.getData().getStatus());
                        intent.putExtra("user_id",listBean.getUser().getId());
                        intent.putExtra("live_id",listBean.getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Live> call, Throwable t) {
                hotRefresh.finishRefresh();
                hotRefresh.finishRefreshLoadMore();
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRefresh(){
        hotRefresh.setLoadMore(true);
        hotRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                STATE = REFRESH;
                loadData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                STATE = LOAD;
                page ++;
                loadData();
            }
        });
    }

    @Override
    protected void processData(Live live) {

    }
}
