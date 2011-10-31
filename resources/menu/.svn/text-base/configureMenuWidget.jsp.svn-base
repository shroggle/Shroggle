<%@ page import="com.shroggle.entity.MenuStructureType" %>
<%@ page import="com.shroggle.entity.MenuStyleType" %>
<%@ page import="com.shroggle.presentation.menu.ConfigureMenuService" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureMenu"/>
<% final ConfigureMenuService service = (ConfigureMenuService) request.getAttribute("menuService");
    final MenuStyleType menuStyleType = service.getMenu().getMenuStyleType(); %>
<input type="hidden" value="<international:get name="menuNameCannotBeEmpty"/>" id="menuNameCannotBeEmpty">
<input type="hidden" value="<international:get name="menuItemNameCannotBeEmpty"/>" id="menuItemNameCannotBeEmpty">
<input type="hidden" value="<international:get name="menuNameNotUnique"/>" id="menuNameNotUnique">
<input type="hidden" value="<international:get name="selectExistingMenu"/>" id="selectExistingMenu">
<input type="hidden" value="<international:get name="selectExistingMenuForDelete"/>" id="selectExistingMenuForDelete">
<input type="hidden" value="<international:get name="selectMenuStyle"/>" id="selectMenuStyle">
<input type="hidden" value="<international:get name="discardCustomizedPageStructure"/>"
       id="discardCustomizedPageStructure">
<input type="hidden" value="<%= MenuStructureType.DEFAULT.toString() %>" id="defaultStructure">
<input type="hidden" value="<%= MenuStructureType.OWN.toString() %>" id="ownStructure">
<input type="hidden" value="<%= service.getSelectedItem().getMenu().getSiteId() %>" id="siteId">

<input type="hidden" value="<%= MenuStyleType.TREE_STYLE_HORIZONTAL.toString() %>" id="treeStyleHorizontal">
<input type="hidden" value="<%= MenuStyleType.TREE_STYLE_VERTICAL.toString() %>" id="treeStyleVertical">
<input type="hidden" value="<%= MenuStyleType.TABBED_STYLE_HORIZONTAL.toString() %>" id="tabbedStyle">

<input type="hidden" id="menuId" value="<%= service.getMenu().getId() %>">

<div class="itemSettingsWindowDiv">
    <h1><international:get name="addEditMenu"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="menuService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="menuErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="menuReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <div style="margin-bottom:20px;">
        <div style="float:left;width:80px;">
            <label for="menuName"> <international:get name="formName"/></label>
        </div>
        <input type="text" id="menuName" style="width:250px;"
               value="<%= service.getMenu().getName().replace("\"", "'") %>" maxlength="255" class="title">
    </div>

    <div style="float:left;">
        <div style="float:left;width:80px;">
            <label for="menuStyle"> <international:get name="menuStyle"/></label>
        </div>
        <select id="menuStyle" style="width:250px;margin-right:20px;" onchange="disableIncludePageTitle(this);updateMenuPreview(this.value);">
            <% for (MenuStyleType type : MenuStyleType.values()) { %>
            <option <% if (menuStyleType == type) { %>selected="selected"<% } %>
                    value="<%= type.toString() %>">
                <international:get name="<%= type.toString() %>"/>
            </option>
            <% } %>
        </select>

        <div style="margin-left: 75px; margin-top:10px;">
            <input type="checkbox" id="includePageTitle"
                   <% if (menuStyleType == MenuStyleType.TREE_STYLE_HORIZONTAL ||
           menuStyleType == MenuStyleType.TREE_STYLE_VERTICAL) { %>disabled="disabled"<% } %>
                   <% if (service.getMenu().isIncludePageTitle()) { %>checked="checked"<% } %>/>
            <label for="includePageTitle"><international:get name="includePageTitle"/></label>
        </div>
    </div>
    <div style="float:left;margin-left:50px;">
        <div style="margin-bottom:10px;">
            <% request.setAttribute("menuStyleType", menuStyleType); %>
            <international:get name="menuStyleExample"/>
        </div>
        <div class="scrollableMenuPreview" id="scrollableMenuPreview">
            <!-- root element for the scrollable elements -->
            <div class="menuPreviewItems" id="menuPreviewItems">
                <jsp:include page="previews/previews.jsp" flush="true"/>
            </div>
        </div>
        <div style="float:left;margin-left:20px;">
            <div class="menuScrollable prev"></div>
            <div class="menuScrollable next"></div>
        </div>
    </div>
    <%@ include file="previews/hiddenPreviewImages.jsp"%>


    <br clear="all">

    <div class="configureMenuLeftDiv">
        <span style="font-weight:bold;"><international:get name="editMenuStructure"/></span>

        <div class="inform_mark"><international:get
                name="uncheckAllPagesThatShouldnotBeAddedToTheSiteMenu"/></div>

        <div class="configureMenuSettingsDiv" id="menuItemsHolder">
            <div id="tree_div" class="configureMenuInnerSettingsDiv configureMenuTreeDiv">
                <ul class="simpleTree" id="menuTree">
                    <li class="root" id='1'>
                        <ul id="menuTreeHolder">
                            <%= service.getMenuTreeHtml() %>
                        </ul>
                    </li>
                </ul>
            </div>

            <div class="configureMenuSettingsButtonsDiv" id="addRemoveMenuItemButtons">
                <input type="button" class="but_w130_misc" onmouseover="this.className='but_w130_Over_misc';"
                       onmouseout="this.className='but_w130_misc';" value="Add Menu Item" onclick="addMenuItem();"/>
            </div>
        </div>
    </div>

    <div class="configureMenuRightDiv">

        <div style="visibility:hidden; height:22px;"></div>

        <span style="font-weight:bold;"><international:get name="editSelectedMenuItem"/></span>

        <div class="configureMenuSettingsDiv" id="menuItemDetailsHolder">
            <div id="menuItemDetails">
                <% request.setAttribute("pages", service.getPages());
                    request.setAttribute("menuItem", service.getSelectedItem()); %>
                <jsp:include page="menuItemDetails.jsp" flush="true"/>
            </div>
        </div>
    </div>

    <div style="clear:both;margin:5px 0 10px 0;">
        <input type="checkbox" id="useDefaultStructure" onchange="restoreDefaultStructure(this.checked);"
               <% if (service.getMenu().getMenuStructure() == MenuStructureType.DEFAULT) { %>checked<% } %>>
        &nbsp;
        <label for="useDefaultStructure">
            <international:get name="useDefaultStructure"/>
        </label>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureMenuButtons">
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';" id="windowApply"
               value="Apply" onclick="applyUnsavedMenuItemDetails(function(){configureMenu.save(false);});"/>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';" id="windowSave"
               value="Save" onclick="applyUnsavedMenuItemDetails(function(){configureMenu.save(true);});"/>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';"
               value="Cancel" id="windowCancel"
               onclick="applyUnsavedMenuItemDetails(closeConfigureWidgetDivWithConfirm);"/>
    </div>
</div>

