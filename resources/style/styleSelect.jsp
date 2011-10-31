<%@ page import="com.shroggle.entity.BorderStyle" %>
<%@ page import="com.shroggle.entity.Style" %>
<%@ page import="com.shroggle.logic.StyleManager" %>
<%--
  @author Balakirev Anatoliy
  Date: 24.07.2009  
--%>
<select size="1" id="<%= (String)request.getAttribute("id") + request.getAttribute("fieldName") %>"
        onchange="<%= (String)request.getAttribute("onChangeFunction") %>();">
    <% for (BorderStyle borderStyle : BorderStyle.values()) { %>
    <option value="<%= borderStyle.toString() %>"
            <% if (StyleManager.createStyleValue((String) request.getAttribute("fieldName"),
                    ((Style) request.getAttribute((String) request.getAttribute("id")))).equals(borderStyle.toString())) { %>
            selected <% } %>>
        <%= borderStyle.getValue() %>
    </option>
    <% } %>
</select>