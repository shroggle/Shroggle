var paymentLogs = {};

function filterPaymentLogs() {
    var paymentMethod = $("#paymentMehtodFilter")[0].options[$("#paymentMehtodFilter")[0].selectedIndex].value;
    var siteId = $("#filterBySiteId").val();
    siteId = siteId == "" ? null : siteId;

    var childSiteSettingsId = $("#filterByChildSiteSettingsId").val();
    childSiteSettingsId = childSiteSettingsId == "" ? null : childSiteSettingsId;

    var showForLogined = $("#showOnlyForLogined")[0].checked;

    var serviceCall = new ServiceCall();

    if (paymentMethod != "null") {
        serviceCall.executeViaDwr("FilterPaymentLogsService", "filter", paymentMethod, siteId, childSiteSettingsId, showForLogined, function (response) {
            $("#paymentLogsDiv").html(response);
        });
    } else {
        serviceCall.executeViaDwr("FilterPaymentLogsService", "showAll", siteId, childSiteSettingsId, showForLogined, function (response) {
            $("#paymentLogsDiv").html(response);
        });
    }
}

paymentLogs.viewError = function (viewLink) {
    var errorMessage = $(viewLink).parents("td:first").find(".paymentErrorMessage");
    errorMessage.css({display:'inline'});
    $(viewLink).html('Hide');
    viewLink.onclick = function () {
        paymentLogs.hideError(viewLink);
    };
};

paymentLogs.hideError = function (viewLink) {
    var errorMessage = $(viewLink).parents("td:first").find(".paymentErrorMessage");
    errorMessage.css({display:'none'});
    $(viewLink).html('View');
    viewLink.onclick = function () {
        paymentLogs.viewError(viewLink);
    };
};

paymentLogs.showCC = function (showLink) {
    var ccInfoDiv = $(showLink).parents("td:first").find(".paymentLogsCreditCardDetails");
    var ccInfoWindow = createConfigureWindow();
    ccInfoWindow.setContent(ccInfoDiv.html());
    getActiveWindow().resize();
};

var purchaseMailLogs = {
    showMessage : function(logId) {
        var window = createConfigureWindow({width:800, height:860});
        window.setContent($("#message" + logId).html());
        getActiveWindow().resize();
    }
};