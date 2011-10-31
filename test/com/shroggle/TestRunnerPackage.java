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

import com.shroggle.util.reflection.*;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.RunnerBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class TestRunnerPackage extends ParentRunner<Runner> {

    public TestRunnerPackage(final Class<?> testClass, final RunnerBuilder builder) throws Throwable {
        super(testClass);

        runners = new ArrayList<Runner>();
        final String[] packageNames = testClass.getAnnotation(TestPackages.class).value();
        for (final String packageName : packageNames) {
            final ClassesFilterAnd filter = new ClassesFilterAnd(
                    new ClassesFilterPackageName(packageName), new ClassesFilterMethodAnnotations(Test.class));
            final Classes classes = new ClassesTimeMeter(new ClassesSimple());
            for (final Class packageClass : classes.get(filter)) {
                runners.add(builder.runnerForClass(packageClass));
            }
        }
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    @Override
    protected Description describeChild(final Runner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(final Runner child, final RunNotifier notifier) {
        child.run(notifier);
    }

    private final List<Runner> runners;

}
