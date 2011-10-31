<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<international:part part="acceptRequestContent"/>
<html>
<head>
    <title><international:get name="title"/></title>
    <jsp:include page="/includeHeadPresentationResources.jsp">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">
    <script type="text/javascript">

        function allowEdit(item) {
            if (item.checked) {
                $("#editSubmit").removeAttr("disabled");
            } else {
                $("#editSubmit").attr("disabled", "disabled");
            }
        }

    </script>
</head>
<body>
<div id="wrapper">
    <div id="container">
    <%@ include file="/includeHeadPresentation.jsp" %>
    <div id="mainContentNdi" class="clearbothNd">
        <h2><international:get name="header"/></h2><br>

        <h3>
            The decision about sharing this content has already been made. You can change
            the content sharing if you visit the 'Manage Blogs', 'Manage Forums' or 'Manage Forms & Records' pages in
            your system account.
        </h3><br><br>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
        </div>
</div>
</body>
</html>
