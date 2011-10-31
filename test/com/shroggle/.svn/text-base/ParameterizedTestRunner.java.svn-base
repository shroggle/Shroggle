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

package com.shroggle;

import org.junit.runner.Runner;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParameterizedTestRunner extends Suite {

    public ParameterizedTestRunner(final Class<?> testClass) throws Throwable {
        super(testClass, Collections.<Runner>emptyList());

        final ParameterizedUsedRunner needRunner =
                testClass.getAnnotation(ParameterizedUsedRunner.class);
        Class<? extends ParameterizedStubRunner> needRunnerClass = ParameterizedStubRunner.class;
        if (needRunner != null) {
            needRunnerClass = needRunner.value();
        }

        final List<Object[]> parametersList = getParametersList(getTestClass());
        for (int i = 0; i < parametersList.size(); i++) {
            final Constructor<? extends ParameterizedStubRunner> constructor =
                    needRunnerClass.getConstructor(Class.class, List.class, int.class);
            runners.add(constructor.newInstance(getTestClass().getJavaClass(), parametersList, i));
        }
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> getParametersList(final TestClass testClass) throws Throwable {
        return (List<Object[]>) getParametersMethod(testClass).invokeExplosively(null);
    }

    private FrameworkMethod getParametersMethod(final TestClass testClass) throws Exception {
        final List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Parameterized.Parameters.class);
        for (final FrameworkMethod each : methods) {
            final int modifiers = each.getMethod().getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers))
                return each;
        }

        throw new Exception("No public static parameters method on class "
                + testClass.getName());
    }

    private final ArrayList<Runner> runners = new ArrayList<Runner>();

}