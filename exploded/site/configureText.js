var configureText = {
    TEXT_EDITOR_ID : "configureWidgetText"
};

configureText.getTextEditorId = function() {
    return configureText.TEXT_EDITOR_ID + configureText.settings.widgetId;
};

configureText.onBeforeShow = function (settings) {
    configureText.settings = settings;
};

configureText.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }
    configureText.errors = new Errors({}, "textErrors");
};

configureText.showTextEditor = function () {
    var text = $("#configureTextItemText").html();

    if ($("#siteOnItemRightType").val() == "READ") {
        configureText.disable(text);
        getActiveWindow().resize();
    } else {
        createEditor({
            width: 800,
            height: 310,
            showLastSavedData: true,
            root: "../",
            editorId: configureText.getTextEditorId(),
            place: $("#configureTextWidgetEditor")[0],
            value: text
        });
    }
};

configureText.save = function (closeAfterSaving) {
    var request = {
        widgetText: getEditorContent(configureText.getTextEditorId()),
        widgetId: configureText.settings.widgetId,
        textItemId: $("#selectedTextItemId").val(),
        name: $("#configureTextName").val(),
        saveDraftText: false
    };

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.TextLargeException", configureText.errors.exceptionAction({
        errorId: "TextLargeException", alternativeMessage: $("#configureTextLarge").val()}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveTextService", "execute", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + request.textItemId).html(request.name);
        } else if (configureText.settings.widgetId && request.widgetText != $("#oldWidgetText").val()) {
            makePageDraftVisual(window.parent.getActivePage());
        }

        if (closeAfterSaving) {
            if (!$("#dashboardPage")[0] && configureText.settings.widgetId) {
                closeConfigureWidgetDivWithUpdate(response);
            } else {
                closeConfigureWidgetDiv();
            }

            closeEditor(configureText.getTextEditorId());
        } else {
            updateWidgetInfo(response);
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    });
};

configureText.disable = function (text) {
    $("#configureTextWidgetEditor").append(text);
    $("#configureTextWidgetEditor").show();
    $("#closeConfigureTextWidgetButton").show();

    $("#tinyMCELoadingMessage").hide();

    $("#windowSave", $("#configureTextButtons")[0]).hide();
    $("#windowApply", $("#configureTextButtons")[0]).hide();
    $("#windowCancel", $("#configureTextButtons")[0]).val("Close");

    $("#textReadOnlyMessage").show();
    $("#textErrors").hide();
};

configureText.close = function () {
    try {
        contents[configureText.getTextEditorId()].setValue(null);
    } catch(ex) {
    }
    if (closeConfigureWidgetDivWithConfirm()) {
        closeEditor(configureText.getTextEditorId());
    }
};
