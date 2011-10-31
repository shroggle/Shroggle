function tellFriendSend(widgetId, siteShowOption, siteId) {
    if ($("#tellFriendEmailTo" + widgetId)[0].value.split(/[;|,]/g).length > 20) {
        $("#tellFriendResult" + widgetId).html("Please limit the number of email addresses to 20.");
        return;
    }

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler("com.shroggle.exception.TellFriendCantSendException", function () {
        $("#tellFriendResult" + widgetId).html("Your message has not been sent. Please, fill email fields correctly.");
        $("#tellFriendResult" + widgetId).css("color", "red");
    });

    serviceCall.executeViaDwr("SendTellFriendService", "execute", {
        widgetId: widgetId,
        siteId: siteId,
        ccMe: $("#tellFriendCcMe" + widgetId)[0].checked,
        emailFrom: $("#tellFriendEmailFrom" + widgetId)[0].value,
        emailTo: $("#tellFriendEmailTo" + widgetId)[0].value,
        email: $("#tellFriendEmail" + widgetId)[0].value,
        parameters: (window.pageParameters ? window.pageParameters.asUrl() : "") + window.location.hash,
        siteShowOption: siteShowOption
    }, function () {
        $("#tellFriendResult" + widgetId).html("Your message has been sent.");
        $("#tellFriendResult" + widgetId).css("color", "green");
        $("#tellFriendEmail" + widgetId)[0].value = "";
        $("#tellFriendEmailTo" + widgetId)[0].value = "";
        $("#tellFriendEmailFrom" + widgetId)[0].value = "";
        $("#tellFriendCcMe" + widgetId).attr("checked", false);
    });
}

function tellFriendShow(widgetId) {
    $($("#tellFriendShow" + widgetId)[0]).hide();
    $($("#tellFriendDiv" + widgetId)[0]).show();
}

function tellFriendClose(widgetId) {
    $($("#tellFriendDiv" + widgetId)[0]).hide();
    $($("#tellFriendShow" + widgetId)[0]).show();
}