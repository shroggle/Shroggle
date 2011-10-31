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

package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class CreateVisitorResponse {

    @RemoteProperty
    private String code;

    @RemoteProperty
    private Integer filledFormId;

    @RemoteProperty
    private String nextPageHtml;

    @RemoteProperty
    private String redirectURL;

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getNextPageHtml() {
        return nextPageHtml;
    }

    public void setNextPageHtml(String nextPageHtml) {
        this.nextPageHtml = nextPageHtml;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(Integer filledFormId) {
        this.filledFormId = filledFormId;
    }
}