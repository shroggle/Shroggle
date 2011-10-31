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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.form.FormItemManager;
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
import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class ShowFilledFormsServiceTest {

    @Before
    public void before() {
        MockWebContext webContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        mockHttpServletRequest.setCookies(new Cookie[0]);
        webContext.setHttpServletRequest(mockHttpServletRequest);
    }

    @Test
    public void showForForm_without_IMAGE_FILE_UPLOAD() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftForm form2 = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form2);

        request.setFormId(form.getFormId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Last Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(2, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals("value", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(1).getValue());
        Assert.assertEquals("value", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(1).getValue());
        Assert.assertFalse(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForForm_with_IMAGE_FILE_UPLOAD() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftFormItem formItem = FormItemManager.createFormItemByName(FormItemName.IMAGE_FILE_UPLOAD, 2, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        form.addFormItem(formItem);
        final DraftForm form2 = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form2);

        request.setFormId(form.getFormId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Image File Upload", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(2, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals(null, service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(1).getValue());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(1).getNotSpecified());
        Assert.assertEquals("value", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals(null, service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(1).getValue());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(1).getNotSpecified());
        Assert.assertTrue(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForWidget() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftForm form2 = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final WidgetItem widgetCustomForm = TestUtil.createWidgetItem();
        widgetCustomForm.setDraftItem(form);
        persistance.putWidget(widgetCustomForm);
        pageVersion.addWidget(widgetCustomForm);

        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form2);

        request.setWidgetId(widgetCustomForm.getWidgetId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Last Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(2, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals("value", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(1).getValue());
        Assert.assertEquals("value", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(1).getValue());
        Assert.assertFalse(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForWidgetGallery() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getFormId());
        persistance.putItem(gallery);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final WidgetItem widgetGallery = TestUtil.createWidgetItem();
        widgetGallery.setDraftItem(gallery);
        persistance.putWidget(widgetGallery);
        pageVersion.addWidget(widgetGallery);


        request.setWidgetId(widgetGallery.getWidgetId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Last Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(0, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertFalse(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForGallery() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getFormId());
        persistance.putItem(gallery);

        request.setGalleryId(gallery.getId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Last Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(0, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertFalse(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForGalleryWithZeroFilter() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftGallery gallery = new DraftGallery();
        gallery.setFormId1(form.getFormId());
        gallery.setFormFilterId(0);
        persistance.putItem(gallery);

        request.setGalleryId(gallery.getId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Last Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(0, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertFalse(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForGalleryWithFilter() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");

        final DraftFormFilter formFilter = new DraftFormFilter();
        form.addFilter(formFilter);
        persistance.putFormFilter(formFilter);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFormFilterId(formFilter.getFormFilterId());
        gallery.setFormId1(-1);
        persistance.putItem(gallery);

        request.setGalleryId(gallery.getId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Last Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(0, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertFalse(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test
    public void showForWidget_with_IMAGE_FILE_UPLOAD() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final DraftFormItem formItem = FormItemManager.createFormItemByName(FormItemName.IMAGE_FILE_UPLOAD, 2, false);
        ServiceLocator.getPersistance().putFormItem(formItem);
        form.addFormItem(formItem);
        final DraftForm form2 = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final WidgetItem widgetCustomForm = TestUtil.createWidgetItem();
        widgetCustomForm.setDraftItem(form);
        persistance.putWidget(widgetCustomForm);
        pageVersion.addWidget(widgetCustomForm);

        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form2);

        request.setWidgetId(widgetCustomForm.getWidgetId());
        final ShowFilledFormsResponse response = service.execute(request);
        Assert.assertEquals("/site/form/manageFormRecords.jsp", response.getHtml());
        Assert.assertEquals("First Name", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(0));
        Assert.assertEquals("Image File Upload", service.getManageFormRecordsRequest().getCustomCellsItemNames().get(1));
        Assert.assertEquals(form, service.getForm());
        Assert.assertEquals(2, service.getManageFormRecordsRequest().getFilledFormInfos().size());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals(null, service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(1).getValue());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(0).getCustomCellsItemValues().get(1).getNotSpecified());
        Assert.assertEquals("value", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(0).getValue());
        Assert.assertEquals(null, service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(1).getValue());
        Assert.assertEquals("&lt;not specified&gt;", service.getManageFormRecordsRequest().getFilledFormInfos().get(1).getCustomCellsItemValues().get(1).getNotSpecified());
        Assert.assertTrue(service.getManageFormRecordsRequest().isShowBulkUpload());
    }

    @Test(expected = UserNotLoginedException.class)
    public void showWithNotLoginedUser() throws IOException, ServletException {
        service.execute(request);
    }

    private final ShowFormRecordsService service = new ShowFormRecordsService();
    private final ShowFilledFormsRequest request = new ShowFilledFormsRequest();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
