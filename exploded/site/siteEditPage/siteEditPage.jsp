<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.SiteEditPageAction" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.presentation.ActionUtil" %>
<%@ page import="com.shroggle.entity.AccessibleElementType" %>
<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsTab" %>
<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="siteEditPage"/>
<% final SiteEditPageAction action = (SiteEditPageAction) request.getAttribute("actionBean"); %>
<% final Cookie leftPartCookie = ActionUtil.findCookie(action, "leftPart" + action.getUserId()); %>
<% final boolean showLeftPartMinimized = leftPartCookie != null && Boolean.parseBoolean(leftPartCookie.getValue());
    request.setAttribute("blueprint", (action.getSiteType() == SiteType.BLUEPRINT)); %>
<html>
<head>
    <title>Edit site pages</title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript">

        var siteId = <%= action.getSiteId() %>;
        var userId = <%= action.getUserId() %>;
        var showLeftPartMinimized = <%= showLeftPartMinimized %>;
        var siteIsBlueprint = <%= action.getSiteType() == SiteType.BLUEPRINT %>;
        var addPageRestricted = <%= new SiteManager(action.getSite()).isAddPagesRestrictedByBlueprint() %>;
        var checkWidgetBlueprintRights = <%= action.getSite().getBlueprintParent() != null %>;
        var currentlyViewedPageId = <%= action.getCurrentlyViewedPageId() %>;
        var pageLocked = false;

        var internationalTexts = new Object();
        internationalTexts.deleteBeg = "<international:get name="deleteBeg"/> ";
        internationalTexts.deleteNormalEnd = " <international:get name="deleteNormalEnd"/>";
        internationalTexts.deleteHiddenEnd = " <international:get name="deleteHiddenEnd"/>";
        internationalTexts.deleteMultiplePages = " <international:get name="deleteMultiplePages"/>";
        internationalTexts.discardDraft = " <international:get name="discardDraft"/>";
        internationalTexts.postLive = " <international:get name="postLive"/>";
        internationalTexts.postAllLive = " <international:get name="postAllLive"/>";
        internationalTexts.KEYWORDS_GROUP_NAME_NOT_UNIQUE = " <international:get name="KEYWORDS_GROUP_NAME_NOT_UNIQUE"/>";
        internationalTexts.KEYWORDS_GROUP_WITHOUT_VALUE = " <international:get name="KEYWORDS_GROUP_WITHOUT_VALUE"/>";
        internationalTexts.KEYWORDS_GROUP_WITHOUT_NAME = " <international:get name="KEYWORDS_GROUP_WITHOUT_NAME"/>";
        internationalTexts.currentPageNotSelected = " <international:get name="currentPageNotSelected"/>";
        internationalTexts.treeViewLive = " <international:get name="treeViewLive"/>";
        internationalTexts.treePreview = " <international:get name="treePreview"/>";
        internationalTexts.theOptionNotYetAvailable = "<international:get name="theOptionNotYetAvailable"/>";

        var preloadedImages = new Array();
        preloadedImages.push('/images/siteEditPage/white-site-pages.png');
        preloadedImages.push('/images/siteEditPage/white-page-components.png');

        preloadImages(preloadedImages);

    </script>
    <script type="text/javascript" src="/tinymce/jquery.tinymce.js"></script>
    <script type="text/javascript">

        $(document).ready(function() {
            enablePageLinks(false);
            enableAddPage(false);

            //Loading pages tree.
            loadTree();

            //Loading pages select.
            loadSelect();

            if (currentlyViewedPageId) {
                var currentlyViewedPageNode = $("li[pageId=\"" + currentlyViewedPageId + "\"]")[0];
                if (currentlyViewedPageNode) {
                    selectPageVersion(currentlyViewedPageNode, false);
                } else {
                    selectPageVersion($("li[page=\"page\"]")[0], false);
                }
            } else {
                selectPageVersion($("li[page=\"page\"]")[0], false);
            }
        });

    </script>
</head>
<body>
<input type="hidden" value="<%= action.isHasAdminRightsOnManyUsers() %>" id="hasAdminRightsOnManyAccounts"/>
<input type="hidden" value="<%= action.isMustBePaidBeforePublishing() %>" id="checkSiteStatus"/>
<input type="hidden" value="<%= action.isUserAdmin() %>" id="isAdmin"/>
<input type="hidden" value="<%= action.isSiteActive() %>" id="siteActive"/>
<input type="hidden" value="<%= action.getSiteId() %>" id="selectedSiteId"/>
<input type="hidden" id="youHaveNoSitesToCreateItem" value="<international:get name="youHaveNoSitesToCreateItem"/>"/>
<input type="hidden" value="true" id="siteEditPage"/>

<input type="hidden" value="<international:get name="noFuncElemetsOnPage"/>" id="noFuncElemetsOnPage"/>
<input type="hidden" value="<international:get name="siteAdminPostLiveSiteWithoutCC"/>"
       id="siteAdminPostLiveSiteWithoutCC"/>
