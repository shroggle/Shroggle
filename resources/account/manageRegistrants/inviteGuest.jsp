<%@ page import="com.shroggle.entity.DraftItem" %>
<%@ page import="com.shroggle.presentation.manageRegistrants.InviteGuestService" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<international:part part="inviteGuest"/>
<%
    final InviteGuestService service = (InviteGuestService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <h1><international:get name="header"/></h1>

    <h2><%=service.getSiteName()%>
    </h2>

    <div id="errors" class="emptyError"></div>
    <br>
    <dl class="w_20">
        <dt width="25%"><label for="firstNameInvite"><international:get name="firstName"/></label></dt>
        <dd><input id="firstNameInvite" type="text" maxlength="255"/></dd>
        <dt width="25%"><label for="lastNameInvite"><international:get name="lastName"/></label></dt>
        <dd><input id="lastNameInvite" type="text" maxlength="255"/></dd>
        <dt width="25%"><label for="emailInvite"><international:get name="email"/></label></dt>
        <dd><input id="emailInvite" type="text" maxlength="255"/></dd>
    </dl>
    <br/>
    <label for="regFormSelect"><international:get name="selectRegForm"/></label>
    <select id="regFormSelect" style="width:150px;">
        <option value="-1"><international:get name="selectRegFormOption"/></option>
        <%for (DraftItem form : service.getRegistrationForms()) {%>
        <option value="<%=form.getFormId()%>"><%=form.getName()%>
        </option>
        <%}%>
    </select>

    <div class="inviteGuestGroupsDiv">
        <international:get name="placeInviteeIntoTheFollowingGroups"/>:
        <div class="inviteGuestGroups">
            <% request.setAttribute("groups", service.getGroups());%>
            <jsp:include page="../groups/availableGroups.jsp" flush="true"/>
        </div>
    </div>

    <div class="inviteGuestEmailDiv">
        <label for="emailMessage"><international:get name="yourEmailMessage"/></label><br>

        <div class="inviteGuestEmail">
            <textarea onfocus="trimTextArea(this);" id="emailMessage"
                      cols="" rows=""><international:get name="emailText"/></textarea>
        </div>
    </div>

    <div align="right">
        <input type="button" class="but_w73" value="Send"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';"
               onclick="inviteGuests.sendInvitation(<%=service.getSiteId()%>);">
        <input type="button" class="but_w73" value="Cancel"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();">
    </div>
</div>