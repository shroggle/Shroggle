<%@ page import="com.shroggle.presentation.account.accessPermissions.AddEditPermissionsService" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.User" %>
<%@ page import="com.shroggle.util.international.InternationalStorage" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.entity.UserOnSiteRight" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.shroggle.util.persistance.Persistance" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="shareYourSites"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% AddEditPermissionsService service = (AddEditPermissionsService) request.getAttribute("service");
    final User selectedUser = service.getSelectedUser();
    final boolean showUserInfo = selectedUser != null;
    final boolean disableWindow = service.isDisableWindow();
    List<Site> sites = service.getSelectedUserSites();
    boolean showRegistrationDate = service.getSelectedUser() != null && service.getSelectedUser().getRegistrationDate() != null;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    final String loginedUserFirstLastNames = ((service.getLoginedUser().getFirstName() != null ? service.getLoginedUser().getFirstName() : "") + " " + service.getLoginedUser().getLastName());
    String sitesId = "";
    if (sites != null) {
        for (Site site : sites) {
            sitesId += site.getSiteId() + ";";
        }
    }
    String invitationText = service.getInvitationText();
    if (invitationText == null || invitationText.isEmpty()) {
        InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        International international = internationalStorage.get("addEditPermissions", Locale.US);
        invitationText = international.get("NoInvitationEmail");
    }
%>

<input type="hidden" id="sitesId" value="<%=sitesId%>">


<div class="windowOneColumn">
    <h1><international:get name="shareYourSitesHeader"/></h1>

    <div class="emptyError" id="errors"></div>

    <dl class="w_20">
        <dt><label for="firstName"><international:get name="firstName"/></label></dt>
        <dd><input type="text" id="firstName" maxlength="255"
                   value="<%= showUserInfo ? service.getSelectedUser().getFirstName() != null ? service.getSelectedUser().getFirstName() : "" : "" %>" <%= showUserInfo || disableWindow ? "disabled" : "" %>>
        </dd>
        <dt><label for="lastName"><international:get name="lastName"/></label></dt>
        <dd><input type="text" id="lastName" maxlength="255"
                   value="<%= showUserInfo ? service.getSelectedUser().getLastName() : ""%>" <%=showUserInfo || disableWindow ? "disabled" : "" %>>
        </dd>
        <dt><label for="email"><international:get name="emailAddress"/></label></dt>
        <dd><input type="text" id="email" maxlength="255"
                   value="<%= showUserInfo ? service.getSelectedUser().getEmail() : ""%>" <%=showUserInfo || disableWindow ? "disabled" : "" %>>

        </dd>
    </dl>
    <br/>

    <div class="inform_mark" style="margin-left:20px;"><international:get name="administratorInfo"/></div>
    <br/>

    <div class="inform_mark" style="margin-left:20px;"><international:get name="siteEditorInfo"/></div>

    <br/>
    <span style="font-weight:bold"><international:get name="selectSitesToShareText"/></span>
    <br/>

    <div id="selectedAccountSites" class="shareSitesTable">
        <table>
            <%
                if (sites != null) {
                    Persistance persistance = ServiceLocator.getPersistance();
                    for (Site site : sites) {
                        String type;
                        if (showUserInfo) {
                            UserOnSiteRight right = persistance.getUserOnSiteRightByUserAndSiteId(selectedUser.getUserId(), site.getSiteId());
                            type = right != null ? right.getSiteAccessType().toString() : "VISITOR";
                        } else {
                            type = "VISITOR";
                        }
            %>
            <tr style="padding: 3px;">
                <td><%= HtmlUtil.limitName(site.getTitle() != null ? site.getTitle() : site.getSubDomain()) %>
                </td>
                <td><input type="radio" id="none<%= site.getSiteId() %>" name="accessLevel<%= site.getSiteId() %>"
                           value="NONE" <%=disableWindow ? "disabled" : "" %>
                           <%if(type.equals("VISITOR")){%>checked<%}%>
                        >
                    <span style="font-weight:bold"><label for="none<%= site.getSiteId() %>"><international:get
                            name="none"/></label></span></td>
                <td><input type="radio" id="admin<%= site.getSiteId() %>" name="accessLevel<%= site.getSiteId() %>"
                    <%=disableWindow ? "disabled" : "" %>
                           <%if(showUserInfo && type.equals("ADMINISTRATOR")){%>checked<%}%>
                           value="ADMINISTRATOR">
                    <label
                            for="admin<%= site.getSiteId() %>"><international:get name="admin"/></label></td>
                <td><input type="radio" id="siteEditor<%= site.getSiteId() %>" value="EDITOR"
                    <%=disableWindow ? "disabled" : "" %> <%if(showUserInfo && type.equals("EDITOR")){%>checked<%}%>
                           name="accessLevel<%= site.getSiteId() %>">
                    <label for="siteEditor<%= site.getSiteId() %>"><international:get name="siteEditor"/></label></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>

    <br/>
    <span style="font-weight:bold"><international:get name="invitationText"/></span>
    <textarea onfocus="trimTextArea(this);" id="invitationText" <%= showUserInfo || disableWindow ? "disabled" : "" %>
              class="shareSitesEmailTextArea" cols=""
              rows=""><% if (!showUserInfo) { %><international:get
            name="invitationTextPart1"/> <%= loginedUserFirstLastNames %> <international:get
            name="invitationTextPart2"/><% } else { %><%= invitationText != null ? invitationText : "" %><% } %></textarea><br/>

    <br/>
    <span style="font-weight:bold"><international:get name="note"/></span>

    <div class="textAddedToEmailDiv">
        <international:get name="instruction">
            <international:param value="<%= UsersManager.getNetworkNameForNetworkUserOrOurAppName() %>"/>
            <international:param value="<%= ServiceLocator.getConfigStorage().get().getApplicationUrl() %>"/>
            <international:param value="<%= UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail() %>"/>
        </international:get>
    </div>
    <% if (showRegistrationDate) { %>
    <br/>
    <international:get name="emailWasSent"/> <%= format.format(service.getSelectedUser().getRegistrationDate()) %>
    <% } %>


    <div class="buttons_box">
        <% if (showUserInfo && !disableWindow) {%>
        <% if (selectedUser.getActiveted() != null) { %>
        <span class="activeUserSpan"><international:get name="activeUserText"/></span>
        <% } else { %>
        <input type="button" class="but_w170" onmouseover="this.className='but_w170_Over';"
               onmouseout="this.className='but_w170';"
               value="<international:get name="resendInvitation"/>"
               onclick="shareYourSitesResentAnActivationEmail('<%= selectedUser.getEmail() %>');"/>
        <% } %>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="save"/>"
               onclick="saveChanges(<%= selectedUser.getUserId() %>);"/>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDiv();"/>
        <% } %>

        <% if (disableWindow) {%>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="close"/>" onclick="closeConfigureWidgetDiv();"/>
        <% } %>

        <% if (!showUserInfo) {%>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="send"/>" onclick="sendInvitation();"/>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDiv();"/>
        <% } %>
    </div>
</div>


<div id="shareYourSitesInvitationResent" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto;height:10px; width:320px; padding:10px; text-align:left;">
            <div style="font-weight:bold; color:green;text-align:center;"><international:get
                    name="anInvitationEmailResent"/></div>
        </div>
        <br clear="all"/><br/>

        <p align="center"><input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                                 onmouseout="this.className='but_w73';"
                                 value="Close" onclick="closeConfigureWidgetDiv();"/></p>
        <br/>
    </div>
</div>