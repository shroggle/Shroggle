var configureMenu = {};

var configureMenuWidget, errors, l_menuItemId;

configureMenu.onBeforeShow = function (settings) {
    configureMenu.settings = settings;
};

configureMenu.onAfterShow = function () {
    if (!isAnyWindowOpened()) {
        return;
    }
    hideMenuDetailsPanel();

    configureMenu.readOnly = $("#siteOnItemRightType").val() == "READ";
    if (configureMenu.readOnly) {
        configureMenu.disable();
    }

    configureMenu.loadTree(true);
    configureMenu.setUpEditHandler();
    //Accuring the error block
    configureMenu.errors = new Errors({}, "menuErrors");
    setSelectedPageInTree(getSelectedMenuItemId());

    // initialize scrollable with mousewheel support
    $("#scrollableMenuPreview").scrollable({ mousewheel: true, speed: 400, items:"#menuPreviewItems"});
};

configureMenu.save = function (closeAfterSaving) {
    configureMenu.errors.clear();

    var request = {
        widgetId: configureMenu.settings.widgetId,
        name: $("#menuName").val(),
        menuId: getSelectedMenuId(),
        menuStyleType: $("#menuStyle").val(),
        includePageTitle: $("#includePageTitle").attr("checked"),
        menuItems: createMenuStructure(),
        menuStructure: $("#useDefaultStructure").attr("checked") ? $("#defaultStructure").val() : $("#ownStructure").val(),
        includeInMenus: collectInfoAboutInclusionInMenu(getMenuHolder(), new Array())
    };

    //Checking for errors
    if (request.name.trim().length == 0) {
        configureMenu.errors.set("EMPTY_MENU_NAME", $("#menuNameCannotBeEmpty").val(), [$("#menuName")[0]]);
    }

    if (configureMenu.errors.hasErrors()) {
        return;
    }

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.MenuNameNotUniqueException",
            configureMenu.errors.exceptionAction({errorId:"MenuNameNotUniqueException", fields:[$("#menuName")[0]]}));
    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateMenuService", "execute", request, function(response) {
        if (response) {
            if ($("#dashboardPage")[0]) {
                $("#itemName" + request.menuId).html(request.name);

                if (closeAfterSaving) {
                    closeConfigureWidgetDiv();
                }
            } else {
                if (configureMenu.settings.widgetId) {
                    makePageDraftVisual(window.parent.getActivePage());
                }

                if (closeAfterSaving) {
                    if (configureMenu.settings.widgetId) {
                        closeConfigureWidgetDivWithUpdate(response);
                    } else {
                        closeConfigureWidgetDiv();
                    }
                }
            }

            if (!closeAfterSaving) {
                updateWidgetInfo(response);
                getActiveWindow().enableContent();
                setWindowSettingsUnchanged();
            }
        }
    });

    function collectInfoAboutInclusionInMenu(menuHolder, includeInMenus) {
        var containers = $(menuHolder).find("[container=\"container\"]");
        $(containers).each(function() {
            var menuItemId = $(this).attr("menuItemId");
            var includeInMenu = $(this).find(".menuCheckbox")[0].checked;
            includeInMenus.push({menuItemId:menuItemId, includeInMenu:includeInMenu});
        });
        return includeInMenus;
    }

    function createMenuStructure() {
        return createMenuStructureInternal(getMenuHolder());
    }

    function createMenuStructureInternal(menuHolder) {
        var rootElements = $(menuHolder).children(":not(.line)");
        var items = new Array();
        $(rootElements).each(function() {
            var container = $(this).children("[container=\"container\"]");
            var pageId = container.attr("pageid");
            var checked = container.find(".menuCheckbox")[0].checked;
            var childrenHolder = $(this).children(".parent");
            var hasChildren = childrenHolder.length > 0;
            var children = [];
            if (hasChildren) {
                children = createMenuStructureInternal(childrenHolder[0]);
            }
            items.push({pageId:pageId, includeInMenu:checked, childs: children});
        });
        return items;
    }
};

configureMenu.loadTree = function (resizeAfterLoad) {
    $("li[page=\"page\"]").each(function () {
        applyNodeAttributes(this);
    });
    $('#menuTree').simpleTree({
        autoclose: false,
        animate:true,
        docToFolderConvert:true,
        drag:!configureMenu.readOnly,
        afterClick: updateInfoAboutSelectedMenuItem,
        afterMove:function(destination, source, pos) {
            $("#useDefaultStructure")[0].checked = false;
            new ServiceCall().executeViaDwr("MoveMenuItemService", "execute", $(source).attr("menuItemId"), $(destination).attr("menuItemId"), pos);
        }
    });
    if (resizeAfterLoad) {
        getActiveWindow().resize();
    }

    function applyNodeAttributes(node) {
        node.pageId = $(node).attr("pageId");
    }
};

