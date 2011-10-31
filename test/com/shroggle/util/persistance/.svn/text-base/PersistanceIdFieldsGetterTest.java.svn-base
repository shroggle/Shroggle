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
package com.shroggle.util.persistance;

import com.shroggle.entity.User;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class PersistanceIdFieldsGetterTest {

    @Test
    public void getForEmpty() {
        List<Class> entityClasses = new ArrayList<Class>();
        Map<Class, Field> idFieldByEntityClasses = persistanceIdFieldsGetter.get(entityClasses);
        Assert.assertTrue(idFieldByEntityClasses.isEmpty());
    }

    @Test
    public void get() {
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(User.class);
        Map<Class, Field> idFieldByEntityClasses = persistanceIdFieldsGetter.get(entityClasses);
        Assert.assertEquals(1, idFieldByEntityClasses.size());
        Assert.assertNotNull(idFieldByEntityClasses.get(User.class));
    }

    @Test
    public void getWithNotEntity() {
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(Object.class);
        Map<Class, Field> idFieldByEntityClasses = persistanceIdFieldsGetter.get(entityClasses);
        Assert.assertEquals(1, idFieldByEntityClasses.size());
        Assert.assertNull(idFieldByEntityClasses.get(Object.class));
    }

    private final PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();

}
