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

import com.shroggle.util.IOUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Artem Stasuk
 */
public class ClassesSimple implements Classes {

    public ClassesSimple() {
    }

    private List<Class> getClassesReal() {
//        final Pattern pattern = Pattern.compile(".*");
        final Pattern pattern = Pattern.compile("^(?!Test)[a-zA-Z0-9]+\\.class");

        final String path = IOUtil.baseDir() + "/WEB-INF/classes";
        final String pathWithDots = path.replaceAll("[\\\\/]+", ".");

        final List<File> classFiles = IOUtil.searchFiles(pattern, path);
        final List<Class> classes = new ArrayList<Class>();
        for (final File classFile : classFiles) {
            String className = classFile.getPath();
            className = className.replaceAll("[\\\\/]+", ".");
            className = className.substring(pathWithDots.length() + 1);
            className = className.substring(0, className.length() - ".class".length());

            final Class aClass = loadClass(className);
            if (aClass != null) {
                classes.add(aClass);
            }
        }
        return classes;
    }

    private Class loadClass(final String className) {
        try {
            return ClassesSimple.class.getClassLoader().loadClass(className);
        } catch (final ClassNotFoundException exception) {
            //logger.warning("Unable to load class by name = " + className);
            return className.contains(".") ? loadClass(className.replaceFirst("^[a-zA-Z]+\\.", "")) : null;
        } catch (Error e) {// todo. Review this. Tolik
            return null;
        }
    }

    private List<Class> getClasses() {
        List<Class> classes = null;

        if (weakReferenceClasses != null) {
            classes = weakReferenceClasses.get();
        }

        if (classes == null) {
            classes = getClassesReal();
            weakReferenceClasses = new WeakReference<List<Class>>(classes);
        }

        return classes;
    }

    @Override
    public List<Class> get(final ClassesFilter filter) {
        final List<Class> result = new ArrayList<Class>();

        for (final Class cClass : getClasses()) {
            if (filter.accept(cClass)) {
                result.add(cClass);
            }
        }

        return result;
    }

    private WeakReference<List<Class>> weakReferenceClasses;

}
