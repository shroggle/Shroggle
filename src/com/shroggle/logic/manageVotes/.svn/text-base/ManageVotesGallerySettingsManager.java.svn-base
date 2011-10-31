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
package com.shroggle.logic.manageVotes;

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.gallery.GalleryNavigationUrl;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.gallery.GalleryNavigationUrlCreator;
import com.shroggle.logic.gallery.voting.VotingStarsData;
import com.shroggle.logic.gallery.voting.VoteManager;
import com.shroggle.logic.gallery.GalleryManager;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
public class ManageVotesGallerySettingsManager {

    public ManageVotesGallerySettingsManager(ManageVotesSettings settings, final SiteShowOption siteShowOption) {
        /*------------------------------------------Fields for internal usage-----------------------------------------*/
        widgetGallery = persistance.getWidgetByCrossWidgetsId(settings.getGalleryCrossWidgetId(), siteShowOption);
        gallery = widgetGallery != null ? (DraftGallery) ((WidgetItem) widgetGallery).getDraftItem() : null;
        formItem = persistance.getFormItemById(settings.getFormItemId());
        /*------------------------------------------Fields for internal usage-----------------------------------------*/

        votingStarsData = createVotingStarsData(gallery, widgetGallery);
        customName = settings.getCustomName();
        colorCode = settings.getColorCode();
        this.siteShowOption = siteShowOption;
    }

    public String getRecordName(final int filledFormId) {
        final String defaultRecordName = international.get("defaultRecordName");
        if (formItem != null) {
            if (formItem.getFormItemName().getType() != FormItemType.FILE_UPLOAD) {
                final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
                final String recordName = FilledFormManager.getFilledFormItemValueByItemName(filledForm, formItem.getItemName());
                return StringUtil.isNullOrEmpty(recordName) ? defaultRecordName : recordName;
            }
        }
        return defaultRecordName;
    }

    public GalleryNavigationUrl createGalleryItemLink(final Widget manageVotesWidget, final int filledFormId) {
        return GalleryNavigationUrlCreator.executeForOtherWidget(gallery, widgetGallery, manageVotesWidget, filledFormId, siteShowOption);
    }

    public String createGalleryLink() {
        return new PageManager(widgetGallery.getPage(), siteShowOption).getUrl();
    }

    public static int getGalleryId(ManageVotesSettings settings, final SiteShowOption siteShowOption) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final WidgetItem widgetGallery = (WidgetItem) persistance.getWidgetByCrossWidgetsId(settings.getGalleryCrossWidgetId(), siteShowOption);
        return (widgetGallery != null && widgetGallery.getDraftItem() != null) ? widgetGallery.getDraftItem().getId() : 0;
    }

    public int getGalleryId() {
        return gallery != null ? gallery.getId() : -1;
    }

    public boolean isIncludesVotingModule() {
        return gallery != null && gallery.isIncludesVotingModule();
    }

    public String getGalleryName() {
        return gallery != null ? gallery.getName() : "";
    }

    public List<VotingStarsData> getVotingStarsData() {
        return votingStarsData;
    }

    public String getCustomName() {
        return customName;
    }

    public String getColorCode() {
        return colorCode;
    }

    /*-------------------------------------------------Private Methods------------------------------------------------*/

    private List<VotingStarsData> createVotingStarsData(final DraftGallery gallery, final Widget widgetGallery) {
        final List<Integer> filledFormsIds = new GalleryManager(gallery).getFilledFormsIds();
        final List<VotingStarsData> tempStarsDatas = VoteManager.createVotingStarsData(gallery, widgetGallery.getWidgetId(), widgetGallery.getSite().getSiteId(), filledFormsIds.toArray(new Integer[filledFormsIds.size()]));
        final List<VotingStarsData> starsDatas = new ArrayList<VotingStarsData>();
        for (VotingStarsData votingStarsData : tempStarsDatas) {
            if (votingStarsData.getVoteData().getVoteValue() != 0) {
                starsDatas.add(votingStarsData);
            }
        }
        return starsDatas;
    }

    /*-------------------------------------------------Private Methods------------------------------------------------*/
    private Persistance persistance = ServiceLocator.getPersistance();
    private International international = ServiceLocator.getInternationStorage().get("renderWidgetManageVotes", Locale.US);
    private final DraftGallery gallery;
    private final Widget widgetGallery;
    private final DraftFormItem formItem;
    private final List<VotingStarsData> votingStarsData;
    private final String customName;
    private final String colorCode;
    private final SiteShowOption siteShowOption;
}
