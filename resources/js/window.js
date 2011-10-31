/**
 * Attributes:
 * resizable - makes window resizable. True by default.
 * closable - makes window closable by x button. True by default.
 * draggable - makes window not dragable. True by default.
 * modal - makes window modal. True by default.
 * resizeInnerIFrames - automatically resizes inner iframes, if they are present. False by default.
 * enableContentOnSetContent - enables content on setContent. True by default.
 * disableContentOnCreation - disables content on window creation. True by default.
 * useItem - cuts item from specefied element and paste it into window then inserts item back to it's place.
 * detectChanges - if true, adds event onChange for every control and sets changed varible if changes were made. True by default.
 *
 * Events:
 * onLoad - executes after window creation.
 * onBeforeClose - executes before closing window.
 * onAfterClose - executes after closing window.
 * @param settings
 */
function Window(settings) {
    //Default settings
    this.settings = {
        resizable: true,
        closable: true,
        draggable: true,
        modal: true,
        detectChanges: true,
        resizeInnerIFrames: false,
        disableContentOnCreation: true,
        uniqueIdentifiers: false,
        twoColumnLayout: false,
        asDialog: false,
        dialogType: "ALERT",
        dialogHeader: "Dialog",
        enableContentOnSetContent:true
    };

    //Applying default settings
    this.settings = $.extend(this.settings, settings);

    this.changed = false;

    //Assign before close action.
    this.onBeforeClose = this.settings.onBeforeClose;

    //Assign after close action.
    this.onAfterClose = this.settings.onAfterClose;

    //Creating disabling background
    if (this.settings.modal) {
        this.createDisableBackground();
    }

    //Creating empty window div and centering it.
    this.createWindowDiv();

    if (this.settings.onLoad) {
        settings.onLoad(this);
        this.resize();
    }

    if (this.settings.useItem) {
        var itemParent = this.settings.useItem.parentNode;

        //Adding div to window and making it visible
        this.getWindowContentDiv().appendChild(this.settings.useItem);
        this.settings.useItem.style.display = "block";

        // Resizing window content
        this.resize();

        //Adding onBeforeClose event
        this.onBeforeClose = function () {
            //Adding div back to it's place and making it invisible
            this.settings.useItem.style.display = "none";
            itemParent.appendChild(this.settings.useItem);
        };
    }

    this.centerWindow();
}
// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.createDisableBackground = function () {
    var disableBackground = getParentWindow().document.createElement("div");
    disableBackground.style.position = isIE6() ? "absolute" : "fixed";
    disableBackground.style.left = "0";
    disableBackground.style.top = "0";
    disableBackground.style.filter = "alpha(opacity=50)";
    disableBackground.style.opacity = "0.5";
    renewBackgroundSize();
    disableBackground.innerHTML = "&nbsp;";
    disableBackground.style.backgroundColor = "gray";
    getParentWindow().document.body.appendChild(disableBackground);

    bringToFront(disableBackground);
    this.disableBackground = disableBackground;

    //Background size renewal on browser window resize.
    $(window).resize(function() {
        renewBackgroundSize();
    });

    function renewBackgroundSize() {
        if (!isIE6()) {
            $(disableBackground).css({
                width: "100%",
                height: "100%"
            });
        } else {
            var windowSize = getWindowSize();
            $(disableBackground).css({
                width: windowSize.scrollWidth + "px",
                height: (windowSize.scrollHeight > windowSize.innerHeight ? windowSize.scrollHeight : windowSize.innerHeight) + "px"
            });
        }

    }
};

