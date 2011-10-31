window.selectSite = {

    show: function (onSave) {
        window.selectSite.onSave = onSave;

        var selectSiteWindow = createConfigureWindow({
            width: 360,
            height: 300
        });

        var serviceCall = new ServiceCall();
        serviceCall.executeViaDwr("SelectSiteService", "execute", function (html) {
            selectSiteWindow.setContent(html);
        });
    },

    save: function () {
        var siteIds = document.getElementById("selectSiteIds");
        if (siteIds.selectedIndex < 0) {
            var errors = new Errors();
            errors.set("siteId", "New item host site should be selected.");

            return;
        }

        var siteId = siteIds.options[siteIds.selectedIndex].value;

        closeConfigureWidgetDiv();

        if (window.selectSite.onSave) {
            window.selectSite.onSave(siteId);
        }
    }

};