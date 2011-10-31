<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.presentation.site.GetBlueprintsAction" %>
<%@ page import="com.shroggle.presentation.site.GetBlueprintsItem" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<international:part part="getBlueprints"/>
<% final GetBlueprintsAction action = (GetBlueprintsAction) request.getAttribute("actionBean"); %>

<% int itemIndex = 0; %>
<% while (itemIndex < action.getItems().size()) { %>
<% final GetBlueprintsItem item = action.getItems().get(itemIndex); %>
<div class="blueprintTemplateBlock fl">
    <div class="templateName">
        <%= item.getTitle() %>
    </div>

    <% boolean hasMoreThatOneImage = item.getScreenShotUrls().size() > 1; %>
    <div class="blueprintTemplateImageBlock"
         screenShotsCount="<%= item.getScreenShotUrls().size() %>"
         blueprintId="<%= item.getId() %>">
        <div class="blueprintTemplateBackButton" id="blueprintTemplateBackButton<%= item.getId() %>"
                <%= !hasMoreThatOneImage ? "style='visibility:hidden'" : "" %>></div>
        <div class="blueprintTemplateNextButton" id="blueprintTemplateNextButton<%= item.getId() %>"
                <%= !hasMoreThatOneImage ? "style='visibility:hidden'" : "" %>></div>
        <div <%= item.getScreenShotUrls().isEmpty() ? "style='display:none'" : "" %>
                class="blueprintTemplatePreviewButton" id="blueprintTemplatePreviewButton<%= item.getId() %>"
                onmouseover="setOpacity(this, 1);"
                onmouseout="setOpacity(this, 0.6);"
                onclick="blueprintTemplateViewImage(this);" blueprintId="<%= item.getId() %>"></div>
        <div style="clear:both;"></div>
        <div class="blueprintTemplateImageBlockShift">
            <% if (!item.getScreenShotUrls().isEmpty()) { %>
            <div class="blueprintTemplateCarousel">
                <ul>
                    <% for (int i = 0; i < item.getScreenShotUrls().size(); i++) { %>
                    <% String screenShotUrl = item.getScreenShotUrls().get(i); %>
                    <li style="display:inline; float: left; height: 300px; width: 440px">
                        <img width="440" height="300" src="<%= screenShotUrl %>" alt=""
                             class="blueprintTemplateScreenShot"
                             id="blueprintTemplateScreenShot<%= i %>"/>
                    </li>
                    <% } %>
                </ul>
            </div>
            <% } else { %>
            <table>
                <tr>
                    <td class="blueprintTemplateEmptyImage">
                        <img src="/images/nofoto.jpg" alt="">
                    </td>
                </tr>
            </table>
            <% } %>
        </div>
    </div>

    <div style="clear:both;"></div>
    <%--<input type="text" name="activatedBlueprintId" value="<%= item.getId() %>"/> I`ve added this to check site based on blueprint creation. Tolik--%>
    <div class="blueprintTemplateManageBlock blueprintTemplateImageBlockShift">
        <div class="blueprintTemplateMoreInfo">
            <a href="javascript:void(0);" blueprintId="<%= item.getId() %>" class="blueprintDetail"><international:get
                    name="more"/></a>
            <img src="/images/ajax-loader-minor.gif" alt="" style="visibility:hidden;"
                 class="blueprintTemplateMoreInfoLoading">
        </div>

        <div class="blueprintTemplateSubmitBlock">
            <input type="submit" name="execute" blueprintId="<%= item.getId() %>"
                   onclick="setBlueprintSelectedId(this);"
                   value="Proceed with this design" onmouseout="this.className='but_w200_misc'"
                   onmouseover="this.className='but_w200_Over_misc'" class="but_w200_misc"/>
        </div>
    </div>

    <div class="blueprintTemplateMoreInfoBlock" style="display:none;"></div>
</div>

<% if (itemIndex != 0 && (itemIndex + 1) % 2 == 0) { %>
<br clear="all">
<% } %>

<% itemIndex++; %>
<% } %>