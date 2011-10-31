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
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 05.10.2008
 */
public class PersistanceIdFieldGetterTest {

    @Test
    public void get() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        Field idField = persistanceIdFieldGetter.get(User.class);
        Assert.assertNotNull(idField);
    }

    @Test
    public void getForNotEntityClass() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        Field idField = persistanceIdFieldGetter.get(Object.class);
        Assert.assertNull(idField);
    }

    @Test
    public void getForAbstractEntityClass() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        Field idField = persistanceIdFieldGetter.get(Widget.class);
        Assert.assertNull(idField);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getForNullEntityClass() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        persistanceIdFieldGetter.get(null);
    }

    @Test
    public void getForInheritance() {
        PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();
        Field idField = persistanceIdFieldGetter.get(WidgetItem.class);
        Assert.assertNotNull(idField);
    }

}
