<%@ page import="com.shroggle.presentation.gallery.comment.GalleryCommentData" %>
<%@ page import="com.shroggle.logic.gallery.comment.GalleryCommentsManager" %>
<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="comment"/>
<% final GalleryCommentData galleryCommentData = (GalleryCommentData) request.getAttribute("galleryCommentData"); %>
<% final User loginedUser = new UsersManager().getLoginedUser(); %>

<%-------------------------------------------------Add Comments Link--------------------------------------------------%>
<a href="#" class="addGalleryCommentLink" onclick="showAddGalleryComment(
                                                        this, <%= galleryCommentData.getFilledFormId() %>,
                                                        <%= galleryCommentData.getGalleryId() %>,
                                                        <%= galleryCommentData.getSiteId() %>,
                                                        <%= galleryCommentData.getWidgetId() %>);return false;"><international:get
        name="addComments"/></a>
<%-------------------------------------------------Add Comments Link--------------------------------------------------%>

<%-------------------------------------------------Hide Comments Link-------------------------------------------------%>
<a href="#" class="hideGalleryCommentsLink"
   id="hideGalleryCommentsLink" style="display:none" onclick="hideGalleryComments(this);return false;"
        ><international:get name="hideComments"/></a>
<%-------------------------------------------------Hide Comments Link-------------------------------------------------%>


<%-------------------------------------------------View Comments Link-------------------------------------------------%>
<a href="#" class="viewGalleryCommentsLink" onclick="showGalleryComments(
                                                        this, <%= galleryCommentData.getFilledFormId() %>,
                                                        <%= galleryCommentData.getGalleryId() %>,
                                                        <%= galleryCommentData.getWidgetId() %>, false);return false;"><international:get
        name="viewComments"/>
    (<span class="galleryCommentCount">
        <%= new GalleryCommentsManager().get(galleryCommentData.getFilledFormId(), galleryCommentData.getGalleryId()).size() %>
    </span>)</a>
<%-------------------------------------------------View Comments Link-------------------------------------------------%>


<div class="afterAddingCommentBlock" style="color:green;font-weight:bold;display:none;">
    <international:get name="afterAddingCommentMessage"/>
</div>

<%---------------------------------------------------hidden fields----------------------------------------------------%>
<input id="youHaveToBeLoggedIn<%= galleryCommentData.getWidgetId() %>" type="hidden"
       value="<international:get name="youHaveToBeLoggedIn"/>">
<input id="galleryLoginedUserId<%= galleryCommentData.getWidgetId() %>"
       value="<%= loginedUser != null ? loginedUser.getUserId() : null %>" type="hidden"/>
<input id="registrationFormIdForVoters<%= galleryCommentData.getWidgetId() %>" type="hidden"
       value="<%= galleryCommentData.getRegistrationFormForVotersId() %>">
<input type="hidden" class="editorWidth" value="200"/>
<input type="hidden" class="editorHeight" value="100"/>
<%---------------------------------------------------hidden fields----------------------------------------------------%>