var configurePageName = {};

configurePageName.onAfterShow = function () {
    configurePageName.errors = new Errors({}, "configurePageNameErrors");
};

configurePageName.save = function (closeAfterSaving) {
    configurePageName.errors.clear();

    if ($("#pageName1").val().length == 0) {
        configurePageName.errors.set("EMPTY_PAGE_NAME", $("#emptyPageName").val(), [$("#pageName1")[0]]);
    }

    if (configurePageName.errors.hasErrors()) {
        return;
    }

    /*----------------------------------------------REQUEST CONSTRUCTION----------------------------------------------*/
    var request = {
        includeInMenu : $("#pageIncludeInMenus").attr('checked'),
        pageType : $("#pageType").val(),
        siteId : $("#siteId").val(),
        name : $("#pageName1").val(),
        title : $("#pageTitle").val(),
        url : $("#pageVersionUrl").val(),
        aliaseUrl : $("#pageVersionAliaseUrl").val(),
        pageToEditId : $("#pageToEditId").val(),
        keywordsGroupIds : new Array()
    };
    $("input[type='checkbox'].pageKeywordsGroupSelect").each(function () {
        if (this.checked) {
            request.keywordsGroupIds.push($(this).parents("tr:first").find(".pageKeywordsGroupId").val());
        }
    });
    request.keywords = $("#pageKeywords").val();

    /*----------------------------------------------EXCEPTION PROCESSING----------------------------------------------*/
    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageVersionNameIncorrectException",
            configurePageName.errors.exceptionAction({errorId:"pageName", fields:[$("#pageName1")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageVersionNameNotUniqueException",
            configurePageName.errors.exceptionAction({errorId:"pageName", fields:[$("#pageName1")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageVersionUrlNotUniqueException",
            configurePageName.errors.exceptionAction({errorId:"UrlNotUniqueException", fields:[$("#pageVersionUrl")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageVersionUrlIncorrectException",
            configurePageName.errors.exceptionAction({errorId:"UrlIncorrectException", fields:[$("#pageVersionUrl")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageVersionAliaseUrlNotUniqueException",
            configurePageName.errors.exceptionAction({errorId:"AliaseUrlNotUnqiueException", fields:[$("#pageVersionAliaseUrl")[0]]}));

    /*-------------------------------------------------SERVICE CALL---------------------------------------------------*/
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SavePageService", "savePageNameTab", request, function (response) {
        configurePageSettings.onAfterSave(response, $("#pageToEditId").val(), closeAfterSaving);
    });
};

configurePageName.addPageNameToPageUrlAndTitleAsUserTypes = function (str) {
    $("#pageVersionUrl").val(str.replace(/[^a-zA-Z0-9_+]/g, "-"));
    $("#pageTitle").val(str);
};
