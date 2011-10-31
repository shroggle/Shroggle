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
package com.shroggle.presentation.account.dashboard;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject(converter = EnumConverter.class)
public enum DashboardSiteType {

    COMMON(false), NETWORK(false), CHILD(false), BLUEPRINT(true), PUBLISHED_BLUEPRINT(true), ACTIVATED_BLUEPRINT(true),
    EMPTY_CELL(false), CREATE_BLUEPRINT_CELL(false);

    DashboardSiteType(boolean blueprint) {
        this.blueprint = blueprint;
    }

    public boolean isBlueprint() {
        return blueprint;
    }

    private final boolean blueprint;
}
