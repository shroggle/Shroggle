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
package com.shroggle.logic.form;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.presentation.site.YourFormTableFormItemInfo;
import com.shroggle.entity.*;

import javax.servlet.ServletException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class FormItemsManagerTest {

    private final FormItemsManager formItemsManager = new FormItemsManager();

    @Test
    public void isPageBreakBeforeRequiredFieldsTest() {
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 1));
        formItems.add(TestUtil.createFormItem(FormItemName.REGISTRATION_EMAIL, 2));

        Assert.assertTrue(formItemsManager.isPageBreakBeforeRequiredFields(formItems));

        formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.REGISTRATION_EMAIL, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 1));

        Assert.assertTrue(formItemsManager.isPageBreakBeforeRequiredFields(formItems));

        formItems = new ArrayList<DraftFormItem>();
        formItems.add(TestUtil.createFormItem(FormItemName.NAME, 0));
        formItems.add(TestUtil.createFormItem(FormItemName.PAGE_BREAK, 2));
        formItems.add(TestUtil.createFormItem(FormItemName.REGISTRATION_EMAIL, 1));

        Assert.assertFalse(formItemsManager.isPageBreakBeforeRequiredFields(formItems));
    }

    @Test
    public void moveProductRelatedItemsToTheTop() {
        final List<FormItemName> allItems = new ArrayList<FormItemName>();
        allItems.addAll(Arrays.asList(FormItemName.values()));

        final List<YourFormTableFormItemInfo> filteredItems = new ArrayList<YourFormTableFormItemInfo>();
        new FormItemsManager().removeItemsNotShownInInitTable(allItems);

        for (FormItemName item : allItems) {
            if (item.getFormItemFilters().contains(FormItemFilter.PRODUCTS)) {
                final YourFormTableFormItemInfo yourFormTableFormItemInfo = new YourFormTableFormItemInfo();
                yourFormTableFormItemInfo.setFormItemName(item.toString());
                filteredItems.add(yourFormTableFormItemInfo);
            }
        }

        FormItemsManager.moveProductRelatedItemsToTheTop(filteredItems);

        Assert.assertEquals(FormItemName.PRICE.toString(), filteredItems.get(0).getFormItemName());
        Assert.assertEquals(FormItemName.PRODUCT_ACCESS_GROUPS.toString(), filteredItems.get(1).getFormItemName());
        Assert.assertEquals(FormItemName.SUBSCRIPTION_BILLING_PERIOD.toString(), filteredItems.get(2).getFormItemName());
    }

    @Test
    public void sortByPostionTest() throws ServletException, IOException {
        List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
        DraftFormItem formItem1 = new DraftFormItem();
        formItem1.setPosition(3);
        formItems.add(formItem1);
        DraftFormItem formItem2 = new DraftFormItem();
        formItem2.setPosition(1);
        formItems.add(formItem2);
        DraftFormItem formItem3 = new DraftFormItem();
        formItem3.setPosition(2);
        formItems.add(formItem3);

        formItemsManager.sortByPosition(formItems);
        junit.framework.Assert.assertEquals(3, formItems.size());
        junit.framework.Assert.assertEquals(1, formItems.get(0).getPosition());
        junit.framework.Assert.assertEquals(2, formItems.get(1).getPosition());
        junit.framework.Assert.assertEquals(3, formItems.get(2).getPosition());
    }

    @Test
    public void testGetFormItemsByPageBreakIndex() {
        List<FormItem> formItems = TestUtil.createFormItems(
                FormItemName.FIRST_NAME,
                FormItemName.LAST_NAME,
                //----------------------
                FormItemName.PAGE_BREAK,
                //----------------------
                FormItemName.EMAIL,
                FormItemName.PDF_FILE_UPLOAD,
                //----------------------
                FormItemName.PAGE_BREAK,
                //----------------------
                FormItemName.AUDIO_FILE_UPLOAD,
                FormItemName.IMAGE_FILE_UPLOAD,
                FormItemName.ADDRESS);

        List<FormItem> newFormItems;

        newFormItems = FormItemsManager.getSortedFormItemsByPageBreakIndex(formItems, 0, false);
        Assert.assertEquals(2, newFormItems.size());
        Assert.assertEquals(FormItemName.FIRST_NAME, newFormItems.get(0).getFormItemName());
        Assert.assertEquals(FormItemName.LAST_NAME, newFormItems.get(1).getFormItemName());


        newFormItems = FormItemsManager.getSortedFormItemsByPageBreakIndex(formItems, 1, false);
        Assert.assertEquals(2, newFormItems.size());
        Assert.assertEquals(FormItemName.EMAIL, newFormItems.get(0).getFormItemName());
        Assert.assertEquals(FormItemName.PDF_FILE_UPLOAD, newFormItems.get(1).getFormItemName());


        newFormItems = FormItemsManager.getSortedFormItemsByPageBreakIndex(formItems, 2, false);
        Assert.assertEquals(3, newFormItems.size());
        Assert.assertEquals(FormItemName.AUDIO_FILE_UPLOAD, newFormItems.get(0).getFormItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, newFormItems.get(1).getFormItemName());
        Assert.assertEquals(FormItemName.ADDRESS, newFormItems.get(2).getFormItemName());


        newFormItems = FormItemsManager.getSortedFormItemsByPageBreakIndex(formItems, 2, true);
        Assert.assertEquals(9, newFormItems.size());
        Assert.assertEquals(FormItemName.FIRST_NAME, newFormItems.get(0).getFormItemName());
        Assert.assertEquals(FormItemName.LAST_NAME, newFormItems.get(1).getFormItemName());
        Assert.assertEquals(FormItemName.PAGE_BREAK, newFormItems.get(2).getFormItemName());
        Assert.assertEquals(FormItemName.EMAIL, newFormItems.get(3).getFormItemName());
        Assert.assertEquals(FormItemName.PDF_FILE_UPLOAD, newFormItems.get(4).getFormItemName());
        Assert.assertEquals(FormItemName.PAGE_BREAK, newFormItems.get(5).getFormItemName());
        Assert.assertEquals(FormItemName.AUDIO_FILE_UPLOAD, newFormItems.get(6).getFormItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, newFormItems.get(7).getFormItemName());
        Assert.assertEquals(FormItemName.ADDRESS, newFormItems.get(8).getFormItemName());
    }

    @Test
    public void testIsPaymentBlockOnFirstPage() {
        // With payment area on second page.
        List<DraftFormItem> formItems = TestUtil.createFormItems(
                FormItemName.FIRST_NAME,
                FormItemName.LAST_NAME,
                //----------------------
                FormItemName.PAGE_BREAK,
                //----------------------
                FormItemName.PAYMENT_AREA);
        Assert.assertFalse(new FormItemsManager().isPaymentBlockOnFirstPage(formItems));

        // Without payment area but with page break
        formItems = TestUtil.createFormItems(
                FormItemName.FIRST_NAME,
                FormItemName.LAST_NAME,
                //----------------------
                FormItemName.PAGE_BREAK);
        Assert.assertFalse(new FormItemsManager().isPaymentBlockOnFirstPage(formItems));

        // Without payment area and page break
        formItems = TestUtil.createFormItems(
                FormItemName.FIRST_NAME,
                FormItemName.LAST_NAME);
        Assert.assertFalse(new FormItemsManager().isPaymentBlockOnFirstPage(formItems));

        //With payment area on first page and without page break.
        formItems = TestUtil.createFormItems(
                FormItemName.FIRST_NAME,
                FormItemName.LAST_NAME,
                FormItemName.PAYMENT_AREA);
        Assert.assertTrue(new FormItemsManager().isPaymentBlockOnFirstPage(formItems));

        //With payment area on first page and with page break.
        formItems = TestUtil.createFormItems(
                FormItemName.FIRST_NAME,
                FormItemName.LAST_NAME,
                FormItemName.PAYMENT_AREA,
                //----------------------
                FormItemName.PAGE_BREAK);
        Assert.assertTrue(new FormItemsManager().isPaymentBlockOnFirstPage(formItems));
    }

    @Test
    public void testCreateFormItems() {
        final Site site = TestUtil.createSite();
        final DraftForm customForm = TestUtil.createCustomForm(site);

        final FilledForm filledForm = new FilledForm();

        final FilledFormItem filledFormItem1 = new FilledFormItem();
        filledFormItem1.setFormItemName(FormItemName.NAME);
        filledFormItem1.setItemName("name");
        filledFormItem1.setFormItemId(10);
        filledFormItem1.setPosition(100);

        final FilledFormItem filledFormItem2 = new FilledFormItem();
        filledFormItem2.setFormItemName(FormItemName.NAME_OF_BROTHER);
        filledFormItem2.setItemName("name of brother");
        filledFormItem2.setFormItemId(1);
        filledFormItem2.setPosition(10);

        final FilledFormItem filledFormItemWithoutFormItemName = new FilledFormItem();
        filledFormItemWithoutFormItemName.setFormItemName(null);
        filledFormItemWithoutFormItemName.setItemName("without form item name");
        filledFormItemWithoutFormItemName.setFormItemId(-1);
        filledFormItemWithoutFormItemName.setPosition(-10);


        filledForm.setFilledFormItems(Arrays.asList(filledFormItem1, filledFormItem2, filledFormItemWithoutFormItemName));

        final List<DraftFormItem> formItems = FormItemsManager.createFormItemsByFilledForm(filledForm, customForm);

        Assert.assertNotNull(formItems);
        Assert.assertEquals(2, formItems.size());

        DraftFormItem formItem1 = formItems.get(0);
        DraftFormItem formItem2 = formItems.get(1);

        Assert.assertEquals(filledFormItem1.getFormItemName(), formItem1.getFormItemName());
        Assert.assertEquals(filledFormItem1.getItemName(), formItem1.getItemName());
        Assert.assertEquals(filledFormItem1.getFormItemId(), formItem1.getFormItemId());
        Assert.assertEquals(filledFormItem1.getPosition(), formItem1.getPosition());


        Assert.assertEquals(filledFormItem2.getFormItemName(), formItem2.getFormItemName());
        Assert.assertEquals(filledFormItem2.getItemName(), formItem2.getItemName());
        Assert.assertEquals(filledFormItem2.getFormItemId(), formItem2.getFormItemId());
        Assert.assertEquals(filledFormItem2.getPosition(), formItem2.getPosition());
    }

    @Test
    public void testCreateFormItems_withoutFilledFormItems() {
        final Site site = TestUtil.createSite();
        final DraftForm customForm = TestUtil.createCustomForm(site);

        final FilledForm filledForm = new FilledForm();
        filledForm.setFilledFormItems(null);

        final List<DraftFormItem> formItem = FormItemsManager.createFormItemsByFilledForm(filledForm, customForm);

        Assert.assertNotNull(formItem);
        Assert.assertEquals(0, formItem.size());
    }

    @Test
    public void testGetDefaultChildSiteRegistrationFormItems() {
        List<DraftFormItem> formItems = FormItemsManager.getDefaultChildSiteRegistrationFormItems();
        junit.framework.Assert.assertEquals(9, formItems.size());
        junit.framework.Assert.assertEquals(FormItemName.FIRST_NAME, formItems.get(0).getFormItemName());
        junit.framework.Assert.assertFalse(formItems.get(0).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.LAST_NAME, formItems.get(1).getFormItemName());
        junit.framework.Assert.assertFalse(formItems.get(1).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.REGISTRATION_EMAIL, formItems.get(2).getFormItemName());
        junit.framework.Assert.assertTrue(formItems.get(2).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.REGISTRATION_PASSWORD, formItems.get(3).getFormItemName());
        junit.framework.Assert.assertTrue(formItems.get(3).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.REGISTRATION_PASSWORD_RETYPE, formItems.get(4).getFormItemName());
        junit.framework.Assert.assertTrue(formItems.get(4).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.YOUR_PAGE_SITE_NAME, formItems.get(5).getFormItemName());
        junit.framework.Assert.assertTrue(formItems.get(5).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.YOUR_OWN_DOMAIN_NAME, formItems.get(6).getFormItemName());
        junit.framework.Assert.assertFalse(formItems.get(6).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.PAGE_BREAK, formItems.get(7).getFormItemName());
        junit.framework.Assert.assertFalse(formItems.get(7).isRequired());
        junit.framework.Assert.assertEquals(FormItemName.PAYMENT_AREA, formItems.get(8).getFormItemName());
        junit.framework.Assert.assertTrue(formItems.get(8).isRequired());
    }

}
