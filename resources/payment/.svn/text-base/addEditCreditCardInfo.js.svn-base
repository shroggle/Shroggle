var addEditCreditCardErrors;

function showAddEditCreditCardWindow(updateTable, cardId) {
    if (!cardId) {
        cardId = -1;
    }
    var addCreditCardWindow = createConfigureWindow({width:550, height:470});
    new ServiceCall().executeViaDwr("AddEditCreditCardService", "showCreditCardWindow", cardId, function(response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        addCreditCardWindow.setContent(response);
        addEditCreditCardErrors = new Errors("", "addEditCreditCardErrors");
        if (updateTable) {
            document.getElementById("updateTable").value = updateTable;
        }
    });
}


function createNewOrUpdateExistingCreditCard(updateCCInfo, updatedCCId) {
    addEditCreditCardErrors.clear();
    var request = new Object();
    collectCardInfo(document.getElementById("creditCardInfoWidgetId").value, request);
    request.updateCCInfo = updateCCInfo;
    request.updatedCCId = updatedCCId;
    if (creditCardInfoContainsErrors(request, document.getElementById("creditCardInfoWidgetId").value)) {
        return;
    }
    new ServiceCall().executeViaDwr("AddEditCreditCardService", "addCreditCard", request, function(response) {
        if (response.cardValidationErrors.length > 0) {
            for (var i = 0; i < response.cardValidationErrors.length; i++) {
                addEditCreditCardErrors.set("ERRORS", response.cardValidationErrors[i]);
            }
        } else {
            if (document.getElementById("updateTable").value == "true") {
                document.getElementById("ccTable").innerHTML = response.innerHTML;
                addEditCreditCardErrors.clear();
                closeConfigureWidgetDiv();
            } else {
                var option = document.createElement("option");
                option.appendChild(document.createTextNode(response.creditCardNumber));
                option.setAttribute("value", response.creditCardId);
                option.defaultSelected = true;
                option.selected = true;
                document.getElementById("creditCardSelect").appendChild(option);
                document.getElementById("creditCardSelect").disabled = "";
                addEditCreditCardErrors.clear();
                closeConfigureWidgetDiv();
            }
        }
    });
}

function deleteCreditCard(confirmMessage, creditCardId) {
    if (confirm(confirmMessage)) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("AddEditCreditCardService", "removeCreditCard", creditCardId, function(response) {
            document.getElementById("ccTable").innerHTML = response;
        });
    }
}

function showWhatsThisForSecurityCode(widgetId, elementId) {
    var configureWindow = createConfigureWindow({
        resizable:false,
        closable:false,
        draggable:false
    });
    configureWindow.setContent($("#" + elementId + "" + widgetId)[0].innerHTML);
}

function collectCardInfo(widgetId, creditCardProperties) {
    creditCardProperties.updateCCInfo = false;
    creditCardProperties.updatedCCId = -1;
    creditCardProperties.creditCardType = document.getElementById("creditCardType" + widgetId).value;
    creditCardProperties.creditCardNumber = document.getElementById("creditCardNumber" + widgetId).value;
    creditCardProperties.creditCardExpirationMonth = parseInt(document.getElementById("creditCardExpirationMonth" + widgetId).selectedIndex);
    creditCardProperties.creditCardExpirationYear = parseInt(document.getElementById("creditCardExpirationYear" + widgetId).value);
    creditCardProperties.securityCode = document.getElementById("securityCode" + widgetId).value;
    creditCardProperties.securityCode = (creditCardProperties.securityCode && isInteger(creditCardProperties.securityCode)) ? creditCardProperties.securityCode : "";
    creditCardProperties.useInfo = document.getElementById("useInfo" + widgetId).checked;
    creditCardProperties.billingAddress1 = document.getElementById("billingAddress1" + widgetId).value;
    creditCardProperties.billingAddress2 = document.getElementById("billingAddress2" + widgetId).value;
    creditCardProperties.city = document.getElementById("city" + widgetId).value;
    creditCardProperties.country = document.getElementById("country" + widgetId).value;
    creditCardProperties.postalCode = document.getElementById("postalCode" + widgetId).value;
    creditCardProperties.region = getStateValue(widgetId);
}


