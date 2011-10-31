<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="com.shroggle.presentation.account.dashboard.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.entity.SiteStatus" %>
<%@ page import="com.shroggle.presentation.account.dashboard.DashboardAction" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="java.text.DateFormat" %>
<international:part part="manageNetworkRegistrants"/>
<html>
<head>
    <title><international:get name="manageChildSiteRegistrantsHeader"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript" src="/tinymce/jquery.tinymce.js"></script>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <div class="box_100">
                <div class="inside_95">
                    <h1><%= request.getAttribute("siteName") %>. <international:get
                            name="manageChildSiteRegistrantsHeader"/></h1>

                    <div align="right">
                        <label for="search"><international:get name="search"/></label>
                        <input type="text" id="search" class="txt" maxlength="255"
                               onkeyup="processKeyManageNetworkRegistrants(this.value, event);"/>

                        <div class="mark" style="display:inline; margin:0;"
                             onmouseover="this.className = 'mark_over';"
                             onmouseout="this.className = 'mark';"
                             onclick="showNetworkRegistrantsHelpWindow();">
                            &nbsp;
                        </div>
                        <a id="showAllLink" style="visibility:hidden;"
                           href="javascript:document.getElementById('search').value='';processKeyManageNetworkRegistrants('', undefined);">
                            <international:get name="showAll"/>
                        </a>
                    </div>
                    <br>

                    <div id="networkRegistrantsTable">
                        <jsp:include page="networkRegistrantsTable.jsp" flush="true"/>
                    </div>
                </div>
            </div>
            <br>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>


<div id="helpWindow" style="display:none;">
    <div class="windowOneColumn">
        <div id="helpText"><international:get name="helpText"/></div>
        
        <div align="right" style="margin-top:10px;">
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   value="Close" onclick="closeConfigureWidgetDiv();"/>
        </div>
    </div>
</div>