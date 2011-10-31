<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGalleryDataItems"/>
<div id="configureGalleryDataItems" style="display: none;">
    <div class="windowOneColumn">
        <h2><international:get name="title"/></h2>
        <br>
        <international:get name="description"/><br><br>

        <div style="overflow-y: auto; height: 350px; width: 100%;">
            <table border="0">
                <thead>
                    <tr>
                        <td><international:get name="column1"/></td>
                        <td><international:get name="column2"/></td>
                        <td><international:get name="column3"/></td>
                        <td><international:get name="column4"/></td>
                        <td><international:get name="column5"/></td>
                        <td><international:get name="column6"/></td>
                        <td><international:get name="column7"/></td>
                        <td><international:get name="column8"/></td>
                    </tr>
                </thead>
                <tbody id="сonfigureGalleryDataItemsItems">
                    
                </tbody>
            </table>

            <br>
            <input type="checkbox" id="configureGalleryDataHideEmpty">
            <label for="configureGalleryDataHideEmpty">Close gaps in the display, when fields are empty</label>
        </div>

        <br>
        <div style="text-align: right;">
            <input type="button" onclick="saveConfigureGalleryDataItems();disableVotingAudioVideoArea(!document.getElementById('includesVotingModule').checked);"
                   value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
            <input type="button" onclick="closeConfigureWidgetDiv();"
                   value="<international:get name="cancel"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
        </div>
    </div>
</div>