<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.QuicklyCreatePagesAction" %>
<%@ page import="com.shroggle.entity.PageType" %>
<%@ page import="com.shroggle.presentation.site.QuicklyCreatePage" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<% final QuicklyCreatePagesAction actionBean = (QuicklyCreatePagesAction) request.getAttribute("actionBean"); %>
<international:part part="quicklyCreatePages"/>
<html>
<head>
    <title><international:get name="header"/></title>
    <cache:no/>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <div class="middle_page">
                <h1 class="fl"><international:get name="subHeader"/></h1>

                <div class="fr">
                    <input type="button" class="but_border" value="Back to Site Designs"
                            onclick="window.location = '/site/setSiteTemplate.action?siteId=<%= actionBean.getSiteId() %>'"/>
                </div>

                <div style="clear:both;"></div>

                <international:get name="subHeaderDesc"/>

                <form action="/site/quicklyCreatePages.action?siteId=<%= actionBean.getSiteId() %>" method="post">
                    <page:errors/>

                    <br>
                    <table id="parent">
                        <tr>
                            <td class="quicklyPageNameTd">
                                <% for (final PageType pageType : QuicklyCreatePagesAction.allowedPageTypes) { %>
                                <div class="quicklyPageName">
                                    <input type="checkbox" id="<%= pageType %>" name="pages" value="<%= pageType %>"
                                           onmouseover="quicklyCreatePages.setDescription('<%= pageType %>');"/>
                                    <label for="<%= pageType %>"
                                           onmouseover="quicklyCreatePages.setDescription('<%= pageType %>');"><international:get
                                            name="<%= pageType.toString() %>"/></label>
                                </div>
                                <% } %>
                            </td>
                            <td id="quicklyPageDescTd">
                                <% for (final PageType pageType : QuicklyCreatePagesAction.allowedPageTypes) { %>
                                <div class="quicklyPageDesc"
                                     <% if (pageType != PageType.HOME) { %>style="display:none;"<% } %>
                                     id="<%= pageType %>Desc">
                                    <% String pageDesc = pageType.toString() + "Desc"; %>
                                    <international:get name="<%= pageDesc %>"/>
                                </div>
                                <% } %>
                            </td>
                        </tr>
                    </table>

                    <br><br>

                    <div class="fr" style="text-align:right">
                        <input type="submit" name="execute" value=""
                               onmouseout="this.className='butSubmit';"
                               onmouseover="this.className='butSubmitOver';" class="butSubmit">
                    </div>
                    <br clear="all">
                </form>
            </div>
        </div>
        <%@ include file="../includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>