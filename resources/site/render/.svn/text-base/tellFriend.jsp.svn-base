<%@ page import="com.shroggle.util.BooleanUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="tellFriend"/>
<% final Integer widgetId = (Integer) request.getAttribute("widgetId"); %>

<% if (BooleanUtils.toBooleanDefaultIfNull(request.getAttribute("sendEmails"), true)) { %>
<%-------------------------------------------------Sending emails mode------------------------------------------------%>
<a href="javascript:tellFriendShow(<%= widgetId %>);" class="tellFriendShow"
   id="tellFriendShow<%= widgetId %>"><international:get name="title"/></a>

<div id="tellFriendDiv<%= widgetId %>" style="display:none;" class="tellFriendMainDiv">
    <div style="padding-bottom:5px;">
        <international:get name="title"/>
    </div>

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tellFriendTable">
        <tr>
            <td class="tellFriendEmailLabelsTd">
                <label for="tellFriendEmailTo<%= widgetId %>">
                    <international:get name="emailTo"/><span class="tellFriendAsterisk">&nbsp;*</span>
                </label>
            </td>
            <td class="tellFriendEmailInputsTd">
                <input type="text" class="tellFriendEmailTo tellFriendEmailInput" id="tellFriendEmailTo<%= widgetId %>"
                       maxlength="255">
            </td>
        </tr>
        <tr>
            <td class="tellFriendEmailLabelsTd">
                <label for="tellFriendEmailFrom<%= widgetId %>">
                    <international:get name="emailFrom"/><span class="tellFriendAsterisk">&nbsp;*</span>
                </label>
            </td>
            <td class="tellFriendEmailInputsTd">
                <input type="text" class="tellFriendEmailFrom tellFriendEmailInput"
                       id="tellFriendEmailFrom<%= widgetId %>"
                       maxlength="255">
            </td>
        </tr>
    </table>

    <div>
        <input type="checkbox" class="tellFriendCcMe" id="tellFriendCcMe<%= widgetId %>">
        <label for="tellFriendCcMe<%= widgetId %>">
            <international:get name="ccMe"/>
        </label>
    </div>

    <div class="tellFriendSubMainDiv">
        <international:get name="yourMessage"/>
        <br/>
        <textarea onfocus="trimTextArea(this);" rows="5" class="tellFriendEmail tellFriendTextarea"
                  id="tellFriendEmail<%= widgetId %>"
                  cols="60"></textarea>

        <div class="tellFriendResult tellFriendError" id="tellFriendResult<%= widgetId %>"></div>

        <div class="tellFriendButtonsDiv">
            <input type="button"
                   onclick="tellFriendSend(<%= widgetId %>, '<%= request.getAttribute("siteShowOption") %>', <%= request.getAttribute("siteId") %>);"
                   value="<international:get name="send"/>" class="tellFriendButton"/>
            <input type="button" onclick="tellFriendClose(<%= widgetId %>);"
                   value="<international:get name="close"/>" class="tellFriendButton"/>
        </div>
    </div>
</div>
<%-------------------------------------------------Sending emails mode------------------------------------------------%>
<% } else { %>
<%---------------------------------------------------Social links mode------------------------------------------------%>
<div class="addthis_toolbox addthis_default_style">
    <a href="http://addthis.com/bookmark.php?v=250" class="addthis_button_compact"
      ><international:get name="share"/></a>

    <span class="addthis_separator">&nbsp;</span>
    <a class="addthis_button_facebook"></a>
    <a class="addthis_button_twitter"></a>
    <a class="addthis_button_googlebuzz"></a>
    <a class="addthis_button_email"></a>
    <span class="addthis_separator">&nbsp;</span>
    <a class="addthis_button_facebook_like"></a>
</div>


<script src="http://s7.addthis.com/js/250/addthis_widget.js" type="text/javascript" charset="utf-8"></script>


<%---------------------------------------------------Social links mode------------------------------------------------%>
<% } %>