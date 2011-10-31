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
package com.shroggle.logic.site;

import com.shroggle.entity.BlueprintCategory;
import com.shroggle.util.ServiceLocator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class BlueprintCategoryManager {

    public BlueprintCategoryManager(BlueprintCategory blueprintCategory) {
        if (blueprintCategory == null) {
            throw new IllegalArgumentException("Unable to create BlueprintCategoryManager without BlueprintCategory.");
        }
        this.blueprintCategory = blueprintCategory;
    }

    public static BlueprintCategory[] getSortedValues() {
        final BlueprintCategory[] blueprintCategories = BlueprintCategory.values();
        Arrays.sort(blueprintCategories, new Comparator<BlueprintCategory>() {
            @Override
            public int compare(BlueprintCategory o1, BlueprintCategory o2) {
                return o1.toString().compareToIgnoreCase(o2.toString());
            }
        });
        return blueprintCategories;
    }

    public String getInternationalValue() {
        return ServiceLocator.getInternationStorage().get("blueprintCategory", Locale.US).get(blueprintCategory.toString());
    }

    private final BlueprintCategory blueprintCategory;
}
