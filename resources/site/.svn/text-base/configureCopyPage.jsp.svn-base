<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.presentation.site.ConfigureCopyPageService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureCopyPage"/>
<% final ConfigureCopyPageService service = (ConfigureCopyPageService) request.getAttribute("service"); %>
<div class="windowOneColumn">
    <h1><international:get name="copyPageConfiguration"/></h1>
    <page:title/>

    <br>
    <br>

    <% if (service.getDraftItems().size() > 0) { %>
    <b><international:get name="itemsOnPage"/>:</b><br><br>
    <% } %>

    <div style="overflow-y: auto; overflow-x: hidden; height: 300px;">
        <% for (final DraftItem draftItem : service.getDraftItems()) {
            final String itemType = (draftItem instanceof Gallery && ((Gallery) draftItem).isIncludesVotingModule()) ? "VOTING" : draftItem.getItemType().toString(); %>
        <div style="margin-bottom: 20px;">
            <b><international:get name="<%= itemType %>"/>:&nbsp;<%= draftItem.getName() %>
            </b>
            <table>
                <tr>
                    <td style="padding-left: 20px;">
                        <input class="configureCopyPageShare" checked="checked" type="radio"
                               value="<%= draftItem.getId() %>"
                               name="dratItem<%= draftItem.getId() %>"
                               id="configureCopyPage<%= draftItem.getId() %>Share"
                               onclick="configureCopyPageChangeCopy(this);">
                        <label for="configureCopyPage<%= draftItem.getId() %>Share"><international:get
                                name="share"/></label><br>

                        <input  class="configureCopyPageCopy" type="radio" name="dratItem<%= draftItem.getId() %>"
                               id="configureCopyPage<%= draftItem.getId() %>Copy"
                               onclick="configureCopyPageChangeCopy(this);" value="<%= draftItem.getId() %>">
                        <label for="configureCopyPage<%= draftItem.getId() %>Copy"><international:get
                                name="createCopy"/></label><br>

                        <input type="radio" name="dratItem<%= draftItem.getId() %>"
                               id="configureCopyPage<%= draftItem.getId() %>Skip"
                               onclick="configureCopyPageChangeCopy(this);" value="<%= draftItem.getId() %>">
                        <label for="configureCopyPage<%= draftItem.getId() %>Skip"><international:get
                                name="skip"/></label>
                    </td>
                    <td>
                        <input type="checkbox" id="configureCopyPage<%= draftItem.getId() %>ClearFontColors"
                               disabled="disabled">
                        <label for="configureCopyPage<%= draftItem.getId() %>ClearFontColors">
                            <international:get name="clearFontAndColorsSettings"/>
                        </label><br>

                        <input type="checkbox" id="configureCopyPage<%= draftItem.getId() %>ClearBorderBackground"
                               disabled="disabled">
                        <label for="configureCopyPage<%= draftItem.getId() %>ClearBorderBackground">
                            <international:get name="clearBorderAndBackgroundSettings"/>
                        </label>
                    </td>
                </tr>
            </table>
        </div>
        <% } %>
    </div>

    <br><br>

    <input type="hidden" id="configureCopyPageId" value="<%= service.getPageId() %>">

    <div align="right">
        <input type="button" value="<international:get name="save"/>"
               onclick="copyPage();" id="windowSave"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';"
               class="but_w73">
        <input type="button" id="windowCancel"
               value="<international:get name="cancel"/>" onclick="closeConfigureWidgetDivWithConfirm();"
               onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';"
               class="but_w73">
    </div>
</div>

