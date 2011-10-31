var dataExportAndScheduler = {

    dataExportErrors : null,

    scheduleExportErrors : null,

    errors : null,

    showDataExportTab : function(tab) {
        $(".c1current").removeClass("c1current").addClass("c1");
        $(tab).removeClass("c1").addClass("c1current");

        $("#dataExportTabContent").show();
        $("#scheduledExportTabContent").hide();
        dataExportAndScheduler.errors = dataExportAndScheduler.dataExportErrors;
    },

    showScheduledExportTab : function(tab) {
        var hasErrors = dataExportAndScheduler.submit.checkDataExportErrors(dataExportAndScheduler.submit.createRequest());
        if (hasErrors) {
            return;
        }
        $(".c1current").removeClass("c1current").addClass("c1");
        $(tab).removeClass("c1").addClass("c1current");

        $("#dataExportTabContent").hide();
        $("#scheduledExportTabContent").show();
        dataExportAndScheduler.errors = dataExportAndScheduler.scheduleExportErrors;
    },

    showNextTab : function() {
        $("#scheduledExportTab").click();
    },

    changeDataFormat : function(format) {
        if (format == $("#CSV").val()) {
            disableFields(true);

            $("#csvDiv").show();
            $("#googleBaseDiv").hide();
            $("#downloadButton").show();
        } else {
            disableFields(false);

            $("#csvDiv").hide();
            $("#googleBaseDiv").show();
            $("#downloadButton").hide();
        }

        function disableFields(disabled) {
            $("#ourListOfOptionsRadio")[0].disabled = disabled;
            $("#ourListOfOptionsRadio")[0].checked = !disabled;

            $("#ownFtpAddressRadio")[0].disabled = !disabled;
            $("#ownFtpAddressRadio")[0].checked = disabled;

            $("#ourListOfOptionsSelect")[0].disabled = disabled;
            $("#googleBaseAccountUsername")[0].disabled = disabled;
            $("#googleBaseAccountPassword")[0].disabled = disabled;

            $("#ftpLogin")[0].disabled = !disabled;
            $("#ftpPassword")[0].disabled = !disabled;
            $("#ownFtpAddress")[0].disabled = !disabled;
        }
    },

    destinationRadioChanged : function(destination) {
        if (destination == $("#FTP").val()) {
            $("#ownFtpAddress")[0].disabled = false;
            $("#ownFtpAddress")[0].focus();
            $("#ourListOfOptionsSelect")[0].disabled = true;
            $("#googleBaseAccountUsername")[0].disabled = true;
            $("#googleBaseAccountPassword")[0].disabled = true;
            $("#ftpLogin")[0].disabled = false;
            $("#ftpPassword")[0].disabled = false;
        } else {
            $("#ownFtpAddress")[0].disabled = true;
            $("#ourListOfOptionsSelect")[0].disabled = false;
            $("#googleBaseAccountUsername")[0].disabled = false;
            $("#googleBaseAccountPassword")[0].disabled = false;
            $("#ftpLogin")[0].disabled = true;
            $("#ftpPassword")[0].disabled = true;
        }
    },

    show : function(formId, formExportTaskId) {
        var window = createConfigureWindow({width:1080, height:650});
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ShowDataExportAndScheduleService", "show", formId, formExportTaskId, function(response) {
            if (!isAnyWindowOpened()) {
                return;
            }
            window.setContent(response);

            dataExportAndScheduler.dataExportErrors = new Errors({}, "errors");
            dataExportAndScheduler.scheduleExportErrors = new Errors({}, "scheduleExportErrors");
            dataExportAndScheduler.errors = dataExportAndScheduler.dataExportErrors;
        });
    },

    save : function() {
        var request = dataExportAndScheduler.submit.createRequest();

        // We don`t have to check data export errors here. We check it when we changing tab.
        var hasErrors = dataExportAndScheduler.submit.checkScheduleExportErrors(request);
        if (hasErrors) {
            return;
        }

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.addExceptionHandler(
                "FormExportTaskNameNotUniqueException",
                dataExportAndScheduler.submit.showNameNotUniqueException);
        serviceCall.executeViaDwr("SaveDataExportAndScheduleService", "execute", request, function() {
            closeConfigureWidgetDiv();
            manageDataExports.reloadManageDataExportsTable(request.formId);
        });
    },

    deleteExportTask : function(formExportTaskId) {
        if (!confirm($("#confirmDeleteExport").val())) {
            return;
        }
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        createLoadingArea({element: $("#manageFormExportTasksContainer")[0], text:"Removing, please wait...", color:"green", guaranteeVisibility:true});
        serviceCall.executeViaDwr("DeleteDataExportTaskService", "execute", formExportTaskId, function() {
            removeLoadingArea();
            $("#" + formExportTaskId).remove();
            getActiveWindow().resize();
        });
    },

    download : function(formExportTaskId) {
        window.location = "/csvFilesGetter.action?formExportTaskId=" + formExportTaskId;
    },

    submit : {

        createRequest : function() {
            var request = {
                formExportTaskId : parseInt($("#formExportTaskId").val()),
                formId : parseInt($("#formId").val()),
                filterId : parseInt($("#formFilterId").val()),
                googleBaseDataExport : {
                    galleryId : parseInt($("#galleryId").val()),
                    formItemIdForTitle : parseInt($("#formItemIdForTitle").val()),
                    formItemIdForDescription : parseInt($("#formItemIdForDescription").val()),
                    googleBaseAccountUsername : $("#googleBaseAccountUsername").val(),
                    googleBaseAccountPassword : $("#googleBaseAccountPassword").val()
                },
                destination : $("#ourListOfOptionsRadio")[0].checked ? $("#ourListOfOptionsSelect").val() : $("#ownFtpAddressRadio").val(),
                dataFormat : $("#dataFormat").val(),
                frequency : $("#frequency").val(),
                name : $("#dataExportName").val(),
                ownFtpAddress : $("#ownFtpAddress").val(),
                startDate : $("#startDate").val(),
                ftpLogin : $("#ftpLogin").val(),
                ftpPassword:$("#ftpPassword").val(),
                fields : collectFields()
            };
            request.filterId = request.galleryId < 0 ? null : request.galleryId;
            request.filterId = request.formItemIdForTitle < 0 ? null : request.formItemIdForTitle;
            request.filterId = request.formItemIdForDescription < 0 ? null : request.formItemIdForDescription;
            request.filterId = request.filterId < 0 ? null : request.filterId;
            return request;

            function collectFields() {
                var table = $("#customizeDataExportTable")[0];
                var trs = $(table).find("tr");
                var fields = [];
                $(trs).each(function(index) {
                    fields.push({
                        position : index,
                        formItemId : this.id,
                        customizeHeader : $("#customizeHeader" + this.id).val(),
                        include : $("#include" + this.id)[0].checked
                    });
                });
                return fields;
            }
        },

        checkDataExportErrors : function(request) {
            var errors = dataExportAndScheduler.errors;
            errors.clear();

            if (request.name.length == 0) {
                dataExportAndScheduler.submit.showNameNotUniqueException();
            }

            if (request.dataFormat != $("#CSV").val()) {// Checking scheduling errors for Google Base format.
                if (parseInt(request.googleBaseDataExport.galleryId) <= 0) {
                    errors.set("WRONG_GALLERY_ID", $("#selectGallery").val(), [$("#galleryId")[0]]);
                }
                if (parseInt(request.googleBaseDataExport.formItemIdForTitle) <= 0) {
                    errors.set("WRONG_TITLE_ID", $("#selectTitle").val(), [$("#formItemIdForTitle")[0]]);
                }
                if (parseInt(request.googleBaseDataExport.formItemIdForDescription) <= 0) {
                    errors.set("WRONG_DESCRIPTION_ID", $("#selectDescription").val(), [$("#formItemIdForDescription")[0]]);
                }
            }
            return errors.hasErrors();
        },

        checkScheduleExportErrors : function(request) {
            var errors = dataExportAndScheduler.errors;
            errors.clear();

            if (request.dataFormat == $("#CSV").val()) {// Checking scheduling errors for CSV format.
                if (request.ownFtpAddress.length == 0) {
                    errors.set("WRONG_FTP", $("#ftpCantBeEmpty").val(), [$("#ownFtpAddress")[0]]);
                }
            } else {// Checking scheduling errors for Google Base format.
                if (request.googleBaseDataExport.googleBaseAccountUsername.length == 0) {
                    errors.set("WRONG_GOOGLE_USER_NAME", $("#googleUsernameCantBeEmpty").val(), [$("#googleBaseAccountUsername")[0]]);
                }
                if (request.googleBaseDataExport.googleBaseAccountPassword.length == 0) {
                    errors.set("WRONG_GOOGLE_PASSWORD", $("#googlePasswordCantBeEmpty").val(), [$("#googleBaseAccountPassword")[0]]);
                }
            }

            if (!parseDate(request.startDate)) { // Checking start date
                errors.set("WRONG_START_DATE", $("#wrongStartDate").val(), [$("#startDate")[0]]);
            }
            return errors.hasErrors();
        },

        showNameNotUniqueException : function() {
            dataExportAndScheduler.errors.set("WRONG_NAME", $("#inputCorrectName").val(), [$("#dataExportName")[0]]);
        }
    }


};