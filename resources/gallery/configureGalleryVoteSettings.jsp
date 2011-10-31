<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGallery"/>

<%@ page import="com.shroggle.entity.DraftItem" %>
<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page import="com.shroggle.entity.VoteSettings" %>
<%@ page import="com.shroggle.presentation.gallery.ConfigureGalleryService" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%--
 @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ConfigureGalleryService votingService = (ConfigureGalleryService) request.getAttribute("galleryService");
    final boolean includesVotingModule = votingService.getGallery().isIncludesVotingModule();
    final VoteSettings voteSettings = votingService.getGallery().getVoteSettings();
%>
<input type="hidden" value="<%= votingService.getSiteManager().getId() %>" id="siteId">
<input type="hidden" id="votingAudioVideoTypes"
       value="<%= FormItemName.AUDIO_FILE_UPLOAD %>;<%= FormItemName.VIDEO_FILE_UPLOAD %>">

<input type="hidden" value="<international:get name="wrongStartDate"/>" id="wrongStartDate">
<input type="hidden" value="<international:get name="wrongEndDate"/>" id="wrongEndDate">
<input type="hidden" value="<international:get name="endBeforeStart"/>" id="endBeforeStart">
<input type="hidden" value="<international:get name="endDatePassed"/>" id="endDatePassed">

<%------------------------------------------------include voting--------------------------------------------------%>
<div style="margin-bottom:25px;">
    <input type="checkbox" style="vertical-align: middle; padding:0;margin:0;" id="includesVotingModule" onclick="disableVotingArea(!this.checked);"
    <% if (includesVotingModule) { %>checked="checked" <% } %>>
    <label for="includesVotingModule" style="font-weight:bold;position:relative;top:2px">
        <international:get name="galleryIncludesVotingModule"/>
    </label>

    <div style="margin-left: 10px; margin-top: 10px;">
        <international:get name="allowsToVote"/>
    </div>
</div>
<%------------------------------------------------include voting--------------------------------------------------%>
<div id="votingSettingsTab">

<div>

