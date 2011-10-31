var LoginInAccount = {

    EXCEPTION_CLASS : "com.shroggle.exception.UserNotLoginedException",
    userNotLoginedDwrException : false,
    userNotLoginedActionException : false,
    actionURL : "",

    EXCEPTION_ACTION : function () {
        // closes all opened windows.
        closeAllWindows();

        // show login window
        showLoginInAccount("dwrException");
    }
};

var doubleLoginProtection = false;

function showLoginInAccount(Exception, actionURI) {
    if (window.parent.doubleLoginProtection) return;
    var LoginInAccount = window.parent.LoginInAccount;
    window.parent.doubleLoginProtection = true;

    var newWidth;
    var newHeight;
    var exception = true;
    if (isIE()) {
        newWidth = 300;
        newHeight = 350;
    } else {
        newWidth = 300;
        newHeight = 330;
    }
    if (Exception == "dwrException") {
        LoginInAccount.userNotLoginedDwrException = true;
    } else if (Exception == "actionException") {
        LoginInAccount.userNotLoginedActionException = true;
        LoginInAccount.actionURL = actionURI;
    } else {
        exception = false;
    }
    var loginInAccountWindow = getParentWindow().createConfigureWindow({
        width: newWidth,
        height: newHeight,
        closable: false,
        resizable: false,
        draggable: exception
    });
    new ServiceCall().executeViaDwr("ShowLoginInAccountService", "execute", exception, function (loginInAccountHtml) {
        loginInAccountWindow.setContent(loginInAccountHtml);
        getParentWindow().getActiveWindow().resize();
        getParentWindow().doubleLoginProtection = false;
        var loginEmailFromCookie = getCookie("loginEmail");
        if (loginEmailFromCookie) {
            window.parent.document.getElementById("loginEmail").value = loginEmailFromCookie;
            window.parent.document.getElementById("loginPassword").value = getCookie("loginPassword");
            if (window.parent.document.getElementById("loginEmail").value.length > 0) {
                window.parent.document.getElementById("loginRemember").checked = true;
            }
        }
        setTimeout(focusMe, 10);
        if (LoginInAccount.userNotLoginedDwrException || LoginInAccount.userNotLoginedActionException) {
            window.parent.document.getElementById("loginError").innerHTML = window.parent.internationalLoginInAccountErrorTexts.sessionHasExpired;
        }
    });
}

function focusMe() {
    window.parent.document.getElementById("loginEmail").focus();
}

function showLoginOnStart() {
    var windowBackground = document.createElement("div");
    windowBackground.id = "windowBackground";
    windowBackground.style.position = "absolute";
    windowBackground.style.left = "0";
    windowBackground.style.top = "0";
    windowBackground.style.width = "100%";
    windowBackground.style.height = "100%";
    windowBackground.innerHTML = "&nbsp;";
    document.body.appendChild(windowBackground);

    windowBackground.onclick = function() {
        document.getElementById("hidden_login").style.display = "none";
        document.body.removeChild(windowBackground);
        document.getElementById("loginEmail_fake").value = document.getElementById("loginEmail_start").value;
        document.getElementById("loginPassword_fake").value = document.getElementById("loginPassword_start").value;
    };

    if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
        document.getElementById("hidden_login_main_box").style.height = "210px";
        document.getElementById("hidden_login").style.height = "310px";
    }

    var loginBlock = document.getElementById("hidden_login");
    loginBlock.style.display = "block";
    loginBlock.style.position = "absolute";

    var loginEmailFromCookie = getCookie("loginEmail");
    if (loginEmailFromCookie) {
        document.getElementById("loginEmail_start").value = loginEmailFromCookie;
        document.getElementById("loginPassword_start").value = getCookie("loginPassword");
        if (document.getElementById("loginEmail_start").value.length > 0) {
            document.getElementById("loginRemember_start").checked = true;
        }
    }

    document.getElementById('loginEmail_start').focus();
}

// == Beggin of Enter to Submit block
function addInputSubmitEvent(form, input) {
    input.onkeydown = function(e) {
        e = e || window.event;
        if (e.keyCode == 13) {
            var submitFun = form.onsubmit;
            if (submitFun == undefined)
                submitFun = form.onSubmit;
            if (submitFun())
                form.submit();
            return false;
        }
    };
}

if (document.all && !window.onload) {
    window.onload = function() {
        var forms = document.getElementsByTagName('form');

        for (var i = 0; i < forms.length; i++) {
            var inputs = forms[i].getElementsByTagName('input');

            for (var j = 0; j < inputs.length; j++)
                addInputSubmitEvent(forms[i], inputs[j]);
        }
    };
}

function executeLogin(start) {
    var request = new Object();
    if (!start) {
        request.email = document.getElementById("loginEmail").value.toLowerCase();
        request.password = document.getElementById("loginPassword").value;
        var loginErrorBlock = document.getElementById("loginError");
        var loginRemember = document.getElementById("loginRemember");
    } else {
        request.email = document.getElementById("loginEmail_start").value.toLowerCase();
        request.password = document.getElementById("loginPassword_start").value;
        loginErrorBlock = document.getElementById("loginError_start");
        loginRemember = document.getElementById("loginRemember_start");
    }

    loginErrorBlock.style.color = "black";
    loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.loading;

    var serviceCall = new ServiceCall();
    serviceCall.addExceptionHandler("com.shroggle.exception.UserNotFoundException", function () {
        loginErrorBlock.style.color = "red";
        loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.unknownLoginOrPassword;
    });
    serviceCall.addExceptionHandler("com.shroggle.exception.UserWithWrongPasswordException", function () {
        loginErrorBlock.style.color = "red";
        loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.unknownLoginOrPassword;
    });
    serviceCall.addExceptionHandler("com.shroggle.exception.UserNotActivatedException", function () {
        loginErrorBlock.style.color = "red";
        loginErrorBlock.innerHTML = window.parent.internationalLoginInAccountErrorTexts.accountNotActive;
    });

    serviceCall.executeViaDwr("LoginUserService", "execute", request, function (isAdmin) {
        if (LoginInAccount.CUSTOM_AFTER_LOGIN_ACTION) {
            LoginInAccount.CUSTOM_AFTER_LOGIN_ACTION();
        }

        loginErrorBlock.style.color = "red";
        if (LoginInAccount.userNotLoginedDwrException) {
            closeConfigureWidgetDiv();
            lastAccessedTime.init();
            LoginInAccount.userNotLoginedDwrException = false;
        } else {
            if (LoginInAccount.userNotLoginedActionException) {
                closeConfigureWidgetDiv();
                lastAccessedTime.init();
                LoginInAccount.userNotLoginedActionException = false;
                window.location = LoginInAccount.actionURL;
                LoginInAccount.actionURL = "";
            } else {
                if (loginRemember.checked) {
                    setCookie("loginEmail", start ? document.getElementById("loginEmail_start").value : document.getElementById("loginEmail").value, "long");
                    setCookie("loginPassword", start ? document.getElementById("loginPassword_start").value : document.getElementById("loginPassword").value, "long");
                } else {
                    setCookie("loginEmail", "", "long");
                    setCookie("loginPassword", "", "long");
                }
                if (isAdmin) {
                    window.location = "/account/showAdminInterface.action";
                } else {
                    window.location = "/account/dashboard.action";
                }
            }
        }
    });
    return false;
}
