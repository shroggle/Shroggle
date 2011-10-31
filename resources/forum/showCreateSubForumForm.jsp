<%@ page import="com.shroggle.presentation.forum.CreateSubForumService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final CreateSubForumService service = (CreateSubForumService) request.getAttribute("service"); %>
<% final boolean isShowOnUserPages = service.isShowOnUserPages; %>
<div id="forum<%=service.isShowOnUserPages ?  "" :service.widgetId%>" class="creationForm forumFont">
    <input type="hidden" id="forumId<%= service.widgetId %>" value="<%= service.forumId %>">
    <input type="hidden" id="showOnUserPages<%= service.widgetId %>" value="<%= service.isShowOnUserPages %>">
    <% if (!isShowOnUserPages) { %>
    <div class="forumBreadCrumbs">
        <a class="navigateForumLink ajaxHistory"
           href="#"
           onclick="$('#forumNavigationSpin'+<%= service.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
           ajaxHistory="#">[Back
            to Forum Manager]</a>
        >> <span class="navigateForumText">[New subforum creation]</span>

        <div id="forumNavigationSpin<%= service.widgetId %>"
             style="display:inline; visibility:hidden;">
            <img alt="Loading..." src="/images/ajax-loader.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0"/>
        </div>
    </div>
    <div id="errors"></div>
    <br>
    <% } else { %>
    <div class="windowOneColumn">
        <div class="pageTitleForum" style="float:left"> Add / Edit Subforum</div>
        <div style="float:right">
            <a class="navigateForumLink"
               href="javascript:returnToForum(<%=service.forumId%> , <%=service.widgetId%>, true)">Back
                to Forum Manager</a>
        </div>
        <br><br>

        <div id="errors" style="text-align:left"></div>
        <% } %>

        <table class="createSubforumForm" id="createSubForumTable">
            <tr>
                <td width="30%">
                    <span class="forumInstruction">Subforum Name:</span>
                </td>
                <td width="70%">
                    <input style="width:100%" type="text" id="subForumName" maxlength="255"
                           onkeydown="processAnyKey(event, 'subForumSubmitButton<%=service.widgetId%>');"
                           value="<%if(service.renameSubForum != null){%><%=service.renameSubForum.getSubForumName()%><%}%>"/><br>
                </td>
            </tr>
            <tr>
                <td style="vertical-align:top;">
                    <span class="forumInstruction">Subforum Description:</span>
                </td>
                <td>
                    <textarea onfocus="trimTextArea(this);"
                              style="width:100%;font-size:10pt;font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;"
                              rows="5" cols="50"
                              id="subForumDescription"><%if (service.renameSubForum != null) {%><%=service.renameSubForum.getSubForumDescription()%><%}%></textarea><br>
                </td>
            </tr>
            <%if (!isShowOnUserPages) {%>
            <tr>
                <td colspan="2" align="right">
                    <div style="text-align:right;" align="right">
                        <div id="forumCreateSubforumSpin<%= service.widgetId %>"
                             style="display:inline; visibility:hidden;">
                            <img alt="Loading..." src="/images/ajax-loader.gif"
                                 style="vertical-align:middle;padding: 0 3px 0 0"/>
                        </div>
                        <input type="button" id="subForumSubmitButton<%=service.widgetId%>"
                               ajaxHistory="#" class="ajaxHistory notUserAjaxDefaultDispatch"
                               onclick="$('#forumCreateSubforumSpin'+<%= service.widgetId %>).makeVisible();<%if(service.renameSubForum == null){%>return createSubForum(this, <%=service.forumId%>, <%=service.widgetId%>, false);<%} else{%>return submitRenameSubForum(this, <%=service.renameSubForum.getSubForumId()%>, <%=service.widgetId%>, false);<%}%>"
                               value="Submit"/>
                        <input type="button" onclick="$('#forumCreateSubforumSpin'+<%= service.widgetId %>).makeVisible();return ajaxDispatcher.execute(this);"
                               ajaxHistory="#" class="ajaxHistory"
                               value="Cancel"
                                />
                    </div>
                    <br>
                </td>
            </tr>
            <%}%>
        </table>

        <% if (isShowOnUserPages) { %>
        <div align="right">
            <input type="button" value="Save" id="subForumSubmitButton<%=service.widgetId%>" class="but_w73"
                   onmouseover="this.className = 'but_w73_Over';"
                   onmouseout="this.className = 'but_w73';"
                   onclick="<%if(service.renameSubForum == null){%>createSubForum(this, <%=service.forumId%>, <%=service.widgetId%>, true);<%} else{%>submitRenameSubForum(this, <%=service.renameSubForum.getSubForumId()%>, <%=service.widgetId%>, true);<%}%>"
                    />
            <input type="button" value="Close" class="but_w73" onmouseover="this.className = 'but_w73_Over';"
                   onmouseout="this.className = 'but_w73';"
                   onclick="window.parent.closeConfigureWidgetDiv();"
                    />
        </div>
    </div>
    <% } %>
    <script>
        if ((navigator.appName.indexOf("Internet Explorer") == -1)) {
            document.getElementById("createSubForumTable").width = "100%";
        }
    </script>
</div>
