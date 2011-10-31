<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.presentation.blog.ShowBlogPostCommentsAction" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.international.InternationalStorage" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.logic.blog.CommentSecurity" %>
<%@ page import="com.shroggle.entity.Comment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="showBlogPosts"/>
<%!

    private static void writeCommentNode(
            final ShowBlogPostCommentsAction.CommentNode commentNode,
            final JspWriter jspWriter, boolean isShowAddComment,
            final int widgetBlogId, final int blogPostId,
            final International international) throws IOException {
        final DateFormat format = new SimpleDateFormat("dd/MM/yyyy h:mm:ss aaa");
        final CommentSecurity commentSecurity = commentNode.getCommentSecurity();
        final Comment comment = commentSecurity.getComment();

        if (comment.getText() != null || commentSecurity.isEditable()) {
            jspWriter.append("<div class=\"blogPostComment\" id=\"widget");
            jspWriter.append(Integer.toString(widgetBlogId));
            jspWriter.append("comment");
            jspWriter.append(Integer.toString(comment.getCommentId()));
            jspWriter.append("\">");
            jspWriter.append("<div class=\"blogPostCommentInfo\">");

            if (commentNode.getVisitor() != null) {
                jspWriter.append("<span class=\"blogPostCommentAuthor\">");
                jspWriter.append("by&nbsp;" + commentNode.getVisitor().getEmail());
                jspWriter.append("</span>");
            } else {
                jspWriter.append("<span class=\"blogPostCommentAuthor\">");
                jspWriter.append("by&nbsp;" + international.get("anonymous"));
                jspWriter.append("</span>");
            }
            jspWriter.append("<span class=\"blogPostCommentDate\">");
            jspWriter.append("&nbsp;at&nbsp;" + format.format(comment.getCreated()));
            jspWriter.append("</span>");
            jspWriter.append("</div>");
            if (commentSecurity.isEditable()) {
                jspWriter.append("<div class=\"blogPostCommentLinks\">");

                jspWriter.append("<span class=\"blogPostCommentDelete\"><a href=\"javascript:deleteComment(");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(blogPostId));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append(")\">");
                jspWriter.append(international.get("delete"));
                jspWriter.append("</a></span> &nbsp;");
                jspWriter.append("<span class=\"blogPostCommentEdit\"><a href=\"javascript:showEditComment(");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append(")\">");
                jspWriter.append(international.get("edit"));
                jspWriter.append("</a></span> &nbsp;");
                final String display = comment.getDraftText() != null ? "inline" : "none";
                jspWriter.append("<span class=\"blogPostCommentPostToWork\" id=\"widget");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append("blogPostCommentPostToWork");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append("\"");
                jspWriter.append(" style=\"display: ");
                jspWriter.append(display);
                jspWriter.append("\"><a href=\"javascript:postToWorkComment(");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getBlogPost().getBlogPostId()));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append(")\">");
                jspWriter.append(international.get("postToWork"));
                jspWriter.append("</a></span> &nbsp;");
                jspWriter.append("<span class=\"blogPostCommentResetChanges\" id=\"widget");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append("blogPostCommentResetChanges");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append("\"");
                jspWriter.append(" style=\"display: ");
                jspWriter.append(display);
                jspWriter.append("\"><a href=\"javascript:resetChangesComment(");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getBlogPost().getBlogPostId()));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append(")\">");
                if (comment.getText() == null) {
                    jspWriter.append(international.get("discartComment"));
                } else {
                    jspWriter.append(international.get("resetChanges"));
                }
                jspWriter.append("</a></span>");
                jspWriter.append("</div>");
            }
            jspWriter.append("<div class=\"blogPostCommentText\" id=\"widget");
            jspWriter.append(Integer.toString(widgetBlogId));
            jspWriter.append("commentText");
            jspWriter.append(Integer.toString(comment.getCommentId()));
            jspWriter.append("\">");
            if (commentSecurity.isDraft()) {
                jspWriter.append(comment.getDraftText());
            } else {
                jspWriter.append(comment.getText());
            }
            jspWriter.append("</div>");
            if (isShowAddComment) {
                jspWriter.append("<div class=\"addComment\" id=\"widget");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append("addComment");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append("\">");
                jspWriter.append("<a href=\"javascript:showAddComment(");
                jspWriter.append(Integer.toString(widgetBlogId));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getCommentId()));
                jspWriter.append(", ");
                jspWriter.append(Integer.toString(comment.getBlogPost().getBlogPostId()));
                jspWriter.append(")\">");
                jspWriter.append(international.get("commentThis"));
                jspWriter.append("</a>");
                jspWriter.append("</div>");
            }
            jspWriter.append("<div class=\"blogPostComments\">");
            for (ShowBlogPostCommentsAction.CommentNode childCommentNode : commentNode.getChildCommentNodes()) {
                writeCommentNode(
                        childCommentNode, jspWriter, isShowAddComment,
                        widgetBlogId, blogPostId, international);
            }
            jspWriter.append("</div>");
            jspWriter.append("</div>");
        }
    }

%>
<% final ShowBlogPostCommentsAction action = (ShowBlogPostCommentsAction) request.getAttribute("actionBean"); %>
<% final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage(); %>
<% final International international = internationalStorage.get("showBlogPostComments", Locale.US); %>
<div class="commentsHeader">
    <%= international.get("comments") %>
</div>
<% for (ShowBlogPostCommentsAction.CommentNode commentNode : action.getCommentNodes()) { %>
    <% writeCommentNode(commentNode, out, action.isShowAddComment(), action.getWidgetBlogId(), action.getBlogPostId(), international); %>
<% } %>