<%@ page import="com.shroggle.logic.user.UsersManager" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<br clear=all>

<div class="footer_container">
    <div class="footer">
        <div class="footermenu"><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Contact">Contact Us</a> |
            <a href="http://www.blogs.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/SEOBlog">SEO Blog</a> |
            <a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Terms_and_Conditions">Fees & Conditions</a>
        </div>

        <% if(!UsersManager.isNetworkUser()) { %><div class="copy">Copyright &copy; 2010 <%= ServiceLocator.getConfigStorage().get().getApplicationName() %>. All rights reserved.</div><% } %>
    </div>
</div>
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
