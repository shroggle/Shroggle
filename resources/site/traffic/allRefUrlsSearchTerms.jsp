<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<% final Map<String, Integer> refUrlsSearchTerms = (Map<String, Integer>) request.getAttribute("allRefUrlsSearchTerms"); %>
<div class="windowOneColumn" style="padding:10px 15px 10px 15px">
    <div style="max-height:500px;overflow:auto;margin-bottom:10px">
        <table>
            <% boolean odd2 = false;
                for (Map.Entry<String, Integer> entry : refUrlsSearchTerms.entrySet()) { %>
            <tr <%= odd2 ? "class=\"odd\"" : "" %>>
                <td style="padding:3px 0 3px 0">
                    <% final boolean URL_NotSetShowAll = entry.getKey().equals("direct traffic"); %>
                    <%= entry.getValue() %>&nbsp;time(s),
                    <% if (!URL_NotSetShowAll) { %>
                    <a href="<%= entry.getKey() %>">
                        <% } %>
                        <%= entry.getKey() %>
                        <% if (!URL_NotSetShowAll) { %>
                    </a>
                    <% } %>
                </td>
            </tr>
            <% odd2 = !odd2; %>
            <% } %>
        </table>
    </div>
    <div align="right">
        <input value="Close" type="button" class="but_w73" onclick="closeConfigureWidgetDiv();"
               onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';"/>
    </div>
</div>
