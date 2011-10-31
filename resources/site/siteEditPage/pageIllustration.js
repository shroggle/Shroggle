/*
 @author Dmitry Solomadin
 */
function getElementByIdForSiteFrame(elementId) {
    var iFrame = window.parent.document.getElementById("site");
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        return iFrame.Document.getElementById(elementId);
    } else {
        return iFrame.contentDocument.getElementById(elementId);
    }
}

function getIFrameScrollPosition() {
    var iFrame = window.parent.document.getElementById("site");
    var body;
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        body = iFrame.Document.body;
    } else {
        body = iFrame.contentDocument.body;
    }

    return {x:body.scrollLeft, y:body.scrollTop};
}

function removePageWidgetInfo(widgetId) {
    var info = window.parent.document.getElementById("widget" + widgetId + "InfoSpan");
    var infoHolder = info.parentNode;

    infoHolder.removeChild(info);

    var infoHolderChilds = $(infoHolder).children();
    for (var i = 0; ; i++) {
        info = infoHolderChilds[i];

        if (info == undefined) {
            break;
        }

        if (i % 2 != 0) {
            info.className = "secondSelectedWidgetInfo";
            info.infoSpanClass = "secondSelectedWidgetInfo";
        } else {
            info.className = "firstSelectedWidgetInfo";
            info.infoSpanClass = "firstSelectedWidgetInfo";
        }
    }

    if (infoHolderChilds.length == 0) {
        infoHolder.innerHTML = window.parent.document.getElementById("noFuncElemetsOnPage").value;
        infoHolder.empty = true;
    }
}

function createPageWidgetInfo(widgetInfo, infoBlock, odd) {
    if (infoBlock.empty) {
        infoBlock.innerHTML = "";
        infoBlock.empty = false;
    }

    var child = window.parent.document.createElement("span");
    child.innerHTML = widgetInfo.widgetInfo;
    child.widgetId = widgetInfo.widgetId;
    child.id = "widget" + widgetInfo.widgetId + "InfoSpan";

    if (odd) {
        child.className = "secondSelectedWidgetInfo";
        child.infoSpanClass = "secondSelectedWidgetInfo";
    } else {
        child.className = "firstSelectedWidgetInfo";
        child.infoSpanClass = "firstSelectedWidgetInfo";
    }

    child.onclick = function () {
        selectWidgetInInfoBlock(this);
    };
    child.ondblclick = function () {
        widgetDoubleClick();
    };

    $(infoBlock).append(child);
}

function showManageLinksForWidget() {
    var moduleContents = $("#contentsWidget", window.parent.document);
    var moduleNetworkSettings = $("#editNetworkSettings", window.parent.document);
    var moduleSettings = $("#configureWidget", window.parent.document);
    var fontColorWidget = $("#fontColorWidget", window.parent.document);
    var backgroundsWidget = $("#widgetBackground", window.parent.document);
    var borderWidget = $("#widgetBorder", window.parent.document);
    var widgetSize = $("#widgetSizeElement", window.parent.document);
    var widgetPerm = $("#widgetAccessible", window.parent.document);
    var widgetBlueprintPerm = $("#widgetBlueprintPerm", window.parent.document);

    var mediaBlockBorder = $("#areaBorder", window.parent.document);
    var mediaBlockBackground = $("#areaBackground", window.parent.document);
    var mediaBlockFontsAndColors = $("#fontColorArea", window.parent.document);


    if (!window.parent.selectedMediaBlock){
        $("#mediaBlockDisplaySettingsText", window.parent.document).hide();
        mediaBlockBorder.parent().hide();
        mediaBlockBackground.parent().hide();
        mediaBlockFontsAndColors.parent().hide();
    } else {
        $("#mediaBlockDisplaySettingsText", window.parent.document).show();
        mediaBlockBorder.parent().show();
        mediaBlockBackground.parent().show();
        mediaBlockFontsAndColors.parent().show();
    }

    if (!window.parent.selectedWidget) {
        $("#widgetDisplaySettingsText", window.parent.document).hide();
        fontColorWidget.parent().hide();
        backgroundsWidget.parent().hide();
        borderWidget.parent().hide();
        widgetSize.parent().hide();
        widgetPerm.parent().hide();
        moduleContents.parent().hide();
        moduleNetworkSettings.parent().hide();
        moduleSettings.parent().hide();
        widgetBlueprintPerm.parent().hide();
        return;
    } else {
        $("#widgetDisplaySettingsText", window.parent.document).show();
    }

    if (window.parent.selectedWidget.itemType != "FORUM" && window.parent.selectedWidget.itemType != "BLOG") {
        moduleContents.parent().hide();
    } else {
        moduleContents.parent().show();
    }

    if (window.parent.selectedWidget.itemType == "CHILD_SITE_REGISTRATION") {
        moduleNetworkSettings.parent().show();
    } else {
        moduleNetworkSettings.parent().hide();
    }

    if (window.parent.selectedWidget.itemType == "LOGIN") {
        moduleSettings.parent().hide();
    } else {
        moduleSettings.parent().show();
    }

    if (window.parent.selectedWidget.itemType == "CONTACT_US" || window.parent.selectedWidget.itemType == "CHILD_SITE_REGISTRATION"
            || window.parent.selectedWidget.itemType == "REGISTRATION" || window.parent.selectedWidget.itemType == "CUSTOM_FORM"
            || window.parent.selectedWidget.itemType == "GALLERY" || window.parent.selectedWidget.itemType == "ADVANCED_SEARCH") {
        $("#addEditFormRecords", window.parent.document).parent().show();
        $($.find("[exportData=true]", window.parent.document)).show();
    } else {
        $("#addEditFormRecords", window.parent.document).parent().hide();
        $($.find("[exportData=true]", window.parent.document)).parent().hide();
    }
    if (window.parent.selectedWidget.eCommerceStoreWidget) {
        $("#completeOrders", window.parent.document).parent().show();
        $("#editStoreSettings", window.parent.document).parent().show();
    } else {
        $("#completeOrders", window.parent.document).parent().hide();
        $("#editStoreSettings", window.parent.document).parent().hide();
    }

    if (window.parent.selectedWidget.itemType == "TAX_RATES") {
        fontColorWidget.parent().hide();
    } else {
        fontColorWidget.parent().show();
    }

    if (window.parent.selectedWidget.itemType == "TAX_RATES") {
        backgroundsWidget.parent().hide();
    } else {
        backgroundsWidget.parent().show();
    }

    if (window.parent.selectedWidget.itemType == "TAX_RATES") {
        borderWidget.parent().hide();
    } else {
        borderWidget.parent().show();
    }

    if (window.parent.selectedWidget.itemType == "TAX_RATES") {
        widgetSize.parent().hide();
    } else {
        widgetSize.parent().show();
    }

    if (window.parent.selectedWidget.itemType == "TAX_RATES") {
        widgetPerm.parent().hide();
    } else {
        widgetPerm.parent().show();
    }

    if (window.parent.siteIsBlueprint) {
        widgetBlueprintPerm.parent().show();
    }

    var removeWidget = window.parent.document.getElementById("removeWidget");
    if (window.parent.checkWidgetBlueprintRights && window.parent.selectedWidget.createdByBlueprintWidget
            && (!window.parent.selectedWidget.blueprintEditRuche || window.parent.pageLocked)) {
        disableControl(fontColorWidget);
        fontColorWidget.title = "Selected Content Module is Not Editable";

        disableControl(backgroundsWidget);
        disableControl(borderWidget);
        backgroundsWidget.title = "Selected Content Module is Not Editable";
        borderWidget.title = "Selected Content Module is Not Editable";

        disableControl(widgetSize);
        widgetSize.attr("title", "Selected Content Module is Not Editable");
    } else {
        disableControl(fontColorWidget, false);
        fontColorWidget.title = "";

        disableControl(backgroundsWidget, false);
        backgroundsWidget.title = "";

        disableControl(widgetSize, false);
        widgetSize.attr("title", "");
    }

    if (window.parent.checkWidgetBlueprintRights && window.parent.selectedWidget.createdByBlueprintWidget
            && (window.parent.selectedWidget.blueprintRequired || window.parent.pageLocked)) {
        disableControl(removeWidget);
        removeWidget.title = "Selected Content Module is Not Editable";
    } else {
        disableControl(removeWidget, false);
        removeWidget.title = "";
    }
}

