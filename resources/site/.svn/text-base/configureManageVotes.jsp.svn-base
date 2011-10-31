<%@ page import="com.shroggle.presentation.manageVotes.ConfigureManageVotesService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="manageVotes"/>
<%
    final ConfigureManageVotesService service = (ConfigureManageVotesService) request.getAttribute("manageVotesService");

    final String description = service.getSelectedManageVotes().getDescription();
    final boolean showDescription = service.getSelectedManageVotes().isShowDescription();
%>
<div class="extendedItemSettingsWindowDiv">
    <div id="ManageVotesHeader" style="display:none">
        <%= description %>
    </div>
    <input type="hidden" id="showManageVotesHeader" value="<%= showDescription %>"/>
    <input type="hidden" id="selectedManageVotesId" value="<%= service.getSelectedManageVotes().getId() %>"/>
    <input type="hidden" id="manageVotesSiteId" value="<%= service.getSite().getSiteId() %>"/>
    <input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
    <input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
    <input type="hidden" value="<international:get name="ManageVotesNotSelectedException"/>"
           id="ManageVotesNotSelectedException"/>

    <h1><international:get name="subHeader"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="manageVotesService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="manageVotesErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="manageVotesReadOnlyMessage">You have only read-only
        access to this module.</div>

    <div class="inform_mark">
        <international:get name="firstInstruction"/>
    </div>

    <div style="width:100%">
        <label for="manageVotesName" style="width:5%;"><international:get
                name="manageVotesName"/></label>
        <input class="title" style="width:40%;" type="text" maxlength="255"
               id="manageVotesName" value="<%=service.getSelectedManageVotes().getName()%>">
        <span style="padding-left:100px;">
            <label for="manageVotesDescription"
                   onmouseover="bindTooltip({element:this, contentId:'ManageVotesHeader'});"><international:get
                    name="manageVotesDescription"/></label>
            <a id="manageVotesDescription"
               onmouseover="bindTooltip({element:this, contentId:'ManageVotesHeader'});"
               href="javascript:showConfigureItemDescription({id:'ManageVotes'});">
                <international:get name="editDesc"/>
            </a>
        </span>
    </div>
    <br/>

    <div id="selectVotingModules">
        <h2>
            <international:get name="selectVotingModulesInfo"/><a style="margin-left:5px"
                                                                  href="javascript:showVotingModulesMoreInfo()"><international:get
                name="moreInfo"/></a>

            <div style="display:none" id="votingModulesMoreInfoText">
                <div class="windowOneColumn">
                    <international:get name="votingModulesMoreInfoText"/>
                    <div align="right">
                        <input type="button" class="but_w73" value="Close" onmouseover="this.className='but_w73_Over';"
                               onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();"/>
                    </div>
                </div>
            </div>
        </h2>
        <input <% if (service.getSelectedManageVotes().isShowVotingModulesFromCurrentSite()) { %>checked="checked"<% } %>
               type="radio"
               name="votingModuleRadio" id="currentSiteOnly" onclick="getAvailableManageVotesGallerySettingsList();"/>
        <label for="currentSiteOnly"><international:get name="currentSiteOnly"/></label>
        <br/>
        <input <% if (!service.getSelectedManageVotes().isShowVotingModulesFromCurrentSite()) { %>checked="checked"<% } %>
               type="radio" name="votingModuleRadio" id="allAvailble"
               onclick="getAvailableManageVotesGallerySettingsList();"/>
        <label for="allAvailble"><international:get name="allAvailble"/></label>
    </div>

    <div id="votingModulesTable" style="margin-top:15px">
        <div class="inform_mark">
            <international:get name="votingModulesTableInstruction"/>
        </div>
        <div id="manageVotesGallerySettingsTable">
            <% request.setAttribute("availableGalleriesWithWidgets", service.getAvailableGalleriesWithWidgets());%>
            <jsp:include page="manageVotesGallerySettingsList.jsp"/>
        </div>
        <div style="margin-top:10px">
            <input type="checkbox"
                   <% if (service.getSelectedManageVotes().isPickAWinner()) { %>checked="checked"<% } %>
                   id="pickAWinner"/><label for="pickAWinner"><international:get
                name="pickAWinner"/></label><a style="margin-left:5px"
                                               href="javascript:showPickAWinnerMoreInfo()"><international:get
                name="moreInfo"/></a>

            <div style="display:none" id="pickAWinnerMoreInfo">
                <div class="windowOneColumn">
                    <international:get name="pickAWinnerMoreInfoText"/>
                    <div align="right">
                        <input type="button" class="but_w73" value="Close" onmouseover="this.className='but_w73_Over';"
                               onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureManageVotesButtons">
        <input type="button" value="<international:get name="apply"/>" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               id="windowApply" onclick="configureManageVotes.save(false);">
        <input type="button" value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               id="windowSave" onclick="configureManageVotes.save(true);">
        <input type="button" value="<international:get name="cancel"/>" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               id="windowCancel" onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>
