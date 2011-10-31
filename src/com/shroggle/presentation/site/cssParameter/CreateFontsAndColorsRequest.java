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

package com.shroggle.presentation.site.cssParameter;

import org.directwebremoting.annotations.DataTransferObject;

import java.util.List;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class CreateFontsAndColorsRequest {

    private boolean saveCssInCurrentPlace;

    private Integer widgetId;

    private Integer draftItemId;

    private List<CssParameter> cssParameters;

    public List<CssParameter> getCssParameters() {
        return cssParameters;
    }

    public void setCssParameters(List<CssParameter> cssParameters) {
        this.cssParameters = cssParameters;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(final Integer widgetId) {
        this.widgetId = widgetId;
    }

    public boolean isSaveCssInCurrentPlace() {
        return saveCssInCurrentPlace;
    }

    public void setSaveCssInCurrentPlace(boolean saveCssInCurrentPlace) {
        this.saveCssInCurrentPlace = saveCssInCurrentPlace;
    }

    public Integer getDraftItemId() {
        return draftItemId;
    }

    public void setDraftItemId(Integer draftItemId) {
        this.draftItemId = draftItemId;
    }
}