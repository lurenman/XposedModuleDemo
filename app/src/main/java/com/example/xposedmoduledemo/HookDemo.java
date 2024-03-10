package com.example.xposedmoduledemo;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 创建日期：2024-03-09
 * 作者:baiyang
 */
public class HookDemo implements IXposedHookLoadPackage {
    private static final String TAG = "HookDemo";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedBridge.log("HookDemo Hook成功咯，宝子们～_~!");
        //过滤包名，定位要Hook的包名
        if (loadPackageParam.packageName.equals("com.example.hookdemo")) {
            XposedHelpers.findAndHookMethod("com.example.hookdemo.util.MD5Utils", loadPackageParam.classLoader, "stringToMD5", String.class, new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    Object result = param.getResult();
                    //log的tag搜索LSPosed-Bridge
                    XposedBridge.log("stringToMD5 参数1 = " + param.args[0] + " result:" + result);
                }
            });
        }
    }
}