function assignButtons() {
    // Don't use jquery click to assign click function here. It may cause that configure window opens twice. Don't know the reason. Dmitry.
    var configureItemSettings = window.parent.configureItemSettings;

    window.parent.document.getElementById("removeWidget").onclick = function () {
        deleteWidget();
    };
    window.parent.document.getElementById("addWidget").onclick = function () {
        showAddWidget();
    };
    window.parent.document.getElementById("configureWidget").onclick = function () {
        configureWidget({});
    };
    window.parent.document.getElementById("editNetworkSettings").onclick = function () {
        configureWidget({showSecondTab: true});
    };
    window.parent.document.getElementById("editStoreSettings").onclick = function () {
        configureWidget({showStoreSettings: true});
    };
    window.parent.document.getElementById("contentsWidget").onclick = function () {
        window.parent.contentsWidget();
    };
    window.parent.document.getElementById("widgetBlueprintPerm").onclick = function () {
        configureItemSettings.show({widgetId: window.parent.selectedWidget.widgetId},
                configureItemSettings.blueprintPermissionsTab, window.parent.selectedWidget.itemType);
    };
    window.parent.document.getElementById("widgetSizeElement").onclick = function () {
        configureItemSettings.show({widgetId: window.parent.selectedWidget.widgetId},
                    configureItemSettings.itemSizeTab, window.parent.selectedWidget.itemType);
    };
    window.parent.document.getElementById("addEditFormRecords").onclick = function () {
        window.parent.contentsWidget();
    };
    window.parent.document.getElementById("completeOrders").onclick = function () {
        window.open("/user/manageItems.action?itemType=ORDER_FORM&presetFilterByOwnerSiteId=" + window.parent.siteId);
    };
    window.parent.document.getElementById("fontColorWidget").onclick = function () {
        configureWidgetCssParameters(false);
    };
    window.parent.document.getElementById("fontColorArea").onclick = function () {
        configureWidgetCssParameters(true);
    };
    window.parent.document.getElementById("widgetBorder").onclick = function () {
        configureItemSettings.show({widgetId: window.parent.selectedWidget.widgetId},
                configureItemSettings.borderTab, window.parent.selectedWidget.itemType);
    };
    window.parent.document.getElementById("widgetBackground").onclick = function () {
        configureItemSettings.show({widgetId: window.parent.selectedWidget.widgetId},
                configureItemSettings.backgroundTab, window.parent.selectedWidget.itemType);
    };
    window.parent.document.getElementById("widgetAccessible").onclick = function () {
        configureItemSettings.show({widgetId: window.parent.selectedWidget.widgetId},
                configureItemSettings.accessibleTab, window.parent.selectedWidget.itemType);
    };
    window.parent.document.getElementById("areaBorder").onclick = function () {
        window.parent.configureMediaBlockSettings.show(window.parent.selectedMediaBlock.widgetId,
                    window.parent.configureMediaBlockSettings.borderTab);
    };
    $($.find("[exportData=true]", window.parent.document)).each(function() {
        this.onclick = function () {
            if (window.parent.selectedWidget && window.parent.selectedWidget.formId) {
                window.parent.manageDataExports.showManageDataExportsWindow(window.parent.selectedWidget.formId);
            }
        };
    });
    window.parent.document.getElementById("areaBackground").onclick = function () {
        window.parent.configureMediaBlockSettings.show(window.parent.selectedMediaBlock.widgetId,
                window.parent.configureMediaBlockSettings.backgroundTab);
    };
}

function widgetDoubleClick() {
    if (!window.parent.selectedWidget) {
        return;
    }

    if (window.parent.selectedWidget.itemType == "FORUM"
            || window.parent.selectedWidget.itemType == "BLOG"
            || window.parent.selectedWidget.itemType == "GALLERY") {
        window.parent.contentsWidget();
    } else {
        configureWidget({});
    }
}

