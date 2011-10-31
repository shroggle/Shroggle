<%@ page import="com.shroggle.presentation.image.GetImagesService" %>
<%@ page import="com.shroggle.presentation.image.GetImagesItem" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% final GetImagesService service = (GetImagesService) request.getAttribute("service"); %>
<% for (final List<GetImagesItem> items : service.getItemsLines()) { %>
<table>
    <tr>
        <% for (final GetImagesItem item : items) { %>
        <td style="vertical-align: bottom; height: 160px;" class="imageBlock">
            <div align="center" class="imageDiv">
                <img src="<%= item.getThumbnailUrl() %>"
                     style="vertical-align: bottom;"
                     class="unselectedConfigureImage"
                     align="middle"
                     height='<%= item.getThumbnailHeight() %>px'
                     alt=""
                     image="image"
                     imageWidth="<%= item.getWidth() %>"
                     imageUrl="<%= item.getUrl() %>"
                     imageThumbnailUrl="<%= item.getThumbnailUrl() %>"
                     imageId="<%= item.getImageId() %>"
                     imageName="<%= StringUtil.getEmptyOrString(item.getName()) %>"
                     imageHeight="<%= item.getHeight() %>"
                     onload="window.images.select.fixImageTitleWidth(this);"
                     detectControlClick="true">
                <br/>

                <div align="center" class="uploadedImageNameDiv">
                    <div class="uploadedImageName" title="<%= StringUtil.getEmptyOrString(item.getName()) %>">
                        <%= StringUtil.getEmptyOrString(item.getName()) %>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="uploadedImageNameEdit">
                        <input class="uploadedImageNameEditInput"
                               onkeyup="window.images.select.saveEditOnEnter(event, this);"
                               value="<%= item.getName() != null ? item.getName() : "" %>"
                               type="text"/>
                    </div>
                    <div class="uploadedImageManage">
                        (<%= item.getWidth() %>x<%= item.getHeight() %>)
                        <img src="/images/pencil.png" alt="E" class="uploadedImageEditImg"/>
                        <img src="/images/cross-circle.png" alt="X" class="uploadedImageDeleteImg"/>
                    </div>
                </div>
            </div>
        </td>
        <% } %>
    </tr>
</table>
<% } %>