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
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Special test runner that before start tests set to ServiceLocator mock services and
 * remove their from it after executed tests. For use it define above test class next
 * line @RunWith(TestRunnerWithMockServices.class), for access service use in test
 * classes ServiceLocator.getXXX()
 *
 * @author Artem Stasuk
 * @see com.shroggle.util.ServiceLocator
 * @see com.shroggle.TestUtil
 */
public class TestRunnerWithMockServices extends BlockJUnit4ClassRunner {

    public TestRunnerWithMockServices(final Class<?> testClass) throws InitializationError {
        super(testClass);
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
