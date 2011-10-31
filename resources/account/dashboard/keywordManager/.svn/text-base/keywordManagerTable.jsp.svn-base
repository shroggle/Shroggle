<%@ page import="com.shroggle.logic.user.dashboard.keywordManager.KeywordManager" %>
<%@ page import="com.shroggle.logic.user.dashboard.keywordManager.SEOTerm" %>
<%@ page import="com.shroggle.logic.user.dashboard.keywordManager.KeywordManagerData" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.shroggle.presentation.site.page.ConfigurePageSettingsTab" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="keywordManager"/>
<%
    final KeywordManager keywordManager = (KeywordManager) request.getAttribute("keywordManager");
    final int siteId = (Integer) request.getAttribute("siteId");
    final int pageId = (Integer) request.getAttribute("pageId");
    final Map<SEOTerm, KeywordManagerData> dataList = keywordManager.buildDatas();
%>
<table>
<thead>
<tr>
    <td style="width:150px">
        <international:get name="contentType"/>
    </td>
    <td style="width:350px">
        <international:get name="content"/>
    </td>
    <td style="width:180px">
        <international:get name="uniqueDensity"/>
    </td>
    <td style="width:140px">
        <international:get name="keyphrase"/>
    </td>
</tr>
</thead>
<tbody>
<%-- Meta tag keywords --%>
<tr>
    <td>
        <a href="javascript:configurePageSettings.show({pageId:<%= pageId %>, siteId:<%= siteId %>, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})"
                ><international:get name="metaTagKeywords"/></a>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.META_KEYWORDS).isPresent()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv" style="height:30px;">
            <international:get name="contentInner"/>&nbsp;<%= dataList.get(SEOTerm.META_KEYWORDS).getElementContent() %>
            &nbsp;
        </div>
    </td>
    <td>
        <% if (dataList.get(SEOTerm.META_KEYWORDS).isPresent()) { %>
        <% if (dataList.get(SEOTerm.META_KEYWORDS).isUnique()) { %>
        <img src="/images/circle_green.png" alt=""><international:get name="unique"/>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="duplicate"/>
        <% } %>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="notFound"/>
        <% } %>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="density"/>&nbsp;<%= dataList.get(SEOTerm.META_KEYWORDS).getDensity() %>&nbsp;
        </div>
    </td>
    <td>
        <div id="META_KEYWORDSKeyphraseUndefined" class="keyphraseUndefined" style="display:inline;">
            <img src="/images/circle_yellow.png" alt=""><international:get name="undefined"/>
        </div>
        <div id="META_KEYWORDSKeyphraseNotPresent" class="keyphraseNotPresent" style="display:none;">
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
        </div>
        <div id="META_KEYWORDSKeyphrasePresent" class="keyphrasePresent" style="display:none;">
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
        </div>
        <div class="keywordManagerContentDiv keyphraseDensity" id="META_KEYWORDSKeyphraseDensity">
            <international:get name="densityNotEntered"/>
        </div>
    </td>
</tr>
<%-- Meta tag description --%>
<tr>
    <td>
        <a href="javascript:configurePageSettings.show({pageId:<%= pageId %>, siteId:<%= siteId %>, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})"
                ><international:get name="metaTagDescription"/></a>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.META_DESCRIPTION).isPresent()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv" style="height:30px;">
            <international:get
                    name="contentInner"/>&nbsp;<%= dataList.get(SEOTerm.META_DESCRIPTION).getElementContent() %>
            &nbsp;
        </div>
    </td>
    <td>
        <% if (dataList.get(SEOTerm.META_DESCRIPTION).isPresent()) { %>
        <% if (dataList.get(SEOTerm.META_DESCRIPTION).isUnique()) { %>
        <img src="/images/circle_green.png" alt=""><international:get name="unique"/>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="duplicate"/>
        <% } %>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="notFound"/>
        <% } %>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="density"/>&nbsp;<%= dataList.get(SEOTerm.META_DESCRIPTION).getDensity() %>&nbsp;
        </div>
    </td>
    <td>
        <div id="META_DESCRIPTIONKeyphraseUndefined" class="keyphraseUndefined" style="display:inline;">
            <img src="/images/circle_yellow.png" alt=""><international:get name="undefined"/>
        </div>
        <div id="META_DESCRIPTIONKeyphraseNotPresent" class="keyphraseNotPresent" style="display:none;">
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
        </div>
        <div id="META_DESCRIPTIONKeyphrasePresent" class="keyphrasePresent" style="display:none;">
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
        </div>
        <div class="keywordManagerContentDiv keyphraseDensity" id="META_DESCRIPTIONKeyphraseDensity">
            <international:get name="densityNotEntered"/>
        </div>
    </td>
</tr>
<%-- Page Title --%>
<tr>
    <td>
        <a href="javascript:configurePageSettings.show({pageId:<%= pageId %>, siteId:<%= siteId %>, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})"
                ><international:get name="pageTitle"/></a>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.PAGE_TITLE).isPresent()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="contentInner"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_TITLE).getElementContent() %>&nbsp;
        </div>
    </td>
    <td>
        <% if (dataList.get(SEOTerm.PAGE_TITLE).isPresent()) { %>
        <% if (dataList.get(SEOTerm.PAGE_TITLE).isUnique()) { %>
        <img src="/images/circle_green.png" alt=""><international:get name="unique"/>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="duplicate"/>
        <% } %>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="notFound"/>
        <% } %>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="density"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_TITLE).getDensity() %>&nbsp;
        </div>
    </td>
    <td>
        <div id="PAGE_TITLEKeyphraseUndefined" class="keyphraseUndefined" style="display:inline;">
            <img src="/images/circle_yellow.png" alt=""><international:get name="undefined"/>
        </div>
        <div id="PAGE_TITLEKeyphraseNotPresent" class="keyphraseNotPresent" style="display:none;">
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
        </div>
        <div id="PAGE_TITLEKeyphrasePresent" class="keyphrasePresent" style="display:none;">
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
        </div>
        <div class="keywordManagerContentDiv keyphraseDensity" id="PAGE_TITLEKeyphraseDensity">
            <international:get name="densityNotEntered"/>
        </div>
    </td>
