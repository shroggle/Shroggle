<%@ page import="com.shroggle.entity.MenuStyleType" %>
<%@ page import="com.shroggle.logic.menu.MenuItemData" %>
<%@ page import="com.shroggle.logic.menu.MenuItemDataHolderColumn" %>
<%--
 @author Balakirev Anatoliy
--%>
<% final MenuItemDataHolderColumn column = (MenuItemDataHolderColumn) request.getAttribute("column");
    final MenuItemData parent = (MenuItemData) request.getAttribute("parent");
    final Double opacity = (Double) request.getAttribute("opacity");
    final Boolean addSeparator = (Boolean) request.getAttribute("addSeparator");
    final MenuStyleType menuStyleType = (MenuStyleType) request.getAttribute("menuStyleType");
    final String menuStyle = menuStyleType.getOldValueAsString(); %>

<div class='menuContainer_<%= menuStyle %> subMenu<%= parent.getNumber() %>' style='filter:alpha(opacity=<%= opacity * 100 %>);opacity:<%= opacity %>;'>
<% for (MenuItemData child : column.getChildren()) {
    final boolean currentPageIsSelected = child.isSelected();
    final boolean elementHasChildren = !child.getChildren().isEmpty() && menuStyleType.isHasSubmenu();
    final boolean lastBottom = child.isLastBottom();
    final boolean lastRight = child.isLastRight();
    String subMenuClass = ((currentPageIsSelected ? " selectedSubMenu_" : " subMenu_") + menuStyle);
    subMenuClass += (addSeparator ? " separator" : "");
    subMenuClass += (" subMenuLevel" + (child.getLevel() + 1));
    subMenuClass += (" subMenuItemNumber" + child.getNumber()); %>
<%-----------------------------todo. I think we can replace this table with one div. Tolik----------------------------%>
<table id='page<%= parent.getId() %>_<%= child.getIndex() %>' class='<%= subMenuClass %>' style="width:100%;"
       cellspacing='0' cellpadding='0' border='0' <% if (elementHasChildren) { %>menu="page<%= child.getId() %>"<% } %>>
    <tr>
        <td class='<%= lastBottom ? "lastBottom" : "" %><%= lastRight ? " lastRight" : "" %>'>
            <% if (elementHasChildren) { %>
            <div class='menuArrow subMenuOpener' isOpener='true'>
                <% } %>
                <a href="<%= child.getHref() %>" externalUrl="<%= child.isExternalUrl() %>"
                    <% if (child.isShowImage()) { %>class="submenuLinkWithImage"<% } %>>
                    <% if (child.isShowImage() && !child.getImageUrl().isEmpty()) { %>
                    <img src="<%= child.getImageUrl() %>" class="submenuImage_<%= menuStyle %>" alt="">
                    <br>
                    <% } %>
                    <span class="menuPageNameTextSubLevel_<%= menuStyle %>">
                        <%= child.getName() %>
                    </span>
                </a>
                <% if (elementHasChildren) { %>
            </div>
            <% } %>
        </td>
    </tr>
</table>
<%-----------------------------todo. I think we can replace this table with one div. Tolik----------------------------%>    
<% } %>
</div>