var keywordManager = {};

keywordManager.show = function (siteId) {
    var keywordManagerWindow = createConfigureWindow({width:900, height:600});

    new ServiceCall().executeViaDwr("ShowKeywordManagerService", "execute", siteId, function (response) {
        if (response) {
            keywordManagerWindow.setContent(response);
        }
    });
};

keywordManager.updateTable = function () {
    var pageId = $("#pageNameSelect > option:selected").val();

    $("#processingPageDiv").css("visibility", "visible");
    disableControl($("#pageNameSelect")[0], true);
    new ServiceCall().executeViaDwr("ShowKeywordManagerTableService", "execute", pageId, function (response) {
        if (response) {
            $("#keywordManagerMainDiv").html(response);
            $("#processingPageDiv").css("visibility", "hidden");
            disableControl($("#pageNameSelect")[0], false);
        }
    });
};

keywordManager.ifEmptyKeyphrase = function() {
    if ($("#primaryKeyphrase").val().length == 0) {
        $(".keyphrasePresent").hide();
        $(".keyphraseNotPresent").hide();
        $(".keyphraseUndefined").css('display', 'inline');

        $(".keyphraseDensity").html("Density: Enter keyphrase");

        $("#processingKeyphraseDiv").css("visibility", "hidden");

        $("#updateKeyphraseLink").hide();

        return true;
    }

    return false;
};

keywordManager.updateKeyphrase = function () {
    var pageId = $("#pageNameSelect > option:selected").val();
    var keyphrase = $("#primaryKeyphrase").val();

    if (keywordManager.ifEmptyKeyphrase()) {
        return;
    }

    $("#updateKeyphraseLink").hide();
    $("#processingKeyphraseDiv").css("visibility", "visible");

    new ServiceCall().executeViaDwr("UpdateKeyphraseService", "execute", pageId, keyphrase, function (response) {
        if (response) {
            $(response).each(function () {
                if (this.present) {
                    $("#" + this.seoTerm + "KeyphrasePresent").css('display', 'inline');
                    $("#" + this.seoTerm + "KeyphraseNotPresent").hide();
                    $("#" + this.seoTerm + "KeyphraseUndefined").hide();
                } else {
                    $("#" + this.seoTerm + "KeyphraseNotPresent").css('display', 'inline');
                    $("#" + this.seoTerm + "KeyphrasePresent").hide();
                    $("#" + this.seoTerm + "KeyphraseUndefined").hide();
                }

                $("#" + this.seoTerm + "KeyphraseDensity").html("Density: " + this.density);
            });
            $("#processingKeyphraseDiv").css("visibility", "hidden");
        }
    });
};

keywordManager.showMoreInfoWindow = function () {
    var keywordManagerMoreInfoWindow = createConfigureWindow({width:700, height:600});
    keywordManagerMoreInfoWindow.setContent($("#keywordManagerMoreInfo").html());
};