function contentsWidget() {
    if (!window.parent.selectedWidget) {
        return;
    }

    if (window.parent.selectedWidget.itemType == 'BLOG' || window.parent.selectedWidget.itemType == 'FORUM') {
        if (window.parent.selectedWidget.itemType == 'BLOG') {
            var contentsWindow = createConfigureWidgetIframe({
                width:800,
                height:750,
                onBeforeClose: function () {
                    new ServiceCall().executeViaDwr("GetFunctionalWidgetsService", "getInfoForSingleWidget", window.parent.selectedWidget.widgetId, function(info) {
                        updateWidgetInfo(info);
                    });
                },
                titleText:window.parent.selectedWidget.itemName});
            contentsWindow.src = "/blog/showBlog.action?widgetId=" + window.parent.selectedWidget.widgetId;
            getActiveWindow().resize();
        } else if (window.parent.selectedWidget.itemType == 'FORUM') {
            contentsWindow = createConfigureWidgetIframe({
                width:1000,
                height:725,
                titleText:window.parent.selectedWidget.itemName,
                onBeforeClose:function() {
                    new ServiceCall().executeViaDwr("GetFunctionalWidgetsService", "getInfoForSingleWidget", window.parent.selectedWidget.widgetId, function(info) {
                        updateWidgetInfo(info);
                    });
                },
                id:"forum_main_window"});
            contentsWindow.src = "/forum/showForum.action?widgetId=" + window.parent.selectedWidget.widgetId;
            getActiveWindow().resize();
        }
    } else {
        showConfigureFilledForms({
            widgetId: window.parent.selectedWidget.widgetId,
            onBeforeClose: updateGalleryWidgetInfo
        });
    }
}

function updateGalleryWidgetInfo() {
    if ($("#manageFormRecrodsFormId")[0]) {
        //Updating site edit page if this window was called from there. Updating only if it's gallery
        if (window.parent.selectedWidget && (window.parent.selectedWidget.itemType == "GALLERY")) {
            new ServiceCall().executeViaDwr("GetFunctionalWidgetsService", "getInfoForSingleWidget", window.parent.selectedWidget.widgetId, function (widgetInfo) {
                updateWidgetInfo(widgetInfo);
            });
        }
    }
}

function showAddWidget() {
    window.parent.addWidgetItem(window.parent.selectedMediaBlock.widgetId);
}

function configureWidget(settings) {
    if (!window.parent.selectedWidget) {
        return;
    }

    settings = settings ? settings : new Object();
    settings.widgetId = window.parent.selectedWidget.widgetId;

    var configureItemSettings = window.parent.configureItemSettings;

    configureItemSettings.show(settings, configureItemSettings.settingsTab, window.parent.selectedWidget.itemType);
}

function configureWidgetCssParameters(isMediaBlock) {
    if (isMediaBlock) {
        window.parent.configureMediaBlockSettings.show(window.parent.selectedMediaBlock.widgetId,
                window.parent.configureMediaBlockSettings.fontsColorsTab);
    } else {
        if (!window.parent.selectedWidget) {
            return;
        }
        var configureItemSettings = window.parent.configureItemSettings;
        configureItemSettings.show({widgetId: window.parent.selectedWidget.widgetId},
                configureItemSettings.fontsColorsTab, window.parent.selectedWidget.itemType);
    }
}

function calculateWidgetPosition(widget) {
    var children = $(widget.mediaBlock.widgetContainer).children();
    var position = 0;
    for (var i = 0; i < children.length; i++) {
        if (widget == children[i]) {
            return position;
        }

        if ($(children[i]).hasClass("widget")) {
            position++;
        }
    }
    return position;
}

function getWidgetInfoByPosition(widgetInfos, position) {
    for (var i = 0; ; i++) {
        var widgetInfo = widgetInfos[i];

        if (widgetInfo == undefined) {
            break;
        }

        if (widgetInfo.widgetPosition == position) {
            return widgetInfo;
        }
    }

    return undefined;
}

function getWidgetInfoByWidgetId(widgetInfos, widgetId) {
    for (var i = 0; ; i++) {
        var widgetInfo = widgetInfos[i];

        if (widgetInfo == undefined) {
            break;
        }

        if (widgetInfo.widgetId == widgetId) {
            return widgetInfo;
        }
    }

    return undefined;
}

function updateWidgetBorderBackgroundStyle(newStyle) {
    setStyleInnerHtml(getElementByIdForSiteFrame("widgetBorderBackgrounds"), newStyle);

    setWidgetSelected(true, true);
}

function removeBorderBackgroundWidgetStyle(response) {
    setStyleInnerHtml(getElementByIdForSiteFrame("widgetBorderBackgrounds"), response);

    setWidgetSelected(true, true);
}

function updateWidgetInfo(newWidgetInfo) {
    if (newWidgetInfo && newWidgetInfo.widget) {
        var widget = getElementByIdForSiteFrame("widgetId" + newWidgetInfo.widget.widgetId);

        var infoSpanEnd = newWidgetInfo.widgetInfo.indexOf(">");
        var divider = newWidgetInfo.widgetInfo.indexOf(":", infoSpanEnd);
        if (divider != -1) {
            widget.header.contentModuleText.innerHTML = newWidgetInfo.widgetInfo.substring(infoSpanEnd + 1, divider + 1);
            widget.header.widgetTypeText.innerHTML = newWidgetInfo.widgetInfo.substring(divider + 1, newWidgetInfo.widgetInfo.length);
        } else {
            widget.header.contentModuleText.innerHTML = newWidgetInfo.widgetInfo;
        }

        widget.content.innerHTML = newWidgetInfo.widgetContent;
        if (widget.itemType != "MENU") {
            setTimeout("setWidgetSelected(true, true);", 200);
        }
        widget.itemName = newWidgetInfo.widget.itemName;
        //Update in block's functional widgets on page/selected
        var widgetInfoOnPage = document.getElementById("widgetNameSpan" + newWidgetInfo.widget.widgetId + "widget");
        // don't remove this if it used for selenium
        if (widgetInfoOnPage) widgetInfoOnPage.innerHTML = newWidgetInfo.widgetInfo;

        updateWidgetBorderBackgroundStyle(newWidgetInfo.widgetStyle);

        //Recalculating widget selected borders position.
        setWidgetSelected(true, true);
    }
}

