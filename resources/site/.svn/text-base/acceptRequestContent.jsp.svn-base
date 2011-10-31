<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                $("#editSubmit").removeAttr('style');
            } else {
                $("#editSubmit").attr("disabled", "disabled");
                $("#editSubmit").css('color', '#999');
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
            <international:get name="subHeader">
                <international:param value="${actionBean.siteItemType.itemName}"/>
                <international:param value="${actionBean.siteItemName}"/>
                <international:param value="${actionBean.siteTitle}"/>
                <international:param value="${actionBean.requestedUser.user.firstName}"/>
                <international:param value="${actionBean.requestedUser.user.lastName}"/>
            </international:get>
            "<a target="_blank" href="${actionBean.targetSiteUrl}">${actionBean.targetSiteTitle}</a>"
        </h3><br>
        <c:if test="${!actionBean.sucessEdit && !actionBean.sucessRead}">
            <international:get name="description">
                <international:param value="${actionBean.requestedUser.user.firstName}"/>
                <international:param value="${actionBean.requestedUser.user.lastName}"/>
            </international:get>
            <br>
        </c:if>
        <div style="color:green">
            <c:if test="${actionBean.sucessRead}">
                <international:get name="sucessREAD">
                    <international:param value="${actionBean.siteItemType.itemName}"/>
                    <international:param value="${actionBean.siteItemName}"/>
                </international:get>
            </c:if>

            <c:if test="${actionBean.sucessEdit}">
                <international:get name="sucessEDIT">
                    <international:param value="${actionBean.siteItemType.itemName}"/>
                    <international:param value="${actionBean.siteItemName}"/>
                </international:get>
            </c:if>
        </div>
        <br>

        <c:if test="${!actionBean.sucessEdit && !actionBean.sucessRead}">
            <table>
                <tr>
                    <td width="50%" style="padding-bottom: 40px;">
                        <input type="checkbox" onclick="allowEdit(this);" id="allowEditCheckbox">
                        <label for="allowEditCheckbox">
                            <international:get name="acceptEdit">
                                <international:param value="${actionBean.requestedUser.firstName}"/>
                                <international:param value="${actionBean.requestedUser.lastName}"/>
                            </international:get>
                        </label>
                    </td>
                    <td width="50%">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <stripes:form
                                beanclass="com.shroggle.presentation.site.requestContent.AcceptRequestContentAction">
                            <input type="submit" name="execute" id="editSubmit" class="but_w230"
                                   style="color:#999;"
                                   onmouseover="this.className='but_w230_Over';"
                                   onmouseout="this.className='but_w230';" disabled="disabled"
                                   value="Editing Access"/>
                            <stripes:hidden name="siteItemId"/>
                            <stripes:hidden name="siteItemType"/>
                            <stripes:hidden name="targetSiteId"/>
                            <stripes:hidden name="acceptCode"/>
                            <stripes:hidden name="showOnItemRightType" value="EDIT"/>
                        </stripes:form>
                    </td>
                    <td>
                        <stripes:form
                                beanclass="com.shroggle.presentation.site.requestContent.AcceptRequestContentAction">
                            <input type="submit" name="execute" class="but_w230"
                                   onmouseover="this.className='but_w230_Over';"
                                   onmouseout="this.className='but_w230';" value="Read Only Access"/>
                            <stripes:hidden name="siteItemId"/>
                            <stripes:hidden name="siteItemType"/>
                            <stripes:hidden name="targetSiteId"/>
                            <stripes:hidden name="acceptCode"/>
                            <stripes:hidden name="showOnItemRightType" value="READ"/>
                        </stripes:form>

                    </td>
                </tr>
            </table>
        </c:if>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
    </div>
</body>
</html>
