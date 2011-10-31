<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
 @author Balakirev Anatoliy
--%>
<%
    final int widgetId = (Integer) request.getAttribute("widgetId");
    List<String> states = (List<String>) request.getAttribute("states");
    String selectedState = request.getAttribute("state") != null ? (String) request.getAttribute("state") : "";
    final boolean disable = request.getAttribute("disable") != null ? (Boolean) request.getAttribute("disable") : false;
%>
<% if (states != null && states.size() > 0) { %>
<%-------------------------------------------States Select--------------------------------------------%>
<select id="state<%= widgetId %>" name="request.region" class="txt" <% if (disable) { %>disabled<% } %>>
    <% for (String state : states) { %>
    <option value="<%= state %>" <% if (state.equals(selectedState)) { %>selected<% } %>>
        <%= state %>
    </option>
    <% } %>
</select>
<%----------------------------------------------States------------------------------------------------%>
 <% } else { %>
<%----------------------------------------------Region------------------------------------------------%>
<input id="state<%= widgetId %>" type="text" class="txt" name="request.region"
       value="<%= selectedState %>"  <% if (disable) { %>disabled<% } %>
       onKeyPress="return romanCharactersOrSpaceOnly(this, event);"/>
<%----------------------------------------------Region------------------------------------------------%>
<% } %>