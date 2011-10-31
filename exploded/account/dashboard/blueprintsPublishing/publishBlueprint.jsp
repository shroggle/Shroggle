<%@ page import="com.shroggle.entity.BlueprintCategory" %>
<%@ page import="com.shroggle.logic.blueprintsPublishing.ShowPublishBlueprintModel" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.logic.site.BlueprintCategoryManager" %>
<%@ taglib prefix="img" tagdir="/WEB-INF/tags/imageWithLoadingProgress" %>
<%@ taglib prefix="onload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%--
    @author Balakirev Anatoliy
--%>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="publishBlueprint"/>
<% final ShowPublishBlueprintModel model = (ShowPublishBlueprintModel) request.getAttribute("model"); %>

<div class="windowOneColumn">
    <input type="hidden" id="selectedPageId" value="null">
    <input type="hidden" id="siteId" value="<%= model.getSiteId() %>">

    <h1>
        <% if (model.isActivationMode()) { %><international:get
            name="activateBlueprint"/><% } else { %><international:get name="publishBlueprint"/><% } %>
    </h1>

    <div class="inform_mark" style="margin-left:7px;"><international:get
            name="useThisPageToSubmitBlueprintInstruction"/></div>
    <div class="marginTopBottom10px">
        <label for="blueprintDescription"><international:get name="blueprintDescription"/>:&nbsp;</label>
        <input type="text" id="blueprintDescription" maxlength="255" value="<%= model.getDescription() %>"/>
    </div>

    <div class="marginBottom10px">
        <label for="businessCategory">
            <international:get name="selectBusinessCategories"/>:&nbsp;
        </label>
        <select id="businessCategory">
            <% for (BlueprintCategory category : BlueprintCategoryManager.getSortedValues()) { %>
            <option value="<%= category.toString() %>"
                    <% if (model.getBlueprintCategory() == category) { %>selected<% } %>>
                <%= new BlueprintCategoryManager(category).getInternationalValue() %>
            </option>
            <% } %>
        </select>
    </div>

    <% if (!model.getPages().isEmpty()) { %>
    <h2><international:get name="uploadScreenShotOfEachPageOfTheSite"/></h2>

    <div class="inform_mark" style="margin-left:7px;"><international:get name="noteThatOnlyPublishedPagesAreShownHere"/></div>
    <div class="blueprintScrreenshots">
        <% StringBuilder pageIds = new StringBuilder(); %>
        <table style="width:100%;">
            <% for (PageManager pageManager : model.getPages()) {
                pageIds.append(pageManager.getId());
                pageIds.append(";"); %>
            <%----------------------------------------------Screen Shot---------------------------------------------------%>
            <%--<div>--%>
            <tr>
                <td style="width:30%;height:55px;">
                    <div style="float:left;margin-top:20px;">
                        <%= HtmlUtil.limitName(pageManager.getName(), 20) %>:
                    </div>
                </td>
                <td style="height:55px;">
                    <% final String imageId = "screenShot" + pageManager.getId(); %>
                    <div style="float:left;margin:0 15px;">
                        <img:image id="<%= imageId %>" src="<%= pageManager.getScreenShotUrl() %>"
                                   width="50" height="50"/>
                    </div>
                    <div style="height:50px;">
                        <input type="button" value="<international:get name="browseAndUpload"/>"
                               style="margin-top:12px;"
                               id="browseAndUploadScreenShotButton<%= pageManager.getId() %>" class="but_w170_misc">

                        <span id="screenShotButtonContainer<%= pageManager.getId() %>"
                              style="position:relative;top:-25px;left:0;cursor: pointer;"
                              onmouseout="$('#browseAndUploadScreenShotButton<%= pageManager.getId() %>')[0].className='but_w170_misc';"
                              onmouseover="$('#browseAndUploadScreenShotButton<%= pageManager.getId() %>')[0].className='but_w170_Over_misc';$('#selectedPageId').val(<%= pageManager.getId() %>);">
                            <span id="screenShotButtonPlaceHolder">

                            </span>
                        </span>
                    </div>
                    <input type="hidden" id="selectedScreenShotId<%= pageManager.getId() %>"
                           value="<%= pageManager.getScreenShotId() %>">
                    <% request.setAttribute("pageId", pageManager.getId()); %>
                    <onload:element onload="screenShotUploader.showUploader(${pageId});"/>
                    <br clear="all">
                </td>
            </tr>
            <%----------------------------------------------Screen Shot---------------------------------------------------%>
            <% } %>
        </table>
    </div>
    <input type="hidden" id="pageIds" value="<%= pageIds.toString() %>">
    <% } else { %>
    <div style="font-weight:bold;text-align:center;color:red;"><international:get
            name="youCantPublishBlueprintWithoutPublishedPages"/></div>
    <% } %>
    <div class="buttons_box">
        <% if (!model.getPages().isEmpty()) { %>
        <% if (model.isActivationMode()) { %>
        <input type="button" onmouseout="this.className='but_w73';" id="windowSave"
               value="<international:get name="activate"/>"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="publishBlueprint.activate();"
               alt="<international:get name="activate"/>">
        <% } else { %>
        <input type="button" onmouseout="this.className='but_w73';" id="windowSave"
               value="<international:get name="publish"/>"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="publishBlueprint.publish();"
               alt="<international:get name="publish"/>">
        <% } %>
        <% } %>
        <input type="button" onmouseout="this.className='but_w73';" id="windowCancel"
               value="<international:get name="cancel"/>"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>