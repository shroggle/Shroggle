//Processing Submit button
function createVisitor(widgetId, showFromAddRecord) {
    var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error", emptyErrorClassName: "emptyHiddenError"}, "registrationErrorBlock" + widgetId);

    errors.clear();

    var hasErrors = false;
    //Checking security code only from preview
    if ($("#registrationVerificationCode" + widgetId).val() == 0) {
        errors.set("WRONG_VERIFICATION_REGISTRATION", $("#registrationErrorsEmptyCode" + widgetId).val(), errors);
        hasErrors = true;
    }

    if ($("#agreeWithTermsAndConditions" + widgetId)[0] && !$("#agreeWithTermsAndConditions" + widgetId).attr("checked")) {
        errors.set("AGREE_WITH_TERMS_AND_CONDITIONS", $("#agreeWithTermsWithConditionsException" + widgetId).val(), errors);
        hasErrors = true;
    }

    if (hasErrors) {
        return;
    }

    //Composing request
    var createVisitorRequest = new Object();
    createVisitorRequest.verificationCode = $("#registrationVerificationCode" + widgetId).val();
    createVisitorRequest.email = $("#REGISTRATION_EMAIL" + widgetId).val();
    createVisitorRequest.password = $("#REGISTRATION_PASSWORD" + widgetId).val();
    createVisitorRequest.confirmPassword = $("#REGISTRATION_PASSWORD_RETYPE" + widgetId).val();
    createVisitorRequest.screenName = $("input[name='TEXT_INPUT_FIELD" + widgetId + "'][formItemName='REGISTRATION_UNREMOVABLE_SCREEN_NAME']").val();
    createVisitorRequest.firstName = $("input[name='TEXT_INPUT_FIELD" + widgetId + "'][formItemName='FIRST_NAME']").val();
    createVisitorRequest.lastName = $("input[name='TEXT_INPUT_FIELD" + widgetId + "'][formItemName='LAST_NAME']").val();
    createVisitorRequest.editDetails = $("#editDetails" + widgetId)[0].value == "true";
    createVisitorRequest.fillOutFormCompletely = $("#fillOutFormCompletely" + widgetId)[0].value == "true";
    createVisitorRequest.inviteForSiteId = $("#inviteForSiteId")[0].value == "null" ? null : $("#inviteForSiteId")[0].value;
    createVisitorRequest.widgetId = widgetId;
    createVisitorRequest.userId = $("#registrationUserId" + widgetId).val() == "null" ? -1 : $("#registrationUserId" + widgetId).val();
    createVisitorRequest.showFromAddRecord = showFromAddRecord;

    if ($("#totalPageBreaks" + widgetId).val() != 0) {
        createVisitorRequest.requestNextPage = true;
        createVisitorRequest.pageBreaksToPass = parseInt($("#pageBreaksToPass" + widgetId).val()) + 1;
        createVisitorRequest.filledFormId = $("#filledFormToUpdateId" + widgetId).val() ? $("#filledFormToUpdateId" + widgetId).val() : null;
    }

    //Error processing
    var requiredExceptionList = new Array();
    var wasRequiredException = collectFilledFormItems(createVisitorRequest, widgetId, requiredExceptionList);

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
            errors.exceptionAction({errorId:"VISITOR_WITH_NOT_UNIQUE_LOGIN", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }, alternativeMessage:"The email address you entered is already registered with the site builder. If this email is correct [<a href=\"javascript:returnToLoginLink(" + widgetId + ", '" + $("#REGISTRATION_EMAIL" + widgetId).val() + "')\">Click here to login</a>], if not, please enter a different email."}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.NotValidVisitorEmailException",
            errors.exceptionAction({errorId:"NOT_VALID_VISITOR_EMAIL", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.WrongVerificationCodeException",
            errors.exceptionAction({errorId:"WRONG_VERIFICATION_CODE_EXCEPTION", onException:function() {
                relaodNoBot("noBotImage" + widgetId);
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ExpiredVisitorAttemptToRegisterException",
            errors.exceptionAction({errorId:"ExpiredVisitorAttemptToRegisterException", onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));

    serviceCall.executeViaDwr("CreateVisitorService", "execute", createVisitorRequest, function(response) {
        response.widgetId = widgetId;
        response.showFromAddRecord = showFromAddRecord;
        registrationResponses.push(response);
        uploadersStarter.startFileUploading(widgetId, response.filledFormId, registrationOnUploadFunction);
    });
}

function showRegistrationPreviewTermsAndConditions() {
    var termsAndConditions = window.parent.createConfigureWindow({width:600});
    termsAndConditions.setContent($("#termsAndConditionsDiv").html());
}

var registrationResponses = new Array();

function registrationOnUploadFunction(widgetId) {
    var response = getResponse(registrationResponses, widgetId);
    registrationResponses.splice(getResponseIndex(registrationResponses, widgetId), 1);
    swfUploaderRemoveBackgroundLoadingMessage();
    updateRegistrationFormOnPage(widgetId, response.showFromAddRecord, response.nextPageHtml, response.filledFormId,
            response.code, response.redirectURL);
}

function updateRegistrationFormOnPage(widgetId, showFormAddRecord, nextPageHtml, filledFormId,
                                      responseCode, resonseRedirectUrl) {
    if (showFormAddRecord) {
        if (nextPageHtml) {
            window.parent.document.getElementById("addNewRecord").src = nextPageHtml;
        } else {
            window.parent.reloadManageRegistrantsTable();
            window.parent.closeConfigureWidgetDiv();
        }
    } else {
        if (nextPageHtml) {
            $("#registrationBlock" + widgetId).replaceWith(nextPageHtml);
        } else {
            if (responseCode == "SuccessfullyRegistered") {
                window.location.reload(true);
            } else if (responseCode == "SuccessfullyEdited") {
                if (!getRequestParameter("showAfterEditingMessage")) {
                    window.location = window.location + "&showAfterEditingMessage=true";
                } else {
                    window.location.reload(true);
                }
            } else if (responseCode == "InvitedRegistered") {
                hideFormsLoadingDiv(widgetId);
                $("#submit" + widgetId)[0].disabled = true;
                $("#reset" + widgetId)[0].disabled = true;
                $("#registrationErrorBlock" + widgetId)[0].className = "";
                $("#registrationErrorBlock" + widgetId)[0].innerHTML =
                        ("<span style='color:green'>" + $("#invitedRegisteredText" + widgetId).val()
                                + "<a href=\"" + resonseRedirectUrl + "\">" + $("#invitedSiteName" + widgetId).val() + "</a></span>");
            }
        }
    }

}

function unsubscribeVisitor(visitorId, siteId, siteName) {
    if (confirm("You are about to delete all of your data from the records of " + siteName + ". Press OK to continue.")) {
        new ServiceCall().executeViaDwr("DeleteVisitorService", "execute", [visitorId], siteId, function() {
            logout();
        });
    }
}

function editVisitorDetails(widgetId) {
    var registrationBlock = document.getElementById("registrationBlock" + widgetId);
    new ServiceCall().executeViaDwr("ShowRegistrationService", "executeWithEditVisitorDetails", widgetId, function(data) {
        registrationBlock.innerHTML = data;
        //showUploaders();
    });
}

//Processing return to login link
function returnToLoginLink(widgetId, visitorEmail) {
    var registrationBlock = document.getElementById("registrationBlock" + widgetId);
    new ServiceCall().executeViaDwr("ShowVisitorLoginService", "execute", widgetId, false, function(data) {
        registrationBlock.innerHTML = data;
        if (visitorEmail) {
            if (document.getElementById("login" + widgetId)) {
                document.getElementById("login" + widgetId).value = visitorEmail;
            }
        }

        bindLoginFormSubmitEvent(widgetId);
    });
}

//Processing return to login link
function showLoginLink(widgetId) {
    new ServiceCall().executeViaDwr("ShowVisitorLoginService", "executeForRegistration", widgetId, function(data) {
        $("#registrationBlock" + widgetId).html(data);
        bindLoginFormSubmitEvent(widgetId);
    });
}

function showPreviousBlockForRegistration(widgetId) {
    var loginBlock = $("#registrationBlock" + widgetId);
    loginBlock.prev().show();
    loginBlock.remove();
}
