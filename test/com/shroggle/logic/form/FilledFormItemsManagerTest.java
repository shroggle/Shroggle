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
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.FilledForm;
import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FormItemName;

import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class FilledFormItemsManagerTest {

    @Test
    public void testGetFirstItemByFilledFormId() {
        final DraftForm form = TestUtil.createContactUsForm();

        //Creating filled forms.
        final FilledForm wrongFilledForm = TestUtil.createFilledForm(form);
        final FilledForm rightFilledForm = TestUtil.createFilledForm(form);

        //Creating filled form items.
        final FilledFormItem wrongItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        wrongItem.setFilledForm(wrongFilledForm);
        final FilledFormItem rightItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        rightItem.setFilledForm(rightFilledForm);

        //Test mehod execution.
        final FilledFormItem filledFormItem =
                FilledFormItemsManager.getFirstItemByFilledFormId(new ArrayList<FilledFormItem>() {{
                    add(wrongItem);
                    add(rightItem);
                }}, rightFilledForm.getFilledFormId());

        Assert.assertEquals(rightItem.getItemId(), filledFormItem.getItemId());
    }

    @Test
    public void testGetFirstItemByFilledFormIdWithNullList() {
        final DraftForm form = TestUtil.createContactUsForm();
        final FilledForm rightFilledForm = TestUtil.createFilledForm(form);

        //Test mehod execution.
        final FilledFormItem filledFormItem =
                FilledFormItemsManager.getFirstItemByFilledFormId(null, rightFilledForm.getFilledFormId());

        Assert.assertNull(filledFormItem);
    }

    @Test
    public void testGetFirstItemByFilledFormIdWithEmptyList() {
        final DraftForm form = TestUtil.createContactUsForm();
        final FilledForm rightFilledForm = TestUtil.createFilledForm(form);

        //Test mehod execution.
        final FilledFormItem filledFormItem =
                FilledFormItemsManager.getFirstItemByFilledFormId(new ArrayList<FilledFormItem>(), rightFilledForm.getFilledFormId());

        Assert.assertNull(filledFormItem);
    }
    
    @Test
    public void testGetFirstItemByFilledFormIdWithNotFound() {
        final DraftForm form = TestUtil.createContactUsForm();

        //Creating filled forms.
        final FilledForm wrongFilledForm = TestUtil.createFilledForm(form);
        final FilledForm wrongFilledForm2 = TestUtil.createFilledForm(form);

        //Creating filled form items.
        final FilledFormItem wrongItem = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        wrongItem.setFilledForm(wrongFilledForm);
        final FilledFormItem wrongItem2 = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        wrongItem2.setFilledForm( wrongFilledForm2);

        //Test mehod execution.
        final FilledFormItem filledFormItem =
                FilledFormItemsManager.getFirstItemByFilledFormId(new ArrayList<FilledFormItem>() {{
                    add(wrongItem);
                    add(wrongItem2);
                }}, 123);

        Assert.assertNull(filledFormItem);
    }

}
