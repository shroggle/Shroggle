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

package com.shroggle.util.payment.javien;

import com.javien.dps.shared.ws.clients.WebService;
import com.javien.dps.shared.ws.clients.WebServiceServiceLocator;
import com.shroggle.entity.*;
import com.shroggle.exception.CannotFindJavienProductException;
import com.shroggle.exception.CreditCardNotFoundException;
import com.shroggle.exception.JavienException;
import com.shroggle.logic.creditCard.CreditCardManager;
import com.shroggle.logic.javien.JavienManager;
import com.shroggle.logic.javien.JavienResponseManager;
import com.shroggle.logic.site.payment.PaymentLogRequest;
import com.shroggle.logic.site.payment.PaymentLogger;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.payment.javien.javienResponse.JavienError;
import com.shroggle.util.payment.javien.javienResponse.JavienProduct;
import com.shroggle.util.payment.javien.javienResponse.JavienResponse;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */
public class JavienReal extends Javien {

    public JavienReal() throws Exception {
        webService = new WebServiceServiceLocator().getDynamicAPI(new URL(JavienManager.getJavienUrl()));
    }

    /*
     * userId - parameter for logging. May be null. For purchasing from child site registration represents child user id
     * that was created during form filling.
     */
    void purchaseProduct(final CreditCard card, final String productName,
                         final PaymentSettingsOwner owner, final double price,
                         final PaymentReason paymentReason, final Integer userId) {
        if (card == null) {
            throw new CreditCardNotFoundException("Cant purchase product by null CreditCard");
        }

        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        final Site site = owner instanceof Site ? (Site) owner : null;
        final ChildSiteSettings childSiteSettings = owner instanceof ChildSiteSettings ? (ChildSiteSettings) owner : null;
        paymentLogRequest.setChildSiteSettingsId(childSiteSettings != null ? childSiteSettings.getId() : null);
        paymentLogRequest.setSiteId(site != null ? site.getSiteId() : null);
        paymentLogRequest.setSum("" + price);
        paymentLogRequest.setPaymentReason(paymentReason);
        paymentLogRequest.setUserId(userId == null ?
                (site != null ? site.getSitePaymentSettings().getUserId() : null) : userId);
        paymentLogRequest.setCreditCard(card);
        paymentLogRequest.setMessage("Purchasing product.");

        String consumerLogin = registerConsumer(paymentLogRequest, card.getUser());
        String consumerSessionId = addBillingInfo(consumerLogin, card, paymentLogRequest);

        // Updating product description (adding site name). Its normal for now, because we don`t have a lot of users but
        // if we will have a lot of them - few users can pay in one time and there can be wrong site name. Maybe we have
        // to add own product for each site but I don`t have information about products limitation in javien so for now
        // I am just updating products description. Tolik.
        addSiteNameToProductDescription(productName, getSiteNameOrChildSiteSettingsInfo(site, childSiteSettings), paymentLogRequest);

        purchaseProduct(consumerSessionId, card, productName, paymentLogRequest);
        logout(consumerSessionId, paymentLogRequest);
    }

    private String getSiteNameOrChildSiteSettingsInfo(final Site site, final ChildSiteSettings childSiteSettings) {
        if (site != null) {
            return site.getTitle();
        }
        if (childSiteSettings != null) {
            return "Site has not been created yet. ChildSiteSettings id = " + childSiteSettings.getChildSiteSettingsId();
        }
        return "";
    }

    void addSiteNameToProductDescription(final String productCode, final String siteName, final PaymentLogRequest paymentLogRequest) {
        final JavienProduct product = getProductByCode(productCode, paymentLogRequest);
        final String adminSessionId = loginAdministrator(paymentLogRequest);
        String newLabelAndDescription = product.getDescription().replaceAll("\\. Site name:.*", "");
        newLabelAndDescription += (". Site name: " + siteName);
        final String request =
                "<request call=\"Product.save\" response-value-version=\"1\" response-value-depth=\"1\">\n" +
                        "  <Session><Id>" + adminSessionId + "</Id></Session>\n" +
                        "  <Product>\n" +
                        "    <Code>" + productCode + "</Code>\n" +
                        "    <Label>" + newLabelAndDescription + "</Label>\n" +
                        "    <Description>" + newLabelAndDescription + "</Description>\n" +
                        "  </Product>\n" +
                        "</request>";
        final String response;
        try {
            response = webService.call(request);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to add site name to product info.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);
    }

    String getExistingProductNameByPriceOrCreateNew(final double sum) {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setSum("" + sum);

        JavienProduct javienProduct = getProductByPrice(sum, paymentLogRequest);
        if (javienProduct != null) {
            return javienProduct.getCode();
        } else {
            addProduct(sum, paymentLogRequest);
            javienProduct = getProductByPrice(sum, paymentLogRequest);
            if (javienProduct != null) {
                return javienProduct.getCode();
            } else {
                throw new CannotFindJavienProductException("Can`t add product.");
            }
        }
    }

