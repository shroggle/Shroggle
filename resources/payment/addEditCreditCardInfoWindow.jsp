<%@ page import="com.shroggle.presentation.site.payment.AddEditCreditCardService" %>
<%@ page import="com.shroggle.presentation.site.payment.AddEditCreditCardData" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="addEditCreditCardInfo"/>
<%
    AddEditCreditCardService service = (AddEditCreditCardService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <h1>
        <international:get name="HEADER"/>
    </h1>

    <div class="emptyError" id="addEditCreditCardErrors">&nbsp;</div>
    <% request.setAttribute("addEditCreditCardData", new AddEditCreditCardData(service.getCard(), null, service.getUser(), false)); %>
    <jsp:include page="addEditCreditCardInfo.jsp" flush="true"/>
    <br>

    <div align="right">
        <input type="button" id="windowSave"
               onclick="createNewOrUpdateExistingCreditCard(<%= service.getCard() != null %>, <%= service.getCard() != null ? service.getCard().getCreditCardId() : -1 %>);"
               value="Save" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" onclick="closeConfigureWidgetDivWithConfirm();" value="Cancel" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>

</div>

<input type="hidden" id="updateTable" value="true">