function getFloatingRowByWidget(widget, checkHasFloating) {
    if ($(widget).hasClass("widgetFloating") || !checkHasFloating) {
        var floatingRow = new Array();
        //Pushing previous elements
        var prev = $(widget).prev();
        for (var i = 0; ; i++) {
            if (prev.hasClass("widgetFloating")) {
                floatingRow.push(prev[0]);
            } else if (prev.hasClass("placeHolder")) {
                //Skip placeholder
                prev = prev.prev();
                continue;
            } else {
                break;
            }
            prev = prev.prev();
        }
        //Pushing widget itself
        floatingRow.push(widget);
        //Pushing next elements
        var next = $(widget).next();
        for (i = 0; ; i++) {
            if (next.hasClass("widgetFloating")) {
                floatingRow.push(next[0]);
            } else if (next.hasClass("placeHolder")) {
                //Skip placeholder
                next = next.next();
                continue;
            } else {
                break;
            }
            next = next.next();
        }

        return floatingRow;
    } else {
        return undefined;
    }
}

function constructRequest(request) {
    var widgets = $(".widget");
    for (var i = 0; ; i++) {
        var widget = widgets[i];
        var widgetToChange = new Object();

        if (widget == undefined) {
            break;
        }

        if (!widget.changed) {
            continue;
        }

        widgetToChange.widgetId = widget.widgetId;
        widgetToChange.itemSize = new Object();
        widgetToChange.itemSize.createClearDiv = widget.createClearDiv;
        widgetToChange.itemSize.floatable = widget.floatable;
        widgetToChange.itemSize.width = widget.width;
        widgetToChange.itemSize.widthSizeType = widget.widthSizeType;
        widgetToChange.itemSize.height = widget.height;
        widgetToChange.itemSize.heightSizeType = widget.heightSizeType;
        widgetToChange.itemSize.overflow_x = widget.overflow_x;
        widgetToChange.itemSize.overflow_y = widget.overflow_y;

        request.push(widgetToChange);
    }
}

function updateWidgetSizeOnPage(settings) {
    if (!settings.widget) {
        settings.widget = getElementByIdForSiteFrame("widgetId" + settings.widgetId);
    }

    if (settings.updateRequest) {
        //If updating from widget size page we need also set class.
        if (settings.updateRequest.itemSize.floatable) {
            $(settings.widget).addClass("widgetFloating");
        } else {
            $(settings.widget).removeClass("widgetFloating");
        }

        settings.widget.style.width = "" + settings.updateRequest.itemSize.width + (settings.updateRequest.itemSize.widthSizeType == "PERCENT" ? "%" : "px");
        settings.widget.style.height = settings.updateRequest.itemSize.height ? "" + settings.updateRequest.itemSize.height + (settings.updateRequest.itemSize.heightSizeType == "PERCENT" ? "%" : "px") : "";
        settings.widget.style.overflowX = settings.updateRequest.itemSize.overflow_x;
        settings.widget.style.overflowY = settings.updateRequest.itemSize.overflow_y;
    } else if (settings.changeFloatable) {
        settings.widget.changed = true;

        //Update with default values
        settings.widget.style.width = "50%";
        settings.widget.style.height = null;
        settings.widget.style.overflowX = "VISIBLE";
        settings.widget.style.overflowY = "VISIBLE";

        settings.widget.floatable = true;
        settings.widget.createClearDiv = false;
        settings.widget.width = 50;
        settings.widget.widthSizeType = "PERCENT";
        settings.widget.height = null;
        settings.widget.heightSizeType = "PERCENT";
        settings.widget.overflow_x = "VISIBLE";
        settings.widget.overflow_y = "VISIBLE";
    } else {
        settings.widget.changed = true;

        settings.widget.style.width = "100%";
        settings.widget.style.height = null;
        settings.widget.style.overflowX = "VISIBLE";
        settings.widget.style.overflowY = "VISIBLE";

        settings.widget.floatable = false;
        settings.widget.createClearDiv = false;
        settings.widget.width = 100;
        settings.widget.widthSizeType = "PERCENT";
        settings.widget.height = null;
        settings.widget.heightSizeType = "PERCENT";
        settings.widget.overflow_x = "VISIBLE";
        settings.widget.overflow_y = "VISIBLE";
    }

    setWidgetSelected(true, true);
}

var globalPageId;

function loadWidget(pageId, widgetPosition) {
    var widgetPositionDiv = document.getElementById("widgetPosition" + widgetPosition);
    widgetPositionDiv.pageId = pageId;
    widgetPositionDiv.position = widgetPosition;

    globalPageId = pageId;
}

function checkColor(color, browser) {
    var coeff;
    if (browser == "FF") {
        var firstSkobkaIndex = color.indexOf("("), firstCommaIndex = color.indexOf(",");
        var red = parseInt(color.substring(firstSkobkaIndex + 1, firstCommaIndex));

        var secondCommaIndex = color.indexOf(",", (firstCommaIndex + 1));
        var green = parseInt(color.substring(firstCommaIndex + 2, secondCommaIndex));

        var secondSkobkaIndex = color.indexOf(")");
        var blue = parseInt(color.substring(secondCommaIndex + 2, secondSkobkaIndex));

        coeff = red + green + blue;
    } else if (browser == "IE") {
        var redHex = color.substring(1, 3), blueHex = color.substring(3, 5), greenHex = color.substring(5, 7);

        red = from16to10(redHex);
        blue = from16to10(blueHex);
        green = from16to10(greenHex);

        coeff = red + green + blue;
    }
    if (coeff >= 500) {
        return "light";
    } else if (coeff < 500 && coeff > 150) {
        return "normal";
    } else {
        return "dark";
    }
}

