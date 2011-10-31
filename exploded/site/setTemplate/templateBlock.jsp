<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ page import="com.shroggle.logic.site.template.TemplateManager" %>
<%@ page import="com.shroggle.logic.site.template.ThemeLogic" %>
<% TemplateManager template = (TemplateManager) request.getAttribute("template"); %>
<% Boolean second = request.getAttribute("second") == null ? false : (Boolean) request.getAttribute("second"); %>
<div class="<%= second ? "fr" : "fl"%> templateblock" id="template<%= template.getId() %>">
    <div class="templateNameDiv">
        <div class="templateName">
            <%= template.getCorrectName() %>
        </div>
        <div class="exampleslink"
             href="<%= template.getThemes().get(0).getThumbnailUrl() %>"
             onmouseover="templatePreviewOver(this);"
             onmouseout="templatePreviewOut(this);"
             onclick="templatePreviewClick(this);"></div>
        <div style="clear:both"></div>
    </div>
    <div class="templateimg">
        <img src="<%= template.getThemes().get(0).getThumbnailUrl() %>" width="440"
             height="300">
    </div>
    <div class="templateBottomMenu">

        <div class="selectcolorscheme" style="display:none;">
            <% boolean first = true; %>
            <% for (final ThemeLogic theme : template.getThemes()) { %>
            <div class="colorthumb <%= first ? "currentcolor" : "" %>" themeId="<%= theme.getId() %>">
                <a href="javascript:void(0);"
                   onclick="selectTheme(this, <%= template.getId() %>, <%= theme.getId() %>, <%= template.getBlueprintId() %>);">
                    <img src="<%= theme.getColorTilesUrl() %>" width="45" height="45" border="0">
                </a>
            </div>

            <script type="text/javascript">

                themeThumbnailUrls["template<%= template.getId() %>Theme<%= theme.getId() %>"] = "<%= theme.getThumbnailUrl() %>";
                themeImageUrls["template<%= template.getId() %>Theme<%= theme.getId() %>"] = "<%= theme.getImageUrl() %>";

            </script>
            <% first = false; %>
            <% } %>
        </div>

        <div class="designsettings">
            <a href="javascript:void(0);" onclick="showThemes(this);">Options & Variations</a>
        </div>
        <div class="selectdesign" templateId="<%= template.getId() %>">
            <stripes:submit name="execute" value="Proceed with this design" class="but_w200_Over"
                            onclick="setTemplateSelectedId(this)"
                            onmouseout="this.className='but_w200_Over';" onmouseover="this.className='but_w200';"/>
        </div>
    </div>
</div>