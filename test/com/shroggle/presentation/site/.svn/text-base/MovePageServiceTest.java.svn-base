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

package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class MovePageServiceTest {

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        Page page0 = TestUtil.createPageVersionAndPage(site).getPage();
        Page page1 = TestUtil.createPageVersionAndPage(site).getPage();
        Page page2 = TestUtil.createPageVersionAndPage(site).getPage();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(page0.getPageId(), menu1);
        final MenuItem element2 = TestUtil.createMenuItem(page1.getPageId(), menu1);
        final MenuItem element3 = TestUtil.createMenuItem(page2.getPageId(), menu1);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/


        service.execute(page2.getPageId(), 0, page0.getPageId());

        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure of this menu is changed because it has DEFAULT structure and there is pages with needed id and DEFAULT type*/
        Assert.assertEquals(1, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(2, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(true, element1.getChildren().contains(element3));
        Assert.assertEquals(0, element2.getChildren().size());
        Assert.assertEquals(0, element3.getChildren().size());
        Assert.assertEquals("This element has position 0 as expected", 0, element3.getPosition());
        Assert.assertEquals("And this element now moved to position 1", 1, element2.getPosition());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotMy() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        service.execute(pageVersion.getPage().getPageId() + 1, 0, null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        service.execute(-1, 0, null);
    }

    private final MovePageService service = new MovePageService();
}