<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGalleryNavigationThumbnails"/>
<div id="configureGalleryNavigationThumbnails" style="display:none;">
    <div class="windowOneColumn">
        <h2><international:get name="title"/></h2>
        <br>
        <international:get name="description"/><br><%--<br>--%>

        <div id="configureGalleryNavigationThumbnailsSmallCellsInfo" style="color: red;">
            &nbsp;
        </div>
        <%--<br>--%>

        <table>
            <tr>
                <td style="padding: 5px 15px 5px 5px;width:30%;"><international:get name="columnCount"/></td>
                <td style="width:25%;">
                    <select size="1" id="configureGalleryNavigationThumbnailsColumns">
                        <% for (int i = 1; i < 21; i++) { %>
                        <option value="<%= i %>" <% if (i == 2) { %>selected="selected" <% } %>><%= i %>
                        </option>
                        <% } %>
                    </select>
                </td>
                <td><international:get name="cellBorderWidth"/></td>
                <td>
                    <select size="1" id="configureGalleryNavigationThumbnailsBorderWidth">
                        <% for (int i = 0; i < 11; i++) { %>
                        <option value="<%= i %>" <% if (i == 0) { %>selected="selected" <% } %>><%= i %>
                        </option>
                        <% } %>
                    </select>
                    <international:get name="px"/>
                </td>
            </tr>
            <tr>
                <td style="padding: 5px;"><international:get name="rowCount"/></td>
                <td>
                    <select size="1" id="configureGalleryNavigationThumbnailsRows">
                        <% for (int i = 1; i <= 40; i++) { %>
                        <option value="<%= i %>" <% if (i == 2) { %>selected="selected" <% } %>><%= i %>
                        </option>
                        <% } %>
                    </select>
                </td>
                <td><international:get name="cellBorderStyle"/></td>
                <td>
                    <select size="1" id="configureGallryNavigationThumbnailsBorderStyle">
                        <option value="solid" selected="selected">solid</option>
                        <option value="dashed">dashed</option>
                        <option value="dotted">dotted</option>
                        <option value="double">double</option>
                        <option value="groove">groove</option>
                        <option value="ridge">ridge</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="padding: 5px;"><international:get name="thumbnailWidth"/></td>
                <td>
                    <input type="text" id="configureGalleryNavigationThumbnailsWidth" maxlength="255"
                           onKeyPress="return numbersOnly(this, event);">
                    <international:get name="px"/>
                </td>
                <td><international:get name="cellBorderColor"/></td>
                <td id="configureGalleryNavigationThumbnailBorderColorContainer"></td>
            </tr>
            <tr>
                <td style="padding: 5px;"><international:get name="thumbnailHeight"/></td>
                <td>
                    <input type="text" id="configureGalleryNavigationThumbnailsHeight" maxlength="255"
                           onKeyPress="return numbersOnly(this, event);">
                    <international:get name="px"/>
                </td>
                <td><international:get name="cellBackgroundColor"/></td>
                <td id="configureGalleryNavigationThumbnailBackgroundColorContainer"></td>
            </tr>
            <tr>
                <td style="padding:5px;"><international:get name="cellWidth"/></td>
                <td>
                    <input type="text" id="configureGalleryNavigationThumbnailsCellWidth" maxlength="255"
                           onKeyPress="return numbersOnly(this, event);">
                    <international:get name="px"/>
                </td>
                <td><international:get name="cellHorizontalMargin"/></td>
                <td>
                    <input type="text" id="configureGalleryNavigationThumbnailsHorizontalMargin" maxlength="255"
                           onKeyPress="return numbersOnly(this, event);">
                    <international:get name="px"/>
                </td>
            </tr>
            <tr>
                <td style="padding:5px;"><international:get name="cellHeight"/></td>
                <td>
                    <input type="text" value="100px" id="configureGalleryNavigationThumbnailsCellHeight" maxlength="255"
                           onKeyPress="return numbersOnly(this, event);">
                    <international:get name="px"/>
                </td>
                <td><international:get name="cellVerticalMargin"/></td>
                <td>
                    <input type="text" id="configureGalleryNavigationThumbnailsVerticalMargin" maxlength="255"
                           onKeyPress="return numbersOnly(this, event);">
                    <international:get name="px"/>
                </td>
            </tr>
        </table>

        <br><br>

        <div style="text-align: right;">
            <input type="button" onclick="saveConfigureGalleryNavigationThumbnails();"
                   value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
            <input type="button" onclick="closeConfigureWidgetDiv();"
                   value="<international:get name="cancel"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
        </div>
    </div>
</div>