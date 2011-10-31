<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.exception.NotUniqueUserEmailException" %>
<%@ page import="com.shroggle.presentation.site.UserInfoAction" %>
<%@ page import="com.shroggle.exception.IncorrectEmailException" %>
<%@ page import="com.shroggle.exception.NullOrEmptyEmailException" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.countries.CountryManager" %>
<%@ page import="com.shroggle.entity.Country" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="userInfo"/>
<%
    UserInfoAction actionBean = (UserInfoAction) request.getAttribute("actionBean");
    final int widgetId = 0;
%>
<html>
<head>
    <title><international:get name="updateYourAccountInfo"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="../../includeHeadApplication.jsp" %>
        <input type="hidden" id="emailNotCorrect" value="<international:get name="emailNotCorrect"/>">
        <input type="hidden" id="emailNotAvalible" value="<international:get name="emailNotAvalible"/>">
        <input type="hidden" id="emailEmptyError" value="<international:get name="emailEmptyError"/>">
        <input type="hidden" id="emailNotEqualError" value="<international:get name="emailNotEqualError"/>">
        <input type="hidden" id="enterName" value="<international:get name="enterName"/>">
        <input type="hidden" id="enterTelephone" value="<international:get name="enterTelephone"/>">

        <div class="content user_info">
            <div class="box_70">
                <%@ include file="../accountMenuInclude.jsp" %>
            </div>
            <!-- start label-box -->
            <div class="box_70">
                <%if (Boolean.parseBoolean(actionBean.getContext().getRequest().getParameter("userInfoUpdated"))) {%>
                <div style="color:green;text-align:center;"><international:get name="changesSaved"/></div>
                <%} else if (Boolean.parseBoolean(actionBean.getContext().getRequest().getParameter("emailUpdated"))) { %>
                <div style="color:green;text-align:center;"><international:get name="emailUpdated"/></div>
                <%} else {%>
                <stripes:form beanclass="com.shroggle.presentation.site.UserInfoAction"
                              onsubmit="return checkFormForUserInfo()">
                    <page:errors/>
                    <div class="span-9">
                        <dl>
                            <dt><label for="firstName"><international:get name="firstName"/></label></dt>
                            <dd><input type="text" name="request.firstName" id="firstName" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getFirstName() != null)%><%=actionBean.getLoginUser().getFirstName()%>"/>
                            </dd>
                            <dt><label for="lastName"><international:get name="lastName"/></label></dt>
                            <dd><input type="text" name="request.lastName" id="lastName" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getLastName() != null)%><%=actionBean.getLoginUser().getLastName()%>"/>
                            </dd>
                        </dl>
                        <div id="accountEmailCheckResult" class="email" style="padding:5px 0 0 0;">&nbsp;</div>
                        <dl>
                            <dt><label for="accountEmail"><international:get name="emailAddress"/></label></dt>
                            <dd><input type="text" name="request.email" id="accountEmail" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getEmail() != null)%><%=actionBean.getLoginUser().getEmail()%>"
                                       onblur="checkAccountEmailForUserInfo(<%=actionBean.getLoginUser().getUserId()%>);"/>
                            </dd>
                            <dt><label for="accountEmailConfirm"><international:get
                                    name="retypeEmailAddress"/></label><input type="text" maxlength="255"
                                                                              name="request.retypeEmailAddress"
                                                                              id="accountEmailConfirm"
                                                                              value="<%if (actionBean.getLoginUser().getEmail() != null)%><%=actionBean.getLoginUser().getEmail()%>"
                                                                              onblur="checkEqualsEmailAndConfirm();"/>
                            </dt>
                            <dd>&nbsp;</dd>
                        </dl>
                        <div id="checkAccountEmailResult" class="email"
                             style="color: red; font-weight: bold;padding:0 0 5px 0;">&nbsp;</div>

                        <dl>
                            <dt><label for="telephone"><international:get name="telephone"/></label></dt>
                            <dd><input type="text" name="request.telephone" id="telephone" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getTelephone() != null)%><%=actionBean.getLoginUser().getTelephone()%>"/>
                            </dd>
                            <dt><label for="telephone"><international:get name="telephone2"/></label></dt>
                            <dd><input type="text" name="request.telephone2" id="telephone2" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getTelephone2() != null)%><%=actionBean.getLoginUser().getTelephone2()%>"/>
                            </dd>
                            <dt><label for="telephone"><international:get name="faxNumber"/></label></dt>
                            <dd><input type="text" name="request.fax" id="fax" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getFax() != null)%><%=actionBean.getLoginUser().getFax()%>"/>
                            </dd>
                        </dl>
                    </div>
                    <div class="span-7_1" style="float:right !important; margin-right:0;">
                        <dl>
                            <dt><label for="street"><international:get name="street"/></label></dt>
                            <dd><input type="text" name="request.street" id="street" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getStreet() != null)%><%=actionBean.getLoginUser().getStreet()%>"/>
                            </dd>
                            <dt><label for="unitNumber"><international:get name="unit"/> #:</label></dt>
                            <dd><input type="text" name="request.unitNumber" id="unitNumber" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getUnitNumber() != null)%><%=actionBean.getLoginUser().getUnitNumber()%>"/>
                            </dd>
                            <dt><label for="city"><international:get name="city"/></label></dt>
                            <dd><input type="text" name="request.city" id="city" maxlength="255"
                                       value="<%if (actionBean.getLoginUser().getCity() != null)%><%=actionBean.getLoginUser().getCity()%>"/>
                            </dd>
                            <dt><label for="country"><international:get name="country"/></label></dt>
                            <dd><stripes:select id="country" name="request.country"
                                                onchange="selectCountryForUserInfo('<%= actionBean.getRequest().getRegion() %>')">
                                <stripes:option value="null"><international:get
                                        name="selectCountry"/></stripes:option>
                                <% for (Country country : Country.values()) { %>
                                <option value="<%= country.toString() %>"
                                        <% if (country == actionBean.getLoginUser().getCountry()) { %>
                                        selected <% } %>>
                                    <%= CountryManager.getCountryValue(country) %>
                                </option>
                                <% } %>
                            </stripes:select></dd>
                            <dt><label for="state<%= widgetId %>"><international:get
                                    name="regionState"/></label></dt>
                            <dd id="statesHolder<%= widgetId %>">
                                <%
                                    request.setAttribute("widgetId", widgetId);
                                    request.setAttribute("states", new CountryManager(actionBean.getLoginUser().getCountry()).getStatesByCountry());
                                    request.setAttribute("state", StringUtil.getEmptyOrString(actionBean.getLoginUser().getRegion()));
                                %>
                                <jsp:include page="../states.jsp" flush="true"/>
                            </dd>
                            <dt><label for="postalCode"><international:get name="postalCode"/></label></dt>
                            <dd>
                                <input type="text" name="request.postalCode" id="postalCode" maxlength="255"
                                       onKeyPress='return checkPostCharactersByCountry(this, $("#country").val(), event);'
                                       value="<%= StringUtil.getEmptyOrString(actionBean.getLoginUser().getPostalCode()) %>"/>
                            </dd>
                        </dl>
                    </div>
                    <br clear="all">
                    <br>

                    <div class="buttons_box">
                            <stripes:submit name="update" value="Save"
                                            id="updateUserInfoButton" onmouseout="this.className='but_w73'"
                                            onmouseover="this.className='but_w73_Over'" class="but_w73"/>
                            <input type="reset" name="reset" value="Cancel"
                                   onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';"
                                   class="but_w73" onclick="delayedSelectCountry();"/>
                        </div>
                    &nbsp;
                </stripes:form>
                <%}%>
            </div>
            <br>
        </div>
        <%@ include file="../../includeFooterApplication.jsp" %>
    </div>
</div>
<input type="hidden" id="widgetId" value="<%= widgetId %>">
</body>
</html>