function showBackgroundImageUploader() {
    removeUploaders(uploaderIds.BACKGROUND_UPLOADER_ID);
    try {
        document.getElementById("backgroundImageButtonContainer").innerHTML = "<span id='backgroundImageButtonPlaceHolder'></span>";
    } catch(exception) {
    }
    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : SUPPORTED_IMAGE_FORMATS,
        file_types_description : "Image Files",
        file_upload_limit : 1000000,
        file_queue_limit : 0,
        file_size_limit : "10Mb",
        custom_settings : {
            progressTarget : "fileProgressDivId"
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "170",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILES,
        button_placeholder_id: "backgroundImageButtonPlaceHolder",

        file_queued_handler : backgroundImageQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_start_handler : backgroundImageUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : backgroundImageUploadError,
        upload_success_handler : backgroundImageUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfUploaderUrl = "/uploadBackgroundImage.action";
    uploader.afterUploadFunction = updateBackgroundImages;
    uploader.id = uploaderIds.BACKGROUND_UPLOADER_ID;
    putUploader(uploader);
}


function startBackgroundImageUploading(siteId) {
    var uploader = getUploaderById(uploaderIds.BACKGROUND_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId);
            uploader.startUpload();
        }
    }
}

function updateBackgroundImages() {
    new ServiceCall().executeViaDwr("ConfigureBackgroundService", "showUploadedBackgroundImages", document.getElementById("siteId").value, function (response) {
        window.parent.document.getElementById("uploadedBackgroundImages").innerHTML = response;
        document.getElementById("selectedBackgroundImageId").value = document.getElementById("lastUploadedBackgroundImageId").value;
        updateBackgroundImage();
        swfUploaderRemoveBackgroundLoadingMessage();
    });
}