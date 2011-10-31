<%@ page import="com.shroggle.presentation.manageRegistrants.EditVisitorService" %>
<%@ page import="com.shroggle.entity.FilledForm" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<international:part part="manageRegistrants"/>
<%
    final EditVisitorService service = (EditVisitorService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <h1><international:get name="editRegistrationData"/></h1>

    <div id="editVisitorDiv" style="max-height:450px; overflow-x:hidden;">
        <tr><td width="30%">&nbsp;</td><td>&nbsp;</td></tr>
        <table class="registrationTable" id="editRegisteredVisitorTable" width="80%">
            <jsp:include page="../../site/render/widgetForm.jsp" flush="true"/>
        </table>
    </div>
    <br>

    <div align="right">
        <input type="button" id="submit0" class="but_w73" value="Save"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';"
               onclick="manageRegistrants.saveVisitorEdit(<%= service.getVisitorToEdit().getUserId() %>);">
        <input type="button" id="close0" class="but_w73" value="Close"
               onmouseover="this.className = 'but_w73_Over';"
               onmouseout="this.className = 'but_w73';" onclick="closeConfigureWidgetDiv();">
    </div>
</div>