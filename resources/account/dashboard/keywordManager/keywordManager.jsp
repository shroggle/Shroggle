<%@ page import="com.shroggle.presentation.account.dashboard.keywordManager.ShowKeywordManagerService" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="keywordManager"/>
<%
    final ShowKeywordManagerService service = (ShowKeywordManagerService) request.getAttribute("service");
%>
<div class="windowOneColumn">
    <h1><international:get name="header"/></h1>

    <h2><%= service.getSiteManager().getSite().getTitle() %>: <international:get name="subHeader"/></h2>

    <div class="windowTopLine" style="margin-bottom: 5px;">&nbsp;</div>

    <div class="inform_mark">
        <a href="javascript:keywordManager.showMoreInfoWindow();"><international:get name="moreInfo"/></a>
    </div>

    <table class="keywordManagerFirstTable">
        <tr>
            <td>
                <label for="pageNameSelect"><international:get name="pageName"/></label>
            </td>
            <td>
                <select id="pageNameSelect" onchange="keywordManager.updateTable();">
                    <% for (PageManager pageManager : service.getSiteManager().getPages()) { %>
                    <option value="<%= pageManager.getPageId() %>">
                        <%= pageManager.getName() %>
                    </option>
                    <% } %>
                </select>
                <div style="visibility:hidden; display:inline;" id="processingPageDiv">
                    <img src="/images/ajax-loader-minor.gif" alt=""/><international:get name="processingPage"/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="primaryKeyphrase"><international:get name="primaryKeyphrase"/></label>
            </td>
            <td>
                <input id="primaryKeyphrase" type="text" onkeyup="$('#updateKeyphraseLink').css('display', 'inline');
                keywordManager.ifEmptyKeyphrase();"/>
                <a href="javascript:keywordManager.updateKeyphrase();" id="updateKeyphraseLink"
                   style="display:none;"><international:get name="updateKeyphrase"/></a>
                <div style="visibility:hidden; display:inline;" id="processingKeyphraseDiv">
                    <img src="/images/ajax-loader-minor.gif" alt=""/><international:get name="processingKeyphrase"/>
                </div>
            </td>
        </tr>
    </table>

    <div class="keywordManagerMainDiv" id="keywordManagerMainDiv">
        <jsp:include page="keywordManagerTable.jsp"/>
    </div>

    <div align="right">
        <input type="button" value="Close" onmouseout="this.className='but_w73';"
               onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
               onclick="closeConfigureWidgetDiv();">
    </div>
</div>

<div id="keywordManagerMoreInfo" style="display:none;">
    <div class="windowOneColumn">
        <span class="keywordManagerMoreInfoHeader"></span>

        <div class="keywordManagerMoreInfoInfo">
            <div class="keywordManagerMoreInfoSubInfo">
                <b>1.&nbsp;</b><international:get name="moreInfoBlock1"/>
            </div>
            <div class="keywordManagerMoreInfoSubInfo">
                <b>2.&nbsp;</b><international:get name="moreInfoBlock2"/>
                <div class="keywordManagerMoreInfoSubSubInfo">
                    <international:get name="moreInfoBlock2_1"/>
                    <international:get name="moreInfoBlock2_2"/>
                    <international:get name="moreInfoBlock2_3"/>
                </div>
            </div>
            <div class="keywordManagerMoreInfoSubInfo">
                <b>3.&nbsp;</b><international:get name="moreInfoBlock3"/>
                <div class="keywordManagerMoreInfoSubSubInfo">
                    <international:get name="moreInfoBlock3_1"/>
                    <international:get name="moreInfoBlock3_2"/>
                    <international:get name="moreInfoBlock3_3"/>
                </div>
            </div>
            <div class="keywordManagerMoreInfoSubInfo">
                <b>4.&nbsp;</b><international:get name="moreInfoBlock4"/>
                <div class="keywordManagerMoreInfoSubSubInfo">
                    <international:get name="moreInfoBlock4_1"/>
                    <international:get name="moreInfoBlock4_2"/>
                    <international:get name="moreInfoBlock4_3"/>
                </div>
            </div>
            <div class="keywordManagerMoreInfoSubInfo">
                <b>5.&nbsp;</b><international:get name="moreInfoBlock5"/>
                <div class="keywordManagerMoreInfoSubSubInfo">
                    <international:get name="moreInfoBlock5_1"/>
                </div>
            </div>
            <div class="keywordManagerMoreInfoSubInfo">
                <b>6.&nbsp;</b><international:get name="moreInfoBlock6"/>
                <div class="keywordManagerMoreInfoSubSubInfo">
                    <international:get name="moreInfoBlock6_1"/>
                    <international:get name="moreInfoBlock6_2"/>
                    <international:get name="moreInfoBlock6_3"/>
                </div>
            </div>
            <div class="keywordManagerMoreInfoSubInfo">
                <b>7.&nbsp;</b><international:get name="moreInfoBlock7"/>
                <div class="keywordManagerMoreInfoSubSubInfo">
                    <international:get name="moreInfoBlock7_1"/>
                    <international:get name="moreInfoBlock7_2"/>
                    <international:get name="moreInfoBlock7_3"/>
                    <international:get name="moreInfoBlock7_4"/>
                    <international:get name="moreInfoBlock7_5"/>
                </div>
            </div>
        </div>

        <div align="right">
            <input type="button" value="Close" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73" id="windowCancel"
                   onclick="closeConfigureWidgetDiv();">
        </div>
    </div>
</div>
