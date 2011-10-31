function paymentBlockExist(widgetId) {
    return document.getElementById("paymentBlock" + widgetId);
}

function isPayPalChosen(widgetId) {
    return document.getElementById("payPal" + widgetId).checked;
}

function goToPaypalForForm(widgetId, pageBreakIndex, filledFormToUpdateId, paymentRequired) {
    var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error"}, "childSiteRegistrationErrorBlock" + widgetId);
    var request = new Object();
    request.ownerId = document.getElementById("settingsId" + widgetId).value;
    request.redirectToUrl = window.location.href;
    request.chargeType = document.getElementById("chargeType" + widgetId).value;
    request.pageBreakIndex = pageBreakIndex;
    request.filledFormToUpdateId = filledFormToUpdateId;
    request.paymentRequired = paymentRequired;
    request.widgetId = widgetId;
    request.totalPageBreaks = $("#childSiteTotalPageBreaks" + widgetId).val();
    request.childSiteUserId = $("#childSiteUserId" + widgetId).val();
    request.settingsId = $("#settingsId" + widgetId).val();

    var serviceCall = new ServiceCall();

    payment.ON_EXCEPTION = removeChildSiteRegistrationLoadingImage;
    payment.PAYMENT_EXCEPTION_ERROR_FIELD = errors;
    serviceCall.addExceptionHandler(
            [payment.PAYPAL_PAYMENT_EXCEPTION, payment.JAVIEN_PAYMENT_EXCEPTION],
            payment.PAYMENT_EXCEPTION_ACTION);
    createChildSiteRegistrationLoadingMessage($("#redirectingToPaypal").val());
    serviceCall.executeViaDwr("PaypalPaymentService", "goToPaypalCSR", request, function(response) {
        removeChildSiteRegistrationLoadingImage();
        window.location = response;
    });
}

function payPalSelectedCSR(widgetId) {
    document.getElementById("creditCardOptions" + widgetId).style.display = "none";
}

function creditCardSelectedCSR(widgetId) {
    document.getElementById("creditCardOptions" + widgetId).style.display = "block";
}

function paymentIAgreeCheckdoxChecked(widgetId) {
    return document.getElementById("iAgreeWithTermsAndConditionsCheckboxCSR" + widgetId).checked;
}

function createChildSiteRegistrationLoadingMessage(message) {
    createBackground();
    createLoadingMessage({text:message, color:"darkgreen"});
}

function removeChildSiteRegistrationLoadingImage() {
    removeLoadingMessage();
    removeBackground();
}

function isPaymentRequired(widgetId) {
    return document.getElementById("paymentRequired" + widgetId).value == "true";
}

function isPaymentComplete(widgetId) {
    return document.getElementById("childSiteRegistrationPaymentComplete" + widgetId).value == "true";
}

function setPaymentComplete(widgetId, value) {
    document.getElementById("childSiteRegistrationPaymentComplete" + widgetId).value = value;
}

function showPaymentTermsConditions(widgetId) {
    var configureWindow = createConfigureWindow({
        width:500,
        height:400,
        resizable:false,
        closable:false,
        draggable:false
    });
    configureWindow.setContent($("#agreementCSRPaymentText" + widgetId)[0].innerHTML);
}