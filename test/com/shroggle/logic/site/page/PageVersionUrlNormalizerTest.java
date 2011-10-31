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

package com.shroggle.logic.site.page;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.pageversion.PageVersionUrlNormalizer;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PageVersionUrlNormalizerTest {

    @Test
    public void executeWithSetUrl() {
        Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createWorkPage(site);
        PageManager pageVersion = new PageManager(page, SiteShowOption.getWorkOption());
        pageVersion.getWorkPageSettings().setName("test 3 в %$ <");
        pageVersion.getWorkPageSettings().setUrl("test1113");

        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageVersion.getWorkPageSettings());

        Assert.assertEquals("/test1113", pageVersion.getUrl());
    }

    @Test
    public void executeWithSameUrlMy() {
        Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createWorkPage(site);
        PageManager pageVersion = new PageManager(page, SiteShowOption.getWorkOption());
        pageVersion.getWorkPageSettings().setName("test 3 в %$ <");
        pageVersion.getWorkPageSettings().setUrl("test3");

        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageVersion.getWorkPageSettings());

        Assert.assertEquals("/test3", pageVersion.getUrl());
    }

    @Test
    public void executeWithSameUrlInOlderVersion() {
        Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createWorkPage(site);
        PageManager pageVersion = new PageManager(page, SiteShowOption.getWorkOption());
        pageVersion.getWorkPageSettings().setUrl("test3");
        pageVersion.getWorkPageSettings().setName("test 3 в %$ <");

        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageVersion.getWorkPageSettings());

        Assert.assertEquals("/test3", pageVersion.getUrl());
    }

    @Test
    public void executeWithNullUrl() {
        Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createWorkPage(site);
        PageManager pageVersion = new PageManager(page, SiteShowOption.getWorkOption());
        pageVersion.getWorkPageSettings().setName("test 3 в %$ <");
        

        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageVersion.getWorkPageSettings());

        Assert.assertEquals("/test3", pageVersion.getUrl());
    }

    @Test
    public void executeWithEmptyUrl() {
        Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        Page page = TestUtil.createWorkPage(site);
        PageManager pageVersion = new PageManager(page, SiteShowOption.getWorkOption());
        pageVersion.getWorkPageSettings().setName("test 3 в %$ <");
        pageVersion.getWorkPageSettings().setUrl("    ");
        

        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageVersion.getWorkPageSettings());

        Assert.assertEquals("/test3", pageVersion.getUrl());
    }

    @Ignore
    @Test
    public void executeWithAlredyExist() {
        Site site = new Site();
        ServiceLocator.getPersistance().putSite(site);

        Page page1 = TestUtil.createPage(site);

        Page page2 = TestUtil.createPage(site);

        PageManager pageVersion1 = new PageManager(page1);
        pageVersion1.setUrl("ff");

        PageManager pageVersion2 = new PageManager(page2);
        pageVersion2.setName("test 3 в %$ <");
        pageVersion2.setUrl("ff");

        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageVersion2.getWorkPageSettings());

        Assert.assertEquals("ff1", pageVersion2.getUrl());
    }

    @Test(expected = NullPointerException.class)
    public void executeByNullPage() {
        PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(null);
    }

}
