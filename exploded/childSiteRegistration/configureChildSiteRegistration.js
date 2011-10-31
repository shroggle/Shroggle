var configureChildSiteRegistration = {
    settings:undefined,
    errors: undefined
};

var configureChildSiteRegistrationErrorFieldId = "childSiteRegistrationErrors";

configureChildSiteRegistration.onBeforeShow = function(settings) {
    var defaultSettings = {showSecondTab: false};
    settings = $.extend(defaultSettings, settings);

    configureChildSiteRegistration.settings = settings;

    return settings;
};

configureChildSiteRegistration.onAfterShow = function() {
    if (!isAnyWindowOpened()) {
        return;
    }

    //Disabling form if it's shared in read-only mode.
    if ($("#siteOnItemRightType").val() == "READ") {
        disableChildSiteRegistrationForm(true);
    } else {
        showLogoUploader();
        showFooterImageUploader();
    }

    if (configureChildSiteRegistration.settings.showSecondTab) {
        $("#childSiteRegistrationNetworkSettingsTab").click();
    }

    //Acquring the error block
    configureChildSiteRegistration.errors = new Errors({}, configureChildSiteRegistrationErrorFieldId);
    getActiveWindow().resize();
};

configureChildSiteRegistration.save = function (closeAfterSaving) {
    configureChildSiteRegistration.errors.clear();
    if (!$("#iAgreeTermsAndConditions").attr("checked")) {
        configureChildSiteRegistration.errors.set("CHECK_TERMS_AND_CONDITIONS", $("#termsAndConditionsError").val(),
                [$("#iAgreeTermsAndConditionsCheckboxWithlabel")[0]]);
        return;
    }

    var request;
    var saveMethod;
    if (configureChildSiteRegistration.isFormSettingsTabSelected()) {
        request = createRequestForFormSettingsTabAndCheckErrors();
        saveMethod = "saveFormSettingsTab";
    } else if (configureChildSiteRegistration.isWhiteLabelSettingsTabSelected()) {
        request = createRequestForWhiteLabelSettingsTabAndCheckErrors();
        saveMethod = "saveWhiteLabelSettingsTab";
    } else {
        request = createRequestForNetworkSettingsTabAndCheckErrors();
        saveMethod = "saveNetworkSettingsTab";
    }

    if (configureChildSiteRegistration.errors.hasErrors()) {
        configureChildSiteRegistration.showNetworkSettingsTab();
        return;
    }

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PageBreakBeforeRequiredFieldsException",
            configureChildSiteRegistration.errors.exceptionAction({errorId:"PageBreakBeforeRequiredFieldsException"}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PaymentBlockOnFirstPageException",
            configureChildSiteRegistration.errors.exceptionAction({errorId:"PaymentBlockOnFirstPageException"}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ChildSiteRegistrationNameNotUnique",
            configureChildSiteRegistration.errors.exceptionAction({errorId:"NOT_UNIQUE_CHILD_SITE_REGISTRATION_NAME", fields:[$("#childSiteRegistrationName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();

    serviceCall.executeViaDwr("CreateChildSiteRegistrationService", saveMethod, request, function (response) {
        configureChildSiteRegistration.handleSaveResponse(response, request.formId, request.name, closeAfterSaving);
    });

    /*-----------------------------------------------Internal functions-----------------------------------------------*/
    function createRequestForFormSettingsTabAndCheckErrors() {
        /*----------------------------------------------Creating Request----------------------------------------------*/
        var request = {
            widgetId: configureChildSiteRegistration.settings.widgetId,
            formId: $("#selectedChildSiteRegistrationId").val(),
            name: $("#childSiteRegistrationName").val(),
            description: $("#ChildSiteRegistrationHeader").html(),
            displayDescription: $("#showChildSiteRegistrationHeader").val()
        };
        /*----------------------------------------------Creating Request----------------------------------------------*/

        /*--------------------------------------------Checking for errors---------------------------------------------*/
        var collectingCompletedSuccessfully = collectFormItems(request);
        if (!collectingCompletedSuccessfully) {
            configureChildSiteRegistration.errors.set("DUBLICATE_ITEMS_EXCEPTION", $("#dublicateFieldExceptionText").html(), []);
        }
        /*--------------------------------------------Checking for errors---------------------------------------------*/
        return request;
    }


    function createRequestForNetworkSettingsTabAndCheckErrors() {
        /*----------------------------------------------Creating Request----------------------------------------------*/
        var request = {
            widgetId: configureChildSiteRegistration.settings.widgetId,
            formId: $("#selectedChildSiteRegistrationId").val(),
            startDate: $("#startDateText").val(),
            endDate: $("#endDateText").val(),
            useStartDate: $("#startDate").attr("checked"),
            useEndDate: $("#endDate").attr("checked"),
            useOneTimeFee: $("#useOneTimeFee").attr("checked"),
            templatesId: getSelectedBlueprints(),
            requiredToUseSiteBlueprint: $("#userIsRequiredToUseASiteBlueprint").attr("checked"),
            useOwnAuthorize:$("#ownAuthorize").attr("checked"),
            useOwnPaypal:$("#ownPaypal").attr("checked"),
            authorizeLogin:$("#authorizeLogin").val(),
            authorizeTransactionKey:$("#authorizeTransactionKey").val(),
            paypalApiUserName:$("#paypalApiUserName").val(),
            paypalApiPassword:$("#paypalApiPassword").val(),
            paypalSignature:$("#paypalSignature").val()
        };

        if ($("#adminAccessLevel").attr("checked")) {
            request.accessLevel = "ADMINISTRATOR";
        } else if ($("#siteEditorAccessLevel").attr("checked")) {
            request.accessLevel = "EDITOR";
        } else {
            request.accessLevel = null;
        }

        if (request.useOneTimeFee) {
            request.oneTimeFee = $("#oneTimeFee").val().trim();
            request.price250mb = "0";
            request.price500mb = "0";
            request.price1gb = "0";
            request.price3gb = "0";
        } else {
            request.price250mb = $("#price250mb").val().trim();
            request.price500mb = $("#price500mb").val().trim();
            request.price1gb = $("#price1gb").val().trim();
            request.price3gb = $("#price3gb").val().trim();
            request.oneTimeFee = "0";
        }
        /*----------------------------------------------Creating Request----------------------------------------------*/

        /*--------------------------------------------Checking for errors---------------------------------------------*/
        checkDate((request.startDate).trim(), (request.endDate).trim(), true, true);

        checkNetworkPrices();

        if (request.requiredToUseSiteBlueprint && request.templatesId.length == 0) {
            configureChildSiteRegistration.errors.set("EMPTY_CHILD_SITE_TEMPLATES_ID",
                    $("#atLeastOneBlueprintHasToBeSelected").val(), [$("#selectTemplatesSpan")[0]]);
        }

        if (request.useOwnAuthorize) {
            if (request.authorizeLogin.trim().length == 0) {
                configureChildSiteRegistration.errors.set("EMPTY_AUTHORIZE_LOGIN",
                        $("#authorizeLoginCannotBeEmpty").val(), [$("#authorizeLogin")[0]]);
            }
            if (request.authorizeTransactionKey.trim().length == 0) {
                configureChildSiteRegistration.errors.set("EMPTY_AUTHORIZE_TRANSACTION_KEY",
                        $("#authorizeTransactionKeyCannotBeEmpty").val(), [$("#authorizeTransactionKey")[0]]);
            }
        }
        if (request.useOwnPaypal) {
            if (request.paypalApiUserName.trim().length == 0) {
                configureChildSiteRegistration.errors.set("EMPTY_PAYPAL_USERNAME",
                        $("#paypalApiUserNameCannotBeEmpty").val(), [$("#paypalApiUserName")[0]]);
            }
            if (request.paypalApiPassword.trim().length == 0) {
                configureChildSiteRegistration.errors.set("EMPTY_PAYPAL_PASSWORD",
                        $("#paypalApiPasswordCannotBeEmpty").val(), [$("#paypalApiPassword")[0]]);
            }
            if (request.paypalSignature.trim().length == 0) {
                configureChildSiteRegistration.errors.set("EMPTY_PAYPAL_SIGNATURE",
                        $("#paypalSignatureCannotBeEmpty").val(), [$("#paypalSignature")[0]]);
            }
        }
        /*--------------------------------------------Checking for errors---------------------------------------------*/
        return request;
    }

    function createRequestForWhiteLabelSettingsTabAndCheckErrors() {
        /*----------------------------------------------Creating Request----------------------------------------------*/
        var request = {
            widgetId: configureChildSiteRegistration.settings.widgetId,
            formId: $("#selectedChildSiteRegistrationId").val(),
            brandedAllowShroggleDomain: $("#whiteLabelBrandedUrlDefault").attr("checked"),
            brandedUrl: $("#whiteLabelBrandedUrl").val(),
            footerText: $("#whiteLabelFooterText").val(),
            footerUrl: $("#whiteLabelFooterUrl").val(),
            contactUsPageId : $("#contactUsPages").val(),
            footerImageId: getFooterImageId(),
            logoId: getLogoId(),
            fromEmail: $("#fromEmail").val(),
            termsAndConditions: $("#ChildSiteRegistrationTermsAndConditionsHeader").html(),
            emailText: $("#ChildSiteRegistrationCustomizeEmailTextHeader").html(),
            thanksForRegisteringText: $("#ChildSiteRegistrationThanksForRegisteringTextHeader").html(),
            welcomeText: $("#ChildSiteRegistrationCustomizeWelcomeTextHeader").html()
        };
        if (!request.fromEmail.length > 0) {
            configureChildSiteRegistration.errors.set("EMPTY_CHILD_SITE_REGISTRATION_FROM_EMAIL",
                    $("#childSiteRegistrationFromEmailCannotBeEmpty").val(), [$("#fromEmail")[0]]);
        }
        return request;
    }

    /*-----------------------------------------------Internal functions-----------------------------------------------*/
};

configureChildSiteRegistration.handleSaveResponse = function(response, formId, name, closeAfterSaving) {
    configureChildSiteRegistration.errors.clear();
    if (response.wrongStartDate) {
        configureChildSiteRegistration.errors.set("WRONG_START_DATE",
                $("#wrongStartDate").val(), [$("#startDateText")[0]]);
    }
    if (response.wrongEndDate) {
        configureChildSiteRegistration.errors.set("WRONG_END_DATE",
                $("#wrongEndDate").val(), [$("#endDateText")[0]]);
    }
    if (response.endBeforeStart) {
        configureChildSiteRegistration.errors.set("END_DATE_BEFORE_START_DATE",
                $("#endBeforeStart").val(), [$("#endDateText")[0]]);
    }
    if (response.endBeforeCurrent) {
        configureChildSiteRegistration.errors.set("END_DATE_BEFORE_CURRENT_DATE",
                $("#endDatePassed").val(), [$("#endDateText")[0]]);
    }

    if (!configureChildSiteRegistration.errors.hasErrors()) {
        if (window.tinyMCE && window.tinyMCE.activeEditor) {
            if (!window.tinyMCE.activeEditor.destroyed) {
                closeEditor(window.tinyMCE.activeEditor.editorId);
            }
        }

        if ($("#dashboardPage")[0]) {
            $("#dashboardNetworkName" + formId).html(name);
            $("#itemName" + formId).html(name);

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureChildSiteRegistration.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureChildSiteRegistration.settings.widgetId) {
                    closeConfigureWidgetDivWithUpdate(response.widgetInfo);
                } else {
                    closeConfigureWidgetDiv();
                }
            }
        }

        if (response.showIncomeSettingsWindow && closeAfterSaving) {
            if (window.tinyMCE && window.tinyMCE.activeEditor) {
                if (!tinyMCE.activeEditor.destroyed) {
                    closeEditor(tinyMCE.activeEditor.editorId);
                }
            }
            showIncomeSettingsWindowInternal(response.receivePaymentsInnerHtml);
        }
    }

    if (!closeAfterSaving) {
        updateWidgetInfo(response.widgetInfo);
        getActiveWindow().enableContent();
        setWindowSettingsUnchanged();
    }
};

function setLogoSrc(src) {
    $("#logoImage")[0].src = src;
}

function setFooterImageSrc(src) {
    $("#footerImage")[0].src = src;
}

function setLogoId(id) {
    $("#logoImageId").val(id);
}

function setFooterImageId(id) {
    $("#footerImageId").val(id);
}

function getLogoId() {
    return $("#logoImageId").val();
}

function getFooterImageId() {
    return $("#footerImageId").val();
}

// ---------------------------------------------------------------------------------------------------------------------

function getSelectedBlueprints() {
    var blueprintsId = new Array();

    $("#childSiteRegistrationBlueprints > input:checked").each(function () {
        blueprintsId.push($(this).val());
    });

    return blueprintsId;
}

// ---------------------------------------------------------------------------------------------------------------------

function disablePricesTable(disabled) {
    document.getElementById("price250mb").disabled = disabled;
    document.getElementById("price500mb").disabled = disabled;
    document.getElementById("price1gb").disabled = disabled;
    document.getElementById("price3gb").disabled = disabled;
    document.getElementById("oneTimeFee").disabled = !disabled;
}

// ---------------------------------------------------------------------------------------------------------------------

function showIncomeSettingsWindow(siteId) {
    new ServiceCall().executeViaDwr("CreateIncomeSettingsService", "execute", siteId, function(response) {
        showIncomeSettingsWindowInternal(response);
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function showIncomeSettingsWindowInternal(innerHTML) {
    var configureWidget = createConfigureWindow({width:600, height:200});
    configureWidget.setContent(innerHTML);
    configureChildSiteRegistration.errors = new Errors();
}

// ---------------------------------------------------------------------------------------------------------------------

function clearField(field) {
    if (field.value == document.getElementById("enterTotalPriceText").value) {
        field.value = "";
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function checkNetworkPrices() {
    configureChildSiteRegistration.errors.clear();
    var fields = ["price250mb", "price500mb", "price1gb", "price3gb"];
    if ($("#useOneTimeFee").attr("checked")) {
        fields = ["oneTimeFee"];
    }
    $(fields).each(function() {
        var field = $("#" + this)[0];
        var value = $(field).val().trim();
        var defaultPrice = $(field).attr("defaultPrice").trim();
        if (!isDouble(value)) {
            configureChildSiteRegistration.errors.set("EMPTY_PRICE",
                    document.getElementById("enterAPriceText").value,
                    [field]);
        } else if (parseFloat(value) < parseFloat(defaultPrice)) {
            configureChildSiteRegistration.errors.set("PRICE_LESS_THAN_DEFAULT",
                    document.getElementById("priceMessageText").value,
                    [field]);
        }
    });
}
// ---------------------------------------------------------------------------------------------------------------------

function checkDate(startDate, endDate, showStartDateErrors, showEndDateErrors) {
    var serviceCall = new ServiceCall();


    var checkStartDate = document.getElementById("startDate").checked;
    var checkEndDate = document.getElementById("endDate").checked;
    configureChildSiteRegistration.errors.clear();
    document.getElementById("startDateText").className = "";
    document.getElementById("endDateText").className = "";

    // Quick javascript checking that date is formed correctly. For example date not like this: 14/40/2010
    if (checkStartDate) {
        if (!parseDate(startDate)) {
            configureChildSiteRegistration.errors.set("WRONG_START_DATE", $("#wrongStartDate").val(), [$("#startDateText")[0]]);
        }
    }
    if (checkEndDate) {
        if (!parseDate(endDate)) {
            configureChildSiteRegistration.errors.set("WRONG_END_DATE", $("#wrongEndDate").val(), [$("#endDateText")[0]]);
        }
    }

    if (configureChildSiteRegistration.errors.hasErrors()) {
        return;
    }

    if (checkStartDate && checkEndDate) {
        serviceCall.executeViaDwr("CreateChildSiteRegistrationService", "checkDate", startDate, endDate, function (response) {
            if (response.wrongStartDate && showStartDateErrors) {
                configureChildSiteRegistration.errors.set("WRONG_START_DATE", $("#wrongStartDate").val(), [$("#startDateText")[0]]);
            }
            if (response.wrongEndDate && showEndDateErrors) {
                configureChildSiteRegistration.errors.set("WRONG_END_DATE", $("#wrongEndDate").val(), [$("#endDateText")[0]]);
            }
            if (response.endBeforeStart) {
                configureChildSiteRegistration.errors.set("END_DATE_BEFORE_START_DATE", $("#endBeforeStart").val(), [$("#endDateText")[0]]);
            }
            if (response.endBeforeCurrent) {
                configureChildSiteRegistration.errors.set("END_DATE_BEFORE_CURRENT_DATE", $("#endDatePassed").val(), [$("#endDateText")[0]]);
            }
        });
    } else if (checkStartDate) {
        serviceCall.executeViaDwr("CreateChildSiteRegistrationService", "isDateCorrect", startDate, function (response) {
            if (!response) {
                configureChildSiteRegistration.errors.set("WRONG_START_DATE", $("#wrongStartDate").val(), [$("#startDateText")[0]]);
            }
        });
    } else if (checkEndDate) {
        serviceCall.executeViaDwr("CreateChildSiteRegistrationService", "isEndDateCorrect", endDate, function (response) {
            if (response.wrongEndDate) {
                configureChildSiteRegistration.errors.set("WRONG_END_DATE", $("#wrongEndDate").val(), [$("#endDateText")[0]]);
            }
            if (response.endBeforeCurrent) {
                configureChildSiteRegistration.errors.set("END_DATE_BEFORE_CURRENT_DATE", $("#endDatePassed").val(), [$("#endDateText")[0]]);
            }
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function showNetworkSettingsHelpWindow(textId) {
    var networkSettingsHelpWindow = createConfigureWindow({width:600, height:500});
    networkSettingsHelpWindow.setContent(document.getElementById(textId).innerHTML);
}

// ---------------------------------------------------------------------------------------------------------------------

function setReceivePayments(siteId) {
    configureChildSiteRegistration.errors.clear();
    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.IncorrectEmailException",
            configureChildSiteRegistration.errors.exceptionAction({errorId:"INCORRECT_EMAIL", fields:[$("#paypalText")[0]], alternativeMessage:$("#emailAddressIsMalformed").val()}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.NullOrEmptyEmailException",
            configureChildSiteRegistration.errors.exceptionAction({errorId:"INCORRECT_EMAIL", fields:[$("#paypalText")[0]], alternativeMessage:$("#emailAddressIsRequired").val()}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.PayPalWrongEmailException",
            configureChildSiteRegistration.errors.exceptionAction({errorId:"INCORRECT_EMAIL", fields:[$("#paypalText")[0]], alternativeMessage:$("#thereIsNoPaypalAccount").val()}));
    serviceCall.executeViaDwr("CreateIncomeSettingsService", "setReceivePayments", siteId, $("#paypalText").val(), function () {
        closeConfigureWidgetDiv(true);
    });
}

function showLogoPreview() {
    return getLogoId() > 0;
}

function disableChildSiteRegistrationForm(disable) {
    disableControl($("#childSiteRegistrationName")[0], disable);
    disableControl($("#editChildSiteRegistrationHeader")[0], disable);
    disableControl($("#iAgreeTermsAndConditions")[0], disable);

    disableControl($("#useOneTimeFee")[0], disable);
    disableControl($("#oneTimeFee")[0], disable);
    disableControl($("#prices")[0], disable);

    disableControl($("#price250mb")[0], disable);
    disableControl($("#price500mb")[0], disable);
    disableControl($("#price1gb")[0], disable);
    disableControl($("#price3gb")[0], disable);

    disableControl($("#userIsRequiredToUseASiteBlueprint")[0], disable);

    disableControl($("#startDate")[0], disable);
    disableControl($("#startDateText")[0], disable);
    disableControl($("#endDate")[0], disable);
    disableControl($("#endDateText")[0], disable);

    disableControl($("#ChildSiteRegistrationTermsAndConditionsLink")[0], disable);
    disableControl($("#ChildSiteRegistrationCustomizeEmailTextLink")[0], disable);
    disableControl($("#ChildSiteRegistrationCustomizeWelcomeTextLink")[0], disable);

    disableControl($("#adminAccessLevel")[0], disable);
    disableControl($("#siteEditorAccessLevel")[0], disable);

    disableFormTables(disable);

    $("#windowSave", $("#configureCSRButtons")[0]).hide();
    $("#windowApply", $("#configureCSRButtons")[0]).hide();
    $("#windowCancel", $("#configureCSRButtons")[0]).val("Close");

    $("#csrReadOnlyMessage").show();
    $("#childSiteRegistrationErrors").hide();
}

function normalizeEmailText(textId) {
    var text = $("#" + textId).html();
    if ($("#useOneTimeFee")[0].checked) {
        text = text.replace("&lt;per month/one time fee&gt; (250mb media storage included)", "one time fee");
        text = text.replace("per month (250mb media storage included)", "one time fee");
    } else {
        text = text.replace("&lt;per month/one time fee&gt; (250mb media storage included)", "per month (250mb media storage included)");
        text = text.replace("one time fee", "per month (250mb media storage included)");
    }
    $("#" + textId).html(text);
}

configureChildSiteRegistration.isFormSettingsTabSelected = function() {
    return $("#formSettingsTab").is(':visible');
};

configureChildSiteRegistration.isWhiteLabelSettingsTabSelected = function() {
    return $("#whiteLabelSettingsTab").is(':visible');
};

configureChildSiteRegistration.showFormSettingsTab = function() {
    $("#paymentSettingsTab").hide();
    $("#formSettingsTab").show();
    $("#whiteLabelSettingsTab").hide();
    $("#csrFormHeader").show();
    $("#csrNetworkHeader").hide();
    $("#csrWhiteLabelHeader").hide();
};

configureChildSiteRegistration.showNetworkSettingsTab = function() {
    $("#formSettingsTab").hide();
    $("#paymentSettingsTab").show();
    $("#whiteLabelSettingsTab").hide();
    $("#csrFormHeader").hide();
    $("#csrNetworkHeader").show();
    $("#csrWhiteLabelHeader").hide();
};

configureChildSiteRegistration.showWhiteLabelSettingsTab = function() {
    $("#formSettingsTab").hide();
    $("#paymentSettingsTab").hide();
    $("#whiteLabelSettingsTab").show();
    $("#csrFormHeader").hide();
    $("#csrNetworkHeader").hide();
    $("#csrWhiteLabelHeader").show();
};

configureChildSiteRegistration.disableAuthorizeCredentials = function(disabled) {
    $("#authorizeLogin")[0].disabled = disabled;
    $("#authorizeTransactionKey")[0].disabled = disabled;
};

configureChildSiteRegistration.disablePaypalCredentials = function(disabled) {
    $("#paypalApiUserName")[0].disabled = disabled;
    $("#paypalApiPassword")[0].disabled = disabled;
    $("#paypalSignature")[0].disabled = disabled;
};