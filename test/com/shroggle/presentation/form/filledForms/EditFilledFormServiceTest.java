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
import com.shroggle.presentation.form.filledForms.EditFilledFormService;
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class EditFilledFormServiceTest {

    @Test
    public void saveFilledFormEdit() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        final FilledForm filledForm = TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        final List<FilledFormItem> newItems = new ArrayList<FilledFormItem>();
        final International international = ServiceLocator.getInternationStorage().get("formTable", Locale.US);

        FilledFormItem firstNameItem = new FilledFormItem();
        firstNameItem.setItemName(international.get(FormItemName.FIRST_NAME.toString() + "_FN"));
        firstNameItem.setFormItemName(FormItemName.FIRST_NAME);
        List<String> values = new ArrayList<String>();
        values.add("new name");
        firstNameItem.setValues(values);

        newItems.add(firstNameItem);

        FilledFormItem addressItem = new FilledFormItem();
        addressItem.setItemName(international.get(FormItemName.ADDRESS.toString() + "_FN"));
        addressItem.setFormItemName(FormItemName.ADDRESS);
        values = new ArrayList<String>();
        values.add("new address");
        addressItem.setValues(values);

        newItems.add(addressItem);

        service.execute(newItems, filledForm.getFilledFormId());
        Assert.assertEquals(2, persistance.getFilledFormsByFormId(form.getFormId()).size());
    }

    @Test(expected = FilledFormNotFoundException.class)
    public void saveFilledFormEditWithoutForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User user = TestUtil.createUserAndUserOnSiteRight(site);
        final DraftForm form = TestUtil.createCustomForm(site.getSiteId(), "formName");
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.ADDRESS), form);
        TestUtil.createFilledContactUsForm(user, TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.LAST_NAME), form);

        service.execute(TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.EYE_COLOR, FormItemName.FIRST_NAME), 0);
    }

    @Test(expected = UserNotLoginedException.class)
    public void saveFilledFormEditWithNotLoginedUser() throws IOException, ServletException {
        service.execute(TestUtil.createFilledFormItems(FormItemName.EMAIL, FormItemName.EYE_COLOR, FormItemName.FIRST_NAME), 0);
    }

    private final EditFilledFormService service = new EditFilledFormService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}