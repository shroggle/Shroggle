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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class CheckSiteAliasServiceTest {

    @Test
    public void execute() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("127.0.0.1", null));
    }

    @Test
    public void executeWithSiteId() {
        Site site = TestUtil.createSite();
        site.setCustomUrl("127.0.0.1");

        Assert.assertNull(service.execute("127.0.0.1", site.getSiteId()));
    }

    @Test
    public void executeForNotFoundSite() {
        TestUtil.createUser();

        Assert.assertNull(service.execute("127.0.0.1", -1));
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
        site.setCustomUrl("aaa.com");
        persistance.putSite(site);

        Assert.assertNotNull(service.execute("aaa.com", null));
    }

    @Test
    public void executeWithOddDomain() {
        Assert.assertNotNull(service.execute("makedirff58945re.net", null));
    }

    @Test
    public void executeWithSpace() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNotNull(service.execute("a a", null));
    }

    @Test
    public void executeWrongDNS() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNotNull(service.execute("www.google.com", null));
    }

    @Test
    public void executeWrongDNS_withDisabledCheckInConfig() {
        ServiceLocator.getConfigStorage().get().setCheckDomainsIp(false);
        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("www.google.com", null));
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

        Assert.assertNull(service.execute("127.0.0.1", null));
    }

    private final CheckSiteAliasService service = new CheckSiteAliasService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}