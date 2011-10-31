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
import com.shroggle.exception.*;
import com.shroggle.logic.site.TellFriendEdit;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.WebContextGetter;
import com.shroggle.presentation.tellFriend.SaveTellFriendService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SaveTellFriendServiceTest {

    @Before
    public void before() {
        final MockWebContext mockWebContext = (MockWebContext) webContextGetter.get();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        mockWebContext.setHttpServletResponse(new MockHttpServletResponse());
    }


    @Test
    public void executeFromSiteEditPage() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        final DraftTellFriend draftTellFriend = TestUtil.createTellFriend(site);
        widgetTellFriend.setDraftItem(draftTellFriend);

        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendName("g1");
        edit.setMailText("g");
        edit.setTellFriendId(draftTellFriend.getId());

        FunctionalWidgetInfo response = service.execute(edit);
        Assert.assertNotNull(response);
        Assert.assertEquals(widgetTellFriend.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertNotNull(widgetTellFriend.getDraftItem());
        final DraftTellFriend tellFriend = (DraftTellFriend) (widgetTellFriend.getDraftItem());
        Assert.assertNotNull(tellFriend);
        TestUtil.assertIntAndBigInt(tellFriend.getId(), widgetTellFriend.getDraftItem().getId());
        Assert.assertEquals("gg", tellFriend.getMailSubject());
        Assert.assertEquals("g1", tellFriend.getName());
        Assert.assertEquals("g", tellFriend.getMailText());
        TestUtil.assertIntAndBigInt(site.getSiteId(), tellFriend.getSiteId());
    }
    
    @Test
    public void executeFromManageItems() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftTellFriend draftTellFriend = TestUtil.createTellFriend(site);

        final TellFriendEdit edit = new TellFriendEdit();
        edit.setMailSubject("gg");
        edit.setTellFriendName("g1");
        edit.setMailText("g");
        edit.setTellFriendId(draftTellFriend.getId());

        FunctionalWidgetInfo response = service.execute(edit);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("gg", draftTellFriend.getMailSubject());
        Assert.assertEquals("g1", draftTellFriend.getName());
        Assert.assertEquals("g", draftTellFriend.getMailText());
        TestUtil.assertIntAndBigInt(site.getSiteId(), draftTellFriend.getSiteId());
    }

    @Test
    public void executeWithLongName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        final DraftTellFriend draftTellFriend = TestUtil.createTellFriend(site);
        widgetTellFriend.setDraftItem(draftTellFriend);

        final TellFriendEdit edit = new TellFriendEdit();
        edit.setTellFriendId(draftTellFriend.getId());
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendName(TestUtil.createString(900));
        edit.setMailText("g");
        service.execute(edit);

        Assert.assertNotNull(widgetTellFriend.getDraftItem());
        final DraftTellFriend tellFriend = (DraftTellFriend) (widgetTellFriend.getDraftItem());
        Assert.assertNotNull(tellFriend);
        TestUtil.assertIntAndBigInt(tellFriend.getId(), widgetTellFriend.getDraftItem().getId());
        Assert.assertEquals("gg", tellFriend.getMailSubject());
        Assert.assertEquals(250, tellFriend.getName().length());
        Assert.assertEquals("g", tellFriend.getMailText());
        TestUtil.assertIntAndBigInt(site.getSiteId(), tellFriend.getSiteId());
    }

    @Test
    public void executeWithLongSubject() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        final DraftTellFriend draftTellFriend = TestUtil.createTellFriend(site);
        widgetTellFriend.setDraftItem(draftTellFriend);

        final TellFriendEdit edit = new TellFriendEdit();
        edit.setTellFriendId(draftTellFriend.getId());
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject(TestUtil.createString(900));
        edit.setTellFriendName("gg");
        edit.setMailText("g");
        service.execute(edit);

        Assert.assertNotNull(widgetTellFriend.getDraftItem());
        final DraftTellFriend tellFriend = (DraftTellFriend) (widgetTellFriend.getDraftItem());
        Assert.assertNotNull(tellFriend);
        TestUtil.assertIntAndBigInt(tellFriend.getId(), widgetTellFriend.getDraftItem().getId());
        Assert.assertEquals("gg", tellFriend.getName());
        Assert.assertEquals(250, tellFriend.getMailSubject().length());
        Assert.assertEquals("g", tellFriend.getMailText());
        TestUtil.assertIntAndBigInt(site.getSiteId(), tellFriend.getSiteId());
    }

    @Test(expected = TellFriendNameNullOrEmptyException.class)
    public void executeWithNullName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendName(null);
        edit.setMailText("g");
        service.execute(edit);
    }

    @Test(expected = TellFriendNameNullOrEmptyException.class)
    public void executeWithEmptyName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendName("     ");
        edit.setMailText("g");
        service.execute(edit);
    }

    @Test(expected = TellFriendNameNotUniqueException.class)
    public void executeNotUniuqeName() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);

        DraftTellFriend tellFriend = TestUtil.createTellFriend(site);
        widgetTellFriend.setDraftItem(tellFriend);

        DraftTellFriend tellFriend2 = TestUtil.createTellFriend(site);
        tellFriend2.setName("not_unique");

        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendName("g1");
        edit.setTellFriendId(tellFriend.getId());
        edit.setTellFriendName("not_unique");
        edit.setMailText("g");
        service.execute(edit);
    }

    @Test(expected = TellFriendNotFoundException.class)
    public void executeForNotMy() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        DraftTellFriend tellFriend = new DraftTellFriend();
        persistance.putTellFriend(tellFriend);

        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendId(tellFriend.getId());
        edit.setTellFriendName("g1");
        edit.setMailText("g");
        service.execute(edit);
    }

    @Test(expected = ClassCastException.class)
    public void executeForNotFindTellFriend() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        edit.setMailSubject("gg");
        edit.setTellFriendId(1);
        edit.setTellFriendName("g1");
        edit.setMailText("g");
        service.execute(edit);
    }

    @Test(expected = NullPointerException.class)
    public void executeWithNull() throws Exception {
        TestUtil.createUserAndLogin();

        service.execute(null);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        service.execute(new TellFriendEdit());
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotFoundWidget() throws Exception {
        TestUtil.createUserAndLogin();

        TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(-1);
        edit.setTellFriendName("test");

        service.execute(edit);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithNotMyWidget() throws Exception {
        TestUtil.createUserAndLogin();
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetTellFriend = new WidgetItem();
        persistance.putWidget(widgetTellFriend);
        pageVersion.addWidget(widgetTellFriend);


        final TellFriendEdit edit = new TellFriendEdit();
        edit.setWidgetId(widgetTellFriend.getWidgetId());
        service.execute(edit);
    }

    private final SaveTellFriendService service = new SaveTellFriendService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final WebContextGetter webContextGetter = ServiceLocator.getWebContextGetter();

}
