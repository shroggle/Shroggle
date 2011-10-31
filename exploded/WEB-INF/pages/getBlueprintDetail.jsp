<%@ page import="com.shroggle.presentation.site.GetBlueprintDetailAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="getBlueprintDetail"/>
<% final GetBlueprintDetailAction action = (GetBlueprintDetailAction) request.getAttribute("actionBean"); %>

<div class="blueprintTemplateDetails">
    <span class="blueprintTemplateDetailsHeader"><international:get name="pageCount"/></span> <%= action.getPageNames().size() %><br>
</div>

<div class="blueprintTemplateDetails">
    <span class="blueprintTemplateDetailsHeader"><international:get name="pages"/></span>
    <% for (int i = 0; i < action.getPageNames().size(); i++) { %>
        <%= action.getPageNames().get(i) %><%= i != action.getPageNames().size() - 1 ? ", " : "" %>
    <% } %>
</div>

<div class="blueprintTemplateDetails">
    <span class="blueprintTemplateDetailsHeader"><international:get name="template"/></span> <%= action.getTemplateName() %><br>
</div>

<div class="blueprintTemplateDetails">
    <span class="blueprintTemplateDetailsHeader"><international:get name="theme"/></span> <%= action.getThemeName() %><br>
</div>

<div class="blueprintTemplateDetails">
    <span class="blueprintTemplateDetailsHeader"><international:get name="items"/></span>

    <% for (int i = 0; i < action.getItemNames().size(); i++) { %>
        <%= action.getItemNames().get(i) %><%= i != action.getItemNames().size() - 1 ? ", " : "" %>
    <% } %>
</div>
