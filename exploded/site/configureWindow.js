// ---------------------------------------------------WINDOW CREATION&SHOWING-------------------------------------------

// settings.uniqueIdentifiers - means that all elements in other opened windows will change their id's to 'malformedId',
// and after this window will be closed id's would became normal again. Note, that currently all form windows are by default
// opened with this parameter as true.
function createConfigureWindow(settings) {
    settings = settings ? settings : new Object();
    var configureWindow = new Window(settings);

    if (settings.uniqueIdentifiers) {
        malformIdsInOpenedWindows();
    }

    getWindowStack().push(configureWindow);

    return configureWindow;
}

function malformIdsInOpenedWindows() {
    for (var i = 0; i < getWindowStack().length; i++) {
        var configureWindow = getWindowStack()[i];

        malformIdsInternal(configureWindow.getWindowContentDiv());
    }
}

function malformIdsInternal(content) {
    $(content).find('[id]').each(function () {
        if (this.id != "malformedId") {
            $(this).attr('oldUniqueId', this.id);
            this.id = "malformedId";
        }
    });

    $(content).find('[class]').each(function () {
        if (this.className != "malformedClass") {
            $(this).attr('oldUniqueClass', this.className);
            this.className = "malformedClass";
        }
    });

    $(content).find('[name]').each(function () {
        if ($(this).attr("name") != "malformedName") {
            $(this).attr('oldUniqueName', $(this).attr("name"));
            $(this).attr("name", "malformedName");
        }
    });
}

function undoMalformIdsInOpenedWindows() {
    for (var i = 0; i < getWindowStack().length; i++) {
        var configureWindow = getWindowStack()[i];

        undoMalformIdsInternal(configureWindow.getWindowContentDiv());
    }
}

function undoMalformIdsInternal(content) {
    $(content).find('[id]').each(function () {
        if ($(this).attr('oldUniqueId') != null) {
            this.id = $(this).attr('oldUniqueId');
            this.oldUniqueId = null;
        }
    });

    $(content).find('[class]').each(function () {
        if ($(this).attr('oldUniqueClass') != null) {
            this.className = $(this).attr('oldUniqueClass');
            this.oldUniqueClass = null;
        }
    });

    $(content).find('[name]').each(function () {
        if ($(this).attr('oldUniqueName') != null) {
            $(this).attr('name', $(this).attr('oldUniqueName'));
            this.oldUniqueName = null;
        }
    });
}

function createConfigureWidgetIframe(settings) {
    createConfigureWindow(settings);

    //Creating iframe that will contain blog/forum or smth else content
    var configureWidget = document.createElement("iframe");
    configureWidget.style.width = "100%";
    configureWidget.id = settings.id ? settings.id : "";
    $(configureWidget).bind("load", function() {
        getActiveWindow().enableContent();
        if (settings.customOnLoadFunction) {
            settings.customOnLoadFunction();
        }
    });

    //Creating border div that will contain all content
    var borderDiv = window.parent.document.createElement("div");
    borderDiv.className = "windowOneColumn";

    if (settings.titleText) {
        var titleDiv = window.parent.document.createElement("span");
        titleDiv.className = "pageWidgetTitle";
        titleDiv.innerHTML = settings.titleText;
        borderDiv.appendChild(titleDiv);
    }

    //Creating another div so our border will fit all page height despite lack of content
    var pageContent = window.parent.document.createElement("div");
    pageContent.id = "pageContentDiv";
    pageContent.style.height = (settings.height - 120) + "px";
    pageContent.style.width = (settings.width - 65) + "px";
    pageContent.style.position = "relative";
    pageContent.style.overflow = "auto";
    borderDiv.appendChild(pageContent);

    getActiveWindow().getWindowContentDiv().appendChild(borderDiv);

    //Creating close button at the bottom of the window
    var closeButton = window.parent.document.createElement("input");
    closeButton.type = "button";
    closeButton.value = "Close";
    closeButton.className = "but_w73";
    closeButton.onmouseover = function () {
        this.className = "but_w73_Over";
    };
    closeButton.onmouseout = function () {
        this.className = "but_w73";
    };
    closeButton.onclick = closeConfigureWidgetDiv;

    //Creating buttons div
    var closeButtonDiv = window.parent.document.createElement("div");
    closeButtonDiv.align = "right";
    closeButtonDiv.className = "iFrameWindowButtonsDiv";
    closeButtonDiv.appendChild(closeButton);

    borderDiv.appendChild(closeButtonDiv);

    //Creating custom buttons
    if (settings.customButtons && settings.customButtons.length != 0) {
        for (var i = 0; ; i++) {
            var customButton = settings.customButtons[i];
            if (customButton == undefined) {
                break;
            }

            var customButtonInstance = window.parent.document.createElement("input");
            customButtonInstance.type = "button";
            customButtonInstance.value = customButton.value;
            customButtonInstance.originalClassName = customButton.className;
            customButtonInstance.className = customButton.className;
            customButtonInstance.onmouseover = function () {
                this.className = this.originalClassName + "_Over";
            };
            customButtonInstance.onmouseout = function () {
                this.className = this.originalClassName;
            };

            if (customButton.customFunction) {
                customButtonInstance.onclick = customButton.customFunction;
            }

            if (customButton.display) {
                customButtonInstance.style.display = customButton.display;
            }

            customButtonInstance.id = customButton.id;

            $(customButtonInstance).insertBefore(closeButton);
        }
    }

    configureWidget.style.height = "100%";
    configureWidget.frameBorder = "0";

    pageContent.appendChild(configureWidget);

    return configureWidget;
}

