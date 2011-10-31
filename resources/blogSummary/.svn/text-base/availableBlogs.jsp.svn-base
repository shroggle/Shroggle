<%@ page import="com.shroggle.presentation.blogSummary.BlogSummaryData" %>
<%@ page import="java.util.List" %>
<%--
 @author Balakirev Anatoliy
--%>
<%
    final List<BlogSummaryData> availableBlogs = (List<BlogSummaryData>) request.getAttribute("availableBlogs");
    final String id = (String) request.getAttribute("availableBlogsAreaId");
    final List<Integer> selectedBlogIds = (List<Integer>) request.getAttribute("selectedBlogIds");
    final List<Integer> selectedCrossWidgetsId = (List<Integer>) request.getAttribute("selectedCrossWidgetsId");
%>
<table style="table-layout:fixed;width:100%;">
    <tbody>
    <% for (BlogSummaryData blogSummaryData : availableBlogs) { %>
    <tr style="height:25px;">
        <td style="width:60px;text-align:center;">
            <input type="checkbox" id="<%= blogSummaryData.getBlogId() + id %>"
                   name="blogsFrom<%= id %>"
                <% if (selectedBlogIds.contains(blogSummaryData.getBlogId())) { %> checked <% } %>
                   onChange="checkBlogWithSameId('<%= blogSummaryData.getBlogId() %>', this.checked, '<%= id %>');">
        </td>
        <td style="width:200px;text-align:center;overflow:hidden;">
            <label for="<%= blogSummaryData.getBlogId() + id %>">
                <%= blogSummaryData.getBlogName() %>
            </label>
        </td>
        <td>
            <select id="widgetBlogsFrom<%= id %><%= blogSummaryData.getBlogId() %>" style="width:300px;">
                <% for (BlogSummaryData.BlogSummaryDataWidget widget : blogSummaryData.getWidgetItems()) { %>
                <option value="<%= widget.getCrossWidgetId() %>"
                        <% if (selectedCrossWidgetsId.contains(widget.getCrossWidgetId())) { %> selected <% } %>>
                    <%= widget.getLocation() %>
                </option>
                <% } %>
            </select>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>