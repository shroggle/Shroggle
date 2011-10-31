var configureTaxRates = {

    show : function(settings) {
        var configureTaxRatesWindow = createConfigureWindow({width:750, height:650});

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.executeViaDwr("ConfigureTaxRatesService", "execute", settings.widgetId, settings.itemId, function (response) {
            if (!isAnyWindowOpened()) {
                return;
            }

            configureTaxRatesWindow.setContent(response);

            if ($("#siteOnItemRightType").val() == "READ") {
                disableControls($("#windowOneColumn"));
            }

            //Accuring the error block
            errors = new Errors();
        });
    },

    save : function() {
        var request = {
            name : $("#newTaxRatesName").val(),
            itemId : $("#itemId").val(),
            statesTaxes : getIncludedStatesTaxes()
        };

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        serviceCall.addExceptionHandler(
                "com.shroggle.exception.TaxRatesNameNotUnique",
                function() {
                    errors.set("NOT_UNIQUE_NAME",
                            $("#nameNotUnique").val(),
                            [document.getElementById("newTaxRatesName")]);
                });
        getActiveWindow().disableContentBeforeSaveSettings();
        serviceCall.executeViaDwr("SaveTaxRatesService", "execute", request, function () {
            closeConfigureWidgetDiv();
            try {
                $("#itemName" + request.itemId).html(request.name);
            } catch(ex) {
                // ignore.
            }
        });

        function getIncludedStatesTaxes() {
            var includedStatesTaxes = {};
            $($("#taxRatesHolderBody").find("tr")).each(function() {
                if ($(this).find("[type=checkbox]")[0].checked) {
                    var stateName = $(this).attr("stateName");
                    var tax = $("#taxRate" + stateName).val();
                    tax = isDouble(tax) ? tax : null;
                    includedStatesTaxes[stateName] = tax;
                }
            });
            return includedStatesTaxes;
        }
    },

    disableTaxRateField : function(checkbox) {
        var taxRateField = configureTaxRates.getTaxRateField($(checkbox).parents("tr").attr("stateName"));
        taxRateField.disabled = !checkbox.checked;
    },

    getTaxRateField : function(stateName) {
        return $("#taxRate" + stateName)[0];
    }

};