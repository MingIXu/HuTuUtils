package com.greentree.test.class_isolation;


import sun.misc.Launcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * CustomClassLoader
 *
 * @author hutu
 * @date 2022/4/25 10:36
 */
public class CustomClassLoader extends ClassLoader {

    /**
     * main 函数入口都是 appClassLoader 加载的，此处获取 extClassLoader 加载 jdk 与 ext 类。
     */
    final ClassLoader parent = Thread.currentThread().getContextClassLoader().getParent();

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }
                if (c == null) {
                    c = findClass(name);
                }
            }
            return c;
        }
    }

    /**
     * 从 apClassLoader {@link com.sun.tools.internal.xjc.api.util.ApClassLoader} 拷的，也可以自己实现
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder(name.length() + 6);
        sb.append(name.replace('.', '/')).append(".class");
        InputStream is = this.getResourceAsStream(sb.toString());
        if (is == null) {
            throw new ClassNotFoundException("Class not found" + sb);
        } else {
            Class var19;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                int len;
                while ((len = is.read(buf)) >= 0) {
                    baos.write(buf, 0, len);
                }

                buf = baos.toByteArray();
                int i = name.lastIndexOf(46);
                if (i != -1) {
                    String pkgname = name.substring(0, i);
                    Package pkg = this.getPackage(pkgname);
                    if (pkg == null) {
                        this.definePackage(pkgname, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (URL) null);
                    }
                }

                var19 = this.defineClass(name, buf, 0, buf.length);
            } catch (IOException var17) {
                throw new ClassNotFoundException(name, var17);
            } finally {
                try {
                    is.close();
                } catch (IOException var16) {
                }

            }

            return var19;
        }
    }
}
