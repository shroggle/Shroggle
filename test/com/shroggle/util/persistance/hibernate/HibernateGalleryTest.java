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

import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class HibernateGalleryTest extends HibernatePersistanceTestBase {

    @Test
    public void getGalleriesByUserIdWithSiteOnItemRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        List<DraftItem> findGalleries = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(gallery, findGalleries.get(0));
        Assert.assertEquals(1, findGalleries.size());
    }

    @Test
    public void getGalleriesByUserIdWithSiteOnItemRightAndOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);

        SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        List<DraftItem> findGalleries = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(gallery, findGalleries.get(0));
        Assert.assertEquals(1, findGalleries.size());
    }

    @Test
    public void getGalleriesByUserIdOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        gallery.setSiteId(site.getSiteId());
        persistance.putItem(gallery);

        List<DraftItem> findGalleries = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(gallery, findGalleries.get(0));
        Assert.assertEquals(1, findGalleries.size());
    }

    @Test
    public void getGalleriesByUserIdWithoutOwner() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        List<DraftItem> findGalleries = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(0, findGalleries.size());
    }

    @Test
    public void getGalleriesByFormId() {
        DraftGallery gallery = new DraftGallery();
        gallery.setName("gallery1");
        gallery.setFormId1(1);
        persistance.putItem(gallery);


        DraftGallery gallery2 = new DraftGallery();
        gallery2.setName("gallery2");
        gallery2.setFormId1(1);
        persistance.putItem(gallery2);

        DraftGallery gallery3 = new DraftGallery();
        gallery3.setName("gallery3");
        gallery3.setFormId1(2);
        persistance.putItem(gallery3);

        List<DraftGallery> findGalleries = persistance.getGalleriesByFormId(1);
        Assert.assertEquals(2, findGalleries.size());
        Assert.assertEquals(gallery, findGalleries.get(0));
        Assert.assertEquals(gallery2, findGalleries.get(1));
    }

    @Test
    public void getGalleriesByUserIdWithSiteOnItemRightNotAcceptedRight() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(true);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(null);
        persistance.putSiteOnItem(siteOnItemRight);

        List<DraftItem> findGalleries = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(0, findGalleries.size());
    }

    @Test
    public void getGalleriesByUserIdWithSiteOnItemRightNotActive() {
        User user = new User();
        user.setEmail("a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setThemeId(new ThemeId());
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setActive(false);
        userOnSiteRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        persistance.putUserOnSiteRight(userOnSiteRight);

        DraftGallery gallery = new DraftGallery();
        gallery.setName("blogName");
        persistance.putItem(gallery);

        SiteOnItem siteOnItemRight = gallery.createSiteOnItemRight(site);
        siteOnItemRight.setAcceptDate(new Date());
        persistance.putSiteOnItem(siteOnItemRight);

        List<DraftItem> findGalleries = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(0, findGalleries.size());
    }

    @Test
    public void getGalleryById() {
        Site site1 = new Site();site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);


        Site site2 = new Site();site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("2");
        id2.setThemeCss("2");
        site2.setThemeId(id2);
        persistance.putSite(site2);

        DraftGallery gallery1 = new DraftGallery();
        gallery1.setName("name1");
        gallery1.setSiteId(site1.getSiteId());
        persistance.putItem(gallery1);


        DraftGallery gallery2 = new DraftGallery();
        gallery2.setName("name2");
        gallery2.setSiteId(site1.getSiteId());
        persistance.putItem(gallery2);


        DraftGallery gallery3 = new DraftGallery();
        gallery3.setName("name3");
        gallery3.setSiteId(site1.getSiteId());
        persistance.putItem(gallery3);

        DraftGallery gallery4 = new DraftGallery();
        gallery4.setName("name4");
        gallery4.setSiteId(site2.getSiteId());
        persistance.putItem(gallery4);


        DraftGallery newGallery = persistance.getGalleryById(gallery4.getId());
        Assert.assertEquals(newGallery, gallery4);
    }

    @Test
    public void getGalleriesByUserId() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        Site site1 = new Site();site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setActive(true);
        user.addUserOnSiteRight(userOnUserRight);
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight);
        site1.addUserOnSiteRight(userOnUserRight);
        persistance.putUserOnSiteRight(userOnUserRight);

        Site site2 = new Site();site2.getSitePaymentSettings().setUserId(-1);
        site2.setTitle("title2");
        site2.setSubDomain("2");
        site2.setCreationDate(new Date());
        ThemeId id2 = new ThemeId();
        id2.setTemplateDirectory("2");
        id2.setThemeCss("2");
        site2.setThemeId(id2);
        persistance.putSite(site2);

        UserOnSiteRight userOnUserRight2 = new UserOnSiteRight();
        userOnUserRight2.setActive(false);
        user.addUserOnSiteRight(userOnUserRight2);
        userOnUserRight2.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        user.addUserOnSiteRight(userOnUserRight2);
        site2.addUserOnSiteRight(userOnUserRight2);
        persistance.putUserOnSiteRight(userOnUserRight2);

        Site site3 = new Site();site3.getSitePaymentSettings().setUserId(-1);
        site3.setTitle("title3");
        site3.setSubDomain("3");
        site3.setCreationDate(new Date());
        ThemeId id3 = new ThemeId();
        id3.setTemplateDirectory("3");
        id3.setThemeCss("3");
        site3.setThemeId(id3);
        persistance.putSite(site3);

        UserOnSiteRight userOnUserRight3 = new UserOnSiteRight();
        userOnUserRight3.setActive(true);
        user.addUserOnSiteRight(userOnUserRight3);
        userOnUserRight3.setSiteAccessType(SiteAccessLevel.EDITOR);
        user.addUserOnSiteRight(userOnUserRight3);
        site3.addUserOnSiteRight(userOnUserRight3);
        persistance.putUserOnSiteRight(userOnUserRight3);

        DraftGallery gallery1 = new DraftGallery();
        gallery1.setName("name1");
        gallery1.setSiteId(site1.getSiteId());
        persistance.putItem(gallery1);

        DraftGallery gallery2 = new DraftGallery();
        gallery2.setName("name2");
        gallery2.setSiteId(site1.getSiteId());
        persistance.putItem(gallery2);

        DraftGallery gallery3 = new DraftGallery();
        gallery3.setName("name3");
        gallery3.setSiteId(site1.getSiteId());
        persistance.putItem(gallery3);

        DraftGallery gallery4 = new DraftGallery();
        gallery4.setName("name4");
        gallery4.setSiteId(site2.getSiteId());
        persistance.putItem(gallery4);

        DraftGallery gallery5 = new DraftGallery();
        gallery5.setName("name5");
        gallery5.setSiteId(site3.getSiteId());
        persistance.putItem(gallery5);

        DraftGallery gallery6 = new DraftGallery();
        gallery6.setName("name6");
        gallery6.setSiteId(-1);
        persistance.putItem(gallery6);

        List<DraftItem> galleriesFromSite1 = persistance.getDraftItemsByUserId(user.getUserId(), ItemType.GALLERY);
        Assert.assertEquals(4, galleriesFromSite1.size());
    }

    @Test
    public void testGetGalleriesByDataAndParentDataCrossWidgetIds() {
        DraftGallery gallery1 = new DraftGallery();
        gallery1.setName("name1");
        gallery1.setSiteId(-1);
        gallery1.setDataCrossWidgetId(1);
        persistance.putItem(gallery1);

        DraftGallery gallery2 = new DraftGallery();
        gallery2.setName("name2");
        gallery2.setSiteId(-1);
        gallery2.setDataCrossWidgetId(1);
        persistance.putItem(gallery2);

        DraftGallery gallery3 = new DraftGallery();
        gallery3.setName("name3");
        gallery3.setSiteId(-1);
        gallery3.setDataCrossWidgetId(2);
        persistance.putItem(gallery3);

        DraftGallery gallery4 = new DraftGallery();
        gallery4.setName("name4");
        gallery4.setSiteId(-1);
        gallery4.setDataCrossWidgetId(2);
        persistance.putItem(gallery4);

        DraftGallery gallery5 = new DraftGallery();
        gallery5.setName("name5");
        gallery5.setSiteId(-1);
        gallery5.setDataCrossWidgetId(3);
        persistance.putItem(gallery5);

        DraftGallery gallery6 = new DraftGallery();
        gallery6.setName("name6");
        gallery6.setSiteId(-1);
        gallery6.setDataCrossWidgetId(null);
        persistance.putItem(gallery6);

        final List<DraftGallery>  galleries = persistance.getGalleriesByDataCrossWidgetIds(1, 2, null);
        Assert.assertEquals(4, galleries.size());
    }

}