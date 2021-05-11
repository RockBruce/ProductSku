package cn.edsmall.productsku.utils;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppGlobals {
    private static Application sApplication;
    public static Application getApplication(){
        if (sApplication==null){
            try {
                Class<?> aClass = Class.forName("android.app.ActivityThread");
                Method declaredMethod = aClass.getDeclaredMethod("currentApplication");
                 sApplication = (Application) declaredMethod.invoke(null, (Object[])null);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return sApplication;
    }
}
