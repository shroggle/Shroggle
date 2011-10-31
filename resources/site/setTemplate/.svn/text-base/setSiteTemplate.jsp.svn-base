<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.Template" %>
<%@ page import="com.shroggle.presentation.site.template.SetSiteTemplateAction" %>
<%@ page import="com.shroggle.entity.Theme" %>
<%@ page import="com.shroggle.logic.site.template.TemplateManager" %>
<%@ page import="com.shroggle.logic.site.template.ThemeLogic" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="setSiteTemplate"/>
<% final SetSiteTemplateAction action = (SetSiteTemplateAction) request.getAttribute("actionBean"); %>
<html>
    <head>
        <title><international:get name="TITLE"/></title>
        <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
        <script type="text/javascript">

            var themeThumbnailUrls = new Object();
            var themeImageUrls = new Object();

            function selectTheme(themeLink, templateId, themeId, blueprintId) {
                blueprintId = blueprintId ? blueprintId : "";

                var selectThemeDiv = $(themeLink).parents(".colorthumb");
                var selectedTemplateDiv = $(themeLink).parents(".templateblock");
                if (!selectThemeDiv) {
                    return;
                }

                $(".colorthumb").removeClass("currentcolor");
                $(selectThemeDiv).addClass("currentcolor");
//                $("#themesTemplate" + templateId + (blueprintId ? "Blueprint" + blueprintId : "")).show();

                $("#selectThemeId").val(themeId == null ? "" : themeId);

                $("#selectBlueprintId").val(blueprintId);
                $("#selectTemplateId").val(templateId);

                $(selectedTemplateDiv).find(".templateimg > img").attr("src", themeThumbnailUrls["template" + templateId + "Theme" + themeId]);
                $(selectedTemplateDiv).find(".exampleslink").attr("href", themeImageUrls["template" + templateId + "Theme" + themeId]);
            }

            function templatePreviewClick(preview){
                window.open($(preview).attr("href"));
            }

            function templatePreviewOver(preview){
                setOpacity(preview, "1");
            }

            function templatePreviewOut(preview){
                setOpacity(preview, "0.6");
            }

            function checkSelectedTheme() {
                if ($("#selectThemeId").val() == "") {
                    var selectedTemplateId = $("#selectTemplateId").val();
                    var firstThemeBlock = $("#template" + selectedTemplateId).find(".selectcolorscheme").find("div:first");
                    $("#selectThemeId").val($(firstThemeBlock).attr("themeId"));
                }

                <% if (action.isEditingMode()) { %>
                    <% if (action.getTemplateBlueprintsLogic().size() > 0) { %>
                        return confirm("<international:get name="APPROVE_THEME_CHANGE_FOR_BLUEPRINT"><international:param value="<%= action.getSiteTitle() %>"/></international:get>");
                    <% } else { %>
                        return confirm("<international:get name="APPROVE_THEME_CHANGE"><international:param value="<%= action.getSiteTitle() %>"/></international:get>");
                    <% } %>
                <% } else { %>
                    return true;
                <% } %>
            }

            function setSelectTheme() {
                <% if (action.getSelectTemplateId() != null) { %>
                    selectTheme(
                        <%= action.getSelectTemplateId() %>,
                        <%= action.getSelectThemeId() %>,
                        <%= action.getSelectBlueprintId() %>);
                <% } %>
            }

            function showThemes(showLink) {
                $(showLink).parents(".templateBottomMenu").find(".selectcolorscheme").slideDown();
                $(showLink)[0].onclick = function (){
                    hideThemes(showLink);
                }
            }

            function hideThemes(showLink) {
                $(showLink).parents(".templateBottomMenu").find(".selectcolorscheme").slideUp();
                $(showLink)[0].onclick = function (){
                    showThemes(showLink);
                }
            }

            function setTemplateSelectedId(submitButton){
                $("#selectTemplateId").val($(submitButton).parent().attr("templateId"));
            }

        </script>
    </head>

    <body onload="addValidationErrors();">

        <div class="wrapper">
            <div class="container">
                <%@ include file="/includeHeadApplication.jsp" %>
                <div class="content">
                    <div class="contentselecttemplates">
                        <% request.setAttribute("selected", SetSiteTemplateAction.class); %>
                        <% request.setAttribute("siteId", action.getSiteId()); %>
                        <% request.setAttribute("createChildSite", false); %>
                        <jsp:include page="/WEB-INF/pages/setSiteTemplateMenu.jsp" flush="true"/>

                        <% if (!action.isEditingMode()) { %>
                            <b><international:get name="step2Of3"/></b>
                        <% } %>

                        <% if (action.isEditingMode()) { %>
                            <div class="b_16"><br><br><%= action.getSiteTitle() %><br><br></div>
                        <% } %>

                        <h1><international:get name="HEADER_INFORMATION"/></h1>

                        <stripes:form beanclass="com.shroggle.presentation.site.template.SetSiteTemplateAction"
                                      onsubmit="return checkSelectedTheme();">
                            <stripes:hidden id="selectTemplateId" name="selectTemplateId"/>
                            <stripes:hidden id="selectBlueprintId" name="selectBlueprintId"/>
                            <stripes:hidden id="selectThemeId" name="selectThemeId"/>
                            <stripes:hidden name="siteId"/>
                            <stripes:hidden name="editingMode"/>

                            <div style="margin: 10px 0 10px 0">
                                <page:errors/>
                            </div>

                            <% if (action.getTemplateBlueprintsLogic().size() > 0) { %>
                                <h2><international:get name="BLUEPRINT_SUB_HEADER_INFORMATION"/></h2><br>

                                <div style="text-align:center;">
                                    <international:get name="BLUEPRINT_DESCRIPTION_INFORMATION"/>
                                </div>
                            <% } %>

                            <div class="templatestable clear">
                                <% int templateIndex = 0; %>
                                <% while (templateIndex < action.getTemplateBlueprintsLogic().size()) { %>
                                    <% final TemplateManager templateBlueprint = action.getTemplateBlueprintsLogic().get(templateIndex); %>

                                        <%-- TEMPLATE BLOCK. BLUEPRINT. FIRST TEMPLATE --%>
                                        <% request.setAttribute("template", templateBlueprint); %>
                                        <% request.setAttribute("second", false); %>
                                        <jsp:include page="templateBlock.jsp"/>

                                    <% templateIndex++; %>
                                    <% TemplateManager secondTemplateBlueprint; %>
                                    <% if (templateIndex < action.getTemplateBlueprintsLogic().size()) { %>
                                        <% secondTemplateBlueprint = action.getTemplateBlueprintsLogic().get(templateIndex); %>

                                        <%-- TEMPLATE BLOCK. BLUEPRINT. SECOND TEMPLATE --%>
                                        <% request.setAttribute("template", secondTemplateBlueprint); %>
                                        <% request.setAttribute("second", true); %>
                                        <jsp:include page="templateBlock.jsp"/>

                                        <% templateIndex++; %>
                                    <% } %>

                                    <div style="clear: both;"></div>

                                <% } %>
                            </div>

                            <% List<TemplateManager> templatesManager = action.getTemplatesLogic(); %>
                            <% if (templatesManager.size() > 0) { %>
                                <h2><international:get name="SUB_HEADER_INFORMATION"/></h2><br>
                                <international:get name="DESCRIPTION_INFORMATION"/>

                                <div class="templatestable clear">
                                    <% templateIndex = 0; %>
                                    <% while (templateIndex < templatesManager.size()) { %>
                                        <% final TemplateManager template = templatesManager.get(templateIndex); %>
                                        <% if ("optima".equals(template.getTemplate().getDirectory())) continue; %>

                                            <%-- TEMPLATE BLOCK. FIRST TEMPLATE --%>
                                            <% request.setAttribute("template", template); %>
                                            <% request.setAttribute("second", false); %>
                                            <jsp:include page="templateBlock.jsp"/>

                                            <% templateIndex++; %>
                                            <% TemplateManager secondTemplate; %>
                                            <% if (templateIndex < templatesManager.size()) { %>
                                                <% secondTemplate = templatesManager.get(templateIndex); %>

                                                <%-- TEMPLATE BLOCK. SECOND TEMPLATE --%>
                                                <% request.setAttribute("template", secondTemplate); %>
                                                <% request.setAttribute("second", true); %>
                                                <jsp:include page="templateBlock.jsp"/>

                                                <% templateIndex++; %>
                                            <% } %>

                                            <div style="clear: both;"></div>

                                    <% } %>

                                    <script type="text/javascript">

                                        setSelectTheme();

                                    </script>

                                    <br clear=all><br><br>
                                </div>
                            <% } %>
                        </stripes:form>
                        <br clear="all">
                    </div>
                </div>
                <%@ include file="/includeFooterApplication.jsp" %>
            </div>
        </div>
    </body>
</html>