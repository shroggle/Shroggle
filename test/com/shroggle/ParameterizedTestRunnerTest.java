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

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Stasuk Artem
 */
@RunWith(ParameterizedTestRunner.class)
@ParameterizedUsedRunner(ParameterizedStubRunner.class)
public class ParameterizedTestRunnerTest {

    @Parameterized.Parameters
    public static Collection regExValues() {
        return Arrays.asList(new Object[][]{
                {"1", true},
                {"2", true},
                {"3", true}});
    }

    public ParameterizedTestRunnerTest(final String firstParameter, boolean secondParameter) {
        this.firstParameter = firstParameter;
        this.secondParameter = secondParameter;
    }

    @Test
    public void execute() {
        Assert.assertTrue(firstParameter.length() > 0);
        Assert.assertTrue(secondParameter);
    }

    private String firstParameter;
    private boolean secondParameter;

}
