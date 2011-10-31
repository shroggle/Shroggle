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
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.TellFriendCantSendException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class TellFriendsManagerTest {

    private final Config config = ServiceLocator.getConfigStorage().get();

    @Before
    public void before() {
        config.setApplicationName("testApplicationName");
        config.setApplicationUrl("www.application.url");
        config.setUserSitesUrl("application.url");
        config.setSupportEmail("support@email.com");
    }

    @Test
    public void sendWithNull() {
        new TellFriendsManager(null).send(null);

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void testSave() throws Exception {
        final Site site = TestUtil.createSite();
        final TellFriend tellFriend = TestUtil.createTellFriend(site);


        final TellFriendEdit edit = new TellFriendEdit();
        edit.setSendEmails(true);
        edit.setTellFriendId(tellFriend.getId());
        edit.setTellFriendName("name");
        new TellFriendsManager(new UserManager(TestUtil.createUser())).save(edit);


        Assert.assertEquals(true, tellFriend.isSendEmails());
    }

    @Test(expected = TellFriendCantSendException.class)
    public void sendWithNullTo() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetTellFriend.getWidgetId());
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test(expected = TellFriendCantSendException.class)
    public void sendWithNullFrom() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@west.com");
        send.setWidgetId(widgetTellFriend.getWidgetId());
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void sendWithNullText() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setSiteShowOption(SiteShowOption.OUTSIDE_APP);
        send.setSiteId(widgetTellFriend.getSiteId());
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void sendWithNotFoundWidget() {
        final TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        send.setSiteId(TestUtil.createSite().getSiteId());
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void send() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setEmailTo("a@west.com");
        send.setSiteShowOption(SiteShowOption.OUTSIDE_APP);
        send.setEmailFrom("a@west.com");
        send.setEmail("a");
        send.setSiteId(widgetTellFriend.getSiteId());
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void send_withMultipleEmails() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setEmailTo("1@com.ua, 2@com.ua, 3@com.ua");
        send.setEmailFrom("1@com.ua, 2@com.ua, 3@com.ua");
        send.setEmail("a");
        send.setSiteId(widgetTellFriend.getSiteId());
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void sendWithWidgetWithoutTellFriend() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void sendWithCcMe() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setCcMe(true);
        send.setEmail("fff");
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        send.setSiteId(widgetTellFriend.getSiteId());
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void sendWithWidget_withOriginalEmailText() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("childSiteDomainName");
        site.setTitle("title");

        final DraftTellFriend tellFriend = TestUtil.createTellFriend(site);
        tellFriend.setMailSubject("subject");
        tellFriend.setMailText(ServiceLocator.getInternationStorage().get("configureTellFriend", Locale.US).get("mailDefaultText", config.getApplicationName(), config.getApplicationUrl()));
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(tellFriend);

        final PageManager pageManager = TestUtil.createPageVersion(TestUtil.createPage(site));
        pageManager.addWidget(widgetItem);

        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetItem.getWidgetId());
        send.setSiteId(site.getId());
        send.setCcMe(false);
        send.setEmail("fff");
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("subject", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("fff\n" +
                "\n" +
                "This message has been sent from your friend a@west.com, while visiting the 'title' site. " +
                "They think you might be interested in this page: " +
                "http://childSiteDomainName.application.url/null?null. " +
                "This site is powered by " + config.getApplicationName() + " " + config.getApplicationUrl() + ", push button easy web site creation tools.",
                mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void sendWithWidget_withCustomEmailText() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("childSiteDomainName");

        final DraftTellFriend tellFriend = TestUtil.createTellFriend(site);
        tellFriend.setMailSubject("subject");
        tellFriend.setMailText("mailText");
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(tellFriend);

        final PageManager pageManager = TestUtil.createPageVersion(TestUtil.createPage(site));
        pageManager.addWidget(widgetItem);

        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetItem.getWidgetId());
        send.setSiteId(site.getId());
        send.setCcMe(false);
        send.setEmail("fff");
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("subject", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("fff" +
                "\n" +
                "\n" +
                "mailText",
                mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void sendWithoutWidget() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("childSiteDomainName");

        final DraftTellFriend tellFriend = TestUtil.createTellFriend(site);
        tellFriend.setMailSubject("subject");
        tellFriend.setMailText("mailText");
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(tellFriend);

        final PageManager pageManager = TestUtil.createPageVersion(TestUtil.createPage(site));
        pageManager.addWidget(widgetItem);

        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(-1);
        send.setSiteId(site.getId());
        send.setCcMe(false);
        send.setEmail("fff");
        send.setEmailTo("a@west.com");
        send.setEmailFrom("a@west.com");
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());

        Assert.assertEquals("Your friend thinks you might be interested in this", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("fff" +
                "\n" +
                "\n" +
                "This message has been sent from your friend " + send.getEmailFrom() + ", while visiting the " +
                "'" + new SiteManager(site).getName() + "' site (http://childSiteDomainName." + config.getUserSitesUrl() + "). " +
                "This site is powered by " + config.getApplicationName() + " http://" + config.getApplicationUrl() +
                ", push button easy web site creation tools.",
                mockMailSender.getMails().get(0).getText());
    }

    @Test(expected = TellFriendCantSendException.class)
    public void sendWithCcMeWithEmptyFrom() {
        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendSend send = new TellFriendSend();
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setCcMe(true);
        send.setEmail("fff");
        new TellFriendsManager(null).send(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
