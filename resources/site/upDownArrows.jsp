<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="upDownArrows"/>

<img src="/images/up_arrow.gif" alt="" name="upImages"
     title="<international:get name="moveUpTitle"/>"
     style="cursor:pointer;<%= ((Boolean)request.getAttribute("first")) ? "visibility:hidden;" : "" %>"
     onclick="tableOperations.moveUpTr(this);"/>
<img src="/images/down_arrow.gif" alt="" name="downImages"
     title="<international:get name="moveDownTitle"/>"
     style="cursor:pointer;<%= ((Boolean)request.getAttribute("last")) ? "visibility:hidden;" : "" %>"
     onclick="tableOperations.moveDownTr(this);"/>