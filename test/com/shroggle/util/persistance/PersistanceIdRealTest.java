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
import com.shroggle.entity.WidgetItem;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class PersistanceIdRealTest {

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullFields() {
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        new PersistanceIdReal(null, maxIdByEntityClasses);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullMax() {
        Map<Class, Field> idFieldByEntityClasses = new HashMap<Class, Field>();
        new PersistanceIdReal(idFieldByEntityClasses, null);
    }

    @Test
    public void createWithEmptyMax() {
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(User.class);
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        User account1 = new User();
        persistanceId.set(account1);
        Assert.assertEquals(1, (int) account1.getUserId());

        User account2 = new User();
        persistanceId.set(account2);
        Assert.assertEquals(2, (int) account2.getUserId());
    }

    @Test
    public void createWithAlredySettedId() {
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(User.class);
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        User user = new User();
        persistanceId.set(user);
        Assert.assertEquals(1, (int) user.getUserId());

        persistanceId.set(user);
        Assert.assertEquals(1, (int) user.getUserId());
    }

    @Test
    public void create() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(User.class);
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        maxIdByEntityClasses.put(persistanceIdFieldGetter.get(User.class), 10);
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        User account = new User();
        persistanceId.set(account);
        Assert.assertEquals(11, (int) account.getUserId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setWithNullEntity() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(User.class);
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        maxIdByEntityClasses.put(persistanceIdFieldGetter.get(User.class), 10);
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        persistanceId.set(null);
    }

    @Test
    public void setWithNotFoundEntity() {
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        final User user = new User();
        Assert.assertEquals(0, (int) user.getUserId());
        persistanceId.set(user);
        Assert.assertEquals(0, (int) user.getUserId());
    }

    @Test
    public void createWithEquals() {
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(WidgetItem.class);
        entityClasses.add(WidgetItem.class);
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        WidgetItem widgetItem = new WidgetItem();
        WidgetItem widgetMenu = new WidgetItem();
        persistanceId.set(widgetItem);
        persistanceId.set(widgetMenu);
        Assert.assertEquals(1, widgetItem.getWidgetId());
        Assert.assertEquals(2, widgetMenu.getWidgetId());
    }

    @Test
    public void createWithEntityClassWithoutField() {
        PersistanceIdFieldsGetter persistanceIdFieldsGetter = new PersistanceIdFieldsGetter();
        List<Class> entityClasses = new ArrayList<Class>();
        entityClasses.add(Object.class);
        Map<Field, Integer> maxIdByEntityClasses = new HashMap<Field, Integer>();
        PersistanceId persistanceId = new PersistanceIdReal(
                persistanceIdFieldsGetter.get(entityClasses), maxIdByEntityClasses);

        Object object = new Object();
        persistanceId.set(object);
    }

}
