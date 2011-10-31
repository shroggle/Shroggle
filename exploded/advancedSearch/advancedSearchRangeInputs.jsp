<%@ page import="com.shroggle.entity.DraftFormItem" %>
<%@ page import="com.shroggle.entity.FormItemCheckerType" %>
<% final DraftFormItem formItem = (DraftFormItem) request.getAttribute("rangeInputsFormItem"); %>
<% final String fromCaption = (String) request.getAttribute("rangeInputsFromCaption"); %>
<% final String tillCaption = (String) request.getAttribute("rangeInputsTillCaption"); %>
<script type="text/javascript">

    function processDateInputKey(input, e){
        var keyCode = (window.event) ? event.keyCode : e.keyCode;

        if (keyCode == 39) {
            if ($(input).hasClass('rangeEndLastInput') || ($(input).val().length != getCaretInfo(input).caret)){
                return;
            }
            var nextInput = $(input).next("input:first")[0] ? $(input).next("input:first")[0] :
                            $(input).parents("table:first").find(".rangeEnd").find("input:first")[0];
            nextInput.focus();
        } else if (keyCode == 37) {
            if ($(input).hasClass('rangeStartFirstInput') || getCaretInfo(input).caret != 0){
                return;
            }
            var prevInput = $(input).prev("input:first")[0] ? $(input).prev("input:first")[0] :
                            $(input).parents("table:first").find(".rangeStart").find("input:last")[0];
            prevInput.focus();
        }
    }

</script>
<% if (formItem.getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)) { %>
<input type="hidden" class="onlyNumbersRange"/>
<% } %>
<table>
    <tr class="rangeStart">
        <td>
            <%= fromCaption %>
        </td>
        <td>
            <% for (int j = 0; j < formItem.getFormItemName().getType().getFieldsCount(); j++) { %>
            <% final Integer dateCharacterLimit = formItem.getFormItemName().isDate() ? ((j == 0 || j == 1 || j == 3 || j == 4) ? 2 : 4) : null; %>
            <input class="rangeStart advSearchRangeInput <% if (j == 0) { %>rangeStartFirstInput<% } %>" type="text" maxlength="255" onkeydown="processDateInputKey(this);"
                   onkeypress="return numbersOnly(this, event) <% if (dateCharacterLimit != null) { %>
                   && limitMaxInputCharacters(<%= dateCharacterLimit %>, this, event, $(this).next('input.advSearchRangeInput')[0])
                   <% } %>;"/>
            <% if ((j == 0 || j == 1) && (formItem.getFormItemName().isDate())) { %>
            /
            <% } else if ((j == 3) && (formItem.getFormItemName().isDate())) { %>
            :
            <% } %>
            <% } %>
        </td>
    </tr>
    <tr class="rangeEnd">
        <td>
            <%= tillCaption %>
        </td>
        <td>
            <% for (int j = 0; j < formItem.getFormItemName().getType().getFieldsCount(); j++) { %>
            <% final Integer dateCharacterLimit = formItem.getFormItemName().isDate() ? ((j == 0 || j == 1 || j == 3 || j == 4) ? 2 : 4) : null; %>
            <input class="rangeEnd advSearchRangeInput <% if (j == formItem.getFormItemName().getType().getFieldsCount() - 1) { %>rangeEndLastInput<% } %>" type="text" maxlength="255" onkeydown="processDateInputKey(this);"
                   onkeypress="return numbersOnly(this, event) <% if (dateCharacterLimit != null) { %>
                   && limitMaxInputCharacters(<%= dateCharacterLimit %>, this, event, $(this).next('input.advSearchRangeInput')[0])
                   <% } %>;"/>
            <% if ((j == 0 || j == 1) && (formItem.getFormItemName().isDate())) { %>
            /
            <% } else if ((j == 3) && (formItem.getFormItemName().isDate())) { %>
            :
            <% } %>
            <% } %>
        </td>
    </tr>
</table>