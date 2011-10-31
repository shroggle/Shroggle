<%@ page import="com.shroggle.entity.WorkPageSettings" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.presentation.MapAction" %>
<%@ page import="com.shroggle.entity.SiteTitlePageName" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<html>
    <head>
        <title>Map Page</title>
        <jsp:include page="/includeHeadPresentationResources.jsp" flush="true">
            <jsp:param name="presentationJs" value="true"/>
        </jsp:include>
        <link rel="stylesheet" href="/css/style_start.css" type="text/css">
    </head>
    <body>
        <div id="wrapper">
            <div id="container"     >
            <%@ include file="/includeHeadPresentation.jsp" %>
            <div id="mainContentNdi" class="clearbothNd">

                <h1>Map</h1>

                <% final MapAction action = (MapAction) request.getAttribute("actionBean"); %>
                <ul style="list-style: decimal;">
                    <% for (final SiteTitlePageName siteTitlePageName : action.getSiteTitlePageNames()) { %>
                        <li>
                            <a href="http://<%= siteTitlePageName.getSiteSubDomain() + "." + ServiceLocator.getConfigStorage().get().getUserSitesUrl() + "/" + siteTitlePageName.getPageUrl()  %>">
                                <%= siteTitlePageName.getSiteTitle() %> - <%= siteTitlePageName.getPageName() %>
                            </a>
                        </li>
                    <% } %>
                </ul>
            </div>
            <%@ include file="/includeFooterPresentation.jsp" %>
                </div>
        </div>
    </body>
</html>
