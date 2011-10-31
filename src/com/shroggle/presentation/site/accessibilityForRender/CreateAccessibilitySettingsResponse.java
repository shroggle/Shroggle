/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.presentation.site.accessibilityForRender;

import com.shroggle.presentation.site.FunctionalWidgetInfo;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class CreateAccessibilitySettingsResponse {
    @RemoteProperty
    private FunctionalWidgetInfo functionalWidgetInfo;

    @RemoteProperty
    private String manageRegistrantsHtml;

    @RemoteProperty
    private String treeHtml;

    @RemoteProperty
    private String pageSelectHtml;

    public String getTreeHtml() {
        return treeHtml;
    }

    public void setTreeHtml(String treeHtml) {
        this.treeHtml = treeHtml;
    }

    public String getPageSelectHtml() {
        return pageSelectHtml;
    }

    public void setPageSelectHtml(String pageSelectHtml) {
        this.pageSelectHtml = pageSelectHtml;
    }

    public FunctionalWidgetInfo getFunctionalWidgetInfo() {
        return functionalWidgetInfo;
    }

    public void setFunctionalWidgetInfo(FunctionalWidgetInfo functionalWidgetInfo) {
        this.functionalWidgetInfo = functionalWidgetInfo;
    }

    public String getManageRegistrantsHtml() {
        return manageRegistrantsHtml;
    }

    public void setManageRegistrantsHtml(String manageRegistrantsHtml) {
        this.manageRegistrantsHtml = manageRegistrantsHtml;
    }
}
