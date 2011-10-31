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
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderKeywordsMetaTagsTest {

    @Test
    public void executeWithoutKeywords() throws IOException, ServletException {
        render.execute(null, stringBuilder);

        Assert.assertEquals("", stringBuilder.toString());
    }

    @Test
    public void executeWithSite() throws IOException, ServletException {
        final KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup();
        keywordsGroup.setName("g");
        keywordsGroup.setValue("a,a1");

        pageVersion.addKeywordsGroup(keywordsGroup);

        stringBuilder.append("<!-- PAGE_HEADER -->");
        render.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"a,a1\">\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithSiteAndPage() throws IOException, ServletException {
        final KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup();
        keywordsGroup.setName("g");
        keywordsGroup.setValue("a,a1");

        pageVersion.addKeywordsGroup(keywordsGroup);

        pageVersion.setKeywords("mega,mamba");

        stringBuilder.append("<!-- PAGE_HEADER -->");
        render.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"mega,mamba,a,a1\">\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithPage() throws IOException, ServletException {
        pageVersion.setKeywords("g,t");

        stringBuilder.append("<!-- PAGE_HEADER -->");
        render.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"g,t\">\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithoutPageHeader() throws IOException, ServletException {
        final KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup();
        keywordsGroup.setName("g");
        keywordsGroup.setValue("a,a1");

        pageVersion.addKeywordsGroup(keywordsGroup);

        render.execute(null, stringBuilder);

        Assert.assertEquals("", stringBuilder.toString());
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

        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderKeywordsMetaTags(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"seo1, seo2, seo3\">\n" +
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

        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderKeywordsMetaTags(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"seo1, seo2, seo3\">\n" +
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

        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderKeywordsMetaTags(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"name\">\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    @Test
    public void executeWithSEOInGalleryAndKeywordsInPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createPage(site));
        pageVersion.setKeywords("g,t");
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

        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderKeywordsMetaTags(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"g,t\">\n" +
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

        stringBuilder.append("<!-- PAGE_HEADER -->");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest("", "");
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery.getId(), filledForm.getFilledFormId());
        mockRequest.setAttribute("currentDisplayedFilledFormId" + gallery1.getId(), filledForm1.getFilledFormId());
        RenderContext context = new RenderContext(
                mockRequest, null, null, null, false);

        final Render render = new RenderKeywordsMetaTags(pageVersion, SiteShowOption.ON_USER_PAGES);
        render.execute(context, stringBuilder);

        Assert.assertEquals("<meta name=\"keywords\" content=\"seo1, seo2, seo3 seo4, seo5, seo6\">\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

    private final StringBuilder stringBuilder = new StringBuilder();
    private final PageManager pageVersion = new PageManager(TestUtil.createPage(TestUtil.createSite()));
    private final Render render = new RenderKeywordsMetaTags(pageVersion, SiteShowOption.ON_USER_PAGES);
}
