<%@ page import="com.shroggle.presentation.system.TimeCounterResultsTotalAction" %>
<%@ page import="com.shroggle.presentation.system.TimeCounterGroup" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<% final TimeCounterResultsTotalAction action = (TimeCounterResultsTotalAction) request.getAttribute("actionBean"); %>
<html>
    <head>
        <cache:no/>
        <title>Total time counters</title>

        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript">

            function update() {
                $("form").submit();
            }

        </script>

        <style type="text/css">

            td {
                font-family: monospace;
                border-bottom: 1px solid #bebdbd;
            }

        </style>
    </head>
    <body style="margin: 0">
        <table border="0" cellpadding="10" cellspacing="0" width="100%">
            <thead style="background-color: #303030; color: white;">
                <tr>
                    <td colspan="99">
                        <stripes:form method="get" beanclass="com.shroggle.presentation.system.TimeCounterResultsTotalAction">
                            Period
                            <stripes:select name="period" onchange="update()">
                                <stripes:option value="0">none</stripes:option>
                                <stripes:option value="<%= 30l * 60l * 1000l %>">half hour</stripes:option>
                                <stripes:option value="<%= 60l * 60l * 1000l %>">hour</stripes:option>
                                <stripes:option value="<%= 2l * 60l * 60l * 1000l %>">two hours</stripes:option>
                                <stripes:option value="<%= 6l * 60l * 60l * 1000l %>">6 hours</stripes:option>
                                <stripes:option value="<%= 12l * 60l * 60l * 1000l %>">12 hours</stripes:option>
                                <stripes:option value="<%= 24l * 60l * 60l * 1000l %>">24 hours</stripes:option>
                            </stripes:select>,

                            <stripes:submit name="execute" value="refresh"/>
                        </stripes:form>
                    </td>
                </tr>
            </thead>
            <tbody>
                <tr><td>
                    GC time, work seconds per time second:<br>
                    <img src="/system/timeCounterResultsGraph.action?group=<%= TimeCounterGroup.GC_TIME %>&period=<%= action.getPeriod() %>" border="0"/>
                </td></tr>

                <tr><td>
                    Used memory in bytes:<br>
                    <img src="/system/timeCounterResultsGraph.action?group=<%= TimeCounterGroup.MEMORY %>&period=<%= action.getPeriod() %>" border="0"/></td></tr>
                
                <tr><td>
                    Load average:<br>
                    <img src="/system/timeCounterResultsGraph.action?group=<%= TimeCounterGroup.LOAD_AVERAGE %>&period=<%= action.getPeriod() %>" border="0"/></td></tr>

                <%--<tr><td>--%>
                    <%--Tomcat sent bytes:<br>--%>
                    <%--<img src="/system/timeCounterResultsGraph.action?name=total://tomcatSentBytes&period=<%= action.getPeriod() %>" border="0"/></td></tr>--%>

                <%--<tr><td>--%>
                    <%--Tomcat received bytes:<br>--%>
                    <%--<img src="/system/timeCounterResultsGraph.action?name=total://tomcatReceivedBytes&period=<%= action.getPeriod() %>" border="0"/></td></tr>--%>

                <tr><td>
                    Http, call per second:<br>
                    <img src="/system/timeCounterResultsGraph.action?group=<%= TimeCounterGroup.HTTP %>&period=<%= action.getPeriod() %>" border="0"/></td></tr>

                <tr><td>
                    Persistance, work seconds per second:<br>
                    <img src="/system/timeCounterResultsGraph.action?group=<%= TimeCounterGroup.PERSISTANCE %>&period=<%= action.getPeriod() %>" border="0"/></td></tr>

                <tr><td>
                    File system time, work seconds per second:<br>
                    <img src="/system/timeCounterResultsGraph.action?group=<%= TimeCounterGroup.FILE_SYSTEM %>&period=<%= action.getPeriod() %>" border="0"/></td></tr>
            </tbody>
        </table>
    </body>
</html>