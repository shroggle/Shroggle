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
package com.shroggle.util.config;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class ConfigTest {

    @Test
    public void defaultIsUseCacheConfigStorage() {
        Config config = new Config();
        Assert.assertTrue(config.isUseCacheConfigStorage());
    }

    @Test
    public void defaultIsUseCacheFileSystem() {
        Config config = new Config();
        Assert.assertTrue(config.isUseCacheFileSystem());
    }

    @Test
    public void defaultGetResourcesVideo() {
        Config config = new Config();
        Assert.assertNotNull(config.getSiteResourcesVideo());

        ConfigResourcesVideo resourcesVideo = new ConfigResourcesVideo();
        config.setSiteResourcesVideo(resourcesVideo);
        Assert.assertEquals(resourcesVideo, config.getSiteResourcesVideo());
    }

    @Test
    public void getAndSetFlvMeta() {
        Config config = new Config();
        Assert.assertEquals("flvmeta", config.getFlvmeta());

        config.setFlvmeta("f");
        Assert.assertEquals("f", config.getFlvmeta());
    }

    @Test
    public void isUseConsoleMailSender() {
        Config config = new Config();
        Assert.assertFalse(config.isUseConsoleMailSender());
        config.setUseConsoleMailSender(true);
        Assert.assertTrue(config.isUseConsoleMailSender());
    }

    @Test
    public void getExpireUserDeleteCount() {
        Config config = new Config();
        Assert.assertEquals(10, config.getExpireUserDeleteCount());
        config.setExpireUserDeleteCount(1000);
        Assert.assertEquals(1000, config.getExpireUserDeleteCount());
    }

    @Test
    public void isUseTestHelp() {
        Config config = new Config();
        Assert.assertTrue(config.isUseTestHelp());
        config.setUseTestHelp(false);
        Assert.assertFalse(config.isUseTestHelp());
    }

    @Test
    public void isUseCacheHibernate() {
        Config config = new Config();
        Assert.assertTrue(config.isUseCacheHibernate());
        config.setUseCacheHibernate(false);
        Assert.assertFalse(config.isUseCacheHibernate());
    }

    @Test
    public void defaultIsUseInternationalHightlight() {
        Config config = new Config();
        Assert.assertFalse(config.isUseInternationalHightlight());
    }

    @Test
    public void getExpireUserTime() {
        Config config = new Config();
        Assert.assertEquals(5L * 24L * 60L * 60L * 1000L, config.getExpireUserTime());
        config.setExpireUserTime(20L);
        Assert.assertEquals(20L, config.getExpireUserTime());
    }

    @Test
    public void isUseCacheConfigStorage() {
        Config config = new Config();
        config.setUseCacheConfigStorage(true);
        Assert.assertTrue(config.isUseCacheConfigStorage());
        config.setUseCacheConfigStorage(false);
        Assert.assertFalse(config.isUseCacheConfigStorage());
    }

    @Test
    public void isUseCacheFileSystem() {
        Config config = new Config();
        config.setUseCacheFileSystem(true);
        Assert.assertTrue(config.isUseCacheFileSystem());
        config.setUseCacheFileSystem(false);
        Assert.assertFalse(config.isUseCacheFileSystem());
    }

    @Test
    public void isUseJournalToPersistance() {
        Config config = new Config();
        Assert.assertTrue(config.isUseJournalToPersistance());
        config.setUseJournalToPersistance(false);
        Assert.assertFalse(config.isUseJournalToPersistance());
    }

    @Test
    public void isUseJournalToConsole() {
        Config config = new Config();
        Assert.assertFalse(config.isUseJournalToConsole());
        config.setUseJournalToConsole(true);
        Assert.assertTrue(config.isUseJournalToConsole());
    }

    @Test
    public void isUseCacheShowWorkPageVersion() {
        Config config = new Config();
        Assert.assertTrue(config.isUseCacheShowWorkPageVersion());
        config.setUseCacheShowWorkPageVersion(false);
        Assert.assertFalse(config.isUseCacheShowWorkPageVersion());
        config.setUseCacheShowWorkPageVersion(true);
        Assert.assertTrue(config.isUseCacheShowWorkPageVersion());
    }

    @Test
    public void getSupportEmail() {
        Config config = new Config();
        Assert.assertNull(config.getSupportEmail());
        config.setSupportEmail("a");
        Assert.assertEquals("a", config.getSupportEmail());
    }

    @Test
    public void getAdminLogin() {
        Config config = new Config();
        Assert.assertNull(config.getAdminLogin());
        config.setAdminLogin("a");
        Assert.assertEquals("a", config.getAdminLogin());
    }

    @Test
    public void getRegistration() {
        Config config = new Config();
        Assert.assertNotNull(config.getRegistration());
        final ConfigRegistration configRegistration = new ConfigRegistration();
        config.setRegistration(configRegistration);
        Assert.assertEquals(configRegistration, config.getRegistration());
    }

    @Test
    public void getSmtp() {
        Config config = new Config();
        Assert.assertNotNull(config.getMail());
        final ConfigSmtp configSmtp = new ConfigSmtp();
        config.setMail(configSmtp);
        Assert.assertEquals(configSmtp, config.getMail());
    }

    @Test
    public void getUserSitesUrl() {
        Config config = new Config();
        Assert.assertNull(config.getUserSitesUrl());
        config.setUserSitesUrl("fff");
        Assert.assertEquals("fff", config.getUserSitesUrl());
    }

    @Test
    public void getApplicationUrl() {
        Config config = new Config();
        Assert.assertNull(config.getApplicationUrl());
        config.setApplicationUrl("fff");
        Assert.assertEquals("fff", config.getApplicationUrl());
    }

    @Test
    public void getNotUserSiteUrls() {
        Config config = new Config();
        Assert.assertNotNull(config.getNotUserSiteUrls());
        Assert.assertTrue(config.getNotUserSiteUrls().isEmpty());
        Set<String> notUserSiteUrls = new HashSet<String>();
        config.setNotUserSiteUrls(notUserSiteUrls);
        Assert.assertEquals(notUserSiteUrls, config.getNotUserSiteUrls());
    }

    @Test
    public void getBlockedSubDomain() {
        Config config = new Config();
        Assert.assertNotNull(config.getBlockedSubDomain());
        Assert.assertTrue(config.getBlockedSubDomain().isEmpty());
        Set<String> blockedSubDomains = new HashSet<String>();
        config.setBlockedSubDomain(blockedSubDomains);
        Assert.assertEquals(blockedSubDomains, config.getBlockedSubDomain());
    }

}
