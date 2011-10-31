<%@ page import="com.shroggle.entity.DraftGallery" %>
<%@ page import="com.shroggle.entity.FormExportDataFormat" %>
<%@ page import="com.shroggle.entity.FormItem" %>
<%@ page import="com.shroggle.logic.form.export.CSVDataExportFieldManager" %>
<%@ page import="com.shroggle.logic.form.export.DataExportAndScheduleModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="dataExport"/>
<%
    final DataExportAndScheduleModel model = (DataExportAndScheduleModel) request.getAttribute("dataExportAndScheduleModel");
    //final CSVDataExportManager csvDataExport = ((ShowDataExportAndScheduleService) request.getAttribute("service")).getCSVDataExport();
%>
<div class="dataExportWindowDiv">
    <h1><%= model.getFormName() %>.&nbsp;<international:get name="dataExport"/></h1>

    <div style="height:1.3em;font-weight:normal;overflow:hidden">
        <h2 style="display:inline;margin-left:0;"><international:get name="formDescription"/></h2>
        &nbsp;<%= model.getLimitedFormDescription() %>
    </div>

    <div id="errors" class="emptyError"></div>


    <div style="margin-bottom:20px;">
        <%---------------------------------------------------Name-----------------------------------------------------%>
        <label for="dataExportName">
            <international:get name="exportName"/>
        </label>
        <input type="text" id="dataExportName" maxlength="255" value="<%= model.getFormExportTaskName() %>"
               style="width:195px;margin-left:28px;"/>
        <%---------------------------------------------------Name-----------------------------------------------------%>

        <%--------------------------------------------------Filter----------------------------------------------------%>
        <div style="margin:10px 0;">
            <div class="inform_mark">
                <international:get name="selectFilter"/>
            </div>
            <div>
                <international:get name="selectFormFilter"/>
                <select id="formFilterId" style="width:200px;">
                    <option value="-1">
                        <international:get name="allRecords"/>
                    </option>
                    <% for (com.shroggle.entity.Filter filter : model.getFormFilters()) { %>
                    <option value="<%= filter.getFormFilterId() %>"
                            <% if(model.getFormExportTaskFilterId() != null && model.getFormExportTaskFilterId() == filter.getFormFilterId()) { %>selected<% } %>>
                        <%= filter.getName() %>
                    </option>
                    <% } %>
                </select>
            </div>
        </div>
        <%--------------------------------------------------Filter----------------------------------------------------%>
        <%--------------------------------------------------Data Format-----------------------------------------------%>
        <div style="margin-top:10px;">
            <international:get name="dataFormat"/>
            <select id="dataFormat" style="width:200px;margin-left:28px;"
                    onchange="dataExportAndScheduler.changeDataFormat(this.value);">
                <% for (FormExportDataFormat format : FormExportDataFormat.values()) { %>
                <option value="<%= format.toString() %>"
                        <% if (format == model.getFormExportTaskDataFormat()) { %>selected<% } %>>
                    <international:get name="<%= format.toString() %>"/>
                </option>
                <% } %>
            </select>
        </div>
        <%--------------------------------------------------Data Format-----------------------------------------------%>
    </div>
    <%--------------------------------------------------------CSV-----------------------------------------------------%>
    <div id="csvDiv"
         <% if (model.getFormExportTaskDataFormat() == FormExportDataFormat.GOOGLE_BASE) { %>style="display:none;"<% } %>>
        <div style="margin-bottom:15px;">
            <international:get name="instruction"/>
        </div>
        <table class="customizeDataExportTable tbl_blog" style="border-bottom:none;">
            <thead>
            <tr>
                <td>
                    <international:get name="show"/>
                </td>
                <td>
                    <international:get name="order"/>
                </td>
                <td>
                    <international:get name="customizeHeader"/>
                </td>
                <td>
                    <international:get name="formFieldName"/>
                </td>
            </tr>
            </thead>
        </table>
        <div class="customizeDataExportTableBodyDiv">
            <table id="customizeDataExportTable" class="customizeDataExportTable tbl_blog">
                <% for (CSVDataExportFieldManager field : model.getCSVFields()) { %>
                <tr id="<%= field.getFormItemId() %>">
                    <td>
                        <input id="include<%= field.getFormItemId() %>" type="checkbox"
                               <% if(field.isInclude()) { %>checked<% } %>>
                    </td>
                    <td>
                        <% request.setAttribute("first", field.isFirst());
                            request.setAttribute("last", field.isLast()); %>
                        <jsp:include page="../../upDownArrows.jsp" flush="true"/>
                    </td>
                    <td>
                        <input id="customizeHeader<%= field.getFormItemId() %>" type="text" style="width:150px;"
                               value="<%= field.getCustomizeHeader() %>"/>
                    </td>
                    <td>
                        <%= field.getFormItemName() %>
                    </td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>
    <%--------------------------------------------------------CSV-----------------------------------------------------%>

    <%----------------------------------------------------Google base-------------------------------------------------%>
    <div id="googleBaseDiv"
         <% if (model.getFormExportTaskDataFormat() == FormExportDataFormat.CSV) { %>style="display:none;"<% } %>>
        <%----------------------------------------------------Gallery-------------------------------------------------%>
        <div style="margin-bottom:5px;font-weight:bold;">
            <international:get name="whichGalleryShouldInboundLinksGoTo"/>
        </div>
        <international:get name="selectGallery"/>
        <select id="galleryId" style="width:200px;">
            <% if (model.isAvailableGalleriesEmpty()) { %>
            <option value="-1">
                <international:get name="selectGalleryTextForSelectOption"/>
            </option>
            <% } %>
            <% for (DraftGallery gallery : model.getAvailableGalleries()) { %>
            <option value="<%= gallery.getId() %>"
                    <% if (model.getFormExportTaskGalleryId() == gallery.getId()) { %>selected<% } %>>
                <%= gallery.getName() %>
            </option>
            <% } %>
        </select>
        <%----------------------------------------------------Gallery-------------------------------------------------%>
        <div style="margin-top:15px;margin-bottom:5px;font-weight:bold;">
            <international:get name="selectWhichFormFieldsRelateToWhichGoogleBaseVariables"/>
        </div>
        <table>
            <tr>
                <td style="padding-right:10px;padding-bottom:5px;">
                    <international:get name="title"/>
                </td>
                <td style="padding-bottom:5px;">
                    <%-----------------------------------------------Title--------------------------------------------%>
                    <select id="formItemIdForTitle" style="width:200px;">
                        <% if (model.isAvailableTextFormItemsEmpty()) { %>
                        <option value="-1">
                            <international:get name="selectTitle"/>
                        </option>
                        <% } %>
                        <% for (FormItem formItem : model.getAvailableTextFormItems()) { %>
                        <option value="<%= formItem.getFormItemId() %>"
                                <% if (model.getFormItemIdForTitleId() == formItem.getFormItemId()) { %>selected<% } %>>
                            <%= formItem.getItemName() %>
                        </option>
                        <% } %>
                    </select>
                    <%-----------------------------------------------Title--------------------------------------------%>
                </td>
            </tr>
            <tr>
                <td style="padding-right:10px;padding-bottom:5px;">
                    <international:get name="description"/>
                </td>
                <td style="padding-bottom:5px;">
                    <%--------------------------------------------Description-----------------------------------------%>
                    <select id="formItemIdForDescription" style="width:200px;">
                        <% if (model.isAvailableTextFormItemsEmpty()) { %>
                        <option value="-1">
                            <international:get name="selectDescription"/>
                        </option>
                        <% } %>
                        <% for (FormItem formItem : model.getAvailableTextFormItems()) { %>
                        <option value="<%= formItem.getFormItemId() %>"
                                <% if (model.getFormItemIdForDescriptionId() == formItem.getFormItemId()) { %>selected<% } %>>
                            <%= formItem.getItemName() %>
                        </option>
                        <% } %>
                    </select>
                    <%--------------------------------------------Description-----------------------------------------%>
                </td>
            </tr>

            <br>

        </table>
    </div>
    <%----------------------------------------------------Google base-------------------------------------------------%>
    <div style="position:absolute;bottom:20px;right:20px;">
        <% if (model.isDestinationOwnFtp() && model.getFormExportTaskId() > 0) { %>
        <input type="button" class="but_w130" value="<international:get name="download"/>"
               id="downloadButton"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="dataExportAndScheduler.download(<%= model.getFormExportTaskId() %>);"/>
        <% } %>
        <input type="button" class="but_w130" value="<international:get name="next"/>"
               id="windowSave"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="dataExportAndScheduler.showNextTab();"/>
        <input type="button" class="but_w130" value="<international:get name="cancel"/>"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();"/>
    </div>
</div>