// ------------------------------------------------WINDOW CREATINON&SHOWING END-----------------------------------------

// ------------------------------------------------WINDOW CLOSING&HIDING------------------------------------------------

//Closes edit widget div with confim about saving changes(by clickng cancel)
function closeConfigureWidgetDivWithConfirm() {
    if (!isAnyWindowOpened()) {
        return false;
    }
    // Ensure that if we forgot to disable fields in configure window that user will not see alert about
    // not saved changes and not save them.
    if ($("#siteOnItemRightType").val() != "READ" && isWindowSettingsChanged() && confirm(window.parent.internationalErrorTexts.saveUnsavedChanges)) {
        getActiveWindow().clickSave();
    } else {
        closeConfigureWidgetDiv();
    }
    return true;
}

//Just closes edit widget div
function closeConfigureWidgetDiv() {
    if (!isAnyWindowOpened()) return;

    var uniqueIdentifiers = getActiveWindow().settings.uniqueIdentifiers;
    closeTinyMCE();
    //Remove flash uploaders
    for (var i in uploaderIds) {
        removeUploaders(uploaderIds[i]);
    }
    //Close active window
    var afterCloseEvent = getActiveWindow().onAfterClose;
    getActiveWindow().onAfterClose = null;

    getActiveWindow().close();
    //Remove window from window stack
    getWindowStack().pop();

    if (uniqueIdentifiers) {
        undoMalformIdsInOpenedWindows();
    }

    if (afterCloseEvent) {
        afterCloseEvent();
    }
}

// Closes all opened windows.
function closeAllWindows() {
    while (isAnyWindowOpened()) {
        closeConfigureWidgetDiv();
    }
}

//Closes edit widget div and updates Edit Site Page about new widget name(info)
function closeConfigureWidgetDivWithUpdate(newWidgetInfo) {
    if (!isAnyWindowOpened()) return;

    updateWidgetInfo(newWidgetInfo);

    closeConfigureWidgetDiv();
}

function closeTinyMCE() {
    if (getTextEditorIdForActiveWindow()) {
        try {
            if (tinyMCE && tinyMCE.activeEditor) {
                if (!tinyMCE.activeEditor.destroyed) {
                    closeEditor(tinyMCE.activeEditor.editorId);
                }
            }
        } catch (ex) {
        }
    }
}

// ---------------------------------------------------WINDOW CLOSING&HIDING END-----------------------------------------

// -------------------------------------------------HELPER FUNCTIONS----------------------------------------------------

function setTextEditorId(Id) {
    var window = getActiveWindow();
    if (window) {
        window.textEditorId = Id;
    }
}

function getTextEditorIdForActiveWindow() {
    var window = getActiveWindow();
    if (window) {
        return window.textEditorId;
    } else {
        return null;
    }
}

function isWindowSettingsChanged() {
    var window = getActiveWindow();
    if (window) {
        return isTextEditorContentChanged(getTextEditorIdForActiveWindow()) || window.changed;
    } else {
        return false;
    }
}

function setWindowSettingsChanged() {
    var window = getActiveWindow();
    if (window) {
        window.changed = true;
    }
}

function setWindowSettingsUnchanged() {
    var window = getActiveWindow();
    if (window) {
        window.changed = false;
        setTextEditorContentChanged(getTextEditorIdForActiveWindow(), false);
    }
}

function getActiveWindow() {
    return getWindowStack()[getWindowStack().length - 1];
}

function getCountOfOpenedWindows() {
    return getWindowStack().length;
}

function isAnyWindowOpened() {
    return getWindowStack().length > 0;
}

function getWindowStack() {
    return getParentWindow().windowStack;
}

function isWindowOpened(window) {
    $(getWindowStack()).each(function () {
        if (this.id == window.id) {
            return true;
        }
    });

    return false;
}

//Contains all opened windows.
if (!getParentWindow().windowStack) {
    getParentWindow().windowStack = new Array();
}
// ---------------------------------------------------HELPER FUNCTIONS END----------------------------------------------
