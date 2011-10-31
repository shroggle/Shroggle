<%@ page import="com.shroggle.presentation.forum.WidgetForumAction" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.forum.ForumDispatchType" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="renderWidgetForum"/>
<% final WidgetForumAction actionBean = (WidgetForumAction) request.getAttribute("actionBean"); %>
<div id="forum<%= actionBean.isShowOnUserPages ?  "" :actionBean.widgetId %>"
     class="forumFont blockToReload<%= actionBean.widgetId %>">
    <input type="hidden" id="forumId<%= actionBean.widgetId %>" value="<%= actionBean.forumId %>">
    <input type="hidden" id="showOnUserPages<%= actionBean.widgetId %>" value="<%= actionBean.isShowOnUserPages %>">

    <% if (actionBean.isShowOnUserPages) { %>
    <div class="divider">&nbsp;</div>
    <div class="pageTitleForum"><international:get name="addSubforum"/></div>
    <br>
    <% } %>
    <% if (actionBean.isCreateSubForumRight() && !actionBean.isShowOnUserPages) { %>
    <div class="forumAddingLinks">
        <a ajaxHistory="#dispatchForum<%= actionBean.forumId %>=<%= ForumDispatchType.SHOW_CREATE_SUBFORUM %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
           onclick="$('#forumCreateSubforumSpin'+<%= actionBean.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);" class="forumLink ajaxHistory" href="#"><international:get
                name="newSubforum"/></a>

        <div id="forumCreateSubforumSpin<%= actionBean.widgetId %>"
             style="display:inline; visibility:hidden;">
            <img alt="Loading..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <br>
    <% } else if (actionBean.isCreateSubForumRight() && actionBean.isShowOnUserPages) { %>
    <input onclick="addNewSubForumLink(<%= actionBean.forumId %>, <%= actionBean.widgetId %> , true);" type="button"
           value="Add Subforum" class="but_w130" onmouseover="this.className='but_w130_Over';"
           onmouseout="this.className='but_w130';">
    <br><br>
    <% } %>
    <table <% if (actionBean.isShowOnUserPages) { %>width="100%" <% } else { %>width="90%"<% } %>
           class="subForumListBorder"
           cellspacing="0" cellpadding="3">
        <tr class="subForumListHeadingRow">
            <td width="44%">
                <span class="subForumListHeadingRowText"><international:get name="subforumName"/></span>
            </td>
            <td width="8%">
                <span class="subForumListHeadingRowText"><international:get name="threads"/></span>
            </td>
            <td width="8%">
                <span class="subForumListHeadingRowText"><international:get name="posts"/></span>
            </td>
            <td width="40%">
                <span class="subForumListHeadingRowText"><international:get name="lastUpdated"/></span>
            </td>
        </tr>
        <% for (SubForum subForum : ServiceLocator.getPersistance().getSubForumsByForumId(actionBean.getForum().getId())) { %>
        <tr class="subForumRow">
            <td class="subForumNameCell">
                <table cellpadding="3" cellspacing="2">
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                    <span class="subForumName"><a class="subForumNameLink ajaxHistory"
                                                                  onclick="$('#forumSubforumSpin'+<%= subForum.getSubForumId() %>).makeVisible();return ajaxDispatcher.execute(this);"
                                            <% if (!actionBean.isShowOnUserPages) { %>
                                                                  href="<%= actionBean.getShowPageVersionUrl() %>&dispatchForum=<%= ForumDispatchType.SHOW_SUBFORUM %>&subForumId=<%= subForum.getSubForumId() %>&showOnUserPages=<%= actionBean.isShowOnUserPages %>&widgetId=<%= actionBean.widgetId %>"
                                                                  ajaxHistory="#dispatchForum<%= actionBean.forumId %>=<%= ForumDispatchType.SHOW_SUBFORUM %>/subForumId=<%= subForum.getSubForumId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
                                            <% } else {%>
                                                                  href="javascript:showSubForum(<%= subForum.getSubForumId() %>, <%= actionBean.widgetId%>, true)"
                                            <%}%>
                                            ><%= HtmlUtil.ignoreHtml(subForum.getSubForumName()) %>
                                    </a></span>

                                        <div id="forumSubforumSpin<%= subForum.getSubForumId() %>"
                                             style="display:inline; visibility:hidden;">
                                            <img alt="Loading..." src="/images/ajax-loader.gif"
                                                 style="vertical-align:middle;padding: 0 3px 0 0"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                    <span class="subForumDescription">
                                            <%= HtmlUtil.ignoreHtml(subForum.getSubForumDescription()) %>
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
            <td class="threadCountCell" style="vertical-align:top">
                <span class="subForumThreadCount"><%= subForum.getForumThreads().size() %></span>
            </td>
            <td class="postCountCell" style="vertical-align:top">
                <% int postCount = 0;
                    for (ForumThread forumThread : subForum.getForumThreads()) {
                        postCount = postCount + actionBean.getForumPosts(forumThread).size();
                    } %>
                <span class="subForumPostCount"><%= postCount %></span>
            </td>
            <% ForumPost lastForumPost = actionBean.getLastPosts().get(subForum); %>
            <% if (lastForumPost != null) { %>
            <td class="subForumLastUpdatedCell">
            <span class="subForumCreated"><small><%= DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.US).format(lastForumPost.getDateCreated()) %>
            </small>
            <br>
            <small>in <%= lastForumPost.getThread().getThreadName() %>
            </small></span>
            </td>
            <% } else {%>
            <td class="subForumLastUpdatedCell">
                <span class="subForumCreated"><small><%= DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, Locale.US).format(subForum.getDateCreated()) %>
                </small></span>
            </td>
            <% } %>
        </tr>
        <tr class="subForumManageRow" <% if (!actionBean.isManageSubFroumsRight()) { %>style="height:6px"<% } %>>
            <td colspan="4">
                <% if (actionBean.isManageSubFroumsRight()) { %>
                <div class="subForumManageRowDiv"><span class="manageSubForumText"><international:get
                        name="manageSubforum"/></span><a class="manageForumLink ajaxHistory"
                                                         onclick="return ajaxDispatcher.execute(this);"
                        <% if (!actionBean.isShowOnUserPages) { %>
                                                         ajaxHistory="#dispatchForum<%= actionBean.forumId %>=<%= ForumDispatchType.SHOW_RENAME_SUBFORUM %>/subForumId=<%= subForum.getSubForumId() %>/showOnUserPages=<%= actionBean.isShowOnUserPages %>/widgetId=<%= actionBean.widgetId %>"
                                                         href="#"
                        <% } else { %>
                                                         href="javascript:showRenameSubForum(<%=subForum.getSubForumId()%>, <%=actionBean.widgetId%>, true)"
                        <%}%>
                                                         id="move<%=subForum.getSubForumId()%>"><international:get
                        name="editSubforumDesc"/></a>&nbsp;|&nbsp;
                    <a class="manageForumLink"
                       href="javascript:deleteSubForum(<%=subForum.getSubForumId()%>, <%=actionBean.widgetId%>, <%= actionBean.isShowOnUserPages %>);"
                       id="move<%=subForum.getSubForumId()%>"><international:get name="deleteSubforum"/></a></div>
                <% } %>
            </td>
        </tr>
        <%}%>
    </table>
    <br>
    <% if (actionBean.isShouldShowRegisterLinks()) { %>
    <div class="forumRegisterLoginBlock">
        <a href="javascript:showForumRegistration(<%= actionBean.getWidget().getWidgetId() %>)"><international:get
                name="register"/></a>
        <a href="javascript:showForumLogin(<%= actionBean.getWidget().getWidgetId() %>)"><international:get
                name="login"/></a>

        <div id="forumRegisterReloadingMessageDiv<%= actionBean.getWidget().getWidgetId() %>"
             style="display:inline;visibility:hidden;">
            <img alt="Loading text editor..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <% } %>
    <% if (actionBean.isShowOnUserPages && actionBean.isCreateSubForumRight()) {%>
    <div class="inform_mark"><international:get name="subforumExplan"/></div>
    <br>

    <div class="bottomButtonsDiv" align="left">
        <input onclick="addNewSubForumLink(<%=actionBean.forumId%>, <%=actionBean.widgetId%> , true)" type="button"
               class="but_w130" onmouseover="this.className='but_w130_Over';"
               onmouseout="this.className='but_w130';" value="Add Subforum">
    </div>
    <% } %>
</div>
