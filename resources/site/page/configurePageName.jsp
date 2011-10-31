<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.presentation.site.page.PageToEditGetterService" %>
<%@ page import="com.shroggle.entity.PageType" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.entity.KeywordsGroup" %>
<%@ page import="com.shroggle.entity.SiteType" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configurePageName"/>
<% final PageToEditGetterService service = (PageToEditGetterService) request.getAttribute("pageToEditGetterService"); %>
<% final boolean isEdit = service.getPageToEdit().isSaved(); %>

<div class="itemSettingsWindowDiv">
    <input type="hidden" id="pageToEditId" value="<%= service.getPageToEdit().getPageId() %>"/>
    <input type="hidden" id="emptyPageName" value="<international:get name="emptyPageName"/>"/>
    <jsp:include page="moreInfoIncludes/pageVersionTitleMoreInfo.jsp" flush="true"/>
    <jsp:include page="moreInfoIncludes/pageVersionUrlMoreInfo.jsp" flush="true"/>
    <jsp:include page="moreInfoIncludes/pageVersionDescriptionMoreInfo.jsp" flush="true"/>
    <jsp:include page="moreInfoIncludes/pageVersionKeywordsMoreInfo.jsp" flush="true"/>
    <jsp:include page="moreInfoIncludes/pageVersionOwnDomain.jsp" flush="true"/>

    <% if (isEdit) { %>
    <h1><international:get name="editSubHeader"/></h1>
    <% } else { %>
    <h1><international:get name="addSubHeader"/></h1>
    <% } %>

    <page:title>
        <jsp:attribute name="customServiceName">pageToEditGetterService</jsp:attribute>
    </page:title>

    <div class="windowTopLine">&nbsp;</div>

    <div class="emptyError" id="configurePageNameErrors"></div>
    
    <% if (service.getSite().getType() == SiteType.BLUEPRINT) { %>
    <div class="inform_mark"><international:get name="blueprintInform"/></div>
    <% } %>

    <dl class="w_20 page_settings">
        <% if (!isEdit) { %>
        <dt><label for="pageType"><international:get name="typeOfPage"/></label></dt>
        <dd>
            <select id="pageType" style="margin-left:0;">
                <%
                    List<PageType> availablePageTypes = new ArrayList<PageType>(Arrays.asList(PageType.values()));
                    availablePageTypes.removeAll(PageType.getDeniedPageTypes());
                    for (PageType pageType : availablePageTypes) { %>
                <option value="<%= pageType %>"
                        <% if (pageType == PageType.BLANK) { %> selected="selected" <% } %>>
                    <international:get name="<%= pageType.toString() %>"/>
                </option>
                <% } %>
            </select>
        </dd>
        <% } %>
        <dt><label for="pageName1"><international:get name="pageName"/></label></dt>
        <dd>
            <input type="text"
                   <% if (!isEdit) { %>onkeyup="configurePageName.addPageNameToPageUrlAndTitleAsUserTypes(this.value);"<% } %>
                   maxlength="255"
                   <% if (service.getPageToEdit().isBlueprintNotEditable()) { %>disabled="disabled"<% } %>
                   id="pageName1" value="<%= service.getPageToEdit().getName() %>">

            <div class="inform_mark_shifted"><international:get name="PubliclydisplayedPageName"/></div>
        </dd>
        <dt><label for="pageTitle"><international:get name="pageTitle"/></label></dt>
        <dd>
            <input type="text" id="pageTitle"
                   value="<%= service.getPageToEdit().getTitle() %>"
                   class="title" maxlength="255">

            <div class="inform_mark_shifted">
                <international:get name="pageTitleDisplayedText"/>&nbsp;<international:get name="titleSize"/>
                <a href="javascript:showPageTitleInfo()">more info</a>
            </div>
        </dd>
        <dt><label for="pageVersionUrl"><international:get name="pageURL"/></label></dt>
        <dd>
            <label for="pageVersionUrl">http://<%= (service.getSiteUrlPrefix() + "." + ServiceLocator.getConfigStorage().get().getUserSitesUrl()) %>/</label>
            <input type="text" id="pageVersionUrl" maxlength="255" style="margin-left:2px;"
                   <% if (service.getPageToEdit().isBlueprintNotEditable()) { %>disabled="disabled"<% } %>
                   value="<%= HtmlUtil.emptyOrValue(service.getPageToEdit().getDraftPageSettings().getUrl()) %>">

            <div class="inform_mark_shifted">
                <international:get name="specifyTheUurl"/>
                <a href="javascript:showPageVersionUrlInfo();"><international:get name="urlMore"/></a>
            </div>
        </dd>
        <dt><label for="pageVersionAliaseUrl"><international:get name="yourOwnDomainName"/></label></dt>
        <dd>
            <label for="pageVersionAliaseUrl">http://www.</label>
            <input type="text" id="pageVersionAliaseUrl" maxlength="255" style="margin-left:2px;"
                   value="<%= service.getPageToEdit().getOwnDomainName() %>">
            <span class="mark" style="margin-left:10px;">&nbsp;<a
                    href="javascript:showHoToSetUpDomainName()"><international:get
                    name="howToSetUpYourOwnDomainName"/></a></span>


            <div class="inform_mark_shifted"><international:get name="specifyYourOwnDomainName"/></div>
        </dd>
        <% if (!isEdit) { %>
        <dt><label for="pageIncludeInMenus"><international:get name="pageIncludeInMenus"/></label></dt>
        <dd>
            <input type="checkbox" style="border:none; width:auto; margin-left:0;" id="pageIncludeInMenus"
                   checked="checked">
        </dd>
        <% } %>
        <br/>

        <div class="keywordTableLabel"><international:get name="pageKeywordGroups"/></div>
        <div class="addPageKeywordsDiv tbl_dblborder">
            <table class="addPageKeywordsTable">
                <thead>
                <tr>
                    <td style="width:50px !important;"><international:get name="include"/></td>
                    <td style="width:180px !important;"><international:get name="groupName"/></td>
                    <td><international:get name="keywords"/></td>
                </tr>
                </thead>
            </table>
            <div id="keywordContainer" class="keywordContainer">
                <table width="100%">
                    <tbody id="pageKeywordsGroups">
                    <% for (int i = 0; i < service.getKeywordsGroups().size(); i++) { %>
                    <% final KeywordsGroup keywordsGroup = service.getKeywordsGroups().get(i); %>
                    <%
                        boolean exist = false;
                        if (isEdit) {
                            for (final KeywordsGroup pageKeywordsGroup : service.getPageToEdit().getKeywordsGroups()) {
                                if (pageKeywordsGroup == keywordsGroup) {
                                    exist = true;
                                    break;
                                }
                            }
                        }
                    %>
                    <tr>
                        <td style="width:50px !important; text-align:center;">
                            <input type="checkbox" class="pageKeywordsGroupSelect" style="width:auto;"
                                   id="pageKeywordsGroupSelect<%= i %>"  <%= isEdit && exist ? "checked=\"checked\"" : "" %>/>
                        </td>
                        <td style="width:180px !important;">
                            <input type="hidden" id="pageKeywordsGroupId<%= i %>" class="txt80 pageKeywordsGroupId"
                                   value="<%= keywordsGroup.getKeywordsGroupId() %>"/>
                            <input id="siteKeywordName<%= i %>" type="text" readonly="true"
                                   value="<%= keywordsGroup.getName() %>" class="txt95"/>
                        </td>
                        <td><input id="siteKeywordValue<%= i %>" type="text" readonly="true"
                                   value="<%= keywordsGroup.getValue() %>" class="txt95"/></td>
                    </tr>
                    <% } %>
                    <tr class="keywordTableSubheader">
                        <td colspan="3" nowrap><b><international:get name="addAdditionalKeywords"/></b></td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <input id="pageKeywords" type="text" class="txt100" maxlength="255" style="width:98%;"
                                   value="<%= service.getPageToEdit().getKeywords() %>"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <br clear="all">

        <div class="inform_mark_shifted">
            <international:get name="pageKeywordsDisplayedText"/>
            <a href="javascript:showPageVersionKeywordsMoreInfo();"><international:get name="keywordsMoreInfo"/></a>
        </div>
    </dl>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" onclick="configurePageName.save(false);"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowApply">
        <input type="button" value="Save" onclick="configurePageName.save(true);" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowSave">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>