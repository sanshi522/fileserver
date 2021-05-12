package com.sanshi.fileserver.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public abstract class MergeObjectsUtil {
    /**
     * 【合并对象】
     * 新对象如果为空，不覆盖
     *
     * @param oldModel：老对象
     * @param newModel：新对象
     * @return 说明：对象相同，新对象不为空的属性覆盖老对象的属性
     */
    public static Object isNullNoCover(Object oldModel, Object newModel) {
        if (oldModel.getClass() != newModel.getClass()) return oldModel;//如果对象不相同直接返回老的
        Field[] oldFields = oldModel.getClass().getDeclaredFields();
        Field[] newFields = newModel.getClass().getDeclaredFields();
        // 对象的属性的访问权限设置为可访问

        for (int i = 0; i < oldFields.length; i++) {
            oldFields[i].setAccessible(true);
            newFields[i].setAccessible(true);
            //String name = newFields[i].getName();    //获取属性的名字
            // name = name.substring(0, 1).toUpperCase() + name.substring(1); //将属性的首字符大写，方便构造get，set方法
            try {
                if (newFields[i].get(newModel) != null)
                    oldFields[i].set(oldModel, newFields[i].get(newModel));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                //  return oldModel;
            }
        }
        return oldModel;
    }
}
