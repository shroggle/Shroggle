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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteUrlGetterTest {

    @Before
    public void before() {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com");
    }

    @Test
    public void getForNull() {
        Assert.assertEquals("", siteUrlGetter.get(null));

    }

    @Test
    public void getForSubDomain() {
        final Site site = new Site();
        site.setSubDomain("fff");

        Assert.assertEquals("http://fff.web-deva.com", siteUrlGetter.get(site));
    }

    @Test
    public void getForCustomUrl() {
        final Site site = new Site();
        site.setCustomUrl("ggg");

        Assert.assertEquals("http://ggg", siteUrlGetter.get(site));
    }

    @Test
    public void getForBrandedSubDomain() {
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(draftChildSiteRegistration);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setBrandedUrl("xxx.com");
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);

        final Site site = new Site();
        site.setChildSiteSettings(childSiteSettings);
        site.setBrandedSubDomain("ggg");

        Assert.assertEquals("http://ggg.xxx.com", siteUrlGetter.get(site));
    }

    @Test
    public void getForBrandedSubDomainAndCustomUrl() {
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(draftChildSiteRegistration);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setBrandedUrl("xxx.com");
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);

        final Site site = new Site();
        site.setChildSiteSettings(childSiteSettings);
        site.setCustomUrl("lol.com");
        site.setBrandedSubDomain("ggg");

        Assert.assertEquals("http://lol.com", siteUrlGetter.get(site));
    }

    @Test
    public void getForBrandedSubDomainAndSubDomain() {
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(draftChildSiteRegistration);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setBrandedUrl("xxx.com");
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);

        final Site site = new Site();
        site.setChildSiteSettings(childSiteSettings);
        site.setSubDomain("ararat");
        site.setBrandedSubDomain("ggg");

        Assert.assertEquals("http://ggg.xxx.com", siteUrlGetter.get(site));
    }

    @Test
    public void getForBrandedSubDomainWithoutWorkAndSubDomain() {
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(draftChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);

        final Site site = new Site();
        site.setChildSiteSettings(childSiteSettings);
        site.setSubDomain("ararat");
        site.setBrandedSubDomain("ggg");

        Assert.assertEquals("http://ararat.web-deva.com", siteUrlGetter.get(site));
    }

    @Test
    public void getForBrandedSubDomainWithoutSettingsAndSubDomain() {
        final Site site = new Site();
        site.setSubDomain("ararat");
        site.setBrandedSubDomain("ggg");

        Assert.assertEquals("http://ararat.web-deva.com", siteUrlGetter.get(site));
    }

    @Test
    public void getForBlueprint() {
        final Site site = new Site();
        site.setType(SiteType.BLUEPRINT);
        site.setSubDomain("ararat");

        Assert.assertEquals("http://&lt;site-domain&gt;.web-deva.com", siteUrlGetter.get(site));
    }

    private final SiteUrlGetter siteUrlGetter = new SiteUrlGetter();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
