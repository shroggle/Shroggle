function showVideoUploader(fileSizeLimit) {
    removeUploaders(uploaderIds.VIDEO_UPLOADER_ID);
    try {
        document.getElementById("videoButtonContainer").innerHTML = "<span id='videoButtonPlaceHolder'></span>";
    } catch(exception) {
    }

    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : "*.avi;*.flv;*.mpg;*.mov;*.mpeg;*.mkv;*.mp4;*.wmv;*.divx;*.dv;*.flc;*.mp3;*.wav;",
        file_types_description : "Video/Audio Files",
        file_upload_limit : 1,
        file_size_limit : fileSizeLimit,
        file_queue_limit : 0,
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
        button_placeholder_id: "videoButtonPlaceHolder",

        file_queued_handler : videoQueued,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_start_handler : videoUploadStart,
        upload_progress_handler : swfUploaderUploadProgress,
        upload_error_handler : videoUploadError,
        upload_success_handler : videoUploadSuccess,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfUploaderUrl = "/uploadVideo.action";
    uploader.fileSizeLimit = fileSizeLimit;
    uploader.id = uploaderIds.VIDEO_UPLOADER_ID;
    putUploader(uploader);
}


function startVideoUploading(siteId) {
    configureVideo.errors.clear();
    var uploader = getUploaderById(uploaderIds.VIDEO_UPLOADER_ID);
    if (siteId && uploader) {
        if (uploader.getFile()) {
            var session = getCookie("JSESSIONID");
            uploader.setUploadURL(uploader.swfUploaderUrl + ";jsessionid=" + session + "?siteId=" + siteId);
            uploader.startUpload();
        }
    }
}

function updateVideoSelect(serverData) {
    swfUploaderRemoveLoadingMessage();
    swfUploaderCreateLoadingMessage("Saving file...");
    var interval = -1;
    checkFileStatus();

    function checkFileStatus() {
        var uploadedFileInfo = getInfoAboutUploadedFile(serverData);
        new ServiceCall().executeViaDwr("CheckResourceStatusService", "execute", uploadedFileInfo.resourceId, uploadedFileInfo.resourceType, function(status) {
            if (status == "SAVED") {
                clearInterval(interval);
                var serviceCall = new ServiceCall();
                serviceCall.addExceptionHandler(
                        LoginInAccount.EXCEPTION_CLASS,
                        LoginInAccount.EXCEPTION_ACTION);
                serviceCall.executeViaDwr("ConfigureVideoService", "showVideoSelect", document.getElementById("siteId").value, null, function (response) {
                    window.parent.document.getElementById("uploadedVideosSelect").innerHTML = response;
                    if ($("#siteOnItemRightType")[0].value != "READ") {
                        setVideoName();
                        setVideoSize();
                    }
                    swfUploaderRemoveBackgroundLoadingMessage();
                });
            } else if (status == "NOT_SAVED") {
                clearInterval(interval);
                configureVideo.errors.set("ERROR", document.getElementById("unableToSaveFile").value);
                swfUploaderRemoveBackgroundLoadingMessage();
            } else {
                if (interval == -1) {
                    interval = setInterval(checkFileStatus, 10000);
                }
            }
        });
    }
}

