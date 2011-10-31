var configurePageSeoMetaTags = {};

configurePageSeoMetaTags.save = function (closeAfterSaving) {
    var request = new Object();
    request.pageToEditId = $("#pageToEditId").val();
    request.siteId = $("#siteId").val();

    request.pageSEOSettings = new Object();

    request.pageSEOSettings.pageDescription = $("#pageDescription").val();
    request.pageSEOSettings.titleMetaTag = $("#pageTitleMetaTag").val();
    request.pageSEOSettings.authorMetaTag = $("#authorMetaTag").val();
    request.pageSEOSettings.copyrightMetaTag = $("#copyrightMetaTag").val();
    request.pageSEOSettings.robotsMetaTag = $("#robotsMetaTag").val();

    request.pageSEOSettings.customMetaTagList = new Array();
    $("#customMetaTagBody").find(".customMetaTagInput").each(function () {
        request.pageSEOSettings.customMetaTagList.push($(this).val());
    });

    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SavePageService", "savePageSeoMetaTagsTab", request, function (response) {
        configurePageSettings.onAfterSave(response, $("#pageToEditId").val(), closeAfterSaving);
    });
};

configurePageSeoMetaTags.addCustomMetaTag = function () {
    var metaTagValue = $("#addCustomMetaTagInput").val();
    $("#addCustomMetaTagInput").val("");

    $("#customMetaTagBody").prepend("<tr>" +
            "<td class='addCustomMetaTagNameColumn'>" +
            "<input type='text' class='customMetaTagInput txt95' value='" + metaTagValue + "'/>" +
            "</td>" +
            "<td class='addCustomMetaTagDeleteColumn'>" +
            "<input type='image' class='customMetaTagDeleteImg' onclick='configurePageSeoMetaTags.removeCustomMetaTag(this)'" +
            " src='/images/cross-circle.png' value='Delete'>" +
            "</td>" +
            "</tr>");
};

configurePageSeoMetaTags.removeCustomMetaTag = function (element) {
    $(element).parents("tr:first").remove();
};