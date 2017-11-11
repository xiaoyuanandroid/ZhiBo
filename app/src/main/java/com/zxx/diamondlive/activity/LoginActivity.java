package com.zxx.diamondlive.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;
import com.zxx.diamondlive.bean.LoginReposeBean;
import com.zxx.diamondlive.network.RetrofitManager;
import com.zxx.diamondlive.network.api.LoginApi;
import com.zxx.diamondlive.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.zxx.diamondlive.R.id.login_phone_input;
import static com.zxx.diamondlive.R.id.login_pwd_input;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_tv_phone)
    TextView loginTvPhone;
    @BindView(R.id.login_et_phone)
    EditText loginEtPhone;
    @BindView(login_phone_input)
    TextView loginPhoneInput;
    @BindView(R.id.login_tv_pwd)
    TextView loginTvPwd;
    @BindView(R.id.login_et_pwd)
    EditText loginEtPwd;
    @BindView(login_pwd_input)
    TextView loginPwdInput;
    @BindView(R.id.login_bt_login)
    Button loginBtLogin;
    @BindView(R.id.login_tv_register)
    TextView loginTvRegister;
    private String pwd;
    private String phone;

    private static final int PHONEMAX = 11;
    private static final int PWDMAX = 20;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void initTitleBar(HeaderBuilder builder) {
        builder.goneToolbar();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
       //初始化输入字符长度
        loginPhoneInput.setText("0"+"/"+PHONEMAX);
        loginPwdInput.setText("0"+"/"+PWDMAX);
        loginEtPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PHONEMAX)});
        loginEtPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PWDMAX)});
        inputListener();
    }

    //处理输入框的监听事件
    private void inputListener() {
        loginEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPhoneInput.setText(s.length() + "/" + PHONEMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPwdInput.setText(s.length() + "/" + PWDMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @OnClick(R.id.login_tv_register)
    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.login_bt_login)
    public void login(View v){
        phone = loginEtPhone.getText().toString().trim();
        pwd = loginEtPwd.getText().toString().trim();

        LoginApi loginApi = RetrofitManager.getTestRetrofit().create(LoginApi.class);
        FormBody formBody = new FormBody.Builder()
                .add("phone",phone)
                .add("password",pwd)
                .build();
        loginApi.login(formBody).enqueue(new Callback<LoginReposeBean>() {
            @Override
            public void onResponse(Call<LoginReposeBean> call, Response<LoginReposeBean> response) {
                LoginReposeBean bean = response.body();
                if (bean.getError_code() == 0){
                    //登录成功
                    ToastUtils.showShort("登录成功");
                    LoginReposeBean.ResultBean.UserDataBean user = bean.getResult().getUser_data();
//                    存储到SP
                    sp = getSharedPreferences("user",MODE_PRIVATE);
                    edit = sp.edit();
                    edit.putLong("user_id",bean.getResult().getId());
                    edit.putString("phone",user.getPhone());
                    edit.putString("avatar",user.getAvatar());
                    edit.putString("sign",user.getSign());
                    edit.putString("user_name",user.getUser_name());
                    edit.putString("password",pwd);
                    edit.commit();

//                    跳转到主界面
                    Intent intent = new Intent(LoginActivity.this,LiveHostActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    //登录失败
                    ToastUtils.showShort(bean.getError_msg()+",请重新输入");;
                    loginEtPhone.setText("");
                    loginEtPwd.setText("");
                }

            }

            @Override
            public void onFailure(Call<LoginReposeBean> call, Throwable t) {
                ToastUtils.showShort("登录失败");
            }
        });
    }
}
