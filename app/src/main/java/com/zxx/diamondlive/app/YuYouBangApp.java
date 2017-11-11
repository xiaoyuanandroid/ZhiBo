package com.zxx.diamondlive.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zxx.diamondlive.network.HttpClient;
import com.zxx.diamondlive.utils.ContextUtil;
import com.zxx.diamondlive.utils.ScreenUtil;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * date: Created xiaoyuan on 16/11/05.
 */
public class YuYouBangApp extends Application {

    private static Context mApplicationContext;
    private static final String LOGIN = "login";
    private YuYouBangApp appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        ContextUtil.init(this);
        ScreenUtil.init(this);
        HttpClient.initOkHttpClient();
        initImageLoaderCache();
        initEM();
    }

    /**
     * 初始化平台分享
     */
    /*void initUmengShare() {
        //微信
        PlatformConfig.setWeixin(GlobalParams.WEIXIN_APP_KEY, GlobalParams.WEIXIN_APP_SECRET);
        //新浪微博
        PlatformConfig.setSinaWeibo(GlobalParams.SINA_APP_KEY, GlobalParams.SINA_APP_SECRET);
    }*/

    /**
     * 初始化imgloader
     */
    public void initImageLoaderCache() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "ImageLoader/Cache");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .memoryCacheExtraOptions(720, 1280)
                        // default = device screen dimensions
                .diskCacheExtraOptions(720, 1280, null)
                .memoryCache(new WeakMemoryCache())
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13)
                        // default
                .diskCache(new UnlimitedDiskCache(cacheDir))
                        // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                        // default
                .imageDownloader(
                        new BaseImageDownloader(getApplicationContext())) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .build();
        ImageLoader.getInstance().init(config);
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }

    //初始化环信SDK
    private void initEM() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        appContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //初始化
        EMClient.getInstance().init(appContext, options);
        //在做打包混淆时，关闭debug模消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
