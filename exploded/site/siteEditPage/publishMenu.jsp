<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page import="com.shroggle.presentation.site.SiteEditPageAction" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="siteEditPage"/>
<%--
    @author Balakirev Anatoliy
--%>
<% final SiteEditPageAction action = (SiteEditPageAction) request.getAttribute("actionBean"); %>
<div class="siteEditPageMenuElementDiv siteEditPageMenuElement cursorPointer"
     onmouseover="siteEditPageMenu.showMenu(this);"
     onmouseout="siteEditPageMenu.hideMenu(this);">
    <a href="javascript:postToLive(<%= action.getCanBePublished().isCanBePublished() %>, '<%= action.getCanBePublished().getStartDateString() %>', null);">
    <div class="textAlignCenter">                                                                                                                                  
        <img src="../../images/siteEditPage/btn_publish.png"
             alt="<international:get name="publish"/>"
        border="0">
    </div>

    <div class="textAlignCenter">
        <international:get name="publish"/>
    </div></a>

    <div menuContent="true" class="siteEditPageMenu <%= request.getAttribute("gray") %>">
        <div class="siteEditPageMenuLine siteEditPageClickableMenu">
            <a href="javascript:postToLive(<%= action.getCanBePublished().isCanBePublished() %>, '<%= action.getCanBePublished().getStartDateString() %>', null)"
               ><%if (action.getSiteType() == SiteType.BLUEPRINT) {%><international:get
                    name="treePostLiveBlueprint"/><%} else {%><international:get
                    name="treePostLive"/><%}%></a></div>
        <div class="siteEditPageMenuLine siteEditPageClickableMenu">
            <a href="javascript:postToLive(<%= action.getCanBePublished().isCanBePublished() %>, '<%= action.getCanBePublished().getStartDateString() %>', <%= action.getSiteId() %>)"
               ><%if (action.getSiteType() == SiteType.BLUEPRINT) {%><international:get
                    name="treePostLiveBlueprintAll"/><%} else {%><international:get
                    name="treePostLiveAll"/><%}%></a></div>
    </div>
</div>