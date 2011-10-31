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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteCopierFromActivatedBlueprintRealTest {

    @Test
    public void testExecute_withBlogAndBlogSummary() throws Exception {
        final Site blueprint = TestUtil.createBlueprint();
        for (Page page : new ArrayList<Page>(blueprint.getPages())) {
            blueprint.removePage(page);
        }
        TestUtil.createMenu(blueprint);
        persistance.putSite(blueprint);
        blueprint.setThemeId(new ThemeId("a1", "z1"));

        /*----------------------------------------------------Blog----------------------------------------------------*/
        final Page pageWithBlog = TestUtil.createPage(blueprint);

        final DraftBlog draftBlog = TestUtil.createBlog(blueprint, "draftBlog");

        final BlogPost blogPost = TestUtil.createBlogPost(draftBlog, "Blog post`s text", "Blog post`s title");

        final Comment comment = TestUtil.createComment(blogPost, "Comment`s text");
        final Comment commentOnComment = TestUtil.createComment("Child comment`s text");
        comment.addChildComment(commentOnComment);

        final WidgetItem widgetBlog = TestUtil.createWidgetBlog(draftBlog.getId());
        widgetBlog.setCrossWidgetId(12345);

        final PageManager pageManagerWithBlog = new PageManager(pageWithBlog);
        pageManagerWithBlog.setName("pageWithBlog");
        pageManagerWithBlog.addWidget(widgetBlog);
        pageManagerWithBlog.publish();
        /*----------------------------------------------------Blog----------------------------------------------------*/

        /*------------------------------------------------Blog Summary------------------------------------------------*/
        final Page pageWithBlogSummary = TestUtil.createPage(blueprint);
        final DraftBlogSummary draftBlogSummary = TestUtil.createBlogSummary(blueprint, "draftBlogSummary");
        draftBlogSummary.setIncludedCrossWidgetId(Arrays.asList(widgetBlog.getCrossWidgetId()));

        final WidgetItem widgetBlogSummary = TestUtil.createWidgetItem();
        widgetBlogSummary.setDraftItem(draftBlogSummary);

        final PageManager pageManagerWithBlogSummary = new PageManager(pageWithBlogSummary);
        pageManagerWithBlogSummary.setName("pageWithBlogSummary");
        pageManagerWithBlogSummary.addWidget(widgetBlogSummary);
        pageManagerWithBlogSummary.publish();
        /*------------------------------------------------Blog Summary------------------------------------------------*/

        final Site site = TestUtil.createSite();
        for (Page page : new ArrayList<Page>(site.getPages())) {
            site.removePage(page);
        }
        persistance.putSite(site);
        final SiteCopierFromActivatedBlueprintReal copier = new SiteCopierFromActivatedBlueprintReal();
        copier.execute(blueprint, site, false);

        Assert.assertEquals("We must copy theme id!", blueprint.getThemeId(), site.getThemeId());
        Assert.assertEquals(2, site.getPages().size());
        /*---------------------------------------Checking first page with blog----------------------------------------*/
        final PageManager copiedPageWithBlog = new PageManager(site.getPages().get(0));
        Assert.assertEquals("pageWithBlog", copiedPageWithBlog.getName());

        Assert.assertEquals(1, copiedPageWithBlog.getWidgets().size());
        final WidgetItem copiedWidgetWithBlog = (WidgetItem) copiedPageWithBlog.getWidgets().get(0);
        Assert.assertEquals(widgetBlog.getCrossWidgetId(), copiedWidgetWithBlog.getParentCrossWidgetId().intValue());
        Assert.assertNotSame(widgetBlog.getCrossWidgetId(), copiedWidgetWithBlog.getCrossWidgetId());
        final DraftBlog copiedBlog = (DraftBlog) copiedWidgetWithBlog.getDraftItem();
        Assert.assertEquals("draftBlog", copiedBlog.getName());
        Assert.assertEquals(1, copiedBlog.getBlogPosts().size());
        final BlogPost copiedBlogPost = copiedBlog.getBlogPosts().get(0);
        Assert.assertEquals(copiedBlog, copiedBlogPost.getBlog());
        Assert.assertEquals("Blog post`s text", copiedBlogPost.getText());
        Assert.assertEquals("Blog post`s title", copiedBlogPost.getPostTitle());
        Assert.assertNotSame(blogPost.getBlogPostId(), copiedBlogPost.getBlogPostId());

        Assert.assertEquals(1, copiedBlogPost.getComments().size());
        final Comment copiedComment = copiedBlogPost.getComments().get(0);
        Assert.assertEquals("Comment`s text", copiedComment.getText());
        Assert.assertNotSame(comment.getCommentId(), copiedComment.getCommentId());

        Assert.assertEquals(1, copiedComment.getAnswerComments().size());
        final Comment copiedCommentOnComment = copiedComment.getAnswerComments().get(0);
        Assert.assertEquals("Child comment`s text", copiedCommentOnComment.getText());
        Assert.assertNotSame(commentOnComment.getCommentId(), copiedCommentOnComment.getCommentId());

        /*---------------------------------------Checking first page with blog----------------------------------------*/

        /*-----------------------------------Checking second page with blogSummary------------------------------------*/
        final PageManager copiedPageWithBlogSummary = new PageManager(site.getPages().get(1));
        Assert.assertEquals("pageWithBlogSummary", copiedPageWithBlogSummary.getName());

        Assert.assertEquals(1, copiedPageWithBlogSummary.getWidgets().size());
        final WidgetItem copiedWidgetWithBlogSummary = (WidgetItem) copiedPageWithBlogSummary.getWidgets().get(0);
        Assert.assertEquals(widgetBlogSummary.getCrossWidgetId(), copiedWidgetWithBlogSummary.getParentCrossWidgetId().intValue());
        final DraftBlogSummary copiedBlogSummary = (DraftBlogSummary) copiedWidgetWithBlogSummary.getDraftItem();
        Assert.assertEquals("draftBlogSummary", copiedBlogSummary.getName());
        Assert.assertEquals(1, copiedBlogSummary.getIncludedCrossWidgetId().size());
        Assert.assertEquals(copiedWidgetWithBlog.getCrossWidgetId(), copiedBlogSummary.getIncludedCrossWidgetId().get(0).intValue());
        /*-----------------------------------Checking second page with blogSummary------------------------------------*/
    }

    @Test
    public void testExecute_withContactUs() throws Exception {
        TestUtil.createUserAndLogin("newEmail");
        final Site blueprint = TestUtil.createBlueprint();
        for (Page page : new ArrayList<Page>(blueprint.getPages())) {
            blueprint.removePage(page);
        }
        TestUtil.createMenu(blueprint);
        persistance.putSite(blueprint);

        /*-------------------------------------------------ContactUs--------------------------------------------------*/
        final Page pageWithContactUs = TestUtil.createPage(blueprint);

        final DraftContactUs draftContactUs = TestUtil.createContactUs(blueprint);
        draftContactUs.setName("draftContactUs");
        draftContactUs.setEmail("oldEmail");


        final WidgetItem widgetContactUs = TestUtil.createWidgetItem();
        widgetContactUs.setDraftItem(draftContactUs);
        widgetContactUs.setCrossWidgetId(12345);

        final PageManager pageManagerWithContactUs = new PageManager(pageWithContactUs);
        pageManagerWithContactUs.setName("pageWithContactUs");
        pageManagerWithContactUs.addWidget(widgetContactUs);
        pageManagerWithContactUs.publish();
        /*-------------------------------------------------ContactUs--------------------------------------------------*/


        final Site site = TestUtil.createSite();
        for (Page page : new ArrayList<Page>(site.getPages())) {
            site.removePage(page);
        }
        persistance.putSite(site);
        final SiteCopierFromActivatedBlueprintReal copier = new SiteCopierFromActivatedBlueprintReal();
        copier.execute(blueprint, site, false);

        Assert.assertEquals(1, site.getPages().size());
        final PageManager copiedPageWithContactUs = new PageManager(site.getPages().get(0));
        Assert.assertEquals("pageWithContactUs", copiedPageWithContactUs.getName());


        Assert.assertEquals(1, copiedPageWithContactUs.getWidgets().size());
        final WidgetItem copiedWidgetWithContactUs = (WidgetItem) copiedPageWithContactUs.getWidgets().get(0);
        Assert.assertEquals(widgetContactUs.getCrossWidgetId(), copiedWidgetWithContactUs.getParentCrossWidgetId().intValue());
        Assert.assertNotSame(widgetContactUs.getCrossWidgetId(), copiedWidgetWithContactUs.getCrossWidgetId());
        final DraftContactUs copiedContactUs = (DraftContactUs) copiedWidgetWithContactUs.getDraftItem();
        Assert.assertEquals("draftContactUs", copiedContactUs.getName());
        Assert.assertEquals("newEmail", copiedContactUs.getEmail());
    }

    @Test
    public void testExecute_withGalleryWithEcommerce() throws Exception {
        TestUtil.createUserAndLogin("newEmail");
        final Site blueprint = TestUtil.createBlueprint();
        for (Page page : new ArrayList<Page>(blueprint.getPages())) {
            blueprint.removePage(page);
        }
        TestUtil.createMenu(blueprint);
        persistance.putSite(blueprint);

        /*--------------------------------------------------Gallery---------------------------------------------------*/
        final Page pageWithGallery = TestUtil.createPage(blueprint);

        final DraftGallery draftGallery = TestUtil.createGallery(blueprint);
        draftGallery.setName("draftGallery");
        draftGallery.getPaypalSettings().setPaypalEmail("oldEmail");
        draftGallery.getPaypalSettings().setEnable(true);
        draftGallery.setFormId1(TestUtil.createContactUsForm().getId());


        final WidgetItem widgetGallery = TestUtil.createWidgetItem();
        widgetGallery.setDraftItem(draftGallery);
        widgetGallery.setCrossWidgetId(12345);

        final PageManager pageManagerWithGallery = new PageManager(pageWithGallery);
        pageManagerWithGallery.setName("pageWithGallery");
        pageManagerWithGallery.addWidget(widgetGallery);
        pageManagerWithGallery.publish();
        /*--------------------------------------------------Gallery---------------------------------------------------*/


        final Site site = TestUtil.createSite();
        for (Page page : new ArrayList<Page>(site.getPages())) {
            site.removePage(page);
        }
        persistance.putSite(site);
        final SiteCopierFromActivatedBlueprintReal copier = new SiteCopierFromActivatedBlueprintReal();
        copier.execute(blueprint, site, false);

        Assert.assertEquals(1, site.getPages().size());
        final PageManager copiedPageWithContactUs = new PageManager(site.getPages().get(0));
        Assert.assertEquals("pageWithGallery", copiedPageWithContactUs.getName());


        Assert.assertEquals(1, copiedPageWithContactUs.getWidgets().size());
        final WidgetItem copiedWidgetWithContactUs = (WidgetItem) copiedPageWithContactUs.getWidgets().get(0);
        Assert.assertEquals(widgetGallery.getCrossWidgetId(), copiedWidgetWithContactUs.getParentCrossWidgetId().intValue());
        Assert.assertNotSame(widgetGallery.getCrossWidgetId(), copiedWidgetWithContactUs.getCrossWidgetId());
        final DraftGallery copiedGallery = (DraftGallery) copiedWidgetWithContactUs.getDraftItem();
        Assert.assertEquals("draftGallery", copiedGallery.getName());
        Assert.assertEquals("newEmail", copiedGallery.getPaypalSettings().getPaypalEmail());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
