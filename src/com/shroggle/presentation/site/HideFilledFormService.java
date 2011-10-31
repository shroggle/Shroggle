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
package com.shroggle.presentation.site;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.FilledForm;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.FilledFormNotFoundException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class HideFilledFormService extends AbstractService {

    @RemoteMethod
    public void execute(final int filledFormId, final boolean hide) {
        final FilledForm filledForm = persistance.getFilledFormById(filledFormId);

        if (filledForm == null) {
            throw new FilledFormNotFoundException("Cannot find filled form by Id=" + filledFormId);
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                filledForm.setHidden(hide);
            }
        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
