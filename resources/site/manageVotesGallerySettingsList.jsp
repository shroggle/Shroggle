<%@ page import="com.shroggle.presentation.manageVotes.ConfigureManageVotesService" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.presentation.manageVotes.GalleryWithWidgets" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.form.FormItemManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.site.WidgetManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageVotes"/>
<% List<GalleryWithWidgets> availableGalleriesWithWidgets = (List<GalleryWithWidgets>) request.getAttribute("availableGalleriesWithWidgets"); %>
<div class="tbl_dblborder">
    <table>
        <thead>
        <tr>
            <td style="width:45px;">
                <international:get name="select"/>
            </td>
            <td style="width:145px;">
                <international:get name="moduleName"/>
            </td>
            <td style="width:200px;">
                <international:get name="modulePage"/>
            </td>
            <td style="width:150px;">
                <international:get name="recordName"/>
            </td>
            <td style="width:170px;">
                <international:get name="editModuleName"/>
            </td>
            <td>
                <international:get name="colorCode"/>
            </td>
        </tr>
        </thead>
    </table>
    <table>
        <tbody>
        <% for (GalleryWithWidgets galleryWithWidget : availableGalleriesWithWidgets) { %>
        <% final DraftGallery gallery = galleryWithWidget.getGallery(); %>
        <tr class="manageVotesGallerySettings">
            <td style="width:45px;text-align:center;">
                <% final boolean existingItem = galleryWithWidget.getManageVotesGallerySettings().getManageVotes() != null; %>
                <input class="manageVotesGalleryChecked" <% if (existingItem) { %>checked="checked"<% }
                    int result1 = galleryWithWidget.getGallery().getId();
                %>
                       value="<%= result1 %>" type="checkbox"/>
            </td>
            <td style="width:145px;">
                <%= gallery.getName() %>
            </td>
            <td style="width:200px;">
                <select class="manageVotesCrossWidgetId" style="width:200px;">
                    <% for (Widget widget : galleryWithWidget.getWidgets()) { %>
                    <option <% if (galleryWithWidget.getManageVotesGallerySettings().getGalleryCrossWidgetId() == widget.getCrossWidgetId()) {%>selected="selected" <% } %>
                            value="<%= widget.getCrossWidgetId() %>"><%= new WidgetManager(widget).getLocation() %></option>
                    <% } %>
                </select>
            </td>
            <td style="width:150px;">
                <select class="manageVotesRecordName" style="width:150px;">
                    <%
                        for (DraftFormItem formItem : ServiceLocator.getPersistance().getFormById(gallery.getFormId1()).getDraftFormItems()) {
                            if (!FormItemManager.isCorrectFormItemForManageYourVotes(formItem)){
                                continue;
                            }
                    %>
                    <option <% if (galleryWithWidget.getManageVotesGallerySettings().getFormItemId() == formItem.getFormItemId()) {%>selected="selected" <% } %>
                            value="<%= formItem.getFormItemId() %>"><%= formItem.getItemName() %></option>
                    <% }
                        int result = galleryWithWidget.getGallery().getId();
                    %>
                </select>
            </td>
            <td style="width:170px;">
                <input style="width:98%;" class="manageVotesGallerySettingsCustomName" id="customName" type="text" maxlength="255"
                       value="<%= galleryWithWidget.getManageVotesGallerySettings().getCustomName() %>"/>
            </td>
            <td style="min-width:150px;vertical-align:middle;" id="colorPickerBlock<%= result %>" class="manageYourVotesColorPickerBlock"
                    colorCode="<%= galleryWithWidget.getManageVotesGallerySettings().getColorCode() %>">
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>