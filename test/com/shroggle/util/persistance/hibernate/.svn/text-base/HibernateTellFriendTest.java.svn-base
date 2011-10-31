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

public class HibernateTellFriendTest extends HibernatePersistanceTestBase {

    @Test
    public void getTellFriendsBySiteId() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("a");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend tellFriend4 = new DraftTellFriend();
        tellFriend4.setName("name4");
        tellFriend4.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend4);

        List<DraftTellFriend> galleriesFromSite1 = persistance.getTellFriendsBySiteId(site1.getSiteId());
        Assert.assertEquals(3, galleriesFromSite1.size());
        Assert.assertEquals(tellFriend2, galleriesFromSite1.get(0));
        Assert.assertEquals(tellFriend, galleriesFromSite1.get(1));
        Assert.assertEquals(tellFriend3, galleriesFromSite1.get(2));
    }

    @Test
    public void getTellFriendsNoneBySiteId() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("a");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend3);

        List<DraftTellFriend> tellFriends = persistance.getTellFriendsBySiteId(site2.getSiteId());
        Assert.assertEquals(0, tellFriends.size());
    }

    @Test
    public void getTellFriendByNameAndSite() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("a");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend foundTellFriend = persistance.getTellFriendByNameAndSiteId("g", site1.getSiteId());
        Assert.assertEquals(tellFriend, foundTellFriend);
    }

    @Test
    public void getTellFriendByNameAndNotNeedSite() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("a");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend foundTellFriend = persistance.getTellFriendByNameAndSiteId("g", site2.getSiteId());
        Assert.assertNull(foundTellFriend);
    }

    @Test
    public void getTellFriendByNotFoundNameAndSite() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("a");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend foundTellFriend = persistance.getTellFriendByNameAndSiteId("g1", site1.getSiteId());
        Assert.assertNull(foundTellFriend);
    }

    @Test
    public void getTellFriendByNameAndSiteWithMany() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("g");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend foundTellFriend = persistance.getTellFriendByNameAndSiteId("g", site1.getSiteId());
        Assert.assertNotNull(foundTellFriend);
    }

    @Test
    public void getTellFriendByOtherCaseNameAndSite() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("g");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("a");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("z");
        tellFriend3.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend foundTellFriend = persistance.getTellFriendByNameAndSiteId("G", site1.getSiteId());
        Assert.assertNull(foundTellFriend);
    }

    @Test
    public void getTellFriendsBySiteIdNotFound() {
        List<DraftTellFriend> galleriesFromSite1 = persistance.getTellFriendsBySiteId(1);
        Assert.assertEquals(0, galleriesFromSite1.size());
    }

    @Test
    public void getTellFriendById() {
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

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("name1");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend tellFriend2 = new DraftTellFriend();
        tellFriend2.setName("name2");
        tellFriend2.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend2);

        DraftTellFriend tellFriend3 = new DraftTellFriend();
        tellFriend3.setName("name3");
        tellFriend3.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend3);

        DraftTellFriend tellFriend4 = new DraftTellFriend();
        tellFriend4.setName("name4");
        tellFriend4.setSiteId(site2.getSiteId());
        persistance.putTellFriend(tellFriend4);


        DraftTellFriend foundTellFriend = persistance.getTellFriendById(tellFriend4.getId());
        Assert.assertEquals(foundTellFriend, tellFriend4);
    }

    @Test
    public void getTellFriendByIdNotFound() {
        Site site1 = new Site();site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("title1");
        site1.setSubDomain("1");
        site1.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site1.setThemeId(id);
        persistance.putSite(site1);

        DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setName("name1");
        tellFriend.setSiteId(site1.getSiteId());
        persistance.putTellFriend(tellFriend);

        DraftTellFriend foundTellFriend = persistance.getTellFriendById(tellFriend.getId() + 10);
        Assert.assertNull(foundTellFriend);
    }

}