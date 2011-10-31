<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.form.filledForms.ShowFormRecordsService" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.entity.DraftFormFilter" %>
<%@ page import="com.shroggle.presentation.form.filledForms.ManageFormRecordsTableRequest" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<international:part part="manageFormRecords"/>
<cache:no/>
<%
    final ShowFormRecordsService service = (ShowFormRecordsService) request.getAttribute("service");
    final ManageFormRecordsTableRequest manageFormRecordsRequest = service.getManageFormRecordsRequest();
%>
<div class="windowOneColumn">
    <% if (service.getForm() == null) { %>
    <international:get name="notConfigured1"/>&nbsp;<international:get
        name='<%= service.getItemType().toString() %>'/>&nbsp;<international:get
        name="notConfigured2"/>
    <br/>

    <div align="right">
        <input type="button" class="but_w73" value="Close"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();"/>
    </div>
    <% } else { %>
    <input id="manageFormRecrodsFormId" value="<%= service.getForm().getFormId() %>" type="hidden"/>
    <input id="manageFormRecordsGalleryId"
           value="<%= service.getGallery() != null ? service.getGallery().getId() : null %>" type="hidden"/>
    <input id="manageFormRecrodsFormType" value="<%= service.getForm().getType() %>" type="hidden"/>
    <input id="deleteText" type="hidden" value="<international:get name="deleteRecord"/>">

    <h1><international:get name="header"/>&nbsp;<%= service.getForm().getName() %>
    </h1>

    <% if (!StringUtil.isNullOrEmpty(service.getForm().getDescription())) { %>
    <div style="height:1.3em;font-weight:normal;overflow:hidden">
        <h2 style="display:inline;margin-left:0;"><international:get name="subHeader"/></h2>
        &nbsp;<%=  HtmlUtil.limitName(HtmlUtil.removeAllTags(service.getForm().getDescription()), 100) %>
    </div>
    <% } %>

    <% if (service.isShowFromGallery()) { %>
    <h2 style="margin-left:0;"><international:get name="gallerySubHeader"/></h2>
    <% } %>

    <br/>

    <div class="filterDiv">
        <label for="filterPickList"><international:get name="selectFilter"/></label>
        <select id="filterPickList" onchange="reloadManageRegistrantsTable();">
            <option value="-1" selected="selected"><international:get name="defaultFilter"/></option>
            <% for (DraftFormFilter formFilter : service.getFormFilters()) { %>
            <option value="<%= formFilter.getFormFilterId() %>"><%= formFilter.getName() %>
            </option>
            <% } %>
        </select>
        <a href="javascript:showAddFilter(null, null, true, true);"
           id="editFilterLink"><international:get name="editFormFilter"/></a>
        <a href="javascript:showAddFilter(null, <%= service.getForm().getFormId() %>, false, true);"
           id="createFilterLink"><international:get
                name="createNewFormFilter"/></a>
    </div>

    <div align="right">
        <label for="search"><international:get name="search"/></label>
        <input type="text" id="search" class="txt" maxlength="255"
               onkeyup="processKeyManageFormRecords(this.value);"/>

        <div class="mark" style="display:inline;margin:0"
             onmouseover="this.className = 'mark_over';" onmouseout="this.className= 'mark';"
             onclick="showHelpWindow();">
            &nbsp;</div>

        <div style="display:inline;visibility:hidden;" id="show_all_div"><a
                href="javascript:resetSearchAndFilter()"><international:get
                name="showAll"/></a></div>
    </div>

    <br clear="all">

    <h2 style="display:inline;margin-left:0;"><international:get name="editRecordSubHeader"/></h2> <a
        href="javascript:editRecordMoreInfo()"><international:get name="moreInfo"/></a>

    <div id="hideRecordInfoDiv" class="inlineSuccess" style="margin-top:5px;font-size:14px;margin-left:10px;">
        <international:get name="hideRecordInfo"/>
    </div>

    <div style="float:right;">
        <a href="javascript:customizeManageRecords.showCustomizeManageRecordsWindow(<%= service.getForm().getFormId() %>);"><international:get
                name="customizeRecordsView"/></a>
    </div>
    <div style="clear:both;"></div>

    <div id="manageFormRecordsTableDiv" class="manageFormRecordsDiv">
        <% request.setAttribute("manageFormRecordsTableRequest", manageFormRecordsRequest); %>
        <jsp:include page="manageFormRecordsTable.jsp" flush="true"/>
    </div>
    <br clear="all">

    <div align="left" style="float:left;">
        <input type="button" class="but_w130" value="<international:get name="editForm"/>"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="editFormFromManageRegistrants('<%= service.getForm().getItemType() %>', <%= service.getForm().getFormId() %>);"/>
    </div>

    <div align="right" style="float:right;">
        <input type="button" class="but_w130" value="<international:get name="exportData"/>"
               id="exportButton"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="manageDataExports.showManageDataExportsWindow(<%= service.getForm().getFormId() %>);"/>
        <input type="button" class="but_w130" value="<international:get name="bulkUpload"/>"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               id="bulkUploadButton"
                <% if (manageFormRecordsRequest.isShowBulkUpload()) { %>
               onclick="showBulkUploadWindow(<%= service.getForm().getFormId() %>, <%= manageFormRecordsRequest.getImageFormItemId() %>);"
                <% } else { %>
               onclick="alert('<international:get name="thisFormHasNoImageFields"/>');"
                <% } %>
                />
        <input type="button" class="but_w130" value="Add New Record"
               id="addRecordButton"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="showAddNewRecordWindow(<%= service.getForm().getFormId() %>);"/>
        <span id="readOnlyAccessFormSpan" style="display:none;margin-right:15px;font-size:14px;"><international:get
                name="readOnlyAccessFormSpan"/></span>
        <input type="button" class="but_w73" value="<international:get name="close"/>"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();"/>
    </div>
    <div style="clear:both;"></div>
    <% } %>
</div>

<div id="helpWindow" style="display:none;padding : 0 0 5px 0">
    <div class="windowOneColumn">
        <div style="overflow:auto;height:50px; width:460px; padding:10px; text-align:left;">
            <div id="helpText"><international:get name="helpText"/></div>
        </div>
        <p align="center"><input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                                 onmouseout="this.className='but_w73';"
                                 value="<international:get name="close"/>" onclick="closeConfigureWidgetDiv();"/></p>
        <br>
    </div>
</div>

<div id="editRecordMoreInfoDiv" style="display:none;">
    <div class="windowOneColumn">
        <div class="inform_mark" style="margin:10px 0 0 0;"><international:get
                name="info"/></div>
        <div class="warning" style="margin:5px 0 5px 0;"><international:get
                name="warning"/></div>
        <div align="right">
            <input type="button" value="Close" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();">
        </div>
    </div>
</div>