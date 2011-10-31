<%@ page import="com.shroggle.entity.GalleryOrientation" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final GalleryOrientation orientation = (GalleryOrientation) request.getAttribute("orientation");
    final Widget widgetGallery = (Widget) request.getAttribute("widgetGallery");
%>
<table id="gallery<%= widgetGallery.getWidgetId() %>" border="0" cellspacing="0" cellpadding="0">
    <tbody>
    <% if (orientation.equals(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW)) { %>
    <tr>
        <td align="center">
            <jsp:include page="renderWidgetGalleryNavigation.jsp" flush="true"/>
        </td>
    </tr>
    <tr>
        <td align="center"<%-- colspan="<%= rows.get(0).getCells().size() %>"--%>>
            <jsp:include page="renderWidgetGalleryData.jsp" flush="true"/>
        </td>
    </tr>
    <% } else if (orientation.equals(GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW)) {%>
    <tr>
        <td align="center"<%--colspan="<%= rows.get(0).getCells().size() %>"--%>>
            <jsp:include page="renderWidgetGalleryData.jsp" flush="true"/>
        </td>
    </tr>
    <tr>
        <td align="center">
            <jsp:include page="renderWidgetGalleryNavigation.jsp" flush="true"/>
        </td>
    </tr>
    <% } else if (orientation.equals(GalleryOrientation.DATA_LEFT_NAVIGATION_RIGHT)) {%>
    <tr>
        <td align="center" style="vertical-align:top;"<%--rowspan="<%= rows.size() %>"--%>>
            <jsp:include page="renderWidgetGalleryData.jsp" flush="true"/>
        </td>
        <td align="center" style="vertical-align:top;">
            <jsp:include page="renderWidgetGalleryNavigation.jsp" flush="true"/>
        </td>
    </tr>
    <% } else if (orientation.equals(GalleryOrientation.NAVIGATION_LEFT_DATA_RIGHT)) {%>
    <tr>
        <td align="center" style="vertical-align:top;">
            <jsp:include page="renderWidgetGalleryNavigation.jsp" flush="true"/>
        </td>
        <td align="center" style="vertical-align:top;"<%--rowspan="<%= rows.size() %>"--%>>
            <jsp:include page="renderWidgetGalleryData.jsp" flush="true"/>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>