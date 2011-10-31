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

import com.shroggle.entity.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.logic.menu.MenuPageCheckSometimes;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class UpdateMenuItemServiceTest {

    private final UpdateMenuItemService service = new UpdateMenuItemService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftMenu menu = TestUtil.createMenu();
        menu.setSiteId(site.getSiteId());
        final MenuItem menuItem1 = TestUtil.createMenuItem(menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(menu);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);

        final UpdateMenuItemRequest request = new UpdateMenuItemRequest();
        request.setName("name");
        request.setTitle("asdgasgasdf");
        request.setImageId(123);
        request.setItemUrlType(MenuUrlType.CUSTOM_URL);
        request.setPageId(345324);
        request.setCustomUrl("url");
        request.setSelectedMenuItemId(menuItem2.getId());

        final UpdateMenuItemResponse response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(menuItem2.getId(), response.getSelectedMenuItemId().intValue());
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        Assert.assertEquals(pagesHtmlTextCreator.getHtml(), response.getTreeHtml());
        Assert.assertEquals(new GetInfoAboutItemService().execute(menuItem2.getId()), response.getInfoAboutSelectedItem());

        Assert.assertEquals("name", menuItem2.getName());
        Assert.assertEquals("asdgasgasdf", menuItem2.getTitle());
        Assert.assertEquals(123, menuItem2.getImageId().intValue());
        Assert.assertEquals(MenuUrlType.CUSTOM_URL, menuItem2.getUrlType());
        Assert.assertEquals(345324, menuItem2.getPageId().intValue());
        Assert.assertEquals("url", menuItem2.getCustomUrl());
        Assert.assertEquals(MenuUrlType.CUSTOM_URL, menuItem2.getUrlType());
    }

    @Test
    public void testExecute_withNotFoundMenuItem() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftMenu menu = TestUtil.createMenu();
        menu.setSiteId(site.getSiteId());
        final MenuItem menuItem1 = TestUtil.createMenuItem(menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(menu);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);

        final UpdateMenuItemRequest request = new UpdateMenuItemRequest();
        request.setSelectedMenuItemId(null);

        final UpdateMenuItemResponse response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(null, response.getSelectedMenuItemId());
        Assert.assertEquals(null, response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
    }
}
