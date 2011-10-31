<%@ page import="com.shroggle.entity.ChargeType" %>
<%@ page import="com.shroggle.logic.site.billingInfo.ChargeTypeManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="cache" uri="/WEB-INF/tags/cache.tld" %>
<html>
<head>
    <meta name="google-site-verification" content="vyKJkxvLzCNj-LJlMOh7U1mWZpPmK71vREmXd8M2saE"/>
    <meta name="alexaVerifyID" content="QqtgT_SDW5JVFrqoC80DhGpoygI"/>
    <META NAME="Title"
          CONTENT="Creating your own website for free, Build free easy websites with Web-Deva, Make your own website with blogs, forums, catalogs & more">
    <META NAME="Description"
          CONTENT="Creating your own website for free with Web-Deva allows you to build free easy websites">
    <META NAME="Keywords"
          CONTENT="creating your own website for free, build free easy websites, building a website free software">
    <international:part part="start"/>
    <title><international:get name="title"/></title>
    <cache:no/>
    <link rel="stylesheet" href="/css/style_start.css" type="text/css">

    <jsp:include page="/includeHeadPresentationResources.jsp" flush="true">
        <jsp:param name="presentationJs" value="true"/>
    </jsp:include>

    <script type="text/javascript">

        var preloadedImages = new Array();
        preloadedImages.push('../images/imagesnew/getstarted-roll2.png');
        preloadedImages.push('../images/imagesnew/takeatour-roll2.png');
        preloadedImages.push('../images/imagesnew/create1button-roll.gif');
        preloadedImages.push('../images/imagesnew/service1button-roll.gif');
        preloadedImages.push('../images/imagesnew/feature1button-roll.gif');
        preloadedImages.push('../images/imagesnew/loginpanel-bg.gif');

        preloadImages(preloadedImages);

    </script>
