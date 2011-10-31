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
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ItemCopier.copy
 * BlueprintCopier.copy
 *
 * @author Stasuk Artem
 */
public class ItemCopierSimple implements ItemCopier {

    public ItemCopierSimple() {
    }

    @Override
    public ItemCopyResult execute(final ItemCopierContext context, final DraftItem draftItem, final Widget widget) {
        try {
            final DraftItem copiedSiteItem = draftItem.getClass().newInstance();
            final Map<Integer, Integer> formItemsIdWithCopiedEquivalents = executeInternal(context, draftItem, copiedSiteItem);
            return new ItemCopyResult(copiedSiteItem, true, formItemsIdWithCopiedEquivalents);
        } catch (final Exception e) {
            logger.log(Level.WARNING, "Unable to copy draft item with id = " + draftItem.getId(), e);
            return null;
        }
    }

    private Map<Integer, Integer> executeInternal(
            final ItemCopierContext context, final Item original, final Item copy) throws Exception {
        ItemCopierUtil.copyProperties(original, copy);
        ItemCopierUtil.copyStyles(original, copy);
        copy.setSiteId(context.getCopiedSite().getSiteId());
        copy.setId(0);
        copy.setName(context.getItemNaming().execute(
                copy.getItemType(), context.getCopiedSite().getSiteId(), copy.getName(), false));
        persistance.putItem(copy);

        ItemCopierUtil.copyStyles(original, copy);

        if (original instanceof DraftTaxRatesUS) {
            final DraftTaxRatesUS copiedTaxRates = (DraftTaxRatesUS) copy;
            final DraftTaxRatesUS taxRates = (DraftTaxRatesUS) original;
            for (final DraftTaxRateUS oldTaxRate : taxRates.getTaxRates()) {
                final DraftTaxRateUS copiedTaxRate = new DraftTaxRateUS();
                CopierUtil.copyProperties(oldTaxRate, copiedTaxRate, "Id", "TaxRates");
                copiedTaxRates.addTaxRate(copiedTaxRate);
                persistance.putTaxRate(copiedTaxRate);
            }
            return Collections.emptyMap();
        }

        if (original instanceof DraftBlog) {
            final DraftBlog copiedBlog = (DraftBlog) copy;
            for (final BlogPost blogPost : ((DraftBlog) original).getBlogPosts()) {
                final BlogPost copiedBlogPost = new BlogPost();
                CopierUtil.copyProperties(blogPost, copiedBlogPost, "BlogPostId", "Blog", "Comments");
                copiedBlog.addBlogPost(copiedBlogPost);
                persistance.putBlogPost(copiedBlogPost);

                copyComments(blogPost.getComments(), copiedBlogPost, null);
            }
            return Collections.emptyMap();
        }

        if (original instanceof DraftSlideShow) {
            final DraftSlideShow copiedSlideShow = (DraftSlideShow) copy;
            final DraftSlideShow slideShow = (DraftSlideShow) original;
            for (final SlideShowImage slideShowImage : slideShow.getImages()) {
                final DraftSlideShowImage copiedImage = copySlideShowImage((DraftSlideShowImage) slideShowImage);
                copiedSlideShow.addSlideShowImage(copiedImage);
                persistance.putSlideShowImage(copiedImage);
            }
            return Collections.emptyMap();
        }

        if (original instanceof DraftForm) {
            final DraftForm copiedForm = (DraftForm) copy;
            final DraftForm form = (DraftForm) original;
            final Map<Integer, Integer> itemIds = new HashMap<Integer, Integer>();
            for (final DraftFormItem item : form.getDraftFormItems()) {
                final DraftFormItem copiedItem = copyFormItem(item);
                copiedForm.addFormItem(copiedItem);
                persistance.putFormItem(copiedItem);
                itemIds.put(item.getFormItemId(), copiedItem.getFormItemId());
            }
            return itemIds;
        }

        if (original instanceof DraftMenu) {
            final DraftMenu draftMenu = (DraftMenu) original;
            final DraftMenu copiedDraftMenu = (DraftMenu) copy;
            CopierUtil.copyProperties(draftMenu, copiedDraftMenu, "Id", "SiteId", "MenuItems");
            copiedDraftMenu.setSiteId(context.getCopiedSite().getSiteId());
            ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                    context.getPageToCopiedPageIds(), draftMenu.getMenuItems(), copiedDraftMenu, null);
        }

