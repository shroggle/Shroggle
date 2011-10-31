function formFileQueued(file) {
    try {
        var txtFileName = document.getElementById(this.customSettings.text_fileName_id);
        if (txtFileName && file && file.name) {
            txtFileName.value = file.name;
            hideFormFileUploaderButtons(this.uploaderId, this.bulkUploaderId);
        }
    } catch (ex) {
    }
}

function formFileUploadSuccess(file, serverData) {
    try {
        var uploader = new Object();
        uploader.customSettings = this.customSettings;
        uploader.afterUploadFunction = this.afterUploadFunction;
        uploader.widgetId = this.widgetId;
        var progress = new FileProgress(file, uploader.customSettings.progressTarget);
        progress.setComplete();
        var txtFileName = document.getElementById(this.customSettings.text_fileName_id);
        if (txtFileName) {
            txtFileName.value = "";
        }
        var filesExists = uploadersStarter.filesExists(uploader.widgetId);
        var bulkFilesExists = uploadersStarter.bulkFilesExists(uploader.widgetId);
        var widgetId = uploader.widgetId;
        var afterUploadFunction = uploader.afterUploadFunction;
        var filledFormId = getInfoAboutUploadedFile(serverData).filledFormId;
        showFileUploadFields(
                this.settings.file_types_description,
                this.settings.button_placeholder_id,
                this.widgetId,
                this.id,
                this.formItemName,
                this.position,
                this.itemName,
                this.videoFileSizeLimit,
                this.bulkUploaderId);

        if (!filesExists) {
            if (bulkFilesExists) {
                uploadersStarter.startBulkUploading(widgetId, filledFormId, afterUploadFunction);
            } else {
                swfUploaderHandleUploadSuccess(file, serverData, uploader); // Executing after upload function if all files has been uploaded for this widget.
            }
        }
    } catch (ex) {
    }
}

function formFileQueueError(file, errorCode, message) {
    var fileSizeLimit = this.settings.file_size_limit;
    showFileUploadFields(
            this.settings.file_types_description,
            this.settings.button_placeholder_id,
            this.widgetId,
            this.id,
            this.formItemName,
            this.position,
            this.itemName,
            this.videoFileSizeLimit,
            this.bulkUploaderId);
    swfUploaderFileQueueError(file, errorCode, message, fileSizeLimit);
}