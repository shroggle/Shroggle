var configureTellFriend = {};

configureTellFriend.onBeforeShow = function (settings) {
    configureTellFriend.settings = settings;
};

configureTellFriend.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    if ($("#siteOnItemRightType").val() == "READ") {
        configureTellFriend.disable();
    }

    configureTellFriend.errors = new Errors({}, "tellFriendErrors");
};

configureTellFriend.save = function (closeAfterSaving) {
    configureTellFriend.errors.clear();

    var request = {
        widgetId: configureTellFriend.settings.widgetId,
        mailSubject: $("#configureTellFriendMailSubject").val(),
        mailText: $("#configureTellFriendMailText").val(),
        tellFriendId: $("#selectedTellFriendId").val(),
        tellFriendName: $("#tellFriendName").val(),
        sendEmails: $("#sendEmails")[0].checked
    };

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.TellFriendNameNotUniqueException",
            configureTellFriend.errors.exceptionAction({errorId:"name", fields:[$("#tellFriendName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.TellFriendNameNullOrEmptyException",
            configureTellFriend.errors.exceptionAction({errorId:"name", fields:[$("#tellFriendName")[0]]}));

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveTellFriendService", "execute", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + request.tellFriendId).html(request.tellFriendName);

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureTellFriend.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureTellFriend.settings.widgetId) {
                    closeConfigureWidgetDivWithUpdate(response);
                } else {
                    closeConfigureWidgetDiv();
                }
            }
        }

        if (!closeAfterSaving) {
            updateWidgetInfo(response);
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    });
};

configureTellFriend.disable = function () {
    disableControl($("#configureTellFriendMailSubject")[0]);
    disableControl($("#configureTellFriendMailText")[0]);
    disableControl($("#tellFriendName")[0]);

    $("#windowSave", $("#configureTellFriendButtons")[0]).hide();
    $("#windowApply", $("#configureTellFriendButtons")[0]).hide();
    $("#windowCancel", $("#configureTellFriendButtons")[0]).val("Close");

    $("#tellFriendReadOnlyMessage").show();
    $("#tellFriendErrors").hide();
};

function disableSendingEmailsArea(disabled) {
    $("#configureTellFriendMailSubject")[0].disabled = disabled;
    $("#configureTellFriendMailText")[0].disabled = disabled;
}