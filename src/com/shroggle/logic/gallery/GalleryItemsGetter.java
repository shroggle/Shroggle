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

package com.shroggle.logic.gallery;

import com.shroggle.entity.*;
import com.shroggle.exception.UserException;
import com.shroggle.logic.advancedSearch.AdvancedSearchManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.filter.FormFilterManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class GalleryItemsGetter {

    public List<FilledForm> getFilledForms(final Gallery gallery, final DraftItem draftItem, final SiteShowOption siteShowOption) {
        //Common gallery filtering.
        List<FilledForm> filledForms = filterForms(gallery, siteShowOption);

        gallery.setFullFilledFormHashCodes(FilledFormManager.generateFilledFormHashCodes(filledForms));

        if (draftItem != null && draftItem instanceof DraftAdvancedSearch) {
            //Filtering filled forms if gallery is shown from advanced search widget.
            filledForms = filterFormsByAdvancedSearch(filledForms, draftItem.getId());
        }

        gallery.setCurrentFilledFormHashCodes(FilledFormManager.generateFilledFormHashCodes(filledForms));

        return filledForms;
    }

    public List<FilledForm> getFilledForms(final DraftGallery gallery, final DraftAdvancedSearch advancedSearch, SiteShowOption siteShowOption) {
        //Common gallery filtering.
        List<FilledForm> filledForms = filterForms(gallery, siteShowOption);

        //Filtering filled forms by advanced search.
        filledForms = filterFormsByAdvancedSearch(filledForms, advancedSearch);

        return filledForms;
    }

    private List<FilledForm> filterForms(Gallery gallery, final SiteShowOption siteShowOption) {
        DraftFormFilter formFilter = null;
        if (gallery.getFormFilterId() != null) {
            formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
        }

        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        if (gallery.isShowOnlyMyRecords()) {
            if (isChildSiteRegistrationForm(gallery)) {
                final Integer siteId = contextStorage.get().getSiteId();
                if (siteId != null) {
                    final Site site = persistance.getSite(siteId);
                    if (site.getChildSiteSettings() != null) {
                        final int filledFormId = site.getChildSiteFilledFormId();
                        final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
                        if (filledForm != null) {
                            if (filledForm.getFormId() == gallery.getFormId1()
                                    || (formFilter != null && formFilter.getForm().getId() == filledForm.getFormId())) {
                                filledForms.add(filledForm);
                            }
                        }
                    }
                }
            } else {
                if (formFilter != null) {
                    filledForms = new FormFilterManager(formFilter).getFilledForms();
                } else {
                    filledForms = persistance.getFilledFormsByFormId(gallery.getFormId1());
                }

                try {
                    final UserManager userManager = new UsersManager().getLogined();
                    final Iterator<FilledForm> filledFormIterator = filledForms.iterator();
                    while (filledFormIterator.hasNext()) {
                        final FilledForm filledForm = filledFormIterator.next();
                        if (filledForm.getUser() != userManager.getUser()) {
                            filledFormIterator.remove();
                        }
                    }
                } catch (final UserException exception) {
                    filledForms.clear();
                }
            }
        } else {
            if (formFilter != null) {
                filledForms = new FormFilterManager(formFilter).getFilledForms();
            } else {
                filledForms = persistance.getFilledFormsByFormId(gallery.getFormId1());
            }
        }

        excludeHiddenFilledForms(filledForms);

        if (isChildSiteRegistrationForm(gallery)) {
            excludeNotUsedChildForms(gallery, filledForms, siteShowOption);
        }

        excludeWatchedVideos(filledForms, gallery.getId());

        return filledForms;
    }

    private List<FilledForm> filterFormsByAdvancedSearch(final List<FilledForm> filledForms, final Integer advancedSearchId) {
        final DraftAdvancedSearch advancedSearch =
                contextStorage.get().getAdvancedSearchRequestById(advancedSearchId);
        if (advancedSearch != null) {
            return new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(filledForms);
        }

        return filledForms;
    }

    private List<FilledForm> filterFormsByAdvancedSearch(final List<FilledForm> filledForms, final DraftAdvancedSearch advancedSearch) {
        if (advancedSearch != null) {
            return new AdvancedSearchManager(advancedSearch).getFilteredFilledForms(filledForms);
        }

        return filledForms;
    }

    private void excludeHiddenFilledForms(final List<FilledForm> filledForms) {
        final Iterator<FilledForm> iterator = filledForms.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isHidden()) {
                iterator.remove();
            }
        }
    }

    private void excludeWatchedVideos(final List<FilledForm> filledForms, final int galleryId) {
        if (contextStorage.get().isHideWatchedVideos(galleryId)) {
            try {
                final UserManager userManager = new UsersManager().getLogined();
                final VideoRangesManager rangesManager = userManager.getVideoRanges();
                final Set<Integer> filledFormIds = rangesManager.getFilledFormIds();

                final Iterator<FilledForm> iterator = filledForms.iterator();
                while (iterator.hasNext()) {
                    if (filledFormIds.contains(iterator.next().getFilledFormId())) {
                        iterator.remove();
                    }
                }
            } catch (final UserException exception) {
                // Nothing!
            }
        }
    }

    private void excludeNotUsedChildForms(final Gallery gallery, final List<FilledForm> filledForms, final SiteShowOption siteShowOption) {
        final Iterator<FilledForm> iterator = filledForms.iterator();
        while (iterator.hasNext()) {
            if (getDataWidget(gallery, iterator.next().getFilledFormId(), siteShowOption) == null) {
                iterator.remove();
            }
        }
    }

    private static Widget getDataWidget(
            final Gallery gallery, final int filledFormId, final SiteShowOption siteShowOption) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Widget dataWidget = persistance.getWidgetByCrossWidgetsId(gallery.getDataCrossWidgetId(), siteShowOption);
        Site dataWidgetSite = dataWidget != null ? dataWidget.getSite() : null;
        if (dataWidgetSite != null && dataWidgetSite.getType() != SiteType.BLUEPRINT) {
            return dataWidget;
        } else {
            final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
            ChildSiteSettings settings = persistance.getChildSiteSettingsById(filledForm != null ? filledForm.getChildSiteSettingsId() : null);
            if (settings != null) {
                Site childSite = settings.getSite();
                if (childSite != null) {
                    /**
                     * If we show gallery on work page we use only work gallery data, in other cases
                     * we can use any gallery data.
                     */
                    final List<WidgetItem> widgetGalleryDatas =
                            persistance.getGalleryDataWidgetsBySitesId(Arrays.asList(childSite.getSiteId()), SiteShowOption.getWorkOption());

                    if (widgetGalleryDatas.size() > 0) {
                        return widgetGalleryDatas.get(0);
                    }
                }
            }
            return null;
        }
    }

    private boolean isChildSiteRegistrationForm(final Gallery gallery) {
        final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
        if (formFilter != null && formFilter.getForm() != null) {
            return formFilter.getForm().getType() == FormType.CHILD_SITE_REGISTRATION;
        } else {
            final DraftForm form = persistance.getFormById(gallery.getFormId1());
            return form != null && form.getType() == FormType.CHILD_SITE_REGISTRATION;
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();

}
