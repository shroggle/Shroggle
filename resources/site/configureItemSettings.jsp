<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsTab" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureItemSettings"/>
<%
    final ConfigureItemSettingsService service = (ConfigureItemSettingsService) request.getAttribute("itemSettingsService");

    final String windowClass;
    if (service.getItem().getItemType().isExtendedSettingsWindow()) {
        windowClass = "wholeExtendedItemSettingsWindowDiv";
    } else {
        windowClass = "wholeItemSettingsWindowDiv";
    }
%>

<div class="windowMainContent">
<div class="twoColumnsWindow_columnWrapper">
    <div id="twoColumnsWindow_leftColumn" class="twoColumnsWindow_leftColumn">
        <div class="leftmenu_header">
            <international:get name="itemSettingsHeader"/>
        </div>
        <div class="leftmenu_body">
            <input type="hidden" id="siteOnItemRightType"
                   value="<%= service.getSiteOnItemRightType() %>"/>
            <% if (service.getItemType() == ItemType.IMAGE) { %>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.SETTINGS ? "c1current" : "c1" %>"
                 id="primaryTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', primaryImage);">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="selectImage"/></span>
            </div>
            <div class="c1" id="rollOverTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', rollOverImage);">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="rollOverEffect"/></span>
            </div>
            <div class="c1" id="labelsLinksTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', labelsLinks);">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="labelsLinks"/></span>
            </div>
            <% } else if (service.getItemType() == ItemType.LOGIN) { %>
            <%-- No settings for login. --%>
            <% } else if (service.getItemType() == ItemType.CHILD_SITE_REGISTRATION) { %>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.SETTINGS ? "c1current" : "c1" %>"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', function(){configureChildSiteRegistration.showFormSettingsTab();
                         configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                     '<%= ConfigureItemSettingsTab.SETTINGS %>', '<%= service.getItemType() %>');
                         });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="formSettings"/></span>
            </div>

            <div class="c1" id="childSiteRegistrationNetworkSettingsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', configureChildSiteRegistration.showNetworkSettingsTab);">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="paymentSettings"/></span>
            </div>

            <div class="c1" id="childSiteRegistrationWhiteLabelSettingsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', configureChildSiteRegistration.showWhiteLabelSettingsTab);">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="whiteLabelSettings"/></span>
            </div>
            <% } else if (service.getItemType() == ItemType.SLIDE_SHOW) { %>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.SETTINGS ? "c1current" : "c1" %>"
                 id="settingsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', function(){configureSlideShow.selectSettingsTab();
                 configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                     '<%= ConfigureItemSettingsTab.SETTINGS %>', '<%= service.getItemType() %>');});">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="settings"/></span>
            </div>

            <div class="c1" id="slideShowManageItemsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent', configureSlideShow.selectManageImagesTab);">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="manageImages"/></span>
            </div>
            <% } else { %>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.SETTINGS ? "c1current" : "c1" %>"
                 id="settingsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.SETTINGS %>TabContent',
                 function(){configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.SETTINGS %>', '<%= service.getItemType() %>');});">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="settings"/></span>
            </div>
            <% } %>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.FONTS_COLORS ? "c1current" : "c1" %>"
                 id="fontsColorsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.FONTS_COLORS %>TabContent', function(){
                         configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.FONTS_COLORS %>', '<%= service.getItemType() %>');
                 });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="fontsColors"/></span>
            </div>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.BORDER ? "c1current" : "c1" %>"
                 id="borderTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.BORDER %>TabContent', function(){
                   configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.BORDER %>', '<%= service.getItemType() %>');
                 });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="border"/></span>
            </div>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.BACKGROUND ? "c1current" : "c1" %>"
                 id="backgroundTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.BACKGROUND %>TabContent', function(){
                    configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.BACKGROUND %>', '<%= service.getItemType() %>');
                 });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="background"/></span>
            </div>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.ITEM_SIZE ? "c1current" : "c1" %>"
                 id="widgetSizeTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.ITEM_SIZE %>TabContent', function(){
                    configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.ITEM_SIZE %>', '<%= service.getItemType() %>');
                 });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="widgetSize"/></span>
            </div>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.ACCESSIBLE ? "c1current" : "c1" %>"
                 id="accessibleTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.ACCESSIBLE %>TabContent', function(){
                    configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.ACCESSIBLE %>', '<%= service.getItemType() %>');
                 });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="accessible"/></span>
            </div>
            <%-- todo We can't show this tab from manage items. Ask Igor. --%>
            <% if (service.getSite().getType() == SiteType.BLUEPRINT && service.getWidgetId() != null) { %>
            <div class="<%= service.getTab() == ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS ? "c1current" : "c1" %>"
                 id="blueprintPermissionsTab"
                 onclick="selectTabInTwoColumnWindow(this, '<%= ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS %>TabContent', function(){
                    configureItemSettings.showSeparateTab({widgetId:<%= service.getWidgetId() %>, itemId:<%= service.getItem().getId() %>},
                 '<%= ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS %>', '<%= service.getItemType() %>');
                 });">
                <div class="c1_over_A">&nbsp;</div>
                <span><international:get name="blueprintPermissions"/></span>
            </div>
            <% } %>
        </div>
    </div>