function loadWidgetInternal(widgetInfo, parent) {
    var reg = /media_block_fixed/;
    if (reg.test(parent.parentNode.parentNode.className) || reg.test(parent.parentNode.className)) {
        widgetInfo.widget.mediaBlockFixed = true;
    }

    var widgetDiv = createWidgetDiv(widgetInfo, parent);

    if (widgetDiv.widgetType != "COMPOSIT" && parent.widgetContainer) {
        widgetDiv.mediaBlockReal = parent.parentNode.parentNode;

        parent.widgetContainer.appendChild(widgetDiv);
        if (widgetInfo.widget.createClearDiv) {
            var clearDiv = createElement("div");
            clearDiv.className = "clearDiv";
            parent.widgetContainer.appendChild(clearDiv);
            widgetDiv.clearDiv = clearDiv;
            widgetDiv.createClearDiv = widgetInfo.widget.createClearDiv;
        }
    } else {
        widgetDiv.mediaBlockReal = parent.parentNode;

        parent.appendChild(widgetDiv);
    }

    if (widgetInfo.widget.childs) {
        for (var i = 0; i < widgetInfo.widget.childs.length; i++) {
            loadWidgetInternal(getWidgetInfoByWidgetId(widgetInfo.childInfos, widgetInfo.widget.childs[i].widgetId), widgetDiv);
        }
    }
}

function createWidgetDiv(widgetInfo, parent) {
    var widget = widgetInfo.widget;

    if (widget.type == "COMPOSIT") {
        var widgetDiv = createElement("ul");
        widgetDiv.itemType = widget.itemType;
        widgetDiv.itemName = widget.itemName;
        widgetDiv.eCommerceStoreWidget = widget.eCommerceStoreWidget;
        widgetDiv.className = "mediaBlockReal";
        widgetDiv.mediaBlockFixed = widget.mediaBlockFixed;
        widgetDiv.childQuanity = widget.childs.length;
        var widgetContainer = createElement("div");
        widgetContainer.className = "widgetContainer";
        widgetContainer.innerHTML = "";
        widgetContainer.id = "widget" + widgetInfo.widget.widgetId;
        widgetDiv.widgetContainer = widgetContainer;

        widgetDiv.onclick = function() {
            selectMediaBlock(widgetDiv);
        };

        widgetDiv.ondblclick = function() {
            if (widgetDiv.childQuanity == 0) {
                widgetDiv.onclick();
                window.parent.addWidgetItem(window.parent.selectedMediaBlock.widgetId);
            }
        };

        var addWidgetButton = createAddButtonForMediaBlock(widgetDiv);

        var contentAreaText = createElement("span");
        contentAreaText.innerHTML = (widgetDiv.childQuanity == 0 ? "Empty " : "") + "Content Area" + (widgetDiv.mediaBlockFixed ? " - Fixed Size" : "");
        contentAreaText.className = "content_area";
        contentAreaText.id = "content_area_text" + widget.widgetId;

        widgetDiv.contentAreaText = contentAreaText;

        if (widgetDiv.childQuanity == 0) {
            $(widgetDiv).addClass("emptyMediaBlock");
        }

        var floatClearUpDiv = createElement("div");
        floatClearUpDiv.style.clear = "both";

        widgetDiv.appendChild(widgetContainer);
        widgetContainer.appendChild(addWidgetButton);
        widgetDiv.appendChild(floatClearUpDiv);
        widgetDiv.appendChild(contentAreaText);
    } else {
        widgetDiv = createElement("li");

        widgetDiv.mediaBlock = parent;
        widgetDiv.itemType = widget.itemType;
        widgetDiv.itemName = widget.itemName;
        widgetDiv.eCommerceStoreWidget = widget.eCommerceStoreWidget;
        widgetDiv.blueprintEditRuche = widget.blueprintEditRuche;
        widgetDiv.blueprintRequired = widget.blueprintRequired;
        widgetDiv.blueprintEditable = widget.blueprintEditable;
        widgetDiv.createdByBlueprintWidget = widget.createdByBlueprintWidget;
        widgetDiv.className = "widget";
        widgetDiv.formId = widget.formId;

        if (widget.itemSize.floatable) {
            $(widgetDiv).addClass("widgetFloating");
        }

        if (window.parent.checkWidgetBlueprintRights && widget.createdByBlueprintWidget
                && (widget.blueprintRequired || window.parent.pageLocked)) {
            $(widgetDiv).addClass("widgetRequired");
        }

        updateWidgetSizeOnPage({widget:widgetDiv, updateRequest:widget});

        widgetDiv.onclick = function() {
            selectWidget(widgetDiv, false);
        };

        widgetDiv.ondblclick = function() {
            widgetDoubleClick();
        };

        var widgetContent = createElement("div");
        widgetContent.innerHTML = widgetInfo.widgetContent;

        widgetDiv.content = widgetContent;
        widgetDiv.appendChild(widgetContent);

        createWidgetHeader(widgetDiv, widget, widgetInfo.widgetInfo);
        createLeftRightBounds(widgetDiv);

        //Updating info block about functional widget's on the page
        var infoBlock = window.parent.document.getElementById("pageFunctionalElements");
        createPageWidgetInfo(widgetInfo, infoBlock, $(infoBlock).children().length % 2 != 0);
    }

    widgetDiv.id = "widgetId" + widget.widgetId;
    widgetDiv.widgetId = widget.widgetId;
    widgetDiv.position = widget.position;

    disableSelection(widgetDiv);

    return widgetDiv;
}

function createLeftRightBounds(widgetDiv) {
    widgetDiv.insideLeft = function (event) {
        if (!event) event = window.event;

        var scrollPos = getIFrameScrollPosition();

        var cursorx = event.clientX + scrollPos.x;
        var cursory = event.clientY + scrollPos.y;

        var widgetTop = findPosAbs(widgetDiv).top;
        var widgetLeft = findPosAbs(widgetDiv).left;
        var widgetLeftBound = widgetLeft + (widgetDiv.offsetWidth / 10);
        var widgetBottom = findPosAbs(widgetDiv).top + widgetDiv.offsetHeight;

        var inside = false;
        if ((cursory > widgetTop && cursory < widgetBottom &&
                cursorx > widgetLeft && cursorx < widgetLeftBound) || isInsideTempDragArea(cursorx, cursory)) {
            inside = true;
            addTempDragArea(cursorx, cursory);
        }

        return inside;
    };

    widgetDiv.insideRight = function (event) {
        if (!event) event = window.event;

        var scrollPos = getIFrameScrollPosition();

        var cursorx = event.clientX + scrollPos.x;
        var cursory = event.clientY + scrollPos.y;

        var widgetTop = findPosAbs(widgetDiv).top;
        var widgetBottom = findPosAbs(widgetDiv).top + widgetDiv.offsetHeight;
        var widgetRight = findPosAbs(widgetDiv).left + widgetDiv.offsetWidth;
        var widgetRightBound = findPosAbs(widgetDiv).left + widgetDiv.offsetWidth - (widgetDiv.offsetWidth / 10);

        var inside = false;
        if ((cursory > widgetTop && cursory < widgetBottom &&
                cursorx > widgetRightBound && cursorx < widgetRight) || isInsideTempDragArea(cursorx, cursory)) {
            inside = true;
            addTempDragArea(cursorx, cursory);
        }

        return inside;
    };
}
var tempDragArea;
function addTempDragArea(cursorx, cursory) {
    tempDragArea = new Object();
    tempDragArea.top = cursory - 10;
    tempDragArea.bottom = cursory + 10;
    tempDragArea.right = cursorx - 10;
    tempDragArea.left = cursorx + 10;
}