configureMenu.disable = function () {
    disableControl($("#menuName")[0]);
    disableControl($("#menuStyle")[0]);
    disableControl($("#includePageTitle")[0]);

    disableControls($(".menuCheckbox"));

    disableControl($("#menuOrientationId")[0]);
    disableControl($("#useDefaultStructure")[0]);
    disableControl($("#menuItemDetails"));
    disableControl($("#addRemoveMenuItemButtons"));

    $("#windowSave", $("#configureMenuButtons")[0]).hide();
    $("#windowApply", $("#configureMenuButtons")[0]).hide();
    $("#windowCancel", $("#configureMenuButtons")[0]).val("Close");

    $("#menuReadOnlyMessage").show();
    $("#menuErrors").hide();
};

configureMenu.setUpEditHandler = function() {

    $("#menuTreeHolder a").bind('click', function(){
        var menuItemId = $(this).attr("menuItemId");
        configureMenu.errors.clear();
        applyUnsavedMenuItemDetails(function() {
            createLoadingArea({element:$("#menuItemDetailsHolder")[0], text: "Loading...", color: "green", guaranteeVisibility:true});
            new ServiceCall().executeViaDwr("GetInfoAboutItemService", "execute", menuItemId, function(response) {
                $("#menuItemDetails").html(response);
                removeLoadingArea();
                setSelectedPageInTree(menuItemId);
                if (configureMenu.readOnly) {
                    configureMenu.disable();
                }
                showMenuDetailsPanel();
            });
        });
    });
};

function disableIncludePageTitle(select) {

    var treeStyleHorizontal = $("#treeStyleHorizontal").val();
    var treeStyleVertical = $("#treeStyleVertical").val();
    var tabbedStyleType = $("#tabbedStyle").val();
    var selectedMenuType = select.value;
    $("#includePageTitle")[0].disabled = (select.value == treeStyleHorizontal || select.value == treeStyleVertical || select.value == tabbedStyleType);

    if (selectedMenuType == tabbedStyleType){
        $("#imageUploaderPanelId").show();
    }
    else {
        $("#imageUploaderPanelId").hide();
    }
}

function updateMenuPreview(style) {
    $("#menuPreviewItems").html($("#" + style).html());
    var scrollableApi = $("#scrollableMenuPreview").data("scrollable");
    scrollableApi.begin(0);
}


function addMenuItem() {
    var parentMenuItemId = getSelectedMenuItemId();
    var menuId = getSelectedMenuId();
    applyUnsavedMenuItemDetails(function() {
        createLoadingArea({element:$("#menuItemsHolder")[0], text: "Adding...", color: "green", guaranteeVisibility:true});
        new ServiceCall().executeViaDwr("AddMenuItemService", "execute", parentMenuItemId, menuId, function (response) {
            updateMenuTreeAndInfoAboutSelectedItem(response);
            $("#useDefaultStructure")[0].checked = false;
            removeLoadingArea();
            showMenuDetailsPanel();
        });
    });
}

function removeMenuItem() {
    var removedMenuItemId = getSelectedMenuItemId();
    if (!removedMenuItemId) {
        alert("Please, select a page!");
        return;
    }
    createLoadingArea({element:$("#menuItemsHolder")[0], text: "Removing...", color: "green", guaranteeVisibility:true});
    new ServiceCall().executeViaDwr("RemoveMenuItemService", "execute", removedMenuItemId, function (response) {
        updateMenuTreeAndInfoAboutSelectedItem(response);
        $("#useDefaultStructure")[0].checked = false;
        removeLoadingArea();
    });
    hideMenuDetailsPanel();
}

function restoreDefaultStructure(createDefault) {
    if (createDefault) {
        if (confirm($("#discardCustomizedPageStructure")[0].value)) {
            var menuId = getSelectedMenuId();
            getActiveWindow().disableContent({backgroundColor: "white", fontWeight: "bold", color: "green", borderStyle:"solid"});
            new ServiceCall().executeViaDwr("RestoreDefaultStructureService", "execute", menuId, function (response) {
                updateMenuTreeAndInfoAboutSelectedItem(response);
                getActiveWindow().enableContent();
            });
        } else {
            $("#useDefaultStructure")[0].checked = false;
        }
    }
}

function updateInfoAboutSelectedMenuItem(element) {
    $("#menuTreeHolder a").hide();
    var menuItemId = $(element).attr("menuItemId");
    if (l_menuItemId == undefined ||
        l_menuItemId != menuItemId ){
            l_menuItemId = menuItemId;
            $(element).find('a:first').show();
            hideMenuDetailsPanel();
            return;
    }
    $(element).find('a:first').show();
    return;
}

