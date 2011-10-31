<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.UserInfoAction" %>
<%@ page import="com.shroggle.exception.NullOrEmptyEmailException" %>
<%@ page import="com.shroggle.exception.NotUniqueUserEmailException" %>
<%@ page import="com.shroggle.exception.IncorrectEmailException" %>
<%@ page import="com.shroggle.presentation.site.LoginHelpAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="loginHelp"/>
<html>
<head><title><international:get name="loginHelp"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript">

        function sendEmail() {
            new ServiceCall().executeViaDwr("LoginHelpService", "execute", function(data) {
                if (data == "ok")
                    document.getElementById("emailSentMessage").innerHTML = "<span style=\"color: green; font-weight: bold;padding:0 0 5px 0;\"><b>" + "<international:get name="passwordSent"/>" + "</span>";
            });
        }

        function changePassword() {
            var oldAccountPassword = document.getElementById("oldPasswordInput").value;
            var newPasswordInput = document.getElementById("newPasswordInput").value;
            var retypeNewPasswordInput = document.getElementById("retypeNewPasswordInput").value;
            if (newPasswordInput != retypeNewPasswordInput) {
                alert("<international:get name="passwordAreNotEqualConfirm"/>");
            } else if (newPasswordInput == "") {
                alert("<international:get name="inputAPassword"/>");
            } else {
                new ServiceCall().executeViaDwr("LoginHelpService", "changeUserPassword", oldAccountPassword, newPasswordInput, function(data) {
                    if (data == "wrongOldPassword") {
                        document.getElementById("passwordChangedMessage").innerHTML = "<span style=\"color: red; font-weight: bold;padding:0 0 5px 0;\"><b>" + "<international:get name="yourOldPasswordIsIncorrect"/>" + "</span>";
                        document.getElementById("emailSentMessage").innerHTML = "&nbsp;";
                        document.getElementById("passwordNotChangedMessage").innerHTML = "&nbsp;";
                    } else if (data == "ok") {
                        document.getElementById("emailSentMessage").innerHTML = "&nbsp;";
                        document.getElementById("passwordNotChangedMessage").innerHTML = "&nbsp;";
                        document.getElementById("passwordChangedMessage").innerHTML = "<span style=\"color: green; font-weight: bold;padding:0 0 5px 0;\"><b>" + "<international:get name="passwordChanged"/>" + "</span>";

                        //Clear password inputs.
                        document.getElementById("oldPasswordInput").value = "";
                        document.getElementById("newPasswordInput").value = "";
                        document.getElementById("retypeNewPasswordInput").value = "";
                    }
                });
            }
        }

        function checkPasswordConfirm() {
            var newPasswordInput = document.getElementById("newPasswordInput").value;
            var retypeNewPasswordInput = document.getElementById("retypeNewPasswordInput").value;
            if (newPasswordInput != "" && retypeNewPasswordInput != "" && newPasswordInput != retypeNewPasswordInput) {
                document.getElementById("passwordChangedMessage").innerHTML = "&nbsp;";
                document.getElementById("emailSentMessage").innerHTML = "&nbsp;";
                document.getElementById("passwordNotChangedMessage").innerHTML = "<span style=\"color: red; font-weight: bold;padding:0 0 5px 0;\"><b>" + "<international:get name="passwordAreNotEqualConfirm"/>" + "</span>";
            } else {
                document.getElementById("passwordNotChangedMessage").innerHTML = "&nbsp;";
            }
        }

        function clearForms() {
            document.getElementById("emailSentMessage").innerHTML = "&nbsp;";
            document.getElementById("passwordNotChangedMessage").innerHTML = "&nbsp;";
            document.getElementById("passwordChangedMessage").innerHTML = "&nbsp;";
            document.getElementById("oldPasswordInput").value = "";
            document.getElementById("newPasswordInput").value = "";
            document.getElementById("retypeNewPasswordInput").value = "";
        }

    </script>
</head>
<body>

<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>

        <div class="content">
            <div class="box_70">
                <%@ include file="accountMenuInclude.jsp" %>
            </div>
            <!-- start label-box -->
            <div class="box_70">
                <international:get name="changeYourEmailAddress"/> <stripes:link
                    beanclass="com.shroggle.presentation.site.UserInfoAction"><international:get
                    name="editContactDetailsPage"/></stripes:link>
                <br>
                <br><br>
                <b><international:get name="lostYorPassword"/></b>
                <br>

                <div id="emailSentMessage">&nbsp;</div>
                <div class="prepend-0"><input type="button" onclick="sendEmail();"
                                              class="but_w130" onmouseover="this.className='but_w130_Over';"
                                              onmouseout="this.className='but_w130';"
                                              value="Email Password"
                        ></div>
                <br>
                <br>

                <b><international:get name="resetYourPassword"/></b>
                <br>
                <br>

                <div class="inform_mark"><international:get name="changePasswordText"/></div>

                <div id="passwordChangedMessage">&nbsp;</div>
                <div class="prepend-0">
                    <dl class="w_30">
                        <dt><b><label for="oldPasswordInput"><international:get name="oldPassword"/></label></b>
                        </dt>
                        <dd><input type="password" id="oldPasswordInput"></dd>
                        <dt><b><label for="newPasswordInput"><international:get name="newPassword"/></label></b>
                        </dt>
                        <dd><input type="password" id="newPasswordInput"
                                   onblur="checkPasswordConfirm();"></dd>
                        <dt><b><label for="retypeNewPasswordInput"><international:get
                                name="retypeNewPassword"/></label></b></dt>
                        <dd><input type="password"
                                   id="retypeNewPasswordInput"
                                   onblur="checkPasswordConfirm();"></dd>
                    </dl>

                </div>
                <br>

                <div id="passwordNotChangedMessage">&nbsp;</div>
                <div class="buttons_box"><input type="button" value="Save" onclick="changePassword();"
                                                onmouseout="this.className='<international:get name="saveButton"/>';"
                                                onmouseover="this.className='<international:get name="saveButtonOnMouseOver"/>';"
                                                class="<international:get name="saveButton"/>">
                    <input type="button" value="Cancel" onclick="clearForms();"
                           onmouseout="this.className='<international:get name="cancelButton"/>';"
                           onmouseover="this.className='<international:get name="cancelButtonOnMouseOver"/>';"
                           class="<international:get name="cancelButton"/>"></div>


            </div>
            <br>

        </div>

        <%@ include file="../includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>