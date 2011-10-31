var configureAccessibility = {};

configureAccessibility.onAfterShow = function (html) {
    if (!isAnyWindowOpened()) {
        return;
    }

    if (html) {
        getActiveWindow().setContent(html);
    }
    disableRestrictedAccessDiv($("#disableRestrictedAccessDiv").val() == "true");

    if ($("#siteOnItemRightType").val() == "READ") {
        configureAccessibility.disable();
    }
};

configureAccessibility.show = function (elementId, elementType) {
    var configureSiteAccessSettings = createConfigureWindow({width:400, height:470});
    new ServiceCall().executeViaDwr("ConfigureAccessibleSettingsService", "execute", elementId, elementType, true, function (html) {
        configureAccessibility.onAfterShow(html);
    });
};

function disableRestrictedAccessDiv(disable) {
    disableControl($("#restrictedAccessDiv")[0], disable);
    if (!disable) {
        disableVisitorsGroupsDiv(!$("#registeredVisitors")[0].checked);
    }
}

function disableVisitorsGroupsDiv(disable) {
    disableControl($("#groupsArea")[0], disable);
}

configureAccessibility.save = function (elementId, itemType, siteId, closeAfterSaving) {
    var showManageRegistrants = $("#manageRegistrantsAfterSave")[0].checked;
    var request = {
        elementId : elementId,
        elementType : itemType,
        showManageRegistrants : showManageRegistrants,
        siteId : siteId,
        saveAccessibilityInCurrentPlace: $("#saveAccessibilityInCurrentPlace").attr("checked") || false,

        accessibleSettings : {
            access : ($("#unlimitedAccess")[0].checked ? $("#unlimitedAccess").val() : $("#restrictedAccess").val()),
            administrators : $("#administrators")[0].checked,
            visitors : $("#registeredVisitors")[0].checked,
            visitorsGroups : getVisitorsGroups()
        }
    };
    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("CreateAccessibilitySettingsService", "execute", request, function (response) {
        var accessibleElementTypes = {
            SITE:$("#accessibleElementType_SITE").val(),
            PAGE:$("#accessibleElementType_PAGE").val(),
            WIDGET:$("#accessibleElementType_WIDGET").val(),
            ITEM:$("#accessibleElementType_ITEM").val()
        };

        // For page we are closing window in configurePageSettings.onAfterSave
        if (itemType != accessibleElementTypes.PAGE) {
            closeConfigureWindow();
        }

        if (itemType != accessibleElementTypes.ITEM && !$("#dashboardPage")[0]) {
            if (itemType != accessibleElementTypes.SITE) {
                makePageDraftVisual(window.parent.getActivePage());
            }
            if (itemType == accessibleElementTypes.PAGE) {
                configurePageSettings.onAfterSave(response, elementId, closeAfterSaving);
            } else if (itemType == accessibleElementTypes.WIDGET) {
                updateWidgetInfo(response.functionalWidgetInfo);
            }
        }
        if (showManageRegistrants) {
            var configureSiteAccessSettings = createConfigureWindow({width:800, height:500});
            configureSiteAccessSettings.setContent(response.manageRegistrantsHtml);
        }
    });

    function closeConfigureWindow() {
        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
        } else {
            getActiveWindow().enableContent();
            setWindowSettingsUnchanged();
        }
    }


    function getVisitorsGroups() {
        var visitorsGroups = [];
        $("#groupsArea").find("input").each(function() {
            if (this.checked) {
                visitorsGroups.push(this.id);
            }
        });
        return visitorsGroups;
    }
};

configureAccessibility.disable = function () {
    disableControl($("#unlimitedAccess")[0]);
    disableControl($("#restrictedAccess")[0]);
    disableControl($("#administrators")[0]);
    disableControl($("#registeredVisitors")[0]);

    disableControl($("#visitorsGroups"));

    disableControl($("#addNewRegistrationFormLink")[0]);
    disableControl($("#inviteGuestLink")[0]);

    $("#forItemDiv", $("#configureAccessibilityButtons")).css("visibility", "hidden");
    $("#windowSave", $("#configureAccessibilityButtons")[0]).hide();
    $("#windowApply", $("#configureAccessibilityButtons")[0]).hide();
    $("#windowCancel", $("#configureAccessibilityButtons")[0]).val("Close");

    $("#accessibilityReadOnlyMessage").show();
};