</head>
<body class="homeNd" id="start-new_body">
<div id="wrapperHome">

    <%@ include file="/includeHeadPresentation-start.jsp" %>

    <div id="mainTopNd">
        <div id="mainTopMenuNd">
            <div id="freetotry">
                <stripes:link beanclass="com.shroggle.presentation.user.create.CreateUserAction">
                    <img src="images/imagesnew/freeToTryAnimated.gif" width="633" height="276" alt="free to try">
                </stripes:link>
            </div>
            <div style="padding-top:15px;"><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/"
                                              onmouseover="$('#takeatourbutton')[0].src='images/imagesnew/takeatour-roll2.png';"
                                              onmouseout="$('#takeatourbutton')[0].src='images/imagesnew/takeatour.png';">
                <img src="images/imagesnew/takeatour.png" alt="take a tour" id="takeatourbutton" width="345"
                     height="96" border="0"/>
            </a>
            </div>
            <div style="padding-top:15px;">
                <stripes:link beanclass="com.shroggle.presentation.user.create.CreateUserAction"
                              onmouseout="$('#getstartedbutton')[0].src='images/imagesnew/getstarted.png';"
                              onmouseover="$('#getstartedbutton')[0].src='images/imagesnew/getstarted-roll2.png';">
                    <img src="images/imagesnew/getstarted.png" alt="get started" id="getstartedbutton" width="345"
                         height="96" border="0"/>
                </stripes:link>
            </div>
        </div>
        <!--end of mainTop --></div>
    <div id="middleTopNd">
        <div id="steps"><img src="/images/imagesnew/4steps.png" width="843" height="47" alt="4 easy steps"></div>
        <div id="menu4steps">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td style="vertical-align:middle;"><img src="/images/imagesnew/button4steps1.png" width="266"
                                                            height="37" alt="create an account"></td>
                    <td style="vertical-align:middle;"><img src="/images/imagesnew/button4steps2.png" width="260"
                                                            height="36" alt="add site content"></td>
                    <td style="vertical-align:middle;"><img src="/images/imagesnew/button4steps3.png" width="246"
                                                            height="50" alt="design your site"></td>
                    <td style="vertical-align:middle;"><img src="/images/imagesnew/button4steps4.png" width="144"
                                                            height="53" alt="go live"></td>
                </tr>
            </table>
        </div>
        <div id="publish">
            <p><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Terms_and_Conditions">When you are ready to publish we will
                host your site for just $<%= new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice() %> per month</a></p>
        </div>
        <div id="business">
            <div class="businessblock">
                <div class="businessimg"><img src="../images/imagesnew/sq-hands.png" width="141" height="127"></div>
                <div class="businesstext">
                    <h2 class="busyh2" style="margin:15px 0 5px 0; padding:0 5px; font-size:14pt;">Small Businesses</h2>

                    <p class="busy" style="margin:0; padding:0 5px;">
                        Web-Deva provides the small business owner with a cost effective, easy to use way to update a site as frequently as you need or want to.</p>

                    <p class="busy" style="margin:0; padding:0 5px;">
                        To modify content, design and functionality in response to industry trends, competition and site traffic analysis.</p>

                    <p class="busy" style="margin:0; padding:0 5px;"><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/smallbiz">For more information about why this is a great tool for Small Business</a></p>

                </div>
            </div>
            <div class="businessblock">
                <div class="businessimg"><img src="../images/imagesnew/sq-house.png" width="141" height="127"></div>

                <div class="businesstext">
                    <h2 class="busyh2" style="margin:15px 0 5px 0; padding:0 5px; font-size:14pt;">Large
                        Corporations</h2>

                    <p class="busy" style="margin:0; padding:0 5px;">Web-Deva allows you to create private back, or mid office web sites.</p>

                    <p class="busy" style="margin:0; padding:0 5px;">It enables you to create a password protected site to share & archive corporate information, collaborate with blogs and discussion boards and disseminate corporate processes, procedures and technical specifications with video, audio, blogs and searchable catalogs or archives.</p>

                    <p class="busy" style="margin:0; padding:0 5px;"><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/enterprise">For more information about enterprise collaboration</a></p>
                </div>
            </div>
            <div class="clearbothNd"></div>
            <div class="businessblock">
                <div class="businessimg"><img src="../images/imagesnew/sq-cubes.png" width="150" height="140"></div>
                <div class="businesstext">
                    <h2 class="busyh2" style="margin:15px 0 5px 0; padding:0 5px; font-size:14pt;">Professional Web
                        Developers</h2>

                    <p class="busy" style="margin:0; padding:0 5px;">Web-Deva affords you a simple, professional interface for creating and editing high quality sites for your customers.</p>


                    <p class="busy" style="margin:0; padding:0 5px;">Web-Deva allows you to create reusable site blueprints and to employ custom html site designs.</p>

                    <p class="busy" style="margin:0; padding:0 5px;">It enables you to give your client full or partial access to CMS tools and to manage your sites from a single web based dashboard.</p>
                   <p class="busy" style="margin:0; padding:0 5px;"><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/pro_website_builder">Why is Web-Deva the best Professional Website Creator?</a></p></div>
            </div>
            <div class="businessblock">
                <div class="businessimg"><img src="../images/imagesnew/sq-family.png" width="150" height="140"></div>
                <div class="businesstext">
                    <h2 class="busyh2" style="margin:15px 0 5px 0; padding:0 5px; font-size:14pt;">Individuals and
                        Families</h2>

                    <p class="busy" style="margin:0; padding:0 5px;">Use Web-Deva to create a site where you can share & archive personal or family information:</p>

                    <p class="busy" style="margin:0; padding:0 5px;">Store and share family photos, home videos, archive digital copies of important documents.</p>

                    <p class="busy" style="margin:0; padding:0 5px;">Create a single repository for all those family recipes.</p>

                    <p class="busy" style="margin:0; padding:0 5px;">Store family news, anecdotes & history in a blog or as a video or audio archive.</p>

                     <p class="busy" style="margin:0; padding:0 5px;"><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/family_sites">10 great ways to use WebDeva for your family web site </a></p>
                </div>
            </div>
            <!--end of business --></div>
        <!--end of middletop--></div>
    <div id="middleBottomNd">
        <div id="middleBottomTextNd" style="text-align:left">
            <h2 style="margin:15px 0 5px 0; padding:0 5px; font-size:14pt;">Professional Results Now!</h2>

            <p style="margin:0; padding:0 5px;">Blog • Video • Images • Catalogs • Forums • Text • Galleries • Forms •
                Networks • Menus • Polls • CRM  • 
                Public sites • Password Protected sites •
                Registrant Database • Newsletter Sign up •
                Meta Tag Editor • Contact Us • Voting and Comments • Advanced Search • Social Networking Integration • Google Base Exports and <a   
                        href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Feature_List">more</a></p>
        </div>
        <div id="findoutmore"><span><a href="http://www.demo.<%= ServiceLocator.getConfigStorage().get().getUserSitesUrl() %>/Feature_List">find out more</a></span>
        </div>
        <!--end of middlebottom --></div>
    <div id="footerNd" class="clearbothNd">
        <div id="customers">
            <div class="customers1">
                <p style="margin:0; padding:0 5px;text-align:left;line-height:12pt;">"Web-Deva allows me to build my
                    customer's web site's myself, rather than hiring a developer to do it. It saves me time & money and
                    produces professional results that my customers love".</p>

                <p class="customername" style="margin:0;padding:0 10px 10px 0;">- Elizabeth R.</p>
            </div>
            <div class="customers1">
                <p style="margin:0; padding:0 5px;text-align:left;line-height:12pt;">"It's such a paradigm shift! </p>

                <p style="margin:0; padding:0 5px;text-align:left;line-height:12pt;">With Web-Deva it costs nothing to
                    edit my site text and images, it costs nothing to add new functionality
                    - its a pleasure!"</p>

                <p class="customername" style="margin:0;padding:0 10px 10px 0;">- Thomas S.</p>
            </div>
            <!--end of customers --></div>
        <%@ include file="/includeFooterPresentation.jsp" %>
    </div>

</body>
</html>