    /*------------------------------------------------Private Methods-------------------------------------------------*/
    private JavienProduct getProductByPrice(final Double productPrice, final PaymentLogRequest paymentLogRequest) {
        return getProductByCode("siteBuilderMonthlyBilling" + String.valueOf(productPrice), paymentLogRequest);
    }

    private void addProduct(final Double productPrice, final PaymentLogRequest paymentLogRequest) {
        final String adminSessionId = loginAdministrator(paymentLogRequest);
        String request =
                "<Request method=\"Product.add\">" +
                        "  <Session><Id>" + adminSessionId + "</Id></Session>" +
                        "  <Product>" +
                        "    <Code>siteBuilderMonthlyBilling" + productPrice + "</Code>" +
                        "    <Label>Web-Deva Monthly Billing</Label>" +
                        "    <Description>Web-Deva Monthly Billing</Description>" +
                        "    <Price>" + productPrice + "</Price>" +
                        "    <Data></Data>" +
                        "  </Product>" +
                        "</Request>";

        final String response;
        try {
            response = webService.call(request);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to add product.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);
    }

    private JavienProduct getProductByCode(final String productCode, final PaymentLogRequest paymentLogRequest) {
        final String adminSessionId = loginAdministrator(paymentLogRequest);
        String request =
                "<Request method=\"Product.get\">" +
                        "  <Session><Id>" + adminSessionId + "</Id></Session>" +
                        "  <Product><Code>" + productCode + "</Code></Product>" +
                        "</Request>";
        final String response;
        try {
            response = webService.call(request);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to get product by code.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);
        return new JavienResponseManager(javienResponse).getCorrectProductOrNull();
    }

    private String loginAdministrator(final PaymentLogRequest paymentLogRequest) {
        String adminLoginRequest = "<request call=\"Administrator.login\" response-value-version=\"1\" " +
                "response-value-depth=\"1\">\n" +
                "  <Consumer>\n" +
                "    <login>" + adminLogin + "</login>\n" +
                "    <password>" + adminPassword + "</password>\n" +
                "  </Consumer>\n" +
                "  <Merchant><name>" + merchantName + "</name></Merchant>\n" +
                "</request>";
        final String response;
        try {
            response = webService.call(adminLoginRequest);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to login administrator.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }

        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);

