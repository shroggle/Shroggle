<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGallery"/>

<!-- Helper windows -->
<jsp:include page="/gallery/configureGalleryNavigationThumbnails.jsp" flush="true"/>
<jsp:include page="/gallery/configureGalleryNavigationPageSelector.jsp" flush="true"/>
<jsp:include page="/gallery/configureGalleryNavigationLabels.jsp" flush="true"/>
<jsp:include page="/gallery/configureGalleryNavigationSortOrder.jsp" flush="true"/>
<jsp:include page="/gallery/configureGalleryDataPageSelector.jsp" flush="true"/>
<jsp:include page="/gallery/configureGalleryDataItems.jsp" flush="true"/>

<!-- Window datas -->
<div style="overflow-y: auto; height: 450px;">
    <table border="0" cellpadding="0" cellspacing="0" style="border:1px solid darkgray;">
        <tr style="background-color: lightgray;">
            <td style="padding: 5px; border-right: solid darkgray 1px">
                <a href="javascript:showConfigureGalleryNavigationThumbnails()">
                    <international:get name="editNavigationThumbnailSizesSpacing"/>
                </a>
            </td>
            <td style="padding: 5px; border-right: solid darkgray 1px">
                <a href="javascript:showConfigureGalleryNavigationLabels()">
                    <international:get name="editTextThatDisplaysWithThumbnails"/>
                </a>
            </td>
            <td style="padding: 5px;">
                <a href="javascript:showConfigureGalleryDataItems()">
                    <international:get name="editDataDisplayContentsLayout"/>
                </a>
            </td>
        </tr>
        <tr>
            <td width="33%" style="padding-left: 10px; border-right: solid darkgray 1px">
                <br>
                <b><international:get name="navigationThumbnailsAreDisplayedInAGrid"/></b><br>
                <international:get name="numberOfRows"/>
                <span id="configureGalleryNavigationThumbnailsRowsInfo">&nbsp;</span>,
                <international:get name="numberOfColumns"/>
                <span id="configureGalleryNavigationThumbnailsColumnsInfo">&nbsp;</span>
                <br><br>

                <b><international:get name="thumbnailDimenions"/></b><br>
                <international:get name="height"/>
                <span id="configureGalleryNavigationThumbnailsHeightInfo">&nbsp;</span>px,
                <international:get name="width"/>
                <span id="configureGalleryNavigationThumbnailsWidthInfo">&nbsp;</span>px
                <br><br>

                <b><international:get name="spaceAroundThumbnails"/></b><br>
                <international:get name="margin"/>
                <span id="configureGalleryNavigationThumbnailsVerticalMarginInfo">&nbsp;</span>px,
                <international:get name="border"/>
                <span id="configureGalleryNavigationThumbnailsBorderWidthInfo">&nbsp;</span>px
                <br><br>
            </td>
            <td width="33%" style="padding-left: 10px; border-right: solid darkgray 1px">
                <br>
                <international:get name="thumbnailsInstrunction"/><br><br>
                <b><international:get name="thumbnailTextLabelsToBeIncluded"/></b><br>
                <span id="configureGalleryNavigationLabelsInfo">&nbsp;</span>
                <br><br>

                <b><international:get name="alignmentOfText"/></b>
                <span id="configureGalleryNavigationLabelsDetailInfo">&nbsp;</span>
                <br><br>
            </td>
            <td style="padding-left: 10px;">
                <br>
                <b><international:get name="dataToBeIncluded"/></b><br>
                <span id="configureGalleryDataItemsInfo">&nbsp;</span>
                <br><br>

                <b><international:get name="arrangementOfDataDisplay"/></b><br>
                <span id="configureGalleryDataItemsDetailInfo">&nbsp;</span>
            </td>
        </tr>
        <tr style="background-color: lightgray;">
            <td style="padding: 5px; border-right: solid darkgray 1px">
                <a href="javascript:showConfigureGalleryNavigationPageSelector()">
                    <international:get name="editPageSelector"/>
                </a>
            </td>
            <td style="padding: 5px; border-right: solid darkgray 1px">
                <a href="javascript:showConfigureGalleryNavigationSortOrder()">
                    <international:get name="editSortOrderForNavigationThumbnails"/>
                </a>
            </td>
            <td style="padding: 5px;">
                <a href="javascript:showConfigureGalleryDataPageSelector()">
                    <international:get name="editPageSelectorBetweenDataPages"/>
                </a>
            </td>
        </tr>
        <tr>
            <td style="padding-left: 10px; border-right: solid darkgray 1px">
                <br>
                <b><international:get name="pageSelector"/></b><br>
                <span id="configureGalleryNavigationPageSelectorInfo">&nbsp;</span>
                <br><br>
            </td>
            <td style="padding-left: 10px; border-right: solid darkgray 1px">
                <br>
                <b><international:get name="sortBy"/></b><br>
                <span id="configureGalleryNavigationSortOrderInfo">&nbsp;</span>
                <br><br>
            </td>
            <td style="padding-left: 10px;">
                <br>
                <b><international:get name="pageSelector"/></b><br>
                <span id="configureGalleryDataPageSelectorInfo">&nbsp;</span>
            </td>
        </tr>
    </table>
</div>