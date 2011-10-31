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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.*;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutor;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stasuk Artem
 */
public class CopierStackExecutorItem implements CopierStackExecutor {

    public CopierStackExecutorItem(
            final ItemNaming itemNaming, final int siteId,
            final Map<Integer, Integer> pageIds) {
        this.itemNaming = itemNaming;
        this.siteId = siteId;
        this.pageIds = pageIds;
    }

    @Override
    public void copy(final CopierStack stack, final Object original) {
        final DraftItem draftItem = (DraftItem) original;
        try {
            final DraftItem copiedSiteItem = draftItem.getClass().newInstance();
            executeInternal(stack, draftItem, copiedSiteItem);
        } catch (final Exception exception) {
            logger.log(Level.WARNING, "Unable to copy draft item = " + draftItem
                    + " with id = " + draftItem.getId(), exception);
        }
    }

    private void executeInternal(final CopierStack stack, final Item original, final Item copy) throws Exception {
        ItemCopierUtil.copyProperties(original, copy);
        copy.setSiteId(siteId);
        copy.setId(0);
        copy.setName(itemNaming.execute(copy.getItemType(), siteId, copy.getName(), false));
        persistance.putItem(copy);
        stack.add(original, copy);

        ItemCopierUtil.copyStyles(original, copy);

        if (original instanceof DraftSlideShow) {
            copySlideShow(stack, original, copy);
        }

        if (original instanceof DraftTaxRatesUS) {
            copyTaxRates(original, copy);
        }

        if (original instanceof DraftForm) {
            copyForm(stack, original, copy);
        }

        if (original instanceof DraftMenu) {
            final DraftMenu copyMenu = (DraftMenu) copy;
            final DraftMenu draftMenu = (DraftMenu) original;

            ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                    pageIds, draftMenu.getMenuItems(), copyMenu, null);
        }

        if (original instanceof DraftImage) {
            copyImage(original);
        }

