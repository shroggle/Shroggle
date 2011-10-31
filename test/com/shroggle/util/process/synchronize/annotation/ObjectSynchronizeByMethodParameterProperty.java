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
public class ObjectSynchronizeByMethodParameterProperty {

    @SynchronizeByMethodParameterProperty(
            entityClass = User.class,
            method = SynchronizeMethod.READ,
            entityIdPropertyPath = "a")
    public void test(final RequestSynchronizeByMethodParameterProperty request) {
        System.out.println(request);
    }

    @SynchronizeByMethodParameterProperty(
            entityClass = Page.class,
            method = SynchronizeMethod.READ,
            entityIdPropertyPath = "a",
            deepParent = 1)
    public void testWithDeep(final RequestSynchronizeByMethodParameterProperty request) {
        System.out.println(request);
    }

    @SynchronizeByMethodParameterProperty(
            entityClass = User.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "f",
            entityIdParameterIndex = -1)
    public void testNegativ(final RequestSynchronizeByMethodParameterProperty request) {
        System.out.println(request);
    }

    @SynchronizeByMethodParameterProperty(
            entityClass = User.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "f",
            entityIdParameterIndex = 2)
    public void testVeryBig(final RequestSynchronizeByMethodParameterProperty request) {
        System.out.println(request);
    }

    @SynchronizeByMethodParameterProperties({
            @SynchronizeByMethodParameterProperty(
                    entityClass = User.class,
                    method = SynchronizeMethod.WRITE,
                    entityIdPropertyPath = "a"),
            @SynchronizeByMethodParameterProperty(
                    entityClass = User.class,
                    method = SynchronizeMethod.READ,
                    entityIdPropertyPath = "f")})
    public void testMany(final RequestSynchronizeByMethodParameterProperty request) {
        System.out.println(request);
    }

    @SynchronizeByMethodParameterProperty(
            entityClass = User.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "ffff")
    public void testNotFound(final RequestSynchronizeByMethodParameterProperty request) {
        System.out.println(request);
    }

}