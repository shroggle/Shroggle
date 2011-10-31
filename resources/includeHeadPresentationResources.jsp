<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<cache:no/>
<link href="/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
<resources:natural name="presentation.css"/>
<jsp:include page="/presentationJs.jsp" flush="true"/>
<link rel="icon" href="/favicon.png" type="image/png">
<% if (request.getParameter("presentationJs") == null) { %>
    <jsp:include page="/js.jsp" flush="true"/>
<% } %>
