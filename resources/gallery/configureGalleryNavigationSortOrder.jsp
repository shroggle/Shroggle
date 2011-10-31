<%@ page import="com.shroggle.entity.GallerySortOrder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGalleryNavigationSortOrder"/>
<div id="configureGalleryNavigationSortOrder" style="display: none;">
    <div class="windowOneColumn">
        <h2><international:get name="title"/></h2>
        <br>
        <international:get name="description"/><br><br>

        <div style="text-align: right; padding-right: 40%; width: 80%;">
            <b><international:get name="first"/></b>
            <select size="1" id="configureGalleryNavigationSortOrderFirst">
            </select>&nbsp;&nbsp;
            <select size="1" style="margin-bottom: 5px; margin-top: 5px;"
                    id="configureGalleryNavigationSortOrderFirstType">
                <option value="<%= GallerySortOrder.ASCENDING %>"><international:get name="sortOrder1"/></option>
                <option value="<%= GallerySortOrder.DESCENDING %>"><international:get name="sortOrder2"/></option>
                <option value="<%= GallerySortOrder.RANDOM %>"><international:get name="sortOrder3"/></option>
            </select><br>

            <b><international:get name="second"/></b>
            <select size="1" id="configureGalleryNavigationSortOrderSecond">
            </select>&nbsp;&nbsp;
            <select size="1" style="margin-top: 5px;" id="configureGalleryNavigationSortOrderSecondType">
                <option value="<%= GallerySortOrder.ASCENDING %>"><international:get name="sortOrder1"/></option>
                <option value="<%= GallerySortOrder.DESCENDING %>"><international:get name="sortOrder2"/></option>
                <option value="<%= GallerySortOrder.RANDOM %>"><international:get name="sortOrder3"/></option>
            </select><br>
        </div>

        <br>
        <div style="text-align: right;">
            <input type="button" onclick="saveConfigureGalleryNavigationSortOrder();"
                   value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
            <input type="button" onclick="closeConfigureWidgetDiv();"
                   value="<international:get name="cancel"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
        </div>
    </div>
</div>