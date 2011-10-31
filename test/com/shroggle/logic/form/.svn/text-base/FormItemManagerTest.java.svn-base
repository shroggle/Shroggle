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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.form.filter.FilterFormItemInfo;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class FormItemManagerTest {

    @Test
    public void testCreateFormItemByFilledFormItem() {
        final Site site = TestUtil.createSite();
        final DraftForm customForm = TestUtil.createCustomForm(site);

        final FilledFormItem filledFormItem = new FilledFormItem();
        filledFormItem.setFormItemName(FormItemName.NAME);
        filledFormItem.setItemName("name");
        filledFormItem.setFormItemId(10);
        filledFormItem.setPosition(100);

        final DraftFormItem formItem = FormItemManager.createFormItemByFilledFormItem(filledFormItem, customForm);

        Assert.assertNotNull(formItem);
        Assert.assertEquals(filledFormItem.getFormItemName(), formItem.getFormItemName());
        Assert.assertEquals(filledFormItem.getItemName(), formItem.getItemName());
        Assert.assertEquals(filledFormItem.getFormItemId(), formItem.getFormItemId());
        Assert.assertEquals(filledFormItem.getPosition(), formItem.getPosition());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFormItemByFilledFormItemWithNullArgument() {
        FormItemManager.createFormItemByFilledFormItem(null, null);
    }

    @Test
    public void testCreateFormItemByName() {
        final DraftFormItem formItem = FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 5, true);

        Assert.assertNotNull(formItem);
        Assert.assertEquals("First Name", formItem.getItemName());
        Assert.assertEquals(FormItemName.FIRST_NAME, formItem.getFormItemName());
        Assert.assertEquals(5, formItem.getPosition());
        Assert.assertEquals(true, formItem.isRequired());
    }

    @Test
    public void testGetItemOptionsList() {
        final DraftForm form = TestUtil.createContactUsForm();
        final DraftFormItem formItem = TestUtil.createFormItem(FormItemName.EYE_COLOR, form, 0);

        final List<String> options = FormItemManager.getItemOptionsList(formItem);

        Assert.assertNotNull(options);
        Assert.assertEquals("Brown", options.get(0));
        Assert.assertEquals("Blue", options.get(1));
        Assert.assertEquals("Turquoise", options.get(2));
        Assert.assertEquals("Green", options.get(3));
        Assert.assertEquals("Hazel", options.get(4));
    }

    @Test
    public void testGetItemOptionsListForInputField() {
        final DraftForm form = TestUtil.createContactUsForm();
        final DraftFormItem formItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, form, 0);

        final List<String> options = FormItemManager.getItemOptionsList(formItem);

        Assert.assertNotNull(options);
        Assert.assertTrue(options.isEmpty());
    }

    @Test
    public void testGetItemOptionListForPickListWithSimplePickList() {
        final DraftForm form = TestUtil.createContactUsForm();
        final DraftFormItem formItem = TestUtil.createFormItem(FormItemName.EYE_COLOR, form, 0);

        final Map<Integer, List<String>> options =
                FormItemManager.getItemOptionListForPickList(formItem);

        Assert.assertNotNull(options);
        Assert.assertEquals(1, options.keySet().size());
        Assert.assertNotNull(options.get(1));
        Assert.assertEquals("Brown", options.get(1).get(0));
        Assert.assertEquals("Blue", options.get(1).get(1));
        Assert.assertEquals("Turquoise", options.get(1).get(2));
        Assert.assertEquals("Green", options.get(1).get(3));
        Assert.assertEquals("Hazel", options.get(1).get(4));
    }

    @Test
    public void testGetItemOptionListForPickListWithComplexPickList() {
        final DraftForm form = TestUtil.createContactUsForm();
        final DraftFormItem formItem = TestUtil.createFormItem(FormItemName.DATE_ADDED, form, 0);

        final Map<Integer, List<String>> options =
                FormItemManager.getItemOptionListForPickList(formItem);

        Assert.assertNotNull(options);

        Assert.assertEquals(5, options.keySet().size());
        Assert.assertNotNull(options.get(1));
        Assert.assertNotNull(options.get(2));
        Assert.assertNotNull(options.get(3));
        Assert.assertNotNull(options.get(4));
        Assert.assertNotNull(options.get(5));

        Assert.assertEquals("January", options.get(1).get(0));
        Assert.assertEquals("February", options.get(1).get(1));
        Assert.assertEquals("March", options.get(1).get(2));
        Assert.assertEquals("April", options.get(1).get(3));
        Assert.assertEquals("May", options.get(1).get(4));
        Assert.assertEquals("June", options.get(1).get(5));
        Assert.assertEquals("July", options.get(1).get(6));
        Assert.assertEquals("August", options.get(1).get(7));
        Assert.assertEquals("September", options.get(1).get(8));
        Assert.assertEquals("October", options.get(1).get(9));
        Assert.assertEquals("November", options.get(1).get(10));
        Assert.assertEquals("December", options.get(1).get(11));

        // Checking that day of the month options got all days - 1..31.
        for (int i = 0; i < 31; i++) {
            Assert.assertEquals("" + (i + 1), options.get(2).get(i));
        }

        // Checking that years options got all years - 1900..2010.
        int possibleYear = 2010;
        for (String year : options.get(3)) {
            Assert.assertEquals("" + possibleYear, year);
            possibleYear--;
        }
        // by possibleYear+1 we are fixing last possibleYear-- in cycle.
        Assert.assertEquals(1900, (possibleYear + 1));

        // Checking that hours options got all hours - 0..23.
        int possibleHour = 0;
        for (String hour : options.get(4)) {
            Assert.assertEquals("" + possibleHour, hour);
            possibleHour++;
        }
        // by possibleHour-1 we are fixing last possibleHour++ in cycle.
        Assert.assertEquals(23, (possibleHour - 1));

        // Checking that minutes options got all minutes - 00..59.
        int possibleMinute = 0;
        for (String minute : options.get(5)) {
            Assert.assertEquals((possibleMinute < 10 ? "0" : "") + possibleMinute, minute);
            possibleMinute++;
        }
        // by possibleMinute-1 we are fixing last possibleMinute++ in cycle.
        Assert.assertEquals(59, (possibleMinute - 1));
    }

    @Test
    public void testGetItemOptionListForPickListWithInternalLinkField() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("testSite");
        final PageManager pageVersion1 = TestUtil.createPageVersionAndPage(site);
        pageVersion1.setName("pageVersion1");
        pageVersion1.setUrl("pageVersion1");
        final PageManager pageVersion2 = TestUtil.createPageVersionAndPage(site);
        pageVersion2.setName("pageVersion2");
        pageVersion2.setUrl("pageVersion2");

        final DraftForm form = TestUtil.createContactUs(site);
        final DraftFormItem formItem = TestUtil.createFormItem(FormItemName.INTERNAL_LINK, form, 0);

        final Map<Integer, List<String>> options =
                FormItemManager.getItemOptionListForPickList(formItem);

        Assert.assertNotNull(options);

        Assert.assertEquals(1, options.keySet().size());
        Assert.assertNotNull(options.get(1));
        Assert.assertEquals("http://testSite.shroggle.com/pageVersion1", options.get(1).get(0));
        Assert.assertEquals("http://testSite.shroggle.com/pageVersion2", options.get(1).get(1));
    }

    @Test
    public void testGetPickListDefaultOptionWithFormItemWithoutOptions() {
        final String defaultOption = FormItemManager.getPickListDefaultOption(FormItemName.FIRST_NAME, 0);
        Assert.assertEquals("", defaultOption);
    }

    @Test
    public void testGetPickListDefaultOptionWithWrongPickListNumber() {
        final String defaultOption = FormItemManager.getPickListDefaultOption(FormItemName.DATE_ADDED, 156);
        Assert.assertEquals("", defaultOption);
    }

    @Test
    public void testGetPickListDefaultOption() {
        final String defaultOption = FormItemManager.getPickListDefaultOption(FormItemName.DATE_ADDED, 1);
        Assert.assertEquals("Pick month", defaultOption);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPickListDefaultOptionWithNullArgument() {
        FormItemManager.getPickListDefaultOption(null, 0);
    }

    @Test
    public void testGetItemDescWithFieldThatGotDescription() {
        final String description = FormItemManager.getItemDesc(FormItemName.AGE);

        Assert.assertEquals("Accepts only numbers", description);
    }

    @Test
    public void testGetItemDescWithFieldThatGotOptions() {
        final String description = FormItemManager.getItemDesc(FormItemName.EYE_COLOR);

        Assert.assertEquals("Brown, Blue, Turquoise, Green, Hazel", description);
    }

    @Test
    public void testGetItemOptionsForDescription() {
        final String options = FormItemManager.getItemOptionsForDescription(FormItemName.EYE_COLOR);

        Assert.assertEquals("Brown, Blue, Turquoise, Green, Hazel", options);
    }

    @Test
    public void testGetItemOptionsForDescriptionWithInputField() {
        final String options = FormItemManager.getItemOptionsForDescription(FormItemName.FIRST_NAME);

        Assert.assertEquals("", options);
    }

    @Test
    public void testGetItemFieldType() {
        String value = FormItemManager.getItemFieldType(FormItemName.FIRST_NAME);
        Assert.assertEquals("text field", value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetItemFieldTypeWithNullArgument() {
        FormItemManager.getItemFieldType(null);
    }

    @Test
    public void testGetItemInfo() {
        final DraftForm form = TestUtil.createContactUsForm();
        final DraftFormItem formItem = TestUtil.createFormItem(FormItemName.EYE_COLOR, form, 5);
        formItem.setItemName("Eye color");

        final FilterFormItemInfo info = new FormItemManager().getItemInfo(formItem.getFormItemId());
        Assert.assertNotNull(info);
        Assert.assertEquals(formItem.getFormItemId(), info.getFormItemId());
        Assert.assertEquals("Eye color", info.getFormItemText());
        Assert.assertEquals(formItem.getFormItemName().getType(), info.getFormItemType());
        Assert.assertNotNull(info.getItemOptions());
        Assert.assertEquals(1, info.getItemOptions().keySet().size());
        Assert.assertNotNull(info.getItemOptions().get(1));
        Assert.assertEquals("Brown", info.getItemOptions().get(1).get(0));
        Assert.assertEquals("Blue", info.getItemOptions().get(1).get(1));
        Assert.assertEquals("Turquoise", info.getItemOptions().get(1).get(2));
        Assert.assertEquals("Green", info.getItemOptions().get(1).get(3));
        Assert.assertEquals("Hazel", info.getItemOptions().get(1).get(4));
    }

    @Test
    public void testIsCorrectFormItemForFilter() {
        final DraftForm form = TestUtil.createContactUsForm();

        final DraftFormItem eyeColorItem = TestUtil.createFormItem(FormItemName.EYE_COLOR, form, 0);
        Assert.assertTrue(FormItemManager.isCorrectFormItemForFilter(eyeColorItem));

        final DraftFormItem specialItem = TestUtil.createFormItem(FormItemName.PAGE_BREAK, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForFilter(specialItem));

        final DraftFormItem regPasswordItem = TestUtil.createFormItem(FormItemName.REGISTRATION_PASSWORD, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForFilter(regPasswordItem));

        final DraftFormItem regPasswordRetypeItem = TestUtil.createFormItem(FormItemName.REGISTRATION_PASSWORD_RETYPE, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForFilter(regPasswordRetypeItem));
    }

    @Test
    public void testIsCorrectFormItemForAdvancedSearch() {
        final DraftForm form = TestUtil.createContactUsForm();

        final DraftFormItem eyeColorItem = TestUtil.createFormItem(FormItemName.EYE_COLOR, form, 0);
        Assert.assertTrue(FormItemManager.isCorrectFormItemForAdvancedSearch(eyeColorItem));

        final DraftFormItem specialItem = TestUtil.createFormItem(FormItemName.PAGE_BREAK, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForAdvancedSearch(specialItem));

        final DraftFormItem regPasswordItem = TestUtil.createFormItem(FormItemName.REGISTRATION_PASSWORD, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForAdvancedSearch(regPasswordItem));

        final DraftFormItem regPasswordRetypeItem = TestUtil.createFormItem(FormItemName.REGISTRATION_PASSWORD_RETYPE, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForAdvancedSearch(regPasswordRetypeItem));

        final DraftFormItem fileUploadItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForAdvancedSearch(fileUploadItem));
    }

    @Test
    public void testIsCorrectFormItemForManageYourVotes() {
        final DraftForm form = TestUtil.createContactUsForm();

        final DraftFormItem eyeColorItem = TestUtil.createFormItem(FormItemName.EYE_COLOR, form, 0);
        Assert.assertTrue(FormItemManager.isCorrectFormItemForManageYourVotes(eyeColorItem));

        final DraftFormItem specialItem = TestUtil.createFormItem(FormItemName.PAGE_BREAK, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForManageYourVotes(specialItem));

        final DraftFormItem regPasswordItem = TestUtil.createFormItem(FormItemName.REGISTRATION_PASSWORD, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForManageYourVotes(regPasswordItem));

        final DraftFormItem regPasswordRetypeItem = TestUtil.createFormItem(FormItemName.REGISTRATION_PASSWORD_RETYPE, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForManageYourVotes(regPasswordRetypeItem));

        final DraftFormItem fileUploadItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, form, 0);
        Assert.assertFalse(FormItemManager.isCorrectFormItemForManageYourVotes(fileUploadItem));
    }

}
