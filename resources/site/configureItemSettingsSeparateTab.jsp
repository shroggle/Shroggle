<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsTab" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%
    final ConfigureItemSettingsService service =
            (ConfigureItemSettingsService) request.getAttribute("itemSettingsService");
%>
<% if (service.getTab() == ConfigureItemSettingsTab.SETTINGS) { %>

<% if (service.getItemType() == ItemType.FORUM) { %>
<jsp:include page="configureForumWidget.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.BLOG) { %>
<jsp:include page="configureBlogWidget.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.REGISTRATION) { %>
<jsp:include page="configureRegistration.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.CUSTOM_FORM) { %>
<jsp:include page="configureCustomForm.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.CONTACT_US) { %>
<jsp:include page="configureContactUsWidget.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.CHILD_SITE_REGISTRATION) { %>
<jsp:include page="/childSiteRegistration/configureChildSiteRegistration.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.ADVANCED_SEARCH) { %>
<jsp:include page="/advancedSearch/configureAdvancedSearch.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.MANAGE_VOTES) { %>
<jsp:include page="/site/configureManageVotes.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.BLOG_SUMMARY) { %>
<jsp:include page="/blogSummary/configureBlogSummary.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.PURCHASE_HISTORY) { %>
<jsp:include page="/site/configurePurchaseHistory.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.SHOPPING_CART) { %>
<jsp:include page="/site/configureShoppingCart.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.TELL_FRIEND) { %>
<jsp:include page="/site/configureTellFriend.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.ADMIN_LOGIN) { %>
<jsp:include page="/site/configureAdminLogin.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.MENU) { %>
<jsp:include page="/menu/configureMenuWidget.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.IMAGE) { %>
<jsp:include page="/image/configureImage.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.TEXT) { %>
<jsp:include page="/site/configureText.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.SCRIPT) { %>
<jsp:include page="/site/configureScript.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.GALLERY) { %>
<jsp:include page="/gallery/configureGallery.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.VIDEO) { %>
<jsp:include page="/video/configureVideo.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.GALLERY_DATA) { %>
<jsp:include page="/gallery/configureWidgetGalleryData.jsp" flush="true"/>
<% } else if (service.getItemType() == ItemType.SLIDE_SHOW) { %>
<jsp:include page="/slideShow/configureSlideShow.jsp" flush="true"/>
<% } %>

<% } else if (service.getTab() == ConfigureItemSettingsTab.FONTS_COLORS) { %>
<jsp:include page="configureFontsAndColors.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureItemSettingsTab.ITEM_SIZE) { %>
<jsp:include page="configureItemSize.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureItemSettingsTab.BORDER) { %>
<jsp:include page="/borderBackground/configureBorder.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureItemSettingsTab.BACKGROUND) { %>
<jsp:include page="/borderBackground/configureBackground.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureItemSettingsTab.ACCESSIBLE) { %>
<jsp:include page="/site/accessibilityForRender/elementAccessibility.jsp" flush="true"/>
<% } else if (service.getTab() == ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS) { %>
<jsp:include page="/site/configureBlueprintItemPermissions.jsp" flush="true"/>
<% } %>
