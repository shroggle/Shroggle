<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ page import="com.shroggle.presentation.site.borderBackground.*" %>
<%@ page import="com.shroggle.entity.Background" %>
<%@ page import="com.shroggle.logic.borderBackground.BorderLogic" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<international:part part="configureBackground"/>
<%
    final ConfigureBackgroundService service = (ConfigureBackgroundService) request.getAttribute("backgroundService");
    final ConfigureItemSettingsService itemSettingsService = request.getAttribute("itemSettingsService") != null ?
            (ConfigureItemSettingsService) request.getAttribute("itemSettingsService") : null;
    final boolean showForPage = service.isShowForPage();
    final Integer itemId = service.getItemId();
    final Integer draftItemId = service.getDraftItemId();

    boolean isWidgetComposit = service.getWidget() != null && service.getWidget().isWidgetComposit();
%>
<input type="hidden" id="applyToAllPagesText" value="<international:get name="allPagesText"/>">
<input type="hidden" id="IMAGE_WIDTH" value="<%= BorderLogic.WIDTH %>">
<input type="hidden" id="IMAGE_HEIGHT" value="<%= BorderLogic.HEIGHT %>">
<input type="hidden" id="backgroundId" value="<%= service.getDraftBackground().getId() %>">

<div <% if (itemSettingsService != null && itemSettingsService.getItemType().isExtendedSettingsWindow()) { %>
        class="extendedItemSettingsWindowDiv"
        <% } else { %>
        class="itemSettingsWindowDiv"
        <% } %>>

    <h1>
        <international:get name="borderBackground">
        <% if (service.getWidget() != null && service.getWidget().getItemType() != null) { %>
            <international:param>
                <jsp:attribute name="value" >
                     <international:get name="<%= String.valueOf(service.getWidget().getItemType()) %>" part="addWidget" />
                </jsp:attribute>
            </international:param>
        <% } else { %>
            <international:param value="<%= '\b' %>"/>
        <% } %>
        </international:get>
    </h1>

    <% if (!showForPage && itemId != null) { %>
    <widget:title customServiceName="backgroundService"/>
    <% } else if (showForPage) { %>
    <page:title>
        <jsp:attribute name="customServiceName">backgroundService</jsp:attribute>
    </page:title>
    <% } %>

    <div class="windowTopLine">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="backgroundReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <%
        final Background selectedBackground = service.getDraftBackground();
        final boolean backgroundExist = selectedBackground.getId() > 0;
        final int selectedBackgroundImage = selectedBackground.getBackgroundImageId();
    %>
    <input type="hidden" id="selectedColorId"
           <% if(backgroundExist && selectedBackground.getBackgroundColor() != null) { %>value="<%= selectedBackground.getBackgroundColor() %>"
           <% } else { %>value=""<% } %>>
    <input type="hidden" id="colorPickerCreated">
    <input type="hidden" value="<international:get name="emptyFile"/>" id="emptyFile">
    <input type="hidden" value="<%= selectedBackgroundImage %>" id="selectedBackgroundImageId">
    <input type="hidden" value="<%= service.getSiteId() %>" id="siteId">

    <div class="inform_mark"><international:get name="selectColorOrImageText"/></div>
    <fieldset>
        <b><international:get name="applyBackgroundColor"/></b>
        <br>
        <dl class="w_20">
            <dt><label for="color"><international:get name="color"/></label></dt>
            <dd>
                <div id="backgroundColorPicker" align="left" style="float:left;display:inline;">
                    <% final String backgroundColor = StringUtil.isNullOrEmpty(selectedBackground.getBackgroundColor()) ||
                            selectedBackground.getBackgroundColor().equals("transaparent") ? "" : selectedBackground.getBackgroundColor(); %>
                    <% final String colorPickerOnload = "configureBackground.showColorPicker('" + backgroundColor + "');"; %>
                    <elementWithOnload:element onload="<%= colorPickerOnload %>"/>
                </div>
            </dd>
        </dl>
    </fieldset>
    <fieldset>
        <b><international:get name="applyBackgroundImage"/></b> <br><br>
        <international:get name="selectUploadedImageText"/> <br><br>

        <div class="span-13">
            <div id="uploadedBackgroundImages"
                 style="overflow-y: auto; overflow-x: auto; height: 210px; border:1px solid #C5C2C2; background: white;">
                <jsp:include page="uploadedBackgroundImages.jsp" flush="true"/>
            </div>
        </div>
        <div class="span-6">
            <b><international:get name="imageDisplayTreatment"/></b>
            <a href="javascript:showImageTreatmentMoreInfo();"><international:get name="moreInfo"/></a><br><br>

            <div id="imageTreatmentMoreInfo" style="display:none;">
                <div class="windowOneColumn">
                    <international:get name="usingBacgroundImageAloneOrWithColorText"/>

                    <div align="right">
                        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                               onmouseout="this.className='but_w73';"
                               value="Close" onclick="closeConfigureWidgetDiv();"/>
                    </div>
                </div>
            </div>
            <br><br>
            <input type="radio" id="tileImageRadio" name="imageDisplayTreatmentRadio"
                   onclick="document.getElementById('alignSelect').disabled = 'true';"
                <% if (selectedBackground == null || selectedBackground.getBackgroundRepeat().equals("repeat")) { %>
                   checked <% } %> >
            <label for="tileImageRadio"><international:get name="tileImage"/></label><br>
            <input type="radio" id="align" name="imageDisplayTreatmentRadio"
                   onclick="document.getElementById('alignSelect').disabled = '';"
                   <% if (backgroundExist && selectedBackground.getBackgroundRepeat().equals("no-repeat")) { %>checked<% } %>>
            <label for="align"><international:get name="align"/></label>&nbsp;&nbsp;
            <select id="alignSelect" name="align"
                    <%if(selectedBackground == null || selectedBackground.getBackgroundRepeat().equals("repeat")){%>disabled="true"<%}%> >
                <% int backgroundCount = 0;
                    final String[] backgroundPositions = {"top left", "top center", "top right",
                            "center left", "center center", "center right",
                            "bottom left", "bottom center", "bottom right"};
                    for (String position : backgroundPositions) { %>
                <option value="<%= position %>"
                        <% if (backgroundExist && selectedBackground.getBackgroundPosition().equals(position) || backgroundCount == 0) { %>
                        selected <% } %> >
                    <% final String replacedPosition = position.replace(" ", ""); %>
                    <international:get name="<%= replacedPosition %>"/>
                    <% backgroundCount++;
                    } %>
                </option>
            </select>
        </div>
        <br clear="all"><br>
        <international:get name="selectAndUploadImageText"/> <br>

        <div style="margin-bottom:10px;height:25px;">
            <input type="button" value="<international:get name="browseAndUpload"/>"
                   id="browseAndUploadBackgroundButton"
                   class="but_w170_misc">
            <span id="backgroundImageButtonContainer"
                  style="margin-right:5px;position:relative;top:0;left:-170px;cursor: pointer;"
                  onmouseout="$('#browseAndUploadBackgroundButton')[0].className='but_w170_misc';"
                  onmouseover="$('#browseAndUploadBackgroundButton')[0].className='but_w170_Over_misc';">
                <span id="backgroundImageButtonPlaceHolder">

                </span>
            </span>
        </div>
    </fieldset>
