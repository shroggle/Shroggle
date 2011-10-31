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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestComposit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.09.2008
 */
class SynchronizeByCreatorComposit implements SynchronizeByCreator {

    public SynchronizeByCreatorComposit(final List<SynchronizeByCreator> creators) {
        this.creators = Collections.unmodifiableList(
                new ArrayList<SynchronizeByCreator>(creators));
    }

    public SynchronizeRequest create(final Object object, final Object... parameters) {
        final List<SynchronizeRequest> requests = new ArrayList<SynchronizeRequest>();
        for (final SynchronizeByCreator creator : creators) {
            requests.add(creator.create(object, parameters));
        }
        return new SynchronizeRequestComposit(
                requests.toArray(new SynchronizeRequest[1]));
    }

    private final List<SynchronizeByCreator> creators;

}
