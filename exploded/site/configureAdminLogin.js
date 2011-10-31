var configureAdminLogin = {};

configureAdminLogin.onBeforeShow = function (settings) {
    configureAdminLogin.settings = settings;
};

configureAdminLogin.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureAdminLogin.errors = new Errors({}, "adminLoginErrors");

    if ($("#siteOnItemRightType").val() == "READ") {
        configureAdminLogin.disable();
    }
};

configureAdminLogin.save = function (closeAfterSaving) {
    var request = {
        widgetId: configureAdminLogin.settings.widgetId,
        adminLoginId: $("#selectedAdminLoginId").val(),
        text: $("#configureAdminLoginText").val(),
        description: $("#configureAdminLoginHeader").html(),
        showDescription: $("#showconfigureAdminLoginHeader").val(),
        name : $("#adminLoginName").val()
    };

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.AdminLoginNameNotUniqueException",
            configureAdminLogin.errors.exceptionAction({errorId:"AdminLoginNameNotUnique", fields:[$("#adminLoginName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveAdminLoginService", "execute", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + request.id).html($("#configureShoppingCartName").val());

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureAdminLogin.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureAdminLogin.settings.widgetId) {
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

configureAdminLogin.disable = function () {
    disableControl($("#configureAdminLoginText")[0]);
    disableControl($("#adminLoginHeader")[0]);

    $("#windowSave", $("#configureAdminLoginButtons")[0]).hide();
    $("#windowApply", $("#configureAdminLoginButtons")[0]).hide();
    $("#windowCancel", $("#configureAdminLoginButtons")[0]).val("Close");

    $("#adminLoginReadOnlyMessage").show();
    $("#adminLoginErrors").hide();
};