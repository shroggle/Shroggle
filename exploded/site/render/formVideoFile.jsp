<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.entity.FilledForm" %>
<%@ page import="com.shroggle.entity.FormItem" %>
<%@ page import="com.shroggle.entity.FlvVideo" %>
<%@ page import="com.shroggle.logic.form.*" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="formVideoFileUpload"/>

<%
    final FormFileData videoData = FormFileManager.createFormFileVideoData(((FilledForm) request.getAttribute("filledForm")), ((FormItem) request.getAttribute("formItem")), ((Integer) request.getAttribute("widgetId")));
    final FormFileData videoImageData = FormFileManager.createFormFileVideoImageData(((FilledForm) request.getAttribute("filledForm")), ((FormItem) request.getAttribute("formItem")), ((Integer) request.getAttribute("widgetId")));
%>
<tr>
<td colspan="2">
<div style="font-weight:bold;text-align:center;margin-bottom:15px;">
    <international:get name="pleaseUploadYourVideoFile"/>
    &nbsp;
    <a href="javascript:showFormFileUploadMoreInfoText('formFileUploadMoreInfoText');">
        <international:get name="moreInfo"/>
    </a>
</div>


<div style="margin-bottom:10px;">
    <a href="javascript:showFormFileUploadEstimatedTimesText('formFileUploadEstimatedTimesText');">
        <international:get name="estimatedFileUploadTimes"/>
    </a>
</div>

<%------------------------------------------------video upload field--------------------------------------------------%>
<div>
    <div>
        <international:get name="selectAndUploadYourVideo"/>
        :<%= videoData.isRequired() ? "&nbsp;*" : ""  %>
    </div>
    <div style="margin-bottom:10px;">
        <% request.setAttribute("formFileData", videoData); %>
        <table>
            <%--<%@ include file="formFile.jsp" %>--%>
            <jsp:include page="formFile.jsp" flush="true"/>
        </table>
    </div>
</div>
<%------------------------------------------------video upload field--------------------------------------------------%>

<%----------------------------------------------video quality/size fields---------------------------------------------%>
<div>
    <input type="hidden" id="<%= videoData.getId() %>IsVideoField"
           value="true">

    <div style="margin-bottom:10px;">
        <international:get name="originalVideoQuality"/>:
        <select id="<%= videoData.getId() %>Quality">
            <%
                for (int videoQuality = FlvVideo.MAX_VIDEO_QUALITY; videoQuality <= FlvVideo.MIN_VIDEO_QUALITY; videoQuality++) { %>
            <option value="<%= videoQuality %>"
                    <% if (videoData.getVideoQuality() == videoQuality) { %>
                    selected <% } %>>
                <%= videoQuality %>
                <%
                    if (videoQuality == FlvVideo.MAX_VIDEO_QUALITY) { %>
                <international:get name="bestQuality"/><% } %>
                <%
                    if (videoQuality == FlvVideo.MIN_VIDEO_QUALITY) { %>
                <international:get name="worstQuality"/><% } %>
            </option>
            <% } %>
        </select>
    </div>
</div>
<%----------------------------------------------video quality/size fields---------------------------------------------%>

<%---------------------------------------------video image upload field-----------------------------------------------%>
<div>
    <div>
        <international:get name="selectAndUploadDefaultImage"/>:
    </div>
    <div style="margin-bottom:10px;">
        <input type="hidden"
               id="<%= videoImageData.getId() %>IsVideoImageField"
               value="true">
        <% request.setAttribute("formFileData", videoImageData);%>
        <table>
            <%--<%@ include file="formFile.jsp" %>--%>
            <jsp:include page="formFile.jsp" flush="true"/>
        </table>
    </div>
</div>
<%---------------------------------------------video image upload field-----------------------------------------------%>

