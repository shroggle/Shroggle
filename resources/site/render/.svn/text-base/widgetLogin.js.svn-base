function loginVisitor(widgetId, returnToRegistration) {
    var errors = new Errors({highlighting: false, scrolling:false,
        errorClassName: "loginFormErrorBlock", emptyErrorClassName:"loginFormErrorBlockEmpty"}, "loginErrorBlock" + widgetId);

    errors.clear();

    var visitorloginRequest = new Object();
    var errorBlock = document.getElementById("loginErrorBlock" + widgetId);
    visitorloginRequest.login = $("#login" + widgetId).val();
    visitorloginRequest.password = $("#password" + widgetId).val();
    visitorloginRequest.remember = document.getElementById("remember" + widgetId).checked;
    visitorloginRequest.registrationFormId = $("#shouldBeRegisteredFromRightFormId" + widgetId).val() == "null" ? null :
            $("#shouldBeRegisteredFromRightFormId" + widgetId).val();

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VisitorNotFoundException",
            errors.exceptionAction({errorId:"UNKNOWN_VISITOR_LOGIN_OR_PASSWORD", fields:[errorBlock]}));
    serviceCall.addExceptionHandler(
            "com.shroggle.exception.UserWithWrongPasswordException",
            errors.exceptionAction({errorId:"UserWithWrongPasswordException", fields:[errorBlock]}));

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.UserNotActivatedException",
            errors.exceptionAction({errorId:"USER_NOT_ACTIVE_EXCEPTION", onAfterException:function () {
                $(errorBlock).find("a.resendActivationEmailLink").click(function () {
                    visitorLoginResendActivationEmail(widgetId);
                });
            }}));

    serviceCall.executeViaDwr("VisitorLoginService", "execute", visitorloginRequest, widgetId, returnToRegistration, function(data) {
        if (data == "ok") {
            //Update entire page on login
            window.location.reload(true);
        } else {
            $("#loginBlock" + widgetId).html(data);
        }
    });
}

function bindLoginFormSubmitEvent(widgetId) {
    $("#loginForm" + widgetId).find("input").each(function() {
        if (!this.submitEventBound) {
            this.submitEventBound = true;
            $(this).keydown(function(event) {
                if (event.keyCode == 13) {
                    loginVisitor(widgetId, $("#returnToRegistration" + widgetId)[0].value);
                }
            });
        }
    });
}

function showPreviousBlock(widgetId) {
    var loginBlock = $("#loginBlock" + widgetId);
    loginBlock.prev().show();
    loginBlock.remove();
}

function showVisitorLogin(widgetId, force) {
    new ServiceCall().executeViaDwr("ShowVisitorLoginService", "execute", widgetId, force, function (html) {
        $("#widget" + widgetId).html(html);
    });
}

//Processing link to registration block
function registerLink(widgetId) {
    new ServiceCall().executeViaDwr("ShowRegistrationService", "executeWithReturnToLogin", widgetId, function(data) {
        $("#loginBlock" + widgetId).html(data);
    });
}

function registerLinkWithFormId(widgetId, formId) {
    if (formId == -1) {
        registerLink(widgetId);
    } else {
        new ServiceCall().executeViaDwr("ShowRegistrationService", "executeWithReturnToLoginAndFormId", widgetId, formId, function(data) {
            $("#loginBlock" + widgetId).html(data);
        });
    }
}

function returnToRegistration(widgetId) {
    new ServiceCall().executeViaDwr("ShowRegistrationService", "execute", widgetId, function(data) {
        $("#loginBlock" + widgetId).html(data);
    });
}

function logout() {
    new ServiceCall().executeViaDwr("VisitorLogoutService", "execute", function() {
        window.location.reload(true);
    });
}

//FORGOTTEN YOUR PASSWORD
function emailVisitorPassword(widgetId) {
    var forgotPasswordArea = $("#forgotPasswordArea" + widgetId);
    var forgotPasswordOkArea = $("#forgotPasswordOkArea" + widgetId);
    var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error"}, "forgotPassErrorBlock" + widgetId);

    errors.clear();

    var email = $("#ForgotPassEmail" + widgetId);
    var retypeEmail = $("#ForgotPassRetypeEmail" + widgetId);
    if (email.val() != retypeEmail.val()) {
        errors.set("VISITOR_WITH_NOT_EQUAL_EMAIL_AND_RETYPE_EMAL", "Email and email confirmation do not match.", [email[0], retypeEmail[0]]);
        return;
    }

    var serviceCall = new ServiceCall();

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.UserNotFoundException",
            errors.exceptionAction({errorId:"VISITOR_WITH_NOT_UNIQUE_LOGIN"}));

    serviceCall.addExceptionHandler(
            "com.shroggle.exception.VistorEmailAndRetypeEmailAreNotEqual",
            errors.exceptionAction({errorId:"VISITOR_WITH_NOT_EQUAL_EMAIL_AND_RETYPE_EMAL"}));

    serviceCall.executeViaDwr("EmailVisitorPasswordService", "email", email.val(), retypeEmail.val(), widgetId, function(data) {
        if (data) {
            forgotPasswordArea.hide();
            forgotPasswordOkArea.show();
        }
    });
}

function closeForgotPassOkArea(widgetId) {
    $("#forgotPasswordArea" + widgetId).hide();
    $("#forgotPasswordOkArea" + widgetId).hide();
    $("#loginForm" + widgetId).show();
}

function showForgotPass(widgetId) {
    $("#forgotPasswordArea" + widgetId).show();
    $("#loginForm" + widgetId).hide();
}

function showLogin(widgetId) {
    $("#forgotPasswordArea" + widgetId).hide();
    $("#loginForm" + widgetId).show();
}

function visitorLoginResendActivationEmail(widgetId) {
    var email = $("#login" + widgetId).val();

    new ServiceCall().executeViaDwr("ResentAnActivationEmailService", "execute", email, function() {
        var errors = new Errors({highlighting: false, scrolling:false, errorClassName: "error"}, "loginErrorBlock" + widgetId);
        errors.clear();
        var emailResentText = $("#emailResentText" + widgetId).val();
        errors.set("EMAIL_SUCCESSFULLY_RESENT", "<span style='color:green'>" + emailResentText + "</span>");
    });
}