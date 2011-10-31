<%@ page import="com.shroggle.entity.ResourceType" %>
<%@ page import="com.shroggle.presentation.site.UploadFormFilesAction" %>
<%@ page import="com.shroggle.presentation.upload.UploadedFiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UploadedFiles action = (UploadedFiles) request.getAttribute("actionBean");
    int resourceId = -1;
    ResourceType resourceType = null;
    String resourceUrl = "";
    Integer filledFormId = null;
    if (action != null) {
        resourceUrl = action.getResourceUrl();
        resourceUrl = resourceUrl != null ? resourceUrl : "";
        resourceId = action.getResourceId();
        resourceType = action.getResourceType();
        if (action instanceof UploadFormFilesAction) {
            filledFormId = ((UploadFormFilesAction) action).getFilledFormId();
        }
    } %>
<html>
<head><title>Done</title></head>
<body>
Done
<input type="hidden" id="resourceId" value="<%= resourceId %>">
<input type="hidden" id="resourceType" value="<%= resourceType %>">
<input type="hidden" id="resourceUrl" value="<%= resourceUrl %>">
<input type="hidden" id="filledFormId" value="<%= filledFormId %>">
</body>
</html>