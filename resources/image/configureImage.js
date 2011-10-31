var configureImage = {
    tabs: {
        PRIMARY : 0,
        ROLL_OVER : 1,
        LABELS_LINKS : 2
    }
};

configureImage.onBeforeShow = function(settings) {
    configureImage.settings = settings;
};

configureImage.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    //Accuring the error block
    configureImage.errors = new Errors({}, "imageErrors");

    disableLinksArea(($("#disableLinksArea").val() == "true"));

    if ($("#siteOnItemRightType").val() == "READ") {
        disableConfigureImageWindow();
    }

    var siteId = $("#siteId").val();
    var imageFileType = $("#imageFileType").val();
    if (siteId && imageFileType) {
        showImageFileUploader(siteId, imageFileType);
    }

    createImageTextEditor($("#selectedImageItemTextArea").val());

    window.uploadFilesControl.bind({
        label: "Browse and Upload",
        siteId: window.siteId,
        element: "#uploadImagesButtonDiv",
        onClose: function () {
            $("#uploadedImages")[0].settings.onAfterRefresh = function () {
                var uploadedImage = $("#uploadedImages").find("tr:first").find("td:first").find("img[image='image']");
                window.images.select.selectImage(uploadedImage);
            };
            window.images.select.refresh("#uploadedImages");
        }
    });
    /*----------------------------Fix for: http://jira.shroggle.com/browse/SW-6508. Tolik-----------------------------*/
    var selectedImageWidth = $("#selectedImageWidth").val();
    var selectedImageHeight = $("#selectedImageHeight").val();
    var selectedImageId = $("#selectedImageId").val();
    var selectedImageUrl = $("#selectedImageUrl").val();
    /*----------------------------Fix for: http://jira.shroggle.com/browse/SW-6508. Tolik-----------------------------*/

    window.images.select.bind({
        imageId: $("#selectedImageId").val(),
        imageItemId: $("#selectedImageItemId").val(),
        lineWidth: 810,
        siteId: window.siteId,
        element: "#uploadedImages",
        onClick: function (image) {
            selectImage(image.id, image.width, image.height, image.url);
        },

        onSelectedRemove: function () {
            deselectImage();
        } ,

        onAfterRefresh: function () {
            /*------------------------Fix for: http://jira.shroggle.com/browse/SW-6508. Tolik-------------------------*/
            selectImage(selectedImageId, selectedImageWidth, selectedImageHeight, selectedImageUrl);
            /*------------------------Fix for: http://jira.shroggle.com/browse/SW-6508. Tolik-------------------------*/
        }
    });

};

