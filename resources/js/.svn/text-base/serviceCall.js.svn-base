/**
 * @author Stasuk Artem
 */

/**
 * This service can find exception
 *
 * Constructor. Service call use predefined listeners in
 * defaultExceptionListeners variable you can change it.
 */
var DWR_DEFAULT_TIMEOUT = 60000; // value in ms.
var defaultExceptionPackage = "com.shroggle.exception.";// Default package for all our exceptions.

function ServiceCall() {
    if (!dwr || !dwr.engine) {
        alert("Before use service call define dwr engine!");
        return;
    }
    this.exceptionListeners = new Object();
    this.EXCEPTION_ACTION_ALERT_MESSAGE = function (exception) {
        alert(exception.message);
    };

    // We do not initialize login exception in default service call.
    if (defaultServiceCall != undefined) {
        this.addExceptionHandler(
                LoginInAccount.EXCEPTION_CLASS,
                LoginInAccount.EXCEPTION_ACTION);
        this.addExceptionHandler(
                "WidgetNotFoundException",
                function() {
                    location.reload();
                });
    }
}

ServiceCall.prototype.addExceptionHandler = function (exceptionClass, exceptionAction) {
    if (!exceptionClass || !exceptionAction) {
        alert("Can't add exception to service call with null class or action!" + exceptionClass);
        return;
    }
    exceptionClass = normalizeExceptionClass();

    if ($.isArray(exceptionClass)) {
        var self = this;
        $(exceptionClass).each(function () {
            self.exceptionListeners[this] = exceptionAction;
        });
    } else {
        this.exceptionListeners[exceptionClass] = exceptionAction;
    }

    function normalizeExceptionClass() {
        if ($.isArray(exceptionClass)) {
            var newArray = [];
            $(exceptionClass).each(function () {
                newArray.push(normalizeExceptionClassInternal(this));
            });
            return newArray;
        } else {
            return normalizeExceptionClassInternal(exceptionClass);
        }

        function normalizeExceptionClassInternal(exceptionClass) {
            if (!exceptionClass.contains("\\.")) {
                return (defaultExceptionPackage + exceptionClass);
            } else {
                return exceptionClass;
            }
        }
    }
};

ServiceCall.prototype.executeViaDwr = function (className, methodName, varargs) {
    if (!className || !methodName) {
        alert("Can't call service call with null action!");
    }

    // Set default timeout.
    dwr.engine.setTimeout(DWR_DEFAULT_TIMEOUT);

    // Start batch
    dwr.engine.beginBatch();

    // Call client services
    execute(arguments);

    // Process end batch
    var tempExceptionListeners = this.exceptionListeners;
    var endBatchHandler = function (errorString, exception) {
        var exceptionAction = tempExceptionListeners[exception.javaClassName || exception.name]
                || defaultServiceCall.exceptionListeners[exception.javaClassName || exception.name];
        try {
            //If we got an exception on page then we should enable window content(It will be enabled only if was disabled previously).
            //Because it could have been disabled on window settings save.
            if (isAnyWindowOpened()) {
                getActiveWindow().enableContentAfterException();
            }
        } catch(ex) {
        }

        if (exceptionAction) {
            exceptionAction(exception);
        } else {
            alert("Unknown error on page: " + errorString + " " + exception);
        }
    };
    dwr.engine.endBatch({
        warningHandler: endBatchHandler,
        errorHandler: endBatchHandler
    });

    function execute(args) {
        var newArgs = ["/dwr"];
        for (var i = 0; i < args.length; i++) {
            newArgs.push(args[i]);
        }
        if (typeof(newArgs[newArgs.length - 1]) != "function") {
            newArgs.push(function() {
            });
        }
        dwr.engine._execute.apply(null, newArgs);
    }
};

ServiceCall.prototype.executeViaJQuery = function (url, data, successHandler) {
    // Set default timeout.
    //dwr.engine.setTimeout(DWR_DEFAULT_TIMEOUT);//todo. Investigate timeout in jQuery. Tolik
    var tempExceptionListeners = this.exceptionListeners;
    $.ajax({
        url: url,
        data: data,
        success: successHandler,

        error: function(request) {
            var exceptionClassName = request.getResponseHeader("exception");

            var exceptionAction = tempExceptionListeners[exceptionClassName]
                    || defaultServiceCall.exceptionListeners[exceptionClassName];
            try {
                //If we got an exception on page then we should enable window content(It will be enabled only if was disabled previously).
                //Because it could have been disabled on window settings save.
                if (isAnyWindowOpened()) {
                    getActiveWindow().enableContentAfterException();
                }
            } catch(ex) {
            }
            if (exceptionAction) {
                exceptionAction(exceptionClassName);
            } else {
                alert("Unknown error on page: " + exceptionClassName);
            }
        }
    });
};

/**
 * Use it for define global exception listeners.
 * Use international variable with field disconnect fo define custom disconnect message.
 * Example international.disconnect = "Sorry!";
 */
var defaultServiceCall = new ServiceCall();

// Add support disconnect exception
defaultServiceCall.addExceptionHandler("dwr.engine.timeout", function () {
    alert("Your internet connection appears to have failed temporarily... please try again in a moment.");
});

// Empty exception processor for missing data exception.
defaultServiceCall.addExceptionHandler("dwr.engine.missingData", function () {
});