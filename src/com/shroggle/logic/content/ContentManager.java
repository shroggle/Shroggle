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

package com.shroggle.logic.content;

import com.shroggle.entity.Content;
import com.shroggle.entity.ContentId;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
public class ContentManager {

    public Content getValue(final ContentId contentId) throws Exception {
        if (contentId == null) {
            throw new NullPointerException("Can't get content by null id!");
        }
        return persistance.getContentById(contentId);
    }

    public ContentId setValue(final ContentId contentId, final String value) throws Exception {
        if (contentId == null) {
            throw new NullPointerException("Can't set content by null id!");
        }

        if (contentId.getSelectId().length() >= 200) {
            throw new IllegalArgumentException("Can't set content by large 200 symbol id!");
        }

        final Content content = persistance.getContentById(contentId);
        if (value == null) {
            if (content != null) {
                persistanceTransaction.execute(new Runnable() {

                    @Override
                    public void run() {
                        persistance.removeContent(content);
                    }

                });
            }
            return null;
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                if (contentId.getClientId() == null) {
                    contentId.setClientId(persistance.getMaxContentClientId() + 1);
                }

                String limitedValue = value;
                if (limitedValue.length() > 500000) limitedValue = value.substring(0, 500000);

                if (content == null) {
                    final Content newContent = new Content();
                    newContent.setId(contentId);
                    newContent.setValue(limitedValue);
                    newContent.setDate(new Date());
                    persistance.putContent(newContent);
                } else {
                    content.setDate(new Date());
                    content.setValue(limitedValue);
                }
            }

        });
        return contentId;
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}