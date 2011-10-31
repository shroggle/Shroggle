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
import com.shroggle.exception.CannotFindSubForumException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.MockWebContextGetter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(value = TestRunnerWithMockServices.class)
@Ignore
public class CreateThreadServiceTest {

    @Test
    public void executeWithoutThreadName() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        SubForum subForum = TestUtil.createSubForum();

        request.setPoll(false);
        request.setForumPostText("Post text");
        request.setThreadDescription("Thread description");

        Assert.assertEquals("ThreadWithNullOrEmptyName",
                createThreadService.execute(request, subForum.getSubForumId(), -1, false));
    }

    @Test
    public void executeWithoutPostText() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        SubForum subForum = TestUtil.createSubForum();

        request.setPoll(false);
        request.setThreadDescription("Thread description");
        request.setThreadName("Thread name");

        Assert.assertEquals("ForumPostWithNullOrEmptyText",
                createThreadService.execute(request, subForum.getSubForumId(), -1, false));
    }

    @Test
    public void executeWithoutPollQuestion() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        SubForum subForum = TestUtil.createSubForum();

        request.setPoll(true);
        request.setThreadDescription("Thread description");
        request.setThreadName("Thread name");
        request.setForumPostText("Post text");
        List<String> answers = new ArrayList<String>();
        answers.add("answer1");
        answers.add("answer2");
        request.setPollAnswers(answers);

        Assert.assertEquals("PollWithoutQuestionException",
                createThreadService.execute(request, subForum.getSubForumId(), -1, false));
    }

    @Test
    public void executeWithoutPollAnswers() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        SubForum subForum = TestUtil.createSubForum();

        request.setPoll(true);
        request.setThreadDescription("Thread description");
        request.setThreadName("Thread name");
        request.setForumPostText("Post text");
        request.setPollQuestion("Poll question");

        Assert.assertEquals("PollWithLessThanTwoAnswersException",
                createThreadService.execute(request, subForum.getSubForumId(), -1, false));
    }

    @Test
    public void executeWithOnePollAnswer() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        SubForum subForum = TestUtil.createSubForum();

        request.setPoll(true);
        request.setThreadDescription("Thread description");
        request.setThreadName("Thread name");
        request.setForumPostText("Post text");
        request.setPollQuestion("Poll question");
        List<String> answers = new ArrayList<String>();
        answers.add("answer1");
        request.setPollAnswers(answers);

        Assert.assertEquals("PollWithLessThanTwoAnswersException",
                createThreadService.execute(request, subForum.getSubForumId(), -1, false));
    }

    @Test
    public void executeWithCorrectData() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());

        request.setPoll(false);
        request.setThreadDescription("Thread description");
        request.setThreadName("Thread name");
        request.setForumPostText("Post text");

        User user = new User();
        user.setEmail("Visitor1");
        user.setPassword("password");
        persistance.putUser(user);

        TestUtil.loginUser(user);

        String response = createThreadService.execute(request, subForum.getSubForumId(), -1, false);
        Assert.assertEquals("/forum/showThread.action?" +
                "threadId=" + createThreadService.getNewThreadId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        ForumThread forumThread = persistance.getForumThreadById(createThreadService.getNewThreadId());
        Assert.assertEquals(forumThread.getAuthor(), user);
        Assert.assertEquals(subForum, forumThread.getSubForum());
        Assert.assertEquals("Thread description", forumThread.getThreadDescription());
        Assert.assertEquals("Thread name", forumThread.getThreadName());
        ForumPost forumPost = persistance.getForumPosts(forumThread.getThreadId()).get(0);
        Assert.assertEquals("Post text", forumPost.getText());
        Assert.assertEquals(forumPost.getAuthor(), user);
    }

    @Test
    public void executeForPollWithCorrectData() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        SubForum subForum = TestUtil.createSubForum();

        request.setPoll(true);
        request.setThreadDescription("Thread description");
        request.setThreadName("Thread name");
        request.setForumPostText("Post text");
        request.setPollQuestion("Poll question");
        List<String> answers = new ArrayList<String>();
        answers.add("answer1");
        answers.add("answer2");
        request.setPollAnswers(answers);

        String response = createThreadService.execute(request, subForum.getSubForumId(), -1, false);
        Assert.assertEquals("/forum/showThread.action?" +
                "threadId=" + createThreadService.getNewThreadId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        ForumThread forumThread = persistance.getForumThreadById(createThreadService.getNewThreadId());
        Assert.assertEquals(subForum, forumThread.getSubForum());
        Assert.assertEquals("Thread description", forumThread.getThreadDescription());
        Assert.assertEquals("Thread name", forumThread.getThreadName());
        Assert.assertEquals("Poll question", forumThread.getPollQuestion());
        Assert.assertEquals(answers, forumThread.getPollAnswers());
        ForumPost forumPost = persistance.getForumPosts(forumThread.getThreadId()).get(0);
        Assert.assertEquals("Post text", forumPost.getText());
    }

    @Test
    public void deleteThread() throws ServletException, IOException {
        Widget widget = new WidgetItem();
        persistance.putWidget(widget);

        final DraftForum forum = new DraftForum();
        forum.setName("My forum");
        persistance.putItem(forum);
        ((WidgetItem) widget).setDraftItem(forum);

        SubForum subForum = new SubForum();
        persistance.putSubForum(subForum);

        ForumThread forumThread1 = new ForumThread();
        forumThread1.setSubForum(subForum);
        persistance.putForumThread(forumThread1);

        ForumThread forumThread2 = new ForumThread();
        forumThread2.setSubForum(subForum);
        persistance.putForumThread(forumThread2);

        subForum.addForumThread(forumThread1);
        subForum.addForumThread(forumThread2);

        String response = createThreadService.deleteThread(forumThread1.getThreadId(), -1, false);
        Assert.assertEquals("/forum/showSubForum.action?subForumId=" + subForum.getSubForumId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        Assert.assertEquals(1, subForum.getForumThreads().size());
        Assert.assertEquals(forumThread2, subForum.getForumThreads().get(0));
    }

    @Test(expected = CannotFindThreadException.class)
    public void closeThreadWithoutThread() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());

        createThreadService.closeThread(subForum.getSubForumId(), -1, -1, false);
    }

    @Test
    public void closeThreadCorrectData() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        String response = createThreadService.closeThread(subForum.getSubForumId(), forumThread.getThreadId(), -1, false);
        Assert.assertEquals("/forum/showSubForum.action?" +
                "subForumId=" + subForum.getSubForumId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        Assert.assertTrue(forumThread.isClosed());
    }

    @Test(expected = CannotFindThreadException.class)
    public void openThreadWithoutThread() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());

        createThreadService.openThread(subForum.getSubForumId(), -1, -1, false);
    }

    @Test
    public void openThreadCorrectData() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        String response = createThreadService.openThread(subForum.getSubForumId(), forumThread.getThreadId(), -1, false);
        Assert.assertEquals("/forum/showSubForum.action?" +
                "subForumId=" + subForum.getSubForumId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        Assert.assertFalse(forumThread.isClosed());
    }

    @Test(expected = CannotFindThreadException.class)
    public void deleteThreadWithoutThreadId() throws ServletException, IOException {
        createThreadService.deleteThread(-1, -1, false);
    }

    @Test(expected = CannotFindThreadException.class)
    public void closeThreadWithoutThreadId() throws ServletException, IOException {
        createThreadService.closeThread(-1, -1, -1, false);
    }

    @Test(expected = CannotFindThreadException.class)
    public void renameWithoutThread() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        TestUtil.createForumThread(subForum);

        final RenameThreadRequest request = new RenameThreadRequest();
        request.setPoll(false);
        request.setThreadDescription("new desc");
        request.setThreadName("new name");
        request.setThreadId(-1);

        createThreadService.rename(request, -1, false);
    }

    @Test(expected = CannotFindSubForumException.class)
    public void renameWithoutSubForum() throws ServletException, IOException {
        ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("asd");
        persistance.putForumThread(forumThread);

        RenameThreadRequest request = new RenameThreadRequest();
        request.setPoll(false);
        request.setThreadDescription("new desc");
        request.setThreadName("new name");
        request.setThreadId(forumThread.getThreadId());

        createThreadService.rename(request, -1, false);
    }

    @Test
    public void renameWithCorrectData() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        RenameThreadRequest request = new RenameThreadRequest();
        request.setPoll(false);
        request.setThreadDescription("new desc");
        request.setThreadName("new name");
        request.setThreadId(forumThread.getThreadId());

        String response = createThreadService.rename(request, -1, false);
        Assert.assertEquals("/forum/showSubForum.action?" +
                "subForumId=" + subForum.getSubForumId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        Assert.assertEquals("new name", forumThread.getThreadName());
        Assert.assertEquals("new desc", forumThread.getThreadDescription());
    }

    @Test
    public void renameForPoll() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        RenameThreadRequest renameThreadRequest = new RenameThreadRequest();
        renameThreadRequest.setPoll(true);
        renameThreadRequest.setThreadDescription("new desc");
        renameThreadRequest.setThreadName("new name");
        List<String> answers = new ArrayList<String>();
        answers.add("answer1");
        answers.add("answer2");
        renameThreadRequest.setPollAnswers(answers);
        renameThreadRequest.setPollQuestion("new question");
        renameThreadRequest.setThreadId(forumThread.getThreadId());

        String response = createThreadService.rename(renameThreadRequest, -1, false);
        Assert.assertEquals("/forum/showSubForum.action?" +
                "subForumId=" + subForum.getSubForumId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        Assert.assertEquals("new name", forumThread.getThreadName());
        Assert.assertEquals("new desc", forumThread.getThreadDescription());
        Assert.assertEquals("new question", forumThread.getPollQuestion());
        Assert.assertEquals(2, forumThread.getPollAnswers().size());
    }

    @Test
    public void showCreateThreadForm() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());

        MockWebContext webContext = new MockWebContext();
        webContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        MockWebContextGetter mockWebContextGetter = new MockWebContextGetter(webContext);
        ServiceLocator.setWebContextGetter(mockWebContextGetter);

        String response = createThreadService.showCreateThreadForm(subForum.getSubForumId(), forumWidget.getWidgetId(), false);
        Assert.assertEquals("/forum/showCreateThreadForm.jsp",
                response);
        Assert.assertEquals(createThreadService.getSubForum(), subForum);
        Assert.assertEquals(createThreadService.getForumId(), (int) forumWidget.getDraftItem().getId());
        Assert.assertEquals(createThreadService.widgetId, forumWidget.getWidgetId());
    }

    @Test
    public void showCreatePollForm() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());

        MockWebContext webContext = new MockWebContext();
        webContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        MockWebContextGetter mockWebContextGetter = new MockWebContextGetter(webContext);
        ServiceLocator.setWebContextGetter(mockWebContextGetter);

        String response = createThreadService.showCreatePollForm(subForum.getSubForumId(), forumWidget.getWidgetId(), false);
        Assert.assertEquals("/forum/showCreateThreadForm.jsp?isPoll=true",
                response);
        Assert.assertEquals(createThreadService.getSubForum(), subForum);
        Assert.assertEquals(createThreadService.getForumId(), (int) forumWidget.getDraftItem().getId());
        Assert.assertEquals(createThreadService.widgetId, forumWidget.getWidgetId());
    }

    @Test
    public void showRenameThreadForm() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        MockWebContext webContext = new MockWebContext();
        webContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        MockWebContextGetter mockWebContextGetter = new MockWebContextGetter(webContext);
        ServiceLocator.setWebContextGetter(mockWebContextGetter);

        String response = createThreadService.showRenameThreadForm(forumThread.getThreadId(), forumWidget.getWidgetId(), false);
        Assert.assertEquals("/forum/showCreateThreadForm.jsp",
                response);
        Assert.assertEquals(createThreadService.getSubForum(), subForum);
        Assert.assertEquals(createThreadService.getForumId(), (int) forumWidget.getDraftItem().getId());
        Assert.assertEquals(createThreadService.widgetId, forumWidget.getWidgetId());
        Assert.assertEquals(createThreadService.renamedThread, forumThread);
    }

    @Test
    public void showRenamePollForm() throws ServletException, IOException {
        WidgetItem forumWidget = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)forumWidget.getDraftItem());
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        MockWebContext webContext = new MockWebContext();
        webContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        MockWebContextGetter mockWebContextGetter = new MockWebContextGetter(webContext);
        ServiceLocator.setWebContextGetter(mockWebContextGetter);

        String response = createThreadService.showRenamePollForm(forumThread.getThreadId(), forumWidget.getWidgetId(), false);
        Assert.assertEquals("/forum/showCreateThreadForm.jsp?isPoll=true",
                response);
        Assert.assertEquals(createThreadService.getSubForum(), subForum);
        Assert.assertEquals(createThreadService.getForumId(), (int) forumWidget.getDraftItem().getId());
        Assert.assertEquals(createThreadService.widgetId, forumWidget.getWidgetId());
        Assert.assertEquals(createThreadService.renamedThread, forumThread);
    }

    private final CreateThreadService createThreadService = new CreateThreadService();
    private final CreateThreadRequest request = new CreateThreadRequest();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}