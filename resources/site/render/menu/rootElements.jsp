<%@ page import="com.shroggle.logic.menu.MenuItemData" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.MenuStyleType" %>
<%@ page import="com.shroggle.logic.menu.MenuItemDataManager" %>
<%@ page import="com.shroggle.entity.Menu" %>
<%--
 @author Balakirev Anatoliy
--%>
<%
    final MenuItemDataManager menuItemManager = (MenuItemDataManager) request.getAttribute("menuItemManager");
    final List<MenuItemData> menuItemDataList = menuItemManager.getRootElementsData();
    final Menu menu = (Menu) request.getAttribute("menu");
    final boolean vertical = menu.getMenuStyleType().isVertical();
    final boolean treeStyle = menu.getMenuStyleType() == MenuStyleType.TREE_STYLE_HORIZONTAL || menu.getMenuStyleType() == MenuStyleType.TREE_STYLE_VERTICAL;
    boolean addHorizontalSeparator = treeStyle && !vertical;
    int menuPageInfoIndex = -1;
    final String menuStyle = menu.getMenuStyleType().getOldValueAsString();
    final int cellspacing = menu.getMenuStyleType() == MenuStyleType.TABBED_STYLE_HORIZONTAL ? 3 : 0;
%>
<table class="rootVoices_<%= menuStyle %> <%= vertical ? " vertical" : ""%>" cellspacing='<%= cellspacing %>'
       cellpadding='0' border='0' style="background-color:inherit;">
    <% if (!vertical) { %>
    <tr>
            <% } for (MenuItemData menuItemData : menuItemDataList) {
            ++menuPageInfoIndex;
            String topLevelMenuClass = ((menuItemData.isSelected() ? "selectedTopLevelMenuItem_" : "topLevelMenuItem_") + menuStyle);
            topLevelMenuClass += (vertical ? " vertical separator" : " horizontal");
            if (vertical) { %>
    <tr><% } %>
        <td <% if (menuItemData.hasChildren()) { %> menu="page<%= menuItemData.getId() %>" <% } %>
                                                               class="<%= topLevelMenuClass %>"
                                                               menuRootElement="true">
            <a href="<%= menuItemData.getHref() %>" externalUrl="<%= menuItemData.isExternalUrl() %>"
               class="topLevelItem<%= menuItemData.getNumber() %>">
                <span class="menuPageNameText_<%= menuStyle %>">
                    <%= menuItemData.getName() %>
                </span>
                <% if (menu.isIncludePageTitle() && !treeStyle && !menuItemData.getDescription().isEmpty()) { %>
                <br/>
                <span class="menuPageDescText_<%= menuStyle %>">
                    <%= menuItemData.getDescription() %>
                </span><% } %>
            </a>
        </td>
        <% if (addHorizontalSeparator && (menuPageInfoIndex != menuItemDataList.size() - 1)) { %>
        <td class="horizontalSeparator">|</td>
        <% } %>
        <% if (vertical) { %></tr>
        <% } } if (!vertical) { %></tr><% } %>
</table>
