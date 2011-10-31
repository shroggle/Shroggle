var publishBlueprint = {

    showPublishWindow : function(siteId, hasBeenPublished) {
        if (hasBeenPublished) {
            alert($("#hasBeenPublished").val());
            return;
        }
        publishBlueprint.showPublishWindowInternal(siteId, false);
    },

    showPublishWindowInActivationMode : function(siteId) {
        publishBlueprint.showPublishWindowInternal(siteId, true);
    },

    publish : function() {
        publishBlueprint.publishOrActivate("PublishBlueprintService");
    },

    activate : function() {
        publishBlueprint.publishOrActivate("ActivateBlueprintService");
    },

    /*------------------------------------------------Internal methods------------------------------------------------*/
    showPublishWindowInternal : function(siteId, activationMode) {
        createConfigureWindow({width:500, height:500});
        new ServiceCall().executeViaJQuery("/showPublishBlueprintWindow.action", {siteId : siteId, activationMode : activationMode}, function(response) {
            var window = getActiveWindow();
            if (window) {
                window.setContent(response);
            }
        });
    },

    createPublishingRequest : function() {
        return {
            siteId : $("#siteId").val(),
            description : $("#blueprintDescription").val(),
            pageScreenShots : getPageScreenShotsId(),
            blueprintCategory : $("#businessCategory").val()
        };

        function getPageScreenShotsId() {
            var pageIds = $("#pageIds").val().split(";");
            var pageScreenShots = {};
            $(pageIds).each(function() {
                var screenShotId = $("#selectedScreenShotId" + this).val();
                if (parseInt(screenShotId)) {
                    pageScreenShots[this] = screenShotId;
                }
            });
            return pageScreenShots;
        }
    },

    publishOrActivate : function(service) {
        var request = publishBlueprint.createPublishingRequest();
        getActiveWindow().disableContentBeforeSaveSettings();
        new ServiceCall().executeViaDwr(service, "execute", request.siteId, request.description, request.pageScreenShots,
                request.blueprintCategory, function() {
            window.location = "/account/dashboard.action";
        });
    }
    /*------------------------------------------------Internal methods------------------------------------------------*/
};