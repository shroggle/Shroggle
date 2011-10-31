var configureBackground = {
    colorPicker:undefined,
    backgroundTabSelector: "#backgroundTabContent"
};

configureBackground.onAfterShow = function () {
    updateBackgroundImage();

    if ($("#siteOnItemRightType").val() == "READ") {
        configureBackground.disable();
    } else {
        showBackgroundImageUploader();
    }
};

configureBackground.showColorPicker = function (value) {
    configureBackground.colorPicker = new ColorPicker({
        renderTo: "backgroundColorPicker",
        width: 160,
        textInputFieldWidth: 125,
        selectedColor : value,
        onColorStateChanged : function() {
            setWindowSettingsChanged();
        }
    });

    if ($("#siteOnItemRightType").val() == "READ") {
        disableColorPicker("backgroundColorPicker");
    }
};

function removeWidgetBackground(itemId, showForPage) {
    if (!confirmApplyToAllPages()) {
        return;
    }
    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateBackgroundService", "removeBorderBackground", itemId, showForPage, isApplyToAllPagesCheckboxChecked(), function (response) {
        if (response || response == '') {
            removeBorderBackgroundWidgetStyle(response);
            makePageDraftVisual(window.parent.getActivePage());
            closeConfigureWidgetDiv();
        }
    });
}

function confirmApplyToAllPages() {
    if (isApplyToAllPagesCheckboxChecked()) {
        return confirm(document.getElementById("applyToAllPagesText").value);
    }
    return true;
}

function isApplyToAllPagesCheckboxChecked() {
    var checkbox = document.getElementById("applyToAllPages");
    if (checkbox) {
        return checkbox.checked;
    }
    return false;
}

configureBackground.save = function (itemId, draftItemId, setForPage, closeAfterSaving, siteId) {
    if (!confirmApplyToAllPages()) {
        return;
    }
    var background = {
        backgroundRepeat : ($("#tileImageRadio")[0].checked ? "repeat" : "no-repeat"),
        backgroundPosition : ($("#tileImageRadio")[0].checked ? "" : $("#alignSelect").val()),
        backgroundColor : configureBackground.colorPicker.getValue(),
        backgroundImageId : $("#selectedBackgroundImageId").val(),
        siteId : siteId
    };

    if (!background.backgroundColor || background.backgroundColor.length < 7) {
        background.backgroundColor = "transparent";
    }

    if (!background.backgroundImageId) {
        background.backgroundImageId = 0;
    }

    var serviceCall = new ServiceCall();
    
    getActiveWindow().disableContentBeforeSaveSettings();
    if (setForPage) {
        serviceCall.executeViaDwr("CreateBackgroundService", "executeForPage", itemId, background, isApplyToAllPagesCheckboxChecked(), function(response) {
            updatePageAfterBackgroundCreation(response);
            configurePageSettings.onAfterSave(response, itemId, closeAfterSaving);
        });
    } else {
        serviceCall.executeViaDwr("CreateBackgroundService", "executeForWidget", itemId, draftItemId, background, ($("#saveBackgroundInCurrentPlace").attr("checked") || false), function(response) {
            updatePageAfterBackgroundCreation(response);
            closeConfigureWindow();
        });
    }

    function updatePageAfterBackgroundCreation(response) {
        var newStyle = response.newBorderBackgroundStyle;
        if (itemId && !$("#dashboardPage")[0]) {
            updateWidgetBorderBackgroundStyle(newStyle);
            makePageDraftVisual(window.parent.getActivePage());
        }
    }

    function closeConfigureWindow() {
        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
        } else {
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    }
};

configureBackground.disable = function () {
    disableControl($("#tileImageRadio")[0]);
    disableControl($("#align")[0]);
    disableControl($("#alignSelect")[0]);

    $("#forItemDiv", $("#configureBackgroundButtons")).css("visibility", "hidden");
    $("#windowSave", $("#configureBackgroundButtons")[0]).hide();
    $("#windowApply", $("#configureBackgroundButtons")[0]).hide();
    $("#windowCancel", $("#configureBackgroundButtons")[0]).val("Close");

    $("#backgroundReadOnlyMessage").show();
};

// ---------------------------------------------------------------------------------------------------------------------


function updateBackgroundImage() {
    var selectedBackgroundImageId = document.getElementById("selectedBackgroundImageId").value;
    if (isInteger(selectedBackgroundImageId) && parseInt(selectedBackgroundImageId) > 0) {
        selectBackgroundImage(selectedBackgroundImageId);
    }
}

function selectBackgroundImage(backgroundImageId) {
    var divForBackgroundImage = document.getElementById("divForBackgroundImage" + backgroundImageId);
    var currentlySelectedBackgroundImageId = document.getElementById("selectedBackgroundImageId");
    if (divForBackgroundImage.className == "unselectedConfigureImage") {
        if (currentlySelectedBackgroundImageId.value != "-1" && currentlySelectedBackgroundImageId.value != "0") {
            document.getElementById("divForBackgroundImage" + currentlySelectedBackgroundImageId.value).className = "unselectedConfigureImage";
        }
        currentlySelectedBackgroundImageId.value = backgroundImageId;
        divForBackgroundImage.className = "selectedConfigureImage";
    } else {
        divForBackgroundImage.className = "unselectedConfigureImage";
        currentlySelectedBackgroundImageId.value = "-1";
    }
}

function showImageTreatmentMoreInfo() {
    var showWindow = createConfigureWindow({width: 400, height: 200});
    showWindow.setContent($("#imageTreatmentMoreInfo").html());
}