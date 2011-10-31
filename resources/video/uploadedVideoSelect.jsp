<%@ page import="com.shroggle.presentation.video.ConfigureVideoService" %>
<%@ page import="com.shroggle.logic.video.VideoManager" %>
<%@ page import="com.shroggle.presentation.video.VideoData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%
    final ConfigureVideoService videoSelectService = (ConfigureVideoService) request.getAttribute("videoService");
    final boolean disableSelect = videoSelectService.getVideos().size() == 0;
%>
<select id="videoSelect" style="width:307px" <% if (disableSelect) { %> disabled="true" <% } %>
        onchange="{setVideoName();setVideoSize();}">
    <% if (disableSelect) { %>
    <option id="-1">
        <international:get name="selectVideo"/>
    </option>
    <%
    } else {
        for (final VideoData tempVideo : videoSelectService.getVideos()) {
    %>
    <option id="<%= tempVideo.getId() %>" value="<%= tempVideo.getName() %>"
            audioFile="<%= tempVideo.isAudioFile() %>"
            videoWidth="<%=  tempVideo.getWidth() %>"
            videoHeight="<%= tempVideo.getHeight() %>">
        <%= tempVideo.getName() %>
        <% if (!tempVideo.isAudioFile()) { %>
        &nbsp;(<%= tempVideo.getWidth() %>x<%= tempVideo.getHeight() %>)
        <% } %>
    </option>
    <% }
    } %>
</select>