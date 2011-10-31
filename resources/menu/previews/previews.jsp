<%@ page import="com.shroggle.entity.MenuStyleType" %>
<%--
    @author Balakirev Anatoliy
--%>
<% final MenuStyleType menuStyleType = (MenuStyleType) request.getAttribute("menuStyleType"); %>
<% if (menuStyleType == MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL) { %>
<%@ include file="dropDownHorizontal.jsp" %>
<% } else if (menuStyleType == MenuStyleType.DROP_DOWN_STYLE_VERTICAL) { %>
<%@ include file="dropDownVertical.jsp" %>
<% } else if (menuStyleType == MenuStyleType.FULL_HEIGHT_STYLE_HORIZONTAL) { %>
<%@ include file="fullHeightHorizontal.jsp" %>
<% } else if (menuStyleType == MenuStyleType.FULL_HEIGHT_STYLE_VERTICAL) { %>
<%@ include file="fullHeightVertical.jsp" %>
<% } else if (menuStyleType == MenuStyleType.TABBED_STYLE_HORIZONTAL) { %>
<%@ include file="tabbedHorizontal.jsp" %>
<% } else if (menuStyleType == MenuStyleType.TREE_STYLE_HORIZONTAL) { %>
<%@ include file="treeStyleHorizontal.jsp" %>
<% } else if (menuStyleType == MenuStyleType.TREE_STYLE_VERTICAL) { %>
<%@ include file="treeStyleVertical.jsp" %>
<% } %>