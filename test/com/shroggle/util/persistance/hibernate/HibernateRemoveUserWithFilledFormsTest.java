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

import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class HibernateRemoveUserWithFilledFormsTest extends HibernatePersistanceTestBase {

    @Before
    public void before() {
        super.before();

        final User user1 = new User();
        user1.setEmail("a1@a");
        persistance.putUser(user1);

        final User user2 = new User();
        user2.setEmail("a2@a");
        persistance.putUser(user2);

        final FilledForm filledForm1 = new FilledForm();
        filledForm1.setType(FormType.CUSTOM_FORM);
        filledForm1.setFormDescription("g");
        user1.addFilledForm(filledForm1);
        persistance.putFilledForm(filledForm1);
        filledFormId1 = filledForm1.getFilledFormId();

        final FilledForm filledForm2 = new FilledForm();
        filledForm2.setType(FormType.CONTACT_US);
        filledForm2.setFormDescription("1");
        user2.addFilledForm(filledForm2);
        persistance.putFilledForm(filledForm2);
        filledFormId2 = filledForm2.getFilledFormId();

        userId1 = user1.getUserId();
    }

    @Test
    public void execute() {
        persistance.removeUser(persistance.getUserById(userId1));

        Assert.assertNull(persistance.getFilledFormById(filledFormId1));
        Assert.assertNotNull(persistance.getFilledFormById(filledFormId2));
    }

    private int userId1;
    private int filledFormId1;
    private int filledFormId2;

}