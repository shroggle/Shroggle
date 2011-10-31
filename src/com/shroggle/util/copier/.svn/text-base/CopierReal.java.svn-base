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
package com.shroggle.util.copier;

/**
 * @author Artem Stasuk
 */
public class CopierReal implements Copier {

    @Override
    public <T> T execute(final T source, final String... exclude) {
        return CopierUtil.copy(source);
    }

    @Override
    public void execute(final Object source, final Object destination, final String... exclude) {
        CopierUtil.copyProperties(source, destination, exclude);
    }

}
