var configureFontsAndColors = {};

configureFontsAndColors.onAfterShow = function () {
    if ($("#siteOnItemRightType").val() == "READ") {
        configureFontsAndColors.disable();
    }
};

configureFontsAndColors.save = function (widgetId, draftItemId, closeAfterSaving) {
    var request = new Object();
    request.widgetId = widgetId;
    request.draftItemId = draftItemId;
    request.cssParameters = new Array();
    request.saveCssInCurrentPlace = ($("#saveCssInCurrentPlace").attr("checked") || false);
    var cssParametersValue = document.getElementsByName("widgetCssParameter");
    var cssParametersName = document.getElementsByName("widgetCssParameterName");
    var cssParametersSelector = document.getElementsByName("widgetCssParameterSelector");
    var cssParametersDescription = document.getElementsByName("widgetCssParameterDescription");
    for (var i = 0; i < cssParametersValue.length; i++) {
        var cssParameter = new Object();
        cssParameter.name = cssParametersName[i].value;
        cssParameter.selector = cssParametersSelector[i].value;
        cssParameter.description = cssParametersDescription[i].value;
        cssParameter.value = cssParametersValue[i].value;
        request.cssParameters.push(cssParameter);
    }

    var serviceCall = new ServiceCall();


    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateFontsAndColorsService", "executeForWidget", request, function (response) {
        if (response && !$("#dashboardPage")[0]) {
            makePageDraftVisual(window.parent.getActivePage());
            setStyleInnerHtml(getIFrameDocument(window.parent.document.getElementById("site")).getElementById("cssParameterStyle"), response);
        }
        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
            if (widgetId) {
                setWidgetSelected(true, true);
            }
        } else {
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    });
};

configureFontsAndColors.disable = function () {
    disableControls(document.getElementsByName("widgetCssParameter"));

    $("#forItemDiv", $("#configureFontsColorsButtons")).css("visibility", "hidden");
    $("#windowSave", $("#configureFontsColorsButtons")[0]).hide();
    $("#windowApply", $("#configureFontsColorsButtons")[0]).hide();
    $("#windowCancel", $("#configureFontsColorsButtons")[0]).val("Close");

    $("#fontsAndColorsReadOnlyMessage").show();
};