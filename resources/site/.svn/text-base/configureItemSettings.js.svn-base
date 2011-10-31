var configureItemSettings = {
    fontsColorsTab: "FONTS_COLORS",
    borderTab: "BORDER",
    backgroundTab: "BACKGROUND",
    itemSizeTab: "ITEM_SIZE",
    settingsTab: "SETTINGS",
    accessibleTab: "ACCESSIBLE",
    blueprintPermissionsTab: "BLUEPRINT_PERMISSIONS"
};

configureItemSettings.show = function(settings, tab, itemType) {
    if (itemType == "LOGIN" && tab == configureItemSettings.settingsTab) {
        return;
    }

    if (itemType == "TAX_RATES") {
        window.parent.configureTaxRates.show({widgetId: settings.widgetId, itemId: settings.itemId});
        return;
    }

    // Normal execution
    var widgetVisualSettingsWindow;

    var standardWindowWidth = isIE() ? 1090 : 1080;
    var extendedWindowWidth = isIE() ? 1200 : 1190;

    var configureObject;
    if (itemType == "FORUM") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureForum.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "BLOG") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureBlog.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "REGISTRATION") {
        widgetVisualSettingsWindow = createConfigureWindow({width:extendedWindowWidth, height:740, uniqueIdentifiers:true});
        widgetVisualSettingsWindow.registrationWindow = true;
        if (tab == configureItemSettings.settingsTab) {
            configureRegistration.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "CUSTOM_FORM") {
        widgetVisualSettingsWindow = createConfigureWindow({width:extendedWindowWidth, height:740, uniqueIdentifiers:true});
        widgetVisualSettingsWindow.customFormWindow = true;
        if (tab == configureItemSettings.settingsTab) {
            configureObject = new ConfigureCustomForms();
            configureObject.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "CONTACT_US") {
        widgetVisualSettingsWindow = createConfigureWindow({width:extendedWindowWidth, height:740, uniqueIdentifiers:true});
        widgetVisualSettingsWindow.contactUsWidnow = true;
        if (tab == configureItemSettings.settingsTab) {
            configureObject = new ConfigureContactUses();
            configureObject.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "CHILD_SITE_REGISTRATION") {
        widgetVisualSettingsWindow = createConfigureWindow({width:extendedWindowWidth, height:740, uniqueIdentifiers:true});
        widgetVisualSettingsWindow.csrWindow = true;
        if (tab == configureItemSettings.settingsTab) {
            configureChildSiteRegistration.onBeforeShow({widgetId: settings.widgetId, showSecondTab: settings.showSecondTab});
        }
    } else if (itemType == "ADVANCED_SEARCH") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureAdvancedSearch.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "MANAGE_VOTES") {
        widgetVisualSettingsWindow = createConfigureWindow({width:extendedWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureManageVotes.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "BLOG_SUMMARY") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureBlogSummary.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "PURCHASE_HISTORY") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configurePurchaseHistory.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "SHOPPING_CART") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureShoppingCart.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "TELL_FRIEND") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureTellFriend.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "ADMIN_LOGIN") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureAdminLogin.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "MENU") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureMenu.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "IMAGE") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureImage.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "TEXT") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureText.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "SCRIPT") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureScript.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "GALLERY" || itemType == "VOTING" || itemType == "E_COMMERCE_STORE") {
        itemType = "GALLERY"; // Setting itemType = gallery in order to be able to open voting or e_commerce settings from dashboard.
        widgetVisualSettingsWindow = createConfigureWindow({width:extendedWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureGallery.onBeforeShow({widgetId: settings.widgetId, showStoreSettings : settings.showStoreSettings});
        }
    } else if (itemType == "VIDEO") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureVideo.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "SLIDE_SHOW") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
        if (tab == configureItemSettings.settingsTab) {
            configureSlideShow.onBeforeShow({widgetId: settings.widgetId});
        }
    } else if (itemType == "LOGIN") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
    } else if (itemType == "GALLERY_DATA") {
        widgetVisualSettingsWindow = createConfigureWindow({width:standardWindowWidth, height:740});
    } else {
        alert("Unknown itemType.");
        return;
    }

    new ServiceCall().executeViaDwr("ConfigureItemSettingsService", "execute",
            settings.widgetId, settings.itemId, tab, itemType, false, function (response) {
        if (!isAnyWindowOpened()) {
            return;
        }

        widgetVisualSettingsWindow.setContent(response.html);

        if (tab == configureItemSettings.settingsTab) {
            if (itemType == "FORUM") {
                configureForum.onAfterShow();
            } else if (itemType == "BLOG") {
                configureBlog.onAfterShow();
            } else if (itemType == "REGISTRATION") {
                configureRegistration.onAfterShow();
            } else if (itemType == "CUSTOM_FORM") {
                configureObject.onAfterShow();
            } else if (itemType == "CONTACT_US") {
                configureObject.onAfterShow();
            } else if (itemType == "CHILD_SITE_REGISTRATION") {
                configureChildSiteRegistration.onAfterShow();
            } else if (itemType == "ADVANCED_SEARCH") {
                configureAdvancedSearch.onAfterShow();
            } else if (itemType == "MANAGE_VOTES") {
                configureManageVotes.onAfterShow();
            } else if (itemType == "BLOG_SUMMARY") {
                configureBlogSummary.onAfterShow();
            } else if (itemType == "PURCHASE_HISTORY") {
                configurePurchaseHistory.onAfterShow();
            } else if (itemType == "SHOPPING_CART") {
                configureShoppingCart.onAfterShow();
            } else if (itemType == "TELL_FRIEND") {
                configureTellFriend.onAfterShow();
            } else if (itemType == "ADMIN_LOGIN") {
                configureAdminLogin.onAfterShow();
            } else if (itemType == "MENU") {
                configureMenu.onAfterShow();
            } else if (itemType == "IMAGE") {
                configureImage.onAfterShow();
            } else if (itemType == "TEXT") {
                configureText.onAfterShow();
            } else if (itemType == "SCRIPT") {
                configureScript.onAfterShow();
            } else if (itemType == "GALLERY") {
                configureGallery.onAfterShow(response.configureGalleryResponse);
            } else if (itemType == "VIDEO") {
                configureVideo.onAfterShow();
            } else if (itemType == "SLIDE_SHOW") {
                configureSlideShow.onAfterShow();
            } else if (itemType == "LOGIN") {
                // Do nothing.
            } else if (itemType == "GALLERY_DATA") {
                // Do nothing.
            } else {
                alert("Unknown itemType.");
            }
        } else if (tab == configureItemSettings.fontsColorsTab) {
            configureFontsAndColors.onAfterShow();
        } else if (tab == configureItemSettings.borderTab) {
            configureBorder.onAfterShow();
        } else if (tab == configureItemSettings.backgroundTab) {
            configureBackground.onAfterShow();
        } else if (tab == configureItemSettings.itemSizeTab) {
            configureItemSize.onAfterShow();
        } else if (tab == configureItemSettings.accessibleTab) {
            configureAccessibility.onAfterShow();
        } else if (tab == configureItemSettings.blueprintPermissionsTab) {
            configureBlueprintItemPermissions.onAfterShow();
        }
    });
};

