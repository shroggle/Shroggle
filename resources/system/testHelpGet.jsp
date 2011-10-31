<%@ page import="com.shroggle.util.testhelp.TestHelpGetAction" %>
<%@ page import="com.shroggle.util.testhelp.TestHelpSource" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final TestHelpGetAction action = (TestHelpGetAction) request.getAttribute("actionBean"); %>
<html>
    <head>
        <title>Use this text for create jira bug.</title>
    </head>
    <body>
        <% if (action.getStringBySources() != null) { %>
<pre>
{code}
<i>Last n events.</i>
<% for (final TestHelpSource source : TestHelpSource.values()) { %>
<b><%= source %>:</b>
<% for (String string : action.getStringBySources().get(source).get()) { %><%= string %><% } %>
<% } %>
{code}
</pre>
        <% } else { %>
            Test help don't on.
        <% } %>
    </body>
</html>