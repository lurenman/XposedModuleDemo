package com.example.xposedmoduledemo;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 创建日期：2024-03-09
 * 作者:baiyang
 */
public class MigoDemo implements IXposedHookLoadPackage {
    private static final String TAG = "MigoDemo";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        ClassLoader classLoader = loadPackageParam.classLoader;
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedBridge.log("HookDemo Hook成功咯，宝子们～_~!");
        //过滤包名，定位要Hook的包名
        if (loadPackageParam.packageName.equals("com.migo.mobile")) {
            try {
                Log.d(TAG, "start hook MigoHook2");
                XposedHelpers.findAndHookMethod("cn.tongdun.mobrisk.TDRisk", classLoader, "initWithOptions", android.content.Context.class, classLoader.loadClass("cn.tongdun.mobrisk.TDRisk$Builder"), new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Log.d(TAG, "TDRisk start init: " + param.toString());
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "hook error1: " + e.getMessage());
            }

            try {
                XposedHelpers.findAndHookMethod("com.base.mobile.f", classLoader, "onEvent", java.lang.String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        String blackbox = (String) param.args[0];
                        Log.d(TAG, "getBalckboxAsync afterHookedMethod result: " + blackbox);
                    }
                });

            } catch (Exception e) {
                Log.d(TAG, "hook error2: " + e.getMessage());
            }
            try {
                XposedHelpers.findAndHookMethod("cn.tongdun.mobrisk.TDRisk", classLoader, "getBlackBox", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Thread thread = Thread.currentThread();
                        Log.d(TAG, "getBalckbox beforeHookedMethod currentThread: " + thread.getName());
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        String result = (String) param.getResult();
                        Log.d(TAG, "getBalckbox afterHookedMethod result: " + result);
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "hook error3: " + e.getMessage());
            }
        }
    }
}
