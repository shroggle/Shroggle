function checkAccountEmailForUserInfo(userId) {
    document.getElementById("accountEmailCheckResult").innerHTML = "Checking records for that email address ...";
    var accountEmail = document.getElementById("accountEmail");
    var accountEmailValue = accountEmail.value;
    new ServiceCall().executeViaDwr("CheckUserEmailService", "execute", accountEmailValue, userId, function(checkAccountEmailResponse) {
        var checkAccountEmailResult = document.getElementById("accountEmailCheckResult");
        var updateUserInfoButton = document.getElementById("updateUserInfoButton");
        if (checkAccountEmailResponse == "com.shroggle.exception.IncorrectEmailException") {
            checkAccountEmailResult.style.color = "red";
            checkAccountEmailResult.style.fontWeight = "bold";
            checkAccountEmailResult.style.padding = "0 0 5 0";
            checkAccountEmailResult.innerHTML = accountEmailValue + document.getElementById("emailNotCorrect").value;
            updateUserInfoButton.disabled = "disabled";
            accountEmail.style.background = "url(/images/error_bg.gif) bottom repeat-x";
            accountEmail.style.border = "1px solid #ff0000";
            accountEmail.hasCheckError = true;
        } else if (checkAccountEmailResponse == "com.shroggle.exception.NotUniqueUserEmailException") {
            checkAccountEmailResult.style.color = "red";
            checkAccountEmailResult.style.fontWeight = "bold";
            checkAccountEmailResult.style.padding = "0 0 5 0";
            checkAccountEmailResult.innerHTML = accountEmailValue + document.getElementById("emailNotAvalible").value;
            updateUserInfoButton.disabled = "disabled";
            accountEmail.style.background = "url(/images/error_bg.gif) bottom repeat-x";
            accountEmail.style.border = "1px solid #ff0000";
            accountEmail.hasCheckError = true;
        } else if (checkAccountEmailResponse == "com.shroggle.exception.NullOrEmptyEmailException") {
            checkAccountEmailResult.style.color = "red";
            checkAccountEmailResult.style.fontWeight = "bold";
            checkAccountEmailResult.style.padding = "0 0 5 0";
            checkAccountEmailResult.innerHTML = document.getElementById("emailEmptyError").value;
            updateUserInfoButton.disabled = "disabled";
            accountEmail.style.background = "url(/images/error_bg.gif) bottom repeat-x";
            accountEmail.style.border = "1px solid #ff0000";
            accountEmail.hasCheckError = true;
        } else {
            checkAccountEmailResult.innerHTML = "&nbsp;";
            updateUserInfoButton.disabled = "";
            if (accountEmail.hasCheckError && !$("#accountEmailConfirm")[0].hasEquallityError) {
                accountEmail.style.background = "";
                accountEmail.style.border = "";
            }
            accountEmail.hasCheckError = false;
        }
    });
    checkEqualsEmailAndConfirm();
}

var updatePending;
function delayedSelectCountry() {
    if (updatePending) {
        clearTimeout(updatePending);
    }

    updatePending = setTimeout('selectCountryForUserInfo(0)', 100);
}

function selectCountryForUserInfo(selectedState) {
    updatePending = false;
    if (selectedState == 0) {
        selectedState = document.getElementById("region").value;
    }

    var countries = document.getElementById("country");
    var selectedOptionValue = countries.options[countries.selectedIndex].value;
    showStatesForCountry(selectedOptionValue, document.getElementById("widgetId").value);
}

function checkEqualsEmailAndConfirm() {
    var accountEmail = document.getElementById("accountEmail");
    var accountEmailConfirm = document.getElementById("accountEmailConfirm");
    if (accountEmail.value != "" && accountEmailConfirm.value != "") {
        if (accountEmail.value.toLowerCase() != accountEmailConfirm.value.toLowerCase()) {
            document.getElementById("checkAccountEmailResult").innerHTML = document.getElementById("emailNotEqualError").value;

            accountEmail.style.background = "url(/images/error_bg.gif) bottom repeat-x";
            accountEmail.style.border = "1px solid #ff0000";
            accountEmailConfirm.style.background = "url(/images/error_bg.gif) bottom repeat-x";
            accountEmailConfirm.style.border = "1px solid #ff0000";
            accountEmailConfirm.hasEquallityError = true;
        } else {
            document.getElementById("checkAccountEmailResult").innerHTML = "&nbsp;";
            if (!accountEmail.hasCheckError) {
                accountEmail.style.background = "";
                accountEmail.style.border = "";
            }
            accountEmailConfirm.style.background = "";
            accountEmailConfirm.style.border = "";
            accountEmailConfirm.hasEquallityError = false;
        }
    }
}

function checkFormForUserInfo() {
    var lastName = $("#lastName")[0];
    var telephone = $("#telephone")[0];

    //Accuring the error block
    var errors = new Errors();
    errors.clear();

    if (lastName.value == "" || telephone.value == "") {
        if (lastName.value == "") {
            errors.set("ENTER_LAST_NAME", $("#enterName").val(), [$("#lastName")[0]]);
        }
        if (telephone.value == "") {
            errors.set("ENTER_TELEPHONE", $("#enterTelephone").val(), [$("#telephone")[0]]);
        }
        return false;
    }
    if ($("#accountEmail").val().toLowerCase() != $("#accountEmailConfirm").val().toLowerCase()) {
        alert($("#emailNotEqualError").val());
        return false;
    }

    errors.clear();
    return true;
}