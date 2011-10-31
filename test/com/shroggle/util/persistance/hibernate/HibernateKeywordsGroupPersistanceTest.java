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
import com.shroggle.entity.KeywordsGroup;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Stasuk Artem
 */
public class HibernateKeywordsGroupPersistanceTest extends HibernatePersistanceTestBase {

    @Before
    public void before() {
        super.before();

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("f");
        site.setSubDomain("aa");
        site.getThemeId().setTemplateDirectory("f");
        site.getThemeId().setThemeCss("f");
        persistance.putSite(site);

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        Assert.assertEquals(keywordsGroup.getKeywordsGroupId(), persistance.getKeywordsGroupById(keywordsGroup.getKeywordsGroupId()).getKeywordsGroupId());

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");
    }

    @Test
    public void removeKeywordsGroup() {
        Site site = persistance.getSite(1);
        KeywordsGroup keywordsGroup = site.getKeywordsGroups().get(0);
        persistance.removeKeywordsGroup(keywordsGroup);
        site.removeKeywordsGroup(keywordsGroup);
    }

}
