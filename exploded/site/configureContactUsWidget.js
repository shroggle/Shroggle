var configureContactUsErrorFieldId = "contactUsErrors";

function ConfigureContactUses() {

}

ConfigureContactUses.prototype.onBeforeShow = function (settings) {
    this.settings = settings;
};

ConfigureContactUses.prototype.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    var self = this;

    if ($("#siteOnItemRightType").val() == "READ") {
        disableContactUs(true);
    }

    //Accuring the error block
    self.errors = new Errors({}, configureContactUsErrorFieldId);

    $("#windowApply", getActiveWindow().getWindowContentDiv()).bind("click", function () {
        self.save(false);
    });

    $("#windowSave", getActiveWindow().getWindowContentDiv()).bind("click", function () {
        self.save(true);
    });

    $("#windowCancel", getActiveWindow().getWindowContentDiv()).bind("click", function () {
        closeConfigureWidgetDivWithConfirm();
    });
};

ConfigureContactUses.prototype.save = function (closeAfterSaving) {
    this.errors.clear();

    var request = {
        widgetId: this.settings.widgetId,
        contactUsName: $("#contactUsName").val(),
        displayHeader: $("#showContactUsHeader").val(),
        email: $("#emailTextField").val(),
        header: $("#ContactUsHeader").html(),
        contactUsId: $("#selectedContactUsId").val()
    };

    if (!request.contactUsName.length > 0) {
        this.errors.set("EMPTY_CONTACT_US_NAME", $("#EmptyContactUsNameException").val(), [$("#contactUsName")[0]]);
    }

    if (!request.email.length > 0) {
        this.errors.set("EMPTY_CONTACT_US_EMAIL", $("#InvalidContactUsEmail").val(), [$("#emailTextField")[0]]);
    }

    if (this.errors.hasErrors()) {
        return;
    }

    var collectingCompletedSuccessfully = collectFormItems(request);
    if (!collectingCompletedSuccessfully) {
        this.errors.set("DUBLICATE_ITEMS_EXCEPTION", $("#dublicateFieldExceptionText").html(), []);
        return;
    }

    var self = this;

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ContactUsNameNotUniqueException",
            self.errors.exceptionAction({errorId:"ContactUsNameNotUniqueException", fields:[$("#contactUsName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageBreakBeforeRequiredFieldsException",
            self.errors.exceptionAction({errorId:"PageBreakBeforeRequiredFieldsException"}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.InvalidContactUsEmailException",
            self.errors.exceptionAction({errorId:"InvalidContactUsEmailException"}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateContactUsService", "execute", request, function (response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + request.contactUsId).html(request.contactUsName);
                closeConfigureWidgetDiv();
            } else {
                if (self.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (self.settings.widgetId) {
                        closeConfigureWidgetDivWithUpdate(response);
                    } else {
                        closeConfigureWidgetDiv();
                    }
                }
            }

            if (self.settings.onAfterSave) {
                self.settings.onAfterSave();
            }

            if (!closeAfterSaving) {
                updateWidgetInfo(response);
                getActiveWindow().enableContent();
                setWindowSettingsUnchanged();
            }
        }
    });
};

// ---------------------------------------------------------------------------------------------------------------------

function disableContactUs(disable) {
    disableControl($("#contactUsName")[0], disable);
    disableControl($("#emailTextField")[0], disable);
    disableControl($("#editContactUsHeader")[0], disable);

    disableFormTables(disable);

    $("#windowSave", $("#configureContactUsButtons")[0]).hide();
    $("#windowApply", $("#configureContactUsButtons")[0]).hide();
    $("#windowCancel", $("#configureContactUsButtons")[0]).val("Close");

    $("#contactUsReadOnlyMessage").show();
    $("#contactUsErrors").hide();
}
