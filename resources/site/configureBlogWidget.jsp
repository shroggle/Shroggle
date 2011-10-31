<%@ page import="com.shroggle.entity.AccessGroup" %>
<%@ page import="com.shroggle.entity.DraftBlog" %>
<%@ page import="com.shroggle.presentation.blog.ConfigureBlogService" %>
<%@ page import="com.shroggle.entity.DisplayPosts" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="configureBlog"/>
<%
    final ConfigureBlogService service = (ConfigureBlogService) request.getAttribute("blogService");
    final DraftBlog selectedBlog = service.getSelectedBlog();
%>

<div class="itemSettingsWindowDiv">
<input type="hidden" id="selectedBlogId" value="<%= selectedBlog.getId() %>"/>
<input type="hidden" id="modified" value="<%= selectedBlog.isModified() %>"/>

<h1><international:get name="blogHeader"/></h1>
<% if (service.getWidgetTitle() != null) { %>
<widget:title customServiceName="blogService"/>
<% } %>
<div class="windowTopLine">&nbsp;</div>

<div id="blogErrors" class="emptyError">&nbsp;</div>

<div class="readOnlyWarning" style="display:none;" id="blogReadOnlyMessage">You have only read-only
        access to this module.</div>

<dl>
    <dd>
        <label for="blogName"><international:get name="blogName"/></label>&nbsp;
        <input class="title" type="text" id="blogName" maxlength="255"
               value="<%= service.getSelectedBlog().getName() %>">
    </dd>
</dl>

