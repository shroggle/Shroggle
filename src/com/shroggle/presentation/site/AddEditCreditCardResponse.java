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

package com.shroggle.presentation.site;

import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.CreditCard;
import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;


@RemoteProxy
public class AddEditCreditCardResponse extends AbstractService {

    private CreditCard creditCard;

    @RemoteProperty
    private String creditCardId;

    @RemoteProperty
    private String creditCardNumber;

    @RemoteProperty
    private String innerHTML;

    @RemoteProperty
    private List<String> cardValidationErrors;

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public List<String> getCardValidationErrors() {
        return cardValidationErrors;
    }

    public void setCardValidationErrors(List<String> cardValidationErrors) {
        this.cardValidationErrors = cardValidationErrors;
    }

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}