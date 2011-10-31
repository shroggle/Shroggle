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
package com.shroggle.logic.site.taxRates;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class TaxManagerTest {

    private TaxManager taxManager = new TaxManager();

    @Test
    public void calculateNormalData(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(1.0, response.getTax(), 0);
        Assert.assertEquals("(plus tax: $<span class='itemTax'>1.0</span>)", response.getTaxString());
    }

    @Test
    public void calculateWithoutSiteId(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, null);

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithFormWithoutTaxField(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithMalformedTaxField(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue("malformed");
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithTaxRatesItemWithoutRates(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithoutLoginedUser(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithLoginedUserWithoutStateField(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }
    
    @Test
    public void calculateWithUserNotFromCali(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("AR");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }
    
    @Test
    public void calculateWithUserWithMalformedState(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("not_existing_state");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithNotIncludedCali(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, false);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("", response.getTaxString());
    }

    @Test
    public void calculateWithNoTaxInCali(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(0.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, null, site.getSiteId());

        Assert.assertEquals(0.0, response.getTax(), 0);
        Assert.assertEquals("(plus tax: $<span class='itemTax'>0.0</span>)", response.getTaxString());
    }

    @Test
    public void calculateNormalDataWithQty(){
        final Site site = TestUtil.createSite();

        // Create tax rates.
        final DraftTaxRatesUS taxRateItem = TestUtil.createTaxRatesUS(site);
        final DraftTaxRateUS caliTaxRate = TestUtil.createTaxRateUS(States_US.CA, true);
        caliTaxRate.setTaxRate(10.0);
        taxRateItem.addTaxRate(caliTaxRate);

        // Create product form.
        final DraftForm productForm = TestUtil.createCustomForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE));

        // Create product.
        final FilledForm filledForm = TestUtil.createFilledForm(productForm);
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.PRODUCT_NAME, FormItemName.PRICE,
                FormItemName.PRODUCT_TAX_RATE);
        filledFormItems.get(0).setValue("product1");
        filledFormItems.get(1).setValue("10.00");
        filledFormItems.get(2).setValue(taxRateItem.getId());
        filledForm.setFilledFormItems(filledFormItems);

        // Create user.
        final User userFromCali = TestUtil.createUser();

        // Create user filled registration form
        final DraftForm registrationForm = TestUtil.createRegistrationForm(site);
        productForm.setFormItems(TestUtil.createFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE));
        final FilledForm filledRegistrationForm = TestUtil.createFilledForm(registrationForm);
        final List<FilledFormItem> filledRegistrationFormItems = TestUtil.createFilledFormItems(FormItemName.REGISTRATION_EMAIL, FormItemName.STATE);
        filledRegistrationFormItems.get(0).setValue("cali@cali.cali");
        filledRegistrationFormItems.get(1).setValue("CA");
        filledRegistrationForm.setFilledFormItems(filledRegistrationFormItems);

        //Create user on site right
        final UserOnSiteRight userOnSiteRight =
                TestUtil.createUserOnSiteRightActive(userFromCali, site, SiteAccessLevel.VISITOR);
        userOnSiteRight.addFilledRegistrationFormId(filledRegistrationForm.getFilledFormId());

        TestUtil.loginUser(userFromCali);

        final TaxManager.CalculateTaxResponse response =
                taxManager.calculateTaxForRender(filledForm, 10.00, 2, site.getSiteId());

        Assert.assertEquals(1.0, response.getTax(), 0);
        Assert.assertEquals("(plus tax: $<span class='itemTax'>2.0</span>)", response.getTaxString());
    }

}
