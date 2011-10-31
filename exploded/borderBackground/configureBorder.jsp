<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.presentation.site.borderBackground.*" %>
<%@ page import="com.shroggle.logic.borderBackground.BorderLogic" %>
<%@ page import="com.shroggle.entity.Border" %>
<%@ page import="com.shroggle.entity.StyleSelectType" %>
<%@ page import="com.shroggle.presentation.site.ConfigureItemSettingsService" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureBorder"/>
<%
    final ConfigureBorderService service = (ConfigureBorderService) request.getAttribute("borderService");
    final ConfigureItemSettingsService itemSettingsService = request.getAttribute("itemSettingsService") != null ?
            (ConfigureItemSettingsService) request.getAttribute("itemSettingsService") : null;
    final Integer itemId = service.getItemId();
    final Integer draftItemId = service.getDraftItemId();

    boolean isWidgetComposit = service.getWidget() != null && service.getWidget().isWidgetComposit();
%>
<input type="hidden" id="applyToAllPagesText" value="<international:get name="allPagesText"/>">
<input type="hidden" id="BORDER_NAME_EMPTY" value="<international:get name="BORDER_NAME_EMPTY"/>">
<input type="hidden" id="BORDER_NAME_NOT_UNIQUE" value="<international:get name="BORDER_NAME_NOT_UNIQUE"/>">
<input type="hidden" id="SELECT_EXISTING_BORDER" value="<international:get name="SELECT_EXISTING_BORDER"/>">
<input type="hidden" id="IMAGE_WIDTH" value="<%= BorderLogic.WIDTH %>">
<input type="hidden" id="IMAGE_HEIGHT" value="<%= BorderLogic.HEIGHT %>">
<input type="hidden" id="borderId" value="<%= service.getDraftBorder().getId() %>">

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

    <% if (itemId != null) { %>
    <widget:title customServiceName="borderService"/>
    <% } %>

    <div class="windowTopLine">&nbsp;</div>

    <div class="readOnlyWarning" style="display:none;" id="borderReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <%
        final Border border = service.getDraftBorder();
        request.setAttribute("styleSelectType", StyleSelectType.TEXT_FIELD);
        request.setAttribute("onChangeFunction", "borderStyleChanged");
        String id;
    %>
    <div style="width:750px; height:440px;">
        <div class="span-8" style="width:50%;margin-left:20px;float:left;">

            <div class="warning" style="display:none;color:red" id="borderPaddingWarning">
                    <international:get name="negativePaddingWarning"/>
            </div>
            <%---------------------------------------------------padding--------------------------------------------------%>
            <%
                id = "borderPadding";
                request.setAttribute("id", id);
                request.setAttribute(id, border.getBorderPadding());
            %>
            <jsp:include page="../style/styleInputFields.jsp" flush="true"/>
            <%---------------------------------------------------padding--------------------------------------------------%>

            <%----------------------------------------------------width---------------------------------------------------%>
            <%
                id = "borderWidth";
                request.setAttribute("id", id);
                request.setAttribute(id, border.getBorderWidth());
            %>
            <jsp:include page="../style/styleInputFields.jsp" flush="true"/>
            <%----------------------------------------------------width---------------------------------------------------%>

            <%----------------------------------------------------style---------------------------------------------------%>
            <%
                id = "borderStyle";
                request.setAttribute("id", id);
                request.setAttribute(id, border.getBorderStyle());
                request.setAttribute("styleSelectType", StyleSelectType.SELECT);
            %>
            <jsp:include page="../style/styleInputFields.jsp" flush="true"/>
            <%----------------------------------------------------style---------------------------------------------------%>

            <%----------------------------------------------------color---------------------------------------------------%>
            <%
                id = "borderColor";
                request.setAttribute("id", id);
                request.setAttribute(id, border.getBorderColor());
                request.setAttribute("styleSelectType", StyleSelectType.COLOR);
            %>
            <jsp:include page="../style/styleInputFields.jsp" flush="true"/>
            <% request.setAttribute("styleSelectType", StyleSelectType.TEXT_FIELD); %>
            <%----------------------------------------------------color---------------------------------------------------%>

            <%---------------------------------------------------margin---------------------------------------------------%>
            <%
                id = "borderMargin";
                request.setAttribute("id", id);
                request.setAttribute(id, border.getBorderMargin());
            %>
            <jsp:include page="../style/styleInputFields.jsp" flush="true"/>
            <%---------------------------------------------------margin---------------------------------------------------%>

        </div>

        <div style="width:40%;float:left;">
            <style type="text/css" id="borderSampleImageStyle">
                <% if(!service.getPreviewImageStyle().trim().isEmpty()) { %>
                #borderSampleImage {
                <%= service.getPreviewImageStyle() %>
                }

                <% } %>
            </style>
            <div style="border:1px solid #c3daf9; width:300px; height:240px;margin-top:50px;" id="imageBorderDiv">
                <img id="borderSampleImage" width="300" height="240"
                     src="/images/borderSample.jpg" alt="<international:get name="borderSample"/>">
            </div>
            <div style="margin:30px 20px 10px 0;text-align:center;">
                <img style="height:100px" src="/images/cssDefinition.jpg"
                     alt="<international:get name="cssDefinition"/>">
            </div>
        </div>
    </div>
</div>

<div class="itemSettingsButtonsDiv" id="configureBorderButtons">
    <div <% if (isWidgetComposit) { %>class="itemSettingsButtonsDivInner"<% } %>>
        <div align="right" class="forItemDiv" id="forItemDiv">
            <% if (itemId != null && !isWidgetComposit) { %>
            <input type="checkbox" id="saveBorderInCurrentPlace"
                   <% if (service.isSavedInCurrentPlace()) { %>checked<% } %>/>
            <label for="saveBorderInCurrentPlace"><international:get name="forItem"/></label>
            <% } %>
        </div>

        <div class="fr">
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" id="windowApply"
                   value="Apply"
                   onclick="submitBorderSettings(<%= itemId %>, <%= draftItemId %>, false, <%= service.getSiteId() %>);"/>
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" id="windowSave"
                   value="Save"
                   onclick="submitBorderSettings(<%= itemId %>, <%= draftItemId %>, true, <%= service.getSiteId() %>);"/>
            <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" id="windowCancel"
                   value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();"/>
        </div>

        <div style="clear:both;"></div>
    </div>
</div>
