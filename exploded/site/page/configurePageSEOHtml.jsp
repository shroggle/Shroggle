<%@ page import="com.shroggle.presentation.site.page.PageToEditGetterService" %>
<%@ page import="com.shroggle.entity.SEOHtmlCode" %>
<%@ page import="com.shroggle.entity.CodePlacement" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configurePageSEOHtml"/>
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
        <div class="keywordTableLabel"><international:get name="htmlCodeHeader"/></div>
        <div class="tbl_dblborder customHtmlCode" style="width:550px;float: left; display:inline;margin-right: 10px;">
            <table style="width:550px;">
                <thead>
                <tr>
                    <td style="width:120px"><international:get name="htmlCodeScriptNameColumn"/></td>
                    <td style="width:270px"><international:get name="htmlCodeCodeColumn"/></td>
                    <td style="width:80px;"><international:get name="htmlCodePlacementColumn"/></td>
                    <td><international:get name="htmlCodeDeleteColumn"/></td>
                </tr>
                </thead>
            </table>
            <div class="customHtmlContainer">
                <table width="100%">
                    <tbody id="customHtmlBody">
                    <% if (isEdit) { %>
                    <% for (SEOHtmlCode seoHtmlCode : service.getPageToEdit().getSeoSettings().getHtmlCodeList()) { %>
                    <tr class="customHtmlCodeRow">
                        <td class="customHtmlCodeNameColumn">
                            <input type="hidden" class="customHtmlCodeNameHiddenInput"
                                   value="<%= seoHtmlCode.getName() %>"/>
                            <%= seoHtmlCode.getName() %>
                        </td>
                        <td class="customHtmlCodeColumn">
                            <textarea rows="" cols="" class="customHtmlCodeTextArea"
                                      disabled="disabled"><%= seoHtmlCode.getCode() %>
                            </textarea>
                        </td>
                        <td class="customHtmlCodePlacementColumn">
                            <input type="hidden" class="customHtmlCodePlacementHiddenInput"
                                   value="<%= seoHtmlCode.getCodePlacement() %>"/>
                            <%= seoHtmlCode.getCodePlacement() %>
                        </td>
                        <td class="customHtmlCodeDeleteColumn">
                            <input type="image" src="/images/cross-circle.png" value="Delete"
                                   class="customMetaTagDeleteImg"
                                   onclick="configurePageSeoMetaTags.removeCustomMetaTag(this);"/>
                        </td>
                    </tr>
                    <% } %>
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
                            <input id="htmlCodeInput" class="addScriptInput"/>
                            <br clear="all"/>

                            <label for="htmlCodeTextArea" class="addScriptLabel"><international:get
                                    name="htmlCodeTextAreaLabel"/></label>
                            <textarea id="htmlCodeTextArea" class="addScriptTextArea" onfocus="trimTextArea(this);"
                                      type="text"></textarea>
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

                            <div id="htmlCodeExceptionDiv" class="inlineError"></div>

                            <div align="right">
                                <input type="button" value="Add" style="text-align:center;"
                                       onclick="configurePageSeoHtml.addCustomHtmlCode();"
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
        <br>

        <div class="inform_mark_shifted">
            <international:get name="htmlCodeExplan"/>
        </div>
    </dl>
</div>

<div class="itemSettingsButtonsDiv">
    <div class="itemSettingsButtonsDivInner" align="right" id="configureRegistrationButtons">
        <input type="button" value="Apply" onclick="configurePageSeoHtml.save(false);" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowApply">
        <input type="button" value="Save" onclick="configurePageSeoHtml.save(true);" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowSave">
        <input type="button" value="Cancel" onclick="closeConfigureWidgetDivWithConfirm();" id="windowCancel"
               onmouseout="this.className='but_w73';" onmouseover="this.className='but_w73_Over';" class="but_w73">
    </div>
</div>