<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.manageVotes.ManageVotesForVotingSettings" %>
<international:part part="configureGallery"/>
<%--
 @author Balakirev Anatoliy
 Date: 26.08.2009
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final boolean showSiteName = (Boolean)request.getAttribute("showAllAvailablePages");
    final boolean includeLinkToManageYourVotes = (Boolean)request.getAttribute("includeLinkToManageYourVotes");
    final Integer crossWidgetId = (Integer)request.getAttribute("crossWidgetId");
    final List<ManageVotesForVotingSettings> manageVotesLinks = (List<ManageVotesForVotingSettings>) request.getAttribute("manageVotesLinks");
%>
<select id="selectAManageYourVotesPage" style="width:250px;"
        <% if (!includeLinkToManageYourVotes) { %>disabled="disabled" <% } %>>
    <option value="-1">
        <international:get name="selectAPage"/>
    </option>
    <% for (ManageVotesForVotingSettings manageVotesLink : manageVotesLinks) { %>
    <option value="<%= manageVotesLink.getCrossWidgetId() %>"
            <% if (crossWidgetId != null && crossWidgetId == manageVotesLink.getCrossWidgetId()) { %>
            selected="selected" <% } %>>
        <%= (showSiteName ? manageVotesLink.getSiteName() + " / " : "") + manageVotesLink.getPageName() + " / [" + manageVotesLink.getManageVotesName() + "]"  %>
    </option>
    <% } %>
</select>