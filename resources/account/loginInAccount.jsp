<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="loginInAccount"/>

<div class="loginPanel" id="promptLoginContent" style="border:3px solid #8D949A; margin:0 10px;" onmouseover="">
    <div class="loginHeader"><international:get name="signIntoYourAccount"/></div>
    <div id="loginError" class="loginError"></div>
    <form action="" id="loginFakeForm" onSubmit="executeLogin(); return false;">
        <table style="line-height:13px !important;width:270px;">
            <tr>
                <td class="align_r"><label for="loginEmail"><international:get name="email"/></label></td>
                <td><input type="text" id="loginEmail" class="txt" maxlength="255"></td>
            </tr>
            <tr>
                <td class="align_r"><label for="loginPassword"><international:get name="password"/></label></td>
                <td><input type="password" id="loginPassword" class="txt"></td>
            </tr>
            <tr>
                <td class="align_r"><input type="checkbox" id="loginRemember"></td>
                <td><label for="loginRemember"><international:get name="rememberMeOnThisComputer"/></label><br></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;">
                    <input type="submit" value="Sign In" onmouseout="this.className='but_w73';"
                           onmouseover="this.className='but_w73_Over';"
                           class="but_w73">

                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;padding-bottom:5px;">
                    <br>
                    <a href="javascript:forgotPassword.show()"><international:get
                            name="iCannotAccessMyAccount"/></a>
                </td>
            </tr>
        </table>
    </form>
</div>
<br><br>

<div class="box_2">
    <stripes:link beanclass="com.shroggle.presentation.user.create.CreateUserAction"><international:get
            name="registerForAShroggleAccount"/></stripes:link>
    <br>
    <stripes:link beanclass="com.shroggle.presentation.user.create.CreateUserAction"><international:get
            name="createAnAccountNow"/></stripes:link>
</div>