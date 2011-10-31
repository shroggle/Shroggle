// todo refactor all this mess.
var doubleClickProtection = false;

function delayedFixManageRecordsHeadTableWidths(){
    fixManageRecordsHeadTableWidths();
    window.setTimeout("fixManageRecordsHeadTableWidths()", 500);
}

function fixManageRecordsHeadTableWidths() {
    var bodyFields = $("#manageRecordsBodyTable").find("tr:first").find("td.fieldTdBody");
    var headFields = $(".fieldTdHead");

    for (var i = 0; i < bodyFields.length; i++) {
        $(headFields[i]).css("width", $(bodyFields[i]).css("width"));
    }
}

function showConfigureFilledForms(settings) {
    var window = createConfigureWindow({
        width: 950,
        height: 600,
        onBeforeClose: function () {
            if (settings.onBeforeClose) {
                settings.onBeforeClose();
            }
        }
    });

    window.manageFormRecordsWindow = true;

    new ServiceCall().executeViaDwr("ShowFormRecordsService", "execute", {
        widgetId: settings.widgetId == undefined ? null : settings.widgetId,
        galleryId: settings.galleryId == undefined ? null : settings.galleryId,
        formId: settings.formId == undefined ? null : settings.formId
    }, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        afterManageRecordsContentLoaded(response);
    });
}

function afterManageRecordsContentLoaded(response) {
    var window = getActiveWindow();
    window.setContent(response.html);
    if (response.configured) {
        disableControl($("#editFilterLink")[0]);

        if (response.readOnly) {
            disableManageFormRecordsWindow(true);
        }
    }
    fixManageRecordsHeadTableWidths();
    window.resize();
}


/*-------------------------------------------------------Search-------------------------------------------------------*/
var updatePending;
function processKeyManageFormRecords(searchKey) {
    if (searchKey.length == 0) {
        updatePending = false;
        reloadManageRegistrantsTable();
        return;
    }

    if (searchKey.length >= 3) {
        if (updatePending) {
            clearTimeout(updatePending);
        }
        updatePending = setTimeout(reloadManageRegistrantsTable, 500);
        return;
    }

    if (getActiveWindow().manageFormRecordsWindow) {
        $(getActiveWindow().getWindowContentDiv()).bind("keydown", function(event) {
            var keyId = event.keyCode;

            switch (keyId) {
                //Enter
                case 13:{
                    reloadManageRegistrantsTable();
                    break;
                }
            }
        });
    }
}

function resetSearchAndFilter() {
    $("#search").val("");
    $("#filterPickList").val("-1");
    reloadManageRegistrantsTable();
    $("#show_all_div")[0].style.visibility = "hidden";
}
/*-------------------------------------------------------Search-------------------------------------------------------*/


/*-----------------------------------------------------Edit Form------------------------------------------------------*/
function editFormFromManageRegistrants(itemType, formId) {
    editForm(itemType, formId);

    //Updating Form Records window after Form Configure window close.
    getActiveWindow().onAfterClose = function () {
        updateManageFormRecordsWindow();
    };
}

function editForm(itemType, formId) {
    manageItems.showItemSettings({itemId:formId, itemType: itemType});
}

function updateManageFormRecordsWindow() {
    var formId = $("#manageFormRecrodsFormId").val();
    var galleryId = $("#manageFormRecordsGalleryId").val() == "null" ? null : $("#manageFormRecordsGalleryId").val();
    var request = new Object();

    // If there is galleryId then initialize Manage Records only with it.
    if (galleryId != null) {
        request.galleryId = galleryId;
    } else {
        request.formId = formId;
    }

    var manageFormRecordsWindow = getActiveWindow();
    createLoadingArea({element: manageFormRecordsWindow.windowDiv, text:"Reloading, please wait...", color:"green"});
    new ServiceCall().executeViaDwr("ShowFormRecordsService", "execute", request, function (response) {
        afterManageRecordsContentLoaded(response);
        removeLoadingArea();
    });
}
/*-----------------------------------------------------Edit Form------------------------------------------------------*/


