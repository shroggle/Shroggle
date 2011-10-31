<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.exception.IncorrectEmailException" %>
<%@ page import="com.shroggle.exception.NotUniqueUserEmailException" %>
<%@ page import="com.shroggle.exception.NullOrEmptyEmailException" %>
<%@ page import="net.sourceforge.stripes.validation.ValidationErrors" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page import="com.shroggle.presentation.user.create.CreateUserMode" %>
<%@ page import="com.shroggle.presentation.user.create.CreateUserAction" %>
<%@ page import="com.shroggle.entity.UserOnSiteRight" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.entity.Country" %>
<%@ page import="com.shroggle.logic.countries.CountryManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="noBot" tagdir="/WEB-INF/tags/nobot" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%
    final CreateUserAction actionBean = (CreateUserAction) request.getAttribute("actionBean");
    final int widgetId = 0;
%>
<international:part part="createUser"/>
<html>
<head>
    <META NAME="Title"
          CONTENT="Create a site builder account to build a website, sign up to build your own website for free.">
    <META NAME="Description"
          CONTENT="Create a site builder account to create a free website using our building a website free software">
    <META NAME="Keywords" CONTENT="create a free website, build a website, building a website free software">
    <title><international:get name="createUser"/></title>
    <jsp:include page="/includeHeadPresentationResources.jsp" flush="true">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">

