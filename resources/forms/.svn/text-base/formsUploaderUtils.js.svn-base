function createFileTypesByFormItemName(formItemName) {
    if (formItemName) {
        switch (formItemName) {
            case "IMAGE_FILE_UPLOAD" : {
                return "*.gif;*.jpeg;*.jpg;*.png;*.tif;*.bmp";
            }
            case "VIDEO_FILE_UPLOAD" : {
                return "*.avi;*.flv;*.mpg;*.mov;*.mpeg;*.mkv;*.mp4;*.wmv;*.divx;*.dv;*.flc;";
            }
            case "AUDIO_FILE_UPLOAD" : {
                return "*.mp3";
            }
            case "PDF_FILE_UPLOAD" : {
                return "*.pdf";
            }
            default:{
                return "*.*";
            }
        }
    } else {
        return "*.*";
    }
}

function createFileMaxSize(formItemName, videoFileSizeLimit) {
    if (formItemName == "IMAGE_FILE_UPLOAD") {
        return "10Mb";
    }
    return videoFileSizeLimit;
}


function formFileUploadStart(file) {
    var fileObject = new Object();
    fileObject.widgetId = this.widgetId;
    createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        var fileProgresDiv = getParentWindow().document.getElementById(this.customSettings.progressTarget);
        if (!fileProgresDiv) {
            fileProgresDiv = getParentWindow().document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "50px";
            loadingMessage.appendChild(fileProgresDiv);
        }
        resizeFormFileProgressDiv(fileProgresDiv, this.widgetId);
        centerElement({elementToCenter:loadingMessage});
        var progress = new FileProgress(file, this.customSettings.progressTarget);
        progress.showCancelButton(this, true);
    } catch (ex) {
    }
    return true;

    function resizeFormFileProgressDiv(fileProgresDiv, widgetId) {
        var selectedFilesNumber = uploadersStarter.getNumberOfFiles(widgetId);
        if (selectedFilesNumber == 1 || selectedFilesNumber == 0) {
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "55px";
        } else {
            fileProgresDiv.style.width = "370px";
            fileProgresDiv.style.overflowY = "auto";
            fileProgresDiv.style.overflowX = "hidden";
        }
        var height = selectedFilesNumber * 65;
        height = height > 180 ? 180 : height;
        fileProgresDiv.style.height = height + "px";
    }
}

function formFileBulkUploadStart(file) {
    var fileObject = new Object();
    fileObject.widgetId = this.widgetId;
    createBackground();
    var loadingMessage = swfUploaderCreateLoadingMessage();
    try {
        var fileProgresDiv = getParentWindow().document.getElementById(this.customSettings.progressTarget);
        if (!fileProgresDiv) {
            fileProgresDiv = getParentWindow().document.createElement("div");
            fileProgresDiv.id = this.customSettings.progressTarget;
            fileProgresDiv.align = "left";
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "50px";
            loadingMessage.appendChild(fileProgresDiv);
        }
        resizeFormFileProgressDiv(fileProgresDiv, this.widgetId);
        centerElement({elementToCenter:loadingMessage});
        var progress = new FileProgress(file, this.customSettings.progressTarget);
        progress.showCancelButton(this, true);
    } catch (ex) {
    }
    return true;

    function resizeFormFileProgressDiv(fileProgresDiv, widgetId) {
        var selectedFilesNumber = uploadersStarter.getNumberOfBulkFiles(widgetId);
        if (selectedFilesNumber == 1 || selectedFilesNumber == 0) {
            fileProgresDiv.style.width = "355px";
            fileProgresDiv.style.height = "55px";
        } else {
            fileProgresDiv.style.width = "370px";
            fileProgresDiv.style.overflowY = "auto";
            fileProgresDiv.style.overflowX = "hidden";
        }
        var height = selectedFilesNumber * 65;
        height = height > 180 ? 180 : height;
        fileProgresDiv.style.height = height + "px";
    }
}

function showFormFileUploaderButtons(firstId, secondId) {
    try {
        showFormFileUploaderButtonInternal(firstId);
    } catch(ex) {
    }
    try {
        showFormFileUploaderButtonInternal(secondId);
    } catch(ex) {
    }
    try {
        showHideRemoveSelectedFileButton(firstId, "none");
    } catch(ex) {
    }

    function showFormFileUploaderButtonInternal(id) {
        showFormFileUploaderButton(id);
        $("#" + id + "browseFormFileButton")[0].style.display = "inline";
    }
}

function showFormFileUploaderButton(id) {
    var button = document.getElementById(id + "SpanButtonContainer");
    if (button) {
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            button.firstChild.style.height = "22px";
        } else {
            button.style.visibility = "visible";
            button.style.height = "20px";
        }
    }
}

function hideFormFileUploaderButtons(firstId, secondId) {
    try {
        hideFormFileUploaderButtonInternal(firstId);
    } catch(ex) {
    }
    try {
        hideFormFileUploaderButtonInternal(secondId);
    } catch(ex) {
    }
    try {
        showHideRemoveSelectedFileButton(firstId, "inline");
    } catch(ex) {
    }

    function hideFormFileUploaderButtonInternal(id) {
        hideFormFileUploaderButton(id);
        $("#" + id + "browseFormFileButton")[0].style.display = "none";
    }
}

