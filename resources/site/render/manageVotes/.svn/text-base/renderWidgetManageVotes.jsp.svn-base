<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.shroggle.logic.manageVotes.ManageVotesManager" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.gallery.voting.VotingStarsData" %>
<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.logic.gallery.comment.GalleryCommentsManager" %>
<%@ page import="com.shroggle.logic.manageVotes.ManageVotesGallerySettingsManager" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page import="com.shroggle.presentation.gallery.comment.GalleryCommentData" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.shroggle.presentation.gallery.GalleryNavigationUrl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderWidgetManageVotes"/>
<%
    final Widget widget = (Widget) request.getAttribute("widget");
    final ManageVotes manageVotes = (ManageVotes) request.getAttribute("manageVotes");
    final User loginedUser = new UsersManager().getLoginedUser();
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
    final ManageVotesManager manageVotesManager = new ManageVotesManager(manageVotes, siteShowOption);

    final List<ManageVotesGallerySettingsManager> managers = new ArrayList<ManageVotesGallerySettingsManager>();
    int totalVotesCount = 0;
    for (ManageVotesSettings manageVotesGallerySettings : manageVotesManager.getManageVotesGallerySettingsList()) {
        ManageVotesGallerySettingsManager manager = new ManageVotesGallerySettingsManager(manageVotesGallerySettings, siteShowOption);
        managers.add(manager);
        totalVotesCount += manager.getVotingStarsData().size();
    }
