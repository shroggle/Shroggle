<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="widget" tagdir="/WEB-INF/tags/widget" %>
<%@ page import="com.shroggle.entity.DraftChildSiteRegistration" %>
<%@ page import="com.shroggle.entity.Image" %>
<%@ page import="com.shroggle.logic.image.ImageManager" %>
<%@ page import="com.shroggle.logic.site.page.PageManager" %>
<%@ page import="com.shroggle.presentation.site.ConfigureChildSiteRegistrationService" %>
<%@ page import="com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager" %>
<%@ page import="com.shroggle.util.ServiceLocator" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureChildSiteRegistration"/>
<%
    final ConfigureChildSiteRegistrationService serviceWhiteLabelTab = (ConfigureChildSiteRegistrationService) request.getAttribute("childSiteRegistrationService");
    final ChildSiteRegistrationManager whiteLabelManager= new ChildSiteRegistrationManager(serviceWhiteLabelTab.getChildSiteRegistration());
    final Image footerImage = ServiceLocator.getPersistance().getImageById(whiteLabelManager.getFooterImageId());
    final String fromEmail = !StringUtil.isNullOrEmpty(whiteLabelManager.getFromEmail()) ? whiteLabelManager.getFromEmail() : serviceWhiteLabelTab.getLoginedUserEmail();
    String id;
%>
<input type="hidden" value="<%= footerImage != null ? footerImage.getImageId() : "" %>" id="footerImageId">
<table>
<tr>
<td>
<%----------------------------------------------------Subdomains------------------------------------------------------%>
<div style="margin-bottom:5px;font-weight:bold;">
    <international:get name="whiteLabelDescription"/>:
</div>
<div style="margin-bottom:5px;">
    <input type="checkbox" id="whiteLabelBrandedUrlDefault"
        <%= whiteLabelManager.isBrandedAllowShroggleDomain() ? "checked=\"checked\"" : "" %>>

    <label for="whiteLabelBrandedUrlDefault">
        <span style="font-weight:bold;"><international:get name="whiteLabelBrandedUrlDefault"/></span>
    </label>
</div>

<div>
    <input type="checkbox" id="whiteLabelBrandedUrlUse"
        <%= !whiteLabelManager.getBrandedUrl().isEmpty() ? "checked=\"checked\"" : "" %>>
    <label for="whiteLabelBrandedUrlUse">
        <span style="font-weight:bold;"><international:get name="whiteLabelBrandedUrlUse"/></span>
    </label>
</div>

<div style="margin-left: 20px; margin-top: 5px;">
    <div style="width:230px;float:left;">
        <label for="whiteLabelBrandedUrl"><international:get name="whiteLabelBrandedUrl"/></label>
    </div>
    <input type="text" id="whiteLabelBrandedUrl" style="width: 170px;" maxlength="500"
           value="<%= whiteLabelManager.getBrandedUrl() %>">

            <span class="inform_mark" style="cursor: pointer;"
                  onmouseover="this.className = 'inform_mark_Over';bindTooltip({contentId: 'whiteLabelBrandedUrlInfoDescriptionWindow', element:this, width:400});"
                  onmouseout="this.className = 'inform_mark';">&nbsp;</span>

    <div style="display: none;" id="whiteLabelBrandedUrlInfoDescriptionWindow">
        <international:get name="whiteLabelBrandedUrlInfoDescription"/>
    </div>
</div>
<%----------------------------------------------------Subdomains------------------------------------------------------%>

<%---------------------------------------------Contact Us Link`s Settings---------------------------------------------%>
<div style="margin-top:15px;">
    <div style="font-weight:bold">
        <international:get name="createLinkToYourContactUsPage"/>:
    </div>
    <div style="margin-left: 20px; margin-top: 5px;">
        <div style="width:230px;float:left;">
            <label for="contactUsPages">
                <international:get name="contactUsPage"/>:
            </label>
        </div>
        <select id="contactUsPages" style="width:177px;"
                <% if (serviceWhiteLabelTab.getPages().isEmpty()) { %>disabled<% } %>>
            <% if (serviceWhiteLabelTab.getPages().isEmpty()) { %>
            <option value="-1">
                <international:get name="selectContactUsPage"/>
            </option>
            <% } else { %>
            <% for (PageManager pageManager : serviceWhiteLabelTab.getPages()) { %>
            <option value="<%= pageManager.getId() %>"
                    <% if (Integer.valueOf(pageManager.getId()).equals(whiteLabelManager.getContactUsPageId())) { %>selected<% } %>>
                <%= pageManager.getName() %>
            </option>
            <% }
            } %>
        </select>
                <span class="inform_mark" style="cursor: pointer;"
                      onmouseover="this.className = 'inform_mark_Over';bindTooltip({contentId: 'whiteLabelContactUsInfoDescriptionWindow', element:this, width:400});"
                      onmouseout="this.className = 'inform_mark';">&nbsp;</span>

        <div style="display: none;" id="whiteLabelContactUsInfoDescriptionWindow">
            <international:get name="whichPageIsContactUsPage"/>
        </div>
    </div>
</div>
<%---------------------------------------------Contact Us Link`s Settings---------------------------------------------%>

