<%@ page import="com.shroggle.util.international.InternationalStorage" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.entity.User" %>
<%@ page import="com.shroggle.presentation.site.LoginedUserInfo" %>
<%@ page import="com.shroggle.presentation.Action" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>

<jsp:include page="/includeHeadLogin.jsp"/>
<div id="topMenuNd">
    <div class="topMenuButtons">
        <stripes:link beanclass="com.shroggle.presentation.user.create.CreateUserAction"
                      onmouseout="$('#createaccountbutton')[0].src = '/images/imagesnew/create1button.gif';"
                      onmouseover="$('#createaccountbutton')[0].src = '/images/imagesnew/create1button-roll.gif';">
            <img src="/images/imagesnew/create1button.gif" alt="create account" id="createaccountbutton" width="230"
                 height="59" border="0">
        </stripes:link>
    </div>
    <div class="topMenuButtons">
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Services"
           onmouseout="$('#services')[0].src = '/images/imagesnew/service1button.gif';"
           onmouseover="$('#services')[0].src = '/images/imagesnew/service1button-roll.gif';">
            <img src="/images/imagesnew/service1button.gif" alt="services" id="services" width="153" height="59"
                 border="0"></a>
    </div>
    <div class="topMenuButtons">
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Feature_List"
           onmouseout="$('#featurelist')[0].src = '/images/imagesnew/feature1button.gif';"
           onmouseover="$('#featurelist')[0].src = '/images/imagesnew/feature1button-roll.gif';">
            <img src="/images/imagesnew/feature1button.gif" alt="feature list" id="featurelist" width="186" height="59"
                 border="0">
        </a>
    </div>
      <div><a href="http://www.blogs.web-deva.com"
             onMouseOut="$('#blogsbutton')[0].src = '/images/imagesnew/blogs.png';"
             onMouseOver="$('#blogsbutton')[0].src = '/images/imagesnew/blogs-roll.png';">
             <img src="/images/imagesnew/blogs.png" alt="blogs" id="blogsbutton" width="256px" height="80" border="0"></a>
     </div>
    <!--end of topMenu -->
    
</div>
<!--end of new-->