</tr>
<%-- URL --%>
<tr>
    <td>
        <a href="javascript:configurePageSettings.show({pageId:<%= pageId %>, siteId:<%= siteId %>, tab:'<%= ConfigurePageSettingsTab.PAGE_NAME %>'})"
                ><international:get name="url"/></a>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.PAGE_URL).isPresent()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv">
            <% String url = dataList.get(SEOTerm.PAGE_URL).getElementContent(); %>
            <international:get name="contentInner"/>&nbsp;<a href="<%= url %>"><%= url %></a>
        </div>
    </td>
    <td>
        <% if (dataList.get(SEOTerm.PAGE_URL).isPresent()) { %>
        <% if (dataList.get(SEOTerm.PAGE_URL).isUnique()) { %>
        <img src="/images/circle_green.png" alt=""><international:get name="unique"/>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="duplicate"/>
        <% } %>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="notFound"/>
        <% } %>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="density"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_URL).getDensity() %>&nbsp;
        </div>
    </td>
    <td>
        <div id="PAGE_URLKeyphraseUndefined" class="keyphraseUndefined" style="display:inline;">
            <img src="/images/circle_yellow.png" alt=""><international:get name="undefined"/>
        </div>
        <div id="PAGE_URLKeyphraseNotPresent" class="keyphraseNotPresent" style="display:none;">
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
        </div>
        <div id="PAGE_URLKeyphrasePresent" class="keyphrasePresent" style="display:none;">
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
        </div>
        <div class="keywordManagerContentDiv keyphraseDensity" id="PAGE_URLKeyphraseDensity">
            <international:get name="densityNotEntered"/>
        </div>
    </td>
</tr>
<%-- Page Header (H1) --%>
<tr>
    <td>
        <a href="javascript:window.location='/site/siteEditPage.action?siteId=<%= siteId %>'"
                target="_blank"><international:get name="pageHeader"/></a>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.PAGE_HEADER).isPresent()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="contentInner"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_HEADER).getElementContent() %>&nbsp;
        </div>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.PAGE_HEADER).isPresent()) { %>
            <% if (dataList.get(SEOTerm.PAGE_HEADER).isUnique()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="unique"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="duplicate"/>
            <% } %>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notFound"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="density"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_HEADER).getDensity() %>&nbsp;
        </div>
    </td>
    <td>
        <div id="PAGE_HEADERKeyphraseUndefined" class="keyphraseUndefined" style="display:inline;">
            <img src="/images/circle_yellow.png" alt=""><international:get name="undefined"/>
        </div>
        <div id="PAGE_HEADERKeyphraseNotPresent" class="keyphraseNotPresent" style="display:none;">
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
        </div>
        <div id="PAGE_HEADERKeyphrasePresent" class="keyphrasePresent" style="display:none;">
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
        </div>
        <div class="keywordManagerContentDiv keyphraseDensity" id="PAGE_HEADERKeyphraseDensity">
            <international:get name="densityNotEntered"/>
        </div>
    </td>
</tr>
<%-- Page Content--%>
<tr>
    <td>
        <a href="javascript:window.location='/site/siteEditPage.action?siteId=<%= siteId %>'"
                target="_blank"><international:get name="pageContent"/></a>
    </td>
    <td>
        <div>
            <% if (dataList.get(SEOTerm.PAGE_CONTENT).isPresent()) { %>
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
            <% } else { %>
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
            <% } %>
        </div>
        <div class="keywordManagerContentDiv">
            <international:get name="wordsCount"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_CONTENT).getWordsNumber() %>
            &nbsp;
        </div>
    </td>
    <td>
        <% if (dataList.get(SEOTerm.PAGE_CONTENT).isPresent()) { %>
        <% if (dataList.get(SEOTerm.PAGE_CONTENT).isUnique()) { %>
        <img src="/images/circle_green.png" alt=""><international:get name="unique"/>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="duplicate"/>
        <% } %>
        <% } else { %>
        <img src="/images/circle_red.png" alt=""><international:get name="notFound"/>
        <% } %>
        <div class="keywordManagerContentDiv">
            <international:get
                    name="density"/>&nbsp;<%= dataList.get(SEOTerm.PAGE_CONTENT).getDensity() %>&nbsp;
        </div>
    </td>
    <td>
       <div id="PAGE_CONTENTKeyphraseUndefined" class="keyphraseUndefined" style="display:inline;">
            <img src="/images/circle_yellow.png" alt=""><international:get name="undefined"/>
        </div>
        <div id="PAGE_CONTENTKeyphraseNotPresent" class="keyphraseNotPresent" style="display:none;">
            <img src="/images/circle_red.png" alt=""><international:get name="notPresent"/>
        </div>
        <div id="PAGE_CONTENTKeyphrasePresent" class="keyphrasePresent" style="display:none;">
            <img src="/images/circle_green.png" alt=""><international:get name="present"/>
        </div>
        <div class="keywordManagerContentDiv keyphraseDensity" id="PAGE_CONTENTKeyphraseDensity">
            <international:get name="densityNotEntered"/>
        </div>
    </td>
</tr>
</tbody>
</table>