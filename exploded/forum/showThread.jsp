<%@ page import="com.shroggle.presentation.forum.ShowThreadAction" %>
<%@ page import="com.shroggle.entity.ForumPost" %>
<%@ page import="static java.util.Collections.sort" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.shroggle.logic.forum.ForumDispatchType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderWidgetForum"/>
<% final ShowThreadAction actionBean = (ShowThreadAction) request.getAttribute("actionBean"); %>
<% final boolean isShowOnUserPages = actionBean.isShowOnUserPages; %>
<% final int forumId = actionBean.getForumThread().getSubForum().getForum().getId(); %>
<% final int subForumId = actionBean.getForumThread().getSubForum().getSubForumId(); %>
<div id="forum<%= actionBean.isShowOnUserPages ?  "" :actionBean.widgetId %>"
     class="forumFont blockToReload<%= actionBean.widgetId %>">
<input type="hidden" id="forumId<%= actionBean.widgetId %>" value="<%= forumId %>"/>
<input type="hidden" id="showOnUserPages<%= actionBean.widgetId %>" value="<%= actionBean.isShowOnUserPages %>">

<% if (isShowOnUserPages) { %>

<div class="divider">&nbsp;</div>

<div class="pageTitleForum" style="float:left"><international:get
        name="forumThread"/><%=actionBean.getForumThread().getThreadName()%>
</div>
<% if (actionBean.getForumThread().getSubForum().getForum().isAllowSubForums()) { %>
<div style="float:right">
    <a class="navigateForumLink"
       href="javascript:returnToForum(<%= forumId %>, <%= actionBean.widgetId %>, true)">
        <international:get name="returnToForumManager"/>
    </a>
</div>
<% }%>
<br>

<div class="divider">&nbsp;</div>

<div class="pageTitleForum" style="float:left"><international:get name="addForumPost"/></div>
<div style="float:right">
    <a class="navigateForumLink"
       href="javascript:returnToSubForum(<%= subForumId %>, <%=actionBean.widgetId%>, true)"><international:get
            name="backToListThreads"/>
    </a>
</div>
<br><br>
<% } %>
<% if (!isShowOnUserPages) { %>
<div class="forumBreadCrumbs">
    <% if (actionBean.getForumThread().getSubForum().getForum().isAllowSubForums()) { %>
    <a class="navigateForumLink ajaxHistory"
       onclick="$('#forumUpperNavigationSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
       ajaxHistory="#"
       href="#"><international:get
            name="returnToForum"/></a> >><%}%> <a
        onclick="$('#forumUpperNavigationSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
        class="navigateForumLink ajaxHistory"
        ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_SUBFORUM %>/subForumId=<%= subForumId %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
        href="#"
        >[Return to <%=actionBean.getForumThread().getSubForum().getSubForumName()%>]</a> >> <span
        class="navigateForumText">[<%=actionBean.getForumThread().getThreadName()%>]</span>

    <div id="forumUpperNavigationSpin<%= actionBean.widgetId %>"
         style="display:inline; visibility:hidden;">
        <img alt="Loading..." src="/images/ajax-loader.gif"
             style="vertical-align:middle;padding: 0 3px 0 0"/>
    </div>
</div>
<br/>
<%}%>

<%if (!actionBean.getForumThread().isClosed() && actionBean.isCreatePostRight() && !isShowOnUserPages) {%>
<div class="forumAddingLinks">
    <a class="forumLink"
       href="javascript:addNewPostLink(<%=actionBean.threadId%>, <%=actionBean.widgetId%>, false)"
       id="addPostLink"><international:get name="addPost"/></a>
</div>

<br/>

<div id="addPost">
</div>
<%} else if (!actionBean.getForumThread().isClosed() && actionBean.isCreatePostRight() && isShowOnUserPages) {%>
<input id="addPostLink" type="button"
       onclick="addNewPostLink(<%=actionBean.threadId%>, <%=actionBean.widgetId%>, true);"
       value="Add post" class="but_w100" onmouseover="this.className ='but_w100_Over';"
       onmouseout="this.className='but_w100';"/>
<br><br>

<div id="addPost">
</div>
<%}%>
<%if (actionBean.isPoll()) {%>
<table width="40%" cellpadding="3" cellspacing="1" align="center">
    <tr>
        <td align="center" colspan="3">
            <%=actionBean.getForumThread().getPollQuestion()%>
        </td>
    </tr>
    <tr bgcolor="#dddddd">
        <%if (!actionBean.alreadyVoted) {%>
        <td>
        </td>
        <%}%>
        <td>
            <international:get name="answers"/>
        </td>
        <td>
            <international:get name="votes"/>
        </td>
    </tr>
    <%for (int i = 0; i < actionBean.getForumThread().getPollAnswers().size(); i++) {%>
    <tr bgcolor="#ffffff">
        <%if (!actionBean.alreadyVoted) {%>
        <td>
            <input type="radio" name="vote" id="<%=i%>">
        </td>
        <%}%>
        <td align="center">
            <%=actionBean.getForumThread().getPollAnswers().get(i)%>
        </td>
        <td align="center">
            <%=actionBean.getPersistance().getThreadVotesCount(actionBean.threadId, i)%>
        </td>
    </tr>
    <%}%>
    <tr>
        <%if (!actionBean.alreadyVoted && actionBean.isVoteInPollRight() && !actionBean.getForumThread().isClosed()) {%>
        <td colspan="2" align="center">
            <%if (!actionBean.isShowOnUserPages) {%>
            <input type="button" value="Vote"
                   onclick="forumPollVote(<%=actionBean.threadId%>, <%=actionBean.widgetId%>, false);"><%} else {%>
            <input type="button" value="Vote" class="but_w100" onmouseover="this.className ='but_w100_Over';"
                   onmouseout="this.className='but_w100';"
                   onclick="forumPollVote(<%=actionBean.threadId%>, <%=actionBean.widgetId%>, true);">
            <%}%>
        </td>
        <%}%>
        <td <%if (actionBean.alreadyVoted || !actionBean.isVoteInPollRight() || actionBean.getForumThread().isClosed()) {%>
                colspan="3"<%}%> align="center">
            Total votes: <%=actionBean.getForumThread().getPollVotes().size()%>
        </td>
    </tr>
</table>
<%}%>


