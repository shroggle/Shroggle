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

package com.shroggle.logic.advancedSearch.resultsNumber;

import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.gallery.GalleryItemsGetter;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.GalleryNotFoundException;
import com.shroggle.exception.AdvancedSearchOptionNotFoundException;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class AdvancedSearchResultsNumberCache {

    private List<AdvancedSearchGalleryPair> cahcedAdvancedSearchGalleryPairs = new ArrayList<AdvancedSearchGalleryPair>();

    /**
     * @param advancedSearchId             - advanced search id for which cache search is performed.
     * @param galleryId                    - gallery id for which cache search is performed
     * @param searchOptionId               - search option id for which result number is generated.
     * @param criteria                     - criteria for which result number is generated.
     * @param currentFilledFormHashCodesFetch
     *                                     - this fetch contains only those filled forms hash codes which are
     *                                     displayed currently in advanced search.
     * @param fullFilledFormHashCodesFetch - this fetch contains all filled forms that should be displayed in adv. search.
     *                                     That param. given here only for performance because when this class is called
     *                                     then we surely know that all filled forms
     *                                     that we need were already grabbed from db, so we mustn't do this second time.
     * @param siteShowOption -
     * @return results number for current option criteria.
     */
    public int getResultsNumber(final int advancedSearchId, final int galleryId, final int searchOptionId,
                                final String criteria,
                                final List<Integer> currentFilledFormHashCodesFetch,
                                final List<Integer> fullFilledFormHashCodesFetch, final SiteShowOption siteShowOption) {
        //Firstly we need to find advanced search gallery pair in cache.
        AdvancedSearchGalleryPair advancedSearchGalleryPair =
                findAdvancedSearchGalleryPairByIds(advancedSearchId, galleryId);

        //If this pair isn't found in cache then we should initialize a new pair and put it into cache.
        if (advancedSearchGalleryPair == null) {
            advancedSearchGalleryPair = initAdvancedSearchGalleryPair(advancedSearchId, galleryId, fullFilledFormHashCodesFetch, siteShowOption);
        } else {
            //Otherwise, let's invalidate cache entries.
            advancedSearchGalleryPair =
                    invalidateCache(advancedSearchId, galleryId, advancedSearchGalleryPair, fullFilledFormHashCodesFetch, siteShowOption);
        }

        //Then we need to find cached search option in that pair
        CachedSearchCriteria currentCachedSearchCriteria = getCachedSearchOptionBySearchOptionIdAndCriterias(
                advancedSearchGalleryPair, searchOptionId, criteria);

        if (currentCachedSearchCriteria == null) {
            //If search option not found in cache. Then let's add it into cache.
            currentCachedSearchCriteria = generateCachedSearchOption(searchOptionId, criteria, galleryId, siteShowOption);
            advancedSearchGalleryPair.addCachedSearchCriteria(currentCachedSearchCriteria);
        }

        final DraftAdvancedSearch advancedSearchRequest =
                ServiceLocator.getContextStorage().get().getAdvancedSearchRequestById(advancedSearchId);
        final List<CachedSearchCriteria> selectedSearchCriteriaList = new ArrayList<CachedSearchCriteria>();
        if (advancedSearchRequest != null) {
            final List<DraftAdvancedSearchOption> selectedSearchOptions =
                    ServiceLocator.getContextStorage().get().getAdvancedSearchRequestById(advancedSearchId).getAdvancedSearchOptions();

            for (DraftAdvancedSearchOption selectedSearchOption : selectedSearchOptions) {
                for (String selectedSearchOptionCriteria : selectedSearchOption.getOptionCriteria()) {
                    final CachedSearchCriteria selectedSearchCriteria = new CachedSearchCriteria();
                    selectedSearchCriteria.setOptionId(selectedSearchOption.getAdvancedSearchOptionId());
                    selectedSearchCriteria.setCriteria(selectedSearchOptionCriteria);
                    selectedSearchCriteriaList.add(selectedSearchCriteria);
                }
            }
        }

        if (advancedSearchRequest == null || selectedSearchCriteriaList.isEmpty()) {
            // If our request is empty then just use initial filter fetches.
            final List<Integer> cachedSearchOptionFilledFormHashCodes = currentCachedSearchCriteria.getResultingHashCodes();
            int resultsNumber = 0;
            for (Integer cachedSearchOptionFilledFormHashCode : cachedSearchOptionFilledFormHashCodes) {
                if (currentFilledFormHashCodesFetch.contains(cachedSearchOptionFilledFormHashCode)) {
                    resultsNumber++;
                }
            }

            return resultsNumber;
        } else {
            //Otherwise find intersection of fetches 
            final Set<Integer> allSelectedCachedHashCodes = new HashSet<Integer>();
            final List<Integer> intersectedHashCodes = new ArrayList<Integer>();
            final List<CachedSearchCriteria> selectedCachedSearchCriteriaList = new ArrayList<CachedSearchCriteria>();
            for (CachedSearchCriteria cachedSearchCriteria : advancedSearchGalleryPair.getCachedSearchCriterias()) {
                for (CachedSearchCriteria selectedSearchCriteria : selectedSearchCriteriaList) {
                    if (selectedSearchCriteria.getCriteria().equals(cachedSearchCriteria.getCriteria())
                            && selectedSearchCriteria.getOptionId() == cachedSearchCriteria.getOptionId()
                            && cachedSearchCriteria.getOptionId() != searchOptionId) {
                        selectedCachedSearchCriteriaList.add(cachedSearchCriteria);
                        allSelectedCachedHashCodes.addAll(cachedSearchCriteria.getResultingHashCodes());
                    }
                }
            }
            selectedCachedSearchCriteriaList.add(currentCachedSearchCriteria);
            allSelectedCachedHashCodes.addAll(currentCachedSearchCriteria.getResultingHashCodes());

            gatherMultiselectFetches(selectedCachedSearchCriteriaList);

            for (Integer selectedCachedHashCode : allSelectedCachedHashCodes) {
                boolean allCriteriasContainsCodes = true;
                for (CachedSearchCriteria selectedCachedSearchCriteria : selectedCachedSearchCriteriaList) {
                    if (!selectedCachedSearchCriteria.getResultingHashCodes().contains(selectedCachedHashCode)) {
                        allCriteriasContainsCodes = false;
                    }
                }

                if (allCriteriasContainsCodes) {
                    intersectedHashCodes.add(selectedCachedHashCode);
                }
            }

            return intersectedHashCodes.size();
        }
    }

    private void gatherMultiselectFetches(List<CachedSearchCriteria> selectedCachedSearchCriteriaList) {
        final List<CachedSearchCriteria> gatheredCriterias = new ArrayList<CachedSearchCriteria>();
        final List<CachedSearchCriteria> criteriasToDelete = new ArrayList<CachedSearchCriteria>();
        for (CachedSearchCriteria selectedCachedSearchCriteria : selectedCachedSearchCriteriaList) {
            if (selectedCachedSearchCriteria.getOptionDisplayType().isPickTypeMultiSelect()) {
                final CachedSearchCriteria foundGatheredCriteria =
                        findCriteriaById(gatheredCriterias, selectedCachedSearchCriteria.getOptionId());

                if (foundGatheredCriteria != null) {
                    foundGatheredCriteria.getResultingHashCodes().addAll(selectedCachedSearchCriteria.getResultingHashCodes());
                } else {
                    CachedSearchCriteria newGatheredCriteria = new CachedSearchCriteria();
                    newGatheredCriteria.getResultingHashCodes().addAll(selectedCachedSearchCriteria.getResultingHashCodes());
                    newGatheredCriteria.setOptionId(selectedCachedSearchCriteria.getOptionId());
                    gatheredCriterias.add(newGatheredCriteria);
                }

                criteriasToDelete.add(selectedCachedSearchCriteria);
            }
        }

        selectedCachedSearchCriteriaList.removeAll(criteriasToDelete);
        selectedCachedSearchCriteriaList.addAll(gatheredCriterias);
    }

    private CachedSearchCriteria findCriteriaById(List<CachedSearchCriteria> criteriaList, final int id) {
        for (CachedSearchCriteria criteria : criteriaList) {
            if (criteria.getOptionId() == id) {
                return criteria;
            }
        }

        return null;
    }

    //NOTE, that there are three cases in which we should do this:
    //  1. If criteria option has been changed but this case is superflous cos if it so then we will insert a new
    //     criteria into cache instead of old one.
    //  2. If filled form value is changed.
    //  3. If more filled forms were added to form.
    //NOTE. We will look into performace here in the future, if it will be not satisfying then instead of invalidating
    //      full cache we will test how new filled forms or changed influences on search criterias and invalidate only some of them.
    //      to do this we will stroe a ChachedFilledForm object insted of just filled form hash codes, this object will contain
    //      filled form hash code, filled from id and filled form values. By knowing these parameter we will be able to check if
    //      one of values have or there is a new value and decrement or increment hash code count in CahcedSearchOption object.

    private AdvancedSearchGalleryPair invalidateCache(final int advancedSearchId, final int galleryId,
                                                      final AdvancedSearchGalleryPair advancedSearchGalleryPair,
                                                      final List<Integer> newFullFilledFormHashCodesFetch,
                                                      final SiteShowOption siteShowOption) {
        final int newFilledFormCount = newFullFilledFormHashCodesFetch.size();
        final int oldFilledFormCount = advancedSearchGalleryPair.getFullFilledFormsHashCodesFetch().size();

        if (newFilledFormCount != oldFilledFormCount) {
            //If form count differs then we should invalidate all cache.
            return reinitAdvancedSearchGalleryPair(advancedSearchId, galleryId, newFullFilledFormHashCodesFetch,
                    advancedSearchGalleryPair, siteShowOption);
        }

        //Checking if we should invalidate one of cached search options. e.g. some of values in some of filled forms
        //is changed.
        final Set<Integer> allHashCodes = new HashSet<Integer>();
        for (CachedSearchCriteria cachedSearchCriteria : advancedSearchGalleryPair.getCachedSearchCriterias()) {
            allHashCodes.addAll(cachedSearchCriteria.getResultingHashCodes());
        }
        if (!(allHashCodes.hashCode() != newFullFilledFormHashCodesFetch.hashCode())) {
            return reinitAdvancedSearchGalleryPair(advancedSearchId, galleryId, newFullFilledFormHashCodesFetch,
                    advancedSearchGalleryPair, siteShowOption);
        }

        return advancedSearchGalleryPair;
    }

    private CachedSearchCriteria getCachedSearchOptionBySearchOptionIdAndCriterias
            (final AdvancedSearchGalleryPair advancedSearchGalleryPair, final int searchOptionId,
             final String criteria) {
        for (CachedSearchCriteria cachedSearchCriteria : advancedSearchGalleryPair.getCachedSearchCriterias()) {
            if (cachedSearchCriteria.getOptionId() == searchOptionId &&
                    criteria.equals(cachedSearchCriteria.getCriteria())) {
                return cachedSearchCriteria;
            }
        }

        return null;
    }

    protected AdvancedSearchGalleryPair findAdvancedSearchGalleryPairByIds(final int advancedSearchId, final int galleryId) {
        for (AdvancedSearchGalleryPair advancedSearchGalleryPair : cahcedAdvancedSearchGalleryPairs) {
            if (advancedSearchGalleryPair.getAdvancedSearchId() == advancedSearchId &&
                    advancedSearchGalleryPair.getGalleryId() == galleryId) {
                return advancedSearchGalleryPair;
            }
        }

        return null;
    }

    private AdvancedSearchGalleryPair reinitAdvancedSearchGalleryPair(final int advancedSearchId, final int galleryId,
                                                                      final List<Integer> fullFilledFormHashCodesFetch,
                                                                      final AdvancedSearchGalleryPair oldAdvancedSearchGalleryPair,
                                                                      final SiteShowOption siteShowOption) {
        cahcedAdvancedSearchGalleryPairs.remove(oldAdvancedSearchGalleryPair);
        return initAdvancedSearchGalleryPair(advancedSearchId, galleryId, fullFilledFormHashCodesFetch, siteShowOption);
    }

    private AdvancedSearchGalleryPair initAdvancedSearchGalleryPair(final int advancedSearchId, final int galleryId,
                                                                    final List<Integer> fullFilledFormHashCodesFetch, final SiteShowOption siteShowOption) {
        final AdvancedSearchGalleryPair advancedSearchGalleryPair = new AdvancedSearchGalleryPair();
        advancedSearchGalleryPair.setAdvancedSearchId(advancedSearchId);
        advancedSearchGalleryPair.setGalleryId(galleryId);
        advancedSearchGalleryPair.setFullFilledFormsHashCodesFetch(fullFilledFormHashCodesFetch);

        //Getting real advanced search.
        final DraftAdvancedSearch realAdvancedSearch = ServiceLocator.getPersistance().getDraftItem(advancedSearchId);

        if (realAdvancedSearch == null) {
            throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + advancedSearchId);
        }

        //Iterating thru it's options to get hash codes of filtered filled forms by each option separately.
        for (DraftAdvancedSearchOption searchOption : realAdvancedSearch.getAdvancedSearchOptions()) {
            for (String criteria : searchOption.getOptionCriteria()) {
                advancedSearchGalleryPair.addCachedSearchCriteria(generateCachedSearchOption(searchOption, criteria, galleryId, siteShowOption));
            }
        }

        cahcedAdvancedSearchGalleryPairs.add(advancedSearchGalleryPair);
        return advancedSearchGalleryPair;
    }

    private CachedSearchCriteria generateCachedSearchOption(final int searchOptionId, final String criteria, final int galleryId,
                                                            final SiteShowOption siteShowOption) {
        final DraftAdvancedSearchOption searchOption = ServiceLocator.getPersistance().getAdvancedSearchOptionById(searchOptionId);

        if (searchOption == null) {
            throw new AdvancedSearchOptionNotFoundException("Cannot find advanced search option by Id=" + searchOptionId);
        }

        return generateCachedSearchOption(searchOption, criteria, galleryId, siteShowOption);
    }

    private CachedSearchCriteria generateCachedSearchOption(final DraftAdvancedSearchOption searchOption,
                                                            final String criteria, final int galleryId,
                                                            final SiteShowOption siteShowOption) {
        final DraftAdvancedSearch artificialAdvancedSearch = new DraftAdvancedSearch();

        final DraftGallery gallery = ServiceLocator.getPersistance().getGalleryById(galleryId);

        if (gallery == null) {
            throw new GalleryNotFoundException("Cannot find gallery by Id=" + galleryId);
        }

        final DraftAdvancedSearchOption artificialSearchOption = new DraftAdvancedSearchOption();
        artificialSearchOption.setOptionCriteria(Arrays.asList(criteria));
        artificialSearchOption.setDisplayType(searchOption.getDisplayType());
        artificialSearchOption.setFormItemId(searchOption.getFormItemId());
        artificialSearchOption.setAdvancedSearch(artificialAdvancedSearch);
        artificialAdvancedSearch.setAdvancedSearchOptions(Arrays.asList(artificialSearchOption));

        final List<FilledForm> filteredFilledForms =
                new GalleryItemsGetter().getFilledForms(gallery, artificialAdvancedSearch, siteShowOption);

        final List<Integer> filledFormHashCodes = FilledFormManager.generateFilledFormHashCodes(filteredFilledForms);

        final CachedSearchCriteria cachedSearchCriteria = new CachedSearchCriteria();
        cachedSearchCriteria.setOptionId(searchOption.getAdvancedSearchOptionId());
        cachedSearchCriteria.setOptionDisplayType(searchOption.getDisplayType());
        cachedSearchCriteria.setResultingHashCodes(filledFormHashCodes);
        cachedSearchCriteria.setCriteria(criteria);

        return cachedSearchCriteria;
    }

}