<br/>
<table class="blog_tbl">
    <tr>
        <td>
            <b><international:get name="whoCanPostInTheBlog"/></b>

            <div><input type="radio" value="OWNER" id="OWNER"
                        name="postRights" <%= selectedBlog.getId() == 0 || AccessGroup.OWNER.equals(selectedBlog.getAddPostRight()) ? "checked" : "" %>>
                <label for="OWNER"><international:get name="onlyAdministrator"/></label></div>
            <div><input type="radio" value="VISITORS" id="VISITORS"
                        name="postRights" <%= selectedBlog.getId() > 0 && AccessGroup.VISITORS.equals(selectedBlog.getAddPostRight()) ? "checked" : "" %>>
                <label for="VISITORS"><international:get name="onlyRegisteredVisitors"/></label></div>
            <div><input type="radio" value="GUEST" id="GUEST"
                        name="postRights" <%= selectedBlog.getId() > 0 && AccessGroup.GUEST.equals(selectedBlog.getAddPostRight()) ? "checked" : "" %>>
                <label for="GUEST"><international:get name="guest"/></label></div>
            <div><input type="radio" value="ALL" id="ALL"
                        name="postRights" <%= selectedBlog.getId() > 0 && AccessGroup.ALL.equals(selectedBlog.getAddPostRight()) ? "checked" : "" %>>
                <label for="ALL"><international:get name="everyone"/></label></div>
        </td>
        <td>
            <span style="font-weight:bold;"><international:get name="commentsInTheBlog"/></span>

            <div>
                <input type="checkbox" onclick="clickCommentRightsAllow();"
                       id="commentRightsAllow" <%= selectedBlog.getId() == 0 || selectedBlog.getAddCommentOnPostRight() != null ? "checked" : "" %>>
                <label for="commentRightsAllow"><international:get name="commentsRightsAllow"/></label>
            </div>
            <div><input type="radio" value="OWNER" id="OWNER2"
                        name="commentRights" <%= selectedBlog.getId() > 0 && AccessGroup.OWNER.equals(selectedBlog.getAddCommentOnPostRight()) ? "checked" : "" %>>
                <label for="OWNER2"><international:get name="onlyForAdministrator"/></label></div>
            <div><input type="radio" value="VISITORS" id="VISITORS2"
                        name="commentRights" <%= selectedBlog.getId() == 0 || selectedBlog.getAddCommentOnPostRight() == null || AccessGroup.VISITORS.equals(selectedBlog.getAddCommentOnPostRight()) ? "checked" : "" %>>
                <label for="VISITORS2"><international:get name="onlyForRegisteredVisitors"/></label></div>
            <div><input type="radio" value="GUEST" id="GUEST2"
                        name="commentRights" <%= selectedBlog.getId() > 0 && AccessGroup.GUEST.equals(selectedBlog.getAddCommentOnPostRight()) ? "checked" : "" %>>
                <label for="GUEST2"><international:get name="guest"/></label></div>
            <div><input type="radio" value="ALL" id="ALL2"
                        name="commentRights" <%= selectedBlog.getId() > 0 && AccessGroup.ALL.equals(selectedBlog.getAddCommentOnPostRight()) ? "checked" : "" %>>
                <label for="ALL2"><international:get name="forEveryone"/></label></div>
        </td>
        <td>
            <span style="font-weight:bold;"><international:get name="commentsOnComments"/></span>

            <div><input type="checkbox" onclick="clickCommentOnCommentRightsAllow();"
                        id="commentToCommentRightsAllow" <%= selectedBlog.getId() > 0 && selectedBlog.getAddCommentOnCommentRight() != null ? "checked" : "" %>>
                <label for="commentToCommentRightsAllow"><international:get
                        name="commentsOnCommentsRightsAllow"/></label></div>
            <div><input type="radio" value="OWNER" id="OWNER3"
                        name="commentOnCommentRights" <%= selectedBlog.getId() > 0 && AccessGroup.OWNER.equals(selectedBlog.getAddCommentOnCommentRight()) ? "checked" : "" %>>
                <label for="OWNER3"><international:get name="onlyForAdministrator"/></label></div>
            <div><input type="radio" value="VISITORS" id="VISITORS3"
                        name="commentOnCommentRights" <%= selectedBlog.getId() == 0 || selectedBlog.getAddCommentOnCommentRight() == null || AccessGroup.VISITORS.equals(selectedBlog.getAddCommentOnCommentRight()) ? "checked" : "" %>>
                <label for="VISITORS3"><international:get name="onlyForRegisteredVisitors"/></label></div>
            <div><input type="radio" value="GUEST" id="GUEST3"
                        name="commentOnCommentRights" <%= selectedBlog.getId() > 0 && AccessGroup.GUEST.equals(selectedBlog.getAddCommentOnCommentRight()) ? "checked" : "" %>>
                <label for="GUEST3"><international:get name="guest"/> </label></div>
            <div><input type="radio" value="ALL" id="ALL3"
                        name="commentOnCommentRights" <%= selectedBlog.getId() > 0 && AccessGroup.ALL.equals(selectedBlog.getAddCommentOnCommentRight()) ? "checked" : "" %>>
                <label for="ALL3"><international:get name="forEveryone"/> </label></div>
        </td>
    </tr>
    <tr>
        <td style="padding: 0 20px 0 0;">
            <b><international:get name="whoCanManageBlogPosts"/></b>

            <div><input type="radio" value="OWNER" id="OWNER4"
                        name="editBlogPostRight" <%= selectedBlog.getId() == 0 || AccessGroup.OWNER.equals(selectedBlog.getEditBlogPostRight()) ? "checked" : "" %>>
                <label for="OWNER4"><international:get name="onlyAdministrator"/></label></div>
            <div><input type="radio" value="VISITORS" id="VISITORS4"
                        name="editBlogPostRight" <%= selectedBlog.getId() > 0 && AccessGroup.VISITORS.equals(selectedBlog.getEditBlogPostRight()) ? "checked" : "" %>>
                <label for="VISITORS4"><international:get name="onlyRegisteredVisitors"/></label></div>
            <div><input type="radio" value="GUEST" id="GUEST4"
                        name="editBlogPostRight" <%= selectedBlog.getId() > 0 && AccessGroup.GUEST.equals(selectedBlog.getEditBlogPostRight()) ? "checked" : "" %>>
                <label for="GUEST4"><international:get name="guest"/></label></div>
            <div><input type="radio" value="ALL" id="ALL4"
                        name="editBlogPostRight" <%= selectedBlog.getId() > 0 && AccessGroup.ALL.equals(selectedBlog.getEditBlogPostRight()) ? "checked" : "" %>>
                <label for="ALL4"><international:get name="everyone"/></label></div>
        </td>
        <td style="padding: 0 20px 0 0;">
            <b><international:get name="whoCanManageBlogComments"/></b>

            <div><input type="radio" value="OWNER" id="OWNER5"
                        name="editCommentRight" <%= selectedBlog.getId() == 0 || AccessGroup.OWNER.equals(selectedBlog.getEditCommentRight()) ? "checked" : "" %>>
                <label for="OWNER5"><international:get name="onlyAdministrator"/></label></div>
            <div><input type="radio" value="VISITORS" id="VISITORS5"
                        name="editCommentRight" <%= selectedBlog.getId() > 0 && AccessGroup.VISITORS.equals(selectedBlog.getEditCommentRight()) ? "checked" : "" %>>
                <label for="VISITORS5"><international:get name="onlyRegisteredVisitors"/></label></div>
            <div><input type="radio" value="GUEST" id="GUEST5"
                        name="editCommentRight" <%= selectedBlog.getId() > 0 && AccessGroup.GUEST.equals(selectedBlog.getEditCommentRight()) ? "checked" : "" %>>
                <label for="GUEST5"><international:get name="guest"/></label></div>
            <div><input type="radio" value="ALL" id="ALL5"
                        name="editCommentRight" <%= selectedBlog.getId() > 0 && AccessGroup.ALL.equals(selectedBlog.getEditCommentRight()) ? "checked" : "" %>>
                <label for="ALL5"><international:get name="everyone"/></label></div>
        </td>
        <td style="padding-top:20px;/*padding-bottom:10px;*/" id="blogDisplayLinksOptions">
            <span style="font-weight:bold;"><international:get name="displayLinksAndLabels"/></span>

            <div>
                <input type="checkbox" id="displayAuthorEmailAddress"
                       <% if (selectedBlog.isDisplayAuthorEmailAddress()) { %>checked<% } %>>
                <label for="displayAuthorEmailAddress">
                    <international:get name="displayAuthorEmailAddress"/>
                </label>
            </div>
            <div>
                <input type="checkbox" id="displayAuthorScreenName"
                       <% if (selectedBlog.isDisplayAuthorScreenName()) { %>checked<% } %>>
                <label for="displayAuthorScreenName">
                    <international:get name="displayAuthorScreenName"/>
                </label>
            </div>
            <div>
                <input type="checkbox" id="displayDate"
                       <% if (selectedBlog.isDisplayDate()) { %>checked<% } %>>
                <label for="displayDate">
                    <international:get name="displayDate"/>
                </label>
            </div>
            <div>
                <input type="checkbox" id="displayBlogName"
                       <% if (selectedBlog.isDisplayBlogName()) { %>checked<% } %>>
                <label for="displayBlogName">
                    <international:get name="displayBlogName"/>
                </label>
            </div>
            <div>
                <input type="checkbox" id="displayNextAndPreviousLinks"
                       <% if (selectedBlog.isDisplayNextAndPreviousLinks()) { %>checked<% } %>>
                <label for="displayNextAndPreviousLinks">
                    <international:get name="displayNextAndPreviousLinks"/>
                </label>
            </div>
            <div>
                <input type="checkbox" id="displayBackToTopLink"
                       <% if (selectedBlog.isDisplayBackToTopLink()) { %>checked<% } %>>
                <label for="displayBackToTopLink">
                    <international:get name="displayBackToTopLink"/>
                </label>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="3" id="blogDisplayPostsOptions">
            <span style="font-weight:bold;"><international:get name="displayPosts"/></span>

            <div>
                <input type="radio" id="DISPLAY_ALL" name="displayPosts" onclick="disableDisplayPostsSelect(this.id);"
                       <% if (selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_ALL) { %>checked<% } %>>
                <label for="DISPLAY_ALL">
                    <international:get name="displayAll"/>
                </label>
            </div>
            <div>
                <input type="radio" id="DISPLAY_FINITE_NUMBER" name="displayPosts"
                       onclick="disableDisplayPostsSelect(this.id);"
                       <% if (selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_FINITE_NUMBER) { %>checked<% } %>>
                <label for="DISPLAY_FINITE_NUMBER">
                    <international:get name="displayFiniteNumber"/>
                </label>
                <% final boolean disableFiniteNumber = selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_ALL || selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_WITHIN_DATE_RANGE; %>
                <select id="DISPLAY_FINITE_NUMBERSelect" <% if (disableFiniteNumber) { %>disabled<% } %>>
                    <% for (int i = 1; i < 5; i++) { %>
                    <option value="<%= i %>"
                            <% if (selectedBlog.getDisplayPostsFiniteNumber() == i) { %>selected<% } %>>
                        <%= i %>
                    </option>
                    <% } %>
                    <% for (int i = 5; i <= 50; i += 5) { %>
                    <option value="<%= i %>"
                            <% if (selectedBlog.getDisplayPostsFiniteNumber() == i) { %>selected<% } %>>
                        <%= i %>
                    </option>
                    <% } %>
                </select>
            </div>
            <div>
                <input type="radio" id="DISPLAY_WITHIN_DATE_RANGE" name="displayPosts"
                       onclick="disableDisplayPostsSelect(this.id);"
                       <% if (selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_WITHIN_DATE_RANGE) { %>checked<% } %>>
                <label for="DISPLAY_WITHIN_DATE_RANGE">
                    <international:get name="displayPostsWithinDateRange"/>
                </label>
                <% final boolean disableDateRange = selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_ALL || selectedBlog.getDisplayPosts() == DisplayPosts.DISPLAY_FINITE_NUMBER; %>
                <select id="DISPLAY_WITHIN_DATE_RANGESelect" <% if (disableDateRange) { %>disabled<% } %>>
                    <option value="1" <% if (selectedBlog.getDisplayPostsWithinDateRange() == 1) { %>selected<% } %>>
                        1&nbsp;<international:get name="month"/>
                    </option>
                    <% for (int i = 2; i < 13; i++) { %>
                    <option value="<%= i %>"
                            <% if (selectedBlog.getDisplayPostsWithinDateRange() == i) { %>selected<% } %>>
                        <%= i %>&nbsp;<international:get name="months"/>
                    </option>
                    <% } %>
                </select>
            </div>
        </td>
    </tr>
</table>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureBlogButtons">
        <input type="button" value="Apply" id="windowApply" onclick="configureBlog.save(false);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Save" id="windowSave" onclick="configureBlog.save(true);"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
        <input type="button" value="Cancel" id="windowCancel" onclick="closeConfigureWidgetDivWithConfirm();"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>

