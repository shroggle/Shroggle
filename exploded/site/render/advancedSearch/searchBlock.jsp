<%@ page import="com.shroggle.entity.*" %>
<%@ page import="java.util.Random" %>
<%@ page import="com.shroggle.logic.advancedSearch.AdvancedSearchHelper" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.logic.advancedSearch.AdvancedSearchManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.logic.advancedSearch.resultsNumber.AdvancedSearchResultsNumberCreator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ page import="com.shroggle.logic.form.LinkedFormManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="advancedSearch"/>
<%
    final Widget widget = (Widget) request.getAttribute("widget");
    final List<Integer> currentGalleryItemsHashCodes = (List<Integer>) request.getAttribute("currentGalleryItemsHashCodes");
    final List<Integer> fullGalleryItemsHashCodes = (List<Integer>) request.getAttribute("fullGalleryItemsHashCodes");
    final DraftAdvancedSearch advancedSearch = (DraftAdvancedSearch) request.getAttribute("advancedSearch");
    final DraftAdvancedSearch existingAdvancedSearchRequest =
            ServiceLocator.getContextStorage().get().getAdvancedSearchRequestById(advancedSearch.getId());

    new AdvancedSearchHelper().sortOptionsByPosition(advancedSearch.getAdvancedSearchOptions());

    final boolean orientationABOVE = advancedSearch.getAdvancedSearchOrientationType() == AdvancedSearchOrientationType.ABOVE;

    final AdvancedSearchResultsNumberCreator resultsNumberCreator = new AdvancedSearchResultsNumberCreator
            (advancedSearch.getId(), advancedSearch.getGalleryId(), currentGalleryItemsHashCodes, fullGalleryItemsHashCodes);
    final boolean includeResultsNumber = advancedSearch.isIncludeResultsNumber();
    final SiteShowOption siteShowOption = (SiteShowOption) request.getAttribute("siteShowOption");
%>
<input type="hidden" id="advSearchBothDateAreEmptyException<%= widget.getWidgetId() %>"
       value="<international:get name="advSearchBothDateAreEmptyException"/>"/>
<input type="hidden" id="advSearchStartDateIsntValidException<%= widget.getWidgetId() %>"
       value="<international:get name="advSearchStartDateIsntValidException"/>"/>
<input type="hidden" id="advSearchEndDateIsntValidException<%= widget.getWidgetId() %>"
       value="<international:get name="advSearchEndDateIsntValidException"/>"/>
<input type="hidden" id="includeResultsNumber<%= widget.getWidgetId() %>" value="<%= includeResultsNumber %>"/>

<div class="searchBlock" id="advancedSearchBlock<%= widget.getWidgetId() %>">
<% if (advancedSearch.isDisplayHeaderComments()) {
    String result = advancedSearch.getDescription();
%>
<div class="advancedSearchHeader">
    <%= result %>
</div>
<% } %>

