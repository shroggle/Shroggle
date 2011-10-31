var configureAdvancedSearch = {};

/* Temporary storage for new search options that have to be saved for new advanced search. */
configureAdvancedSearch.searchOptions = new Array();

configureAdvancedSearch.onBeforeShow = function (settings) {
    configureAdvancedSearch.settings = settings;
};

configureAdvancedSearch.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    configureAdvancedSearch.clearSearchOptionsArray();
    configureAdvancedSearch.errors = new Errors({}, "advancedSearchErrors");

    //Disabling 'manage records' and 'edit selected gallery links' if we need to.
    if ($("#selectedGalleryId").val() != "null" && $("#selectedGalleryId").val() != "-1") {
        configureAdvancedSearch.disableConfigureGalleryLink(false);
    } else {
        configureAdvancedSearch.disableConfigureGalleryLink(true);
        configureAdvancedSearch.disableManageRecordsLink(true);
    }

    //Disabling form if it's shared in read-only mode.
    if ($("#siteOnItemRightType").val() == "READ") {
        configureAdvancedSearch.disableAdvancedSearch();
    }

    getActiveWindow().resize();
    getActiveWindow().enableContent();
};

configureAdvancedSearch.save = function (closeAfterSaving) {
    configureAdvancedSearch.errors.clear();

    if (configureAdvancedSearch.getSelectedFormId() == -1 && $("#recordsSourceGallery").is(":checked")) {
        configureAdvancedSearch.errors.set("AdvancedSearchGalleryNotSelectBeforeSaveException", $("#AdvancedSearchGalleryNotSelectBeforeSaveException").val(),
                [$("#recordsSourceGallery")[0]]);
        return;
    }

    if (configureAdvancedSearch.getSelectedFormId() == -1 && $("#recordsSourceForm").is(":checked")) {
        configureAdvancedSearch.errors.set("AdvancedSearchFormNotSelectBeforeSaveException", $("#AdvancedSearchFormNotSelectBeforeSaveException").val(),
                [$("#recordsSourceForm")[0]]);
        return;
    }

    var advancedSearchId = $("#selectedAdvancedSearchId").val();
    var request = {
        widgetId: configureAdvancedSearch.settings.widgetId,
        formId: configureAdvancedSearch.getSelectedFormId(),
        galleryId: configureAdvancedSearch.getSelectedGalleryId(),
        advancedSearchId: advancedSearchId,
        name: $("#advancedSearchName").val(),
        orientationType: $("#displayTypeDiv input:checked").val(),
        // We updating options here only if we creating a new Adv. Search, otherwise they are updated after "Edit Options Page" save.
        searchOptions: $("#selectedAdvancedSearchId").val() == "null" ? configureAdvancedSearch.searchOptions : new Array(),
        includeResultsNumber: $("#includeResultsNumber").is(":checked"),
        headerComment: $("#AdvancedSearchHeader").html(),
        displayHeaderComment: $("#showAdvancedSearchHeader").val()
    };

    getActiveWindow().disableContentBeforeSaveSettings();
    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.AdvancedSearchNotUniqueNameException",
            configureAdvancedSearch.errors.exceptionAction({errorId:"AdvancedSearchNotUniqueNameException", fields:[$("#advancedSearchName")[0]]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.AdvancedSearchNullOrEmptyNameException",
            configureAdvancedSearch.errors.exceptionAction({errorId:"AdvancedSearchNullOrEmptyNameException", fields:[$("#advancedSearchName")[0]]}));
    serviceCall.executeViaDwr("SaveAdvancedSearchService", "execute", request, function (response) {
        if ($("#dashboardPage")[0]) {
            $("#itemName" + advancedSearchId).html($("#advancedSearchName").val());

            if (closeAfterSaving) {
                closeConfigureWidgetDiv();
            }
        } else {
            if (configureAdvancedSearch.settings.widgetId) {
                makePageDraftVisual(window.parent.getActivePage());
            }

            if (closeAfterSaving) {
                if (configureAdvancedSearch.settings.widgetId) {
                    closeConfigureWidgetDivWithUpdate(response);
                } else {
                    closeConfigureWidgetDiv();
                }
            }
        }

        if (configureAdvancedSearch.settings.onAfterSave) {
            configureAdvancedSearch.settings.onAfterSave(request);
        }

        if (!closeAfterSaving) {
            updateWidgetInfo(response);
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    });
};

configureAdvancedSearch.disableAdvancedSearch = function () {
    disableControl($("#advancedSearchName")[0]);
    disableControl($("#editAdvancedSearchHeader")[0]);

    disableControl($("#recordsSourceGallery")[0]);
    disableControl($("#galleriesSelect")[0]);
    disableControl($("#configureGalleryLink")[0]);
    disableControl($("#recordsSourceUpload")[0]);
    disableControl($("#recordsSourceForm")[0]);
    disableControl($("#formsSelect")[0]);

    disableControl($("#displayTypeABOVE")[0]);
    disableControl($("#displayTypeLEFT")[0]);

    disableControl($("#manageRecordsLink")[0]);

    disableControl($("#editSearchOptionsButton")[0]);
    disableControl($("#includeResultsNumber")[0]);

    $("#windowSave", $("#configureAdvancedSearchButtons")[0]).hide();
    $("#windowApply", $("#configureAdvancedSearchButtons")[0]).hide();
    $("#windowCancel", $("#configureAdvancedSearchButtons")[0]).val("Close");

    $("#advancedSearchReadOnlyMessage").show();
    $("#advancedSearchErrors").hide();
};

configureAdvancedSearch.close = function () {
    var defaultAdvSearchFormThatShouldBeDeletedId = $("#alreadyCreatedDefaultFormId").val();
    if (closeConfigureWidgetDivWithConfirm()) {
        if (defaultAdvSearchFormThatShouldBeDeletedId && defaultAdvSearchFormThatShouldBeDeletedId != "null") {
            new ServiceCall().executeViaDwr("CancelAdvancedSearchService", "removeDefaultForm", defaultAdvSearchFormThatShouldBeDeletedId);
        }
    }
};

/*-----------------------------------------------------MISC-----------------------------------------------------------*/

configureAdvancedSearch.showMoreInfo = function () {
    var moreInfoWindow = createConfigureWindow({height:300, width:500});
    moreInfoWindow.setContent($("#advSearchMoreInfoDiv").html());
};

configureAdvancedSearch.showEditGalleryAndRecordsExplan = function () {
    var moreInfoWindow = createConfigureWindow({height:300, width:500});
    moreInfoWindow.setContent($("#editGalleryAndRecordsExplanDiv").html());
};

configureAdvancedSearch.showSearchModulesExplan = function () {
    var moreInfoWindow = createConfigureWindow({height:300, width:500});
    moreInfoWindow.setContent($("#searchModulesExplanDiv").html());
};

configureAdvancedSearch.recordsSourceFormClick = function () {
    if ($("#selectedAdvancedSearchId").val() != "null") {
        if (confirm($("#confirmSoruceChange").val())) {
            new ServiceCall().executeViaDwr("RemoveSearchOptionService", "removeAll", $("#selectedAdvancedSearchId").val(), function () {
                recordsSourceFormClickInternal();
                return true;
            });
        } else {
            return false;
        }
    } else {
        recordsSourceFormClickInternal();
        return true;
    }

    function recordsSourceFormClickInternal() {
        disableControl($("#galleriesSelect")[0]);
        disableControl($("#formsSelect")[0], false);
        configureAdvancedSearch.disableConfigureGalleryLink(true);
        configureAdvancedSearch.disableManageRecordsLink(false);
    }
};

configureAdvancedSearch.recordsSourceGalleryClick = function () {
    disableControl($("#galleriesSelect")[0], false);
    disableControl($("#formsSelect")[0]);
    configureAdvancedSearch.disableConfigureGalleryLink(false);
    configureAdvancedSearch.disableManageRecordsLink(false);
};

configureAdvancedSearch.recordsSourceUploadClick = function () {
    if ($("#selectedAdvancedSearchId").val() != "null") {
        if (confirm($("#confirmSoruceChange").val())) {
            new ServiceCall().executeViaDwr("RemoveSearchOptionService", "removeAll", $("#selectedAdvancedSearchId").val(), function () {
                recordsSourceUploadClickInternal();
                return true;
            });
        } else {
            return false;
        }
    } else {
        recordsSourceUploadClickInternal();
        return true;
    }

    function recordsSourceUploadClickInternal() {
        disableControl($("#galleriesSelect")[0]);
        disableControl($("#formsSelect")[0]);
        configureAdvancedSearch.disableConfigureGalleryLink(true);
        configureAdvancedSearch.disableManageRecordsLink(true);
    }
};

configureAdvancedSearch.disableConfigureGalleryLink = function(disable) {
    if (!disable) {
        disable = $("#galleriesSelect > option:selected").val() == -1;
    }

    disableControl($("#configureGalleryLink")[0], disable);
};

configureAdvancedSearch.disableManageRecordsLink = function(disable) {
    if (!disable) {
        if ($("#recordsSourceForm").is(":checked")) {
            disable = $("#formsSelect > option:selected").val() == -1;
        } else if ($("#recordsSourceGallery").is(":checked")) {
            disable = $("#galleriesSelect > option:selected").val() == -1;
        }
    }

    disableControl($("#manageRecordsLink")[0], disable);
};

configureAdvancedSearch.showConfigureGalleryFromAdvSearch = function () {
    configureItemSettings.show({itemId: $("#galleriesSelect > option:selected").val()},
            configureItemSettings.settingsTab, "GALLERY");
};

configureAdvancedSearch.showManageRecordsFromAdvSearch = function () {
    showConfigureFilledForms({formId:configureAdvancedSearch.getSelectedFormId()});
};

configureAdvancedSearch.gallerySelectOnChange = function () {
    configureAdvancedSearch.disableConfigureGalleryLink();
    configureAdvancedSearch.disableManageRecordsLink();
};

configureAdvancedSearch.formsSelectOnChange = function () {
    configureAdvancedSearch.disableManageRecordsLink();
};

configureAdvancedSearch.getSelectedFormId = function () {
    var selectedFormId = null;
    if ($("#recordsSourceGallery").is(":checked")) {
        selectedFormId = $("#galleriesSelect > option:selected").attr("formId");
    } else if ($("#recordsSourceForm").is(":checked")) {
        selectedFormId = $("#formsSelect > option:selected").val();
    } else if ($("#recordsSourceUpload").is(":checked")) {
        selectedFormId = $("#alreadyCreatedDefaultFormId").val() == "null" ? null : $("#alreadyCreatedDefaultFormId").val();
    }

    return selectedFormId;
};

configureAdvancedSearch.getSelectedGalleryId = function() {
    var selectedGalleryId = null;
    if ($("#recordsSourceGallery").is(":checked")) {
        selectedGalleryId = $("#galleriesSelect > option:selected").val();
    }
    return selectedGalleryId;
};

configureAdvancedSearch.clearSearchOptionsArray = function() {
    configureAdvancedSearch.searchOptions = new Array();
};


