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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Artem Stasuk
 */
public class CopierUtil {

    static {
        java.util.Date defaultValue = null;
        DateConverter converter = new DateConverter(defaultValue);
        ConvertUtils.register(converter, java.util.Date.class);
    }

    public static <T> T copy(final T object) {
        final CopierExplorer explorer = new CopierExplorerStack(
                new CopierExplorerList(),
                new CopierExplorerMethod());

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        final CopierWraper<T> wraper = new CopierWraper<T>(object);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    public static <T> void copyProperties(final T source, final T destination) {
        try {
            BeanUtils.copyProperties(destination, source);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        } catch (InvocationTargetException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static void copyProperties(final Object source, final Object destination, final String... excludedFields) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()),
                excludedFields
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        for (final CopierItem copierItem : explorer.find(source, destination)) {
            worker.copy(copierItem);
        }
    }
}
