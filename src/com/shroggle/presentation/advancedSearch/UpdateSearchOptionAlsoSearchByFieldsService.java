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

import com.shroggle.entity.DraftAdvancedSearchOption;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class UpdateSearchOptionAlsoSearchByFieldsService {

    @RemoteMethod
    public void execute(final int searchOptionId, final int addedAlsoSearchByFieldsFieldId) {
        final DraftAdvancedSearchOption option =
                ServiceLocator.getPersistance().getAdvancedSearchOptionById(searchOptionId);

        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {

            @Override
            public void run() {
                option.addAlsoSearchByFields(addedAlsoSearchByFieldsFieldId);
            }
        });
    }

}