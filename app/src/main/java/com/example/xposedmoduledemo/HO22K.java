package com.example.xposedmoduledemo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 参考：https://blog.csdn.net/Ananas_Orangey/article/details/126219878
 */
public class HO22K implements IXposedHookLoadPackage {

    private void hook_method(String className, ClassLoader classLoader, String methodName,
                             Object... parameterTypesAndCallback) {
        try {
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, parameterTypesAndCallback);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    private void hook_methods(String className, String methodName, XC_MethodHook xmh) {
        try {
            Class<?> clazz = Class.forName(className);
            for (Method method : clazz.getDeclaredMethods())
                if (method.getName().equals(methodName)
                        && !Modifier.isAbstract(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers())) {
                    XposedBridge.hookMethod(method, xmh);
                }
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    /**
     * xpose插件入口点
     *
     * @param
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 获取加载的apk程序包名
        XposedBridge.log("当前启动的应用程序是: " + loadPackageParam.packageName);
        XposedBridge.log("Hook成功咯，宝子们～_~!");

        //过滤包名，定位要Hook的包名
        if (loadPackageParam.packageName.equals("com.example.simplehookdemo")) {

            //定位要Hook的具体的类名
            Class clazz = loadPackageParam.classLoader.loadClass("com.example.simplehookdemo.MainActivity");
            //Hook的方法为toastMessage，XposedHelpers的静态方法 findAndHookMethod就是hook函数的的方法，其参数对应为   类名+loadPackageParam.classLoader（照写）+方法名+参数类型（根据所hook方法的参数的类型，即有多少个写多少个，加上.class）+XC_MethodHook回调接口；
            XposedHelpers.findAndHookMethod(clazz, "toastMessage", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    //param.setResult("你已被劫持")将返回的结果设置成了你已被劫持
                    param.setResult("哦吼，同学你已被劫持");
                }
            });

            XposedHelpers.findAndHookMethod("com.example.simplehookdemo.MainActivity", loadPackageParam.classLoader, "test", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    param.setResult("hello hook test");
                }
            });
            XposedHelpers.findAndHookMethod("android.os.SystemProperties", loadPackageParam.classLoader, "get", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    // super.afterHookedMethod(param);
                    Object result = param.getResult();
                    XposedBridge.log("参数1 = " + param.args[0] + " result:" + result);
                }
            });
        }
    }
}
