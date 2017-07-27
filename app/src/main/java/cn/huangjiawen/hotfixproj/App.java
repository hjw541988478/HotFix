package cn.huangjiawen.hotfixproj;

import android.app.Application;

import cn.huangjiawen.hotfixlib.HotFix;

/**
 * Created by Administrator on 026 2017/7/26.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        HotFix.patch(this, "/sdcard/stub_dex.jar", "stub_dex.jar");
        super.onCreate();
    }
}
