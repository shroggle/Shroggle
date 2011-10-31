function menuImageQueued(file) {
    startMenuImageUploading(document.getElementById('siteId').value);
}

function menuImageUploadStart(file) {
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

function menuImageUploadSuccess(file, serverData) {
    swfUploaderHandleUploadSuccess(file, serverData, this);
    showMenuImageUploader();

    var uploadedFileInfo = getInfoAboutUploadedFile(serverData);
    setMenuImageSrc(uploadedFileInfo.resourceUrl);
    setMenuImageId(uploadedFileInfo.resourceId);

    swfUploaderRemoveBackgroundLoadingMessage();
    setMenuItemDetailsChanged(true);
}

function menuImageUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showMenuImageUploader();
}


function setMenuImageSrc(src) {
    document.getElementById("menuItemImage").src = src;
    document.getElementById("menuItemImageHolder").style.visibility = "visible";
}

function setMenuImageId(id) {
    document.getElementById("menuItemImageId").value = id;
}