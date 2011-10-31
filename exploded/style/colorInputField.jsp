<%@ page import="com.shroggle.entity.Style" %>
<%@ page import="com.shroggle.logic.StyleManager" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%--
  @author Balakirev Anatoliy
  Date: 25.07.2009  
--%>
<div id="<%= (String)request.getAttribute("id") + request.getAttribute("fieldName") %>" style="float:left;">
    <% final String colorInputFieldId = (String) request.getAttribute("id");
        final String fieldName = (String) request.getAttribute("fieldName");
        final Style style = (Style) request.getAttribute(colorInputFieldId); %>

    <% final String colorPickerOnload = "showStyleColorPicker({" +
            "id:'" + (colorInputFieldId + fieldName) + "'," +
            "value:'" + (StyleManager.createStyleValue(fieldName, style)) + "'," +
            "onChangeFunction:" + request.getAttribute("onChangeFunction") + "});"; %>
    <elementWithOnload:element onload="<%= colorPickerOnload %>"/>
</div>