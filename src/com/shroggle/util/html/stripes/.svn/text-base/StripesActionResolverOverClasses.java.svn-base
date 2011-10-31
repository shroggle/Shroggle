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
package com.shroggle.util.html.stripes;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.reflection.ClassesFilterAnd;
import com.shroggle.util.reflection.ClassesFilterClassAnnotations;
import com.shroggle.util.reflection.ClassesFilterPackageName;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.NameBasedActionResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class StripesActionResolverOverClasses extends NameBasedActionResolver {

    @SuppressWarnings({"UnnecessaryLocalVariable", "unchecked"})
    @Override
    protected Set<Class<? extends ActionBean>> findClasses() {
        final HashSet classes = new HashSet<Class>(
                ServiceLocator.getClasses().get(
                        new ClassesFilterAnd(
                                new ClassesFilterPackageName("com.shroggle"),
                                new ClassesFilterClassAnnotations(UrlBinding.class))));
        return classes;
    }

}
