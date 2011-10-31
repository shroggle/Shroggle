function selectWinner(img, colorCode) {
    if ($(img).hasClass("winner")) {
        return;
    }

    var winnerTableDiv = $(".winnerTableDiv", $(img).parents(".manageVotesBlock"));
    var manageVotesId = $(".manageVotesId", $(img).parents(".manageVotesBlock")).val();
    var siteShowOption = $(".siteShowOption", $(img).parents(".manageVotesBlock")).val();

    //Remove previous winner in this gallery if exists
    var previousWinner = $(".winner", $(img).parents("table:first"));
    removePreviousWinner(previousWinner);

    //Add a new winner
    img.src = "/images/winnerIcon.gif";
    $(img).addClass("winner");

    //Add background color to table row
    $(img).parents("tr:first").find("td").css({backgroundColor:colorCode});

    //Pick a winner server request
    var galleryId = $(img).parents("table:first").attr("galleryId");
    var voteId = $(img).parents("tr:first").attr("voteId");

    new ServiceCall().executeViaDwr("PickWinnerService", "execute", galleryId, voteId, manageVotesId, siteShowOption, function (response) {
        winnerTableDiv.html(response);
    });
}

function removePreviousWinner(winner) {
    if (winner[0]) {
        winner[0].src = "/images/winnerIcon-deselect.gif";
        winner.removeClass("winner");
        winner.parents("tr:first").find("td").css({backgroundColor:""});
    }
}

function removeWinner(img, voteId) {
    var winnerTableDiv = $(".winnerTableDiv", $(img).parents(".manageVotesBlock"));
    var manageVotesId = $(".manageVotesId", $(img).parents(".manageVotesBlock")).val();
    var siteShowOption = $(".siteShowOption", $(img).parents(".manageVotesBlock")).val();

    new ServiceCall().executeViaDwr("RemoveWinnerService", "execute", voteId, manageVotesId, siteShowOption, function (response) {
        winnerTableDiv.html(response);

        //Remove previous winner
        removePreviousWinner($(".winner", $("tr[voteId=" + voteId + "]")));
    });
}