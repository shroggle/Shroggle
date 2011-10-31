<%@ tag import="com.shroggle.presentation.Action" %>
<%@ tag import="net.sourceforge.stripes.validation.ValidationErrors" %>
<%@ tag import="net.sourceforge.stripes.validation.ValidationError" %>
<%@ tag import="java.util.List" %>
<%@ tag import="java.util.Locale" %>
<%@ tag body-content="empty" %>
<%
    final Action action = (Action) request.getAttribute("actionBean");
    if (action == null) {
        throw new UnsupportedOperationException("Can't show errors block with null action bean!");
    }
    final ValidationErrors errorsByKey = action.getContext().getValidationErrors();
%>
<% if (errorsByKey != null && errorsByKey.size() != 0) { %>
    <div id="errors" class="error">
        <% for (final String errorKey : errorsByKey.keySet()) { %>
            <% final List<ValidationError> errors = errorsByKey.get(errorKey); %>
            <% for (final ValidationError error : errors) { %>
                <div id="error_<%= errorKey %>" errorFieldId="<%= errorKey %>"><%= error.getMessage(Locale.US) %></div>
            <% } %>
        <% } %>
    </div>
<% } else { %>
        <%-- We do not need empty error class here. This tag used for pages and pages are reloaded after submit. --%>
    <div id="errors"></div>
<%}%>

