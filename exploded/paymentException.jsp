<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="paymentException"/>
<html>
<head>
    <title><international:get name="header"/></title>
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
    <div class="inside_70" style="padding-bottom:15px">
        <br/>
        <span style="font-size:18px;font-weight:bold;color:black;padding-bottom:10px">
            <international:get name="text"/>
        </span>
    </div>
</div>
</body>
</html>
