<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configurePageName"/>
<div id="ownDomainName" style="display:none;">
    <div class="windowOneColumn">
        <div style="overflow:auto;height:450px; text-align:left;">
            <international:get name="domainNameText"/>
        </div>
        <br clear="all"/>

        <div align="right"><input type="button" onclick="closeConfigureWidgetDiv();" class="but_w73"
                                onmouseover="this.className='but_w73_Over';"
                                onmouseout="this.className='but_w73';"
                                value="Close"></div>
    </div>
</div>