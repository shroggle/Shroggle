var manageItems = {

    getSelectedItems : function () {
        var items = new Array();
        $(".itemSelectOrDeselect").each(function () {
            var item = new Object();
            var itemIdAndTypeSplit = this.value.split(",");
            item.itemId = itemIdAndTypeSplit[0];
            item.itemType = itemIdAndTypeSplit[1];
            if (this.checked) items.push(item);
        });
        return items;
    },

    sortUserItems : function () {
        manageItems.updateUserItems("Data sorting, please wait...");
    },

    updateUserItems : function (message) {
        itemsSearch.showClearSearchLink();
        message = message || "Updating data, please wait...";
        var request = manageItems.createUpdateUserItemsRequest();
        selectDashboardMenuLink(request.itemType);
        createLoadingArea({element:$("#userItemsTable")[0], text: message, color:"green", guaranteeVisibility:true});
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ManageItemsService", "execute", request, function(response) {
            if (response) {
                $("#userItemsDiv").html(response);
                removeLoadingArea();
            }
        });

        function selectDashboardMenuLink(selectedItemType) {
            selectedItemType = isFormItemType() ? $("#allFormsType").val() : selectedItemType;
            var selectAllItemsMenu = true;
            $($("#dashboardItemTypes").val().split(",")).each(function() {
                var itemType = this.trim();
                var a = $("#dashboardMenuLink" + itemType);
                if (itemType == selectedItemType) {
                    selectAllItemsMenu = false;
                    setSelected(a);
                } else {
                    var parentDiv = a.parent().parent();
                    parentDiv.removeClass("selected");
                    parentDiv[0].onmouseover = function() {
                        dashboardMenu.setSelected(parentDiv[0]);
                    };
                    parentDiv[0].onmouseout = function() {
                        dashboardMenu.setUnselected(parentDiv[0]);
                    };
                    a.attr("href", a.attr("previousHref"));
                    a.css("cursor", "pointer");
                }
            });
            if (selectAllItemsMenu) {
                setSelected($("#dashboardMenuLink" + $("#allItemsType").val()));
            }

            /*-------------------------------------------Internal Functions-------------------------------------------*/
            function isFormItemType() {
                var formItemType = false;
                $($("#formItemsType").val().split(",")).each(function() {
                    var itemType = this.trim();
                    if (itemType == selectedItemType) {
                        formItemType = true;
                    }
                });
                return formItemType;
            }

            function setSelected(a) {
                var parentDiv = a.parent().parent();
                parentDiv[0].onmouseover = function() {
                };
                parentDiv[0].onmouseout = function() {
                };
                parentDiv.addClass("selected");
                a.attr("href", "javascript:void(0)");
                a.css("cursor", "default");
            }

            /*-------------------------------------------Internal Functions-------------------------------------------*/
        }
    },

    createUpdateUserItemsRequest : function () {
        var sortProperties = tableWithSort.getSortProperties();
        return {
            itemType: $("#filterItemType").val(),
            sortType: sortProperties.sortFieldType,
            DESC: sortProperties.descending,
            filterByOwnerSiteId: $("#filterSiteOwner > option:selected").val(),
            searchKeyByItemName : itemsSearch.getSearchKey(),
            pageNumber : paginator.getPageNumber()
        };
    },

    showAddItem : function () {
        addWidgetItem(null, true);
    },

    constructDefaultCreateItemRequest : function (type, siteId) {
        return {
            clearFontsAndColors: false,
            clearBorderAndBackground: false,
            copyContent: false,
            itemId: null,
            siteId: siteId,
            itemType: type,
            widgetType: type
        };
    },

    constructCreateItemRequest: function (type) {
        var request = {
            clearFontsAndColors: $("#insertClearFontAntColor").attr("checked"),
            clearBorderAndBackground: $("#insertClearBorderAndBackground").attr("checked"),
            copyContent: $("#insertCopy").attr("checked"),
            itemType: type,
            widgetType: type
        };

        if ($("#insertExist").attr("checked")) {
            var items = $("#insertItems")[0];
            if (items.selectedIndex > -1) {
                request.itemId = items.options[items.selectedIndex].value;
            }

            if (!request.itemId || request.itemId < 0) {
                alert("Please select item!");
                return "ERROR";
            }
        }

        return request;
    },

    // use this function to create items elsewhere (i.e. manage registrants page).
    // settings are:
    // type - type of created item. Required.
    // siteId - siteId to witch item will belong. Required.
    // manualSettingsShowFunc - this function will be used to show settings after item creation if set. Optional.
    createItem : function (settings) {
        var request = this.constructDefaultCreateItemRequest(settings.itemType, settings.siteId);

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("AddItemService", "execute", request, function (response) {
            response.onAfterClose = settings.onAfterClose;

            manageItems.showItemSettings(response);
        });
    },

    createItemOnManageItemsPage : function (type) {
        var errors = new Errors();
        errors.clear();

        var request = this.constructCreateItemRequest(type);
        request.siteId = $("#availableSites").val();

        if (!request.widgetType) {
            errors.set("SELECT_WIDGET", $("#selectContentModule").val());
            return;
        }

        if ($("#insertExist").attr("checked")) {
            if (!request.itemId) {
                alert("Please select item!");
                return;
            }
        }

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        getActiveWindow().disableContentBeforeSaveSettings("Inserting an element...");
        serviceCall.executeViaDwr("AddItemService", "execute", request, function (response) {
            if (isAnyWindowOpened()) {
                closeConfigureWidgetDiv();
            }

            response.onAfterClose = manageItems.updateUserItems;

            manageItems.showItemSettings(response);
        });
    },

    showItemContent : function (settings) {
        if (settings.itemType == "BLOG") {
            var contentsWindow = createConfigureWidgetIframe({width:800, height:750, titleText:"Blog managment: " + settings.itemName});
            contentsWindow.src = "/blog/showBlog.action?blogId=" + settings.itemId;
            getActiveWindow().resize();

            if (settings.onAfterClose) {
                getActiveWindow().onAfterClose = settings.onAfterClose;
            }
        } else if (settings.itemType == "FORUM") {
            contentsWindow = createConfigureWidgetIframe({width:1000, height:725, titleText:"Forum managment: " + settings.itemName, id:"forum_main_window"});
            contentsWindow.src = "/forum/showForum.action?forumId=" + settings.itemId;
            getActiveWindow().resize();

            if (settings.onAfterClose) {
                getActiveWindow().onAfterClose = settings.onAfterClose;
            }
        } else if (settings.itemType == "REGISTRATION" || settings.itemType == "CHILD_SITE_REGISTRATION"
                || settings.itemType == "CONTACT_US" || settings.itemType == "CUSTOM_FORM") {
            showConfigureFilledForms({
                formId: settings.itemId,
                onClose: settings.onAfterClose ? settings.onAfterClose : null
            });
        } else if (settings.itemType == "GALLERY") {
            showConfigureFilledForms({
                galleryId: settings.itemId,
                onClose: settings.onAfterClose ? settings.onAfterClose : null
            });
        }
    },

    showItemSettings : function (settings) {
        configureItemSettings.show({widgetId: settings.widgetId, itemId: settings.itemId}, configureItemSettings.settingsTab, settings.itemType);

        if (settings.onAfterClose) {
            getActiveWindow().onAfterClose = settings.onAfterClose;
        }
    }
};