function isInsideTempDragArea(cursorx, cursory) {
    if (tempDragArea) {
        if (cursory > tempDragArea.top && cursory < tempDragArea.bottom &&
                cursorx > tempDragArea.right && cursorx < tempDragArea.left) {
            return true;
        } else {
            destroyTempDragArea();
            return false;
        }
    }
    return false;
}

function destroyTempDragArea() {
    tempDragArea = undefined;
}

function createWidgetHeader(widgetDiv, widget, text) {
    var widgetHeader = createElement("div");
    widgetHeader.className = "widgetHeader";

    if ($(widgetDiv).hasClass("widgetRequired")) {
        $(widgetHeader).addClass("widgetHeaderRequired");
        widgetHeader.title = "This module is locked, and can not be moved.";
    }

    widgetHeader.style.display = "none";
    widgetHeader.id = "widgetHedaer" + widget.widgetId;
    setOpacity(widgetHeader, 0.8);

    widgetDiv.header = widgetHeader;

    widgetDiv.onmouseover = function () {
        var headerTop = widgetDiv.mediaBlock.mediaBlockFixed && isIE() ? widgetDiv.offsetTop : findPosReal(widgetDiv).top;
        var headerLeft = findPosAbs(widgetDiv).left;
        repositionHeader(headerTop, headerLeft, widgetHeader, widgetDiv);
    };

    widgetDiv.onmouseout = function () {
        widgetHeader.style.display = "none";
    };

    widgetDiv.appendChild(widgetHeader);

    //Create header text spans
    var headerText = createElement("div");
    headerText.style.display = "inline";
    var contentModuleText = createElement("span");
    contentModuleText.innerHTML = "Content Module:&nbsp;";
    contentModuleText.style.verticalAlign = "top";
    widgetHeader.contentModuleText = contentModuleText;
    var widgetTypeText = createElement("span");
    widgetTypeText.style.verticalAlign = "top";
    widgetHeader.widgetTypeText = widgetTypeText;
    headerText.appendChild(contentModuleText);
    headerText.appendChild(widgetTypeText);
    widgetHeader.headerText = headerText;
    widgetHeader.appendChild(headerText);

    //Set up header text
    var infoSpanEnd = text.indexOf(">");
    var divider = text.indexOf(":", infoSpanEnd);
    if (divider != -1) {
        contentModuleText.innerHTML = text.substring(infoSpanEnd + 1, divider + 1);
        widgetTypeText.innerHTML = text.substring(divider + 1, text.length);
    } else {
        contentModuleText.innerHTML = text;
    }

    //Create manage images
    if (widgetDiv.itemType != "LOGIN") {
        var editSettingsButton = createElement("span");
        $(editSettingsButton).css({
            backgroundImage: "url(/images/edit_widget.gif)",
            padding: "0 22px 9px 0",
            marginLeft: "5px",
            cursor:"pointer"
        });
        editSettingsButton.innerHTML = "&nbsp;";
        setOpacity(editSettingsButton, 0.8);

        editSettingsButton.onclick = function () {
            selectWidget(widgetDiv, false);
            configureWidget({});
        };

        widgetHeader.appendChild(editSettingsButton);
        widgetHeader.editSettingsButton = editSettingsButton;
    }

    if (!$(widgetDiv).hasClass("widgetRequired")) {
        var deleteButton = createElement("span");
        deleteButton.className = "deleteWidgetButton";
        $(deleteButton).css({
            backgroundImage: "url(/images/trash_widget.jpg)",
            padding: "0 22px 9px 0",
            marginLeft: "5px",
            cursor:"pointer"
        });
        deleteButton.innerHTML = "&nbsp;";
        setOpacity(deleteButton, 0.8);

        deleteButton.onclick = function () {
            selectWidget(widgetDiv, false);
            deleteWidget();
        };


        widgetHeader.appendChild(deleteButton);
        widgetHeader.deleteButton = deleteButton;
    }
}

function repositionHeader(headerTop, headerLeft, widgetHeader, widgetDiv) {
    var widgetDivHeight = widgetDiv.offsetHeight;
    var widgetDivWidth = widgetDiv.offsetWidth - 2;

    var visibleHorizontalSpaceLeft = findPosAbs(widgetDiv.mediaBlock).left + widgetDiv.mediaBlock.offsetWidth - headerLeft;
    var visibleVerticalSpaceLeft = findPosAbs(widgetDiv.mediaBlockReal).top + widgetDiv.mediaBlockReal.offsetHeight - headerTop;

    if (visibleHorizontalSpaceLeft < widgetDivWidth) {
        if (visibleHorizontalSpaceLeft < 0) {
            widgetDivWidth = 0;
        } else {
            widgetDivWidth = visibleHorizontalSpaceLeft;
        }
    }

    if (visibleVerticalSpaceLeft < widgetDivHeight) {
        if (visibleVerticalSpaceLeft < 0) {
            widgetDivHeight = 0;
        } else {
            widgetDivHeight = visibleVerticalSpaceLeft;
        }
    }

    var buttonsAreNotInOneLine = false;
    if (widgetHeader.editSettingsButton && widgetHeader.deleteButton) {
        buttonsAreNotInOneLine = findPosAbs(widgetHeader.widgetTypeText).top != findPosAbs(widgetHeader.editSettingsButton).top
                || findPosAbs(widgetHeader.deleteButton).top != findPosAbs(widgetHeader.editSettingsButton).top;
    } else if (widgetHeader.deleteButton) {
        buttonsAreNotInOneLine = findPosAbs(widgetHeader.widgetTypeText).top != findPosAbs(widgetHeader.deleteButton).top;
    }

    //If header content doesn't fit in one row and goes out of header then add height to header.
    if (widgetDivHeight < 50 && buttonsAreNotInOneLine) {
        widgetDivHeight = 50;
    } else if (widgetDivHeight < 24) {
        widgetDivHeight = 24;
    }

    widgetHeader.style.position = "absolute";
    widgetHeader.style.top = headerTop + "px";
    widgetHeader.style.left = headerLeft + "px";
    widgetHeader.style.width = widgetDivWidth > 0 ? widgetDivWidth + "px" : "";
    widgetHeader.style.height = widgetDivHeight > 0 ? widgetDivHeight + "px" : "";

    widgetHeader.style.display = "block";
}