function checkCreditCardInfoForForm(creditCardProperties, requiredExceptionList, widgetId) {
    var addPaymentRequiredMessage = false;
    var paymentRequired = isPaymentRequired(widgetId);
    if (!paymentIAgreeCheckdoxChecked(widgetId)) {
        addRequiredError(document.getElementById("checkIAgree" + widgetId).value, requiredExceptionList);
    }
    var creditCardNumberEmpty = creditCardProperties.creditCardNumber == "" || !isInteger(creditCardProperties.creditCardNumber);
    if (creditCardNumberEmpty) {
        addRequiredError(document.getElementById("enterCCNumber" + widgetId).value, requiredExceptionList);
    }
    /*-----------------------------------Checking credit card number length and prefix--------------------------------*/
    if (!creditCardNumberEmpty) {
        var prefix;
        var length = creditCardProperties.creditCardNumber.length;
        if (creditCardProperties.creditCardType == "Visa") {
            prefix = creditCardProperties.creditCardNumber.substring(0, 1);
            if (!((length == 16 || length == 15) && (prefix == 4))) {
                addRequiredError(document.getElementById("enterValidVisaNumber" + widgetId).value, requiredExceptionList);
            }
        } else if (creditCardProperties.creditCardType == "MasterCard") {
            prefix = creditCardProperties.creditCardNumber.substring(0, 2);
            if (!((length == 16) && (prefix >= 51 && prefix <= 55))) {
                addRequiredError(document.getElementById("enterValidMCNumber" + widgetId).value, requiredExceptionList);
            }
        }
    }
    /*-----------------------------------Checking credit card number length and prefix--------------------------------*/

    var expirationDate = new Date();
    expirationDate.setFullYear(creditCardProperties.creditCardExpirationYear, (creditCardProperties.creditCardExpirationMonth + 1), 1);
    if (expirationDate < new Date()) {
        addRequiredError(document.getElementById("wrongDate" + widgetId).value, requiredExceptionList);
    }
    if (creditCardProperties.securityCode.length < 3 || creditCardProperties.securityCode.length > 4) {
        addRequiredError(document.getElementById("wrongSecurityCode" + widgetId).value, requiredExceptionList);
    }
    if (creditCardProperties.billingAddress1 == "") {
        addRequiredError(document.getElementById("billingAddress" + widgetId).value, requiredExceptionList);
    }
    if (creditCardProperties.city == "") {
        addRequiredError(document.getElementById("enterCity" + widgetId).value, requiredExceptionList);
    }
    if (creditCardProperties.region == "") {
        addRequiredError(document.getElementById("enterRegion" + widgetId).value, requiredExceptionList);
    }
    if (creditCardProperties.postalCode == "") {
        addRequiredError(document.getElementById("enterZipPostal" + widgetId).value, requiredExceptionList);
    }
    function addRequiredError(errorText, requiredExceptionList) {
        if (errorText && requiredExceptionList && paymentRequired) {
            requiredExceptionList.push(errorText);
            addPaymentRequiredMessage = true;
        }
    }
}

