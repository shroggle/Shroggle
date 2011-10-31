<%@ page import="com.shroggle.entity.GalleryDataPaginatorType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGalleryDataPageSelector"/>
<textarea onfocus="trimTextArea(this);" rows="5" cols="5" style="display: none;" id="configureGalleryDataPageSelectorInternational">

    window.configureGalleryInternational.dataPageSelector = {
        ARROWS: "Arrows",
        PREV_NEXT: "Prev and Next",
        PREVIOUS_NEXT: "Previous and Next",
        PREVIOUS_NEXT_WITH_NUMBERS: "Prev and Next with numbers",
        PREVIOUS_NEXT_WITH_BORDERED_NUMBERS: "Previous and Next with bordered numbers"
    };

</textarea>
<div id="configureGalleryDataPageSelector" style="display: none;">
    <div class="windowOneColumn">
        <h2><international:get name="title"/></h2>
        <br>
        <international:get name="description"/><br><br>

        <br>
        <input type="radio" id="configureGalleryDataPageSelectorPaginator"
               onchange="selectConfigureGalleryDataPageSelector();"
               name="configureGalleryDataPageSelectorType">
        <label for="configureGalleryDataPageSelectorPaginator">
            <international:get name="paginator"/>
        </label>
        <br>
        <international:get name="paginatorDescription"/><br>
        <div style="padding-left: 10px; padding-top: 5px;">
            <input type="radio" id="configureGalleryDataPageSelectorPaginator0" checked="checked"
                   name="configureGalleryDataPageSelectorPaginator"
                   value="<%= GalleryDataPaginatorType.PREV_NEXT %>">
            <label for="configureGalleryDataPageSelectorPaginator0">
                <u>&lt; Prev</u> <u>Next &gt;</u>
            </label><br>    
            <input type="radio" id="configureGalleryDataPageSelectorPaginator1" checked="checked"
                   name="configureGalleryDataPageSelectorPaginator"
                   value="<%= GalleryDataPaginatorType.PREVIOUS_NEXT %>">
            <label for="configureGalleryDataPageSelectorPaginator1">
                <u>&lt; Previous</u> <u>Next &gt;</u>
            </label><br>
            <input type="radio" id="configureGalleryDataPageSelectorPaginator2"
                   name="configureGalleryDataPageSelectorPaginator"
                   value="<%= GalleryDataPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS %>">
            <label for="configureGalleryDataPageSelectorPaginator2">
                <u>&lt; Prev</u> 1 <u>2</u> <u>3</u> <u>4</u> <u>5</u> <u>Next &gt;</u>
            </label>
            <br>
            <input type="radio" id="configureGalleryDataPageSelectorPaginator3"
                   name="configureGalleryDataPageSelectorPaginator"
                   value="<%= GalleryDataPaginatorType.PREVIOUS_NEXT_WITH_BORDERED_NUMBERS %>">
            <label for="configureGalleryDataPageSelectorPaginator3">
                <span style="color: #ffffff; background-color: #000000;">1</span>
                <u style="border: #000000 1px solid">2</u>
                <u style="border: #000000 1px solid">3</u>
                <u style="border: #000000 1px solid">4</u>
                <u style="border: #000000 1px solid">5</u>
            </label>
        </div><br>

        <input type="radio" id="configureGalleryDataPageSelectorArrow"
               onchange="selectConfigureGalleryDataPageSelector();"
               name="configureGalleryDataPageSelectorType">
        <label for="configureGalleryDataPageSelectorArrow">
            <international:get name="arrow"/>
        </label>
        <br>
        <div style="padding-left: 10px; padding-top: 5px; overflow-x: auto;">
            <table>
                <tr>
                    <td style="padding: 0 10px">
                        <input type="radio" checked="checked"
                               id="configureGalleryDataPageSelectorArrows1"
                               value="/images/gallery/arrow1Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows1">
                            <img src="/images/gallery/arrow1Right.gif" height="75" width="80">
                        </label>
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               id="configureGalleryDataPageSelectorArrows2"
                               value="/images/gallery/arrow2Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows2">
                            <img src="/images/gallery/arrow2Right.gif" height="59" width="80">
                        </label>
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               id="configureGalleryDataPageSelectorArrows3"
                               value="/images/gallery/arrow3Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows3">
                            <img src="/images/gallery/arrow3Right.gif" height="80" width="80">
                        </label>
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               id="configureGalleryDataPageSelectorArrows4"
                               value="/images/gallery/arrow4Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows4">
                            <img src="/images/gallery/arrow4Right.gif" height="82" width="80">
                        </label>
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               id="configureGalleryDataPageSelectorArrows5"
                               value="/images/gallery/arrow5Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows5">
                            <img src="/images/gallery/arrow5Right.gif" height="80" width="80">
                        </label>
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               value="/images/gallery/arrow6Right.gif"
                               id="configureGalleryDataPageSelectorArrows6"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows6">
                            <img src="/images/gallery/arrow6Right.gif" height="69" width="80">
                        </label>    
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               id="configureGalleryDataPageSelectorArrows7"
                               value="/images/gallery/arrow7Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows7">
                            <img src="/images/gallery/arrow7Right.gif" height="80" width="80">
                        </label>
                    </td>
                    <td style="padding: 0 10px">
                        <input type="radio"
                               id="configureGalleryDataPageSelectorArrows8"
                               value="/images/gallery/arrow8Right.gif"
                               name="configureGalleryDataPageSelectorArrows">
                        <label for="configureGalleryDataPageSelectorArrows8">
                            <img src="/images/gallery/arrow8Right.gif" height="80" width="80">
                        </label>
                    </td>
                </tr>
            </table>
        </div>

        <div style="text-align: right;">
            <input type="button" onclick="saveConfigureGalleryDataPageSelector();"
                   value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
            <input type="button" onclick="closeConfigureWidgetDiv();"
                   value="<international:get name="cancel"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
        </div>
    </div>
</div>