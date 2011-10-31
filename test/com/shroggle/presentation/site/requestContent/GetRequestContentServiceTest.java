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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class GetRequestContentServiceTest {

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();

        service.execute(site.getSiteId());
    }

    @Test
    public void executeForEmpty() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final GetRequestContentResponse response = service.execute(site.getSiteId());
        Assert.assertNotNull(response.getItems());
        Assert.assertEquals(0, response.getItems().size());
    }

    // This test also check's availability of different content modules for sharing.

    private static boolean containsItemType(List<RequestContentItem> requestItemList, ItemType itemType) {
        for (RequestContentItem requestContentItem : requestItemList) {
            if (requestContentItem.getType() == itemType) {
                return true;
            }
        }

        return false;
    }

    @Test
    public void executeForCustomForm() {
        configStorage.get().setUserSitesUrl("g");

        final Site site = TestUtil.createSite();
        site.setSubDomain("f");
        TestUtil.createUserAndLogin();

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("ff");
        customForm.setSiteId(site.getSiteId());
        persistance.putCustomForm(customForm);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);

        final WidgetItem widgetCustomForm = new WidgetItem();
        widgetCustomForm.setDraftItem(customForm);
        persistance.putWidget(widgetCustomForm);
        pageVersion.getWorkPageSettings().addWidget(widgetCustomForm);

        final GetRequestContentResponse response = service.execute(site.getSiteId());
        Assert.assertNotNull(response.getItems());
        Assert.assertEquals("http://f.g", response.getPreviewUrl());
        Assert.assertEquals(1, response.getItems().size());
    }

    @Test
    public void executeForContactUs() {
        configStorage.get().setUserSitesUrl("g");

        final Site site = TestUtil.createSite();
        site.setSubDomain("f");
        TestUtil.createUserAndLogin();

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setName("ff");
        contactUs.setSiteId(site.getSiteId());
        persistance.putContactUs(contactUs);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, PageVersionType.WORK);

        final WidgetItem widgetContactUs = new WidgetItem();
        widgetContactUs.setDraftItem(contactUs);
        persistance.putWidget(widgetContactUs);
        pageVersion.getWorkPageSettings().addWidget(widgetContactUs);

        final GetRequestContentResponse response = service.execute(site.getSiteId());
        Assert.assertNotNull(response.getItems());
        Assert.assertEquals("http://f.g", response.getPreviewUrl());
        Assert.assertEquals(1, response.getItems().size());
    }

    @Test(expected = SiteNotFoundException.class)
    public void executeWithNotFoundSite() {
        TestUtil.createUserAndLogin();

        service.execute(1);
    }

    private final GetRequestContentService service = new GetRequestContentService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}
