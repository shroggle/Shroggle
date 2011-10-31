<%@ attribute name="onload" required="true" rtexprvalue="true" %>
<img src="../../../images/temp.gif" style="display:none;" alt=""
     onload="if(isChrome()){setTimeout(function(){${onload}}, 500);}else{${onload}}">