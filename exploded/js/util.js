var ESCAPE_CODE = 27;

function acceptOnlyDigits(input) {
    var exp = /[^\d]/g;
    input.value = input.value.replace(exp, '');
}

// ---------------------------------------------------------------------------------------------------------------------

function isInteger(value) {
    return !/\D/.test(value);
}

// ---------------------------------------------------------------------------------------------------------------------
function isSignedInteger(value){
    return /^-?\d+$/.test(value);
}
// ---------------------------------------------------------------------------------------------------------------------
function isNegativeInteger(value){
    return /^-/.test(value);
}
// ---------------------------------------------------------------------------------------------------------------------

function isDouble(value) {
    return /(?:(?:^\d+(?:\.\d+)?$)|(?:^\.\d+$))/.test(value);
}

// ---------------------------------------------------------------------------------------------------------------------

function isRomanCharacter(value) {
    return /[a-z A-Z]/.test(value);
}

// ---------------------------------------------------------------------------------------------------------------------

function disableControls(controls, settings) {
    $(controls).each(function() {
        disableControl(this, settings);
    });
}

// ---------------------------------------------------------------------------------------------------------------------

function disableControl(control, settings) {
    if (typeof(settings) == "boolean") {
        settings = {disabled:settings};
    }
    var defaultSettings = {disabled:true, saveOldDisabledState : false};
    settings = $.extend(defaultSettings, settings);

    if (!control) return;
    $(control).find("input").each(function() {
        disableControlInternal($(this)[0], settings.disabled, settings.saveOldDisabledState);
    });
    $(control).find("select").each(function() {
        disableControlInternal($(this)[0], settings.disabled, settings.saveOldDisabledState);
    });
    $(control).find("a").each(function() {
        disableControlInternal($(this)[0], settings.disabled, settings.saveOldDisabledState);
    });
    disableControlInternal(control, settings.disabled, settings.saveOldDisabledState);

    function disableControlInternal(control, disabled, saveOldDisabledState) {
        if (!control) return;
        if (disabled != false && disabled != "false" || disabled == "true") {
            disabled = true;
        }
        if (!$(control).attr("oldDisabled")) {
            $(control).attr("oldDisabled", (control.disabled && saveOldDisabledState));
        }

        var wasDisabled = control.disabled;
        control.disabled = (disabled || $(control).attr("oldDisabled") == "true");
        if (control.disabled) {
            var href = $(control).attr("href");
            if (href) {
                if (href != "javascript:void(0)") {
                    $(control).attr("previousHref", href);
                    $(control).attr("previousTarget", $(control).attr("target"));
                    $(control).attr("target", "");
                }
                $(control).attr("href", "javascript:void(0)");
                if (control.style) {
                    //This prevents script to save #999 (disabled) color as old control color
                    //(this could happen if you try to disable control twice).
                    if (!wasDisabled) {
                        $(control).attr("previousColor", control.style.color);
                    }

                    control.style.color = "#999999";
                }
            }
            if (control.onclick != "javascript:void(0)") {
                control.previousonclick = control.onclick;
                control.onclick = "javascript:void(0)";
            }
            if (control.style) {
                $(control).attr("previousCursor", control.style.cursor);
                control.style.cursor = "default";
            }
        } else {
            var previousHref = $(control).attr("previousHref");
            var previousColor = $(control).attr("previousColor");
            if (previousHref) {
                $(control).attr("href", previousHref);
                if (control.style) {
                    control.style.color = previousColor;
                }
            }
            $(control).attr("target", $(control).attr("previousTarget"));
            if (control.previousonclick) {
                control.onclick = control.previousonclick;
            }
            if (control.style) {
                control.style.cursor = $(control).attr("previousCursor");
            }
        }
    }
}

function disableColorPicker(colorPickerDivId) {
    disableControl($("#" + colorPickerDivId + "  > input")[0]);
    $("#" + colorPickerDivId + "  > span")[0].onclick = function () {
    };
    $("#" + colorPickerDivId + "  > span")[0].style.cursor = "default";
}

// ---------------------------------------------------------------------------------------------------------------------

//Return's current date in following format: MM/dd/yy @ hh:mm:ss AM/PM
function getCurrentDate() {
    var currentDate = "";
    var pmOrAm = "";
    var currentTime = new Date();
    var month = currentTime.getMonth() + 1;

    if (month < 10) {
        month = "0" + month;
    }

    var day = currentTime.getDate();

    if (day < 10) {
        day = "0" + day;
    }

    var year = currentTime.getFullYear();

    if (year > 2000) {
        year -= 2000;
    }

    if (year < 10) {
        year = "0" + year;
    }

    currentDate = day + "/" + month + "/" + year + " @ ";

    var hours = currentTime.getHours();

    if (hours > 11) {
        pmOrAm = "PM";
    } else {
        pmOrAm = "AM";
    }

    if (hours < 10) {
        hours = "0" + hours;
    } else if (hours > 12) {
        hours -= 12;
    }

    var minutes = currentTime.getMinutes();
    var seconds = currentTime.getSeconds();
    if (minutes < 10) {
        minutes = "0" + minutes;
    }

    if (seconds < 10) {
        seconds = "0" + seconds;
    }

    currentDate += hours + ":" + minutes + ":" + seconds + " " + pmOrAm;

    return currentDate;
}

// ---------------------------------------------------------------------------------------------------------------------

function makeAllPagesLiveVisual() {
    $(".simpleTree").find("li[page='page'] > div").each(function () {
        makePageLiveVisual($("#pageDiv" + $(this).attr('pageId')).parent("li")[0]);
    });
}

function makePageLiveVisual(selectedPage) {
    // Updating page in tree
    $(selectedPage).find("div:first").find("span.pageName:first").removeClass("draftPage");
    $(selectedPage).find("div:first").find("span.pageName:first").addClass("livePage");
    // Updating page in select
    $(window.parent.getPageSelect().newSelect).find("span.pageName:first").removeClass("draftPage");
    $(window.parent.getPageSelect().newSelect).find("span.pageName:first").addClass("livePage");

    $(window.parent.getPageSelect().containerDiv).find("#pageDiv" + selectedPage.pageId).find("span.pageName:first").removeClass("draftPage");
    $(window.parent.getPageSelect().containerDiv).find("#pageDiv" + selectedPage.pageId).find("span.pageName:first").addClass("livePage");

    createOrUpdatePagePostTimestamp(selectedPage);
}

