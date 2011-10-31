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
package com.shroggle.util.reflection;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class ClassesTimeMeter implements Classes {

    public ClassesTimeMeter(final Classes classes) {
        this.classes = classes;
    }

    @Override
    public List<Class> get(final ClassesFilter filter) {
        final long start = System.currentTimeMillis();
        try {
            return classes.get(filter);
        } finally {
            logger.info("get classes by filter: " + filter + ", executed "
                    + (System.currentTimeMillis() - start) + " msec");
        }
    }

    private final Classes classes;
    private final Logger logger = Logger.getLogger(ClassesTimeMeter.class.getSimpleName());

}
