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

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.09.2008
 */
public class SynchronizeByCache {

    public SynchronizeRequest getRequest(
            final Method method, final Object object, final Object... parameters) {
        if (object == null) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by null object!");
        }
        if (method == null) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by null method!");
        }
        SynchronizeByCreator creator = classToCreators.get(method);
        if (creator == null) {
            creator = processor.getCreator(method, object, parameters);
            if (creator == null) {
                creator = new SynchronizeByCreatorNone();
            }
            classToCreators.putIfAbsent(method, creator);
        }
        return creator.create(object, parameters);
    }

    private final SynchronizeByProcessor processor = new SynchronizeByProcessor();
    private final ConcurrentMap<Method, SynchronizeByCreator> classToCreators =
            new ConcurrentHashMap<Method, SynchronizeByCreator>();

}
