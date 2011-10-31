<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.presentation.site.SiteUnderConstructionAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="siteUnderConstruction"/>
<% final SiteUnderConstructionAction action = (SiteUnderConstructionAction) request.getAttribute("actionBean"); %>
 <link href="/css/reset.css" rel="stylesheet" type="text/css">
    <link href="/css/app_style.css" rel="stylesheet" type="text/css">
    <link href="/css/window_style.css" rel="stylesheet" type="text/css">
<html>
    <head>
        <title><international:get name="title"/></title>
    </head>
    <body>
       <div style="border:3px solid #8D949A; margin:10px auto; width:470px;text-align:center;">
            <br><bR><bR>
            <p>
                <international:get name="thank">
                    <international:param value="<%= action.getSiteUrl() %>"/>
                </international:get>
            </p>
            <p><international:get name="currently"/></p>
            <h1 style="text-transform:uppercase;"><international:get name="big"/></h1>
            <br><br>
            <international:get name="being"/> <br>
            <img src="/images/wdfooter-logo.png">
            <p><international:get name="easy"/></p>
           <br><br>
           </div>
    </body>
</html>