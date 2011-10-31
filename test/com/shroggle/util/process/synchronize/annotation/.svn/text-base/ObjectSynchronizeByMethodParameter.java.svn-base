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
public class ObjectSynchronizeByMethodParameter {

    @SynchronizeByMethodParameter(
            entityClass = User.class,
            method = SynchronizeMethod.READ)
    public void test(final int id) {
        System.out.println(id);
    }

    @SynchronizeByMethodParameter(
            entityClass = Page.class,
            method = SynchronizeMethod.READ,
            deepParent = 1)
    public void testWithDeep(final int id) {
        System.out.println(id);
    }

    @SynchronizeByMethodParameter(
            entityClass = User.class,
            method = SynchronizeMethod.READ,
            entityIdParameterIndex = -1)
    public void testNegativ(final int id) {
        System.out.println(id);
    }

    @SynchronizeByMethodParameter(
            entityClass = User.class,
            method = SynchronizeMethod.READ,
            entityIdParameterIndex = 2)
    public void testVeryBig(final int id) {
        System.out.println(id);
    }

    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = User.class,
                    method = SynchronizeMethod.WRITE),
            @SynchronizeByMethodParameter(
                    entityClass = Page.class,
                    method = SynchronizeMethod.READ)})
    public void testMany(final int id) {
        System.out.println(id);
    }

}