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
package com.shroggle.logic.javien;

import org.junit.Test;
import com.shroggle.util.payment.javien.javienResponse.*;
import junit.framework.Assert;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 23.08.2009
 */
public class JavienResponseManagerTest {

    @Test
    public void testGetCorrectProductOrNull() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienProduct product = new JavienProduct();
        product.setPrice(10.0);
        product.setCode("code");
        javienResponse.setProduct(product);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        JavienProduct newJavienProduct = manager.getCorrectProductOrNull();
        Assert.assertNotNull(newJavienProduct);
        Assert.assertEquals(10.0, newJavienProduct.getPrice());
        Assert.assertEquals("code", newJavienProduct.getCode());
    }

    @Test
    public void testGetCorrectProductOrNull_withoutCode() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienProduct product = new JavienProduct();
        product.setPrice(10.0);
        product.setCode(null);
        javienResponse.setProduct(product);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        JavienProduct newJavienProduct = manager.getCorrectProductOrNull();
        Assert.assertNull(newJavienProduct);
    }

    @Test
    public void testGetCorrectProductOrNull_withoutPrice() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienProduct product = new JavienProduct();
        product.setPrice(null);
        product.setCode("code");
        javienResponse.setProduct(product);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        JavienProduct newJavienProduct = manager.getCorrectProductOrNull();
        Assert.assertNull(newJavienProduct);
    }

    @Test
    public void testGetCorrectProductOrNull_withEmptyCode() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienProduct product = new JavienProduct();
        product.setPrice(10.0);
        product.setCode("");
        javienResponse.setProduct(product);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        JavienProduct newJavienProduct = manager.getCorrectProductOrNull();
        Assert.assertNull(newJavienProduct);
    }

    @Test
    public void testGetCorrectProductsOrEmptyList() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienProductHolder javienProductHolder = javienResponse.getJavienProductHolder();
        List<JavienProduct> javienProducts = javienProductHolder.getProducts();
        Assert.assertEquals(8, javienProducts.size());

        JavienResponseManager manager = new JavienResponseManager(javienResponse);
        List<JavienProduct> products = manager.getCorrectProductsOrEmptyList();
        Assert.assertEquals(4, products.size());
        for (JavienProduct product : products) {
            Assert.assertNotNull(product.getCode());
            Assert.assertNotNull(product.getPrice());
            Assert.assertFalse(product.getCode().isEmpty());
            Assert.assertFalse(product.getPrice().isInfinite());
            Assert.assertFalse(product.getPrice().isNaN());
        }
    }

    @Test
    public void testGetSessionId() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienSession session = new JavienSession();
        session.setEnabled(true);
        session.setId("dsfasrftfgds");

        javienResponse.setSession(session);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        String sessionId = manager.getSessionId();
        Assert.assertNotNull(sessionId);
        Assert.assertEquals("dsfasrftfgds", sessionId);
    }

    @Test
    public void testGetSessionId_withEnabledFalse() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienSession session = new JavienSession();
        session.setEnabled(false);
        session.setId("dsfasrftfgds");

        javienResponse.setSession(session);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        String sessionId = manager.getSessionId();
        Assert.assertNull(sessionId);
    }

    @Test
    public void testGetSessionId_withoutEnabled() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienSession session = new JavienSession();
        session.setEnabled(null);
        session.setId("dsfasrftfgds");

        javienResponse.setSession(session);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        String sessionId = manager.getSessionId();
        Assert.assertNull(sessionId);
    }

    @Test
    public void testGetSessionId_withoutId() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienSession session = new JavienSession();
        session.setEnabled(true);
        session.setId(null);

        javienResponse.setSession(session);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        String sessionId = manager.getSessionId();
        Assert.assertNull(sessionId);
    }

    @Test
    public void testGetSessionId_withEmptyId() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienSession session = new JavienSession();
        session.setEnabled(true);
        session.setId("");

        javienResponse.setSession(session);
        JavienResponseManager manager = new JavienResponseManager(javienResponse);

        String sessionId = manager.getSessionId();
        Assert.assertNull(sessionId);
    }

    @Test
    public void testGetSessionId_withoutResponse() {
        JavienResponseManager manager = new JavienResponseManager(null);

        String sessionId = manager.getSessionId();
        Assert.assertNull(sessionId);
    }

    @Test
    public void testGetCorrectProductOrNull_withoutResponse() {
        JavienResponseManager manager = new JavienResponseManager(null);

        JavienProduct newJavienProduct = manager.getCorrectProductOrNull();
        Assert.assertNull(newJavienProduct);
    }

    @Test
    public void testGetCorrectProductsOrEmptyList_withoutResponse() {
        JavienResponseManager manager = new JavienResponseManager(null);

        List<JavienProduct> products = manager.getCorrectProductsOrEmptyList();
        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }

    private final String responseText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Response Method=\"Merchant.selectProductList\">\n" +
            "  <Session>\n" +
            "    <Id>sxxx3b6233fe75ac4417bed560425c69f6f8</Id>\n" +
            "  </Session>\n" +

            "  <ProductList>\n" +
            "    <Product>\n" +
            "      <Id>pxxx77bba6992bc74c42bcc02cfee4444f4f</Id>\n" +
            "      <Code></Code>\n" + //wrong code
            "      <Label><![CDATA[Web-Deva 1gb Billing]]></Label>\n" +
            "      <Description><![CDATA[Web-Deva 1gb Billing]]></Description>\n" +
            "      <Price>25.00</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "    <Product>\n" +
            "      <Id>pxxx20273349ba584bed99fd856e7721b9a8</Id>\n" +
            "      <Code>shroggle250mbBilling</Code>\n" +
            "      <Label><![CDATA[Web-Deva 250 mb Billing]]></Label>\n" +
            "      <Description><![CDATA[Web-Deva 250 mb Billing]]></Description>\n" +
            "      <Price></Price>\n" + //wrong price
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "    <Product>\n" +
            "      <Id>pxxx7b15ace0ac414733b885012efd1e22b5</Id>\n" +
            "      <Code>shroggle3gbBilling</Code>\n" +
            "      <Label><![CDATA[Web-Deva 3gb Billing]]></Label>\n" +
            "      <Description><![CDATA[Web-Deva 3gb Billing]]></Description>\n" +
            "      <Price>45.00</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "    <Product>\n" +
            "      <Id>pxxx438b1a9a37e349d18509bdb0208a5901</Id>\n" +
            "      <Code>shroggle500mbBilling</Code>\n" +
            "      <Label><![CDATA[Web-Deva 500mb Billing]]></Label>\n" +
            "      <Description><![CDATA[Web-Deva 500mb Billing]]></Description>\n" +
            "      <Price>15.00</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "    <Product>\n" +
            "      <Id>pxxx2fb7477b13f7442b8d2762366fdd3286</Id>\n" +
            "      <Code>siteBuilderAnnualBilling</Code>\n" +
            "      <Label><![CDATA[Web-Deva Annual Billing]]></Label>\n" +
            "      <Description><![CDATA[Web-Deva Annual Billing]]></Description>\n" +
            "      <Price>300.00</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "    <Product>\n" +
            "      <Id>pxxxbd90a3d5b07e404e8b296af992c19672</Id>\n" +
            "      <Code>siteBuilderMonthlyBilling</Code>\n" +
            "      <Label><![CDATA[Web-Deva Monthly Billing]]></Label>\n" +
            "      <Description><![CDATA[Web-Deva Monthly Billing]]></Description>\n" +
            "      <Price>29.99</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "    <Product>\n" + //product without code
            "      <Id>pxxx9f07b6bf52ec4a3d80c3a16876aca2ce</Id>\n" +
            "      <Label><![CDATA[Test Subscription]]></Label>\n" +
            "      <Description/>\n" +
            "      <Price>3.00</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "      <ProductSubscription>\n" +
            "        <Id>psxx3335a248952a4c08a4559cb5ea43b9e1</Id>\n" +
            "        <PeriodType>Days</PeriodType>\n" +
            "        <PeriodLength>1</PeriodLength>\n" +
            "        <AmountOfAvailablePeriods>1</AmountOfAvailablePeriods>\n" +
            "        <RecurringPrice>4.00</RecurringPrice>\n" +
            "      </ProductSubscription>\n" +
            "    </Product>\n" +
            "    <Product>\n" +   //product without price
            "      <Id>pxxx6c889376eb0b487f8b028f29908e9789</Id>\n" +
            "      <Code>test</Code>\n" +
            "      <Label><![CDATA[Test Label]]></Label>\n" +
            "      <Description/>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "  </ProductList>\n" +

            "  <Product>\n" +
            "    <Id>pxxx77bba6992bc74c42bcc02cfee4444f4f</Id>\n" +
            "    <Code>shroggle1gbBilling</Code>\n" +
            "    <Label><![CDATA[Web-Deva 1gb Billing]]></Label>\n" +
            "    <Description><![CDATA[Web-Deva 1gb Billing]]></Description>\n" +
            "    <Price>25.00</Price>\n" +
            "    <Data><![CDATA[<Data/>]]></Data>\n" +
            "  </Product>\n" +
            "</Response>";
}
