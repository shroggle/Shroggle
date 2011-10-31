function showSiteIconUploader() {
    removeUploaders(uploaderIds.SITE_ICON_UPLOADER_ID);
    try {
        document.getElementById("siteIconButtonContainer").innerHTML = "<span id='siteIconButtonPlaceHolder'></span>";
    } catch(exception) {
    }
    $("#browseAndUploadIconButton")[0].style.display = "inline";
    $("#siteIconButtonContainer")[0].style.display = "inline";     
    $("#removeIconButton")[0].style.display = "none";
    if (isIconExists()) {
        $("#browseAndUploadIconButton")[0].style.display = "none";
        $("#siteIconButtonContainer")[0].style.display = "none";
        $("#removeIconButton")[0].style.display = "inline";
        return;
    }
    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : "*.ico;*.png;*.gif",
        file_types_description : "Icon Files",
        file_upload_limit : 1,
        file_queue_limit : 0,
        file_size_limit : "1Mb",
        custom_settings : {
            progressTarget : "fileProgressDivId",
            text_fileName_id: "siteIconName"
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "170",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_placeholder_id: "siteIconButtonPlaceHolder",

        file_queued_handler : siteIconQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        //file_dialog_complete_handler : siteIconDialogComplete,
        upload_start_handler : siteIconUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : siteIconUploadError,
        upload_success_handler : siteIconUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfUploaderUrl = "/uploadIcon.action";
    uploader.id = uploaderIds.SITE_ICON_UPLOADER_ID;
    putUploader(uploader);
}

function startSiteIconUploading() {
    var uploader = getUploaderById(uploaderIds.SITE_ICON_UPLOADER_ID);
    if (uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID"));
            uploader.startUpload();
        }
    }
}