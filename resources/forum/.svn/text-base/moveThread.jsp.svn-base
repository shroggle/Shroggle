<%@ page import="com.shroggle.entity.SubForum" %>
<%@ page import="com.shroggle.presentation.forum.MoveThreadService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% MoveThreadService service = (MoveThreadService) request.getAttribute("service"); %>
<div class="windowOneColumn">
    <div class="forumFont">
        <div class="pageTitleForum">Move Selected Thread To:</div>
        <br>
        <table class="moveThreadTable">
            <tr>
                <td colspan="2" class="pageTitleForum">
                    Destination Subforum:
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <select id="subForums" size="1" style="width:100%">
                        <% for (SubForum subForum : service.getSubForums()) { %>
                        <option value="<%= subForum.getSubForumId() %>"
                                <%if (service.getThread().getSubForum().getSubForumId() == subForum.getSubForumId()){%>selected="" <%}%>>
                            <%if (service.getThread().getSubForum().getSubForumId() == subForum.getSubForumId()) {%>
                            Curently:
                            <%}%>
                            <%= subForum.getSubForumName() %>
                        </option>
                        <% } %>
                    </select>
                </td>
            </tr>
        </table>
        <br>
        <input type="button" <%if (!service.isShowOnUserPages){%>value="Save" <%} else{%>value="Save" class="but_w73"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';" <%}%>
               <%if (!service.isShowOnUserPages){%>onclick="submitMoveThread(<%=service.getThread().getThreadId()%>, <%=service.widgetId%>, false);"
               <%} else{%>onclick="window.parent.document.getElementById('forum_main_window').contentWindow.submitMoveThread(<%=service.getThread().getThreadId()%>, <%=service.widgetId%>, true);"<%}%>/>
        <input type="button" <%if (!service.isShowOnUserPages){%>value="Cancel" <%} else{%>value="Cancel"
               class="but_w73"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';" <%}%>
               onclick="window.parent.closeConfigureWidgetDiv();"/>
    </div>
</div>
