<%@ page import="com.shroggle.presentation.site.ConfigureTextService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="onload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<international:part part="configureText"/>
<% final ConfigureTextService service = (ConfigureTextService) request.getAttribute("textService"); %>
<% final String text = service.getTextItem().getText() == null ? "" : service.getTextItem().getText(); %>
<div class="itemSettingsWindowDiv">
    <input type="hidden" id="selectedTextItemId" value="<%= service.getTextItem().getId() %>"/>

    <div id="configureTextItemText" style="display: none;">
        <%= text %>
    </div>
    <input type="hidden" id="configureTextLarge" value="<international:get name="large"/>"/>

    <h1><international:get name="header"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="textService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="textErrors"></div>

    <div class="readOnlyWarning" style="display:none;" id="textReadOnlyMessage">You have only read-only
        access to this module.
    </div>

    <label for="configureTextName" style="vertical-align:baseline;width:5%;"><international:get name="name"/></label>
    <input class="title" style="vertical-align: baseline;width: 40%;" type="text" id="configureTextName"
           value="<%= service.getTextItem().getName() %>" maxlength="255"><br><br>

    <div class="textTopButtons">
        <input type="button" class="but_w73" value="Apply" onclick="configureText.save(false);" id="textTopApply"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
        <input type="button" class="but_w73" value="Save" onclick="configureText.save(true);" id="textTopSave"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
        <input type="button" class="but_w73" value="Cancel" onclick="configureText.close();"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
    </div>
    <br clear="all">

    <div id="configureTextWidgetEditor" style="display:none">
        &nbsp;
    </div>

    <div id="tinyMCELoadingMessage" style="width:800px; height:412px;text-align:center;">
        <table style="width:100%;height:100%;">
            <tr>
                <td align="center" style="width:100%;height:100%;text-align:center;vertical-align:middle;">
                    <img alt="Loading text editor..." src="../images/ajax-loader.gif">
                    <br>
                    <international:get name="loadingTextEditor"/>
                    <onload:element onload="configureText.showTextEditor();"/>
                </td>
            </tr>
        </table>
    </div>
    <textarea onfocus="trimTextArea(this);" id="oldWidgetText" style="display: none;" rows="1"
              cols="1"><%= text %>
    </textarea>

    <div id="editorRestoreContext">&nbsp;</div>
    <div class="warning">
        <international:get name="textStyles"/>
    </div>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureTextButtons">
        <input type="button" class="but_w73" value="Apply" id="windowApply"
               onclick="configureText.save(false);"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
        <input type="button" class="but_w73" value="Save" id="windowSave"
               onclick="configureText.save(true);"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
        <input type="button" class="but_w73" value="Cancel" onclick="configureText.close();"
               id="windowCancel"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
    </div>
</div>