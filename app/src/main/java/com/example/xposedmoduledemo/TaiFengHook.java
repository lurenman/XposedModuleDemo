package com.example.xposedmoduledemo;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class TaiFengHook implements IXposedHookLoadPackage {
    private static final String TAG = "HookDemo";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedBridge.log("HookDemo Hook成功咯，宝子们～_~!");
        //过滤包名，定位要Hook的包名
        try {
            Log.d(TAG, "doHook ");
            setAdInfoEntryNull(loadPackageParam.classLoader);
        } catch (Throwable e) {
            Log.e(TAG, "doHook error:" + e.getMessage());
        }
    }

    private static void setAdInfoEntryNull(ClassLoader classLoader) {
        for (int i = 1; i < 23; i++) {
            int finalI = i;
            try {
                XposedHelpers.findAndHookMethod("com.bdc.chief.data.entry.adenter.AdInfoEntry", classLoader, "getAd_position_" + i, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(TAG, "afterHookedMethod getAd_position_" + finalI + " null");
                        param.setResult(null);
                    }
                });
            } catch (Throwable e) {
                Log.e(TAG, "setAdInfoEntryNull error: " + e.getMessage());
            }

        }

    }
}
