// Constructor
// file is a SWFUpload file object
// targetID is the HTML element id attribute that the FileProgress HTML structure will be added to.
// Instantiating a new FileProgress object with an existing file will reuse/update the existing DOM elements
function FileProgress(file, targetID) {
    this.fileProgressID = file.id;

    this.fileProgressWrapper = window.parent.document.getElementById(this.fileProgressID);
    if (!this.fileProgressWrapper) {
        this.fileProgressWrapper = createElement("div", "progressWrapper", this.fileProgressID);
        this.fileProgressWrapper.fileProgressElement = createElement("div", "uploadProgress", "");
        this.fileProgressWrapper.progressCancel = createElement("div", "progressCancel", "");
        var clearBoth = createElement("div", "clearBoth", "");
        this.fileProgressWrapper.progressBar = createElement("div", "progressBarInProgress", "");

        this.fileProgressWrapper.appendChild(this.fileProgressWrapper.fileProgressElement);
        this.fileProgressWrapper.appendChild(this.fileProgressWrapper.progressCancel);
        this.fileProgressWrapper.appendChild(clearBoth);
        this.fileProgressWrapper.appendChild(this.fileProgressWrapper.progressBar);

        showProgress(this.fileProgressWrapper.fileProgressElement, file);

        var loadingMessageDiv = window.parent.document.getElementById(targetID);
        if (loadingMessageDiv) {
            loadingMessageDiv.appendChild(this.fileProgressWrapper);
        }
    }

    function removeItem(array, item) {
        array.splice(getElementIndex(), 1);
        function getElementIndex() {
            for (var i in array) {
                if (array[i] != item) {
                    return i;
                }
            }
            return -1;
        }
    }

    function createElement(elementName, className, id) {
        var element = window.parent.document.createElement(elementName);
        element.className = className;
        element.id = id;
        return element;
    }
}

function showProgress(parentDiv, file) {
    if (!parentDiv.progress) {
        parentDiv.progress = createProgressDivs();
        parentDiv.appendChild(parentDiv.progress);
        var texts = parentDiv.progress.childNodes[1].childNodes[1];
        texts.childNodes[0].innerHTML = "File:";
        texts.childNodes[1].innerHTML = "Status:";
        texts.childNodes[2].innerHTML = "Average speed:";
        texts.childNodes[3].innerHTML = "Time Left:";
        parentDiv.progress.childNodes[0].childNodes[1].childNodes[0].innerHTML = file.name;
    }
    var progress = parentDiv.progress.childNodes[0].childNodes[1];
    if (file.percentUploaded && file.sizeUploaded && file.size) {
        progress.childNodes[1].innerHTML = (SWFUpload.speed.formatPercent(file.percentUploaded) + " (" + SWFUpload.speed.formatBytes(file.sizeUploaded) + " of " + SWFUpload.speed.formatBytes(file.size) + ")");
    }
    if (file.averageSpeed) {
        progress.childNodes[2].innerHTML = SWFUpload.speed.formatBPS(file.averageSpeed);
    }
    if (file.timeRemaining) {
        progress.childNodes[3].innerHTML = SWFUpload.speed.formatTime(file.timeRemaining);
    }

    /*-----------------------------------------------internal functions-----------------------------------------------*/
    function createProgressDivs() {
        var container = createElement("div", "", "");

        var progressContainerTexts = createElement("div", "progressContainer", "");
        var texts = createElement("div", "texts", "");
        for (var i = 0; i < 4; i++) {
            texts.appendChild(createElement("div", "", ""));
        }
        progressContainerTexts.appendChild(createElement("div", "clearBoth", ""));
        progressContainerTexts.appendChild(texts);


        var progressContainerProgress = createElement("div", "progressContainer", "");
        var progress = createElement("div", "progress", "");
        for (i = 0; i < 4; i++) {
            progress.appendChild(createElement("div", "", ""));
        }
        progressContainerProgress.appendChild(createElement("div", "clearBoth", ""));
        progressContainerProgress.appendChild(progress);
        container.appendChild(progressContainerProgress);
        container.appendChild(progressContainerTexts);
        return container;

        function createElement(elementName, className, id) {
            var element = window.parent.document.createElement(elementName);
            element.className = className;
            element.id = id;
            return element;
        }
    }

    /*-----------------------------------------------internal functions-----------------------------------------------*/
}


FileProgress.prototype.setProgress = function (file) {
    this.fileProgressWrapper.className = "progressWrapper green";
    showProgress(this.fileProgressWrapper.fileProgressElement, file);
    this.fileProgressWrapper.progressBar.style.width = Math.ceil(file.percentUploaded) + "%";
};

FileProgress.prototype.setComplete = function () {
    removeProgress(this, ["progressWrapper blue", "progressBarComplete"]);
};

FileProgress.prototype.setError = function () {
    removeProgress(this, ["progressWrapper red", "progressBarError"]);
};

FileProgress.prototype.setCancelled = function () {
    removeProgress(this, ["progressWrapper", "progressBarError"]);
};

function removeProgress(progress, classNames) {
    progress.fileProgressWrapper.className = classNames[0];
    progress.fileProgressWrapper.progressBar.className = classNames[1];
    progress.fileProgressWrapper.progressBar.style.width = "";
    progress.disappear();
}

// Show/Hide the cancel button
FileProgress.prototype.showCancelButton = function (swfUploadInstance, triggerErrorEvent) {
    this.fileProgressWrapper.progressCancel.style.display = "block";
    if (swfUploadInstance) {
        var progressInstance = this;
        var fileID = progressInstance.fileProgressID;
        this.fileProgressWrapper.progressCancel.onclick = function () {
            progressInstance.setCancelled();
            swfUploadInstance.cancelUpload(fileID, triggerErrorEvent);
            return false;
        };
    }
};

// Fades out and clips away the FileProgress box.
FileProgress.prototype.disappear = function () {
    var opacity = 100;
    var element = this.fileProgressWrapper;
    var height = element.offsetHeight;
    var delay = 30;

    setTimeout(function () {
        disappearInternal(opacity, height, element, delay);
    }, delay);

    /*-----------------------------------------------internal functions-----------------------------------------------*/
    function disappearInternal(opacity, height, element, delay) {
        opacity -= 15;
        opacity = opacity > 0 ? opacity : 0;
        height -= 10;
        height = height > 0 ? height : 0;
        element.style.height = height + "px";
        if (navigator.appName.indexOf("Internet Explorer") != -1) {
            element.style.filter = "alpha(opacity=" + opacity + ")";
        } else {
            element.style.opacity = (opacity / 100);
        }
        if (height <= 0 || opacity <= 0) {
            element.style.display = "none";
        } else {
            setTimeout(function () {
                disappearInternal(opacity, height, element, delay);
            }, delay);
        }
    }

    /*-----------------------------------------------internal functions-----------------------------------------------*/
};