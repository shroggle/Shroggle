<%@ page import="com.shroggle.presentation.blog.ShowBlogPostsAction" %>
<%@ page import="com.shroggle.logic.blog.BlogDispatchType" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<international:part part="showBlogPosts"/>
<%
    final ShowBlogPostsAction action = (ShowBlogPostsAction) request.getAttribute("actionBean");
    final int blogId = action.getBlog().getBlogId();
    final int widgetBlogId = action.getWidgetBlogId();

    final int itemsBefore = action.getBlogPosts().getItemsBefore();
    final int itemsAfter = action.getBlogPosts().getItemsAfter();

    final boolean showInsideApp = action.getSiteShowOption() == SiteShowOption.INSIDE_APP;
%>
<% if (action.getBlog().isDisplayNextAndPreviousLinks()) { %>
<% if (itemsBefore > 0 || itemsAfter > 0) { %>
<div class="previousOrNextBlogPosts">
    <% if (action.getExactBlogPostId() != null) { %>
    <a ajaxHistory="#" onclick="return ajaxDispatcher.execute(this);"
            <% if (showInsideApp) { %>
       href="javascript:showBlogPosts(<%= widgetBlogId %>, <%= blogId %>, 0);"
            <% } else { %>
       href="<%= action.getShowPageVersionUrl() %>"
            <% } %>><international:get name="backToBlog"/></a>
    <% } %>
    <% if (itemsBefore > 0) { %>
    <%-- "Previous %post_count%" link --%>
    <a ajaxHistory="#dispatchBlog<%= blogId %>=<%= BlogDispatchType.SHOW_BLOG %>/siteShowOption=<%= action.getSiteShowOption() %>/widgetId=<%= widgetBlogId %><%= action.getExactBlogPostId() != null ? "/exactBlogPostId=" + action.getBlogPosts().getPrevBlogPostId() : "/startIndex=" + action.getBlogPosts().getPrevStartIndex() %>"
       onclick="return ajaxDispatcher.execute(this);"
            <% if (showInsideApp) { %>
       href="javascript:showBlogPosts(<%= widgetBlogId %>, <%= blogId %>, <%= action.getExactBlogPostId() != null ? "null, " + action.getBlogPosts().getPrevBlogPostId() : action.getBlogPosts().getPrevStartIndex() %>)"
            <% } else { %>
       href="<%= action.getShowPageVersionUrl() %>&widgetId=<%= widgetBlogId %><%= action.getExactBlogPostId() != null ? "&exactBlogPostId=" + action.getBlogPosts().getPrevBlogPostId() : "&startIndex=" + action.getBlogPosts().getPrevStartIndex() %>"
            <% } %>><% if (action.getExactBlogPostId() == null) {%>
        <international:get name="previous">
            <international:param value="${actionBean.blogPosts.itemsBefore}"/>
        </international:get><% } else { %><international:get name="justPrevious"/><% } %></a>
    <% } %>
    <% if (itemsAfter > 0) { %>
    <%-- "Next %post_count%" link --%>
    <a ajaxHistory="#dispatchBlog<%= blogId %>=<%= BlogDispatchType.SHOW_BLOG %>/siteShowOption=<%= action.getSiteShowOption() %>/widgetId=<%= widgetBlogId %><%= action.getExactBlogPostId() != null ? "/exactBlogPostId=" + action.getBlogPosts().getNextBlogPostId() : "/startIndex=" + action.getBlogPosts().getNextStartIndex() %>"
       onclick="return ajaxDispatcher.execute(this);"
            <% if (showInsideApp) { %>
       href="javascript:showBlogPosts(<%= widgetBlogId %>, <%= blogId %>, <%= action.getExactBlogPostId() != null ? "null, " + action.getBlogPosts().getNextBlogPostId() : action.getBlogPosts().getNextStartIndex() %>)"
            <% } else { %>
       href="<%= action.getShowPageVersionUrl() %>&widgetId=<%= widgetBlogId %><%= action.getExactBlogPostId() != null ? "&exactBlogPostId=" + action.getBlogPosts().getNextBlogPostId() : "&startIndex=" + action.getBlogPosts().getNextStartIndex() %>"
            <% } %>>
        <% if (action.getExactBlogPostId() == null) {%>
        <international:get name="next">
            <international:param value="${actionBean.blogPosts.itemsAfter}"/>
        </international:get><% } else { %><international:get name="justNext"/><% } %></a>
    <% } %>
</div>
<% } %>
<% } %>