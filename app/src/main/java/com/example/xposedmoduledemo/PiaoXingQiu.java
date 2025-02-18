package com.example.xposedmoduledemo;

import android.content.Context;
import android.content.pm.PackageManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 创建日期：2024-03-09
 * 作者:baiyang
 */
public class PiaoXingQiu implements IXposedHookLoadPackage {
    private static final String TAG = "TD_TEST";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedHelpers.findAndHookMethod("android.app.Application", loadPackageParam.classLoader, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("PiaoXingQiu Hook afterHookedMethod");
                Context context = (Context) param.args[0];
                PackageManager packageManager = context.getPackageManager();
                String installerPackageName = packageManager.getInstallerPackageName(context.getPackageName());
                XposedBridge.log("PiaoXingQiu installerPackageName: " + installerPackageName);
            }
        });
    }
}
