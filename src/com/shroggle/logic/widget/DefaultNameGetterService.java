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
package com.shroggle.logic.widget;

import com.shroggle.entity.ItemType;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.presentation.AbstractService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class DefaultNameGetterService extends AbstractService {

    @RemoteMethod
    public String getNextDefaultName(final ItemType type, final Integer siteId) {
        if (siteId != null) {
            return SiteItemsManager.getNextDefaultName(type, siteId);
        }
        return "";
    }

}