// ---------------------------------------------------------------------------------------------------------------------

function createOrUpdatePagePostTimestamp(selectedPage) {
    //Updating post stamp in tree.
    createOrUpdatePagePostTimestampInternal($(selectedPage).find("div:first").find("span.postedText:first")[0],
            $(selectedPage).find("div:first")[0]);
    //Updating post stamp in select.
    createOrUpdatePagePostTimestampInternal($(getPageSelect().newSelect).find("span.postedText:first")[0],
            $(getPageSelect().newSelect).find("div:first")[0]);
    //Updating post stamp in select container.
    createOrUpdatePagePostTimestampInternal($(window.parent.getPageSelect().containerDiv).
            find("#pageDiv" + selectedPage.pageId).find("span.postedText:first")[0],
            $(window.parent.getPageSelect().containerDiv).
                    find("#pageDiv" + selectedPage.pageId).find("div:first")[0]);

    function createOrUpdatePagePostTimestampInternal(element, appendPlace) {
        if (element) {
            element.innerHTML = " Posted: " + getCurrentDate();
        } else {
            var postedText = document.createElement("span");
            postedText.className = "postedText";
            postedText.innerHTML = " Posted: " + getCurrentDate();
            appendPlace.appendChild(postedText);
        }
    }
}


// ---------------------------------------------------------------------------------------------------------------------

function makePageDraftVisual(selectedPage) {
    // Updating page in tree
    $(selectedPage).find("div:first").find("span.pageName:first").addClass("draftPage");
    $(selectedPage).find("div:first").find("span.pageName:first").removeClass("livePage");

    // Updating page in select
    $(window.parent.getPageSelect().newSelect).find("span.pageName:first").addClass("draftPage");
    $(window.parent.getPageSelect().newSelect).find("span.pageName:first").removeClass("livePage");

    $(window.parent.getPageSelect().containerDiv).find("#pageDiv" + selectedPage.pageId).find("span.pageName:first").addClass("draftPage");
    $(window.parent.getPageSelect().containerDiv).find("#pageDiv" + selectedPage.pageId).find("span.pageName:first").removeClass("livePage");
}

// ---------------------------------------------------------------------------------------------------------------------

function setDefaultItemName(field, itemName) {
    field.value = itemName;

    field.onclick = function () {
        defaultItemNameClick(field);
    };
}

// ---------------------------------------------------------------------------------------------------------------------

function defaultItemNameClick(field) {
    field.value = "";
    field.onclick = "";
}

// ---------------------------------------------------------------------------------------------------------------------

function limitName(name, length) {
    var thisLength = length ? length : 60;
    return (name && name.trim().length > thisLength) ? name.trim().substring(0, thisLength - 3) + "..." : name;
}

// ---------------------------------------------------------------------------------------------------------------------

function updateDefaultName(selectedSiteId, defaultNameInput, type) {
    //Updating only if nobody enetered nothing in input
    if (defaultNameInput.onclick) {
        new ServiceCall().executeViaDwr("DefaultNameGetterService", "getNextDefaultName", type, selectedSiteId, function (response) {
            setDefaultItemName(defaultNameInput, response);
        });
    }
}

// ---------------------------------------------------------------------------------------------------------------------

//Converts hex number to decimal number
function from16to10(hexNumber) {
    var result = 0;
    for (var i = 0; ; i++) {
        var hexDigit = hexNumber.substring(i, i + 1);

        if (hexDigit == "") {
            break;
        }

        var decimalDigit;
        if (hexDigit == "F" || hexDigit == "f") {
            decimalDigit = 15;
        } else if (hexDigit == "E" || hexDigit == "e") {
            decimalDigit = 14;
        } else if (hexDigit == "D" || hexDigit == "d") {
            decimalDigit = 13;
        } else if (hexDigit == "C" || hexDigit == "c") {
            decimalDigit = 12;
        } else if (hexDigit == "B" || hexDigit == "b") {
            decimalDigit = 11;
        } else if (hexDigit == "A" || hexDigit == "a") {
            decimalDigit = 10;
        } else {
            decimalDigit = parseInt(hexDigit);
        }

        var partResult = 1;
        for (var j = (hexNumber.length - (i + 1)); j > 0; j--) {
            partResult = partResult * 16;
        }
        result = result + decimalDigit * partResult;
    }
    return result;
}

// ---------------------------------------------------------------------------------------------------------------------

//Making element text unselectable
function disableSelection(element) {
    element.onselectstart = function() {
        return false;
    };
    element.unselectable = "on";
    element.style.MozUserSelect = "none";
}

// ---------------------------------------------------------------------------------------------------------------------
/*
 function findPosReal
 Pay attention: this method calculates real position for an element inside a page.
 In example: <div style='position:relative'><span></span></div> - span would have 0px top position no matter where his parent div is.
 So element positioned in such way should appended to parent node of element for witch you'r calculating position.
 function findPosAbs
 Find's absolute position within a page.
 */

function findPosReal(obj) {
    return findPos(obj, true);
}

function findPosAbs(obj) {
    return findPos(obj, false);
}

function findPos(obj, real) {
    var curleft = 0;
    var curtop = 0;
    var initObject = obj;
    if (obj.offsetParent) {
        while (obj && obj.offsetParent) {
            curleft += obj.offsetLeft - obj.scrollLeft;
            curtop += obj.offsetTop - obj.scrollTop;

            var position = $(obj).css("position");
            if ((position == 'absolute') || (position == 'relative')) {
                if (real) {
                    return {left:initObject.parentNode.offsetLeft,top:initObject.parentNode.offsetTop};
                }
            }

            while (obj.parentNode != obj.offsetParent) {
                obj = obj.parentNode;
                curleft -= obj.scrollLeft ? obj.scrollLeft : 0;
                curtop -= obj.scrollTop ? obj.scrollTop : 0;
            }
            obj = obj.offsetParent;
        }
    } else {
        if (obj.x)
            curleft += obj.x;
        if (obj.y)
            curtop += obj.y;
    }
    return {left:curleft,top:curtop};
}