        return new JavienResponseManager(javienResponse).getSessionId();
    }

    private String registerConsumer(final PaymentLogRequest paymentLogRequest, final User user) {
        String adminSessionId = loginAdministrator(paymentLogRequest);

        //generate pseudo-unique consumer login
        String consumerLogin = "test_" + System.currentTimeMillis();
        String request =
                "<request call=\"Consumer.save\" response-value-version=\"1\" response-value-depth=\"1\">\n" +
                        "  <Session><Id>" + adminSessionId + "</Id></Session>\n" +
                        "  <Consumer>\n" +
                        "    <Login>" + consumerLogin + "</Login>\n" +
                        "    <Password>" + consumerLogin + "</Password>\n" +
                        "    <FirstName>" + StringUtil.getEmptyOrString(user.getFirstName()) + "</FirstName>\n" +
                        "    <MiddleName></MiddleName>\n" +
                        "    <LastName>" + StringUtil.getEmptyOrString(user.getLastName()) + "</LastName>\n" +
                        "    <Email>" + StringUtil.getEmptyOrString(user.getEmail()) + "</Email>\n" +
                        "  </Consumer>\n" +
                        "</request>";
        final String response;
        try {
            response = webService.call(request);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to register customer.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);

        return consumerLogin;
    }

    private String addBillingInfo(final String consumerLogin, final CreditCard creditCard, final PaymentLogRequest paymentLogRequest) {
        final String consumerSessionId = loginConsumer(consumerLogin, paymentLogRequest);
        final String userName = creditCard.getUser() != null ? StringUtil.getEmptyOrString(creditCard.getUser().getFirstName()) + " " +
                StringUtil.getEmptyOrString(creditCard.getUser().getLastName()) : "";
        if (consumerSessionId == null) return null;
        final String request =
                "<request call=\"BillingInfo.save\" response-value-version=\"1\" response-value-depth=\"1\">\n" +
                        "  <Session><Id>" + consumerSessionId + "</Id></Session>\n" +
                        "  <BillingInfo>\n" +
                        "    <Name>" + userName + "</Name>\n" +
                        "    <Number>" + creditCard.getCreditCardNumber() + "</Number>\n" +
                        "    <PaymentResult>" + "CREDIT_CARD" + "</PaymentResult>\n" +
                        "    <CVV>" + creditCard.getSecurityCode() + "</CVV>\n" +
                        "    <ExpirationDate>" + DateUtil.getJavienDateFormat(
                        new CreditCardManager(creditCard).createRealExpirationDate()) + "</ExpirationDate>\n" +
                        "    <Address1>" + creditCard.getBillingAddress1() + "</Address1>\n" +
                        "    <Address2>" + creditCard.getBillingAddress2() + "</Address2>\n" +
                        "    <City>" + creditCard.getCity() + "</City>\n" +
                        "    <State>" + creditCard.getRegion() + "</State>\n" +
                        "    <Zip>" + creditCard.getPostalCode() + "</Zip>\n" +
                        "    <Country>" + creditCard.getCountry().toString() + "</Country>\n" +
                        "  </BillingInfo>\n" +
                        "</request>";

        final String response;
        try {
            response = webService.call(request);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to register customer.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);

        return consumerSessionId;
    }

    private String loginConsumer(final String consumerLogin, final PaymentLogRequest paymentLogRequest) {
        String consumerLoginRequest =
                "<request call=\"Consumer.login\" response-value-version=\"1\" response-value-depth=\"1\">\n" +
                        "  <Consumer>\n" +
                        "    <login>" + consumerLogin + "</login>\n" +
                        "    <password>" + consumerLogin + "</password>\n" +
                        "  </Consumer>\n" +
                        "  <Merchant><name>" + merchantName + "</name></Merchant>\n" +
                        "</request>";

        final String response;
        try {
            response = webService.call(consumerLoginRequest);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to login consumer.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);

        return new JavienResponseManager(javienResponse).getSessionId();
    }

    private void purchaseProduct(String consumerSessionId, final CreditCard creditCard, final String productName,
                                 final PaymentLogRequest paymentLogRequest) {
        JavienResponse javienResponse;
        String shoppingCartRequest = "<request call=\"ShoppingCart.add\" response-value-version=\"1\" " +
                "response-value-depth=\"1\">\n" +
                "  <Session><Id>" + consumerSessionId + "</Id></Session>\n" +
                "  <Product><code>" + productName + "</code></Product>\n" +
                "</request>";
        final String shoppingCartResponse;
        try {
            shoppingCartResponse = webService.call(shoppingCartRequest);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to purchase product.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        javienResponse = JavienResponse.newInstance(shoppingCartResponse);
        handleJavienErrors(javienResponse, paymentLogRequest);

        String purchaseProductRequest = "<request call=\"Consumer.buy\" response-value-version=\"1\" " +
                "response-value-depth=\"1\">\n" +
                "  <Session><Id>" + consumerSessionId + "</Id></Session>\n" +
                "  <BillingInfo>\n" +
                "    <CVV>" + creditCard.getSecurityCode() + "</CVV>\n" +
                "    <IP>" + "192.168.1.1" + "</IP>\n" +
                "  </BillingInfo>\n" +
                "</request>";

        final String purchaseProductResponse;
        try {
            purchaseProductResponse = webService.call(purchaseProductRequest);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to purchase product.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        javienResponse = JavienResponse.newInstance(purchaseProductResponse);
        handleJavienErrors(javienResponse, paymentLogRequest);

        paymentLogger.logJavienTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
    }

    private void logout(final String sessionId, final PaymentLogRequest paymentLogRequest) {
        String request =
                "<request call=\"Session.logout\" response-value-version=\"1\" response-value-depth=\"1\">\n" +
                        "  <Session><Id>" + sessionId + "</Id></Session>\n" +
                        "</request>";
        final String response;
        try {
            response = webService.call(request);
        } catch (RemoteException e) {
            paymentLogRequest.setErrorMessage("Failed to logout.");
            paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new JavienException(javienInternational.get("exception"), e);
        }
        JavienResponse javienResponse = JavienResponse.newInstance(response);
        handleJavienErrors(javienResponse, paymentLogRequest);
    }

    private void handleJavienErrors(final JavienResponse response, final PaymentLogRequest paymentLogRequest) {
        if (response != null && response.getJavienErrorHolder() != null &&
                response.getJavienErrorHolder().getErrors() != null) {
            String message = "";
            for (JavienError error : response.getJavienErrorHolder().getErrors()) {
                message += "\nError code: " + error.getCode() + ", Error message: " + error.getMessage();
            }
            if (paymentLogRequest != null) {
                paymentLogRequest.setErrorMessage(message);
                paymentLogger.logJavienTransaction(TransactionStatus.FAILED, paymentLogRequest);
            } else {
                logger.log(Level.SEVERE, "Javien exception! Cause: " + message);
            }
            throw new JavienException(javienInternational.get("exception"));
        }
    }

    private final String merchantName = JavienManager.getMerchantName();// PUT YOUR MERCHANT NAME HERE
    private final String adminLogin = JavienManager.getAdminLogin(); // PUT YOUR WS ADMIN LOGIN HERE
    private final String adminPassword = JavienManager.getAdminPassword(); // PUT YOUR WS ADMIN PASSWORD HERE

    private final WebService webService;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final PaymentLogger paymentLogger = new PaymentLogger();
    private final International javienInternational = ServiceLocator.getInternationStorage().get("javien", Locale.US);
}