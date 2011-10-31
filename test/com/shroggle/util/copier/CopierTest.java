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

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CopierTest {

    @Test
    public void copySimple() {
        final Object original = new Object();
        final Object copy = copy(original);

        Assert.assertNotSame(original, copy);
    }

    @Test
    public void copyUnmodificable() {
        final Object original = "fff";
        final Object copy = copy(original);

        Assert.assertEquals(original, copy);
    }

    @Test
    public void copyWithUnmodificable() {
        final CopierObjectUnmodificable original = new CopierObjectUnmodificable();
        original.setA(33);
        original.setD(1.22);
        original.setG("GGGG");
        final CopierObjectUnmodificable copy = copy(original);

        Assert.assertNotSame(original, copy);
        Assert.assertEquals(original.getA(), copy.getA());
        Assert.assertEquals(original.getD(), copy.getD());
        Assert.assertEquals(original.getG(), copy.getG());
    }

    @Test
    public void copyWithObject() {
        final CopierObject original = new CopierObject();
        original.setObject(original);
        original.setUnmodificable(new CopierObjectUnmodificable());
        original.getUnmodificable().setA(33);
        original.getUnmodificable().setD(1.22);
        original.getUnmodificable().setG("GGGG");
        final CopierObject copy = copy(original);

        Assert.assertNotSame(original, copy);
        Assert.assertNotSame(original.getUnmodificable(), copy.getUnmodificable());
        Assert.assertNotSame(original.getObject(), copy.getObject());
        Assert.assertEquals(copy.getObject(), copy);
        Assert.assertEquals(original.getUnmodificable().getA(), copy.getUnmodificable().getA());
        Assert.assertEquals(original.getUnmodificable().getD(), copy.getUnmodificable().getD());
        Assert.assertEquals(original.getUnmodificable().getG(), copy.getUnmodificable().getG());
    }

    @Test
    public void copyList() {
        final List<CopierObjectUnmodificable> original = new ArrayList<CopierObjectUnmodificable>();
        original.add(new CopierObjectUnmodificable());
        original.get(0).setA(33);
        original.get(0).setD(1.22);
        original.get(0).setG("GGGG");
        final List<CopierObjectUnmodificable> copy = copy(original);

        Assert.assertNotSame(original, copy);
        Assert.assertEquals(original.size(), copy.size());
        Assert.assertEquals(original.get(0).getA(), copy.get(0).getA());
        Assert.assertEquals(original.get(0).getD(), copy.get(0).getD());
        Assert.assertEquals(original.get(0).getG(), copy.get(0).getG());
    }

    private static <T> T copy(final T object) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                )
        );

        CopierWorkerWrapper workerWrapper = new CopierWorkerWrapper();
        CopierWorker worker = new CopierWorkerStack(
                new CopierWorkerUnmodificable(),
                new CopierWorkerList(explorer, workerWrapper),
                new CopierWorkerHistory(
                        new CopierWorkerObject(workerWrapper, explorer)
                )
        );
        workerWrapper.setWorker(worker);

        CopierWraper<T> wraper = new CopierWraper<T>(object);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }
}
