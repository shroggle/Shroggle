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
package com.shroggle.presentation.blog;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowBlogPostCommentsActionTest {

    @Test
    public void executeWithoutBlogPost() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetBlog = new WidgetItem();
        persistance.putWidget(widgetBlog);
        pageVersion.addWidget(widgetBlog);


        action.setWidgetBlogId(widgetBlog.getWidgetId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/blog/showBlogPostCommentsNotFound.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithoutWidgetBlog() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = new DraftBlog();
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        action.setBlogPostId(blogPost.getBlogPostId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/blog/showBlogPostComments.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(1, blogPost.getPostRead());
    }

    @Test
    public void executeWithSort() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftBlog blog = TestUtil.createBlog(site);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment veryOldComment = new Comment();
        veryOldComment.setCreated(new Date(System.currentTimeMillis() / 3));
        blogPost.addComment(veryOldComment);
        persistance.putComment(veryOldComment);

        final Comment comment = new Comment();
        blogPost.addComment(comment);
        persistance.putComment(comment);

        final Comment oldComment = new Comment();
        oldComment.setCreated(new Date(System.currentTimeMillis() / 2));
        blogPost.addComment(oldComment);
        persistance.putComment(oldComment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.setWidgetBlogId(2);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("/blog/showBlogPostComments.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(1, blogPost.getPostRead());
        Assert.assertEquals(blogPost.getBlogPostId(), action.getBlogPostId());
        Assert.assertEquals(2, action.getWidgetBlogId());
        final List<ShowBlogPostCommentsAction.CommentNode> commentNodes = action.getCommentNodes();
        Assert.assertEquals(comment.getCommentId(), commentNodes.get(2).getCommentSecurity().getComment().getCommentId());
        Assert.assertEquals(oldComment.getCommentId(), commentNodes.get(1).getCommentSecurity().getComment().getCommentId());
        Assert.assertEquals(veryOldComment.getCommentId(), commentNodes.get(0).getCommentSecurity().getComment().getCommentId());
    }

    @Test
    public void executeWithoutLoginWithDraftForOwner() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.OWNER);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setDraftText("F");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.setWidgetBlogId(2);
        action.execute();

        Assert.assertEquals(0, action.getCommentNodes().size());
    }

    @Test
    public void executeWithoutLoginForAll() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.ALL);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setText("F");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.execute();

        Assert.assertEquals(1, action.getCommentNodes().size());
        final ShowBlogPostCommentsAction.CommentNode commentNode = action.getCommentNodes().get(0);

        Assert.assertNull(commentNode.getVisitor());
        Assert.assertEquals(0, commentNode.getChildCommentNodes().size());
        Assert.assertFalse(commentNode.getCommentSecurity().isDraft());
        Assert.assertTrue(commentNode.getCommentSecurity().isEditable());
        Assert.assertFalse(commentNode.getCommentSecurity().isOwner());
    }

    @Test
    public void executeWithoutLoginForOwner() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.OWNER);
        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setText("F");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.execute();

        Assert.assertEquals(1, action.getCommentNodes().size());
        final ShowBlogPostCommentsAction.CommentNode commentNode = action.getCommentNodes().get(0);

        Assert.assertNull(commentNode.getVisitor());
        Assert.assertEquals(0, commentNode.getChildCommentNodes().size());
        Assert.assertFalse(commentNode.getCommentSecurity().isDraft());
        Assert.assertFalse(commentNode.getCommentSecurity().isEditable());
        Assert.assertFalse(commentNode.getCommentSecurity().isOwner());
    }

    @Test
    public void executeWithAuthorForVisitor() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.VISITOR);
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.VISITORS);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setVisitorId(user.getUserId());
        blogPost.addComment(comment);
        comment.setText("F");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.execute();

        Assert.assertEquals(1, action.getCommentNodes().size());
        final ShowBlogPostCommentsAction.CommentNode commentNode = action.getCommentNodes().get(0);

        Assert.assertEquals(user, commentNode.getVisitor());
        Assert.assertEquals(0, commentNode.getChildCommentNodes().size());
        Assert.assertFalse(commentNode.getCommentSecurity().isDraft());
        Assert.assertTrue(commentNode.getCommentSecurity().isEditable());
        Assert.assertFalse(commentNode.getCommentSecurity().isOwner());
    }

    @Test
    public void executeWithAuthorForOwner() throws Exception {
        final Site site = TestUtil.createSite();

        final User user = TestUtil.createUserAndLogin();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.OWNER);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setVisitorId(user.getUserId());
        blogPost.addComment(comment);
        comment.setText("F");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.execute();

        Assert.assertEquals(1, action.getCommentNodes().size());
        final ShowBlogPostCommentsAction.CommentNode commentNode = action.getCommentNodes().get(0);

        Assert.assertEquals(user, commentNode.getVisitor());
        Assert.assertEquals(0, commentNode.getChildCommentNodes().size());
        Assert.assertFalse(commentNode.getCommentSecurity().isDraft());
        Assert.assertTrue(commentNode.getCommentSecurity().isEditable());
        Assert.assertFalse(commentNode.getCommentSecurity().isOwner());
    }

    @Test
    public void executeWithAuthorWithTextAndDraft() throws Exception {
        final Site site = TestUtil.createSite();

        final User user = TestUtil.createUserAndLogin();
        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.OWNER);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        comment.setVisitorId(user.getUserId());
        blogPost.addComment(comment);
        comment.setText("F");
        comment.setDraftText("GG");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.execute();

        Assert.assertEquals(1, action.getCommentNodes().size());
        final ShowBlogPostCommentsAction.CommentNode commentNode = action.getCommentNodes().get(0);

        Assert.assertEquals(user, commentNode.getVisitor());
        Assert.assertEquals(0, commentNode.getChildCommentNodes().size());
        Assert.assertTrue(commentNode.getCommentSecurity().isDraft());
        Assert.assertTrue(commentNode.getCommentSecurity().isEditable());
        Assert.assertFalse(commentNode.getCommentSecurity().isOwner());
    }

    @Test
    public void executeWithoutLoginWithTextAndDraft() throws Exception {
        final Site site = TestUtil.createSite();

        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setEditCommentRight(AccessGroup.OWNER);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        blogPost.addComment(comment);
        comment.setText("F");
        comment.setDraftText("GG");
        persistance.putComment(comment);

        action.setBlogPostId(blogPost.getBlogPostId());
        action.execute();

        Assert.assertEquals(1, action.getCommentNodes().size());
        final ShowBlogPostCommentsAction.CommentNode commentNode = action.getCommentNodes().get(0);

        Assert.assertEquals(0, commentNode.getChildCommentNodes().size());
        Assert.assertFalse(commentNode.getCommentSecurity().isDraft());
        Assert.assertFalse(commentNode.getCommentSecurity().isEditable());
        Assert.assertFalse(commentNode.getCommentSecurity().isOwner());
    }

    private final ShowBlogPostCommentsAction action = new ShowBlogPostCommentsAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
