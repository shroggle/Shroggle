<%@ tag import="com.shroggle.logic.site.page.pageversion.PageTitle" %>
<%@ tag import="com.shroggle.presentation.site.WithPageVersionTitle" %>
<%@ tag import="com.shroggle.util.html.HtmlUtil" %>
<%@ tag import="com.shroggle.util.StringUtil" %>
<%@ tag body-content="empty" %>
<%@ attribute name="customServiceName" required="false" type="java.lang.String" %>
<%
    WithPageVersionTitle withPageVersionTitle = null;

    if (StringUtil.isNullOrEmpty(customServiceName)) {
        final Object action = request.getAttribute("actionBean");
        if (action != null) {
            if (action instanceof WithPageVersionTitle) {
                withPageVersionTitle = (WithPageVersionTitle) action;
            } else {
                throw new UnsupportedOperationException(
                        "Can't get page version info from action that not support page version info interface!");
            }
        }
        final Object service = request.getAttribute("service");
        if (service != null) {
            if (service instanceof WithPageVersionTitle) {
                withPageVersionTitle = (WithPageVersionTitle) service;
            } else {
                throw new UnsupportedOperationException(
                        "Can't get page version info from service that not support page version info interface!");
            }
        }
    } else {
        final Object service = request.getAttribute(customServiceName);
        if (service != null) {
            if (service instanceof WithPageVersionTitle) {
                withPageVersionTitle = (WithPageVersionTitle) service;
            } else {
                throw new UnsupportedOperationException(
                        "Can't get page version info from service that not support page version info interface!");
            }
        }
    }

    if (withPageVersionTitle == null) {
        throw new UnsupportedOperationException(
                "Can't get page version info. No action or service in request attribute!");
    }

    final PageTitle pageTitle = withPageVersionTitle.getPageTitle();
    if (pageTitle == null) {
        throw new UnsupportedOperationException(
                "Can't get page info. Action or service return null page version info!");
    }
%>
<h2>
    <%= HtmlUtil.limitName(pageTitle.getSiteTitle(), 40) %>
    / <%= HtmlUtil.limitName(pageTitle.getPageVersionTitle(), 40) %>
</h2>

