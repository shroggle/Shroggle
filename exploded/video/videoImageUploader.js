function showVideoImageUploader() {
    removeUploaders(uploaderIds.VIDEO_IMAGE_UPLOADER_ID);
    try {
        document.getElementById("videoImageButtonContainer").innerHTML = "<span id='videoImageButtonPlaceHolder'></span>";
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
        button_placeholder_id: "videoImageButtonPlaceHolder",

        file_queued_handler : videoImageQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_start_handler : videoImageUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : videoImageUploadError,
        upload_success_handler : videoImageUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfUploaderUrl = "/uploadVideoImage.action";
    uploader.afterUploadFunction = updateVideoImages;
    uploader.id = uploaderIds.VIDEO_IMAGE_UPLOADER_ID;
    putUploader(uploader);
}


function startVideoImageUploading(siteId) {
    configureVideo.errors.clear();
    var uploader = getUploaderById(uploaderIds.VIDEO_IMAGE_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId);
            uploader.startUpload();
        }
    }
}

function updateVideoImages() {
    new ServiceCall().executeViaDwr("ConfigureVideoService", "showVideoImages", $("#siteId").val(), $("#selectedVideoItemId").val(), function (response) {
        window.parent.document.getElementById("uploadedVideoImages").innerHTML = response;
        document.getElementById("selectedImageForVideoId").value = document.getElementById("lastUploadedVideoImageId").value;
        updateImageForVideo();
        swfUploaderRemoveBackgroundLoadingMessage();
    });
}