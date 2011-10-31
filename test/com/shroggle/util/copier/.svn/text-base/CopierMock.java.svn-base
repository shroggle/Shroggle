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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CopierMock implements Copier {

    @Override
    public <T> T execute(final T source, final String... exclude) {
        return null;
    }

    @Override
    public void execute(final Object source, final Object destination, final String... exclude) {
        calls.add(new CopierMockItem(source, destination, exclude));
    }

    public List<CopierMockItem> getCalls() {
        return calls;
    }

    private List<CopierMockItem> calls = new ArrayList<CopierMockItem>();

}
