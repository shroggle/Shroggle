<%@ page import="com.shroggle.presentation.account.dashboard.keywordManager.ShowKeywordManagerService" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="keywordManager"/>
<%
    final ShowKeywordManagerService service = (ShowKeywordManagerService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <international:get name="noPages"/>
    <input type="button" value="Site Edit Page" onmouseout="this.className='but_w130';"
           onmouseover="this.className='but_w130_Over';" class="but_w130"
           onclick="window.location='/site/siteEditPage.action?siteId=<%= service.getSiteManager().getSiteId() %>';">
    <international:get name="noPages2"/>

    <div align="right">
        <input type="button" value="Close" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="closeConfigureWidgetDiv();">
    </div>
</div>