<% for (int i = 0; i < advancedSearch.getAdvancedSearchOptions().size(); i++) { %>
<% final DraftAdvancedSearchOption searchOption = advancedSearch.getAdvancedSearchOptions().get(i); %>
<% final DraftAdvancedSearchOption existingSearchOptionRequest = existingAdvancedSearchRequest != null ?
        new AdvancedSearchManager(existingAdvancedSearchRequest).getSearchOptionById(searchOption.getAdvancedSearchOptionId()) : null; %>
<% final String compositeUniqueId = widget.getWidgetId() + "" + searchOption.getAdvancedSearchOptionId(); %>
<%
    final DraftFormItem searchOptionFormItem = ServiceLocator.getPersistance().getFormItemById(searchOption.getFormItemId()); %>
<div class="advancedSearchOption
    <% if (orientationABOVE) { %> advancedSearchOptionInlined<% } %>"
     id="advancedSearchOption<%= compositeUniqueId %>">
    <input type="hidden" id="optionType<%= compositeUniqueId %>"
           value="<%= searchOption.getDisplayType() %>"/>
    <input type="hidden" class="advancedSearchOptionId"
           value="<%= searchOption.getAdvancedSearchOptionId() %>"/>

    <div class="advancedSearchOptionHeader"
         onclick="renderWidgetAdvancedSearch.collapseOrExpandOption(event, this, <%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"
         id="advancedSearchOptionHeader<%= compositeUniqueId %>">
        <img src="/images/minus.png" alt=""
             id="advancedSearchOptionCollapseImg<%= compositeUniqueId %>"
             class="advSearchCollapseImg"/><span
            class="searchOptionHeaderText"><%= searchOption.getFieldLabel() %></span>
        <% if (searchOption.getDisplayType() == OptionDisplayType.PICK_LIST_MULTISELECT) { %>
        <a href="javascript:renderWidgetAdvancedSearch.deselectLinkClick(<%= compositeUniqueId %>, <%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"
           class="advSearchLink deselectLink" id="deselectLink<%= compositeUniqueId %>"><international:get
                name="deselect"/></a>
        <% } %>
    </div>
    <div class="advancedSearchOptionBody <%= searchOption.getDisplayType().isMultiselect() ? "advancedSearchPickListDiv" : "" %>"
         id="advancedSearchOptionBody<%= compositeUniqueId %>">
        <% if (searchOption.getDisplayType() == OptionDisplayType.PICK_LIST_SELECT) { %>
        <select id="searchPickListSelect<%= compositeUniqueId %>" class="advancedSearchSelect"
                onchange="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);">
            <option value="null"><international:get name="selectDefaultOption"/></option>
            <% for (String criteria : searchOption.getOptionCriteria()) { %>
            <option value="<%= criteria %>" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(criteria)) { %>selected="selected"<% } %>>
                <%= criteria %>
                <% if (includeResultsNumber) { %>
                (<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %>
            </option>
            <% } %>
        </select>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.SINGLE_CHECKBOX) { %>
        <% int uniqueCriteriaId = new Random().nextInt(); %>
        <% final String criteria = searchOption.getOptionCriteria().get(0); %>
        <div>
            <input type="checkbox" id="singleCheckboxChecked<%= compositeUniqueId %>" value="<%= FormCheckboxValueType.CHECKED %>"
                   <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(FormCheckboxValueType.CHECKED.getValue())) { %>checked="checked"<% } %>
                   onclick="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"/>
            <label for="singleCheckboxChecked<%= compositeUniqueId %>">
                <span class="advancedSearchOptionCriteria">Checked <%= criteria %></span>
                <% if (includeResultsNumber) { %>
                <span class="advancedSearchOptionResultsNumber">(<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %></span>
            </label>
        </div>

        <div>
            <input type="checkbox" id="singleCheckboxUnchecked<%= compositeUniqueId %>" value="<%= FormCheckboxValueType.UNCHECKED %>"
                   <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(FormCheckboxValueType.UNCHECKED.getValue())) { %>checked="checked"<% } %>
                   onclick="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"/>
            <label for="singleCheckboxUnchecked<%= compositeUniqueId %>">
                <span class="advancedSearchOptionCriteria">Unchecked <%= criteria %></span>
                <% if (includeResultsNumber) { %>
                <span class="advancedSearchOptionResultsNumber">(<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %></span>
            </label>
        </div>
        <br/>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.PICK_LIST_MULTISELECT) { %>
        <% for (String criteria : searchOption.getOptionCriteria()) { %>
        <% int uniqueCriteriaId = new Random().nextInt(); %>
        <input type="checkbox" id="pickListMultiselect<%= uniqueCriteriaId %>" value="<%= criteria %>"
               <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(criteria)) { %>checked="checked"<% } %>
               onclick="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"/>
        <label for="pickListMultiselect<%= uniqueCriteriaId %>">
            <span class="advancedSearchOptionCriteria"><%= criteria %></span>
            <% if (includeResultsNumber) { %>
                <span class="advancedSearchOptionResultsNumber">(<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %></span>
        </label>
        <br/>
        <% } %>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.TEXT_AS_SEP_OPTION) { %>
        <% new AdvancedSearchHelper().addSeparateOptionCriteria(searchOption); %>
        <select id="searchTextAsSepOptionSelect<%= compositeUniqueId %>" class="advancedSearchSelect"
                onchange="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);">
            <option value="null"><international:get name="selectDefaultOption"/></option>
            <% for (String criteria : searchOption.getOptionCriteria()) { %>
            <option value="<%= criteria %>" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(criteria)) { %>selected="selected"<% } %>
                    formItemName="<%= searchOptionFormItem.getFormItemName() %>"
                    linkedValue="<%= searchOptionFormItem.getFormItemName() == FormItemName.LINKED ? LinkedFormManager.getLinkedValue(criteria) : "" %>">
                <%= searchOptionFormItem.getFormItemName() == FormItemName.LINKED ? LinkedFormManager.getLinkedValue(criteria) : criteria %>
                <% if (includeResultsNumber) { %>
                (<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %>
            </option>
            <% } %>
        </select>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.TEXT_AS_FREE) { %>
        <input type="text" id="searchTextAsFreeInput<%= compositeUniqueId %>" maxlength="255"
               <% if (existingSearchOptionRequest != null && !existingSearchOptionRequest.getOptionCriteria().isEmpty() &&
                 !StringUtil.isNullOrEmpty(existingSearchOptionRequest.getOptionCriteria().get(0))) { %>value="<%= existingSearchOptionRequest.getOptionCriteria().get(0) %>"<% } %>/>
        <a class="advSearchLink" href="javascript:renderWidgetAdvancedSearch.
            executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>)"><international:get
                name="search"/></a>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_RANGE) { %>
        <% for (String criteria : searchOption.getOptionCriteria()) { %>
        <% int uniqueCriteriaId = new Random().nextInt(); %>
        <input type="checkbox" id="rangeAsRangeCriteria<%= uniqueCriteriaId %>" value="<%= criteria %>"
               <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(criteria)) { %>checked="checked"<% } %>
               onclick="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"/>
        <label for="rangeAsRangeCriteria<%= uniqueCriteriaId %>"><span
                class="advancedSearchOptionCriteria"><%= AdvancedSearchHelper.formatRange(searchOptionFormItem, criteria, true) %></span>
            <% if (includeResultsNumber) { %>
                <span class="advancedSearchOptionResultsNumber">(<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %></span>
        </label>
        <br/>
        <% } %>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_SEP_OPTION) { %>
        <% new AdvancedSearchHelper().addSeparateOptionCriteria(searchOption); %>
        <% for (String criteria : searchOption.getOptionCriteria()) { %>
        <% int uniqueCriteriaId = new Random().nextInt(); %>
        <input type="checkbox" id="rangeAsSepOptionCriteria<%= uniqueCriteriaId %>" value="<%= criteria %>"
               <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains(criteria)) { %>checked="checked"<% } %>
               onclick="renderWidgetAdvancedSearch.executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>);"/>
        <label for="rangeAsSepOptionCriteria<%= uniqueCriteriaId %>"><span
                class="advancedSearchOptionCriteria"><%= AdvancedSearchHelper.formatRange(searchOptionFormItem, criteria, false) %></span>
            <% if (includeResultsNumber) { %>
                <span class="advancedSearchOptionResultsNumber">(<%= resultsNumberCreator.getResultsNumber(searchOption.getAdvancedSearchOptionId(), criteria, siteShowOption) %>)
                <% } %></span>
        </label>
        <br/>
        <% } %>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.RANGE_AS_RANGE_INPUTS) { %>
        <% request.setAttribute("rangeInputsFormItem", searchOptionFormItem); %>
        <% request.setAttribute("rangeInputsFromCaption",
                ServiceLocator.getInternationStorage().get("advancedSearch", Locale.US).get("rangeStart")); %>
        <% request.setAttribute("rangeInputsTillCaption",
                ServiceLocator.getInternationStorage().get("advancedSearch", Locale.US).get("rangeEnd")); %>
        <jsp:include page="/advancedSearch/advancedSearchRangeInputs.jsp"/>
        <a class="advSearchLink" href="javascript:renderWidgetAdvancedSearch.
            executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>)"><international:get
                name="search"/></a>

        <div id="addRangeError<%= compositeUniqueId %>" style="color:red;display:inline;opacity:0;"></div>
        <% } else if (searchOption.getDisplayType() == OptionDisplayType.POST_CODE) { %>
        <international:get name="enterZipCode"/><input type="text" id="zipCode<%= compositeUniqueId %>"
                                                       maxlength="255"
                                                       <% if (existingSearchOptionRequest != null && !existingSearchOptionRequest.getOptionCriteria().isEmpty() &&
                 !StringUtil.isNullOrEmpty(existingSearchOptionRequest.getOptionCriteria().get(0))) { %>value="<%= existingSearchOptionRequest.getOptionCriteria().get(0) %>"<% } %>/>
        <select id="zipCodeSelect<%= compositeUniqueId %>" class="advancedSearchSelect">
            <option value="null"><international:get name="selectDefaultOption"/></option>
            <option value="3" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("3")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption1"/></option>
            <option value="5" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("5")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption2"/></option>
            <option value="10" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("10")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption3"/></option>
            <option value="20" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("20")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption4"/></option>
            <option value="50" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("50")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption5"/></option>
            <option value="100" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("100")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption6"/></option>
            <option value="200" <% if (existingSearchOptionRequest != null &&
                 existingSearchOptionRequest.getOptionCriteria().contains("200")) { %>selected="selected"<% } %>>
                <international:get name="selectPostCodeOption7"/></option>
        </select>
        <a class="advSearchLink" href="javascript:renderWidgetAdvancedSearch.
            executeSearch(<%= widget.getWidgetId() %>, <%= searchOption.getAdvancedSearchOptionId() %>)"><international:get
                name="search"/></a>
        <% } %>
    </div>
</div>
<% if (orientationABOVE && (i + 1) % 3 == 0) { %>
<div style="clear:both;"></div>
<% } %>
<% } %>

<% if (orientationABOVE) { %>
<div style="clear:both;"></div>
<% } %>
<div class="settings_block">
    <input type="button" value="<international:get name="newSearchBtn"/>"
           onclick="renderWidgetAdvancedSearch.resetSearch(<%= widget.getWidgetId() %>);"/>
</div>
</div>

