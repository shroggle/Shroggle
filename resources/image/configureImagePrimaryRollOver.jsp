<%@ page import="com.shroggle.presentation.image.ConfigureImageService" %>
<%@ page import="com.shroggle.presentation.image.ConfigureImageData" %>
<%@ page import="com.shroggle.util.StringUtil" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<international:part part="configureImageWidget"/>

<%
    ConfigureImageService service = (ConfigureImageService) request.getAttribute("imageService");
    ConfigureImageData data = service.getConfigureImageData();
%>
<div style="width:800px;height:540px;">
    <div style="height:120px;">
        <span id="selectImageTD" class="fl">
            <div style="margin:10px 0;">
                <label for="imageName"><international:get name="imageName"/>:</label>&nbsp;
                <input type="text" id="imageName" style="width:150px;" maxlength="255" value="<%= data.getName() %>">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <label for="imageDescription"><international:get name="description"/>:</label>&nbsp;
                <input type="text" id="imageDescription" style="width:150px;" maxlength="255" onkeyup="setSampleTitle();"
                       value="<%= data.getDescription() %>">
            </div>

            <div style="margin-bottom:5px;">
                <international:get name="selectAndSpecifyImagePrimary"/>
            </div>
            <div style="margin-left:5px;">
                <div style="margin-bottom:5px;">
                    <label for="widgetImageWidth">
                        <international:get name="imageDisplaySize"/>
                    </label>
                    <international:get name="w"/> <input style="width: 30px" type="text"
                                                         id="widgetImageWidth"
                                                         value="<%= data.getWidth() == null ? "" : data.getWidth() %>"
                                                         onblur="widthChanged(this.value);"
                                                         onKeyPress="return numbersOnly(this, event);"
                                                         maxlength="5"
                                                         class="txt_nowidth"> x
                    <international:get name="h"/> <input style="width: 30px" type="text"
                                                         id="widgetImageHeight"
                                                         value="<%= data.getHeight() == null ? "" : data.getHeight() %>"
                                                         onblur="heightChanged(this.value);"
                                                         onKeyPress="return numbersOnly(this, event);"
                                                         maxlength="5"
                                                         class="txt_nowidth">
                    &nbsp;&nbsp;&nbsp;&nbsp;<input
                        type="checkbox" id="saveProportionCheckbox"
                        onclick="saveProportionCheckboxChanged(this);"
                    <% if (data.isSaveRatio()) { %> checked <% } %>> <label for="saveProportionCheckbox">
                    <international:get name="saveProportion"/></label>
                </div>

                <div style="margin-bottom:5px;">
                    <label for="widgetImageMargin">
                        <international:get name="marginInPixels"/>
                    </label>
                    <select size="1" id="widgetImageMargin">
                        <% for (int i = 0; i < 51; i++) { %>
                        <option value="<%= i %>"
                                <% if (data.getMargin() == i) { %> selected <% } %> >
                            <%= i %>
                        </option>
                        <% } %>
                    </select>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label for="aligmentImageSelect"><international:get
                        name="alignment"/></label>
                    <select id="aligmentImageSelect">
                        <% String alingnment = StringUtil.getEmptyOrString(data.getAligment()); %>
                        <option value="Left"
                                <% if (alingnment.equals("Left") || alingnment.equals("")) { %> selected <% } %>>
                            <international:get name="left"/>
                        </option>
                        <option value="Center"
                                <% if (alingnment.equals("Center")) { %> selected <% } %>>
                            <international:get name="center"/>
                        </option>
                        <option value="Right"
                                <% if (alingnment.equals("Right")) { %> selected <% } %>>
                            <international:get name="right"/>
                        </option>
                    </select>

                </div>
                <div style="margin-bottom:5px;">
                </div>
            </div>
        </span>

        <span id="rollOverTD" class="fl" style="display:none;">
            <div style="margin-bottom:5px;">
                <international:get name="selectAndSpecifyImageRollOver"/>
            </div>

            <div style="margin-bottom:5px;">
                <input id="widgetImageShowDescriptionOnMouseOver" onclick="disableOnMouseOverText(true);"
                       onchange="setSampleTitle();" name="onMouseOverText"
                       type="radio" <%= data.isDescriptionOnMouseOver() ? "checked" : "" %>>
                <label for="widgetImageShowDescriptionOnMouseOver">
                    <international:get name="showDescriptionOnMouseOver"/>
                </label>
            </div>

            <div style="margin-bottom:5px;">
                <input id="widgetImageShowTextOnMouseOver" onclick="disableOnMouseOverText(false);"
                       onchange="setSampleTitle();" name="onMouseOverText"
                       type="radio" <%= !data.isDescriptionOnMouseOver() ? "checked" : "" %>>
                <label for="widgetImageShowTextOnMouseOver">
                    <international:get name="showTheFollowingTextOnMouseOver"/>
                </label>

                <div style="margin-top:10px;margin-left:25px;">
                    <input type="text" id="onMouseOverText" value="<%= data.getOnMouseOverText() %>" maxlength="255"
                           <%= data.isDescriptionOnMouseOver() ? "disabled" : "" %>
                           style="width:200px;">
                </div>
            </div>
        </span>
    </div>

    <br/>

    <div id="uploadedImages" class="uploadedImages"></div>
    <br clear="all">

    <div>
        <international:get name="browseYourComputer"/>
        <div id="uploadImagesButtonDiv"></div>
        <br/>
    </div>
</div>