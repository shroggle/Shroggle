var configurePageSettings = {
    pageNameTab: "PAGE_NAME",
    borderTab: "BORDER",
    backgroundTab: "BACKGROUND",
    layoutTab: "LAYOUT",
    accessibilityTab: "ACCESSIBILITY",
    htmlCssTab: "HTML_CSS",
    seoMetaTagsTab: "SEO_META_TAGS",
    seoHtmlTab: "SEO_HTML"
};

/**
 * @param settings are:
 *        - isEdit. If true, tries to get selected page from the tree and show edit page for it. False by default.
 *        - tab.
 */
configurePageSettings.show = function (settings) {
    //Default settings
    configurePageSettings.settings = {
        isEdit: false
    };

    //Applying default settings
    configurePageSettings.settings = $.extend(configurePageSettings.settings, settings);

    var pageToEditId = null;
    if (configurePageSettings.settings.isEdit) {
        pageToEditId = configurePageSettings.getPageIdToEdit();
    } else if (configurePageSettings.settings.pageId) {
        pageToEditId = configurePageSettings.settings.pageId;
    }

    var siteId = configurePageSettings.settings.siteId ? configurePageSettings.settings.siteId :
            $("#siteId").val();

    var windowWidth = isIE() ? 1090 : 1080;
    var configurePageSettingsWindow = createConfigureWindow({width:windowWidth, height:740});

    new ServiceCall().executeViaDwr("ConfigurePageSettingsService", "execute", siteId, pageToEditId, settings.tab, false, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        // If we have created default page for user to edit then we should delete it on window close.
        if (pageToEditId == null) {
            configurePageSettingsWindow.onAfterClose = function () {
                configurePageSettings.removeDefaultPage(response.createdDefaultPageId);
            }
        }

        configurePageSettingsWindow.setContent(response.html);

        if (settings.tab == configurePageSettings.pageNameTab) {
            configurePageName.onAfterShow();
        } else if (settings.tab == configurePageSettings.borderTab) {
            configureBorder.onAfterShow();
        } else if (settings.tab == configurePageSettings.backgroundTab) {
            configureBackground.onAfterShow();
        } else if (settings.tab == configurePageSettings.accessibilityTab) {
            configureAccessibility.onAfterShow();
        }

        configurePageSettingsWindow.resize();
    });
};

configurePageSettings.showSeparateTab = function(pageId, tab) {
    var tabContentId = tab + "TabContent";

    var siteId = configurePageSettings.settings.siteId ? configurePageSettings.settings.siteId :
            $("#siteId").val();

    createLoadingArea({element:$("#twoColumnsWindow_rightColumn")[0], text: "Loading...", color:"green", guaranteeVisibility:true});

    if (isCached(tabContentId)) {
        restoreFromCache(tabContentId);

        removeLoadingArea();
    } else {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ConfigurePageSettingsService", "execute", siteId, pageId, tab, true, function (response) {
            if (!isAnyWindowOpened()) {
                return;
            }

            cacheHtml(tabContentId, response.html);

            if (tab == configurePageSettings.pageNameTab) {
                configurePageName.onAfterShow();
            } else if (tab == configurePageSettings.borderTab) {
                configureBorder.onAfterShow();
            } else if (tab == configurePageSettings.backgroundTab) {
                configureBackground.onAfterShow();
            } else if (tab == configurePageSettings.accessibilityTab) {
                configureAccessibility.onAfterShow();
            }

            removeLoadingArea();
        });
    }
};

configurePageSettings.onAfterSave = function (response, pageId, closeAfterSaving) {
    if ($("#siteEditPage")[0]) {
        //Reloading select
        reloadPageSelect(response.pageSelectHtml);

        //Reloading tree
        reloadTree(response.treeHtml);
    }

    //Closing add/edit page window. Keep it here.
    if (!closeAfterSaving) {
        getActiveWindow().enableContent();
        setWindowSettingsUnchanged();
    } else {
        closeConfigureWidgetDiv();
    }

    if ($("#siteEditPage")[0]) {
        //Selecting page in tree
        selectPageVersion(getTreeNodeById(pageId), true);

        makePageDraftVisual(window.parent.getActivePage());

        if (response.createdWidgetId) {
            setOpenConfigureWidgetFunction(response.createdWidgetId);
        }
    }
};

configurePageSettings.removeDefaultPage = function (defaultPageId) {
    dwr.engine._execute("/dwr", "DeleteDefaultPageService", "execute", defaultPageId, function () {
    });
};

configurePageSettings.getPageIdToEdit = function() {
    var selectedPage = getActivePage();
    if (selectedPage) {
        return selectedPage.pageId;
    } else {
        alert("Attempt to edit page version without selected page version in the tree.");
    }
};