<input type="hidden" value="<international:get name="enterPaymentDetails"/>" id="enterPaymentDetails"/>
<input type="hidden" value="<international:get name="cantBePublishedUntil"/>" id="cantBePublishedUntil"/>

<div id="pageNotFound" style="display: none;"><international:get name="pageNotFound"/></div>

<div class="wrapper">
<div class="container">
<%@ include file="/includeHeadApplication.jsp" %>

<div class="content_max">
<input type="hidden" id="siteId" value="<%= action.getSiteId() %>">
<input type="hidden" id="keywordCount" value="<%= action.getKeywordCount() %>">

<div id="page_select_div" style="height:auto;overflow:hidden;min-width:1060px;">
    <div class="page_select_inner_div">
        <div id="select_strecher">&nbsp;</div>
        <div style="display:none" id="page_select">
            <%= action.getSelectHtml() %>
        </div>
        <span id="rightSideSelectText">
            <%= HtmlUtil.limitName(action.getSiteName(), 16) %>:
        </span>
    </div>
    <span id="managePageMenuSecondDiv">
        <% request.setAttribute("gray", ""); %>
        <jsp:include page="managePageMenu.jsp" flush="true"/>
        <jsp:include page="publishMenu.jsp" flush="true"/>
    </span>

    <div class="siteEditPageMenuDiv">
        <a href="#" id="addWidget" class="siteEditPageMenuElement">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_addModule.png"
                     alt="<international:get name="addModule"/>" border="0">
            </div>
            <div style="text-align:center;">
                <international:get name="addModule"/>
            </div>
        </a>

        <div class="siteEditPageMenuElementDiv siteEditPageMenuElement" onmouseover="siteEditPageMenu.showMenu(this);"
             onmouseout="siteEditPageMenu.hideMenu(this);">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_edit.png" alt="<international:get name="editMenuButton"/>"
                     border="0">
            </div>

            <div style="text-align:center;">
                <international:get name="editMenuButton"/>
            </div>

            <div menuContent="true" class="siteEditPageMenu">
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a
                        id="configureWidget"
                        style="width:100%;height:100%" href="javascript:void(0);">Module
                    Settings</a>
                </div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a id="contentsWidget"
                                                                                                     href="javascript:void(0);">Module
                    Contents</a>
                </div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a
                        id="addEditFormRecords"
                        href="javascript:void(0);">Add / Edit Form
                    Records</a></div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a
                        id="editNetworkSettings"
                        href="javascript:void(0);">Network
                    Settings</a></div>
            </div>
        </div>
        <div class="siteEditPageMenuElementDiv siteEditPageMenuElement" onmouseover="siteEditPageMenu.showMenu(this);"
             onmouseout="siteEditPageMenu.hideMenu(this);">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_access.png" alt="<international:get name="access"/>"
                     border="0">
            </div>

            <div style="text-align:center;">
                <international:get name="access"/>
            </div>

            <div menuContent="true" class="siteEditPageMenu">
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a
                        id="widgetAccessible"
                        href="javascript:void(0);">Permissions</a>
                </div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a
                        id="widgetBlueprintPerm"
                        href="javascript:void(0);">Blueprint Content
                    Permissions</a>
                </div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a exportData="true"
                                                                                                     href="javascript:void(0);">Export
                    Data</a>
                </div>
            </div>
        </div>
        <div class="siteEditPageMenuElementDiv siteEditPageMenuElement" onmouseover="siteEditPageMenu.showMenu(this);"
             onmouseout="siteEditPageMenu.hideMenu(this);">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_display.png" alt="<international:get name="display"/>"
                     border="0">
            </div>

            <div style="text-align:center;">
                <international:get name="display"/>
            </div>

            <div menuContent="true" class="siteEditPageMenu">
                <div class="siteEditPageMenuLine" id="widgetDisplaySettingsText" style="display:none;">Content Module:
                </div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="widgetBorder"
                        href="javascript:void(0);">Borders
                    and
                    Spacing</a></div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="widgetBackground"
                        href="javascript:void(0);">Backgrounds</a>
                </div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="fontColorWidget"
                        href="javascript:void(0);">Fonts and
                    Styles</a></div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="widgetSizeElement"
                        href="javascript:void(0);">Content
                    Module Size</a></div>
                <div class="siteEditPageMenuLine" id="mediaBlockDisplaySettingsText" style="display:none;">Media Block
                    (page area):
                </div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="areaBorder" href="javascript:void(0);">Borders
                    and Spacing</a></div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="areaBackground"
                        href="javascript:void(0);">Backgrounds</a></div>
                <div class="siteEditPageMenuLine subMenu siteEditPageClickableMenu" style="display:none;"><a
                        id="fontColorArea" href="javascript:void(0);">Fonts and
                    Styles</a></div>
            </div>
        </div>
        <div class="siteEditPageMenuElementDiv siteEditPageMenuElement" onmouseover="siteEditPageMenu.showMenu(this);"
             onmouseout="siteEditPageMenu.hideMenu(this);">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_manageStore.png" alt="<international:get name="manageStore"/>"
                     border="0">
            </div>

            <div style="text-align:center;">
                <international:get name="manageStore"/>
            </div>

            <div menuContent="true" class="siteEditPageMenu">
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a id="completeOrders"
                                                                                                     href="javascript:void(0);">Complete
                    Orders</a>
                </div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a
                        id="editStoreSettings"
                        href="javascript:void(0);">Edit Store
                    Settings</a>
                </div>
                <div class="siteEditPageMenuLine siteEditPageClickableMenu" style="display:none;"><a exportData="true"
                                                                                                     href="javascript:void(0);">Export
                    Data</a></div>
            </div>
        </div>
        <a href="#" id="removeWidget" class="siteEditPageMenuElement">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_remove.png" alt="<international:get name="remove"/>"
                     border="0">
            </div>

            <div style="text-align:center;">
                <international:get name="remove"/>
            </div>
        </a>
        <a href="javascript:preview()" class="siteEditPageMenuElement">
            <div style="text-align:center;">
                <img src="../../images/siteEditPage/btn_preview.png" alt="<international:get name="previewMenuButton"/>"
                     border="0">
            </div>

            <div style="text-align:center;">
                <international:get name="previewMenuButton"/>
            </div>
        </a>
    </div>
