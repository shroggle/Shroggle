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

package com.shroggle.presentation.forum;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.ForumNameNotUniqueException;
import com.shroggle.exception.ForumNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(value = TestRunnerWithMockServices.class)
public class EditForumServiceTest {

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(forum);
        page.addWidget(widget);

        EditForumRequest request = new EditForumRequest();
        request.setNewForumName("new forum name");
        request.setForumId(forum.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setAllowSubForums(false);
        request.setCreatePollRight(AccessGroup.ALL);
        request.setCreatePostRight(AccessGroup.ALL);
        request.setCreateSubForumRight(AccessGroup.ALL);
        request.setCreateThreadRight(AccessGroup.ALL);
        request.setManagePostsRight(AccessGroup.ALL);
        request.setManageSubForumsRight(AccessGroup.ALL);
        request.setVoteInPollRight(AccessGroup.ALL);

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);

        Assert.assertNotNull(functionalWidgetInfo);
        Assert.assertEquals(widget.getWidgetId(), functionalWidgetInfo.getWidget().getWidgetId());

        Assert.assertNotNull(forum);
        Assert.assertEquals("new forum name", forum.getName());
        Assert.assertEquals(false, forum.isAllowSubForums());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreatePollRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreatePostRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreateSubForumRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreateThreadRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getManagePostsRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getManageSubForumsRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getVoteInPollRight());
        Assert.assertEquals(1, persistance.getSubForumsByForumId(forum.getId()).size());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        EditForumRequest request = new EditForumRequest();
        request.setNewForumName("new forum name");
        request.setForumId(forum.getId());
        request.setAllowSubForums(false);
        request.setCreatePollRight(AccessGroup.ALL);
        request.setCreatePostRight(AccessGroup.ALL);
        request.setCreateSubForumRight(AccessGroup.ALL);
        request.setCreateThreadRight(AccessGroup.ALL);
        request.setManagePostsRight(AccessGroup.ALL);
        request.setManageSubForumsRight(AccessGroup.ALL);
        request.setVoteInPollRight(AccessGroup.ALL);

        final FunctionalWidgetInfo functionalWidgetInfo = service.execute(request);

        Assert.assertNotNull(functionalWidgetInfo);
        Assert.assertNull(functionalWidgetInfo.getWidget());

        Assert.assertNotNull(forum);
        Assert.assertEquals("new forum name", forum.getName());
        Assert.assertEquals(false, forum.isAllowSubForums());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreatePollRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreatePostRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreateSubForumRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getCreateThreadRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getManagePostsRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getManageSubForumsRight());
        Assert.assertEquals(AccessGroup.ALL, forum.getVoteInPollRight());
        Assert.assertEquals(1, persistance.getSubForumsByForumId(forum.getId()).size());
    }

    @Test(expected = ForumNameNotUniqueException.class)
    public void executeWithDuplicateForumName() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        final DraftForum forum2 = new DraftForum();
        forum2.setSiteId(site.getSiteId());
        forum2.setName("bb");
        persistance.putItem(forum2);

        final WidgetItem widget = TestUtil.createWidgetItem();
        widget.setDraftItem(forum);
        page.addWidget(widget);

        EditForumRequest request = new EditForumRequest();
        request.setNewForumName("bb");
        request.setForumId(forum.getId());
        request.setWidgetId(widget.getWidgetId());
        request.setAllowSubForums(false);
        request.setCreatePollRight(AccessGroup.ALL);
        request.setCreatePostRight(AccessGroup.ALL);
        request.setCreateSubForumRight(AccessGroup.ALL);
        request.setCreateThreadRight(AccessGroup.ALL);
        request.setManagePostsRight(AccessGroup.ALL);
        request.setManageSubForumsRight(AccessGroup.ALL);
        request.setVoteInPollRight(AccessGroup.ALL);

        service.execute(request);
    }

    @Test(expected = ForumNotFoundException.class)
    public void executeWithNotFoundForum() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        EditForumRequest request = new EditForumRequest();
        request.setNewForumName("bb");
        request.setForumId(-1);
        request.setAllowSubForums(false);
        request.setCreatePollRight(AccessGroup.ALL);
        request.setCreatePostRight(AccessGroup.ALL);
        request.setCreateSubForumRight(AccessGroup.ALL);
        request.setCreateThreadRight(AccessGroup.ALL);
        request.setManagePostsRight(AccessGroup.ALL);
        request.setManageSubForumsRight(AccessGroup.ALL);
        request.setVoteInPollRight(AccessGroup.ALL);

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLoginedUser() throws Exception {
        EditForumRequest request = new EditForumRequest();
        request.setNewForumName("bb");
        request.setForumId(-1);
        request.setAllowSubForums(false);
        request.setCreatePollRight(AccessGroup.ALL);
        request.setCreatePostRight(AccessGroup.ALL);
        request.setCreateSubForumRight(AccessGroup.ALL);
        request.setCreateThreadRight(AccessGroup.ALL);
        request.setManagePostsRight(AccessGroup.ALL);
        request.setManageSubForumsRight(AccessGroup.ALL);
        request.setVoteInPollRight(AccessGroup.ALL);

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForum forum = new DraftForum();
        forum.setSiteId(site.getSiteId());
        forum.setName("aa");
        persistance.putItem(forum);

        EditForumRequest request = new EditForumRequest();
        request.setNewForumName("bb");
        request.setForumId(forum.getId());
        request.setWidgetId(-1);
        request.setAllowSubForums(false);
        request.setCreatePollRight(AccessGroup.ALL);
        request.setCreatePostRight(AccessGroup.ALL);
        request.setCreateSubForumRight(AccessGroup.ALL);
        request.setCreateThreadRight(AccessGroup.ALL);
        request.setManagePostsRight(AccessGroup.ALL);
        request.setManageSubForumsRight(AccessGroup.ALL);
        request.setVoteInPollRight(AccessGroup.ALL);

        service.execute(request);
    }

    private final EditForumService service = new EditForumService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
