<%@ page import="com.shroggle.presentation.site.WidgetRegistrationAction" %>
<%@ page import="com.shroggle.logic.form.FormManager" %>
<%@ page import="com.shroggle.entity.ItemType" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%
    final WidgetRegistrationAction action = (WidgetRegistrationAction) request.getAttribute("actionBean");

    final boolean disablePasswordField = (action.getLoginedVisitor() != null && action.isEditVisitorDetails())
            || action.isFillOutFormCompletely() || request.getParameter("filledFormToUpdateId") != null;
    final boolean disableEmailField = (action.getLoginedVisitor() != null && (action.isEditVisitorDetails() || action.isShowForInvited()))
            || action.isFillOutFormCompletely() || request.getParameter("filledFormToUpdateId") != null;

    request.setAttribute("disableEmailField", disableEmailField);
    request.setAttribute("disablePasswordField", disablePasswordField);

    Integer registrationUserId = request.getAttribute("registrationUserId") != null ? (Integer) request.getAttribute("registrationUserId") :
            (request.getParameter("registrationUserId") != null ? Integer.parseInt(request.getParameter("registrationUserId")) : null);
    if (registrationUserId == null && action.isEditVisitorDetails()) {
        registrationUserId = action.getLoginedVisitor() != null ? action.getLoginedVisitor().getUserId() : null;
    }

    final int pageBreaksToPass = request.getParameter("pageBreaksToPass") != null ? Integer.parseInt(request.getParameter("pageBreaksToPass")) : 0;
    final int totalPageBreaks = FormManager.getTotalPageBreaks(action.getFormData());
    final boolean cameFromReset = request.getParameter("cameFromReset") != null && Boolean.parseBoolean(request.getParameter("cameFromReset"));

    final boolean showManageVisitorPage = action.getLoginedVisitor() != null && action.isFilledInThisSession()
            && !action.isEditVisitorDetails() && !action.isShowForInvited() && !action.isShowFromAddRecord() && pageBreaksToPass == 0
            && request.getParameter("filledFormToUpdateId") == null && !cameFromReset;
%>
<international:part part="registration"/>

