<%@ page import="com.shroggle.presentation.image.ConfigureImageService" %>
<%@ page import="com.shroggle.entity.ImageFile" %>
<%@ page import="com.shroggle.presentation.image.ConfigureImageData" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="uploadedImageFileSelect"/>

<%
    final ConfigureImageService service = (ConfigureImageService) request.getAttribute("imageService");
    ConfigureImageData configureImageData = service.getConfigureImageData();
%>


<select id="imageFileSelect" <% if (service.getImageFiles().size() == 0) { %>disabled="true"<% } %>style="width:200px;">
    <% if (service.getImageFiles().size() == 0) { %>
    <option value="-1">
        <international:get name="selectFile"/>
    </option>
    <% } else {
        for (final ImageFile imageFile : service.getImageFiles()) { %>
    <option id="<%= imageFile.getImageFileId() %>" value="<%= imageFile.getImageFileId() %>"
            <%= !service.isShowLastImageFile() && configureImageData.getImageFileId() == imageFile.getImageFileId() ? "selected" : "" %>>
        <%= imageFile.getSourceName() %>
    </option>
    <% }
    } %>
</select>