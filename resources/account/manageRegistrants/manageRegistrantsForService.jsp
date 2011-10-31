<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="manageRegistrants"/>
<% final int siteId = (Integer) request.getAttribute("manageRegistrantsSiteId"); %>

<div class="windowOneColumn">
<script type="text/javascript">
    var siteId = <%= siteId %>;
</script>
    <jsp:include page="manageRegistrantsContent.jsp" flush="true"/>

    <div style="margin-top:30px;text-align:right;">
        <input type="button" value="<international:get name="close"/>" onClick="closeConfigureWidgetDiv();" id="windowCancel"
           class="but_w73" onmouseover="this.className='but_w73_Over';" onmouseout="this.className='but_w73';">
    </div>
</div>