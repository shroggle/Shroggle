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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class LinkedFormManagerTest {

    @Test
    public void testLinkedOptionWithoutFilledItems() {
        final DraftForm linkHolderForm = TestUtil.createContactUsForm();
        final DraftForm linkTargetForm = TestUtil.createContactUsForm();
        final DraftFormItem linkTargetItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, linkTargetForm, 0);
        final DraftFormItem linkHolderItem = TestUtil.createFormItem(FormItemName.LINKED, linkHolderForm, 0);

        linkHolderItem.setLinkedFormItemId(linkTargetItem.getFormItemId());
        linkHolderItem.setFormItemDisplayType(FormItemType.SELECTION_LIST);

        final List<String> options = LinkedFormManager.getLinkedOptions(linkHolderItem);
        Assert.assertTrue(options.isEmpty());
    }

    @Test
    public void testLinkedOptionWithSomeSingleFilledItems() {
        final DraftForm linkHolderForm = TestUtil.createContactUsForm();
        final DraftForm linkTargetForm = TestUtil.createContactUsForm();
        final DraftFormItem linkTargetItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, linkTargetForm, 0);
        final DraftFormItem linkHolderItem = TestUtil.createFormItem(FormItemName.LINKED, linkHolderForm, 0);

        linkHolderItem.setLinkedFormItemId(linkTargetItem.getFormItemId());
        linkHolderItem.setFormItemDisplayType(FormItemType.SELECTION_LIST);

        final FilledForm filledForm = TestUtil.createFilledForm(linkTargetForm);
        final FilledFormItem filledFormItem1 =
                TestUtil.createFilledFormItem(linkTargetItem.getFormItemId(), FormItemName.FIRST_NAME, "value1");
        filledFormItem1.setFilledForm(filledForm);

        final FilledForm filledForm2 = TestUtil.createFilledForm(linkTargetForm);
        final FilledFormItem filledFormItem2 =
                TestUtil.createFilledFormItem(linkTargetItem.getFormItemId(), FormItemName.FIRST_NAME, "value2");
        filledFormItem2.setFilledForm(filledForm2);
        final FilledFormItem filledFormItem3 =
                TestUtil.createFilledFormItem(0, FormItemName.FIRST_NAME, "value_not_in_fetch");
        filledFormItem3.setFilledForm(filledForm);

        final List<String> options = LinkedFormManager.getLinkedOptions(linkHolderItem);
        Assert.assertEquals(2, options.size());
        Assert.assertEquals("value1;" + filledForm.getFilledFormId() + ";" + filledFormItem1.getItemId(), options.get(0));
        Assert.assertEquals("value2;" + filledForm2.getFilledFormId() + ";" + filledFormItem2.getItemId(), options.get(1));
    }

    @Test
    public void testLinkedOptionWithSomeMultipleFilledItems() {
        final DraftForm linkHolderForm = TestUtil.createContactUsForm();
        final DraftForm linkTargetForm = TestUtil.createContactUsForm();
        final DraftFormItem linkTargetItem = TestUtil.createFormItem(FormItemName.FIRST_NAME, linkTargetForm, 0);
        final DraftFormItem linkHolderItem = TestUtil.createFormItem(FormItemName.LINKED, linkHolderForm, 0);

        linkHolderItem.setLinkedFormItemId(linkTargetItem.getFormItemId());
        linkHolderItem.setFormItemDisplayType(FormItemType.SELECTION_LIST);

        final FilledForm filledForm = TestUtil.createFilledForm(linkTargetForm);
        final FilledFormItem filledFormItem1 = TestUtil.createFilledFormItem(linkTargetItem.getFormItemId(), FormItemName.FIRST_NAME, "value1");
        filledFormItem1.setFilledForm(filledForm);
        final FilledFormItem multipleItem = TestUtil.createFilledFormItem(linkTargetItem.getFormItemId(),
                FormItemName.FIRST_NAME, "value2");
        multipleItem.setValues(new ArrayList<String>() {{
            add("value2");
            add("value3");
        }});
        multipleItem.setFilledForm(filledForm);

        TestUtil.createFilledFormItem(0, FormItemName.FIRST_NAME, "value_not_in_fetch");

        final List<String> options = LinkedFormManager.getLinkedOptions(linkHolderItem);
        Assert.assertEquals(2, options.size());
        Assert.assertEquals("value1;" + filledForm.getFilledFormId() + ";" + +filledFormItem1.getItemId(), options.get(0));
        Assert.assertEquals("value2" + FilledFormItem.VALUE_DELIMETER + "value3;" + filledForm.getFilledFormId() + ";" + multipleItem.getItemId(), options.get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkedOptionWithoutItem() {
        LinkedFormManager.getLinkedOptions(null);
    }

}
