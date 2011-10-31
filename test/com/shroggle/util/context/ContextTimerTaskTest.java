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
package com.shroggle.util.context;

import org.junit.Test;
import org.junit.Assert;

import java.util.TimerTask;

import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
public class ContextTimerTaskTest {

    @Test
    public void run() {
        ServiceLocator.setContextStorage(new ContextStorage());
        new ContextTimerTask(new TimerTask() {

            @Override
            public void run() {
                Assert.assertNotNull(ServiceLocator.getContextStorage().get());
            }

        }).run();
    }

}
