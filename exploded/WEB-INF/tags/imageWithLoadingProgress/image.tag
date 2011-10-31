<%@ tag body-content="empty" dynamic-attributes="tagAttr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="onload" required="false" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="height" required="false" %>
<%@ attribute name="alt" required="false" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="style" required="false" %>
<%@ tag import="com.shroggle.util.StringUtil" %>
<% final String id = String.valueOf(System.nanoTime()); %>
<table style="width:100%;" id="<%= id %>">
        <tr>
            <td style="width:${width}px; height:${height}px;margin:0 auto;vertical-align:middle;display:none;" id="loadErrorMessageSpan<%= id %>">
                <img src="../../../images/nofoto.jpg" alt="Image was not specified" title="Image was not specified"
                     <% if (!StringUtil.isNullOrEmpty(onload)) { %>onload="${onload}"<% } %>
                    <% if (!StringUtil.isNullOrEmpty(width) && (Integer.valueOf(width) < 100)) { %>width="${width}"<% } else { %>width="100"<% } %>
                    <% if (!StringUtil.isNullOrEmpty(height) && (Integer.valueOf(height) < 100)) { %>height="${height}"<% } else { %>height="100"<% } %> >
            </td>
            <td style="width:${width}px; height:${height}px;margin:0 auto;vertical-align:middle;" id="loadingMessageSpan<%= id %>">
                <img src="../../../images/ajax-loader.gif" align="center" style="margin-left:10px;">
                    <br>
                    Loading...
            </td>
        </tr>
</table>

<%-- Do not remove checks on width, heigth parameters emptiness. It fixes bug with IE:
 when width is empty and with attribute is present it automatically sets width to 1. Dima --%>
<img style="${style};display:none;"
     <c:forEach var="attr" items="${tagAttr}">
        ${attr.key}='${attr.value}'
     </c:forEach>
     <% if (!StringUtil.isNullOrEmpty(alt)) { %>alt="${alt}"<% } %>
     <% if (!StringUtil.isNullOrEmpty(title)) { %>title="${title}"<% } %>
     <% if (!StringUtil.isNullOrEmpty(width)) { %>width="${width}"<% } %>
     <% if (!StringUtil.isNullOrEmpty(height)) { %>height="${height}"<% } %>
    onerror="document.getElementById('loadingMessageSpan<%= id %>').style.display = 'none';document.getElementById('loadErrorMessageSpan<%= id %>').style.display = 'table-cell';"
    onload="document.getElementById('<%= id %>').style.display = 'none';this.style.display = 'inline';${onload}">
