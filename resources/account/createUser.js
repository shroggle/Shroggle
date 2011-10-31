var userFields = [
    "email",
    "emailConfirm",
    "password",
    "passwordConfirm",
    "telephone",
    "firstName",
    "lastName"];

function checkAccountEmail() {
    var email = document.getElementById("email");
    var checkAccountEmailResult = document.getElementById("accountEmailCheckResult");
    var startCreateAccount = document.getElementById("startCreateAccount");
    document.getElementById("accountEmailCheckResult").innerHTML = "Checking records for that email address ...";
    var invitedUserId = document.getElementById("invitedUserId").value;
    new ServiceCall().executeViaDwr("CheckUserEmailService", "execute", email.value, invitedUserId, function(checkAccountEmailResponse) {
        if (checkAccountEmailResponse == "com.shroggle.exception.IncorrectEmailException") {
            checkAccountEmailResult.style.color = "red";
            checkAccountEmailResult.style.fontWeight = "bold";
            checkAccountEmailResult.style.padding = "0 0 5 0";
            checkAccountEmailResult.innerHTML = email.value + $("#emailNotCorrect").val();
            startCreateAccount.disabled = "disabled";
            email.className = "errorBorder";
            email.hasCheckError = true;
        } else if (checkAccountEmailResponse == "com.shroggle.exception.NotUniqueUserEmailException") {
            checkAccountEmailResult.style.color = "red";
            checkAccountEmailResult.style.fontWeight = "bold";
            checkAccountEmailResult.style.padding = "0 0 5 0";
            checkAccountEmailResult.innerHTML = $("#emailNotAvalible").val();
            startCreateAccount.disabled = "disabled";
            email.className = "errorBorder";
            email.hasCheckError = true;
        } else if (checkAccountEmailResponse == "com.shroggle.exception.NullOrEmptyEmailException") {
            checkAccountEmailResult.style.color = "red";
            checkAccountEmailResult.style.fontWeight = "bold";
            checkAccountEmailResult.style.padding = "0 0 5 0";
            checkAccountEmailResult.innerHTML = $("#emailEmptyError").val();
            startCreateAccount.disabled = "disabled";
            email.className = "errorBorder";
            email.hasCheckError = true;
        } else {
            checkAccountEmailResult.innerHTML = "&nbsp;";
            startCreateAccount.disabled = "";
            if (email.hasCheckError && !$("#emailConfirm")[0].hasEquallityError) {
                email.className = "txt";
            }
            email.hasCheckError = false;
        }
    });
    checkEqualsEmailAndConfirm();
}

function showMoreInfoWindow() {
    window.accountMoreInfoWindow = createConfigureWindow({
        width: 530,
        height: 200
    });
    window.accountMoreInfoWindow.setContent($("#accountMoreInfoText").html());
}

function hideAccountMoreInfoWindow() {
    closeConfigureWidgetDiv();
}

function showResentWindow() {
    var activationLinkResentWindow = createConfigureWindow({
        width: 355,
        height: 200
    });
    activationLinkResentWindow.setContent($("#activationLinkResent")[0].innerHTML);
}

function resentAnActivationEmail() {
    var accountEmail = document.getElementById("email").value;
    new ServiceCall().executeViaDwr("ResentAnActivationEmailService", "execute", accountEmail, function() {
        if (window.accountMoreInfoWindow) {
            closeConfigureWidgetDiv();
            showResentWindow();
        } else {
            showResentWindow();
        }
    });
}

function init() {
    var useExistDetail = document.getElementById("useExistingDetailsCheckbox");
    if (useExistDetail && useExistDetail.checked) {
        disableUserData("disabled");
    }
}

function checkEqualsPasswordAndConfirm() {
    var password = document.getElementById("password");
    var confirmPassword = document.getElementById("passwordConfirm");
    if (password.value != "" && confirmPassword.value != "") {
        if (password.value != confirmPassword.value) {
            document.getElementById("checkAccountPasswordResult").innerHTML = $("#passNotEqualError").val();
            password.className = "errorBorder";
            confirmPassword.className = "errorBorder";
        } else {
            document.getElementById("checkAccountPasswordResult").innerHTML = "&nbsp;";
            password.className = "txt";
            confirmPassword.className = "txt";
        }
    }
}

function checkEqualsEmailAndConfirm() {
    var accountEmail = document.getElementById("email");
    var accountEmailConfirm = document.getElementById("emailConfirm");
    if (accountEmail.value != "" && accountEmailConfirm.value != "") {
        if (accountEmail.value.toLowerCase() != accountEmailConfirm.value.toLowerCase()) {
            document.getElementById("checkAccountEmailResult").innerHTML = $("#emailNotEqualError").val();
            accountEmail.className = "errorBorder";
            accountEmailConfirm.className = "errorBorder";
            accountEmailConfirm.hasEquallityError = true;
        } else {
            document.getElementById("checkAccountEmailResult").innerHTML = "&nbsp;";
            if (!accountEmail.hasCheckError) {
                accountEmail.className = "txt";
            }
            accountEmailConfirm.className = "txt";
            accountEmailConfirm.hasEquallityError = false;
        }
    }
}

function checkFormForCreateUser() {
    if (document.getElementById("password").value != document.getElementById("passwordConfirm").value) {
        alert($("#passNotEqualError").val());
        return false;
    }
    if (document.getElementById("email").value.toLowerCase() != document.getElementById("emailConfirm").value.toLowerCase()) {
        alert($("#emailNotEqualError").val());
        return false;
    }

    disableUserData("");

    if (document.getElementById("agree").value != "true") {
        termsAndConditionsWindow();
        return false;
    }

    return true;
}

function fillUserData(thisCheckbox) {
    if (thisCheckbox.checked) {
        var password = document.getElementById("password");
        var confirmPassword = document.getElementById("passwordConfirm");
        document.getElementById("checkAccountPasswordResult").innerHTML = "&nbsp;";
        password.className = "txt";
        confirmPassword.className = "txt";
        document.getElementById("accountEmailCheckResult").innerHTML = "";
        document.getElementById("startCreateAccount").disabled = "";
        for (var i = 0; i < userFields.length; i++) {
            var originalField = document.getElementById("original" + userFields[i]);
            var field = document.getElementById(userFields[i]);
            if (field && originalField) {
                field.value = originalField.value;
            } else {
                alert("Field " + userFields[i] + " original field: " + ("original" + userFields[i]));
            }
        }
        disableUserData("disabled");
    } else {
        disableUserData("");
    }
}

function disableUserData(disable) {
    for (var i = 0; i < userFields.length; i++) {
        var userField = document.getElementById(userFields[i]);
        if (userField) {
            userField.disabled = disable;
        } else {
            alert("Not found " + userFields[i]);
        }
    }
}

// ---------------------------------------------------------------------------------------------------------------------

function termsAndConditionsWindow() {
    var helpWindow = createConfigureWindow({
        width: 600,
        height: 200
    });

    helpWindow.setContent($("#termsAndConditions")[0].innerHTML);
}

// ---------------------------------------------------------------------------------------------------------------------

function agreeToConditions() {
    document.getElementById("agree").value = "true";
    document.getElementById("form").submit();

    closeConfigureWidgetDiv();
}
