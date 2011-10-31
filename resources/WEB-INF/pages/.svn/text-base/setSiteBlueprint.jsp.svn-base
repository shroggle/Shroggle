<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.shroggle.entity.BlueprintCategory" %>
<%@ page import="com.shroggle.logic.site.BlueprintCategoryManager" %>
<%@ page import="com.shroggle.presentation.site.SetSiteBlueprintAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="resources" uri="/WEB-INF/tags/optimization/pageResources.tld" %>
<%@ taglib prefix="international" uri="/WEB-INF/tags/international/international.tld" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags/page" %>
<international:part part="setSiteBlueprint"/>
<% final SetSiteBlueprintAction action = (SetSiteBlueprintAction) request.getAttribute("actionBean"); %>
<html>
<head>
    <title><international:get name="title"/></title>
    <jsp:include page="/includeHeadApplicationResources.jsp" flush="true"/>
    <script type="text/javascript">

        var carousels = new Array();

        $(document).ready(function () {
            var getBlueprints = function () {
                createLoadingArea({
                    element: $("blueprints")[0],
                    text: "Loading data...",
                    color: "green",
                    guaranteeVisibility: true
                });

                var blueprintCategory = $("#blueprintCategory").val();
                var data = blueprintCategory ? {blueprintCategory: blueprintCategory} : null;
                new ServiceCall().executeViaJQuery("/site/getBlueprints.action", data, function(html) {
                    $("#blueprints").html(html);

                    // Initialize carousel for those blueprints who have more than one screen shot.
                    $(".blueprintTemplateImageBlock").each(function () {
                        var screenShotsCount = parseInt($(this).attr("screenShotsCount"));
                        var blueprintId = $(this).attr("blueprintId");
                        if (screenShotsCount > 1) {
                            var carousel = new Object();
                            carousel.blueprintId = blueprintId;

                            carousel.carousel = $(this).find(".blueprintTemplateCarousel").carousel({
                                dispItems: 1,
                                effect: "slide",
                                animSpeed: "500",
                                autoSlide: false,
                                direction: "horizontal",
                                treatAsCarousel: false,
                                prevBtnInsertFn: function(carousel) {
                                    return $("#blueprintTemplateBackButton" + blueprintId);
                                },
                                nextBtnInsertFn: function(carousel) {
                                    return $("#blueprintTemplateNextButton" + blueprintId);
                                },
                                loop: true
                            }).carousel;

                            carousels.push(carousel);
                        }
                    });

                    $(".blueprintDetail").each(function () {
                        this.onclick = function () {
                            blueprintTemplateShowMoreInfoClick(this);
                        }
                    });

                    $("#blueprints").append("<div style='clear:both;'></div>");

                    removeLoadingArea();
                });
            };

            $("#blueprintCategory").bind("change", getBlueprints);

            getBlueprints();
        });

        function blueprintTemplateShowMoreInfoClick(moreInfoLink) {
            var blueprintMoreInfoBlock = $(moreInfoLink).parents(".blueprintTemplateBlock").find(".blueprintTemplateMoreInfoBlock");
            var blueprintId = $(moreInfoLink).attr("blueprintId");

            if ($(blueprintMoreInfoBlock)[0].loaded) {
                $(blueprintMoreInfoBlock).slideDown();

                moreInfoLink.onclick = function () {
                    blueprintTemplateHideMoreInfoClick(blueprintMoreInfoBlock, moreInfoLink);
                }
            } else {
                $(moreInfoLink).parent().find(".blueprintTemplateMoreInfoLoading").css("visibility", "visible");
                new ServiceCall().executeViaJQuery("/site/getBlueprintDetail.action", {blueprintId: blueprintId}, function(html) {
                    $(blueprintMoreInfoBlock).html(html);
                    $(blueprintMoreInfoBlock)[0].loaded = true;
                    $(blueprintMoreInfoBlock).slideDown();

                    moreInfoLink.onclick = function () {
                        blueprintTemplateHideMoreInfoClick(blueprintMoreInfoBlock, moreInfoLink);
                    };

                    $(moreInfoLink).parent().find(".blueprintTemplateMoreInfoLoading").css("visibility", "hidden");
                });
            }
        }

        function blueprintTemplateHideMoreInfoClick(moreInfoBlock, moreInfoLink) {
            $(moreInfoBlock).slideUp();
            moreInfoLink.onclick = function () {
                blueprintTemplateShowMoreInfoClick(moreInfoLink);
            }
        }

        function setBlueprintSelectedId(submitButton) {
            $("#activatedBlueprintId").val($(submitButton).attr("blueprintId"));
        }

        function blueprintTemplateViewImage(viewImage) {
            var blueprintId = $(viewImage).attr("blueprintId");
            var blueprintBlock = $(viewImage).parents(".blueprintTemplateBlock");
            var foundCarousel = false;

            $(carousels).each(function () {
                if (this.blueprintId == blueprintId) {
                    var selectedCarouselItemIndex = this.carousel.getCurrentItemIndex();
                    window.open(blueprintBlock.find("#blueprintTemplateScreenShot" + selectedCarouselItemIndex)[0].src);
                    foundCarousel = true;
                }
            });

            if (!foundCarousel) {
                window.open(blueprintBlock.find(".blueprintTemplateScreenShot")[0].src);
            }
        }

    </script>
</head>

<body>

<div class="wrapper">
    <div class="container">
        <%@ include file="/includeHeadApplication.jsp" %>
        <div class="content">
            <div class="contentselecttemplates">
                <% request.setAttribute("selected", SetSiteBlueprintAction.class); %>
                <% request.setAttribute("siteId", action.getSiteId()); %>
                <% request.setAttribute("createChildSite", action.isCreateChildSite()); %>
                <jsp:include page="setSiteTemplateMenu.jsp" flush="true"/>

                <h1><international:get name="title"/></h1>
                <international:get name="description"/>

                <stripes:form beanclass="com.shroggle.presentation.site.SetSiteBlueprintAction">

                    <international:get name="viewBlueprintSitesByIndustry"/>

                    <select id="blueprintCategory" name="blueprintCategory">
                        <option value="" selected>
                            <international:get name="allBlueprints"/>
                        </option>
                        <% for (BlueprintCategory category : BlueprintCategoryManager.getSortedValues()) { %>
                        <option value="<%= category.toString() %>">
                            <%= new BlueprintCategoryManager(category).getInternationalValue() %>
                        </option>
                        <% } %>
                    </select>

                    <div style="clear: both;"></div>
                    <br><br>

                    <stripes:form beanclass="com.shroggle.presentation.site.SetSiteBlueprintAction">
                        <stripes:hidden name="siteId"/>
                        <stripes:hidden name="activatedBlueprintId" id="activatedBlueprintId"/>
                        <stripes:hidden name="editingMode"/>

                        <div style="margin: 10px 0 10px 0">
                            <page:errors/>
                        </div>

                        <div id="blueprints"></div>
                    </stripes:form>
                    <br clear="all">
                </stripes:form>
            </div>
        </div>
        <%@ include file="/includeFooterApplication.jsp" %>
    </div>
</div>
</body>
</html>