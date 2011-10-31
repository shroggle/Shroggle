var configureVideo = {};

configureVideo.onBeforeShow = function (settings) {
    configureVideo.settings = settings;
};

configureVideo.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }

    if ($("#siteOnItemRightType").val() == "READ") {
        disableConfigureVideoWidget();
    }

    //Accuring the error block
    configureVideo.errors = new Errors({}, "videoErrors");
    showVideoUploader($("#fileSizeLimit").val());
    showVideoImageUploader();
    setSelectedImageForVideo();
    getActiveWindow().resize();
};

// ---------------------------------------------------------------------------------------------------------------------

function setSelectedImageForVideo() {
    var imageId = $("#selectedImageForVideoId").val();
    if (imageId && imageId != null && imageId > 0) {
        $("#selectedImageForVideoId").val(imageId);
        $("#imageForVideoDiv" + imageId.toString())[0].className = "selectedConfigureImage";
        var width = $("#width" + imageId)[0];
        var height = $("#height" + imageId)[0];
        if (width && height) {
            $("#selectedImageWidth").val(width.value);
            $("#selectedImageHeight").val(height.value);
        }
        return imageId;
    } else {
        return null;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function updateImageForVideo() {
    var imageId = setSelectedImageForVideo();
    if (imageId) {
        var width = $("#width" + imageId)[0];
        var height = $("#height" + imageId)[0];
        if (width && height) {
            $("#selectedImageWidth").val(width.value);
            $("#selectedImageHeight").val(height.value);
            $("#widgetImageWidth").val(width.value);
            $("#widgetImageHeight").val(height.value);
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function setVideoName() {
    $("#videoName").val($("#videoSelect").val());
}

// ---------------------------------------------------------------------------------------------------------------------


function setVideoSize() {
    var videoSelect = $("#videoSelect")[0];
    var videoWidth = videoSelect[videoSelect.selectedIndex].attributes['videoWidth'].value;
    var videoHeight = videoSelect[videoSelect.selectedIndex].attributes['videoHeight'].value;
    var videoWidthField = $("#width")[0];
    var videoHeightField = $("#height")[0];
    if (videoWidthField && videoHeightField) {
        var smallVideoCheckbox = $('#displaySmallOptionsCheckbox')[0];
        var largeVideoCheckbox = $('#displayLargeOptionsCheckbox')[0];
        var smallVideoSelect = $('#smallOptions')[0];
        var largeVideoSelect = $('#largeOptions')[0];
        if (videoWidth && videoHeight) {
            videoWidthField.disabled = false;
            videoWidthField.value = videoWidth;
            videoHeightField.disabled = false;
            videoHeightField.value = videoHeight;
            smallVideoCheckbox.disabled = false;
            largeVideoCheckbox.disabled = false;
            smallVideoSelect.disabled = !smallVideoCheckbox.checked;
            largeVideoSelect.disabled = !largeVideoCheckbox.checked;
        } else {
            videoWidthField.disabled = true;
            videoWidthField.value = "";
            videoHeightField.disabled = true;
            videoHeightField.value = "";
            smallVideoSelect.disabled = true;
            smallVideoSelect.value = "";
            largeVideoSelect.disabled = true;
            largeVideoSelect.value = "";
            smallVideoCheckbox.checked = false;
            smallVideoCheckbox.disabled = true;
            largeVideoCheckbox.checked = false;
            largeVideoCheckbox.disabled = true;
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function selectImageForVideo(imageForVideoId, imageWidth, imageHeight) {
    if (imageWidth && imageHeight) {
        $("#selectedImageWidth").val(imageWidth);
        $("#selectedImageHeight").val(imageHeight);
    }
    setWindowSettingsChanged();
    var imageForVideoDiv = $("#imageForVideoDiv" + imageForVideoId)[0];
    if (imageForVideoDiv.className == "unselectedConfigureImage") {
        var selectedImageId = $("#selectedImageForVideoId")[0];
        if (selectedImageId.value > 0 && selectedImageId.value != "") {
            $("#imageForVideoDiv" + selectedImageId.value)[0].className = "unselectedConfigureImage";
        }
        selectedImageId.value = imageForVideoId;
        imageForVideoDiv.className = "selectedConfigureImage";

        if (imageWidth) {
            $("#widgetImageWidth").val(imageWidth);
        }

        if (imageHeight) {
            $("#widgetImageHeight").val(imageHeight);
        }
    } else {
        imageForVideoDiv.className = "unselectedConfigureImage";
        $("#selectedImageForVideoId").val("-1");
        if (imageWidth) {
            $("#widgetImageWidth").val("");
        }

        if (imageHeight) {
            $("#widgetImageHeight").val("");
        }

        $("#selectedImageWidth").val("");
        $("#selectedImageHeight").val("");
    }
}

// ---------------------------------------------------------------------------------------------------------------------

configureVideo.save = function (closeAfterSaving) {
    configureVideo.errors.clear();
    configureVideo.closeAfterSaving = closeAfterSaving;

    var request = {
        widgetId: configureVideo.settings.widgetId,
        videoItemId: $("#selectedVideoItemId").val(),
        videoDescription: $("#VideoHeader").html(),
        includeDescription: $("#showVideoHeader").val(),
        keywords: $("#keywords").val(),
        videoName: $("#videoName").val(),
        width: $("#width").val(),
        height: $("#height").val(),
        playInCurrentPage: $("#currentPage").attr("checked"),
        videoImageId: $("#selectedImageForVideoId").val(),
        imageWidth: -1,
        imageHeight: -1,
        saveRatio: $("#saveProportionCheckbox").attr("checked")
    };
    configureVideo.settings.videoName = request.videoName;
    configureVideo.settings.videoItemId = request.videoItemId;

    if (request.videoImageId == "undefined" || request.videoImageId == "" || request.videoImageId == null) {
        request.videoImageId = -1;
    }

    if (request.videoImageId != -1 && request.videoImageId != "-1") {
        request.imageWidth = $("#widgetImageWidth").val();
        request.imageHeight = $("#widgetImageHeight").val();
        var width = request.imageWidth;
        var height = request.imageHeight;
        if (!$("#saveProportionCheckbox").attr("checked")) {
            if (height == "" || height == "0") {
                configureVideo.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                        $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageHeight")[0]]);
            }
            if (width == "" || width == "0") {
                configureVideo.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                        $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageWidth")[0]]);
            }
        }
        if (!isInteger(height) || height == "0") {
            configureVideo.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                    $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageHeight")[0]]);
        }
        if (!isInteger(width) || width == "0") {
            configureVideo.errors.set("HEIGHT_OR_WIDTH_ARE_NOT_VALID",
                    $("#pleaseEnterValidWidthHeightNumbers").val(), [$("#widgetImageWidth")[0]]);
        }
    }
    var videoSelect = $("#videoSelect")[0];
    request.videoId = videoSelect[videoSelect.selectedIndex].id;

    var smallOptionsSelect = $("#smallOptions")[0];
    var largeOptionsSelect = $("#largeOptions")[0];
    request.videoSmallSize = smallOptionsSelect[smallOptionsSelect.selectedIndex].value;
    request.videoLargeSize = largeOptionsSelect[largeOptionsSelect.selectedIndex].value;
    request.displaySmallOptions = $("#displaySmallOptionsCheckbox").attr("checked");
    request.displayLargeOptions = $("#displayLargeOptionsCheckbox").attr("checked");
    if (request.videoId < 0) {
        configureVideo.errors.set("SELECT_EXISTING_VIDEO", $("#selectExistingVideo").val(), [$("#videoSelect")[0]]);
    }
    if (videoSelect[videoSelect.selectedIndex].attributes["audioFile"].value != "true") {
        if (!request.width || !isInteger(request.width) || request.width == "0") {
            configureVideo.errors.set("INPUT_CORRECT_WIDTH", $("#notValidWidthNumbers").val(), [$("#width")[0]]);
        }
        if (!request.height || !isInteger(request.height) || request.height == "0") {
            configureVideo.errors.set("INPUT_CORRECT_HEIGHT", $("#notValidHeightNumbers").val(), [$("#height")[0]]);
        }
        if ((parseInt(request.width) % 2 > 0)) {
            configureVideo.errors.set("INPUT_CORRECT_WIDTH", $("#widthMultipleBeAMultipleOf2").val(), [$("#width")[0]]);
        }
        if ((parseInt(request.height) % 2 > 0)) {
            configureVideo.errors.set("INPUT_CORRECT_HEIGHT", $("#heightMultipleBeAMultipleOf2").val(),
                    [$("#height")[0]]);
        }
    }

    if (configureVideo.errors.hasErrors()) {
        return;
    }

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VideoNotFoundException", videoNotFoundFunction);
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.WidgetNotFoundException", widgetVideoNotFoundFunction);
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VideoWriteException", videoWriteExceptionFunction);
    serviceCall.addExceptionHandler(
            "dwr.engine.timeout", handleVideoTimeoutException);
    createBackground();
    swfUploaderCreateLoadingMessage("Saving settings...");
    serviceCall.executeViaDwr("CreateVideoService", "execute", request, function() {
        // Setting first check in 1sec because video may be already prepared.
        configureVideo.videoIntervalCheckTime = 1000;
        configureVideo.videoConvertingIntervalFunc = setInterval("configureVideo.onVideoConvert(configureVideo.closeAfterSaving)", configureVideo.videoIntervalCheckTime);
    });
};

// ---------------------------------------------------------------------------------------------------------------------

configureVideo.onVideoConvert = function (closeAfterSaving) {
    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.WidgetNotFoundException",
            widgetVideoNotFoundFunction);
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VideoWriteException",
            videoWriteExceptionFunction);
    serviceCall.executeViaDwr("IsWidgetVideoPreparedService", "execute", configureVideo.settings.widgetId,
            $("#selectedVideoItemId").val(), function(response) {
        if (response) {
            if (closeAfterSaving) {
                if ($("#dashboardPage")[0]) {
                    $("#itemName" + configureVideo.settings.videoItemId).html(configureVideo.settings.videoName);
                    closeConfigureWidgetDiv();
                } else if (configureVideo.settings.widgetId) {
                    closeConfigureWidgetDivWithUpdate(response);
                } else {
                    closeConfigureWidgetDiv();
                }
            } else {
                updateWidgetInfo(response);
            }

            removeVideoLoadingMessage();
            clearVideoTimeout();
        } else {
            // Here we are adding 1000ms to interval of every check so user won't wait long if video have been already prepared.
            // also we are changing message from 'Saving settings' to 'Converting video' on 3000ms interval.
            clearVideoTimeout();

            if (configureVideo.videoIntervalCheckTime == 3000) {
                changeLoadingMessage($("#convertingVideo").val());
            }

            if (configureVideo.videoIntervalCheckTime < 10000) {
                configureVideo.videoIntervalCheckTime += 1000;
            }

            configureVideo.videoConvertingIntervalFunc = setInterval("configureVideo.onVideoConvert(configureVideo.closeAfterSaving)", configureVideo.videoIntervalCheckTime);
        }
    });
};

// ---------------------------------------------------------------------------------------------------------------------

function clearVideoTimeout() {
    if (configureVideo.videoConvertingIntervalFunc) {
        clearInterval(configureVideo.videoConvertingIntervalFunc);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function videoWriteExceptionFunction() {
    clearVideoTimeout();
    configureVideo.errors.set("VIDEO_WRITE_EXCEPTION", $("#errorWhileTranscodingVideo").val(), [$("#videoSelect")[0]]);
    removeVideoLoadingMessage();
}

// ---------------------------------------------------------------------------------------------------------------------

function widgetVideoNotFoundFunction() {
    clearVideoTimeout();
    configureVideo.errors.set("WIDGET_VIDEO_NOT_FOUND", $("#widgetVideoNotFound").val());
    removeVideoLoadingMessage();
}

// ---------------------------------------------------------------------------------------------------------------------

function videoNotFoundFunction() {
    clearVideoTimeout();
    configureVideo.errors.set("VIDEO_NOT_FOUND", $("#videoNotFound").val());
    removeVideoLoadingMessage();
}

// ---------------------------------------------------------------------------------------------------------------------

function handleVideoTimeoutException() {
}

// ---------------------------------------------------------------------------------------------------------------------

function removeVideoLoadingMessage() {
    removeBackground();
    swfUploaderRemoveLoadingMessage();
}

// ---------------------------------------------------------------------------------------------------------------------

function showVideoHelpWindow(id) {
    var videoHelpWindow = createConfigureWindow({width:600, height:500});
    videoHelpWindow.setContent($("#" + id)[0].innerHTML);
}

// ---------------------------------------------------------------------------------------------------------------------

function disableConfigureVideoWidget() {
    disableControl($("#videoSelect")[0]);

    disableControl($("#videoName")[0]);
    disableControl($("#width")[0]);
    disableControl($("#height")[0]);
    disableControl($("#displaySmallOptionsCheckbox")[0]);
    disableControl($("#smallOptions")[0]);
    disableControl($("#displayLargeOptionsCheckbox")[0]);
    disableControl($("#largeOptions")[0]);
    disableControl($("#currentPage")[0]);
    disableControl($("#newPage")[0]);
    disableControl($("#keywords")[0]);
    disableControl($("#videoShowEditorLink")[0]);

    disableControl($("#widgetImageWidth")[0]);
    disableControl($("#widgetImageHeight")[0]);
    disableControl($("#saveProportionCheckbox")[0]);

    $("#windowSave", $("#configureVideoButtons")[0]).hide();
    $("#windowApply", $("#configureVideoButtons")[0]).hide();
    $("#windowCancel", $("#configureVideoButtons")[0]).val("Close");

    $("#videoReadOnlyMessage").show();
    $("#videoErrors").hide();
}