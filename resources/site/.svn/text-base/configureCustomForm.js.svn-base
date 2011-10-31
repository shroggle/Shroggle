var customFormErrorFieldId = "customFormErrors";

function ConfigureCustomForms() {

}

ConfigureCustomForms.prototype.onBeforeShow = function (settings) {
    this.settings = settings;
};

ConfigureCustomForms.prototype.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    var self = this;
    self.errors = new Errors({}, customFormErrorFieldId);

    if ($("#siteOnItemRightType").val() == "READ") {
        disableCustomForm(true);
    }

    $("#windowSave", getActiveWindow().getWindowContentDiv()).bind("click", function () {
        self.save(true);
    });

    $("#windowApply", getActiveWindow().getWindowContentDiv()).bind("click", function () {
        self.save(false);
    });

    $("#windowCancel", getActiveWindow().getWindowContentDiv()).bind("click", function () {
        closeConfigureWidgetDivWithConfirm();
    });
};

// ---------------------------------------------------------------------------------------------------------------------

ConfigureCustomForms.prototype.save = function (closeAfterSaving) {
    var self = this;
    this.errors.clear();

    if ($("#formName").val() == "") {
        this.errors.set("FormNameIsEmpty", $("#emptyFormName").val(), [$("#formName")[0]]);
        return;
    }

    var request = {
        widgetId: this.settings.widgetId,
        formId: $("#selectedCustomFormId").val(),
        formName: $("#formName").val(),
        formDescription: $("#CustomFormHeader").html(),
        showHeader: $("#showCustomFormHeader").val()
    };

    var collectingCompletedSuccessfully = collectFormItems(request);
    if (!collectingCompletedSuccessfully) {
        this.errors.set("DUBLICATE_ITEMS_EXCEPTION", $("#dublicateFieldExceptionText").html(), []);
        return;
    }

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.CustomFormNameNotUniqueException",
            this.errors.exceptionAction({errorId:"CustomFormNameNotUniqueException", fields:[$("#formName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.FormWithoutFormItemsException",
            this.errors.exceptionAction({errorId:"FormWithoutFormItemsException", fields:[$("#your_form_div")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageBreakBeforeRequiredFieldsException",
            this.errors.exceptionAction({errorId:"PageBreakBeforeRequiredFieldsException"}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateCustomFormService", "execute", request, function (response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + request.formId).html(request.formName);

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
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

function showCreateRegistrationFormInfoWindow() {
    var configureWindow = createConfigureWindow({width:400, height:100});
    configureWindow.setContent($("#customFormInfoWindow").html());
}

// ---------------------------------------------------------------------------------------------------------------------

function disableCustomForm(disable) {
    disableControl($("#formName")[0], disable);
    disableControl($("#editCustomFormHeader")[0], disable);

    disableFormTables(disable);

    $("#windowSave", $("#configureCustomFormButtons")[0]).hide();
    $("#windowApply", $("#configureCustomFormButtons")[0]).hide();
    $("#windowCancel", $("#configureCustomFormButtons")[0]).val("Close");

    $("#customFormReadOnlyMessage").show();
    $("#customFormErrors").hide();
}