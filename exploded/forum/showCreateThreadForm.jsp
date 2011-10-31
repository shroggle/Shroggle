<%@ page import="com.shroggle.presentation.forum.CreateThreadService" %>
<%@ page import="com.shroggle.logic.forum.ForumDispatchType" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<% final CreateThreadService service = (CreateThreadService) request.getAttribute("service"); %>
<% final boolean isPoll = Boolean.valueOf(request.getParameter("isPoll")); %>
<% final int forumId = service.getSubForum().getForum().getId(); %>
<% final String subforumName = HtmlUtil.limitName(service.getSubForum().getSubForumName()); %>

<div id="forum<%=service.isShowOnUserPages ?  "" :service.widgetId%>" class="creationForm forumFont">
    <input type="hidden" id="forumId<%= service.widgetId %>" value="<%= forumId %>">
    <input type="hidden" id="showOnUserPages<%= service.widgetId %>" value="<%= service.isShowOnUserPages %>">
    <%if (!service.isShowOnUserPages) {%>
    <div class="forumBreadCrumbs">
        <%if (service.getSubForum().getForum().isAllowSubForums()) {%><a
            class="navigateForumLink ajaxHistory"
            onclick="$('#forumNavigationSpin'+<%= service.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
            href="#"
            ajaxHistory="#">[Return
        to Forums]</a>
        >><%}%> <a class="navigateForumLink ajaxHistory" href="#"
                   onclick="$('#forumNavigationSpin'+<%= service.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
                   ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_SUBFORUM %>/subForumId=<%= service.getSubForum().getSubForumId() %>/showOnUserPages=<%= service.isShowOnUserPages %>/widgetId=<%= service.widgetId %>"
            >[Return
        to <%= subforumName %> ]</a>
        >> <span class="navigateForumText">[New thread creation]</span>

        <div id="forumNavigationSpin<%= service.widgetId %>"
             style="display:inline; visibility:hidden;">
            <img alt="Loading..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <div id="errors"></div>

    <br>
    <%} else {%>
    <div class="windowOneColumn">
        <div class="pageTitleForum" style="float:left">
            Add / Edit Forum Thread
        </div>
        <div style="float:right">
            <a class="navigateForumLink"
               href="javascript:returnToForum(<%=service.getForumId()%>, <%=service.widgetId%>, true)">Back
                to Forum Manager</a>
        </div>
        <br>

        <div class="divider">&nbsp;</div>

        <div style="float:right">
            <a class="navigateForumLink"
               href="javascript:returnToSubForum(<%=service.getSubForum().getSubForumId()%>, <%=service.widgetId%>, true)">Back
                to <%= subforumName %>
            </a>
        </div>
        <br><br>

        <div id="errors" style="text-align:left"></div>
        <%}%>
        <table class="createThreadForm" id="createThreadTable">
            <tr>
                <td width="25%" style="vertical-align:top;">
                    <span class="forumInstruction">Thread name:</span>
                </td>
                <td width="75%">
                    <input type="text" id="threadName" style="width:100%" maxlength="255"
                           onkeydown="processAnyKey(event, 'threadSubmitButton<%=service.widgetId%>');"
                           value="<%if (service.renamedThread != null){%><%=service.renamedThread.getThreadName()%><%}%>"/>

                    <div class="inform_mark" style="width:100%;">Threads are subject groups or themes in which forum
                        posts are grouped
                    </div>
                </td>
            </tr>
            <tr>
                <td style="vertical-align:top;">
                    <span class="forumInstruction">Thread Description:</span>
                </td>
                <td>
                    <textarea onfocus="trimTextArea(this);" rows="5" cols="100"
                              style="width:100%;font-size:10pt;font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;"
                              id="threadDescription"><%if (service.renamedThread != null) {%><%=service.renamedThread.getThreadDescription()%><%}%></textarea>
                </td>
            </tr>
            <%if (isPoll) {%>
            <tr>
                <td>
                    <span class="forumInstruction">Enter poll question:</span>
                </td>
                <td>
                    <input type="text" id="pollQuestion" style="width:100%" maxlength="255"
                           value="<%if (service.renamedThread != null) {%><%=service.renamedThread.getPollQuestion()%><%}%>"/><br>
                </td>
            </tr>
            <%if (service.renamedThread == null) {%>
            <tr id="answer0">
                <td>
                    <span class="forumInstruction">Enter poll answer:</span>
                </td>
                <td>
                    <input type="text" id="answerInput0" name="answer" class="answers" onkeydown="processKey(event);"
                           maxlength="255"/>
                    <a href="javascript:deleteAnswer(0);" id="deleteAsnwer0">
                        <img border="0" alt="Delete" src="/images/cross-circle.png"/>
                    </a>
                </td>
            </tr>
            <%} else {%>
            <input type="hidden" id="answerCount" value="<%=service.renamedThread.getPollAnswers().size()%>">
            <%for (int i = 0; i < service.renamedThread.getPollAnswers().size(); i++) {%>
            <tr id="answer<%=i%>">
                <td>
                </td>
                <td>
                    <input type="text" id="answerInput<%=i%>" <%if (service.checkIfAnswerIsVoted(i)){%>name="voted"
                           maxlength="255"
                           <%}else{%>name="answer"<%}%> class="answers"
                           value="<%=service.renamedThread.getPollAnswers().get(i)%>" onkeydown="processKey(event);"/>

                    <a href="javascript:deleteAnswer(<%=i%>);" id="deleteAsnwer<%=i%>">
                        <img border="0" alt="Delete" src="/images/cross-circle.png"/>
                    </a><br>
                </td>
            </tr>
            <%}%>
            <%}%>
            <tr id="answers">
                <td>
                <td style="text-align:left">
                    <input type="button" <%if (service.isShowOnUserPages) {%>class="but_w130"
                           onmouseover="this.className = 'but_w130_Over';"
                           onmouseout="this.className = 'but_w130';"<%}%>
                           id="addAnswer" onclick="addAnswer();" value="Add answer"/>
                </td>
            </tr>
            <%}%>
            <%if (service.renamedThread == null) {%>
            <tr>
                <td style="vertical-align:top;">
                    <div class="forumInstruction">Enter the first post for the new thread:</div>
                </td>
                <td>
                    <div id="forumTextEditor" style="width:100%">
                    </div>
                </td>
            </tr>
            <%}%>
            <tr>
                <td colspan="2" align="right">
                    <%if (!service.isShowOnUserPages) {%>
                    <div id="forumCreateThreadSpin<%= service.widgetId %>"
                         style="display:inline; visibility:hidden;">
                        <img alt="Loading..." src="/images/ajax-loader.gif"
                             style="vertical-align:middle;padding: 0 3px 0 0"/>
                    </div>
                    <input type="button" id="threadSubmitButton<%=service.widgetId%>"
                           ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_SUBFORUM %>/subForumId=<%= service.getSubForum().getSubForumId() %>/showOnUserPages=<%= service.isShowOnUserPages %>/widgetId=<%= service.widgetId %>"
                           class="ajaxHistory notUserAjaxDefaultDispatch"
                            <% if (isPoll) { %>
                           onclick="$('#forumCreateThreadSpin'+<%= service.widgetId %>).makeVisible();<%if (service.renamedThread == null) {%>return createNewPoll(this, <%=service.getSubForum().getSubForumId()%>, <%=service.widgetId%>, false);<%}else{%>return submitRenamePoll(this, <%=service.renamedThread.getThreadId()%>, <%=service.widgetId%>, false);<%}%>"
                            <% } else { %>
                           onclick="$('#forumCreateThreadSpin'+<%= service.widgetId %>).makeVisible();<%if (service.renamedThread == null) {%>return createNewThread(this, <%=service.getSubForum().getSubForumId()%>, <%=service.widgetId%>, false);<%}else{%>return submitRenameThread(this, <%=service.renamedThread.getThreadId()%>, <%=service.widgetId%>, false);<%}%>" <%}%>
                           align="middle" value="Save"/>
                    <input type="button"
                           ajaxHistory="#dispatchForum<%= forumId %>=<%= ForumDispatchType.SHOW_SUBFORUM %>/subForumId=<%= service.getSubForum().getSubForumId() %>/showOnUserPages=<%= service.isShowOnUserPages %>/widgetId=<%= service.widgetId %>"
                           class="ajaxHistory"
                           onclick="$('#forumCreateThreadSpin'+<%= service.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
                           align="middle" value="Cancel"/>
                    <%}%>
                </td>
            </tr>
        </table>
        <%if (service.isShowOnUserPages) {%>
        <div align="right">
            <input type="button" id="threadSubmitButton<%=service.widgetId%>"
                   <%if (isPoll) {%>onclick="<%if (service.renamedThread == null) {%>createNewPoll(this, <%=service.getSubForum().getSubForumId()%>, <%=service.widgetId%>, true); <%}else{%>submitRenamePoll(this, <%=service.renamedThread.getThreadId()%>, <%=service.widgetId%>, true);<%}%>"
                   <%}else{%>onclick="<%if (service.renamedThread == null) {%>createNewThread(this, <%=service.getSubForum().getSubForumId()%>, <%=service.widgetId%>, true);<%}else{%>submitRenameThread(this, <%=service.renamedThread.getThreadId()%>, <%=service.widgetId%>, true);<%}%>" <%}%>
                   class="but_w73" onmouseover="this.className = 'but_w73_Over';"
                   onmouseout="this.className = 'but_w73';"
                   align="middle" value="Save"/>
            <input type="button"
                   onclick="window.parent.closeConfigureWidgetDiv();" value="Close"
                   class="but_w73" onmouseover="this.className = 'but_w73_Over';"
                   onmouseout="this.className = 'but_w73';"
                   align="middle"/>
        </div>
        <%}%>

        <%if (service.renamedThread == null) {%>
        <elementWithOnload:element onload="showThreadTextEditor();"/>
        <%}%>
        <script>
            if ((navigator.appName.indexOf("Internet Explorer") == -1)) {
                document.getElementById("createThreadTable").width = "100%";
            }
        </script>
    </div>
</div>