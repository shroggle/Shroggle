var requestContent = {};
requestContent.errors = undefined;

requestContent.showRequestContent = function (targetSiteId) {
    var serviceCall = new ServiceCall();

    var showRequestContentWindow = createConfigureWindow({width:900, height:500});
    serviceCall.executeViaDwr("ShowRequestContentService", "execute", targetSiteId, function (html) {
        if (!isAnyWindowOpened()) {
            return;
        }

        showRequestContentWindow.setContent(html);
        $("#requestContentCancel").bind("click", function () {
            closeConfigureWidgetDiv();
        });

        requestContent.errors = new Errors();
    });
};

// ---------------------------------------------------------------------------------------------------------------------

requestContent.selectItem = function(selectedItemDiv) {
    $(".requestContentSelectedItem").removeClass("requestContentSelectedItem");
    $(selectedItemDiv).addClass("requestContentSelectedItem");
};

// ---------------------------------------------------------------------------------------------------------------------

requestContent.selectSite = function(selectedSiteDiv) {
    $(".requestContentSelectedSite").removeClass("requestContentSelectedSite");
    $(selectedSiteDiv).addClass("requestContentSelectedSite");
    var siteId = $(selectedSiteDiv).attr("siteId");

    $(selectedSiteDiv).find("img").css("display", "inline");
    new ServiceCall().executeViaDwr("GetRequestContentService", "execute", siteId, function (response) {
        var requestContentItemsHtml = "";
        if (response.items.length > 0) {
            for (var i = 0; i < response.items.length; i++) {
                var item = response.items[i];
                var requestContentItemName = item.type.toLowerCase() + ": " + item.name;
                requestContentItemsHtml += "<div itemId='" + item.itemId + "'" +
                        " onclick=requestContent.selectItem(this);" + 
                        " itemType='" + item.type + "'" + (i % 2 == 0 ? "class='even'" : "") + ">" +
                        limitName(requestContentItemName, 48) + "</div>";
            }
        } else {
            requestContentItemsHtml = "<div>" + $("#requestContentNoItems").val() + "</div>";
        }
        $("#requestContentItems").html(requestContentItemsHtml);
        $(selectedSiteDiv).find("img").hide();
    });
};

// ---------------------------------------------------------------------------------------------------------------------

requestContent.sendRequestContent = function() {
    $("#sendRequestContentSuccess").hide();
    requestContent.errors.clear();

    var targetSiteId = $("#requestContentTargetSites > option:selected").val();
    var itemId = $("#requestContentItems > .requestContentSelectedItem").attr("itemId");
    var itemType = $("#requestContentItems > .requestContentSelectedItem").attr("itemType");
    var siteId = $(".requestContentSelectedSite").attr("siteId");
    var requestContentNote = $("#requestContentNote").val();

    if (!siteId) {
        requestContent.errors.set("EMPTY_SITE_OWNER", $("#siteOwnerIsNotSelectedError").val(), [$("#requestContentSites")[0]]);
        return;
    }

    if (!itemId) {
        requestContent.errors.set("CONTENT_MODULE_NOT_SELECTED", $("#contentModuleNotSelectedError").val(), [$("#requestContentItems")[0]]);
        return;
    }

    if (targetSiteId > -1 && itemId > -1) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler("com.shroggle.exception.SiteOnItemRightExistException", function () {
            requestContent.errors.set("ALREDY_CONNECTED", $("#siteAlredyConnected").val(), [$("#requestContentSites")[0]]);
        });

        serviceCall.executeViaDwr("SendRequestContentService", "execute", targetSiteId, itemId, itemType,
                requestContentNote, function () {
            $("#sendRequestContentSuccess").show();
            getActiveWindow().resize();
        });
    }
};