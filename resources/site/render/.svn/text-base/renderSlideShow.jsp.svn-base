<%@ page import="com.shroggle.entity.Widget" %>
<%@ page import="com.shroggle.entity.SlideShow" %>
<%@ page import="com.shroggle.entity.SlideShowImage" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.slideShow.SlideShowManager" %>
<%@ page import="com.shroggle.logic.slideShow.SlideShowImageManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="elementWithOnload" tagdir="/WEB-INF/tags/elementWithOnload" %>
<%
    final SlideShow slideShow = (SlideShow) request.getAttribute("slideShow");
    final Widget widget = (Widget) request.getAttribute("widget");

    final List<SlideShowImage> images = new SlideShowManager(slideShow).getSortedImages();
%>
<div class="slideShowBlock" id="slideShow<%= widget.getWidgetId() %>" style="display:none;">
    <input type="hidden" id="carouselWidth<%= widget.getWidgetId() %>"
           value="<%= slideShow.getImageWidth() * new SlideShowManager(slideShow).getDisplayItems() %>"/>
    <% int buttonWidth = 24; %>
    <% int buttonCount = 4; %>
    <% int buttonsLeftPadding = 15; %>
    <input type="hidden" id="carouselButtonDivPaddingLeft<%= widget.getWidgetId() %>"
           value="<%= new SlideShowManager(slideShow).getSlideShowTopEdgeWidth() / 2 - (buttonWidth * buttonCount + buttonsLeftPadding) / 2 %>"/>
    <input type="hidden" id="carouselDisplayItems<%= widget.getWidgetId() %>"
           value="<%= new SlideShowManager(slideShow).getDisplayItems() %>"/>
    <input type="hidden" id="carouselEffect<%= widget.getWidgetId() %>"
           value="<%= new SlideShowManager(slideShow).getEffect() %>"/>
    <input type="hidden" id="carouselAnimationSpeed<%= widget.getWidgetId() %>"
           value="<%= new SlideShowManager(slideShow).getAnimationSpeed() %>"/>
    <input type="hidden" id="carouselAutoPlay<%= widget.getWidgetId() %>"
           value="<%= slideShow.isAutoPlay() %>"/>
    <input type="hidden" id="carouselAutoPlayInterval<%= widget.getWidgetId() %>"
           value="<%= slideShow.getAutoPlayInterval() %>"/>
    <input type="hidden" id="carouselDirection<%= widget.getWidgetId() %>"
           value="<%= new SlideShowManager(slideShow).getDirection() %>"/>
    <input type="hidden" id="carouselTreatAsCarousel<%= widget.getWidgetId() %>"
           value="<%= new SlideShowManager(slideShow).isTreatAsCarousel() %>"/>
    <input type="hidden" class="widgetId" value="<%= widget.getWidgetId() %>"/>

    <div id="carouselWholeBlock<%= widget.getWidgetId() %>">
        <div class="carousel" id="carousel<%= widget.getWidgetId() %>">
            <ul>
                <% for (SlideShowImage image : images) { %>
                <li style="display:inline; float: left; height:<%= slideShow.getImageHeight() %>px">
                    <img src="<%= new SlideShowImageManager(image).getResizedImageUrl() %>"
                         width="<%= slideShow.getImageWidth() %>" height="<%= slideShow.getImageHeight() %>"
                         alt="">
                </li>
                <% } %>
            </ul>
        </div>
        <div class="buttonsDiv" <% if (!slideShow.isDisplayControls()) { %>style="display:none;"<% } %>>
            <div title="Previous image" id="slideShowPrevBtn<%= widget.getWidgetId() %>"></div>
            <% if (slideShow.isAutoPlay()) { %>
            <div title="Pause" class="carousel-pause"
                 onclick="renderSlideShow.slideShowPlayClick(this, <%= widget.getWidgetId() %>);"></div>
            <% } else { %>
            <div title="Play" class="carousel-play"
                 onclick="renderSlideShow.slideShowPlayClick(this, <%= widget.getWidgetId() %>);"></div>
            <% } %>
            <div title="Toggle fullscreen" class="carousel-fullscreen"
                 <% if (!images.isEmpty()) {%>onclick="renderSlideShow.enterFullScreenMode(<%= widget.getWidgetId() %>);"<% } %>></div>
            <div title="Next Image" id="slideShowNextBtn<%= widget.getWidgetId() %>"></div>
        </div>
        <div style="clear:both;"></div>
    </div>

    <div id="slideShowFullScreenDiv" class="slideShowFullScreenDiv" style="display:none;">
        <input type="hidden" id="slideShowFullScreenWidgetId" value="<%= widget.getWidgetId() %>"/>

        <div class="slideShowCloseDiv" id="slideShowCloseDiv<%= widget.getWidgetId() %>"
             onclick="renderSlideShow.exitFullScreenMode()">
            <div class="slideShowCloseText">Exit full screen mode</div>
            <div class="slideShowCloseImg"></div>
        </div>
        <div class="slideShowCloseFakeImg"></div>

        <div class="slideShowFullScreenMainDiv" id="slideShowFullScreenMainDiv">
            <img src="<%= images.isEmpty() ? "" : new SlideShowImageManager(images.get(0)).getOriginalImageUrl() %>"
                 id="slideShowFullScreenMainImage">

            <div class="slideShowFullScreenButtons" id="slideShowFullScreenButtons<%= widget.getWidgetId() %>">
                <div id="fullScreenSlideShowPrevBtn<%= widget.getWidgetId() %>" class="fullScreenPrevBtn"></div>
                <div id="fullScreenSlideShowNextBtn<%= widget.getWidgetId() %>" class="fullScreenNextBtn"></div>
            </div>
        </div>

        <div id="smallGalleryBlock<%= widget.getWidgetId() %>" class="slideShowFullScreenSmallGalleryBlock">
            <div id="smallGallery<%= widget.getWidgetId() %>" class="slideShowFullScreenSmallGallery">
                <ul>
                    <% for (SlideShowImage image : images) { %>
                    <li>
                        <img realsrc="<%= new SlideShowImageManager(image).getOriginalImageUrl() %>" src=""
                             height="64" alt="">
                    </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </div>

    <%-- Loading carousel --%>
    <% final String carouselLoadFunc = "renderSlideShow.initCarousel(" + widget.getWidgetId() + ");"; %>
    <elementWithOnload:element onload="<%= carouselLoadFunc %>"/>
</div>


