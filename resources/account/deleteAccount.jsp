<%@ page import="com.shroggle.presentation.site.DeleteUserAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="deleteAccount"/>
<html>
<head>
    <title><international:get name="deleteAccount"/></title>
    <script type="text/javascript">

        function deleteAccount() {
            return confirm("<international:get name="accountDeleteConfirm"/>");
        }

    </script>

    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
</head>
<body>
<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <div class="box_70">
                <%@ include file="accountMenuInclude.jsp" %>
            </div>
            <!-- start label-box -->
            <div class="box_70">
                <div id="errorBlock" style="display:none;">&nbsp;</div>
                <div class="inform_mark"><international:get name="infoInstructions"/></div>
                <br>
                <br clear="all">

                <div align="right" id="deleteAccountButtonDiv">
                    <stripes:form beanclass="com.shroggle.presentation.site.DeleteUserAction"
                                  onsubmit="return deleteAccount()">
                        <input type="hidden" name="execute" value="true">
                        <input type="submit" value="<international:get name="deleteLabel"/>"
                               onmouseout="this.className='but_w73';"
                               onmouseover="this.className='but_w73_Over';" class="but_w73">
                    </stripes:form>
                </div>
            </div>
            <br>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>