        if (original instanceof DraftGallery) {
            copyGallery(stack, original, copy);
        }
    }

    private void copyGallery(final CopierStack stack, final Item original, final Item copy) {
        final DraftGallery gallery = (DraftGallery) original;
        final DraftGallery copiedGallery = (DraftGallery) copy;

        int formId = gallery.getFormId1();
        DraftFormFilter copiedFormFilter = null;
        final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
        if (formFilter != null) {
            formId = formFilter.getForm().getId();
            copiedFormFilter = ItemCopierUtil.copyFormFilter(formFilter);
            persistance.putFormFilter(copiedFormFilter);
            copiedGallery.setFormFilterId(copiedFormFilter.getFormFilterId());
            copiedGallery.setFormId1(copiedFormFilter.getForm().getId());
        }

        final DraftForm form = persistance.getFormById(formId);
        final DraftForm copiedForm = stack.copy(form);
        copiedGallery.setFormId1(copiedForm.getFormId());

        final List<GalleryLabel> copiedLabels = new ArrayList<GalleryLabel>();
        for (final GalleryLabel label : gallery.getLabels()) {
            final DraftGalleryLabel copiedLabel = new DraftGalleryLabel();
            ItemCopierUtil.copyProperties(label, copiedLabel);

            final DraftFormItem formItem = persistance.getFormItemById(label.getItemId());
            copiedLabel.getId().setFormItemId(stack.copy(formItem).getFormItemId());

            copiedLabel.getId().setGallery(copiedGallery);
            copiedLabels.add(copiedLabel);
            persistance.putGalleryLabel(copiedLabel);
        }
        copiedGallery.setLabels(copiedLabels);

        final List<GalleryItem> copiedItems = new ArrayList<GalleryItem>();
        for (final GalleryItem item : gallery.getItems()) {
            final DraftGalleryItem copiedItem = new DraftGalleryItem();
            ItemCopierUtil.copyProperties(item, copiedItem);

            final DraftFormItem formItem = persistance.getFormItemById(item.getItemId());
            copiedItem.getId().setFormItemId(stack.copy(formItem).getFormItemId());

            copiedItem.getId().setGallery(copiedGallery);
            copiedItems.add(copiedItem);
            persistance.putGalleryItem(copiedItem);
        }
        copiedGallery.setItems(copiedItems);

        if (copiedFormFilter != null) {
            copiedForm.addFilter(copiedFormFilter);
            for (final DraftFormFilterRule filterRule : formFilter.getRules()) {
                final DraftFormFilterRule copiedFilterRule = ItemCopierUtil.copyFilterRule(filterRule);
                copiedFormFilter.addRule(copiedFilterRule);

                final DraftFormItem formItem = persistance.getFormItemById(filterRule.getFormItemId());
                copiedFilterRule.setFormItemId(stack.copy(formItem).getFormItemId());
                persistance.putFormFilterRule(copiedFilterRule);
            }
        }
    }

    private void copyForm(final CopierStack stack, final Item original, final Item copy) {
        final DraftForm copiedForm = (DraftForm) copy;
        final DraftForm form = (DraftForm) original;
        for (final DraftFormItem item : form.getDraftFormItems()) {
            final DraftFormItem copiedItem = copyFormItem(item);
            copiedForm.addFormItem(copiedItem);
            persistance.putFormItem(copiedItem);
            stack.add(item, copiedItem);
        }
    }

    private void copySlideShow(final CopierStack stack, final Item original, final Item copy) {
        final DraftSlideShow copiedSlideShow = (DraftSlideShow) copy;
        final DraftSlideShow slideShow = (DraftSlideShow) original;
        for (final SlideShowImage image : slideShow.getImages()) {
            final DraftSlideShowImage copiedImage = copySlideShowImage((DraftSlideShowImage) image);
            copiedSlideShow.addSlideShowImage(copiedImage);
            persistance.putSlideShowImage(copiedImage);
            stack.add(image, copiedImage);
        }
    }

    private void copyTaxRates(Item original, Item copy) {
        final DraftTaxRatesUS copiedTaxRates = (DraftTaxRatesUS) copy;
        final DraftTaxRatesUS taxRates = (DraftTaxRatesUS) original;
        for (final DraftTaxRateUS oldTaxRate : taxRates.getTaxRates()) {
            final DraftTaxRateUS copiedTaxRate = new DraftTaxRateUS();
            CopierUtil.copyProperties(oldTaxRate, copiedTaxRate, "Id", "TaxRates");
            copiedTaxRates.addTaxRate(copiedTaxRate);
            persistance.putTaxRate(copiedTaxRate);
        }
    }

    private void copyImage(Item original) {
        final DraftImage draftImage = (DraftImage) original;

        final DraftImage copyDraftImage = new DraftImage();
        ItemCopierUtil.copyProperties(draftImage, copyDraftImage);
        ItemCopierUtil.copyStyles(draftImage, copyDraftImage);

        copyDraftImage.setId(-1);
        copyDraftImage.setSiteId(siteId);
        persistance.putItem(copyDraftImage);

        final Image image = persistance.getImageById(draftImage.getImageId());
        final Image rollOverImage = persistance.getImageById(draftImage.getRollOverImageId());
        if (image != null) {
            final Image copyImage = ItemCopierUtil.copyImage(image);
            copyImage.setSiteId(siteId);
            persistance.putImage(copyImage);
            try {
                fileSystem.setResourceStream(copyImage, fileSystem.getResourceStream(image));
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can`t set image stream for widget image", exception);
            }
            copyDraftImage.setImageId(copyImage.getImageId());
            if (image == rollOverImage) {
                copyDraftImage.setRollOverImageId(copyImage.getImageId());
            }
        }

        if (rollOverImage != null && image != rollOverImage) {
            Image newImage = ItemCopierUtil.copyImage(rollOverImage);
            newImage.setSiteId(siteId);
            persistance.putImage(newImage);
            fileSystem.setResourceStream(newImage, fileSystem.getResourceStream(rollOverImage));
            copyDraftImage.setRollOverImageId(newImage.getImageId());
        }
    }

    private DraftFormItem copyFormItem(final DraftFormItem formItem) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "FormItemId", "Form"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        final CopierWraper<DraftFormItem> wraper = new CopierWraper<DraftFormItem>(formItem);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    private DraftSlideShowImage copySlideShowImage(final DraftSlideShowImage slideShowImage) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "SlideShowImageId", "SlideShow"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        final CopierWraper<DraftSlideShowImage> wraper = new CopierWraper<DraftSlideShowImage>(slideShowImage);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    private final ItemNaming itemNaming;
    private final int siteId;
    private final Map<Integer, Integer> pageIds;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Logger logger = Logger.getLogger(CopierStackExecutorItem.class.getName());

}