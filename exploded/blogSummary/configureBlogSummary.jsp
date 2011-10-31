<%@ page import="com.shroggle.presentation.blogSummary.ConfigureBlogSummaryService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.presentation.blogSummary.BlogSummaryData" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureBlogSummary"/>
<%
    final ConfigureBlogSummaryService service = (ConfigureBlogSummaryService) request.getAttribute("blogSummaryService");

    final DraftBlogSummary blogSummary = service.getBlogSummary();

    final List<BlogSummaryData> userBlogsFromAllSites = service.getBlogsFromAllSites();
    final List<BlogSummaryData> userBlogsFromCurrentSite = service.getBlogsFromCurrentSite();

    List<Integer> selectedCrossWidgetsId = blogSummary.getIncludedCrossWidgetId();
    List<Integer> selectedBlogIds = service.getSelectedBlogIds();

    request.setAttribute("selectedBlogIds", selectedBlogIds);
    request.setAttribute("selectedCrossWidgetsId", selectedCrossWidgetsId);
%>

<input type="hidden" id="selectedBlogSummaryId" value="<%= blogSummary.getId() %>"/>
<input type="hidden" id="blogsIdFromCurrentSite" value="<%= service.getBlogsIdsFromCurrentSite() %>">
<input type="hidden" id="blogsIdFromCurrentAccount" value="<%= service.getBlogsIdsFromCurrentAccount() %>">
<input type="hidden" value="<international:get name="defaultHeader"/>" id="itemDescriptionDefaultHeader"/>
<input type="hidden" value="<international:get name="displayHeader"/>" id="itemDescriptionDisplayHeader"/>
<input type="hidden" value="<international:get name="blogSummaryNameCannotBeEmpty"/>"
       id="blogSummaryNameCannotBeEmpty"/>
<input type="hidden" value="<international:get name="emptyBlogSummaryDisplayedData"/>"
       id="emptyBlogSummaryDisplayedData"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="itemSettingsWindowDiv">

<h1><international:get name="header"/></h1>
<% if (service.getWidgetTitle() != null) { %>
<widget:title customServiceName="blogSummaryService"/>
<% } %>
<div class="windowTopLine">&nbsp;</div>

<div class="emptyError" id="blogSummaryErrors"></div>

<div class="readOnlyWarning" style="display:none;" id="blogSummaryReadOnlyMessage">
    <international:get name="readOnlyAccess"/>
</div>

<%--------------------------------------new blog summary name, header ------------------------------------------------%>
<dl>
    <div style="float:left; margin-right:10px;">
        <label for="newBlogSummaryName">
            <international:get name="name"/>
        </label>&nbsp;
    </div>
    <div style="float:left;">
        <input type="text" id="newBlogSummaryName" class="title" maxlength="255"
               value="<%= blogSummary.getName() %>">
    </div>
    <%----------------------------------------------text editor-------------------------------------------------------%>
    <div id="BlogSummaryHeader" style="display: none;"><%= blogSummary.getBlogSummaryHeader() %>
    </div>
    <input type="hidden" id="showBlogSummaryHeader" value="<%= blogSummary.isDisplayBlogSummaryHeader() %>">

    <div style="float:left;margin-left:70px;margin-right:10px;"
         onmouseover="bindTooltip({element:this, contentId:'BlogSummaryHeader'});">
        <international:get name="blogSummaryHeader"/>
    </div>
    <div style="float:left;">
        <a id="blogSummaryShowEditorLink"
           href="javascript:showConfigureItemDescription({id:'BlogSummary'});"
           onmouseover="bindTooltip({element:this, contentId:'BlogSummaryHeader'});">
            <international:get name="edit"/>
        </a>
    </div>
    <%----------------------------------------------text editor-------------------------------------------------------%>
</dl>
<br clear="all">
<%---------------------------------new blog summary radio, text input field-------------------------------------------%>

<div style="margin:10px 0 5px 10px;">
<div style="margin-bottom:15px;">
    <div style=" margin-right:40px;">
        <international:get name="selectBlogsToIncludeInTheBlogSummaryText"/>
    </div>
    <%----------------------------------current site, all sites blogs radio-------------------------------------------%>
    <div style="margin: 7px 0 0 10px;width:150px;">
        <div style="margin-bottom:5px;">
            <input type="radio" id="currentSite" name="includedBlogs"
                <% if (blogSummary.isCurrentSiteBlogs()) { %> checked <% } %>
                   onclick="currentSiteBlogs();">
            <label for="currentSite">
                &nbsp;<international:get name="currentSiteOnly"/>
            </label>
        </div>
        <div>
            <input type="radio" id="allBlogs" name="includedBlogs"
                <% if(blogSummary.isAllSiteBlogs()) { %> checked <% } %>
                   onclick="allAvailableBlogs();">
            <label for="allBlogs">
                &nbsp;<international:get name="allAvailableBlogs"/>
            </label>
        </div>
    </div>
    <%----------------------------------current site, all sites blogs radio-------------------------------------------%>
