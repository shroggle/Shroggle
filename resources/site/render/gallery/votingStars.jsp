<%@ page import="com.shroggle.logic.gallery.voting.VoteManager" %>
<%@ page import="com.shroggle.logic.gallery.voting.VotingStarsData" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="votingStars"/>
<%--
 @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final VotingStarsData votingStarsData = (VotingStarsData) request.getAttribute("votingStarsData");
    final boolean disableVoting = votingStarsData.isDisabled();
    final int widgetId = votingStarsData.getWidgetId();
    final String compositId = widgetId + "" + votingStarsData.getVoteData().getFilledFormId() + "" + votingStarsData.getGalleryId();
    final String videoPlayerId = (String) request.getAttribute("videoPlayerId");
%>

<div id="votingStars<%= compositId %>" voting="voting" class="galleryDataVotingStars"
     style="width:<%= (VoteManager.getMaxVoteValue() * 19) + "px" %>;">
    <div style="float:left;"
            <% if (!votingStarsData.isFilledFormExist()) { %>
         onclick="alert('<international:get name="atLeastOneItemShouldBePresent"/>');"
            <% } %>
         <% if (votingStarsData.isVotingEnded()) { %>onclick="alert(document.getElementById('votingEndedMsg<%= widgetId%>').value);"<% } %>
            <% if (votingStarsData.isWrongStartOrEndDate()) { %>
         onclick="alert('<international:get name="votingIsNotAvailable"><international:param value="<%= votingStarsData.getStartDate() %>"/><international:param value="<%= votingStarsData.getEndDate() %>"/></international:get>');"
            <% } %>>
        <international:get name="yourVote"/>
        <br>
        <% for (int i = 1; i <= VoteManager.getMaxVoteValue(); i++) { %>
        <input filledFormId="<%= votingStarsData.getVoteData().getFilledFormId() %>"
               galleryId="<%= votingStarsData.getGalleryId() %>"
               siteId="<%= votingStarsData.getSiteId() %>"
               voteValue="<%= i %>"
               compositId="<%= compositId %>"
               voteId="<%= votingStarsData.getVoteData().getVoteId() %>"
               disableVoting="<%= disableVoting %>"
               widgetId="<%= widgetId %>"
               videoPlayerId="<%= videoPlayerId %>"
               name="star<%= compositId %>" type="radio"
               class="star {required:true, callback:vote}" value=""
               style="visibility:hidden;"
               <% if (disableVoting) { %>disabled="disabled" <% } %>
               <% if (votingStarsData.getVoteData().getVoteValue() == i) { %>checked="checked" <% } %>/>
        <% } %>
    </div>
    <div style="clear:both;"></div>
</div>


<%---------------------------------------------------hidden fields----------------------------------------------------%>
<input id="youHaveToBeLoggedIn<%= widgetId %>" type="hidden" value="<international:get name="youHaveToBeLoggedIn"/>">
<input id="enterCorrectVoteValue<%= widgetId %>" type="hidden"
       value="<international:get name="enterCorrectVoteValue"/>">
<input id="notEnoughWatchedFiles<%= widgetId %>" type="hidden"
       value="<international:get name="notEnoughWatchedFiles"/>">
<input id="registrationFormIdForVoters<%= widgetId %>" type="hidden"
       value="<%= votingStarsData.getVoteSettings().getRegistrationFormIdForVoters() %>">
<%---------------------------------------------------hidden fields----------------------------------------------------%>
