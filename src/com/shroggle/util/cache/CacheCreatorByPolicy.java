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

import net.sf.ehcache.Ehcache;
import org.hibernate.cache.StandardQueryCache;
import org.hibernate.cache.UpdateTimestampsCache;

import java.util.*;


/**
 * @author Taras Puchko
 */
public final class CacheCreatorByPolicy {

    public CacheCreatorByPolicy(final CacheCreator cacheCreator) {
        this.cacheCreator = cacheCreator;
    }

    public Cache createFifo(final String name) {
        boolean isTimestamp = name.equals(UpdateTimestampsCache.class.getName());
        boolean isStandardQuery = name.equals(StandardQueryCache.class.getName());
        boolean isQuery = isStandardQuery || name.startsWith(QUERY_PREFIX);
        boolean isSummary = name.startsWith(SUMMARY_PREFIX);
        int maxElementsInMemory = getValue(isTimestamp, isQuery, isSummary, Collections.EMPTY_MAP);
        int timeToLiveSeconds = getValue(isTimestamp, isQuery, isSummary, Collections.EMPTY_MAP);
        int timeToIdleSeconds = getValue(isTimestamp, isQuery, isSummary, Collections.EMPTY_MAP);
        final Ehcache ehCache;
        if (isTimestamp) {
            return cacheCreator.createFifo(name, maxElementsInMemory, timeToIdleSeconds, timeToIdleSeconds);
        } else if (isStandardQuery) {
            return cacheCreator.createFifo(name, maxElementsInMemory, timeToLiveSeconds, timeToIdleSeconds);
        } else {
            CachePolicy policy = getPolicy(name, isQuery);
            if (policy != null) {
                maxElementsInMemory = getValid(policy.maxElementsInMemory(), maxElementsInMemory);
                timeToLiveSeconds = getValid(policy.timeToLiveSeconds(), timeToLiveSeconds);
                timeToIdleSeconds = getValid(policy.timeToIdleSeconds(), timeToIdleSeconds);
            }
            return cacheCreator.createFifo(name, maxElementsInMemory, timeToLiveSeconds, timeToIdleSeconds);
        }
    }

    private CachePolicy getPolicy(String regionName, boolean query) {
        for (Class<?> currentClass = getClassByCacheName(regionName);
             currentClass != Object.class;
             currentClass = currentClass.getSuperclass()) {
            List<CachePolicy> policyList = getActualPolicyList(currentClass, query);
            if (policyList.isEmpty()) {
                continue;
            }
            if (policyList.size() == 1) {
                return policyList.get(0);
            }
            return null;
        }
        return null;
    }

    private List<CachePolicy> getActualPolicyList(Class<?> currentClass, boolean query) {
        return getFilteredPolicyList(currentClass, query);
    }

    private List<CachePolicy> getFilteredPolicyList(Class<?> currentClass, boolean query) {
        List<CachePolicy> list = getFullPolicyList(currentClass);
        for (Iterator<CachePolicy> iterator = list.iterator(); iterator.hasNext();) {
            CachePolicy policy = iterator.next();
            if (query && !policy.queryCache() || !query && policy.queryCache()) {
                iterator.remove();
            }
        }
        return list;
    }

    private List<CachePolicy> getFullPolicyList(Class<?> currentClass) {
        List<CachePolicy> result = new ArrayList<CachePolicy>();
        CachePolicy policy = currentClass.getAnnotation(CachePolicy.class);
        if (policy != null) {
            result.add(policy);
        }
        return result;
    }

    private static int getValue(
            final boolean isTimestamp, final boolean isQuery,
            final boolean isSummary, final Map<String, Integer> map) {
        Integer result = map.get(isTimestamp ? "TIMESTAMP" : isQuery ? "QUERY" : isSummary ? "SUMMARY" : "INSTANCE");
        return result != null ? result : 300;
    }

    private static int getValid(int value, int defaultValue) {
        return value >= 0 ? value : defaultValue;
    }

    private static Class<?> getClassByCacheName(String cacheName) {
        String className = getClassName(cacheName);
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new net.sf.ehcache.CacheException(
                    "Cannot find class '" + className + "' for cache '" + cacheName + "'.");
        }
    }

    private static String getClassName(String cacheName) {
        if (cacheName.startsWith(QUERY_PREFIX)) {
            return cacheName.substring(QUERY_PREFIX.length());
        }
        if (cacheName.startsWith(SUMMARY_PREFIX)) {
            return cacheName.substring(SUMMARY_PREFIX.length());
        }
        int dotIndex = cacheName.lastIndexOf('.');
        if (dotIndex > 0 &&
                dotIndex + 1 < cacheName.length() &&
                Character.isLowerCase(cacheName.charAt(dotIndex + 1))) {
            return cacheName.substring(0, dotIndex);
        }
        return cacheName;
    }

    public static final String QUERY_PREFIX = "QUERY.";
    public static final String SUMMARY_PREFIX = "SUMMARY.";
    private final CacheCreator cacheCreator;

}