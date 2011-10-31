<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.presentation.site.requestContent.ShowRequestContentService" %>
<%@ page import="com.shroggle.entity.Site" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="showRequestContent"/>
<% final ShowRequestContentService service = (ShowRequestContentService) request.getAttribute("service"); %>

<input type="hidden" value="<international:get name="siteOwnerIsNotSelectedError"/>" id="siteOwnerIsNotSelectedError">
<input type="hidden" value="<international:get name="siteAlredyConnected"/>" id="siteAlredyConnected">
<input type="hidden" value="<international:get name="requestContentNoItems"/>" id="requestContentNoItems">
<input type="hidden" value="<international:get name="contentModuleNotSelectedError"/>"
       id="contentModuleNotSelectedError">

<div class="windowOneColumn">
    <% if (service.getTargetSite() != null) { %>
    <h1><%= service.getTargetSite().getTitle() %>
    </h1>
    <% } %>
    <h2><international:get name="subHeader"/></h2>

    <div class="emptyError" id="errors" style="margin-bottom:2px;">&nbsp;</div>
    <div style="color: green; display: none; margin-bottom: 5px;" id="sendRequestContentSuccess"><international:get
            name="success"/></div>

    <label for="requestContentTargetSites"
           style="display: <%= service.getTargetSite() == null ? "inline" : "none" %>"><international:get
            name="selectSites"/></label>
    <select id="requestContentTargetSites" size="1"
            style="margin-bottom:5px;display: <%= service.getTargetSite() == null ? "inline" : "none" %>">
        <% for (final Site targetSite : service.getTargetSites()) { %>
        <option value="<%= targetSite.getSiteId() %>" <%= service.getTargetSite() == targetSite ? "selected" : "" %>>
            <%= HtmlUtil.limitName(targetSite.getTitle()) %>
        </option>
        <% } %>
    </select>
    <br/>

    <international:get name="requestContentSitesLabel"/> <br><br>

    <div class="inform_mark"><international:get name="info"/></div>
    <br>

    <div class="requestContentSites" id="requestContentSites">
        <% for (int i = 0; i < service.getSites().size(); i++) { %>
        <% final Site site = service.getSites().get(i); %>
        <% final String siteUrl = "http://www." + site.getSubDomain() + "." + service.getUserSitesUrl(); %>
        <div siteId="<%= site.getSiteId() %>" <% if (i % 2 == 0) {%>class="even"<% } %>
             onclick="requestContent.selectSite(this);">
            <span class="requestContentSiteTitle"><%= HtmlUtil.limitName(site.getTitle()) %></span>
            <a class="requestContentSiteUrl" href="<%= siteUrl %>" target="_blank"><%= siteUrl %>
            </a>
            <img alt="Loading..." src="/images/ajax-loader-minor.gif"
                 style="vertical-align:middle;padding: 0 3px 0 0;display:none;"/>
        </div>
        <% } %>
    </div>

    <div style="float:left;margin-left:10px;padding-top:150px;">
        <img src="/images/request_arrow.png" alt="">
    </div>

    <div class="requestContentItems" id="requestContentItems">
        <div class="even notSelectable">
            <international:get name="requestContentSiteNotSelected"/>
        </div>
    </div>

    <div style="clear:both;"></div>

    <div class="requestContentPersonalNoteTextareaDiv">
        Include a personal note:<br>
        <textarea onfocus="trimTextArea(this);" class="requestContentPersonalNoteTextarea"
                  id="requestContentNote"></textarea>
    </div>

    <div class="buttons_box" align="right">
        <input type="button" onclick="requestContent.sendRequestContent();" value="Request"
               onmouseout="this.className='but_w100';" onmouseover="this.className='but_w100_Over';"
               class="but_w100">
        <input type="button" class="but_w73" onmouseover="this.className='but_w73_Over';"
               onmouseout="this.className='but_w73';" id="requestContentCancel" value="Close"/>
    </div>
</div>