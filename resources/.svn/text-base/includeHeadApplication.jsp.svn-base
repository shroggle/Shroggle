<%@ page import="com.shroggle.presentation.site.QuicklyCreatePagesAction" %>
<%@ page import="com.shroggle.entity.User" %>
<%@ page import="com.shroggle.presentation.site.LoginedUserInfo" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.logic.user.*" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.international.InternationalStorage" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%
    final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    final International international = internationalStorage.get("head", Locale.US);
    final User loginedUser = ((LoginedUserInfo) request.getAttribute("actionBean")).getLoginUser();
    final String networkLogoUrl = UsersManager.getHisNetworkLogoUrl();
%>

<div class="header" style="min-width:1020px;">

    <script> lastAccessedTime.init(); </script>

    <div class="logo">
        <% if (networkLogoUrl.isEmpty()) { %>
        <a href="/"><img src="/images/logo.png" width="151" height="34"
                         alt="shroggle"></a>
        <% } else { %>
        <a href="<%= UsersManager.getParentSiteUrl() %>"><img src="<%= networkLogoUrl %>" width="151" height="34"
                         alt=""></a>
        <% } %>
    </div>
    <div class="pageHeader">
        <% if (request.getAttribute("actionBean") != null && request.getAttribute("actionBean") instanceof QuicklyCreatePagesAction) { %>
        Add pages
        <% } %>
    </div>
    <div class="topmenu roundedbottom">
        <%
            final String email = loginedUser.getEmail();
            final String limitedEmail = HtmlUtil.limitName(email, 26);
            final boolean limited = !email.equals(limitedEmail);
        %>
        <span class="loginedUserEmailDiv" <% if (limited) { %>title="<%= email %>"<% } %>>
            <%= limitedEmail %>
        </span>
        <span class="topmenu_links">
            <stripes:link class="rounded"
                          beanclass="com.shroggle.presentation.account.dashboard.DashboardAction"><%=international.get("yourDashboard") %>
            </stripes:link> |             
            <stripes:link class="rounded"
                          beanclass="com.shroggle.presentation.account.items.ManageItemsAction"><%=international.get("manageItems") %>
            </stripes:link> |
            <stripes:link class="rounded"
                          beanclass="com.shroggle.presentation.site.UserInfoAction"><%= international.get("accountSettings") %>
            </stripes:link> |
            <a class="rounded" href="#" onclick="showApplicationHelpWindow();"><%= international.get("helpfaq") %>
            </a> |
            <a class="rounded" target="_top" href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Contact">Contact Us</a> |
            <stripes:link class="rounded"
                          beanclass="com.shroggle.presentation.site.LogoutFromUserAction"><%= international.get("signOut") %>
            </stripes:link>
        </span>
    </div>
</div>
