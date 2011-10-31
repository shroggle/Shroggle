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
package com.shroggle.logic.gallery.paypal;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.logic.gallery.PaypalSettingsData;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.GalleryNotFoundException;
import junit.framework.Assert;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaypalSettingsForGalleryManagerTest {


    @Test
    public void testCreatePaypalSettingsData() {
        TestUtil.createUserAndLogin("usersEmail");
        final Site site = TestUtil.createSite();
        final Gallery gallery = TestUtil.createGallery(site);

        final PaypalSettingsForGallery paypalSettings = new PaypalSettingsForGallery();
        paypalSettings.setFormItemIdWithPrice(12345);
        paypalSettings.setFormItemIdWithProductName(3214);
        paypalSettings.setPaypalEmail("email");

        paypalSettings.setPaypalSettingsAlign(GalleryAlign.RIGHT);
        paypalSettings.setPaypalSettingsColumn(GalleryItemColumn.COLUMN_123);
        paypalSettings.setPaypalSettingsName("settings name");
        paypalSettings.setPaypalSettingsPosition(111111);
        paypalSettings.setPaypalSettingsRow(3233);
        paypalSettings.setEnable(true);

        gallery.setPaypalSettings(paypalSettings);

        final PaypalSettingsData data = new PaypalSettingsForGalleryManager(gallery).createPaypalSettingsData();

        Assert.assertNotNull(data);
        Assert.assertEquals(12345, data.getFormItemIdWithFullPrice().intValue());
        Assert.assertEquals(3214, data.getFormItemIdWithProductName().intValue());
        Assert.assertEquals("email", data.getPaypalEmail());

        Assert.assertEquals(GalleryAlign.RIGHT, data.getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_123, data.getColumn());
        Assert.assertEquals("settings name", data.getName());
        Assert.assertEquals(111111, data.getPosition());
        Assert.assertEquals(3233, data.getRow());
        Assert.assertEquals(true, data.isEnable());

        final International international = ServiceLocator.getInternationStorage().get("paypalSettingsForGallery", Locale.US);
        Assert.assertEquals(international.get("payPal"), data.getItemName());
    }

    @Test
    public void testCreatePaypalSettingsData_withoutName() {
        TestUtil.createUserAndLogin("usersEmail");
        final Site site = TestUtil.createSite();
        final Gallery gallery = TestUtil.createGallery(site);

        final PaypalSettingsForGallery paypalSettings = new PaypalSettingsForGallery();
        paypalSettings.setFormItemIdWithPrice(12345);
        paypalSettings.setFormItemIdWithProductName(3214);
        paypalSettings.setPaypalEmail("email");

        paypalSettings.setPaypalSettingsAlign(GalleryAlign.RIGHT);
        paypalSettings.setPaypalSettingsColumn(GalleryItemColumn.COLUMN_123);
        paypalSettings.setPaypalSettingsName(null);
        paypalSettings.setPaypalSettingsPosition(111111);
        paypalSettings.setPaypalSettingsRow(3233);
        paypalSettings.setEnable(true);

        gallery.setPaypalSettings(paypalSettings);

        final PaypalSettingsData data = new PaypalSettingsForGalleryManager(gallery).createPaypalSettingsData();

        Assert.assertNotNull(data);
        Assert.assertEquals(12345, data.getFormItemIdWithFullPrice().intValue());
        Assert.assertEquals(3214, data.getFormItemIdWithProductName().intValue());
        Assert.assertEquals("email", data.getPaypalEmail());

        Assert.assertEquals(GalleryAlign.RIGHT, data.getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_123, data.getColumn());
        Assert.assertEquals(111111, data.getPosition());
        Assert.assertEquals(3233, data.getRow());
        Assert.assertEquals(true, data.isEnable());

        final International international = ServiceLocator.getInternationStorage().get("paypalSettingsForGallery", Locale.US);
        Assert.assertEquals(international.get("payPal"), data.getItemName());
    }

    @Test
    public void testCreatePaypalSettingsData_withEmptyName() {
        TestUtil.createUserAndLogin("usersEmail");
        final Site site = TestUtil.createSite();
        final Gallery gallery = TestUtil.createGallery(site);

        final PaypalSettingsForGallery paypalSettings = new PaypalSettingsForGallery();
        paypalSettings.setFormItemIdWithPrice(12345);
        paypalSettings.setFormItemIdWithProductName(3214);
        paypalSettings.setPaypalEmail("email");

        paypalSettings.setPaypalSettingsAlign(GalleryAlign.RIGHT);
        paypalSettings.setPaypalSettingsColumn(GalleryItemColumn.COLUMN_123);
        paypalSettings.setPaypalSettingsName("");
        paypalSettings.setPaypalSettingsPosition(111111);
        paypalSettings.setPaypalSettingsRow(3233);
        paypalSettings.setEnable(true);

        gallery.setPaypalSettings(paypalSettings);

        final PaypalSettingsData data = new PaypalSettingsForGalleryManager(gallery).createPaypalSettingsData();

        Assert.assertNotNull(data);
        Assert.assertEquals(12345, data.getFormItemIdWithFullPrice().intValue());
        Assert.assertEquals(3214, data.getFormItemIdWithProductName().intValue());
        Assert.assertEquals("email", data.getPaypalEmail());

        Assert.assertEquals(GalleryAlign.RIGHT, data.getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_123, data.getColumn());
        Assert.assertEquals(111111, data.getPosition());
        Assert.assertEquals(3233, data.getRow());
        Assert.assertEquals(true, data.isEnable());

        final International international = ServiceLocator.getInternationStorage().get("paypalSettingsForGallery", Locale.US);
        Assert.assertEquals(international.get("payPal"), data.getItemName());
        Assert.assertEquals("", data.getName());
    }

    @Test
    public void testCreatePaypalSettingsData_withoutSavedEmail() {
        final Site site = TestUtil.createSite();
        final Gallery gallery = TestUtil.createGallery(site);

        TestUtil.createUserAndLogin("usersEmail");
        final PaypalSettingsForGallery paypalSettings = new PaypalSettingsForGallery();
        paypalSettings.setFormItemIdWithPrice(12345);
        paypalSettings.setFormItemIdWithProductName(3214);
        paypalSettings.setPaypalEmail("");

        paypalSettings.setPaypalSettingsAlign(GalleryAlign.RIGHT);
        paypalSettings.setPaypalSettingsColumn(GalleryItemColumn.COLUMN_123);
        paypalSettings.setPaypalSettingsName("settings name");
        paypalSettings.setPaypalSettingsPosition(111111);
        paypalSettings.setPaypalSettingsRow(3233);
        paypalSettings.setEnable(true);

        gallery.setPaypalSettings(paypalSettings);

        final PaypalSettingsData data = new PaypalSettingsForGalleryManager(gallery).createPaypalSettingsData();

        Assert.assertNotNull(data);
        Assert.assertEquals(12345, data.getFormItemIdWithFullPrice().intValue());
        Assert.assertEquals(3214, data.getFormItemIdWithProductName().intValue());
        Assert.assertEquals("usersEmail", data.getPaypalEmail());

        Assert.assertEquals(GalleryAlign.RIGHT, data.getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_123, data.getColumn());
        Assert.assertEquals("settings name", data.getName());
        Assert.assertEquals(111111, data.getPosition());
        Assert.assertEquals(3233, data.getRow());
        Assert.assertEquals(true, data.isEnable());

        final International international = ServiceLocator.getInternationStorage().get("paypalSettingsForGallery", Locale.US);
        Assert.assertEquals(international.get("payPal"), data.getItemName());
    }

    @Test
    public void testCreatePaypalSettingsForGallery() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.createGroup("group", site);
        final Gallery gallery = TestUtil.createGallery(site);

        final DraftForm productsForm = TestUtil.createCustomForm(site);
        gallery.setFormId1(productsForm.getFormId());

        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);

        final PaypalSettingsData data = new PaypalSettingsData(1, 2, GalleryItemColumn.COLUMN_3, GalleryAlign.LEFT,
                "defaultItemName", "custom name", "email", 123, 456, null, null, true, registrationForm.getId());
        data.setEnable(true);

        new PaypalSettingsForGalleryManager(gallery).fillPaypalSettingsForGallery(data);
        final PaypalSettingsForGallery savedSettings = gallery.getPaypalSettings();
        Assert.assertNotNull(savedSettings);
        Assert.assertEquals("email", savedSettings.getPaypalEmail());
        Assert.assertEquals(123, savedSettings.getFormItemIdWithPrice().intValue());
        Assert.assertEquals(456, savedSettings.getFormItemIdWithProductName().intValue());

        Assert.assertEquals(1, savedSettings.getPaypalSettingsPosition());
        Assert.assertEquals(2, savedSettings.getPaypalSettingsRow());
        Assert.assertEquals(GalleryItemColumn.COLUMN_3, savedSettings.getPaypalSettingsColumn());
        Assert.assertEquals(GalleryAlign.LEFT, savedSettings.getPaypalSettingsAlign());
        Assert.assertEquals("custom name", savedSettings.getPaypalSettingsName());
        Assert.assertEquals(true, savedSettings.isShowPaypalButton());
        
        Assert.assertNotNull(savedSettings.getOrdersFormId());
    }

    @Test(expected = GalleryNotFoundException.class)
    public void testCreateWithoutGallery() {
        TestUtil.createSite();
        TestUtil.createUserAndLogin("usersEmail");

        new PaypalSettingsForGalleryManager(null);
    }

    @Test
    public void testCreateWithouPaypalSettings() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin("usersEmail");
        final Gallery gallery = TestUtil.createGallery(site);
        gallery.setPaypalSettings(null);

        final PaypalSettingsData data = new PaypalSettingsForGalleryManager(gallery).createPaypalSettingsData();
        // Asserting that we have created default settings and all goes well inside a method.
        new PaypalSettingsForGalleryManager(gallery).fillPaypalSettingsForGallery(data);
    }

}
