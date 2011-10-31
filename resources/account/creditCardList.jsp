<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="creditCardList"/>
<html>
<head>
    <title><international:get name="HEADER"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>

        <div class="content">
            <div class="box_80">
                <%@ include file="accountMenuInclude.jsp" %>
            </div>
            <div class="box_100">
                <div class="inside_95"
                     id="pageContent" style="display:block">

                    <div class="pageTitle"><international:get name="HEADER"/></div>
                    <div class="inform_mark"><international:get name="DESCRIPTION"/></div>
                    <bR>

                    <div class="warning"><international:get name="WARNING"/></div>
                    <bR>

                    <div id="ccTable">
                        <jsp:include page="creditCardListTable.jsp" flush="true"/>
                    </div>
                    <br clear="all">

                    <div class="buttons_box" align="right">
                        <input type="button" onclick="showAddEditCreditCardWindow('true');" value="Add"
                               onmouseout="this.className='but_w73';"
                               onmouseover="this.className='but_w73_Over';" class="but_w73">
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