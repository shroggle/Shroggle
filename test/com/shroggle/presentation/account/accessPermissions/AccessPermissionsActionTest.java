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

package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestUtil;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;


public class AccessPermissionsActionTest extends TestBaseWithMockService {

    @Before
    public void before() {
        final ActionBeanContext actionBeanContext = new ActionBeanContext();
        actionBeanContext.setRequest(new MockHttpServletRequest("", ""));
        action.setContext(actionBeanContext);
    }

    @Test
    public void show() throws Exception {
        TestUtil.createUserAndLogin();

        
        action.show();


        final AccessPermissionsModel model = (AccessPermissionsModel)action.getHttpServletRequest().getAttribute("model");
        Assert.assertNotNull(model);
    }


    private final AccessPermissionsAction action = new AccessPermissionsAction();
}
