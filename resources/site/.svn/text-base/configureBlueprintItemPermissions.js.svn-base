var configureBlueprintItemPermissions = {};

configureBlueprintItemPermissions.onAfterShow = function () {
    var checkSharable = function () {
        var pageLock = $("#configureWidgetBlueprintRightPageLock")[0] != undefined;
        disableControls($("input[name=configureWidgetBlueprintRightShared]"), pageLock);

        disableControls($("input[name=widgetBlueprintRight]"),
                pageLock || $("#configureWidgetBlueprintRightNotShared").attr("checked"));

        disableControls($("#widgetBlueprintEditableRushe"),
                pageLock || $("#configureWidgetBlueprintRightNotShared").attr("checked"));
    };

    $("#configureWidgetBlueprintRightIsShared").click(checkSharable);
    $("#configureWidgetBlueprintRightNotShared").click(checkSharable);

    checkSharable();
};

configureBlueprintItemPermissions.save = function (closeAfterSaving) {
    var widgetId = selectedWidget.widgetId;
    if (!widgetId) return;

    var editable = $("#widgetBlueprintEditableRequired")[0].checked || $("#widgetBlueprintEditableNoRequired")[0].checked;
    var required = $("#widgetBlueprintEditableRequired")[0].checked || $("#widgetBlueprintNoEditableRequired")[0].checked;
    var editRuche = !$("#widgetBlueprintEditableRushe")[0].checked;
    var sharable = $("#configureWidgetBlueprintRightIsShared")[0].checked;

    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveBlueprintItemPermissionsService", "execute", widgetId, editable, required, editRuche, sharable, function () {
        makePageDraftVisual(window.parent.getActivePage());

        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
        } else {
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    });
};
