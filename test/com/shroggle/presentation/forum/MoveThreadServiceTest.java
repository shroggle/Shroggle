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
import com.shroggle.entity.*;
import com.shroggle.exception.CannotFindSubForumException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.exception.ThreadWithoutSubForumException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(value = TestRunnerWithMockServices.class)
public class MoveThreadServiceTest {

    private MoveThreadService service = new MoveThreadService();
    private Persistance persistance = ServiceLocator.getPersistance();

    @Test(expected = CannotFindThreadException.class)
    public void showWithNotFoundThread() throws IOException, ServletException {
        service.show(-1, -1, false);
    }

    @Test(expected = CannotFindThreadException.class)
    public void executeWithNotFoundThread() throws IOException, ServletException {
        service.execute(-1, -1, -1, false);
    }

    @Test(expected = ThreadWithoutSubForumException.class)
    public void executeWithThreadWithoutSubForum() throws IOException, ServletException {
        final ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("Name");
        forumThread.setThreadDescription("Desc");
        forumThread.setClosed(false);
        persistance.putForumThread(forumThread);

        service.execute(forumThread.getThreadId(), -1, -1, false);
    }

    @Test(expected = CannotFindSubForumException.class)
    public void executeWithNotFoundSubForum() throws IOException, ServletException {
        final SubForum subForum = new SubForum();
        persistance.putSubForum(subForum);

        final ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("Name");
        forumThread.setThreadDescription("Desc");
        forumThread.setSubForum(subForum);
        forumThread.setClosed(false);
        persistance.putForumThread(forumThread);

        service.execute(forumThread.getThreadId(), -1, -1, false);
    }

    @Test
    public void show() throws IOException, ServletException {
        Widget widget = new WidgetItem();
        persistance.putWidget(widget);

        DraftForum forum = new DraftForum();
        persistance.putItem(forum);
        ((WidgetItem) widget).setDraftItem(forum);

        SubForum subForum = new SubForum();
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        ForumThread forumThread = new ForumThread();
        subForum.addForumThread(forumThread);
        persistance.putForumThread(forumThread);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        Assert.assertEquals(service.show(forumThread.getThreadId(), -1, false), "/forum/moveThread.jsp");
    }

    @Test
    public void execute() throws IOException, ServletException {
        Widget widget = new WidgetItem();
        persistance.putWidget(widget);

        DraftForum forum = new DraftForum();
        persistance.putItem(forum);
        ((WidgetItem) widget).setDraftItem(forum);

        SubForum subForum1 = new SubForum();
        forum.addSubForum(subForum1);
        persistance.putSubForum(subForum1);

        SubForum subForum2 = new SubForum();
        forum.addSubForum(subForum2);
        persistance.putSubForum(subForum2);

        ForumThread forumThread = new ForumThread();
        subForum1.addForumThread(forumThread);
        persistance.putForumThread(forumThread);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        Assert.assertEquals("/forum/showSubForum.action?subForumId=" + subForum1.getSubForumId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                service.execute(forumThread.getThreadId(), subForum2.getSubForumId(), -1, false));
        Assert.assertEquals(forumThread.getSubForum(), subForum2);
        Assert.assertEquals(subForum1.getForumThreads().size(), 0);
        Assert.assertEquals(subForum2.getForumThreads().get(0), forumThread);
    }

}