var screenShotUploader = {

    showUploader : function (pageId) {
        var id = (uploaderIds.SCREEN_SHOT_UPLOADER_ID + pageId);
        removeUploaders(id);
        try {
            document.getElementById("screenShotButtonContainer" + pageId).innerHTML = "<span id='screenShotButtonPlaceHolder" + pageId + "'></span>";
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
                text_fileName_id: "screenShotName"
            },
            debug: false,
            prevent_swf_caching : true,

            // Button settings
            button_width: "170",
            button_height: "25",
            button_cursor : SWFUpload.CURSOR.HAND,
            button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
            button_placeholder_id: ("screenShotButtonPlaceHolder" + pageId),

            file_queued_handler : screenShotUploaderHandler.screenShotQueued,
            file_queue_error_handler : swfUploaderFileQueueError,
            //file_dialog_complete_handler : screenShotDialogComplete,
            upload_start_handler : screenShotUploaderHandler.screenShotUploadStart,
            upload_progress_handler : swfUploaderUploadProgress,
            upload_error_handler : screenShotUploaderHandler.screenShotUploadError,
            upload_success_handler : screenShotUploaderHandler.screenShotUploadSuccess,
            button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
            file_post_name : "fileData"
        };
        var uploader = new SWFUpload(settings);
        uploader.swfUploaderUrl = "/uploadImage.action";
        uploader.id = id;
        putUploader(uploader);
    },

    startUploading : function (pageId) {
        var uploader = getUploaderById((uploaderIds.SCREEN_SHOT_UPLOADER_ID + pageId));
        var siteId = $("#siteId").val();
        if (siteId && uploader) {
            if (uploader.getFile()) {
                uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + getCookie("JSESSIONID") + "?siteId=" + siteId);
                uploader.startUpload();
            }
        }
    }
};