</head>
<body onload="init(); addValidationErrors();">
<div id="wrapper">
    <div id="container">
    <%@ include file="/includeHeadPresentation.jsp" %>
    <div id="mainContentNdi" class="clearbothNd ">
        <input type="hidden" value="<international:get name="emailNotCorrect"/>" id="emailNotCorrect"/>
        <input type="hidden" value="<international:get name="emailNotAvalible"/>" id="emailNotAvalible"/>
        <input type="hidden" value="<international:get name="emailEmptyError"/>" id="emailEmptyError"/>
        <input type="hidden" value="<international:get name="passNotEqualError"/>" id="passNotEqualError"/>
        <input type="hidden" value="<international:get name="emailNotEqualError"/>" id="emailNotEqualError"/>

        <div class="regsteps">
            <span>Step 1 of 3</span>

            <h1><international:get name="threeStepsHeader"/></h1>

            <p class="regstepsp"><international:get name="setUpAccHeader"/></p>
        </div>
        <stripes:form beanclass="com.shroggle.presentation.user.create.CreateUserAction" method="post" id="form"
                      onsubmit="return checkFormForCreateUser()">
            <stripes:hidden name="request.agree" id="agree"/>
            <div class="middle_form">
                <page:errors/>
                <% if (actionBean.getRequest().getMode().isInvited() && !actionBean.getRequest().isShowWrongUrlMessage()) { %>
                <p style="color:green">
                    <%= actionBean.getRequest().getHasAccessToSitesMessage() %>
                </p>
                <% } else if (actionBean.getRequest().getMode() == CreateUserMode.INVITED_DELETE || actionBean.getRequest().isShowWrongUrlMessage()) { %>
                <p style="color:red"><international:get name="brokenLink"/></p>
                <% } %>

                <stripes:hidden name="request.originalEmail" id="originalemail"/>
                <stripes:hidden name="request.originalEmailConfirm" id="originalemailConfirm"/>
                <stripes:hidden name="request.originalPassword" id="originalpassword"/>
                <stripes:hidden name="request.originalPasswordConfirm" id="originalpasswordConfirm"/>
                <stripes:hidden name="request.originalTelephone" id="originaltelephone"/>
                <stripes:hidden name="request.originalFirstName" id="originalfirstName"/>
                <stripes:hidden name="request.originalLastName" id="originallastName"/>
                <stripes:hidden name="request.firstShow" id="firstShow"/>
                <stripes:hidden name="request.userId" id="userId"/>
                <stripes:hidden name="request.confirmCode"/>
                <stripes:hidden name="request.invitedUserId" id="invitedUserId"/>
                <stripes:hidden name="request.mode" id="userExist"/>
                <input type="hidden" name="execute" value="true">
                <% if (actionBean.getRequest().getMode().isInvitedExist()) { %>
                <div>
                    <stripes:checkbox name="request.notWantNewUser" id="useExistingDetailsCheckbox"
                                      onclick="fillUserData(this)"/>
                    <international:get name="useExistingDetails"/>
                </div>
                <% } %>
            </div>
            <div style="padding-left:20px;">

                <table class="registrationtable">
                    <tr>
                        <td><label for="firstName"><international:get name="firstName"/></label></td>
                        <td><stripes:text name="request.firstName" class="clearOrDisable" id="firstName"/></td>
                    </tr>
                    <tr>
                        <td><label for="lastName"><international:get name="lastName"/>&nbsp;*</label></td>
                        <td><stripes:text name="request.lastName" class="clearOrDisable" id="lastName"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div id="accountEmailCheckResult" class="email" style="padding:5px 0 0 0;">&nbsp;</div>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="email"><international:get name="emailAddress"/>&nbsp;*</label></td>
                        <td><stripes:text name="request.email" id="email" class="clearOrDisable"
                                          onblur="checkAccountEmail()"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="emailConfirm"><international:get name="retypeEmailAddress"/>&nbsp;*</td>
                        <td><stripes:text name="request.emailConfirm" id="emailConfirm" class="clearOrDisable"
                                          onblur="checkEqualsEmailAndConfirm()"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div id="checkAccountEmailResult" class="email"
                                 style="color: red; font-weight: bold;padding:0 0 7px 0;">&nbsp;</div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="regstepsp hightd">
                              <span style="white-space:nowrap; overflow:visible;">
                             <strong>Please select a password.</strong> You will use this to login to create and manage your web site.
                             </span>
                        </td>
                    </tr>
                    <tr>
                        <td class="hightd"><label for="password"><international:get name="pass"/>&nbsp;*</label></td>
                        <td><stripes:password name="request.pass" id="password" class="clearOrDisable"
                                              repopulate="true"
                                              onblur="checkEqualsPasswordAndConfirm()"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="passwordConfirm"><international:get name="retypePass"/>&nbsp;*</label></td>
                        <td><stripes:password name="request.passwordConfirm" id="passwordConfirm"
                                              class="clearOrDisable"
                                              repopulate="true" onblur="checkEqualsPasswordAndConfirm()"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="hightd"><label for="telephone"><international:get name="tel1"/>&nbsp;*</label></td>
                        <td><stripes:text name="request.telephone" class="clearOrDisable" value="" id="telephone"/>
                        </td>

                    </tr>
                    <tr>
                        <td colspan="2">
                            <div id="checkAccountPasswordResult" class="email"
                                 style="color: red; font-weight: bold;padding-bottom:5px;">&nbsp;</div>

                        </td>
                    </tr>
                </table>
            </div>
            <br clear=all>

            <div class="span-11">
                <table class="registrationtable">
                    <tr>
                        <td colspan="2"><label class="enterSecCodeLabel regstepsp"><international:get
                                name="secCode"/></label></td>
                    </tr>
                    <tr>
                        <td><label><noBot:image prefix="createUser" className="bot_code"
                                                alt="Bot denied code"/></label></td>
                        <td><stripes:text name="request.noBotCodeConfirm" id="noBotCodeConfirm"
                                          class="clearOrDisable" style="margin-left:20px; margin-top:7px;"/></td>
                    </tr>
                </table>

            </div>
            <br clear=all><br>

            <div class="span-11" style="padding:15px 0 0 20px;">
                <stripes:link beanclass="com.shroggle.presentation.StartAction"><img
                        src="/images/spacer.gif" alt=""></stripes:link></div>
            <div class="span-8" style=" text-align:right;" id="createAccountBtnDiv">&nbsp;
                <stripes:submit name="execute" value="" id="startCreateAccount" class="butSubmit"
                                onmouseout="this.className='butSubmit'"
                                onmouseover="this.className='butSubmitOver'"/>
            </div>
        </stripes:form>

    </div>
    <br clear="all">

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
<div id="termsAndConditions" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto;height:300px; width:520px; padding:10px; text-align:left;font-family:'times new roman', times">
            <div class="b_16"><international:get name="termsOfUse"/></div>
            <br>
            <international:get name="welcome"/>
            <hr>
            <div class="b_16"><international:get name="copyrightAndTrademarkNotice"/></div>
            <br>
            <international:get name="thisSite"/>
            <hr>
            <div class="b_16"><international:get name="yourComments"/></div>
            <br>
            <international:get name="weAppreciate"/>
            <hr>
            <div class="b_16"><international:get name="privacy"/></div>
            <br>
            <international:get name="shroggle"/>
            <div class="b_16"><international:get name="linkedSites"/></div>
            <br>
            <international:get name="thisSiteMayContain"/>
            <hr>
            <div class="b_16"><international:get name="networkSitesAndTheirChildSites"/></div>
            <br>
            <international:get name="ShroggleComFunctionally"/>
            <hr>
            <div class="b_16"><international:get name="disclaimer"/></div>
            <br>
            <international:get name="thisSiteContent"/>
            <hr>
            <div class="b_16"><international:get name="indemnification"/></div>
            <br>
            <international:get name="youAgreeToDefend"/>
            <hr>
            <div class="b_16"><international:get name="limitationOfLiability"/></div>
            <br>
            <international:get name="inNoEventShall"/>
            <hr>
            <div class="b_16"><international:get name="payment"/></div>
            <br>
            <international:get name="failure"/>
            <hr>
            <div class="b_16"><international:get name="miscellaneous"/></div>
            <br>
            <international:get name="theseTerms"/>
            <br><bR>
        </div>
        <bR clear="all"><bR>

        <p align="right"><input type="button" value="I agree to terms and conditions" onclick="agreeToConditions();"
                                onmouseout="this.className='but_w230';"
                                onmouseover="this.className='but_w230_Over';" class="but_w230"></p>
    </div>
