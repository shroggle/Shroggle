<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.presentation.site.LoginedUserInfo" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.international.InternationalStorage" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.presentation.StartAction" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%
    final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    final International international = internationalStorage.get("head", Locale.US);
    final LoginedUserInfo loginedUserInfoAction = ((LoginedUserInfo) request.getAttribute("actionBean"));
    final String logoUrl = StringUtil.getEmptyOrString(request.getAttribute("logoUrl")); %>
<div id="headerNd">
    <div id="logoNd">
        <h1>
            <% if (logoUrl.isEmpty()) { %>
            <% if (loginedUserInfoAction instanceof StartAction) {%>
            <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/definiton"><img class="logoNdImage" src="/images/imagesnew/logo24.png" alt="web-deva definition"></a>
<%--
            <stripes:link beanclass="com.shroggle.presentation.StartAction"><img class="logoNdImage" src="/images/imagesnew/logo24.png" alt="web-deva.com"></stripes:link>
--%>
            <% } else { %>
            <stripes:link beanclass="com.shroggle.presentation.StartAction"><img class="logoNdImage" src="/images/imagesnew/logo24.png" alt="Web-Deva.com"></stripes:link>
            <% } %>
            <% } else { %>
            <a href="#"><img class="logoNdImage" src="<%= logoUrl %>" alt=""></a>
            <% } %>
        </h1>
    </div>
    <div class="loginNd" id="start_login_block"
         <% if (loginedUserInfoAction.getLoginUser() == null) {  %>onclick="window.loginControl.showLogin();"<% } %>>
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <% if (loginedUserInfoAction.getLoginUser() == null) { %>
                <td style="vertical-align:middle"><span style="font-weight:bold">My account</span>
                </td>
                <td style="vertical-align:middle">
                    <div id="loginForm">
                        <table border="0" cellspacing="0" cellpadding="0" style="font-weight:bold">
                            <tr>
                                <td style="vertical-align:middle">
                                    <input class="logtext logPassTextDefault" type="text"
                                           onblur="window.loginControl.blurLoginOrPassField(this);" id="loginEmail_start"
                                           onfocus="window.loginControl.focusLoginOrPassField(this);" value="username"/>
                                </td>
                                <td style="vertical-align:middle">
                                    <input class="passtext logPassTextDefault" type="password"
                                           onblur="window.loginControl.blurLoginOrPassField(this);" id="loginPassword_start"
                                           onfocus="window.loginControl.focusLoginOrPassField(this);" value="password"/>
                                </td>
                                <td style="vertical-align:middle">
                                    <input type="image" onclick="login.executeLogin(true);"
                                           onmouseover="this.src = '/images/imagesnew/loginbutton-text-roll.gif';"
                                           onmouseout="this.src = '/images/imagesnew/loginbutton-text.jpg';"
                                           src="/images/imagesnew/loginbutton-text.jpg" alt="login">
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
                <td style="vertical-align:middle">
                    <span class="startPageLoginLinks">
                       <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/FAQ">FAQ</a>  |
                        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Contact">Contact Us</a>
                    </span>
                </td>
                <% } else { %>
                <td style="vertical-align:middle">
                    <span class="loginedUserWelcomeSpan">Welcome,&nbsp;</span>
                    <span class="loginedUserEmailSpan"><%= loginedUserInfoAction.getLoginUser().getEmail() %></span>
                </td>
                <td style="vertical-align:middle">
                    <span class="startPageLoginLinks">
                        <stripes:link beanclass="com.shroggle.presentation.account.dashboard.DashboardAction">
                            <%=international.get("ydashboard")%>
                        </stripes:link> |
                        <stripes:link beanclass="com.shroggle.presentation.site.LogoutFromUserAction">
                            <%=international.get("signOut")%>
                        </stripes:link> |
                        <% if (UsersManager.isNetworkUser()) { %>
                        <a href="<%= request.getAttribute("contactUsPageUrl") != null ? request.getAttribute("contactUsPageUrl") : "#" %>"><%= international.get("contactUs") %></a>
                        <% } else { %>
                        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/FAQ"><%= international.get("FAQ") %></a> |
                        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Contact"><%= international.get("contactUs") %></a>
                        <% } %>
                    </span>
                </td>
                <% } %>
            </tr>
        </table>
        <div class="loginNd_additinal_block">
            <div id="remember_checkbox_div" style="visibility:hidden;">
                <input type="checkbox" id="loginRemember_start">
                <label for="loginRemember_start"><%=international.get("rememberMeOnThisComputer")%>
                </label>
            </div>
            <div id="loginError_start"></div>
            <div id="loginLinksBlock">
                <a id="forgotPasswordLoginLink" style="display:none;"
                   href="javascript:forgotPassword.show()"><%=international.get("iCannotAccessMyAccount")%>
                </a>
                <br/>
                <stripes:link id="registerLoginLink" style="display:none;"
                              beanclass="com.shroggle.presentation.user.create.CreateUserAction"><%=international.get("registerForAShroggleAccount")%>
                </stripes:link>
                <br>
            </div>
        </div>
        <!--end of login -->
    </div>
    <!--end of header -->

    <div id="resendActivationEmailWindowText" style="display:none;">
        <div class="windowOneColumn">
            <div style="color:green;font-weight:bold;text-align:left;margin-bottom:10px;">
                <%= international.get("activationEmailResent") %>
            </div>

            <div align="right">
                <input type="button" onclick="closeConfigureWidgetDiv();" class="but_w73"
                       onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73-new';"
                       value="Close"/>
            </div>
        </div>
    </div>
</div>