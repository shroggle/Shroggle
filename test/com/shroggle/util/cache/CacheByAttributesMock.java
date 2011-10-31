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
package com.shroggle.util.cache;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CacheByAttributesMock<V> implements CacheByAttributes<V> {

    public Object getInternal() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void remove(Object id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void add(Object id, List<V> value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<V> get(Object id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CacheStatistic getStatistic() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
