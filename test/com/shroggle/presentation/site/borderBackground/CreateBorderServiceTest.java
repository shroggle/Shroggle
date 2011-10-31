/*
*/
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


package com.shroggle.presentation.site.borderBackground;

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;


@RunWith(TestRunnerWithMockServices.class)
public class CreateBorderServiceTest extends TestBaseWithMockService {

    private CreateBorderService service = new CreateBorderService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void execute_forWidget() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);


        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.addWidget(widget);


        BackgroundImage backgroundImage = new BackgroundImage();
        persistance.putBackgroundImage(backgroundImage);

        final Border border = new Border();
        Style padding = TestUtil.createStyle("padding", StyleType.ALL_SIDES, MeasureUnit.EM, "2");
        Style margin = TestUtil.createStyle("margin", StyleType.ALL_SIDES, MeasureUnit.PX, "1");
        Style width = TestUtil.createStyle("border-width", StyleType.ALL_SIDES, MeasureUnit.CM, "10");
        Style style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "solid");
        Style color = TestUtil.createStyle("border-color", StyleType.ALL_SIDES, MeasureUnit.PX, "red");
        border.setBorderColor(color);
        border.setBorderWidth(width);
        border.setBorderMargin(margin);
        border.setBorderPadding(padding);
        border.setBorderStyle(style);

        service.executeForWidget(widget.getWidgetId(), null, border, true);
        Assert.assertTrue(widget.getBorderId() != null);

        final Border borderBackground = persistance.getBorder(widget.getBorderId());
        Assert.assertEquals(color.getValues().getTopValue(), borderBackground.getBorderColor().getValues().getTopValue());
    }

    @Test
    public void execute_forDraftItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setPassword("1");
        user.setEmail("a@a");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        WidgetItem widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);


        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.addWidget(widget);


        BackgroundImage backgroundImage = new BackgroundImage();
        persistance.putBackgroundImage(backgroundImage);

        final Border border = new Border();
        Style padding = TestUtil.createStyle("padding", StyleType.ALL_SIDES, MeasureUnit.EM, "2");
        Style margin = TestUtil.createStyle("margin", StyleType.ALL_SIDES, MeasureUnit.PX, "1");
        Style width = TestUtil.createStyle("border-width", StyleType.ALL_SIDES, MeasureUnit.CM, "10");
        Style style = TestUtil.createStyle("border-style", StyleType.ALL_SIDES, MeasureUnit.PX, "solid");
        Style color = TestUtil.createStyle("border-color", StyleType.ALL_SIDES, MeasureUnit.PX, "red");
        border.setBorderColor(color);
        border.setBorderWidth(width);
        border.setBorderMargin(margin);
        border.setBorderPadding(padding);
        border.setBorderStyle(style);

        final DraftImage draftText = TestUtil.createDraftImage(site);

        service.executeForWidget(null, draftText.getId(), border, true);
        Assert.assertTrue(widget.getBorderId() != null);

        final Border borderBackground = persistance.getBorder(draftText.getBorderId());
        Assert.assertEquals(color.getValues().getTopValue(), borderBackground.getBorderColor().getValues().getTopValue());
    }
    
    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
        service.executeForWidget(null, null, null, false);
    }

}
