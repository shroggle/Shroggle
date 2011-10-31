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
package com.shroggle.logic.registrationConfirmation;

import com.shroggle.entity.DraftChildSiteRegistration;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.User;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import junit.framework.Assert;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 *         Date: 14.08.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RegistrationConfirmationManagerTest {


    @Test
    public void testIsLinkExpired_user() {
        User user = TestUtil.createUser();
        Assert.assertFalse(RegistrationConfirmationManager.isLinkExpired(user));
    }

    @Test
    public void testIsLinkExpired_settings() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration(site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(form, site);
        settings.setCreatedDate(new Date());
        Assert.assertFalse(RegistrationConfirmationManager.isLinkExpired(settings));
    }


    @Test
    public void testIsLinkExpired_withoutChildSiteSettings() {
        ChildSiteSettings settings = null;
        Assert.assertFalse(RegistrationConfirmationManager.isLinkExpired(settings));
    }

    @Test
    public void testIsLinkExpired_withoutUser() {
        User user = null;
        Assert.assertFalse(RegistrationConfirmationManager.isLinkExpired(user));
    }

    @Test
    public void testIsLinkExpired_WithNullUserRegistration() {
        User user = TestUtil.createUser();


        Site site = TestUtil.createSite();
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration(site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(form, site);


        settings.setCreatedDate(new Date());
        user.setRegistrationDate(null);

        Assert.assertFalse(RegistrationConfirmationManager.isLinkExpired(user));
    }


    @Test
    public void testIsLinkExpired_withExpiredUser() {
        User user = TestUtil.createUser();


        Site site = TestUtil.createSite();
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration(site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(form, site);


        settings.setCreatedDate(new Date());
        user.setRegistrationDate(new Date(System.currentTimeMillis() - 20 * 24 * 60 * 60 * 1000L));

        Assert.assertTrue(RegistrationConfirmationManager.isLinkExpired(user));
    }

    @Test
    public void testIsLinkExpired_withExpiredChildSiteSettings() {
        User user = TestUtil.createUser();


        Site site = TestUtil.createSite();
        DraftChildSiteRegistration form = TestUtil.createChildSiteRegistration(site);
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(form, site);


        settings.setCreatedDate(new Date(System.currentTimeMillis() - 20 * 24 * 60 * 60 * 1000L));
        user.setRegistrationDate(new Date());

        Assert.assertTrue(RegistrationConfirmationManager.isLinkExpired(settings));
    }
}
