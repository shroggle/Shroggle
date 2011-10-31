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

package com.shroggle.util.process;

import java.util.logging.Logger;

/**
 * @author Stasuk Artem
 */
public class ThreadUtil {

    public static void sleep(final long millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            log.info("Stop sleep by interrupted exception!");
            Thread.currentThread().interrupt();
        }
    }

    private static final Logger log = Logger.getLogger(ThreadUtil.class.getName());

}
