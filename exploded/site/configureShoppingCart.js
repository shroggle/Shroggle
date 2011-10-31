var configureShoppingCart = {};

configureShoppingCart.onBeforeShow = function (settings) {
    configureShoppingCart.settings = settings;
};

configureShoppingCart.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    if ($("#siteOnItemRightType").val() == "READ") {
        configureShoppingCart.disableShoppingCart(true);
    }

    configureShoppingCart.errors = new Errors({}, "shoppingCartErrors");
};

configureShoppingCart.save = function (closeAfterSaving) {
    configureShoppingCart.errors.clear();

    if ($("#configureShoppingCartName").val() == "") {
        configureShoppingCart.errors.set("ShoppingCartNameIsNullOrEmpty", $("#ShoppingCartNameIsNullOrEmpty").val(),
                [$("#configureShoppingCartName")[0]]);
        return;
    }

    var request = {
        id: $("#selectedShoppingCartId").val(),
        name: $("#configureShoppingCartName").val(),
        imageHeight: $("#configureShoppingCartImageHeight").val(),
        imageWidth: $("#configureShoppingCartImageWight").val(),
        imageInFirstColumn: $("#configureShoppingCartImageInFirstColumn").attr("checked"),
        descriptionAfterName: $("#configureShoppingCartDescriptionAfterName").attr("checked"),
        showDescription: $("#showShoppingCartHeader").val(),
        description: $("#ShoppingCartHeader").html(),
        widgetId: configureShoppingCart.settings.widgetId
    };

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ShoppingCartNameNotUniqueException",
            configureShoppingCart.errors.exceptionAction({errorId:"ShoppingCartNameNotUniqueException", fields:[$("#configureShoppingCartName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveShoppingCartService", "execute", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + request.id).html($("#configureShoppingCartName").val());

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureShoppingCart.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureShoppingCart.settings.widgetId) {
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

configureShoppingCart.disableShoppingCart = function(disable) {
    disableControl($("#configureShoppingCartName")[0]);
    disableControl($("#editShoppingCartDescLink")[0]);

    disableControl($("#configureShoppingCartImageInFirstColumn")[0]);
    disableControl($("#configureShoppingCartImageWight")[0]);
    disableControl($("#configureShoppingCartImageHeight")[0]);
    disableControl($("#configureShoppingCartDescriptionAfterName")[0]);

    $("#windowSave", $("#configureShoppingCartButtons")[0]).hide();
    $("#windowApply", $("#configureShoppingCartButtons")[0]).hide();
    $("#windowCancel", $("#configureShoppingCartButtons")[0]).val("Close");

    $("#shoppingCartReadOnlyMessage").show();
    $("#shoppingCartErrors").hide();
};
