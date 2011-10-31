<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="updatePaymentInfo"/>
<% final String paymentAmount = (String) request.getAttribute("paymentAmount"); %>
<% final int siteId = (Integer) request.getAttribute("siteId"); %>
<div>
    <div style="font-weight: bold; margin-bottom: 10px;">
        <international:get name="paymentDone">
            <international:param value="<%= paymentAmount %>"/>
        </international:get>
    </div>

    <input type="checkbox" id="publishAllPages" checked="checked">
    <label for="publishAllPages">
        &nbsp;<international:get name="publishAllPages"/>
    </label>
    <br>
    <br>
    <international:get name="paymentDoneExplan"/>
    <br>
    <br>

    <div style="margin-top: 10px;">
        <input type="button" class="but_w200" onmouseover="this.className = 'but_w200_Over'"
               onmouseout="this.className = 'but_w200'" value="<international:get name="returnToSiteEditPage"/>"
               onclick="returnToSiteEditPage(<%= siteId %>)"/>
    </div>
</div>