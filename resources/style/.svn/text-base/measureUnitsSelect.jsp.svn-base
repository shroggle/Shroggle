<%@ page import="com.shroggle.entity.Style" %>
<%@ page import="com.shroggle.logic.StyleManager" %>
<%@ page import="com.shroggle.entity.MeasureUnit" %>
<%--
  @author Balakirev Anatoliy
  Date: 23.07.2009  
--%>

<select id="<%= (String)request.getAttribute("id") + request.getAttribute("fieldName") %>MeasureUnit"
        style="width:50px;"
        onchange="<%= (String)request.getAttribute("onChangeFunction") %>();">
    <% for (MeasureUnit unit : MeasureUnit.values()) { %>
    <option value="<%= unit.toString() %>"
            <% if (StyleManager.createMeasureValue((String) request.getAttribute("fieldName"),
                    ((Style) request.getAttribute((String) request.getAttribute("id")))).equals(unit)) { %>
            selected <% } %>>
        <%= unit.getValue() %>
    </option>
    <% } %>
</select>&nbsp;