var itemsShare = {
    showShareItem : function () {
        if (manageItems.getSelectedItems().length == 0) {
            alert($("#selectOneOrMoreObjectsToShare").val());
            return;
        }

        var configureWindow = createConfigureWindow({width: 700, height: 500});
        new ServiceCall().executeViaDwr("ShowShareUserItemService", "execute", manageItems.getSelectedItems(), function (html) {
            configureWindow.setContent(html);
            $("#shareUserItemsCancel").bind("click", function () {
                closeConfigureWidgetDiv();
            });
            configureWindow.resize();
        });
    },

    shareItem : function () {
        var shareType = "EDIT";
        if ($("#shareTypeRead").get(0).checked) {
            shareType = "READ";
        }

        var items = new Array();
        $(".itemIdAndType").each(function () {
            var item = new Object();
            var itemIdAndTypeSplit = this.value.split(",");
            item.itemId = itemIdAndTypeSplit[0];
            item.itemType = itemIdAndTypeSplit[1];
            item.shareType = shareType;
            items.push(item);
        });

        var targetSiteIds = new Array();
        $(".targetSiteId").each(function () {
            if (this.checked) {
                targetSiteIds.push(this.value);
            }
        });
        if (targetSiteIds.length == 0) {
            alert("Please, select some sites to share selected items with.");
            return;
        }

        var serviceCall = new ServiceCall();
        serviceCall.executeViaDwr("ShareUserItemService", "execute", targetSiteIds, items, function () {
            closeAllWindows();
            manageItems.updateUserItems();
        });
    }
};

var itemsDeletion = {

    deleteItems : function () {
        var items = manageItems.getSelectedItems();
        if (items.length == 0) {
            alert($("#selectOneOrMoreObjectsToDelete").val());
            return;
        }

        if (!confirm($("#deleteItemsConfirm").val())) {
            return;
        }

        var itemIdAndTypesString = "";
        for (var i = 0; i < items.length; i++) {
            itemIdAndTypesString += "&itemTypeByIds[" + items[i].itemId + "]=" + items[i].itemType;
        }
        var request = manageItems.createUpdateUserItemsRequest();
        window.location = "/user/removeSiteItems.action" +
                "?itemType=" + request.itemType +
                "&filterByOwnerSiteId=" + request.filterByOwnerSiteId +
                "&sortType=" + request.sortType +
                "&descending=" + request.DESC +
                "&searchKeyByItemName=" + request.searchKeyByItemName +
                "&pageNumber=" + request.pageNumber + itemIdAndTypesString;
    },

    deleteItemAccess : function (siteId, itemId, itemType) {
        if (confirm($("#removeAccessPermissionsConfirm").val())) {
            window.location =
                    "/user/deleteItemAccess.action?itemId=" + itemId + "&siteId=" + siteId + "&itemType=" + itemType;
        }
    }
};

/*-------------------------------------------------------Search-------------------------------------------------------*/

var itemsSearch = {

    searchTimeout : undefined,

    searchByName : function (searchKey) {
        if (searchKey.length == 0) {
            itemsSearch.searchTimeout = false;
            manageItems.updateUserItems();
            return;
        }

        if (searchKey.length >= 3) {
            if (itemsSearch.searchTimeout) {
                clearTimeout(itemsSearch.searchTimeout);
            }
            itemsSearch.searchTimeout = setTimeout(manageItems.updateUserItems, 500);
        }
    },

    clearSearch : function () {
        $("#searchByItemName").val("");
        manageItems.updateUserItems();
    },

    showClearSearchLink : function () {
        $("#clearSearch")[0].style.visibility = (itemsSearch.getSearchKey() == "") ? "hidden" : "visible";
    },

    getSearchKey : function () {
        return $("#searchByItemName").val();
    }
};


/*-------------------------------------------------------Search-------------------------------------------------------*/





