/**
 * Special object, it contants all players on page, that need for
 * correct check playing time.
 */
window.videoPlayers = {

    oldunloadInit: false,
    /**
     * Why we use old onUnLoad event and add boolean flag, because
     * onUnLoad can undefine and in this case we don't know that it init.
     */
    oldunload: undefined,
    states: {},

    timeHanlder: function (player) {
        var playerState = window.videoPlayers.states[player.id];
        if (!playerState.runtime.startPosition) {
            playerState.runtime.startPosition = player.position;
        }
        playerState.runtime.finishPosition = player.position;
        playerState.runtime.total = player.duration;
    }

};

function addVideoPlayerListeners(settings) {
    var player = document.getElementById(settings.videoPlayerId);
    if (player) {
        if (player.addModelListener) {
            window.videoPlayers.states[settings.videoPlayerId] = settings;
            player.addModelListener("TIME", "window.videoPlayers.timeHanlder");
            player.addModelListener("STATE", "videoPlayerStateHandler");
            if (!window.videoPlayers.oldunloadInit) {
                window.videoPlayers.oldunloadInit = true;
                window.videoPlayers.oldunload = window.onunload;
            }
            window.onunload = function () {
                player.sendEvent("STOP", "true");
                if (window.videoPlayers.oldunload) window.videoPlayers.oldunload();
            };
            return;
        }
    }
    window.setTimeout("addVideoPlayerListeners({videoPlayerId: '"
            + settings.videoPlayerId + "', galleryId: " + settings.galleryId + ", filledFormId: "
            + settings.filledFormId + "})", 10);
}

function videoPlayerStateHandler(player) {
    var playerState = getPlayerState(player.id);
    if (player.newstate == "PLAYING") {
        playerState.runtime = new Object();
    } else {
        var request = getVideoRangeEdit(playerState);
        if (request) {
            new ServiceCall().executeViaDwr("AddVideoRangeService", "execute", request);
        }
    }
}

function getPlayerState(playerId) {
    return window.videoPlayers.states[playerId];
}

function getVideoRangeEdit(playerState) {
    if (!playerState){
        return null;
    }

    if (playerState.runtime) {
        var request = {
            filledFormId: playerState.filledFormId,
            galleryId: playerState.galleryId,
            start: playerState.runtime.startPosition,
            finish: playerState.runtime.finishPosition,
            total: playerState.runtime.total
        };
        playerState.runtime.startPosition = playerState.runtime.finishPosition;
        if (request.filledFormId && request.galleryId && request.start && request.finish && request.total) {
            return request;
        }
    }
    return null;
}