<%-----------------------------------'Choose your own 'From Email Address'------------------------------------%>
<div style="margin:10px 0;">
    <label for="fromEmail">
                <span style="font-weight:bold;">
                    <international:get name="chooseYourOwnFromEmailAddress"/>:
                </span>
    </label>

    <div style="margin-left: 20px; margin-top: 5px;">
        <div style="width:230px;float:left;">
            <label for="fromEmail">
                <international:get name="fromEmail"/>:
            </label>
        </div>
        <input type="text" maxlength="255" id="fromEmail" value="<%= fromEmail %>" style="width:170px;">

                <span class="inform_mark" style="cursor: pointer;"
                      onmouseover="this.className = 'inform_mark_Over';bindTooltip({contentId: 'whiteLabelEmailInfoDescriptionWindow', element:this, width:400});"
                      onmouseout="this.className = 'inform_mark';">&nbsp;</span>

        <div style="display: none;" id="whiteLabelEmailInfoDescriptionWindow">
            <international:get name="fromEmailAddressInstruction"/>
        </div>
    </div>
</div>
<%-----------------------------------'Choose your own 'From Email Address'------------------------------------%>

<%--------------------------------------------------Logo------------------------------------------------------%>
<div style="margin-top:15px;">
    <div style="margin-bottom:2px;font-weight:bold;">
        <international:get name="addYourLogo"/>:
    </div>
    <div style="width:470px;height:40px;overflow:hidden;margin-left: 20px; margin-top: 5px;">
        <% int result = ImageManager.LOGO_HEIGHT;
            int result1 = ImageManager.LOGO_WIDTH; %>
        <div style="width:230px;float:left;">
            <img id="logoImage" src="<%= serviceWhiteLabelTab.getImageUrl() %>" alt="logo"
                 width="<%= result1 / 2 %>"
                 height="<%= result / 2 %>">
        </div>
        <div style="float:left;width:175px;">
            <input type="button" value="<international:get name="browseAndUpload"/>" id="browseAndUploadLogoButton"
                   class="but_w170_misc">

                    <span id="logoButtonContainer"
                          style="position:relative;top:-25px;left:0;cursor: pointer;"
                          onmouseout="$('#browseAndUploadLogoButton')[0].className='but_w170_misc';"
                          onmouseover="$('#browseAndUploadLogoButton')[0].className='but_w170_Over_misc';">
                        <span id="logoButtonPlaceHolder">

                        </span>
                    </span>
        </div>
                <span class="inform_mark" style="cursor: pointer;float:left;"
                      onmouseover="this.className = 'inform_mark_Over';bindTooltip({contentId: 'whiteLabelLogoInfoDescriptionWindow', element:this, width:400});"
                      onmouseout="this.className = 'inform_mark';">&nbsp;</span>

        <div style="display: none;" id="whiteLabelLogoInfoDescriptionWindow">
            <international:get name="logoText">
                <% result = ImageManager.LOGO_WIDTH; %><international:param value="<%= result %>"/>
                <% result = ImageManager.LOGO_HEIGHT; %><international:param value="<%= result %>"/>
            </international:get>
        </div>
    </div>
</div>
<%--------------------------------------------------Logo------------------------------------------------------%>


<%---------------------------------------------Footer Image, Text and Url---------------------------------------------%>
<div style="margin-bottom:5px;font-weight:bold;margin-top:15px;">
    <international:get name="whiteLabelFooter"/>:
</div>
<div style="margin-left: 20px;">
    <%--------------------------------------------------Footer Text---------------------------------------------------%>
    <div style="width:230px;float:left;">
        <label for="whiteLabelFooterText"><international:get name="whiteLabelFooterText"/>:</label>
    </div>
    <input type="text" maxlength="1000" id="whiteLabelFooterText" style="width: 170px;"
           value="<%= whiteLabelManager.getFooterText() %>">
    <%--------------------------------------------------Footer Text---------------------------------------------------%>

    <%-------------------------------------------------Footer Image---------------------------------------------------%>
    <div>
        <international:get name="addFooterImage"/>:
    </div>
    <div style="width: 470px; height: 40px; overflow: hidden;">
        <div style="width:230px;float:left;">
            <img id="footerImage" src="<%= serviceWhiteLabelTab.getFooterImageUrl() %>" alt="footer image"
                 width="<%= ImageManager.FOOTER_IMAGE_WIDTH / 2 %>"
                 height="<%= ImageManager.FOOTER_IMAGE_HEIGHT / 2 %>">
        </div>
        <div style="float:left;width:175px;">
            <input type="button" value="<international:get name="browseAndUpload"/>"
                   id="browseAndUploadFooterImageButton"
                   class="but_w170_misc">
                    <span id="footerImageButtonContainer"
                          style="position:relative;top:-25px;left:0;cursor: pointer;"
                          onmouseout="$('#browseAndUploadFooterImageButton')[0].className='but_w170_misc';"
                          onmouseover="$('#browseAndUploadFooterImageButton')[0].className='but_w170_Over_misc';">
                        <span id="footerImageButtonPlaceHolder"></span>
                    </span>
        </div>
                <span class="inform_mark" style="cursor: pointer;float:left;"
                      onmouseover="this.className = 'inform_mark_Over';bindTooltip({contentId: 'whiteLabelFooterImageInfoDescriptionWindow', element:this, width:400});"
                      onmouseout="this.className = 'inform_mark';">&nbsp;</span>

        <div style="display: none;" id="whiteLabelFooterImageInfoDescriptionWindow">
            <international:get name="whiteLabelFooterImage">
                <international:param value="<%= ImageManager.FOOTER_IMAGE_WIDTH %>"/>
                <international:param value="<%= ImageManager.FOOTER_IMAGE_HEIGHT %>"/>
            </international:get>
        </div>
    </div>
    <%-------------------------------------------------Footer Image---------------------------------------------------%>

    <%--------------------------------------------------Footer Url----------------------------------------------------%>
    <div style="width:230px;float:left;margin-top:5px;">
        <label for="whiteLabelFooterUrl">
            <international:get name="whiteLabelFooterUrl"/>:
        </label>
    </div>
    http:// <input type="text" maxlength="255" id="whiteLabelFooterUrl" style="width: 140px;"
           value="<%= whiteLabelManager.getFooterUrl() %>">
    <%--------------------------------------------------Footer Url----------------------------------------------------%>
</div>

