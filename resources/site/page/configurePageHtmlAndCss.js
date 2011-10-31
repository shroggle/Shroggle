var configurePageHtmlAndCss = {};

configurePageHtmlAndCss.save = function (pageId, closeAfterSaving) {
    var pageVersionHtml = $("#pageVersionHtml").val();
    var pageVersionThemeCss = $("#pageVersionThemeCss").val();

    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SavePageHtmlAndCssService", "execute",
            pageId, pageVersionHtml, pageVersionThemeCss, function (response) {
        configurePageSettings.onAfterSave(response, pageId, closeAfterSaving);
    });
};

configurePageHtmlAndCss.selectHtmlOrCss = function(htmlOrCss) {
    var pageVersionThemeCssTab = $("#pageVersionThemeCssTab")[0];
    var pageVersionHtmlTab = $("#pageVersionHtmlTab")[0];
    if (htmlOrCss.selectedIndex > 0) {
        // show css for this page version
        $(pageVersionHtmlTab).hide();
        $(pageVersionThemeCssTab).show();
    } else {
        // show html for this page version
        $(pageVersionHtmlTab).show();
        $(pageVersionThemeCssTab).hide();
    }
};

configurePageHtmlAndCss.resetHtml = function(pageId) {
    new ServiceCall().executeViaDwr("GetPageVersionTemplateHtmlService", "execute", pageId, function (html) {
        $("#pageVersionHtml").val(html);
    });
};

configurePageHtmlAndCss.resetCss = function(pageId) {
    new ServiceCall().executeViaDwr("GetPageVersionTemplateCssService", "execute", pageId, function (css) {
        $("#pageVersionThemeCss").val(css);
    });
};
