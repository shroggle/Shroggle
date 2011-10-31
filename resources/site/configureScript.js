var configureScript = {

    onBeforeShow: function (settings) {
        configureScript.settings = settings;
    },

    onAfterShow: function () {
        if (!isAnyWindowOpened()) {
            return;
        }

        if ($("#siteOnItemRightType").val() == "READ") {
            configureScript.disable();
        }
    },

    save: function (closeAfterSaving) {
        var request = {
            widgetId: configureScript.settings.widgetId,
            scriptItemId: $("#selectedScriptItemId").val(),
            text: $("#configureScriptText").val(),
            description: $("#configureScriptHeader").html(),
            showDescription: $("#showconfigureScriptHeader").val(),
            name: $("#configureScriptName").val()
        };

        getActiveWindow().disableContentBeforeSaveSettings();
        new ServiceCall().executeViaDwr("SaveScriptService", "execute", request, function (response) {
            if (closeAfterSaving) {
                if (configureScript.settings.widgetId) {
                    closeConfigureWidgetDivWithUpdate(response);
                } else {
                    closeConfigureWidgetDiv();
                }
            }

            if (!closeAfterSaving) {
                updateWidgetInfo(response);
                getActiveWindow().enableContent();
                setWindowSettingsUnchanged();
            }
        });
    },

    disable: function () {
        disableControl($("#configureScriptName")[0]);
        disableControl($("#scriptHeader")[0]);
        disableControl($("#configureScriptText")[0]);

        $("#windowSave", $("#configureScriptButtons")[0]).hide();
        $("#windowApply", $("#configureScriptButtons")[0]).hide();
        $("#windowCancel", $("#configureScriptButtons")[0]).val("Close");

        $("#scriptReadOnlyMessage").show();
    }

};
