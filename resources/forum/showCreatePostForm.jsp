<%@ page import="com.shroggle.presentation.forum.CreatePostService" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final CreatePostService service = (CreatePostService) request.getAttribute("service"); %>
<% final int forumId = service.getForumThread().getSubForum().getForum().getId(); %>
<% final String threadName = HtmlUtil.limitName(service.getForumThread().getThreadName(), 38); %>
<% final String subforumName = HtmlUtil.limitName(service.getForumThread().getSubForum().getSubForumName(), 38); %>
<div id="postForm" <% if (!service.isShowOnUserPages) { %>style="width:100%" class="postForm forumFont"
     <% } else{ %>class="windowOneColumn forumFont"<% } %>>
    <% if (!service.isShowOnUserPages) { %>
    <div id="error" class="forumInstruction">Enter post text:</div>
    <div id="errors"></div>
    <% } else { %>
    <div class="pageTitleForum" style="float:left">Forum Thread: <%= threadName %>
    </div>
    <div style="float:right">
        <a class="navigateForumLink"
           href="javascript:returnToForum(<%= forumId %>, <%= service.widgetId %>, true)">Back
            to Forum Manager</a>
    </div>
    <br>

    <div class="divider">&nbsp;</div>

    <div class="pageTitleForum" style="float:left"> Add / Edit Forum Post</div>
    <div style="float:right">
        <a class="navigateForumLink"
           href="javascript:returnToSubForum(<%= service.getForumThread().getSubForum().getSubForumId() %>, <%= service.widgetId %>, true)">Back
            to <%= subforumName %>
        </a>
    </div>
    <br>

    <div class="divider">&nbsp;</div>

    <div style="float:right">
        <a class="navigateForumLink"
           href="javascript:returnToThread(<%= service.getForumThread().getThreadId()%>, <%=service.widgetId%>, true)">Back
            to <%= threadName %>
        </a>
    </div>
    <br><br>

    <div class="emptyForumError" id="errors"></div>
    <% } %>
    <div style="display:none;" id="forumPostTextToEdit">
        <% if (service.isDraftEdit()) { %>
        <%= service.getForumPost().getDraftText() %>
        <% } else if (service.isEdit()) { %>
        <%= service.getForumPost().getText() %>
        <% } else if (service.isQuote()) { %>
        <%= "<blockquote>" + service.getForumPost().getText() + "</blockquote><br>" %>
        <% } %>
    </div>

    <div id="forumTextEditor" style="width:90%"></div>
    <div class="buttons">
        <% if (service.isShowOnUserPages) { %>
        <br>

        <div class="inform_mark">Enter your post and press the Save button to publish, or cancel to return to the list
            of Posts.
        </div>
        <br>
        <% } %>
        <div <% if (service.isShowOnUserPages){ %>align="right"<% } %>>
            <input type="button"
                   <%if (!service.isShowOnUserPages){%>onclick="<%if (service.isQuote()) {%>previewNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, false) <%} else if (service.isEdit()){%>previewEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, false) <%} else if (service.isDraftEdit()){%>previewEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, false)<%} else{%>previewNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, false)<%}%>"
                   <%} else{%>onclick="<%if (service.isQuote()) {%>previewNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, true); <%} else if (service.isEdit()){%>previewEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, true) <%} else if (service.isDraftEdit()){%>previewEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, true)<%} else{%>previewNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, true)<%}%>"<%}%>
                   <%if (!service.isShowOnUserPages){%>value="Save to Draft" <%} else{%>class="but_w130"
                   onmouseover="this.className = 'but_w130_Over';" onmouseout="this.className='but_w130';"
                   value="Save to Draft" <%}%>
                   align="middle"/>
            <input type="button"
                   <%if (!service.isShowOnUserPages){%>onclick="<%if (service.isQuote()) {%>createNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, false) <%} else if (service.isEdit()){%>saveEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, false) <%} else if (service.isDraftEdit()){%>submitDraftEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, false)<%} else{%>createNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, false)<%}%>"
                   <%} else{%>onclick="<%if (service.isQuote()) {%>createNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, true); <%} else if (service.isEdit()){%>saveEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, true) <%} else if (service.isDraftEdit()){%>submitDraftEditedPost(<%=service.getForumPost().getForumPostId()%>, <%=service.widgetId%>, true)<%} else{%>createNewPost(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, true)<%}%>"<%}%>
                   <%if (!service.isShowOnUserPages){%>value="Publish" <%} else{%>class="but_w73"
                   onmouseover="this.className = 'but_w73_Over';" onmouseout="this.className='but_w73';"
                   value="Publish"<%}%>
                   align="middle"/>
            <input type="button" id="cancelPostInput"
                   <% if (!service.isShowOnUserPages){%>onclick="showThread(<%=service.getForumThread().getThreadId()%>, <%=service.widgetId%>, false);"
                   <% } else{ %>onclick="window.parent.closeConfigureWidgetDiv();"<%}%>
                   <% if (!service.isShowOnUserPages){%>value="Cancel" <%} else{%>class="but_w73"
                   onmouseover="this.className = 'but_w73_Over';" onmouseout="this.className='but_w73';"
                   value="Cancel"<% } %>
                   align="middle"/>
        </div>
    </div>
    <% if (service.isShowOnUserPages) {%>
    <input id="textEditorHeight" value="300" type="hidden"/>
    <script type="text/javascript">
        var editorPlace = document.getElementById("forumTextEditor");
        createEditor({
            width: "100%",
            height: parseInt($("#textEditorHeight").val()),
            showLastSavedData: false,
            place: editorPlace,
            editorId: "forumPostTextEditor<%if (service.isEdit() || service.isDraftEdit()){%><%=service.getForumPost().getForumPostId()%><%}%>",
            value: document.getElementById("forumPostTextToEdit").innerHTML,
            root: "../"});
    </script>
    <% } %>
</div>