</div>

<div class="blogSummaryBlogsArea">
    <table style="table-layout:fixed;width:100%;">
        <tbody>
        <tr style="height:30px;">
            <td style="width:60px;text-align:center;vertical-align:middle;font-weight:bold;">
                <international:get name="select"/>
            </td>
            <td style="width:200px;text-align:center;vertical-align:middle;font-weight:bold;">
                <international:get name="blogName"/>
            </td>
            <td style="vertical-align:middle;font-weight:bold;">
                <international:get name="summaryLinksTo"/>
            </td>
        </tr>
        </tbody>
    </table>
    <div style="overflow-y:auto;overflow-x:hidden;height:190px;">
        <%--------------------------------------blogs from current site-----------------------------------------------%>
        <div id="displayBlogFromCurrentSite" <% if (!blogSummary.isCurrentSiteBlogs()) { %>
             style="display:none;" <% } %>>
            <% request.setAttribute("availableBlogs", userBlogsFromCurrentSite);
                request.setAttribute("availableBlogsAreaId", "CurrentSite"); %>
            <jsp:include page="availableBlogs.jsp" flush="true"/>
        </div>
        <%--------------------------------------blogs from current site-----------------------------------------------%>
        <%---------------------------------------blogs from all sites-------------------------------------------------%>
        <div id="displayBlogFromCurrentAccount" <% if (!blogSummary.isAllSiteBlogs()) { %>
             style="display:none;" <% } %>>
            <% request.setAttribute("availableBlogs", userBlogsFromAllSites);
                request.setAttribute("availableBlogsAreaId", "CurrentAccount"); %>
            <jsp:include page="availableBlogs.jsp" flush="true"/>
        </div>
        <%---------------------------------------blogs from all sites-------------------------------------------------%>
    </div>
</div>
<br>
<%-------------------------------------------number of blog posts-----------------------------------------------------%>
<dl class="w_50" style="margin-bottom:10px;">
    <dt>
        <label for="numberOfBlogPosts">
            <international:get name="numberOfBlogPostsToIncludeInTheBlogSummary"/>
        </label>
    </dt>
    <dd>
        <select name="numberOfBlogPosts" id="numberOfBlogPosts">
            <% for (int i = 0; i <= 200; i++) { %>
            <option value="<%= i %>" id="numberOfBlogPosts<%= i %>"
                    <% if (blogSummary.getIncludedPostNumber() == i) { %> selected <% } %>
                    <% if (i > 30) {
                        i += 9;
                    } else if (i > 10) {
                        i += 4;
                    } %>>
                <%= i %>
            </option>
            <% } %>
        </select>
    </dd>
</dl>
<%--------------------------------------------number of blog posts----------------------------------------------------%>
<%--------------------------------------------post display criteria---------------------------------------------------%>
<dl class="w_50" style="margin-bottom:10px;">
    <dt>
        <label for="postDisplayCriteria">
            <international:get name="selectBlogPostsBasedOn"/>
        </label>
    </dt>
    <dd>
        <select id="postDisplayCriteria" class="txt">
            <% for (PostDisplayCriteria displayCriteria : PostDisplayCriteria.values()) { %>
            <option value="<%= displayCriteria.toString() %>"
                    <% if(blogSummary.getPostDisplayCriteria() == displayCriteria) { %>selected<% } %>>
                <international:get name="<%= displayCriteria.toString() %>"/>
            </option>
            <% } %>
        </select>
    </dd>
</dl>
<%-------------------------------------------post display criteria----------------------------------------------------%>
<%---------------------------------------------post sort criteria-----------------------------------------------------%>
<dl class="w_50" style="margin-bottom:10px;">
    <dt>
        <label for="postSortCriteria">
            <international:get name="SortBlogPostsBasedOn"/>
        </label>
    </dt>
    <dd>
        <select id="postSortCriteria" style="width:280px;">
            <% for (PostSortCriteria sortCriteria : PostSortCriteria.values()) { %>
            <option value="<%= sortCriteria.toString() %>"
                    <% if(blogSummary.getPostSortCriteria() == sortCriteria) { %>selected<% } %>>
                <international:get name="<%= sortCriteria.toString() %>"/>
            </option>
            <% } %>
        </select>
    </dd>
</dl>
<%---------------------------------------------post sort criteria-----------------------------------------------------%>
<br>

<div style="margin-bottom:10px;font-weight:bold;">
    <label>
        <international:get name="whatDataShouldBeDisplayedInTheBlogSummary"/>
    </label>
</div>