function createAddButtonForMediaBlock(mediaBlock) {
    var addWidgetButton = createElement("img");
    addWidgetButton.src = "/images/Blue+Button.gif";
    addWidgetButton.onclick = function () {
        mediaBlock.onclick();
        window.parent.addWidgetItem(window.parent.selectedMediaBlock.widgetId);
    };

    addWidgetButton.onmouseover = function () {
        addWidgetButton.src = "/images/Blue+Button+grn.gif";
    };

    addWidgetButton.onmouseout = function () {
        addWidgetButton.src = "/images/Blue+Button.gif";
    };

    addWidgetButton.className = "widget_add_button";
    addWidgetButton.style.display = mediaBlock.childQuanity == 0 ? "block" : "none";

    mediaBlock.addWidgetButton = addWidgetButton;
    return addWidgetButton;
}

function selectMediaBlock(mediaBlock) {
    if (window.parent.comeFormWidgetSelection) {
        window.parent.comeFormWidgetSelection = false;
        return;
    }

    //Unselecting old media block
    if (window.parent.selectedMediaBlock) {
        $(window.parent.selectedMediaBlock).removeClass("selectedMediaBlock");
    }

    //Unselecting old widget
    if (window.parent.selectedWidget) {
        setWidgetSelected(false);
        window.parent.selectedWidget = undefined;
    }

    //Unselecting old info span
    if (window.parent.previousSelectedWidgetInfo) {
        window.parent.previousSelectedWidgetInfo.className = window.parent.previousSelectedWidgetInfo.infoSpanClass;
    }

    //Unselecting "Content Area Text"
    if (window.parent.selectedMediaBlock) {
        window.parent.selectedMediaBlock.contentAreaText.className = "content_area";
    }

    //Setting selected widget to this media block
    window.parent.selectedMediaBlock = mediaBlock;
    //Setting selected style to this media block
    $(window.parent.selectedMediaBlock).addClass("selectedMediaBlock");

    //Selecting "Content Area Text"
    window.parent.selectedMediaBlock.contentAreaText.className = "content_area_selected";

    showManageLinksForWidget();
}

function selectWidget(widget, recalcPosition) {
    //Unselecting old selected widget
    if (window.parent.selectedWidget) {
        setWidgetSelected(false, recalcPosition);
    }

    //Unselecting old info span and selecting new info span
    if (window.parent.previousSelectedWidgetInfo) {
        window.parent.previousSelectedWidgetInfo.className = window.parent.previousSelectedWidgetInfo.infoSpanClass;
    }

    var infoSpan = window.parent.document.getElementById("widget" + widget.widgetId + "InfoSpan");
    window.parent.previousSelectedWidgetInfo = infoSpan;
    infoSpan.className = "selectedWidgetInfo";

    //Unselect's old parent media block for widget and selecteds new one.
    unselectMediaBlockAndSelectNew(widget);

    //Setting selected widget to this widget
    window.parent.selectedWidget = widget;

    //Setting selected style to this widget
    setWidgetSelected(true, recalcPosition);

    showManageLinksForWidget();

    window.parent.comeFormWidgetSelection = true;
}

function setWidgetSelected(selected, calculatePosition) {
    if (!window.parent.selectedWidget || window.parent.dragging) {
        return;
    }

    if (selected) {
        if (calculatePosition) {
            var headerTop = window.parent.selectedWidget.mediaBlock.mediaBlockFixed && isIE() ?
                    window.parent.selectedWidget.offsetTop : findPosReal(window.parent.selectedWidget).top;
            var headerLeft = findPosAbs(window.parent.selectedWidget).left;
            repositionHeader(headerTop, headerLeft, window.parent.selectedWidget.header, window.parent.selectedWidget);
        }

        $(window.parent.selectedWidget.header).addClass("widgetHeaderSelected");
        window.parent.selectedWidget.header.style.display = "block";

        if (!window.parent.selectedWidget.useOldFunction) {
            window.parent.selectedWidget.oldonmouseout = window.parent.selectedWidget.onmouseout;
            window.parent.selectedWidget.oldonmouseover = window.parent.selectedWidget.onmouseover;
            window.parent.selectedWidget.useOldFunction = true;
            window.parent.selectedWidget.onmouseout = "";
            window.parent.selectedWidget.onmouseover = "";
        }
    } else {
        $(window.parent.selectedWidget.header).removeClass("widgetHeaderSelected");
        window.parent.selectedWidget.header.style.display = "none";

        window.parent.selectedWidget.onmouseout = window.parent.selectedWidget.oldonmouseout;
        window.parent.selectedWidget.onmouseover = window.parent.selectedWidget.oldonmouseover;
        window.parent.selectedWidget.useOldFunction = false;
    }
}

//Unselect's old parent media block for widget and selecteds new one.
function unselectMediaBlockAndSelectNew(widget) {
    if (window.parent.selectedMediaBlock != widget.mediaBlock) {
        //Unselecting old selected media block
        if (window.parent.selectedMediaBlock) {
            $(window.parent.selectedMediaBlock).removeClass("selectedMediaBlock");
            window.parent.selectedMediaBlock.contentAreaText.className = "content_area";
        }

        //Selecting new media block
        window.parent.selectedMediaBlock = widget.mediaBlock;
        $(window.parent.selectedMediaBlock).addClass("selectedMediaBlock");
        window.parent.selectedMediaBlock.contentAreaText.className = "content_area_selected";
    }
}

