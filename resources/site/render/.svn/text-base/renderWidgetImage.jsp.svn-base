<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String widgetImageThumbnailUrl = (String) request.getAttribute("widgetImageThumbnailUrl");
    final String widgetImageUrl = (String) request.getAttribute("widgetImageUrl");
    final String widgetImageRolloverThumbnailUrl = (String) request.getAttribute("widgetImageRolloverThumbnailUrl");
    final boolean widgetImageShowDescription = (Boolean) request.getAttribute("widgetImageShowDescription");
    final boolean widgetImageShowBelowTitle = (Boolean) request.getAttribute("widgetImageShowBelowTitle");
    final boolean widgetImageShowUpTitle = (Boolean) request.getAttribute("widgetImageShowUpTitle");
    final String widgetImageDescription = StringUtil.getEmptyOrString(request.getAttribute("widgetImageDescription"));
    final String title = StringUtil.getEmptyOrString(request.getAttribute("widgetImageTitle"));
    final String alt = StringUtil.getEmptyOrString(request.getAttribute("widgetImageAlt"));
    final String widgetImageAlign = StringUtil.getEmptyOrString(request.getAttribute("widgetImageAlign"));
    final int id = (Integer) request.getAttribute("id");
    final boolean showStartStopAudioIcon = (Boolean) request.getAttribute("showStartStopAudioIcon");
    final boolean showStartStopFlashIcon = (Boolean) request.getAttribute("showStartStopFlashIcon");
    final Integer thumbnailImageWidth = (Integer) request.getAttribute("thumbnailWidth");
    final Integer thumbnailImageHeight = (Integer) request.getAttribute("thumbnailHeight");
    final boolean showFromSiteEditPage = (Boolean) request.getAttribute("showFromSiteEditPage");
    final boolean isBlank = (Boolean) request.getAttribute("isBlank");
    final int margin =  request.getAttribute("margin") != null ? (Integer) request.getAttribute("margin") : 0;
%>

<div align="<%= widgetImageAlign %>" style="padding: 0; margin: 0;">
    <% if (widgetImageShowUpTitle) { %><div class="imageTitle">
        <%= title %>
    </div><% } %>
    <%----------------------------------------------main image----------------------------------------------%>
    <div id="imageHref<%= id %>" style="display:none;"><%= widgetImageUrl %>
    </div>
    <% if (!StringUtil.getEmptyOrString(widgetImageUrl).isEmpty()) { %>
    <a href='<%= widgetImageUrl %>' <% if(isBlank) { %>target="_blank" <% } %> class="imageLink" externalUrl="true"><% } %>
        <img id="widgetImage<%= id %>" src="<%= widgetImageThumbnailUrl %>"
             <% if (margin > 0) { %>style="margin:<%= margin %>px"<% } %>
             width="<%= thumbnailImageWidth %>" height="<%= thumbnailImageHeight %>"
             onmouseout="setImageSrc('widgetImage<%= id %>', '<%= widgetImageThumbnailUrl %>');"
             onmouseover="setImageSrc('widgetImage<%= id %>', '<%= widgetImageRolloverThumbnailUrl %>');"
             border="0"
             <% if (!StringUtil.isNullOrEmpty(alt)) { %>alt="<%= alt %>" title="<%= alt %>"<% } %>
             class="imageImage"
        <%------If image is a link to audio or flash - show "play.png" little image in the center of main image-------%>
            <% if (!showFromSiteEditPage && (showStartStopAudioIcon || showStartStopFlashIcon)) { %>
             onload="showPlayIconForImage(<%= id %>, this, 'imageHref<%= id %>');"
            <% } %>
        <%------If image is a link to audio or flash - show "play.png" little image in the center of main image-------%>
                ><% if (!StringUtil.getEmptyOrString(widgetImageUrl).isEmpty()) { %></a><% } %>
    <%----------------------------------------------main image----------------------------------------------%>
    <% if (widgetImageShowBelowTitle) { %><div class="imageTitle"><%= title %>
    </div><% } %>
    <% if (widgetImageShowDescription && !StringUtil.getEmptyOrString(widgetImageDescription).trim().isEmpty()) { %>
    <div class="imageDescription"><%= StringUtil.getEmptyOrString(widgetImageDescription) %>
    </div>
    <% } %>
</div>
<div id="mp3playerDiv<%= id %>" style="width:0;height:0; padding: 0; margin: 0; font-size: 0">&nbsp;</div>