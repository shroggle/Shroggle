function vote() {
    var compositId = this.attributes['compositId'].value;
    var voteValue = this.attributes['voteValue'].value;
    var siteId = this.attributes['siteId'].value;
    var galleryId = this.attributes['galleryId'].value;
    var filledFormId = this.attributes['filledFormId'].value;
    var disableVotingVar = this.attributes['disableVoting'].value;
    var voteId = this.attributes['voteId'].value;
    var widgetId = this.attributes['widgetId'].value;

    if (disableVotingVar == "true") {
        //Disable voting if there is no forceEnabling(in fact this one disables re-voting on gallery voting and enables on manage votes).
        disableVoting(true);
    }

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler(
            LoginInAccount.EXCEPTION_CLASS,
            showVotingRegistrationBlock);
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.IncorrectVoteValueException",
            handleIncorrectVoteValueException);
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.NotEnoughWatchedFilesException",
            handleNotEnoughWatchedFilesException);
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.NotEnoughWatchedPercentageOfCurrentFileException",
            handleNotEnoughWatchedPercentageOfCurrentFileException);

    var videoRangeEdit = getVideoRangeEdit(getPlayerState(this.attributes['videoPlayerId'].value));
    var request = {
        voteValue: voteValue,
        siteId: siteId,
        galleryId: galleryId,
        filledFormId: filledFormId,
        voteId: voteId,
        videoRangeEdit: videoRangeEdit
    };
    var stars = this;
    serviceCall.executeViaDwr("VoteService", "vote", request, function(voteId) {
        $(getVotingElementsById(compositId)).each(function() {
            $(this).attr("voteId", voteId);
        });
        //$(stars).attr("voteId", voteId);// attributes['voteId'].value = voteId;
        //After voting from Manage Votes. jQuery will ignroe call if would be not able to find the field.
        var afterVotingMessage = $("#widget" + widgetId).find("#afterVoteMessageBlock" + galleryId)[0];
        addSlidingTimeoutEvent(afterVotingMessage, 2500);
    });

    /*-------------------------------------------internal functions-------------------------------------------*/

    function showVotingRegistrationBlock() {
        showRegistrationBlockIfConfirm(widgetId);
        clearVoting();
    }

    function clearVoting() {
        disableVoting(false);
        getVotingElementsById(compositId).rating('drain');
        getVotingElementsById(compositId).rating('select', -1);
    }

    function disableVoting(disable) {
        getVotingElementsById(compositId).rating(disable ? "disable" : "enable");
    }

    function handleIncorrectVoteValueException() {
        clearVoting();
        alert(document.getElementById("enterCorrectVoteValue" + widgetId).value);
    }

    function handleNotEnoughWatchedPercentageOfCurrentFileException(exception) {
        clearVoting();
        alert(exception.message);
    }

    function handleNotEnoughWatchedFilesException() {
        clearVoting();
        alert(document.getElementById("notEnoughWatchedFiles" + widgetId).value);
    }

    /*-------------------------------------------internal functions-------------------------------------------*/
}

function createVotingStars(compositId) {
    getVotingElementsById(compositId).rating();
}

function getVotingElementsById(compositId) {
    return $('input[type=radio]', $("#votingStars" + compositId));
}