</div>

<div class="itemSettingsButtonsDiv" id="configureBackgroundButtons">
    <div <% if (isWidgetComposit) { %>class="itemSettingsButtonsDivInner"<% } %>>
        <div align="right" class="forItemDiv" id="forItemDiv">
            <% if (showForPage) { %>
            <input type="checkbox" id="applyToAllPages">
            <label for="applyToAllPages"><international:get name="applyThisToAllMySitePages"/></label>
            <% } else if (itemId != null && !isWidgetComposit) { %>
            <input type="checkbox" id="saveBackgroundInCurrentPlace"
                   <% if (service.isSavedInCurrentPlace()) { %>checked<% } %>/>
            <label for="saveBackgroundInCurrentPlace"><international:get name="forItem"/></label>
            <% } %>
        </div>

        <div class="fr">
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" id="windowApply"
                   value="Apply"
                   onclick="configureBackground.save(<%= itemId %>, <%= draftItemId %>, <%= showForPage %>, false, <%= service.getSiteId() %>);"/>
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" id="windowSave"
                   value="Save"
                   onclick="configureBackground.save(<%= itemId %>, <%= draftItemId %>, <%= showForPage %>, true, <%= service.getSiteId() %>);"/>
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" id="windowCancel"
                   value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();"/>
        </div>

        <div style="clear:both;"></div>
    </div>
</div>
