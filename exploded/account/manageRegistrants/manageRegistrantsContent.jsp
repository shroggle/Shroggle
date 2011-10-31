<%@ page import="com.shroggle.entity.RegistrantFilterType" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageRegistrants"/>
<%
    final int siteId = (Integer) request.getAttribute("manageRegistrantsSiteId");
    final String siteName = (String) request.getAttribute("manageRegistrantsSiteName");
%>
<input type="hidden" id="manageRegistrantsSiteId" value="<%= siteId %>"/>
<input type="hidden" id="visitorDeleted" value="<international:get name="visitorDeleted"/>"/>
<input type="hidden" id="visitorInvited" value="<international:get name="visitorInvited"/>"/>
<input type="hidden" id="delUnregGuest" value="<international:get name="delUnregGuest"/>"/>
<input type="hidden" id="delRegGuest" value="<international:get name="delRegGuest"/>"/>
<input type="hidden" id="delRegVisitor" value="<international:get name="delRegVisitor"/>"/>

<h1><%= siteName %>. <international:get name="pageTitle"/></h1>
<international:get name="info"/>

<div class="inform_mark_shifted" style="margin:10px 0 0 0">
    <international:get name="registrantsFilterInfo"/>
</div>
<div class="inform_mark_shifted" style="margin:5px 0 0 0">
    <international:get name="statusInfo"/>
</div>
<br>

<div align="right">
    <label for="filter"><international:get name="filter"/></label>
    <select id="filter"
            onchange="manageRegistrants.search();">
        <option selected="selected" value="<%=RegistrantFilterType.SHOW_ALL%>" id="show_all">
            <international:get name="show_all"/>
        </option>
        <option value="<%= RegistrantFilterType.REGISTERED %>">
            <international:get name="registered"/>
        </option>
        <option value="<%= RegistrantFilterType.PENDING %>">
            <international:get name="pending"/>
        </option>
        <option value="<%= RegistrantFilterType.EXPIRED %>">
            <international:get name="expired"/>
        </option>
        <option value="<%= RegistrantFilterType.INVITED %>">
            <international:get name="invited"/>
        </option>
    </select>
    <label for="search"><international:get name="search"/></label>
    <input type="text" id="search" class="txt" maxlength="255"
           onkeyup="manageRegistrants.processKey(this.value, event);"/>

    <div style="display:inline;visibility:hidden;" id="show_all_div"><a
            href="javascript:manageRegistrants.resetSearchAndFilter()"><international:get name="showAll"/></a>
    </div>
</div>
<div class="manageRegistransHR"></div>

<div style="color:green;margin-bottom:5px;display:none;" id="updateDiv">&nbsp;</div>
<div id="registrantsDiv">
    <jsp:include page="manageRegistrantsList.jsp"/>
</div>

<br clear="all">

<div style="margin-top:10px;">
    <div style="float:left;">
        <input type="button" value="<international:get name="manageGroups"/>"
               class="but_w130" onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="groups.showConfigureGroupsWindow(<%= siteId %>);"/>
        <input onclick="manageItems.createItem({itemType:'<%= ItemType.REGISTRATION %>', siteId:<%= siteId %>});"
               type="button" class="but_w230"
               value="<international:get name="createRegistrationForm"/>"
               onmouseover="this.className = 'but_w230_Over';"
               onmouseout="this.className = 'but_w230';"/>
    </div>

    <div style="float:right;">
        <input onclick="inviteGuests.show(<%= siteId %>);" type="button"
               class="but_w100"
               value="<international:get name="inviteNew"/>"
               onmouseover="this.className = 'but_w100_Over';"
               onmouseout="this.className = 'but_w100';"/>
        <input onclick="manageRegistrants.deleteSelectedVisitors();" type="button"
               class="but_w100"
               value="<international:get name="delete"/>"
               onmouseover="this.className = 'but_w100_Over';"
               onmouseout="this.className = 'but_w100';"/>
    </div>
</div>
<br clear="all">
