package com.example.xposedmoduledemo;

import android.content.Context;
import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 创建日期：2024-03-09
 * 作者:baiyang
 */
public class PaasDemo implements IXposedHookLoadPackage {
    private static final String TAG = "HookDemo";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedBridge.log("HookDemo Hook成功咯，宝子们～_~!");
        //过滤包名，定位要Hook的包名
        if (loadPackageParam.packageName.startsWith("cn.tongdun.demo")) {
            //注意Build设置时机
            XposedHelpers.findAndHookMethod("android.app.Application", loadPackageParam.classLoader, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    String fingerprint = Build.FINGERPRINT;
                    if (fingerprint.equals("xiaomi/lavender/lavender:10/QKQ1.190910.002/V12.5.7.0.QFGCNXM:user/release-keys")){
                        XposedHelpers.findField(Build.class, "TAGS").set(null, "test-keys");
                    }
                    // XposedHelpers.findField(Build.class, "BRAND").set(null, "mini");
                    XposedBridge.log("class loader:" + loadPackageParam.classLoader);
                }
            });
        }
    }
}
