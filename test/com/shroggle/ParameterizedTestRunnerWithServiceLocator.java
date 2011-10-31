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

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.List;

public class ParameterizedTestRunnerWithServiceLocator extends ParameterizedStubRunner {

    public ParameterizedTestRunnerWithServiceLocator(
            final Class<?> type, final List<Object[]> parameterList,
            final int i) throws InitializationError {
        super(type, parameterList, i);
    }

    @Override
    protected void runChild(final FrameworkMethod method, final RunNotifier notifier) {
        TestUtil.initServiceLocator();
        try {
        super.runChild(method, notifier);
        } finally {
            TestUtil.clearServiceLocator();
        }
    }

}