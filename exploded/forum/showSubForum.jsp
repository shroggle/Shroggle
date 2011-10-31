<%@ page import="com.shroggle.entity.ForumThread" %>
<%@ page import="com.shroggle.presentation.forum.ShowSubForumActionBean" %>
<%@ page import="com.shroggle.entity.ForumPost" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.logic.forum.ForumDispatchType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderWidgetForum"/>
<% final ShowSubForumActionBean actionBean = (ShowSubForumActionBean) request.getAttribute("actionBean"); %>
<% final boolean isShowOnUserPages = actionBean.isShowOnUserPages; %>
<% final int forumId = actionBean.getSubForum().getForum().getId(); %>
<div id="forum<%= actionBean.isShowOnUserPages ?  "" :actionBean.widgetId %>" class="forumFont blockToReload<%= actionBean.widgetId %>">
    <input type="hidden" id="forumId<%= actionBean.widgetId %>" value="<%= forumId %>"/>
    <input type="hidden" id="showOnUserPages<%= actionBean.widgetId %>" value="<%= actionBean.isShowOnUserPages %>">

    <% if (actionBean.isShowOnUserPages) { %>
    <div class="divider">&nbsp;</div>
    <div class="pageTitleForum" style="float:left"><international:get name="addForumThread"/></div>
    <% } %>

    <% if (!isShowOnUserPages) { %>
    <div class="forumBreadCrumbs">
        <%if (actionBean.getSubForum().getForum().isAllowSubForums()) { %>
        <a class="navigateForumLink ajaxHistory" href="#" onclick="$('#forumUpperNaviagtionSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);" ajaxHistory="#"><international:get name="returnToForum"/></a>
        >> <% } %><span class="navigateForumText">[<%= actionBean.getSubForum().getSubForumName() %>]</span>

        <div id="forumUpperNaviagtionSpin<%= actionBean.widgetId %>" style="display:inline; visibility:hidden;">
            <img alt="Loading..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <br>
    <% } else { %>
    <div style="float:right">
        <a class="navigateForumLink"
           href="javascript:returnToForum(<%= forumId %>, <%= actionBean.widgetId %>, true)"><international:get name="returnToForumManager"/></a>
    </div>
    <br><br>
    <% } %>

    <% if ((actionBean.isCreateThreadRight() || actionBean.isCreatePollRight()) && !isShowOnUserPages) { %>
    <div class="forumAddingLinks">
        <% if (actionBean.isCreateThreadRight()) { %>
        <a class="forumLink ajaxHistory" href="#" onclick="$('#forumCreateThreadLinksSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
           ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_CREATE_THREAD %>/subForumId=<%= actionBean.getSubForum().getSubForumId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
           ><international:get name="createNewThread"/></a>
        <% } %>
        <% if (actionBean.isCreatePollRight() && actionBean.getSubForum().getForum().isAllowPolls()) { %>
        &nbsp;|&nbsp;<a class="forumLink ajaxHistory" href="#" onclick="$('#forumCreateThreadLinksSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
           ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_CREATE_POLL %>/subForumId=<%= actionBean.getSubForum().getSubForumId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
           ><international:get name="createNewPoll"/></a>
        <% } %>
        <div id="forumCreateThreadLinksSpin<%= actionBean.widgetId %>"
             style="display:inline; visibility:hidden;">
            <img alt="Loading..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <br>
    <% } else if ((actionBean.isCreateThreadRight() || actionBean.isCreatePollRight()) && isShowOnUserPages) { %>
    <% if (actionBean.isCreateThreadRight()) { %>
    <input onclick="addNewThreadLink(<%=actionBean.getSubForum().getSubForumId()%>, <%= actionBean.widgetId %>, true);"
           type="button"
           class="but_w100" onmouseover="this.className='but_w100_Over';"
           onmouseout="this.className='but_w100';" value="Add Thread">
    <% } %>
    <% if (actionBean.isCreatePollRight() && actionBean.getSubForum().getForum().isAllowPolls()) { %>
    <input onclick="addNewPollLink(<%=actionBean.getSubForum().getSubForumId()%>, <%=actionBean.widgetId%>, true);"
           type="button"
           class="but_w100" onmouseover="this.className='but_w100_Over';"
           onmouseout="this.className='but_w100';" value="Add Poll">
    <% } %>
    <br><br>
    <% } %>

    <table cellpadding="3" cellspacing="0" class="threadList" <% if (actionBean.isShowOnUserPages) { %>width="100%"<% } else { %>width="90%"<% } %>>
        <tr class="threadListHeading">
            <td width="30%">
                <span class="threadListHeadingRowText"><international:get name="thread"/></span>
            </td>
            <td width="30%">
                <span class="threadListHeadingRowText"><international:get name="author"/></span>
            </td>
            <td width="10%">
                <span class="threadListHeadingRowText"><international:get name="posts"/></span>
            </td>
            <td width="30%">
                <span class="threadListHeadingRowText"><international:get name="lastPosted"/></span>
            </td>
        </tr>
        <% for (ForumThread forumThread : actionBean.getForumThreads()) { %>
        <% final ForumPost lastForumThreadPost = actionBean.getLastForumThreadsPost().get(forumThread); %>
        <tr class="threadRow" >
            <td>
                <table>
                    <tr>
                        <td>
                            <span class="threadName"><international:get name="threadName"/></span><a
                                class="threadNameLink ajaxHistory" onclick="$('#forumShowThreadSpin'+<%= forumThread.getThreadId() %>).makeVisible(); return ajaxDispatcher.execute(this);"
                                <% if (!isShowOnUserPages) { %>
                                ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_THREAD %>/threadId=<%= forumThread.getThreadId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
                                href="<%= actionBean.getShowPageVersionUrl() %>&dispatchForum=<%= ForumDispatchType.SHOW_THREAD %>&threadId=<%= forumThread.getThreadId() %>&showOnUserPages=<%= actionBean.isShowOnUserPages %>&widgetId=<%= actionBean.widgetId %>"
                                <% } else { %>
                                href="javascript:showThread(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, true)"
                                <%}%>
                                ><%= HtmlUtil.ignoreHtml(forumThread.getThreadName()) %>
                        </a>

                            <div id="forumShowThreadSpin<%= forumThread.getThreadId() %>"
                                 style="display:inline; visibility:hidden;">
                                <img alt="Loading..." src="/images/ajax-loader.gif"
                                     style="vertical-align:middle;padding: 0 3px 0 0"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        <span class="threadDescription">
                          <international:get name="threadDesc"/><%= HtmlUtil.ignoreHtml(forumThread.getThreadDescription()) %>
                        </span>
                        </td>
                    </tr>
                </table>
                <small>
                </small>
            </td>
            <td style="vertical-align:top">
            <span class="threadAuthor"><% if (forumThread.getAuthor() != null) { %><%= forumThread.getAuthor().getEmail() %><% } else { %>Anonymous<% } %></span>
            </td>
            <td style="vertical-align:top">
                <span class="threadPostCount"><%= actionBean.getForumPosts(forumThread).size() %></span>
            </td>
            <td>
            <span class="threadCreated"><% if (lastForumThreadPost != null) { %>
            <small><%= DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.US).format(lastForumThreadPost.getDateCreated()) %>
            </small>
            <br>
            <% if (lastForumThreadPost.getAuthor() != null) { %>
            by <%= lastForumThreadPost.getAuthor().getEmail() %>
            <% } else { %>
            by Anonymous
            <% } %>
            <% } else { %>
            There are no posts
            <% } %></span>
            </td>
        </tr>
        <tr class="threadManageRow">
            <td colspan="4">
                <% if (actionBean.isManageSubFroumsRight() || (actionBean.getLoginedVisitorId() != null && forumThread.getAuthor().getUserId() == actionBean.getLoginedVisitorId())) { %>
                <div class="subForumManageRowDiv">
                    <% if (forumThread.isClosed()) { %>
                    <img src="/forum/threadClosed.gif" alt="" align="right"/>
                    <% } %>
                    <span class="manageSubForumText"><international:get name="manageThread"/></span>
                    <% if (forumThread.isClosed()) {%>
                    <a class="manageForumLink" <%if (!isShowOnUserPages){%>href="javascript:openThread(<%= forumThread.getSubForum().getSubForumId() %>,
                 <%= forumThread.getThreadId() %>, <%=actionBean.widgetId%>, false)" <% } else { %>href="javascript:openThread(<%=forumThread.getSubForum().getSubForumId() %>,
                 <%= forumThread.getThreadId() %>, <%=actionBean.widgetId%>, true)"<% } %>><international:get name="openThread"/></a>&nbsp;|&nbsp;
                    <% } else { %>
                    <a class="manageForumLink" <%if (!isShowOnUserPages){%>href="javascript:closeThread(<%= forumThread.getSubForum().getSubForumId() %>,
                 <%= forumThread.getThreadId() %>, <%=actionBean.widgetId%>, false)" <% } else { %>href="javascript:closeThread(<%= forumThread.getSubForum().getSubForumId() %>,
                 <%= forumThread.getThreadId() %>, <%=actionBean.widgetId%>, true)"<% } %>><international:get name="closeThread"/></a>&nbsp;|&nbsp;
                    <%  } %>
                    <% if (actionBean.getSubForum().getForum().isAllowSubForums()) { %>
                    <a class="manageForumLink"
                       <%if (!isShowOnUserPages){%>href="javascript:showMoveThread(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, false)"
                       <%} else{%>href="javascript:showMoveThread(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, true)"<%}%>
                       id="move<%=forumThread.getThreadId()%>"
                            ><international:get name="moveThread"/></a>&nbsp;|&nbsp;<% } %>
                    <a class="manageForumLink ajaxHistory" onclick="return ajaxDispatcher.execute(this);"
                            <%if (forumThread.getPollQuestion() == null) {%>
                                <%if (!isShowOnUserPages){%>
                                ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_RENAME_THREAD %>/threadId=<%= forumThread.getThreadId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
                                href="#"
                                <%} else{%>
                                href="javascript:showRenameThread(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, true)"
                                <%}%>
                            <%} else {%>
                                <%if (!isShowOnUserPages){%>
                                ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_RENAME_POLL %>/threadId=<%= forumThread.getThreadId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
                                href="#"
                                <%} else{%>
                                href="javascript:showRenamePoll(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, true)"
                                <%}%>
                            <%}%>
                            id="rename<%=forumThread.getThreadId()%>"
                            ><international:get name="renameThread"/></a>&nbsp;|&nbsp;
                    <a class="manageForumLink"
                       <%if (!isShowOnUserPages){%>href="javascript:deleteThread(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, false)"
                       <%} else{%>href="javascript:deleteThread(<%=forumThread.getThreadId()%>, <%=actionBean.widgetId%>, true)"<%}%>><international:get name="deleteThread"/></a><br>
                </div>
                <%}%>
            </td>
        </tr>
        <%}%>
    </table>

    <%if (isShowOnUserPages) {%>
    <br>

    <div class="inform_mark"><international:get name="threadExplan"/></div>
    <br>

    <div class="bottomButtonsDiv" align="left">
        <%if (actionBean.isCreateThreadRight()) {%>
        <input onclick="addNewThreadLink(<%=actionBean.getSubForum().getSubForumId()%>, <%=actionBean.widgetId%>, true)"
               type="button"
               class="but_w100" onmouseover="this.className='but_w100_Over';"
               onmouseout="this.className='but_w100';" value="Add Thread">
        <%}%>
        <%if (actionBean.isCreatePollRight() && actionBean.getSubForum().getForum().isAllowPolls()) {%>
        <input onclick="addNewPollLink(<%=actionBean.getSubForum().getSubForumId()%>, <%=actionBean.widgetId%>, true)"
               type="button"
               class="but_w100" onmouseover="this.className='but_w100_Over';"
               onmouseout="this.className='but_w100';" value="Add Poll">
        <%}%>
    </div>
    <% } else { %>
    <br>

    <div class="forumBreadCrumbs">
        <% if (actionBean.getSubForum().getForum().isAllowSubForums()) { %>
        <a class="navigateForumLink ajaxHistory" onclick="$('#forumLowerNaviagtionSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);" ajaxHistory="#" href="#">[Return
        to Forums]</a>
        >> <% } %><span class="navigateForumText">[<%=actionBean.getSubForum().getSubForumName()%>]</span>
        <div id="forumLowerNaviagtionSpin<%= actionBean.widgetId %>" style="display:inline; visibility:hidden;">
            <img alt="Loading..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <% } %>
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