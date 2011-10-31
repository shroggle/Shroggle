var usedBulkUploaders = {
    usedUploaders : new Array(),

    setBulkUploaderUsed : function (widgetId, id) {
        this.usedUploaders.push({widgetId:widgetId, id:id});
    },

    setBulkUploaderUnused :function (widgetId) {
        for (var i = (this.usedUploaders.length - 1); i >= 0; i--) {
            if (this.usedUploaders[i].widgetId == widgetId) {
                this.usedUploaders.splice(i, 1);
            }
        }
    },

    isBulkUploaderUsed : function (widgetId, id) {
        for (var i = 0; i < this.usedUploaders.length; i++) {
            if (this.usedUploaders[i].widgetId == widgetId && this.usedUploaders[i].id != id) {// For current widget uploader with other id has been already used.
                return true;
            }
        }
        return false;
    }
};

function getBulkUploadersByWidgetId(widgetId) {
    var uploaders = [];
    for (var i in formBulkUploaders) {
        if (formBulkUploaders[i].widgetId == widgetId) {
            uploaders.push(formBulkUploaders[i]);
        }
    }
    return uploaders;
}


function formBulkFilesQueued(file) {
    try {
        var txtFileName = document.getElementById(this.customSettings.text_fileName_id);
        if (txtFileName && file && file.name) {
            txtFileName.value = file.name;
            hideFormFileUploaderButtons(this.bulkUploaderId, this.uploaderId);
            usedBulkUploaders.setBulkUploaderUsed(this.widgetId, this.id);
            var widgetUploaders = getBulkUploadersByWidgetId(this.widgetId);
            for (var i in widgetUploaders) {
                if (widgetUploaders[i].id != this.id) {
                    hideFormFileUploaderButton(widgetUploaders[i].id);
                }
            }
        }
    } catch (ex) {
    }
}

function formBulkUploadSuccess(file, serverData) {
    try {
        usedBulkUploaders.setBulkUploaderUnused(this.widgetId);
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
        var bulkFilesExists = uploadersStarter.bulkFilesExists(uploader.widgetId);
        if (this.getQueuedFiles().length == 0) {
            var widgetId = this.widgetId;
            showFormBulkUploaders(
                    this.settings.file_types_description,
                    this.widgetId,
                    this.id,
                    this.formItemName,
                    this.position,
                    this.itemName,
                    this.videoFileSizeLimit,
                    this.uploaderId);
            showBulkUploadersForWidget(widgetId);
        }
        if (!bulkFilesExists) {
            swfUploaderHandleUploadSuccess(file, serverData, uploader); // Executing after upload function if all files has been uploaded for this widget.
        }
    } catch (ex) {
    }
}

function showBulkUploadersForWidget(widgetId) {
    var widgetUploaders = getBulkUploadersByWidgetId(widgetId);
    for (var i in widgetUploaders) {
        showFormFileUploaderButton(widgetUploaders[i].id);
    }
}

function formBulkFileQueueError(file, errorCode, message) {
    var fileSizeLimit = this.settings.file_size_limit;
    showFormBulkUploaders(
            this.settings.file_types_description,
            this.widgetId,
            this.id,
            this.formItemName,
            this.position,
            this.itemName,
            this.videoFileSizeLimit,
            this.uploaderId);
    swfUploaderFileQueueError(file, errorCode, message, fileSizeLimit);
}