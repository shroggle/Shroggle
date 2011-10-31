var errors;
function sendMessage(widgetId, showFromAddRecord) {
    var request = new Object();
    var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error", emptyErrorClassName: "emptyHiddenError"}, "contactUsErrorBlock" + widgetId);
    errors.clear();

    if ($("#verificationCode" + widgetId).val() == 0) {
        errors.set("WRONG_VERIFICATION", $("#emptyCode" + widgetId).val(), [errors]);
        return;
    }

    request.widgetId = widgetId;
    request.verificationCode = $("#verificationCode" + widgetId).val();
    request.formId = $("#contactUsFormId" + widgetId).val();
    request.showFromAddRecord = showFromAddRecord;
    request.siteShowOption = $("#siteShowOption" + widgetId).val();

    if ($("#totalPageBreaks" + widgetId).val() != 0) {
        request.requestNextPage = true;
        request.pageBreaksToPass = parseInt($("#pageBreaksToPass" + widgetId).val()) + 1;
        request.filledFormId = $("#filledFormToUpdateId" + widgetId).val() ? $("#filledFormToUpdateId" + widgetId).val() : null;
    }

    var requiredExceptionList = new Array();
    var wasRequiredException = collectFilledFormItems(request, widgetId, requiredExceptionList);
    //Checking for required exception's
    if (wasRequiredException) {
        for (var i = 0; ; i++) {
            var requiredException = requiredExceptionList[i];
            if (requiredException == undefined) {
                break;
            }
            errors.set("REQUIRED_EXCEPTION" + i, requiredException, errors);
        }
        return;
    }

    showFormsLoadingDiv(widgetId);

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.CannotSendMailWithEmptyMessageException",
            errors.exceptionAction({errorId:"CannotSendMailWithEmptyMessageException", fields:[errors], onException:function() {
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.WrongVerificationCodeException",
            errors.exceptionAction({errorId:"WRONG_VERIFICATION_CODE_EXCEPTION", fields:[errors], alternativeMessage:"", onException:function() {
                relaodNoBot("noBotImage" + widgetId);
                hideFormsLoadingDiv(widgetId);
            }}));
    serviceCall.executeViaDwr("SendContactUsInfoService", "execute", request, function (response) {
        if (response) {
            response.widgetId = widgetId;
            response.showFromAddRecord = showFromAddRecord;
            contactUsOnUploadResponses.push(response);
            uploadersStarter.startFileUploading(widgetId, response.filledFormId, contactUsOnUploadFunction);
        }
    });
}

var contactUsOnUploadResponses = new Array();

function contactUsOnUploadFunction(widgetId) {
    var response = getResponse(contactUsOnUploadResponses, widgetId);
    contactUsOnUploadResponses.splice(getResponseIndex(contactUsOnUploadResponses, widgetId), 1);
    swfUploaderRemoveBackgroundLoadingMessage();
    updateContactUsFormOnPage(widgetId, response.showFromAddRecord, response.nextPageHtml, response.filledFormId,
            response.showSuccessfullSubmitMessage);
}

function updateContactUsFormOnPage(widgetId, showFormAddRecord, nextPageHtml, filledFormId, showSuccessfullSubmitMessage) {
    if (showFormAddRecord) {
        if (nextPageHtml) {
            window.parent.document.getElementById("addNewRecord").src = nextPageHtml;
        } else {
            window.parent.reloadManageRegistrantsTable();
            window.parent.closeConfigureWidgetDiv();
        }
    } else {
        if (nextPageHtml) {
            $("#contactUsBlock" + widgetId).html($(nextPageHtml).find("#contactUsBlock" + widgetId).html());

            if (showSuccessfullSubmitMessage) {
                addSlidingTimeoutEvent($("#messageSent" + widgetId)[0], 5000);
            }
        } else {
            clearForm(widgetId, true);
            $("#messageSent" + widgetId)[0].style.display = "block";
            $("#form" + widgetId)[0].style.display = "none";
        }
    }
}
