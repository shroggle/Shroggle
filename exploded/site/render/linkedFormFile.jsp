<%@ page import="com.shroggle.logic.form.LinkedFormManager" %>
<% final LinkedFormManager.LinkedFormFileData linkedFormFileData =
        (LinkedFormManager.LinkedFormFileData) request.getAttribute("linkedFormFileData"); %>

<a href="<%= linkedFormFileData.getFullSizeUrl() %>" target="_blank">
    <img width="100px" border="0" src="<%= linkedFormFileData.getPreviewSrc() %>">
</a>