function selectWidgetInInfoBlock(infoSpan) {
    var iFrame = window.parent.document.getElementById("site");
    if (iFrame.style.display != "none") {
        var widget = getElementByIdForSiteFrame("widgetId" + infoSpan.widgetId);

        scrollToWidget(widget);
        selectWidget(widget, true);
    }
}

function deleteWidget() {
    if (!window.parent.selectedWidget) {
        return;
    }

    if ($(window.parent.selectedWidget).hasClass("widgetRequired")) {
        alert("You have no permissions to remove this visual item.");
        return;
    }

    var serviceCall = new ServiceCall();


    if (confirm("You are about to remove this element, please confirm.")) {
        serviceCall.executeViaDwr("DeleteWidgetService", "execute", window.parent.selectedWidget.widgetId, function (response) {
            if (response) {
                makePageDraftVisual(window.parent.getActivePage());

                window.parent.selectedMediaBlock = window.parent.selectedWidget.mediaBlock;
                window.parent.selectedMediaBlock.childQuanity--;
                window.parent.selectedMediaBlock.widgetContainer.removeChild(window.parent.selectedWidget);

                if (window.parent.selectedMediaBlock.childQuanity == 0) {
                    window.parent.selectedMediaBlock.addWidgetButton.style.display = "block";

                    $(window.parent.selectedMediaBlock).addClass("emptyMediaBlock");

                    window.parent.selectedMediaBlock.contentAreaText.innerHTML = "Empty Content Area" + (window.parent.selectedMediaBlock.mediaBlockFixed ? " - Fixed Size" : "");
                }
                removePageWidgetInfo(window.parent.selectedWidget.widgetId);
            } else {
                alert("Can't delete widget!");
            }
        });
    }
}

// this function call from main edit page not from frame
function createWidget(type) {
    if (window.parent.selectedMediaBlock) {
        var request = manageItems.constructCreateItemRequest(type);
        request.widgetId = window.parent.selectedMediaBlock.widgetId;

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        getActiveWindow().disableContentBeforeSaveSettings("Inserting an element...");
        serviceCall.executeViaDwr("AddWidgetService", "execute", request, function (widgetInfo) {
            if (widgetInfo) {
                makePageDraftVisual(window.parent.getActivePage());

                var widget = createWidgetDiv(widgetInfo, window.parent.selectedMediaBlock);
                widget.mediaBlockReal = window.parent.selectedMediaBlock.mediaBlockReal;
                addChild(window.parent.selectedMediaBlock.widgetContainer, widget);

                //Make media block selected and not empty.
                $(window.parent.selectedMediaBlock).addClass("selectedMediaBlock");
                $(window.parent.selectedMediaBlock).removeClass("emptyMediaBlock");
                window.parent.selectedMediaBlock.childQuanity++;

                //Change text to "Content Area"
                window.parent.selectedMediaBlock.contentAreaText.innerHTML = "Content Area" + (window.parent.selectedMediaBlock.mediaBlockFixed ? " - Fixed Size" : "");

                //Hide add widget button
                window.parent.selectedMediaBlock.addWidgetButton.style.display = "none";

                updateWidgetBorderBackgroundStyle(widgetInfo.widgetStyle);

                selectWidget(widget, true);

                if (getActiveWindow()) {
                    closeConfigureWidgetDiv();
                }
                configureWidget({});
            } else {
                alert("Can't create widget!");
            }
        });
    }
}

function scrollToWidget(widget) {
    var iFrame = window.parent.document.getElementById("site");

    var scrollingDocument = isIE() ? iFrame.Document.documentElement :
            (isFF() ? iFrame.contentDocument.documentElement : iFrame.contentDocument.body);
    var documentElement = isIE() ? iFrame.Document.documentElement : iFrame.contentDocument;
    var scrollLeft = $(documentElement).scrollLeft();
    var scrollTop = $(documentElement).scrollTop();
    var widgetLeft = isIE() ? findPosAbs(widget).left : $(widget).offset().left;
    var widgetTop = isIE() ? findPosAbs(widget).top : $(widget).offset().top;
    var scrolled = false;
    if ((widgetLeft + (($(widget).width() / 2)) > $(iFrame).width() + scrollLeft) &&
            (widgetTop > $(iFrame).height() + scrollTop || widgetTop < scrollTop)) {
        // From top left corner to widget in bottom right corner.
        $(scrollingDocument).animate({
            scrollTop: widgetTop - 15,
            scrollLeft: widgetLeft + ($(widget).width() / 2)
        }, 500);
        scrolled = true;
    } else if ((widgetLeft < scrollLeft) &&
            (widgetTop > $(iFrame).height() + scrollTop || widgetTop < scrollTop)) {
        // From bottom right corner to widget in top left corner.
        $(scrollingDocument).animate({
            scrollTop: widgetTop - 15,
            scrollLeft: widgetLeft - ($(widget).width() / 2)
        }, 500);
        scrolled = true;
    }

    if ((widgetTop > $(iFrame).height() + scrollTop || widgetTop < scrollTop) && !scrolled) {
        // From top or bottom to widget on bottom or top.
        $(scrollingDocument).animate({
            scrollTop: widgetTop - 15
        }, 500);
        scrolled = true;
    }

    if ((widgetLeft + (($(widget).width() / 2)) > $(iFrame).width() + scrollLeft) && !scrolled) {
        // From left to widget on right.
        $(scrollingDocument).animate({
            scrollLeft: widgetLeft + ($(widget).width() / 2)
        }, 500);
        scrolled = true;
    } else if ((widgetLeft < scrollLeft) && !scrolled) {
        // From right to widget on left.
        $(scrollingDocument).animate({
            scrollLeft: widgetLeft - ($(widget).width() / 2)
        }, 500);
        scrolled = true;
    }
}

//These method's needed for crossframe element creation.
function createElement(descriptor) {
    var element;
    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        var iFrame = window.parent.document.getElementById("site");
        element = iFrame.Document.createElement(descriptor);
    } else {
        element = document.createElement(descriptor);
    }
    return element;
}

function addChild(parent, child) {
    getElementByIdForSiteFrame(parent.id).appendChild(child);
}
