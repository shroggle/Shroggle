var payment = {};

payment.JAVIEN_PAYMENT_EXCEPTION = "com.shroggle.exception.JavienException";
payment.PAYPAL_PAYMENT_EXCEPTION = "com.shroggle.exception.PayPalException";
payment.PAYMENT_EXCEPTION_ERROR_FIELD = undefined;
payment.ON_EXCEPTION = undefined;

payment.PAYMENT_EXCEPTION_ACTION = function (exception) {
    if (payment.PAYMENT_EXCEPTION_ERROR_FIELD) {
        var errors = payment.PAYMENT_EXCEPTION_ERROR_FIELD;
    } else {
        errors = new Errors({highlighting:true, scrolling:true}, "errors");
    }
    errors.clear();
    errors.set("PAYMENT_REQUEST_ERROR", exception.message, {});

    if (payment.ON_EXCEPTION && $.isFunction(payment.ON_EXCEPTION)) {
        payment.ON_EXCEPTION();
    }

    payment.cleanUpParameters();
};

payment.cleanUpParameters = function () {
    payment.PAYMENT_EXCEPTION_ERROR_FIELD = undefined;
    payment.ON_EXCEPTION = undefined;
};