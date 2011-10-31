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
package com.shroggle.util.process.timecounter;

import java.util.List;

/**
 * @author Artem Stasuk
 * @see com.shroggle.util.process.timecounter.TimeCounterCreator
 */
public interface TimeCounterResult {

    long getExecutedTime();

    String getName();

    int getExecutingCount();

    int getExecutedCount();

    /**
     * @return - null if not supported in implementation or value
     */
    Long getExecutingTime();

    /**
      * @return - list of long each element of which represent each execution`s time in order they were made.
     *              Returns emptyList if not supported in implementation.
      * */
    List<Long> getExecutedHistory();

}
