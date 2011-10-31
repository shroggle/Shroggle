<%@ page import="com.shroggle.presentation.site.template.SetSiteTemplateAction" %>
<%@ page import="com.shroggle.presentation.site.SetSiteBlueprintAction" %>
<%@ page import="com.shroggle.presentation.site.EditSiteTemplateHtmlAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="setSiteTemplateMenu"/>
<% final Class selected = (Class) request.getAttribute("selected"); %>
<% final int siteId = (Integer) request.getAttribute("siteId"); %>
<% final boolean createChildSite = (Boolean) request.getAttribute("createChildSite"); %>
<div class="topmenu2 fr template_menu">
    <div class="menu_dashboard"></div>

    <% boolean isSetSiteTemplateAction = SetSiteTemplateAction.class == selected; %>
    <div class="topmenulink<%=  isSetSiteTemplateAction ? " selected" : "" %>"
            <% if (!isSetSiteTemplateAction) { %>
         onmouseover="dashboardMenu.setSelected(this);" onmouseout="dashboardMenu.setUnselected(this);"<% } %>>
        <div>
            <% if (isSetSiteTemplateAction) { %>
            <a><international:get name="menu3"/></a>
            <% } else { %>
            <stripes:link beanclass="com.shroggle.presentation.site.template.SetSiteTemplateAction">
                <stripes:param name="siteId" value="<%= siteId %>"/>
                <international:get name="menu3"/>
            </stripes:link>
            <% } %>
        </div>
    </div>

    <div class="menuline"> |</div>

    <% boolean isBlueprintAction = SetSiteBlueprintAction.class == selected; %>
    <div class="topmenulink<%= isBlueprintAction ? " selected" : "" %>"
            <% if (!isBlueprintAction) { %>
         onmouseover="dashboardMenu.setSelected(this);" onmouseout="dashboardMenu.setUnselected(this);"<% } %>>
        <div>
            <% if (isBlueprintAction) { %>
            <a><international:get name="menu2"/></a>
            <% } else { %>
            <stripes:link beanclass="com.shroggle.presentation.site.SetSiteBlueprintAction">
                <stripes:param name="siteId" value="<%= siteId %>"/>
                <stripes:param name="createChildSite" value="<%= createChildSite %>"/>
                <international:get name="menu2"/>
            </stripes:link>
            <% } %>
        </div>
    </div>

    <div class="menuline"> |</div>

    <% boolean isEditTemplateHtmlAction = EditSiteTemplateHtmlAction.class == selected; %>
    <div class="topmenulink<%= isEditTemplateHtmlAction ? " selected" : "" %>"
            <% if (!isEditTemplateHtmlAction) { %>
         onmouseover="dashboardMenu.setSelected(this);" onmouseout="dashboardMenu.setUnselected(this);"<% } %>>
        <div>
            <% if (isEditTemplateHtmlAction) { %>
            <a><international:get name="menu1"/></a>
            <% } else { %>
            <stripes:link beanclass="com.shroggle.presentation.site.EditSiteTemplateHtmlAction">
                <stripes:param name="siteId" value="<%= siteId %>"/>
                <stripes:param name="createChildSite" value="<%= createChildSite %>"/>
                <international:get name="menu1"/>
            </stripes:link>
            <% } %>
        </div>
    </div>
</div>
