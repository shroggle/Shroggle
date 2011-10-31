function showDashboardWidget(widgetId, itemId, itemType) {
    manageItems.showItemSettings({widgetId:widgetId, itemId : itemId, itemType: itemType});
}

function deleteSite(siteId) {
    if (confirm($("#siteDelete").val())) {
        window.location = "/site/deleteSite.action?siteId=" + siteId;
    }
}

function deleteActivatedBlueprint(siteId) {
    if (confirm($("#deleteActivatedBlueprint").val())) {
        window.location = "/site/deleteSite.action?siteId=" + siteId;
    }
}

function viewLiveSite(pageId) {
    viewLivePage(pageId);
}

function previewSite(pageId) {
    window.open("/site/showPageVersion.action?pageId=" + pageId + "&siteShowOption=ON_USER_PAGES");
}

function optOutFromNetworkFinish() {
    window.location = "/site/optOutFromNetwork.action?siteId=" + disconnectFromNetworkSiteId;
}

function removeFromNetworkFinish() {
    window.location = "/site/removeFromNetwork.action?siteId=" + disconnectFromNetworkSiteId;
}

function optOutFromNetwork(siteId, networkName) {
    if (confirm($("#optOutFromNetworkConfirm").val().replace("<network name>", networkName))) {
        disconnectFromNetworkSiteId = siteId;
        var configureWindow = createConfigureWindow({
            width: 400,
            height: 200
        });
        configureWindow.setContent($("#optOutFromNetworkInfo")[0].innerHTML);
    }
}

function removeFromNetwork(siteId, networkName) {
    if (confirm($("#removeFromNetworkConfirm").val().replace("<network name>", networkName))) {
        disconnectFromNetworkSiteId = siteId;
        var configureWindow = createConfigureWindow({
            width: 400,
            height: 200
        });
        configureWindow.setContent($("#removeFromNetworkInfo")[0].innerHTML);
    }
}

var disconnectFromNetworkSiteId;


function showNetworkInfoWindow(childSiteSettingsId) {
    var showNetworkInfoSettings = createConfigureWindow({width:500, height:300});
    new ServiceCall().executeViaDwr("ShowNetworkSettingsInfoService", "execute", childSiteSettingsId, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        showNetworkInfoSettings.setContent(response);
    });
}

function deactivateSite(siteId) {
    if (confirm($("#offlineSiteConfirm").val())) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("AddEditCreditCardService", "deactivateSite", siteId, function() {
            $("#liveStatus").hide();
            $("#offlineStatus").show();
            $("#deactivateSite").hide();
            $("#goLive").show();

            //Update preview site link
            $("#activeSiteUrl").hide();
            $("#disabledSiteUrl").show();

            $("#siteName").css("color", "#777777");
        });
    }
}


function showSiteInfo(siteId, childSiteSettingsId, currentElement, siteType) {
    if ($(currentElement).attr("selected") == "true") {
        return;
    }
    var previouslySelected = selectSite(currentElement);
    createLoadingArea({element:$("#siteInfo")[0], text: "Loading...", color:"green", guaranteeVisibility:true});
    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            LoginInAccount.EXCEPTION_CLASS,
            userNotLoginedHandler);
    serviceCall.executeViaJQuery("/account/showDashboardSiteInfo.action", {siteId : siteId, childSiteSettingsId : childSiteSettingsId, siteType : siteType}, function(response) {
        $("#siteInfo").html(response);
        removeLoadingArea();
    });

    function userNotLoginedHandler() {
        LoginInAccount.EXCEPTION_ACTION();
        selectSite(previouslySelected);
    }

    function selectSite(currentElement) {
        var currentlySelected = $.find("[selected=true]");
        $(currentlySelected).attr("selected", false);
        setSelected(currentlySelected, false);

        $(currentElement).attr("selected", true);
        setSelected(currentElement, true);
        return currentlySelected;
    }

    function setSelected(element, setSelected) {
        $(element).each(function() {
            var className = this.className;
            var addChildSite = (this.className != this.className.replace("childSite", ""));
            this.className = this.className.replace("childSite", "").trim();
            if (setSelected) {
                this.className += "_selected";
            } else {
                this.className = this.className.replace("_selected", "");
            }
            if (addChildSite) {
                this.className += " childSite";
            }
        });
    }
}

function deleteChildSiteSettings(childSiteSettingsId) {
    if (confirm($("#siteDelete").val())) {
        window.location = "/deleteChildSiteSettings.action?childSiteSettingsId=" + childSiteSettingsId;
    }
}

function initDashboardAccordion(accordionBlockType) {
    $("#dashboardAccordion").accordion({
        header: "div.dashboardAccordionHeader",
        autoHeight: false,
        change : function(event, ui) {
            ui.oldHeader[0].className = ui.oldHeader[0].className.replace("dashboardSelectedAccordion", "dashboardNotSelectedAccordion");
        },
        changestart: function(event, ui) {
            ui.newHeader[0].className = ui.newHeader[0].className.replace("dashboardNotSelectedAccordion", "dashboardSelectedAccordion");
        },
        active: "#" + accordionBlockType
    });
}