Window.prototype.createWindowDiv = function () {
    //Creating window div itself
    var windowDiv = getParentWindow().document.createElement("div");
    windowDiv.className = this.settings.asDialog ? getDialogClass(this.settings.dialogType) : "window";
    windowDiv.style.position = "absolute";
    windowDiv.style.backgroundColor = "#6997ad";

    windowDiv.style.top = "10px";
    if (this.settings.width) {
        windowDiv.style.width = this.settings.width + "px";
    }
    if (this.settings.height) {
        windowDiv.style.height = this.settings.height + "px";
    }
    windowDiv.style.paddingBottom = this.settings.asDialog ? "3px" : "10px";
    windowDiv.frameBorder = "0";
    if (this.settings.windowDivId) {
        windowDiv.id = this.settings.windowDivId;
    } else {
        windowDiv.id = "window" + Math.floor(100 * Math.random());
    }

    getParentWindow().document.body.insertBefore(windowDiv, getParentWindow().document.body.firstChild);

    //Creating top panel div
    var panelDiv = getParentWindow().document.createElement("div");
    panelDiv.className = "windowPanel";
    this.panelDivHeight = this.settings.asDialog ? 18 : 18;
    panelDiv.style.height = this.panelDivHeight + "px";
    panelDiv.align = "right";
    if (this.settings.asDialog) {
        panelDiv.innerHTML = "<div class='dialogHeaderTextDiv'>" + this.settings.dialogHeader + "</div>";
    }

    if (this.settings.closable) {
        var closeButton = getParentWindow().document.createElement("img");
        closeButton.src = this.settings.asDialog ? "/images/cross-circle.png" : "/images/window/cross1.png";
        closeButton.className = "windowCloseButton";

        closeButton.style.cursor = "pointer";

        closeButton.onclick = function () {
            if (!getActiveWindow().clickCancel()) {
                getParentWindow().closeConfigureWidgetDiv();
            }
        };

        panelDiv.appendChild(closeButton);
    }

    windowDiv.appendChild(panelDiv);

    //Creating div which will have content.
    var windowContentDiv = getParentWindow().document.createElement("div");
    if (this.settings.width) {
        windowContentDiv.style.width = this.settings.width + "px";
    }
    if (this.settings.height) {
        windowContentDiv.style.height = this.settings.height + "px";
    }
    windowContentDiv.id = this.settings.windowContentDivId ? this.settings.windowContentDivId : "content" + Math.floor(100 * Math.random());
    windowDiv.appendChild(windowContentDiv);

    bringToFront(windowDiv);
    this.windowDiv = windowDiv;
    this.windowContentDiv = windowContentDiv;
    this.panelDiv = panelDiv;

    if (this.settings.draggable) {
        panelDiv.style.cursor = "move";
        $(windowDiv).draggable({
            handle:panelDiv
        });
    }

    if (this.settings.resizable && !this.settings.asDialog) {
        $(windowDiv).resizable({
            minWidth: this.settings.width,
            handles:"s, se, e"
        });
    }

    if (this.settings.disableContentOnCreation) {
        var disableContentSettings = new Object();
        disableContentSettings.onWindowLoad = true;
        this.disableContent(disableContentSettings);
    }

    function getDialogClass(dialogType) {
        return dialogType == "ALERT" ? "alertDialog dialog" : "dialog";
    }
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.setContent = function(content) {
    var dialogContent = "<div class='windowOneColumn'>" +
            "<div class='dialogImage'></div>" +
            content +
            "<div align='right' class='bottomCloseButtonDiv'>" +
            "<input type='button' class='but_w62_small' value='Close' onclick='closeConfigureWidgetDiv();'" +
            " onmouseover='this.className=\"but_w62_small_Over\"' onmouseout='this.className=\"but_w62_small\"'>" +
            "</div>" +
            "</div>";

    this.windowContentDiv.innerHTML = this.settings.asDialog ? dialogContent : content;

    this.resize();

    var self = this;
    if (this.settings.detectChanges) {
        addContentChangedTrigger(this.windowContentDiv);
    }

    if (this.settings.enableContentOnSetContent) {
        this.enableContent();
    }

    //Set settings to also resize container on window resize.
    $(this.windowDiv).resizable("option", "alsoResize", $("#twoColumnsWindow_rightColumn", this.windowContentDiv)[0] ?
            $("#twoColumnsWindow_rightColumn", this.windowContentDiv)[0] : $(".windowOneColumn", this.windowContentDiv)[0]);
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.destroy = function() {
    //Keep order. Look at: http://jira.web-deva.com/browse/SW-4620.
    $(this.disableBackground).remove();
    $(this.windowDiv).remove();
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.getWindowContentDiv = function () {
    return this.windowContentDiv;
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.show = function () {
    this.windowDiv.style.display = "block";
    this.disableBackground.style.display = "block";
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.resize = function () {
    this.windowContentDiv.className = "";
    //Resizing inner iframes. Add this function to iframe onload event to resize whole window with iframe during window loading
    if (this.settings.resizeInnerIFrames) {
        $(this.windowDiv).find("iframe").each(function() {
            var textEditorHeight = isIE() && $(getIFrameDocument(this).body).find("#textEditorHeight")[0]
                    ? parseInt($(getIFrameDocument(this).body).find("#textEditorHeight").val()) + 103 : 0;
            this.style.height = getIFrameDocument(this).body.scrollHeight + textEditorHeight + "px";
        });
    }

    var contentDiv = this.windowContentDiv;
    contentDiv.style.height = null;
    contentDiv.style.width = null;
    while (contentDiv.scrollHeight == undefined) {
        contentDiv = contentDiv.nextSibling;
    }
    var contentHeight = contentDiv.scrollHeight;

    this.windowDiv.style.height = contentHeight + this.panelDivHeight + "px";

    this.centerWindow();
    var windowSize = getWindowSize();
    this.disableBackground.style.height = (windowSize.scrollHeight > windowSize.innerHeight ? windowSize.scrollHeight : windowSize.innerHeight) + "px";

    // Adding restriction to resize window by height.
    $(this.windowDiv).resizable("option", "minHeight", contentHeight + this.panelDivHeight);
    this.windowContentDiv.className = "windowContent";
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.disableContentBeforeSaveSettings = function (text) {
    this.disableContent({backgroundColor: "#6997ad", loadingMessageBackgroundColor:"white", backgroundOpacity: 0.3,
        text: text ? text : "Saving settings...", fontWeight: "bold", color: "green", borderStyle:"solid",
        element: getActiveWindow().windowDiv});
};

Window.prototype.disableContent = function (settings) {
    settings = $.extend({element: this.windowContentDiv}, settings);
    settings = $.extend({backgroundColor: "#6997ad"}, settings);
    if (settings.onWindowLoad) {
        settings = $.extend({customLoaderImage: "../images/window-loader.gif"}, settings);
    }
    if (!this.isDisabled()) {
        disableElementsContent(settings.element, settings);
        this.setDisabled(true);
    }
};

Window.prototype.enableContentAfterException = function (settings) {
    this.enableContentInternal(settings);
    this.onEnableContent = function() {
    };
};

Window.prototype.enableContent = function (settings) {
    this.enableContentInternal(settings);
    this.onEnableContent();
};

Window.prototype.enableContentInternal = function (settings) {
    settings = $.extend({element: this.windowContentDiv}, settings);
    if (this.isDisabled()) {
        enableElementsContent(settings.element);
        this.setDisabled(false);
    }
};

Window.prototype.onEnableContent = function () {
};

Window.prototype.isDisabled = function () {
    return this.windowContentDiv.disabledWindow;
};

Window.prototype.setDisabled = function (disabled) {
    this.windowContentDiv.disabledWindow = disabled;
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.centerWindow = function () {
    centerElement({elementToCenter: this.windowDiv});
};

// ---------------------------------------------------------------------------------------------------------------------

//Never use these functions directly, please use closeConfigureWidgetDiv instead

Window.prototype.close = function () {
    if (this.onBeforeClose) {
        this.onBeforeClose();
    }

    this.destroy();
    //On after close event is executed in configureWindow.js. To ensure execution after removing window from stack.
};

// ---------------------------------------------------------------------------------------------------------------------

Window.prototype.clickCancel = function () {
    var cancelButton = $("#windowCancel:visible", this.getWindowContentDiv());
    if (cancelButton[0]) {
        // We need this, because onchange fuction for text input fields works only when focus is removed
        // from it and closeConfigureWidgetWithConfirm() will not work. Tolik
        cancelButton[0].focus();
        $(cancelButton).click();
        return true;
    } else {
        return false;
    }
};

Window.prototype.clickSave = function () {
    var saveButton = $("#windowSave:visible", this.getWindowContentDiv());
    if (saveButton[0]) {
        $(saveButton).click();
        return true;
    } else {
        return false;
    }
};

Window.prototype.clickApply = function () {
    var applyButton = $("#windowApply:visible", this.getWindowContentDiv());
    if (applyButton[0]) {
        $(applyButton).click();
        return true;
    } else {
        return false;
    }
};

Window.prototype.isClosable = function() {
    return this.settings.closable;
};