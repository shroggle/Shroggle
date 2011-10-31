<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="forgottenMyPasswordWindow"/>
<div class="windowOneColumn">
    <h1 style="text-align:center;"><international:get name="forgottenYourPassword"/></h1>

    <div style="text-align:center;"><international:get name="enterYourEmailAddressAndWeWillEmailYouYourPasswordText"/></div>

    <div id="emailCheckResult" class="forgottenPasswordError">&nbsp;</div>
    <dl class="w_30 forgottenPassMainDiv" style="margin-left: -10px;">
        <dt>
            <label for="forgottenMyPasswordWindowAccountEmail" class="align_r"><international:get name="email"/></label>
        </dt>
        <dd>
            <input type="text" id="forgottenMyPasswordWindowAccountEmail" class="txt"
                   onblur="forgotPassword.checkAccountEmailExist();" maxlength="255">
        </dd>
        <dt>
            <label for="forgottenMyPasswordWindowAccountEmailConfirm" class="align_r"><international:get
                    name="retypeEmail"/></label>
        </dt>
        <dd>
            <input type="text" id="forgottenMyPasswordWindowAccountEmailConfirm" class="txt"
                   onblur="forgotPassword.checkEmailAndConfirmEquals();" maxlength="255">
        </dd>
    </dl>
    <br clear="all">

    <div id="emailConfirnCheckResult" class="forgottenPasswordError">&nbsp;</div>

    <div class="forgottenPasswordButtonDiv" align="center">
        <input type="button" onclick="forgotPassword.submit();" id="submitForgottenMyPasswordWindow"
               class="but_w130" onmouseover="this.className='but_w130_Over';"
               onmouseout="this.className='but_w130';"
               value="Email Password">
    </div>
    <br><br>

    <div class="forgottenPasswordCreateAccountLinks">
        <stripes:link
                beanclass="com.shroggle.presentation.user.create.CreateUserAction"><international:get
                name="ifYouHaveNotYetRegisteredText"/></stripes:link>
    </div>
</div>