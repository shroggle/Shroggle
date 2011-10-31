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

import com.shroggle.util.process.synchronize.SynchronizeMethod;

import java.lang.annotation.*;

/**
 * This annotation used if method need synhronzie protection and key for this synchronize
 * is a this class field
 *
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 15 вер 2008
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SynchronizeByClassProperty {

    Class entityClass();

    SynchronizeMethod method() default SynchronizeMethod.READ;

    /**
     * This path support "field in field", as example: account.accountId
     * find field with name "account" in source class and fiend in account field with name
     * "accountId"
     *
     * @return - path to field
     */
    String entityIdFieldPath();

    int deepParent() default 0;

}
