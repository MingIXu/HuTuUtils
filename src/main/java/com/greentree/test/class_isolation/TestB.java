package com.greentree.test.class_isolation;

/**
 * TestB
 *
 * @author hutu
 * @date 2022/4/25 10:39
 */
public class TestB {

    public void printClassLoaderName(){
        System.out.println(this.getClass().getSimpleName()+": "+this.getClass().getClassLoader());
    }
}