%>
<div class="manageVotesBlock blockToReload<%= widget.getWidgetId() %>" id="manageVotesBlock<%= widget.getWidgetId() %>">
    <% if (loginedUser != null) {
        int result = manageVotes.getId();
    %>
    <input type="hidden" class="manageVotesId" value="<%= result %>"/>
    <input type="hidden" class="siteShowOption" value="<%= siteShowOption %>"/>    

    <h4><international:get name="header"/></h4>
    <% if (totalVotesCount == 0) { %>
    <international:get name="subHeaderNoVotes"/>
    <% } else { %>
    <h5>
        <international:get name="subHeader">
            <international:param value="<%= totalVotesCount %>"/>
            <international:param value="<%= DateUtil.toDayMonthAndShortYear(new Date())%>"/>
        </international:get>
    </h5>
    <% } %>
    <% if (!StringUtil.isNullOrEmpty(manageVotes.getDescription()) && manageVotes.isShowDescription()) { %>
    <%= manageVotes.getDescription() %>
    <% } %>
    <% if (manageVotes.isPickAWinner()) { %>
    <h4 style="font-weight:bold;color:#983A2C;margin-top:30px;"><international:get name="pickAWinnerHeader"/></h4>
    <h5><international:get name="pickAWinnerSubheader"/></h5>

    <div class="winnerTableDiv">
        <% request.setAttribute("winnerList", manageVotesManager.constructWinnerList()); %>

        <jsp:include page="winnerList.jsp"/>
    </div>
    <% } %>
    <%
        for (ManageVotesGallerySettingsManager manageVotesSettingsManager : managers) {
            if (manageVotesSettingsManager.getVotingStarsData().size() > 0) {
    %>
    <hr style="margin-top:35px;"/>
    <div id="afterVoteMessageBlock<%= manageVotesSettingsManager.getGalleryId() %>"
         style="color:green;display:none;font-weight:bold">
        <international:get name="afterVoteMessage"/>
    </div>
    <% if (!manageVotesSettingsManager.isIncludesVotingModule()) { %>
    <div style="color:red;">
        <input id="votingEndedMsg<%= widget.getWidgetId() %>" type="hidden"
               value="<international:get name="votingEndedMsg">
               <international:param value="<%= manageVotesSettingsManager.getCustomName() %>"/>
               </international:get>"/>
        <international:get name="votingIsOver">
            <international:param value="<%= manageVotesSettingsManager.getCustomName() %>"/>
        </international:get>
    </div>
    <% } %>
    <table style="width:100%;font-size:10pt;border-collapse:collapse;margin-top:5px;"
           galleryId="<%= manageVotesSettingsManager.getGalleryId() %>">
        <thead>
        <tr>
            <td <% if (manageVotes.isPickAWinner()) { %>colspan="4" <% } else { %>colspan="3"<% } %>
                style="background-color:<%= manageVotesSettingsManager.getColorCode() %>;padding:5px">
                <a href="<%= manageVotesSettingsManager.createGalleryLink() %>"><%= manageVotesSettingsManager.getGalleryName() %>
                </a>
            </td>
        </tr>
        <tr>
            <% if (manageVotes.isPickAWinner()) { %>
            <td style="background-color:<%= manageVotesSettingsManager.getColorCode() %>;padding:5px;color:black"
                width="5%"></td>
            <% } %>
            <td style="background-color:<%= manageVotesSettingsManager.getColorCode() %>;" class="manageVotesRecordNameHeader"
                width="40%">
                <international:get name="itemName"/>
            </td>
            <td style="background-color:<%= manageVotesSettingsManager.getColorCode() %>;" class="manageVotesYourRatingHeader"
                width="10%">
                <international:get name="yourRating"/>
            </td>
            <td style="background-color:<%= manageVotesSettingsManager.getColorCode() %>;" class="manageVotesViewEditCommentsHeader"
                width="35%">
                <international:get name="viewEditComments"/>
            </td>
        </tr>
        </thead>
        <tbody>
        <% for (VotingStarsData votingStarsData : manageVotesSettingsManager.getVotingStarsData()) { %>
        <tr voteId="<%= votingStarsData.getVoteData().getVoteId() %>">
            <% if (manageVotes.isPickAWinner()) { %>
            <td <% if (votingStarsData.getVoteData().isWinner()) { %>style="background-color:<%= manageVotesSettingsManager.getColorCode() %>"<% } %>>
                <img <% if (votingStarsData.getVoteData().isWinner()) { %>class="winner" src="/images/winnerIcon.gif"
                     <% } else { %>src="/images/winnerIcon-deselect.gif"<% } %>
                     alt="Pick a Winner" style="width:25px;height:25px;cursor:pointer"
                     onclick="selectWinner(this, '<%= manageVotesSettingsManager.getColorCode() %>');"/>
            </td>
            <% } %>
            <td <% if (manageVotes.isPickAWinner() && votingStarsData.getVoteData().isWinner()) { %>style="background-color:<%= manageVotesSettingsManager.getColorCode() %>"<% } %>>
                <% GalleryNavigationUrl galleryUrl = manageVotesSettingsManager.createGalleryItemLink(widget, votingStarsData.getVoteData().getFilledFormId()); %>
                <a onclick="<%= galleryUrl.getUserScript() %>" href="#" class="manageVotesRecordNameLink"
                        ajaxHistory="<%= galleryUrl.getAjaxDispatch() %>"><%= manageVotesSettingsManager.getRecordName(votingStarsData.getVoteData().getFilledFormId()) %>
                </a>
            </td>
            <td <% if (manageVotes.isPickAWinner() && votingStarsData.getVoteData().isWinner()) { %>style="background-color:<%= manageVotesSettingsManager.getColorCode() %>"<% } %>>
                <% request.setAttribute("votingStarsData", votingStarsData); %>
                <jsp:include page="../gallery/votingStars.jsp"/>
            </td>
            <td <% if (manageVotes.isPickAWinner() && votingStarsData.getVoteData().isWinner()) { %>style="background-color:<%= manageVotesSettingsManager.getColorCode() %>"<% } %> id="manageVotesCommentsLinks">
                <% final GalleryCommentData galleryCommentData =
                        new GalleryCommentsManager().createData(votingStarsData.getVoteData().getFilledFormId(), manageVotesSettingsManager.getGalleryId(), widget.getWidgetId()); %>
                <% request.setAttribute("galleryCommentData", galleryCommentData); %>
                <jsp:include page="/gallery/comment/comment.jsp"/>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <%
            }
        }
    %>
    <% } else {%>
    <script type="text/javascript">
        showVisitorLogin(<%= widget.getWidgetId() %>, false);
    </script>
    <% } %>
</div>

