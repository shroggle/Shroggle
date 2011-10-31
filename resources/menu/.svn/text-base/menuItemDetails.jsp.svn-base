<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.entity.MenuItem" %>
<%@ page import="com.shroggle.logic.menu.MenuItemManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.Page" %>
<%@ page import="com.shroggle.entity.MenuUrlType" %>
<%@ page import="com.shroggle.entity.SiteShowOption" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<international:part part="configureMenuItem"/>
<%--
  @author Balakirev Anatoliy
--%>
<%
    final MenuItem menuItem = (MenuItem) request.getAttribute("menuItem");
    if (menuItem != null) {
        final List<Page> pages = (List<Page>) request.getAttribute("pages");
        final MenuItemManager manager = new MenuItemManager(menuItem, SiteShowOption.getDraftOption());
%>
<div class="configureMenuInnerSettingsDiv">
    <input type="hidden" id="selectedMenuItemId" value="<%= menuItem.getId() %>">

    <input type="hidden" id="name" value="<%= manager.getName() %>">
    <input type="hidden" id="title" value="<%= manager.getTitle() %>">
    <input type="hidden" id="pageId" value="<%= menuItem.getPageId() %>">
    <input type="hidden" id="customUrl" value="<%= manager.getCustomUrl() %>">
    <input type="hidden" id="imageId" value="<%= manager.getImageId() %>">
    <input type="hidden" id="imageUrl" value="<%= manager.getThumbnailImageUrl() %>">
    <input type="hidden" id="menuUrlType" value="<%= manager.getMenuUrlType().toString() %>">
    <input type="hidden" id="visibility" value="<%= manager.isImageExist() ? "visible" : "hidden" %>">
    <input type="hidden" id="saveUnsavedMenuItemChanges" value="<international:get name="saveUnsavedMenuItemChanges"/>">

    <div id="menuInnerSettings">
    <table class="menuItemDetailsTable">
        <tr>
            <td style="width:20%">
                <label for="menuItemName">
                    <international:get name="name"/>:
                </label>
            </td>
            <td style="width:80%">
                <input type="text" id="menuItemName" excludeControlChange onkeydown="setMenuItemDetailsChanged(true);" value="<%= manager.getName() %>" style="width:250px;">
            </td>
        </tr>
        <tr>
            <td style="width:20%">
                <label for="menuItemTitle">
                    <international:get name="title"/>:
                </label></td>
            <td style="width:80%">
                <input type="text" id="menuItemTitle" excludeControlChange onkeydown="setMenuItemDetailsChanged(true);" value="<%= manager.getTitle() %>" style="width:250px;">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <span style="font-weight:bold;"><international:get name="linkMenuItem"/></span>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <input type="radio" id="menuItemPageIdCheckbox" name="menuUrlType" excludeControlChange onchange="setMenuItemDetailsChanged(true);"
                       value="<%=  MenuUrlType.SITE_PAGE.toString() %>"
                       onclick="$('#menuItemPageId')[0].disabled=false;$('#menuItemCustomUrl')[0].disabled=true;"
                       <% if (manager.getMenuUrlType() == MenuUrlType.SITE_PAGE) { %>checked <% } %>>
                <label for="menuItemPageIdCheckbox">
                    <international:get name="openSitePage"/>:
                </label>
            </td>
        </tr>
        <tr>
            <td/>
            <td>
                <select id="menuItemPageId" style="margin-left:25px;" excludeControlChange onchange="setMenuItemDetailsChanged(true);"
                        <% if (manager.getMenuUrlType() == MenuUrlType.CUSTOM_URL) { %>disabled <% } %>>
                    <% for (Page currentPage : pages) { %>
                    <option value="<%= currentPage.getPageId() %>"
                            <% if (menuItem.getPageId() != null && menuItem.getPageId().intValue() == currentPage.getPageId()) { %>selected="selected"<% } %>>
                        <%= currentPage.getPageSettings().getName() %>
                    </option>
                    <% } %>
                </select>
            </td>
        </tr>

        <tr>
            <td/>
            <td>
                <input type="radio" id="menuItemCustomUrlCheckbox" name="menuUrlType" excludeControlChange onchange="setMenuItemDetailsChanged(true);"
                       value="<%=  MenuUrlType.CUSTOM_URL.toString() %>"
                       onclick="$('#menuItemPageId')[0].disabled=true;$('#menuItemCustomUrl')[0].disabled=false;$('#menuItemCustomUrl')[0].focus();"
                       <% if (manager.getMenuUrlType() == MenuUrlType.CUSTOM_URL) { %>checked <% } %>>
                <label for="menuItemCustomUrlCheckbox">
                    <international:get name="customUrl"/>:
                </label>
            </td>
        </tr>
        <tr>
            <td/>
            <td>
                <div style="font-weight:bold;margin-left:25px;">
                    <label for="menuItemCustomUrl"><international:get name="http"/></label>
                    <input type="text" id="menuItemCustomUrl" value="<%= manager.getCustomUrl() %>" style="width:200px;" excludeControlChange  onkeydown="setMenuItemDetailsChanged(true);"
                           <% if (manager.getMenuUrlType() == MenuUrlType.SITE_PAGE) { %>disabled <% } %>>
                </div>
            </td>
        </tr>
    </table>
    </div>

    <div id="imageUploaderPanelId">
    <span style="font-weight:bold;"><international:get name="addImageForMenu"/></span>
    <elementWithOnload:element onload="showMenuImageUploader();"/>
    <table class="menuItemDetailsTable">
        <%---------------------------------------------------Image--------------------------------------------------------%>

        <tr>
            <td>
                <div id="menuItemImageHolder"
                     style="width:200px; height:100px; text-align:center;<% if (!manager.isImageExist()) { %>visibility:hidden;<% } %>">
                    <img id="menuItemImage" src="<%= manager.getThumbnailImageUrl() %>">
                </div>
            </td>
            <td style="vertical-align:middle;">
                <input type="button" value="<international:get name="browseAndUpload"/>" id="browseAndUploadMenuImageButton"
                       class="but_w170_misc">
                <span id="menuImageButtonContainer"
                     style="position:relative;top:-25px;left:0;cursor: pointer;"
                     onmouseout="$('#browseAndUploadMenuImageButton')[0].className='but_w170_misc';"
                     onmouseover="$('#browseAndUploadMenuImageButton')[0].className='but_w170_Over_misc';">
                    <span id="menuImageButtonPlaceHolder">

                    </span>
                </span>
            </td>
        </tr>
    </table>
    <%---------------------------------------------------Image--------------------------------------------------------%>
    </div>
</div>


<div id="menuInnerSettingsBtns" class="configureMenuSettingsButtonsDiv" style="margin-top:5px;">
    <input type="button" class="but_w73_misc" onmouseover="this.className='but_w73_Over_misc';"
           onmouseout="this.className='but_w73_misc';" value="Remove" onclick="removeMenuItem();"/>
    <input type="button" class="but_w73_misc" onmouseover="this.className='but_w73_Over_misc';"
           onmouseout="this.className='but_w73_misc';" value="Apply" onclick="applyChangedMenuItemSettings();"/>
    <input type="button" class="but_w73_misc" onmouseover="this.className='but_w73_Over_misc';"
           onmouseout="this.className='but_w73_misc';" value="Cancel" onclick="restoreDefaultSettings();"/>
</div>
<input type="hidden" id="menuItemImageId" value="<%= manager.getImageId() %>">
<% } %>
