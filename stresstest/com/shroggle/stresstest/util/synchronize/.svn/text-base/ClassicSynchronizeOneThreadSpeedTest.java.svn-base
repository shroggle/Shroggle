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

package com.shroggle.stresstest.util.synchronize;

import com.shroggle.PersistanceMock;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.Synchronize;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import com.shroggle.util.process.synchronize.classic.ClassicSynchronize;
import org.junit.Test;
import org.junit.Before;

/**
 * @author Stasuk Artem
 */
public class ClassicSynchronizeOneThreadSpeedTest {

    @Before
    public void before() {
        ServiceLocator.setPersistance(new PersistanceMock());
    }

    @Test
    public void read() throws Exception {
        SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.READ, "b");
        Synchronize synchronize = new ClassicSynchronize();
        final long startTime = System.currentTimeMillis();
        final int executeCount = 10000;
        for (int i = 0; i < executeCount; i++) {
            synchronize.execute(request, new SynchronizeContext<Void>() {

                public Void execute() {
                    return null;
                }

            });
        }
        System.out.println("Costs read synchronize, count calls " + executeCount + " in " + (System.currentTimeMillis() - startTime) + " msec");
    }

    @Test
    public void write() throws Exception {
        SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.WRITE, "b");
        Synchronize synchronize = new ClassicSynchronize();
        final long startTime = System.currentTimeMillis();
        final int executeCount = 10000;
        for (int i = 0; i < executeCount; i++) {
            synchronize.execute(request, new SynchronizeContext<Void>() {

                public Void execute() {
                    return null;
                }

            });
        }
        System.out.println("Costs write synchronize, count calls " + executeCount + " in " + (System.currentTimeMillis() - startTime) + " msec");
    }

}
