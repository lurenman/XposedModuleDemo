package com.example.xposedmoduledemo;

import android.content.pm.PackageInfo;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 创建日期：2024-03-09
 * 作者:baiyang
 */
public class MobriskDemo implements IXposedHookLoadPackage {
    private static final String TAG = "HookDemo";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedBridge.log("HookDemo Hook成功咯，宝子们～_~!");
        //过滤包名，定位要Hook的包名
        if (loadPackageParam.packageName.equals("cn.tongdun.mobrisk.demo")) {
//            XposedHelpers.findAndHookMethod("cn.tongdun.android.OOoOoOO0O0oO00OoOoO0oO.oOoOoO0oo00O000oOoOOO.oooooo0OOooOoO0OO0o", loadPackageParam.classLoader, "o0o0Oo0", android.content.Context.class, java.lang.String.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    String androidid = (String) param.args[1];
//                    if ("android_id".equals(androidid)) {
//                        Object result = param.getResult();
//                        param.setResult("123456789");
//                        XposedBridge.log("android_id=" + result);
//                    }
//                }
//            });
//            XposedHelpers.findAndHookMethod("cn.tongdun.android.kKkKkKkKk₭KKk₭kkk.KKKKK₭KKK₭Kk₭₭kK.K₭KKKKkkKKK", loadPackageParam.classLoader, "KKkkKK₭Kk₭₭k", android.content.Context.class, java.lang.String.class, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                }
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    String androidid = (String) param.args[1];
//                    if ("android_id".equals(androidid)) {
//                        Object result = param.getResult();
//                        param.setResult("123456789");
//                        XposedBridge.log("android_id=" + result);
//                    }
//                }
//            });
            XposedBridge.hookAllMethods(Class.forName("android.content.pm.PackageParser"), "generatePackageInfo", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {

                }
            });

        }

    }
}
