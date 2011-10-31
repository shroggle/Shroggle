var formBulkUploaders = new Array();

function showFormBulkUploaders(fileTypesDescription, widgetId, id, formItemName, position, itemName, videoFileSizeLimit, uploaderId) {
    var textFileNameId = (uploaderId + "TxtFileName");
    document.getElementById(textFileNameId).value = "";
    if (document.getElementById(id + "PreviewImageTxtFileName")) {
        document.getElementById(id + "PreviewImageTxtFileName").value = "";
    }
    showFormFileUploaderButtons(id, uploaderId);
    destroyAllUploadersById(id);
//    removeElementFromSelectedElementsArrayByNameWidgetId(id);
    var buttonPlaceholderId = id + "SpanButtonPlaceHolder";
    var spanContainer = document.getElementById(id + "SpanButtonContainer");
    if (spanContainer) {
        spanContainer.innerHTML = "<span id='" + buttonPlaceholderId + "'></span>";
    }
    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : createFileTypesByFormItemName(formItemName),
        file_types_description : fileTypesDescription,
        file_upload_limit : 1000000,
        file_size_limit : createFileMaxSize(formItemName, videoFileSizeLimit),
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fileProgressDivId",
            text_fileName_id: textFileNameId
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "100",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILES,
        button_placeholder_id: buttonPlaceholderId,

        file_queued_handler : formBulkFilesQueued,
        upload_start_handler : formFileBulkUploadStart,
        upload_success_handler : formBulkUploadSuccess,
        upload_error_handler : formFileUploadError,
        file_queue_error_handler : formBulkFileQueueError,
        upload_progress_handler : swfUploaderUploadProgress,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.widgetId = widgetId;
    uploader.swfUploaderQueue = 0;
    uploader.swfUploaderUrl = "/bulkUploadFormFiles.action";
    uploader.swfUploaderType = id;
    uploader.formItemName = formItemName;
    uploader.position = position;
    uploader.itemName = itemName;
    uploader.id = id;
    uploader.uploaderId = uploaderId;
    uploader.bulkUploaderId = id;
    uploader.videoFileSizeLimit = videoFileSizeLimit;
    formBulkUploaders.push(uploader);

    showBulkUploadersForWidget(widgetId);

    function destroyAllUploadersById(id) {
        for (var i = formBulkUploaders.length - 1; i >= 0; i--) {
            if (formBulkUploaders[i].id == id) {
                formBulkUploaders[i].destroy();
                formBulkUploaders.splice(i, 1);
            }
        }
    }
}