configureItemSettings.showSeparateTab = function(settings, tab, itemType) {
    var tabContentId = tab + "TabContent";

    createLoadingArea({element:$("#twoColumnsWindow_rightColumn")[0], text: "Loading...", color:"green", guaranteeVisibility:true});

    if (isCached(tabContentId)) {
        restoreFromCache(tabContentId);

        removeLoadingArea();
//        alert("Content has been restored from the cache!");
    } else {
        // Normal execution

        var configureObject;
        if (tab == configureItemSettings.settingsTab) {
            if (itemType == "FORUM") {
                configureForum.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "BLOG") {
                configureBlog.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "REGISTRATION") {
                configureRegistration.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "CUSTOM_FORM") {
                configureObject = new ConfigureCustomForms();
                configureObject.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "CONTACT_US") {
                configureObject = new ConfigureContactUses();
                configureObject.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "CHILD_SITE_REGISTRATION") {
                configureChildSiteRegistration.onBeforeShow({widgetId: settings.widgetId, showSecondTab: settings.showSecondTab});
            } else if (itemType == "ADVANCED_SEARCH") {
                configureAdvancedSearch.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "MANAGE_VOTES") {
                configureManageVotes.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "BLOG_SUMMARY") {
                configureBlogSummary.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "PURCHASE_HISTORY") {
                configurePurchaseHistory.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "SHOPPING_CART") {
                configureShoppingCart.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "TELL_FRIEND") {
                configureTellFriend.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "ADMIN_LOGIN") {
                configureAdminLogin.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "MENU") {
                configureMenu.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "IMAGE") {
                configureImage.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "TEXT") {
                configureText.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "SCRIPT") {
                configureScript.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "GALLERY" || itemType == "VOTING" || itemType == "E_COMMERCE_STORE") {
                itemType = "GALLERY"; // Setting itemType = gallery in order to be able to open voting or e_commerce settings from dashboard.
                configureGallery.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "VIDEO") {
                configureVideo.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "SLIDE_SHOW") {
                configureSlideShow.onBeforeShow({widgetId: settings.widgetId});
            } else if (itemType == "LOGIN") {
                // Do nothing.
            } else if (itemType == "GALLERY_DATA") {
                // Do nothing.
            } else {
                alert("Unknown itemType.");
                return;
            }
        }

        new ServiceCall().executeViaDwr("ConfigureItemSettingsService", "execute",
                settings.widgetId, settings.itemId, tab, itemType, true, function (response) {
            if (!isAnyWindowOpened()) {
                return;
            }

            cacheHtml(tabContentId, response.html);

            if (tab == configureItemSettings.settingsTab) {
                if (itemType == "FORUM") {
                    configureForum.onAfterShow();
                } else if (itemType == "BLOG") {
                    configureBlog.onAfterShow();
                } else if (itemType == "REGISTRATION") {
                    configureRegistration.onAfterShow();
                } else if (itemType == "CUSTOM_FORM") {
                    configureObject.onAfterShow();
                } else if (itemType == "CONTACT_US") {
                    configureObject.onAfterShow();
                } else if (itemType == "CHILD_SITE_REGISTRATION") {
                    configureChildSiteRegistration.onAfterShow();
                } else if (itemType == "ADVANCED_SEARCH") {
                    configureAdvancedSearch.onAfterShow();
                } else if (itemType == "MANAGE_VOTES") {
                    configureManageVotes.onAfterShow();
                } else if (itemType == "BLOG_SUMMARY") {
                    configureBlogSummary.onAfterShow();
                } else if (itemType == "PURCHASE_HISTORY") {
                    configurePurchaseHistory.onAfterShow();
                } else if (itemType == "SHOPPING_CART") {
                    configureShoppingCart.onAfterShow();
                } else if (itemType == "TELL_FRIEND") {
                    configureTellFriend.onAfterShow();
                } else if (itemType == "ADMIN_LOGIN") {
                    configureAdminLogin.onAfterShow();
                } else if (itemType == "MENU") {
                    configureMenu.onAfterShow();
                } else if (itemType == "IMAGE") {
                    configureImage.onAfterShow();
                } else if (itemType == "TEXT") {
                    configureText.onAfterShow();
                } else if (itemType == "SCRIPT") {
                    configureScript.onAfterShow();
                } else if (itemType == "GALLERY") {
                    configureGallery.onAfterShow(response.configureGalleryResponse);
                } else if (itemType == "VIDEO") {
                    configureVideo.onAfterShow();
                } else if (itemType == "SLIDE_SHOW") {
                    configureSlideShow.onAfterShow();
                } else if (itemType == "LOGIN") {
                    // Do nothing.
                } else if (itemType == "GALLERY_DATA") {
                    // Do nothing.
                } else {
                    alert("Unknown itemType.");
                }
            }

            if (tab == configureItemSettings.fontsColorsTab) {
                configureFontsAndColors.onAfterShow();
            } else if (tab == configureItemSettings.borderTab) {
                configureBorder.onAfterShow();
            } else if (tab == configureItemSettings.backgroundTab) {
                configureBackground.onAfterShow();
            } else if (tab == configureItemSettings.itemSizeTab) {
                configureItemSize.onAfterShow();
            } else if (tab == configureItemSettings.accessibleTab) {
                configureAccessibility.onAfterShow();
            } else if (tab == configureItemSettings.blueprintPermissionsTab) {
                configureBlueprintItemPermissions.onAfterShow();
            }


            removeLoadingArea();
        });
    }
};

configureItemSettings.closeItemSettings = function (functionalWidgetInfo, closeAfterSaving, widgetId, itemId, itemName) {
    if ($("#dashboardPage")[0]) {
        $("#itemName" + itemId).html(itemName);
        if (closeAfterSaving) {
            closeConfigureWidgetDiv();
        }
    } else {
        if (widgetId) {
            makePageDraftVisual(window.parent.getActivePage());
        }

        if (closeAfterSaving) {
            if (widgetId) {
                closeConfigureWidgetDivWithUpdate(functionalWidgetInfo);
            } else {
                closeConfigureWidgetDiv();
            }
        }
    }

    if (!closeAfterSaving) {
        getActiveWindow().enableContent();
        setWindowSettingsUnchanged();
    }
};