</div>

<div id="accountMoreInfoText" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto; width:450px; text-align:left;">
            <div>
                <international:get name="alreadyRegisteredMoreInfo"/>
                <a href='javascript:{closeConfigureWidgetDiv();showLoginInAccount();}'><international:get
                        name="signIn"/></a><international:get name="alreadyRegisteredMoreInfo2"/>
            </div>
            <br>

            <div>
                <international:get name="forgottenPassword"/>
                <a href='javascript:{closeConfigureWidgetDiv();forgotPassword.show();}'><international:get
                        name="forgottenPasswordLink"/></a>
            </div>
            <br>

            <div>
                <international:get name="resentAnActivationEmail"/>
                <a href='javascript:{closeConfigureWidgetDiv();resentAnActivationEmail();}'><international:get
                        name="resentAnActivationEmailLink"/></a>
            </div>
            <br>

            <div>
                <international:get name="createNewSite"/>
                <a href='javascript:{closeConfigureWidgetDiv();showLoginInAccount();}'><international:get
                        name="signIn"/></a><international:get name="createNewSite2"/>
            </div>
            <br>
        </div>
        <bR clear="all"><bR>

        <div align="right"><input type="button" onclick="closeConfigureWidgetDiv();" value="Close"
                                  onmouseout="this.className='but_w73';"
                                  onmouseover="this.className='but_w73_Over';" class="but_w73"></div>
    </div>
</div>
<div id="activationLinkResent" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto;height:30px; width:270px; padding:10px; text-align:left;">
            <div style="color:green;"><b><international:get name="activationEmailResent"/></b></div>
            <br>
        </div>
        <bR clear="all"><bR>

        <p align="right"><input type="button" onclick="closeConfigureWidgetDiv();" value="Close"
                                onmouseout="this.className='but_w73';"
                                onmouseover="this.className='but_w73_Over';" class="but_w73">
        </p>

    </div>
</div>
<input type="hidden" id="widgetId" value="<%= widgetId %>">
</div>
</body>
</html>