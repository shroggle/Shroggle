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

package com.shroggle.util.process.synchronize;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Stasuk Artem
 */
final class SynchronizeEntityParent {

    public static SynchronizeEntityParentResult next(final SynchronizeEntityParentResult previousResult) {
        if (previousResult.getEntityId() == null) return null;
        final Persistance persistance = ServiceLocator.getPersistance();
        if (previousResult.getEntityClass() == DraftBlog.class) {
            final DraftBlog blog = persistance.getDraftItem((Integer) previousResult.getEntityId());
            if (blog != null) return createResult(Site.class, blog.getSiteId());
        } else if (previousResult.getEntityClass() == DraftForum.class) {
            final DraftForum forum = persistance.getDraftItem((Integer) previousResult.getEntityId());
            if (forum != null) return createResult(Site.class, forum.getSiteId());
        } else if (previousResult.getEntityClass() == Page.class) {
            final Page page = persistance.getPage((Integer) previousResult.getEntityId());
            if (page != null) return createResult(Site.class, page.getSite().getSiteId());
        } else if (previousResult.getEntityClass() == Widget.class) {
            final Widget widget = persistance.getWidget((Integer) previousResult.getEntityId());
            if (widget != null) return createResult(Page.class, widget.getPage().getPageId());
        }
        return null;
    }

    private static SynchronizeEntityParentResult createResult(final Class entityClass, final Object entityId) {
        return new SynchronizeEntityParentResult(entityClass, entityId);
    }

}