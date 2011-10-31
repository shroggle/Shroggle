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
package com.shroggle.presentation.menu;

import com.shroggle.entity.DraftMenu;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.MenuNotFoundException;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RemoveSelectedMenuServiceTest {

    RemoveSelectedMenuService service = new RemoveSelectedMenuService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    
    @Test
    public void testExecute() throws Exception {
        TestUtil.createUserAndLogin();
        DraftMenu menu = TestUtil.createMenu();
        Assert.assertNotNull(ServiceLocator.getPersistance().getMenuById(menu.getId()));
        service.execute(menu.getId(), 0);
        Assert.assertNull(ServiceLocator.getPersistance().getMenuById(menu.getId()));
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_withoutLogin() throws Exception {
        service.execute(0, 0);
    }
    
    @Test(expected = MenuNotFoundException.class)
    public void testExecute_withoutMenu() throws Exception {
        TestUtil.createUserAndLogin();
        service.execute(0, 0);
    }
}