// ---------------------------------------------------------------------------------------------------------------------

function setOpacity(element, opacity) {
    if (isIE()) {
        element.style.filter = "alpha(opacity=" + opacity * 100 + ")";
    } else {
        element.style.opacity = opacity;
    }
}
// ---------------------------------------------------------------------------------------------------------------------

function getKeyCode(event) {
    if (window.event) {
        return window.event.keyCode;
    } else if (event) {
        return event.which;
    } else {
        return null;
    }
}
// ---------------------------------------------------------------------------------------------------------------------

function isKeyCodeServiceDigit(key) {
    return (!key) || (key == null) || (key == 0) || (key == 8) ||
            (key == 9) || (key == 13) || (key == ESCAPE_CODE);
}
// ---------------------------------------------------------------------------------------------------------------------

function numbersAndOneDotOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return ((myfield.value.indexOf(".") == -1 && String.fromCharCode(key) == ".") || numbersOnly(myfield, event));
}

// ---------------------------------------------------------------------------------------------------------------------

function negativeNumbersAlso(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return ((myfield.value.indexOf("-") == -1 && String.fromCharCode(key) == "-") || numbersOnly(myfield, event));
}

// ---------------------------------------------------------------------------------------------------------------------


function numbersOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return (("0123456789").indexOf(String.fromCharCode(key)) > -1);
}

// ---------------------------------------------------------------------------------------------------------------------

function limitMaxInputCharacters(maxCharacters, myfield, event, nextFieldFocus) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    if ($(myfield).val().length < maxCharacters) {
        return true;
    } else if (nextFieldFocus) {
        nextFieldFocus.focus();
        if ($(nextFieldFocus).val().length == 0) {
            $(nextFieldFocus).val(String.fromCharCode(key));
        }
        return false;
    } else {
        return false;
    }

}
// ---------------------------------------------------------------------------------------------------------------------

function numbersRomanCharactersOrSpaceOnly(myfield, event) {
    return (romanCharactersOrSpaceOnly(myfield, event) || numbersOnly(myfield, event));
}
// ---------------------------------------------------------------------------------------------------------------------

function romanCharactersOrSpaceOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return (isRomanCharacter(String.fromCharCode(key)) || String.fromCharCode(key) == " ");//(("0123456789").indexOf(String.fromCharCode(key)) > -1);
}

// ---------------------------------------------------------------------------------------------------------------------
function numbersAndOneHyphenOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return ((myfield.value.indexOf("-") == -1 && String.fromCharCode(key) == "-") || numbersOnly(myfield, event));
}

// ---------------------------------------------------------------------------------------------------------------------

function numbersRomanCharactersAndOneHyphenOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return ((myfield.value.indexOf("-") == -1 && String.fromCharCode(key) == "-") || numbersOnly(myfield, event)) || isRomanCharacter(String.fromCharCode(key));
}

// ---------------------------------------------------------------------------------------------------------------------

function romanCharactersOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return isRomanCharacter(String.fromCharCode(key));
}

// ---------------------------------------------------------------------------------------------------------------------
function numbersOrSlashOnly(myfield, event) {
    var key = getKeyCode(event);
    if (isKeyCodeServiceDigit(key)) {
        return true;
    }
    return (("0123456789/").indexOf(String.fromCharCode(key)) > -1);
}
// ---------------------------------------------------------------------------------------------------------------------

