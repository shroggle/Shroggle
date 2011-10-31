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

/**
 * [ Border = 12
 * Blog = 90]
 * <p/>
 * object
 * <p/>
 * <pre>
 * CacheWithAttributes cache = ...;
 * <p/>
 * List<Object> attributes = Arrays.asList(1, 2, 3);
 * cache.add();
 * </pre>
 *
 * @author Artem Stasuk
 */
public interface Cache<I, V> {

    Object getInternal();

    void remove(final I id);

    void add(final I id, final V value);

    V get(final I id);

    String getName();

    CacheStatistic getStatistic();

    void reset();

    void destroy();

}
