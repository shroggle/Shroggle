<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="incomeSettings"/>
<%
    final String paypalEmail = (String) request.getAttribute("paypalEmail");
    final Integer siteId = (Integer) request.getAttribute("siteId");
%>
<div class="windowOneColumn">
    <h1><international:get name="header"/></h1><br><br>
    
    <input type="hidden" id="emailAddressIsMalformed" value="<international:get name="emailAddressIsMalformed"/>">
    <input type="hidden" id="emailAddressIsRequired" value="<international:get name="emailAddressIsRequired"/>">
    <input type="hidden" id="thereIsNoPaypalAccount" value="<international:get name="thereIsNoPaypalAccount"/>">

    <div class="emptyError" id="errors"></div>

    <div class="inform_mark"><international:get name="info"/></div>
    <br>

    <div class="warning"><international:get name="warning"/></div>
    <br>

    <label for="paypalText"><international:get name="paypalHeader"/></label>

    <input type="text" id="paypalText" value="<%=paypalEmail%>" maxlength="255">
    <br><br>

    <div align="right">
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';" id="windowSave"
               value="Save" onclick="setReceivePayments('<%=siteId%>');"/>
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';" id="windowCancel"
               value="Close" onclick="closeConfigureWidgetDiv();"/>

    </div>
</div>