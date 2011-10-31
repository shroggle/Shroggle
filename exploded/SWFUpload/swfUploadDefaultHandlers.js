function swfUploaderFileQueueError(file, errorCode, message, fileSizeLimit) {
    try {
        if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
            showError("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")), textFileNameId);
            return;
        }
        try {
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.setError();
            var textFileNameId = this.customSettings.text_fileName_id;
            fileSizeLimit = fileSizeLimit ? fileSizeLimit : this.settings.file_size_limit;
        } catch(ex) {
            fileSizeLimit = fileSizeLimit ? fileSizeLimit : "10Mb";
        }
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:{
                showError("\"" + file.name + "\" is too big. It should'n be more than " + fileSizeLimit + ".\nPlease, convert it before uploading.", textFileNameId);
                break;
            }
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:  {
                showError("Cannot upload Zero Byte files.", textFileNameId);
                break;
            }
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:{
                showError("Invalid File Type.", textFileNameId);
                break;
            }
            default:{
                if (file !== null) {
                    showError("Unhandled Error", textFileNameId);
                }
                break;
            }
        }
        swfUploaderRemoveBackgroundLoadingMessage();
    } catch (ex) {
        swfUploaderRemoveBackgroundLoadingMessage();
    }
}


function swfUploaderHandleUploadError(file, errorCode, message, uploader) {
    try {
        var progress = new FileProgress(file, uploader.customSettings.progressTarget);
        progress.setError();
        var textFileNameId = uploader.customSettings.text_fileName_id;
        switch (errorCode) {
            case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:{
                showError("File upload error, File name: " + file.name, textFileNameId);
                break;
            }
            case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:{
                showError("Upload Failed, File name: " + file.name + ", File size: " + file.size, textFileNameId);
                break;
            }
            case SWFUpload.UPLOAD_ERROR.IO_ERROR: {
                showError("We were unable to upload your file " + file.name, textFileNameId);
                break;
            }
            case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:{
                showError("Security Error, File name: " + file.name, textFileNameId);
                break;
            }
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:{
                showError("Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size, textFileNameId);
                break;
            }
            case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:{
                showError("File Validation Failed, File name: " + file.name + ", File size: " + file.size, textFileNameId);
                break;
            }
            case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED: {
                var txtFileName = document.getElementById(textFileNameId);
                if (txtFileName) {
                    txtFileName.value = "";
                }
                progress.setCancelled();
                break;
            }
            case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED: {
                showError("Stopped", textFileNameId);
                break;
            }
            default: {
                showError("Unhandled Error: " + errorCode, textFileNameId);
                break;
            }
        }
        swfUploaderRemoveBackgroundLoadingMessage();
    } catch (ex) {
        swfUploaderRemoveBackgroundLoadingMessage();
    }
}

function showError(errorText, fileNameId) {
    try {
        configureMenu.errors.set(errorText, errorText,
                [document.getElementById(fileNameId)]);
    } catch(ex) {
        alert(errorText);
    }
}


function swfUploaderCreateLoadingMessage(messageText) {
    if (!messageText) {
        messageText = "Uploading...";
    }
    return createLoadingMessage({text:messageText, color:"darkgreen"});
}


function swfUploaderRemoveLoadingMessage() {
    removeLoadingMessage();
}

var progressDelays = new Array();
function swfUploaderUploadProgress(file, bytesLoaded, bytesTotal) {
    if (updateProgress(file.id)) {
        try {
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.setProgress(file);
        } catch (ex) {
        }
    }

    function updateProgress(fileId) {
        if (bytesTotal <= (1024 * 500)) {
            return true;
        }
        var delay = getDelayByIdOrCreateNew(fileId);
        return (delay.delay++) % 10 == 0;
    }

    function getDelayByIdOrCreateNew(id) {
        for (var i in progressDelays) {
            if (progressDelays[i].id = id) {
                return progressDelays[i];
            }
        }
        var delay = {id:id, delay:0};
        progressDelays.push(delay);
        return delay;
    }
}

function swfUploaderRemoveBackgroundLoadingMessage() {
    removeBackground();
    swfUploaderRemoveLoadingMessage();
}

function swfUploaderHandleUploadSuccess(file, serverData, uploader) {
    try {
        var progress = new FileProgress(file, uploader.customSettings.progressTarget);
        progress.setComplete();
        if (uploader.afterUploadFunction) {
            uploader.afterUploadFunction(uploader.widgetId);
            try {
                uploader.destroy();
            } catch(ex) {
                //ignore
            }
        }
    } catch (ex) {
        swfUploaderRemoveBackgroundLoadingMessage();
    }
}

function getInfoAboutUploadedFile(serverData) {
    return {resourceId:getValueById("resourceId"), resourceType:getValueById("resourceType"),
        resourceUrl:getValueById("resourceUrl"), filledFormId:getValueById("filledFormId")};

    function getValueById(id) {
        var value;
        $(serverData).each(function() {
            if ($(this).attr("id") == id) {
                value = $(this).val();
            }
        });
        return value;
    }
}
