var configureManageVotes = {
    errors: undefined
};

configureManageVotes.onBeforeShow = function (settings) {
    configureManageVotes.settings = settings;
};

configureManageVotes.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    if ($("#siteOnItemRightType").val() == "READ") {
        disableManageYourVotesWidget();
    }

    $("td.manageYourVotesColorPickerBlock").each(function () {
        renderColorPicker($(this).attr("colorCode"), this.id);
    });

    getActiveWindow().manageVotes = true;
    configureManageVotes.errors = new Errors({}, "manageVotesErrors");

};

configureManageVotes.save = function (closeAfterSaving) {
    configureManageVotes.errors.clear();
    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ManageVotesNotUniqueNameException",
            configureManageVotes.errors.exceptionAction({errorId:"ManageVotesNotUniqueNameException", fields:[$("#manageVotesName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.ManageVotesNullOrEmptyNameException",
            configureManageVotes.errors.exceptionAction({errorId:"ManageVotesNullOrEmptyNameException", fields:[$("#manageVotesName")[0]]}));

    getActiveWindow().disableContentBeforeSaveSettings();
    var request = {
        widgetId: configureManageVotes.settings.widgetId,
        manageVotesId: $("#selectedManageVotesId").val(),
        name: $("#manageVotesName").val(),
        description: $("#ManageVotesHeader").html(),
        showDescription: $("#showManageVotesHeader").val(),
        showVotingModulesFromCurrentSite: $("#currentSiteOnly").attr("checked"),
        pickAWinner: $("#pickAWinner").attr("checked"),
        manageVotesGallerySettingsListChecked: collectManageVotesGallerySettingsList(true),
        manageVotesGallerySettingsListUnchecked: collectManageVotesGallerySettingsList(false)
    };
    serviceCall.executeViaDwr("SaveManageVotesService", "save", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + $("#selectedManageVotesId").val()).html($("#manageVotesName").val());

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureManageVotes.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureManageVotes.settings.widgetId) {
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

function renderColorPicker(value, renderToId) {
    new ColorPicker({
        renderTo: renderToId,
        width: 160,
        notSetWidth: true,
        textInputFieldWidth: 125,
        selectedColor : value,
        onColorStateChanged : function() {
            setWindowSettingsChanged();
        }
    });

    getActiveWindow().resize();
}

function collectManageVotesGallerySettingsList(checked) {
    var list = new Array();
    $(".manageVotesGallerySettings").each(function() {
        if ($(this).find(".manageVotesGalleryChecked").attr("checked") == checked) {
            var manageVotesGallerySettings = new Object();
            manageVotesGallerySettings.customName = $(this).find(".manageVotesGallerySettingsCustomName").val();
            manageVotesGallerySettings.galleryCrossWidgetId = $(this).find(".manageVotesCrossWidgetId > option:selected").val();
            manageVotesGallerySettings.colorCode = $(this).find(".color_picker_text").val();
            manageVotesGallerySettings.formItemId = $(this).find(".manageVotesRecordName > option:selected").val();

            list.push(manageVotesGallerySettings);
        }
    });
    return list;
}

function getAvailableManageVotesGallerySettingsList() {
    var byCurrentSite = $("#currentSiteOnly").attr("checked");
    var siteId = $("#manageVotesSiteId").val();
    var selectedManageVotesId = $("#selectedManageVotesId").val();

    new ServiceCall().executeViaDwr("ConfigureManageVotesService", "getAvailableVotingModulesList", byCurrentSite, siteId, selectedManageVotesId, function (response) {
        $("#manageVotesGallerySettingsTable").html(response);

        $("td.manageYourVotesColorPickerBlock").each(function () {
            renderColorPicker($(this).attr("colorCode"), this.id);
        });

        getActiveWindow().resize();
    });
}

function showVotingModulesMoreInfo() {
    var moreInfoWindow = createConfigureWindow({width:400, height: 200});
    moreInfoWindow.setContent($("#votingModulesMoreInfoText")[0].innerHTML);
}

function showPickAWinnerMoreInfo() {
    var moreInfoWindow = createConfigureWindow({width:400, height: 200});
    moreInfoWindow.setContent($("#pickAWinnerMoreInfo")[0].innerHTML);
}

function disableManageYourVotesWidget() {
    disableControl($("#manageVotesName")[0]);
    disableControl($("#manageVotesDescription")[0]);
    disableControl($("#currentSiteOnly")[0]);
    disableControl($("#allAvailble")[0]);

    disableControls($("#manageVotesGallerySettingsTable").find("input[type='text'], select, input[type='checkbox']"));

    disableControl($("#pickAWinner")[0]);

    $("#windowSave", $("#configureManageVotesButtons")[0]).hide();
    $("#windowApply", $("#configureManageVotesButtons")[0]).hide();
    $("#windowCancel", $("#configureManageVotesButtons")[0]).val("Close");

    $("#manageVotesReadOnlyMessage").show();
    $("#manageVotesErrors").hide();
}