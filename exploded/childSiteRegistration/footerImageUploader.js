function showFooterImageUploader() {
    removeUploaders(uploaderIds.FOOTER_IMAGE_UPLOADER_ID);
    try {
        document.getElementById("footerImageButtonContainer").innerHTML = "<span id='footerImageButtonPlaceHolder'></span>";
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
            progressTarget : "fileProgressDivId",
            text_fileName_id: "footerImageName"
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "170",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_placeholder_id: "footerImageButtonPlaceHolder",

        file_queued_handler : footerImageQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        //file_dialog_complete_handler : logoDialogComplete,
        upload_start_handler : footerImageUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : footerImageUploadError,
        upload_success_handler : footerImageUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfUploaderUrl = "/uploadImage.action";
    uploader.id = uploaderIds.FOOTER_IMAGE_UPLOADER_ID;
    putUploader(uploader);
}

function startFooterImageUploading(siteId) {
    configureChildSiteRegistration.errors.clear();
    var uploader = getUploaderById(uploaderIds.FOOTER_IMAGE_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId);
            uploader.startUpload();
        } else {
            configureChildSiteRegistration.errors.set("ERROR", document.getElementById("emptyFile").value,
                    [document.getElementById("footerImageName")]);
        }
    }
}