<%----------------------------------------------------left div----------------------------------------------------%>
<div style="float:left; width:50%;">
    <div style="font-weight:bold;margin-bottom:5px;">
        <international:get name="voteStarsAndTextLinksHeader"/>
    </div>
    <div style="font-weight:bold;margin-bottom:15px;">
        <international:get name="byDefaultTheyWillDisplayBelowThisArea"/>
        &nbsp;
        <a href="javascript:editVotingLinkLocation();">
            <international:get name="editLocation"/>
        </a>
    </div>

    <%-----------------------------------------display vote data publicly-----------------------------------------%>
    <div style="font-weight:bold;margin-bottom:5px;">
        <input type="checkbox" id="displayVoteDataPublicly"
               <% if (voteSettings.isDisplayVote()) { %>checked="checked" <% } %>>
        &nbsp;
        <label for="displayVoteDataPublicly">
            <international:get name="displayVoteDataPublicly"/>
        </label>
    </div>
    <%-----------------------------------------display vote data publicly-----------------------------------------%>

    <%------------------------------------------display comments publicly-----------------------------------------%>
    <div style="font-weight:bold;margin-bottom:5px;">
        <input type="checkbox" id="displaySiteVisitorCommentsPublicly"
               <% if (voteSettings.isDisplayComments()) { %>checked="checked" <% } %>>
        &nbsp;
        <label for="displaySiteVisitorCommentsPublicly">
            <international:get name="displaySiteVisitorCommentsPublicly"/>
        </label>
    </div>
    <%------------------------------------------display comments publicly-----------------------------------------%>

    <%---------------------------Include a Link to an existing `Manage Your Votes` page---------------------------%>
    <div style="margin-bottom:15px;">
        <div style="font-weight:bold;margin-bottom:5px;">
            <input type="checkbox" id="includeManageYourVotesLink"
                   onchange="includeManageYourVotesLink(this.checked);"
                   <% if (voteSettings.isIncludeLinkToManageYourVotes()) { %>checked="checked" <% } %>>
            &nbsp;
            <label for="includeManageYourVotesLink">
                <international:get name="includeManageYourVotesLink"/>
            </label>
        </div>
        <%------------------------------------Select a Manage Your Votes page-------------------------------------%>
        <div style="margin-left:25px">
            <div style="margin-bottom:10px;">
                <input type="checkbox" id="showPagesFromAllAvailableSites" onchange="showAllPages(this.checked);"
                       <% if (voteSettings.isShowAllAvailablePages()) { %>checked="checked" <% } %>
                       <% if (!voteSettings.isIncludeLinkToManageYourVotes()) { %>disabled="disabled" <% } %>>
                &nbsp;
                <label for="showPagesFromAllAvailableSites">
                    <international:get name="showPagesFromAllAvailableSites"/>
                </label>
            </div>
            <div style="font-weight:bold;margin-bottom:5px;">
                <international:get name="selectAManageYourVotesPage"/>
            </div>

            <div id="manageVotesSelect">
                <jsp:include page="manageVotesLinks.jsp" flush="true"/>
            </div>
        </div>
        <%------------------------------------Select a Manage Your Votes page-------------------------------------%>
    </div>
    <%---------------------------Include a Link to an existing `Manage Your Votes` page---------------------------%>

    <%----------------------------------Site Visitor must be registered to vote-----------------------------------%>
    <div style="margin-bottom:15px;">
        <h2><international:get name="mustBeRegisteredSubHeader"/></h2>
        <%---------------------------------Select a Registration form for voters----------------------------------%>
        <div style="margin-left:25px;">
            <div style="font-weight:bold;margin-bottom:5px;">
                <international:get name="selectARegistrationFormForVoters"/>
            </div>
            <select id="selectARegistrationFormForVoters" style="width:250px;">
                <option value="-1">
                    <international:get name="selectRegistrationForm"/>
                </option>
                <%  final int selectedFormId = voteSettings.getRegistrationFormIdForVoters() != null ? voteSettings.getRegistrationFormIdForVoters() : votingService.getDefaultFormId();
                    for (DraftItem registrationForm : votingService.getRegistrationFormsForVoters()) { %>
                <option value="<%= registrationForm.getFormId() %>"
                        <% if (selectedFormId == registrationForm.getFormId()) { %>
                        selected="selected" <% } %>>
                    <%= HtmlUtil.limitName(registrationForm.getName()) %>
                </option>
                <% } %>
            </select>
        </div>
        <div style="margin-left:130px;">
            <a href="javascript:showMoreInfoForNetworkSites();">
                <international:get name="moreInfoForNetworkSites"/>
            </a>
        </div>
        <%---------------------------------Select a Registration form for voters----------------------------------%>
    </div>
    <%----------------------------------Site Visitor must be registered to vote-----------------------------------%>

