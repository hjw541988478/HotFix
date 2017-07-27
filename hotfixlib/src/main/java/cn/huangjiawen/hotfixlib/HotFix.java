package cn.huangjiawen.hotfixlib;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by Administrator on 026 2017/7/26.
 */

public class HotFix {
    private static void setField(Object obj, Class clazz, String fieldName, Object fieldVal) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, fieldVal);
    }

    private static Object getField(Object obj, Class clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static void patch(Context context, String outerDexPath, String outDexName) {
        PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
        String dexPath = outerDexPath;
        try {
            Object dexPathList = getField(pathClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
            Object pluginPathList = getField(new DexClassLoader(dexPath, context.getDir("dex", Context.MODE_PRIVATE)
                            .getAbsolutePath(), dexPath, pathClassLoader),
                    Class.forName("dalvik.system.BaseDexClassLoader"), "pathList");
            Object dexElements = getField(dexPathList, dexPathList.getClass(), "dexElements");
            Object pluginElements = getField(pluginPathList, pluginPathList.getClass(), "dexElements");
            int pluginLen = Array.getLength(pluginElements);
            int newDexElementsLen = pluginLen + Array.getLength(dexElements);
            Object newDexElements = Array.newInstance(dexElements.getClass().getComponentType(), newDexElementsLen);
            for (int i = 0; i < newDexElementsLen; i ++) {
                if (i < pluginLen) {
                    Array.set(newDexElements, i, Array.get(pluginElements, i));
                } else {
                    Array.set(newDexElements, i, Array.get(dexElements, i - pluginLen));
                }
            }
            setField(dexPathList, dexPathList.getClass(), "dexElements", newDexElements);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
