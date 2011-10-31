<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ page import="com.shroggle.presentation.site.accessibilityForRender.ConfigureAccessibleSettingsService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%
    final ConfigureItemSettingsService itemSettingsService = (ConfigureItemSettingsService) request.getAttribute("itemSettingsService");
    final ConfigureAccessibleSettingsService service = (ConfigureAccessibleSettingsService) request.getAttribute("accessibleService");
    final AccessibleForRender element = service.getAccessibleForRender();
%>
<international:part part="elementAccessibility"/>
<div    <% if (service.isShowAsSeparate()) { %>
        class="windowOneColumn"
        <% } else { %>
        <% if (itemSettingsService != null && itemSettingsService.getItemType().isExtendedSettingsWindow()) { %>
        class="extendedItemSettingsWindowDiv"
        <% } else { %>
        class="itemSettingsWindowDiv"
        <% } %>
        <% } %>>
    <input type="hidden" id="accessibleElementType_SITE" value="<%= AccessibleElementType.SITE %>">
    <input type="hidden" id="accessibleElementType_PAGE" value="<%= AccessibleElementType.PAGE %>">
    <input type="hidden" id="accessibleElementType_WIDGET" value="<%= AccessibleElementType.WIDGET %>">
    <input type="hidden" id="accessibleElementType_ITEM" value="<%= AccessibleElementType.ITEM %>">
    <input type="hidden" id="disableRestrictedAccessDiv"
           value="<%= element.getAccessibleSettings().getAccess() == AccessForRender.UNLIMITED %>">

    <% if (element.getAccessibleElementType() == AccessibleElementType.SITE) { %>
    <h1><international:get name="siteSubHeader"/></h1>

    <h2><%= service.getSiteTitle() %>
    </h2>
    <% } else if (element.getAccessibleElementType() == AccessibleElementType.PAGE) { %>
    <h1><international:get name="pageSubHeader"/></h1>
    <page:title customServiceName="accessibleService"/>
    <% } else if (element.getAccessibleElementType() == AccessibleElementType.WIDGET) { %>

    <h1>
        <international:get name="widgetSubHeader">
        <% if (service.getWidget() != null && service.getWidget().getItemType() != null) { %>
            <international:param>
                <jsp:attribute name="value" >
                     <international:get name="<%= String.valueOf(service.getWidget().getItemType()) %>" part="addWidget" />
                </jsp:attribute>
            </international:param>
        <% } else { %>
            <international:param value="<%= '\b' %>"/>
        <% } %>
        </international:get>
    </h1>

    <widget:title customServiceName="accessibleService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="accessibilityReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <br>

    <h3><international:get name="access"/></h3>

    <div style="margin:5px 0;">
        <input type="radio" name="accessType" id="unlimitedAccess" onclick="disableRestrictedAccessDiv(true);"
               value="<%= AccessForRender.UNLIMITED %>"
               <% if (element.getAccessibleSettings().getAccess() == AccessForRender.UNLIMITED) { %>checked="true"<% } %>>
        <label for="unlimitedAccess">&nbsp;<international:get name="unlimitedAccess"/></label>
    </div>
    <div style="margin-bottom:10px;">
        <input type="radio" name="accessType" id="restrictedAccess"
               onclick="disableRestrictedAccessDiv(false);$('#administrators')[0].checked = true;"
               value="<%= AccessForRender.RESTRICTED %>"
               <% if(element.getAccessibleSettings().getAccess() == AccessForRender.RESTRICTED) { %>checked="true"<% } %>>
        <label for="restrictedAccess">&nbsp;<international:get
                name="restrictedAccess"/></label>

        <div id="restrictedAccessDiv" style="margin-left:20px;margin-top:5px;">
            <div style="margin-bottom:5px;">
                <input type="checkbox" id="administrators"
                       <% if(element.getAccessibleSettings().isAdministrators()) { %>checked="true"<% } %>>
                <label for="administrators">&nbsp;<international:get name="administrators"/></label>
            </div>
            <div style="margin-bottom:5px;">
                <input type="checkbox" id="registeredVisitors" onclick="disableVisitorsGroupsDiv(!this.checked);"
                       <% if(element.getAccessibleSettings().isVisitors()) { %>checked="true"<% } %>>
                <label for="registeredVisitors">&nbsp;<international:get name="registeredVisitors"/></label>
            </div>
            <div id="visitorsGroups" style="margin-left:20px;">
                <div style="margin-bottom:5px;">
                    <international:get name="selectVisitorGroups"/>
                </div>

                <div id="groupsArea"
                     style="width:220px; height:100px; overflow-y:auto;overflow-x:hidden;border:1px solid gray; padding:5px;margin-bottom:5px;">
                    <% for (Group group : element.getAvailableGroups()) { %>
                    <div style="margin-bottom:5px;">
                        <input type="checkbox" id="<%= group.getGroupId() %>"
                            <% if (element.getAccessibleSettings().getVisitorsGroups().contains(group.getGroupId())) { %>
                               checked  <% } %>>
                        <label for="<%= group.getGroupId() %>">&nbsp;<%= group.getName() %>
                        </label>
                    </div>
                    <% } %>
                </div>
            </div>
            <div style="margin-bottom:5px;">
                <a href="javascript:manageItems.createItem({itemType:'<%= ItemType.REGISTRATION %>', siteId:<%= element.getSiteId() %>});"
                   style="display:block;margin-bottom:2px" id="addNewRegistrationFormLink"><international:get
                        name="createNewForm"/></a>
            </div>
            <div style="margin-bottom:5px;">
                <a href="javascript:inviteGuests.show(<%= service.getSiteId() %>);" id="inviteGuestLink"
                   style="display:block;"><international:get
                        name="inviteGuestsToRegister"/></a></div>
        </div>
    </div>
    <input type="checkbox" id="manageRegistrantsAfterSave"/>
    <%-- todo Revise this checkbox, maybe it would be better to make link to 'manage registrants' instead of it. --%>
    <label for="manageRegistrantsAfterSave">&nbsp;<international:get name="manageRegistrantsAfterSave"/></label>
    <br/><br/>

    <% if (service.isShowAsSeparate()) { %>
    <div align="right">
        <% if (service.getAccessibleForRender().getAccessibleElementType() == AccessibleElementType.WIDGET) { %>
        <div class="forItemDiv" style="display:<%= service.isShowForPage() ? "none" : "block"%>">
            <input type="checkbox" id="saveAccessibilityInCurrentPlace"
                   <% if (service.isSavedInCurrentPlace()) { %>checked<% } %>/>
            <label for="saveAccessibilityInCurrentPlace"><international:get name="forItem"/></label>
        </div>
        <% } %>

        <input type="button" value="<international:get name="save"/>" id="windowSave"
               onclick="configureAccessibility.save(<%= element.getId() %>, '<%= element.getAccessibleElementType() %>', <%= service.getSiteId() %>, true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDivWithConfirm();"
               id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
    </div>
    <% } %>
</div>

<% if (!service.isShowAsSeparate()) { %>
<div class="itemSettingsButtonsDiv" align="right" id="configureAccessibilityButtons">
    <% if (service.getAccessibleForRender().getAccessibleElementType() == AccessibleElementType.WIDGET) { %>
    <div class="forItemDiv" style="display:<%= service.isShowForPage() ? "none" : "block"%>"
         id="forItemDiv">
        <input type="checkbox" id="saveAccessibilityInCurrentPlace"
               <% if (service.isSavedInCurrentPlace()) { %>checked<% } %>/>
        <label for="saveAccessibilityInCurrentPlace"><international:get name="forItem"/></label>
    </div>
    <% } %>

    <div class="fr">
        <input type="button" value="<international:get name="apply"/>" id="windowApply"
               onclick="configureAccessibility.save(<%= element.getId() %>, '<%= element.getAccessibleElementType() %>', <%= service.getSiteId() %>, false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="save"/>" id="windowSave"
               onclick="configureAccessibility.save(<%= element.getId() %>, '<%= element.getAccessibleElementType() %>', <%= service.getSiteId() %>, true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDivWithConfirm();"
               id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
               class="but_w73">
    </div>
</div>
<% } %>
