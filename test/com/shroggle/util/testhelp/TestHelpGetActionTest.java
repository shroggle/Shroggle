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
package com.shroggle.util.testhelp;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class TestHelpGetActionTest {

    @Test
    public void showWithoutStorage() {
        ServiceLocator.setTestHelpStorage(null);
        ResolutionMock resolutionMock = (ResolutionMock) action.show();
        Assert.assertEquals("/system/testHelpGet.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(action.getStringBySources());
    }

    @Test
    public void show() {
        ResolutionMock resolutionMock = (ResolutionMock) action.show();
        Assert.assertEquals("/system/testHelpGet.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNotNull(action.getStringBySources());
    }

    private final TestHelpGetAction action = new TestHelpGetAction();

}
