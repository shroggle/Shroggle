package com.shroggle.util.reflection;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class ClassesClassLoader implements Classes {

    public ClassesClassLoader(final String classLoaderClass, final Classes yes, final Classes no) {
        if (classLoaderClass == null) {
            throw new IllegalArgumentException("Null class for loader!");
        }

        if (no == null) {
            throw new IllegalArgumentException("Null for no!");
        }

        if (yes == null) {
            throw new IllegalArgumentException("Null for yes!");
        }

        this.yes = yes;
        this.no = no;
        this.classLoaderClass = classLoaderClass;
    }

    @Override
    public List<Class> get(final ClassesFilter filter) {
        final ClassLoader classLoader = ClassesClassLoader.class.getClassLoader();
        if (classLoader.getClass().getName().equals(classLoaderClass)) {
            return yes.get(filter);
        }
        return no.get(filter);
    }

    private String classLoaderClass;
    private Classes yes;
    private Classes no;

}
