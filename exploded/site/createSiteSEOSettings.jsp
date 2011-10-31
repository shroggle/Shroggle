<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.CreateSiteAction" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.entity.CodePlacement" %>
<%@ page import="com.shroggle.entity.SEOHtmlCode" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="createSite"/>
<% final CreateSiteAction action = (CreateSiteAction) request.getAttribute("actionBean"); %>
<dl class="w_30">
<dt><label for="authorMetaTag"><international:get name="authorMetaTag"/></label></dt>
<dd>
    <input type="text" class="title" id="authorMetaTag" maxlength="255" name="seoSettings.authorMetaTag"
           value="<%= HtmlUtil.emptyOrValue(action.getSeoSettings().getAuthorMetaTag()) %>">
    <span style="font-size: 8pt;"><international:get name="authorMetaTagComment"/></span>

    <div class="inform_mark_shifted create_site_inform_mark">
        <international:get name="authorMetaTagExplan"/>
    </div>
</dd>

<dt><label for="copyrightMetaTag"><international:get name="copyrightMetaTag"/></label></dt>
<dd>
    <input type="text" class="title" id="copyrightMetaTag" maxlength="255" name="seoSettings.copyrightMetaTag"
           value="<%= HtmlUtil.emptyOrValue(action.getSeoSettings().getCopyrightMetaTag()) %>">
    <span style="font-size: 8pt;"><international:get name="copyrightMetaTagComment"/></span>

    <div class="inform_mark_shifted create_site_inform_mark">
        <international:get name="copyrightMetaTagExplan"/>
    </div>
</dd>

<br>

