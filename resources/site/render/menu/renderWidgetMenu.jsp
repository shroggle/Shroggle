<%--
  @author Balakirev Anatoliy
 --%>
<%@ page import="com.shroggle.entity.Menu" %>
<%@ page import="com.shroggle.entity.MenuStyleType" %>
<%@ page import="com.shroggle.logic.menu.MenuItemData" %>
<%@ page import="com.shroggle.logic.menu.MenuItemDataHolder" %>
<%@ page import="com.shroggle.logic.menu.MenuItemDataHolderColumn" %>
<%@ page import="com.shroggle.logic.menu.MenuItemDataManager" %>
<%@ page import="com.shroggle.util.BooleanUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="onload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%
    final MenuItemDataManager menuItemManager = (MenuItemDataManager) request.getAttribute("menuItemManager");
    final Menu menu = (Menu) request.getAttribute("menu");
    //Menu attributes
    final boolean addSeparator = menu.getMenuStyleType() == MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL ||
            menu.getMenuStyleType() == MenuStyleType.DROP_DOWN_STYLE_VERTICAL;
    final double opacity = (menu.getMenuStyleType() == MenuStyleType.FULL_HEIGHT_STYLE_HORIZONTAL ||
            menu.getMenuStyleType() == MenuStyleType.FULL_HEIGHT_STYLE_VERTICAL) ? .75 : .95;
    final String menuStyle = menu.getMenuStyleType().getOldValueAsString();
    final String orientation = menu.getMenuStyleType().isVertical() ? "vertical" : " ";
%>
<div id="menu<%= menu.getId() %>" class="menuBlock <%= orientation %> menuStrecher_<%= menuStyle %>">
    <jsp:include page="rootElements.jsp" flush="true"/>
    <% final List<MenuItemDataHolder> dataHolders = menuItemManager.getParentsWithChildrenInColumns();
        for (MenuItemDataHolder holder : dataHolders) {
            final MenuItemData parent = holder.getParent();
            final List<MenuItemDataHolderColumn> columns = holder.getColumnsWithChildren(); %>
    <div class='menuDiv' style="display:none;position:absolute;" id="page<%= parent.getId() %>">
        <div class="submenuColumnsHolder_<%= menuStyle %>">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <% request.setAttribute("menuStyleType", menu.getMenuStyleType());
                        request.setAttribute("parent", parent);
                        request.setAttribute("opacity", opacity);
                        request.setAttribute("addSeparator", addSeparator);
                        for (MenuItemDataHolderColumn column : columns) {
                            request.setAttribute("column", column); %>
                    <td class="submenuColumnsTD_<%= menuStyle %>">
                        <jsp:include page="submenuElements.jsp" flush="true"/>
                    </td>
                    <% } %>
                </tr>
            </table>
        </div>
    </div>
    <% } %>
</div>
<br clear="all" style="display:none;">
<% final boolean skipInitialization = BooleanUtils.toBooleanDefaultIfNull(request.getAttribute("showFromSiteEditPage"), false);
    final String createMenuScript = "" +
            "$('#menu" + menu.getId() + "').addMenuEventHandlers({" +
            "openOnRight:" + menu.getMenuStyleType().isVertical() + "," +
            "        submenuSelector: '.menuContainer_" + menuStyle + "'," +
            "        fadeInTime:50," +
            "        fadeOutTime:100," +
            "        menuStyleType:'" + menuStyle + "'," +
            "        shadow:false," +
            "        closeOnMouseOut:true," +
            "        closeAfter:500," +
            "        skipInitialization:" + skipInitialization +
            "    });" +
            ""; %>
<onload:element onload="<%= createMenuScript %>"/>