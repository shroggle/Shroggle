<%--
  @author Balakirev Anatoliy
  Date: 24.07.2009  
--%>

<%@ page import="com.shroggle.entity.StyleSelectType" %>

<% if (request.getAttribute("styleSelectType") == StyleSelectType.SELECT) { %>
<jsp:include page="styleSelect.jsp" flush="true"/>
<% } else  if (request.getAttribute("styleSelectType") == StyleSelectType.TEXT_FIELD) { %>
<jsp:include page="numbersOnlyInputTextField.jsp" flush="true"/>
<jsp:include page="measureUnitsSelect.jsp" flush="true"/>
<% } else  if (request.getAttribute("styleSelectType") == StyleSelectType.COLOR) { %>
<jsp:include page="colorInputField.jsp" flush="true"/>
<% } %>
