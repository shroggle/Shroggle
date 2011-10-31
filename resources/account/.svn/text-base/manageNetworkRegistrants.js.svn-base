function editChildSiteRegistrationWindow(visitorId, filledFormId, parentSiteId, showVisitor) {
    var editCSRWindow = createConfigureWindow({width: showVisitor ? 610 : 1120, height: 600});

    new ServiceCall().executeViaDwr("ManageNetworkRegistrantsService", "execute", visitorId, filledFormId, parentSiteId, showVisitor, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        editCSRWindow.setContent(response);
    });
}

function saveNetworkVisitorEdit(visitorId, widgetId, parentSiteId) {
    var formItems = new Object();
    var filledFormId = document.getElementById("filledFormId").value;
    collectFilledFormItems(formItems, widgetId, new Array());

    new ServiceCall().executeViaDwr("ManageNetworkRegistrantsService", "updateNetworkVisitorForm", visitorId, filledFormId, formItems.filledFormItems, parentSiteId, function (response) {
        manageNetworkRegistrantsResponse = response;
        var widgetId = document.getElementById("formWidgetId").value;
        var filledFormId = document.getElementById("prefilledFormId").value;
        uploadersStarter.startFileUploading(widgetId, filledFormId, manageNetworkRegistrantsOnUploadFunction);
    });
}

var manageNetworkRegistrantsResponse;
function manageNetworkRegistrantsOnUploadFunction() {
    doubleClickProtection = false;
    document.getElementById("networkRegistrantsTable").innerHTML = manageNetworkRegistrantsResponse;
    swfUploaderRemoveBackgroundLoadingMessage();
    closeConfigureWidgetDiv();
}

function processKeyManageNetworkRegistrants(searchKey, e) {
    if (searchKey.length == 0) {
        searchChildSiteFilledForms();
        document.getElementById("showAllLink").style.visibility = "hidden";
        return;
    }
    if (searchKey.length >= 3) {
        delayedNetworkRegistrantsSearch();
        document.getElementById("showAllLink").style.visibility = "visible";
        return;
    }
    if (!isAnyWindowOpened()) {
        var keyId = (window.event) ? event.keyCode : e.keyCode;
        if (window) {
            switch (keyId) {
                //Enter
                case 13:{
                    searchChildSiteFilledForms();
                    break;
                }
            }
        }
    }
}

function searchChildSiteFilledForms() {
    updateNetworkRegistrantsPending = false;
    var parentSiteId = document.getElementById("parentSiteId").value;
    var searchKey = document.getElementById("search").value;

    createLoadingArea({element:$("#networkRegistrantsTable")[0], text: "Applying new search parameters...",
        color: "green", guaranteeVisibility:true});


    new ServiceCall().executeViaDwr("SearchChildSiteFilledFormsService", "execute", searchKey, parentSiteId, function (response) {
        document.getElementById("networkRegistrantsTable").innerHTML = response;

        removeLoadingArea();
    });
}

var updateNetworkRegistrantsPending;
function delayedNetworkRegistrantsSearch() {
    if (updateNetworkRegistrantsPending) {
        clearTimeout(updateNetworkRegistrantsPending);
    }
    updateNetworkRegistrantsPending = setTimeout(searchChildSiteFilledForms, 500);
}

function showNetworkRegistrantsHelpWindow() {
    var window = createConfigureWindow({
        width: 540,
        height: 200
    });

    window.setContent($("#helpWindow")[0].innerHTML);
}
