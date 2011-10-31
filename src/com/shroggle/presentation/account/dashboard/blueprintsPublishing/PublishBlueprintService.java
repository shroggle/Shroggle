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
package com.shroggle.presentation.account.dashboard.blueprintsPublishing;

import com.shroggle.entity.BlueprintCategory;
import com.shroggle.logic.site.SiteManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class PublishBlueprintService {

    @RemoteMethod
    public void execute(int siteId, String description, Map<Integer, Integer> pageScreenShots, final BlueprintCategory blueprintCategory) {
        new SiteManager(siteId).publishBlueprint(description, pageScreenShots, blueprintCategory);
    }

}
