var configureMediaBlockSettings = {
    fontsColorsTab: "FONTS_COLORS",
    borderTab: "BORDER",
    backgroundTab: "BACKGROUND"
};

configureMediaBlockSettings.show = function(widgetId, tab) {
    var windowWidth = isIE() ? 1090 : 1080;
    var widgetVisualSettingsWindow = createConfigureWindow({width:windowWidth, height:740});

    new ServiceCall().executeViaDwr("ConfigureMediaBlockSettingsService", "execute", widgetId, tab, false, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }
        widgetVisualSettingsWindow.setContent(response);

        if (tab == configureMediaBlockSettings.borderTab) {
            configureBorder.onAfterShow();
        } else if (tab == configureMediaBlockSettings.backgroundTab) {
            configureBackground.onAfterShow();
        } else if (tab == configureMediaBlockSettings.fontsColorsTab) {
            configureFontsAndColors.onAfterShow();
        }
    });
};

configureMediaBlockSettings.showSeparateTab = function(widgetId, tab) {
    var tabContentId = tab + "TabContent";

    createLoadingArea({element:$("#twoColumnsWindow_rightColumn")[0], text: "Loading...", color:"green", guaranteeVisibility:true});

    if (isCached(tabContentId)) {
        restoreFromCache(tabContentId);

        removeLoadingArea();
    } else {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ConfigureMediaBlockSettingsService", "execute",
                widgetId, tab, true, function (html) {
            if (!isAnyWindowOpened()) {
                return;
            }

            cacheHtml(tabContentId, html);

            if (tab == configureItemSettings.fontsColorsTab) {
                configureFontsAndColors.onAfterShow();
            } else if (tab == configureItemSettings.borderTab) {
                configureBorder.onAfterShow();
            } else if (tab == configureItemSettings.backgroundTab) {
                configureBackground.onAfterShow();
            }

            removeLoadingArea();
        });
    }
};
