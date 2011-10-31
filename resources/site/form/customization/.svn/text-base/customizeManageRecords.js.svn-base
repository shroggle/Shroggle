/*@author Balakirev Anatoliy*/
var customizeManageRecords = {
    showCustomizeManageRecordsWindow : function (formId) {
        var window = createConfigureWindow({width:750, height:650});
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ShowCustomizeManageRecordsWindowService", "execute", formId, function(response) {
            if (!isAnyWindowOpened()) {
                return;
            }

            window.setContent(response);

            //Accuring the error block
            errors = new Errors();
        });
    },

    save : function() {
        var request = {
            formId : $("#formId").val(),
            fields : collectFields()
        };
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        getActiveWindow().disableContentBeforeSaveSettings();
        serviceCall.executeViaDwr("SaveCustomizeManageRecordsService", "execute", request, function(response) {
            closeConfigureWidgetDiv();
            afterManageRecordsContentLoaded(response);
        });

        function collectFields() {
            var table = $("#customizeManageRecordsTable")[0];
            var trs = $(table).find("tr");
            var fields = [];
            var includedFieldsQuantity = 0;
            var MAX_FIELDS_QUANTITY = parseInt($("#MAX_FIELDS_QUANTITY").val());
            $(trs).each(function(index) {
                var include = $("#include" + this.id)[0].checked;
                if (include) {
                    includedFieldsQuantity++;
                }
                if (includedFieldsQuantity > MAX_FIELDS_QUANTITY) {
                    include = false;
                }
                fields.push({
                    position : index,
                    formItemId : this.id,
                    include : include
                });
            });
            return fields;
        }

    },

    checkIncludedFieldsQuantity : function(field) {
        if (field.checked) {
            var table = $("#customizeManageRecordsTable")[0];
            var trs = $(table).find("tr");
            var includedFieldsQuantity = 0;
            $(trs).each(function(index) {
                if ($("#include" + this.id)[0].checked) {
                    includedFieldsQuantity++;
                }
            });
            if (includedFieldsQuantity > 5) {
                field.checked = false;
                alert($("#tooManyFieldsErrorMessage").val());
            }
        }
    } ,

    showUnableToIncludeMessage : function(field) {
        field.checked = false;
        alert($("#unableToIncludeErrorMessage").val());
    }
};