</div>
<%----------------------------------------------------left div----------------------------------------------------%>
<%---------------------------------------------------right div----------------------------------------------------%>
<div style="float:left; width:50%;margin-bottom:10px">
    <%----------------------------------------------Duration of Vote----------------------------------------------%>
    <div style="margin-bottom:5px;font-weight:bold;">
        <international:get name="durationOfVote"/>
    </div>
    <div style="margin-bottom:5px;">
        <input type="radio" id="indefinite" name="durationOfVote"
               onclick="disableStartEndDate(document.getElementById('limited').checked);"
            <% if (!voteSettings.isDurationOfVoteLimited()) { %> checked="checked" <% } %>>
        &nbsp;
        <label for="indefinite">
            <international:get name="indefinite"/>
        </label>
    </div>
    <div style="margin-bottom:5px;">
        <input type="radio" id="limited" name="durationOfVote"
               onclick="disableStartEndDate(this.checked);"
            <% if (voteSettings.isDurationOfVoteLimited()) { %> checked="checked" <% } %>>
        &nbsp;
        <label for="limited">
            <international:get name="limited"/>
        </label>
        <%------------------------------------------start date, end date------------------------------------------%>
        <div style="margin-top:5px;margin-left:25px;">
            <label for="startDateText">
                &nbsp;<international:get name="startDate"/>
            </label>
            <input type="text" id="startDateText" style="width:70px;" maxlength="10"
                   onclick="if(this.value == '<international:get name="dateFormat"/>') {this.value = '';}"
                   onblur="if(this.value == '') {this.value = '<international:get name="dateFormat"/>';}"
                   onkeypress="return numbersOrSlashOnly(this, event);"
                <% if (!voteSettings.isDurationOfVoteLimited()) { %> disabled="disabled" <% } %>
                   value='<% if (!voteSettings.isDurationOfVoteLimited() || voteSettings.getStartDate() == null) { %><international:get name="dateFormat"/><% } else { %><%= DateUtil.toMonthDayAndYear(voteSettings.getStartDate()) %><% } %>'>
            &nbsp;
            <label for="endDateText">
                &nbsp;<international:get name="endDate"/>
            </label>
            <input type="text" id="endDateText" style="width:70px;" maxlength="10"
                   onclick="if(this.value == '<international:get name="dateFormat"/>') {this.value = '';}"
                   onblur="if(this.value == '') {this.value = '<international:get name="dateFormat"/>';}"
                   onkeypress="return numbersOrSlashOnly(this, event);"
                <% if (!voteSettings.isDurationOfVoteLimited()) { %> disabled="disabled" <% } %>
                   value="<% if (!voteSettings.isDurationOfVoteLimited() || voteSettings.getEndDate() == null) { %><international:get name="dateFormat"/><% } else { %><%= DateUtil.toMonthDayAndYear(voteSettings.getEndDate()) %><% } %>">
        </div>
        <%------------------------------------------start date, end date------------------------------------------%>
    </div>
    <%---------------------------------------Precautions against poll fraud---------------------------------------%>
    <div id="precautionsAgainstPollFraud">
        <div style="font-weight:bold;margin-bottom:5px;">
            <international:get name="precautionsAgainstPollFraud"/>
        </div>
        <div style="margin-bottom:15px;">
            <international:get name="toVoteOnAMovieOrAudio"/>
        </div>

        <%--------------------------------------Minimum number of media items played--------------------------------------%>
        <div style="margin-bottom:10px;">
            <div style="float:left;margin-right:23px;">
                <international:get name="minimumNumberOfMediaItemsPlayed"/>
            </div>
            <div style="float:left;">
                <select id="minimumNumberOfMediaItemsPlayed">
                    <% for (int i = 0; i < 26; i++) { %>
                    <option value="<%= i %>" <% if (voteSettings.getMinimumNumberOfMediaItemsPlayed() == i) { %>
                            selected="selected" <% } %>>
                        <%= i %>
                    </option>
                    <% } %>
                </select>
            </div>
            <div style="clear:both;">
            </div>
            <input type="checkbox" id="minimumNumberAppliesToCurrentFilterOnly"
                   <% if(voteSettings.isMinimumNumberAppliesToCurrentFilter()) { %>checked="checked"<% } %>>&nbsp;
            <label for="minimumNumberAppliesToCurrentFilterOnly">
                <international:get name="minimumNumberAppliesToCurrentFilterOnly"/>
            </label>
        </div>
        <%--------------------------------------Minimum number of media items played--------------------------------------%>

        <%---------------------------------------Minimum percentage of total played---------------------------------------%>
        <div>
            <div style="float:left;margin-right:50px;">
                <international:get name="minimumPercentageOfTotalPlayed"/>
            </div>
            <div style="float:left;">
                <select id="minimumPercentageOfTotalPlayed">
                    <option value="5" <% if (voteSettings.getMinimumPercentageOfTotalPlayed() == 5) { %>
                            selected="selected" <% } %>>
                        5%
                    </option>
                    <option value="10" <% if (voteSettings.getMinimumPercentageOfTotalPlayed() == 10) { %>
                            selected="selected" <% } %>>
                        10%
                    </option>
                    <option value="20" <% if (voteSettings.getMinimumPercentageOfTotalPlayed() == 20) { %>
                            selected="selected" <% } %>>
                        20%
                    </option>
                    <% for (int i = 25; i < 101; i += 25) { %>
                    <option value="<%= i %>" <% if (voteSettings.getMinimumPercentageOfTotalPlayed() == i) { %>
                            selected="selected" <% } %>>
                        <%= i %>%
                    </option>
                    <% } %>
                </select>
            </div>
            <div style="clear:both;">
            </div>
        </div>
        <%---------------------------------------Minimum percentage of total played---------------------------------------%>
    </div>
    <%---------------------------------------Precautions against poll fraud---------------------------------------%>
</div>
<%---------------------------------------------------right div----------------------------------------------------%>
<div style="clear:both;"></div>
</div>

<div style="display:none;" id="moreInfoTextDiv">
    <div class="windowOneColumn">
        <international:get name="moreInfoText"/>
        <div style="text-align:center;margin-top:15px;">
            <input type="button" value="<international:get name="close"/>"
                   class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   onclick="closeConfigureWidgetDiv();">
        </div>
    </div>
</div>
</div>