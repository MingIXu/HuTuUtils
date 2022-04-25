package com.greentree.test.class_isolation;

import java.lang.reflect.Method;

/**
 * TestMain
 *
 * @author hutu
 * @date 2022/4/25 11:11
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        Class<?> aClass = new CustomClassLoader().loadClass(TestA.class.getName());
        Method method = aClass.getMethods()[0];
        method.invoke(aClass.newInstance());

        Class<?> bClass = new CustomClassLoader().loadClass(TestB.class.getName());
        Method method2 = bClass.getMethods()[0];
        method2.invoke(bClass.newInstance());
    }
}
