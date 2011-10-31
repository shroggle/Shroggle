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
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Test;

public class HibernateCssParameterValuePersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void putWidget() {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setSubDomain("a");
        site.setTitle("f");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        Widget widget = new WidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);
        
    }

    @Test
    public void putPageVersion() {
        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("a");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

//        PageVersionCssParameterValue cssParameterValue = (PageVersionCssParameterValue) pageVersion.addCssParameterValue();
//        cssParameterValue.getId().setName("a");
//        cssParameterValue.setDescription("desc");
//        cssParameterValue.setSelector(CssParameter.NONE_SELECTOR);
//        persistance.putCssParameterValue(cssParameterValue);
//
//        PageVersionCssParameterValue findCssParameterValue = HibernateManager.get().find(
//                PageVersionCssParameterValue.class, cssParameterValue.getId());
//        Assert.assertNotNull(findCssParameterValue);
//        Assert.assertEquals("a", findCssParameterValue.getId().getName());

        //todo fix method
        //persistance.removeCssParameterValue(cssParameterValue);
    }

}