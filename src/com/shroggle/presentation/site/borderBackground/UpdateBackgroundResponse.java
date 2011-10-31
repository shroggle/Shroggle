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

package com.shroggle.presentation.site.borderBackground;

import com.shroggle.entity.Background;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */

@DataTransferObject
public class UpdateBackgroundResponse {

    @RemoteProperty
    private String innerHTML;

    @RemoteProperty
    private Background background;

    @RemoteProperty
    private String newBorderBackgroundStyle;

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

    public String getNewBorderBackgroundStyle() {
        return newBorderBackgroundStyle;
    }

    public void setNewBorderBackgroundStyle(String newBorderBackgroundStyle) {
        this.newBorderBackgroundStyle = newBorderBackgroundStyle;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

}