<%@ taglib prefix="image" tagdir="/WEB-INF/tags/imageWithLoadingProgress" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.logic.gallery.GalleryCellData" %>
<%@ page import="com.shroggle.logic.gallery.GalleryData" %>
<%@ page import="com.shroggle.logic.gallery.GalleryItemData" %>
<%@ page import="com.shroggle.logic.gallery.GalleryItemType" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ page import="com.shroggle.util.Dimension" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.presentation.gallery.GalleryNavigationUrlCreator" %>
<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.shroggle.presentation.gallery.GalleryNavigationUrl" %>
<%@ page import="com.shroggle.entity.Page" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<international:part part="renderWidgetGalleryData"/>
<%
    final Widget galleryDataWidget = (Widget) request.getAttribute("widget");
    final int galleryDataWidgetId = galleryDataWidget.getWidgetId();
    final Widget widget = ServiceLocator.getPersistance().getWidget(galleryDataWidgetId);
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
    final GalleryData galleryData = (GalleryData) request.getAttribute("galleryData");
    final int currentPageId = widget.getPage().getPageId();
//    final Integer crossWidgetId = galleryData.getGalleryManager().getEntity().getDataCrossWidgetId();
    final String videoPlayerDivId = "galleryVideo" + galleryDataWidgetId;
    final String videoPlayerId = videoPlayerDivId + "Player";
    final International galleryDataInternational = ServiceLocator.getInternationStorage().get("renderWidgetGalleryData", Locale.US);
%>
<table id="galleryData<%= galleryDataWidgetId %>"
       class="blockToReload<%= galleryDataWidgetId %> galleryData" border="0"
       cellspacing="0" cellpadding="0" width="100%">
<tbody>
<input type="hidden" id="noFilmAvailableText" value="<international:get name="noFilmAvailable"/>">

