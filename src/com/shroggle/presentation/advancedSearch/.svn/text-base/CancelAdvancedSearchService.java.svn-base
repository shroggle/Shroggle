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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.entity.DraftForm;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class CancelAdvancedSearchService {

    @RemoteMethod
    public void removeDefaultForm(final int defaultFormId) {
        final DraftForm form = persistance.getFormById(defaultFormId);

        if (form == null) {
            throw new FormNotFoundException(
                    "Cannot find just created default form for advanced search by Id = " + defaultFormId);
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                persistance.removeDraftItem(form);
            }
        });
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
