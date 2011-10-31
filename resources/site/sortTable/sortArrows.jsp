<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final boolean descending = request.getAttribute("descending") != null ? (Boolean) request.getAttribute("descending") : false;
    final String sortFieldType = (String) request.getAttribute("sortFieldType");
    final boolean show = request.getAttribute("show") != null ? (Boolean) request.getAttribute("show") : false;
    if (descending) { %>
<span name="sort_arrow_span" descending="true" sortFieldType="<%= sortFieldType %>"
      <% if(!show) { %>style="display:none;"<% } %>>&nbsp;<img id="sort_arrow" src="/images/down.gif"
                                                               onload="tableWithSort.addOnclickForParent(this);"></span>
<% } else { %>
<span name="sort_arrow_span" descending="false" sortFieldType="<%= sortFieldType %>"
      <% if(!show) { %>style="display:none;"<% } %>>&nbsp;<img id="sort_arrow" onload="tableWithSort.addOnclickForParent(this);"
                                                               src="/images/up.gif"></span>
<% } %>