<% for (final GalleryCellData[] columnData : galleryData.getCells()) { %>
<tr>
<% for (final GalleryCellData cellData : columnData) { %>
<% if (cellData == null) { %>

<% } else if (cellData.getItems().isEmpty()) { %>
<td>&nbsp;</td>
<% } else { %>

<td colspan="<%= cellData.getWidth() %>">
<% for (final GalleryItemData itemData : cellData.getItems()) { %>


<%-------------------------------------------------Gallery data items-----------------------------------------%>
<% if (itemData.getType() == GalleryItemType.GALLERY_DATA_ITEM) { %>
<div class="galleryDataItem" style="text-align: <%= itemData.getItem().getAlign() %>">
    <% if (!StringUtil.isNullOrEmpty(itemData.getItem().getName())) { %>
    <span class="galleryDataItemLabel"><%= itemData.getItem().getName() %></span>
    <% } %>


    <% if (itemData.isImage()) {
        final String imageUrl = itemData.getResizedImageUrl(); %>
    <div style="width:<%= itemData.getDataItemWidth() %>px; height:<%= itemData.getDataItemHeight() %>px;">
        <% if (imageUrl.isEmpty()) { %>
        <%-------------------------------------gray rectangle instead of image----------------------------------------%>
        <div style="width:<%= itemData.getResizedWidth() %>px;height:<%= itemData.getResizedHeight() %>px;background : #F0F0F0;"></div>
        <%-------------------------------------gray rectangle instead of image----------------------------------------%>
        <% } else { %>
        <%---------------------------------------------------image----------------------------------------------------%>
        <% final String id = String.valueOf(System.nanoTime());
            final Dimension dimension = itemData.getSourceImageDimension();
            final boolean showOriginalImage = dimension.getWidth() > itemData.getResizedWidth() || dimension.getHeight() > itemData.getResizedHeight(); %>
        <input type="hidden" id="sourceSizeUrl<%= id %>" value="<%= itemData.getSourceImageUrl() %>">
        <input type="hidden" id="sourceWidth<%= id %>" value="<%= dimension.getWidth() %>">
        <input type="hidden" id="sourceHeight<%= id %>" value="<%= dimension.getHeight() %>">
            <span class="galleryDataItemText">
                <%
                    String onclick = showOriginalImage ? "showSourceSize(this, " + id + "," + itemData.getResizedWidth() + "," + itemData.getResizedHeight() + ");" : "";
                    final String style = showOriginalImage ? "cursor:url(../../../images/zoomin.cur), default;" : ""; %>
                <image:image src="<%= imageUrl %>"
                             alt="<%= itemData.getImageAltAndTitle() %>"
                             title="<%= itemData.getImageAltAndTitle() %>"
                             width="<%= String.valueOf(itemData.getResizedWidth()) %>"
                             height="<%= String.valueOf(itemData.getResizedHeight()) %>"
                             onclick="<%= onclick %>"
                             style="<%= style %>"/>
            </span>
        <%---------------------------------------------------image----------------------------------------------------%>
        <% } %>
    </div>
    <% } else if (itemData.isVideo()) { %>
    <%---------------------------------------------------video----------------------------------------------------%>
    <%
        final String normalVideoUrlId = "galleryNormalVideoUrl" + galleryDataWidgetId;
        final String largeVideoUrlId = "galleryLargeVideoUrl" + galleryDataWidgetId;
        final String videoImageUrlId = "galleryVideoImageUrl" + galleryDataWidgetId;
    %>
    <input type="hidden" id="<%= normalVideoUrlId %>" value="<%= itemData.getNormalVideoUrl() %>"/>
    <input type="hidden" id="<%= largeVideoUrlId %>" value="<%= itemData.getLargeVideoUrl() %>"/>
    <input type="hidden" id="<%= videoImageUrlId %>" value="<%= itemData.getVideoImageUrl() %>"/>

    <%-- Close gaps for video. SW-6578 --%>
    <% if (!galleryData.getGalleryManager().getEntity().isHideEmpty() || !itemData.getNormalVideoUrl().isEmpty()) { %>
    <div style="width: <%= itemData.getNormalVideoWidth() %>px;height: <%= itemData.getNormalVideoHeight() %>px;">
        <% if (itemData.getNormalVideoUrl().isEmpty()) { %>
        <%-------------------------------gray rectangle instead of empty video--------------------------------%>
        <table style="width:100%;height:100%;">
            <tr>
                <td style="background : #F0F0F0;vertical-align:middle;text-align:center;font-weight:bold;">
                    <international:get name="noFilmAvailable"/>
                </td>
            </tr>
        </table>
        <%-------------------------------gray rectangle instead of empty video--------------------------------%>
        <% } else { %>
        <div id="galleryVideo<%= galleryDataWidgetId %>"
             style="width: <%= itemData.getNormalVideoWidth() %>px;height: <%= itemData.getNormalVideoHeight() %>px">
        </div>
        <% final String videoOnloadValue = "showVideo({" +
                "videoFlvId: " + itemData.getNormalVideoFlvId() + "," +
                "videoUrlId: '" + normalVideoUrlId + "'," +
                "imageUrlId: '" + videoImageUrlId + "'," +
                "width: '" + itemData.getNormalVideoWidth() + "', height: '" + itemData.getNormalVideoHeight() + "'," +
                "videoPlayerDivId: '" + videoPlayerDivId + "'," +
                " videoPlayerId: '" + videoPlayerId + "'," +
                "galleryId: " + galleryData.getGalleryManager().getId() + "," +
                "filledFormId: " + galleryData.getFilledFormId() + "," +
                "videoStatus:'" + itemData.getNormalVideoStatus() + "'" +
                "});"; %>
        <elementWithOnload:element onload="<%= videoOnloadValue %>"/>
        <script type="text/javascript">
            function viewLarger() {
                createVideo({
                    videoFlvId: <%= itemData.getLargeVideoFlvId() %>,
                    videoUrlId: '<%= largeVideoUrlId %>',
                    imageUrlId: '<%= videoImageUrlId %>',
                    width: '<%= itemData.getLargeVideoWidth() %>', height: '<%= itemData.getLargeVideoHeight() %>',
                    videoPlayerDivId: '<%= videoPlayerDivId %>ViewLarge',
                    videoPlayerId: '<%= videoPlayerId %>',
                    galleryId: <%= galleryData.getGalleryManager().getId() %>,
                    filledFormId: <%= galleryData.getFilledFormId() %>,
                    videoStatus:'<%= itemData.getLargeVideoStatus() %>',
                    smallVideoPlayerId: '<%= videoPlayerDivId %>'
                });
            }
        </script>
        <div class="galleryDataNormalLargeVideoLinks">
            <a href="javascript:viewLarger();"><%= itemData.getInternational().get("viewLarger") %>
            </a>
        </div>
        <% } %>
    </div>
    <br clear="right">
    <% } %>
    <%---------------------------------------------------video----------------------------------------------------%>
    <% } else if (itemData.isHr()) { %>
    <%-----------------------------------------------------hr-----------------------------------------------------%>
            <span class="galleryDataItemText">
            <hr>
            </span>
    <%-----------------------------------------------------hr-----------------------------------------------------%>
    <% } else if (itemData.isHeader()) { %>
    <%---------------------------------------------------header---------------------------------------------------%>
            <span class="galleryDataItemText">
            <%= itemData.getHeader() %>
            </span>
    <%---------------------------------------------------header---------------------------------------------------%>
    <% } else if (itemData.isTextArea()) { %>
    <div class="galleryDataItemText">
        <%= itemData.getTextAreaValue() %>
    </div>
    <% } else { %>
            <span class="galleryDataItemText">
            <%= itemData.getValue() %>
            </span>
    <% } %>
</div>
<% } %>
<%-------------------------------------------------Gallery data items-----------------------------------------%>
<% if (galleryData.isShowVotingItems()) { %>
<%----------------------------------------------Voting stars item---------------------------------------------%>
<% if (itemData.getType() == GalleryItemType.VOTING_STARS) {
    request.setAttribute("videoPlayerId", videoPlayerId); %>
<table style="width:100%;" cellpadding="0" cellspacing="0" border="0">
    <tr>
        <td align="<%= galleryData.getVotingStarsAlign() %>">
            <jsp:include page="votingStars.jsp" flush="true"/>
        </td>
    </tr>
</table>
<% } %>
<%----------------------------------------------Voting stars item---------------------------------------------%>

<%----------------------------------------------Voting links item---------------------------------------------%>
<% if (itemData.getType() == GalleryItemType.VOTING_LINKS) { %>
<div style="text-align: <%= galleryData.getVotingLinksAlign() %>">
    <jsp:include page="votingLinks.jsp" flush="true"/>
</div>
<% } %>
<%----------------------------------------------Voting links item---------------------------------------------%>
<% } %>
<%----------------------------------------------Back to navigation--------------------------------------------%>
<% if (itemData.getType() == GalleryItemType.BACK_TO_NAVIGATION) { %>
<div class="galleryDataBackToNavigation" style="text-align: <%= galleryData.getBackToNavigationAlign() %>">
    <a href="<%= galleryData.getBackToNavigationUrl() %>">
        <% if (galleryData.getBackToNavigationName() != null && !"".equals(galleryData.getBackToNavigationName())) { %>
        <%= galleryData.getBackToNavigationName() %>
        <% } else { %>
        Back to Navigation
        <% } %>
    </a>
</div>
<% } %>
<%----------------------------------------------Back to navigation--------------------------------------------%>


<%--------------------------------------------Child site links item-------------------------------------------%>
<% if (itemData.getType() == GalleryItemType.CHILD_SITE_LINK) { %>
<div style="text-align: <%= galleryData.getChildSiteLinkAlign() %>">
    <% if (galleryData.isChildSiteExistAndActive()) { %>
    <a href="<%= galleryData.getChildSiteLink() %>" target="_blank">
        &nbsp;<%= galleryData.getChildSiteLinkText() %>&nbsp;
    </a>
    <% } else { %>
    &nbsp;<%= galleryData.getChildSiteLinkText() %>&nbsp;
    <% } %>
</div>
<% } %>
<%--------------------------------------------Child site links item-------------------------------------------%>


<%------------------------------------------------Paypal button-----------------------------------------------%>
<% if (itemData.getType() == GalleryItemType.PAYPAL_BUTTON) { %>
<div style="text-align: <%= galleryData.getPaypalButtonAlign() %>">
    <% request.setAttribute("paypalSettings", galleryData.getPaypalSettings()); %>
    <% request.setAttribute("paypalButtonGallery", galleryData.getGalleryManager().getEntity()); %>
    <% request.setAttribute("currentDisplayedFilledFormId", galleryData.getFilledFormId()); %>
    <jsp:include page="paypalButton.jsp" flush="true"/>
</div>
<% } %>
<%------------------------------------------------Paypal button-----------------------------------------------%>

<%------------------------------------------------Paypal button-----------------------------------------------%>
<% if (itemData.getType() == GalleryItemType.GO_TO_SHOPPING_CART && galleryData.getPaypalSettings().getSelectedShoppingCartPageId() != null) { %>
<% final Page shoppingCartPage = ServiceLocator.getPersistance().getPage(galleryData.getPaypalSettings().getSelectedShoppingCartPageId()); %>
<% if (shoppingCartPage != null) { %>
<% final String shoppingCartPageUrl = currentPageId == shoppingCartPage.getPageId() ? "#" :
        new PageManager(shoppingCartPage, siteShowOption).getUrl() + "&galleryReferralPageId=" + currentPageId; %>
<div style="text-align: <%= galleryData.getPaypalSettings().getGoToShoppingCartAlign() %>">
    <% final String shoppingCartCustomName = galleryData.getPaypalSettings().getGoToShoppingCartName(); %>
    <a href="<%= shoppingCartPageUrl %>"><%= StringUtil.isNullOrEmpty(shoppingCartCustomName) ? galleryDataInternational.get("goToCart") : shoppingCartCustomName %>
    </a>
</div>
<% } %>
<% } %>

<% if (itemData.getType() == GalleryItemType.VIEW_PURCHASE_HISTORY && galleryData.getPaypalSettings().getSelectedPurchaseHistoryPageId() != null) { %>
<% final Page purchaseHistoryPage = ServiceLocator.getPersistance().getPage(galleryData.getPaypalSettings().getSelectedPurchaseHistoryPageId()); %>
<% if (purchaseHistoryPage != null) { %>
<% final String purchaseHistoryPageUrl = currentPageId == purchaseHistoryPage.getPageId() ? "#" :
        new PageManager(purchaseHistoryPage, siteShowOption).getUrl() + "&galleryReferralPageId=" + currentPageId; %>
<div style="text-align: <%= galleryData.getPaypalSettings().getViewPurchaseHistoryAlign() %>">
    <% final String purchaseHistoryCustomName = galleryData.getPaypalSettings().getViewPurchaseHistoryName(); %>
    <a href="<%= purchaseHistoryPageUrl %>"><%= StringUtil.isNullOrEmpty(purchaseHistoryCustomName) ? galleryDataInternational.get("viewPurchaseHistory") : purchaseHistoryCustomName %>
    </a>
</div>
<% } %>
<% } %>
<%------------------------------------------------Paypal button-----------------------------------------------%>

<% if (itemData.getType() == GalleryItemType.DATA_PAGINATOR) { %>
<div style="text-align: <%= galleryData.getPaypalSettings().getGoToShoppingCartAlign() %>">
    <c:set var="galleryData" value="<%= galleryData %>"/>
    <c:set var="navigation" value="${galleryData.navigation}"/>
    <c:set var="after" value="${navigation.afterItem}"/>
    <c:set var="before" value="${navigation.beforeItem}"/>
    <%
        final Integer before = galleryData.getNavigation().getBeforeItem();
        final Integer after = galleryData.getNavigation().getAfterItem();
        final String urlAfter = after != null ? GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, after, siteShowOption).getUserScript() : null;
        final String ajaxDispatchAfter = after != null ? GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, after, siteShowOption).getAjaxDispatch() : null;
        final GalleryNavigationUrl galleryNavigationUrl = before != null ? GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, before, siteShowOption) : null;
        final String urlBefore = galleryNavigationUrl != null ? galleryNavigationUrl.getUserScript() : null;
        final String ajaxDispatchBefore = galleryNavigationUrl != null ? galleryNavigationUrl.getAjaxDispatch() : null;
    %>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREVIOUS_NEXT'}">
        <c:if test="${after != null}">
            <a class="galleryDataPrev" ajaxHistory="<%= ajaxDispatchAfter %>" href="#"
               onclick="<%= urlAfter %>">Previous</a>
        </c:if>
    </c:if>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREVIOUS_NEXT_WITH_NUMBERS'}">
        <c:if test="${after != null}">
            <a class="galleryDataPrev" ajaxHistory="<%= ajaxDispatchAfter %>" href="#"
               onclick="<%= urlAfter %>">Prev</a>
        </c:if>
    </c:if>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREV_NEXT'}">
        <c:if test="${after != null}">
            <a class="galleryDataPrev" ajaxHistory="<%= ajaxDispatchAfter %>" href="#"
               onclick="<%= urlAfter %>;">Prev</a>
        </c:if>
    </c:if>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREVIOUS_NEXT_WITH_NUMBERS'}">
        <% for (Map.Entry entry : galleryData.getNavigation().getAfterItems().entrySet()) {
            final GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, (Integer) entry.getValue(), siteShowOption); %>
        <a class="galleryDataPageNumber galleryDataItemPrev" onclick="<%= url.getUserScript() %>" href="#"
           ajaxHistory="<%= url.getAjaxDispatch() %>"
                ><%= entry.getKey() %>
        </a>
        <% } %>
        <span class="galleryDataItemCurrent">${navigation.currentPosition}</span>

        <% for (Map.Entry entry : galleryData.getNavigation().getBeforeItems().entrySet()) {
            final GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, (Integer) entry.getValue(), siteShowOption); %>
        <a class="galleryDataPageNumber galleryDataItemNext" ajaxHistory="<%= url.getAjaxDispatch() %>"
           onclick="<%= url.getUserScript() %>" href="#"><%= entry.getKey() %>
        </a>
        <% } %>
    </c:if>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREVIOUS_NEXT' || galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREV_NEXT' || galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREVIOUS_NEXT_WITH_NUMBERS'}">
        <c:if test="${before != null}">
            <a class="galleryDataNext" ajaxHistory="<%= ajaxDispatchBefore %>"
               onclick="<%= urlBefore %>" href="#">Next</a>
        </c:if>
    </c:if>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'PREVIOUS_NEXT_WITH_BORDERED_NUMBERS'}">

        <c:if test="${after != null}">
            <a class="galleryDataAfterItemBordered" ajaxHistory="<%= ajaxDispatchAfter %>"
               onclick="<%= urlAfter %>" href="#"
                    >Previous</a>
        </c:if>

        <% for (Map.Entry entry : galleryData.getNavigation().getAfterItems().entrySet()) {
            final GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, (Integer) entry.getValue(), siteShowOption); %>
        <a class="galleryDataItemBordered" onclick="<%= url.getUserScript() %>" href="#"
           ajaxHistory="<%= url.getAjaxDispatch() %>"
                ><%= entry.getKey() %>
        </a>
        <% } %>

        <span class="galleryDataCurrentItemBordered">${navigation.currentPosition}</span>

        <% for (Map.Entry entry : galleryData.getNavigation().getBeforeItems().entrySet()) {
            final GalleryNavigationUrl url = GalleryNavigationUrlCreator.executeForCurrentWindow(galleryData.getGalleryManager().getEntity(), widget, (Integer) entry.getValue(), siteShowOption); %>
        <a class="galleryDataItemBordered" onclick="<%= url.getUserScript() %>" href="#"
           ajaxHistory="<%= url.getAjaxDispatch() %>"
                ><%= entry.getKey() %>
        </a>
        <% } %>
        <c:if test="${before != null}">
            <a class="galleryDataBeforeItemBordered" ajaxHistory="<%= ajaxDispatchBefore %>"
               onclick="<%= urlBefore %>" href="#"
                    >Next</a>
        </c:if>

    </c:if>

    <c:if test="${galleryData.galleryManager.entity.dataPaginator.dataPaginatorType == 'ARROWS'}">
        <c:if test="${after != null}">
            <a class="galleryDataLeftArrow" ajaxHistory="<%= ajaxDispatchAfter %>"
               onclick="<%= urlAfter %>" href="#">
                <img src="${galleryData.galleryManager.leftArrowUrl}" alt="" height="80" border="0"></a>
        </c:if>

        <c:if test="${before != null}">
            <a class="galleryDataRightArrow" ajaxHistory="<%= ajaxDispatchBefore %>"
               onclick="<%= urlBefore %>" href="#">
                <img src="${galleryData.galleryManager.rightArrowUrl}" alt="" height="80" border="0"></a>
        </c:if>
    </c:if>
</div>
<% } %>

<% } %>
</td>
<% } %>
<% } %>
</tr>
<% } %>

</tbody>
</table>