<%-----------------------------------------------------Help texts-----------------------------------------------------%>
<div style="display:none;">
<div id="formFileUploadMoreInfoText">
    <div style="border:5px solid #ECE9E6; margin:0 10px 0 10px; padding-top:20px;padding-left:20px;padding-right:20px;">
        <div style="overflow:auto;height:100px; width:420px; padding:10px; text-align:left;">
            <international:get name="moreInfoText"/>
        </div>
        <div align="center">
            <input type="button" onclick="closeConfigureWidgetDiv();"
                   value="<international:get name="close"/>">
        </div>
        <br>
    </div>
</div>
<style type="text/css" id="borderSampleImageStyle">
    .formFileUploadEstimatedTimesTable  td {
        text-align: center;
        width: 100px;
        padding: 5px;
        vertical-align: middle;
    }
</style>

<div id="formFileUploadEstimatedTimesText">
<div style="border:5px solid #ECE9E6; margin:0 10px 0 10px; padding-top:20px;padding-left:20px;padding-right:20px;">
    <div style="width:500px; padding: 10px">
        <table class="formFileUploadEstimatedTimesTable" width="100%" cellpadding="0" cellspacing="0"
               border="0">
            <tr>
                <td>
                    <international:get name="uploadSpeed">
                        <international:param value="<br>"/>
                    </international:get>
                </td>
                <td>
                    <international:get name="fileSize">
                        <international:param value="<br>"/>
                    </international:get>
                </td>
                <td>
                    <international:get name="uploadTimeSec">
                        <international:param value="<br>"/>
                    </international:get>
                </td>
                <td>
                    <international:get name="uploadTimeMinSec">
                        <international:param value="<br>"/>
                    </international:get>
                </td>
            </tr>
        </table>
    </div>
    <div style="overflow-y:auto; width:520px; height:270px; padding: 0 10px 10px;">
        <table class="formFileUploadEstimatedTimesTable" width="100%" cellpadding="0" cellspacing="0"
               border="0">
            <tr>
                <td>1</td>
                <td>50</td>
                <td>400</td>
                <td>6 <international:get name="min"/> 40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>5</td>
                <td>50</td>
                <td>80</td>
                <td>1 <international:get name="min"/>
                    20 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>50</td>
                <td>40</td>
                <td>0 <international:get name="min"/>
                    40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>50</td>
                <td>4</td>
                <td>0 <international:get name="min"/>
                    4 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>1</td>
                <td>100</td>
                <td>800</td>
                <td>13 <international:get name="min"/>
                   20 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>5</td>
                <td>100</td>
                <td>160</td>
                <td>2 <international:get name="min"/>
                    40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>100</td>
                <td>80</td>
                <td>1 <international:get name="min"/>
                     20 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>100</td>
                <td>8</td>
                <td>0 <international:get name="min"/>
                    8 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>500</td>
                <td>400</td>
                <td>6 <international:get name="min"/>
                    40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>500</td>
                <td>40</td>
                <td>0 <international:get name="min"/>
                    40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>1000</td>
                <td>800</td>
                <td>13 <international:get name="min"/>
                    20 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>1000</td>
                <td>80</td>
                <td>1 <international:get name="min"/>
                    20 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>2000</td>
                <td>1600</td>
                <td>26 <international:get name="min"/>
                    40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>2000</td>
                <td>160</td>
                <td>2 <international:get name="min"/>
                     40 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>3000</td>
                <td>2400</td>
                <td>40 <international:get name="min"/>
                    0 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>3000</td>
                <td>240</td>
                <td>4 <international:get name="min"/>
                    0 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>10</td>
                <td>4000</td>
                <td>3200</td>
                <td>53 <international:get name="min"/>
                    20 <international:get name="sec"/>
                </td>
            </tr>
            <tr>
                <td>100</td>
                <td>4000</td>
                <td>320</td>
                <td>5 <international:get name="min"/>
                    20 <international:get name="sec"/>
                </td>
            </tr>
        </table>
    </div>
    <br>

    <div align="center">
        <input type="button" onclick="closeConfigureWidgetDiv();"
               value="<international:get name="close"/>">
    </div>
    <br>
</div>
</div>
</div>
<%-----------------------------------------------------Help texts-----------------------------------------------------%>
</td>
</tr>