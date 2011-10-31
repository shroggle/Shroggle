<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.image.ShowImageTextAction" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>

<% final ShowImageTextAction action = (ShowImageTextAction) request.getAttribute("actionBean"); %>
<html>
<head>
</head>
<body>
<%= StringUtil.getEmptyOrString(action.getTextArea()) %>
</body>
</html>
