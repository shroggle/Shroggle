<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.PageType" %>
<%@ page import="java.util.List" %>
<%@ page import="com.shroggle.presentation.site.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<% final EditSiteTemplateHtmlAction actionBean = (EditSiteTemplateHtmlAction) request.getAttribute("actionBean"); %>
<international:part part="editSiteTemplateHtml"/>
<html>
    <head>
        <title><international:get name="title"/></title>
        <cache:no/>
        <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
        <script type="text/javascript">

            $(document).ready(function () {
                window.uploadFilesControl.bind({
                    element: "#upload",
                    label: "Bulk Image Upload",
                    siteId: <%= actionBean.getSiteId() %>,
                    onClose: function () {
                        window.images.select.refresh("#images");
                    }
                });

                window.images.select.bind({
                    element: "#images",
                    siteId: <%= actionBean.getSiteId() %>,
                    lineWidth: 810,
                    onClick: function (image) {

                    }
                });

            });

            function openSpecialTagsWindow() {
                var specialTagsWindow = createConfigureWindow({
                    width: 600,
                    height: 600
                });
                specialTagsWindow.setContent($("#specialTagsWindow").html());
            }

            function openPreview() {
                $("#previewHtml").val($("#html").val());
                $("#previewForm").submit();
            }

        </script>
    </head>

    <body>
        <div id="specialTagsWindow" style="display: none;">
            <div class="windowOneColumn">
                <h3>Required HTML tags</h3>

                <br><br>

                HTML uploaded here will be used to create your site pages. Some modification should be done to allow
                the site builder to insert dynamic content and functional modules into a page. The following tags
                must be included in your HTML:

                <br><br>

                <ul style="margin-left: 20px;">
                    <li>
                        &#60;!-- PAGE_TITLE --&#62; should replace your page 'title' in &#60;header&#62; section.
                        <br>
                        Example: '&#60;title&#62;&#60;!-- PAGE_TITLE --&#62;&#60;/title&#62;'.
                        The site builder will use this tag to place a dynamic page title defined during page creation.
                        <br><br>
                    </li>

                    <li>
                        &#60;!-- PAGE_HEADER --&#62; should placed in the end of HTML &#60;header&#62; section.<br>
                        Example: '<br>
                            &#60;/style&#62;<br>
                            &#60;!-- PAGE_HEADER --&#62;<br>
                        &#60;/head&#62;<br>
                        &#60;body&#62;<br><br>
                    </li>

                    <li>
                        To add a content container you will need to add the 'mediaBlock' class.
                        <br>
                        Example: " &#60;td class="middleBottomLeft mediaBlock"&#62;&#60;/td&#62;"
                        or "&#60;div class="header mediaBlock"&#62;"
                        <br><br>
                    </li>

                    <li>
                        &#60;!-- TEMPLATE_RESOURCE --&#62; should be added to the HTML before names of uploaded files.
                        <br>
                        Example: "&#60;img src='&#60;!-- TEMPLATE_RESOURCE --&#62;/logo.png'&#62;"
                    </li>
                </ul>
            </div>
        </div>

        <div class="wrapper">
            <div class="container">
                <%@ include file="/includeHeadApplication.jsp" %>
                <div class="content">
                    <div class="contentselecttemplates">
                        <% request.setAttribute("selected", EditSiteTemplateHtmlAction.class); %>
                        <% request.setAttribute("siteId", actionBean.getSiteId()); %>
                        <% request.setAttribute("createChildSite", false); %>
                        <jsp:include page="setSiteTemplateMenu.jsp" flush="true"/>

                        <h1 class="fl"><international:get name="title"/></h1>

                        <div style="clear: both;"></div>

                        <br><br>

                        This page allows users to create their own site design by uploading and editing HTML.
                        To customize your HTML site design for use in this application, please add the following
                        opening and closing tags where ever you would like to be able to insert a content element
                        such as text, images, blogs etc. This design will be applied to all site pages,
                        but can be edited page by page from the site editor interface

                        <br><br><br>

                        <h3><international:get name="subtitle"/></h3>
                        <a href="javascript:openSpecialTagsWindow()">Important instructions for adding required tags</a>

                        <br>

                        <stripes:form beanclass="com.shroggle.presentation.site.ShowHtmlAsPageAction" method="post" id="previewForm" style="display: none;" target="_blank">
                            <textarea rows="5" cols="5" id="previewHtml" name="html"></textarea>
                            <stripes:hidden name="siteId"/>
                        </stripes:form>

                        <stripes:form beanclass="com.shroggle.presentation.site.EditSiteTemplateHtmlAction" method="post">
                            <page:errors/>

                            <br>

                            <stripes:textarea name="value" rows="50" style="width: 795px;" id="html"></stripes:textarea>

                            <br><br><br>

                            <h3><international:get name="subtitleImages"/></h3>

                            <br>

                            <div id="images" class="uploadedImages"></div>
                            <div id="upload"></div>
                            
                            <br><br>

                            <input type="button" onclick="openPreview();" value="Preview"
                                onmouseout="this.className='but_w73_Over';"
                                onmouseover="this.className='but_w73';" class="but_w73_Over">

                            <div class="fr" style="text-align:right">
                                <input type="submit" name="execute" value=""
                                       onmouseout="this.className='butSubmit';"
                                       onmouseover="this.className='butSubmitOver';" class="butSubmit">
                            </div>
                            <br clear="all">

                            <stripes:hidden name="siteId"/>
                        </stripes:form>
                    </div>
                </div>
                <jsp:include page="/includeFooterApplication.jsp" flush="true"/>
            </div>
        </div>
    </body>
</html>