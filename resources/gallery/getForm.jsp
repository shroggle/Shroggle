<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${actionBean != null}">
        <c:set var="form" value="${actionBean.form}"/>
    </c:when>
    <c:otherwise>
        <c:set var="form" value="${galleryService.form}"/>
    </c:otherwise>
</c:choose>

window.configureGalleryTempForm = {
    items: [
        <c:if test="${form != null}">
            <c:forEach items="${form.items}" var="item" varStatus="index">
                <c:if test="${index.index > 0}">,</c:if>
                {id: ${item.formItemId}, type: "${item.formItemName}", name: "${item.itemName}", formItemType: "${item.formItemName.type}"}
            </c:forEach>
        </c:if>
    ]
};


