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
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackSimple;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

@RunWith(TestRunnerWithMockServices.class)
public class CopierStackExecutorFullTest {

    @Test
    public void copyBlog() {
        final DraftBlog blog = new DraftBlog();
        blog.setName("aa");
        persistance.putItem(blog);

        final BlogPost blogPost = new BlogPost();
        blog.addBlogPost(blogPost);
        persistance.putBlogPost(blogPost);

        final Comment comment = new Comment();
        blogPost.addComment(comment);
        persistance.putComment(comment);

        final Comment answerComment = new Comment();
        blogPost.addComment(answerComment);
        comment.addChildComment(answerComment);
        persistance.putComment(answerComment);

        final CopierStack stack = new CopierStackSimple(new CopierStackExecutorItemFull(
                new ItemNamingSameAsInOriginal(), -1, Collections.<Integer, Integer>emptyMap()));
        final DraftBlog copiedBlog = persistance.getDraftItem(stack.copy(blog).getId());

        Assert.assertNotNull(copiedBlog);
        Assert.assertEquals(1, copiedBlog.getBlogPosts().size());
        Assert.assertEquals(2, copiedBlog.getBlogPosts().get(0).getComments().size());
    }

    @Test
    public void copyForum() {
        final DraftForum forum = new DraftForum();
        forum.setName("aa");
        persistance.putItem(forum);

        final SubForum subForum = new SubForum();
        forum.addSubForum(subForum);
        persistance.putSubForum(subForum);

        final ForumThread forumThread = new ForumThread();
        subForum.addForumThread(forumThread);
        persistance.putForumThread(forumThread);

        final ForumPost forumPost = new ForumPost();
        forumThread.addForumPost(forumPost);
        persistance.putForumPost(forumPost);

        final CopierStack stack = new CopierStackSimple(new CopierStackExecutorItemFull(
                new ItemNamingSameAsInOriginal(), -1, Collections.<Integer, Integer>emptyMap()));
        final DraftForum copiedForum = persistance.getDraftItem(stack.copy(forum).getId());

        Assert.assertNotNull(copiedForum);
        Assert.assertEquals(1, copiedForum.getSubForums().size());
        Assert.assertEquals(1, copiedForum.getSubForums().get(0).getForumThreads().size());
        Assert.assertEquals(1, copiedForum.getSubForums().get(0).getForumThreads().get(0).getForumPosts().size());
    }

    @Test
    public void copyForm() {
        final DraftCustomForm form = new DraftCustomForm();
        form.setName("aa");
        persistance.putItem(form);

        final DraftFormItem formItem = new DraftFormItem();
        form.addFormItem(formItem);
        persistance.putFormItem(formItem);

        final FilledForm filledForm = new FilledForm();
        filledForm.setFormId(form.getId());
        persistance.putFilledForm(filledForm);

        final FilledFormItem filledFormItem = new FilledFormItem();
        filledFormItem.setFormItemId(formItem.getFormItemId());
        filledForm.addFilledFormItem(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);

        final CopierStack stack = new CopierStackSimple(new CopierStackExecutorItemFull(
                new ItemNamingSameAsInOriginal(), -1, Collections.<Integer, Integer>emptyMap()));
        final DraftCustomForm copiedForm = persistance.getDraftItem(stack.copy(form).getId());

        Assert.assertNotNull(copiedForm);
        Assert.assertEquals(1, copiedForm.getFormItems().size());
        final List<FilledForm> copiedFilledForms = persistance.getFilledFormsByFormId(copiedForm.getId());
        Assert.assertEquals(1, copiedFilledForms.size());
        Assert.assertEquals(1, copiedFilledForms.get(0).getFilledFormItems().size());
    }

    @Test
    public void copyGallery() {
        final DraftCustomForm form = new DraftCustomForm();
        form.setName("aa");
        persistance.putItem(form);

        final DraftFormItem formItem = new DraftFormItem();
        form.addFormItem(formItem);
        persistance.putFormItem(formItem);

        final FilledForm filledForm = new FilledForm();
        filledForm.setFormId(form.getId());
        persistance.putFilledForm(filledForm);

        final FilledFormItem filledFormItem = new FilledFormItem();
        filledFormItem.setFormItemId(formItem.getFormItemId());
        filledForm.addFilledFormItem(filledFormItem);
        persistance.putFilledFormItem(filledFormItem);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getId());
        persistance.putItem(gallery);

        final DraftGalleryItem galleryItem = new DraftGalleryItem();
        galleryItem.getId().setFormItemId(formItem.getFormItemId());
        gallery.addItem(galleryItem);
        persistance.putGalleryItem(galleryItem);

        final CopierStack stack = new CopierStackSimple(new CopierStackExecutorItemFull(
                new ItemNamingSameAsInOriginal(), -1, Collections.<Integer, Integer>emptyMap()));
        final DraftGallery copiedGallery = persistance.getDraftItem(stack.copy(gallery).getId());

        Assert.assertNotNull(copiedGallery);
        Assert.assertEquals(1, copiedGallery.getItems().size());
        final DraftCustomForm copiedForm = persistance.getCustomFormById(copiedGallery.getFormId1());
        Assert.assertNotSame(form, copiedForm);
        Assert.assertEquals(1, copiedForm.getFormItems().size());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}