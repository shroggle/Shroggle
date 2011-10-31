/*********************************************************************
 *                                                                   *
 * Copyright (c) 2002-2006 by Survey Software Services, Inc.         *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/

package com.shroggle.logic.statistics.searchengines;

/**
 * Author: Igor Kanshin (grenader).
 * </p>
 * Date: Nov 3, 2007
 */
public class SearchEngineUtils {
    public static boolean isStringEmpty(CharSequence src) {
        try {
            return src == null || src.toString().trim().length() == 0;
        }
        catch (Exception ex) {/**/
        }
        return true;
    }
}
