<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page import="com.shroggle.logic.user.UserManager" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.entity.AdminLogin" %>
<international:part part="configureAdminLogin"/>
<% final String applicationUrl = (String) request.getAttribute("applicationUrl"); %>
<% final AdminLogin adminLogin = (AdminLogin) request.getAttribute("adminLogin"); %>
<% final UserManager userManager = (UserManager) request.getAttribute("userManager"); %>
<%
    if (adminLogin != null) { %>
        <span style="display: none;" class="adminLoginError">Invalid email or password!</span>
        <a href="javascript:void(0);" onclick="adminLogin(this);" class="adminLoginShow">
    <%
        if (adminLogin.getText() != null) {
            String result = adminLogin.getText();
    %>
        <%= result %>
    <% } else { %>
        <international:get name="defaultText"/>
    <% } %>
</a>
<form method="post" action="http://<%= applicationUrl %>/user/login.action" style="display: none;">
    <table class="adminLoginTable">
        <thead>
            <%
                if (adminLogin.isShowDescription()) {
                    String result = adminLogin.getDescription();
            %>
                <tr><td colspan="2"><%= StringUtil.getEmptyOrString(result) %></td></tr>
            <% } %>
            <tr><td colspan="2">Please sign in to your administrative interface</td></tr>
        </thead>
        <tbody>
            <tr>
                <td><label for="adminLoginEmail<%= adminLogin.getId() %>">Enter email address</label></td>
                <td><input type="text" name="email" maxlength="255" id="adminLoginEmail<%= adminLogin.getId() %>"
                           value="<%= userManager != null ? userManager.getUser().getEmail() : "" %>"></td>
            </tr>
            <tr>
                <td><label for="adminLoginPassword<%= adminLogin.getId() %>">Enter Password</label></td>
                <td><input type="password" name="password" id="adminLoginPassword<%= adminLogin.getId() %>"
                           value="<%= userManager != null ? userManager.getUser().getPassword() : "" %>"></td>
            </tr>
            <tr><td colspan="2"><input type="submit" value="Login" class="adminLoginSubmit"></td></tr>
        </tbody>
    </table>
</form>
<% } %>