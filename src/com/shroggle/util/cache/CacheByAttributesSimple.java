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

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class CacheByAttributesSimple<V> implements CacheByAttributes<V> {

    public CacheByAttributesSimple(final String name, final int size) {
        this.size = size;
        this.name = name;
    }

    public Object getInternal() {
        return this;
    }

    public void remove(final Object id) {
        synchronized (this) {
            final CacheByAttributesSimpleValues<V> attributesValues = valuesByIds.get(id);
            if (attributesValues != null) {
                for (final Object tempId : attributesValues.getIds()) {
                    valuesByIds.remove(tempId);
                }
            }
        }
    }

     public void add(final Object idsObject, final List<V> values) {
        synchronized (this) {
            if (valuesByIds.size() >= size) {
                final List<CacheByAttributesSimpleValuesUsed<V>> allAttributesValuesUsed =
                        new ArrayList<CacheByAttributesSimpleValuesUsed<V>>();
                for (final CacheByAttributesSimpleValues<V> attributesValues : valuesByIds.values()) {
                    final CacheByAttributesSimpleValuesUsed<V> attributesValuesUsed =
                            new CacheByAttributesSimpleValuesUsed<V>();
                    attributesValuesUsed.setValue(attributesValues);
                    for (final CacheByAttributesSimpleValue<V> attributesValue : attributesValues.getValues()) {
                        if (attributesValuesUsed.getUsed() == null || attributesValuesUsed.getUsed().getUsed() > attributesValue.getUsed()) {
                            attributesValuesUsed.setUsed(attributesValue);
                        }
                    }
                    allAttributesValuesUsed.add(attributesValuesUsed);
                }
                Collections.sort(allAttributesValuesUsed, CacheByAttributesSimpleValuesUsedComparator.instance);

                final int countRemove = Math.round((float) allAttributesValuesUsed.size() / 10f);
                for (int i = 0; i < countRemove; i++) {
                    final CacheByAttributesSimpleValuesUsed<V> attributesValuesUsed = allAttributesValuesUsed.get(i);
                    attributesValuesUsed.getValue().getValues().remove(attributesValuesUsed.getUsed());
                    if (attributesValuesUsed.getValue().getValues().isEmpty()) {
                        for (final Object id : attributesValuesUsed.getValue().getIds()) {
                            valuesByIds.remove(id);
                        }
                    }
                }
            }
            @SuppressWarnings({"unchecked"})// todo. Comment, why this suppress is correct. Tolik
            final List<Object> ids = (List<Object>) idsObject;
            final List<CacheByAttributesSimpleValue<V>> attributesValues =
                    new ArrayList<CacheByAttributesSimpleValue<V>>();
            for (final V value : values) {
                final CacheByAttributesSimpleValue<V> attributesValue =
                        new CacheByAttributesSimpleValue<V>(value);
                attributesValue.setUsed(System.currentTimeMillis());
                attributesValues.add(attributesValue);
            }

            for (final Object id : ids) {
                CacheByAttributesSimpleValues<V> addAttributesValues = valuesByIds.get(id);
                if (addAttributesValues == null) {
                    addAttributesValues = new CacheByAttributesSimpleValues<V>();
                    valuesByIds.put(id, addAttributesValues);
                }
                addAttributesValues.getIds().addAll(ids);
                addAttributesValues.getValues().addAll(attributesValues);
            }
        }
    }

    public List<V> get(final Object id) {
        synchronized (this) {
            final CacheByAttributesSimpleValues<V> attributesValues = valuesByIds.get(id);
            if (attributesValues != null) {
                final List<V> values = new ArrayList<V>(attributesValues.getValues().size());
                for (final CacheByAttributesSimpleValue<V> attributesValue : attributesValues.getValues()) {
                    values.add(attributesValue.getValue());
                    attributesValue.setUsed(System.currentTimeMillis());
                }
                return values;
            }
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public CacheStatistic getStatistic() {
        return null;
    }

    public void reset() {
        synchronized (this) {
            valuesByIds.clear();
        }
    }

    public void destroy() {
        reset();
    }

    private final String name;
    private final int size;
    private final Map<Object, CacheByAttributesSimpleValues<V>> valuesByIds =
            new HashMap<Object, CacheByAttributesSimpleValues<V>>();

}
