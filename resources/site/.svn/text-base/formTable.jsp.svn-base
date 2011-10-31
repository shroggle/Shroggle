<%-- !!! I'll break your hands for ctrl+alt+L !!! --%>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.presentation.form.ShowForm" %>
<%@ page import="com.shroggle.util.html.HtmlUtil" %>
<%@ page import="com.shroggle.logic.form.FormTypeGetter" %>
<%@ page import="com.shroggle.entity.*" %>
<%@ page import="com.shroggle.logic.form.FormItemManager" %>
<%@ page import="java.util.Random" %>
<% final ShowForm formService = ((ShowForm) request.getAttribute("formService")); %>
<% final International formInternational = ServiceLocator.getInternationStorage().get("formTable", Locale.US); %>

<% for (FormItemName formItemName : FormTypeGetter.getByType(FormItemType.SPECIAL)) { %>
<input type="hidden" class="specialFormField" value="<%= formItemName %>"/>
<% } %>
<% for (FormItemName formItemName : FormTypeGetter.getMandatory()) { %>
<input type="hidden" class="mandatoryFormField" value="<%= formItemName %>"/>
<% } %>
<% for (FormItemName formItemName : FormTypeGetter.getAlwaysRequired()) { %>
<input type="hidden" class="alwaysRequiredFormField" value="<%= formItemName %>"/>
<% } %>

<input type="hidden" id="formTableSelectedFormId"
       value="<%= formService.getForm() != null ? formService.getForm().getFormId() : "null"%>"/>
<%-- FORM FILTERS --%>
<div style="margin-bottom:3px;"><%= formInternational.get("selectFields") %></div>
<label for="filterSelect"><%= formInternational.get("filterLabel") %></label>
<select style="display:inline;" id="filterSelect" onchange="updateByFilter();">
    <option value="NO_FILTER"><%= formInternational.get("NO_FILTER") %>
    </option>
    <option value="BASIC" selected="selected"><%= formInternational.get("BASIC") %>
    </option>
    <option value="CRM_TICKET_TRACKING"><%= formInternational.get("CRM_TICKET_TRACKING") %>
    </option>
    <option value="PERSONAL_DATING"><%= formInternational.get("PERSONAL_DATING") %>
    </option>
    <option value="FAMILY"><%= formInternational.get("FAMILY") %>
    </option>
    <option value="JOBS_EMPLOYMENT"><%= formInternational.get("JOBS_EMPLOYMENT") %>
    </option>
    <option value="VACATION_RENTAL"><%= formInternational.get("VACATION_RENTAL") %>
    </option>
    <option value="REAL_ESTATE"><%= formInternational.get("REAL_ESTATE") %>
    </option>
    <option value="FILM"><%= formInternational.get("FILM") %>
    </option>
    <option value="EVENTS"><%= formInternational.get("EVENTS") %>
    </option>
    <option value="PRODUCTS"><%= formInternational.get("PRODUCTS") %>
    </option>
    <option value="GALLERY"><%= formInternational.get("GALLERY") %>
    </option>
    <option value="PLANTS"><%= formInternational.get("PLANTS") %>
    </option>
    <option value="CREATE_A_SITE"><%= formInternational.get("CREATE_A_SITE") %>
    </option>
</select>

<div id="dublicateFieldExceptionText" class="inlineError"><%= formInternational.get("dublicateFieldExceptionText") %>
</div>
<div style="clear:both;"></div>

