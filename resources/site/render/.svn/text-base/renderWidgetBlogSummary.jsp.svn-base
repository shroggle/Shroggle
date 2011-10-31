<%@ page import="com.shroggle.entity.BlogPost" %>
<%@ page import="com.shroggle.logic.blog.BlogPostManager" %>
<%@ page import="com.shroggle.presentation.blogSummary.BlogSummaryDataForPreview" %>
<%@ page import="com.shroggle.util.DateUtil" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="com.shroggle.entity.BlogSummary" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final List<BlogSummaryDataForPreview> blogsWithWidgets = (List<BlogSummaryDataForPreview>) request.getAttribute("blogsWithWidgets");
    final BlogSummary blogSummary = (BlogSummary) request.getAttribute("blogSummary"); %>
<%-------------------------------------------------Blog Summary Header------------------------------------------------%>
<% if (blogSummary.isDisplayBlogSummaryHeader()) { %>
<div class="blogSummaryHeader">
    <%= blogSummary.getBlogSummaryHeader() %>
</div>
<% } %>
<%-------------------------------------------------Blog Summary Header------------------------------------------------%>


<% for (BlogSummaryDataForPreview blogSummaryData : blogsWithWidgets) { %>


<%------------------------------------------------------Blog Name-----------------------------------------------------%>
<% if (blogSummary.isShowBlogName()) { %>
<div class="blogSummaryBlogName">
    <%= blogSummaryData.getBlogName() %>
</div>
<% } %>
<%------------------------------------------------------Blog Name-----------------------------------------------------%>


<% for (BlogPost blogPost : blogSummaryData.getBlogPosts()) { %>

<div class="blogSummaryData">
    <%----------------------------------------------------Post Name---------------------------------------------------%>
    <% if (blogSummary.isShowPostName()) { %>
    <div class="blogSummaryPostName">
        <% if (blogSummary.isPostTitleALink()) { %>
        <a href="<%= blogSummaryData.getPostUrl().get(blogPost.getBlogPostId()) %>">
            <%= blogPost.getPostTitle() %>&nbsp;
        </a>
        <% } else { %>
        <%= blogPost.getPostTitle() %>&nbsp;
        <% } %>
    </div>
    <% } %>
    <%----------------------------------------------------Post Name---------------------------------------------------%>

    <div>
        <%-------------------------------------------------Post Author------------------------------------------------%>
        <% if (blogSummary.isShowPostAuthor()) { %>
        <span class="blogSummaryPostAuthor">
            <%= new BlogPostManager(blogPost).getPostAuthor() %>&nbsp;
        </span>
        <% } %>
        <%-------------------------------------------------Post Author------------------------------------------------%>


        <%--------------------------------------------------Post Date-------------------------------------------------%>
        <% if (blogSummary.isShowPostDate()) { %>
        <span class="blogSummaryPostDate">
            <%= DateUtil.getDateForBlogAndBlogSummary(blogPost.getCreationDate()) %>&nbsp;
        </span>
        <% } %>
        <%--------------------------------------------------Post Date-------------------------------------------------%>


        <%--------------------------------------------------Post Text-------------------------------------------------%>
        <% if (blogSummary.isShowPostContents()) { %>
        <div class="blogSummaryPostText">
            <% // prepare post text
                String postText = "";
                if (blogSummary.getNumberOfWordsToDisplay() > 0) {
                    String blogPostText = HtmlUtil.replaceBlockTagsByBr(blogPost.getText());
                    blogPostText = HtmlUtil.removeAllTagsExceptBr(blogPostText);
                    StringTokenizer tokenizer = new StringTokenizer(blogPostText);
                    if (tokenizer.countTokens() > blogSummary.getNumberOfWordsToDisplay()) {
                        for (int i = 0; i < blogSummary.getNumberOfWordsToDisplay(); i++) {
                            postText += (" " + tokenizer.nextToken());
                        }
                        postText += " ...";
                    } else {
                        postText = blogPostText;
                    }
                } else {
                    postText = blogPost.getText();
                }
            %>
            <a href="<%= blogSummaryData.getPostUrl().get(blogPost.getBlogPostId()) %>">
                <%= postText %>
            </a>
        </div>
        <% } %>
        <%--------------------------------------------------Post Text-------------------------------------------------%>
    </div>
</div>
<% } %>
<br/>
<% } %>
