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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.logic.form.FormManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.presentation.site.RegisteredVisitorInfo;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SaveEditVisitorServiceTest {

    final SaveEditVisitorService service = new SaveEditVisitorService();

    @Test
    public void testExecute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUser();
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("e");
        DraftForm form = TestUtil.createRegistrationForm(site.getSiteId());
        FilledForm filledForm = TestUtil.createDefaultRegistrationFilledFormForVisitorAndFormId(user, site, form.getFormId());

        List<FilledFormItem> editedFormItems = new ArrayList<FilledFormItem>();

        FilledFormItem emailItem = new FilledFormItem();
        emailItem.setFormItemName(FormItemName.EMAIL);
        emailItem.setItemName(FormManager.getFormInternational().get(FormItemName.EMAIL.toString() + "_FN"));
        List<String> values = new ArrayList<String>();
        values.add("New Email Address");
        emailItem.setValues(values);
        editedFormItems.add(emailItem);

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setFormItemName(FormItemName.FIRST_NAME);
        firstNameItem.setItemName(FormManager.getFormInternational().get(FormItemName.FIRST_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add("New First Name");
        firstNameItem.setValues(values);
        editedFormItems.add(firstNameItem);

        FilledFormItem lastNameItem = new FilledFormItem();
        lastNameItem.setFormItemName(FormItemName.LAST_NAME);
        lastNameItem.setItemName(FormManager.getFormInternational().get(FormItemName.LAST_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add("New Last Name");
        lastNameItem.setValues(values);
        editedFormItems.add(lastNameItem);

        FilledFormItem screenNameItem = new FilledFormItem();
        screenNameItem.setFormItemName(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME);
        screenNameItem.setItemName(FormManager.getFormInternational().get(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add("New Screen Name");
        screenNameItem.setValues(values);
        editedFormItems.add(screenNameItem);

        FilledFormItem newItem = new FilledFormItem();
        newItem.setFormItemName(FormItemName.MIDDLE_NAME);
        newItem.setItemName(FormManager.getFormInternational().get(FormItemName.MIDDLE_NAME.toString() + "_FN"));
        values = new ArrayList<String>();
        values.add("Middle name");
        newItem.setValues(values);
        editedFormItems.add(newItem);

        final RegisteredVisitorInfo registeredVisitorInfo = service.execute(user.getUserId(), site.getSiteId(), editedFormItems);

        Assert.assertEquals("New Email Address", registeredVisitorInfo.getEmail());
        Assert.assertEquals("New First Name", registeredVisitorInfo.getFirstName());
        Assert.assertEquals("New Last Name", registeredVisitorInfo.getLastName());
        Assert.assertEquals((int) user.getUserId(), registeredVisitorInfo.getVisitorId());

        //Checking that we added all fields to registration form
        FilledForm foundFilledForm = new FilledForm();
        foundFilledForm.setFilledFormItems(TestUtil.createDefaultRegistrationFilledFormItems("New First Name", "New Last Name", "New Screen Name", "My Telephone Number", "New Email Address"));

        Assert.assertEquals(filledForm.getFilledFormItems().size() - 1, foundFilledForm.getFilledFormItems().size());
        for (int i = 0; i < foundFilledForm.getFilledFormItems().size() + 1; i++) {
            if (i == foundFilledForm.getFilledFormItems().size()) {
                Assert.assertEquals(filledForm.getFilledFormItems().get(i).getItemName(), newItem.getItemName());
                for (int j = 0; j < newItem.getValues().size(); j++) {
                    Assert.assertEquals(filledForm.getFilledFormItems().get(i).getValues().get(j),
                            newItem.getValues().get(j));
                }
            } else {
                Assert.assertEquals(filledForm.getFilledFormItems().get(i).getItemName(), foundFilledForm.getFilledFormItems().get(i).getItemName());
                for (int j = 0; j < foundFilledForm.getFilledFormItems().get(i).getValues().size(); j++) {
                    Assert.assertEquals(filledForm.getFilledFormItems().get(i).getValues().get(j),
                            foundFilledForm.getFilledFormItems().get(i).getValues().get(j));
                }
            }
        }

        //Checking that we not changed user itself
        Assert.assertEquals("fn", user.getFirstName());
        Assert.assertEquals("ln", user.getLastName());
        Assert.assertEquals("e", user.getEmail());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithNotLoginedUser() throws Exception {
        final Site site = TestUtil.createSite();
        final User user = TestUtil.createVisitorForSite(site);
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("e");

        service.execute(user.getUserId(), site.getSiteId(), null);
    }

    @Test(expected = UserNotFoundException.class)
    public void testExecuteWithoutVisitor() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(0, site.getSiteId(), null);
    }

}
