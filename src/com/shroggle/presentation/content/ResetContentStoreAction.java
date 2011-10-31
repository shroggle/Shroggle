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

package com.shroggle.presentation.content;

import com.shroggle.entity.Content;
import com.shroggle.entity.ContentId;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/resetContentStore.action")
public class ResetContentStoreAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final Content content = persistance.getContentById(contentId);
                if (content != null) {
                    persistance.removeContent(content);
                }
            }

        });
        return resolutionCreator.forwardToUrl("/WEB-INF/pages/resetContentStore.jsp");
    }

    public ContentId getContentId() {
        return contentId;
    }

    public void setContentId(ContentId contentId) {
        this.contentId = contentId;
    }

    private ContentId contentId = new ContentId();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}