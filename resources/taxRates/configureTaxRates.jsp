<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.logic.site.taxRates.TaxRateUSManager" %>
<%@ page import="com.shroggle.logic.site.taxRates.TaxRatesUSManager" %>
<%@ page import="com.shroggle.presentation.taxRates.ConfigureTaxRatesService" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="onloadElement" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureTaxRates"/>
<%--
    @author Balakirev Anatoliy
--%>
<% final ConfigureTaxRatesService service = (ConfigureTaxRatesService) request.getAttribute("service"); %>
<% final TaxRatesUSManager manager = new TaxRatesUSManager(service.getDraftTaxRates()); %>
<input type="hidden" id="siteOnItemRightType" value="<%= service.getSiteOnItemRightType() %>"/>
<input type="hidden" id="itemId" value="<%= manager.getId() %>"/>
<input type="hidden" id="nameNotUnique" value="<international:get name="nameNotUnique"/>"/>

<div class="windowOneColumn">
    <h1><international:get name="taxRatesSettings"/></h1>
    <% if (service.getWidgetTitle() != null) { %><widget:title/><% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="errors"></div>

    <div style="float:left; margin-right:10px;">
        <label for="newTaxRatesName">
            <international:get name="taxRatesItemName"/>
        </label>&nbsp;
    </div>
    <div style="float:left;">
        <input type="text" id="newTaxRatesName" class="title" maxlength="255" value="<%= manager.getName() %>">
    </div>
    <div style="clear:both;margin-bottom:15px;"></div>
    <div class="inform_mark" style="margin-left:0"><international:get name="selectStatesAndAndModifyTaxRates"/></div>
    <table class="tbl_blog" style="width:100%;margin-top:10px;">
        <thead id="taxRatesHolderHead">
        <tr>
            <td style="width:140px;">
                <international:get name="StateofOperation"/>                
            </td>
            <td style="width:50px;">
                <international:get name="USPS"/>
            </td>
            <td style="width:140px;">
                <international:get name="stateName"/>
            </td>
            <td>
                <international:get name="stateTaxRate"/>
            </td>
        </tr>
        </thead>
    </table>
    <div class="fixedTableHead" style="height:500px; overflow-y:auto;overflow-x:hidden;margin-bottom:15px;">
        <table class="tbl_blog" style="width:100%;">
            <tbody id="taxRatesHolderBody">
            <% for (TaxRateUSManager taxRateManager : manager.getTaxRates()) {
                final String checkboxId = ("include" + taxRateManager.getState().toString()); %>
            <tr stateName="<%= taxRateManager.getState().toString() %>">
                <td style="width:140px;text-align:center;">
                    <input type="checkbox" id="<%= checkboxId %>"
                           <% if (taxRateManager.isIncluded()) { %>checked<% } %>
                           onchange="configureTaxRates.disableTaxRateField(this);">
                </td>
                <td style="width:50px;text-align:center;">
                    <label for="<%= checkboxId %>">
                        <%= taxRateManager.getState().toString() %>
                    </label>
                </td>
                <td style="width:140px;text-align:center;">
                    <label for="<%= checkboxId %>">
                        <%= taxRateManager.getStateName() %>
                    </label>
                </td>
                <td style="text-align:center;">
                    <input type="text" id="taxRate<%= taxRateManager.getState().toString() %>"
                           value="<%= taxRateManager.getTaxRateAsString() %>"
                           onkeypress="return numbersAndOneDotOnly(this, event);"
                           <% if (!taxRateManager.isIncluded()) { %>disabled<% } %> >
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
    <%---------------------------------------------------buttons----------------------------------------------------------%>
    <div id="blogSummarySettingsButtons" align="right">
        <input type="button" value="<international:get name="save"/>"
               onClick="configureTaxRates.save({widgetId : null, itemId : null});"
               id="windowSave"
               class="but_w73" onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';">
        <input type="button" value="<international:get name="cancel"/>" onClick="closeConfigureWidgetDivWithConfirm();"
               id="windowCancel"
               class="but_w73" onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';">
    </div>
    <%---------------------------------------------------buttons----------------------------------------------------------%>
</div>