<table <%if (actionBean.isShowOnUserPages) {%>width="100%" <%} else {%>width="90%"<%}%> class="postList" cellspacing="0"
       cellpadding="3">
    <tr class="postListHeading">
        <td>
            <span class="postListHeadingRowText"><international:get name="post"/></span>
        </td>
        <td>
            <span class="postListHeadingRowText"><international:get name="author"/></span>
        </td>
        <td>
            <span class="postListHeadingRowText"><international:get name="posted"/></span>
        </td>
    </tr>
    <%
        int i = 1;
        sort(actionBean.getForumThread().getForumPosts());
        for (ForumPost forumPost : actionBean.getForumThread().getForumPosts()) {
            boolean postIsDraft = forumPost.getDraftText() != null;
            boolean postAuthorIsLoginedUser = actionBean.getLoginedVisitorId() != null && forumPost.getAuthor() != null
                    && actionBean.getLoginedVisitorId() == forumPost.getAuthor().getUserId();
            boolean postAuthorIsAnonymousPageVisiorThatIsCurrentlyViewingPage = forumPost.getAuthor() == null &&
                    actionBean.getActivePageVisitorId() != null && forumPost.getPageVisitor() != null &&
                    actionBean.getActivePageVisitorId().equals(forumPost.getPageVisitor().getPageVisitorId());
            if (!postIsDraft || postAuthorIsLoginedUser || forumPost.getText() != null ||
                    postAuthorIsAnonymousPageVisiorThatIsCurrentlyViewingPage) {
    %>
    <tr <% if (i % 2 == 0) { %>class="evenPostBGColor" <% } else { %>class="notEvenPostBGColor"<% } %>>
        <td width="55%">
            <div style="width:100%" id="editPost<%=forumPost.getForumPostId()%>">
                <%
                    if (postIsDraft &&
                            (postAuthorIsLoginedUser || (postAuthorIsAnonymousPageVisiorThatIsCurrentlyViewingPage))) {
                %>
                <%= forumPost.getDraftText() %>
                <% } else { %>
                <%= forumPost.getText() %>
                <% } %>
            </div>
        </td>
        <td valign="top" width="20%" class="postAuthorCell">
            <%if (forumPost.getAuthor() != null) {%>
            <span class="postAuthor"><%=forumPost.getAuthor().getEmail()%></span>
            <%} else {%>
            <span class="postAuthor">Anonymous</span>
            <%}%>
        </td>
        <td valign="top" width="25%" class="postAuthorCell">
            <span class="postCreated"><small>
                Posted: <%=DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.US).format(forumPost.getDateCreated())%>
            </small>
            <small># <%=i%>
            </small></span>
        </td>
    </tr>
    <tr class="postManageRow">
        <td colspan="3">
            <%
                if ((!actionBean.getForumThread().isClosed() || postIsDraft)
                        && (postAuthorIsLoginedUser || postAuthorIsAnonymousPageVisiorThatIsCurrentlyViewingPage)) {
            %>
            <%
                if (actionBean.isManagePostsRight()
                        || (actionBean.getLoginedVisitorId() != null && forumPost.getAuthor().getUserId() == actionBean.getLoginedVisitorId())
                        || (forumPost.getAuthor() == null && actionBean.getActivePageVisitorId().equals(forumPost.getPageVisitor().getPageVisitorId()))) {
            %>
            <span class="manageSubForumText"><international:get
                    name="managePosts"/></span><%if (forumPost.getDraftText() == null) {%>
            <a class="managePostLink"
               <%if (!actionBean.isShowOnUserPages){%>href="javascript:quotePost(<%=forumPost.getForumPostId()%>,<%=actionBean.threadId%>, <%=actionBean.widgetId%>, false)"
               <%} else{%>href="javascript:quotePost(<%=forumPost.getForumPostId()%>,<%=actionBean.threadId%>, <%=actionBean.widgetId%>, true)"<%}%>>Reply</a>&nbsp;|&nbsp;
            <%} else {%>
            <a class="managePostLink"
               <%if (!actionBean.isShowOnUserPages){%>href="javascript:submitDraftPost(<%=forumPost.getForumPostId()%>, <%=actionBean.widgetId%>, false)"
               <%} else{%>href="javascript:submitDraftPost(<%=forumPost.getForumPostId()%>, <%=actionBean.widgetId%>, true)"<%}%>><international:get
                    name="publishDraft"/></a>&nbsp;|&nbsp;
            <%}%>
            <%if (forumPost.getDraftText() == null) {%>
            <a class="managePostLink"
               <%if (!actionBean.isShowOnUserPages){%>href="javascript:editPost(<%=actionBean.threadId%>, <%=forumPost.getForumPostId()%>, false, <%=actionBean.widgetId%>, false)"
               <%} else{%>href="javascript:editPost(<%=actionBean.threadId%>, <%=forumPost.getForumPostId()%>, false, <%=actionBean.widgetId%>, true)"<%}%>
                    ><international:get name="editPost"/></a>&nbsp;|&nbsp;
            <%} else {%>
            <a class="managePostLink"
               <%if (!actionBean.isShowOnUserPages){%>href="javascript:editPost(<%=actionBean.threadId%>, <%=forumPost.getForumPostId()%>, true, <%=actionBean.widgetId%>, false)"
               <%} else{%>href="javascript:editPost(<%=actionBean.threadId%>, <%=forumPost.getForumPostId()%>, true, <%=actionBean.widgetId%>, true)"<%}%>><international:get
                    name="editDraft"/></a>&nbsp;|&nbsp;
            <%}%>
            <%if (forumPost.getDraftText() == null) {%>
            <a class="managePostLink"
               <%if (!actionBean.isShowOnUserPages){%>href="javascript:deletePost(<%=actionBean.threadId%>, <%=forumPost.getForumPostId()%>, <%=actionBean.widgetId%>, false)"
               <%} else{%>href="javascript:deletePost(<%=actionBean.threadId%>, <%=forumPost.getForumPostId()%>, <%=actionBean.widgetId%>, true)"<%}%>
                    ><international:get name="deletePost"/>
            </a>
            <%} else {%>
            <a class="managePostLink"
               <%if (!actionBean.isShowOnUserPages){%>href="javascript:discardDraftPost(<%=forumPost.getForumPostId()%>, <%=actionBean.widgetId%>, false)"
               <%} else{%>href="javascript:discardDraftPost(<%=forumPost.getForumPostId()%>, <%=actionBean.widgetId%>, true)"<%}%>><international:get
                    name="discardDraft"/></a>
            <%}%>
            <%}%>
            <%}%>
        </td>
    </tr>
    <%
                i++;

            }
        }
    %>
