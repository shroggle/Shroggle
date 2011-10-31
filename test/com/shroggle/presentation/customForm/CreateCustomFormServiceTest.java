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
package com.shroggle.presentation.customForm;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Author: dmitry.solomadin
 * Date: 09.02.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class CreateCustomFormServiceTest {
    private final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CreateCustomFormService service = new CreateCustomFormService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void set() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setSiteId(site.getSiteId());
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);

        Assert.assertEquals("FormName1", customForm.getName());
        Assert.assertEquals("FormDescription1", customForm.getDescription());
        Assert.assertTrue(customForm.isShowDescription());
        Assert.assertEquals(FormType.CUSTOM_FORM, customForm.getType());

        final List<DraftFormItem> testFormItems = TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        for (int i = 0; i < testFormItems.size(); i++) {
            Assert.assertEquals(i, customForm.getFormItems().get(i).getPosition());
            Assert.assertEquals(international.get(testFormItems.get(i).getFormItemName().toString() + "_FN"), customForm.getFormItems().get(i).getItemName());
            Assert.assertEquals(testFormItems.get(i).getFormItemName(), customForm.getFormItems().get(i).getFormItemName());
            Assert.assertFalse(customForm.getFormItems().get(i).isRequired());
        }
    }

    @Test(expected = PageBreakBeforeRequiredFieldsException.class)
    public void setWithPageBreaksBeforeMandatory() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setSiteId(site.getSiteId());
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.PAGE_BREAK, FormItemName.REGISTRATION_EMAIL));

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void setWithUserNotLogined() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void setWithNotFoundWidget() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(0);
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void setWithNotMy() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        final Site site2 = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site2);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(widget.getDraftItem().getId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);
    }

    @Test(expected = CustomFormNotFoundException.class)
    public void setWithNotFoundCustomForm() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(0);
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);
    }

    @Test(expected = FormWithoutFormItemsException.class)
    public void setWithEmptyItems() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("asd");
        customForm.setDescription("asd");
        customForm.setSiteId(site.getSiteId());
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(customForm.getFormId());

        service.execute(request);
    }

    @Test(expected = CustomFormNameNotUniqueException.class)
    public void createNotUniqueName() throws ServletException, IOException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setSiteId(site.getSiteId());
        customForm.setName("asd");
        customForm.setDescription("asd");
        persistance.putCustomForm(customForm);
        widget.setDraftItem(customForm);

        final CreateCustomFormRequest request = new CreateCustomFormRequest();

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription1");
        request.setShowHeader(true);
        request.setFormId(customForm.getFormId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);

        customForm = persistance.getCustomFormById(widget.getDraftItem().getId());

        Assert.assertEquals("FormName1", customForm.getName());
        Assert.assertEquals("FormDescription1", customForm.getDescription());
        Assert.assertTrue(customForm.isShowDescription());
        Assert.assertEquals(FormType.CUSTOM_FORM, customForm.getType());

        final List<DraftFormItem> testFormItems = TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        for (int i = 0; i < testFormItems.size(); i++) {
            Assert.assertEquals(i, customForm.getFormItems().get(i).getPosition());
            Assert.assertEquals(international.get(testFormItems.get(i).getFormItemName().toString() + "_FN"), customForm.getFormItems().get(i).getItemName());
            Assert.assertEquals(testFormItems.get(i).getFormItemName(), customForm.getFormItems().get(i).getFormItemName());
            Assert.assertFalse(customForm.getFormItems().get(i).isRequired());
        }

        customForm = TestUtil.createCustomForm(site.getSiteId(), "name");

        request.setWidgetId(widget.getWidgetId());
        request.setFormName("FormName1");
        request.setFormDescription("FormDescription2");
        request.setShowHeader(true);
        request.setFormId(customForm.getFormId());
        request.setFormItems(TestUtil.<DraftFormItem>createFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME));

        service.execute(request);
    }
}
