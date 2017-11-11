package com.zxx.diamondlive.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zxx.diamondlive.R;
import com.zxx.diamondlive.activity.base.BaseActivity;
import com.zxx.diamondlive.bean.RegReposeBean;
import com.zxx.diamondlive.bean.UploadBean;
import com.zxx.diamondlive.network.RetrofitManager;
import com.zxx.diamondlive.network.api.RegisterApi;
import com.zxx.diamondlive.network.api.UploadApi;
import com.zxx.diamondlive.utils.RegexUtil;
import com.zxx.diamondlive.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {


    private static final int MY_PERMISSIONS_CAMERA = 1;
    private static final int PICTURE = 100;
    private static final int CAMERA = 200;
    private static final int RESULT_REQUEST_CODE = 300;
    private static final int MY_PERMISSIONS_PICTURE = 2;
    @BindView(R.id.reg_avatar)
    CircleImageView regAvatar;
    @BindView(R.id.reg_tv_nickname)
    TextView regTvNickname;
    @BindView(R.id.reg_et_nickname)
    EditText regEtNickname;
    @BindView(R.id.reg_nickname_input)
    TextView regNicknameInput;
    @BindView(R.id.reg_tv_phone)
    TextView regTvPhone;
    @BindView(R.id.reg_et_phone)
    EditText regEtPhone;
    @BindView(R.id.reg_phone_input)
    TextView regPhoneInput;
    @BindView(R.id.reg_tv_pwd)
    TextView regTvPwd;
    @BindView(R.id.reg_et_pwd)
    EditText regEtPwd;
    @BindView(R.id.reg_pwd_input)
    TextView regPwdInput;
    @BindView(R.id.reg_tv_pwd2)
    TextView regTvPwd2;
    @BindView(R.id.reg_et_pwd2)
    EditText regEtPwd2;
    @BindView(R.id.reg_pwd2_input)
    TextView regPwd2Input;
    @BindView(R.id.reg_tv_sign)
    TextView regTvSign;
    @BindView(R.id.reg_et_sign)
    EditText regEtSign;
    @BindView(R.id.reg_sign_input)
    TextView regSignInput;
    @BindView(R.id.reg_bt_reg)
    Button regBtReg;
    private String nickname;
    private String phone;
    private String pwd;
    private String pwdAgain;
    private String sign;

    private static final int NICKNAMEMAX = 6;
    private static final int PHONEMAX = 11;
    private static final int PWDMAX = 20;
    private static final int PWDAGAINMAX = 20;
    private static final int SIGNMAX = 20;
    private Uri imageUri;
    private String thumbnail_pic;

    @Override
    protected void initTitleBar(HeaderBuilder builder) {
        builder.goneToolbar();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        regEtNickname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(NICKNAMEMAX)});
        regEtPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PHONEMAX)});
        regEtPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PWDMAX)});
        regEtPwd2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PWDAGAINMAX)});
        regEtSign.setFilters(new InputFilter[]{new InputFilter.LengthFilter(SIGNMAX)});
        initData();
    }

    private void initData() {
        regEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                regNicknameInput.setText(s.length()+"/"+NICKNAMEMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        regEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                regPhoneInput.setText(s.length()+"/"+PHONEMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        regEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                regPwdInput.setText(s.length()+"/"+PWDMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        regEtPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                regPwd2Input.setText(s.length()+"/"+PWDAGAINMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        regEtSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                regSignInput.setText(s.length()+"/"+SIGNMAX);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    //注册按钮点击事件
    @OnClick(R.id.reg_bt_reg)
    public void registerClick(View v) {
        regBtReg.setClickable(false);
        regBtReg.setEnabled(false);
        nickname = regEtNickname.getText().toString().trim();
        phone = regEtPhone.getText().toString().trim();
        pwd = regEtPwd.getText().toString().trim();
        pwdAgain = regEtPwd2.getText().toString().trim();
        sign = regEtSign.getText().toString().trim();

        if (RegexUtil.checkBlankSpace(nickname) || nickname == null){
            ToastUtils.showShort("请输入昵称");
            return;
        }
//        if (!RegexUtil.checkMobile(phone)){
//            ToastUtils.showShort("请输入正确的手机号");
//            return;
//        }
        if (!pwd.equals(pwdAgain)) {
            ToastUtils.showShort("两次输入密码不一致");
            return;
        }
        if (RegexUtil.checkBlankSpace(sign) || sign == null){
            ToastUtils.showShort("请输入个人签名");
            return;
        }
        if (readImage() == null){
            ToastUtils.showShort("请上传图片");
            return;
        }
//        upload(readImage());
        register();
    }
    //选择上传头像的图片
    @OnClick(R.id.reg_avatar)
    public void uploadAvatar(View v){
        //显示对话框，选择图片来源
        String[] source = {"相机","图库"};
        new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(source, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case 0://相机
                            requestCameraPermission();
                            break;
                        case 1://图库
                            requestPicturePermission();
                            break;
                    }
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void openCamera() {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(RegisterActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA);
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,PICTURE);
    }

    private void upload(File file){
        MultipartBody.Part filepart =
                MultipartBody.Part.createFormData("pic",file.getName()+".jpg",
                        RequestBody.create(MediaType.parse("image/*"),file));

        UploadApi uploadApi = RetrofitManager.getTestRetrofit().create(UploadApi.class);

        Call<UploadBean> upload = uploadApi.upload(filepart);
        upload.enqueue(new Callback<UploadBean>() {
            @Override
            public void onResponse(Call<UploadBean> call, Response<UploadBean> response) {
                UploadBean.ResultBean result = response.body().getResult();
                thumbnail_pic = result.getThumbnail_pic();
                String original_pic = result.getOriginal_pic();
                String bmiddle_pic = result.getBmiddle_pic();
                register();
            }
            @Override
            public void onFailure(Call<UploadBean> call, Throwable t) {
                ToastUtils.showShort(t.getMessage()+"上传头像失败");
            }
        });
    }

    //从在服务器中注册，并返回数据
    private void register() {
        RegisterApi registerApi = RetrofitManager.getTestRetrofit().create(RegisterApi.class);
        FormBody formBody = new FormBody.Builder()
                .add("phone", phone)
                .add("user_name", nickname)
                .add("avatar","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508841445373&di=9631847c8b48c29ce9003d7bec9e193b&imgtype=0&src=http%3A%2F%2Fpic53.nipic.com%2Ffile%2F20141119%2F15088508_104943407000_2.jpg")//用户头像，
                .add("sign", sign)
                .add("password", pwd)
                .build();
        Call<RegReposeBean> register = registerApi.register(formBody);
        register.enqueue(new Callback<RegReposeBean>() {
            @Override
            public void onResponse(Call<RegReposeBean> call, Response<RegReposeBean> response) {
                RegReposeBean bean = response.body();
                if (bean.getError_code()==0){
                    //注册成功
                    ToastUtils.showShort("注册成功");
                    finish();
                }else{
                    ToastUtils.showShort(bean.getError_msg()+",请重新输入");
                    if (bean.getError_msg().equals("手机号已经被注册")){
                        regEtPhone.setText("");
                    }
                }
            }
            @Override
            public void onFailure(Call<RegReposeBean> call, Throwable t) {
                regBtReg.setEnabled(true);
                regBtReg.setClickable(true);
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestCameraPermission(){
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA))
                != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                ToastUtils.showShort("需要申请照相机权限，到设置中同意权限");
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_CAMERA);
            }
        }else{
            openCamera();
        }
    }

    private void requestPicturePermission(){
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                ToastUtils.showShort("需要申请读取SD卡权限，到设置中同意权限");
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_PICTURE);
            }
        }else{
            openAlbum();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_CAMERA:
                if (grantResults.length >0 &&
                        grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else {
                    ToastUtils.showShort("用户已拒绝");
                }
                break;
            case MY_PERMISSIONS_PICTURE:
                if (grantResults.length >0 &&
                        grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    ToastUtils.showShort("用户已拒绝");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICTURE && resultCode == RESULT_OK){
            //图库
            startPhotoZoom(data.getData());
        }else if (requestCode == CAMERA && resultCode == RESULT_OK){
            //相机
            startPhotoZoom(imageUri);
        }else if(requestCode == RESULT_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null){
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = extras.getParcelable("data");
                    Drawable drawable = new BitmapDrawable(this.getResources(), bitmap);
                    regAvatar.setImageDrawable(drawable);
                    saveImage(bitmap);
                }
            }
        }
    }

    // 保存到本地的方法
    private void saveImage(Bitmap bitmap) {
        File uploadImage;
        //判断sd卡是否处于挂载状态
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            uploadImage = this.getExternalFilesDir("");//sdcard/Android/data/应用包名/file/...jpg
        }else{
            uploadImage = this.getFilesDir();
        }

        FileOutputStream fos = null;
        try {
            File file = new File(uploadImage,"upload_image.jpg");
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos !=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //从本地读取图片
    private File readImage() {
        File filesDir;
        //判断sd卡是否处于挂载状态
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filesDir = this.getExternalFilesDir("");//sdcard/Android/data/应用包名/file/...jpg
        }else{
            filesDir = this.getFilesDir();
        }
        File file = new File(filesDir,"upload_image.jpg");
        if (file.exists()){
            return  file;
        }
        return file;
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }
}
