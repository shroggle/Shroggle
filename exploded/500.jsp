<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<html>
    <head>
        <title>The page you requested is temporarily unavailable.</title>
        <cache:no/>
        <script type="text/javascript" src="dwr/engine.js"></script>
        <link href="/css/reset.css" rel="stylesheet" type="text/css">
        <link href="/css/pages_style.css" rel="stylesheet" type="text/css">
        <link href="/css/app_style.css" rel="stylesheet" type="text/css">
        <!--
            Raise exception:
            <% final Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception"); %>
            <% if (throwable != null) { %>
                <%
                    final PrintWriter printWriter = new PrintWriter(out);
                    throwable.printStackTrace(printWriter);
                %>
            <% } else { %>
                Can't find exception in request attributes.
            <% } %>
        -->
    </head>
    <body>
        <br><br>
        <div class="box" style="width:500px;">
        <div class="inside_70">
            <br><br>
            <h1>The page you requested is temporarily unavailable</h1>
            <p class="blue_14" style="margin:12px; line-height:24px;">
                    due to an: HTTP Error 500 - Internal Server Error <br><br>
                    <span class="b_16">Sincere apologies for the inconvenience.</span><bR><bR>
                    We suggest that you either: <bR>
                    <span class="d_12">Try again in a few minutes, or <bR>
                    Click the 'Back' button to select another link.</span>
                </p> &nbsp;
            </div>
        </div>
    </body>
</html>
