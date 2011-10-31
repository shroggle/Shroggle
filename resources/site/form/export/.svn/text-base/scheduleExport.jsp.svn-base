<%--
    @author Balakirev Anatoliy
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shroggle.logic.form.export.DataExportAndScheduleModel" %>
<%@ page import="com.shroggle.entity.FormExportFrequency" %>
<%@ page import="com.shroggle.entity.FormExportDestination" %>
<%@ page import="com.shroggle.entity.FormExportDataFormat" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="scheduleExport"/>
<% final DataExportAndScheduleModel model = (DataExportAndScheduleModel) request.getAttribute("dataExportAndScheduleModel"); %>
<div class="dataExportWindowDiv">
    <h1><international:get name="scheduledDataExport"/></h1>

    <h2 style="display:inline;margin-left:0;"><international:get name="destinationRecipientOfExportedFile"/></h2>

    <div id="scheduleExportErrors" class="emptyError"></div>

    <div>
        <%----------------------------------------------------Recipient---------------------------------------------------%>
        <%------------------------------------------------------Google----------------------------------------------------%>
        <div id="googleRecipient">
            <div style="margin-left:-5px;">
                <input type="radio" id="ourListOfOptionsRadio" name="destinationRadio"
                       onclick="dataExportAndScheduler.destinationRadioChanged(null);"
                       <% if (model.getFormExportTaskDataFormat() == FormExportDataFormat.CSV) { %>disabled<% } %>
                       <% if (model.isDestinationFromOurListOfOptions()) { %>checked<% } %>>
                <label for="ourListOfOptionsRadio">
                    <international:get name="selectFromOurListOfOptions"/>
                </label>
                <select id="ourListOfOptionsSelect" style="margin-left:17px;"
                        <% if (model.isDestinationOwnFtp()) { %>disabled<% } %>>
                    <option value="<%= FormExportDestination.GOOGLE %>"
                            <% if (model.getFormExportTaskDestination() == FormExportDestination.GOOGLE) { %>selected<% } %>>
                        <international:get name="google"/>
                    </option>
                </select>
            </div>
            <table style="margin-top:5px;margin-left:10px;">
                <tr>
                    <td style="padding-right:10px;padding-bottom:5px;">
                        <label for="googleBaseAccountUsername">
                            <international:get name="googleBaseAccountUsername"/>
                        </label>
                    </td>
                    <td style="padding-bottom:5px;">
                        <input type="text" maxlength="255" id="googleBaseAccountUsername"
                               value="<%= model.getGoogleBaseAccountUsername() %>"
                               <% if (model.isDestinationOwnFtp()) { %>disabled<% } %>>
                    </td>
                </tr>
                <tr>
                    <td style="padding-right:10px;padding-bottom:5px;">
                        <label for="googleBaseAccountPassword">
                            <international:get name="googleBaseAccountPassword"/>
                        </label>
                    </td>
                    <td style="padding-bottom:5px;">
                        <input type="password" maxlength="255" id="googleBaseAccountPassword"
                               value="<%= model.getGoogleBaseAccountPassword() %>"
                               <% if (model.isDestinationOwnFtp()) { %>disabled<% } %>>
                    </td>
                </tr>
            </table>
        </div>
        <%------------------------------------------------------Google----------------------------------------------------%>


        <%------------------------------------------------------Own ftp---------------------------------------------------%>
        <div style="margin-left:-5px;">
            <input type="radio" id="ownFtpAddressRadio" value="<%= FormExportDestination.FTP %>" name="destinationRadio"
                   onclick="dataExportAndScheduler.destinationRadioChanged('<%= FormExportDestination.FTP %>');"
                   <% if (model.isDestinationOwnFtp()) { %>checked<% } %>>
            <label for="ownFtpAddressRadio">
                <international:get name="enterYourOwnFtpAddress"/>
            </label>
        </div>
        <div id="ownFtpRecipient">
            <%--<div style="margin-top:5px;margin-left:10px;">


            </div>--%>
            <table style="margin-top:5px;margin-left:10px;">
                <tr>
                    <td style="padding-right:10px;padding-bottom:5px;">
                        <label for="ownFtpAddress">
                    <international:get name="ftp"/>
                </label>
                    </td>
                    <td style="padding-bottom:5px;">
                        <input type="text" maxlength="255" id="ownFtpAddress" value="<%= model.getOwnFtpAddress() %>"
                        <% if (model.isDestinationFromOurListOfOptions()) { %>disabled<% } %>>
                    </td>
                </tr>
                <tr>
                    <td style="padding-right:10px;padding-bottom:5px;">
                        <label for="ftpLogin">
                            <international:get name="ftpLogin"/>
                        </label>
                    </td>
                    <td style="padding-bottom:5px;">
                        <input type="text" maxlength="255" id="ftpLogin"
                               value="<%= model.getFtpLogin() %>"
                               <% if (model.isDestinationFromOurListOfOptions()) { %>disabled<% } %>>
                    </td>
                </tr>
                <tr>
                    <td style="padding-right:10px;padding-bottom:5px;">
                        <label for="ftpPassword">
                            <international:get name="ftpPassword"/>
                        </label>
                    </td>
                    <td style="padding-bottom:5px;">
                        <input type="password" maxlength="255" id="ftpPassword"
                               value="<%= model.getFtpPassword() %>"
                               <% if (model.isDestinationFromOurListOfOptions()) { %>disabled<% } %>>
                    </td>
                </tr>
            </table>
        </div>
        <%------------------------------------------------------Own ftp---------------------------------------------------%>
        <%----------------------------------------------------Recipient---------------------------------------------------%>

        <%----------------------------------------------------Frequency---------------------------------------------------%>
        <div style="margin-top:15px;">
            <label for="frequency">
                <international:get name="selectExportFrequency"/>
            </label>

            <div style="margin-top:5px;margin-left:10px;">
                <select id="frequency">
                    <% for (FormExportFrequency frequency : FormExportFrequency.values()) { %>
                    <option value="<%= frequency.toString() %>"
                            <% if (model.getFrequency() == frequency) { %>selected<% } %>>
                        <international:get name="<%= frequency.toString() %>"/>
                    </option>
                    <% } %>
                </select>
            </div>
        </div>
        <%----------------------------------------------------Frequency---------------------------------------------------%>

        <%----------------------------------------------------Start Date--------------------------------------------------%>
        <div style="margin-top:15px;">
            <label for="startDate" style="margin-top:15px;">
                <international:get name="startDate"/>
            </label>
            <input type="text" id="startDate" style="width:70px;" maxlength="10"
                   value='<%= model.getStartDateString() %>'>
        </div>
        <%----------------------------------------------------Start Date--------------------------------------------------%>
    </div>
    <div style="position:absolute;bottom:20px;right:20px;">
        <input type="button" class="but_w130" value="<international:get name="save"/>"
               id="windowSave"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               onclick="dataExportAndScheduler.save();"/>
        <input type="button" class="but_w130" value="<international:get name="cancel"/>"
               onmouseover="this.className = 'but_w130_Over';"
               onmouseout="this.className = 'but_w130';"
               id="windowCancel"
               onclick="closeConfigureWidgetDivWithConfirm();"/>
    </div>
</div>