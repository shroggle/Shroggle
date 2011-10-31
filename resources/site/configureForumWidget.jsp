<%@ page import="com.shroggle.presentation.site.ConfigureForumService" %>
<%@ page import="com.shroggle.entity.DraftForum" %>
<%@ page import="com.shroggle.entity.AccessGroup" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="configureForum"/>
<%
    final ConfigureForumService service = (ConfigureForumService) request.getAttribute("forumService");
    final DraftForum selectedForum = service.getSelectedForum();
    final International international = ServiceLocator.getInternationStorage().get("configureForum", Locale.US);
%>
<div class="itemSettingsWindowDiv">
    <input type="hidden" id="modified" value="<%= selectedForum.isModified() %>"/>
    <input type="hidden" id="selectedForumId"
           value="<%= service.getSelectedForum().getId() %>"/>
    <input id="emptyForumName" type="hidden" value="<%= international.get("emptyForumName") %>">
    <input id="notExistingForum" type="hidden" value="<%= international.get("notExistingForum") %>">
    <input id="forumNameNotUnique" type="hidden" value="<%= international.get("forumNameNotUnique") %>">

    <h1><international:get name="forumHeader"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="forumService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div id="forumErrors" class="emptyError">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="forumReadOnlyMessage">You have only read-only
        access to this module.</div>

    <dl>
        <dd>
            <label for="forumName"><international:get name="forumNameField"/></label>&nbsp;
            <input class="title" type="text" id="forumName" maxlength="255"
                   value="<%= service.getSelectedForum().getName() %>">
        </dd>
    </dl>
    <br>

    <div class="forum_setting">
        <input type="checkbox" id="allowPolls" onclick="checkAllowPolls();"
               <% if (selectedForum.isAllowPolls()) { %>checked="checked"<% } %>/>
        <label for="allowPolls"><international:get name="allowPolls"/></label>
    </div>
    <div class="forum_setting">
        <input type="checkbox" id="allowSubForums" onclick="checkAllowSubForums();"
               <% if (selectedForum.isAllowSubForums()) { %>checked="checked"<% } %>/>
        <label for="allowSubForums"><international:get name="allowSubForums"/></label>
    </div>

    <hr>
    <table class="forum_tbl">
        <tr>
            <td>
                <b><international:get name="createSubforumRight"/></b><br>

                <div><input value="OWNER" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowSubForums()){%>disabled="" <%}%>
                            name="subForum" id="ownerSubForumRights"
                            <%if ((selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getCreateSubForumRight())) || (selectedForum == null || selectedForum.getCreateSubForumRight() == null)){%>checked="" <%}%>>
                    <label for="ownerSubForumRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowSubForums()){%>disabled="" <%}%>
                            name="subForum" id="guestSubForumRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getCreateSubForumRight())){%>checked="" <%}%>>
                    <label for="guestSubForumRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowSubForums()){%>disabled="" <%}%>
                            name="subForum" id="visitorsSubForumRights"
                            <%if (selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getCreateSubForumRight())){%>checked="" <%}%>>
                    <label for="visitorsSubForumRights"><international:get name="onlyVisitors"/></label></div>

                <br><span class="createThreadRightHeader"><international:get name="createThreadRight"/></span>

                <div><input value="OWNER" type="radio" name="thread"
                            id="ownerThreadRights"
                            <%if (selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getCreateThreadRight())){%>checked="" <%}%>>
                    <label for="ownerThreadRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio" name="thread"
                            id="guestThreadRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getCreateThreadRight())){%>checked="" <%}%>>
                    <label for="guestThreadRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio" name="thread"
                            id="visitorsThreadRights"
                            <%if ((selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getCreateThreadRight())) || (selectedForum == null || selectedForum.getCreateThreadRight() == null)){%>checked="" <%}%>>
                    <label for="visitorsThreadRights"><international:get name="onlyVisitors"/></label></div>
                <div><input value="ALL" type="radio" name="thread"
                            id="allThreadRights"
                            <%if (selectedForum != null && AccessGroup.ALL.equals(selectedForum.getCreateThreadRight())){%>checked="" <%}%>>
                    <label for="allThreadRights"><international:get name="onlyAll"/></label></div>


                <br><b><international:get name="createPostRight"/></b><br>

                <div><input value="OWNER" type="radio" name="post"
                            id="ownerPostRights"
                            <%if (selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getCreatePostRight())){%>checked="" <%}%>>
                    <label for="ownerPostRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio" name="post"
                            id="guestPostRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getCreatePostRight())){%>checked="" <%}%>>
                    <label for="guestPostRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio" name="post"
                            id="visitorsPostRights"
                            <%if ((selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getCreatePostRight())) || (selectedForum == null || selectedForum.getCreatePostRight() == null)){%>checked="" <%}%>>
                    <label for="visitorsPostRights"><international:get name="onlyVisitors"/></label></div>
                <div><input value="ALL" type="radio" name="post" id="allPostRights"
                            <%if (selectedForum != null && AccessGroup.ALL.equals(selectedForum.getCreatePostRight())){%>checked="" <%}%>>
                    <label for="allPostRights"><international:get name="onlyAll"/></label></div>
            </td>
            <td>
                <b><international:get name="manageSubForumsRight"/></b><br>

                <div><input value="OWNER" type="radio" name="manageSubForums"
                            id="ownerManageSubForumsRights"
                            <%if ((selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getManageSubForumsRight())) || (selectedForum == null || selectedForum.getManageSubForumsRight() == null)){%>checked="" <%}%>>
                    <label for="ownerManageSubForumsRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio" name="manageSubForums"
                            id="guestManageSubForumsRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getManageSubForumsRight())){%>checked="" <%}%>>
                    <label for="guestManageSubForumsRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio" name="manageSubForums"
                            id="visitorsManageSubForumsRights"
                            <%if (selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getManageSubForumsRight())){%>checked="" <%}%>>
                    <label for="visitorsManageSubForumsRights"><international:get name="onlyVisitors"/></label></div>
                <div><input value="ALL" type="radio" name="manageSubForums" id="allManageSubForumsRights"
                            <%if (selectedForum != null && AccessGroup.ALL.equals(selectedForum.getManageSubForumsRight())){%>checked="" <%}%>>
                    <label for="allManageSubForumsRights"><international:get name="onlyAll"/></label></div>

                <br><b><international:get name="managePostsRight"/></b><br>

                <div><input value="OWNER" type="radio" name="managePosts"
                            id="ownerManagePostsRights"
                            <%if ((selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getManagePostsRight()))  || (selectedForum == null || selectedForum.getManagePostsRight() == null)){%>checked="" <%}%>>
                    <label for="ownerManagePostsRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio" name="managePosts"
                            id="guestManagePostsRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getManagePostsRight())){%>checked="" <%}%>>
                    <label for="guestManagePostsRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio" name="managePosts"
                            id="visitorsManagePostsRights"
                            <%if (selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getManagePostsRight())){%>checked="" <%}%>>
                    <label for="visitorsManagePostsRights"><international:get name="onlyVisitors"/></label></div>
                <div><input value="ALL" type="radio" name="managePosts" id="allManagePostsRights"
                            <%if (selectedForum != null && AccessGroup.ALL.equals(selectedForum.getManagePostsRight())){%>checked="" <%}%>>
                    <label for="allManagePostsRights"><international:get name="onlyAll"/></label></div>
            </td>
            <td>
                <b><international:get name="createPollRight"/></b><br>

                <div><input value="OWNER" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="poll" id="ownerPollRights"
                            <%if ((selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getCreatePollRight())) || (selectedForum == null || selectedForum.getCreatePollRight() == null)){%>checked="" <%}%>>
                    <label for="ownerPollRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="poll" id="guestPollRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getCreatePollRight())){%>checked="" <%}%>>
                    <label for="guestPollRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="poll" id="visitorsPollRights"
                            <%if (selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getCreatePollRight())){%>checked="" <%}%>>
                    <label for="visitorsPollRights"><international:get name="onlyVisitors"/></label></div>
                <div><input value="ALL" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="poll" id="allPollRights"
                            <%if (selectedForum != null && AccessGroup.ALL.equals(selectedForum.getCreatePollRight())){%>checked="" <%}%>>
                    <label for="allPollRights"><international:get name="onlyAll"/></label></div>
                <br>

                <b><international:get name="voteRight"/></b><br>

                <div><input value="OWNER" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="vote" id="ownerVoteRights"
                            <%if (selectedForum != null && AccessGroup.OWNER.equals(selectedForum.getVoteInPollRight())){%>checked="" <%}%>>
                    <label for="ownerVoteRights"><international:get name="onlyAdmins"/></label></div>
                <div><input value="GUEST" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="vote" id="guestVoteRights"
                            <%if (selectedForum != null && AccessGroup.GUEST.equals(selectedForum.getVoteInPollRight())){%>checked="" <%}%>>
                    <label for="guestVoteRights"><international:get name="onlyGuests"/></label></div>
                <div><input value="VISITORS" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="vote" id="visitorsVoteRights"
                            <%if ((selectedForum != null && AccessGroup.VISITORS.equals(selectedForum.getVoteInPollRight())) || (selectedForum == null || selectedForum.getVoteInPollRight() == null)){%>checked="" <%}%>>
                    <label for="visitorsVoteRights"><international:get name="onlyVisitors"/></label></div>
                <div><input value="ALL" type="radio"
                            <%if (selectedForum != null && !selectedForum.isAllowPolls()){%>disabled="" <%}%>
                            name="vote" id="allVoteRights"
                            <%if (selectedForum != null && AccessGroup.ALL.equals(selectedForum.getVoteInPollRight())){%>checked="" <%}%>>
                    <label for="allVoteRights"><international:get name="onlyAll"/></label></div>
            </td>
        </tr>
    </table>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureForumButtons">
        <input type="button" value="Apply" id="windowApply" onclick="configureForum.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onclick="configureForum.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" id="windowCancel" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>
