<%@ tag import="com.shroggle.logic.widget.WidgetTitle" %>
<%@ tag import="com.shroggle.presentation.site.WithWidgetTitle" %>
<%@ tag import="com.shroggle.util.html.HtmlUtil" %>
<%@ tag import="com.shroggle.util.StringUtil" %>
<%@ attribute name="customServiceName" required="false" type="java.lang.String" %>
<%

    WithWidgetTitle withWidgetTitle = null;

    if (StringUtil.isNullOrEmpty(customServiceName)) {
        final Object action = request.getAttribute("actionBean") != null ? request.getAttribute("actionBean") : request.getAttribute("service");
        if (action != null) {
            if (action instanceof WithWidgetTitle) {
                withWidgetTitle = (WithWidgetTitle) action;
            } else {
                throw new UnsupportedOperationException(
                        "Can't get widget info from action that not support widget info interface!");
            }
        }
    } else {
        final Object service = request.getAttribute(customServiceName);
        if (service != null) {
            if (service instanceof WithWidgetTitle) {
                withWidgetTitle = (WithWidgetTitle) service;
            } else {
                throw new UnsupportedOperationException(
                        "Can't get widget info from service that not support widget info interface!");
            }
        }
    }

    if (withWidgetTitle == null) {
        throw new UnsupportedOperationException(
                "Can't get widget info. No action or service in request attribute!");
    }

    final WidgetTitle widgetTitle = withWidgetTitle.getWidgetTitle();
    if (widgetTitle == null) {
        throw new UnsupportedOperationException(
                "Can't get widget info. Action or service return null widget info!");
    }
%>
<h2>
    <%= HtmlUtil.limitName(widgetTitle.getSiteTitle(), 40) %> / <%= HtmlUtil.limitName(widgetTitle.getPageVersionTitle(), 40) %> <%if (widgetTitle.getWidgetTitle() != null){%>/ <%= HtmlUtil.limitName(widgetTitle.getWidgetTitle(), 40) %><%}%>
</h2>

