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
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * @author Stasuk Artem
 */
public class ParameterizedStubRunner extends BlockJUnit4ClassRunner {

    public ParameterizedStubRunner(
            final Class<?> type, final List<Object[]> parameterList, final int i)
            throws InitializationError {
        super(type);
        fParameterList = parameterList;
        fParameterSetNumber = i;
    }

    @Override
    public Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(computeParams());
    }

    private Object[] computeParams() {
        try {
            return fParameterList.get(fParameterSetNumber);
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("must return a Collection of arrays.");
        }
    }

    @Override
    protected String getName() {
        return String.format("[%s]", fParameterSetNumber);
    }

    @Override
    protected String testName(final FrameworkMethod method) {
        return String.format("%s[%s]", method.getName(),
                fParameterSetNumber);
    }

    @Override
    protected void validateZeroArgConstructor(List<Throwable> errors) {
        // constructor can, nay, should have args.
    }

    @Override
    protected Statement classBlock(RunNotifier notifier) {
        return childrenInvoker(notifier);
    }

    private final int fParameterSetNumber;
    private final List<Object[]> fParameterList;

}
