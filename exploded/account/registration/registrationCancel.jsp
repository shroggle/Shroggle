<%@ page import="com.shroggle.presentation.site.RegistrationCancelAction" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="registrationCancel"/>
<% final RegistrationCancelAction actionBean = (com.shroggle.presentation.site.RegistrationCancelAction) request.getAttribute("actionBean"); %>
<html>
<head>
    <title><international:get name="registrationCanceled"/></title>
    <jsp:include page="/includeHeadPresentationResources.jsp" flush="true">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">
</head>
<body>
<div id="wrapper">
    <div id="container">
    <%@ include file="/includeHeadPresentation.jsp" %>
    <div id="mainContentCentered" class="clearbothNd">
        <stripes:link beanclass="com.shroggle.presentation.StartAction"><international:get
                name="home"/></stripes:link>

        <div class="registrationCancelMainDiv">
            <international:get name="why"/>
            <stripes:form beanclass="com.shroggle.presentation.site.RegistrationCancelAction" method="post">

                <stripes:hidden name="registrationCode" value="<%= actionBean.getRegistrationCode() %>"/>
                <stripes:hidden name="userId" value="<%= actionBean.getUserId() %>"/>

                <h3 class="registrationCancelPickListHeader"><international:get name="pickListOfOptions"/></h3>

                <div class="registrationCancelPickList">
                    <div>
                        <stripes:radio value='<international:get name="later"/>' id="cancel" name="cancel"/>
                        <label for="cancel"><international:get name="later"/></label>
                    </div>

                    <div>
                        <stripes:radio value='<international:get  name="shopping"/>' id="shopping" name="cancel"/>
                        <label for="shopping"><international:get name="shopping"/></label>
                    </div>

                    <div>
                        <stripes:radio value='<international:get name="dontNeed"/>' id="dontNeed" name="cancel"/>
                        <label for="dontNeed"><international:get name="dontNeed"/></label>
                    </div>

                    <div>
                        <stripes:radio value='<international:get name="how"/>' id="how" name="cancel"/>
                        <label for="how"><international:get name="how"/></label>
                    </div>
                </div>

                <div class="registrationCancelSubmitButton">
                    <stripes:submit name="execute" onmouseout="this.className='but_w73'"
                                    onmouseover="this.className='but_w73_Over'" class="but_w73">
                        <international:get name="submit"/>
                    </stripes:submit>
                </div>

            </stripes:form>
        </div>
    </div>

    <%@ include file="/includeFooterPresentation.jsp" %>
</div>
    </div>
</body>
</html>