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
package com.shroggle.logic;

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.FormExportTask;
import com.shroggle.entity.ItemType;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class SiteItemsManager {

    public static String getNextDefaultName(final ItemType type, final Integer siteId) {
        return getNextDefaultName(type, siteId, null, false);
    }

    public static String getNextDefaultName(final ItemType type, final Integer siteId, final String customNamePattern,
                                            final boolean dontUseFirstNumber) {
        if (siteId == null) {
            return "";
        }
        final Set<String> usedNames = new HashSet<String>();
        final Persistance persistance = ServiceLocator.getPersistance();
        for (final DraftItem siteItem : persistance.getDraftItemsBySiteId(siteId, type)) {
            usedNames.add(siteItem.getName());
        }
        final String name;
        if (StringUtil.isNullOrEmpty(customNamePattern)) {
            name = (type != null ? new ItemTypeManager(type).getName() : "Undefined Item");
        } else {
            name = customNamePattern;
        }
        return new DefaultNameUtil().getNext(name, dontUseFirstNumber, usedNames);
    }

    public static String getNextDefaultNameForFormExportTask(final Integer siteId) {
        if (siteId == null) {
            return "";
        }
        final String customNamePattern = "Form export";
        final Set<String> usedNames = new HashSet<String>();
        final Persistance persistance = ServiceLocator.getPersistance();
        for (final FormExportTask formExportTask : persistance.getFormExportTasksBySiteId(siteId)) {
            usedNames.add(formExportTask.getName());
        }
        return new DefaultNameUtil().getNext(customNamePattern, false, usedNames);
    }

}
