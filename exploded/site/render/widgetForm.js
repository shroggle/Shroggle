function removeFormFile(filledFormId, filledFormItemId, elementsId, fileTypesDescription, formWidgetId, position,
                        itemText, itemName, formFileTypeForDeletion, videoFileSizeLimit, bulkUploaderId) {
    new ServiceCall().executeViaDwr("RemoveFormFileService", "execute", filledFormItemId, formFileTypeForDeletion, function (response) {
        if (response == "ok") {
            document.getElementById(elementsId + "RemoveFormFileButton").style.display = "none";
            document.getElementById(elementsId + "SpanButtonContainer").style.display = "inline";
            document.getElementById(elementsId + "TxtFileName").value = "";
            document.getElementById(elementsId + "videoLinkDiv").style.display = "none";
            document.getElementById(elementsId + "downloadVideoLink").style.display = "none";
            document.getElementById(elementsId + "videoNameDiv").style.display = "inline";
            showFileUploadFields(
                    fileTypesDescription,
                    elementsId + 'SpanButtonPlaceHolder',
                    formWidgetId,
                    elementsId,
                    itemName,
                    position,
                    itemText,
                    videoFileSizeLimit,
                    bulkUploaderId);
        } else {
            //todo show error
        }
    });
}

function showFormsLoadingDiv(widgetId) {
    $("#formsLoadingMessageDiv" + widgetId).css("display", "inline");

    var formSubmitButton = $("#submit" + widgetId)[0];
    if (formSubmitButton) {
        disableControl(formSubmitButton);
    }

    var formResetButton = $("#reset" + widgetId)[0];
    if (formResetButton) {
        disableControl(formResetButton);
    }

    var formBackButton = $("#back" + widgetId)[0];
    if (formBackButton) {
        disableControl(formBackButton);
    }
}

function hideFormsLoadingDiv(widgetId) {
    $("#formsLoadingMessageDiv" + widgetId).css("display", "none");

    var formSubmitButton = $("#submit" + widgetId)[0];
    if (formSubmitButton) {
        disableControl(formSubmitButton, false);
    }

    var formResetButton = $("#reset" + widgetId)[0];
    if (formResetButton) {
        disableControl(formResetButton, false);
    }

    var formBackButton = $("#back" + widgetId)[0];
    if (formBackButton) {
        disableControl(formBackButton, false);
    }
}

function resetForm(widgetId, formId, showFromAddRecord, widgetType) {
    showFormsLoadingDiv(widgetId);

    new ServiceCall().executeViaDwr("ShowFormPageService", "reset", widgetId, widgetType, formId, function(response) {
        if (response) {
            if (widgetType == "CUSTOM_FORM") {
                updateCustomFormOnPage(widgetId, showFromAddRecord, response);
            } else if (widgetType == "REGISTRATION") {
                updateRegistrationFormOnPage(widgetId, showFromAddRecord, response);
            } else if (widgetType == "CONTACT_US") {
                updateContactUsFormOnPage(widgetId, showFromAddRecord, response);
            } else if (widgetType == "CHILD_SITE_REGISTRATION") {
                updateCSRFormOnPage(widgetId, showFromAddRecord, response);
            }
        }
    });
}

function goBackOnForms(widgetId, formId, showFromAddRecord, widgetType) {
    //Composing request
    var request = new Object();
    request.widgetId = widgetId;
    request.widgetType = widgetType;
    request.formId = formId;

    if ($("#totalPageBreaks" + widgetId).val() != 0) {
        request.pageBreaksToPass = parseInt($("#pageBreaksToPass" + widgetId).val()) - 1;
        request.filledFormToUpdateId = $("#filledFormToUpdateId" + widgetId).val() ? $("#filledFormToUpdateId" + widgetId).val() : null;
    }

    if (widgetType == "CHILD_SITE_REGISTRATION") {
        request.additionalParameters = new Array();

        var userIdParameter = new Object();
        userIdParameter.parameterName = "childSiteUserId";
        userIdParameter.parameterValue = $("#childSiteUserId" + widgetId).val();
        request.additionalParameters.push(userIdParameter);

        var chilsSiteSettingsIdParameter = new Object();
        chilsSiteSettingsIdParameter.parameterName = "settingsId";
        chilsSiteSettingsIdParameter.parameterValue = $("#settingsId" + widgetId).val();
        request.additionalParameters.push(chilsSiteSettingsIdParameter);
    } else if (widgetType == "REGISTRATION") {
        request.additionalParameters = new Array();

        var registrationUserIdParameter = new Object();
        registrationUserIdParameter.parameterName = "registrationUserId";
        registrationUserIdParameter.parameterValue = $("#registrationUserId" + widgetId).val();
        request.additionalParameters.push(registrationUserIdParameter);
    }

    showFormsLoadingDiv(widgetId);

    new ServiceCall().executeViaDwr("ShowFormPageService", "execute", request, function(response) {
        if (response) {
            if (widgetType == "CUSTOM_FORM") {
                updateCustomFormOnPage(widgetId, showFromAddRecord, response, request.filledFormToUpdateId);
            } else if (widgetType == "REGISTRATION") {
                updateRegistrationFormOnPage(widgetId, showFromAddRecord, response, request.filledFormToUpdateId);
            } else if (widgetType == "CONTACT_US") {
                updateContactUsFormOnPage(widgetId, showFromAddRecord, response, request.filledFormToUpdateId);
            } else if (widgetType == "CHILD_SITE_REGISTRATION") {
                updateCSRFormOnPage(widgetId, showFromAddRecord, response, request.filledFormToUpdateId);
            }
        }
    });
}

// We doing this because error block is a part of the table and it can hold really huge error in that case this block
// will cause table to stretchen out to this error length. We preventing that by settings fixed width of error block.
function makeErrorBlockWidthLikeTable(widgetId) {
    var formsErrorBlock = $(".formsErrorBlock" + widgetId);
    var formsTable = $(".form" + widgetId);

    var tableWidth = formsTable[0].offsetWidth;

    var formsErrorBlockWidth = parseInt(tableWidth) - 10;
    $(formsErrorBlock).css('max-width', formsErrorBlockWidth + "px");
}

function removePreviewImage(elementsId) {
    try {
        document.getElementById('widgetVideoImageUrl' + elementsId).value = "";
    } catch(ex) {
    }
    try {
        document.getElementById('previewImageTR' + elementsId).style.display = 'none';
    } catch(ex) {
    }
    try {
        document.getElementById('withoutImageTR' + elementsId).style.display = 'table-row';
    } catch(ex) {
    }
    try {
        document.getElementById('formImageTd1' + elementsId).style.height = '25px';
    } catch(ex) {
    }
    try {
        document.getElementById('formImageTd2' + elementsId).style.height = '25px';
    } catch(ex) {
    }
    try {
        getActiveWindow().resize();
    } catch(ex) {
    }
}

function getResponse(responses, widgetId) {
    for (var i = 0; i < responses.length; i++) {
        if (responses[i].widgetId == widgetId) {
            return responses[i];
        }
    }
    return new Object();
}

function getResponseIndex(responses, widgetId) {
    for (var i = 0; i < responses.length; i++) {
        if (responses[i].widgetId == widgetId) {
            return i;
        }
    }
    return 0;
}