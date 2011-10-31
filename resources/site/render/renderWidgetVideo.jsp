<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="widgetVideo"/>
<%
    final Integer imageWidth = (Integer) request.getAttribute("imageWidth");
    final Integer imageHeight = (Integer) request.getAttribute("imageHeight");
    Integer widgetVideoWidth = (Integer) request.getAttribute("widgetVideoWidth");
    Integer widgetVideoHeight = (Integer) request.getAttribute("widgetVideoHeight");
    widgetVideoWidth = widgetVideoWidth != null ? widgetVideoWidth : ((imageWidth != null && imageWidth > 0) ? imageWidth : 250);
    widgetVideoHeight = widgetVideoHeight != null ? widgetVideoHeight : ((imageHeight != null && imageHeight > 0) ? imageHeight : 20);
    final int widgetId = (Integer) request.getAttribute("widgetId");
    final String widgetVideoUrl = (String) request.getAttribute("widgetVideoUrl");
    final String widgetVideoImageUrl = (String) request.getAttribute("widgetVideoImageUrl");
    final boolean playInCurrentPage = (Boolean) request.getAttribute("widgetPlayInCurrentPage");
    final boolean showDescription = (Boolean) request.getAttribute("widgetVideoShowDescription");

    final boolean displaySmallOptions = (Boolean) request.getAttribute("widgetDisplaySmallOptions");
    final boolean displayLargeOptions = (Boolean) request.getAttribute("widgetDisplayLargeOptions");

    final String widgetLargeVideoUrl = (String) request.getAttribute("widgetLargeVideoUrl");
    final String widgetSmallVideoUrl = (String) request.getAttribute("widgetSmallVideoUrl");
    final String widgetVideoLargeSize = (String) request.getAttribute("widgetVideoLargeSize");
    final String widgetVideoSmallSize = (String) request.getAttribute("widgetVideoSmallSize");

    final Integer videoFlvId = (Integer) request.getAttribute("videoFlvId");
    final Integer largeVideoFlvId = (Integer) request.getAttribute("largeVideoFlvId");
    final Integer smallVideoFlvId = (Integer) request.getAttribute("smallVideoFlvId");

    final String normalVideoStatus = (String) request.getAttribute("normalVideoStatus");
    final String largeVideoStatus = (String) request.getAttribute("largeVideoStatus");
    final String smallVideoStatus = (String) request.getAttribute("smallVideoStatus");
%>

<input type="hidden" id="widgetLargeVideoUrl<%= widgetId %>" value="<%= widgetLargeVideoUrl %>"/>
<input type="hidden" id="widgetSmallVideoUrl<%= widgetId %>" value="<%= widgetSmallVideoUrl %>"/>
<input type="hidden" id="widgetVideoUrl<%= widgetId %>" value="<%= widgetVideoUrl %>"/>
<input type="hidden" id="widgetVideoImageUrl<%= widgetId %>"
       value="<%= HtmlUtil.encodeToPercent(widgetVideoImageUrl) %>"/>

