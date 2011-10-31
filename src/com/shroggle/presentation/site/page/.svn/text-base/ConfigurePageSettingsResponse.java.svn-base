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
package com.shroggle.presentation.site.page;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.convert.BeanConverter;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject(converter = BeanConverter.class)
public class ConfigurePageSettingsResponse {

    @RemoteProperty
    private String html;

    @RemoteProperty
    private Integer createdDefaultPageId;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getCreatedDefaultPageId() {
        return createdDefaultPageId;
    }

    public void setCreatedDefaultPageId(Integer createdDefaultPageId) {
        this.createdDefaultPageId = createdDefaultPageId;
    }
    
}
