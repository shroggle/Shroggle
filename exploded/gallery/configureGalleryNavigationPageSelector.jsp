<%@ page import="com.shroggle.entity.GalleryNavigationPaginatorType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureGalleryNavigationPageSelector"/>
<textarea onfocus="trimTextArea(this);" rows="5" cols="5" style="display: none;"
          id="configureGalleryNavigationPageSelectorInternational">

    window.configureGalleryInternational.navigationPageSelector = {
        SCROLL_VERTICALLY: "Scroll vertically",
        SCROLL_HORIZONTALLY: "Scroll horizontally",
        PREV_NEXT: "Prev and Next",
        PREVIOUS_NEXT: "Previous and Next",
        PREVIOUS_NEXT_WITH_NUMBERS: "Prev and Next with numbers",
        PREVIOUS_NEXT_WITH_BORDERED_NUMBERS: "Previous and Next with bordered numbers",
        PICK_LIST_WITH_NUMBERS: "Go To Page "
    };

</textarea>

<div id="configureGalleryNavigationPageSelector" style="display: none;">
    <div class="windowOneColumn">
        <h2><international:get name="title"/></h2>
        <br>
        <international:get name="description"/><br><br>

        <input type="radio" id="configureGalleryNavigationPageSelectorScroll"
               name="configureGalleryNavigationPageSelectorType" checked="true"
               onclick="selectConfigureGalleryNavigationPageSelector();disablePaginatorGoToSelect();">
        <label for="configureGalleryNavigationPageSelectorScroll">
            <international:get name="scroll"/>
        </label>
        <br>

        <div style="padding-left: 10px; padding-top: 5px;">
            <input type="radio" id="configureGalleryNavigationPageSelectorScrollVerticall" checked="true"
                   value="<%= GalleryNavigationPaginatorType.SCROLL_VERTICALLY %>"
                   name="configureGalleryNavigationPageSelectorScroll">
            <label for="configureGalleryNavigationPageSelectorScrollVerticall">
                <international:get name="scrollVerticall"/>
            </label>
            <br>
            <input type="radio" id="configureGalleryNavigationPageSelectorScrollHorizontal"
                   value="<%= GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY %>"
                   name="configureGalleryNavigationPageSelectorScroll">
            <label for="configureGalleryNavigationPageSelectorScrollHorizontal">
                <international:get name="scrollHorizontal"/>
            </label>
        </div>

        <br>
        <input type="radio" id="configureGalleryNavigationPageSelectorPaginator"
               class="configureGalleryNavigationPageSelectorType"
               name="configureGalleryNavigationPageSelectorType"
               onclick="selectConfigureGalleryNavigationPageSelector();disablePaginatorGoToSelect();">
        <label for="configureGalleryNavigationPageSelectorPaginator">
            <international:get name="paginator"/>
        </label>
        <br>
        <international:get name="paginatorDescription"/><br>

        <div style="padding-left: 10px; padding-top: 5px;">
            <input type="radio" id="configureGalleryNavigationPageSelectorPaginator1"
                   class="configureGalleryNavigationPageSelector" checked="true" onclick="disablePaginatorGoToSelect();"
                   value="<%= GalleryNavigationPaginatorType.PREV_NEXT %>"
                   name="configureGalleryNavigationPageSelectorPaginator">
            <label for="configureGalleryNavigationPageSelectorPaginator1">
                <u>&lt; Prev</u> <u>Next &gt;</u>
            </label><br>
            <input type="radio" id="configureGalleryNavigationPageSelectorPaginator2"
                   value="<%= GalleryNavigationPaginatorType.PREVIOUS_NEXT %>"
                   class="configureGalleryNavigationPageSelector"  onclick="disablePaginatorGoToSelect();"
                   name="configureGalleryNavigationPageSelectorPaginator">
            <label for="configureGalleryNavigationPageSelectorPaginator2">
                <u>&lt; Previous</u> <u>Next &gt;</u>
            </label>
            <br>
            <input type="radio" id="configureGalleryNavigationPageSelectorPaginator3"
                   value="<%= GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS %>"
                   class="configureGalleryNavigationPageSelector" onclick="disablePaginatorGoToSelect();"
                   name="configureGalleryNavigationPageSelectorPaginator">
            <label for="configureGalleryNavigationPageSelectorPaginator3">
                <u>&lt; Prev</u> 1 <u>2</u> <u>3</u> <u>4</u> <u>5</u> <u>Next &gt;</u>
            </label>
            <br>
            <input type="radio" id="configureGalleryNavigationPageSelectorPaginator4"
                   class="configureGalleryNavigationPageSelector" onclick="disablePaginatorGoToSelect();"
                   name="configureGalleryNavigationPageSelectorPaginator"
                   value="<%= GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_BORDERED_NUMBERS %>">
            <label for="configureGalleryNavigationPageSelectorPaginator4">
                <span style="color: #ffffff; background-color: #000000;">1</span>
                <u style="border: #000000 1px solid">2</u>
                <u style="border: #000000 1px solid">3</u>
                <u style="border: #000000 1px solid">4</u>
                <u style="border: #000000 1px solid">5</u>
            </label>
            <br>
            <input type="radio" id="configureGalleryNavigationPageSelectorPaginator5"
                   class="configureGalleryNavigationPageSelector" onclick="disablePaginatorGoToSelect();"
                   name="configureGalleryNavigationPageSelectorPaginator"
                   value="<%= GalleryNavigationPaginatorType.PICK_LIST_WITH_NUMBERS %>">
            <label for="configureGalleryNavigationPageSelectorPaginator5">
                <international:get name="goToPage"/>
            </label>
            <select id="paginatorGoToSelect" name="configureGalleryNavigationPageSelectorPaginator">
                <% for (int i = 1; i < 7; i++) { %>
                <option>
                    <%= i %>
                </option>
                <% } %>
                <option>
                    ...
                </option>
                <option>
                    10
                </option>
            </select>
        </div>
        <br><br>

        <div style="text-align: right;">
            <input type="button" onclick="saveConfigureGalleryNavigationPageSelector();"
                   value="<international:get name="save"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
            <input type="button" onclick="closeConfigureWidgetDiv();"
                   value="<international:get name="cancel"/>" onmouseout="this.className='but_w73';"
                   onmouseover="this.className='but_w73_Over';" class="but_w73">
        </div>
    </div>
</div>