<%---------------------------------------------Footer Image, Text and Url---------------------------------------------%>
<br clear="all">
</td>
<td>
    <%-------------------------------------Terms & Conditions text editor-----------------------------------------%>
    <div style="margin-bottom:15px;">
        <%--------------------------------------hidden fields---------------------------------------%>
        <% id = "ChildSiteRegistrationTermsAndConditions"; %>
        <div id="<%= id %>Header" style="display: none;">
            <%= whiteLabelManager.getSavedTermsAndConditionsTextOrDefault() %>
        </div>
        <input type="hidden" id="show<%= id %>Header" value="false">
        <input type="hidden" id="<%= id %>WindowHeader"
               value="<international:get name="termsAndConditions"/>">
        <%--------------------------------------hidden fields---------------------------------------%>

        <div onmouseover="bindTooltip({element:this, contentId:'<%= id %>Header', width:600, delay:1000, holdPositionOnMouseOver:true});">
            <div style="float:left;font-weight:bold;">
                <label for="<%= id %>Link">
                    <international:get name="termsAndConditions"/>&nbsp;-&nbsp;
                </label>
            </div>
            <div style="float:left;margin-bottom:2px;">
                <a id="<%= id %>Link"
                   href="javascript:showConfigureItemDescription({id:'<%= id %>', showCheckbox:false, headerId:'<%= id %>WindowHeader'});">
                    <international:get name="edit"/></a>
            </div>
            <br clear="all">
            <label for="<%= id %>Link">
                <international:get name="legallyDefineYourServiceAndRestrictions"/>
            </label>
        </div>
    </div>
    <%--------------------------------------Terms & Conditions text editor----------------------------------------%>

    <%------------------------------------Customize Email Text text editor----------------------------------------%>
    <div style="margin-bottom:15px;">
        <%--------------------------------------hidden fields---------------------------------------%>
        <% id = "ChildSiteRegistrationCustomizeEmailText"; %>
        <div id="<%= id %>Header" style="display: none;">
            <%= whiteLabelManager.getSavedEmailTextOrDefault() %>
        </div>
        <input type="hidden" id="show<%= id %>Header" value="false">
        <input type="hidden" id="<%= id %>WindowHeader"
               value="<international:get name="customizeEmailText"/>">
        <%--------------------------------------hidden fields---------------------------------------%>

        <div onmouseover="bindTooltip({element:this, contentId:'<%= id %>Header', width:600, delay:1000, holdPositionOnMouseOver:true});">
            <div style="float:left;font-weight:bold;">
                <label for="<%= id %>Link">
                    <international:get name="customizeEmailText"/>&nbsp;-&nbsp;
                </label>
            </div>
            <div style="float:left;margin-bottom:2px;">
                <a id="<%= id %>Link"
                   href="javascript:normalizeEmailText('<%= id %>Header');showConfigureItemDescription({id:'<%= id %>', showCheckbox:false, headerId:'<%= id %>WindowHeader'});">
                    <international:get name="edit"/></a>
            </div>
            <br clear="all">
            <label for="<%= id %>Link">
                <international:get name="shroggleSendsEmailVerification"/>
            </label>
        </div>
    </div>
    <%------------------------------------Customize Email Text text editor----------------------------------------%>

    <%-----------------------------Customize Thanks for Registering Text editor-----------------------------------%>
    <div style="margin-bottom:15px;">
        <%--------------------------------------hidden fields---------------------------------------%>
        <% id = "ChildSiteRegistrationThanksForRegisteringText"; %>
        <div id="<%= id %>Header" style="display: none;">
            <%= whiteLabelManager.getSavedThanksForRegisteringTextOrDefault() %>
        </div>
        <input type="hidden" id="show<%= id %>Header" value="false">
        <input type="hidden" id="<%= id %>WindowHeader"
               value="<international:get name="customizeThanksForRegisteringText"/>">
        <%--------------------------------------hidden fields---------------------------------------%>

        <div onmouseover="bindTooltip({element:this, contentId:'<%= id %>Header', width:600, delay:1000, holdPositionOnMouseOver:true});">
            <div style="float:left;font-weight:bold;">
                <label for="<%= id %>Link">
                    <international:get name="customizeThanksForRegisteringText"/>&nbsp;-&nbsp;
                </label>
            </div>
            <div style="float:left;margin-bottom:2px;">
                <a id="<%= id %>Link"
                   href="javascript:normalizeEmailText('<%= id %>Header');showConfigureItemDescription({id:'<%= id %>', showCheckbox:false, headerId:'<%= id %>WindowHeader'});">
                    <international:get name="edit"/></a>
            </div>
            <br clear="all">
            <label for="<%= id %>Link">
                <international:get name="customizeThanksForRegisteringInstruction"/>
            </label>
        </div>
    </div>
    <%-----------------------------Customize Thanks for Registering Text editor-----------------------------------%>


    <%-----------------------------------Customize Welcome Text text editor---------------------------------------%>
    <div style="margin-bottom:15px;">
        <%--------------------------------------hidden fields---------------------------------------%>
        <% id = "ChildSiteRegistrationCustomizeWelcomeText"; %>
        <div id="<%= id %>Header" style="display: none;">
            <%= whiteLabelManager.getSavedWelcomeTextOrDefault() %>
        </div>
        <input type="hidden" id="show<%= id %>Header" value="false">
        <input type="hidden" id="<%= id %>WindowHeader"
               value="<international:get name="customizeWelcomeText"/>">
        <%--------------------------------------hidden fields---------------------------------------%>
        <div onmouseover="bindTooltip({element:this, contentId:'<%= id %>Header', width:600, delay:1000, holdPositionOnMouseOver:true});">
            <div style="float:left;font-weight:bold;">
                <label for="<%= id %>Link">
                    <international:get name="customizeWelcomeText"/>&nbsp;-&nbsp;
                </label>
            </div>
            <div style="float:left;margin-bottom:2px;">
                <a id="<%= id %>Link"
                   href="javascript:showConfigureItemDescription({id:'<%= id %>', showCheckbox:false, headerId:'<%= id %>WindowHeader'});">
                    <international:get name="edit"/></a>
            </div>
            <br clear="all">
            <label for="<%= id %>Link">
                <international:get name="customerWelcomeScreen"/>
            </label>
        </div>
    </div>
    <%------------------------------------Customize Welcome Text text editor---------------------------------------%>
    <br clear="all">
</td>
</tr>
</table>