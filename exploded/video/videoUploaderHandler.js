function videoQueued(file) {
    startVideoUploading(document.getElementById('siteId').value);
}

function videoUploadStart(file) {
    var backgroundExist = createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        if (!backgroundExist) {
            var fileProgresDiv = window.parent.document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "55px";
            loadingMessage.appendChild(fileProgresDiv);
            centerElement({elementToCenter:loadingMessage});
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.showCancelButton(this, true);
            fileProgresDiv.style.height = 65 + "px";
        }
    }
    catch (ex) {
    }
    return true;
}

function videoUploadSuccess(file, serverData) {
    var fileSizeLimit = this.fileSizeLimit;
    swfUploaderHandleUploadSuccess(file, serverData, this);
    showVideoUploader(fileSizeLimit);
    updateVideoSelect(serverData);
}

function videoUploadError(file, errorCode, message) {
    var fileSizeLimit = this.fileSizeLimit;
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showVideoUploader(fileSizeLimit);
}

