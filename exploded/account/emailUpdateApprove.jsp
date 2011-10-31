<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<international:part part="emailUpdateApprove"/>
<html>
<head>
    <title><international:get name="pageTitle"/></title>
    <cache:no/>
</head>
<body>
<international:get name="emailApproved"/>
<stripes:link beanclass="com.shroggle.presentation.StartAction"><international:get
        name="backToHomeLink"/></stripes:link>
</body>
</html>