function applyChangedMenuItemSettings(onApplyHandler) {
    configureMenu.errors.clear();

    var request = {
        selectedMenuItemId: getSelectedMenuItemId(),
        name: $("#menuItemName").val(),
        title: $("#menuItemTitle").val(),
        pageId: $("#menuItemPageId").val(),
        customUrl: $("#menuItemCustomUrl").val(),
        imageId: $("#menuItemImageId").val() == "null" ? -1 : $("#menuItemImageId").val(),
        itemUrlType: $("#menuItemPageIdCheckbox")[0].checked ? $("#menuItemPageIdCheckbox").val() : $("#menuItemCustomUrlCheckbox").val()
    };

    //Checking for errors
    if (request.name.trim().length == 0) {
        configureMenu.errors.set("EMPTY_MENU_ITEM_NAME", $("#menuItemNameCannotBeEmpty").val(), [$("#menuItemName")[0]]);
    }

    if (configureMenu.errors.hasErrors()) {
        return;
    }

    createLoadingArea({element:$("#menuItemDetailsHolder")[0], text: "Saving...", color: "green", guaranteeVisibility:true});
    new ServiceCall().executeViaDwr("UpdateMenuItemService", "execute", request, function(response) {
        updateMenuTreeAndInfoAboutSelectedItem(response);
        removeLoadingArea();
        setMenuItemDetailsChanged(false);
        showMenuDetailsPanel();
        try {
            onApplyHandler();
        } catch(ex) {
        }
    });
}

function restoreDefaultSettings() {
    $("#menuItemName").val($("#name").val());
    $("#menuItemTitle").val($("#title").val());
    $("#menuItemPageId").val($("#pageId").val());
    $("#menuItemCustomUrl").val($("#customUrl").val());
    $("#menuItemImageId").val($("#imageId").val());
    var menuUrlType = $("#menuUrlType").val();
    if (menuUrlType == "SITE_PAGE") {
        $("#menuItemPageIdCheckbox")[0].checked = true;
        $("#menuItemPageId")[0].disabled = false;
        $("#menuItemCustomUrl")[0].disabled = true;
    } else {
        $("#menuItemCustomUrlCheckbox")[0].checked = true;
        $("#menuItemPageId")[0].disabled = true;
        $("#menuItemCustomUrl")[0].disabled = false;
    }
    $("#menuItemImageHolder")[0].style.visibility = $("#visibility").val();
    $("#menuItemImage")[0].src = $("#imageUrl").val();
    setMenuItemDetailsChanged(false);
}

function getTreeNodeByMenuItemId(menuItemId) {
    return getMenuHolder().find("li[menuItemId=\"" + menuItemId + "\"]")[0];
}

function getSelectedMenuId() {
    return $("#menuId").val();
}

function getSelectedMenuItemId() {
    return $("#selectedMenuItemId").val();
}

function setSelectedPageInTree(menuItemId) {
    if (menuItemId) {
        getMenuHolder().find("div.active").removeClass("active");
        $(getTreeNodeByMenuItemId(menuItemId)).children("div").addClass("active");
    }
}

function getMenuHolder() {
    return $("#menuTreeHolder");
}

function updateMenuTreeAndInfoAboutSelectedItem(response) {
    if (response.treeHtml != null) {
        $("#menuTreeHolder").html(response.treeHtml);
        configureMenu.loadTree(false);
        setSelectedPageInTree(response.selectedMenuItemId);
    }
    $("#menuItemDetails").html(response.infoAboutSelectedItem);
    configureMenu.setUpEditHandler();
}

var menuItemDetailsChanged = false;
function setMenuItemDetailsChanged(changed) {
    menuItemDetailsChanged = changed;
}

function isMenuItemDetailsChanged() {
    return menuItemDetailsChanged;
}

function applyUnsavedMenuItemDetails(onApplyHandler) {
    if (!isMenuItemDetailsChanged() || !confirm($("#saveUnsavedMenuItemChanges").val())) {
        try {
            onApplyHandler();
            setMenuItemDetailsChanged(false);
        } catch(ex) {
        }
    } else {
        applyChangedMenuItemSettings(onApplyHandler);
    }
}

function hideMenuDetailsPanel(){
    $("#menuInnerSettings").hide();
    $("#imageUploaderPanelId").hide();
    $("#menuInnerSettingsBtns").hide();
}

function showMenuDetailsPanel(){

    $("#menuInnerSettings").show();

    var selectedMenuType = $("#menuStyle :selected").val();
    var tabbedStyleType = $("#tabbedStyle").val();
    if (selectedMenuType == tabbedStyleType){
        $("#imageUploaderPanelId").show();
    }
    else {
        $("#imageUploaderPanelId").hide();
    }
    $("#menuInnerSettingsBtns").show();
}