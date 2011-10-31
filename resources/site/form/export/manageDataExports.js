var manageDataExports = {

    showManageDataExportsWindow : function (formId) {
        createConfigureWindow({width:950, height:650});
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ManageDataExportsService", "execute", formId, function(response) {
            if (!isAnyWindowOpened()) {
                return;
            }
            getActiveWindow().setContent(response);
        });
    },

    reloadManageDataExportsTable : function(formId) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        createLoadingArea({element: $("#manageFormExportTasksContainer")[0], text:"Reloading data, please wait...", color:"green", guaranteeVisibility:true});
        serviceCall.executeViaDwr("ReloadDataExportsTableService", "execute", formId, function(response) {
            removeLoadingArea();
            if (!isAnyWindowOpened()) {
                return;
            }
            $("#manageFormExportTasksContainer").html(response);
            getActiveWindow().resize();
        });
    }

};