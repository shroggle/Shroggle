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
<international:part part="selectSiteDesignPage"/>
<% final SelectSiteDesignPageAction action = (SelectSiteDesignPageAction) request.getAttribute("actionBean"); %>
<html>
    <head>
        <title><international:get name="title"/></title>
        <cache:no/>
        <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
        <script type="text/javascript">

            var preloadedImages = new Array();
            preloadedImages.push("/images/selectdesign/moreadvanced-rollover.png");
            preloadedImages.push("/images/selectdesign/siteblueprints-rollover.png");
            preloadedImages.push("/images/selectdesign/pros-rollover.png");

            preloadImages(preloadedImages);

        </script>
    </head>

    <body style="background-image: url(/images/imagesnew/bg-2000.jpg);">
        <div class="wrapper">
            <div class="container">
                <%@ include file="/includeHeadApplication.jsp" %>
                <div class="content">

                    <div class="textAlignCenter" style="width:900px; margin:20px auto; overflow:hidden; height:400px;">
                      <div style="width:500px; margin:30px  auto 0;">
                        <stripes:link beanclass="com.shroggle.presentation.site.SetSiteBlueprintAction">
                            <stripes:param name="siteId" value="<%= action.getSiteId() %>"/>
                            <stripes:param name="createChildSite" value="<%= action.isCreateChildSite() %>"/>
                            <div style="float:left; margin:25px 0 0 0;"> <img src="/images/selectdesign/supereasy.png" alt="Super Easy"/>   </div>
                            <div style="float:left;">
                                <img src="/images/selectdesign/siteblueprints.png" alt="site blueprints"
                                     id="siteblueprints" width="301" height="80"
                                     onmouseover="$('#siteblueprints')[0].src='/images/selectdesign/siteblueprints-rollover.png';"
                                     onmouseout="$('#siteblueprints')[0].src='/images/selectdesign/siteblueprints.png';"/>
                            </div>
                            </stripes:link>
                            <div style="float:left; margin:-30px 0 0 30px;">
                            <a href="javascript:createConfigureWindow({width: 300, height: 200}).setContent($('#more1').html())"><img src="/images/selectdesign/moreinfo.png"/></a>
                            </div>

                        <div id="more1" style="display: none;">
                            <div class="windowOneColumn">
                                <div style="overflow: auto; height: 400px; text-align: left;">
                                    Use an existing site blueprint with pages, images and functionality already included
                                </div>

                            <div align="right" style="margin-top:10px"><input type="button" onclick="closeConfigureWidgetDiv();"
                                                              class="but_w73"
                                                              onmouseover="this.className='but_w73_Over';"
                                                              onmouseout="this.className='but_w73';"
                                                              value="Close"></div>
                            </div>
                        </div>  </div>

                          <br style="clear:both;" />
                    <div style="float:left; margin:30px 0 0 50px;">
                        <stripes:link beanclass="com.shroggle.presentation.site.template.SetSiteTemplateAction" >
                            <stripes:param name="siteId" value="<%= action.getSiteId() %>"/>
                            <stripes:param name="createChildSite" value="<%= action.isCreateChildSite() %>"/>
                            <div>
                                <img src="/images/selectdesign/designtemplates.png" alt="design templates" id="designtemplates" width="340" height="92"
                                    onmouseover="$('#designtemplates')[0].src='/images/selectdesign/designtemplates-rollover.png';"
                                    onmouseout="$('#designtemplates')[0].src='/images/selectdesign/designtemplates.png';"/>
                            </div>
                            <div style="margin:20px 0 0 0;"><img src="/images/selectdesign/moreadvanced.png"/></div>
                        </stripes:link>

                                 <div style="margin:5px 0 0 100px;">
                        <a href="javascript:createConfigureWindow({width: 300, height: 200}).setContent($('#more2').html())"><img src="/images/selectdesign/moreinfo.png"/></a>
                            </div>
                        <div id="more2" style="display: none;">
                            <div class="windowOneColumn">
                                <div style="overflow: auto; height: 400px; text-align: left;">
                                    Use an existing site design (without pages, images or functionality) and then use our
                            simple interface to add your own text, images and functional modules. Designs range from
                            the specific and unmistakable, to the more flexible options that can be modified to
                            create an entirely custom look’
                                </div>

                            <div align="right" style="margin-top:10px"><input type="button" onclick="closeConfigureWidgetDiv();"
                                                              class="but_w73"
                                                              onmouseover="this.className='but_w73_Over';"
                                                              onmouseout="this.className='but_w73';"
                                                              value="Close"></div>
                            </div>
                        </div>
                       </div>
                        <div style="float:left;margin:30px 0 0 60px;">
                        <stripes:link beanclass="com.shroggle.presentation.site.EditSiteTemplateHtmlAction">
                            <stripes:param name="siteId" value="<%= action.getSiteId() %>"/>
                            <stripes:param name="createChildSite" value="<%= action.isCreateChildSite() %>"/>
                            <div>
                                <img src="/images/selectdesign/customhtml.png" alt="custom html" id="customhtml" width="301" height="77"
                                    onmouseover="$('#customhtml')[0].src='/images/selectdesign/customhtml-rollover.png';"
                                    onmouseout="$('#customhtml')[0].src='/images/selectdesign/customhtml.png';"/>
                            </div>
                            <div style="margin:20px 0 0 0;"><img src="/images/selectdesign/pros.png"/></div>
                        </stripes:link>

                            <div style="margin:5px 0 0 100px;">
                        <a href="javascript:createConfigureWindow({width: 300, height: 200}).setContent($('#more3').html())"><img src="/images/selectdesign/moreinfo.png"/></a>
                           </div>
                        <div id="more3" style="display: none;">
                            <div class="windowOneColumn">
                                <div style="overflow: auto; height: 400px; text-align: left;">
                                    Drop in your site design html, modify to accommodate our content modules, and finally upload referenced site design images’.
                                </div>

                            <div align="right" style="margin-top:10px"><input type="button" onclick="closeConfigureWidgetDiv();"
                                                              class="but_w73"
                                                              onmouseover="this.className='but_w73_Over';"
                                                              onmouseout="this.className='but_w73';"
                                                              value="Close"></div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="/includeFooterApplication.jsp" flush="true"/>
            </div>
        </div>
    </body>
</html>