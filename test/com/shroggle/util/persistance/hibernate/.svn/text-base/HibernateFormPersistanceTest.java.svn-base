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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.DraftCustomForm;
import junit.framework.Assert;
import org.junit.Test;
import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FormType;
import com.shroggle.entity.User;

import java.util.List;
import java.util.Date;

public class HibernateFormPersistanceTest extends HibernatePersistanceTestBase {

    @Test
    public void getLastFillFormDateByFormIdWithNotFound() {
        Assert.assertNull(persistance.getLastFillFormDateByFormId(1));
    }

    @Test
    public void getLastFillFormDateByFormIdWithout() {
        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("gg");
        persistance.putCustomForm(customForm);

        Assert.assertNull(persistance.getLastFillFormDateByFormId(customForm.getFormId()));
    }

    @Test
    public void getLastFillFormDateByFormId() {
        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("gg");
        persistance.putCustomForm(customForm);

        FilledForm filledForm1 = new FilledForm();
        filledForm1.setFormDescription("g");
        filledForm1.setType(FormType.CUSTOM_FORM);
        filledForm1.setFormId(customForm.getFormId());
        persistance.putFilledForm(filledForm1);

        FilledForm filledForm2 = new FilledForm();
        filledForm2.setFormDescription("g");
        filledForm2.setType(FormType.CUSTOM_FORM);
        filledForm2.setFillDate(new Date(System.currentTimeMillis() * 2));
        filledForm2.setFormId(customForm.getFormId());
        persistance.putFilledForm(filledForm2);

        DraftCustomForm otherCustomForm = new DraftCustomForm();
        otherCustomForm.setName("gg1");
        persistance.putCustomForm(otherCustomForm);

        FilledForm otherFilledForm = new FilledForm();
        otherFilledForm.setFormDescription("g");
        otherFilledForm.setType(FormType.CUSTOM_FORM);
        otherFilledForm.setFormId(otherCustomForm.getFormId());
        otherFilledForm.setFillDate(new Date(System.currentTimeMillis() * 3));
        persistance.putFilledForm(otherFilledForm);

        Assert.assertEquals(filledForm2.getFillDate(),
                persistance.getLastFillFormDateByFormId(customForm.getFormId()));
    }

    @Test
    public void getFilledFormsByFormIdNotFound() {
        Assert.assertTrue(persistance.getFilledFormsByFormId(1).isEmpty());
    }

    @Test
    public void getFilledFormsByFormIdWithoutFilled() {
        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("gg");
        persistance.putCustomForm(customForm);

        Assert.assertTrue(persistance.getFilledFormsByFormId(customForm.getFormId()).isEmpty());
    }

    @Test
    public void getFilledFormsByFormId() {
        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("gg");
        persistance.putCustomForm(customForm);

        FilledForm filledForm = new FilledForm();
        filledForm.setFormDescription("g");
        filledForm.setType(FormType.CUSTOM_FORM);
        filledForm.setFormId(customForm.getFormId());
        persistance.putFilledForm(filledForm);

        DraftCustomForm otherCustomForm = new DraftCustomForm();
        otherCustomForm.setName("gg1");
        persistance.putCustomForm(otherCustomForm);

        FilledForm otherFilledForm = new FilledForm();
        otherFilledForm.setFormDescription("g");
        otherFilledForm.setType(FormType.CUSTOM_FORM);
        otherFilledForm.setFormId(otherCustomForm.getFormId());
        persistance.putFilledForm(otherFilledForm);

        final List<FilledForm> filledForms = persistance.getFilledFormsByFormId(customForm.getFormId());
        Assert.assertEquals(1, filledForms.size());
        Assert.assertEquals(filledForm, filledForms.get(0));
    }

    @Test
    public void getFilledFormsByFormAndUserId() {
        final User user = new User();
        user.setEmail("");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        DraftCustomForm customForm = new DraftCustomForm();
        customForm.setName("gg");
        persistance.putCustomForm(customForm);

        FilledForm filledForm1 = new FilledForm();
        filledForm1.setUser(user);
        filledForm1.setFormDescription("filledForm1");
        filledForm1.setType(FormType.CUSTOM_FORM);
        filledForm1.setFormId(customForm.getFormId());
        persistance.putFilledForm(filledForm1);

        FilledForm filledForm2 = new FilledForm();
        filledForm2.setUser(user);
        filledForm2.setFormDescription("filledForm2");
        filledForm2.setType(FormType.CUSTOM_FORM);
        filledForm2.setFormId(customForm.getFormId());
        persistance.putFilledForm(filledForm2);



        DraftCustomForm otherCustomForm = new DraftCustomForm();
        otherCustomForm.setName("gg1");
        persistance.putCustomForm(otherCustomForm);

        FilledForm otherFilledForm = new FilledForm();
        otherFilledForm.setFormDescription("g");
        otherFilledForm.setType(FormType.CUSTOM_FORM);
        otherFilledForm.setFormId(otherCustomForm.getFormId());
        persistance.putFilledForm(otherFilledForm);

        final List<FilledForm> filledForms = persistance.getFilledFormsByFormAndUserId(customForm.getFormId(), user.getUserId());
        Assert.assertEquals(2, filledForms.size());
        Assert.assertEquals(filledForm1, filledForms.get(0));
        Assert.assertEquals(filledForm2, filledForms.get(1));
    }

}