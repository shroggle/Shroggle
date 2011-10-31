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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.entity.Page;
import com.shroggle.entity.User;
import com.shroggle.util.process.synchronize.SynchronizeMethod;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 15 вер 2008
 */
public class ObjectSynchronizeByClassProperty {

    public ObjectSynchronizeByClassProperty(final int id) {
        this.id = id;
    }

    @SynchronizeByClassProperty(
            entityClass = User.class,
            entityIdFieldPath = "id")
    public void test() {
        System.out.println(id);
    }

    @SynchronizeByClassProperty(
            entityClass = Page.class,
            entityIdFieldPath = "id",
            deepParent = 1)
    public void testWithDeep() {
        System.out.println(id);
    }

    @SynchronizeByClassProperties({
            @SynchronizeByClassProperty(
                    entityClass = Page.class,
                    entityIdFieldPath = "id"),
            @SynchronizeByClassProperty(
                    entityClass = User.class,
                    method = SynchronizeMethod.WRITE,
                    entityIdFieldPath = "id")})
    public void testMany() {
        System.out.println(id);
    }

    private int id;

}
