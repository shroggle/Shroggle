//Processing Submit button
function createChildSite(widgetId, showFromAddRecord) {
    var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error",
        emptyErrorClassName: "emptyHiddenError"}, "childSiteRegistrationErrorBlock" + widgetId);
    errors.clear();

    var agreeCheckbox = $("#childSiteRegistrationIAgree" + widgetId)[0];
    var hasErrors = false;
    if (agreeCheckbox && !agreeCheckbox.checked) {
        errors.set("UserDidntAgree", " " + $("#childSiteRegistrationErrorsAgreeToTheConditions" + widgetId).val() + " ");
        hasErrors = true;
    }

    if ($("#childSiteRegistrationVerificationCode" + widgetId).val() == 0) {
        errors.set("WrongVerificationCode", " " + document.getElementById("childSiteRegistrationErrorsEmptyCode" + widgetId).value + " ");
        hasErrors = true;
    }

    if (hasErrors) {
        return;
    }

    var createChildSiteRequest = new Object();
    createChildSiteRequest.verificationCode = $("#childSiteRegistrationVerificationCode" + widgetId).val();

    createChildSiteRequest.email = $("#REGISTRATION_EMAIL" + widgetId).val();
    createChildSiteRequest.password = $("#REGISTRATION_PASSWORD" + widgetId).val();
    createChildSiteRequest.confirmPassword = $("#REGISTRATION_PASSWORD_RETYPE" + widgetId).val();
    createChildSiteRequest.widgetId = widgetId;
    createChildSiteRequest.showFromAddRecord = showFromAddRecord;
    createChildSiteRequest.editDetails = $("#editDetails" + widgetId).val();

    createChildSiteRequest.userId = $("#childSiteUserId" + widgetId).val() == "null" ? -1 : $("#childSiteUserId" + widgetId).val();
    createChildSiteRequest.settingsId = $("#childSiteSettingsId" + widgetId).val() == "null" ? -1 : $("#childSiteSettingsId" + widgetId).val();

    if ($("#totalPageBreaks" + widgetId).val() != 0) {
        createChildSiteRequest.requestNextPage = true;
        createChildSiteRequest.pageBreaksToPass = parseInt($("#pageBreaksToPass" + widgetId).val()) + 1;
    }

    createChildSiteRequest.filledFormId = $("#filledFormToUpdateId" + widgetId).val() ? $("#filledFormToUpdateId" + widgetId).val() : null;

    var paymentRequest = new Object();
    var requiredExceptionList = new Array();
    var wasRequiredException = collectFilledFormItems(createChildSiteRequest, widgetId, requiredExceptionList, paymentRequest);
    createChildSiteRequest.formId = document.getElementById("childSiteRegistrationId" + widgetId).value;

    //Checking for required exception's
    if (wasRequiredException) {
        for (var i = 0; ; i++) {
            var requiredException = requiredExceptionList[i];
            if (requiredException == undefined) {
                break;
            }
            errors.set("REQUIRED_EXCEPTION" + i, requiredException);
        }
        return;
    }

    showFormsLoadingDiv(widgetId);

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VisitorWithNullOrEmptyEmailException",
            errors.exceptionAction({errorId:"VISITOR_WITH_NULL_OR_EMPTY_LOGIN", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VisitorWithNullPasswordException",
            errors.exceptionAction({errorId:"VISITOR_WITH_NULL_PASSWORD", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VisitorWithNotEqualsPasswordAndConfirmPaswordException",
            errors.exceptionAction({errorId:"VISITOR_WITH_NOT_EQUAL_PASSWORD_AND_CONFIRM_PASSWORD", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VisitorWithNotUniqueLogin",
            errors.exceptionAction({errorId:"VISITOR_WITH_NOT_UNIQUE_LOGIN",
                alternativeMessage:document.getElementById("childSiteRegistrationErrorsEmailRegistered" + widgetId).value, onException:function() {
                    hideFormsLoadingDiv(widgetId);
                }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.WrongSubDomainNameException",
            errors.exceptionAction({errorId:"WRONG_DOMAIN_NAME",
                alternativeMessage:document.getElementById("childSiteRegistrationErrorsEnterCorrectDomainName" + widgetId).value, onException:function() {
                    hideFormsLoadingDiv(widgetId);
                }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.NotValidVisitorEmailException",
            errors.exceptionAction({errorId:"NOT_VALID_VISITOR_EMAIL", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.WrongVerificationCodeException",
            errors.exceptionAction({errorId:"WRONG_VERIFICATION_CODE",
                alternativeMessage:document.getElementById("childSiteRegistrationErrorsWrongCode" + widgetId).value,
                onException:function() {
                    hideFormsLoadingDiv(widgetId);
                    relaodNoBot("noBotImage" + widgetId);
                }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ChildSiteRegistrationNotFoundException",
            errors.exceptionAction({errorId:"CHILD_SITE_REGISTRATION_NOT_FOUND", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
        serviceCall.executeViaDwr("CreateChildSiteService", "execute", createChildSiteRequest, function(response) {
            response.widgetId = widgetId;
            response.showFromAddRecord = showFromAddRecord;
            response.pageBreaksIndex = createChildSiteRequest.pageBreaksToPass;
            if (paymentBlockExist(widgetId) && !isPaymentComplete(widgetId) && !isPayPalChosen(widgetId)) {
                //todo move this into separate function.
                /*-----------------------------------------Payment processing-----------------------------------------*/
                var paymentServiceCall = new ServiceCall();
                paymentServiceCall.addExceptionHandler(
                        LoginInAccount.EXCEPTION_CLASS,
                        LoginInAccount.EXCEPTION_ACTION);
                payment.ON_EXCEPTION = removeChildSiteRegistrationLoadingImage;
                payment.PAYMENT_EXCEPTION_ERROR_FIELD = errors;
                paymentServiceCall.addExceptionHandler(
                        [payment.PAYPAL_PAYMENT_EXCEPTION, payment.JAVIEN_PAYMENT_EXCEPTION],
                        payment.PAYMENT_EXCEPTION_ACTION);

                    paymentServiceCall.executeViaDwr("CreditCardForChildSiteService", "execute", paymentRequest, function(paymentResponse) {
                        if (paymentResponse.cardValidationErrors.length > 0 && isPaymentRequired(widgetId)) {
                            for (var i in paymentResponse.cardValidationErrors) {
                                errors.set("CARD_VALIDATION_ERRORS_" + i, paymentResponse.cardValidationErrors[i]);
                                hideFormsLoadingDiv(widgetId);
                            }
                        } else {
                            if (paymentResponse.cardValidationErrors.length > 0) {
                                errors.set("PAYMENT_SKIPPED", "Payment skipped.");
                                hideFormsLoadingDiv(widgetId);
                            }
                            $("#childSiteRegistrationPaymentDiv" + widgetId).find("input").each(function() {
                                disableControl($(this)[0]);
                            });
                            $("#childSiteRegistrationPaymentDiv" + widgetId).find("select").each(function() {
                                disableControl($(this)[0]);
                            });
                            $("#childSiteRegistrationPaymentDiv" + widgetId).find("a").each(function() {
                                disableControl($(this)[0]);
                            });
                            document.getElementById("childSiteRegistrationPaymentCompleteMessageDiv" + widgetId).style.visibility = "visible";
                            setPaymentComplete(widgetId, "true");
                            childSiteRegistrationResponses.push(response);
                            uploadersStarter.startFileUploading(widgetId, response.filledFormId, onUploadFunctionCSR);
                        }
                    });
                /*-----------------------------------------Payment processing-----------------------------------------*/
            } else {
                childSiteRegistrationResponses.push(response);
                uploadersStarter.startFileUploading(widgetId, response.filledFormId, onUploadFunctionCSR);
            }
        });
}

var childSiteRegistrationResponses = new Array();

function onUploadFunctionCSR(widgetId) {
    var response = getResponse(childSiteRegistrationResponses, widgetId);
    childSiteRegistrationResponses.splice(getResponseIndex(childSiteRegistrationResponses, widgetId), 1);
    swfUploaderRemoveBackgroundLoadingMessage();
    updateCSRFormOnPage(widgetId, response.showFromAddRecord, response.nextPageHtml, response.filledFormId, response.pageBreaksIndex);
}

function updateCSRFormOnPage(widgetId, showFromAddRecord, nextPageHtml, filledFormId, pageBreaksIndex) {
    if (showFromAddRecord) {
        if (nextPageHtml) {
            window.parent.document.getElementById("addNewRecord").src = nextPageHtml;
        } else {
            window.parent.reloadManageRegistrantsTable();
            window.parent.closeConfigureWidgetDiv();
        }
    } else {
        if (paymentBlockExist(widgetId) && isPayPalChosen(widgetId) && !isPaymentComplete(widgetId)) {
            goToPaypalForForm(widgetId, pageBreaksIndex, filledFormId, isPaymentRequired(widgetId));
        } else if (nextPageHtml) {
            $("#widget" + widgetId).replaceWith(nextPageHtml);
        } else {
            $("#childSiteRegistrationComplete" + widgetId).css("display", "block");
            $("#childSiteRegistration" + widgetId).css("display", "none");
        }
    }
}

function showChildSiteRegistrationForm(widgetId) {
    new ServiceCall().executeViaDwr("RenderChildSiteRegistrationService", "showForm", widgetId, function(response) {
        updateChildSiteRegistrationContent(widgetId, response);
    });
}

function showLoginBlockForChildSiteRegistration(widgetId) {
    new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForChildSiteRegistration", widgetId, function(response) {
        updateChildSiteRegistrationContent(widgetId, response);
        bindLoginFormSubmitEvent(widgetId);
    });
}

function childSiteRegistrationOptOut(widgetId, filledFormId) {
    if (confirm(document.getElementById("optOutConfirmation" + widgetId).value)) {
        new ServiceCall().executeViaDwr("OptOutFilledFormFromNetworkService", "execute", widgetId, filledFormId, function(response) {
            updateChildSiteRegistrationContent(widgetId, response);
        });
    }
}

function childSiteRegistrationDelete(widgetId, filledFormId) {
    if (confirm(document.getElementById("deleteConfirmation" + widgetId).value)) {
        new ServiceCall().executeViaDwr("DeleteChildSiteAndFilledFormService", "execute", widgetId, filledFormId, function(response) {
            updateChildSiteRegistrationContent(widgetId, response);
        });
    }
}

function childSiteRegistrationEdit(widgetId, filledFormId) {
    new ServiceCall().executeViaDwr("RenderChildSiteRegistrationService", "editFilledForm", widgetId, filledFormId, function(response) {
        updateChildSiteRegistrationContent(widgetId, response);
    });
}


function updateChildSiteRegistrationContentByWidgetId(widgetId) {
    updateChildSiteRegistrationContent(widgetId, document.getElementById("afterEditText" + widgetId).innerHTML);
    swfUploaderRemoveBackgroundLoadingMessage();
}

function updateChildSiteRegistrationContent(widgetId, response) {
    document.getElementById("widget" + widgetId).innerHTML = response;
}

// Showing message about registration completion on redirect from paypal.
$(function () {
    if (document.location.hash.indexOf('#showSuccessMessageLastPage') != -1) {
        var hash = document.location.hash;
        var widgetId = hash.substring('#showSuccessMessageLastPage'.length, hash.length);

        $("#childSiteRegistration" + widgetId).hide();
        $("#childSiteRegistrationComplete" + widgetId).show();
        $("#paypalPaymentComplete" + widgetId).show();

        document.location.hash = '';
    } else if (document.location.hash.indexOf('#showSuccessMessage') != -1) {
        hash = document.location.hash;
        widgetId = hash.substring('#showSuccessMessage'.length, hash.length);

        $("#paypalPaymentComplete" + widgetId).show();

        document.location.hash = '';
    }
});


function aboutOptOut() {
    var aboutOptOutWindow = createConfigureWindow({width:600, height:100, resizable: false, draggable:false});
    aboutOptOutWindow.setContent($("#aboutOptOutText").html());
}