function centerElement(settings) {
    //Default settings
    var defaultSettings = {
        withinElement: getParentWindow().document.body,
        guaranteeVisibility: false
    };

    //Applying default settings
    settings = $.extend(defaultSettings, settings);

    var elementWidth = $(settings.elementToCenter).outerWidth();
    var elementHeight = $(settings.elementToCenter).outerHeight();

    if (settings.withinElement == getParentWindow().document.body) {
        var windowSize = getWindowSize();
        var left, top;
        if (windowSize.innerWidth > elementWidth) {
            left = (windowSize.scrollLeft + ((windowSize.innerWidth / 2) - (elementWidth / 2))).toFixed(0);
        } else {
            left = windowSize.scrollLeft;
        }
        if (windowSize.innerHeight > elementHeight) {
            top = (windowSize.scrollTop + ((windowSize.innerHeight / 2) - (elementHeight / 2))).toFixed(0);
        } else {
            top = windowSize.scrollTop;
        }

        settings.elementToCenter.style.top = top < 0 ? 0 : top + "px";
        settings.elementToCenter.style.left = left < 0 ? 0 : left + "px";
    } else {
        if (settings.guaranteeVisibility) {
            function reAppendElementToBody() {
                var oldParent = settings.elementToCenter.parentNode;
                oldParent.removeChild(settings.elementToCenter);
                getParentWindow().document.body.appendChild(settings.elementToCenter);
            }

            windowSize = getWindowSize();
            reAppendElementToBody();
            var visibleScreenTop = windowSize.scrollTop;
            var visibleScreenBottom = windowSize.scrollTop + windowSize.innerHeight;

            var visibleScreenLeft = windowSize.scrollLeft;
            var visibleScreenRight = windowSize.scrollLeft + windowSize.innerWidth;

            var visibleElementTop = $(settings.withinElement).offset().top < visibleScreenTop ?
                    visibleScreenTop : $(settings.withinElement).offset().top;
            var visibleElementBottom = (settings.withinElement.offsetHeight + visibleElementTop) < visibleScreenBottom ?
                    (settings.withinElement.offsetHeight + visibleElementTop) : visibleScreenBottom;

            var visibleElementLeft = $(settings.withinElement).offset().left < visibleScreenLeft ?
                    visibleScreenLeft : $(settings.withinElement).offset().left;
            var visibleElementRight = settings.withinElement.offsetWidth + visibleElementLeft < visibleScreenRight ?
                    settings.withinElement.offsetWidth + visibleElementLeft : visibleScreenRight;

            settings.elementToCenter.style.top = ((visibleElementBottom - visibleElementTop) / 2).toFixed() - (elementHeight / 2).toFixed() + $(settings.withinElement).offset().top + "px";
            settings.elementToCenter.style.left = ((visibleElementRight - visibleElementLeft) / 2).toFixed() - (elementWidth / 2).toFixed() + $(settings.withinElement).offset().left + "px";
            bringToFront(settings.elementToCenter);
        } else {
            settings.elementToCenter.style.top = (settings.withinElement.offsetHeight / 2) - (elementHeight / 2) + "px";
            settings.elementToCenter.style.left = (settings.withinElement.offsetWidth / 2) - (elementWidth / 2) + "px";
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------

function getWindowSize() {
    var size = new Object();
    var mainWindow = getParentWindow();
    size.innerWidth = (mainWindow.innerWidth ? mainWindow.innerWidth : (mainWindow.document.documentElement.clientWidth ? mainWindow.document.documentElement.clientWidth : mainWindow.document.body.offsetWidth));
    size.innerHeight = (mainWindow.innerHeight ? mainWindow.innerHeight : (mainWindow.document.documentElement.clientHeight ? mainWindow.document.documentElement.clientHeight : mainWindow.document.body.offsetHeight));
    size.scrollLeft = mainWindow.document.body.scrollLeft != 0 ? mainWindow.document.body.scrollLeft : mainWindow.document.documentElement.scrollLeft;
    size.scrollTop = mainWindow.document.body.scrollTop != 0 ? mainWindow.document.body.scrollTop : mainWindow.document.documentElement.scrollTop;
    size.scrollWidth = mainWindow.document.body.scrollWidth != 0 ? mainWindow.document.body.scrollWidth : mainWindow.document.documentElement.scrollWidth;
    size.scrollHeight = mainWindow.document.body.scrollHeight != 0 ? mainWindow.document.body.scrollHeight : mainWindow.document.documentElement.scrollHeight;
    return size;
}

//----------------------------------------------------------------------------------------------------------------------

function getParentWindow() {
    var parentWindow = getParentWindowInternal();
    try {
        // This code prevents errors with crossdomain scripting.
        // If parent window has another domain then JS will throw exception when we try to test parent window location
        var location = parentWindow.location;
        if (location == "randomTestLocation") {
        }
    } catch(e) {
        return window;
    }
    return parentWindow;

    function getParentWindowInternal() {
        var element = window;
        if (element && element.parent) {
            while (element != element.parent) {
                element = element.parent;
            }
        }
        return element;
    }
}

//----------------------------------------------------------------------------------------------------------------------

//Brings element to front by modifing it's zIndex.
function bringToFront(element) {
    var zIndex = 10;
    getElements().each(function() {
        var currentZIndex = parseInt($(this).css('zIndex'));
        if (currentZIndex && currentZIndex > zIndex) {
            zIndex = currentZIndex;
        }
    });
    $(element).css('zIndex', zIndex += 10);

    function getElements() {
        if (isIE()) {
            return getParentWindow().$(":visible");
        } else {
            return getParentWindow().$("[style*='z-index']");
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------

function createLoadingArea(settings) {
    createBackground(settings);
    createLoadingMessage(settings);
}

function removeLoadingArea() {
    removeBackground();
    removeLoadingMessage();
}

//----------------------------------------------------------------------------------------------------------------------

function createBackground(settings) {
    var loadingBackgroundDiv = getParentWindow().document.getElementById("disabledWindowBackground");
    if (loadingBackgroundDiv) {
        return true;
    }

    //Default settings
    var defaultSettings = {
        backgroundColor: "black",
        backgroundOpacity: 0.1,
        element: getParentWindow().document.body
    };

    //Applying default settings
    settings = $.extend(defaultSettings, settings);

    var background = getParentWindow().document.createElement("div");

    setOpacity(background, settings.backgroundOpacity);
    applyBackgroundDimensionsAndPosition(background, settings.element);
    background.id = "disabledWindowBackground";
    background.style.backgroundColor = settings.backgroundColor;
    settings.element.appendChild(background);
    bringToFront(background);
    return false;

    function applyBackgroundDimensionsAndPosition(background, element) {
        if (element == getParentWindow().document.body) {
            if (isIE()) {
                background.style.width = getParentWindow().document.body.scrollWidth + "px";
                background.style.height = getParentWindow().document.body.scrollHeight + "px";
            } else {
                background.style.width = getParentWindow().document.width + "px";
                background.style.height = getParentWindow().document.height + "px";
            }

            background.style.position = isIE6() ? "absolute" : "fixed";
            background.style.top = "0px";
            background.style.left = "0px";
        } else {
            background.style.width = element.offsetWidth + "px";
            background.style.height = element.offsetHeight + "px";
            background.style.position = "absolute";
            if (element.style.position == "absolute") {
                background.style.top = "0px";
                background.style.left = "0px";
            } else {
                background.style.top = $(element).position().top + "px";
                background.style.left = $(element).position().left + "px";
                $(background).css('margin-left', $(element).css('margin-left'));
                $(background).css('margin-right', $(element).css('margin-right'));
                $(background).css('margin-top', $(element).css('margin-top'));
                $(background).css('margin-bottom', $(element).css('margin-bottom'));
            }
            background.innerHTML = "&nbsp;";
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------

function removeBackground() {
    var background = getParentWindow().document.getElementById("disabledWindowBackground");
    if (background) {
        background.parentNode.removeChild(background);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function getIFrameDocument(iframe) {
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        return iframe.Document;
    } else {
        return iframe.contentDocument;
    }
}

//----------------------------------------------------------------------------------------------------------------------

function relaodNoBot(id) {
    var obj = $("#" + id)[0];
    if (obj) {
        var src = obj.src;
        var pos = src.indexOf('&noCache');
        if (pos >= 0) {
            src = src.substr(0, pos);
        }
        var date = new Date();
        obj.src = src + '&noCache=' + date.getTime();
    }
}

//----------------------------------------------------------------------------------------------------------------------

function disableElementsContent(element, settings) {
    //Default settings
    var defaultSettings = {
        backgroundColor: "white",
        backgroundOpacity: 0.1,
        text: "Loading...",
        color: "black",
        fontWeight: "normal",
        borderStyle: "none"
    };
    //Applying default settings
    settings = $.extend(defaultSettings, settings);
    element.elementDisabled = true;
    createBackground({backgroundColor:settings.backgroundColor, backgroundOpacity: settings.backgroundOpacity, element: element});
    createLoadingMessage({text:settings.text, color:settings.color, fontWeight:settings.fontWeight,
        borderStyle: settings.borderStyle, element: element,customLoaderImage: settings.customLoaderImage,
        backgroundColor: settings.loadingMessageBackgroundColor ? settings.loadingMessageBackgroundColor : settings.backgroundColor});
}

function enableElementsContent(element) {
    removeLoadingMessage();
    removeBackground();
    element.elementDisabled = false;
}

function changeLoadingMessage(newText) {
    $("#loadingMessageDivText").html(newText);
}

function createLoadingMessage(settings) {
    var loadingMessage = getParentWindow().document.getElementById("loadingMessageDiv");
    if (loadingMessage) {
        return loadingMessage;
    }

    //Default settings
    var defaultSettings = {
        text: "...",
        color: "black",
        fontWeight: "bold",
        borderStyle: "solid",
        guaranteeVisibility: false,
        element: getParentWindow().document.body,
        backgroundColor : "white"
    };

    //Applying default settings
    settings = $.extend(defaultSettings, settings);

    loadingMessage = getParentWindow().document.createElement("div");
    setOpacity(loadingMessage, 1);
    loadingMessage.id = "loadingMessageDiv";
    loadingMessage.align = "center";
    loadingMessage.style.position = "absolute";
    loadingMessage.style.backgroundColor = settings.backgroundColor;
    loadingMessage.style.borderStyle = settings.borderStyle;
    loadingMessage.style.borderWidth = "2px";
    loadingMessage.style.padding = "10px";

    var image = getParentWindow().document.createElement("img");
    image.src = settings.customLoaderImage ? settings.customLoaderImage : "../images/ajax-loader.gif";
    image.style.width = "32px";
    image.style.height = "32px";

    var textContainer = getParentWindow().document.createElement("span");
    textContainer.style.fontWeight = settings.fontWeight;

    var text = getParentWindow().document.createElement("div");
    text.style.color = settings.color;
    text.id = "loadingMessageDivText";
    text.innerHTML = settings.text;

    textContainer.appendChild(text);
    loadingMessage.appendChild(image);
    loadingMessage.appendChild(textContainer);
    settings.element.appendChild(loadingMessage);
    centerElement({elementToCenter: loadingMessage, withinElement: settings.element, guaranteeVisibility: settings.guaranteeVisibility});
    bringToFront(loadingMessage);
    return loadingMessage;
}

//----------------------------------------------------------------------------------------------------------------------

function removeLoadingMessage() {
    var loadingMessageDiv = getParentWindow().document.getElementById("loadingMessageDiv");
    if (loadingMessageDiv) {
        loadingMessageDiv.parentNode.removeChild(loadingMessageDiv);
    }
}

//----------------------------------------------------------------------------------------------------------------------

function setStyleInnerHtml(styleElement, innerHTML) {
    if (styleElement.styleSheet) {
        styleElement.styleSheet.cssText = innerHTML;
    } else {
        try {
            styleElement.innerHTML = innerHTML;
        } catch(exception) {
            styleElement.innerText = innerHTML;
        }
    }
}

//----------------------------------------------------------------------------------------------------------------------

function createPX(nonPXValue, oldMeasurementUnit) {
    if (!nonPXValue || !oldMeasurementUnit) {
        return 0;
    } else if (oldMeasurementUnit.toLowerCase() == "px") {
        return parseInt(nonPXValue);
    } else if (oldMeasurementUnit.toLowerCase() == "em") {
        return (parseInt(nonPXValue) * 16);
    } else if (oldMeasurementUnit.toLowerCase() == "pt") {
        return (parseInt(nonPXValue) * 1.4);
    } else if (oldMeasurementUnit.toLowerCase() == "cm") {
        return (parseInt(nonPXValue) * 37.9);
    } else if (oldMeasurementUnit.toLowerCase() == "mm") {
        return (parseInt(nonPXValue) * 3.8);
    } else if (oldMeasurementUnit.toLowerCase() == "in") {
        return (parseInt(nonPXValue) * 96.3);
    } else if (oldMeasurementUnit.toLowerCase() == "pc") {
        return (parseInt(nonPXValue) * 16);
    } else if (oldMeasurementUnit.toLowerCase() == "ex") {
        return (parseInt(nonPXValue) * 7.6);
    }
    return 0;
    /*1em = 16px
     1pt = 1.3333333px
     1cm = 37.9381093px
     1mm = 3.7938109px
     1in = 96.3627976px
     1pc = 15,9999996px
     1ex = 7,58762186px????*/
}

function numberValue(value) {
    return (!value || value == '' || !isInteger(value)) ? 0 : parseInt(value);
}

// ---------------------------------------------------------------------------------------------------------------------
function signedNumberValue(value) {
    return (!value || value == '' || !isSignedInteger(value)) ? 0 : parseInt(value);
}
// ---------------------------------------------------------------------------------------------------------------------

function isIE6() {
    return navigator.appName.indexOf("Internet Explorer") != -1 && navigator.appVersion.indexOf("MSIE 6") != -1;
}

function isIE7() {
    return navigator.appName.indexOf("Internet Explorer") != -1 && navigator.appVersion.indexOf("MSIE 7") != -1;
}

function isIE8() {
    return navigator.appName.indexOf("Internet Explorer") != -1 && navigator.appVersion.indexOf("MSIE 8") != -1;
}

function isIE() {
    return navigator.appName.indexOf("Internet Explorer") != -1;
}

function isFF() {
    var browser = css_browser_selector(navigator.userAgent);
    return browser.indexOf("ff3") != -1 || browser.indexOf("ff3_5") != -1;
}

function isChrome() {
    return css_browser_selector(navigator.userAgent).indexOf("chrome") != -1;
}

function isSafari() {
    return css_browser_selector(navigator.userAgent).indexOf("safari") != -1;
}

// ---------------------------------------------------------------------------------------------------------------------

function ignoreHtml(s) {
    return s.replace("<", "&lt;").replace(">", "&gt;");
}

// ---------------------------------------------------------------------------------------------------------------------

function removeAllHtmlTags(s) {
    return s.replace(/<(.|)*?>/g, "");
}
// ---------------------------------------------------------------------------------------------------------------------

function insertElementIntoArray(array, element, index) {
    if (array && element && (index || index == 0)) {
        if (array.length <= index || index < 0) {
            array.push(element);
        } else {
            array.splice(index, 0, element);
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function addSlidingTimeoutEvent(element, delay) {
    if (element) {
        clearTimeout(element.timeout);
        $(element).slideDown();
        element.timeout = setTimeout(function() {
            $(element).slideUp();
        }, delay);
    }
}

function addFadingTimeoutEvent(element, delay) {
    if (element) {
        clearTimeout(element.timeout);
        $(element).animate({
            'opacity' : '1'
        });
        element.timeout = setTimeout(function() {
            $(element).animate({
                'opacity' : '0'
            });
        }, delay);
    }
}

function addVisibilityTimeoutEvent(element, delay) {
    if (element) {
        clearTimeout(element.timeout);
        element.style.visibility = "visible";
        element.timeout = setTimeout(function() {
            element.style.visibility = "hidden";
        }, delay);
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function getRequestParameter(parameterName) {
    return getParameter(window.location.href, parameterName, false);
}

function getHashParameter(parameterName) {
    return getParameter(window.location.hash, parameterName, true);
}

function getParameter(queryString, parameterName, forHash) {
    // Add "=" to the parameter name (i.e. parameterName=value)
    parameterName = parameterName + "=";
    if (queryString.length > 0) {
        // Find the beginning of the string
        var begin = queryString.indexOf(parameterName);
        // If the parameter name is not found, skip it, otherwise return the value
        if (begin != -1) {
            // Add the length (integer) to the beginning
            begin += parameterName.length;
            // Multiple parameters are separated by the "&" sign
            var end = queryString.indexOf(forHash ? ";" : "&", begin);
            if (end == -1) {
                end = queryString.length;
            }
            // Return the string
            return unescape(queryString.substring(begin, end));
        }
        // Return "null" if no parameter has been found
        return undefined;
    }

    return undefined;
}

// ---------------------------------------------------------------------------------------------------------------------

function parseBoolean(s) {
    if (s == "true") {
        return true;
    } else if (s == "false") {
        return false;
    } else {
        return null;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function trimTextArea(textArea) {
    if (!textArea.trimmed && $(textArea).html()) {
        $(textArea).html($(textArea).html().trim());
        textArea.trimmed = true;
    }
}

// ---------------------------------------------------------------------------------------------------------------------

String.prototype.startsWith = function(str) {
    return (this.search("^" + str) != -1);
};

String.prototype.endsWith = function(str) {
    return (this.search(str + "$") != -1);
};

String.prototype.trim = function() {
    return (this.replace(/^\s+/, "").replace(/\s+$/, ""));
};

String.prototype.contains = function(regex) {
    return this.search(regex) > 0;
};

String.prototype.replaceAll = function(oldValue, newValue) {
    var newString = this;
    while (newString.match(oldValue)) {
        newString = newString.replace(oldValue, newValue);
    }
    return newString;
};

// ---------------------------------------------------------------------------------------------------------------------

function escPressed(event) {
    event = event || window.event;
    return event.keyCode == ESCAPE_CODE;
}

// ---------------------------------------------------------------------------------------------------------------------

String.prototype.insertJsessionId = function () {
    // If jessionid is already present do nothing.
    if (this.indexOf(";jsessionid=") != -1) return this;
    if (this.indexOf("sid=") != -1) return this;

    return this + ((this.indexOf("?") < 1) ? "?" : "&") + "sid=" + getCookie("JSESSIONID");
};

// ---------------------------------------------------------------------------------------------------------------------

$(document).ready && $(document).ready(function () {
    $("a").each(function () {
        if (!isUnmodifiable(this)) {
            if (this.href && this.href.startsWith("http://")) {
                var linkDomain = getDomainName(this.href);
                var currentDomain = getDomainName(location.href);

                if (linkDomain == "" || currentDomain == "") {
                    alert("Failed to get domain name from link.");
                }

                if (linkDomain != currentDomain) {
                    var innerHTMLbeforeModifingHref = this.innerHTML;
                    this.href = this.href.insertJsessionId();

                    //Inserting innerHTML that was before modifing link's href. This is done to fix an IE8 bug,
                    //it was inserting updated href with jsessionid into innerHTML.
                    this.innerHTML = innerHTMLbeforeModifingHref;
                }
            }
        }
    });
    function isUnmodifiable(link) {
        return $(link).attr("externalUrl") == "true";
    }
});

function getDomainName(href) {
    var httpIndex = href.indexOf("http://");
    var domainName = "";

    if (httpIndex != -1) {
        var firstSlashIndex = href.substring(httpIndex + "http://".length, href.length).indexOf("/");
        domainName = href.substring(httpIndex + "http://".length, "http://".length + firstSlashIndex);
    }

    return domainName;
}

// ---------------------------------------------------------------------------------------------------------------------

function validateDateArray(array) {
    var dateAsString = "";
    for (var i = 0; i < array.length; i++) {
        var element = array[i];

        dateAsString += element + (i != array.length - 1 ? ";" : "");
    }

    return validateDate(dateAsString);
}

// input date format - MM;dd;yyyy;hh;mm or MM;dd;yyyy or hh:mm.
function validateDate(s) {
    var splintedDate = s.split(";");

    if (splintedDate.length == 5) {
        var month = splintedDate[0] == "" ? "01" : splintedDate[0];
        var day = splintedDate[1] == "" ? "01" : splintedDate[1];
        var year = splintedDate[2] == "" ? "1900" : splintedDate[2];
        var hours = splintedDate[3] == "" ? "00" : splintedDate[3];
        var minutes = splintedDate[4] == "" ? "00" : splintedDate[4];
    } else if (splintedDate.length == 3) {
        month = splintedDate[0] == "" ? "01" : splintedDate[0];
        day = splintedDate[1] == "" ? "01" : splintedDate[1];
        year = splintedDate[2] == "" ? "1900" : splintedDate[2];
        hours = "00";
        minutes = "00";
    } else if (splintedDate.length == 2) {
        month = "01";
        day = "01";
        year = "1900";
        hours = splintedDate[0] == "" ? "00" : splintedDate[0];
        minutes = splintedDate[1] == "" ? "00" : splintedDate[1];
    }

    if (month.length != 2 || day.length != 2 || year.length != 4 || hours.length != 2 || minutes.length != 2) {
        return false;
    }

    try {
        if (parseInt(month) > 12) {
            return false;
        }

        if (parseInt(day) > 31) {
            return false;
        }

        if (parseInt(hours) > 24) {
            return false;
        }

        if (parseInt(minutes) > 60) {
            return false;
        }
    } catch(ex) {
        return false;
    }

    return true;
}

// ---------------------------------------------------------------------------------------------------------------------

function parseDate(dateString) {
    var date = null;
    try {
        var dateTime = Date.parse(dateString);
        if (dateTime) {
            date = new Date();
            date.setTime(dateTime);
        }
        if (date == "Invalid Date" || date == "NaN") {
            date = null;
        }
        // This check, that you enter correctly formed date. For example, if you enter 14/40/2010 - parsed date will be not equal to initial string.
        if (date && (date.format("mm/dd/yyyy") != dateString && date.format("mm dd yyyy") != dateString)) {
            date = null;
        }
    } catch(ex) {
        //Ignore.
    }
    return date;
}

// ---------------------------------------------------------------------------------------------------------------------

function getCaretInfo(oTextarea) {
    var docObj = oTextarea.ownerDocument;
    var result = {start:0, end:0, caret:0};

    if (navigator.appVersion.indexOf("MSIE") != -1) {
        if (oTextarea.tagName.toLowerCase() == "textarea") {
            if (oTextarea.value.charCodeAt(oTextarea.value.length - 1) < 14) {
                oTextarea.value = oTextarea.value.replace(/34/g, '') + String.fromCharCode(28);
            }
            var oRng = docObj.selection.createRange();
            var oRng2 = oRng.duplicate();
            oRng2.moveToElementText(oTextarea);
            oRng2.setEndPoint('StartToEnd', oRng);
            result.end = oTextarea.value.length - oRng2.text.length;
            oRng2.setEndPoint('StartToStart', oRng);
            result.start = oTextarea.value.length - oRng2.text.length;
            result.caret = result.end;
            if (oTextarea.value.substr(oTextarea.value.length - 1) == String.fromCharCode(28)) {
                oTextarea.value = oTextarea.value.substr(0, oTextarea.value.length - 1);
            }
        } else {
            var range = docObj.selection.createRange();
            var r2 = range.duplicate();
            result.start = 0 - r2.moveStart('character', -100000);
            result.end = result.start + range.text.length;
            result.caret = result.end;
        }
    } else {
        result.start = oTextarea.selectionStart;
        result.end = oTextarea.selectionEnd;
        result.caret = result.end;
    }
    if (result.start < 0) {
        result = {start:0, end:0, caret:0};
    }
    return result;
}

// ---------------------------------------------------------------------------------------------------------------------
function isZipCodeValid(zipCode, country) {
    if (!zipCode) {
        return false;
    }
    // Checking the zipCode by USA rules.
    if (country == 'US') {
        return /^\d\d\d\d\d(?:-\d\d\d\d)?$/.test(zipCode);//99999 or 99999-9999
    }
    // Checking the zipCode by Canadian rules.
    if (country == 'CA') {
        return /^\D\d\D\-?\d\D\d$/.test(zipCode);//Z5Z-5Z5 orZ5Z5Z5
    }
    // todo. Add zipCode checking for other countries if needed. Tolik.
    return true;
}

// ---------------------------------------------------------------------------------------------------------------------

function listSelectOption(item, value) {
    for (var i = 0; i < $(item).attr("options").length; i++)
        if ($(item).attr("options")[i].value == value) {
            $(item).attr("selectedIndex", i);
            return true;
        }
    return false;
}

// ---------------------------------------------------------------------------------------------------------------------

function getNullOrValue(value) {
    return value == "null" ? null : value;
}

function contains(array, element) {
    var contains = false;
    $(array).each(function () {
        if (this == element) {
            contains = true;
        }
    });

    return contains;
}

if (window.jQuery) {
    $.fn.showInline = function () {
        $(this).css('display', 'inline');
    };

    $.fn.makeVisible = function () {
        $(this).css('visibility', 'visible');
    };

    $.fn.makeInvisible = function () {
        $(this).css('visibility', 'hidden');
    };
}

function viewLivePage(pageId) {
    new ServiceCall().executeViaDwr("PageUrlGetterService", "getWorkUrl", pageId, function (url) {
        if (url) {
            window.open(url);
        } else {
            alert("Work page not found!");
        }
    });
}

function preloadImages(images) {
    $(document).ready(function() {
        try {
            var div = document.createElement("div");
            div.style.position = "absolute";
            div.style.top = div.style.left = 0;
            div.style.visibility = "hidden";
            getParentWindow().$("body")[0].appendChild(div);
            div.innerHTML = "<img src=\"" + images.join("\" /><img src=\"") + "\" />";
        } catch(e) {
            // Error. Do nothing.
        }
    });
}

function convertFormStringMonthToInt(stringMonth) {
    if (stringMonth == "January") {
        return 0;
    } else if (stringMonth == "February") {
        return 1;
    } else if (stringMonth == "March") {
        return 2;
    } else if (stringMonth == "April") {
        return 3;
    } else if (stringMonth == "May") {
        return 4;
    } else if (stringMonth == "June") {
        return 5;
    } else if (stringMonth == "July") {
        return 6;
    } else if (stringMonth == "August") {
        return 7;
    } else if (stringMonth == "September") {
        return 8;
    } else if (stringMonth == "October") {
        return 9;
    } else if (stringMonth == "November") {
        return 10;
    } else if (stringMonth == "December") {
        return 11;
    } else {
        throw "Unknown month.";
    }
}

var tableOperations = {
    moveUpTr : function (upImg) {
        var tr = $(upImg).parents("tr:first");
        var upperTr = $(tr).prev();

        // Add current tr before tr that is upper than current.
        $(upperTr).before(tr);

        this.hideFirstLastUpDownImages(tr);
    } ,

    moveDownTr : function (downImg) {
        var tr = $(downImg).parents("tr:first");
        var lowerTr = $(tr).next()[0];

        // Add current tr after tr that is lower than current.
        $(lowerTr).after(tr);

        this.hideFirstLastUpDownImages(tr);
    },

    hideFirstLastUpDownImages : function (tr) {
        var table = $(tr).parents("table")[0];
        // At first, lets show all manage row position images.
        $(table).find("img[name='upImages']").css('visibility', 'visible');
        $(table).find("img[name='downImages']").css('visibility', 'visible');

        // Now, lets hide 'move up' image for first row
        var firstTr = $(table).find("tr:first");
        $(firstTr).find("img[name='upImages']").css('visibility', 'hidden');

        // And 'move down' image for last row.
        var lastTr = $(table).find("tr:last");
        $(lastTr).find("img[name='downImages']").css('visibility', 'hidden');
    }
};

//Global key processing.
$(document).ready && $(document).ready(function () {
    $("body").each(function () {
        this.onkeydown = window.parent.globalKeyProcessor;
    });

    if (isFF()) {
        $(document).each(function () {
            this.onkeydown = window.parent.globalKeyProcessor;
        });
    }
});

function globalKeyProcessor(e) {
    if (!window.event && !e) {
        return;
    }

    var keyId = (window.event) ? window.event.keyCode : e.keyCode;
    if (window.parent.isAnyWindowOpened()) {
        switch (keyId) {
            //Esc. Close window.
            case 27:{
                var activeWindow = getActiveWindow();
                if (activeWindow.isClosable()) {
                    if (!activeWindow.clickCancel()) {
                        activeWindow.closeConfigureWidgetDiv();
                    }
                }
                break;
            }
        }

        //Emulate submit button.
        if ((window.event) ? event.ctrlKey : e.ctrlKey) {
            if (keyId == 13) {
                getActiveWindow().clickSave();
            }
        }
    } else if (!window.parent.isAnyWindowOpened() && window.parent.document.getElementById("siteEditPage")) {
        // Site edit page.
        switch (keyId) {
            //Delete
            case 46:{
                deleteWidget();
                break;
            }
            //Insert
            case 45:{
                showAddWidget();
                break;
            }
            //Enter
            case 13:{
                widgetDoubleClick();
                break;
            }
        }
    } else if ($("#fullScreenSlideShow")[0]) {
        switch (keyId) {
            // Close fullscreen slideshow.
            case 27:{
                renderSlideShow.exitFullScreenMode();
                break;
            }
        }
    }
}

function cacheHtml(contentDivId, html) {
    /*--------------------------------------------------Caching html--------------------------------------------------*/
    if (!$("#" + contentDivId + "Cached")[0]) {
        $("#" + contentDivId).parent().append("<div style='display:none;' id='" + contentDivId + "Cached'></div>");
    }

    onloadImagesMalformation();
    $("#" + contentDivId + "Cached").html(html);

    malformIdsInternal($("#" + contentDivId + "Cached")[0]);
    /*--------------------------------------------------Caching html--------------------------------------------------*/

    /*------------------------------------------Restoring Content From Cache------------------------------------------*/
    restoreFromCache(contentDivId);
    getActiveWindow().invalidCache = false;
    /*------------------------------------------Restoring Content From Cache------------------------------------------*/

    function onloadImagesMalformation() {
        var onloadSrc = new Array();

        $(html).find("img[onload]").each(function () {
            var src = $(this).attr("src");
            var containsValue = false;
            $(onloadSrc).each(function () {
                if (this == src) {
                    containsValue = true;
                }
            });

            if (!containsValue) {
                onloadSrc.push($(this).attr("src"));
            }
        });

        $(onloadSrc).each(function () {
            var src = this;
            var malformedSrc = src.substring(0, src.length / 2) + IMAGE_SRC_APPENDIX + src.substring(src.length / 2);
            html = html.replaceAll(src, malformedSrc);
        });
    }
}

function setOnApplyEvent(contentDivId) {
    //After user clicks 'Apply' we should set wasSettingsApplied = true so later when user
    //will go on this tab it's contents were reloaded from server ;
    var applyButton = $("#windowApply", $("#" + contentDivId)[0])[0];
    if (applyButton) {
        $(applyButton).click(function () {
            getActiveWindow().invalidCache = true;
            deleteHtmlCache(contentDivId);
        });
    }
}

function restoreFromCache(contentDivId) {
    // Please, restrain order. It takes only 50ms in FF to undomalform and then madlform ids but it fixes some bugs.
    undoMalformIdsInternal($("#" + contentDivId + "Cached")[0]);

    $("#" + contentDivId).html($("#" + contentDivId + "Cached").html());

    fixOnloadImagesUrls();

    malformIdsInternal($("#" + contentDivId + "Cached")[0]);

    setOnApplyEvent(contentDivId);

    addContentChangedTrigger($("#" + contentDivId));

    function fixOnloadImagesUrls() {
        $("#" + contentDivId).find("img[onload]").each(function() {
            this.src = this.src.replace(IMAGE_SRC_APPENDIX, "");
        });
    }
}

function deleteHtmlCache(contentDivId) {
    $("#" + contentDivId + "Cached").remove();
}

function isCached(contentDivId) {
    return $("#" + contentDivId + "Cached")[0] && !getActiveWindow().invalidCache;
}

function addContentChangedTrigger(content) {
    if (!content) {
        return;
    }
    $(content).find("input, select, textarea, .detectControlChange").change(function() {
        if (!(this).hasAttribute("excludeControlChange")) {
            getActiveWindow().changed = true;
        }
    });

    $(content).find("[detectControlClick=true]").click(function () {
        getActiveWindow().changed = true;
    });
}

var IMAGE_SRC_APPENDIX = "BrokenImageUrl";
