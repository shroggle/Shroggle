function sumbitCustomForm(widgetId, showFromAddRecord) {
    var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error", emptyErrorClassName: "emptyHiddenError"}, "customFormErrorBlock" + widgetId);
    errors.clear();

    if ($("#customFormVerificationCode" + widgetId).val() == 0) {
        errors.set("WRONG_VERIFICATION_CUSTOM_FORM", $("#registrationErrorsEmptyCode" + widgetId).val(), [errors]);
        return;
    }

    //Composing request
    var request = new Object();
    request.verificationCode = $("#customFormVerificationCode" + widgetId).val();
    request.widgetId = widgetId;
    request.showFromAddRecord = showFromAddRecord;

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
            "com.shroggle.exception.WrongVerificationCodeException",
            errors.exceptionAction({errorId:"WRONG_VERIFICATION_CODE_EXCEPTION", fields:[errors], onException:function() {
                relaodNoBot("noBotImage" + widgetId);
                hideFormsLoadingDiv(widgetId);
            }}));

    serviceCall.executeViaDwr("SubmitCustomFormService", "execute", request, function(serviceResponse) {
        if (serviceResponse) {
            serviceResponse.widgetId = widgetId;
            serviceResponse.showFromAddRecord = showFromAddRecord;

            customFormOnUploadResponses.push(serviceResponse);
            uploadersStarter.startFileUploading(widgetId, serviceResponse.filledFormId, customFormOnUploadFunction);
        }
    });
}

var customFormOnUploadResponses = new Array();

function customFormOnUploadFunction(widgetId) {
    var response = getResponse(customFormOnUploadResponses, widgetId);
    customFormOnUploadResponses.splice(getResponseIndex(customFormOnUploadResponses, widgetId), 1);
    swfUploaderRemoveBackgroundLoadingMessage();

    updateCustomFormOnPage(widgetId, response.showFromAddRecord, response.nextPageHtml, response.filledFormId,
            response.showSuccessfullSubmitMessage);
}

function updateCustomFormOnPage(widgetId, showFormAddRecord, nextPageHtml, filledFormId, showSuccessfullSubmitMessage) {
    if (showFormAddRecord) {
        if (nextPageHtml) {
            window.parent.document.getElementById("addNewRecord").src = nextPageHtml;
        } else {
            window.parent.reloadManageRegistrantsTable();
            window.parent.closeConfigureWidgetDiv();
        }
    } else {
        if (nextPageHtml) {
            $("#customFormBlock" + widgetId).html($(nextPageHtml).find("#customFormBlock" + widgetId).html());

            if (showSuccessfullSubmitMessage) {
                $("#customFormErrorBlock" + widgetId).html("<span id='successfullSubmit" + widgetId + "' style='display:none; color:green'>" +
                        $("#successfulSubmit" + widgetId).val() + "</span>");
                addSlidingTimeoutEvent($("#successfullSubmit" + widgetId)[0], 5000);
            }
        } else {
            $("#customFormErrorBlock" + widgetId).html("<span style='color:green'>" + $("#successfulSubmit" + widgetId).val() + "</span>");
            resetCustomForm(widgetId);
            relaodNoBot("noBotImage" + widgetId);
            hideFormsLoadingDiv(widgetId);
        }
    }
}

function resetCustomForm(widgetId) {
    $("#customFormVerificationCode" + widgetId).val("");

    clearForm(widgetId, false);
}