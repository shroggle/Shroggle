<%@ page import="com.shroggle.entity.FormExportDataFormat" %>
<%@ page import="com.shroggle.logic.form.export.DataExportAndScheduleModel" %>
<%@ page import="com.shroggle.entity.FormExportDestination" %>
<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="dataExportAndScheduler"/>
<% final DataExportAndScheduleModel model = (DataExportAndScheduleModel) request.getAttribute("dataExportAndScheduleModel"); %>
<input type="hidden" id="formId" value="<%= model.getFormId() %>">
<input type="hidden" id="formExportTaskId" value="<%= model.getFormExportTaskId() %>">
<input type="hidden" id="CSV" value="<%= FormExportDataFormat.CSV %>">
<input type="hidden" id="FTP" value="<%= FormExportDestination.FTP %>">
<input type="hidden" id="wrongStartDate" value="<international:get name="wrongStartDate"/>">
<input type="hidden" id="inputCorrectName" value="<international:get name="inputCorrectName"/>">
<input type="hidden" id="nameNotUnique" value="<international:get name="nameNotUnique"/>">
<input type="hidden" id="ftpCantBeEmpty" value="<international:get name="ftpCantBeEmpty"/>">
<input type="hidden" id="googleUsernameCantBeEmpty" value="<international:get name="googleUsernameCantBeEmpty"/>">
<input type="hidden" id="googlePasswordCantBeEmpty" value="<international:get name="googlePasswordCantBeEmpty"/>">

<input type="hidden" id="selectGallery" value="<international:get name="selectGallery"/>">
<input type="hidden" id="selectTitle" value="<international:get name="selectTitle"/>">
<input type="hidden" id="selectDescription" value="<international:get name="selectDescription"/>">




<div class="windowMainContent">
    <div class="twoColumnsWindow_columnWrapper">
        <div id="twoColumnsWindow_leftColumn" class="twoColumnsWindow_leftColumn">
            <div class="leftmenu_header">
                <international:get name="itemSettingsHeader"/>
            </div>
            <div class="leftmenu_body">
                <div class="c1current" id="dataExportTab" onclick="dataExportAndScheduler.showDataExportTab(this);">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="dataExport"/></span>
                </div>
                <div class="c1" id="scheduledExportTab" onclick="dataExportAndScheduler.showScheduledExportTab(this);">
                    <div class="c1_over_A">&nbsp;</div>
                    <span><international:get name="scheduledExport"/></span>
                </div>
            </div>
        </div>
    </div>
    <div class="twoColumnsWindow_columnWrapper">
        <div id="twoColumnsWindow_rightColumn" class="twoColumnsWindow_rightColumn">
            <div id="dataExportTabContent" class="tabContent dataExportWindowDivWrapper">
                <jsp:include page="dataExport.jsp" flush="true"/>
            </div>
            <div id="scheduledExportTabContent" class="tabContent dataExportWindowDivWrapper" style="display:none;">
                <jsp:include page="scheduleExport.jsp" flush="true"/>
            </div>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
