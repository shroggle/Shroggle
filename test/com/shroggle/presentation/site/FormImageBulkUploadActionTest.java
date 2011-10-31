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

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.presentation.image.FormImageBulkUploadAction;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemMockException;
import com.shroggle.util.filesystem.FileSystemReal;
import junit.framework.Assert;
import net.sourceforge.stripes.action.FileBean;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class FormImageBulkUploadActionTest extends TestAction<FormImageBulkUploadAction> {

    @Before
    public void before() throws IOException {
        final FileSystem fileSystem = new FileSystemReal(null, null, null, null, null, null, null, null, null);
        ServiceLocator.setFileSystem(fileSystem);
    }

    public FormImageBulkUploadActionTest() {
        super(FormImageBulkUploadAction.class, true);
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        persistance.putCustomForm(form);

        DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        formItem.setDraftForm(form);
        formItem.setItemName("image");
        formItem.setPosition(1);
        persistance.putFormItem(formItem);

        DraftFormItem formItem2 = new DraftFormItem();
        formItem2.setFormItemName(FormItemName.NAME);
        formItem2.setDraftForm(form);
        formItem2.setItemName("text");
        formItem2.setPosition(2);
        persistance.putFormItem(formItem2);

        DraftFormItem formItem3 = new DraftFormItem();
        formItem3.setFormItemName(FormItemName.ENTERED_BY);
        formItem3.setDraftForm(form);
        formItem3.setItemName("entered by");
        formItem3.setPosition(3);
        persistance.putFormItem(formItem3);


        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(formItem);
        formItems.add(formItem2);
        formItems.add(formItem3);
        form.setFormItems(formItems);

        FilledFormItem item = new FilledFormItem();
        item.setItemName("name");
        persistance.putFilledFormItem(item);
        List<FilledFormItem> items = new ArrayList<FilledFormItem>();
        items.add(item);

        FilledForm filledForm = new FilledForm();
        filledForm.setFilledFormItems(items);
        filledForm.setFormId(form.getFormId());
        persistance.putFilledForm(filledForm);

        FileBean image = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setFormId(form.getFormId());
        actionOrService.setLoginedUserId(user.getUserId());
        actionOrService.setFormItemId(formItem.getFormItemId());

        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 1);


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        final List<FilledForm> newFilledForms = ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId());
        Assert.assertEquals(newFilledForms.size(), 2);
        Assert.assertEquals(1, newFilledForms.get(0).getFilledFormItems().size());
        Assert.assertEquals(3, newFilledForms.get(1).getFilledFormItems().size());

        final List<FilledFormItem> addedItems = newFilledForms.get(1).getFilledFormItems();
        Assert.assertEquals("1", addedItems.get(0).getValues().get(0));
        Assert.assertEquals("test.png", addedItems.get(1).getValues().get(0));
        Assert.assertEquals("Balakirev Anatoliy", addedItems.get(2).getValue());
    }


    @Test
    public void execute_withSortOrder() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);



        DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        persistance.putCustomForm(form);

        DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        formItem.setDraftForm(form);
        formItem.setItemName("image");
        formItem.setPosition(1);
        persistance.putFormItem(formItem);

        DraftFormItem formItem2 = new DraftFormItem();
        formItem2.setFormItemName(FormItemName.NAME);
        formItem2.setDraftForm(form);
        formItem2.setItemName("text");
        formItem2.setPosition(2);
        persistance.putFormItem(formItem2);

        DraftFormItem formItem3 = new DraftFormItem();
        formItem3.setFormItemName(FormItemName.SORT_ORDER);
        formItem3.setDraftForm(form);
        formItem3.setItemName("Sort Order");
        formItem3.setPosition(3);
        persistance.putFormItem(formItem3);


        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(formItem);
        formItems.add(formItem2);
        formItems.add(formItem3);
        form.setFormItems(formItems);

        FilledFormItem item = new FilledFormItem();
        item.setItemName("name");
        persistance.putFilledFormItem(item);
        List<FilledFormItem> items = new ArrayList<FilledFormItem>();
        items.add(item);

        FilledForm filledForm = new FilledForm();
        filledForm.setFilledFormItems(items);
        filledForm.setFormId(form.getFormId());
        persistance.putFilledForm(filledForm);

        FileBean image = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setFormId(form.getFormId());
        actionOrService.setLoginedUserId(user.getUserId());
        actionOrService.setFormItemId(formItem.getFormItemId());


        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 1);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 2);
        final List<FilledForm> newFilledForms = ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId());
        Assert.assertEquals(1, newFilledForms.get(0).getFilledFormItems().size());
        Assert.assertEquals(3, newFilledForms.get(1).getFilledFormItems().size());

        Assert.assertEquals("image", newFilledForms.get(1).getFilledFormItems().get(0).getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, newFilledForms.get(1).getFilledFormItems().get(0).getFormItemName());
        Assert.assertEquals("1", newFilledForms.get(1).getFilledFormItems().get(0).getValues().get(0));

        Assert.assertEquals("Sort Order", newFilledForms.get(1).getFilledFormItems().get(1).getItemName());
        Assert.assertEquals(FormItemName.SORT_ORDER, newFilledForms.get(1).getFilledFormItems().get(1).getFormItemName());
        Assert.assertEquals("2", newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(0));


        Assert.assertEquals("text", newFilledForms.get(1).getFilledFormItems().get(2).getItemName());
        Assert.assertEquals(FormItemName.NAME, newFilledForms.get(1).getFilledFormItems().get(2).getFormItemName());
        Assert.assertEquals("test.png", newFilledForms.get(1).getFilledFormItems().get(2).getValues().get(0));
    }


    @Test
    public void execute_withDateAdded() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);



        DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        persistance.putCustomForm(form);

        DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        formItem.setDraftForm(form);
        formItem.setItemName("image");
        formItem.setPosition(1);
        persistance.putFormItem(formItem);

        DraftFormItem formItem2 = new DraftFormItem();
        formItem2.setFormItemName(FormItemName.NAME);
        formItem2.setDraftForm(form);
        formItem2.setItemName("text");
        formItem2.setPosition(2);
        persistance.putFormItem(formItem2);

        DraftFormItem formItem3 = new DraftFormItem();
        formItem3.setFormItemName(FormItemName.DATE_ADDED);
        formItem3.setDraftForm(form);
        formItem3.setItemName("Date Added");
        formItem3.setPosition(3);
        persistance.putFormItem(formItem3);


        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(formItem);
        formItems.add(formItem2);
        formItems.add(formItem3);
        form.setFormItems(formItems);

        FilledFormItem item = new FilledFormItem();
        item.setItemName("name");
        persistance.putFilledFormItem(item);
        List<FilledFormItem> items = new ArrayList<FilledFormItem>();
        items.add(item);

        FilledForm filledForm = new FilledForm();
        filledForm.setFilledFormItems(items);
        filledForm.setFormId(form.getFormId());
        persistance.putFilledForm(filledForm);

        FileBean image = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setFormId(form.getFormId());
        actionOrService.setLoginedUserId(user.getUserId());
        actionOrService.setFormItemId(formItem.getFormItemId());


        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 1);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 2);
        final List<FilledForm> newFilledForms = ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId());
        Assert.assertEquals(1, newFilledForms.get(0).getFilledFormItems().size());
        Assert.assertEquals(3, newFilledForms.get(1).getFilledFormItems().size());

        Assert.assertEquals("image", newFilledForms.get(1).getFilledFormItems().get(0).getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, newFilledForms.get(1).getFilledFormItems().get(0).getFormItemName());
        Assert.assertEquals("1", newFilledForms.get(1).getFilledFormItems().get(0).getValues().get(0));


        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());

        Assert.assertEquals("Date Added", newFilledForms.get(1).getFilledFormItems().get(1).getItemName());
        Assert.assertEquals(FormItemName.DATE_ADDED, newFilledForms.get(1).getFilledFormItems().get(1).getFormItemName());
        Assert.assertEquals(5, newFilledForms.get(1).getFilledFormItems().get(1).getValues().size());
        Assert.assertEquals(("" + DateUtil.getMonth(gregorianCalendar)), newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("" + gregorianCalendar.get(Calendar.DAY_OF_MONTH), newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(1));
        Assert.assertEquals("" + gregorianCalendar.get(Calendar.YEAR), newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(2));


        Assert.assertEquals("text", newFilledForms.get(1).getFilledFormItems().get(2).getItemName());
        Assert.assertEquals(FormItemName.NAME, newFilledForms.get(1).getFilledFormItems().get(2).getFormItemName());
        Assert.assertEquals("test.png", newFilledForms.get(1).getFilledFormItems().get(2).getValues().get(0));
    }


    @Test
    public void execute_withDateAddedAndSortOrder() throws Exception {
        final Site site = TestUtil.createSite();
        User user = TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);



        DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        persistance.putCustomForm(form);

        DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        formItem.setDraftForm(form);
        formItem.setItemName("image");
        formItem.setPosition(1);
        persistance.putFormItem(formItem);

        DraftFormItem formItem2 = new DraftFormItem();
        formItem2.setFormItemName(FormItemName.NAME);
        formItem2.setDraftForm(form);
        formItem2.setItemName("text");
        formItem2.setPosition(2);
        persistance.putFormItem(formItem2);

        DraftFormItem formItem3 = new DraftFormItem();
        formItem3.setFormItemName(FormItemName.DATE_ADDED);
        formItem3.setDraftForm(form);
        formItem3.setItemName("Date Added");
        formItem3.setPosition(3);
        persistance.putFormItem(formItem3);

        DraftFormItem formItem4 = new DraftFormItem();
        formItem4.setFormItemName(FormItemName.SORT_ORDER);
        formItem4.setDraftForm(form);
        formItem4.setItemName("Sort Order");
        formItem4.setPosition(4);
        persistance.putFormItem(formItem4);


        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(formItem);
        formItems.add(formItem2);
        formItems.add(formItem3);
        formItems.add(formItem4);
        form.setFormItems(formItems);

        FilledFormItem item = new FilledFormItem();
        item.setItemName("name");
        persistance.putFilledFormItem(item);
        List<FilledFormItem> items = new ArrayList<FilledFormItem>();
        items.add(item);

        FilledForm filledForm = new FilledForm();
        filledForm.setFilledFormItems(items);
        filledForm.setFormId(form.getFormId());
        persistance.putFilledForm(filledForm);

        FileBean image = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setFormId(form.getFormId());
        actionOrService.setLoginedUserId(user.getUserId());
        actionOrService.setFormItemId(formItem.getFormItemId());


        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 1);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId()).size(), 2);
        final List<FilledForm> newFilledForms = ServiceLocator.getPersistance().getFilledFormsByFormId(form.getFormId());
        Assert.assertEquals(1, newFilledForms.get(0).getFilledFormItems().size());
        Assert.assertEquals(4, newFilledForms.get(1).getFilledFormItems().size());

        Assert.assertEquals("image", newFilledForms.get(1).getFilledFormItems().get(0).getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, newFilledForms.get(1).getFilledFormItems().get(0).getFormItemName());
        Assert.assertEquals("1", newFilledForms.get(1).getFilledFormItems().get(0).getValues().get(0));


        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new Date());

        Assert.assertEquals("Date Added", newFilledForms.get(1).getFilledFormItems().get(1).getItemName());
        Assert.assertEquals(FormItemName.DATE_ADDED, newFilledForms.get(1).getFilledFormItems().get(1).getFormItemName());
        Assert.assertEquals(5, newFilledForms.get(1).getFilledFormItems().get(1).getValues().size());
        Assert.assertEquals(("" + DateUtil.getMonth(gregorianCalendar)), newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("" + gregorianCalendar.get(Calendar.DAY_OF_MONTH), newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(1));
        Assert.assertEquals("" + gregorianCalendar.get(Calendar.YEAR), newFilledForms.get(1).getFilledFormItems().get(1).getValues().get(2));


        Assert.assertEquals("Sort Order", newFilledForms.get(1).getFilledFormItems().get(2).getItemName());
        Assert.assertEquals(FormItemName.SORT_ORDER, newFilledForms.get(1).getFilledFormItems().get(2).getFormItemName());
        Assert.assertEquals("2", newFilledForms.get(1).getFilledFormItems().get(2).getValues().get(0));

        Assert.assertEquals("text", newFilledForms.get(1).getFilledFormItems().get(3).getItemName());
        Assert.assertEquals(FormItemName.NAME, newFilledForms.get(1).getFilledFormItems().get(3).getFormItemName());
        Assert.assertEquals("test.png", newFilledForms.get(1).getFilledFormItems().get(3).getValues().get(0));
    }

    @Test
    public void executeWithoutData() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);



        DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        persistance.putCustomForm(form);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormId(form.getFormId());
        persistance.putFilledForm(filledForm);

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(filledForm.getFilledFormItems().size(), 0);
    }

    @Test
    public void executeWithException() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        WidgetItem widget = TestUtil.createWidgetItem();
        widget.setPosition(0);
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);



        DraftCustomForm form = new DraftCustomForm();
        form.setSiteId(site.getSiteId());
        persistance.putCustomForm(form);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormId(form.getFormId());
        persistance.putFilledForm(filledForm);

        FileBean image = new FileBean(TestUtil.createFile(this.getClass(), "test.png"), "file", "test.png");

        actionOrService.setFileData(image);
        ServiceLocator.setFileSystem(new FileSystemMockException());

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getForwardToUrl());
        Assert.assertEquals(filledForm.getFilledFormItems().size(), 0);
    }

}
