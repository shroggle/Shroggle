<%@ page import="com.shroggle.presentation.system.CheckPerformanceDataAction" %>
<%@ page import="com.shroggle.util.testspeed.CheckPerformanceStatisticsItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<% final CheckPerformanceDataAction action = (CheckPerformanceDataAction) request.getAttribute("actionBean"); %>

<% if (action.isWork()) { %>
    <h4>Working...</h4>
<% } %>

<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>Threads</th>
        <th>Request delay</th>
        <th>Calls</th>
        <th>IO errors</th>
        <th>404</th>
        <th>500</th>
        <th>Unknown errors</th>
        <th>Response size</th>
        <th>Response time</th>
        <th>Avg. response size</th>
        <th>Avg. response time</th>
    </tr>

    <% for (final CheckPerformanceStatisticsItem item : action.getItems()) { %>
        <% final int count = item.getSuccessCount(); %>
        <% final long time = item.getTime(); %>
        <% final long size = item.getSize();%>
        <tr>
            <td align="right"><%= item.getThreadCount() %></td>
            <td align="right"><%= item.getRequestDelay() %> msec</td>
            <td align="right"><%= count %></td>
            <td align="right"><%= item.getIOErrorCount() %></td>
            <td align="right"><%= item.getNotFoundCount() %></td>
            <td align="right"><%= item.getServerErrorCount() %></td>
            <td align="right"><%= item.getUnknownErrorCount() %></td>
            <td align="right"><%= size / 1024l %> kb or <%= size / 1024l / 1024l %> mb</td>
            <td align="right"><%= time / 1000l %> sec</td>
            <td align="right">
                <% if (count > 0) { %>
                    <%= size / count / 1000l %> kb
                <% } %>
            </td>

            <td align="right">
                <% if (count > 0) { %>
                    <%= time / count %> msec
                <% } %>
            </td>
        </tr>
    <% } %>
</table>