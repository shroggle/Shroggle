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

package com.shroggle.logic.advancedSearch;

import com.shroggle.entity.DraftAdvancedSearchOption;
import com.shroggle.exception.AdvancedSearchOptionNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

/**
 * @author dmitry.solomadin
 */
public class AdvancedSearchOptionManager {

    private DraftAdvancedSearchOption advancedSearchOption;

    private AdvancedSearchOptionManager() {
    }

    public AdvancedSearchOptionManager(final DraftAdvancedSearchOption advancedSearchOption) {
        this.advancedSearchOption = advancedSearchOption;

        if (this.advancedSearchOption == null) {
            throw new AdvancedSearchOptionNotFoundException("Cannot initialize AdvancedSearchOptionManager with null search option.");
        }
    }

    public AdvancedSearchOptionManager(final int advancedSearchOptionId) {
        this.advancedSearchOption = persistance.getAdvancedSearchOptionById(advancedSearchOptionId);

        if (this.advancedSearchOption == null) {
            throw new AdvancedSearchOptionNotFoundException("Cannot find option by Id=" + advancedSearchOptionId);
        }
    }

    public void remove() {
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                persistance.removeAdvancedSearchOption(advancedSearchOption);
            }
        });
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();


}