        if (original instanceof DraftImage) {
            final DraftImage draftImage = (DraftImage) original;
            final DraftImage copyDraftImage = (DraftImage) copy;
            final Image image = persistance.getImageById(draftImage.getImageId());
            final Image rollOverImage = persistance.getImageById(draftImage.getRollOverImageId());
            if (image != null) {
                final Image copyImage = ItemCopierUtil.copyImage(image);
                copyImage.setSiteId(context.getCopiedSite().getSiteId());
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
                newImage.setSiteId(context.getCopiedSite().getSiteId());
                persistance.putImage(newImage);
                fileSystem.setResourceStream(newImage, fileSystem.getResourceStream(rollOverImage));
                copyDraftImage.setRollOverImageId(newImage.getImageId());
            }
        }

        if (original instanceof DraftGallery) {
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
            final DraftForm copiedForm = form.getClass().newInstance();
            final Map<Integer, Integer> formItemIds = executeInternal(context, form, copiedForm);
            copiedGallery.setFormId1(copiedForm.getFormId());


            final List<GalleryLabel> copiedLabels = new ArrayList<GalleryLabel>();
            for (final GalleryLabel label : ((DraftGallery) original).getLabels()) {
                if (formItemIds.containsKey(label.getId().getFormItemId())) {
                    final DraftGalleryLabel copiedLabel = new DraftGalleryLabel();
                    ItemCopierUtil.copyProperties(label, copiedLabel);
                    copiedLabel.getId().setFormItemId(formItemIds.get(label.getId().getFormItemId()));
                    copiedLabel.getId().setGallery(copiedGallery);
                    copiedLabels.add(copiedLabel);
                    persistance.putGalleryLabel(copiedLabel);
                }
            }
            copiedGallery.setLabels(copiedLabels);

            final List<GalleryItem> copiedItems = new ArrayList<GalleryItem>();
            for (final GalleryItem item : ((DraftGallery) original).getItems()) {
                if (formItemIds.containsKey(item.getId().getFormItemId())) {
                    final DraftGalleryItem copiedItem = new DraftGalleryItem();
                    ItemCopierUtil.copyProperties(item, copiedItem);
                    copiedItem.getId().setFormItemId(formItemIds.get(item.getId().getFormItemId()));
                    copiedItem.getId().setGallery(copiedGallery);
                    copiedItems.add(copiedItem);
                    persistance.putGalleryItem(copiedItem);
                }
            }
            copiedGallery.setItems(copiedItems);

            if (copiedFormFilter != null) {
                copiedForm.addFilter(copiedFormFilter);
                for (final DraftFormFilterRule filterRule : formFilter.getRules()) {
                    final DraftFormFilterRule copiedFilterRule = ItemCopierUtil.copyFilterRule(filterRule);
                    copiedFormFilter.addRule(copiedFilterRule);
                    copiedFilterRule.setFormItemId(formItemIds.get(filterRule.getFormItemId()));
                    persistance.putFormFilterRule(copiedFilterRule);
                }
            }
        }
        return Collections.emptyMap();
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

    private void copyComments(final List<Comment> comments, final BlogPost parentBlogPost, final Comment parentComment) {
        if (comments == null || comments.isEmpty()) {
            return;
        }
        for (final Comment comment : comments) {
            final Comment copiedComment = new Comment();
            CopierUtil.copyProperties(comment, copiedComment, "CommentId", "BlogPost", "AnswerComments", "QuestionComment");
            if (parentBlogPost != null) {
                parentBlogPost.addComment(copiedComment);
            }
            if (parentComment != null) {
                parentComment.addChildComment(copiedComment);
            }
            persistance.putComment(copiedComment);
            copyComments(comment.getAnswerComments(), null, copiedComment);
        }
    }

    protected final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Logger logger = Logger.getLogger(ItemCopierSimple.class.getName());

}
