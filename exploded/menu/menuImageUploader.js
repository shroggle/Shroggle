function showMenuImageUploader() {
    removeUploaders(uploaderIds.MENU_IMAGE_UPLOADER_ID);
    try {
        document.getElementById("menuImageButtonContainer").innerHTML = "<span id='menuImageButtonPlaceHolder'></span>";
    } catch(exception) {
    }
    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : SUPPORTED_IMAGE_FORMATS,
        file_types_description : "Image Files",
        file_upload_limit : 1,
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
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_placeholder_id: "menuImageButtonPlaceHolder",

        file_queued_handler : menuImageQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_start_handler : menuImageUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : menuImageUploadError,
        upload_success_handler : menuImageUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfUploaderUrl = "/uploadMenuImage.action";
    uploader.id = uploaderIds.MENU_IMAGE_UPLOADER_ID;
    putUploader(uploader);
}

function startMenuImageUploading(siteId) {
    configureMenu.errors.clear();
    var uploader = getUploaderById(uploaderIds.MENU_IMAGE_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId);
            uploader.startUpload();
        }
    }
}