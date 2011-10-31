var configureRegistration = {};
var configureRegistrationErrorFieldId = "registrationErrors";

configureRegistration.onBeforeShow = function (settings) {
    configureRegistration.settings = settings;
};

configureRegistration.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureRegistration.errors = new Errors({}, configureRegistrationErrorFieldId);

    //Disabling form if it's shared in read-only mode.
    if ($("#siteOnItemRightType").val() == "READ") {
        configureRegistration.disableRegistrationForm(true);
    }

    //Disabling or enabling terms and conditions on startup
    configureRegistration.disableTermsAndConditions();

    getActiveWindow().resize();
    getActiveWindow().enableContent();
};

configureRegistration.save = function(closeAfterSaving) {
    var serviceCall = new ServiceCall();

    configureRegistration.errors.clear();

    if ($("#registrationFormName").val() == "") {
        configureRegistration.errors.set("RegistrationFormNullOrEmptyNameException", $("#RegistrationFormNullOrEmptyNameException").val(),
                [$("#registrationFormName")[0]]);
        return;
    }

    var formId = $("#selectedFormId").val();
    var request = {
        widgetId: configureRegistration.settings.widgetId,
        formId: formId == -1 ? null : formId,
        formName: $("#registrationFormName").val(),
        formHeader: $("#RegistrationHeader")[0].innerHTML,
        defaultForm: $("#registrationDefaultForm").attr("checked"),
        showHeader: $("#showRegistrationHeader")[0].value,
        requireTermsAndConditions: $("#requireTermsAndConditions").attr("checked"),
        termsAndConditions: $("#registrationTermsAndConditionsHeader").html(),
        networkRegistration: $("#networkRegistration").attr("checked"),
        groupsWithTime : groups.getGroupsWithTimeInterval()
    };

    var collectingCompletedSuccessfully = collectFormItems(request);
    if (!collectingCompletedSuccessfully) {
        configureRegistration.errors.set("DUBLICATE_ITEMS_EXCEPTION", $("#dublicateFieldExceptionText").html(), []);
        return;
    }


    serviceCall.addExceptionHandler(
            "com.shroggle.exception.RegistrationFormNotUniqueNameException",
            configureRegistration.errors.exceptionAction({errorId:"RegistrationFormNotUniqueNameException", fields:[$("#registrationFormName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.RegistrationFormNullOrEmptyNameException",
            configureRegistration.errors.exceptionAction({errorId:"RegistrationFormNullOrEmptyNameException", fields:[$("#registrationFormName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageBreakBeforeRequiredFieldsException",
            configureRegistration.errors.exceptionAction({errorId:"PageBreakBeforeRequiredFieldsException"}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveRegistrationService", "execute", request, function (response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + formId).html($("#registrationFormName").val());

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureRegistration.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (configureRegistration.settings.widgetId) {
                        closeConfigureWidgetDivWithUpdate(response);
                    } else {
                        closeConfigureWidgetDiv();
                    }
                }
            }

            if (configureRegistration.settings.onAfterSave) {
                configureRegistration.settings.onAfterSave(request);
            }

            if (!closeAfterSaving) {
                updateWidgetInfo(response);
                getActiveWindow().enableContent();
                setWindowSettingsUnchanged();
            }
        }
    });
};

configureRegistration.disableTermsAndConditions = function () {
    disableControl($("#editTermsAndConditions")[0], !$("#requireTermsAndConditions").attr("checked"));
};

configureRegistration.disableRegistrationForm = function(disable) {
    disableControl($("#registrationFormName")[0], disable);
    disableControl($("#registrationDefaultForm")[0], disable);
    disableControl($("#editRegistrationHeader")[0], disable);

    disableFormTables(disable);

    $("#windowSave", $("#configureRegistrationButtons")[0]).hide();
    $("#windowApply", $("#configureRegistrationButtons")[0]).hide();
    $("#windowCancel", $("#configureRegistrationButtons")[0]).val("Close");

    $("#registrationReadOnlyMessage").show();
    $("#registrationErrors").hide();
};