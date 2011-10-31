function logoQueued(file) {
    startLogoUploading($('#childSiteRegSiteId').val());
}

function logoUploadStart(file) {
    var backgroundExist = createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        if (!backgroundExist) {
            var fileProgresDiv = window.parent.document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "65px";
            loadingMessage.appendChild(fileProgresDiv);
            centerElement({elementToCenter:loadingMessage});
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.showCancelButton(this, true);
        }
    }
    catch (ex) {
    }
    return true;
}

function logoUploadSuccess(file, serverData) {
    swfUploaderHandleUploadSuccess(file, serverData, this);
    showLogoUploader();
    var uploadedFileInfo = getInfoAboutUploadedFile(serverData);
    setLogoSrc(uploadedFileInfo.resourceUrl);
    setLogoId(uploadedFileInfo.resourceId);
    swfUploaderRemoveBackgroundLoadingMessage();
}

function logoUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showLogoUploader();
}