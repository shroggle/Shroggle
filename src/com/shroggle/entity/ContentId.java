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

package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
@Embeddable
public class ContentId implements Serializable {

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != ContentId.class) {
            return false;
        }
        final ContentId contentId = (ContentId) object;
        return selectId.equals(contentId.getSelectId()) && clientId.equals(contentId.getClientId());
    }

    public ContentId() {

    }

    public ContentId(final Integer clientId, final String selectId) {
        this.clientId = clientId;
        this.selectId = selectId;
    }

    @RemoteProperty
    @Column(nullable = false, length = 200)
    private String selectId;

    @RemoteProperty
    @Column(nullable = false)
    private Integer clientId;

}
