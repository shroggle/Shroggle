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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(value = TestRunnerWithMockServices.class)
public class CreateContactUsServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final ContactUs contactUs = TestUtil.createContactUs(site);

        WidgetItem widget = TestUtil.createTextWidget();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setDisplayHeader(true);
        request.setEmail("a@a.a");
        request.setHeader("header");
        request.setWidgetId(widget.getWidgetId());
        request.setContactUsId(contactUs.getId());

        DraftFormItem item = new DraftFormItem();
        item.setPosition(0);
        item.setRequired(true);
        item.setFormItemName(FormItemName.FIRST_NAME);
        ServiceLocator.getPersistance().putFormItem(item);
        List<DraftFormItem> items = new ArrayList<DraftFormItem>();
        items.add(item);
        request.setFormItems(items);

        final FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(widget.getWidgetId(), response.getWidget().getWidgetId());
        Assert.assertEquals("name", contactUs.getName());
        Assert.assertEquals("header", contactUs.getDescription());
        Assert.assertTrue(contactUs.isShowDescription());
        Assert.assertEquals(FormType.CONTACT_US, contactUs.getType());
    }

    @Test
    public void executeFromManageItems() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final ContactUs contactUs = TestUtil.createContactUs(site);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setDisplayHeader(true);
        request.setEmail("a@a.a");
        request.setHeader("header");
        request.setContactUsId(contactUs.getId());

        DraftFormItem item = new DraftFormItem();
        item.setPosition(0);
        item.setRequired(true);
        item.setFormItemName(FormItemName.FIRST_NAME);
        ServiceLocator.getPersistance().putFormItem(item);
        List<DraftFormItem> items = new ArrayList<DraftFormItem>();
        items.add(item);
        request.setFormItems(items);

        final FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());
        Assert.assertEquals("name", contactUs.getName());
        Assert.assertEquals("header", contactUs.getDescription());
        Assert.assertTrue(contactUs.isShowDescription());
        Assert.assertEquals(FormType.CONTACT_US, contactUs.getType());
    }

    @Test(expected = InvalidContactUsEmailException.class)
    public void executeWithBadEmail() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final ContactUs contactUs = TestUtil.createContactUs(site);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setDisplayHeader(true);
        request.setEmail("a");
        request.setHeader("header");
        request.setContactUsId(contactUs.getId());

        DraftFormItem item = new DraftFormItem();
        item.setPosition(0);
        item.setRequired(true);
        item.setFormItemName(FormItemName.FIRST_NAME);
        ServiceLocator.getPersistance().putFormItem(item);
        List<DraftFormItem> items = new ArrayList<DraftFormItem>();
        items.add(item);
        request.setFormItems(items);

        service.execute(request);
    }

    @Test(expected = InvalidContactUsEmailException.class)
    public void executeWithoutEmail() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final ContactUs contactUs = TestUtil.createContactUs(site);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setDisplayHeader(true);
        request.setEmail(null);
        request.setHeader("header");
        request.setContactUsId(contactUs.getId());

        DraftFormItem item = new DraftFormItem();
        item.setPosition(0);
        item.setRequired(true);
        item.setFormItemName(FormItemName.FIRST_NAME);
        ServiceLocator.getPersistance().putFormItem(item);
        List<DraftFormItem> items = new ArrayList<DraftFormItem>();
        items.add(item);
        request.setFormItems(items);

        service.execute(request);
    }


    @Test(expected = PageBreakBeforeRequiredFieldsException.class)
    public void executeWithPageBreakBeforeMandatory() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setDisplayHeader(true);
        request.setEmail("a@a.com");
        request.setHeader("header");
        request.setWidgetId(widget.getWidgetId());

        final List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.PAGE_BREAK, FormItemName.REGISTRATION_EMAIL);

        request.setFormItems(formItems);

        service.execute(request);
    }

    @Test(expected = ContactUsNameNotUniqueException.class)
    public void executeWithNotUniqueName() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftContactUs contactUs = new DraftContactUs();
        contactUs.setShowDescription(true);
        contactUs.setEmail("aa@a.a");
        contactUs.setDescription("header");
        contactUs.setName("name2");
        contactUs.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putContactUs(contactUs);

        DraftContactUs contactUs2 = new DraftContactUs();
        contactUs2.setShowDescription(true);
        contactUs2.setEmail("aa2@a.a");
        contactUs2.setDescription("header2");
        contactUs2.setName("name");
        contactUs2.setSiteId(site.getSiteId());
        ServiceLocator.getPersistance().putContactUs(contactUs2);

        widget.setDraftItem(contactUs);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setContactUsId(contactUs.getId());
        request.setDisplayHeader(true);
        request.setEmail("a@a.a");
        request.setHeader("header");
        request.setWidgetId(widget.getWidgetId());
        final List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_EMAIL);
        request.setFormItems(formItems);

        service.execute(request);
    }

    @Test(expected = ContactUsNotFoundException.class)
    public void executeWithNotFoundContactUs() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setContactUsName("name");
        request.setContactUsId(-1);
        request.setDisplayHeader(true);
        request.setEmail("a@a.a");
        request.setHeader("header");
        final List<DraftFormItem> formItems = TestUtil.createFormItems(FormItemName.FIRST_NAME, FormItemName.REGISTRATION_EMAIL);
        request.setFormItems(formItems);

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws ServletException, IOException {
        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void executeWithoutWidget() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        final CreateContactUsWidgetRequest request = new CreateContactUsWidgetRequest();
        request.setWidgetId(2);
        service.execute(request);
    }

    private final CreateContactUsService service = new CreateContactUsService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
