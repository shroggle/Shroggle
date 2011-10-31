var forgotPassword = {};

forgotPassword.emptyEmail = true;
forgotPassword.incorrectEmail = true;
forgotPassword.incorrectEmailConfirmation = true;

forgotPassword.show = function() {
    closeAllWindows();

    //Hide expanded login block
    var loginBlock = document.getElementById("hidden_login");
    if (loginBlock) {
        loginBlock.style.display = "none";
        var windowBackground = document.getElementById("windowBackground");
        if (windowBackground) {
            document.body.removeChild(windowBackground);
        }
    }

    var forgottenMyPasswordWindow = createConfigureWindow({
        width: 300,
        height: 345,
        notResizable: true
    });

    var serviceCall = new ServiceCall();
    serviceCall.executeViaDwr("ForgottenMyPasswordService", "execute", function (forgottenMyPasswordHtml) {
        if (!isAnyWindowOpened()) {
            return;
        }

        forgottenMyPasswordWindow.setContent(forgottenMyPasswordHtml);
    });
};

forgotPassword.checkAccountEmailExist = function () {
    var checkAccountEmailResult = document.getElementById("emailCheckResult");
    checkAccountEmailResult.innerHTML = window.parent.internationalLoginInAccountErrorTexts.waitCheckInputEmail;
    checkAccountEmailResult.style.color = "black";

    var accountEmail = document.getElementById("forgottenMyPasswordWindowAccountEmail").value;
    new ServiceCall().executeViaDwr("CheckUserEmailService", "execute", accountEmail, null, function(checkAccountEmailResponse) {
        if (checkAccountEmailResponse == "com.shroggle.exception.NotUniqueUserEmailException") {
            checkAccountEmailResult.innerHTML = window.parent.internationalLoginInAccountErrorTexts.availableEmaile;
            checkAccountEmailResult.style.color = "green";
            forgotPassword.incorrectEmail = false;
            forgotPassword.emptyEmail = false;
        } else if (checkAccountEmailResponse == "com.shroggle.exception.NullOrEmptyEmailException") {
            checkAccountEmailResult.innerHTML = window.parent.internationalLoginInAccountErrorTexts.inputAnEmail;
            checkAccountEmailResult.style.color = "red";
            forgotPassword.incorrectEmail = true;
            forgotPassword.emptyEmail = true;
        } else {
            checkAccountEmailResult.innerHTML = window.parent.internationalLoginInAccountErrorTexts.wrongEmailAddress;
            checkAccountEmailResult.style.color = "red";
            forgotPassword.incorrectEmail = true;
            forgotPassword.emptyEmail = false;
        }
    });
    forgotPassword.checkEmailAndConfirmEquals();
};

forgotPassword.checkEmailAndConfirmEquals = function() {
    var accountEmail = $("#forgottenMyPasswordWindowAccountEmail").val();
    var accountEmailConfirm = $("#forgottenMyPasswordWindowAccountEmailConfirm").val();
    if (accountEmail != "" && accountEmailConfirm != "") {
        if (accountEmail.toLowerCase() != accountEmailConfirm.toLowerCase()) {
            $("#emailConfirnCheckResult").html(window.parent.internationalLoginInAccountErrorTexts.wrongEmailAddressConfirm);
            forgotPassword.incorrectEmailConfirmation = true;
        } else {
            $("#emailConfirnCheckResult").html("&nbsp;");
            forgotPassword.incorrectEmailConfirmation = false;
        }
    }
};

forgotPassword.submit = function () {
    if (forgotPassword.emptyEmail) {
        alert(window.parent.internationalLoginInAccountErrorTexts.inputAnEmail);
    } else if (forgotPassword.incorrectEmail) {
        alert(window.parent.internationalLoginInAccountErrorTexts.wrongEmailAddress);
    } else if (forgotPassword.incorrectEmailConfirmation) {
        alert(window.parent.internationalLoginInAccountErrorTexts.wrongEmailAddressConfirm);
    } else {
        var accountEmail = document.getElementById("forgottenMyPasswordWindowAccountEmail").value;
        new ServiceCall().executeViaDwr("ForgottenMyPasswordService", "sendEmailWhithForgottenPassword", accountEmail, function (passwordSentWindowText) {
            forgotPassword.emptyEmail = true;
            forgotPassword.incorrectEmail = true;
            forgotPassword.incorrectEmailConfirmation = true;
            closeConfigureWidgetDiv();

            var windowBackground = document.createElement("div");
            windowBackground.id = "windowBackground_password";
            windowBackground.style.position = "absolute";
            windowBackground.style.left = "0";
            windowBackground.style.top = "0";
            windowBackground.style.width = "100%";
            windowBackground.style.height = "100%";
            windowBackground.style.backgroundColor = "gray";
            if ((navigator.appName.indexOf("Internet Explorer") > -1)) {
                windowBackground.style.filter = "alpha(opacity=25)";
            } else {
                windowBackground.style.opacity = "0.25";
            }
            windowBackground.innerHTML = "&nbsp;";
            document.body.appendChild(windowBackground);

            var confirmDiv = document.createElement("div");
            confirmDiv.style.width = "460px";
            confirmDiv.style.height = "135px";
            confirmDiv.style.position = "absolute";
            confirmDiv.style.left = "32%";
            confirmDiv.style.top = "40%";
            confirmDiv.id = "confirmDiv_password";
            confirmDiv.innerHTML = passwordSentWindowText;
            document.body.appendChild(confirmDiv);
        });
    }
};

forgotPassword.closePasswordSentWindow = function () {
    $("#windowBackground_password").remove();
    $("#confirmDiv_password").remove();
    showLoginInAccount();
};
