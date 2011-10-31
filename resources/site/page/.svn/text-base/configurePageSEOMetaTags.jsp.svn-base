<%@ page import="com.shroggle.presentation.site.page.PageToEditGetterService" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configurePageSEOMetaTags"/>
<% final PageToEditGetterService service = (PageToEditGetterService) request.getAttribute("pageToEditGetterService"); %>
<% final boolean isEdit = service.getPageToEdit() != null; %>

<div class="itemSettingsWindowDiv">
    <input type="hidden" id="pageToEditId" value="<%= service.getPageToEdit().getPageId() %>"/>

    <% if (isEdit) { %>
    <h1><international:get name="editSubHeader"/></h1>
    <% } else { %>
    <h1><international:get name="addSubHeader"/></h1>
    <% } %>

    <page:title>
        <jsp:attribute name="customServiceName">pageToEditGetterService</jsp:attribute>
    </page:title>

    <div class="windowTopLine">&nbsp;</div>

    <dl class="w_20 page_settings">
        <dt><label for="pageDescription"><international:get name="pageDescription"/></label></dt>
        <dd>
            <input type="text" class="title" id="pageDescription" maxlength="255"
                   value="<%= isEdit ? HtmlUtil.emptyOrValue(service.getPageToEdit().getSeoSettings().getPageDescription()) : "" %>">
            <span style="font-size: 8pt;"><international:get name="descriptionRightInfo"/></span>

            <div class="inform_mark_shifted">
                <international:get name="thePageDescriptionWillBeUsed"/>
                <a href="javascript:showPageVersionDescriptionMoreInfo();"><international:get
                        name="descriptionMoreInfo"/></a>
            </div>
        </dd>

        <dt><label for="pageTitleMetaTag"><international:get name="pageTitleMetaTag"/></label></dt>
        <dd>
            <input type="text" class="title" id="pageTitleMetaTag" maxlength="255"
                   value="<%= isEdit ? HtmlUtil.emptyOrValue(service.getPageToEdit().getSeoSettings().getTitleMetaTag()) : "" %>">
            <span style="font-size: 8pt;"><international:get name="pageTitleMetaTagComment"/></span>

            <div class="inform_mark_shifted">
                <international:get name="pageTitleExplan"/>
            </div>
        </dd>

        <dt><label for="authorMetaTag"><international:get name="authorMetaTag"/></label></dt>
        <dd>
            <input type="text" class="title" id="authorMetaTag" maxlength="255"
                   value="<%= isEdit ? HtmlUtil.emptyOrValue(service.getPageToEdit().getSeoSettings().getAuthorMetaTag()) : HtmlUtil.emptyOrValue(service.getSite().getSeoSettings().getAuthorMetaTag()) %>">
            <span style="font-size: 8pt;"><international:get name="authorMetaTagComment"/></span>

            <div class="inform_mark_shifted">
                <international:get name="authorMetaTagExplan"/>
            </div>
        </dd>

        <dt><label for="copyrightMetaTag"><international:get name="copyrightMetaTag"/></label></dt>
        <dd>
            <input type="text" class="title" id="copyrightMetaTag" maxlength="255"
                   value="<%= isEdit ? HtmlUtil.emptyOrValue(service.getPageToEdit().getSeoSettings().getCopyrightMetaTag()) : HtmlUtil.emptyOrValue(service.getSite().getSeoSettings().getCopyrightMetaTag()) %>">
            <span style="font-size: 8pt;"><international:get name="copyrightMetaTagComment"/></span>

            <div class="inform_mark_shifted">
                <international:get name="copyrightMetaTagExplan"/>
            </div>
        </dd>

        <div class="keywordTableLabel"><international:get name="customKeywordMetaTags"/></div>
        <div class="pageCustomMetaTagDiv tbl_dblborder">
            <table class="pageCustomMetaTagTable">
                <thead>
                <tr>
                    <td style="width:465px"><international:get name="customKeywordColumn"/></td>
                    <td><international:get name="customKeywordDeleteColumn"/></td>
                </tr>
                </thead>
            </table>
            <div class="customMetaTagContainer">
                <table width="100%">
                    <tbody id="customMetaTagBody">
                    <% if (isEdit) { %>
                    <% for (final String customMetaTag : service.getPageToEdit().getSeoSettings().getCustomMetaTagList()) { %>
                    <tr>
                        <td class="addCustomMetaTagNameColumn">
                            <textarea rows="1" cols="80"
                                      class="customMetaTagInput txt95 autoHeight"><%= customMetaTag %>
                            </textarea>
                        </td>
                        <td class="addCustomMetaTagDeleteColumn">
                            <input type="image" src="/images/cross-circle.png" class="customMetaTagDeleteImg"
                                   value="Delete"
                                   onclick="configurePageSeoMetaTags.removeCustomMetaTag(this);"/>
                        </td>
                    </tr>
                    <% } %>
                    <% } %>
                    <tr class="keywordTableSubheader">
                        <td colspan="2" nowrap="nowrap">
                            <b><international:get name="customKeywordSubHeader"/></b>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="text" id="addCustomMetaTagInput" class="txt80"/>
                            <input type="button" value="Add" style="text-align:center;"
                                   onclick="configurePageSeoMetaTags.addCustomMetaTag();"
                                   class="but_w73" onmouseover="this.className='but_w73_Over';"
                                   onmouseout="this.className='but_w73';"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <br clear="all">

        <div class="inform_mark_shifted">
            <% final String applicationUrl = ServiceLocator.getConfigStorage().get().getApplicationUrl(); %>
            <international:get name="customMetaTagExplan">
                <international:param value="<%= applicationUrl %>"/>
            </international:get>
        </div>

        <dt><label for="robotsMetaTag"><international:get name="robotsMetaTag"/></label></dt>
        <dd>
            <input type="text" class="title" id="robotsMetaTag" maxlength="255"
                   value="<%= isEdit ? HtmlUtil.emptyOrValue(service.getPageToEdit().getSeoSettings().getRobotsMetaTag()) : "" %>">

            <div class="inform_mark_shifted">
                <international:get name="robotsMetaTagExplan"/>
            </div>
        </dd>
    </dl>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" onclick="configurePageSeoMetaTags.save(false);"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowApply">
        <input type="button" value="Save" onclick="configurePageSeoMetaTags.save(true);"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowSave">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>