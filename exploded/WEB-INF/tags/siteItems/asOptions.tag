<%@ tag import="com.shroggle.presentation.site.SiteItemByNameComparator" %>
<%@ tag import="com.shroggle.util.html.HtmlUtil" %>
<%@ tag import="java.util.Collections" %>
<%@ tag import="java.util.List" %>
<%@ tag import="com.shroggle.logic.site.item.ItemManager" %>
<%@ tag body-content="empty" %>
<%@ attribute name="value" required="true" type="java.util.List" %>
<%@ attribute name="selectedItemId" required="false" type="java.lang.Integer" %>
<%
    final List<ItemManager> siteItemManagers = (List<ItemManager>) jspContext.getAttribute("value");
    final Integer selectedItemIdInt = (Integer) jspContext.getAttribute("selectedItemId");
    Collections.sort(siteItemManagers, SiteItemByNameComparator.instance);
%>
<% for (final ItemManager formManager : siteItemManagers) { %>
    <option value="<%= formManager.getId() %>" formType="<%= formManager.getFormType() %>"
            isChildSiteRegistration="<%= formManager.isChildSiteRegistration() %>"
            <% if (selectedItemIdInt != null && selectedItemIdInt == formManager.getId()) { %>selected="selected"<% } %>>
        <%= HtmlUtil.limitName(formManager.getOwnerSiteName(), 40) %> / <%= HtmlUtil.limitName(formManager.getName(), 40) %>
    </option>
<% } %>

