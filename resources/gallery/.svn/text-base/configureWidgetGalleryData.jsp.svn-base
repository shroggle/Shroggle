<%@ page import="com.shroggle.presentation.gallery.ConfigureGalleryDataService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<international:part part="configureWidgetGalleryData"/>
<% final ConfigureGalleryDataService service = (ConfigureGalleryDataService) request.getAttribute("galleryDataService"); %>
<div class="itemSettingsWindowDiv">
    <h1><international:get name="title"/></h1>
    <% if (service.getWidgetTitle() != null) { %>
    <widget:title customServiceName="galleryDataService"/>
    <% } %>
    <div class="windowTopLine">&nbsp;</div>

    <br>
    <% if (service.getGalleryNames().isEmpty()) { %>
    <international:get name="noConnectedGalleries"/>
    <% } else { %>
    <international:get name="description">
        <international:param value="<%= service.getGalleryNames() %>"/>
    </international:get>
    <% } %>

    <br>
    <ul style="padding-left: 30px; padding-top: 10px;">
        <% for (String siteAndPageVersionName : service.getSiteAndPageVersionNames()) { %>
        <li><%= siteAndPageVersionName %></li>
        <% } %>
    </ul>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right">
        <input type="button" value="Close" onclick="closeConfigureWidgetDiv();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>