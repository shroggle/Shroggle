var configurePageSeoHtml = {};

configurePageSeoHtml.save = function (closeAfterSaving) {
    var request = new Object();
    request.pageToEditId = $("#pageToEditId").val();
    request.siteId = $("#siteId").val();

    request.pageSEOSettings = new Object();

    request.pageSEOSettings.htmlCodeList = new Array();
    $("#customHtmlBody").find(".customHtmlCodeRow").each(function () {
        var customHtmlCode = new Object();

        var htmlCodeName = $(this).find(".customHtmlCodeNameHiddenInput").val();
        var htmlCode = $(this).find(".customHtmlCodeTextArea").val();
        var htmlCodePlacement = $(this).find(".customHtmlCodePlacementHiddenInput").val();

        customHtmlCode.name = htmlCodeName;
        customHtmlCode.code = htmlCode;
        customHtmlCode.codePlacement = htmlCodePlacement;

        request.pageSEOSettings.htmlCodeList.push(customHtmlCode);
    });

    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SavePageService", "savePageSeoHtmlTab", request, function (response) {
        configurePageSettings.onAfterSave(response, $("#pageToEditId").val(), closeAfterSaving);
    });
};

configurePageSeoHtml.addCustomHtmlCode = function () {
    var htmlCodeName = $("#htmlCodeInput").val();
    var htmlCode = $("#htmlCodeTextArea").val();
    var htmlCodePlacement = $("input[name='htmlCodeRadio']:checked").val();

    if (htmlCodeName.length == 0 && htmlCode.length == 0) {
        $("#htmlCodeExceptionDiv").html("Please, enter HTML code name and body.");
        addFadingTimeoutEvent($("#htmlCodeExceptionDiv")[0], 4000);
        return;
    } else if (htmlCodeName.length == 0) {
        $("#htmlCodeExceptionDiv").html("Please, enter HTML code name.");
        addFadingTimeoutEvent($("#htmlCodeExceptionDiv")[0], 4000);
        return;
    } else if (htmlCode.length == 0) {
        $("#htmlCodeExceptionDiv").html("Please, enter HTML code body.");
        addFadingTimeoutEvent($("#htmlCodeExceptionDiv")[0], 4000);
        return;
    }

    $("#htmlCodeInput").val("");
    $("#htmlCodeTextArea").val("");

    $("#customHtmlBody").prepend("<tr class='customHtmlCodeRow'>" +
            "<td class='customHtmlCodeNameColumn'>" +
            "<input type='hidden' class='customHtmlCodeNameHiddenInput' value='" + htmlCodeName + "'/>" +
            htmlCodeName +
            "</td>" +
            "<td class='customHtmlCodeColumn'>" +
            "<textarea disabled='disabled' class='customHtmlCodeTextArea'>" +
            htmlCode +
            "</textarea>" +
            "</td>" +
            "<td class='customHtmlCodePlacementColumn'>" +
            "<input type='hidden' class='customHtmlCodePlacementHiddenInput' value='" + htmlCodePlacement + "'/>" +
            htmlCodePlacement +
            "</td>" +
            "<td class='customHtmlCodeDeleteColumn'>" +
            "<input type='image' class='customMetaTagDeleteImg' onclick='configurePageSeoHtml.removeCustomHtmlCode(this)'" +
            " src='/images/cross-circle.png' value='Delete'>" +
            "</td>" +
            "</tr>");

};

configurePageSeoHtml.removeCustomHtmlCode = function (element) {
    $(element).parents("tr:first").remove();
};