/*---------------------------------------------------Add New Record---------------------------------------------------*/
function showAddNewRecordWindow(formId) {
    var type = $("#manageFormRecrodsFormType")[0].value;

    var iFrameId = "addNewRecord";
    var customButtons = new Array();

    //Creating and adding back button
    var backButton = new Object();
    backButton.className = "but_w73";
    backButton.value = "Back";
    backButton.display = "none";
    backButton.id = "iframeBackButton";
    customButtons.push(backButton);

    //Creating and adding submit button
    var submitButton = new Object();
    submitButton.className = "but_w100";
    submitButton.value = "Submit";
    submitButton.id = "iframeSubmitButton";
    submitButton.customFunction = function () {
        getIFrameDocument(document.getElementById(iFrameId)).getElementById("submit0").onclick();
    };
    customButtons.push(submitButton);

    //Creating and adding reset button
    var resetButton = new Object();
    resetButton.className = "but_w73";
    resetButton.id = "iframeResetButton";
    resetButton.value = "Reset";
    resetButton.customFunction = function () {
        getIFrameDocument(document.getElementById(iFrameId)).getElementById("reset0").onclick();
    };
    customButtons.push(resetButton);

    var contentsWindow = createConfigureWidgetIframe({width:850, height:550, customButtons:customButtons, titleText:"<h1>Add Record</h1>", id:iFrameId, customOnLoadFunction:function () {
        updateAddRecordWindowButtons(iFrameId);
    }});

    if (type == "REGISTRATION") {
        contentsWindow.src = "/site/showRegistrationForm.action?formId=" + formId + "&showFromAddRecord=true";
    } else if (type == "CUSTOM_FORM" || type == "ORDER_FORM") {
        contentsWindow.src = "/site/showCustomForm.action?formId=" + formId + "&showFromAddRecord=true";
    } else if (type == "CONTACT_US") {
        contentsWindow.src = "/site/showContactUs.action?formId=" + formId + "&showFromAddRecord=true";
    } else if (type == "CHILD_SITE_REGISTRATION") {
        contentsWindow.src = "/site/showChildSiteRegistration.action?formId=" + formId + "&showFromAddRecord=true";
    }

    getActiveWindow().resize();
}

function updateAddRecordWindowButtons(iFrameId) {
    var backButtonOnPage = getIFrameDocument(document.getElementById(iFrameId)).getElementById("back0");
    if (backButtonOnPage) {
        var iframeBackButton = $("#iframeBackButton")[0];
        iframeBackButton.onclick = backButtonOnPage.onclick;
        iframeBackButton.style.display = "inline";
    } else {
        iframeBackButton = $("#iframeBackButton")[0];
        iframeBackButton.style.display = "none";
    }

    var submitButtonOnPage = getIFrameDocument(document.getElementById(iFrameId)).getElementById("submit0");
    var iframeSubmitButton = $("#iframeSubmitButton")[0];
    iframeSubmitButton.value = submitButtonOnPage.value;
}
/*---------------------------------------------------Add New Record---------------------------------------------------*/


/*-----------------------------------------------------Edit Record----------------------------------------------------*/
function showEditRecordWindow(filledFormId, linkedFilledFormId) {
    var editRecordWindow = createConfigureWindow({width:850, height:500});
    new ServiceCall().executeViaDwr("ShowEditFilledFormService", "execute", filledFormId, linkedFilledFormId, function (html) {
        editRecordWindow.setContent(html);
    });
}

var manageFormRecordsResponse;
function saveFilledFormEdit(filledFormId, widgetId, showForChildSiteRegistration, linkedFilledFormId) {
    if (doubleClickProtection) return;
    doubleClickProtection = true;

    var errors = new Errors();

    errors.clear();

    var requiredExceptionList = new Array();
    var addNewFormRecordRequest = new Object();
    var wasRequiredException = collectFilledFormItems(addNewFormRecordRequest, widgetId, requiredExceptionList);

    //Checking for required exception's
    if (wasRequiredException) {
        for (var i = 0; ; i++) {
            var requiredException = requiredExceptionList[i];
            if (requiredException == undefined) {
                break;
            }

            errors.set("REQUIRED_EXCEPTION" + i, requiredException);
            doubleClickProtection = false;
        }

        return;
    }
    new ServiceCall().executeViaDwr("EditFilledFormService", "execute", addNewFormRecordRequest.filledFormItems, filledFormId, function() {
        manageFormRecordsResponse = new Object();
        manageFormRecordsResponse.showForChildSiteRegistration = showForChildSiteRegistration;
        manageFormRecordsResponse.filledFormId = filledFormId;
        manageFormRecordsResponse.linkedFilledFormId = linkedFilledFormId;
        var widgetId = document.getElementById("formWidgetId").value;
        uploadersStarter.startFileUploading(widgetId, filledFormId, saveFormRecordsOnUploadFunction);
    });
}

