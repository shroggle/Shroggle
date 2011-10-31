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
package com.shroggle.logic.site.item;

import com.shroggle.entity.*;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.CopierUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class ItemPosterReal implements ItemPoster {


    public void publish(DraftItem draftItem) {
        draftItem = persistance.getDraftItem(draftItem != null ? draftItem.getId() : null);// Because session may be closed.
        if (draftItem instanceof DraftGallery) {
            publishGallery(draftItem);
        } else if (draftItem instanceof DraftAdvancedSearch) {
            publishAdvancedSearch(draftItem);
        } else if (draftItem instanceof DraftManageVotes) {
            publishManageVotes(draftItem);
        } else if (draftItem instanceof DraftMenu) {
            publishMenu(draftItem);
        } else if (draftItem instanceof DraftTaxRatesUS) {
            publishTaxRates(draftItem);
        } else if (draftItem instanceof DraftContactUs) {
            publishForm(draftItem, new WorkContactUs());
        } else if (draftItem instanceof DraftRegistrationForm) {
            publishForm(draftItem, new WorkRegistrationForm());
        } else if (draftItem instanceof DraftChildSiteRegistration) {
            publishForm(draftItem, new WorkChildSiteRegistration());
        } else if (draftItem instanceof DraftCustomForm) {
            publishForm(draftItem, new WorkCustomForm());
        } else if (draftItem instanceof DraftSlideShow) {
            publishSlideShow(draftItem);
        } else if (draftItem instanceof DraftPage) {

        } else if (draftItem != null) {
            final WorkItem workItem;
            final String workClass = draftItem.getClass().getName().replace("Draft", "Work");
            try {
                workItem = (WorkItem) Class.forName(workClass).newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
            publishDraftItemIfExist(draftItem, workItem);
        }
    }

    private void publishGallery(final DraftItem draftItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, new WorkGallery());
        if (hasBeenPublished) {
            final DraftGallery draftGallery = (DraftGallery) draftItem;
            final WorkGallery workGallery = (WorkGallery) new ItemManager(draftGallery).getWorkItem();

            final PaypalSettingsForGallery paypalSettingsForGalleryWork = new PaypalSettingsForGallery();
            if (((DraftGallery) draftItem).getPaypalSettings() != null) {
                ItemCopierUtil.copyProperties(((DraftGallery) draftItem).getPaypalSettings(), paypalSettingsForGalleryWork);
            }
            workGallery.setPaypalSettings(paypalSettingsForGalleryWork);
            workGallery.setItems(new ArrayList<GalleryItem>());
            workGallery.setLabels(new ArrayList<GalleryLabel>());


            for (GalleryItem draftGalleryItem : draftGallery.getItems()) {
                final WorkGalleryItem workItem = new WorkGalleryItem();
                workItem.setAlign(draftGalleryItem.getAlign());
                workItem.setColumn(draftGalleryItem.getColumn());
                workItem.setHeight(draftGalleryItem.getHeight());
                workItem.setName(draftGalleryItem.getName());
                workItem.setPosition(draftGalleryItem.getPosition());
                workItem.setRow(draftGalleryItem.getRow());
                workItem.setWidth(draftGalleryItem.getWidth());
                workItem.getId().setFormItemId(draftGalleryItem.getItemId());
                workItem.getId().setGallery(workGallery);
                persistance.putWorkGalleryItem(workItem);
            }

            for (GalleryLabel draftLabel : draftGallery.getLabels()) {
                final WorkGalleryLabel workLabel = new WorkGalleryLabel();
                workLabel.setAlign(draftLabel.getAlign());
                workLabel.setColumn(draftLabel.getColumn());
                workLabel.setPosition(draftLabel.getPosition());
                workLabel.getId().setFormItemId(draftLabel.getItemId());
                workLabel.getId().setGallery(workGallery);
                persistance.putWorkGalleryLabel(workLabel);
            }
        }
    }

    private void publishAdvancedSearch(final DraftItem draftItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, new WorkAdvancedSearch());
        if (hasBeenPublished) {
            final DraftAdvancedSearch draftAdvancedSearch = (DraftAdvancedSearch) draftItem;
            final WorkAdvancedSearch workAdvancedSearch = (WorkAdvancedSearch) new ItemManager(draftAdvancedSearch).getWorkItem();
            for (DraftAdvancedSearchOption draftOption : draftAdvancedSearch.getAdvancedSearchOptions()) {
                final WorkAdvancedSearchOption workOption = new WorkAdvancedSearchOption();
                workOption.setAlsoSearchByFields(new ArrayList<Integer>(draftOption.getAlsoSearchByFields()));
                workOption.setDisplayType(draftOption.getDisplayType());
                workOption.setFieldLabel(draftOption.getFieldLabel());
                workOption.setFormItemId(draftOption.getFormItemId());
                workOption.setPosition(draftOption.getPosition());
                workOption.setOptionCriteria(new ArrayList<String>(draftOption.getOptionCriteria()));
                workOption.setAdvancedSearch(workAdvancedSearch);
                workAdvancedSearch.addSearchOption(workOption);
                persistance.putWorkAdvancedSearchOption(workOption);
            }
        }
    }

    private void publishSlideShow(final DraftItem draftItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, new WorkSlideShow());
        if (hasBeenPublished) {
            final DraftSlideShow draftSlideShow = (DraftSlideShow) draftItem;
            final WorkSlideShow workSlideShow = (WorkSlideShow) new ItemManager(draftSlideShow).getWorkItem();
            for (DraftSlideShowImage image : draftSlideShow.getDraftImages()) {
                final WorkSlideShowImage workSlideShowImage = new WorkSlideShowImage();
                workSlideShowImage.setImageId(image.getImageId());
                workSlideShowImage.setImageType(image.getImageType());
                workSlideShowImage.setLinkBackGalleryWidgetId(image.getLinkBackGalleryWidgetId());
                workSlideShowImage.setPosition(image.getPosition());

                workSlideShow.addSlideShowImage(workSlideShowImage);
                persistance.putWorkSlideShowImage(workSlideShowImage);
            }
        }
    }

    private void publishManageVotes(final DraftItem draftItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, new WorkManageVotes());
        if (hasBeenPublished) {
            final DraftManageVotes draftManageVotes = (DraftManageVotes) draftItem;
            final WorkManageVotes workManageVotes = (WorkManageVotes) new ItemManager(draftManageVotes).getWorkItem();
            for (DraftManageVotesSettings draftSettings : draftManageVotes.getManageVotesGallerySettingsList()) {
                final WorkManageVotesSettings workSettings = new WorkManageVotesSettings();
                workSettings.setColorCode(draftSettings.getColorCode());
                workSettings.setCustomName(draftSettings.getCustomName());
                workSettings.setFormItemId(draftSettings.getFormItemId());
                workSettings.setGalleryCrossWidgetId(draftSettings.getGalleryCrossWidgetId());
                workSettings.setManageVotes(workManageVotes);
                workManageVotes.addGallerySettings(workSettings);
                persistance.putWorkManageVotesSettings(workSettings);
            }
        }
    }

    private void publishMenu(final DraftItem draftItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, new WorkMenu());
        if (hasBeenPublished) {
            final DraftMenu draftMenu = (DraftMenu) draftItem;
            final WorkMenu workMenu = (WorkMenu) new ItemManager(draftMenu).getWorkItem();
            final List<MenuItem> draftMenuItems = draftMenu.getMenuItems();
            MenuItemsManager.copyItemsAndAddThemToMenu(workMenu, draftMenuItems);
        }
    }

    private void publishTaxRates(final DraftItem draftItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, new WorkTaxRatesUS());
        if (hasBeenPublished) {
            final DraftTaxRatesUS draftTaxRates = (DraftTaxRatesUS) draftItem;
            final WorkTaxRatesUS workTaxRates = (WorkTaxRatesUS) new ItemManager(draftTaxRates).getWorkItem();
            for (DraftTaxRateUS draftTaxRate : draftTaxRates.getTaxRates()) {
                final WorkTaxRateUS workTaxRate = new WorkTaxRateUS();
                workTaxRate.setIncluded(draftTaxRate.isIncluded());
                workTaxRate.setState((States_US) draftTaxRate.getState());
                workTaxRate.setTaxRate(draftTaxRate.getTaxRate());

                workTaxRates.addTaxRate(workTaxRate);
                ServiceLocator.getPersistance().putTaxRate(workTaxRate);
            }
        }
    }

    private void publishForm(final DraftItem draftItem, final WorkItem newWorkItem) {
        final boolean hasBeenPublished = publishDraftItemIfExist(draftItem, newWorkItem);
        if (hasBeenPublished) {
            final DraftForm draftForm = (DraftForm) draftItem;
            final WorkForm workForm = (WorkForm) new ItemManager(draftForm).getWorkItem();

            if (draftItem instanceof DraftChildSiteRegistration) {
                final DraftChildSiteRegistration draftChildSiteRegistration = (DraftChildSiteRegistration) draftItem;
                final WorkChildSiteRegistration workChildSiteRegistration = (WorkChildSiteRegistration) workForm;
                workChildSiteRegistration.setBlueprintsId(new ArrayList<Integer>(draftChildSiteRegistration.getBlueprintsId()));
            }

            for (Filter draftFilter : draftForm.getFilters()) {
                final WorkFormFilter workFilter = new WorkFormFilter();
                CopierUtil.copyProperties(draftFilter, workFilter);
                workFilter.setWorkForm(workForm);
                persistance.putWorkFilter(workFilter);
            }

            for (FormItem draftFormItem : draftForm.getFormItems()) {
                final WorkFormItem workFormItem = new WorkFormItem();
                CopierUtil.copyProperties(draftFormItem, workFormItem);
                workFormItem.setWorkForm(workForm);
                persistance.putWorkFormItem(workFormItem);
            }
        }
    }

    private boolean publishDraftItemIfExist(final DraftItem draftItem, final WorkItem workItem) {
        if (draftItem != null) {
            new ItemManager(draftItem).removeWorkItem();
            try {
                ItemCopierUtil.copyProperties(draftItem, workItem);
                ItemCopierUtil.copyStyles(draftItem, workItem);
                workItem.setSiteId(draftItem.getSiteId());
                persistance.putItem(workItem);
            } catch (Exception e) {
                logger.warning("Unable to copy properties from draft item to work one.");
            }
            return true;
        } else {
            return false;
        }
    }


    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}