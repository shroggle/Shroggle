<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<international:part part="manageDataExports"/>
<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="manageDataExportsModel" type="com.shroggle.logic.form.export.ManageDataExportsModel" scope="request"/>
<div class="windowOneColumn">
    <input type="hidden" id="formId" value="${manageDataExportsModel.formId}">
    <input type="hidden" id="confirmDeleteExport" value="<international:get name="confirmDeleteExport"/>">

    <h1>${manageDataExportsModel.formName}.&nbsp;<international:get name="scheduledExportToGoogleBase"/></h1>

    <h2><international:get name="scheduledExportsForThisForm"/></h2>

    <div class="paddingBottom15px">
        <a href="javascript:dataExportAndScheduler.show(${manageDataExportsModel.formId}, null);"
                ><international:get name="createNewExportDownload"/></a>
    </div>
    <div id="manageFormExportTasksContainer">
        <jsp:include page="manageDataExportTable.jsp" flush="true"/>
    </div>
    <div class="paddingTop15px textAlignRight">
        <input type="button" class="but_w73" value="<international:get name="close"/>"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();"/>
    </div>
</div>