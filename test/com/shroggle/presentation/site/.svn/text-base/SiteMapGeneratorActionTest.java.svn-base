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
package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SiteMapGeneratorActionTest {

    @Test
    public void testGenerate() throws ParserConfigurationException {
        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        page1.getPageSettings().setUrl("url1");
        final Page page2 = TestUtil.createPage(site);
        page2.getPageSettings().setUrl("url2");

        action.siteId = site.getSiteId();
        final Document siteMap = action.generateMap();

        final Node root = siteMap.getFirstChild();

        Assert.assertEquals("urlset", root.getNodeName());
        Assert.assertEquals(2, root.getChildNodes().getLength());
        Assert.assertEquals("url", root.getChildNodes().item(0).getNodeName());
        Assert.assertEquals("loc", root.getChildNodes().item(0).getFirstChild().getNodeName());
        Assert.assertEquals(new PageManager(page1).getPublicUrl(), root.getChildNodes().item(0).getFirstChild().getTextContent());
        Assert.assertEquals("url", root.getChildNodes().item(1).getNodeName());
        Assert.assertEquals("loc", root.getChildNodes().item(1).getFirstChild().getNodeName());
        Assert.assertEquals(new PageManager(page2).getPublicUrl(), root.getChildNodes().item(1).getFirstChild().getTextContent());
    }

    @Test
    public void testGenerateWithSiteWithoutPages() throws ParserConfigurationException {
        final Site site = TestUtil.createSite();

        action.siteId = site.getSiteId();
        final Document siteMap = action.generateMap();

        final Node root = siteMap.getFirstChild();

        Assert.assertEquals("urlset", root.getNodeName());
        Assert.assertEquals(0, root.getChildNodes().getLength());
    }

    @Test(expected = SiteNotFoundException.class)
    public void testGenerateWithoutSite() throws ParserConfigurationException {
        action.generateMap();
    }

    @Test
    public void testGetInternalPages_normalData() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        final Gallery gallery = TestUtil.createGallery(site);

        galleryWidget.setDraftItem((DraftItem) gallery);

        DraftForm form = TestUtil.createCustomForm(site);
        FilledForm filledForm1 = TestUtil.createFilledForm(form);
        FilledForm filledForm2 = TestUtil.createFilledForm(form);
        FilledForm filledForm3 = TestUtil.createFilledForm(form);
        gallery.setFormId1(form.getId());

        pageManager.addWidget(galleryWidget);

        final List<String> internalPagesUrls = SiteMapGeneratorAction.getInternalPages(pageManager);

        junit.framework.Assert.assertEquals(3, internalPagesUrls.size());
        junit.framework.Assert.assertEquals("?gallerySelectedFilledFormId=" + filledForm1.getFilledFormId() +
                "&selectedGalleryId=" + gallery.getId() + "&SELink=true&User+Email+Address", internalPagesUrls.get(0));
        junit.framework.Assert.assertEquals("?gallerySelectedFilledFormId=" + filledForm2.getFilledFormId() +
                "&selectedGalleryId=" + gallery.getId() + "&SELink=true&User+Email+Address", internalPagesUrls.get(1));
        junit.framework.Assert.assertEquals("?gallerySelectedFilledFormId=" + filledForm3.getFilledFormId() +
                "&selectedGalleryId=" + gallery.getId() + "&SELink=true&User+Email+Address", internalPagesUrls.get(2));
    }

    @Test
    public void testGetInternalPages_withoutGalleryWidget() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);

        final List<String> internalPagesUrls = SiteMapGeneratorAction.getInternalPages(pageManager);

        junit.framework.Assert.assertTrue(internalPagesUrls.isEmpty());
    }

    @Test
    public void testGetInternalPages_withoutForms() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final WidgetItem galleryWidget = TestUtil.createWidgetItem();
        final Gallery gallery = TestUtil.createGallery(site);

        galleryWidget.setDraftItem((DraftItem) gallery);

        pageManager.addWidget(galleryWidget);

        final List<String> internalPagesUrls = SiteMapGeneratorAction.getInternalPages(pageManager);

        junit.framework.Assert.assertTrue(internalPagesUrls.isEmpty());
    }

    private SiteMapGeneratorAction action = new SiteMapGeneratorAction();

}

