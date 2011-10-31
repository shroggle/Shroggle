SWFUpload.prototype.isFilesQueueEmpty = function () {
    try {
        return this.getStats().files_queued == 0;
    } catch(ex) {
        return true;
    }
};

SWFUpload.prototype.getQueuedFiles = function () {
    var files = new Array();
    for (var i = 0; i < this.getStats().files_queued; i++) {
        var file = this.getFile(i);
        if (file) {
            files.push({name:file.name, id:file.id});
        }
    }
    return files;
};

SWFUpload.prototype.startUpload = function (fileID) {
    this.keepSession();
    try {
        this.queueSettings.queue_cancelled_flag = false;// We need this only if swfupload.queue is used.
    } catch(ex) {
    }
    this.callFlash("StartUpload", [fileID]);
};

SWFUpload.prototype.keepSession = function() {
    if (!this.isFilesQueueEmpty()) {
        var interval = this.getSessionKeeperInterval(this.id);
        if (interval.interval) {// We should create new session keeper only if it`s not created already.
            return;
        }
        new ServiceCall().executeViaDwr("SessionKeeperService", "execute");
        interval.interval = setInterval(this.keepSession, (5 * 60 * 1000));// 5 minutes in millis
    } else {
        this.clearSessionKeeper();
    }
};

SWFUpload.prototype.clearSessionKeeper = function() {
    var interval = this.getSessionKeeperInterval(this.id);
    if (interval.interval) {
        clearInterval(interval.interval);
    }
};

SWFUpload.prototype.getSessionKeeperInterval = function(id) {
    for (var i in sessionKeeperIntervals) {
        if (sessionKeeperIntervals[i].id == id) {
            return sessionKeeperIntervals[i];
        }
    }
    this.putSessionKeeperInterval(id, null);
    return this.getSessionKeeperInterval(id);
};

SWFUpload.prototype.putSessionKeeperInterval = function(id, interval) {
    sessionKeeperIntervals.push({id : id, interval : interval});
};

var sessionKeeperIntervals = new Array();