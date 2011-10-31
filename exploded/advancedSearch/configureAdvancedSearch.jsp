<%@ page import="com.shroggle.entity.AdvancedSearchOrientationType" %>
<%@ page import="com.shroggle.entity.DraftAdvancedSearch" %>
<%@ page import="com.shroggle.entity.DraftForm" %>
<%@ page import="com.shroggle.entity.DraftGallery" %>
<%@ page import="com.shroggle.logic.gallery.GalleryManager" %>
<%@ page import="com.shroggle.logic.site.item.ItemManager" %>
<%@ page import="com.shroggle.presentation.advancedSearch.ConfigureAdvancedSearchService" %>
<%@ page import="com.shroggle.presentation.site.SiteItemByNameComparator" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="siteItems" tagdir="/WEB-INF/tags/siteItems" %>
<international:part part="advancedSearch"/>
<%
    final ConfigureAdvancedSearchService service = (ConfigureAdvancedSearchService) request.getAttribute("advancedSearchService");
    final DraftAdvancedSearch advancedSearch = service.getSelectedAdvancedSearch();

    final String notesAndComments = advancedSearch.getDescription() != null ? advancedSearch.getDescription() : "";

    boolean showNotesAndComments = advancedSearch.isDisplayHeaderComments();

    final Integer selectedGalleryId = advancedSearch.getGalleryId();

    final boolean isGallerySource = advancedSearch.getGalleryId() != -1;
    final boolean isUploadSource = advancedSearch.getGalleryId() == -1;
