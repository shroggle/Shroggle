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
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.site.SiteByUrlGetterMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class CheckSiteUrlPrefixServiceTest {

    @Test
    public void execute() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("a", null));
    }

    @Test
    public void executeWithSiteIdForThisPrefix() {
        Site site = TestUtil.createSite();
        site.setSubDomain("f");

        Assert.assertNull(service.execute("f", site.getSiteId()));
    }

    @Test
    public void executeWithSiteId() {
        Site site = TestUtil.createSite();
        site.setSubDomain("ffe");

        Assert.assertNull(service.execute("f", site.getSiteId()));
    }

    @Test
    public void executeForNotFoundSite() {
        TestUtil.createUser();

        Assert.assertNull(service.execute("f", -1));
    }

    @Test
    public void executeWithEmpty() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNotNull(service.execute("", null));
    }

    @Test
    public void executeWithNotUnique() {
        User account = new User();
        persistance.putUser(account);

        Site site = new Site();
        site.setSubDomain("a");
        persistance.putSite(site);
        siteByUrlGetterMock.setSite(site);

        Assert.assertNotNull(service.execute("a", null));
    }

    @Test
    public void executeWithSpace() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNotNull(service.execute("a a", null));
    }

    @Test
    public void executeWithSpecial() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNotNull(service.execute("|", null));
    }

    @Test
    public void executeWithoutLogin() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("a", null));
    }

    private final CheckSiteUrlPrefixService service = new CheckSiteUrlPrefixService();
    private final SiteByUrlGetterMock siteByUrlGetterMock = (SiteByUrlGetterMock) ServiceLocator.getSiteByUrlGetter();
    private final Persistance persistance = ServiceLocator.getPersistance();

}