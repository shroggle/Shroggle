var configureBlueprintPagePermissions = {};

configureBlueprintPagePermissions.save = function(closeAfterSaving) {
    var blueprintRequired = $("#pageIsRequired")[0].checked;
    var blueprintNotEditable = $("#pageUrlAndNameNotEditable")[0].checked;
    var blueprintLocked = $("#pageIsLocked")[0].checked;

    var serviceCall = new ServiceCall();

    getActiveWindow().disableContentBeforeSaveSettings();
    serviceCall.executeViaDwr("SaveBlueprintPagePermissionsService", "execute", $("#pageToEditId").val(),
            blueprintRequired, blueprintNotEditable, blueprintLocked, function (response) {
        configurePageSettings.onAfterSave(response, $("#pageToEditId").val(), closeAfterSaving);
    });
};