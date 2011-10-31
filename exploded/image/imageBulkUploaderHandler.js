function imageBulkQueued(file) {
    try {
        var progress = new FileProgress(file, this.customSettings.progressTarget);
        progress.showCancelButton(this, true);
        document.getElementById(this.customSettings.uploadedFilesNumber).innerHTML = "There are " + this.getStats().files_queued + " file(s) scheduled to upload.";
        startImageBulkUploading($("#loginedUserId").val());
    } catch (ex) {
    }
}

function imageBulkFileUploadStart(file) {
    try {
        document.getElementById(this.customSettings.uploadedFilesNumber).innerHTML = "There are " + this.getStats().files_queued + " file(s) scheduled to upload.";
    }
    catch (ex) {
    }
    return true;
}

function bulkUploadComplete(file) {
    try {
        var progress = new FileProgress(file, this.customSettings.progressTarget);
        progress.setComplete();
        if (this.isFilesQueueEmpty()) {
            document.getElementById(this.customSettings.uploadedFilesNumber).innerHTML = this.getStats().successful_uploads + " file(s) have been successfully uploaded.";
        }
    } catch (ex) {
    }
}

function imageBulkUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
}