</div>
<div style="clear:both;"></div>
<div class="right_box" id="right_box">
    <div class="box_100">
        <div class="inside_100" style="height:600px; overflow: hidden;">
            <div class="site_edit_page_arrow_transparent" onmouseover="setShowArrowTransparency(false);"
                 onmouseout="setShowArrowTransparency(true);" id="siteEditPageShowArrow"
                 onclick="showLeftPart()">
            </div>
            <iframe id="site" style="width: 100%; height: 100%; background-color:white;" src="pageNotSelected.jsp"
                    frameborder="0"></iframe>
            <div style="display:none; width:100%; height: 100%;  background-color:white;" id="mainLoadingMessageDiv">
                <img style="position:absolute;left:50%; top:50%" src="../../images/ajax-loader.gif">
                <span style="position:absolute;left:49%; top:55%">Loading...</span>
            </div>

        </div>
    </div>
</div>

<div class="sitePageBtn" onmouseover="this.className = 'sitePageBtn_Over';" id="sitePageBtn"
     onmouseout="this.className='sitePageBtn'" onclick="showLeftPart(false);"></div>
<div class="pageComponentsBtn" onmouseover="this.className = 'pageComponentsBtn_Over';" id="pageComponentsBtn"
     onmouseout="this.className='pageComponentsBtn'" onclick="showLeftPart(true);"></div>
<div class="left_part <% if (showLeftPartMinimized){ %>left_part_minimized<% } else { %>left_part_maximized<% } %>"
     id="left_part">

    <div class="tree_box" id="tree_box">
        <div class="site_edit_page_arrow_turned_transparent" onmouseover="setHideArrowTransparency(false);"
             onmouseout="setHideArrowTransparency(true);"
             onclick="hideLeftPart()" id="site_edit_page_arrow_turned">
        </div>

        <div class="left_part_header">
            <div class="siteEditPageMenuDivForPage" id="left_part_links">
                <% request.setAttribute("gray", "gray"); %>
                <jsp:include page="managePageMenu.jsp" flush="true"/>

                <div class="siteEditPageMenuElement">
                    <a id="addPage"
                       href="javascript:configurePageSettings.show({isEdit:false, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})">
                        <div style="text-align:center;">
                            <img src="../../images/siteEditPage/btn_addModule.png"
                                 alt="<international:get name="addPage"/>"
                                 border="0">
                        </div>

                        <div style="text-align:center;">
                            <international:get name="addPage"/>
                        </div>
                    </a>
                </div>

                <jsp:include page="publishMenu.jsp" flush="true"/>

            </div>
        </div>

        <div id="tree-div" class="overflow_a" style="height:88%">
            <ul class="simpleTree">
                <li class="root" id='1'>
                    <ul>
                        <%= action.getTreeHtml() %>
                    </ul>
                </li>
            </ul>
        </div>

        <div id="pageComponentsContent" style="display:none">
            <div id="elements_div">
                <div class="head_13" style="height:34px"><international:get name="funcElementsOnPage"/></div>

                <div id="pageFunctionalElements" class="overflow_a" style="height:173px;">
                    <international:get name="noFuncElemetsOnPage"/>
                </div>
            </div>
        </div>
    </div>
</div>

<br>
<br clear="all">&nbsp;
</div>
<%@ include file="../../includeFooterApplication.jsp" %>
</div>
</div>
</body>
</html>