<div class="siteKeywordTableLabel"><international:get name="customKeywordMetaTags"/></div>
<div class="createSiteCustomMetaTagDiv tbl_dblborder">
    <table class="createSiteCustomMetaTagTable">
        <thead>
        <tr>
            <td style="width:415px"><international:get name="customKeywordColumn"/></td>
            <td><international:get name="customKeywordDeleteColumn"/></td>
        </tr>
        </thead>
    </table>
    <div class="createSiteCustomMetaTagContainer">
        <table width="100%">
            <tbody id="customMetaTagBody" style="font-style:normal;">
            <% for (String customMetaTag : action.getSeoSettings().getCustomMetaTagList()) { %>
            <tr class="customMetaTagRow">
                <td class="createSiteAddCustomMetaTagNameColumn">
                    <input type="text" class="customMetaTagInput txt95" name="seoSettings.customMetaTagList"
                           value="<%= customMetaTag %>"/>
                </td>
                <td class="createSiteAddCustomMetaTagDeleteColumn">
                    <input type="image" src="/images/cross-circle.png" class="customMetaTagDeleteImg" value="Delete"
                           onclick="createSite.removeCustomMetaTag(this);"/>
                </td>
            </tr>
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
                           onclick="createSite.addCustomMetaTag();"
                           class="but_w73" onmouseover="this.className='but_w73_Over';"
                           onmouseout="this.className='but_w73';"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<br clear="all">

<div class="inform_mark_shifted create_site_inform_mark">
    <% final String applicationUrl = ServiceLocator.getConfigStorage().get().getApplicationUrl(); %>
    <international:get name="customMetaTagExplan">
        <international:param value="<%= applicationUrl %>"/>
    </international:get>
</div>

<dt><label for="robotsMetaTag"><international:get name="robotsMetaTag"/></label></dt>
<dd>
    <input type="text" class="title" id="robotsMetaTag" maxlength="255" name="seoSettings.robotsMetaTag"
           value="<%= HtmlUtil.emptyOrValue(action.getSeoSettings().getRobotsMetaTag()) %>">

    <div class="inform_mark_shifted create_site_inform_mark">
        <international:get name="robotsMetaTagExplan"/>
    </div>
</dd>

<br>

<div class="siteKeywordTableLabel"><international:get name="htmlCodeHeader"/></div>
<div class="tbl_dblborder createSiteCustomHtmlDiv">
    <table class="createSiteCustomHtmlTable">
        <thead>
        <tr>
            <td style="width:120px"><international:get name="htmlCodeScriptNameColumn"/></td>
            <td style="width:240px"><international:get name="htmlCodeCodeColumn"/></td>
            <td style="width:75px;"><international:get name="htmlCodePlacementColumn"/></td>
            <td><international:get name="htmlCodeDeleteColumn"/></td>
        </tr>
        </thead>
    </table>
    <div class="createSiteCustomHtmlContainer">
        <table width="100%">
            <tbody id="customHtmlBody" style="font-style:normal;">
            <% for (int i = 0; i < action.getSeoSettings().getHtmlCodeList().size(); i++) { %>
            <% final SEOHtmlCode seoHtmlCode = action.getSeoSettings().getHtmlCodeList().get(i); %>
            <tr class="customHtmlCodeRow">
                <td class="createSiteCustomHtmlCodeNameColumn">
                    <input type="hidden" class="customHtmlCodeNameHiddenInput"
                           name="seoSettings.htmlCodeList[<%= i %>].name"
                           value="<%= seoHtmlCode.getName() %>"/>
                    <%= seoHtmlCode.getName() %>
                </td>
                <td class="createSiteCustomHtmlCodeColumn">
                    <textarea rows="" cols="" class="createSiteCustomHtmlCodeTextArea" readonly='readonly'
                              name="seoSettings.htmlCodeList[<%= i %>].code"
                            ><%= seoHtmlCode.getCode() %>
                    </textarea>
                </td>
                <td class="createSiteCustomHtmlCodePlacementColumn">
                    <input type="hidden" class="customHtmlCodePlacementHiddenInput"
                           name="seoSettings.htmlCodeList[<%= i %>].codePlacement"
                           value="<%= seoHtmlCode.getCodePlacement() %>"/>
                    <%= seoHtmlCode.getCodePlacement() %>
                </td>
                <td class="createSiteCustomHtmlCodeDeleteColumn">
                    <input type="image" src="/images/cross-circle.png" value="Delete" class="customMetaTagDeleteImg"
                           onclick="createSite.removeCustomMetaTag(this);"/>
                </td>
            </tr>
            <% } %>
            <tr class="keywordTableSubheader">
                <td colspan="4" nowrap="nowrap">
                    <b><international:get name="htmlCodeSubHeader"/></b>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <label for="htmlCodeInput" class="addScriptLabel"><international:get
                            name="htmlCodeInputLabel"/></label>
                    <input id="htmlCodeInput" class="createSiteAddScriptInput"/>
                    <br clear="all"/>

                    <label for="htmlCodeTextArea" class="addScriptLabel"><international:get
                            name="htmlCodeTextAreaLabel"/></label>
                    <textarea id="htmlCodeTextArea" class="createSiteAddScriptTextArea"
                              onfocus="trimTextArea(this);" type="text"></textarea>
                    <br clear="all"/>

                    <label class="addScriptLabel"><international:get name="htmlCodeRadioLabel"/></label>
                    <input id="htmlCodeRadioBeginning" type="radio" name="htmlCodeRadio" class="addScriptRadio"
                           checked="checked" value="<%= CodePlacement.BEGINNING %>"/>
                    <label for="htmlCodeRadioBeginning" class="addScriptRadioLabel"><international:get
                            name="htmlCodeRadioBeginning"/></label>
                    <input id="htmlCodeRadioEnd" type="radio" name="htmlCodeRadio" class="addScriptRadio"
                           style="margin-left:25px;" value="<%= CodePlacement.END %>"/>
                    <label for="htmlCodeRadioEnd" class="addScriptRadioLabel"><international:get
                            name="htmlCodeRadioEnd"/></label>
                    <br clear="all"/>

                    <label class="addScriptLabel">&nbsp;</label>

                    <div align="right">
                        <input type="button" value="Add" style="text-align:center;"
                               onclick="createSite.addCustomHtmlCode();"
                               class="but_w73" onmouseover="this.className='but_w73_Over';"
                               onmouseout="this.className='but_w73';"/>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<br clear="all">

<div class="inform_mark_shifted create_site_inform_mark">
    <international:get name="htmlCodeExplan"/>
</div>

<div class="siteKeywordTableLabel"><international:get name="createYourMetaTagGroups"/></div>
<div class="createSiteKeywordsDiv">
    <div class="tbl_dblborder">
        <table>
            <thead>
            <tr>
                <td><international:get name="groupName"/></td>
                <td><international:get name="keywords"/></td>
                <td>&nbsp;</td>
            </tr>
            </thead>
            <tbody id="keywordsGroups">
            <% if (action.getKeywordsGroups() == null || action.getKeywordsGroups().isEmpty()) { %>
            <tr id="keywordsGroup0" class="siteKeywordGroupRow">
                <td><input type="text" id="keywordsGroupName0" name="keywordsGroups[0].name" maxlength="255"
                           class="txt95"></td>
                <td><input type="text" id="keywordsGroupValue0" name="keywordsGroups[0].value" maxlength="255"
                           class="txt95"></td>
                <td class="siteKeywordsDeleteColumn">
                    <input type="hidden" name="keywordsGroups[0].id" value="0">
                    <img src="/images/cross-circle.png" style="cursor:pointer;"
                         alt="Delete" onclick="createSite.deleteKeywordsGroup(this); return false;"/>
                </td>
            </tr>
            <% } else { %>
            <% for (int i = 0; i < action.getKeywordsGroups().size(); i++) { %>
            <% if (action.getKeywordsGroups().get(i) != null) { %>
            <tr id="keywordsGroup<%= i %>" class="siteKeywordGroupRow">
                <% final String keywordsGroupName = action.getKeywordsGroups().get(i).getName(); %>
                <% final String keywordsGroupValue = action.getKeywordsGroups().get(i).getValue(); %>
                <td>
                    <input type="text" id="keywordsGroupName<%= i %>" maxlength="255"
                           name="keywordsGroups[<%= i %>].name"
                           value="<%= keywordsGroupName == null ? "" : keywordsGroupName %>"
                           class="txt95">
                </td>
                <td>
                    <input type="text" id="keywordsGroupValue<%= i %>" maxlength="255"
                           name="keywordsGroups[<%= i %>].value"
                           value="<%= keywordsGroupValue == null ? "" : keywordsGroupValue %>"
                           class="txt95">
                    <input type="hidden" name="keywordsGroups[<%= i %>].id" value="<%= i %>">
                </td>
                <td class="siteKeywordsDeleteColumn"><img src="/images/cross-circle.png" alt="Delete"
                                                          style="cursor:pointer;"
                                                          onclick="createSite.deleteKeywordsGroup(this); return false;"/>
                </td>
            </tr>
            <% } %>
            <% } %>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="createSiteKeywordsAddNewDiv">
        <input type="button" value="Add" onclick="createSite.addKeywordsGroup();"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>
<br clear="all">

<div class="inform_mark_shifted create_site_inform_mark">
    <international:get name="metaTagText"/>
</div>

</dl>