configureImage.save = function (closeAfterSaving) {
    var serviceCall = new ServiceCall();


    configureImage.errors.clear();

    switch (configureImage.getSelectedTab()) {
        case configureImage.tabs.PRIMARY : {
            savePrimaryTabContent();
            return;
        }
        case configureImage.tabs.ROLL_OVER : {
            saveRollOverTabContent();
            return;
        }
        case configureImage.tabs.LABELS_LINKS : {
            saveLabelsLinksTabContent();
            return;
        }
    }

    function savePrimaryTabContent() {
        var height = document.getElementById("widgetImageHeight").value;
        var width = document.getElementById("widgetImageWidth").value;
        var tempSelectedImageId = document.getElementById("selectedImageId").value;

        if (tempSelectedImageId == "undefined" || tempSelectedImageId == "") {
            configureImage.errors.set("PRIMARY_IMAGE_NOT_SELECTED", $("#pleaseSelectPrimaryImage").val());
        }
        if (!document.getElementById("saveProportionCheckbox").checked) { // Primary tab
            if (height == "" && width == "" || ((height == "" || height == "0") && (width == "" || width == "0"))) {
                configureImage.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID", $("#pleaseEnterValidWidthHeightNumbers").val(),
                        [$("#widgetImageHeight")[0], $("#widgetImageWidth")[0]]);
            }
            if (height == "" || height == "0") {
                configureImage.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                        $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageHeight")[0]]);
            }
            if (width == "" || width == "0") {
                configureImage.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                        $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageWidth")[0]]);
            }
        }
        if (!isInteger(height) || !isInteger(width) || (height == "0" && width == "0")) { // Primary tab
            configureImage.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID", $("#pleaseEnterValidWidthHeightNumbers").val(),
                    [$("#widgetImageHeight")[0], $("#widgetImageWidth")[0]]);
        }
        if (height == "0") {  // Primary tab
            configureImage.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                    $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageHeight")[0]]);
        }
        if (width == "0") {  // Primary tab
            configureImage.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                    $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageWidth")[0]]);
        }
        if (configureImage.errors.hasErrors()) {
            return;
        }
        serviceCall.addExceptionHandler(
                "com.shroggle.exception.ImageNotSelectException",
                configureImage.errors.exceptionAction({errorId:"ImageSource"}));
        serviceCall.addExceptionHandler(
                "com.shroggle.exception.ImageSizeIncorrectException",
                configureImage.errors.exceptionAction({errorId:"ImageSize"}));
        var request = new Object();
        request.imageId = (tempSelectedImageId != "undefined" ? tempSelectedImageId : "");
        request.imageItemId = $("#selectedImageItemId").val();
        request.width = width;
        request.height = height;
        request.saveRatio = document.getElementById("saveProportionCheckbox").checked;
        request.margin = document.getElementById("widgetImageMargin").value;
        request.alignment = document.getElementById("aligmentImageSelect").value;
        request.widgetId = configureImage.settings.widgetId;
        request.name = $("#imageName").val();
        request.description = $("#imageDescription").val();

        getActiveWindow().disableContentBeforeSaveSettings();
        serviceCall.executeViaDwr("CreateImageService", "savePrimaryImageTab", request, function (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + $("#selectedImageItemId").val()).html(request.name);

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureImage.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());

                    if (closeAfterSaving) {
                        closeConfigureWidgetDivWithUpdate(response);
                    }
                } else {
                    if (closeAfterSaving) {
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

    }

    function saveRollOverTabContent() {
        var request = new Object();
        request.imageItemId = $("#selectedImageItemId").val();
        request.widgetId = configureImage.settings.widgetId;
        request.descriptionOnMouseOver = document.getElementById("widgetImageShowDescriptionOnMouseOver").checked;
        request.onMouseOverText = document.getElementById("onMouseOverText").value;

        var tempRollOverImageId = document.getElementById("selectedRollOverImageId").value;
        request.rollOverImageId = (tempRollOverImageId != "undefined" ? tempRollOverImageId : "");

        getActiveWindow().disableContentBeforeSaveSettings();
        serviceCall.executeViaDwr("CreateImageService", "saveRollOverImageTab", request, function (response) {
            if ($("#dashboardPage")[0]) {
                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureImage.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());

                    if (closeAfterSaving) {
                        closeConfigureWidgetDivWithUpdate(response);
                    }
                } else {
                    if (closeAfterSaving) {
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

    }

    function saveLabelsLinksTabContent() {
        var widthHeightResponse = getWidthHeght();
        if (widthHeightResponse.wrongWidth) {
            configureImage.errors.set("CUSTOM_HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                    $("#defineCustomWindowSize").val(), [widthHeightResponse.wrongWidthElement]);
        }
        if (widthHeightResponse.wrongHeight) {
            configureImage.errors.set("CUSTOM_HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                    $("#defineCustomWindowSize").val(), [widthHeightResponse.wrongHeightElement]);
        }

        if ((document.getElementById("labelIsALink").checked && document.getElementById("displayLabel").checked) ||
                document.getElementById("imageIsALink").checked) {
            var linkTargetResponse = isLinkTargetSelected();
            if (!linkTargetResponse.linkTargetSelected) {
                configureImage.errors.set("WRONG_LINK_TARGET", $("#selectLinkTarget").val(),
                        [linkTargetResponse.errorElement]);
            }
        }
        if (configureImage.errors.hasErrors()) {
            return;
        }
        var request = new Object();
        request.imageItemId = $("#selectedImageItemId").val();
        request.widgetId = configureImage.settings.widgetId;
        //---------------------------labels and links tab settings--------------------------------
        request.title = document.getElementById("widgetImageTitle").value;
        request.titlePosition = getTitlePosition();
        request.labelIsALinnk = document.getElementById("labelIsALink").checked;
        request.imageIsALinnk = document.getElementById("imageIsALink").checked;
        request.imageLinkType = document.getElementById("imageLinkType").value;

        //---------------labels and links tab settings; media file---------------
        request.customizeWindowSize = widthHeightResponse.checkbox.checked;
        request.newWindowWidth = widthHeightResponse.width;
        request.newWindowHeight = widthHeightResponse.heght;
        request.imageFileType = document.getElementById("imageFileType").value;
        request.imagePdfDisplaySettings = getPdfDisplaySettings();
        request.imageAudioDisplaySettings = getAudioDisplaySettings();
        request.imageFlashDisplaySettings = getImageFlashDisplaySettings();
        request.imageFileId = document.getElementById("imageFileSelect").value;
        //---------------labels and links tab settings; media file---------------

        //---------------labels and links tab settings; external url---------------
        request.externalUrl = document.getElementById("externalUrl").value;
        request.externalUrlDisplaySettings = getExternalUrlDisplaySettings();
        //---------------labels and links tab settings; external url---------------

        //---------------labels and links tab settings; internal page---------------
        request.internalPageId = document.getElementById("internalPageId").value;
        request.internalPageDisplaySettings = getInternalPageDisplaySettings();
        //---------------labels and links tab settings; internal page---------------

        //---------------labels and links tab settings; text area---------------
        request.textArea = getEditorContent("imageTextEditor");
        request.textAreaDisplaySettings = getTextAreaDisplaySettings();
        //---------------labels and links tab settings; text area---------------

        //---------------------------labels and links tab settings--------------------------------

        getActiveWindow().disableContentBeforeSaveSettings();
        serviceCall.executeViaDwr("CreateImageService", "saveLabelsLinksImageTab", request, function (response) {
            if ($("#dashboardPage")[0]) {
                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureImage.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());

                    if (closeAfterSaving) {
                        closeConfigureWidgetDivWithUpdate(response);
                    }
                } else {
                    if (closeAfterSaving) {
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
    }
};

configureImage.getSelectedTab = function () {
    var selectedTabId = $(".c1current")[0].id;
    if (selectedTabId == "primaryTab") {
        return configureImage.tabs.PRIMARY;
    }
    if (selectedTabId == "rollOverTab") {
        return configureImage.tabs.ROLL_OVER;
    }
    if (selectedTabId == "labelsLinksTab") {
        return configureImage.tabs.LABELS_LINKS;
    }
    return -1;
};


configureImage.isPrimaryImageTabSelected = function () {
    return configureImage.getSelectedTab() == configureImage.tabs.PRIMARY;
};

function resetWidgetImage(widgetImageId) {
    new ServiceCall().executeViaDwr("ResetWidgetImageService", "execute", widgetImageId, function (response) {
        makePageDraftVisual(window.parent.getActivePage());
        closeConfigureWidgetDivWithUpdate(response);
    });
}

function checkDisplayLabelCheckbox(checked) {
    var checkbox = $("#displayLabel")[0];
    if (checked && !checkbox.disabled) {
        checkbox.checked = true;
    }
}

function disableLinksArea(disabled) {
    disableControl($("#imageLinkType")[0], {disabled:disabled, saveOldDisabledState : true});
    disableControl($("#INTERNAL_URLDiv"), {disabled:disabled, saveOldDisabledState : true});
    disableControl($("#MEDIA_FILEDiv"), {disabled:disabled, saveOldDisabledState : true});
    disableControl($("#TEXT_AREADiv"), {disabled:disabled, saveOldDisabledState : true});

    disableControl($("#externalUrl")[0], {disabled:disabled, saveOldDisabledState : true});
    disableControl($("#OPEN_IN_NEW_WINDOW_EXTERNAL_URL")[0], {disabled:disabled, saveOldDisabledState : true});
    disableControl($("#OPEN_IN_SAME_WINDOW_EXTERNAL_URL")[0], {disabled:disabled, saveOldDisabledState : true});
    disableControl($("#previewExternalUrl")[0], {disabled:disabled, saveOldDisabledState : false});
    if (disabled) {
        $("#imageFileButtonContainer").hide();
        $("#textAreaDiv")[0].style.visibility = "hidden";
    } else {
        $("#imageFileButtonContainer").show();
        $("#textAreaDiv")[0].style.visibility = "visible";
    }
}

function createImageTextEditor(header) {
    createEditor({
        width: 620,
        height: 50,
        showLastSavedData: false,
        place: document.getElementById("textAreaDiv"),
        editorId: "imageTextEditor",
        value: (header != null ? header : ""),
        root: "../"});
}

function primaryImage() {
    $("#primaryImageHeader").show();
    $("#rollOverImageHeader").hide();
    $("#labelsLinksImageHeader").hide();
    $("#labelsLinksImageTabContent").hide();
    $("#rollOverTD").hide();
    $("#primaryImageTabContent").show();
    $("#selectImageTD").show();

    window.images.select.selectImage(getSelectedPrimaryImageDiv());

    $("#deselectImageControl").show();
}

function rollOverImage() {
    $("#primaryImageHeader").hide();
    $("#rollOverImageHeader").show();
    $("#labelsLinksImageHeader").hide();
    $("#labelsLinksImageTabContent").hide();
    $("#selectImageTD").hide();
    $("#primaryImageTabContent").show();
    $("#rollOverTD").show();

    window.images.select.selectImage(getSelectedRollOverImageDiv());

    $("#deselectImageControl").show();
}

function labelsLinks() {
    $("#primaryImageHeader").hide();
    $("#rollOverImageHeader").hide();
    $("#labelsLinksImageHeader").show();
    $("#primaryImageTabContent").hide();
    $("#labelsLinksImageTabContent").show();
    $("#deselectImageControl").hide();
}

function disableAboveBelowRadiobuttons(displayTitle) {
    document.getElementById("displayLabelAbove").disabled = !displayTitle.checked;
    document.getElementById("displayLabelBelow").disabled = !displayTitle.checked;
    setSampleTitle();
}

function setSampleTitle() {
    if (document.getElementById("widgetImageShowDescriptionOnMouseOver").checked) {
        document.getElementById("curentlySelectedImage").title = document.getElementById("imageDescription").value;
        document.getElementById("curentlySelectedImage").alt = document.getElementById("imageDescription").value;
    } else {
        document.getElementById("curentlySelectedImage").title = document.getElementById("onMouseOverText").value;
        document.getElementById("curentlySelectedImage").alt = document.getElementById("onMouseOverText").value;
    }
}

function checkLabelsCheckboxes() {
    var widgetImageTitle = $("#widgetImageTitle").val().trim();
    if (!widgetImageTitle) {
        $("#displayLabel").attr("disabled", true);
        $("#labelIsALink").attr("disabled", true);
        $("#displayLabel")[0].checked = false;
        $("#labelIsALink")[0].checked = false;
        $("#displayLabelAbove").attr("disabled", true);
        $("#displayLabelBelow").attr("disabled", true);
    } else {
        $("#displayLabel").removeAttr("disabled");
        $("#labelIsALink").removeAttr("disabled");
    }
}

function widthChanged(newWidth) {
    var imageWidth = document.getElementById("selectedImageWidth").value;
    var imageHeight = document.getElementById("selectedImageHeight").value;
    if (imageWidth && imageHeight && document.getElementById("saveProportionCheckbox").checked) {
        var ratio = imageHeight / imageWidth;
        var height;
        if (newWidth) {
            height = (newWidth * ratio).toFixed(0);
            document.getElementById("widgetImageHeight").value = height && height != "NaN" ? height : "";
        } else {
            height = document.getElementById("widgetImageHeight").value;
            if (height && newWidth) {
                var width = (height * (imageWidth / imageHeight)).toFixed(0);
                document.getElementById("widgetImageWidth").value = width && width != "NaN" ? width : "";
            }
        }
    }
}

function heightChanged(newHeight) {
    var imageWidth = document.getElementById("selectedImageWidth").value;
    var imageHeight = document.getElementById("selectedImageHeight").value;
    if (imageWidth && imageHeight && document.getElementById("saveProportionCheckbox").checked) {
        var ratio = imageWidth / imageHeight;
        if (newHeight) {
            var width = (newHeight * ratio).toFixed(0);
            document.getElementById("widgetImageWidth").value = width && width != "NaN" ? width : "";
        } else {
            var tempWidgetImageWidth = document.getElementById("widgetImageWidth").value;
            if (tempWidgetImageWidth && newHeight) {
                var height = ((tempWidgetImageWidth * (imageHeight / imageWidth))).toFixed(0);
                document.getElementById("widgetImageHeight").value = height && height != "NaN" ? height : "";
            }
        }
    }
}

function selectRollOverImage(imageId) {
    document.getElementById("selectedRollOverImageId").value = imageId;
}

function saveProportionCheckboxChanged(checkbox) {
    var height = document.getElementById("widgetImageHeight").value;
    var width = document.getElementById("widgetImageWidth").value;
    if (checkbox.checked) {
        if (width && height) {
            if (parseInt(width) > parseInt(height)) {
                widthChanged(width);
            } else {
                heightChanged(height);
            }
        } else if (width) {
            widthChanged(width);
        } else if (height) {
            heightChanged(height);
        }
    }
}

function setThumbnailImageSrc(src) {
    if (src == "image") {
        setThumbnailImage(getSelectedImageUrl());
    } else if (src == "rollOverImage") {
        var rolloverUrl = getSelectedRoleOverImageUrl();
        if (rolloverUrl) {
            setThumbnailImage(rolloverUrl);
        }
    }
}

function getSelectedImageUrl() {
    var src = document.getElementById("selectedImageUrl").value;
    return src == 'undefined' ? "" : src;
}

function getSelectedRoleOverImageUrl() {
    var src = document.getElementById("selectedRollOverImageUrl").value;
    return src == 'undefined' ? "" : src;
}

function showImageWhatsThisWindow() {
    var imageHelpWindow = createConfigureWindow({width:400, height:500});
    imageHelpWindow.setContent($("#iamgeWhatsThis").html());
}

function thumbnailLinksChanged() {
    document.getElementById("EXTERNAL_URLDiv").style.display = "none";
    document.getElementById("INTERNAL_URLDiv").style.display = "none";
    document.getElementById("MEDIA_FILEDiv").style.display = "none";
    document.getElementById("TEXT_AREADiv").style.display = "none";
    document.getElementById(document.getElementById("imageLinkType").value + "Div").style.display = "block";
}


function fileTypeChanged(imageFileType) {
    document.getElementById("imageFileType").value = imageFileType;
    updateImageFileSelect();
    document.getElementById("pdfRadioDiv").style.display = "none";
    document.getElementById("audioRadioDiv").style.display = "none";
    document.getElementById("imageFlashRadioDiv").style.display = "none";
    document.getElementById("cadRadioDiv").style.display = "none";
    if (imageFileType) {
        switch (imageFileType) {
            case "PDF" : {
                document.getElementById("pdfRadioDiv").style.display = "block";
                break;
            }
            case "AUDIO" : {
                document.getElementById("audioRadioDiv").style.display = "block";
                break;
            }
            case "FLASH" : {
                document.getElementById("imageFlashRadioDiv").style.display = "block";
                break;
            }
            case "IMAGE" : {
                document.getElementById("imageFlashRadioDiv").style.display = "block";
                break;
            }
            case "CAD" : {
                document.getElementById("cadRadioDiv").style.display = "block";
                break;
            }
        }
    }
}

function disableCustomizeTextWindowSize(disabled) {
    document.getElementById("customizeTextWindowSize").disabled = disabled;
    document.getElementById("customizeTextWindowWidth").disabled = disabled;
    document.getElementById("customizeTextWindowHeight").disabled = disabled;
    if (!disabled) {
        disableCustomizeTextWindowWidthHeight(!document.getElementById("customizeTextWindowSize").checked);
    }
}

function disableCustomizeTextWindowWidthHeight(disabled) {
    document.getElementById("customizeTextWindowWidth").disabled = disabled;
    document.getElementById("customizeTextWindowHeight").disabled = disabled;
}


function disableCustomizeWindowSizeDiv(disabled) {
    document.getElementById("customizeWindowSize").disabled = disabled;
    document.getElementById("customizeWindowWidth").disabled = disabled;
    document.getElementById("customizeWindowHeight").disabled = disabled;
    if (!disabled) {
        disableCustomizeWindowWidthHeight(!document.getElementById("customizeWindowSize").checked);
    }
}

function disableCustomizeWindowWidthHeight(disabled) {
    document.getElementById("customizeWindowWidth").disabled = disabled;
    document.getElementById("customizeWindowHeight").disabled = disabled;
}

function selectImage(imageId, imageWidth, imageHeight, imageThumbnailUrl) {
    if (imageWidth && imageHeight && configureImage.isPrimaryImageTabSelected()) {
        document.getElementById("selectedImageWidth").value = imageWidth;
        document.getElementById("selectedImageHeight").value = imageHeight;
    }

    if (configureImage.isPrimaryImageTabSelected()) {
        var tempSelectedImageId = document.getElementById("selectedImageId").value;

        document.getElementById("selectedImageId").value = imageId;
        if (imageWidth) {
            document.getElementById("widgetImageWidth").value = imageWidth;
        }
        if (imageHeight) {
            document.getElementById("widgetImageHeight").value = imageHeight;
        }
        if (imageThumbnailUrl) {
            document.getElementById("selectedImageUrl").value = imageThumbnailUrl;
            setThumbnailImage(imageThumbnailUrl);
        }
        var savedName = $("#imageName").val();
        var currentlySelectedImageName = $("#imageName" + tempSelectedImageId).val() || "";
        var newImageName = $("#imageName" + imageId).val();
        if (newImageName && savedName == currentlySelectedImageName) {// Image name was not changed manually.
            $("#imageName").val(newImageName);
        }
    } else {
        var selectedRollOverImageId = document.getElementById("selectedRollOverImageId").value;

        document.getElementById("selectedRollOverImageId").value = imageId;
        if (imageThumbnailUrl) {
            document.getElementById("selectedRollOverImageUrl").value = imageThumbnailUrl;
        }
    }
}

function getSelectedImageDiv() {
    return configureImage.isPrimaryImageTabSelected() ? getSelectedPrimaryImageDiv() : getSelectedRollOverImageDiv();
}

function getSelectedPrimaryImageDiv() {
    return $("img[imageId='" + $("#selectedImageId").val() + "']")[0];
}

function getSelectedRollOverImageDiv() {
    return $("img[imageId='" + $("#selectedRollOverImageId").val() + "']")[0];
}

function deselectImage() {
    var image = getSelectedImageDiv();

    if (!image || image.className == "unselectedConfigureImage") {
        return;
    }

    if (configureImage.isPrimaryImageTabSelected()) {
        $("#selectedImageWidth").val("");
        $("#selectedImageHeight").val("");
        image.className = "unselectedConfigureImage";
        $("#selectedImageId").val("undefined");
        $("#widgetImageWidth").val("");
        $("#widgetImageHeight").val("");
        $("#selectedImageUrl").val("undefined");
        setThumbnailImage(undefined);
        $("#imageName").val("");
    } else {
        image.className = "unselectedConfigureImage";
        $("#selectedRollOverImageId").val("undefined");
        $("#selectedRollOverImageUrl").val("undefined");
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function setThumbnailImage(thumbnailSrc) {
    document.getElementById("curentlySelectedImage").src = thumbnailSrc;
    document.getElementById("curentlySelectedImage").style.visibility = thumbnailSrc ? "visible" : "hidden";
}

function disableConfigureImageWindow() {
    disableControl($("#saveProportionCheckbox")[0]);
    disableControl($("#widgetImageWidth")[0]);
    disableControl($("#widgetImageHeight")[0]);
    disableControl($("#widgetImageMargin")[0]);
    disableControl($("#aligmentImageSelect")[0]);
    disableControl($("#deselectImageControl")[0]);
    disableControl($("#removeImageControl")[0]);

    disableControl($("#widgetImageShowDescriptionOnMouseOver")[0]);
    disableControl($("#imageDescription")[0]);

    disableControl($("#widgetImageTitle")[0]);
    disableControl($("#displayLabel")[0]);
    disableControl($("#displayLabelAbove")[0]);
    disableControl($("#displayLabelBelow")[0]);
    disableControl($("#labelIsALink")[0]);
    disableControl($("#imageIsALink")[0]);
    disableControl($("#imageLinkType")[0]);
    disableControl($("#externalUrl")[0]);
    disableControl($("#externalUrl")[0]);
    disableControl($("#OPEN_IN_NEW_WINDOW_EXTERNAL_URL")[0]);
    disableControl($("#OPEN_IN_SAME_WINDOW_EXTERNAL_URL")[0]);

    disableImageLibrary();

    $("#windowSave", $("#configureImageButtons")[0]).hide();
    $("#windowApply", $("#configureImageButtons")[0]).hide();
    $("#windowCancel", $("#configureImageButtons")[0]).val("Close");

    $("#imageReadOnlyMessage").show();
    $("#imageErrors").hide();
}

function disableImageLibrary() {
    disableControls($("#uploadedImages").find("img"));
}

function createCorrectUrl(url) {
    if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://")) {
        return url;
    } else {
        return "http://" + url;
    }
}

function disableOnMouseOverText(disabled) {
    $("#onMouseOverText")[0].disabled = disabled;
}