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
package com.shroggle.presentation.site.render;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RenderTitleMetaTagTest {

    @Test
    public void testExecute() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setTitleMetaTag("NOINDEX,FOLLOW");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderTitleMetaTag renderTitleMetaTag = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        renderTitleMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"NOINDEX,FOLLOW\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithoutTitle() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setTitleMetaTag(null);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderTitleMetaTag renderTitleMetaTag = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        renderTitleMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void testExecuteWithEmptyTitle() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getSeoSettings().setTitleMetaTag("");

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderTitleMetaTag renderTitleMetaTag = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        renderTitleMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithSEOInGallery() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        final Gallery gallery = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.SEO_KEYWORDS, galleryForm, 0);

        gallery.setFormId1(galleryForm.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.SEO_KEYWORDS);
        filledFormItem.setValue("seo1;seo2;seo3");
        filledForm.addFilledFormItem(filledFormItem);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"seo1;seo2;seo3\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithSEOInGalleryData() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        final Gallery gallery = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        gallery.setDataCrossWidgetId(1);
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        final DraftGalleryData galleryData = new DraftGalleryData();
        ServiceLocator.getPersistance().putItem(galleryData);

        final PageManager pageVersion2 = new PageManager(TestUtil.createPage(site));
        final WidgetItem galleryDataWidget = TestUtil.createWidgetItem();
        galleryDataWidget.setDraftItem(galleryData);
        galleryDataWidget.setCrossWidgetId(1);
        pageVersion2.addWidget(galleryDataWidget);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.SEO_KEYWORDS, galleryForm, 0);

        gallery.setFormId1(galleryForm.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.SEO_KEYWORDS);
        filledFormItem.setValue("seo1;seo2;seo3");
        filledForm.addFilledFormItem(filledFormItem);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion2, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"seo1;seo2;seo3\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithSEOAndNameInGallery() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        final Gallery gallery = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.SEO_KEYWORDS, galleryForm, 0);
        TestUtil.createFormItem(FormItemName.NAME, galleryForm, 1);

        gallery.setFormId1(galleryForm.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.SEO_KEYWORDS);
        filledFormItem.setValue("seo1;seo2;seo3");
        filledForm.addFilledFormItem(filledFormItem);
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.NAME);
        filledFormItem1.setValue("name");
        filledForm.addFilledFormItem(filledFormItem1);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"name\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithNameInGallery() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        final Gallery gallery = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.NAME, galleryForm, 0);

        gallery.setFormId1(galleryForm.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.NAME);
        filledFormItem.setValue("name");
        filledForm.addFilledFormItem(filledFormItem);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"name\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithNameInGalleryAndTitleInPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        pageVersion.getSeoSettings().setTitleMetaTag("title1");
        final Gallery gallery = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.NAME, galleryForm, 0);

        gallery.setFormId1(galleryForm.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.NAME);
        filledFormItem.setValue("name1");
        filledForm.addFilledFormItem(filledFormItem);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"title1. name1\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithSEOInMultipleGalleries() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        final Gallery gallery = TestUtil.createGallery(site);
        final Gallery gallery1 = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        final WidgetItem galleryWidget1 = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        galleryWidget1.setDraftItem((DraftItem) gallery1);
        pageVersion.addWidget(galleryWidget1);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.SEO_KEYWORDS, galleryForm, 0);
        gallery.setFormId1(galleryForm.getFormId());

        final DraftForm galleryForm1 = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.SEO_KEYWORDS, galleryForm1, 0);
        gallery1.setFormId1(galleryForm1.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.SEO_KEYWORDS);
        filledFormItem.setValue("seo1;seo2;seo3");
        filledForm.addFilledFormItem(filledFormItem);

        final FilledForm filledForm1 = TestUtil.createFilledForm(galleryForm1);
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.SEO_KEYWORDS);
        filledFormItem1.setValue("seo4;seo5;seo6");
        filledForm1.addFilledFormItem(filledFormItem1);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery1.getId(), filledForm1.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"seo1;seo2;seo3 seo4;seo5;seo6\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithNameInMultipleGalleries() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        final Gallery gallery = TestUtil.createGallery(site);
        final Gallery gallery1 = TestUtil.createGallery(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        final WidgetItem galleryWidget1 = TestUtil.createWidgetItem();
        galleryWidget.setDraftItem((DraftItem) gallery);
        pageVersion.addWidget(galleryWidget);

        galleryWidget1.setDraftItem((DraftItem) gallery1);
        pageVersion.addWidget(galleryWidget1);

        final DraftForm galleryForm = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.NAME, galleryForm, 0);
        gallery.setFormId1(galleryForm.getFormId());

        final DraftForm galleryForm1 = TestUtil.createCustomForm(site);
        TestUtil.createFormItem(FormItemName.NAME, galleryForm1, 0);
        gallery1.setFormId1(galleryForm1.getFormId());

        final FilledForm filledForm = TestUtil.createFilledForm(galleryForm);
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.NAME);
        filledFormItem.setValue("name1");
        filledForm.addFilledFormItem(filledFormItem);

        final FilledForm filledForm1 = TestUtil.createFilledForm(galleryForm1);
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(FormItemName.NAME);
        filledFormItem1.setValue("name2");
        filledForm1.addFilledFormItem(filledFormItem1);

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery1.getId(), filledForm1.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderTitleMetaTag(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"title\" content=\"name1 name2\"/>\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntializationWithNullPageVersion() {
        new RenderTitleMetaTag(null, SiteShowOption.ON_USER_PAGES);
    }

}
