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
package com.shroggle.presentation.page;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PageUrlGetterServiceTest {

    @Test
    public void testGetWorkUrl() throws Exception {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());
        final PageManager pageVersionWork = new PageManager(pageVersion.getPage());
        pageVersionWork.getWorkPageSettings().setUrl("workPageVersionUrl");

        pageVersion.getPage().getSite().setSubDomain("temp");
        pageVersion.setUrl("godzila");

        Assert.assertEquals("http://temp.shroggle.com/workPageVersionUrl", new PageUrlGetterService().getWorkUrl(pageVersion.getPage().getPageId()));
    }

    @Test
    public void testGetWorkUrl_withoutWorkPage() throws Exception {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();

        pageVersion.getPage().getSite().setSubDomain("temp");
        pageVersion.setUrl("godzila");

        Assert.assertEquals(null, new PageUrlGetterService().getWorkUrl(null));
    }

    @Test
    public void testGetWorkUrl_withoutPage() throws Exception {
        final PageManager pageVersion = TestUtil.createPageVersionPageAndSite();
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());
        final PageManager pageVersionWork = new PageManager(pageVersion.getPage());
        pageVersionWork.getWorkPageSettings().setUrl("workPageVersionUrl");

        pageVersion.getPage().getSite().setSubDomain("temp");
        pageVersion.setUrl("godzila");

        Assert.assertEquals(null, new PageUrlGetterService().getWorkUrl(null));
    }
}
