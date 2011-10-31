<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>


<div id="footerBottomNd">
    <p>
        <% if (UsersManager.isNetworkUser()) { %>
        <a href="<%= request.getAttribute("contactUsPageUrl") != null ? request.getAttribute("contactUsPageUrl") : "#" %>">Contact
            Us</a>
        <% } else { %>
        <stripes:link beanclass="com.shroggle.presentation.user.create.CreateUserAction">Create Account</stripes:link> |
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Services">Services</a> |
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Feature_List">Feature List</a> |
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/FAQ">FAQ</a> |
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Contact">Contact Us</a> |
        <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Terms_and_Conditions">Fees & Conditions</a> |
        <stripes:link beanclass="com.shroggle.presentation.MapAction">Map</stripes:link>
        <% } %>
    </p>

    <% if (!UsersManager.isNetworkUser()) { %>
    <p><strong>Copyright &copy; 2010 <%= ServiceLocator.getConfigStorage().get().getApplicationName() %></strong> All rights reserved. XHTML and CSS validated.</p></div>
    <% } %>

<!--end of footer --></div>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-19271407-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>