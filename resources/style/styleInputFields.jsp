<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ page import="com.shroggle.entity.StyleType" %>
<%@ page import="com.shroggle.entity.Style" %>
<%@ page import="com.shroggle.entity.MeasureUnit" %>
<%@ page import="com.shroggle.util.international.International" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.shroggle.entity.StyleSelectType" %>
<%--
  @author Balakirev Anatoliy
  Date: 23.07.2009  
--%>
<div style="height:25px;vertical-align:middle;margin-top:30px;margin-bottom:62px;">

    <%-----------------------------------------------------label------------------------------------------------------%>
    <div style="width:50px;float:left;position:relative;top:3px;">
        <label for="<%= request.getAttribute("id") %>">
            <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get((String) request.getAttribute("id")) %>
        </label>
    </div>
    <%-----------------------------------------------------label------------------------------------------------------%>

    <%------------------values: (none; all sides; vertical & horizontal; each side separately)------------------------%>
    <select size="1" id="<%= request.getAttribute("id") %>" onchange="<%= (String)request.getAttribute("onChangeFunction") %>();">
        <% for (StyleType value : StyleType.values()) { %>
        <option value="<%= value.toString() %>"
                <% if (value.equals(((Style) request.getAttribute((String) request.getAttribute("id"))).getType())) { %>
                selected <% } %>>
            <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get(value.toString()) %>
        </option>
        <% } %>
    </select>
    <%------------------values: (none; all sides; vertical & horizontal; each side separately)------------------------%>

    <div style="height:25px;vertical-align:middle;margin:7px 0;">

        <%--------------------------------------------empty div (none)------------------------------------------------%>
        <div id="<%= (String)request.getAttribute("id") %>EmptyDiv"
                <% if (StyleType.NONE.equals(((Style) request.getAttribute((String) request.getAttribute("id"))).getType())) { %>
             style="display:block;height:27px;"
                <% } else { %>
             style="display:none;height:27px;"
                <% } %>>
        </div>
        <%--------------------------------------------empty div (none)------------------------------------------------%>

        <%---------------------------------------one input field (all sides)------------------------------------------%>
        <div id="<%= (String)request.getAttribute("id") %>ValueDiv"
                <% if (StyleType.ALL_SIDES.equals(((Style) request.getAttribute((String) request.getAttribute("id"))).getType())) { %>
             style="display:block;margin:0 0 0 50px;"
                <% } else { %>
             style="display:none;margin:0 0 0 50px;"
                <% } %> >
            <div style="width:140px;float:left;margin-bottom:5px;">
                <div style="width:50px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("value") %>
                    <% request.setAttribute("fieldName", "Value");%>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
        </div>
        <%---------------------------------------one input field (all sides)------------------------------------------%>

        <%---------------------------------two input fields (vertical & horizontal)-----------------------------------%>
        <div id="<%= (String)request.getAttribute("id") %>VerticalHorizontal"
                <% if (StyleType.VERTICAL_HORIZONTAL.equals(((Style) request.getAttribute((String) request.getAttribute("id"))).getType())) { %>
             style="display:block;margin:0 0 0 50px;"
                <% } else { %>
             style="display:none;margin:0 0 0 50px;"
                <% } %>>
            <div style="width:140px;float:left;margin-bottom:5px;">
                <div style="width:50px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("vertical") %>
                    <% request.setAttribute("fieldName", "Vertical");%>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
            <div style="width:150px;float:left;margin-bottom:5px;">
                <div style="width:60px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("horizontal") %>
                    <% request.setAttribute("fieldName", "Horizontal");%>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
        </div>
        <%---------------------------------two input fields (vertical & horizontal)-----------------------------------%>

        <%---------------------------------four input fields (each side separately)-----------------------------------%>
        <div id="<%= (String)request.getAttribute("id") %>Separately"
                <% if (StyleType.EACH_SIDE_SEPARATELY.equals(((Style) request.getAttribute((String) request.getAttribute("id"))).getType())) { %>
             style="display:block;margin:0 0 0 50px;"
                <% } else { %>
             style="display:none;margin:0 0 0 50px;"
                <% } %> >
            <div style="width:140px;float:left;margin-bottom:5px;">
                <div style="width:50px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("top") %>
                    <% request.setAttribute("fieldName", "Top"); %>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
            <div style="width:140px;float:left;margin-bottom:5px;">
                <div style="width:35px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("right") %>
                    <% request.setAttribute("fieldName", "Right"); %>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
            <br clear="all"/>

            <div style="width:140px;float:left;">
                <div style="width:50px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("bottom") %>
                    <% request.setAttribute("fieldName", "Bottom"); %>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
            <div style="width:140px;float:left;">
                <div style="width:35px;float:left;">
                    <%= ServiceLocator.getInternationStorage().get("styleInputFields", Locale.US).get("left") %>
                    <% request.setAttribute("fieldName", "Left"); %>
                </div>
                <jsp:include page="textFieldWithSelect.jsp" flush="true"/>
            </div>
        </div>
        <%---------------------------------four input fields (each side separately)-----------------------------------%>
    </div>
</div>


