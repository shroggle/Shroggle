<%@ page import="com.shroggle.entity.Page" %>
<%@ page import="com.shroggle.presentation.site.SiteDetailsAction" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="siteDetails"/>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<% SiteDetailsAction actionBean = (SiteDetailsAction) request.getAttribute("actionBean"); %>
<html>
<head>
    <title><international:get name="siteDetails"/></title>
    <cache:no/>
    <script type="text/javascript">
        function show(id) {
            if (document.getElementById("page" + id).style.display == "none") {
                document.getElementById("page" + id).style.display = "block";
            } else {
                document.getElementById("page" + id).style.display = "none";
            }
        }
    </script>
</head>
<body>
<table width="100%" bgcolor="gainsboro">
    <tr>
        <td>
            <stripes:link
                    beanclass="com.shroggle.presentation.site.ShowAdminInterfaceAction">Back to search</stripes:link>
        </td>
    </tr>
</table>
<br>

<international:get name="detailsFor"/> <b><%=actionBean.getSite().getSubDomain()%>.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %></b> - <%=actionBean.getSite().getTitle()%>.
<br>

<international:get name="selectPageForDetails"/>
<ul>
    <%for (Page p : actionBean.getSite().getPages()) {%>
    <li>
        <a href="javascript:show(<%=p.getPageId()%>)">
        </a>

        <div id="page<%=p.getPageId()%>" style="display:none">
            <table>
                <tr>
                    <td>
                        <international:get name="hits"/>
                    </td>
                    <td>
                        Redo hits info
                    </td>
                </tr>
                <tr>
                    <td>
                        <international:get name="uniqueVisits"/>
                    </td>
                    <td>
                        <%=p.getPageVisits().size()%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <international:get name="refferingSearchTerms"/>
                    </td>
                    <td>
                        <international:get name="undefined"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <international:get name="refferingUrls"/>
                    </td>
                    <td>
                        <%if (actionBean.getReferringUrls().get(p.getPageId()) != null && !actionBean.getReferringUrls().get(p.getPageId()).isEmpty()) {%>
                        <% for (Map.Entry entry : actionBean.getReferringUrls().get(p.getPageId()).entrySet()) {%>
                        <%=entry.getKey()%>, <%=entry.getValue()%> time(s).
                        <%}%>
                        <%}%>
                    </td>
                </tr>
                <tr>
                    <td>
                        <international:get name="totalTimeOnPage"/>
                    </td>
                    <td>
                       Redo overall time spend
                    </td>
                </tr>
            </table>
        </div>
    </li>
    <%}%>
</ul>

</body>
</html>