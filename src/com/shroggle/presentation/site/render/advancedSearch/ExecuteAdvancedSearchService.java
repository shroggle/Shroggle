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

package com.shroggle.presentation.site.render.advancedSearch;

import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.advancedSearch.AdvancedSearchManager;
import com.shroggle.logic.advancedSearch.AdvancedSearchHelper;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.gallery.ShowGalleryUtils;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ExecuteAdvancedSearchService extends AbstractService {

    @RemoteMethod
    public ExecuteAdvancedSearchResponse execute(final ExecuteAdvancedSearchRequest request) throws IOException, ServletException {
        final DraftAdvancedSearch realAdvancedSearch = persistance.getDraftItem(request.getAdvancedSearchId());

        if (realAdvancedSearch == null) {
            throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + request.getAdvancedSearchId());
        }

        final Widget widget = persistance.getWidget(request.getWidgetId());

        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by Id=" + request.getWidgetId());
        }

        /* Modifying advanced search in request */
        final DraftAdvancedSearch existingAdvancedSearchRequest = contextStorage.get().getAdvancedSearchRequestById(request.getAdvancedSearchId());
        if (existingAdvancedSearchRequest != null) {
            addArtificialSearchOptionIntoAdvancedSearch(existingAdvancedSearchRequest, realAdvancedSearch,
                    request.getAdvancedSearchOptionId(), request.getSearchOptionCriteriaList());
        } else {
            final DraftAdvancedSearch artificialAdvancedSearch =
                    new AdvancedSearchHelper().createArtificialAdvancedSearch(realAdvancedSearch);

            addArtificialSearchOptionIntoAdvancedSearch(artificialAdvancedSearch, realAdvancedSearch,
                    request.getAdvancedSearchOptionId(), request.getSearchOptionCriteriaList());

            contextStorage.get().setAdvancedSearchRequest(artificialAdvancedSearch);
        }

        /* Executing search */
        final DraftGallery draftGallery = persistance.getDraftItem(realAdvancedSearch.getGalleryId());
        final String galleryHtml = new ShowGalleryUtils(request.getSiteShowOption()).
                createGalleryInnerHtml(draftGallery, widget, createRenderContext(false));

        /* Reloading search criteria results numbers */
        final DraftGallery gallery = ServiceLocator.getPersistance().getGalleryById(realAdvancedSearch.getGalleryId());
        for (SearchCriteriaResultsNumber criteriaResultsNumberToReload : request.getCriteriaResultsNumberToReloadList()) {
            criteriaResultsNumberToReload.setResultsNumber(ServiceLocator.getAdvancedSearchResultsNumberCache().getResultsNumber(
                    realAdvancedSearch.getId(), realAdvancedSearch.getGalleryId(), criteriaResultsNumberToReload.getOptionId(),
                    criteriaResultsNumberToReload.getCriteria(), gallery.getCurrentFilledFormHashCodes(),
                    gallery.getFullFilledFormHashCodes(), request.getSiteShowOption()));
        }

        /* Forming response */
        final ExecuteAdvancedSearchResponse response = new ExecuteAdvancedSearchResponse();
        response.setGalleryHtml(galleryHtml);
        response.setResultsNumberList(request.getCriteriaResultsNumberToReloadList());

        return response;
    }

    protected void addArtificialSearchOptionIntoAdvancedSearch(final DraftAdvancedSearch artificialAdvancedSearch,
                                                               final DraftAdvancedSearch realAdvancedSearch,
                                                               final int advancedSearchOptionId,
                                                               final List<String> searchCriterias) {
        DraftAdvancedSearchOption artificialSearchOption =
                new AdvancedSearchManager(artificialAdvancedSearch).getSearchOptionById(advancedSearchOptionId);

        if (artificialSearchOption == null) {
            DraftAdvancedSearchOption realSearchOption =
                    new AdvancedSearchManager(realAdvancedSearch).getSearchOptionById(advancedSearchOptionId);

            artificialSearchOption = new AdvancedSearchHelper().addArtificialOption(artificialAdvancedSearch, realSearchOption);
        }

        final boolean searchCriteriasShouldBeCleaned = searchCriterias != null && searchCriterias.size() == 1
                && searchCriterias.get(0).equals("null");
        if (!searchCriteriasShouldBeCleaned) {
            artificialSearchOption.setOptionCriteria(searchCriterias);
        } else {
            artificialSearchOption.setOptionCriteria(new ArrayList<String>());
        }
    }

    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
