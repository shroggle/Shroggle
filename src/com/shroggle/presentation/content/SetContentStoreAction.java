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
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/setContentStore.action")
public class SetContentStoreAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        value = StringUtil.trimCutIfNeed(value, 500000);

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                if (contentId.getClientId() == null) {
                    contentId.setClientId(persistance.getMaxContentClientId() + 1);
                }

                Content content = persistance.getContentById(contentId);
                if (content == null) {
                    content = new Content();
                    content.setId(contentId);
                    content.setValue(value);
                    content.setDate(new Date());
                    persistance.putContent(content);
                } else {
                    content.setValue(value);
                    content.setDate(new Date());
                }


            }

        });
        return resolutionCreator.forwardToUrl("/WEB-INF/pages/setContentStore.jsp");
    }

    public ContentId getContentId() {
        return contentId;
    }

    public void setContentId(ContentId contentId) {
        this.contentId = contentId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
    private ContentId contentId = new ContentId();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}