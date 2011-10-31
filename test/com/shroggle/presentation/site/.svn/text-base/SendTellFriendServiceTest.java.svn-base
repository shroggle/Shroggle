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
import com.shroggle.entity.*;
import com.shroggle.logic.site.TellFriendSend;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class SendTellFriendServiceTest {

    @Test
    public void executeWithNull() {
        service.execute(null);

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void executeWithNotFoundWidgetId() {
        TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@w.com");
        send.setEmailFrom("a@w.com");
        send.setSiteId(TestUtil.createSite().getSiteId());

        service.execute(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void executeWithoutTellFriend() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@w.com");
        send.setEmailFrom("a@w.com");
        send.setWidgetId(widgetTellFriend.getWidgetId());

        service.execute(send);

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void executeWithNullMailText() {
        final Site site = TestUtil.createSite();

        final DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        final TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@w.com");
        send.setEmailFrom("a@w.com");
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        send.setSiteId(site.getSiteId());

        service.execute(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();

        final DraftTellFriend tellFriend = new DraftTellFriend();
        tellFriend.setMailText("ff");
        persistance.putTellFriend(tellFriend);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        widgetTellFriend.setDraftItem(tellFriend);
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        final TellFriendSend send = new TellFriendSend();
        send.setEmailTo("a@w.com");
        send.setEmailFrom("a@w.com");
        send.setWidgetId(widgetTellFriend.getWidgetId());
        send.setSiteShowOption(SiteShowOption.ON_USER_PAGES);
        send.setSiteId(site.getSiteId());

        service.execute(send);

        Assert.assertEquals(1, mockMailSender.getMails().size());
    }

    private final SendTellFriendService service = new SendTellFriendService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();

}