function saveFormRecordsOnUploadFunction() {
    if (manageFormRecordsResponse.linkedFilledFormId) {
        closeConfigureWidgetDiv();
        doubleClickProtection = false;

        var editRecordWindow = getActiveWindow();
        createLoadingArea({element:editRecordWindow.windowDiv, text:"Reloading, please wait...", color:"green"});
        new ServiceCall().executeViaDwr("ShowEditFilledFormService", "execute", manageFormRecordsResponse.linkedFilledFormId, null, function (html) {
            editRecordWindow.setContent(html);
            removeLoadingArea();
        });

        return;
    }
    reloadManageRegistrantsTable();
    swfUploaderRemoveBackgroundLoadingMessage();
    closeConfigureWidgetDiv();
    doubleClickProtection = false;
}

function editRecordMoreInfo() {
    var moreInfoWindow = createConfigureWindow({width:500, height:200});
    moreInfoWindow.setContent($("#editRecordMoreInfoDiv")[0].innerHTML);
}
/*-----------------------------------------------------Edit Record----------------------------------------------------*/


/*----------------------------------------------Show/hide/delete record-----------------------------------------------*/
function showFormRecordDataWindow(filledFormId) {
    var formRecordDataWindowDiv = createConfigureWindow({width:540, height:500});
    new ServiceCall().executeViaDwr("ShowFilledFormService", "execute", filledFormId, function (html) {
        formRecordDataWindowDiv.setContent(html);
    });
}

function hideRecord(filledFormId, hide) {
    new ServiceCall().executeViaDwr("HideFilledFormService", "execute", filledFormId, hide, function () {
        var hideRecordInfoDiv = $("#hideRecordInfoDiv")[0];
        if (hide && !hideRecordInfoDiv.messageShown) {
            hideRecordInfoDiv.messageShown = true;
            addFadingTimeoutEvent(hideRecordInfoDiv, 4000);
        }
    });
}

function deleteFilledForm(filledFormId) {
    if (confirm(document.getElementById("deleteText").value)) {
        new ServiceCall().executeViaDwr("DeleteFilledFormService", "execute", filledFormId, function() {
            var tableBody = document.getElementById("manageFormRecordsTable");
            var rowToRemove = document.getElementById("row" + filledFormId);
            tableBody.removeChild(rowToRemove);
        });
    }
}
/*----------------------------------------------Show/hide/delete record-----------------------------------------------*/


/*--------------------------------------------------Common functions--------------------------------------------------*/
function reloadManageRegistrantsTable() {
    var request = {
        formId : $("#manageFormRecrodsFormId").val(),
        formFilterId : $("#filterPickList").val(),
        searchKey : $("#search").val(),
        sortProperties : tableWithSort.getSortProperties()
    };

    createLoadingArea({element:$("#manageFormRecordsTableDiv")[0], text: "Loading, please wait...", color:"green", guaranteeVisibility:true});
    new ServiceCall().executeViaDwr("ReloadManageRegistrantsTableService", "execute", request, function(response) {
        removeLoadingArea();
        $("#manageFormRecordsTableDiv").html(response);

        if ($("#filterPickList > option:selected").val() != -1) {
            disableControl($("#editFilterLink")[0], false);
        }
        
        $("#show_all_div")[0].style.visibility = (request.searchKey != "" ? "visible" : "hidden");

        fixManageRecordsHeadTableWidths();
    });
}

function disableManageFormRecordsWindow(disable) {
    disableControl($("#createFilterLink")[0], disable);
    if (disable) {
        $("#addRecordButton").hide();
        $("#bulkUploadButton").hide();
        $("#readOnlyAccessFormSpan").show();
    } else {
        $("#addRecordButton").show();
        $("#bulkUploadButton").show();
        $("#readOnlyAccessFormSpan").hide();
    }
}

function showHelpWindow() {
    var helpWindow = createConfigureWindow({
        width: 540,
        height: 200
    });
    helpWindow.setContent($("#helpWindow")[0].innerHTML);
}
/*--------------------------------------------------Common functions--------------------------------------------------*/