<%@ page import="com.shroggle.logic.gallery.voting.VotingLinksData" %>
<%@ page import="com.shroggle.logic.gallery.GalleryData" %>
<%@ page import="com.shroggle.logic.gallery.comment.GalleryCommentsManager" %>
<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.entity.User" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="votingLinks"/>
<%--
 @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final GalleryData linksGalleryData = (GalleryData) request.getAttribute("galleryData");
    final Widget widget = (Widget) request.getAttribute("widget");
    final int widgetId = widget.getWidgetId();
    final VotingLinksData votingLinksData = (VotingLinksData) request.getAttribute("votingLinksData");
    final User loginedUser = new UsersManager().getLoginedUser();
    final int filledFormId = linksGalleryData.getFilledFormId();
    final int siteId = ServiceLocator.getPersistance().getWidget(widgetId).getSite().getSiteId();
%>
<div class="galleryDataVotingLinksDiv">

    <%--------------------------------------------Manage Your Votes link----------------------------------------------%>
    <% if (votingLinksData.isIncludeLinkToManageYourVotes()) { %>
    <div class="galleryDataManageYourVotesLink">
        <a      <% if (new UsersManager().isUserLoginedAndHasRightsToSite(siteId)) { %>
                href="<%= votingLinksData.getManageVotesUrl() %>"
                <% } else { %>
                href="javascript:showRegistrationBlockIfConfirm(<%= widgetId %>)"
                <% } %>><international:get name="manageYourVotes"/></a>
    </div>
    <% } %>
    <%--------------------------------------------Manage Your Votes link----------------------------------------------%>

    <%--------------------------------------------Register / Login link-----------------------------------------------%>
    <% if (loginedUser == null) { %>
    <div class="galleryDataRegisterLoginLink">
        <a href="javascript:showGalleryRegistrationBlock(<%= widgetId %>)"><international:get name="registerLogin"/></a>
    </div>
    <% } %>
    <%--------------------------------------------Register / Login link-----------------------------------------------%>

    <%-----------------------------------------------Comments links---------------------------------------------------%>
    <div class="galleryDataCommentsLinksDiv"
         <% if (filledFormId == 0 || filledFormId== -1) { %>style="display:none;"<% } %>>
        <% request.setAttribute("galleryCommentData", new GalleryCommentsManager().createData(
                filledFormId, linksGalleryData.getGalleryManager().getId(), widgetId)); %>
        <jsp:include page="/gallery/comment/comment.jsp"/>
    </div>
    <%-----------------------------------------------Comments links---------------------------------------------------%>
</div>