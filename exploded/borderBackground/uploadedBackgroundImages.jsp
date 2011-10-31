<%--
  @author Balakirev Anatoliy
  Date: 23.09.2009
 --%>
<%@ page import="com.shroggle.presentation.image.ImageData" %>
<%@ page import="com.shroggle.presentation.site.borderBackground.ConfigureBackgroundService" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final ConfigureBackgroundService service = (ConfigureBackgroundService) request.getAttribute("backgroundService");
    final int lastUploadedVideoImageId = service.getImageThumbnailsDatas().size() > 0 ? service.getImageThumbnailsDatas().get(0).getImageId() : 0;
%>

<input type="hidden" value="<%= lastUploadedVideoImageId %>" id="lastUploadedBackgroundImageId">

<table>
    <tr>
        <% for (final ImageData imageData : service.getImageThumbnailsDatas()) { %>
        <td style="vertical-align:bottom;padding:3px;">
            <div align="center">
                <img src="..<%= imageData.getUrl() %>"
                     style="padding:5px;vertical-align:bottom;"
                     class="unselectedConfigureImage"
                     detectControlClick="true"
                     id="divForBackgroundImage<%= imageData.getImageId() %>"
                     align="middle"
                     height="<%= imageData.getThumbnailHeight() %>px"
                     alt="" border="0"
                     onclick="selectBackgroundImage(<%= imageData.getImageId() %>);">
                <br/>
                <div align="center">
                    <%= StringUtil.getEmptyOrString(imageData.getName()) %>
                </div>
            </div>
        </td>
        <% } %>
    </tr>
</table>