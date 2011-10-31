function isIncludesVotingModule() {
    return document.getElementById("includesVotingModule").checked;
}

function disableVotingArea(disabled) {
    disableControl($("#votingSettingsTab"), disabled);
    if (!disabled) {
        includeManageYourVotesLink(document.getElementById("includeManageYourVotesLink").checked);
        disableStartEndDate(document.getElementById("limited").checked);
    }
    disableVotingAudioVideoArea(disabled);
}

function includeManageYourVotesLink(checked) {
    document.getElementById("showPagesFromAllAvailableSites").disabled = !checked;
    document.getElementById("selectAManageYourVotesPage").disabled = !checked;
}

function disableStartEndDate(checked) {
    document.getElementById("startDateText").disabled = !checked;
    document.getElementById("endDateText").disabled = !checked;
}

function disableVotingAudioVideoArea(disabled) {
    var disableAudioVideoArea = true, galleryItems = configureGalleryGetGalleryItems();
    for (var i in galleryItems) {
        if (galleryItems[i].display && isAudioOrVideo(galleryItems[i])) {
            disableAudioVideoArea = false;
            break;
        }
    }
    document.getElementById("minimumNumberOfMediaItemsPlayed").disabled = disabled;
    document.getElementById("minimumNumberAppliesToCurrentFilterOnly").disabled = disabled;
    document.getElementById("minimumPercentageOfTotalPlayed").disabled = disabled;
    disableControl($("#precautionsAgainstPollFraud")[0], disableAudioVideoArea);
    //document.getElementById("precautionsAgainstPollFraud").style.display = disableAudioVideoArea ? "none" : "block";

    function isAudioOrVideo(item) {
        var votingAudioVideoTypes = document.getElementById("votingAudioVideoTypes").value.split(";");
        for (var i in votingAudioVideoTypes) {
            if (item.type == votingAudioVideoTypes[i]) {
                return true;
            }
        }
        return false;
    }
}

function showMoreInfoForNetworkSites() {
    var moreInfoForNetworkSitesWindow = createConfigureWindow({width:400, height:100});
    moreInfoForNetworkSitesWindow.setContent(document.getElementById("moreInfoTextDiv").innerHTML);
}

function editVotingLinkLocation() {
    showConfigureGalleryDataItems();
}

function createVoteSettings() {
    var voteSettings = new Object();
    voteSettings.displayVote = document.getElementById("displayVoteDataPublicly").checked;
    voteSettings.displayComments = document.getElementById("displaySiteVisitorCommentsPublicly").checked;
    voteSettings.durationOfVoteLimited = document.getElementById("limited").checked;
    voteSettings.includeLinkToManageYourVotes = document.getElementById("includeManageYourVotesLink").checked;
    voteSettings.minimumNumberAppliesToCurrentFilter = document.getElementById("minimumNumberAppliesToCurrentFilterOnly").checked;
    voteSettings.showAllAvailablePages = document.getElementById("showPagesFromAllAvailableSites").checked;
    voteSettings.minimumNumberOfMediaItemsPlayed = document.getElementById("minimumNumberOfMediaItemsPlayed").value;
    voteSettings.minimumPercentageOfTotalPlayed = document.getElementById("minimumPercentageOfTotalPlayed").value;
    voteSettings.manageYourVotesCrossWidgetId = document.getElementById("selectAManageYourVotesPage").value;
    voteSettings.registrationFormIdForVoters = document.getElementById("selectARegistrationFormForVoters").value;
    voteSettings.startDate = parseDate(document.getElementById("startDateText").value);
    voteSettings.endDate = parseDate(document.getElementById("endDateText").value);

    /*----------------------------------------vote stars, vote links settings-----------------------------------------*/
    voteSettings.votingStarsPosition = window.voteStars.position;
    voteSettings.votingStarsRow = window.voteStars.row;
    voteSettings.votingStarsAlign = window.voteStars.align;
    voteSettings.votingStarsColumn = window.voteStars.column;
    voteSettings.votingStarsName = window.voteStars.name;
    voteSettings.votingTextLinksPosition = window.voteLinks.position;
    voteSettings.votingTextLinksRow = window.voteLinks.row;
    voteSettings.votingTextLinksAlign = window.voteLinks.align;
    voteSettings.votingTextLinksColumn = window.voteLinks.column;
    voteSettings.votingTextLinksName = window.voteLinks.name;
    /*----------------------------------------vote stars, vote links settings-----------------------------------------*/
    return voteSettings;
}

function checkVotingDates(startDate, endDate) {
    var currentDate = new Date();
    currentDate.setHours(0);
    currentDate.setMinutes(0);
    currentDate.setSeconds(0);
    currentDate.setMilliseconds(0);
    if (endDate == null) {
        errors.set("WRONG_END_DATE",
                document.getElementById("wrongEndDate").value,
                [document.getElementById("endDateText")]);
    } else if (endDate.getTime() < startDate.getTime()) {
        errors.set("END_DATE_BEFORE_START_DATE",
                document.getElementById("endBeforeStart").value,
                [document.getElementById("endDateText")]);
    } else if (endDate.getTime() < currentDate.getTime()) {
        errors.set("END_DATE_BEFORE_CURRENT_DATE",
                document.getElementById("endDatePassed").value,
                [document.getElementById("endDateText")]);
    }
    if (startDate == null) {
        errors.set("WRONG_START_DATE",
                document.getElementById("wrongStartDate").value,
                [document.getElementById("startDateText")]);
    }
}

function showAllPages(showAll) {
    var serviceCall = new ServiceCall();

    document.getElementById("selectAManageYourVotesPage").disabled = true;
    serviceCall.executeViaDwr("ConfigureGalleryService", "getManageVotesForVotingSettings", document.getElementById("selectAManageYourVotesPage").value, document.getElementById("siteId").value, showAll, function(response) {
        document.getElementById("manageVotesSelect").innerHTML = response;
        document.getElementById("selectAManageYourVotesPage").disabled = false;
    });
}