<% if (showManageVisitorPage) { %>
<div id="registrationBlock<%= action.getWidgetId() %>">
    <international:get name="welcome"/>&nbsp;<%= action.getLoginedVisitor().getEmail() %>.
    <% if (action.isShowAfterEditingMessage()) { %>
    <international:get name="thankEdit"/>
    <% } else { %>
    <international:get name="thankReg"/>
    <% } %>
    <br>
    <a href="javascript:editVisitorDetails(<%= action.getWidgetId() %>)"><international:get name="editDetails"/></a>
    <br>
    <a href="javascript:logout()"><international:get name="signOff"/></a>
    <br>
    <a href="javascript:unsubscribeVisitor(<%= action.getLoginedVisitor().getUserId() %>, <%= action.getSiteId() %>, '<%= action.getSiteName() %>')"><international:get
            name="unsubscribe"/></a>&nbsp;<international:get name="unsubscribeComment"/>
</div>
<% } else { %>
<div id="registrationBlock<%= action.getWidgetId() %>" class="widgetRegistrationBlock">
    <input id="registrationErrorsEmptyCode<%= action.getWidgetId() %>" type="hidden"
           value="<international:get name="emptyCode"/>">
    <input id="editDetails<%= action.getWidgetId() %>" type="hidden"
           value="<%=  (action.isEditVisitorDetails()) ? "true" : "false" %>"/>
    <input id="fillOutFormCompletely<%= action.getWidgetId() %>" type="hidden"
           value="<%=  (action.isFillOutFormCompletely()) ? "true" : "false" %>"/>
    <input id="inviteForSiteId" type="hidden" value="<%= action.isShowForInvited() ? action.getSiteId() : null %>"/>
    <input id="invitedRegisteredText<%= action.getWidgetId() %>" type="hidden"
           value="<international:get name="invitedRegistered"/>"/>
    <input id="invitedSiteName<%= action.getWidgetId() %>" type="hidden"
           value="<%= action.getSiteName() %>"/>
    <input type="hidden" id="registrationUserId<%= action.getWidgetId() %>" value="<%= registrationUserId %>">
    <input type="hidden" id="agreeWithTermsWithConditionsException<%= action.getWidgetId() %>"
           value="<international:get name="agreeWithTermsWithConditionsException"/>">

    <% if (Boolean.parseBoolean(request.getParameter("showSpecificFormRegisterMessage"))) { %>
    <div class="registrationSpecificFormMessage">
        <international:get name="specificFormMessage"/>
    </div>
    <% } %>

    <% if (action.isFillOutFormCompletely()) { %>
    <div class="registrationHeader">
        <international:get name="fillForm"/>
    </div>
    <% } %>

    <% if (action.isShowFromAddRecord()) { %>
    <div id="registrationErrorBlock<%= action.getWidgetId() %>"></div>
    <% } %>

    <% if (action.getFormData().getDescription() != null && action.getFormData().isShowDescription()) { %>
    <div class="registrationHeader"
         id="formDescription<%= action.getWidgetId() %>"><%= action.getFormData().getDescription() %>
    </div>
    <% } %>

    <table class="registrationTable form<%= action.getWidgetId() %>" style="table-layout:fixed;">
        <tr>
            <td colspan="2">
                <international:get name="enterRegistrationData"/>
            </td>
        </tr>
        <% if (!action.isReturnToLogin() && !action.isReturnToForum() && action.getLoginedVisitor() == null && !action.isShowFromAddRecord() && pageBreaksToPass == 0) { %>
        <tr id="alreadyHaveAccTr">
            <td colspan="2">
                <a href="javascript:showLoginLink(<%= action.getWidgetId() %>);"
                   class="showLoginLink"><international:get
                        name="alreadyHaveAccount"/></a>
            </td>
        </tr>
        <% } %>
        <jsp:include page="widgetForm.jsp" flush="true"/>
        <% if (!action.isShowFromAddRecord() && pageBreaksToPass == 0 && !action.isEditVisitorDetails()) { %>
        <tr>
            <td colspan="2">
                <div id="securityCodeText"><international:get name="securityCode"/></div>
            </td>
        </tr>
        <tr class="trWithVerificationWord">
            <td>
                <label><img
                        src="/noBotImage.action?noBotPrefix=registration<%= action.getWidgetId() %>&noCache=<%=  Math.random()  %>"
                        alt="Verification code" class="bot_code" id="noBotImage<%= action.getWidgetId() %>"></label>
            </td>
            <td>
                <input type="text" class="formTextInput" id="registrationVerificationCode<%= action.getWidgetId() %>"
                       maxlength="255">
            </td>
        </tr>
        <% } %>
        <% if (action.getFormData().isRequireTermsAndConditions()) { %>
        <tr>
            <td colspan="2">
                <div class="registrationTermsAndConditionsBlock">
                    <input type="checkbox" id="agreeWithTermsAndConditions<%= action.getWidgetId() %>"><label
                        for="agreeWithTermsAndConditions<%= action.getWidgetId() %>"><international:get
                        name="agree"/></label>
                    <a href="javascript:showRegistrationPreviewTermsAndConditions()"><international:get
                            name="termsAndConditionsPreviewLink"/></a>

                    <div id="termsAndConditionsDiv" style="display:none;">
                        <div class="windowOneColumn">
                            <div style="max-height:500px;overflow:auto;">
                                <%= action.getFormData().getTermsAndConditions() %>
                            </div>
                            <div align="right">
                                <input value="<international:get name="close"/>" type="button" class="but_w73"
                                       onclick="closeConfigureWidgetDiv();"
                                       onmouseover="this.className = 'but_w73_Over';"
                                       onmouseout="this.className = 'but_w73';"/>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <% } %>
        <tr>
            <td class="registrationSubmitBlockTd" colspan="2">
                <div id="registrationSubmitBlock" <% if (action.isShowFromAddRecord()) { %>style="display:none;"<% } %>>
                    <% if (!action.isShowFromAddRecord()) { %>
                    <div class="formsErrorBlock<%= action.getWidgetId() %>"
                         id="registrationErrorBlock<%= action.getWidgetId() %>"></div>
                    <% } %>

                    <div class="registrationButtons">
                        <% if (pageBreaksToPass != 0) { %>
                        <input value="<international:get name="back"/>" class="formBackButton"
                               id="back<%= action.getWidgetId() %>" type="button"
                               onclick="goBackOnForms(<%= action.getWidgetId() %>, <%= action.getRegistrationForm().getFormId() %>, <%= action.isShowFromAddRecord() %>, '<%= ItemType.REGISTRATION %>');"/>
                        <% } %>
                        <input <% if (pageBreaksToPass == totalPageBreaks) { %>value="<international:get name="signIn"/>"
                               <% } else { %>value="<international:get name="next"/>"<% } %>
                               id="submit<%= action.getWidgetId() %>" type="button"
                               onclick="createVisitor(<%= action.getWidgetId() %>, <%= action.isShowFromAddRecord() %>);"/>
                        <input value="<international:get name="reset"/>" type="button"
                               id="reset<%= action.getWidgetId() %>"
                               onclick="resetForm(<%= action.getWidgetId() %>, <%= action.getRegistrationForm().getFormId() %>, <%= action.isShowFromAddRecord() %>, '<%= ItemType.REGISTRATION %>');"/>

                        <div id="formsLoadingMessageDiv<%= action.getWidgetId() %>" style="display:none;">
                            <img alt="Loading text editor..." src="/images/ajax-loader.gif"
                                 style="vertical-align:middle;padding: 0 3px 0 0"/>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <% final String registrationOnLoad = "makeErrorBlockWidthLikeTable(" + action.getWidgetId() + ");"; %>
    <elementWithOnload:element onload="<%= registrationOnLoad %>"/>

    <% if (action.isReturnToLogin()) { %>
    <a href="javascript:returnToLoginLink(<%= action.getWidgetId() %>);" id="returnToLoginLink"><international:get
            name="returnToLogin"/></a>
    <% } else if (action.isReturnToForum()) { %>
    <a href="javascript:showPreviousBlockForRegistration(<%= action.getWidgetId() %>);"
       id="returnToForumLink"><international:get
            name="goBackToForum"/></a>
    <% } %>
</div>
<% } %>
