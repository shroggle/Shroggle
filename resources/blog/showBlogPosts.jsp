<%@ page import="com.shroggle.entity.BlogPost" %>
<%@ page import="com.shroggle.logic.blog.BlogPostManager" %>
<%@ page import="com.shroggle.presentation.blog.ShowBlogPostsAction" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.blog.BlogDispatchType" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<international:part part="showBlogPosts"/>
<% final ShowBlogPostsAction action = (ShowBlogPostsAction) request.getAttribute("actionBean");
    final int blogId = action.getBlog().getBlogId();
    final int widgetBlogId = action.getWidgetBlogId(); %>
<div id="blockToReload<%= widgetBlogId %>">
    <script type="text/javascript"> window.siteId = <%= action.getSiteId() %>; </script>
    <input type="hidden" id="blogId<%= widgetBlogId %>" value="<%= blogId %>"/>
    <input type="hidden" id="siteShowOption<%= widgetBlogId %>" value="<%= action.getSiteShowOption() %>"/>
    <input type="hidden" id="promptLogin<%= widgetBlogId %>" value="<international:get name="promptLogin"/>"/>
    <input type="hidden" id="promptLoginPrivileges<%= widgetBlogId %>"
           value="<international:get name="promptLoginPrivileges"/>"/>
    <input type="hidden" id="loginedUserId<%= widgetBlogId %>"
           value="<%= action.getLoginedUser() != null ? action.getLoginedUser().getUserId() : null %>"/>

    <%-----------------------------------------------------Blog Name------------------------------------------------------%>
    <% if (action.getBlog().isDisplayBlogName()) { %>
    <div class="blogName">
        <%= action.getBlog().getName() %>
    </div>
    <% } %>
    <%-----------------------------------------------------Blog Name------------------------------------------------------%>
    <% if (action.getBlog().allowAddBlogPost()) { %>
    <div class="addBlogPost" id="widget<%= widgetBlogId %>addBlogPost<%= blogId %>">
        <% if (action.getSiteShowOption() == SiteShowOption.INSIDE_APP) { %>
        <input type="button" value="<international:get name="addPost"/>"
               class="but_w100" onmouseover="this.className='but_w100_Over';"
               onclick="showAddBlogPost(<%= widgetBlogId %>, <%= blogId %>);"
               onmouseout="this.className='but_w100';"/>
        <% } else { %>
        <a href="javascript:showAddBlogPost(<%= widgetBlogId %>, <%= blogId %>)"><international:get name="addPost"/></a>
        <% } %>
    </div>
    <% } %>

    <div class="naviagtionUpperBlock">
        <jsp:include page="showBlogPostsNavigation.jsp" flush="true"/>
    </div>

    <%
        for (int i = 0; i < action.getBlogPosts().getItems().size(); i++) {
            final BlogPostManager blogPostManager = action.getBlogPosts().getItems().get(i);
            final BlogPost blogPost = blogPostManager.getBlogPost();
    %>
    <div class="blogPost <% if (i != 0) { %>blogPostShifted<% } %>"
         id="widget<%= widgetBlogId %>blogPost<%= blogPost.getBlogPostId() %>">
        <div class="blogHeader">
            <%------------------------------------------------Blog Post Title-------------------------------------------------%>
            <% if (blogPost.getPostTitle() != null) { %>
        <span class="blogPostTitle">
            <a ajaxHistory="#dispatchBlog<%= blogId %>=<%= BlogDispatchType.SHOW_BLOG %>/siteShowOption=<%= action.getSiteShowOption() %>/widgetId=<%= widgetBlogId %>/exactBlogPostId=<%= blogPost.getBlogPostId() %>"
               onclick="return ajaxDispatcher.execute(this);"
                    <% if (action.getSiteShowOption() == SiteShowOption.INSIDE_APP) { %>
               href="javascript:showBlogPosts(<%= widgetBlogId %>, <%= blogId %>, null, <%= blogPost.getBlogPostId() %>);"
                    <% } else { %>
               href="<%= action.getShowPageVersionUrl() %>&widgetId=<%= widgetBlogId %>&exactBlogPostId=<%= blogPost.getBlogPostId() %>"
                    <% } %>
               id="widget<%= widgetBlogId %>blogPostTitle<%= blogPost.getBlogPostId() %>">
                <%= blogPost.getPostTitle() %>
            </a>
        </span>
            <% } %>
            <%------------------------------------------------Blog Post Title-------------------------------------------------%>
            <div class="blogPostInfo">
                <%---------------------------------------------Author Screen Name---------------------------------------------%>
                <% if (action.getBlog().isDisplayAuthorScreenName() && blogPostManager.getAuthor() != null) { %>
        <span class="blogPostAuthorScreenName">
            <%= StringUtil.getEmptyOrString(blogPostManager.getAuthor().getScreenName()) %>
        </span><% } %>
                <%---------------------------------------------Author Screen Name---------------------------------------------%>
                <%--------------------------------------------Author Email Address--------------------------------------------%>
                <% if (action.getBlog().isDisplayAuthorEmailAddress()) { %>
        <span class="blogPostAuthor">
            by&nbsp;<% if (blogPostManager.getAuthor() != null) { %><%= blogPostManager.getAuthor().getEmail() %><% } else { %><international:get
                name="anonymous"/><% } %>
        </span><% } %>
                <%--------------------------------------------Author Email Address--------------------------------------------%>
                <%-----------------------------------------------Blog Post Date-----------------------------------------------%>
                <% if (action.getBlog().isDisplayDate()) { %>
        <span class="blogPostDate" id="widget<%= widgetBlogId %>blogPostDate<%= blogPost.getBlogPostId() %>">
            at&nbsp;<%= DateUtil.getDateForBlogAndBlogSummary(blogPost.getCreationDate()) %>
        </span><% } %>
                <input type="hidden" id="widget<%= widgetBlogId %>blogPostCreationDate<%= blogPost.getBlogPostId() %>"
                       value="<%= DateUtil.toMonthDayAndYear(blogPost.getCreationDate()) %>">
                <%-----------------------------------------------Blog Post Date-----------------------------------------------%>
            </div>

        </div>

        <div class="blogPostText" id="widget<%= widgetBlogId %>blogPostText<%= blogPost.getBlogPostId() %>">
            <%= blogPostManager.getText() %>
        </div>
        <div class="blogPostLinks">
            <% if (blogPostManager.isEditable()) { %>
        <span class="blogPostDelete">
            <a href="javascript:deleteBlogPost(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>, <%= blogId %>, <%= action.getStartIndex() %>)"><international:get
                    name="delete"/></a>
        </span>
        <span class="blogPostUpdate"
              id="widget<%= widgetBlogId %>blogPostEditDraft<%= blogPost.getBlogPostId() %>"
              style="display: <%= blogPostManager.isDraft() ? "inline" : "none" %>;">
            <a href="javascript:showEditBlogPost(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>)"
                    ><international:get name="editDraft"/></a>
        </span>
        <span class="blogPostUpdate"
              id="widget<%= widgetBlogId %>blogPostEditPost<%= blogPost.getBlogPostId() %>"
              style="display: <%= !blogPostManager.isDraft() ? "inline" : "none" %>;">
            <a href="javascript:showEditBlogPost(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>)"
                    ><international:get name="editPost"/></a>
        </span>
            <% final String visibility = blogPostManager.isDraft() ? "inline" : "none"; %>
        <span class="blogPostPostToWork"
              id="widget<%= widgetBlogId %>blogPostPostToWork<%= blogPost.getBlogPostId() %>"
              style="display: <%= visibility %>;">
            <a href="javascript:postToWorkBlogPost(<%= widgetBlogId %>, <%= blogId %>, <%= blogPost.getBlogPostId() %>, <%= action.getStartIndex() %>)"
                    ><international:get name="postToWork"/></a>
        </span>
        <span class="blogPostResetChanges"
              id="widget<%= widgetBlogId %>blogPostResetChanges<%= blogPost.getBlogPostId() %>"
              style="display: <%= visibility %>;">
            <a href="javascript:resetChangesBlogPost(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>, <%= blogId %>, <%= action.getStartIndex() %>)">
                <% if (blogPostManager.getText() != null) { %>
                <international:get name="resetChanges"/>
                <% } else { %>
                <international:get name="discardChanges"/>
                <% } %>
            </a>
        </span>
            <% } %>

            <% if (action.getBlog().isAllowComments()) { %>
                <span class="addBlogPostCommentLink">
                    <a href="javascript:showAddBlogPostComment(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>, <%= action.getBlog().isAllowAddComment() %>)"><international:get
                            name="addComment"/></a>
                </span>

                <span class="blogPostCommentsShowHide">
                     <a href="<% if (blogPostManager.getCommentsCount() != 0) { %>javascript:showBlogPostComments(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>)<% } else { %>#<% } %>"
                        id="widget<%= widgetBlogId %>blogPostCommentsShowLink<%= blogPost.getBlogPostId() %>"><international:get
                             name="showComments"/> (<span style="padding:0;" id="blogPostCommentCount<%= blogPost.getBlogPostId() %>"><%= blogPostManager.getCommentsCount() %></span>)</a>
                </span>
            <% } %>

         <span class="blogPostCommentsShowHide">
             <a href="javascript:hideBlogPostComments(<%= widgetBlogId %>, <%= blogPost.getBlogPostId() %>)"
                style="display:none;"
                id="widget<%= widgetBlogId %>blogPostCommentsHideLink<%= blogPost.getBlogPostId() %>"><international:get
                     name="hideComments"/></a>
         </span>
        </div>

    </div>

    <div class="addBlogPostCommentLink"
         id="widget<%= widgetBlogId %>addBlogPostComment<%= blogPost.getBlogPostId() %>">
    </div>

    <div class="blogPostComments" id="widget<%= widgetBlogId %>blogPostComments<%= blogPost.getBlogPostId() %>">
    </div>
    <% } %>

    <div class="naviagtionLowerBlock">
        <jsp:include page="showBlogPostsNavigation.jsp" flush="true"/>
    </div>

    <% if (action.getBlog().isDisplayBackToTopLink()) { %>
    <div class="blogsBackToTop">
        <a href="#widget<%= widgetBlogId %>"><international:get name="backToTop"/></a>
    </div>
    <% } %>
</div>