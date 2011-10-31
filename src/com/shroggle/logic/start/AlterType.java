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
package com.shroggle.logic.start;

/**
 * @author Balakirev Anatoliy
 */
public enum AlterType {
    ADD(false),
    CHANGE(false),
    CREATE(false),
    COMMIT(false),
    DELETE(false),
    DROP(true),
    INSERT(false),
    MODIFY(false),
    SET(true),
    START(true),
    SELECT(true),
    UPDATE(false),
    RENAME(false);

    AlterType(boolean ignoreExecutionFail) {
        this.ignoreExecutionFail = ignoreExecutionFail;
    }

    public boolean isIgnoreExecutionFail() {
        return ignoreExecutionFail;
    }

    private final boolean ignoreExecutionFail;

}
