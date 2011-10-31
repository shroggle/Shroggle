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
import com.shroggle.entity.MenuItem;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class MoveMenuItemServiceTest {

    private final MoveMenuItemService service = new MoveMenuItemService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute_toZeroPosition() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(menuItem5.getId(), menuItem1.getId(), 0);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(3, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem5, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(1));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(2));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(1, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_toFirstPosition() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(menuItem5.getId(), menuItem1.getId(), 1);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(3, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem1.getChildren().get(1));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(2));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(1, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withoutPosition() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(menuItem5.getId(), menuItem1.getId(), null);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(3, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));
        Assert.assertEquals(menuItem5, menuItem1.getChildren().get(2));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(1, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withNegativePosition() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(menuItem5.getId(), menuItem1.getId(), -1);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(3, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));
        Assert.assertEquals(menuItem5, menuItem1.getChildren().get(2));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(1, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withNotFoundParent() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(menuItem5.getId(), -1, 0);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals("Element added here because parent was not found.", 2, menu.getMenuItems().size());
        Assert.assertEquals("Element added here because parent was not found.", menuItem5, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(1));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(1, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withNotFoundParent_withNull() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(menuItem5.getId(), null, 0);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals("Element added here because parent was not found.", 2, menu.getMenuItems().size());
        Assert.assertEquals("Element added here because parent was not found.", menuItem5, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(1));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(1, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withNotFoundChild() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        service.execute(-1, menuItem1.getId(), 0);


        /*-------------------------------------Checking structure after execution-------------------------------------*/
        // Structure is the same
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }
}