%>
<div class="itemSettingsWindowDiv">
    <input type="hidden" value="<%= advancedSearch.getGalleryId() %>"
           id="selectedGalleryId"/>
    <input type="hidden" value="<%= service.getSite().getSiteId() %>" id="advancedSearchSiteId"/>
    <input type="hidden" value="<%= advancedSearch.getId() %>"
           id="selectedAdvancedSearchId"/>
    <input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
    <input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
    <input type="hidden" value="null" id="alreadyCreatedDefaultFormId"/>
    <input type="hidden" id="confirmSoruceChange" value="<international:get name="confirmSoruceChange"/>">
    <input type="hidden" id="AdvancedSearchGalleryNotSelectException"
           value="<international:get name="AdvancedSearchGalleryNotSelectException"/>"/>
    <input type="hidden" id="AdvancedSearchFormNotSelectException"
           value="<international:get name="AdvancedSearchFormNotSelectException"/>"/>
    <input type="hidden" id="AdvancedSearchGalleryNotSelectBeforeSaveException"
           value="<international:get name="AdvancedSearchGalleryNotSelectBeforeSaveException"/>"/>
    <input type="hidden" id="AdvancedSearchFormNotSelectBeforeSaveException"
           value="<international:get name="AdvancedSearchFormNotSelectBeforeSaveException"/>"/>

    <div id="AdvancedSearchHeader" style="display:none"><%= notesAndComments %>
    </div>
    <input type="hidden" id="showAdvancedSearchHeader" value="<%= showNotesAndComments %>"/>

    <h1><international:get name="subHeader"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="advancedSearchService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="advancedSearchErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="advancedSearchReadOnlyMessage">You have only read-only
        access to this module.</div>

    <label for="advancedSearchName"><international:get name="advancedSearchName"/></label>
    <input class="title" type="text" id="advancedSearchName" maxlength="255"
           value="<%= advancedSearch.getName() %>">
    <span style="padding-left:100px;">
    <label for="editAdvancedSearchHeader"
           onmouseover="bindTooltip({element:this, contentId:'AdvancedSearchHeader'});"><international:get
            name="advancedSearchHeader"/></label>
        <a id="editAdvancedSearchHeader"
           onmouseover="bindTooltip({element:this, contentId:'AdvancedSearchHeader'});"
           href="javascript:showConfigureItemDescription({id:'AdvancedSearch'});">
            <international:get name="editAdvancedSearchHeader"/>
        </a>
    </span>

    <div class="default_settings_header">
        <b><international:get name="advSearchSubHeader"/></b>
        <a href="javascript:configureAdvancedSearch.showMoreInfo()"><international:get name="moreInfo"/></a>

        <div style="display:none" id="advSearchMoreInfoDiv">
            <div class="windowOneColumn">
                <international:get name="advSearchExplanWindow"/>
                <div align="right">
                    <input type="button" class="but_w73" value="Close" onmouseover="this.className='but_w73_Over';"
                           onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();"/>
                </div>
            </div>
        </div>
    </div>

    <div class="default_settings_block">
        <div>
            <input type="radio" name="recordsSource" id="recordsSourceGallery"
                   onclick="configureAdvancedSearch.recordsSourceGalleryClick();"
                   <% if (isGallerySource) { %>checked="checked"<% } %>/>
            <label for="recordsSourceGallery"><international:get name="recordsSourceGallery"/></label>

            <div class="default_settings_subblock" style="margin-left:20px">
                <select id="galleriesSelect" <% if (!isGallerySource) { %>disabled="disabled"<% } %>
                        onchange="configureAdvancedSearch.gallerySelectOnChange();">
                    <option value="-1" formId="-1"><international:get name="gallerySelectDefaultOption"/></option>
                    <% Collections.sort(service.getAvailableGalleries(), SiteItemByNameComparator.instance); %>
                    <% for (final ItemManager galleryManager : service.getAvailableGalleries()) { %>
                    <% final DraftForm realGalleryForm = new GalleryManager((DraftGallery) galleryManager.getDraftItem()).getGalleryRealForm(); %>
                    <% if (realGalleryForm != null) { %>
                    <option value="<%= galleryManager.getId() %>"
                            <% if (selectedGalleryId != null && selectedGalleryId == galleryManager.getId()){ %>selected="selected"<% } %>
                            formId="<%= realGalleryForm != null ? realGalleryForm.getFormId() : null %>">
                        <%= HtmlUtil.limitName(galleryManager.getOwnerSiteName(), 40) %>
                        / <%= HtmlUtil.limitName(galleryManager.getName(), 40) %>
                    </option>
                    <% } %>
                    <% } %>
                </select>
                <a href="javascript:configureAdvancedSearch.showConfigureGalleryFromAdvSearch()"
                   id="configureGalleryLink"><international:get name="editSelectedGallery"/></a>
            </div>
        </div>

        <div class="default_settings_subblock">
            <input type="radio" name="recordsSource" id="recordsSourceUpload"
                   onclick="return configureAdvancedSearch.recordsSourceUploadClick();"
                   <% if (isUploadSource) { %>checked="checked"<% } %>/>
            <label for="recordsSourceUpload"><international:get name="recordsSourceUpload"/></label>
        </div>

        <div class="default_settings_subblock">
            <input type="radio" name="recordsSource" id="recordsSourceForm"
                   onclick="return configureAdvancedSearch.recordsSourceFormClick();"/>
            <label for="recordsSourceForm"><international:get name="recordsSourceForm"/></label>

            <div class="default_settings_subblock" style="margin-left:20px">
                <select id="formsSelect" disabled="disabled"
                        onchange="configureAdvancedSearch.formsSelectOnChange();">
                    <option value="-1"><international:get name="formsSelectDefaultOption"/></option>
                    <siteItems:asOptions value="<%= service.getAvailableForms() %>"/>
                </select>
            </div>
        </div>
    </div>

    <div class="default_settings_header">
        <international:get name="searchOptionsSubHeader"/>
    </div>

    <div class="default_settings_block" id="displayTypeDiv">
        <table style="width:650px;height:140px;">
            <tr>
                <td>
                    <input type="radio" name="displayType" id="displayTypeABOVE"
                           value="<%= AdvancedSearchOrientationType.ABOVE %>"
                           <% if (advancedSearch.getAdvancedSearchOrientationType() == AdvancedSearchOrientationType.ABOVE) { %>checked="checked"<% } %>/>
                    <label for="displayTypeABOVE"><international:get name="displayTypeABOVE"/></label>
                </td>
                <td>
                    <input type="radio" name="displayType" id="displayTypeLEFT"
                           value="<%= AdvancedSearchOrientationType.LEFT %>"
                           <% if (advancedSearch.getAdvancedSearchOrientationType() == AdvancedSearchOrientationType.LEFT) { %>checked="checked"<% } %>/>
                    <label for="displayTypeLEFT"><international:get name="displayTypeLEFT"/></label>
                </td>
            </tr>
            <tr>
                <td>
                    <img src="/images/AdvSearchOrientationAbove.jpg" alt=""/>
                </td>
                <td>
                    <img src="/images/AdvSearchOrientationLeft.jpg" alt=""/>
                </td>
            </tr>
        </table>
    </div>

    <div class="default_settings_block">
        <a href="javascript:configureAdvancedSearch.showManageRecordsFromAdvSearch()"
           id="manageRecordsLink"><international:get name="manageRecords"/></a>
        <a class="mark2" style="margin-left:10px"
           href="javascript:configureAdvancedSearch.showEditGalleryAndRecordsExplan()">
            <international:get name="whatsThis"/>
        </a>

        <div style="display:none" id="editGalleryAndRecordsExplanDiv">
            <div class="windowOneColumn">
                <international:get name="whatsThisWindow"/>
                <div align="right">
                    <input type="button" class="but_w73" value="Close"
                           onmouseover="this.className='but_w73_Over';"
                           onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();"/>
                </div>
            </div>
        </div>
    </div>

    <div class="default_settings_header">
        <international:get name="defineSearchOptionsSubHeader"/>
    </div>

    <div class="default_settings_block">
        <div class="inform_mark">
            <international:get name="defineSearchOptionExplan"/>
        </div>

        <div class="default_settings_subblock">
            <input type="button" id="editSearchOptionsButton" value="<international:get name="editSearchOptions"/>"
                   onmouseout="this.className='but_w230_misc';" onclick="configureAdvancedSearch.showEditOptions()"
                   onmouseover="this.className='but_w230_Over_misc';" class="but_w230_misc">
        </div>

        <div class="default_settings_subblock">
            <input type="checkbox" id="includeResultsNumber"
                   <% if (advancedSearch.isIncludeResultsNumber()) { %>checked="checked"<% } %>/>
            <label for="includeResultsNumber"><international:get name="includeResultsNumber"/></label>
        </div>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureAdvancedSearchButtons">
        <input type="button" value="Apply" id="windowApply" onclick="configureAdvancedSearch.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onclick="configureAdvancedSearch.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" id="windowCancel" value="Cancel" onclick="configureAdvancedSearch.close();"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>
