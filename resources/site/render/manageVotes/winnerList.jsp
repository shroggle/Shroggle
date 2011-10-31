<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.manageVotes.WinnerInfo" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.logic.manageVotes.ManageVotesGallerySettingsManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.presentation.gallery.GalleryNavigationUrl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final Widget widget = (Widget) request.getAttribute("widget"); %>
<% final List<WinnerInfo> winners = (List<WinnerInfo>) request.getAttribute("winnerList"); %>
<% final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption"); %>

<table style="width:100%;font-size:10pt;color:black;border-collapse:collapse;">
    <tbody>
    <%
        for (WinnerInfo winner : winners) {
            final Vote vote = ServiceLocator.getPersistance().getVoteById(winner.getVoteId());
            final DraftGallery gallery = ServiceLocator.getPersistance().getDraftItem(vote.getGalleryId());
            final ManageVotesGallerySettingsManager manageVotesGallerySettingsManager = new ManageVotesGallerySettingsManager(winner.getManageVotesGallerySettings(), siteShowOption);
    %>
    <tr>
        <td style="background-color:<%= winner.getManageVotesGallerySettings().getColorCode() %>;padding:5px" width="5%">
            <img style="width:25px;height:25px;cursor:pointer" src="/images/winnerIcon.gif" alt="Pick a Winner"/>
        </td>
        <td style="background-color:<%= winner.getManageVotesGallerySettings().getColorCode() %>;padding:5px" width="25%">
            <a href="<%= manageVotesGallerySettingsManager.createGalleryLink() %>"><%= gallery.getName() %></a>
        </td>
        <td style="background-color:<%= winner.getManageVotesGallerySettings().getColorCode() %>;padding:5px" width="65%">
            <% GalleryNavigationUrl galleryItemUrl = manageVotesGallerySettingsManager.createGalleryItemLink(widget, vote.getFilledFormId()); %>
            <a href="#" onclick="<%= galleryItemUrl.getUserScript() %>"
                    ajaxHistory="<%= galleryItemUrl.getAjaxDispatch() %>"><%= manageVotesGallerySettingsManager.getRecordName(vote.getFilledFormId()) %></a>
        </td>
        <td style="background-color:<%= winner.getManageVotesGallerySettings().getColorCode() %>;padding:5px" width="5%">
            <img src="/images/cross-circle.png" alt="Delete" style="cursor:pointer"
                 onclick="removeWinner(this, <%= winner.getVoteId() %>);">
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
