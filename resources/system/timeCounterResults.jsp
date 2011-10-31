<%@ page import="com.shroggle.presentation.system.TimeCounterResultsAction" %>
<%@ page import="java.text.DecimalFormatSymbols" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.shroggle.util.process.timecounter.TimeCounterResult" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.math.MathContext" %>
<%@ page import="java.math.RoundingMode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<%
    final TimeCounterResultsAction action = (TimeCounterResultsAction) request.getAttribute("actionBean");
    final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
    decimalFormatSymbols.setDecimalSeparator(' ');
    decimalFormatSymbols.setGroupingSeparator(',');
    final DecimalFormat format = new DecimalFormat("###,###", decimalFormatSymbols);
    format.setDecimalSeparatorAlwaysShown(true);
    format.setGroupingUsed(true);
    final MathContext mathContext = new MathContext(2, RoundingMode.HALF_UP);
%>
<html>
<head>
    <cache:no/>
    <title>Time counters</title>
    <script type="text/javascript" src="/js/jquery.js"></script>
    <script type="text/javascript" src="/js/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/util.js"></script>
    <script type="text/javascript" src="/js/window.js"></script>
    <script type="text/javascript" src="/site/configureWindow.js"></script>
    <script type="text/javascript" src="/SWFUpload/uploadersStack.js"></script>


    <script type="text/javascript">

        <%--var startTime = <%= action.getStartTime() %>;--%>

        function update() {
            $("form").submit();
        }

        function updatePredefine() {
            $("[name=filter]").val($("#predefined")[0].options[$("#predefined")[0].selectedIndex].value);
            //                $("[name=filter]").val($("#predefined option[selected=selected]").val());
            update();
        }

        function showExecutedTimesDetails(detailsId) {
            var detailsWindow = createConfigureWindow({width:300, height:740});
            detailsWindow.setContent($("#detailedStatistic" + detailsId).html());
        }

    </script>

    <style type="text/css">

        td {
            font-family: monospace;
            border-bottom: 1px solid #bebdbd;
        }

    </style>
</head>
<body style="margin: 0;height:100%;">
    <table border="0" cellpadding="10" cellspacing="0" style="width:100%;">
        <thead style="width:100%;background-color: #303030; color: white;position:fixed;">
        <tr>
            <td colspan="99">
                <stripes:form method="get" beanclass="com.shroggle.presentation.system.TimeCounterResultsAction">
                    Filter: <stripes:text name="filter" maxlength="255"/>

                    <select size="1" id="predefined" onchange="updatePredefine()">
                        <option value="" selected="selected">none</option>
                        <option value="internationalStorage://">internationalStorage://</option>
                        <option value="persistance://">persistance://</option>
                        <option value="http://">http://</option>
                        <option value="fileSystem://">fileSystem://</option>
                    </select>,

                    sort by
                    <stripes:select name="order" size="1" onchange="update()">
                        <stripes:option value="">average time</stripes:option>
                        <stripes:option value="executedTime">total time</stripes:option>
                        <stripes:option value="executedCount">total count</stripes:option>
                    </stripes:select>,

                    <stripes:submit name="execute" value="refresh"/>
                </stripes:form>
            </td>
        </tr>
        <tr>
            <td style="width:100%;">Counter name</td>
            <td style="width:50px;">avr(executing time)</td>
            <td style="width:50px;">executing count</td>
            <td style="width:50px;">executing time</td>
            <td style="width:50px;">avr (executed time)</td>
            <td style="width:50px;">executed count</td>
            <td style="width:50px;">executed time</td>
        </tr>
        </thead>
        <tbody style="width:100%;">
        <tr style="height:130px;">

        </tr>
        <% for (final TimeCounterResult timeCounterResult : action.getResults()) { %>
        <% final Long executingTime = timeCounterResult.getExecutingTime(); %>
        <% final int executingCount = timeCounterResult.getExecutingCount(); %>

        <tr>
            <% String label = timeCounterResult.getName(); %>
            <% if (label.startsWith("http://")) { %>
            <% label = "<a href=\"" + label + "\">" + label + "</a>"; %>
            <% } else if (label.contains("select ") && label.contains(" from ")) { %>
            <% label = label.replaceAll("select ", "<b>select</b> ").replaceAll(" from ", " <b>from</b> "); %>
            <% } %>

            <td><%= label %>
            </td>
            <td align="right" style="width:50px;">
                <% if (executingTime != null) { %>
                <% if (executingCount > 0) { %>
                <%= new BigDecimal((double) executingTime / executingCount).round(mathContext).doubleValue() %>
                <% } else { %>
                0
                <% } %>
                <% } else { %>
                N/A
                <% } %>
            </td>
            <td align="right" style="width:50px;"><%= format.format(executingCount) %>
            </td>
            <td align="right" style="width:50px;">
                <% if (executingTime != null) { %>
                <%= format.format(timeCounterResult.getExecutingTime()) %>
                <% } else { %>
                N/A
                <% } %>
            </td>
            <td align="right" style="width:50px;">
                <% if (timeCounterResult.getExecutedCount() > 0) { %>
                <%= new BigDecimal((double) timeCounterResult.getExecutedTime() / timeCounterResult.getExecutedCount()).round(mathContext).doubleValue() %>
                <% } else { %>
                0
                <% } %>
            </td>
            <td align="right" style="width:50px;"><%= format.format(timeCounterResult.getExecutedCount()) %>
            </td>
            <td align="right" style="width:50px;">
                <%= format.format(timeCounterResult.getExecutedTime()) %>

                <% if (timeCounterResult.getExecutedHistory().size() > 1) {
                    final long id = System.nanoTime(); %>
                <br>
                <a href="javascript:showExecutedTimesDetails('<%= id %>');">View detailed history</a>

                <div id="detailedStatistic<%= id %>" style="display:none;">
                    <div style="width:100%;text-align:center;max-height:500px;overflow-y:auto;">
                        <% for (long time : timeCounterResult.getExecutedHistory()) { %>
                        <%= time %>ms
                        <br>
                        <% } %>
                    </div>
                </div>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</body>
</html>