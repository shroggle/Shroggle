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
package com.shroggle.presentation.site.page;

import com.shroggle.entity.Page;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class DeleteDefaultPageService extends AbstractService {

    @RemoteMethod
    public void execute(final int pageId) {
        final Page page = ServiceLocator.getPersistance().getPage(pageId);

        if (page != null && !page.isSaved()) {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    new PageManager(page).remove();
                }
            });
        }
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