<%-- INIT FORM TABLE --%>
<div class="init_form_div" id="initFormDiv">
    <div class="tbl_dblborder">
        <table id="formTableHead" class="span-17" style="width:100%;overflow-y:visible;">
            <thead>
            <tr>
                <td style="width:20px !important">
                </td>
                <td style="width:160px !important">
                    <%= formInternational.get("fieldName") %>
                </td>
                <td>
                    <%= formInternational.get("contentType") %>
                </td>
            </tr>
            </thead>
        </table>
        <div class="registrationParameterDiv">
            <table id="formTableBody" class="span-17" style="width:100%;border-collapse:collapse;" cellpadding="0" cellspacing="0" >
                <tbody>
                <%
                    for (int i = 0; i < formService.getInitFormItems().size(); i++) {
                        FormItemName item = formService.getInitFormItems().get(i);
                %>
                <tr>
                    <td style="width:20px !important;text-align:center;">
                        <input type="checkbox" name="initFormCheckbox"
                               id="<%=i%>">
                    </td>
                    <td id="initFormItemNameTD_<%=i%>" style="width:160px !important">
                        <input type="hidden" class="initFormItemNameText"
                               value="<%= formInternational.get(item.toString() + "_FN") %>"/>
                        <input type="hidden" class="initFormItemName" value="<%= item.toString() %>"/>
                        <label for="<%= i %>"><%= formInternational.get(item.toString() + "_FN") %>
                        </label>
                    </td>
                    <td>
                        <label for="<%= i %>"><%= FormItemManager.getItemFieldType(item) %>
                        </label>

                        <% String description = FormItemManager.getItemDesc(item).trim(); %>
                        <% if (!description.isEmpty()) { %>
                        <div class="initTableDescriptionInfoMark">
                            <span class="inform_mark" style="cursor:pointer"
                                  onmouseover="this.className = 'inform_mark_Over';bindTooltip({contentId:'description<%= i %>', element:this, width:400});"
                                  onmouseout="this.className = 'inform_mark';">&nbsp;</span>
                        </div>
                        <% } %>

                        <div style="display:none" id="description<%= i %>">
                            <span style="font-weight: bold;"><%= formInternational.get("desc") %></span>:<br>
                            <%= description %>
                        </div>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%-- MOVE ITEMS BETWEEN TABLES CONTROLS --%>
<div class="manage_form_div">
    <a href="javascript:addFormItem();"
       id="addFormItemLink"><%= formInternational.get("add") %></a>
    <br>
    <img src="/images/add_arrow.gif" alt="" onclick="addFormItem();" id="addFormItemImg" style="cursor:pointer"
                     detectControlClick="true"/>
    <br><br>
    <a href="javascript:removeFormItem();"
       id="removeFormItemLink"><%= formInternational.get("remove") %></a>
    <br>
    <img src="/images/remove_arrow.gif" alt="" onclick="removeFormItem();" id="removeFormItemImg"
                     detectControlClick="true"
         style="cursor:pointer"/>
</div>

<%--YOUR FORM TABLE--%>
<div class="your_form_div" id="your_form_div">
    <div class="tbl_dblborder" id="your_form_div_dblborder">
        <table id="yourFormTableHead" class="span-17" style="width:100%;">
            <thead>
            <tr>
                <td style="width:15px !important">
                    <%= formInternational.get("number") %>
                </td>
                <td style="width:77px !important">
                    <%= formInternational.get("action") %>
                </td>
                <td style="width:118px !important">
                    <%= formInternational.get("editFieldName") %>
                </td>
                <td style="width:110px !important">
                    <%= formInternational.get("fieldName") %>
                </td>
                <td style="width:28px !important" title="<%= formInternational.get("required_full") %>">
                    <%= formInternational.get("required") %>
                </td>
                <td title="<%= formInternational.get("instruction_full") %>">
                    <%= formInternational.get("instruction") %>
                </td>
            </tr>
            </thead>
        </table>
        <div class="registrationParameterDiv">
            <table class="span-17" style="width:100%;">
                <tbody id="yourFormTableBody">
                <%
                    int i = 0;
                    for (FormItem item : formService.getExistingFormItems()) {%>
                <% final boolean specialItem = item.getFormItemName().getType().equals(FormItemType.SPECIAL); %>
                <tr id="<%= i %>" class="yourTableRow">
                    <td style="width:15px !important; text-align:center;" class="numberTd" position="<%= i + 1 %>">
                        <%= i + 1 %>
                    </td>
                    <td style="width:20px !important">
                        <%if (!(item.getFormItemName().getCheckers().contains(FormItemCheckerType.MANDATORY))) {%>
                        <input type="checkbox"
                               name="yourFormCheckbox">
                        <%}%>
                    </td>
                    <td style="width:50px !important" class="imagesTd">
                        <img src="/images/up_arrow.gif" alt="" name="upImages"
                             title="<%= formInternational.get("moveUpTitle") %>"
                             style="cursor:pointer;<%= i == 0 ? "visibility:hidden;" : "" %>" onclick="moveUp(this);"/>
                        <img src="/images/down_arrow.gif" alt="" name="downImages"
                             title="<%= formInternational.get("moveDownTitle") %>"
                             style="cursor:pointer;<%= i == formService.getExistingFormItems().size() - 1 ? "visibility:hidden;" : "" %>"
                             onclick="moveDown(this);"/>
                        <img src="/images/arrow-return-180-left.png" alt="" name="moveImages"
                             title="<%= formInternational.get("moveToPositionTitle") %>"
                             style="cursor:pointer;" onclick="showMoveToPosition(this);"/>
                    </td>
                    <% if (!specialItem && item.getFormItemName() != FormItemName.LINKED) { %>
                    <td style="width:118px !important">
                        <input type="text" class="txt100 yourFormTexts" maxlength="255"
                               value="<%= item.getItemName() %>"/>
                    </td>
                    <% } %>
                    <td style="width:110px !important;<%= specialItem || item.getFormItemName() == FormItemName.LINKED ?
                     "text-align:center" : "" %>" <%= specialItem ? "colspan=\"4\"" : (item.getFormItemName() == FormItemName.LINKED ? "colspan=\"2\"" : "") %>>
                        <input type="hidden" id="<%=item.getFormItemName().toString()%>itemId" class="yourItemId"
                               value="<%= item.getFormItemId() %>">
                        <input type="hidden" class="linkedSourceItemId" value="<%= item.getLinkedFormItemId() %>"/>
                        <input type="hidden" class="linkedItemDisplayType"
                               value="<%= item.getFormItemDisplayType() %>"/>
                        <% final String staticName = item.getFormItemName() == FormItemName.LINKED ?
                                item.getItemName() : formInternational.get(item.getFormItemName() + "_FN"); %>
                        <span class="yourFormStaticTexts" itemName="<%= staticName %>"
                              id="<%=item.getFormItemName().toString()%>"><%= staticName %><% if (item.getFormItemName().equals(FormItemName.HEADER)) { %>:<% } %></span>
                        <% if (item.getFormItemName().equals(FormItemName.HEADER)) { %>
                        <div style="font-style:italic;display:inline"
                             class="headerInlineBlock"><%= HtmlUtil.limitName(HtmlUtil.removeAllTags(item.getInstruction()), 35) %>
                        </div>
                        <a href="javascript:void(0)" onclick="editFormHeader(this);"
                           onmouseover='bindTooltip({element:this, contentElement:$(this).parent().find(".instruction")});'><%= formInternational.get("edit") %>
                        </a>
                        <% final int uniqueEditorId = new Random().nextInt(); %>
                        <% final String itemHeader = item.getInstruction() == null ? "" : item.getInstruction(); %>
                        <div style="display:none;" class="headerHolder" id="uniqueEditorId<%= uniqueEditorId %>">
                            <%= itemHeader %>
                        </div>
                        <% } %>
                        <% if (item.getFormItemName() == FormItemName.LINKED) { %>
                        <% final int linkUniqueId = new Random().nextInt(); %>
                        <a style="margin-left:5px;" id="linkUniqueId<%= linkUniqueId %>"
                           href="javascript:addLinkedField.show({linkId:'linkUniqueId<%= linkUniqueId %>'})">Edit linked field</a>
                        <% } %>
                    </td>
                    <% if (!specialItem) { %>
                    <% boolean reqItem = item.isRequired() || item.getFormItemName().getCheckers().contains(FormItemCheckerType.ALWAYS_REQUIRED); %>
                    <td style="width:28px !important; <% if (!item.getFormItemName().getCheckers().contains(FormItemCheckerType.ALWAYS_REQUIRED)) { %>cursor:pointer;<% } %> text-align:center"
                        <%if (!(item.getFormItemName().getCheckers().contains(FormItemCheckerType.ALWAYS_REQUIRED))){%>id="reqTd<%=item.getItemName()%>"
                        onclick="clickReq(this);"<%}%>>
                        <span class="asterisk" <%= reqItem ? "style=\"color:black;\"" : "style=\"color:gray;\""%>><%= reqItem ? "Y" : "N"%></span>
                    </td>
                    <% final String itemInstruction = item.getInstruction() == null ? "" : item.getInstruction(); %>
                    <td style="text-align:center"
                        onmouseover='bindTooltip({element:this, contentElement:$(this).parent().find(".instruction"), useValue:true});'>
                        <a href="javascript:void(0)"
                           onclick="editInstruction(this);"><%= formInternational.get("edit") %>
                        </a>

                        <input type="hidden" class="instruction" value="<%= itemInstruction %>" style="display:none;"/>
                    </td>
                    <% } %>
                    <%i++;%>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%-- Select/Unselect links for INIT table --%>
<div style="float:left;width:48%;position:relative;">
    <a id="selectAllLink" class="ajax" href="javascript:selectAll()"><%=formInternational.get("selectAll")%>
    </a>&nbsp;
    <a id="unselectAllLink" class="ajax" href="javascript:unselectAll()"><%=formInternational.get("unselectAll")%>
    </a>
</div>

<%-- Select/Unselect links for YOUR table --%>
<div style="float:right;width:28%;text-align:right;position:relative;">
    <a id="selectAllYourTableLink" class="ajax" href="javascript:selectAllYourTable()">
        <%= formInternational.get("selectAll") %>
    </a>&nbsp;
    <a id="unselectAllYourTableeLink" class="ajax"
       href="javascript:unselectAllYourTable()"><%= formInternational.get("unselectAll") %>
    </a>
</div>

<%-- Add Linked Field link under YOUR table --%>
<div style="float:right;width:20%; position:relative;">
    <a id="addLinkedFieldLink" href="javascript:addLinkedField.show({})"><%= formInternational.get("addLinkedField") %>
    </a>
</div>

<%-- EDIT INSTRUCTION DIV --%>
<div id="editInstruction" style="display:none;">
    <div class="windowOneColumn">
        <%-- No trimming here, please — http://jira.web-deva.com/browse/SW-4264. Please take care about this textarea :) --%>
        <textarea cols="5" rows="5" style="width:100%;height:100px" id="instructionTextarea"></textarea>

        <div align="right">
            <input type="button" class="but_w73" value="Save" onmouseover="this.className='but_w73_Over';"
                   id="windowSave"
                   onmouseout="this.className='but_w73';" onclick="saveInstruction();">
            <input type="button" class="but_w73" value="Cancel" onmouseover="this.className='but_w73_Over';"
                   id="windowCancel"
                   onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();">
        </div>
    </div>
</div>

<div id="editHeader" style="display:none;">
    <div class="windowOneColumn">
        <div id="headerTextEditorPlace">
        </div>

        <div align="right">
            <input type="button" class="but_w73" value="Save" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" onclick="saveFormHeader();">
            <input type="button" class="but_w73" value="Cancel" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();">
        </div>
    </div>
</div>

<div id="moveToPositionWindowContent" style="display:none;">
    <div class="windowOneColumn">
        <input type="hidden" id="currentRowPosition" value="Note, that value is set up after open of this window"/>

        <h1><%= formInternational.get("moveRowHeader") %>
        </h1>

        <div style="margin-bottom:5px;">
            <label for="moveRowToInput"><%= formInternational.get("moveRowInputLabel") %>
            </label>
            <input type="text" style="width:55px;" onkeypress="return numbersOnly(this, event);" id="moveRowToInput"/>
        </div>

        <div align="right">
            <input type="button" class="but_w73" value="Move" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';"
                   onclick="moveToPosition($('#currentRowPosition').val(), $('#moveRowToInput').val());">
            <input type="button" class="but_w73" value="Cancel" onmouseover="this.className='but_w73_Over';"
                   onmouseout="this.className='but_w73';" onclick="closeConfigureWidgetDiv();">
        </div>
    </div>
</div>


