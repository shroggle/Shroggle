/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.util.reflection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * @author Artem Stasuk
 */
public class ClassesOneJar implements Classes {

    public ClassesOneJar(final String packageName) {
        this.packageName = packageName;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Class> get(final ClassesFilter filter) {
        try {
            return getInTryCatch(filter);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private List<Class> getInTryCatch(final ClassesFilter filter) throws IOException {
        final ClassLoader classLoader = ClassesOneJar.class.getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream("main/main.jar");
        final JarInputStream jarInputStream = new JarInputStream(inputStream);
        final String packagePath = packageName.replace('.', '/') + "/";
        final List<Class> classes = new ArrayList<Class>();

        ZipEntry zipEntry;
        while ((zipEntry = jarInputStream.getNextEntry()) != null) {
            final String classPath = zipEntry.getName();
            if (classPath.startsWith(packagePath) && classPath.contains(".class")) {
                String className = classPath.replace('/', '.');
                className = className.replace(".class", "");

                final Class<?> cClass;
                try {
                    cClass = classLoader.loadClass(className);
                } catch (final ClassNotFoundException exception) {
                    throw new RuntimeException(exception);
                }

                if (filter.accept(cClass)) {
                    classes.add(cClass);
                }
            }
        }

        return classes;
    }

    private final String packageName;

}