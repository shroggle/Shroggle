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
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class FormManagerTest {

    final FormManager formManager = new FormManager();
    final International formInternational = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

    @Test
    public void getFormItemByFormItemName() {
        final DraftContactUs contactUs = new DraftContactUs();

        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        ServiceLocator.getPersistance().putContactUs(contactUs);

        DraftFormItem formItem = FormManager.getFormItemByFormItemName(FormItemName.FIRST_NAME, contactUs);
        Assert.assertNotNull(formItem);
        Assert.assertEquals(contactUs.getFormId(), formItem.getForm().getId());

        DraftFormItem formItem2 = FormManager.getFormItemByFormItemName(FormItemName.IMAGE_FILE_UPLOAD, contactUs);
        Assert.assertNull(formItem2);
    }

    @Test
    public void getFormItemByFormItemType() {
        final DraftContactUs contactUs = new DraftContactUs();

        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        ServiceLocator.getPersistance().putContactUs(contactUs);

        FormItem formItem = FormManager.getFormItemByFormItemType(FormItemType.TEXT_INPUT_FIELD, contactUs);
        Assert.assertNotNull(formItem);
        Assert.assertEquals(contactUs.getFormId(), formItem.getForm().getId());

        FormItem formItem2 = FormManager.getFormItemByFormItemType(FormItemType.FILE_UPLOAD, contactUs);
        Assert.assertNull(formItem2);
    }

    @Test
    public void getFormItemsByFormItemName() {
        final DraftContactUs contactUs = new DraftContactUs();

        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.FIRST_NAME, 0, false));
        contactUs.addFormItem(FormItemManager.createFormItemByName(FormItemName.LAST_NAME, 1, false));
        ServiceLocator.getPersistance().putContactUs(contactUs);

        List<FormItem> formItem = FormManager.getFormItemListByFormItemName(FormItemName.FIRST_NAME, contactUs);
        Assert.assertNotNull(formItem);
        Assert.assertEquals(3, formItem.size());

        List<FormItem> formItem2 = FormManager.getFormItemListByFormItemName(FormItemName.IMAGE_FILE_UPLOAD, contactUs);
        Assert.assertEquals(0, formItem2.size());
    }

    @Test
    public void getFormItemByFormItemName_WithoutForm() {
        DraftFormItem formItem2 = FormManager.getFormItemByFormItemName(FormItemName.IMAGE_FILE_UPLOAD, null);
        Assert.assertNull(formItem2);
    }

    @Test
    public void getFormItemByFormItemName_WithoutFormName() {
        DraftFormItem formItem2 = FormManager.getFormItemByFormItemName(null, new DraftContactUs());
        Assert.assertNull(formItem2);
    }

    @Test
    public void getFormItemByFormItemName_WithoutFormAndFormName() {
        DraftFormItem formItem2 = FormManager.getFormItemByFormItemName(null, null);
        Assert.assertNull(formItem2);
    }

    @Test
    public void isForceFormShowing() {
        Map<ItemType, String> parameters = new HashMap<ItemType, String>();
        parameters.put(ItemType.CHILD_SITE_REGISTRATION, "pageBreaksToPass");
        RenderContext context = new RenderContext(null, null, null, parameters, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertTrue(isForceFormShowing);
    }

    @Test
    public void isForceFormShowing_withEmptyPageBreaksToPassParam() {
        Map<ItemType, String> parameters = new HashMap<ItemType, String>();
        parameters.put(ItemType.CHILD_SITE_REGISTRATION, "");
        RenderContext context = new RenderContext(null, null, null, parameters, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertFalse(isForceFormShowing);
    }


    @Test
    public void isForceFormShowing_withWrongPageBreaksToPassParam() {
        Map<ItemType, String> parameters = new HashMap<ItemType, String>();
        parameters.put(ItemType.CHILD_SITE_REGISTRATION, "agasdgafdsg");
        RenderContext context = new RenderContext(null, null, null, parameters, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertFalse(isForceFormShowing);
    }

    @Test
    public void isForceFormShowing_withWrongItemType() {
        Map<ItemType, String> parameters = new HashMap<ItemType, String>();
        parameters.put(ItemType.FORUM, "pageBreaksToPass");
        RenderContext context = new RenderContext(null, null, null, parameters, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertFalse(isForceFormShowing);
    }


    @Test
    public void isForceFormShowing_withoutContext() {
        RenderContext context = new RenderContext(null, null, null, null, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertFalse(isForceFormShowing);
    }

    @Test
    public void isForceFormShowing_withoutParametersMapInContext() {
        Map<ItemType, String> parameters = new HashMap<ItemType, String>();
        RenderContext context = new RenderContext(null, null, null, parameters, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, ItemType.CHILD_SITE_REGISTRATION);
        Assert.assertFalse(isForceFormShowing);
    }

    @Test
    public void isForceFormShowing_withoutItemType() {
        Map<ItemType, String> parameters = new HashMap<ItemType, String>();
        parameters.put(ItemType.CHILD_SITE_REGISTRATION, "pageBreaksToPass");
        RenderContext context = new RenderContext(null, null, null, parameters, false);
        boolean isForceFormShowing = FormManager.isForceFormShowing(context, null);
        Assert.assertFalse(isForceFormShowing);
    }

    @Test
    public void testCreateDefaultAccountRegistrationForm() {
        DraftForm form = new FormManager().createDefaultAccountRegistrationForm(1);
        Assert.assertNotNull(form);
        Assert.assertEquals("Default Account Registration Form", form.getName());
        Assert.assertEquals(1, form.getSiteId());
        Assert.assertEquals(6, form.getFormItems().size());

        DraftFormItem firstName = FormManager.getFormItemByFormItemName(FormItemName.FIRST_NAME, form);
        DraftFormItem lastName = FormManager.getFormItemByFormItemName(FormItemName.LAST_NAME, form);
        DraftFormItem nickname = FormManager.getFormItemByFormItemName(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME, form);
        DraftFormItem email = FormManager.getFormItemByFormItemName(FormItemName.REGISTRATION_EMAIL, form);
        DraftFormItem password = FormManager.getFormItemByFormItemName(FormItemName.REGISTRATION_PASSWORD, form);
        DraftFormItem passwordRetype = FormManager.getFormItemByFormItemName(FormItemName.REGISTRATION_PASSWORD_RETYPE, form);

        Assert.assertNotNull(firstName);
        Assert.assertEquals(0, firstName.getPosition());
        Assert.assertEquals(false, firstName.isRequired());
        Assert.assertEquals(FormItemName.FIRST_NAME, firstName.getFormItemName());

        Assert.assertNotNull(lastName);
        Assert.assertEquals(1, lastName.getPosition());
        Assert.assertEquals(false, lastName.isRequired());
        Assert.assertEquals(FormItemName.LAST_NAME, lastName.getFormItemName());

        Assert.assertNotNull(nickname);
        Assert.assertEquals(2, nickname.getPosition());
        Assert.assertEquals(true, nickname.isRequired());
        Assert.assertEquals(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME, nickname.getFormItemName());

        Assert.assertNotNull(email);
        Assert.assertEquals(3, email.getPosition());
        Assert.assertEquals(true, email.isRequired());
        Assert.assertEquals(FormItemName.REGISTRATION_EMAIL, email.getFormItemName());

        Assert.assertNotNull(password);
        Assert.assertEquals(4, password.getPosition());
        Assert.assertEquals(true, password.isRequired());
        Assert.assertEquals(FormItemName.REGISTRATION_PASSWORD, password.getFormItemName());

        Assert.assertNotNull(passwordRetype);
        Assert.assertEquals(5, passwordRetype.getPosition());
        Assert.assertEquals(true, passwordRetype.isRequired());
        Assert.assertEquals(FormItemName.REGISTRATION_PASSWORD_RETYPE, passwordRetype.getFormItemName());
    }

    @Test
    public void testCreateOrSetFormItemsForEmptyForm() {
        final DraftForm form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<DraftFormItem>());

        form.setFormItems(new FormManager().createOrSetFormItems(form, new ArrayList<DraftFormItem>() {{
            final DraftFormItem regularFormItem1 = new DraftFormItem();
            regularFormItem1.setFormItemName(FormItemName.FIRST_NAME);
            regularFormItem1.setItemName("First Name");
            regularFormItem1.setDraftForm(form);
            regularFormItem1.setInstruction("regularFormItem1_instruction");
            regularFormItem1.setPosition(0);
            regularFormItem1.setRequired(true);
            add(regularFormItem1);

            final DraftFormItem regularFormItem2 = new DraftFormItem();
            regularFormItem2.setFormItemName(FormItemName.LAST_NAME);
            regularFormItem2.setItemName("Last Name");
            regularFormItem2.setDraftForm(form);
            regularFormItem2.setInstruction("regularFormItem2_instruction");
            regularFormItem2.setPosition(1);
            regularFormItem2.setRequired(true);
            add(regularFormItem2);

            final DraftFormItem linkedFormItem = new DraftFormItem();
            linkedFormItem.setFormItemName(FormItemName.LINKED);
            linkedFormItem.setItemName("Date Addded");
            linkedFormItem.setDraftForm(form);
            linkedFormItem.setInstruction("linkedFormItem_instruction");
            linkedFormItem.setPosition(2);
            linkedFormItem.setRequired(false);
            linkedFormItem.setLinkedFormItemId(999);
            linkedFormItem.setFormItemDisplayType(FormItemType.RADIOBUTTON);
            add(linkedFormItem);
        }}));

        Assert.assertEquals(3, form.getDraftFormItems().size());
        Assert.assertEquals(FormItemName.FIRST_NAME, form.getDraftFormItems().get(0).getFormItemName());
        Assert.assertEquals("First Name", form.getDraftFormItems().get(0).getItemName());
        Assert.assertEquals(form.getId(), form.getDraftFormItems().get(0).getForm().getId());
        Assert.assertEquals("regularFormItem1_instruction", form.getDraftFormItems().get(0).getInstruction());
        Assert.assertEquals(0, form.getDraftFormItems().get(0).getPosition());
        Assert.assertEquals(true, form.getDraftFormItems().get(0).isRequired());
        Assert.assertEquals(null, form.getDraftFormItems().get(0).getLinkedFormItemId());
        Assert.assertEquals(null, form.getDraftFormItems().get(0).getFormItemDisplayType());

        Assert.assertEquals(FormItemName.LAST_NAME, form.getDraftFormItems().get(1).getFormItemName());
        Assert.assertEquals("Last Name", form.getDraftFormItems().get(1).getItemName());
        Assert.assertEquals(form.getId(), form.getDraftFormItems().get(1).getForm().getId());
        Assert.assertEquals("regularFormItem2_instruction", form.getDraftFormItems().get(1).getInstruction());
        Assert.assertEquals(1, form.getDraftFormItems().get(1).getPosition());
        Assert.assertEquals(true, form.getDraftFormItems().get(1).isRequired());
        Assert.assertEquals(null, form.getDraftFormItems().get(1).getLinkedFormItemId());
        Assert.assertEquals(null, form.getDraftFormItems().get(1).getFormItemDisplayType());

        Assert.assertEquals(FormItemName.LINKED, form.getDraftFormItems().get(2).getFormItemName());
        Assert.assertEquals("Date Addded", form.getDraftFormItems().get(2).getItemName());
        Assert.assertEquals(form.getId(), form.getDraftFormItems().get(2).getForm().getId());
        Assert.assertEquals("linkedFormItem_instruction", form.getDraftFormItems().get(2).getInstruction());
        Assert.assertEquals(2, form.getDraftFormItems().get(2).getPosition());
        Assert.assertEquals(false, form.getDraftFormItems().get(2).isRequired());
        Assert.assertEquals((int) 999, (int) form.getDraftFormItems().get(2).getLinkedFormItemId());
        Assert.assertEquals(FormItemType.RADIOBUTTON, form.getDraftFormItems().get(2).getFormItemDisplayType());
    }

    @Test
    public void testCreateOrSetFormItemsForFormWithFormItems() {
        final DraftForm form = TestUtil.createContactUsForm();
        form.setFormItems(new ArrayList<DraftFormItem>() {{
            //This item will stay in the form but won't be updated
            final DraftFormItem regularFormItem1 = new DraftFormItem();
            regularFormItem1.setFormItemName(FormItemName.FIRST_NAME);
            regularFormItem1.setItemName("First Name");
            regularFormItem1.setDraftForm(form);
            regularFormItem1.setInstruction("regularFormItem1_instruction");
            regularFormItem1.setPosition(0);
            regularFormItem1.setRequired(true);
            add(regularFormItem1);
            ServiceLocator.getPersistance().putFormItem(regularFormItem1);

            //This item will be deleted
            final DraftFormItem regularFormItem2 = new DraftFormItem();
            regularFormItem2.setFormItemName(FormItemName.LAST_NAME);
            regularFormItem2.setItemName("Last Name");
            regularFormItem2.setDraftForm(form);
            regularFormItem2.setInstruction("regularFormItem2_instruction");
            regularFormItem2.setPosition(1);
            regularFormItem2.setRequired(true);
            add(regularFormItem2);
            ServiceLocator.getPersistance().putFormItem(regularFormItem2);

            //This item will be updated
            final DraftFormItem linkedFormItem = new DraftFormItem();
            linkedFormItem.setFormItemName(FormItemName.LINKED);
            linkedFormItem.setItemName("Date Addded");
            linkedFormItem.setDraftForm(form);
            linkedFormItem.setInstruction("linkedFormItem_instruction");
            linkedFormItem.setPosition(2);
            linkedFormItem.setRequired(false);
            linkedFormItem.setLinkedFormItemId(999);
            linkedFormItem.setFormItemDisplayType(FormItemType.RADIOBUTTON);
            add(linkedFormItem);
            ServiceLocator.getPersistance().putFormItem(linkedFormItem);
        }});

        form.setFormItems(new FormManager().createOrSetFormItems(form, new ArrayList<DraftFormItem>() {{
            final DraftFormItem regularFormItem1 = new DraftFormItem();
            regularFormItem1.setFormItemName(FormItemName.FIRST_NAME);
            regularFormItem1.setItemName("First Name");
            regularFormItem1.setDraftForm(form);
            regularFormItem1.setInstruction("regularFormItem1_instruction");
            regularFormItem1.setPosition(0);
            regularFormItem1.setRequired(true);
            regularFormItem1.setFormItemId(form.getFormItems().get(0).getFormItemId());
            add(regularFormItem1);

            final DraftFormItem regularFormItem3_new = new DraftFormItem();
            regularFormItem3_new.setFormItemName(FormItemName.ACADEMIC_DEGREE);
            regularFormItem3_new.setItemName("Academic Degree");
            regularFormItem3_new.setDraftForm(form);
            regularFormItem3_new.setInstruction("regularFormItem3_new_instruction");
            regularFormItem3_new.setPosition(1);
            regularFormItem3_new.setRequired(true);
            add(regularFormItem3_new);

            final DraftFormItem linkedFormItem_updated = new DraftFormItem();
            linkedFormItem_updated.setFormItemName(FormItemName.LINKED);
            linkedFormItem_updated.setItemName("Date Addded_new");
            linkedFormItem_updated.setDraftForm(form);
            linkedFormItem_updated.setInstruction("linkedFormItem_instruction_new");
            linkedFormItem_updated.setPosition(2);
            linkedFormItem_updated.setRequired(true);
            linkedFormItem_updated.setLinkedFormItemId(777);
            linkedFormItem_updated.setFormItemDisplayType(FormItemType.SELECT);
            linkedFormItem_updated.setFormItemId(form.getFormItems().get(2).getFormItemId());
            add(linkedFormItem_updated);
        }}));

        Assert.assertEquals(3, form.getDraftFormItems().size());
        Assert.assertEquals(FormItemName.FIRST_NAME, form.getDraftFormItems().get(0).getFormItemName());
        Assert.assertEquals("First Name", form.getDraftFormItems().get(0).getItemName());
        Assert.assertEquals(form.getId(), form.getDraftFormItems().get(0).getForm().getId());
        Assert.assertEquals("regularFormItem1_instruction", form.getDraftFormItems().get(0).getInstruction());
        Assert.assertEquals(0, form.getDraftFormItems().get(0).getPosition());
        Assert.assertEquals(true, form.getDraftFormItems().get(0).isRequired());
        Assert.assertEquals(null, form.getDraftFormItems().get(0).getLinkedFormItemId());
        Assert.assertEquals(null, form.getDraftFormItems().get(0).getFormItemDisplayType());

        Assert.assertEquals(FormItemName.ACADEMIC_DEGREE, form.getDraftFormItems().get(1).getFormItemName());
        Assert.assertEquals("Academic Degree", form.getDraftFormItems().get(1).getItemName());
        Assert.assertEquals(form.getId(), form.getDraftFormItems().get(1).getForm().getId());
        Assert.assertEquals("regularFormItem3_new_instruction", form.getDraftFormItems().get(1).getInstruction());
        Assert.assertEquals(1, form.getDraftFormItems().get(1).getPosition());
        Assert.assertEquals(true, form.getDraftFormItems().get(1).isRequired());
        Assert.assertEquals(null, form.getDraftFormItems().get(1).getLinkedFormItemId());
        Assert.assertEquals(null, form.getDraftFormItems().get(1).getFormItemDisplayType());

        Assert.assertEquals(FormItemName.LINKED, form.getDraftFormItems().get(2).getFormItemName());
        Assert.assertEquals("Date Addded_new", form.getDraftFormItems().get(2).getItemName());
        Assert.assertEquals(form.getId(), form.getDraftFormItems().get(2).getForm().getId());
        Assert.assertEquals("linkedFormItem_instruction_new", form.getDraftFormItems().get(2).getInstruction());
        Assert.assertEquals(2, form.getDraftFormItems().get(2).getPosition());
        Assert.assertEquals(true, form.getDraftFormItems().get(2).isRequired());
        Assert.assertEquals((int) 777, (int) form.getDraftFormItems().get(2).getLinkedFormItemId());
        Assert.assertEquals(FormItemType.SELECT, form.getDraftFormItems().get(2).getFormItemDisplayType());
    }

}