function hideFormFileUploaderButton(id) {
    var button = document.getElementById(id + "SpanButtonContainer");
    if (button) {
        if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
            button.firstChild.style.height = "0px";
        } else {
            button.style.visibility = "hidden";
            button.style.height = "0px";
        }
    }
}

function showHideRemoveSelectedFileButton(id, display) {
    var removeSelectedFileButton = document.getElementById(id + "RemoveSelectedFileButton");
    if (removeSelectedFileButton) {
        removeSelectedFileButton.style.display = display;
    }
}

function formFileUploadError(file, errorCode, message) {
    swfUploaderHandleUploadError(file, errorCode, message, this);
    showFormFileUploaderButtons(this.uploaderId, this.bulkUploaderId);
}

var uploadersStarter = {
    startFileUploading : function (widgetId, filledFormId, afterUploadFunction) {
        var swfUploadStarted = false;
        if (widgetId || filledFormId) {
            for (var i = 0; i < swfUploaders.length; i++) {
                if (swfUploaders[i].widgetId == widgetId && this.startUploading(swfUploaders[i])) {
                    swfUploaders[i].setUploadURL(this.createUrl(filledFormId, swfUploaders[i]));
                    swfUploaders[i].afterUploadFunction = afterUploadFunction;
                    swfUploaders[i].startUpload();
                    if (swfUploaders[i].getFile()) {
                        swfUploadStarted = true;
                    }
                }
            }
        }
        if (!swfUploadStarted) {
            this.destroy(swfUploaders);
            if (this.bulkFilesExists(widgetId)) {
                this.startBulkUploading(widgetId, filledFormId, afterUploadFunction);
            } else if (afterUploadFunction) {
                afterUploadFunction(widgetId);
            }
        }
    },

    startBulkUploading : function (widgetId, filledFormId, afterUploadFunction) {
        var swfUploadStarted = false;
        for (var i = 0; i < formBulkUploaders.length; i++) {
            if (formBulkUploaders[i].widgetId == widgetId && this.startUploading(formBulkUploaders[i])) {
                formBulkUploaders[i].setUploadURL(this.createUrl(filledFormId, formBulkUploaders[i]));
                formBulkUploaders[i].afterUploadFunction = afterUploadFunction;
                var files = formBulkUploaders[i].getQueuedFiles();
                for (var fileIndex in files) {
                    formBulkUploaders[i].startUpload(files[fileIndex].id);
                    swfUploadStarted = true;
                }
                /*if (files.length == 1) {
                 formBulkUploaders[i].startUpload();
                 swfUploadStarted = true;
                 } else {
                 for (var fileIndex in files) {
                 if (fileIndex == 1) {//I don`t know why, but it doesn`t work without this fucking stupid check. Tolik.
                 alert(files[fileIndex].id);
                 formBulkUploaders[i].startUpload(files[fileIndex].id);
                 swfUploadStarted = true;
                 }
                 }
                 }*/
            }
        }
        if (!swfUploadStarted) {
            this.destroy(formBulkUploaders);
            if (afterUploadFunction) {
                afterUploadFunction(widgetId);
            }
        }
    },

    filesExists : function(widgetId) {
        for (var i = 0; i < swfUploaders.length; i++) {
            if (swfUploaders[i].widgetId == widgetId && swfUploaders[i].getQueuedFiles().length > 0) {
                return true;
            }
        }
        return false;
    },

    bulkFilesExists : function(widgetId) {
        for (var i = 0; i < formBulkUploaders.length; i++) {
            if (formBulkUploaders[i].widgetId == widgetId && formBulkUploaders[i].getQueuedFiles().length > 0) {
                return true;
            }
        }
        return false;
    },

    getNumberOfFiles : function(widgetId) {
        var numberOfFiles = 0;
        for (var i = 0; i < swfUploaders.length; i++) {
            if (swfUploaders[i].widgetId == widgetId) {
                numberOfFiles += swfUploaders[i].getQueuedFiles().length;
            }
        }
        return numberOfFiles;
    },

    getNumberOfBulkFiles : function(widgetId) {
        var numberOfFiles = 0;
        for (var i = 0; i < formBulkUploaders.length; i++) {
            if (formBulkUploaders[i].widgetId == widgetId) {
                numberOfFiles += formBulkUploaders[i].getQueuedFiles().length;
            }
        }
        return numberOfFiles;
    },

    startUploading : function (uploader) {
        try {
            return uploader.getFile();
        } catch(ex) {
            return false;
        }
    },

    createUrl : function (filledFormId, uploader) {
        var url = isVideoOrVideoImageField(uploader.id) ? uploader.swfVideoFileUploaderUrl : uploader.swfUploaderUrl;
        url += ";jsessionid=" + getCookie("JSESSIONID");
        url += "?formItemName=" + uploader.formItemName;
        url += "&filledFormId=" + filledFormId;
        url += "&position=" + uploader.position;
        return url;
    },

    destroy : function (uploaders) {
        for (var i in uploaders) {
            try {
                uploaders[i].destroy();
            } catch(ex) {
            }
        }
    }
};