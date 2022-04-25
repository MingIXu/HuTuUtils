package com.greentree.utils;

import cn.hutool.core.util.ClassUtil;

import java.io.InputStream;

/**
 * ClassUtils
 *
 * @author hutu
 * @date 2022/4/25 11:09
 */
public class ClassUtils {
    public static String getPackagePath(Class<?> clazz) {
        return ClassUtil.getPackage(clazz) + "." + clazz.getSimpleName();
    }

    public static String getAbsolutePath(Class<?> clazz) {
        return clazz.getResource("").getPath() + clazz.getSimpleName();
    }

    public static void main(String[] args) {
        InputStream is = ClassUtils.class.getResourceAsStream("/com/greentree/test/class_isolation/TestA.class");
        System.out.println();
    }
}
