<%@ page import="com.shroggle.entity.GalleryOrientation" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.entity.WidgetItem" %>
<%@ page import="com.shroggle.logic.accessibility.UserRightManager" %>
<%@ page import="com.shroggle.logic.form.filter.FormFilterManager" %>
<%@ page import="com.shroggle.logic.site.item.ItemManager" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.presentation.gallery.ConfigureGalleryService" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="siteItems" tagdir="/WEB-INF/tags/siteItems" %>
<% final ConfigureGalleryService service = (ConfigureGalleryService) request.getAttribute("galleryService"); %>
<international:part part="configureGallery"/>
<table border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td valign="top" width="190" style="padding-right: 5px;">
            <!-- Layout preview section -->
            <b><international:get name="layoutPreview"/></b>
            <international:get name="layoutPreviewDescription"/><br><br>
            <img src="/images/gallery/customLayoutImage.png"
                 id="configureGalleryDefaultLayoutImage" alt="" width="200" height="140">
        </td>
        <td valign="top">
            <b><international:get name="selectOrientation"/></b><br>
            <international:get name="ie"/><br>
            <select style="margin-left: 15px; margin-top: 10px;" size="1"
                    id="configureGalleryDefaultLayoutGroups"
                    onchange="changeConfigureGallerytDefaultLayoutGroup();">
                <option value="<%= GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW %>"><international:get name="navigationAboveDataBelow"/></option>
                <option value="<%= GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW %>"><international:get name="dataAboveNavigationBelow"/></option>
                <option value="<%= GalleryOrientation.NAVIGATION_LEFT_DATA_RIGHT %>"><international:get name="navigationLeftDataRight"/></option>
                <option value="<%= GalleryOrientation.DATA_LEFT_NAVIGATION_RIGHT %>"><international:get name="dataLeftNavigationRight"/></option>
                <option value="<%= GalleryOrientation.NAVIGATION_ONLY %>"><international:get name="navigationOnly"/></option>
                <option value="<%= GalleryOrientation.DATA_ONLY %>"><international:get name="dataOnly"/></option>
            </select><br><br>
            <b><international:get name="selectDefaultLayout"/></b> <international:get name="editLayout"/>
            <table border="0" cellpadding="0" cellspacing="0" style="margin-left: 15px; margin-top: 10px; width:593px">
                <tr id="configureGalleryDefaultLayout">
                    <td ><img src="/images/gallery/customLayoutThumbnail.png" width="70" height="51" alt=""></td>
                </tr>
            </table>
            <% final List<Site> sitesForData = service.getSitesForData(); %>
            <% if (sitesForData.size() > 0) { %>
                    <br>
                    <div id="configureGalleryAllowPagesBlock">
                        <b><international:get name="selectWhichPageToDisplayDataIn"/></b><br>

                        <!-- Pages for create data layout -->
                        <input type="radio" name="configureGalleryDataType" id="configureGalleryDataTypeNew"
                               checked="checked" onchange="changeConfigureGalleryAllowData();">
                        <label for="configureGalleryDataTypeNew"><international:get name="selectSiteForData"/></label>
                        <select size="1" id="configureGallerySitesForData" onchange="changeConfigureGallerySiteForData();"
                                <% if (service.getWidgetGalleryDatas().size() > 0) { %>disabled="disabled"<% } %>
                                style="width:130px;">
                            <% for (final Site site : sitesForData) { %>
                                <option value="<%= site.getId() %>" <%= service.getSiteManager() != null && service.getSiteManager().getSite() == site ? "selected=\"true\"" : "" %>><%= HtmlUtil.limitName(site.getTitle(), 40) %></option>
                            <% } %>
                        </select>

                        <international:get name="selectPageForData"/>
                        <span id="configureGalleryPagesForDataHolder">
                            <% request.setAttribute("sitePages", (service.getSiteManager() != null ? service.getSiteManager().getPages() : null));
                                request.setAttribute("configureGalleryPagesForDataDisabled", (service.getWidgetGalleryDatas().size() > 0)); %>
                            <jsp:include page="sitePagesSelect.jsp" flush="true"/>
                        </span>


                        <span class="mark2" style="margin-left:0;margin-top:5px;"><a href="javascript:showConfigureGalleryAllowPageDatasInfo()">
                            <international:get name="whatsThis"/>
                        </a></span>
                        <div id="configureGalleryAllowPageDatasInfo" style="display:none;">
                            <div class="windowOneColumn">
                                <div style="overflow:auto; padding:10px; text-align:left;">
                                    <international:get name="allowPageDatasInfo"/>
                                </div>
                                <br clear="all"><br>
                                <p align="right">
                                    <input type="button" onclick="closeConfigureWidgetDiv();"
                                           class="but_w73"
                                onmouseover="this.className='but_w73_Over';"
                                onmouseout="this.className='but_w73';"
                                value="Close"></p>
                            </div>
                        </div>
                    </div>
                <% } %>
                <div id="configureGalleryAllowWidgetDatasBlock" style="padding-top:5px;">
                    <input type="radio" name="configureGalleryDataType"
                           <% if (service.getWidgetGalleryDatas().size() < 1) { %>disabled="disabled"<% } %>
                        onclick="changeConfigureGalleryAllowData();"
                        id="configureGalleryAllowWidgetsRadio">
                    <label for="configureGalleryAllowWidgetsRadio">
                        <international:get name="selectWhichPage"/>
                    </label>

                    <!-- Widget gallery data for use -->
                    <select size="1" id="configureGalleryAllowWidgetDatas">
                        <option selected="true">
                            <international:get name="selectWhichPageToDisplayDataOrNone"/>
                        </option>
                        <% for (WidgetItem widgetGalleryData : service.getWidgetGalleryDatas()) { %>
                            <option value="<%= widgetGalleryData.getCrossWidgetId() %>|<%= widgetGalleryData.getWidgetId() %>">
                                <%= HtmlUtil.limitName(new PageManager(widgetGalleryData.getPage()).getName(), 40) %> (<%= widgetGalleryData.getPosition() %>)
                            </option>
                        <% } %>
                    </select>
                </div>

            <br>
            <b><international:get name="whereWillGalleryImagesComeFrom"/></b><br>

            <%
                final UserRightManager userRightManager = service.getUser().getRight();
                final List<ItemManager> formManagers = ItemManager.siteItemsToManagers(
                    userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));
            %>
            <input type="radio" style="margin-left: 15px; margin-top: 10px;" <%= formManagers.isEmpty() ? "disabled=1" : "" %>
                   id="configureGalleryUploadImagesExists" name="configureGalleryUploadImages"
                   onchange="configureGalleryGetForm(<%= service.getWidgetId() %>);disableFormFilterLinks();">
            <label for="configureGalleryUploadImagesExists">
                <international:get name="iWillUseAnExistingForm"/>
            </label>

            <!-- Forms for use -->
            <div style="margin-left: 35px; margin-top: 5px;">
                <select id="configureGalleryForms" size="1" style="width:220px;" <%= formManagers.isEmpty() ? "disabled=true" : "" %>
                        onchange="configureGalleryGetForm();disableFormFilterLinks();">
                    <option value="-1"><international:get name="selectAForm"/></option>
                    <siteItems:asOptions value="<%= formManagers %>"/>
                </select>&nbsp;&nbsp;
                <span class="mark" style="margin-left:0;margin-top:5px;"><a href="javascript:showConfigureGalleryGetFormInfo()"><international:get
                        name="whatsThis"/></a></span>

                <div id="configureGalleryGetFormInfo" style="display:none;">
                    <div class="windowOneColumn">
                        <div style="overflow:auto;padding:10px;text-align:left;">
                            <international:get name="whatsThisText1"/>
                        </div>
                        <br clear="all"><br>

                        <p align="right">
                            <input type="button" onclick="closeConfigureWidgetDiv();"
                                   class="but_w73"
                                onmouseover="this.className='but_w73_Over';"
                                onmouseout="this.className='but_w73';"
                                value="Close"></p>
                    </div>
                </div>
            </div>

            <input type="radio" checked="checked" style="margin-left: 15px; margin-top: 10px;"
                   id="configureGalleryUploadImagesNew" name="configureGalleryUploadImages"
                   onchange="configureGalleryGetDefaultForm();">
            <label for="configureGalleryUploadImagesNew">
                <international:get name="iWillUploadImages"/>
            </label><br>

                <!-- Form Filters -->
            <input type="radio" style="margin-left: 15px; margin-top: 10px;"
                   id="configureGalleryUseFormFilter" name="configureGalleryUploadImages"
                   onchange="configureGallerySetUseFilter();disableFormFilterLinks();">
            <label for="configureGalleryUseFormFilter">
                <international:get name="formFilterTitle"/>
            </label>
            <div style="margin-left: 35px; margin-top: 5px;">
                <select id="configureGalleryFormFilter" onchange="disableFormFilterLinks();">
                    <option value="-1" selected="selected"><international:get name="formFilterNone"/></option>
                    <% final List<FormFilterManager> filterManagers = service.getFormFiltersLogic().getFilters(); %>
                    <% for (final FormFilterManager filterManager : filterManagers) { %>
                        <option value="<%= filterManager.getId() %>">
                            <%= filterManager.getName() %>
                        </option>
                    <% } %>
                </select>&nbsp;&nbsp;
                <a href="javascript:configureGalleryEditFormFilter()" id="editFilterLink"><international:get name="formFilterEdit"/></a>
                <a href="javascript:configureGalleryCreateFormFilter()" id="createFilterLink" style="margin-left:5px;"><international:get name="formFilterNew"/></a>
                <span class="mark" style="margin-left:5px;margin-top:5px;">
                    <a href="javascript:showConfigureGalleryFormFilterInfo()"><international:get name="whatsThis"/></a>
                </span>
            </div>

            <input type="checkbox" style="margin-left: 15px; margin-top: 10px;" id="configureGalleryShowOnlyMyRecords">
            <label for="configureGalleryShowOnlyMyRecords">
                <international:get name="showOnlyMyRecords"/>
            </label>

                <div id="configureGalleryGetFormFilterInfo" style="display:none;">
                    <div class="windowOneColumn">
                        <div style="overflow:auto;padding:10px;text-align:left;">
                            <international:get name="formFilterWhatsThisText"/>
                        </div>
                        <br clear="all"><br>

                        <p align="right">
                            <input type="button" onclick="closeConfigureWidgetDiv();"
                                   class="but_w73"
                                onmouseover="this.className='but_w73_Over';"
                                onmouseout="this.className='but_w73';"
                                value="Close"></p>
                    </div>
                </div>
        </td>
    </tr>
</table>