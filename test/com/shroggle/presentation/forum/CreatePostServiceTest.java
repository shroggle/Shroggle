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
import com.shroggle.exception.CannotCreatePostInClosedThread;
import com.shroggle.exception.CannotFindPostException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.exception.ForumPostWithNullOrEmptyText;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Date;

@RunWith(value = TestRunnerWithMockServices.class)
public class CreatePostServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) createPostService.getContext();
        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setCookies(new Cookie[0]);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
    }

    @Test
    public void show() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        String response = createPostService.showCreatePostForm(false, false, forumThread.getThreadId(), 0, false, -1, false);
        Assert.assertEquals("/forum/showCreatePostForm.jsp", response);
    }

    @Test(expected = ForumPostWithNullOrEmptyText.class)
    public void executeWithoutPostText() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        createPostService.execute("", forumThread.getThreadId(), -1, false);
    }

    @Test(expected = CannotFindThreadException.class)
    public void executeWithoutThreadId() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();

        createPostService.execute("Text", 0, -1, false);
    }

    @Test
    public void executeWithCorrectData() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        User user = TestUtil.createUserAndLogin();

        createPostService.execute("Text", forumThread.getThreadId(), -1, false);
        Assert.assertEquals(forumThread.getForumPosts().size(), 1);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getText(), "Text");
        Assert.assertEquals(forumThread.getForumPosts().get(0).getAuthor(), user);
    }

    @Test
    public void createDraftPost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        User user = TestUtil.createUserAndLogin();

        createPostService.previewNewPost("Text", forumThread.getThreadId(), -1, false);
        Assert.assertEquals(forumThread.getForumPosts().size(), 1);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getText(), null);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getAuthor(), user);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getDraftText(), "Text");
    }

    @Test
    public void editDraftPost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        ForumPost forumPost = TestUtil.createForumPost(forumThread);

        createPostService.previewEditedPost("Text", forumPost.getForumPostId(), -1, false);
        Assert.assertEquals(forumThread.getForumPosts().size(), 1);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getText(), "Post1");
        Assert.assertEquals(forumThread.getForumPosts().get(0).getDraftText(), "Text");
    }

    @Test
    public void submitDraftPost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        ForumPost forumPost = TestUtil.createDraftForumPost(forumThread);

        createPostService.submitDraftPost(forumPost.getForumPostId(), -1, false);
        Assert.assertEquals(forumThread.getForumPosts().size(), 1);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getText(), "Post1");
        Assert.assertEquals(forumThread.getForumPosts().get(0).getDraftText(), null);
    }

    @Test
    public void discardDraftPost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        ForumPost forumPost = TestUtil.createDraftForumPost(forumThread);

        createPostService.discardDraftPost(forumPost.getForumPostId(), -1, false);
        Assert.assertEquals(persistance.getForumPosts(forumThread.getThreadId()).size(), 0);
    }

    @Test
    public void discardEditedDraftPost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        ForumPost forumPost = TestUtil.createForumPost(forumThread);

        forumPost.setDraftText("New text");

        createPostService.discardDraftPost(forumPost.getForumPostId(), -1, false);
        Assert.assertEquals(forumThread.getForumPosts().size(), 1);
        Assert.assertEquals(forumThread.getForumPosts().get(0).getText(), "Post1");
        Assert.assertEquals(forumThread.getForumPosts().get(0).getDraftText(), null);
    }

    @Test(expected = CannotCreatePostInClosedThread.class)
    public void executeWithClosedThread() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        forumThread.setClosed(true);

        createPostService.execute("Text", forumThread.getThreadId(), -1, false);
    }

    @Test
    public void saveEditedPost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);
        ForumPost forumPost = TestUtil.createForumPost(forumThread);

        Date creationDate = forumPost.getDateCreated();

        createPostService.saveEditedPost("new text", forumPost.getForumPostId(), -1, false);
        Assert.assertEquals(forumPost.getText(), "new text");
        Assert.assertSame(forumPost.getDateCreated(), creationDate);
    }

    @Test(expected = CannotCreatePostInClosedThread.class)
    public void saveEditedPostInClosedThread() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);
        ForumPost forumPost = TestUtil.createForumPost(forumThread);

        forumThread.setClosed(true);

        createPostService.saveEditedPost("new text", forumPost.getForumPostId(), -1, false);
    }

    @Test
    public void deletePost() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);
        ForumPost forumPost = TestUtil.createForumPost(forumThread);

        String response = createPostService.deletePost(forumThread.getThreadId(), forumPost.getForumPostId(), -1, false);
        Assert.assertEquals("/forum/showThread.action?threadId=" + forumThread.getThreadId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                response);
        Assert.assertTrue(forumThread.getForumPosts().isEmpty());
    }

    @Test(expected = CannotFindThreadException.class)
    public void deletePostWithoutThreadId() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);
        ForumPost forumPost = TestUtil.createForumPost(forumThread);

        createPostService.deletePost(-1, forumPost.getForumPostId(), -1, false);
    }

    @Test(expected = CannotFindPostException.class)
    public void deletePostWithoutPostId() throws ServletException, IOException {
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();
        SubForum subForum = TestUtil.createSubForum((DraftForum)(widgetForum.getDraftItem()));
        ForumThread forumThread = TestUtil.createForumThread(subForum);

        createPostService.deletePost(forumThread.getThreadId(), -1, -1, false);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CreatePostService createPostService = new CreatePostService();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}
