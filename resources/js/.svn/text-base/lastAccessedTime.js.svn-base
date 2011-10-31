var lastAccessedTime = {

    init : function() {
        lastAccessedTime.update("");
    },

    update : function (lastAccessByThisServiceMd5) {
        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);

        serviceCall.executeViaDwr("CheckLoginedUserService", "execute", lastAccessByThisServiceMd5, function(response) {
            setTimeout(function () {
                lastAccessedTime.update(response.currentAccessTimeMd5);
            }, response.delay);
        });
    }

};
