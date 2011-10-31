<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="passwordSentWindow"/>

<div class='windowOneColumn' style="background-color:white;">
    <h1 align='center' style="color: green; font-weight: bold;padding:0 0 5px 0;">
        <international:get name="anEmailHasBeenSuccessfullySent"/>
    </h1>

    <div align="right">
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over'"
               onmouseout="this.className='but_w73'"
               value="Close" onclick="forgotPassword.closePasswordSentWindow();"/>
    </div>
</div>


