function updateSitePaymentInfo() {
    var request = new Object();
    request.siteId = document.getElementById("selectedSite").value;
    request.cardId = document.getElementById("creditCardSelect").value;
    if (checkTermsAndConditions() && checkCardId(request.cardId) && checkSiteId(request.siteId)) {
        $("input[name='chargeType']").each(function() {
            if (this.checked) {
                request.chargeType = this.value;
            }
        });

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);

        payment.ON_EXCEPTION = removeShrogglePaymentLoadingImage;
        serviceCall.addExceptionHandler(
                [payment.PAYPAL_PAYMENT_EXCEPTION, payment.JAVIEN_PAYMENT_EXCEPTION],
                payment.PAYMENT_EXCEPTION_ACTION);

        createShrogglePaymentLoadingMessage($("#cardProcessing").val());
        serviceCall.executeViaDwr("AddEditCreditCardService", "updateSitePaymentInfo", request, function(response) {
            removeShrogglePaymentLoadingImage();
            $("#updatePaymentInfoDiv").html("<a href=\"/account/updatePaymentInfo.action\">" + $("#siteActivation").val() + "</a>");
            $("#pageContent").hide();
            $("#noAccess").html(response);
            $("#noAccess").show();
        });
    }
}

function returnToSiteEditPage(siteId) {
    if ($('#publishAllPages').attr('checked')) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("CopyPageVersionService", "execute", null, "WORK", siteId, function () {
            window.location = '/site/siteEditPage.action?siteId=' + siteId;
        });
    } else {
        window.location = '/site/siteEditPage.action?siteId=' + siteId;
    }
}

function createShrogglePaymentLoadingMessage(message) {
    createBackground({backgroundOpacity:0.3});
    createLoadingMessage({text:message, color:"darkgreen"});
}

function removeShrogglePaymentLoadingImage() {
    removeLoadingMessage();
    removeBackground();
}

function goToPaypal() {
    if (!checkTermsAndConditions()) {
        return;
    }
    var siteId = $("#selectedSite")[0][$("#selectedSite")[0].selectedIndex].id;
    if (checkSiteId(siteId)) {
        var request = new Object();
        request.ownerId = siteId;
        //request.childSiteSettingsId = $("#childSiteSettingsId").val();
        $("input[name='chargeType']").each(function() {
            if (this.checked) {
                request.chargeType = this.value;
            }
        });
        request.redirectToUrl = "/account/updatePaymentInfo.action?showTransaction=true";

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);

        payment.ON_EXCEPTION = removeShrogglePaymentLoadingImage;
        serviceCall.addExceptionHandler(
                [payment.PAYPAL_PAYMENT_EXCEPTION, payment.JAVIEN_PAYMENT_EXCEPTION],
                payment.PAYMENT_EXCEPTION_ACTION);

        createShrogglePaymentLoadingMessage($("#redirectingToPaypal").val());
        serviceCall.executeViaDwr("PaypalPaymentService", "goToPaypal", request, function(response) {
            removeShrogglePaymentLoadingImage();
            if (response.activatedWithOneTimeFee) {
                alert($("#profileActiveWithOneTimeFee")[0].value);
                return;
            }
            if (response.activeProfile) {
                if (confirm($("#profileActive")[0].value)) {
                    window.location = response.paypalLink;
                }
            } else if (response.javienActivated) {
                if (confirm($("#javienActive")[0].value)) {
                    window.location = response.paypalLink;
                }
            } else {
                window.location = response.paypalLink;
            }
        });
    }
}

function checkCardId(cardId) {
    var errors = new Errors();
    if (!cardId || cardId == "-1" || cardId == "") {
        errors.set("CHECK_CARD_ID", $("#selectCreditCard").val());
        return false;
    } else {
        return true;
    }
}

function checkSiteId(siteId) {
    var errors = new Errors();
    if (!siteId || siteId == "-1" || siteId == "") {
        errors.set("CHECK_SITE_ID", $("#selectSite").val());
        alert($("#selectSite").val());
        return false;
    } else {
        return true;
    }
}

function checkTermsAndConditions() {
    var errors = new Errors();
    if (!document.getElementById("iAgreeWithTermsAndConditionsCheckboxCC").checked) {
        errors.set("CHECK_TERMS_AND_CONDITIONS", $("#termsAndConditions").val());
        return false;
    } else {
        return true;
    }
}

function payPalSelected() {
    document.getElementById("PayPal").checked = "true";
    document.getElementById("updatePaymentInfoButtons").style.display = "none";
    document.getElementById("updatePaymentInfoPaypalButton").style.display = "";
    document.getElementById("creditCardSelectBlock").style.display = "none";
}

function creditCardSelected() {
    document.getElementById("CC").checked = "true";
    document.getElementById("updatePaymentInfoButtons").style.display = "";
    document.getElementById("updatePaymentInfoPaypalButton").style.display = "none";
    document.getElementById("creditCardSelectBlock").style.display = "";
}

function reactivateSitePaymentInfo() {
    var siteId = document.getElementById("selectedSite").value;
    if (checkSiteId(siteId)) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("AddEditCreditCardService", "reactivateSite", siteId, function(response) {
            document.getElementById("updatePaymentInfoDiv").innerHTML =
                    "<a href=\"/account/updatePaymentInfo.action\">" + $("#siteActivation").val() + "</a>";
            document.getElementById("pageContent").style.display = "none";
            document.getElementById("noAccess").innerHTML = response;
            document.getElementById("noAccess").style.display = "block";
        });
    }
}

function cantBePublishedMessage(startDate) {
    alert($("#cantBePublishedUntil").val() + ' ' + startDate);
    return false;
}

function siteChanged(siteId) {
    var divToUpdate = $("#updatePaymentInfoInternal");
    createLoadingArea({text: "Loading, please wait...", color:"green", guaranteeVisibility:true});

    var errors = new Errors();
    errors.clear();
    new ServiceCall().executeViaDwr("UpdatePaymentInfoService", "execute", siteId, function (response) {
        divToUpdate.html(response);
        removeLoadingArea();
    });
}


function checkPaymentForm() {
    var iAgree = document.getElementById("iAgreeWithTermsAndConditionsCheckboxCC");
    var errors = new Errors();
    errors.clear();
    if (!iAgree.checked) {
        errors.set("CHECK_TERMS_AND_CONDITIONS", $("#checkIAgree").val());
        return false;
    }
    errors.clear();
    return true;
}