<%@ page import="com.shroggle.presentation.image.ShowImageFilesService" %>
<%@ page import="com.shroggle.entity.ImageFileType" %>
<%@ page import="com.shroggle.entity.ImageLinkType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>

<% final ShowImageFilesService service = (ShowImageFilesService) request.getAttribute("service"); %>

<%---------------------------------------------------image---------------------------------------------------%>
<% if (service.getDraftImage().getImageLinkType().equals(ImageLinkType.MEDIA_FILE) &&
        service.getImageFile().getImageFileType().equals(ImageFileType.IMAGE)) { %>
<% if (service.getItemWidth() > service.getWindowWidth() || service.getItemHeight() > service.getWindowHeight()) { %>
<div style="width:<%= service.getWindowWidth() %>px; height:<%= service.getWindowHeight() %>px;
    text-align:center;vertical-align:middle; overflow-x:<%= service.getXScroll() %>;
    overflow-y: <%= service.getYScroll() %> "><% } %>
    <table width="<%= service.getItemWidth() %>px" height="<%= service.getItemHeight() %>px" cellpadding="0" cellspacing="0">
        <tr>
            <td valign="middle" align="center">
                <img align="middle" src='<%= service.getImageFileUrl() %>' width='<%= service.getItemWidth() %>'
                     height='<%= service.getItemHeight() %>' style="vertical-align:middle;" alt="">
            </td>
        </tr>
    </table>
<% if (service.getItemWidth() > service.getWindowWidth() || service.getItemHeight() > service.getWindowHeight()) { %></div><% } %>
<input type="button" value="Close" style="margin-top:2px;" onclick="removeWidgetImageTextInSmallWindow();">
<%---------------------------------------------------image---------------------------------------------------%>

<%---------------------------------------------------flash---------------------------------------------------%>
<% } else if (service.getDraftImage().getImageLinkType().equals(ImageLinkType.MEDIA_FILE) &&
        service.getImageFile().getImageFileType().equals(ImageFileType.FLASH)) { %>
<div style="width:<%= service.getWindowWidth() %>; height:<%= service.getWindowHeight() %>;
    text-align:center;vertical-align:middle; background:#fff;overflow-x:<%= service.getXScroll() %>;
    overflow-y: <%= service.getYScroll() %> ">
    <object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='<%= service.getWindowWidth() %>'
            height='<%= service.getWindowHeight() %>'>
        <param name='movie' value='<%= service.getImageFileUrl() %>'/>
        <param name='quality' value='high'>
        <object type='application/x-shockwave-flash'
                data='<%= service.getImageFileUrl() %>' width='<%= service.getWindowWidth() %>'
                height='<%= service.getWindowHeight() %>'>
            <p>
            </p>
        </object>
    </object>
    <input type="button" value="Close" style="vertical-align:bottom;"
           onclick="removeWidgetImageTextInSmallWindow();">
</div>
<% } %>
<%---------------------------------------------------flash---------------------------------------------------%>

<%---------------------------------------------------text---------------------------------------------------%>
<% if (service.getDraftImage().getImageLinkType().equals(ImageLinkType.TEXT_AREA)) { %>
<div style=" <%if (service.getWindowWidth() > 0) { %>width:<%= service.getWindowWidth() %>;<% } %>
    <%if (service.getWindowHeight() > 0) { %>height:<%= service.getWindowHeight() %><% }
        String result=service.getDraftImage().getTextArea();
    %>;
    text-align:center;vertical-align:middle; overflow:hidden ">
    <span class="imageTextInSmallWindow">
         <%= result %>
    </span>
    <br/>
    <input type="button" value="Close" style="vertical-align:bottom;"
           onclick="removeWidgetImageTextInSmallWindow();">
</div>
<% } %>
<%---------------------------------------------------text---------------------------------------------------%>