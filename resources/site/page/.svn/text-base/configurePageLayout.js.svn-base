var configurePageLayout = {};

configurePageLayout.save = function (pageId, closeAfterSaving) {
    var selectedLayoutFile = null;
    var pageLayouts = document.getElementsByName("layout");
    for (var i = 0; i < pageLayouts.length; i++) {
        if (pageLayouts[i].checked) {
            selectedLayoutFile = pageLayouts[i].value;
        }
    }

    if (!selectedLayoutFile) {
        alert($("#layoutNotSelected").val());
        return;
    }

    var selectThemeFile = null;
    var themeFiles = document.getElementsByName("themeCssFile");
    for (var j = 0; j < themeFiles.length; j++) {
        if (themeFiles[j].checked) {
            selectThemeFile = themeFiles[j].value;
        }
    }

    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SetPageLayoutService", "execute", pageId, selectedLayoutFile, selectThemeFile, function (response) {
        configurePageSettings.onAfterSave(response, pageId, closeAfterSaving);
    });
};
