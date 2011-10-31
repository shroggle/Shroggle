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
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.WidgetComposit;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.TestCase;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowAddWidgetActionTest {

    @Test
    public void show() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetComposit widget = new WidgetComposit();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        action.setWidgetId(widget.getWidgetId());
        action.setShowFromManageItemsPage(false);
        
        action.execute();
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithoutLogin() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        action.setWidgetId(-1);
        action.setShowFromManageItemsPage(false);

        action.execute();
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithoutWidget() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        action.setWidgetId(-1);
        action.setShowFromManageItemsPage(false);

        action.execute();
    }

    private final ShowAddWidgetAction action = new ShowAddWidgetAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
