function adminLogin(a) {
    $(a).hide();
    var email = $("input[type=text]", a.parentNode);
    var password = $("input[type=password]", a.parentNode);

    $("form", a.parentNode).bind("submit", function () {
        if ($("form", a.parentNode).attr("alredyCheck")) return true;

        var serviceCall = new ServiceCall();
        serviceCall.addExceptionHandler("com.shroggle.exception.UserNotFoundException", function () {
            $("form", a.parentNode).show();
            $(".adminLoginError", a.parentNode).show();
        });
        serviceCall.addExceptionHandler("com.shroggle.exception.UserWithWrongPasswordException", function () {
            $("form", a.parentNode).show();
            $(".adminLoginError", a.parentNode).show();
        });

        serviceCall.executeViaDwr("LoginUserService", "execute", {
            email: email.val(),
            password: password.val()
        }, function () {
            $("form", a.parentNode).attr("alredyCheck", true);
            $("form", a.parentNode).submit();
        });

        return false;
    });

    if (email.val() != "") $("form", a.parentNode).submit();
    else $("form", a.parentNode).show();
}