<%@ page import="com.shroggle.entity.StyleValue" %>
<%@ page import="com.shroggle.entity.Style" %>
<%@ page import="com.shroggle.logic.StyleManager" %>
<%--
  @author Balakirev Anatoliy
  Date: 23.07.2009  
--%>

 
<input type="text" id="<%= (String)request.getAttribute("id") + request.getAttribute("fieldName") %>" maxlength="3"
       value="<%= StyleManager.createStyleValue((String)request.getAttribute("fieldName"),
                                                    ((Style) request.getAttribute((String)request.getAttribute("id")))) %>"
       style="width:25px;"
       onkeyup="if(this.value > 800){this.value = 800;}<%= (String)request.getAttribute("onChangeFunction") %>();"
       onKeyPress="<% if ( (request.getAttribute("id") == "borderPadding") ||
                        (request.getAttribute("id") == "borderMargin")) { %>return negativeNumbersAlso(this,event); <% } else {%>
                       return numbersOnly(this, event); <% } %>"
        />
<!--       onKeyPress="return numbersOnly(this, event);"/> -->