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

import java.lang.annotation.*;

// todo add support write method and use it in @see com.shroggle.presentation.account.items.RemoveSiteItemsAction
/**
 * This is very interseting synchronize. It synchronize on login in session user.
 * This synchronize need for correct lock method where only take user for show on pages.
 *
 * @author Artem Stasuk
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SynchronizeByLoginUser {

}