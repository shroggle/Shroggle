<%@ page import="com.shroggle.presentation.site.ShowVisitorLoginService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="widgetLogin"/>
<%
    final ShowVisitorLoginService service = (ShowVisitorLoginService) request.getAttribute("service");
    final int widgetId = service.getWidgetId();
    final boolean returnToRegistration = service.isReturnToRegistration();
    final boolean returnToChildSiteRegistration = service.isReturnToChildSiteRegistration();
    final boolean returnToManageVotes = service.isReturnToManageVotes();
    final boolean returnToForum = service.isReturnToForum();
    final boolean returnToGallery = service.isReturnToGallery();
    final boolean returnToBlog = service.isReturnToBlog();
    final boolean returnToShoppingCart = service.isReturnToShoppingCart();
%>
<div id="loginBlock<%= widgetId %>">
    <input type="hidden" id="returnToRegistration<%= widgetId %>" value="<%= returnToRegistration %>"/>
    <input type="hidden" id="emailResentText<%= widgetId %>" value="<international:get name="emailResentText"/>"/>

    <div id="loginForm<%= widgetId %>" class="loginForm">
        <form id="loginForm<%= widgetId %>" onsubmit="return false;">
            <div id="loginForm<%= service.getWidgetId() %>">
                <input type="hidden" id="returnToRegistration<%= service.getWidgetId() %>"
                       value="<%= service.isReturnToRegistration() %>">

                <div class='loginFormHeader'><international:get name="pleaseSsignInIntoSystem"/></div>
                <div class="lf loginFormLabel"><international:get name="enterLogin"/></div>
                <div class="rg loginFormInput"><input type="text" id="login<%= service.getWidgetId() %>" maxlength="255"
                                                      name="visitorLogin"></div>
                <div class="lf loginFormLabel"><international:get name="enterPassword"/></div>
                <div class="rg loginFormInput"><input type="password" name="visitorPassword"
                                                      id="password<%= service.getWidgetId() %>"></div>
            </div>
            <div class="loginFormLabel">
                <input id="remember<%= widgetId %>" type="checkbox"/>
                <label for="remember<%= widgetId %>">
                    <international:get name="keepMeSignedIn"/>
                </label>
            </div>
            <div class="loginFormErrorBlockEmpty" id="loginErrorBlock<%= service.getWidgetId() %>"></div>
            <div class="sec loginFormButton">
                <input value="Login" type="button"
                       onclick="loginVisitor(<%= widgetId %>, <%= returnToRegistration %>);"/>
            </div>
        </form>
        <div class="sec">
            <a href="javascript:showForgotPass(<%= widgetId %>)"><international:get name="forgotPass"/></a>
            <br>
            <% if (returnToRegistration) { %>
            <a href="javascript:returnToRegistration(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get name="returnToRegistration"/></a>
            <% } else if (returnToChildSiteRegistration) {%>
            <a href="javascript:showChildSiteRegistrationForm(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get
                    name="goBackToTheForm"/></a>
            <% } else if (returnToGallery) {%>
            <input type="hidden" id="shouldBeRegisteredFromRightFormId<%= widgetId %>"
                   value="<%= service.getShouldBeRegisteredFromRightFormId() %>"/>
            <a href="javascript:showPreviousBlock(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get
                    name="goBackToGallery"/></a><br>
            <% if (service.getFormId() == null) { %>
            <a href="javascript:registerLink(<%= widgetId %>)" class="loginFormRegLink"><international:get
                    name="registerNow"/></a>
            <% } else { %>
            <a href="javascript:registerLinkWithFormId(<%= widgetId %>, <%= service.getFormId() %>)"
               class="loginFormRegLink"><international:get
                    name="registerNow"/></a>
            <% } %>
            <% } else if (returnToManageVotes) {%>
            <a href="javascript:showPreviousBlock(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get
                    name="goBackToManageVotes"/></a><br>
            <a href="javascript:registerLinkWithFormId(<%= widgetId %>, <%= service.getFormId() %>)"
               class="loginFormRegLink"><international:get
                    name="registerNow"/></a>
            <% } else if (returnToForum) {%>
            <a href="javascript:showPreviousBlock(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get
                    name="goBackToForum"/></a><br>
            <% } else if (returnToBlog) {%>
            <a href="javascript:showPreviousBlock(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get
                    name="goBackToBlog"/></a><br>
            <% } else if (returnToShoppingCart) {%>
            <input type="hidden" id="shouldBeRegisteredFromRightFormId<%= widgetId %>"
                   value="<%= service.getFormId() %>"/>
            <a href="javascript:showPreviousBlock(<%= widgetId %>)"
               class="loginFormReturnToRegLink"><international:get
                    name="goBackToShoppingCart"/></a><br>
            <a href="javascript:registerLinkWithFormId(<%= widgetId %>, <%= service.getFormId() %>)"
               class="loginFormRegLink"><international:get
                    name="registerNow"/></a>
            <% } else { %>
            <a href="javascript:registerLink(<%= widgetId %>)" class="loginFormRegLink"><international:get
                    name="registerNow"/></a>
            <% } %>
        </div>
    </div>

    <script type="text/javascript">
        //Binding submit event on "enter" key press.
        bindLoginFormSubmitEvent(<%=widgetId%>);
    </script>

    <div id="forgotPasswordArea<%=widgetId%>" style="display:none;">
        <international:get name="forgotPass"/>
        <br>
        <international:get name="forgotPassInfo"/>
        <div id="forgotPassErrorBlock<%=widgetId%>">&nbsp;</div>
        <table>
            <tr>
                <td>
                    <international:get name="forgotPassEnterEmail"/>
                </td>
                <td><input type="text" id="ForgotPassEmail<%=widgetId%>" maxlength="255"/>
                </td>
            </tr>
            <tr>
                <td>
                    <international:get name="forgotPassRetypeEmail"/>
                </td>
                <td><input type="text" id="ForgotPassRetypeEmail<%=widgetId%>" maxlength="255"/>
                </td>
            </tr>
        </table>
        <input type="button" onclick="emailVisitorPassword(<%=widgetId%>);"
               value="<international:get name="forgotPassEmailPass"/>"/>
        <br>
        <br>
        <a href="javascript:showLogin(<%= widgetId %>)"
           class="loginFormReturnToRegLink"><international:get name="returnToTheLogin"/></a>
    </div>

    <div id="forgotPasswordOkArea<%=widgetId%>" style="display:none;">
        <span style="color:green;"><international:get name="forgotPassOk"/></span><br>
        <input type="button" onclick="closeForgotPassOkArea(<%=widgetId%>);" value="Ok"/>
    </div>
</div>


