<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageDataExportsTable"/>
<%--
    @author Balakirev Anatoliy
--%>
<jsp:useBean id="manageDataExportsModel" type="com.shroggle.logic.form.export.ManageDataExportsModel" scope="request"/>
<table class="customizeDataExportTable tbl_blog" style="border-bottom:none;">
    <thead>
    <tr>
        <td style="width:100px;">
            <international:get name="exportNames"/>
        </td>
        <td style="width:100px;">
            <international:get name="recipient"/>
        </td>
        <td style="width:100px;">
            <international:get name="frequency"/>
        </td>
        <td>
            <international:get name="lastSuccessfulExport"/>
        </td>
        <td style="width:100px;">
            <international:get name="created"/>
        </td>
        <td style="width:30px;">
            <international:get name="edit"/>
        </td>
        <td style="width:40px;">
            <international:get name="delete"/>
        </td>
        <td style="width:50px;">
            <international:get name="download"/>
        </td>
    </tr>
    </thead>
</table>
<table id="customizeDataExportTable" class="customizeDataExportTable tbl_blog">
    <c:forEach var="taskManager" items="${manageDataExportsModel.formExportTasks}">
        <tr id="${taskManager.id}">
            <td style="width:100px;">
                    <c:out value="${taskManager.name}"/>
            </td>
            <td style="width:100px;">
                <international:get name="${taskManager.dataFormat}"/>
            </td>
            <td style="width:100px;">
                <international:get name="${taskManager.frequency}"/>
            </td>
            <td>
                    <c:out value="${taskManager.lastSuccessfulExportDateString}"/>
            </td>
            <td style="width:100px;">
                    <c:out value="${taskManager.createdString}"/>
            </td>
            <td style="width:30px;">
                <a href="javascript:dataExportAndScheduler.show(${manageDataExportsModel.formId}, ${taskManager.id});"><international:get
                        name="edit"/></a>
            </td>
            <td style="width:40px;text-align:center;">
                <img src="/images/cross-circle.png" class="deleteRecordButton" style="cursor:pointer;"
                     alt="<international:get name="delete"/>"
                     title="<international:get name="delete"/>"
                     onclick="dataExportAndScheduler.deleteExportTask(${taskManager.id});">
            </td>
            <td style="width:50px;text-align:center;">
                <c:if test="${taskManager.showDownloadButton}">
                    <img src="/images/down_arrow.gif" class="deleteRecordButton" style="cursor:pointer;"
                         alt="<international:get name="download"/>" title="<international:get name="download"/>"
                         onclick="dataExportAndScheduler.download(${taskManager.id});">
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>