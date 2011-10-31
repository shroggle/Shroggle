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
package com.shroggle.presentation.account.items;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.user.items.UserItemsInfo;
import com.shroggle.logic.user.items.UserItemsSortType;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ManageItemsActionTest {

    @Before
    public void setMockRequest() {
        action.setContext(new ActionBeanContext());
        final MockHttpServletRequest request = new MockHttpServletRequest("a", "b");
        request.setSession(new MockHttpSession(new MockServletContext("")));
        action.getContext().setRequest(request);
    }

    @Test
    public void executeWithout() {
        final User user = TestUtil.createUserAndLogin();
        action.setItemType(ItemType.BLOG);
        action.getContext().getRequest().setAttribute("userItemsSortType", UserItemsSortType.DATE_CREATED);
        action.getContext().getRequest().setAttribute("descending", true);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(((Paginator)action.getContext().getRequest().getAttribute("paginator")).getItems().isEmpty());
        Assert.assertEquals(ItemType.BLOG, action.getItemType());
        Assert.assertEquals(user, action.getLoginUser());
        Assert.assertNull(action.getInfoText());
        Assert.assertFalse(action.isShowAddButton());
        // Sort order should be reseted to default.
        Assert.assertEquals(UserItemsSortType.NAME, action.getContext().getRequest().getAttribute("userItemsSortType"));
        Assert.assertEquals(false, action.getContext().getRequest().getAttribute("descending"));
        Assert.assertEquals("/user/manageItems.jsp", resolutionMock.getForwardToUrl());
    }
    
    @Test
    public void executeIfCameFromDeleteItems() {
        final User user = TestUtil.createUserAndLogin();
        action.setItemType(ItemType.BLOG);
        action.setFromRemoveItems(true);
        action.setSortType(UserItemsSortType.DATE_CREATED);
        action.setDescending(true);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(((Paginator)action.getContext().getRequest().getAttribute("paginator")).getItems().isEmpty());
        Assert.assertEquals(ItemType.BLOG, action.getItemType());
        Assert.assertEquals(user, action.getLoginUser());
        Assert.assertNull(action.getInfoText());
        Assert.assertFalse(action.isShowAddButton());
        // Sort order should be preserved.
        Assert.assertEquals(UserItemsSortType.DATE_CREATED, action.getContext().getRequest().getAttribute("userItemsSortType"));
        Assert.assertEquals(true, action.getContext().getRequest().getAttribute("descending"));
        Assert.assertEquals("/user/manageItems.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithBlogWithoutOwner() {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        SiteOnItem siteOnBlogRight = new SiteOnItem();
        siteOnBlogRight.getId().setItem(blog);
        siteOnBlogRight.getId().setSite(site);
        siteOnBlogRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnBlogRight);

        action.setItemType(ItemType.BLOG);
        ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(action.isShowAddButton());
        Assert.assertEquals(1, ((Paginator)action.getContext().getRequest().getAttribute("paginator")).getItems().size());
        UserItemsInfo showUserItem = (UserItemsInfo)((Paginator)action.getContext().getRequest().getAttribute("paginator")).getItems().get(0);
        Assert.assertEquals(ItemType.BLOG, action.getItemType());
        Assert.assertEquals(user, action.getLoginUser());
        Assert.assertNull(action.getInfoText());
        Assert.assertEquals("/user/manageItems.jsp", resolutionMock.getForwardToUrl());
    }

    private final ManageItemsAction action = new ManageItemsAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
