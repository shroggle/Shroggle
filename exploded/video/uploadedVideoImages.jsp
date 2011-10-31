<%@ page import="com.shroggle.presentation.image.ImageData" %>
<%@ page import="com.shroggle.presentation.video.ConfigureVideoService" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final ConfigureVideoService imagesVideoService = (ConfigureVideoService) request.getAttribute("videoService");
    final int lastUploadedVideoImageId = imagesVideoService.getImageThumbnailsWithPaths().size() > 0 ? imagesVideoService.getImageThumbnailsWithPaths().get(0).getImageId() : 0;
%>

<input type="hidden" value="<%= lastUploadedVideoImageId %>" id="lastUploadedVideoImageId">

<table>
    <tr>
        <% for (final ImageData imageData : imagesVideoService.getImageThumbnailsWithPaths()) { %>
        <input type="hidden" value="<%= imageData.getWidth() %>" id="width<%= imageData.getImageId() %>">
        <input type="hidden" value="<%= imageData.getHeight() %>" id="height<%= imageData.getImageId() %>">
        <td style="vertical-align:bottom; height:190px;padding:3px;">
            <div align="center">
                <img src="..<%= imageData.getUrl() %>"
                     style="padding:5px;vertical-align:bottom;"
                     class="unselectedConfigureImage"
                     id="imageForVideoDiv<%= imageData.getImageId() %>"
                     align="middle"
                     height="<%= imageData.getThumbnailHeight() %>px"
                     alt=""
                     onclick="selectImageForVideo(<%= imageData.getImageId() %>,
                         <%= imageData.getWidth() %>, <%= imageData.getHeight() %>);">
                <br/>

                <div align="center">
                    <%= StringUtil.getEmptyOrString(imageData.getName()) %>
                    <br/>
                    (<%= imageData.getWidth() %> x <%= imageData.getHeight() %>)
                </div>
            </div>
        </td>
        <% } %>
    </tr>
</table>