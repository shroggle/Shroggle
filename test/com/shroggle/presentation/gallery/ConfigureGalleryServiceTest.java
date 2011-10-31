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
package com.shroggle.presentation.gallery;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureGalleryServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);

        Site site2 = TestUtil.createSite("title2", "url2");
        TestUtil.createUserOnSiteRightInactiveAdmin(user, site2);

        Site site3 = TestUtil.createSite("title3", "url3");
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.EDITOR);

        DraftGallery gallery1 = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");
        TestUtil.createGallery(site1.getSiteId(), "galleryName2", "commentsNotes2");
        TestUtil.createGallery(site1.getSiteId(), "galleryName3", "commentsNotes3");
        TestUtil.createGallery(site2.getSiteId(), "galleryName4", "commentsNotes4");
        TestUtil.createGallery(site3.getSiteId(), "galleryName5", "commentsNotes5");
        TestUtil.createGallery(-1, "galleryName6", "commentsNotes6");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1, "page1");
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery1.getId(), true, true);

        ConfigureGalleryResponse response = service.execute(widget.getWidgetId(), null);
        Assert.assertEquals(2, response.getFormItemsWithSize().length);
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, response.getFormItemsWithSize()[0]);
        Assert.assertEquals(FormItemName.VIDEO_FILE_UPLOAD, response.getFormItemsWithSize()[1]);
        Assert.assertEquals("title1", service.getWidgetTitle().getSiteTitle());
        Assert.assertEquals("page1", service.getWidgetTitle().getPageVersionTitle());
        Assert.assertEquals("galleryName1", service.getWidgetTitle().getWidgetTitle());
        Assert.assertEquals("galleryName1", service.getGallery().getName());
        Assert.assertEquals("commentsNotes1", service.getGallery().getNotesComments());
        TestUtil.assertIntAndBigInt(widget.getWidgetId(), service.getWidgetId());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteSettings());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteStars());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteLinks());
    }

    @Test
    public void executeFromManageItems() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);

        Site site2 = TestUtil.createSite("title2", "url2");
        TestUtil.createUserOnSiteRightInactiveAdmin(user, site2);

        Site site3 = TestUtil.createSite("title3", "url3");
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.EDITOR);

        DraftGallery gallery1 = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");
        TestUtil.createGallery(site1.getSiteId(), "galleryName2", "commentsNotes2");
        TestUtil.createGallery(site1.getSiteId(), "galleryName3", "commentsNotes3");
        TestUtil.createGallery(site2.getSiteId(), "galleryName4", "commentsNotes4");
        TestUtil.createGallery(site3.getSiteId(), "galleryName5", "commentsNotes5");
        TestUtil.createGallery(-1, "galleryName6", "commentsNotes6");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1, "page1");

        ConfigureGalleryResponse response = service.execute(null, gallery1.getId());
        Assert.assertEquals(2, response.getFormItemsWithSize().length);
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, response.getFormItemsWithSize()[0]);
        Assert.assertEquals(FormItemName.VIDEO_FILE_UPLOAD, response.getFormItemsWithSize()[1]);
        Assert.assertEquals(null, service.getWidgetTitle());
        Assert.assertEquals("galleryName1", service.getGallery().getName());
        Assert.assertEquals("commentsNotes1", service.getGallery().getNotesComments());
        Assert.assertEquals(null, service.getWidgetId());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteSettings());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteStars());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteLinks());
    }

    @Test
    public void executeWithCrossWidgetIdThatWasDeleted() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);

        DraftGallery gallery1 = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");
        gallery1.setDataCrossWidgetId(123);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1, "page1");
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery1.getId(), true, true);

        ConfigureGalleryResponse response = service.execute(widget.getWidgetId(), null);
        Assert.assertEquals(null, response.getGallery().getDataCrossWidgetId());
        Assert.assertEquals(2, response.getFormItemsWithSize().length);
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, response.getFormItemsWithSize()[0]);
        Assert.assertEquals(FormItemName.VIDEO_FILE_UPLOAD, response.getFormItemsWithSize()[1]);
        Assert.assertEquals("title1", service.getWidgetTitle().getSiteTitle());
        Assert.assertEquals("page1", service.getWidgetTitle().getPageVersionTitle());
        Assert.assertEquals("galleryName1", service.getWidgetTitle().getWidgetTitle());
        Assert.assertEquals("galleryName1", service.getGallery().getName());
        Assert.assertEquals("commentsNotes1", service.getGallery().getNotesComments());
        TestUtil.assertIntAndBigInt(widget.getWidgetId(), service.getWidgetId());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteSettings());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteStars());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteLinks());
    }

    @Test
    public void executeWithWidgetWithoutRightOnOwnerSite() throws Exception {
        final Site onwerSite = TestUtil.createSite();

        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin("aa");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftGallery gallery = TestUtil.createGallery(onwerSite.getSiteId(), "galleryName1", "commentsNotes1");
        final SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "page1");
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion,
                TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1").getId());
        widgetGallery.setDraftItem(gallery);

        ConfigureGalleryResponse response = service.execute(widgetGallery.getWidgetId(), null);
        Assert.assertEquals(2, response.getFormItemsWithSize().length);
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, response.getFormItemsWithSize()[0]);
        Assert.assertEquals(FormItemName.VIDEO_FILE_UPLOAD, response.getFormItemsWithSize()[1]);
        TestUtil.assertIntAndBigInt(widgetGallery.getWidgetId(), service.getWidgetId());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteSettings());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteStars());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteLinks());
        Assert.assertEquals(1, service.getSitesForData().size());
        Assert.assertEquals(site, service.getSitesForData().get(0));
    }

    @Test(expected = SiteNotFoundException.class)
    public void executeWithoutRightOnOwnerSite() throws Exception {
        final Site onwerSite = TestUtil.createSite();

        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndLogin("aa");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftGallery gallery = TestUtil.createGallery(onwerSite.getSiteId(), "galleryName1", "commentsNotes1");
        final SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "page1");
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion,
                TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1").getId());
        widgetGallery.setDraftItem(gallery);

        service.execute(null, gallery.getId());
    }

    @Test
    public void executeForNewGalleryWithWidgetAndForceVoting() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActiveAdmin(user, site1);

        Site site2 = TestUtil.createSite("title2", "url2");
        TestUtil.createUserOnSiteRightInactiveAdmin(user, site2);

        Site site3 = TestUtil.createSite("title3", "url3");
        TestUtil.createUserOnSiteRightActive(user, site3, SiteAccessLevel.EDITOR);

        TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");
        TestUtil.createGallery(site1.getSiteId(), "galleryName2", "commentsNotes2");
        TestUtil.createGallery(site1.getSiteId(), "galleryName3", "commentsNotes3");
        TestUtil.createGallery(site2.getSiteId(), "galleryName4", "commentsNotes4");
        TestUtil.createGallery(site3.getSiteId(), "galleryName5", "commentsNotes5");
        TestUtil.createGallery(-1, "galleryName6", "commentsNotes6");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1, "page1");
        final DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");
        gallery.setIncludesVotingModule(true);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId());

        ConfigureGalleryResponse response = service.execute(widget.getWidgetId(), null);
        Assert.assertEquals(2, response.getFormItemsWithSize().length);
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, response.getFormItemsWithSize()[0]);
        Assert.assertEquals(FormItemName.VIDEO_FILE_UPLOAD, response.getFormItemsWithSize()[1]);
        Assert.assertTrue(service.getGallery().isIncludesVotingModule());
        Assert.assertEquals("title1", service.getWidgetTitle().getSiteTitle());
        Assert.assertEquals("page1", service.getWidgetTitle().getPageVersionTitle());
        Assert.assertEquals("galleryName1", service.getWidgetTitle().getWidgetTitle());
        Assert.assertEquals("galleryName1", service.getGallery().getName());
        Assert.assertEquals("commentsNotes1", service.getGallery().getNotesComments());
        TestUtil.assertIntAndBigInt(widget.getWidgetId(), service.getWidgetId());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteSettings());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteStars());
        Assert.assertNotNull(service.getGallery().getEdit().getVoteLinks());
        Assert.assertEquals(2, service.getSitesForData().size());
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        User user = TestUtil.createUser("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        DraftGallery gallery1 = TestUtil.createGallery(site1.getSiteId(), "name1");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery1.getId(), true, true);
        service.execute(widget.getWidgetId(), null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws Exception {
        TestUtil.createUserAndLogin("aa");
        service.execute(-1, null);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotMy() throws Exception {
        TestUtil.createUserAndLogin("aa");
        User user2 = TestUtil.createUser("aa2");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user2, site1, SiteAccessLevel.ADMINISTRATOR);

        DraftGallery gallery1 = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site1, "page1");
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery1.getId(), true, true);

        service.execute(widget.getWidgetId(), null);
    }

    private final ConfigureGalleryService service = new ConfigureGalleryService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
