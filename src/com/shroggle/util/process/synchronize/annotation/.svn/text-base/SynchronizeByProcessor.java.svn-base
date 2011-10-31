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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 15 вер 2008
 */
class SynchronizeByProcessor {

    public SynchronizeByCreator getCreator(
            final Method method, final Object object, final Object... parameters) {
        if (method == null) {
            throw new UnsupportedOperationException(
                    "Can't create syncronize by null object method!");
        }
        if (object == null) {
            throw new UnsupportedOperationException(
                    "Can't create synhronize by null object!");
        }

        final List<SynchronizeByCreator> creators = new ArrayList<SynchronizeByCreator>();
        creators.addAll(creatorsByClassProperties(method, object));
        creators.addAll(creatorsByMethodParameters(method));
        creators.addAll(creatorsByMethodParameterPropertyes(method, parameters));
        final SynchronizeByCreator creator = creatorByAllEntity(method);
        if (creator != null) creators.add(creator);

        if (creators.isEmpty()) return null;
        return new SynchronizeByCreatorComposit(creators);
    }

    private static List<SynchronizeByCreator> creatorsByMethodParameterPropertyes(
            final Method method, final Object... parameters) {
        List<SynchronizeByCreator> result = new ArrayList<SynchronizeByCreator>();
        final List<SynchronizeByMethodParameterProperty> all =
                new ArrayList<SynchronizeByMethodParameterProperty>();

        final SynchronizeByMethodParameterProperty one =
                method.getAnnotation(SynchronizeByMethodParameterProperty.class);
        if (one != null) all.add(one);

        final SynchronizeByMethodParameterProperties many =
                method.getAnnotation(SynchronizeByMethodParameterProperties.class);
        if (many != null) all.addAll(Arrays.asList(many.value()));

        for (final SynchronizeByMethodParameterProperty by : all) {
            result.add(new SynchronizeByCreatorMethodParameterProperty(by, parameters));
        }
        return result;
    }

    private static SynchronizeByCreator creatorByAllEntity(final Method method) {
        final SynchronizeByAllEntity one = method.getAnnotation(SynchronizeByAllEntity.class);
        if (one != null) {
            return new SynchronizeByCreatorAllEntity(one.entityClass());
        }
        return null;
    }

    private static List<SynchronizeByCreator> creatorsByMethodParameters(final Method method) {
        final List<SynchronizeByCreator> requests = new ArrayList<SynchronizeByCreator>();
        final List<SynchronizeByMethodParameter> all = new ArrayList<SynchronizeByMethodParameter>();

        final SynchronizeByMethodParameter one =
                method.getAnnotation(SynchronizeByMethodParameter.class);
        if (one != null) all.add(one);

        final SynchronizeByMethodParameters many =
                method.getAnnotation(SynchronizeByMethodParameters.class);
        if (many != null) all.addAll(Arrays.asList(many.value()));

        for (final SynchronizeByMethodParameter temp : all) {
            requests.add(new SynchronizeByCreatorMethodParameter(temp));
        }
        return requests;
    }

    private static List<SynchronizeByCreator> creatorsByClassProperties(
            final Method method, final Object object) {
        final List<SynchronizeByCreator> requests = new ArrayList<SynchronizeByCreator>();
        final List<SynchronizeByClassProperty> all = new ArrayList<SynchronizeByClassProperty>();

        final SynchronizeByClassProperty one = method.getAnnotation(SynchronizeByClassProperty.class);
        if (one != null) all.add(one);

        final SynchronizeByClassProperties many = method.getAnnotation(SynchronizeByClassProperties.class);
        if (many != null) all.addAll(Arrays.asList(many.value()));

        for (final SynchronizeByClassProperty temp : all) {
            requests.add(new SynchronizeByCreatorClassParameter(object, temp));
        }
        return requests;
    }

}
