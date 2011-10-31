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
public class ClassicSynchronizeTwoThreadSpeedTest {

    private static class UseSynchronizeThread extends Thread {

        public UseSynchronizeThread(
                Synchronize synchronize, SynchronizeRequestEntity request, int executeCount) {
            this.synchronize = synchronize;
            this.request = request;
            this.executeCount = executeCount;

        }

        public void run() {
            for (int i = 0; i < executeCount; i++) {
                try {
                    synchronize.execute(request, new SynchronizeContext<Void>() {

                        public Void execute() {
                            return null;
                        }

                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private final SynchronizeRequestEntity request;
        private final Synchronize synchronize;
        private final int executeCount;

    }

    @Before
    public void before() {
        ServiceLocator.setPersistance(new PersistanceMock());
    }

    @Test
    public void read() throws InterruptedException {
        final SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.READ, 1);
        final Synchronize synchronize = new ClassicSynchronize();
        final long startTime = System.currentTimeMillis();
        final int executeCount = 10000;
        Thread thread1 = new UseSynchronizeThread(synchronize, request, executeCount);
        Thread thread2 = new UseSynchronizeThread(synchronize, request, executeCount);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Costs  two conccurent read synchronize, count calls " + executeCount + " in " + (System.currentTimeMillis() - startTime) + " msec");
    }

    @Test
    public void readWrite() throws InterruptedException {
        final String monitor = "a";
        final SynchronizeRequestEntity request1 = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.READ, monitor);
        final SynchronizeRequestEntity request2 = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.WRITE, monitor);
        final Synchronize synchronize = new ClassicSynchronize();
        final long startTime = System.currentTimeMillis();
        final int executeCount = 10000;
        Thread thread1 = new UseSynchronizeThread(synchronize, request1, executeCount);
        Thread thread2 = new UseSynchronizeThread(synchronize, request2, executeCount);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Costs  two conccurent read/write synchronize, count calls " + executeCount + " in " + (System.currentTimeMillis() - startTime) + " msec");
    }

}