</div>
<div class="twoColumnsWindow_columnWrapper">
    <div id="twoColumnsWindow_rightColumn" class="twoColumnsWindow_rightColumn">
        <div id="<%= ConfigureItemSettingsTab.SETTINGS %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.SETTINGS ? "" : "display:none;" %>">
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
            <% } %>
        </div>
        <div id="<%= ConfigureItemSettingsTab.FONTS_COLORS %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.FONTS_COLORS ? "" : "display:none;" %>">
            <% if (service.getTab() == ConfigureItemSettingsTab.FONTS_COLORS) { %>
            <jsp:include page="configureFontsAndColors.jsp" flush="true"/>
            <% } %>
        </div>
        <div id="<%= ConfigureItemSettingsTab.ITEM_SIZE %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.ITEM_SIZE ? "" : "display:none;" %>">
            <% if (service.getTab() == ConfigureItemSettingsTab.ITEM_SIZE) { %>
            <jsp:include page="configureItemSize.jsp" flush="true"/>
            <% } %>
        </div>
        <div id="<%= ConfigureItemSettingsTab.BORDER %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.BORDER ? "" : "display:none;" %>">
            <% if (service.getTab() == ConfigureItemSettingsTab.BORDER) { %>
            <jsp:include page="/borderBackground/configureBorder.jsp" flush="true"/>
            <% } %>
        </div>
        <div id="<%= ConfigureItemSettingsTab.BACKGROUND %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.BACKGROUND ? "" : "display:none;" %>">
            <% if (service.getTab() == ConfigureItemSettingsTab.BACKGROUND) { %>
            <jsp:include page="/borderBackground/configureBackground.jsp" flush="true"/>
            <% } %>
        </div>
        <div id="<%= ConfigureItemSettingsTab.ACCESSIBLE %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.ACCESSIBLE ? "" : "display:none;" %>">
            <% if (service.getTab() == ConfigureItemSettingsTab.ACCESSIBLE) { %>
            <jsp:include page="/site/accessibilityForRender/elementAccessibility.jsp" flush="true"/>
            <% } %>
        </div>
        <div id="<%= ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS %>TabContent" class="tabContent <%= windowClass %>"
             style="<%= service.getTab() == ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS ? "" : "display:none;" %>">
            <% if (service.getTab() == ConfigureItemSettingsTab.BLUEPRINT_PERMISSIONS) { %>
            <jsp:include page="/site/configureBlueprintItemPermissions.jsp" flush="true"/>
            <% } %>
        </div>
    </div>
</div>
<div style="clear:both;"></div>
</div>
