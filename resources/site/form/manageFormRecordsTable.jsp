<%@ taglib prefix="image" tagdir="/WEB-INF/tags/imageWithLoadingProgress" %>
<%@ page import="com.shroggle.presentation.form.filledForms.ManageFormRecordsTableRequest" %>
<%@ page import="com.shroggle.presentation.form.filledForms.SortProperties" %>
<%@ page import="com.shroggle.presentation.site.FilledFormInfo" %>
<%@ page import="com.shroggle.presentation.site.ManageFormRecordSortType" %>
<%@ page import="com.shroggle.entity.FormItemName" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageFormRecordsTable"/>
<%--
    @author Balakirev Anatoliy
--%>
<% final ManageFormRecordsTableRequest tableRequest = (ManageFormRecordsTableRequest) request.getAttribute("manageFormRecordsTableRequest");
    final SortProperties sortProperties = tableRequest.getSortProperties();
    request.setAttribute("descending", sortProperties.isDescending()); %>
<div class="tbl_manageRecords">
    <table class="manageRecordsHeadTable">
        <thead style="cursor:default">
        <tr>
            <% for (String itemName : tableRequest.getCustomCellsItemNames()) { %>
            <td class="fieldTdHead" style="word-wrap:break-word;"
                itemName="<%= itemName %>"
                onclick="reloadManageRegistrantsTable();">
                <%= itemName %>
                <% request.setAttribute("show", (sortProperties.getSortFieldType() == ManageFormRecordSortType.CUSTOM_FIELD && sortProperties.getItemName().equals(itemName)));
                    request.setAttribute("sortFieldType", ManageFormRecordSortType.CUSTOM_FIELD.toString()); %>
                <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
            </td>
            <% } %>
            <td id="showMoreTd" style="width:80px;"><international:get name="showMoreTd"/></td>
            <td id="createdTd" style="width:120px;"
                itemName=""
                onclick="reloadManageRegistrantsTable();">
                <international:get name="createdTd"/>
                <% request.setAttribute("show", (sortProperties.getSortFieldType() == ManageFormRecordSortType.FILL_DATE));
                    request.setAttribute("sortFieldType", ManageFormRecordSortType.FILL_DATE.toString()); %>
                <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
            </td>
            <td id="updatedTd" style="width:120px;"
                itemName=""
                onclick="reloadManageRegistrantsTable();">
                <international:get name="updatedTd"/>
                <% request.setAttribute("show", (sortProperties.getSortFieldType() == ManageFormRecordSortType.UPDATE_DATE));
                    request.setAttribute("sortFieldType", ManageFormRecordSortType.UPDATE_DATE.toString()); %>
                <jsp:include page="../sortTable/sortArrows.jsp" flush="true"/>
            </td>
            <td id="hideTd" style="width:40px;"><international:get name="hideTd"/></td>
            <td id="deleteTd" style=""><international:get name="deleteTd"/></td>
        </tr>
        </thead>
    </table>
    <div class="manageRecordsBodyTableDiv">
        <table id="manageRecordsBodyTable" class="manageRecordsBodyTable">
            <tbody id="manageFormRecordsTable">
            <% if (tableRequest.getFilledFormInfos() == null || tableRequest.getFilledFormInfos().isEmpty()) { %>
            <tr id="no_filledForms">
                <td colspan="<%= tableRequest.getCustomCellsItemNames().size() + 5 %>">
                    <international:get name="emptyTable"/></td>
            </tr>
            <% } else {
                boolean odd = true;
                for (FilledFormInfo filledFormInfo : tableRequest.getFilledFormInfos()) {
                    odd = !odd; %>
            <tr id="row<%= filledFormInfo.getFilledFormId() %>" class="<%= odd ? "odd" : ""%>">
                <% for (FilledFormInfo.CustomCellValue customCellValue : filledFormInfo.getCustomCellsItemValues()) { %>
                <% if (customCellValue.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) { %>
                <td class="fieldTdBody imageTdImage">
                    <% if (customCellValue.getImageUrl() != null) { %>
                    <div style="width:100%; height:100%; overflow:auto;">
                        <image:image src="<%= customCellValue.getImageUrl() %>"
                                     alt="<%= customCellValue.getImageAlt() %>"
                                onload="delayedFixManageRecordsHeadTableWidths();"/>
                    </div>
                    <% } else { %>
                    <%= customCellValue.getNotSpecified() %><% } %>
                </td>
                <% } else { %>
                <td class="fieldTdBody">
                    <a href="<%= tableRequest.isShowForChildSiteRegistration() ?
            ("javascript:editChildSiteRegistrationWindow(" + filledFormInfo.getVisitorId() + "," +
            filledFormInfo.getFilledFormId() + ", -1, false)") :
            "javascript:showEditRecordWindow(" + filledFormInfo.getFilledFormId() + ", null)" %>"><%= customCellValue.getValue() %>
                    </a>
                </td>
                <% } %>
                <% } %>
                <td style="width:80px;">
                    <a href="javascript:showFormRecordDataWindow(<%= filledFormInfo.getFilledFormId() %>);"
                            ><international:get name="showMore"/></a>
                </td>
                <td style="width:120px;">
                    <%= filledFormInfo.getFillDate() %>
                </td>
                <td style="width:120px;">
                    <%= filledFormInfo.getUpdateDate() %>
                </td>
                <td style="width:40px;">
                    <input type="checkbox" <% if (filledFormInfo.isHidden()) { %>checked<% } %>
                           onclick="hideRecord(<%= filledFormInfo.getFilledFormId() %>, this.checked);">
                </td>
                <td style="text-align:center;width:80px;cursor:pointer;">
                    <img src="/images/cross-circle.png" class="deleteRecordButton" alt="<international:get name="delete"/>"
                         onclick="deleteFilledForm(<%= filledFormInfo.getFilledFormId() %>);">
                </td>
            </tr>
            <% }
            } %>
            </tbody>
        </table>
    </div>
</div>
