function imageQueued(file) {
    startImageUploading(document.getElementById('siteId').value);
}

function imageFileQueued(file) {
    startImageFileUploading(document.getElementById('siteId').value);
}

function imageUploadStart(file) {
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


function imageFileUploadStart(file) {
    var backgroundExist = createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        if (!backgroundExist) {
            var fileProgressDiv = window.parent.document.createElement("div");
            fileProgressDiv.id = this.customSettings.progressTarget;
            fileProgressDiv.align = "left";
            fileProgressDiv.style.width = "355px";
            fileProgressDiv.style.height = "65px";
            loadingMessage.appendChild(fileProgressDiv);
            centerElement({elementToCenter:loadingMessage});
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.showCancelButton(this, true);
        }
    } catch (ex) {
    }
    return true;
}

function imageFileUploadSuccess(file, serverData) {
    var siteId = this.customSettings.site_id;
    var fileType = this.customSettings.image_file_type;
    swfUploaderHandleUploadSuccess(file, serverData, this);
    showImageFileUploader(siteId, fileType);
}

function imageFileUploadError(file, errorCode, message) {
    var siteId = this.customSettings.site_id;
    var fileType = this.customSettings.image_file_type;
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showImageFileUploader(siteId, fileType);
}
