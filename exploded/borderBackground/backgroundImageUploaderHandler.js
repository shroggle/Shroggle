function backgroundImageQueued(file) {
    startBackgroundImageUploading(document.getElementById('siteId').value);
}

function backgroundImageUploadStart(file) {
    var backgroundExist = createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        if (!backgroundExist) {
            var fileProgresDiv = window.parent.document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            var queuedFiles = this.getQueuedFiles();
            if (queuedFiles.length > 1) {
                fileProgresDiv.style.width = "370px";
                fileProgresDiv.style.overflowY = "auto";
                fileProgresDiv.style.overflowX = "hidden";
            } else {
                fileProgresDiv.style.width = "355px";
                fileProgresDiv.style.height = "55px";
            }
            loadingMessage.appendChild(fileProgresDiv);
            var height = 0;
            for (var i in queuedFiles) {
                height += 65;
                var progress = new FileProgress(queuedFiles[i], this.customSettings.progressTarget);
                progress.showCancelButton(this, false);
            }
            height = height > 180 ? 180 : height;
            fileProgresDiv.style.height = height + "px";
            centerElement({elementToCenter:loadingMessage});
        }
    }
    catch (ex) {
    }
    return true;
}

function backgroundImageUploadSuccess(file, serverData) {
    var progress = new FileProgress(file, this.customSettings.progressTarget);
    progress.setComplete();
    if (this.isFilesQueueEmpty()) {
        swfUploaderHandleUploadSuccess(file, serverData, this);
        showBackgroundImageUploader();
    }
}

function backgroundImageUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showBackgroundImageUploader();
}

