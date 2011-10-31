window.uploadFilesControl = {

    bind: function (settings) {
        var button = document.createElement("input");
        button.type = "button";
        button.value = settings.label;
        button.className = "but_w170_misc";
        $(settings.element).append(button);

        var containerSpan = document.createElement("span");
        $(containerSpan).css("margin-right", 10);
        $(containerSpan).css("position", "relative");
        $(containerSpan).css("top", 0);
        $(containerSpan).css("left", -170);
        $(containerSpan).css("cursor", "pointer");
        $(containerSpan).bind("mouseout", function () {
            $(button)[0].className = 'but_w170_misc';
        });

        $(containerSpan).bind("mouseover", function () {
            $(button)[0].className = 'but_w170_Over_misc';
        });

        $(settings.element).append(containerSpan);

        settings.containerSpan = containerSpan;
        showImageUploader1(settings);
    }

};

function showImageUploader1(settings) {
    settings.containerSpan.style.visibility = "visible";
    removeUploaders(uploaderIds.IMAGE_FILE_UPLOADER_ID);

    try {
        settings.containerSpan.innerHTML = "<span id='imageButtonPlaceHolder'></span>";
    } catch(exception) {
    }

    var uploader = new SWFUpload({
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : SUPPORTED_IMAGE_FORMATS,
        file_types_description : "Image Files",
        file_upload_limit : 1000000,
        file_queue_limit : 0,
        file_size_limit : "10Mb",
        custom_settings: {
            settings: settings,
            progressTarget: "fileProgressDivId"
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "170",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILES,
        button_placeholder_id: "imageButtonPlaceHolder",

        file_queued_handler : imageQueued1,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_start_handler : imageUploadStart1,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : imageUploadError1,
        upload_success_handler : imageUploadSuccess1,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    });
    uploader.afterUploadFunction = function () {
        removeLoadingArea();

        if (settings.onClose) {
            settings.onClose();
        }
    };
    uploader.swfUploaderUrl = settings.customUploadAction ? settings.customUploadAction : "/uploadImage.action";
    uploader.id = uploaderIds.IMAGE_FILE_UPLOADER_ID;
    putUploader(uploader);
}

function imageUploadSuccess1(file, serverData) {
    var progress = new FileProgress(file, this.customSettings.progressTarget);
    progress.setComplete();
    if (this.isFilesQueueEmpty()) {
        var settings = this.customSettings.settings;
        swfUploaderHandleUploadSuccess(file, serverData, this);
        showImageUploader1(settings);
    }
}

function imageUploadError1(file, errorCode, message) {
    var settings = this.customSettings.settings;
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showImageUploader1(settings);
}

function imageUploadStart1() {
    var backgroundExist = createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        if (!backgroundExist) {
            var fileProgresDiv = window.parent.document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            var queuedFiles = this.getQueuedFiles();
            if (queuedFiles.length > 1) {
                fileProgresDiv.style.width = "370px";
                fileProgresDiv.style.overflowY = "auto";
                fileProgresDiv.style.overflowX = "hidden";
            } else {
                fileProgresDiv.style.width = "355px";
            }
            loadingMessage.appendChild(fileProgresDiv);
            var height = 0;
            for (var i in queuedFiles) {
                height += 65;
                var progress = new FileProgress(queuedFiles[i], this.customSettings.progressTarget);
                progress.showCancelButton(this, false);
            }
            height = height > 180 ? 180 : height;
            fileProgresDiv.style.height = height + "px";
            centerElement({elementToCenter:loadingMessage});
        }
    }
    catch (ex) {
    }
    return true;
}

function imageQueued1() {
    var uploader = getUploaderById(uploaderIds.IMAGE_FILE_UPLOADER_ID);
    var uploaderCustomParameters = "";
    $(uploader.customSettings.settings.customParameters).each(function () {
        uploaderCustomParameters = "&" + this;
    });
    if (uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID")
                    + "?siteId=" + uploader.customSettings.settings.siteId + uploaderCustomParameters);
            uploader.startUpload();
        }
    }
}