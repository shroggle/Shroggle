<%@ page import="com.shroggle.presentation.tellFriend.ConfigureTellFriendService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="configureTellFriend"/>
<% final ConfigureTellFriendService service = (ConfigureTellFriendService) request.getAttribute("tellFriendService"); %>
<div class="itemSettingsWindowDiv">
    <input type="hidden" id="selectedTellFriendId" value="<%= service.getTellFriend().getId() %>"/>
    <input type="hidden" id="TellFriendNotSelectedException"
           value="<international:get name="TellFriendNotSelectedException"/>"/>

    <h1><international:get name="addEdit"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="tellFriendService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="tellFriendErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="tellFriendReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <div style="width: 100%">
        <label for="tellFriendName" style="vertical-align:baseline;width:5%;"><international:get name="name"/>:</label>
        &nbsp;&nbsp;
        <input class="title" style="width:200px;" type="text" id="tellFriendName"
               value="<%= service.getTellFriend().getName() %>" maxlength="255"><br><br>

        <input type="radio" id="socialScripts" name="tellFriendMode"
               <% if (!service.getTellFriend().isSendEmails()) { %>checked<% } %>
               onclick="disableSendingEmailsArea(true);">
        <label for="socialScripts"><international:get name="showExpandingControl"/></label>

        <div style="margin-top:5px;margin-bottom:20px;margin-left:25px;">
            <international:get name="expandingControlHelp"/>
            <div style="margin-top:15px ;margin-bottom:5px;">
            <international:get name="preview"/>:
            </div>
            <img src="/images/tellFriendPreview.png" alt="<international:get name="preview"/>">
        </div>


        <input type="radio" id="sendEmails" name="tellFriendMode"
               <% if (service.getTellFriend().isSendEmails()) { %>checked<% } %>
               onclick="disableSendingEmailsArea(false);">
        <label for="sendEmails"><international:get name="sendingEmails"/></label>

        <div id="sendingEmailsArea" style="margin-top:5px;margin-left:25px;">
            <label for="configureTellFriendMailSubject"><international:get name="mailSubject"/></label><br>
            <textarea onfocus="trimTextArea(this);" rows="3" cols="5" id="configureTellFriendMailSubject"
                      <% if (!service.getTellFriend().isSendEmails()) { %>disabled<% } %>
                      style="width: 95%;height: 100px;"><%= service.getTellFriend().getMailSubject() %>
            </textarea><br><br>

            <label for="configureTellFriendMailText"><international:get name="mailText"/></label><br>
            <textarea onfocus="trimTextArea(this);" rows="3" cols="5" id="configureTellFriendMailText"
                      <% if (!service.getTellFriend().isSendEmails()) { %>disabled<% } %>
                      style="width: 95%;height: 100px;"><%= service.getTellFriend().getMailText() %>
            </textarea><br>
        </div>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureTellFriendButtons">
        <input type="button" value="Apply" onmouseout="this.className='but_w73';" id="windowApply"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureTellFriend.save(false);">
        <input type="button" value="Save" onmouseout="this.className='but_w73';" id="windowSave"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureTellFriend.save(true);">
        <input type="button" value="Cancel" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();">
    </div>
</div>