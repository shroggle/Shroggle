<!-- DO NOT USE CTRL+ALT+L -->
<%@ page import="com.shroggle.logic.paginator.Paginator" %>
<% final Paginator paginator = (Paginator) request.getAttribute("paginator");
    if (paginator != null && paginator.getPagesCount() > 1) {

        final boolean currentPageIsFirstPage = paginator.getPageNumber() == 1;
        final boolean currentPageIsLastPage = paginator.getPageNumber() == paginator.getPagesCount();
        final int forwardEllipsisPos = paginator.getPageNumber() + paginator.getMaxPagesBackwardAndForwardFromCurrent() + 1;
        final int backwardEllipsisPos = paginator.getPageNumber() - paginator.getMaxPagesBackwardAndForwardFromCurrent() - 1;
        final boolean forwardEllipsisIsNeeded = forwardEllipsisPos < paginator.getPagesCount();
        final boolean forceLastPage = forwardEllipsisPos == paginator.getPagesCount();
        final boolean backwardEllipsisIsNeeded = backwardEllipsisPos > 1;
        final boolean forceFirstPage = backwardEllipsisPos == 1;
        final String updatePaginatorItemsFunction = (String)request.getAttribute("updatePaginatorItemsFunction");
%>

<script type="text/javascript">

    function paginatorNavigatonByKeys(e) {
        var keyCode = (window.event) ? event.keyCode : e.keyCode;

        if ((window.event) ? event.ctrlKey : e.ctrlKey) {
            if (keyCode == 39) {
                paginator.next(<%= updatePaginatorItemsFunction %>);
            } else if (keyCode == 37) {
                paginator.prev(<%= updatePaginatorItemsFunction %>);
            }
        }
    }

    $("body")[0].onkeydown = paginatorNavigatonByKeys;

</script>

<div id="paginatorDiv" class="paginator">
    <input type="hidden" id="paginatorTotalPages" value="<%= paginator.getPagesCount() %>">
    <input type="hidden" id="maxPagesBackwardAndForwardFromCurrent" value="<%= paginator.getMaxPagesBackwardAndForwardFromCurrent() %>">

    <span class="pglink" style="margin-right:5px" id="paginatorPrevLink">
    &larr;&nbsp;<% if (!currentPageIsFirstPage) { %><a href="javascript:paginator.prev(<%= updatePaginatorItemsFunction %>);"
                                                       href="#"><% } %>Prev<% if (!currentPageIsFirstPage) { %></a><% } %>
    </span>

    <%--firstPage ...--%>
    <% if (backwardEllipsisIsNeeded) { %>
    <span class="pglink numberlink" pageNumber="<%= 1 %>"><a
            href="javascript:paginator.byPageNumber(<%= 1 %>, <%= updatePaginatorItemsFunction %>);"><%= 1 %></a></span>
    <span class="pglink numberlink" id="forwardEllipsis" pageNumber="<%= backwardEllipsisPos %>"><a href="javascript:paginator.byPageNumber(<%= backwardEllipsisPos %>, <%= updatePaginatorItemsFunction %>);">&hellip;</a></span>
    <% } else if (forceFirstPage) { %>
    <span class="pglink numberlink" pageNumber="<%= 1 %>"><a
            href="javascript:paginator.byPageNumber(<%= 1 %>, <%= updatePaginatorItemsFunction %>);"><%= 1 %></a></span>
    <% } %>

    <%-- paginator.getMaxPagesBackwardAndForwardFromCurrent() - currnetPage. --%>
    <% int endPosition = backwardEllipsisPos + 1 > 1 ? backwardEllipsisPos + 1 : 1; %>
    <% for (int i = endPosition; i <= paginator.getPageNumber() - 1; i++) { %>
    <span class="pglink numberlink" pageNumber="<%= i %>"><a
            href="javascript:paginator.byPageNumber(<%= i %>, <%= updatePaginatorItemsFunction %>);"><%= i %></a></span>
    <% } %>

    <span class="currentPage" id="currentPaginatorPage" pageNumber="<%= paginator.getPageNumber() %>"><%= paginator.getPageNumber() %></span>

    <%-- paginator.getMaxPagesBackwardAndForwardFromCurrent() + currnetPage. --%>
    <% endPosition = forwardEllipsisPos - 1 < paginator.getPagesCount() ? forwardEllipsisPos - 1 : paginator.getPagesCount(); %>
    <% for (int i = paginator.getPageNumber() + 1; i <= endPosition; i++) { %>
    <%--<span class="currentPage" id="currentPaginatorPage" pageNumber="<%= i %>"><%= i %></span>--%>
    <span class="pglink numberlink" pageNumber="<%= i %>"><a
            href="javascript:paginator.byPageNumber(<%= i %>, <%= updatePaginatorItemsFunction %>);"><%= i %></a></span>
    <% } %>

    <%--... lastPage--%>
    <% if (forwardEllipsisIsNeeded) { %>
    <span class="pglink numberlink" id="forwardEllipsis" pageNumber="<%= forwardEllipsisPos %>"><a href="javascript:paginator.byPageNumber(<%= forwardEllipsisPos %>, <%= updatePaginatorItemsFunction %>);">&hellip;</a></span>
    <span class="pglink numberlink" pageNumber="<%= paginator.getPagesCount() %>"><a
            href="javascript:paginator.byPageNumber(<%= paginator.getPagesCount() %>, <%= updatePaginatorItemsFunction %>);"><%= paginator.getPagesCount() %></a></span>
    <% } else if (forceLastPage) { %>
    <span class="pglink numberlink" pageNumber="<%= paginator.getPagesCount() %>"><a
            href="javascript:paginator.byPageNumber(<%= paginator.getPagesCount() %>, <%= updatePaginatorItemsFunction %>);"><%= paginator.getPagesCount() %></a></span>
    <% } %>

    <span class="pglink" style="margin-left:5px" id="paginatorNextLink">
        <% if (!currentPageIsLastPage) { %><a href="javascript:paginator.next(<%= updatePaginatorItemsFunction %>);"
                                              href="#"><% } %>Next<% if (!currentPageIsLastPage) { %></a><% } %>&nbsp;&rarr;
    </span>
</div>

<% } %>