<%@ page import="com.shroggle.logic.site.SiteManager" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.util.BooleanUtils" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="sitePagesSelect"/>
<%--
    @author Balakirev Anatoliy
--%>
<% final Boolean configureGalleryPagesForDataDisabled = BooleanUtils.toBooleanDefaultIfNull(request.getAttribute("configureGalleryPagesForData"), false);
    final List<PageManager> pageManagerList = (List<PageManager>) request.getAttribute("sitePages"); %>
<select size="1" id="configureGalleryPagesForData" style="width:130px;"
        <% if (configureGalleryPagesForDataDisabled) { %>disabled="disabled"<% } %>>
    <% if (pageManagerList == null || pageManagerList.isEmpty()) { %>
    <option><international:get name="nonePageForData"/></option>
    <% } else {
        for (PageManager pageManager : pageManagerList) { %>
    <option value="<%= pageManager.getId() %>">
        <%= pageManager.getName() %>
    </option>
    <% }
    } %>
</select>
