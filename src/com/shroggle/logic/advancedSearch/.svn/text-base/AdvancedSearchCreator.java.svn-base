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

import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.AdvancedSearchNotUniqueNameException;
import com.shroggle.exception.AdvancedSearchNullOrEmptyNameException;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.advancedSearch.SaveAdvancedSearchRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
public class AdvancedSearchCreator {

    public AdvancedSearchCreator(final UserManager userManager) {
        this.userManager = userManager;
    }

    public void save(final SaveAdvancedSearchRequest request) {
        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (StringUtil.isNullOrEmpty(request.getName())) {
            throw new AdvancedSearchNullOrEmptyNameException(advancedSearchBundle.get("AdvancedSearchNullOrEmptyNameException"));
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftAdvancedSearch advancedSearch = persistance.getDraftItem(request.getAdvancedSearchId());
                if (advancedSearch == null || advancedSearch.getSiteId() <= 0) {
                    throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" +
                            request.getAdvancedSearchId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(advancedSearch.getSiteId());
                }

                final DraftAdvancedSearch duplicateAdvancedSearch = persistance.getDraftAdvancedSearch(
                        request.getName(), site.getSiteId());
                if (duplicateAdvancedSearch != null && duplicateAdvancedSearch != advancedSearch) {
                    throw new AdvancedSearchNotUniqueNameException(advancedSearchBundle.get("AdvancedSearchNotUniqueNameException"));
                }

                advancedSearch.setName(request.getName());

                // If default or existing form id wasn't specified in request.
                if (request.getFormId() == null) {
                    request.setFormId(new AdvancedSearchHelper().createDefaultSearchForm(site.getSiteId(), userManager).getId());
                }

                //Here gallery should always have form.
                if (request.getGalleryId() == null) {
                    request.setGalleryId(new AdvancedSearchHelper().createDefaultGallery(site.getSiteId(), request.getFormId()).getId());
                }

                advancedSearch.setGalleryId(request.getGalleryId());
                advancedSearch.setDescription(request.getHeaderComment());
                advancedSearch.setDisplayHeaderComments(request.isDisplayHeaderComment());
                advancedSearch.setAdvancedSearchOrientationType(request.getOrientationType());
                advancedSearch.setIncludeResultsNumber(request.isIncludeResultsNumber());

                //Updating or creating new search options.
                new AdvancedSearchManager(advancedSearch).createOrUpdateSearchOptions(request.getSearchOptions());
            }
        });
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private International advancedSearchBundle = ServiceLocator.getInternationStorage().get("advancedSearch", Locale.US);
    private UserManager userManager;

}