</table>

<%if (!actionBean.isShowOnUserPages) {%>
<br/>

<div class="forumBreadCrumbs">
    <%if (actionBean.getForumThread().getSubForum().getForum().isAllowSubForums()) { %>
    <a class="navigateForumLink ajaxHistory"
       onclick="$('#forumLowerNavigationSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
       ajaxHistory="#"
       href="#"><international:get
            name="returnToForum"/></a> >><%}%> <a
        class="navigateForumLink ajaxHistory"
        onclick="$('#forumLowerNavigationSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
        ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_SUBFORUM %>/subForumId=<%= subForumId %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
        href="#"
        >[Return to <%=actionBean.getForumThread().getSubForum().getSubForumName()%>]</a> >> <span
        class="navigateForumText">[<%=actionBean.getForumThread().getThreadName()%>]</span>

    <div id="forumLowerNavigationSpin<%= actionBean.widgetId %>"
         style="display:inline; visibility:hidden;">
        <img alt="Loading..." src="/images/ajax-loader.gif"
             style="vertical-align:middle;padding: 0 3px 0 0"/>
    </div>
</div>
<%} else {%>

<%if (!actionBean.getForumThread().isClosed() && actionBean.isCreatePostRight()) {%>
<br>

<div class="inform_mark"><international:get name="postExplan"/></div>
<br>

<div align="left">
    <input id="addPostLink" align="right" type="button"
           onclick="addNewPostLink(<%=actionBean.threadId%>, <%=actionBean.widgetId%>, true);"
           value="Add post" class="but_w100" onmouseover="this.className ='but_w100_Over';"
           onmouseout="this.className='but_w100';"/>
    <%}%>
</div>

<%}%>
<% if (actionBean.isShouldShowRegisterLinks()) { %>
<div class="forumRegisterLoginBlock">
    <a href="javascript:showForumRegistration(<%= actionBean.widgetId %>)"><international:get
            name="register"/></a>
    <a href="javascript:showForumLogin(<%= actionBean.widgetId %>)"><international:get
            name="login"/></a>

    <div id="forumRegisterReloadingMessageDiv<%= actionBean.widgetId %>" style="display:inline;visibility:hidden;">
        <img alt="Loading text editor..." src="/images/ajax-loader.gif"
             style="vertical-align:middle;padding: 0 3px 0 0"/>
    </div>
</div>
<% } %>
</div>