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

package com.shroggle.util.process.synchronize.classic;

import com.shroggle.PersistanceMock;
import com.shroggle.entity.User;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.*;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Stasuk Artem
 */
public class ClassicSynchronizeTest {

    @Before
    public void before() {
        ServiceLocator.setPersistance(new PersistanceMock());
    }

    @Test
    public void execute() throws Exception {
        SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.WRITE, "a");
        Synchronize synchronize = new ClassicSynchronize();
        synchronize.execute(request, new SynchronizeContext<Void>() {

            public Void execute() {
                return null;
            }

        });
    }

    @Test(expected = NullPointerException.class)
    public void executeWithNullRequest() throws Exception {
        Synchronize synchronize = new ClassicSynchronize();
        synchronize.execute(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void executeWithNullContext() throws Exception {
        SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.WRITE, "a");
        Synchronize synchronize = new ClassicSynchronize();
        synchronize.execute(request, null);
    }

    @Test
    public void executeWithEmptyRequest() throws Exception {
        Synchronize synchronize = new ClassicSynchronize();
        final SynchronizeRequestEmpty request = new SynchronizeRequestEmpty();
        synchronize.execute(request, new SynchronizeContext<Void>() {

            public Void execute() throws Exception {
                return null;
            }

        });
    }

    @Test
    public void executeWithNotFoundPage() throws Exception {
        SynchronizeRequestEntity request = new SynchronizeRequestEntity(
                PageManager.class, SynchronizeMethod.WRITE, 1);
        Synchronize synchronize = new ClassicSynchronize();
        synchronize.execute(request, new SynchronizeContext<Void>() {

            public Void execute() {
                return null;
            }

        });
    }

}
