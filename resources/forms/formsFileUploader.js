var swfUploaders = new Array();

function showFileUploadFields(fileTypesDescription, buttonPlaceholderId, widgetId, id, formItemName, position, itemName,
                              videoFileSizeLimit, bulkUploaderId) {
    var textFileNameId = (id + "TxtFileName");
    document.getElementById(textFileNameId).value = "";
    if (document.getElementById(id + "PreviewImageTxtFileName")) {
        document.getElementById(id + "PreviewImageTxtFileName").value = "";
    }
    showFormFileUploaderButtons(id, bulkUploaderId);
    destroyAllUploadersById(id);
    var spanContainer = document.getElementById(id + "SpanButtonContainer");
    if (spanContainer) {
        spanContainer.innerHTML = "<span id='" + id + "SpanButtonPlaceHolder'></span>";
    }
    var settings = {
        flash_url : "/SWFUpload/swfupload.swf",
        upload_url: "#",
        file_types : createFileTypesByFormItemName(formItemName),
        file_types_description : fileTypesDescription,
        file_upload_limit : 1,
        file_size_limit : createFileMaxSize(formItemName, videoFileSizeLimit),
        file_queue_limit : 0,
        custom_settings : {
            progressTarget : "fileProgressDivId",
            text_fileName_id: textFileNameId
        },
        debug: false,
        prevent_swf_caching : true,

        // Button settings
        button_width: "70",
        button_height: "25",
        button_cursor : SWFUpload.CURSOR.HAND,
        button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
        button_placeholder_id: buttonPlaceholderId,

        file_queued_handler : formFileQueued,
        upload_start_handler : formFileUploadStart,
        upload_success_handler : formFileUploadSuccess,
        upload_error_handler : formFileUploadError,
        file_queue_error_handler : formFileQueueError,
        upload_progress_handler : swfUploaderUploadProgress,
        button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
        file_post_name : "fileData"
    };
    var uploader = new SWFUpload(settings);
    uploader.widgetId = widgetId;
    uploader.swfUploaderQueue = 0;
    uploader.swfUploaderUrl = "/uploadFormFiles.action";
    uploader.swfVideoFileUploaderUrl = "/uploadFormVideoFiles.action";
    uploader.swfUploaderType = id;
    uploader.formItemName = formItemName;
    uploader.position = position;
    uploader.itemName = itemName;
    uploader.id = id;
    uploader.uploaderId = id;
    uploader.bulkUploaderId = bulkUploaderId;
    uploader.videoFileSizeLimit = videoFileSizeLimit;
    swfUploaders.push(uploader);

    function destroyAllUploadersById(id) {
        for (var i = swfUploaders.length - 1; i >= 0; i--) {
            if (swfUploaders[i].id == id) {
                swfUploaders[i].destroy();
                swfUploaders.splice(i, 1);
            }
        }
    }
}