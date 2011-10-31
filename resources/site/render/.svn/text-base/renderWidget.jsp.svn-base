<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.site.WidgetManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final Widget widget = (Widget) request.getAttribute("widget");
    final ItemSize itemSize = (ItemSize) request.getAttribute("itemSize");
    final ItemType itemType = widget.getItemType();
    final boolean showFormSiteEditPage = (Boolean) request.getAttribute("showFromSiteEditPage");
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
    final String widgetHtml = (String) request.getAttribute("widgetHtml");
    final WidgetManager widgetManager = new WidgetManager(widget);
    final String width = widgetManager.getItemSize(siteShowOption).getWidth() + (itemSize.getWidthSizeType().equals(WidgetSizeType.PERCENT) ? "%" : "px");
    final String height = widgetManager.getItemSize(siteShowOption).getHeight() + (itemSize.getHeightSizeType().equals(WidgetSizeType.PERCENT) ? "%" : "px");
    final String overflow_x = itemSize.getOverflow_x().toString().toLowerCase();
    final String overflow_y = itemSize.getOverflow_y().toString().toLowerCase();
%>

<input type="hidden" id="itemType<%= widget.getWidgetId() %>" value="<%= itemType %>"/>
<% if (!widget.isWidgetComposit() && !showFormSiteEditPage && !siteShowOption.equals(SiteShowOption.INSIDE_APP)) { %>
<div style="width: <%= width %>; <% if (itemSize.getHeight() != null) { %>height: <%= height %> <% } %>; overflow-x: <%= overflow_x %>; overflow-y: <%= overflow_y %>; <%= itemSize.isFloatable() ? "float:left;" : "" %>"
        id="itemBlock<%= widget.getWidgetId() %>">
    <% } %>
    <%= widgetHtml %>
    <% if (!widget.isWidgetComposit()) { %>
</div>
<% }
    if (itemSize.isCreateClearDiv() && !showFormSiteEditPage && !siteShowOption.equals(SiteShowOption.INSIDE_APP)) { %>
<div style="clear:both;"></div>
<% } %>