<%@ page import="com.shroggle.presentation.account.accessPermissions.AccessPermissionsModel" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="accessPermissions"/>
<% AccessPermissionsModel model = ((AccessPermissionsModel) request.getAttribute("model")); %>
<input type="hidden" id="loginedUserId" value="<%= model.getLoginUserId() %>">
<html>
<head>
    <title><international:get name="accessPermissions"/></title>
    <script type="text/javascript">
        var internationalAccessPermissionsErrorTexts = new Object();
        internationalAccessPermissionsErrorTexts.emptyEmail = "<international:get name="emptyEmail"/>";
        internationalAccessPermissionsErrorTexts.siteEmpty = "<international:get name="siteEmpty"/>";
        internationalAccessPermissionsErrorTexts.emptyInvitationText = "<international:get name="emptyInvitationText"/>";
        internationalAccessPermissionsErrorTexts.emailNotCorrect = "<international:get name="emailNotCorrect"/>";
        internationalAccessPermissionsErrorTexts.deleteUserConfirmation = "<international:get name="deleteUserConfirmation"/>";
        internationalAccessPermissionsErrorTexts.lastAdmin = "<international:get name="lastAdmin"/>";
        internationalAccessPermissionsErrorTexts.pleaseSelectUser = "<international:get name="pleaseSelectUser"/>";
        internationalAccessPermissionsErrorTexts.userExist = "<international:get name="userExist"/>";
        internationalAccessPermissionsErrorTexts.selectOneOrMoreSites = "<international:get name="selectOneOrMoreSites"/>";
        internationalAccessPermissionsErrorTexts.yourUserIsDeleted = "<international:get name="yourUserIsDeleted"/>";

    </script>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>

        <div class="content">
            <div class="box_70">
                <%@ include file="../accountMenuInclude.jsp" %>
            </div>
            <!-- start label-box -->
            <div class="box_70">
                <div id="errorBlock" style="display:none;">&nbsp;</div>
                <b><international:get name="listOfUsers"/></b><br><br>

                <div class="inform_mark"><international:get name="accountLevelUsers"/></div>
                <br>

                <div id="accountUsersTable">
                    <jsp:include page="accountUsers.jsp" flush="true"/>
                </div>
                <br clear="all">

                <div align="right" id="accessPermissionsButtons"
                        >
                    <input type="button" value="Invite New" onclick="showShareYourSitesWindow(-1);"
                           onmouseout="this.className='but_w100';"
                           onmouseover="this.className='but_w100_Over';" class="but_w100">
                    <input type="button" value="Delete" onmouseout="this.className='but_w73';"
                           onclick="deleteAccount();"
                           onmouseover="this.className='but_w73_Over';" class="but_w73">
                    <input type="button" value="Cancel" onclick="cancelAccessPermissions();"
                           onmouseout="this.className='but_w73';"
                           onmouseover="this.className='but_w73_Over';" class="but_w73">
                </div>
            </div>
            <br>
        </div>

        <%@ include file="../../includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>