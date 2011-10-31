// ---------------------------------------------------------------------------------------------------------------------
var errors;
function showBulkUploadWindow(formId, formItemId) {
    var bulkUploadWindow = createConfigureWindow({width:800, height:500});
    new ServiceCall().executeViaDwr("BulkUploadImageService", "show", formId, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        bulkUploadWindow.setContent(response);
        //Accuring the error block
        errors = new Errors();
        showImageBulkUploader(formId, formItemId);
    });
}

// ---------------------------------------------------------------------------------------------------------------------
function showImageBulkUploader(formId, formItemId) {
    removeUploaders(uploaderIds.BULK_UPLOADER_ID);
    try {
        document.getElementById("imageBulkUploadButtonContainer").innerHTML = "<span id='imageBulkUploadButtonPlaceHolder'></span>";
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
            progressTarget : "bulkUploadProgress",
            uploadedFilesNumber : "uploadedFilesNumber",
            formId: formId,
            formItemId: formItemId
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "170",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILES,
        button_placeholder_id: "imageBulkUploadButtonPlaceHolder",

        file_queued_handler : imageBulkQueued,
        upload_error_handler : imageBulkUploadError,
        upload_start_handler : imageBulkFileUploadStart,
        upload_complete_handler: bulkUploadComplete,
        file_queue_error_handler : swfUploaderFileQueueError,
        upload_progress_handler : swfUploaderUploadProgress ,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.swfBulkUploaderUrl = "/formImageBulkUpload.action;jsessionid=" + getCookie("JSESSIONID") + "?formId=" + formId + "&formItemId=" + formItemId;
    uploader.id = uploaderIds.BULK_UPLOADER_ID;
    putUploader(uploader);
}
// ---------------------------------------------------------------------------------------------------------------------

function showFormsAfterBulkUpload() {
    removeUploaders(uploaderIds.BULK_UPLOADER_ID);
    closeConfigureWidgetDiv();
    updateManageFormRecordsWindow();
}

// ---------------------------------------------------------------------------------------------------------------------
function startImageBulkUploading(loginedUserId) {
    errors.clear();
    var uploader = getUploaderById(uploaderIds.BULK_UPLOADER_ID);
    if (uploader) {
        if (uploader.getFile()) {
            uploader.setUploadURL(uploader.swfBulkUploaderUrl + "&loginedUserId=" + loginedUserId);
            uploader.startUpload();
        } else {
            errors.set("ERROR", document.getElementById("emptyFile").value, [document.getElementById("imageFileName")]);
        }
    }
}
// ---------------------------------------------------------------------------------------------------------------------
