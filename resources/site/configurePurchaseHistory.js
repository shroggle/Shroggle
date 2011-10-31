var configurePurchaseHistory = {
    errors: undefined
};

configurePurchaseHistory.onBeforeShow = function (settings) {
    configurePurchaseHistory.settings = settings;
};

configurePurchaseHistory.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    if ($("#siteOnItemRightType").val() == "READ") {
        configurePurchaseHistory.disablePurchaseHistory(true);
    }

    configurePurchaseHistory.errors = new Errors({}, "purchaseHistoryErrors");
};

configurePurchaseHistory.save = function (closeAfterSaving) {
    configurePurchaseHistory.errors.clear();

    if ($("#configurePurchaseHistoryName").val() == "") {
        configurePurchaseHistory.errors.set("PurchaseHisotryNameIsNullOrEmpty", $("#PurchaseHisotryNameIsNullOrEmpty").val(),
                [$("#configurePurchaseHistoryName")[0]]);
        return;
    }

    var request = {
        id: $("#selectedPurchaseHistoryId").val(),
        name: $("#configurePurchaseHistoryName").val(),
        imageHeight: $("#configurePurchaseHistoryImageHeight").val(),
        imageWidth: $("#configurePurchaseHistoryImageWight").val(),
        showProductImage: $("#configurePurchaseHistoryShowImage").attr("checked"),
        showProductDescription: $("#configurePurchaseHistoryShowProductDescription").attr("checked"),
        showDescription: $("#showPurchaseHistoryHeader").val(),
        description: $("#PurchaseHistoryHeader").html()
    };

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PurchaseHistoryNameNotUniqueException",
            configurePurchaseHistory.errors.exceptionAction({errorId:"PurchaseHistoryNameNotUniqueException", fields:[$("#configurePurchaseHistoryName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SavePurchaseHistoryService", "execute", configurePurchaseHistory.settings.widgetId, request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + request.id).html($("#configurePurchaseHistoryName").val());

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configurePurchaseHistory.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configurePurchaseHistory.settings.widgetId) {
                    closeConfigureWidgetDivWithUpdate(response);
                } else {
                    closeConfigureWidgetDiv();
                }
            }
        }

        if (!closeAfterSaving) {
            updateWidgetInfo(response);
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    });
};

configurePurchaseHistory.disablePurchaseHistory = function () {
    disableControl($("#configurePurchaseHistoryName")[0]);
    disableControl($("#editPurchaseHistoryDescLink")[0]);

    disableControl($("#configurePurchaseHistoryShowImage")[0]);
    disableControl($("#configurePurchaseHistoryImageWight")[0]);
    disableControl($("#configurePurchaseHistoryImageHeight")[0]);
    disableControl($("#configurePurchaseHistoryShowProductDescription")[0]);

    $("#windowSave", $("#configurePurchaseHistoryButtons")[0]).hide();
    $("#windowApply", $("#configurePurchaseHistoryButtons")[0]).hide();
    $("#windowCancel", $("#configurePurchaseHistoryButtons")[0]).val("Close");

    $("#purchaseHistoryReadOnlyMessage").show();
    $("#purchaseHistoryErrors").hide(); // Hiding error block.
};