function creditCardInfoContainsErrors(creditCardProperties, widgetId) {
    addEditCreditCardErrors.clear();
    var creditCardNumberEmpty = creditCardProperties.creditCardNumber == "" || !isInteger(creditCardProperties.creditCardNumber);
    if (creditCardNumberEmpty) {
        addEditCreditCardErrors.set("ENTER_CC_NUMBER",
                document.getElementById("enterCCNumber" + widgetId).value,
                [document.getElementById("creditCardNumber")]);
    }
    /*-----------------------------------Checking credit card number length and prefix--------------------------------*/
    if (!creditCardNumberEmpty) {
        var prefix;
        var length = creditCardProperties.creditCardNumber.length;
        if (creditCardProperties.creditCardType == "Visa") {
            prefix = creditCardProperties.creditCardNumber.substring(0, 1);
            if (!((length == 16 || length == 15) && (prefix == 4))) {
                addEditCreditCardErrors.set("ENTER_CC_NUMBER",
                        document.getElementById("enterValidVisaNumber" + widgetId).value,
                        [document.getElementById("creditCardNumber")]);
            }
        } else if (creditCardProperties.creditCardType == "MasterCard") {
            prefix = creditCardProperties.creditCardNumber.substring(0, 2);
            if (!((length == 16) && (prefix >= 51 && prefix <= 55))) {
                addEditCreditCardErrors.set("ENTER_CC_NUMBER",
                        document.getElementById("enterValidMCNumber" + widgetId).value,
                        [document.getElementById("creditCardNumber")]);
            }
        }
    }
    /*-----------------------------------Checking credit card number length and prefix--------------------------------*/
    var expirationDate = new Date();
    expirationDate.setFullYear(creditCardProperties.creditCardExpirationYear, (creditCardProperties.creditCardExpirationMonth + 1), 1);
    if (expirationDate < new Date()) {
        addEditCreditCardErrors.set("ENTER_VALID_DATE",
                document.getElementById("wrongDate" + widgetId).value,
                [document.getElementById("creditCardExpirationYear"),document.getElementById("creditCardExpirationMonth")]);
    }
    if (creditCardProperties.securityCode.length < 3 || creditCardProperties.securityCode.length > 4) {
        addEditCreditCardErrors.set("ENTER_SECURITY_CODE",
                document.getElementById("wrongSecurityCode" + widgetId).value,
                [document.getElementById("securityCode")]);
    }
    if (creditCardProperties.billingAddress1 == "") {
        addEditCreditCardErrors.set("ENTER_BILLING_ADRESS",
                document.getElementById("billingAddress" + widgetId).value,
                [document.getElementById("billingAddress1")]);
    }
    if (creditCardProperties.city == "") {
        addEditCreditCardErrors.set("ENTER_CITY",
                document.getElementById("enterCity" + widgetId).value,
                [document.getElementById("city")]);
    }
    if (creditCardProperties.region == "") {
        addEditCreditCardErrors.set("ENTER_REGION",
                document.getElementById("enterRegion" + widgetId).value,
                [document.getElementById("region")]);
    }
    if (!isZipCodeValid(creditCardProperties.postalCode, creditCardProperties.country)) {
        addEditCreditCardErrors.set("ENTER_POSTAL_CODE",
                document.getElementById("enterZipPostal" + widgetId).value,
                [document.getElementById("postalCode")]);
    }
    return addEditCreditCardErrors.hasErrors();
}

function checkPostCharactersByCountry(element, country, event) {
    // var country = $("#country" + $("#creditCardInfoWidgetId").val()).val();
    if (country == 'US') {
        return numbersAndOneHyphenOnly(element, event);
    } else if (country == 'CA') {
        return numbersRomanCharactersAndOneHyphenOnly(element, event);
    } else {
        return numbersOnly(element, event);
    }
}

function selectCountryForCreditCard(selectedState, widgetId, state, disable) {
    var countries = document.getElementById("country" + widgetId);
    var selectedOptionValue = countries.options[countries.selectedIndex].value;
    showStatesForCountry(selectedOptionValue, widgetId, state, disable);
}


function useUserBillingInfoForCreditCard(widgetId) {
    if (document.getElementById("useInfo" + widgetId).checked) {
        document.getElementById("billingAddress1" + widgetId).value = document.getElementById("userBillingAddress1" + widgetId).value;
        document.getElementById("billingAddress2" + widgetId).value = document.getElementById("userBillingAddress2" + widgetId).value;
        document.getElementById("city" + widgetId).value = document.getElementById("userCity" + widgetId).value;
        document.getElementById("country" + widgetId).value = document.getElementById("userCountry" + widgetId).value;
        document.getElementById("postalCode" + widgetId).value = document.getElementById("userPostalCode" + widgetId).value;
        selectCountryForCreditCard('', widgetId, document.getElementById("userRegion" + widgetId).value, true);
        disableBilingInfoFieldsForCreditCard(true, widgetId);
    } else {
        document.getElementById("billingAddress1" + widgetId).value = "";
        document.getElementById("billingAddress2" + widgetId).value = "";
        document.getElementById("city" + widgetId).value = "";
        document.getElementById("country" + widgetId).value = "USA";
        document.getElementById("postalCode" + widgetId).value = "";
        selectCountryForCreditCard('', widgetId, "", false);
        disableBilingInfoFieldsForCreditCard(false, widgetId);
    }
}


function disableBilingInfoFieldsForCreditCard(disabled, widgetId) {
    document.getElementById("billingAddress1" + widgetId).disabled = disabled;
    document.getElementById("billingAddress2" + widgetId).disabled = disabled;
    document.getElementById("city" + widgetId).disabled = disabled;
    document.getElementById("country" + widgetId).disabled = disabled;
    document.getElementById("state" + widgetId).disabled = disabled;
    document.getElementById("postalCode" + widgetId).disabled = disabled;
    document.getElementById("state" + widgetId).disabled = disabled;
}