<div id="userVideo<%= widgetId %>">
</div>
<div>
    <% if (playInCurrentPage) { %>
    <%------------------------------------------FLV_VIDEO PLAYER ON CURRENT PAGE------------------------------------------%>
    <% final String videoOnload = "showVideo({" +
            "videoFlvId:" + videoFlvId + ", videoUrlId: 'widgetVideoUrl" + widgetId + "'," +
            "imageUrlId: 'widgetVideoImageUrl" + widgetId + "',width: '" + widgetVideoWidth + "'," +
            "height: '" + widgetVideoHeight + "',videoPlayerDivId: 'userVideo" + widgetId + "'," +
            "videoPlayerId: 'userVideo" + widgetId + "Player',videoStatus:'" + normalVideoStatus + "'});"; %>
    <elementWithOnload:element onload="<%= videoOnload %>"/>
    <%------------------------------------------FLV_VIDEO PLAYER ON CURRENT PAGE------------------------------------------%>
    <% } else { %>


    <%------------------------------------------FLV_VIDEO PLAYER IN SMALL WINDOW------------------------------------------%>
    <div id="videoImage<%= widgetId %>">
        <% if (!widgetVideoImageUrl.isEmpty()) { %>


        <%------------------------------------------------Video Image-------------------------------------------------%>
        <img src="<%= widgetVideoImageUrl %>"
                <% if (imageWidth != null && imageWidth != -1) { %> width="<%= imageWidth %>" <% } %>
                <% if (imageHeight != null && imageHeight != -1) { %> height="<%= imageHeight %>" <% } %>
                <% if (!displaySmallOptions && !displayLargeOptions) { %>
             title='<international:get name="clickToPlay"/>' style="cursor:pointer;"
             alt='<international:get name="clickToPlay"/>'
             onclick="createVideo({videoFlvId: <%= videoFlvId %>, videoUrlId : 'widgetVideoUrl<%= widgetId %>', imageUrlId : 'widgetVideoImageUrl<%= widgetId %>',
             width : '<%= widgetVideoWidth %>', height : '<%= widgetVideoHeight %>', videoStatus:'<%= normalVideoStatus %>', videoPlayerDivId: 'userVideoNew<%= widgetId %>',
          videoPlayerId: 'userVideo<%= widgetId %>Player'});"
                <% } %> />
        <%------------------------------------------------Video Image-------------------------------------------------%>
        <% } else if (!showDescription && !displaySmallOptions && !displayLargeOptions) { %>


        <%--------------------------------------------Click To Play link----------------------------------------------%>
        <a title='<international:get name="clickToPlay"/>'
           href="javascript:createVideo({videoFlvId: <%= videoFlvId %>, videoUrlId : 'widgetVideoUrl<%= widgetId %>', imageUrlId : 'widgetVideoImageUrl<%= widgetId %>',
           width : '<%= widgetVideoWidth %>', height : '<%= widgetVideoHeight %>', videoStatus:'<%= normalVideoStatus %>', videoPlayerDivId: 'userVideoNew<%= widgetId %>',
          videoPlayerId: 'userVideo<%= widgetId %>Player'})">
            <international:get name="clickToPlay"/>
        </a>
        <%--------------------------------------------Click To Play link----------------------------------------------%>
        <% } %>
    </div>
    <%------------------------------------------FLV_VIDEO PLAYER IN SMALL WINDOW------------------------------------------%>
    <% } %>


    <% if (displaySmallOptions || displayLargeOptions) {%>
    <div id="videoOptions<%= widgetId %>">

        <%----------------------------------------------FLV_VIDEO SMALL SIZE----------------------------------------------%>
        <% if (!widgetSmallVideoUrl.isEmpty() && !StringUtil.getEmptyOrString(widgetVideoSmallSize).isEmpty()) { %>
        <% if (playInCurrentPage) { %>
        <%--------------------------------------------play on current page--------------------------------------------%>
        <a href="javascript:showVideo({videoFlvId: <%= smallVideoFlvId %>, videoUrlId: 'widgetSmallVideoUrl<%= widgetId %>', imageUrlId: 'widgetVideoImageUrl<%= widgetId %>',
        width:'<%= widgetVideoSmallSize.split("x")[0] %>',  height:'<%= widgetVideoSmallSize.split("x")[1] %>', videoPlayerDivId:'userVideo<%= widgetId %>',videoPlayerId: 'userVideo<%= widgetId %>Player', videoStatus:'<%= smallVideoStatus %>'});">
            <international:get name="small"/>
        </a>&nbsp;
        <%--------------------------------------------play on current page--------------------------------------------%>
        <% } else { %>
        <%---------------------------------------------play in new window---------------------------------------------%>
        <a href="javascript:createVideo({videoFlvId: <%= smallVideoFlvId %>, videoUrlId : 'widgetSmallVideoUrl<%= widgetId %>', imageUrlId : 'widgetVideoImageUrl<%= widgetId %>',
        width : '<%= widgetVideoSmallSize.split("x")[0] %>', height : '<%= widgetVideoSmallSize.split("x")[1] %>', videoStatus:'<%= smallVideoStatus %>', videoPlayerDivId: 'userVideoNew<%= widgetId %>',
          videoPlayerId: 'userVideo<%= widgetId %>Player'})">
            <international:get name="small"/>
        </a>&nbsp;
        <%---------------------------------------------play in new window---------------------------------------------%>
        <% }
        }%>
        <%----------------------------------------------FLV_VIDEO SMALL SIZE----------------------------------------------%>


        <%----------------------------------------------FLV_VIDEO NORMAL SIZE---------------------------------------------%>
        <% if (playInCurrentPage) { %>
        <%--------------------------------------------play on current page--------------------------------------------%>
        <a href="javascript:showVideo({videoFlvId: <%= videoFlvId %>, videoUrlId : 'widgetVideoUrl<%= widgetId %>', imageUrlId:'widgetVideoImageUrl<%= widgetId %>',
        width:'<%= widgetVideoWidth %>', height: '<%= widgetVideoHeight %>', videoPlayerDivId:'userVideo<%= widgetId %>', videoPlayerId: 'userVideo<%= widgetId %>Player', videoStatus:'<%= normalVideoStatus %>'});">
            <international:get name="normal"/>
        </a>&nbsp;
        <%--------------------------------------------play on current page--------------------------------------------%>
        <% } else { %>
        <%---------------------------------------------play in new window---------------------------------------------%>
        <a href="javascript:createVideo({videoFlvId: <%= videoFlvId %>, videoUrlId : 'widgetVideoUrl<%= widgetId %>', imageUrlId : 'widgetVideoImageUrl<%= widgetId %>',
        width : '<%= widgetVideoWidth %>', height : '<%= widgetVideoHeight %>', videoStatus:'<%= normalVideoStatus %>', videoPlayerDivId: 'userVideoNew<%= widgetId %>',
          videoPlayerId: 'userVideo<%= widgetId %>Player'})">
            <international:get name="normal"/>
        </a>&nbsp;
        <%---------------------------------------------play in new window---------------------------------------------%>
        <% } %>
        <%----------------------------------------------FLV_VIDEO NORMAL SIZE---------------------------------------------%>


        <%----------------------------------------------FLV_VIDEO LARGE SIZE----------------------------------------------%>
        <% if (!widgetLargeVideoUrl.isEmpty() && !StringUtil.getEmptyOrString(widgetVideoLargeSize).isEmpty()) { %>
        <% if (playInCurrentPage) { %>
        <%--------------------------------------------play on current page--------------------------------------------%>
        <a href="javascript:showVideo({videoFlvId:<%= largeVideoFlvId %>,videoUrlId:'widgetLargeVideoUrl<%= widgetId %>',imageUrlId:'widgetVideoImageUrl<%= widgetId %>',
        width:'<%= widgetVideoLargeSize.split("x")[0] %>',height:'<%= widgetVideoLargeSize.split("x")[1] %>',videoPlayerDivId:'userVideo<%= widgetId %>',videoPlayerId: 'userVideo<%= widgetId %>Player',videoStatus:'<%= largeVideoStatus %>'});">
            <international:get name="large"/>
        </a>&nbsp;
        <%--------------------------------------------play on current page--------------------------------------------%>
        <% } else { %>
        <%---------------------------------------------play in new window---------------------------------------------%>
        <a href="javascript:createVideo({videoFlvId: <%= largeVideoFlvId %>, videoUrlId : 'widgetLargeVideoUrl<%= widgetId %>', imageUrlId : 'widgetVideoImageUrl<%= widgetId %>',
        width : '<%= widgetVideoLargeSize.split("x")[0] %>', height : '<%= widgetVideoLargeSize.split("x")[1] %>', videoStatus:'<%= largeVideoStatus %>', videoPlayerDivId: 'userVideoNew<%= widgetId %>',
          videoPlayerId: 'userVideo<%= widgetId %>Player'})">
            <international:get name="large"/>
        </a>&nbsp;
        <%---------------------------------------------play in new window---------------------------------------------%>
        <% }
        } %>
        <%----------------------------------------------FLV_VIDEO LARGE SIZE----------------------------------------------%>
    </div>
    <% } %>


    <%------------------------------------------------FLV_VIDEO DESCRIPTION-----------------------------------------------%>
    <% if (showDescription) { %>
    <% if (widgetVideoImageUrl.isEmpty() && !playInCurrentPage && !displaySmallOptions && !displayLargeOptions) { %>
    <div id="videoDescription<%= widgetId %>">
        <a href="javascript:createVideo({videoFlvId: <%= videoFlvId %>, videoUrlId : 'widgetVideoUrl<%= widgetId %>', imageUrlId : 'widgetVideoImageUrl<%= widgetId %>', width : '<%= widgetVideoWidth %>', height : '<%= widgetVideoHeight %>', videoStatus:'<%= normalVideoStatus %>', videoPlayerDivId: 'userVideoNew<%= widgetId %>',
          videoPlayerId: 'userVideo<%= widgetId %>Player'})"
           title='<international:get name="clickToPlay"/>'>
            <%= (String) request.getAttribute("widgetVideoDescription")%>
        </a>
    </div>
    <% } else { %>
    <div>
        <%= (String) request.getAttribute("widgetVideoDescription") %>
    </div>
    <% } %>
    <% } %>
    <%------------------------------------------------FLV_VIDEO DESCRIPTION-----------------------------------------------%>
</div>