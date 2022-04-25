package com.greentree.test.class_isolation;

/**
 * TestA
 *
 * @author hutu
 * @date 2022/4/25 10:38
 */
public class TestA {
    public void printClassLoaderName(){
        System.out.println(this.getClass().getSimpleName()+": "+this.getClass().getClassLoader());
        TestB testB = new TestB();
        testB.printClassLoaderName();
    }
}
