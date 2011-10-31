<%@ page import="com.shroggle.presentation.site.htmlAndCss.ConfigurePageHtmlAndCssService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="pageVersionHtmlAndCss"/>
<% final ConfigurePageHtmlAndCssService service =
        (ConfigurePageHtmlAndCssService) request.getAttribute("pageHtmlAndCssService"); %>
<div class="itemSettingsWindowDiv">
    <h1><international:get name="description"/></h1>

    <page:title>
        <jsp:attribute name="customServiceName">pageHtmlAndCssService</jsp:attribute>
    </page:title>
    <div class="windowTopLine">&nbsp;</div>

    <p>
        <international:get name="type"/>
        <select onchange="configurePageHtmlAndCss.selectHtmlOrCss(this);">
            <option selected="selected"><international:get name="htmlLayout"/></option>
            <option><international:get name="cssColorSchema"/></option>
        </select>
    </p>
    <p id="pageVersionHtmlTab" style="font-weight:bold;">
        <international:get name="htmlLayout"/><br>
        <textarea id="pageVersionHtml" rows="20" cols="40" class="pageHtmlCssTextArea"
                ><%= service.getPageVersionHtml() %></textarea><br>
        <a href="javascript:configurePageHtmlAndCss.resetHtml(<%= service.getPageId() %>);">
            <international:get name="resetHtml"/></a>
    </p>

    <p id="pageVersionThemeCssTab" style="font-weight:bold; display: none;">
        <international:get name="cssColorSchema"/><br>
        <textarea id="pageVersionThemeCss" rows="20" cols="40" class="pageHtmlCssTextArea"
                ><%= service.getPageVersionThemeCss() %></textarea><br>
        <a href="javascript:configurePageHtmlAndCss.resetCss(<%= service.getPageId() %>);">
            <international:get name="resetCss"/></a>
    </p>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" onclick="configurePageHtmlAndCss.save(<%= service.getPageId() %>, false);"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowApply">
        <input type="button" value="Save" onclick="configurePageHtmlAndCss.save(<%= service.getPageId() %>, true);"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowSave">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>
