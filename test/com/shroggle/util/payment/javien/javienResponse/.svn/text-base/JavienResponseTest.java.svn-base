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
package com.shroggle.util.payment.javien.javienResponse;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 23.08.2009
 */
public class JavienResponseTest {

    @Test
    public void createInstance() {
        JavienResponse javienResponse = JavienResponse.newInstance(responseText);
        Assert.assertNotNull(javienResponse);

        JavienProductHolder javienProductHolder = javienResponse.getJavienProductHolder();
        List<JavienProduct> javienProducts = javienProductHolder.getProducts();
        Assert.assertEquals(javienProducts.size(), 8);
        Assert.assertEquals(javienProducts.get(0).getPrice(), 25.00);
        Assert.assertEquals(javienProducts.get(0).getCode(), "shroggle1gbBilling");
        Assert.assertEquals(javienProducts.get(0).getLabel(), "Web-Deva 1gb Billing");
        Assert.assertEquals(javienProducts.get(0).getDescription(), "Web-Deva 1gb Billing");


        Assert.assertNull(javienResponse.getProduct());
        Assert.assertNull(javienResponse.getJavienErrorHolder());
        Assert.assertNotNull(javienResponse.getSession());
        Assert.assertEquals("sxxx3b6233fe75ac4417bed560425c69f6f8", javienResponse.getSession().getId());
    }

    @Test
    public void createInstanceByWrongResponseString() {
        JavienResponse javienResponse = JavienResponse.newInstance("jh gahfgagf jhdsafhgsadhfgsadhfghj");
        Assert.assertNull(javienResponse);
    }

    @Test
    public void createInstanceByEmptyResponseString() {
        JavienResponse javienResponse = JavienResponse.newInstance("");
        Assert.assertNull(javienResponse);
    }


    @Test
    public void getProductByResponse() throws Exception {
        JavienResponse javienResponse = JavienResponse.newInstance(productText);
        Assert.assertNotNull(javienResponse);

        JavienProduct javienProduct = javienResponse.getProduct();
        Assert.assertNotNull(javienProduct);
        Assert.assertEquals(javienProduct.getPrice(), 25.00);
        Assert.assertEquals(javienProduct.getCode(), "shroggle1gbBilling");
        Assert.assertEquals(javienProduct.getLabel(), "Web-Deva 1gb Billing");
        Assert.assertEquals(javienProduct.getDescription(), "Web-Deva 1gb Billing");
    }

    @Test
    public void getSessionIdByResponse() throws Exception {
        JavienResponse javienResponse = JavienResponse.newInstance(sessionTextEnabledTrue);
        Assert.assertNotNull(javienResponse);

        JavienSession javienSession = javienResponse.getSession();
        Assert.assertNotNull(javienSession.getId());
        Assert.assertEquals(javienSession.getId(), "sxxxfeec0e3a85a94ed1afbf28bacd3b61b0");
    }


    @Test
    public void getErrors() throws Exception {
        JavienResponse javienResponse = JavienResponse.newInstance(errorsResponse);
        Assert.assertNotNull(javienResponse);

        JavienErrorHolder javienErrorHolder = javienResponse.getJavienErrorHolder();
        Assert.assertNotNull(javienErrorHolder);
        Assert.assertNotNull(javienErrorHolder.getErrors());
        Assert.assertEquals(2, javienErrorHolder.getErrors().size());
        Assert.assertEquals("30001", javienErrorHolder.getErrors().get(0).getCode());
        Assert.assertEquals("Transaction charge error.", javienErrorHolder.getErrors().get(0).getMessage());
        Assert.assertEquals("20001", javienErrorHolder.getErrors().get(1).getCode());
        Assert.assertEquals("The transaction has been rejected by payment provider: [This transaction cannot be processed.].", javienErrorHolder.getErrors().get(1).getMessage());
    }

    private String errorsResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<response>\n" +
            "  <Errors>\n" +
            "    <errorList>\n" +
            "      <Error>\n" +
            "        <code>30001</code>\n" +
            "        <message>Transaction charge error.</message>\n" +
            "      </Error>\n" +
            "      <Error>\n" +
            "        <code>20001</code>\n" +
            "        <message>The transaction has been rejected by payment provider: [This transaction cannot be processed.].</message>\n" +
            "        <thirdPartyError>\n" +
            "          <originalResponse>This transaction cannot be processed.</originalResponse>\n" +
            "        </thirdPartyError>\n" +
            "      </Error>\n" +
            "    </errorList>\n" +
            "  </Errors>\n" +
            "</response>";

    private final String sessionTextEnabledTrue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<response>\n" +
            "  <value>\n" +
            "    <Session>\n" +
            "      <id>sxxxfeec0e3a85a94ed1afbf28bacd3b61b0</id>\n" +
            "      <created>2009-08-23T08:02:46</created>\n" +
            "      <modified>2009-08-23T08:02:46</modified>\n" +
            "      <enabled>true</enabled>\n" +
            "    </Session>\n" +
            "  </value>\n" +
            "</response>";


    private String productText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Response Method=\"Product.get\">\n" +
            "  <Session>\n" +
            "    <Id>sxxxf35845b09a7c481ab3ea365d4a79a5ee</Id>\n" +
            "  </Session>\n" +
            "  <Product>\n" +
            "    <Id>pxxx77bba6992bc74c42bcc02cfee4444f4f</Id>\n" +
            "    <Code>shroggle1gbBilling</Code>\n" +
            "    <Label><![CDATA[Web-Deva 1gb Billing]]></Label>\n" +
            "    <Description><![CDATA[Web-Deva 1gb Billing]]></Description>\n" +
            "    <Price>25.00</Price>\n" +
            "    <Data><![CDATA[<Data/>]]></Data>\n" +
            "  </Product>\n" +
            "</Response>";


    private final String responseText = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Response Method=\"Merchant.selectProductList\">\n" +
            "  <Session>\n" +
            "    <Id>sxxx3b6233fe75ac4417bed560425c69f6f8</Id>\n" +
            "  </Session>\n" +
            "  <ProductList>\n" +
            "    <Product>\n" +
            "      <Id>pxxx77bba6992bc74c42bcc02cfee4444f4f</Id>\n" +
            "      <Code>shroggle1gbBilling</Code>\n" +
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
            "      <Price>10.00</Price>\n" +
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
            "    <Product>\n" +
            "      <Id>pxxx9f07b6bf52ec4a3d80c3a16876aca2ce</Id>\n" +
            "      <Code>subscription</Code>\n" +
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
            "    <Product>\n" +
            "      <Id>pxxx6c889376eb0b487f8b028f29908e9789</Id>\n" +
            "      <Code>test</Code>\n" +
            "      <Label><![CDATA[Test Label]]></Label>\n" +
            "      <Description/>\n" +
            "      <Price>7.00</Price>\n" +
            "      <Data><![CDATA[<Data/>]]></Data>\n" +
            "    </Product>\n" +
            "  </ProductList>\n" +
            "</Response>";
}