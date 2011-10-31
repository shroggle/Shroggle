<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
    final boolean showFromSiteEditPage = request.getAttribute("showFromSiteEditPage") != null ? (Boolean) request.getAttribute("showFromSiteEditPage") : false; %>
<% if (siteShowOption == SiteShowOption.INSIDE_APP || showFromSiteEditPage) { %>
This item is not configured. Please, use 'Edit Settings' link to complete its configuration.
<% } else { %>
Please return to the 'Edit your Site' page and select content module to configure it.
<% } %>