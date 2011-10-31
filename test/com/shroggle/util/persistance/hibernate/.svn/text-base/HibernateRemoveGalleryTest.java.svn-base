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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;

/**
 * @author Artem Stasuk
 */
public class HibernateRemoveGalleryTest extends HibernateComplexTestBase {

    public void init() {
        final DraftGallery gallery1 = new DraftGallery();
        gallery1.setName("a1@a");
        persistance.putItem(gallery1);

        final DraftGallery gallery2 = new DraftGallery();
        gallery2.setName("a2@a");
        persistance.putItem(gallery2);

        galleryId = gallery1.getId();

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        site.setSubDomain("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);
        page.setType(PageType.BLANK);

        PageManager pageVersion = new PageManager(page);

        WidgetItem widgetGallery = TestUtil.createWidgetItem();
        widgetGallery.setDraftItem(gallery1);
        persistance.putWidget(widgetGallery);
        pageVersion.addWidget(widgetGallery);
        
        widgetId = widgetGallery.getWidgetId();
    }

    public void make() {
        persistance.removeDraftItem(
                persistance.getDraftItem(
                        galleryId));
    }

    public void test() {
        Assert.assertNull(persistance.getDraftItem(
                galleryId));
        final WidgetItem widgetGallery = (WidgetItem) persistance.getWidget(widgetId);
        Assert.assertNull(widgetGallery.getDraftItem());
    }

    private int galleryId;
    private int widgetId;

}