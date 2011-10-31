<%@ page import="com.shroggle.entity.DraftSlideShow" %>
<%@ page import="com.shroggle.presentation.slideShow.ConfigureSlideShowService" %>
<%@ page import="com.shroggle.entity.SlideShowTransitionEffectType" %>
<%@ page import="com.shroggle.entity.SlideShowType" %>
<%@ page import="com.shroggle.entity.SlideShowImage" %>
<%@ page import="com.shroggle.entity.SlideShowDisplayType" %>
<%@ page import="com.shroggle.logic.slideShow.SlideShowManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.slideShow.SlideShowImageManager" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="slideShow"/>
<%
    final List<SlideShowImage> images = (List<SlideShowImage>) request.getAttribute("slideShowImages");
%>

<div id="slideShowManageImagesWholeDiv">
    <div align="right" class="slideShowAddMoreImagesDiv">
        <a href="#" onclick="$('#settingsTab').click()"><international:get name="addMoreImages"/></a>
    </div>
    <div class="slideShowManageImagesDiv" id="slideShowManageImagesDiv">
        <table>
            <% for (int i = 0; i < images.size(); i++) { %>
            <% if (i % 3 == 0) {%>
            <tr>
                <% } %>
                <% final SlideShowImageManager imageManager = new SlideShowImageManager(images.get(i)); %>
                <td class="slideShowImageTd">
                    <div align="center" style="padding:5px;">
                        <img src="..<%= imageManager.getOriginalImageUrl() %>" height="185"
                             style="vertical-align:bottom;">
                        <br/>

                        <div align="center">
                            <%= HtmlUtil.limitName(StringUtil.getEmptyOrString(imageManager.getTitle()), 35) %>
                        </div>

                        <div>
                            <div class="fl">
                                <div onclick="configureSlideShow.moveImageLeft(this, <%= images.get(i).getSlideShowImageId() %>);"
                                     class="slideShowLeftArrow"></div>
                                <div onclick="configureSlideShow.moveImageRight(this, <%= images.get(i).getSlideShowImageId() %>);"
                                     class="slideShowRightArrow"></div>
                            </div>

                            <div class="fr">
                                <div onclick="configureSlideShow.deleteImage(this, <%= images.get(i).getSlideShowImageId() %>);"
                                     class="slideShowDelete"></div>
                            </div>
                        </div>
                    </div>
                </td>
                <% if ((i != 0 && i % 3 == 2) || (i == images.size() - 1)) {%>
            </tr>
            <% } %>
            <% } %>
        </table>
    </div>
</div>
