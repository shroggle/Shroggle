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
package com.shroggle.presentation.site.page;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class DeleteDefaultPageServiceTest {
    
    @Test
    public void execute(){
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        page.setSaved(false);

        service.execute(page.getPageId());

        Assert.assertNull(ServiceLocator.getPersistance().getPage(page.getPageId()));
    }

    @Test
    public void executeForSavedPage(){
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        page.setSaved(true);

        service.execute(page.getPageId());

        Assert.assertNotNull(ServiceLocator.getPersistance().getPage(page.getPageId()));
    }

    @Test
    public void executeWithoutPage(){
        service.execute(-1); // Checking that service will not throw any exception.
    }

    final DeleteDefaultPageService service = new DeleteDefaultPageService();

}
