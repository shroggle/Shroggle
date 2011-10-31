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
import com.shroggle.entity.DraftText;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(TestRunnerWithMockServices.class)
public class ConfigureTextServiceTest {

    private ConfigureTextService service = new ConfigureTextService();

    @Before
    public void before(){
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void showFromSiteEditPage() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widgetText = TestUtil.createTextWidget(pageVersion);

        service.execute(widgetText.getWidgetId(), null);
        Assert.assertEquals(widgetText.getDraftItem().getId(), service.getTextItem().getId());
        Assert.assertNotNull(service.getWidgetTitle());
    }

    @Test
    public void showFromManageItems() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftText draftText = new DraftText();
        ServiceLocator.getPersistance().putItem(draftText);

        service.execute(null, draftText.getId());
        Assert.assertEquals(draftText.getId(), service.getTextItem().getId());
        Assert.assertNull(service.getWidgetTitle());
    }

    @Test
    public void testWidgetText() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetItem widgetItem = TestUtil.createTextWidget(pageVersion);
        final DraftText draftText = (DraftText)(widgetItem.getDraftItem());

        draftText.setText("text");

        service.execute(widgetItem.getWidgetId(), null);
        Assert.assertEquals("text", service.getTextItem().getText());
        Assert.assertEquals("Text", service.getWidgetTitle().getWidgetTitle());
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithoutLogin() throws IOException, ServletException {
        WidgetItem widgetText = TestUtil.createTextWidget();

        service.execute(widgetText.getWidgetId(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showWithNotFoundTextItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(null, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showWithWidgetWithoutItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widgetText = TestUtil.createTextWidget(pageVersion);
        widgetText.setDraftItem(null);

        service.execute(widgetText.getWidgetId(), null);
    }    

}