<%-----------------------------------show first words of the postcheckbox---------------------------------------------%>
<input type="checkbox" id="showPostContents" name="displayedData"
       <% if(blogSummary.isShowPostContents()) { %>checked<% } %>
       onchange="disableNumberOfWordsToDisplaySelect(this.checked);">
<label for="showPostContents" class="margend-0">
    <international:get name="showPostContents"/>
</label>
<%-----------------------------------show first words of the postcheckbox---------------------------------------------%>


<%---------------------------------------------post name checkbox-----------------------------------------------------%>
<input type="checkbox" id="showPostName" name="displayedData"<% if(blogSummary.isShowPostName()) { %> checked <% } %>>
<label for="showPostName" class="margend-0">
    <international:get name="postName"/>
</label>
<%---------------------------------------------post name checkbox-----------------------------------------------------%>

<%--------------------------------------------post author checkbox----------------------------------------------------%>
<input type="checkbox" id="showPostAuthor" name="displayedData"
       <% if(blogSummary.isShowPostAuthor()) { %>checked<% } %>>
<label for="showPostAuthor" class="margend-0">
    <international:get name="postAuthor"/>
</label>
<%--------------------------------------------post author checkbox----------------------------------------------------%>

<%---------------------------------------------post data checkbox-----------------------------------------------------%>
<input type="checkbox" id="showPostDate" name="displayedData" <% if (blogSummary.isShowPostDate()) { %>checked<% } %>>
<label for="showPostDate" class="margend-0">
    <international:get name="postDate"/>
</label>
<%---------------------------------------------post data checkbox-----------------------------------------------------%>

<%---------------------------------------------blog name checkbox-----------------------------------------------------%>
<input type="checkbox" id="showBlogName" name="displayedData"
    <% if (blogSummary.getIncludedCrossWidgetId().size() < 2) { %> disabled="true" <% } %>
       <% if(blogSummary.isShowBlogName()) { %>checked<% } %>>
<label for="showBlogName" class="margend-0">
    <international:get name="blogTitle"/>
</label>
<%---------------------------------------------blog name checkbox-----------------------------------------------------%>

<%-------------------------------------Number of words to display select----------------------------------------------%>
<div style="margin-left:10px;margin-top:10px;">
    <label for="numberOfWordsToDisplay">
        <international:get name="numberOfWordsToDisplay"/>
    </label>
    <select id="numberOfWordsToDisplay" <% if (!blogSummary.isShowPostContents()) { %> disabled="true" <% } %>>
        <option value="5" <% if (blogSummary.getNumberOfWordsToDisplay() == 5) { %> selected <% } %>>5</option>
        <option value="10" <% if (blogSummary.getNumberOfWordsToDisplay() == 10) { %> selected <% } %>>10</option>
        <option value="20" <% if (blogSummary.getNumberOfWordsToDisplay() == 20) { %> selected <% } %>>20</option>
        <option value="30" <% if (blogSummary.getNumberOfWordsToDisplay() == 30) { %> selected <% } %>>30</option>
        <option value="40" <% if (blogSummary.getNumberOfWordsToDisplay() == 40) { %> selected <% } %>>40</option>
        <option value="50" <% if (blogSummary.getNumberOfWordsToDisplay() == 50) { %> selected <% } %>>50</option>
        <option value="70" <% if (blogSummary.getNumberOfWordsToDisplay() == 70) { %> selected <% } %>>70</option>
        <option value="100" <% if (blogSummary.getNumberOfWordsToDisplay() == 100) { %> selected <% } %>>100</option>
        <option value="150" <% if (blogSummary.getNumberOfWordsToDisplay() == 150) { %> selected <% } %>>150</option>
        <option value="200" <% if (blogSummary.getNumberOfWordsToDisplay() == 200) { %> selected <% } %>>200</option>
        <option value="350" <% if (blogSummary.getNumberOfWordsToDisplay() == 350) { %> selected <% } %>>350</option>
        <option value="400" <% if (blogSummary.getNumberOfWordsToDisplay() == 400) { %> selected <% } %>>400</option>
        <option value="0" <% if (blogSummary.getNumberOfWordsToDisplay() == 0) { %> selected <% } %>><international:get
                name="fullText"/></option>
    </select>
</div>
<%-------------------------------------Number of words to display select----------------------------------------------%>
</div>
<br>
<%---------------------------------------------------buttons----------------------------------------------------------%>

<%---------------------------------------------------buttons----------------------------------------------------------%>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureBlogSummaryButtons">
        <input type="button" value="<international:get name="apply"/>" id="windowApply"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73"
               onclick="configureBlogSummary.save(false);">
        <input type="button" value="<international:get name="save"/>" onClick="configureBlogSummary.save(true);"
               id="windowSave"
               class="but_w73" onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';">
        <input type="button" value="<international:get name="cancel"/>" onClick="closeConfigureWidgetDivWithConfirm();"
               id="windowCancel"
               class="but_w73" onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';">
    </div>
</div>