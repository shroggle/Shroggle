<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsTab" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="siteEditPage"/>
<%--
    @author Balakirev Anatoliy
--%>
<div class="siteEditPageMenuElementDiv siteEditPageMenuElement cursorPointer"
     onmouseover="siteEditPageMenu.showMenu(this);"
     onmouseout="siteEditPageMenu.hideMenu(this);">

    <a href="javascript:configurePageSettings.show({isEdit:true, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})">
        <div style="text-align:center;">
            <img src="../../images/siteEditPage/btn_managePage.png"
                 alt="<international:get name="managePage"/>"
                 border="0">
        </div>

        <div style="text-align:center;">
            <international:get name="managePage"/>
        </div>
    </a>

    <div menuContent="true" class="siteEditPageMenu <%= request.getAttribute("gray") %>">
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a
                href="javascript:configurePageSettings.show({isEdit:true, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})"
                ><international:get
                name="treeRename"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:clickDeleteSelectPage()"
                                                                       deletePage="deletePage"><international:get
                name="treeDelete"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:showLayout()"
                                                                       setLayoutPage="setLayoutPage"><international:get
                name="treeLayout"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a
                href="javascript:configurePageSettings.show({isEdit:true, tab:'<%= ConfigurePageSettingsTab.SEO_META_TAGS %>'})"
                ><international:get name="treePageSEOSettings"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:resetChanges()"
                                                                       resetChanges="resetChanges"><international:get
                name="treeDiscardDraft"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:configureCopyPage()"
                ><international:get name="copyPage"/></a>
        </div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:showAccessibleForPage()"
                ><international:get
                name="treePerm"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:showBackgroundsForPage()"
                ><international:get
                name="treeBackground"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:editHtml()"
                                                                       editHtml="editHtml"><international:get
                name="treeHTML"/></a>
        </div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:preview()"
                                                                       preview="preview"><international:get
                name="treePreview"/></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a href="javascript:showStatistics()"
                                                                       style="margin-bottom:0"><international:get
                name="treePageTraffic"/></a></div>
        <% if (request.getAttribute("blueprint") != null && (Boolean) request.getAttribute("blueprint")) { %>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu"><a
                href="javascript:configurePageSettings.show({isEdit:true, tab:'<%= ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS %>'})"
                ><international:get
                name="pageVersionBlueprintRight"/></a></div>
        <% } %>
    </div>
</div>