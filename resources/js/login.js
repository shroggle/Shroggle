window.loginControl = {

    loginBlockId: "start_login_block",
    EXCEPTION_CLASS: "com.shroggle.exeption.UserNotLoginedException",
    userNotLoginedDwrException: false,
    userNotLoginedActionException: false,
    actionURL: "",
    doubleLoginProtection: false,

    EXCEPTION_ACTION: function (exception) {
        if (!exception) {
            alert("Can't process null exception!");
            return;
        }

        // closes all opened windows.
        closeAllWindows();

        // show login window
        showLoginInAccount("dwrException");
    },

    resendActivationEmail: function () {
        var accountEmail = $("#loginEmail_start").val().toLowerCase();
        if (!accountEmail) {
            accountEmail = $("#loginEmail").val().toLowerCase();
        }

        new ServiceCall().executeViaDwr("ResentAnActivationEmailService", "execute", accountEmail, function() {
            var loginInAccountEmailResend = createConfigureWindow({
                width: 355,
                height: 200
            });
            loginInAccountEmailResend.setContent($("#resendActivationEmailWindowText").html());
        });
    },

    showLogin: function () {
        $("#" + window.loginControl.loginBlockId).addClass("login_expanded");
        $("#remember_checkbox_div").css("visibility", "visible");
        $("#forgotPasswordLoginLink").show();
        $("#registerLoginLink").show();
        $("#" + window.loginControl.loginBlockId).animate({height: 118}, 200);
    },

    hideLogin: function (event) {
        var loginBlock = $("#" + login.loginBlockId);
        var clickedElement = event ? event.target : window.event.target;
        var clickedElementInsideLoginBlock = (clickedElement.id && clickedElement.id == login.loginBlockId) ||
                $(clickedElement).parents("#" + login.loginBlockId)[0];
        if (!clickedElementInsideLoginBlock) {
            $("#loginError_start").html("");
            $("#forgotPasswordLoginLink").hide();
            $("#registerLoginLink").hide();
            $(loginBlock).animate({height: 39}, 200, function () {
                $(loginBlock).removeClass("login_expanded");
            });
            $("#remember_checkbox_div").css("visibility", "hidden");
        }
    },

    init: function () {
        if (!$("#loginForm")[0] || window.loginControl.inited) {
            return;
        }

        window.loginControl.inited = true;

        $("input", "#loginForm").keydown(function (event) {
            if (event.keyCode == 13) {
                login.executeLogin(true);
            }
        });

        $("body").click(login.hideLogin);

        var emailFromCookie = getCookie("loginEmail");
        if (emailFromCookie) {
            $("#loginEmail_start")[0].entered = true;
            $("#loginEmail_start").val(emailFromCookie);
            $("#loginEmail_start").removeClass("logPassTextDefault");
            $("#loginPassword_start")[0].entered = true;
            $("#loginPassword_start").val(getCookie("loginPassword"));
            $("#loginPassword_start").removeClass("logPassTextDefault");
            $("#loginRemember_start").attr("checked", true);
        }
    },

    focusLoginOrPassField: function (field) {
        $(field).removeClass("logPassTextDefault");

        if (!field.entered) {
            $(field).val("");
        }

        //Firefox bug. Show login if hidden
        if (!$("#" + login.loginBlockId).hasClass('login_expanded')) {
            login.showLogin();
        }
    },

    blurLoginOrPassField: function (field) {
        field.entered = $(field).val().trim() != "";

        if (!field.entered) {
            if (field.id == "loginEmail_start") {
                $(field).val("username");
            } else if (field.id == "loginPassword_start") {
                $(field).val("password");
            }

            $(field).addClass("logPassTextDefault");
        }
    }

};

window.login = window.loginControl;

login.executeLogin = function (start) {
    var request = {
        email: start ? $("#loginEmail_start").val().toLowerCase() : $("#loginEmail").val().toLowerCase(),
        password: start ? $("#loginPassword_start").val() : $("#loginPassword").val()
    };
    var loginErrorBlock = start ? $("#loginError_start")[0] : $("#loginError")[0];
    var loginRemember = start ? $("#loginRemember_start")[0] : $("#loginRemember")[0];

    loginErrorBlock.style.color = "black";
    loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.loading;

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler("com.shroggle.exception.UserNotFoundException", function () {
        loginErrorBlock.style.color = "red";
        loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.unknownLoginOrPassword;
    });
    serviceCall.addExceptionHandler("com.shroggle.exception.UserNotActivatedException", function () {
        loginErrorBlock.style.color = "red";
        loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.accountNotActive;
    });
    serviceCall.addExceptionHandler("com.shroggle.exception.UserWithWrongPasswordException", function () {
        loginErrorBlock.style.color = "red";
        loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.unknownLoginOrPassword;
    });

    serviceCall.executeViaDwr("LoginUserService", "execute", request, function () {
        if (login.CUSTOM_AFTER_LOGIN_ACTION) {
            login.CUSTOM_AFTER_LOGIN_ACTION();
        }

        loginErrorBlock.style.color = "green";
        if (login.userNotLoginedDwrException) {
            closeConfigureWidgetDiv();
            lastAccessedTime.init();
            login.userNotLoginedDwrException = false;
        } else {
            if (login.userNotLoginedActionException) {
                closeConfigureWidgetDiv();
                lastAccessedTime.init();
                login.userNotLoginedActionException = false;
                window.location = login.actionURL;
                login.actionURL = "";
            } else {
                if (loginRemember.checked) {
                    setCookie("loginEmail", start ? $("#loginEmail_start").val() : $("#loginEmail").val(), "long");
                    setCookie("loginPassword", start ? $("#loginPassword_start").val() : $("#loginPassword").val(), "long");
                } else {
                    setCookie("loginEmail", "", "long");
                    setCookie("loginPassword", "", "long");
                }
                window.location = "/account/dashboard.action";
            }
        }
    });
    return false;
};

$ && $(document).ready(window.login.init);