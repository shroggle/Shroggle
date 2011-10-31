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
package com.shroggle.logic.site.item;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemCopierSimpleTest {

    @Test
    public void executeText() {
        final Site site = TestUtil.createSite();
        final DraftText draft = new DraftText();
        draft.setText("fff");

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftText copiedDraftText = (DraftText) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraftText);
        Assert.assertEquals("fff", copiedDraftText.getText());
    }

    @Test
    public void executeAdminLogin() {
        final Site site = TestUtil.createSite();
        final DraftAdminLogin draft = new DraftAdminLogin();
        draft.setText("fff");

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftAdminLogin copiedDraft = (DraftAdminLogin) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertEquals("fff", copiedDraft.getText());
    }

    @Test
    public void executeTellFriend() {
        final Site site = TestUtil.createSite();
        final DraftTellFriend draft = new DraftTellFriend();
        draft.setMailSubject("fff");
        draft.setMailText("aa");

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftTellFriend copiedDraft = (DraftTellFriend) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertEquals("fff", copiedDraft.getMailSubject());
        Assert.assertEquals("aa", copiedDraft.getMailText());
    }

    @Test
    public void executeScript() {
        final Site site = TestUtil.createSite();
        final DraftScript draft = new DraftScript();
        draft.setText("fff");

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftScript copiedDraft = (DraftScript) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertEquals("fff", copiedDraft.getText());
    }

    @Test
    public void executeShoppingCart() {
        final Site site = TestUtil.createSite();
        final DraftShoppingCart draft = new DraftShoppingCart();
        draft.setImageHeight(1);
        draft.setDescriptionAfterName(true);
        draft.setImageInFirstColumn(false);
        draft.setImageWidth(12);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftShoppingCart copiedDraft = (DraftShoppingCart) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertTrue(copiedDraft.isDescriptionAfterName());
        Assert.assertFalse(copiedDraft.isImageInFirstColumn());
        Assert.assertEquals(1, copiedDraft.getImageHeight());
        Assert.assertEquals(12, copiedDraft.getImageWidth());
    }

    @Test
    public void executeBlog() {
        final Site site = TestUtil.createSite();
        final DraftBlog draft = new DraftBlog();
        final BlogPost blogPost = new BlogPost();
        blogPost.setDraftText("vvv");
        blogPost.setPostTitle("zzz");
        blogPost.setText("nn");
        blogPost.setVisitorId(12);
        draft.addBlogPost(blogPost);
        final Comment comment = new Comment();
        comment.setDraftText("111");
        comment.setText("33");
        blogPost.addComment(comment);
        final Comment subComment = new Comment();
        blogPost.addComment(subComment);
        subComment.setDraftText("1f");
        subComment.setText("bbb");
        comment.addChildComment(subComment);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftBlog copiedDraft = (DraftBlog) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertEquals(1, copiedDraft.getBlogPosts().size());
    }

    @Test
    public void executeForum() {
        final Site site = TestUtil.createSite();
        final DraftForum draft = new DraftForum();
        final SubForum subForum = new SubForum();
        draft.addSubForum(subForum);
        final ForumThread forumThread = new ForumThread();
        forumThread.setAuthor(new User());
        subForum.addForumThread(forumThread);
        final ForumPost forumPost = new ForumPost();
        forumPost.setAuthor(new User());
        forumThread.addForumPost(forumPost);
        final ForumPollVote forumPollVote = new ForumPollVote();
        forumThread.addPollVote(forumPollVote);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftForum copiedDraft = (DraftForum) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertEquals(0, copiedDraft.getSubForums().size());
    }

    @Test
    public void executeImage() {
        final Site site = TestUtil.createSite();
        final DraftForum draft = new DraftForum();
        final SubForum subForum = new SubForum();
        draft.addSubForum(subForum);
        final ForumThread forumThread = new ForumThread();
        forumThread.setAuthor(new User());
        subForum.addForumThread(forumThread);
        final ForumPost forumPost = new ForumPost();
        forumPost.setAuthor(new User());
        forumThread.addForumPost(forumPost);
        final ForumPollVote forumPollVote = new ForumPollVote();
        forumThread.addPollVote(forumPollVote);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());
        final ItemCopyResult itemCopyResult = new ItemCopierSimple().execute(context, draft, null);

        Assert.assertTrue(itemCopyResult.isCopied());
        final DraftForum copiedDraft = (DraftForum) itemCopyResult.getDraftItem();
        Assert.assertNotNull(copiedDraft);
        Assert.assertEquals(0, copiedDraft.getSubForums().size());
    }

}