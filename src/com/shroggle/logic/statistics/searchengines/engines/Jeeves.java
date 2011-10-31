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
package com.shroggle.logic.statistics.searchengines.engines;

class Jeeves extends MinorEngine {

    public String getSearchWord(String ref) {
        return getParameterFromUrlString(ref, "q");
    }

    public String getName() {
        return "Jeeves";
    }
}
