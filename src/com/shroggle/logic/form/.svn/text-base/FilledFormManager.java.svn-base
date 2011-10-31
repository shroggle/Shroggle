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
package com.shroggle.logic.form;

import com.shroggle.entity.*;
import com.shroggle.logic.coordinates.CoordinateCreator;
import com.shroggle.logic.gallery.GalleriesManager;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.logic.user.dashboard.keywordManager.KeywordManager;
import com.shroggle.presentation.site.FilledFormInfo;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: dmitry.solomadin
 * Date: 12.06.2009
 */
public class FilledFormManager {

    public FilledFormManager(final FilledForm filledForm) {
        if (filledForm == null) {
            throw new IllegalArgumentException("Can`t create FilledFormManager without filledForm.");
        }
        this.filledForm = filledForm;
    }

    public FilledForm copyFilledForm() {
        final DraftForm form = persistance.getFormById(filledForm.getFormId());
        return copyFilledFormInternal(form, persistance.getSite(form.getSiteId()), filledForm, null).getCopiedFilledForm();
    }

    public Map<Integer, Integer> copyFilledForm(final DraftForm newForm, final Site site,
                                                final Map<Integer, Integer> formItemsIdWithCopiedEquivalents) {
        return copyFilledFormInternal(newForm, site, filledForm, formItemsIdWithCopiedEquivalents).getCopiedCommentsId();
    }

    public static List<Integer> generateFilledFormHashCodes(List<FilledForm> filledForms) {
        final List<Integer> filledFormsHashCodes = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            filledFormsHashCodes.add(filledForm.getValueHashCode());
        }

        return filledFormsHashCodes;
    }

    public static FilledForm saveFilledForm(final List<FilledFormItem> filledFormItems, final Form form) {
        final FilledForm filledForm = new FilledForm();

        filledForm.setFormId(form.getId());
        filledForm.setType(form.getType());
        filledForm.setFormDescription(StringUtil.isNullOrEmpty(form.getDescription()) ? "" : form.getDescription());
        ServiceLocator.getPersistance().putFilledForm(filledForm);

        updateFilledFormItems(filledFormItems, filledForm);

        return filledForm;
    }

    public static List<FilledFormItem> sortByPositionFilledFormItems(List<FilledFormItem> filledFormItems) {
        Collections.sort(filledFormItems, filledFormItemPositionComparator);

        return filledFormItems;
    }

    private static final Comparator<FilledFormItem> filledFormItemPositionComparator = new Comparator<FilledFormItem>() {
        public int compare(final FilledFormItem fi1, final FilledFormItem fi2) {
            return ((Integer) fi1.getPosition()).compareTo(fi2.getPosition());
        }
    };

    public static boolean isFilledFormContainsKey(FilledForm filledForm, String key) {
        for (FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
            if (filledFormItem.getValues() != null && !filledFormItem.getValues().isEmpty()) {
                for (String value : filledFormItem.getValues()) {
                    if (value.contains(key) || value.toLowerCase().contains(key.toLowerCase())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //Constructs FilledFormInfo for page update
    public static FilledFormInfo getFilledFormInfo(final FilledForm filledForm, final List<String> itemNames) {
        if (filledForm != null && itemNames != null) {
            final FilledFormInfo filledFormInfo = new FilledFormInfo();
            final int visitorId = (filledForm.getUser() != null ? filledForm.getUser().getUserId() : -1);
            filledFormInfo.setVisitorId(visitorId);
            filledFormInfo.setFillDate(DateUtil.toCommonDateStr(filledForm.getFillDate()));
            filledFormInfo.setUpdateDate(DateUtil.toCommonDateStr(filledForm.getUpdatedDate() != null ? filledForm.getUpdatedDate() : filledForm.getFillDate()));
            filledFormInfo.setFilledFormId(filledForm.getFilledFormId());
            filledFormInfo.setHidden(filledForm.isHidden());

            final International international = ServiceLocator.getInternationStorage().get("manageFormRecordsTable", Locale.US);
            final String notSpecified = "&lt;" + international.get("notSpecified") + "&gt;";
            for (String itemName : itemNames) {
                final FilledFormItem filledFormItem = getFilledFormItemByItemName(filledForm, itemName);
                if (filledFormItem == null) {
                    filledFormInfo.addCustomCell(null, notSpecified);
                    continue;
                }
                if (filledFormItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
                    FilledFormItemManager manager = new FilledFormItemManager(filledFormItem);
                    final Integer imageId = manager.getFormImageId();
                    final String imageUrl = (imageId == null || imageId <= 0) ? null : ResourceGetterType.FORM_FILE_THUMBNAIL.getUrl(imageId);
                    filledFormInfo.addCustomCell(filledFormItem.getFormItemName(), imageUrl, manager.getFormImageAlt(), notSpecified);
                } else {
                    String value = getFilledFormItemFormattedValueByItemName(filledForm, itemName);
                    value = StringUtil.isNullOrEmpty(value) ? notSpecified : value;
                    filledFormInfo.addCustomCell(filledFormItem.getFormItemName(), value);
                }
            }
            return filledFormInfo;
        }
        return null;
    }

    public static String createImageItemUrl(String stringImageItemId) {
        if (stringImageItemId != null && !stringImageItemId.isEmpty()) {
            stringImageItemId = stringImageItemId.trim();
            final int imageItemId = Integer.parseInt(stringImageItemId);
            final String url = ServiceLocator.getResourceGetter().get(ResourceGetterType.FORM_FILE_THUMBNAIL, imageItemId, 0, 0, 0, false);
            return url != null ? url : "";
        }
        return "";
    }

    protected static FilledForm createFilledFormByLoginedUser(User user) {
        final FilledForm returnForm = new FilledForm();

        returnForm.addFilledFormItem(createMockItem(getFormInternational().get(FormItemName.EMAIL.toString() + "_FN"), FormItemName.EMAIL, StringUtil.getEmptyOrString(user.getEmail())));
        returnForm.addFilledFormItem(createMockItem(getFormInternational().get(FormItemName.FIRST_NAME.toString() + "_FN"), FormItemName.FIRST_NAME, StringUtil.getEmptyOrString(user.getFirstName())));
        returnForm.addFilledFormItem(createMockItem(getFormInternational().get(FormItemName.LAST_NAME.toString() + "_FN"), FormItemName.LAST_NAME, StringUtil.getEmptyOrString(user.getLastName())));
        returnForm.addFilledFormItem(createMockItem(getFormInternational().get(FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME.toString() + "_FN"),
                FormItemName.REGISTRATION_UNREMOVABLE_SCREEN_NAME, StringUtil.getEmptyOrString(user.getScreenName())));
        returnForm.addFilledFormItem(createMockItem(getFormInternational().get(FormItemName.TELEPHONE.toString() + "_FN"), FormItemName.TELEPHONE, StringUtil.getEmptyOrString(user.getTelephone())));

        return returnForm;
    }

    protected static FilledFormItem createMockItem(String formItemText, FormItemName formItemName, String... formValues) {
        final FilledFormItem filledFormItem = new FilledFormItem();
        filledFormItem.setItemName(formItemText);
        filledFormItem.setValues(Arrays.asList(formValues));
        filledFormItem.setFormItemName(formItemName);
        return filledFormItem;
    }

    public static FilledFormItem saveFilledFormItem(final String formItemText, final FormItemName formItemName,
                                                    final FilledForm filledForm, final String... formValues) {
        return saveFilledFormItem(formItemText, formItemName, -1, filledForm, formValues);
    }

    public static FilledFormItem saveFilledFormItem(final String formItemText, final FormItemName formItemName,
                                                    final int formItemId, final FilledForm filledForm,
                                                    final String... formValues) {
        final FilledFormItem filledFormItem = new FilledFormItem();
        filledFormItem.setItemName(formItemText);
        filledFormItem.setValues(Arrays.asList(formValues));
        filledFormItem.setFormItemName(formItemName);
        filledFormItem.setFilledForm(filledForm);
        filledFormItem.setFormItemId(formItemId);

        ServiceLocator.getPersistance().putFilledFormItem(filledFormItem);

        filledForm.addFilledFormItem(filledFormItem);

        return filledFormItem;
    }

    public static FilledForm getFirstRegistrationFilledFormForSite(final User user, final Site site) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(user, site));
        if (userOnSiteRight != null && !userOnSiteRight.getFilledRegistrationFormIds().isEmpty()) {
            return persistance.getFilledFormById(userOnSiteRight.getFilledRegistrationFormIds().get(0));
        }
        return null;
    }

    public static FilledForm getFirstRegistrationFilledFormForSite(final int userId, final int siteId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final User user = persistance.getUserById(userId);
        final Site site = persistance.getSite(siteId);
        if (user != null && site != null) {
            return getFirstRegistrationFilledFormForSite(user, site);
        }

        return null;
    }

    private static FilledForm findRegistrationFormForEditDetails(User user, Site site) {
        final FilledForm filledForm = getFirstRegistrationFilledFormForSite(user, site);

        if (filledForm != null) {
            return filledForm;
        }

        return createFilledFormByLoginedUser(user);
    }

    //forcePrefill = true if editing visior details

    public static FilledForm findPrefilledForm(User user, int siteId, boolean forcePrefill) {
        if (user != null) {
            if (forcePrefill) {
                return findRegistrationFormForEditDetails(user, ServiceLocator.getPersistance().getSite(siteId));
            } else {
                return createFilledFormByLoginedUser(user);
            }
        } else {
            return null;
        }
    }

    public static List<String> getFilledFormItemValueByItemNameList(final FilledForm prefilledForm, final FormItemName formItemName, final String itemCustomName, final int formId) {
        if (prefilledForm != null) {
            for (FilledFormItem filledFormItem : prefilledForm.getFilledFormItems()) {
                if (filledFormItem.getItemName().equals(itemCustomName)) {
                    if (filledFormItem.getValues().contains(null)) {
                        return Collections.emptyList();
                    } else {
                        return filledFormItem.getValues();
                    }
                }
            }

            //If we did not found selected option then we will prefill it with default option
            return getPrefilledOption(formItemName, formId);
        } else {
            return getPrefilledOption(formItemName, formId);
        }
    }

    //Return's default prefilled options for form item.

    public static List<String> getPrefilledOption(final FormItemName formItemName, final int formId) {
        final List<String> prefilledItems = new ArrayList<String>();
        if (formItemName.equals(FormItemName.DATE_ADDED)) {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(new Date());
            prefilledItems.add(String.valueOf(DateUtil.getMonth(gregorianCalendar)));
            prefilledItems.add(String.valueOf(gregorianCalendar.get(Calendar.DAY_OF_MONTH)));
            prefilledItems.add(String.valueOf(gregorianCalendar.get(Calendar.YEAR)));
            prefilledItems.add(String.valueOf(gregorianCalendar.get(Calendar.HOUR_OF_DAY)));
            prefilledItems.add(String.valueOf(gregorianCalendar.get(Calendar.MINUTE)));
        } else if (formItemName.equals(FormItemName.SORT_ORDER)) {
            prefilledItems.add(String.valueOf((ServiceLocator.getPersistance().getFilledFormsCountByFormId(formId) + 1)));
        }

        return prefilledItems;
    }

    public static FilledFormItem getFilledFormItemByFormItemId(FilledForm filledForm, int formItemId) {
        if (filledForm != null) {
            return selectFilledFormItemByFormItemId(filledForm.getFilledFormItems(), formItemId);
        }
        return null;
    }

    public static FilledFormItem selectFilledFormItemByFormItemId(List<FilledFormItem> filledFormItems, int formItemId) {
        if (filledFormItems != null) {
            for (FilledFormItem filledFormItem : filledFormItems) {
                if (filledFormItem.getFormItemId() == formItemId) {
                    return filledFormItem;
                }
            }
        }
        return null;
    }

    public static String getFilledFormItemValueByItemName(final FilledForm filledForm, final String itemCustomName) {
        final FilledFormItem filledFormItem = getFilledFormItemByItemName(filledForm, itemCustomName);

        if (filledFormItem != null && !filledFormItem.getValues().isEmpty()) {
            return filledFormItem.getValues().get(0);
        }

        return "";
    }

    public static String getFilledFormItemFormattedValueByItemName(final FilledForm filledForm, final String itemCustomName) {
        final FilledFormItem filledFormItem = getFilledFormItemByItemName(filledForm, itemCustomName);
        if (filledFormItem != null && !filledFormItem.getValues().isEmpty()) {
            return new FilledFormItemManager(filledFormItem).getFormattedValue(null);
        }

        return "";
    }

    public static String getFilledFormItemValueByFormItemId(final FilledForm filledForm, final Integer formItemId) {
        final FilledFormItem filledFormItem = getFilledFormItemByFormItemId(filledForm, formItemId);

        if (filledFormItem != null && !filledFormItem.getValues().isEmpty()) {
            return filledFormItem.getValues().get(0);
        }

        return "";
    }


    /**
     * Removes filled form. Use this method in case of any form except Registration.
     * For registration form we have removeVisitorOnSiteRight method in VisitorManager it also use this mehtod.
     *
     * @param filledForm - filled form
     */
    //todo write tests on this mehtod. test items deletion, files deletion, votes deletion. Dmitry Solomadin
    public static void remove(final FilledForm filledForm) {
        final Persistance persistance = ServiceLocator.getPersistance();
        //Removing filled form items
        final List<FilledFormItem> itemsToRemove = new ArrayList<FilledFormItem>();
        itemsToRemove.addAll(filledForm.getFilledFormItems());
        for (final FilledFormItem itemToRemove : itemsToRemove) {
            filledForm.getFilledFormItems().remove(itemToRemove);

            if (itemToRemove.getFormItemName().getType() == FormItemType.FILE_UPLOAD) {
                FormFile formFile = null;
                try {
                    final int fileId = FilledFormItemManager.getIntValue(itemToRemove);
                    formFile = persistance.getFormFileById(fileId);
                } catch (final Exception exception) {
                    logger.log(Level.SEVERE, "Error while removing file", exception);
                }
                if (formFile != null) {
                    ServiceLocator.getFileSystem().removeResource(formFile);
                    persistance.removeFormFile(formFile);
                }
            }

            itemToRemove.setValues(new ArrayList<String>());
            persistance.removeFilledFormItem(itemToRemove);
        }

        //Removing form from visitor or from page visitor
        if (filledForm.getUser() != null) {
            filledForm.getUser().removeFilledForm(filledForm);
        } else if (filledForm.getPageVisitor() != null) {
            filledForm.getPageVisitor().removeFilledForm(filledForm);
        }

        //Removing votes associated with this filled form.
        persistance.removeVotesByFilledFormId(filledForm.getFilledFormId());
        persistance.removeFilledForm(filledForm);
    }

    // This method used to synchronize filledForm with form.
    // It adds empty items that was added to form after filledForm creation
    // Also it updates names of filledForm items.

    public static void updateAndAddFilledFormItemNames(final FilledForm filledForm) {
        final Persistance persistance = ServiceLocator.getPersistance();
        //Updating filledFormItem names.
        final List<DraftFormItem> updatedItems = new ArrayList<DraftFormItem>();
        if (filledForm.getFilledFormItems() != null && !filledForm.getFilledFormItems().isEmpty()) {
            for (FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
                DraftFormItem initFormItem = persistance.getFormItemById(filledFormItem.getFormItemId());

                if (initFormItem != null) {
                    updatedItems.add(initFormItem);
                    if (!filledFormItem.getItemName().equals(initFormItem.getItemName())) {
                        filledFormItem.setItemName(initFormItem.getItemName());
                    }
                }
            }
        }

        //Adding new empty filledFormItems.
        final DraftForm form = persistance.getFormById(filledForm.getFormId());
        for (DraftFormItem formItem : form.getDraftFormItems()) {
            if (!updatedItems.contains(formItem) && formItem.getFormItemName().getType() != FormItemType.SPECIAL) {
                final FilledFormItem filledFormItem = new FilledFormItem();
                filledFormItem.setFormItemName(formItem.getFormItemName());
                filledFormItem.setItemName(formItem.getItemName());
                filledFormItem.setFormItemId(formItem.getFormItemId());
                filledFormItem.setPosition(formItem.getPosition());
                final List<String> emptyValues = new ArrayList<String>();
                emptyValues.add("");
                filledFormItem.setValues(emptyValues);

                filledFormItem.setFilledForm(filledForm);
                persistance.putFilledFormItem(filledFormItem);
                filledForm.addFilledFormItem(filledFormItem);
            }
        }
    }

    public static void updateFilledFormItems(final List<FilledFormItem> newFilledFormItems, final FilledForm filledForm) {
        for (FilledFormItem newItem : newFilledFormItems) {
            FilledFormItem oldItem = getFilledFormItemByItemName(filledForm, newItem.getItemName());
            if (oldItem != null) {
                /*-------------------------------------update existing item values------------------------------------*/
                if (oldItem.getFormItemName().getType() != FormItemType.FILE_UPLOAD && oldItem.getFormItemName() != FormItemName.POST_CODE) {
                    oldItem.setValue(newItem.getValue());
                } else if (oldItem.getFormItemName() == FormItemName.POST_CODE) {
                    List<String> newCoordinateValues = Arrays.asList(createCoordinateValues(newFilledFormItems, filledForm, newItem.getValue()));
                    oldItem.setValues(newCoordinateValues);
                } else if (oldItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
                    FormVideo formVideo = FormVideoManager.createNewFormVideoOrUpdateExisting(
                            FilledFormItemManager.getIntValue(oldItem), newItem.getValues().get(0));
                    oldItem.setValue(String.valueOf(formVideo.getFormVideoId()));
                } else if (oldItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
                    final FilledFormItemManager manager = new FilledFormItemManager(oldItem);
                    manager.setFormImageAlt(newItem.getValue());
                } else if (newItem.getFormItemName() == FormItemName.SEO_KEYWORDS) {
                    oldItem.setValue(KeywordManager.normalizeKeywords(newItem.getValue()));
                }
                /*-------------------------------------update existing item values------------------------------------*/
            } else {
                /*----------------------------------------add new FilledFormItem--------------------------------------*/
                if (newItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
                    FormVideo formVideo = FormVideoManager.createNewFormVideoOrUpdateExisting(null, newItem.getValues().get(0));
                    newItem.setValue(String.valueOf(formVideo.getFormVideoId()));
                } else if (newItem.getFormItemName() == FormItemName.POST_CODE) {
                    List<String> newCoordinateValues = Arrays.asList(createCoordinateValues(newFilledFormItems, filledForm, newItem.getValue()));
                    newItem.setValues(newCoordinateValues);
                } else if (newItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
                    if (newItem.getValues().size() == 1) {// move keywords to the second position
                        final String keywords = newItem.getValue();
                        newItem.setValue(null);
                        final FilledFormItemManager manager = new FilledFormItemManager(newItem);
                        manager.setFormImageAlt(keywords);
                    }
                } else if (newItem.getFormItemName() == FormItemName.SEO_KEYWORDS) {
                    newItem.setValue(KeywordManager.normalizeKeywords(newItem.getValue()));
                }
                newItem.setFilledForm(filledForm);
                ServiceLocator.getPersistance().putFilledFormItem(newItem);
                filledForm.addFilledFormItem(newItem);
                /*----------------------------------------add new FilledFormItem--------------------------------------*/
            }
        }
        filledForm.setUpdatedDate(new Date());
        GalleriesManager.updateVideoFilesForGalleriesThatUseCurentForm(filledForm != null ? filledForm.getFormId() : null);
    }

    protected static String[] createCoordinateValues(final List<FilledFormItem> newFilledFormItems, final FilledForm filledForm, final String zip) {
        Country country = getFirstCountry(newFilledFormItems);
        if (country == null) {
            country = getFirstCountry(filledForm);
        }
        final Coordinate coordinate = CoordinateCreator.getExistingOrCreateNew(zip, country);
        return coordinate.toStringArray();
    }

    public static Country getFirstCountry(FilledForm filledForm) {
        List<FilledFormItem> filledFormItems = getFilledFormItemsByFormItemName(filledForm, FormItemName.COUNTRY);
        return getFirstCountry(filledFormItems);
    }

    private static Country getFirstCountry(List<FilledFormItem> filledForms) {
        List<FilledFormItem> filledFormItems = getFilledFormItemsByFormItemName(filledForms, FormItemName.COUNTRY);
        for (FilledFormItem item : filledFormItems) {
            try {
                Country country = Country.valueOf(item.getValue());
                if (country != null) {
                    return country;
                }
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can`t create Country by filledFormValue = " + item.getValue());
            }
        }
        return null;
    }

    public static FilledFormItem getFilledFormItemByFormItemName(final Integer filledFormId, final FormItemName formItemName) {
        if (filledFormId == null) {
            return null;
        }

        final FilledForm filledForm = ServiceLocator.getPersistance().getFilledFormById(filledFormId);

        return getFilledFormItemByFormItemName(filledForm, formItemName);
    }

    public static FilledFormItem getFilledFormItemByFormItemName(final FilledForm filledForm, final FormItemName formItemName) {
        if (filledForm == null) {
            return null;
        }

        return getFilledFormItemByFormItemName(filledForm.getFilledFormItems(), formItemName);
    }

    public static FilledFormItem getFilledFormItemByFormItemName(final List<FilledFormItem> filledFormItems, final FormItemName formItemName) {
        if (filledFormItems == null || formItemName == null || filledFormItems.isEmpty()) {
            return null;
        }

        for (FilledFormItem item : filledFormItems) {
            if (item != null && item.getFormItemName() == formItemName) {
                return item;
            }
        }

        return null;
    }

    public static List<FilledFormItem> getFilledFormItemsByFormItemName(final FilledForm filledForm, final FormItemName formItemName) {
        if (filledForm == null || formItemName == null || filledForm.getFilledFormItems() == null || filledForm.getFilledFormItems().isEmpty()) {
            return Collections.emptyList();
        }
        return getFilledFormItemsByFormItemName(filledForm.getFilledFormItems(), formItemName);
    }

    public static List<FilledFormItem> getFilledFormItemsByFormItemName(final List<FilledFormItem> filledFormItems, final FormItemName formItemName) {
        final List<FilledFormItem> newItems = new ArrayList<FilledFormItem>();
        if (filledFormItems == null || formItemName == null || filledFormItems.isEmpty()) {
            return newItems;
        }
        for (FilledFormItem item : filledFormItems) {
            if (item != null && item.getFormItemName() == formItemName) {
                newItems.add(item);
            }
        }
        return newItems;
    }

    public static FilledFormItem getFilledFormItemByItemName(final FilledForm prefilledForm, final String itemName) {
        if (prefilledForm != null) {
            for (FilledFormItem filledFormItem : prefilledForm.getFilledFormItems()) {
                if (filledFormItem.getItemName().equals(itemName)) {
                    return filledFormItem;
                }
            }
        }
        return null;
    }

    public static FilledFormItem getFilledFormItemByItemPosition(FilledForm prefilledForm, Integer position) {
        if (prefilledForm != null && position != null) {
            for (FilledFormItem filledFormItem : prefilledForm.getFilledFormItems()) {
                if (filledFormItem.getPosition() == position) {
                    return filledFormItem;
                }
            }
        }
        return null;
    }

    public static String getDomainName(final List<FilledFormItem> filledFormItems) {
        if (filledFormItems != null && !filledFormItems.isEmpty()) {
            final FilledFormItem item = FilledFormManager.getFilledFormItemByFormItemName(filledFormItems, FormItemName.YOUR_OWN_DOMAIN_NAME);
            if (item != null && !item.getValues().isEmpty() && !StringUtil.isNullOrEmpty(item.getValues().get(0))) {
                return item.getValues().get(0);
            }
        }

        return null;
    }

    public static String getPageSiteName(final List<FilledFormItem> filledFormItems) {
        if (filledFormItems != null && !filledFormItems.isEmpty()) {
            final FilledFormItem item = FilledFormManager.getFilledFormItemByFormItemName(filledFormItems, FormItemName.YOUR_PAGE_SITE_NAME);
            if (item != null && !item.getValues().isEmpty() && !StringUtil.isNullOrEmpty(item.getValues().get(0))) {
                return item.getValues().get(0);
            }
        }
        return null;
    }

    public static List<FilledForm> getFilledFormsByNetworkSiteId(final int networkSiteId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Site site = persistance.getSite(networkSiteId);
        final List<FilledForm> filledForms = new ArrayList<FilledForm>();
        if (site != null) {
            for (int childSiteRegistrationsId : site.getChildSiteRegistrationsId()) {
                filledForms.addAll(persistance.getFilledFormsByFormId(childSiteRegistrationsId));
            }
            Collections.sort(filledForms, new Comparator<FilledForm>() {
                public int compare(FilledForm filledForm1, FilledForm filledForm2) {
                    return filledForm1.getFillDate().compareTo(filledForm2.getFillDate());
                }
            });
        }
        return filledForms;
    }

    // selectNumber starts from 1.

    public static boolean isGotPrefilledRecordForSelect(final List<String> prefilledItems, final FormItem formItem, final int selectNumber, final String matchTo) {
        boolean gotPrefilledRecord = !prefilledItems.isEmpty() && prefilledItems.size() > (selectNumber - 1);

        if (gotPrefilledRecord) {
            if (formItem.getFormItemName() == FormItemName.LINKED) {
                return prefilledItems.get(selectNumber - 1).contains(matchTo);
            } else if (formItem.getFormItemName().isDate()) {
                return datePartEquals(prefilledItems.get(selectNumber - 1), matchTo);
            } else {
                return prefilledItems.get(selectNumber - 1).equals(matchTo);
            }
        } else {
            return false;
        }
    }

    public static boolean datePartEquals(String datePart1, String datePart2) {
        return datePart1.equals(datePart2) || ("0" + datePart1).equals(datePart2) ||
                datePart1.equals("0" + datePart2);
    }

    /**
     * Why we use method instaed static variable? Ha?
     * Becouse static variable it's long life link on service it's bad!
     * Also LightsTest, can't work with static variables on services, why?
     * Becouse ServiceLocator init execute before each tests, and get service in class init sections it's full mistake!
     *
     * @return
     */
    public static International getFormInternational() {
        return ServiceLocator.getInternationStorage().get("formTable", Locale.US);
    }

    public static class GroupsPrefilledRecord {

        private String groupId = "";

        private String groupTimePeriod = "";

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupTimePeriod() {
            return groupTimePeriod;
        }

        public void setGroupTimePeriod(String groupTimePeriod) {
            this.groupTimePeriod = groupTimePeriod;
        }
    }

    public static GroupsPrefilledRecord getGroupsPrefilledRecord(final List<String> prefilledItems, final int groupId) {
        for (int i = 0; i < prefilledItems.size(); i++) {
            String prefilledItem = prefilledItems.get(i);

            if (prefilledItems.size() > i + 1) {
                String nextPrefilledItem = prefilledItems.get(i + 1);

                if (prefilledItem.equals("" + groupId)) {
                    final GroupsPrefilledRecord groupsPrefilledRecord = new GroupsPrefilledRecord();
                    groupsPrefilledRecord.setGroupId(prefilledItem);
                    groupsPrefilledRecord.setGroupTimePeriod(nextPrefilledItem);

                    return groupsPrefilledRecord;
                }
            }
        }

        return null;
    }

    public static String extractKeywords(final int filledFormId, final String delimiter) {
        final FilledForm filledForm = ServiceLocator.getPersistance().getFilledFormById(filledFormId);

        if (filledForm != null) {
            final FilledFormItem seoKeywordsFilledItem =
                    getFilledFormItemByFormItemName(filledForm, FormItemName.SEO_KEYWORDS);

            if (seoKeywordsFilledItem != null && !seoKeywordsFilledItem.getValue().isEmpty()) {
                return FilledFormItemManager.extractKeywordsValue(seoKeywordsFilledItem, delimiter);
            } else {
                final FilledFormItem nameFilledItem =
                        getFilledFormItemByFormItemName(filledForm, FormItemName.NAME);

                if (nameFilledItem != null && !nameFilledItem.getValue().isEmpty()) {
                    return FilledFormItemManager.extractKeywordsValue(nameFilledItem, delimiter);
                } else {
                    final FilledFormItem firstTextFilledItem =
                            FilledFormItemsManager.getFirstFilledFormItemByItemType(filledForm, FormItemType.TEXT_INPUT_FIELD);

                    return FilledFormItemManager.extractKeywordsValue(firstTextFilledItem, delimiter);
                }
            }
        }

        return "";
    }

    private CopyFilledFormResponse copyFilledFormInternal(final DraftForm newForm, final Site site, final FilledForm filledForm,
                                                          final Map<Integer, Integer> formItemsIdWithCopiedEquivalents) {
        final FilledForm copiedFilledForm = new FilledForm();
        ItemCopierUtil.copyProperties(filledForm, copiedFilledForm);
        copiedFilledForm.setFormId(newForm.getFormId());
        copiedFilledForm.setUser(filledForm.getUser());
        persistance.putFilledForm(copiedFilledForm);

        persistance.getSite(site.getSiteId()).setChildSiteFilledFormId(copiedFilledForm.getFilledFormId());

        //Copying filled form items
        for (FilledFormItem item : filledForm.getFilledFormItems()) {
            final FilledFormItem copiedItem = new FilledFormItem();
            ItemCopierUtil.copyProperties(item, copiedItem);
            copiedItem.setFilledForm(copiedFilledForm);
            final Integer formItemIdEquivalent = formItemsIdWithCopiedEquivalents != null ? formItemsIdWithCopiedEquivalents.get(item.getFormItemId()) : item.getFormItemId();
            copiedItem.setFormItemId(formItemIdEquivalent != null ? formItemIdEquivalent : -1);
            persistance.putFilledFormItem(copiedItem);
            copiedFilledForm.addFilledFormItem(copiedItem);
            copiedItem.setFilledForm(copiedFilledForm);
            copiedItem.setValue(item.getValue());

            // Copying files
            if (copiedItem.getFormItemName().getType() == FormItemType.FILE_UPLOAD) {
                int copiedFormItemValue = -1;
                switch (copiedItem.getFormItemName()) {
                    case IMAGE_FILE_UPLOAD: {
                        final FormFile formFile = persistance.getFormFileById(new FilledFormItemManager(copiedItem).getFormImageId());
                        copiedFormItemValue = copyFormFile(formFile, site);
                        new FilledFormItemManager(copiedItem).setFormImageId(copiedFormItemValue);
                        break;
                    }
                    case VIDEO_FILE_UPLOAD: {
                        final FormVideo formVideo = persistance.getFormVideoById(copiedItem.getIntValue());
                        if (formVideo != null) {
                            final FormVideo copiedFormVideo = new FormVideo();
                            ItemCopierUtil.copyProperties(formVideo, copiedFormVideo);
                            persistance.putFormVideo(copiedFormVideo);
                            copiedFormItemValue = copiedFormVideo.getFormVideoId();
                            copiedItem.setValue(copiedFormItemValue);

                            final FormFile image = persistance.getFormFileById(formVideo.getImageId());
                            copiedFormVideo.setImageId(copyFormFile(image, site));

                            final Video video = persistance.getVideoById(formVideo.getVideoId());
                            if (video != null) {
                                final Video copiedVideo = new Video();
                                ItemCopierUtil.copyProperties(video, copiedVideo);
                                copiedVideo.setSiteId(site.getSiteId());
                                copiedVideo.setFilledFormId(video.getFilledFormId());
                                persistance.putVideo(copiedVideo);
                                copiedFormVideo.setVideoId(copiedVideo.getVideoId());
                                fileSystem.setResourceStream(copiedVideo, fileSystem.getResourceStream(video));
                            }
                        }
                        break;
                    }
                    default: {
                        final FormFile formFile = persistance.getFormFileById(copiedItem.getIntValue());
                        copiedFormItemValue = copyFormFile(formFile, site);
                        copiedItem.setValue(copiedFormItemValue);
                    }
                }
            }
        }


        Map<Integer, Integer> copiedCommentsId = new HashMap<Integer, Integer>();
        //Copying comments
        for (GalleryComment comment : filledForm.getComments()) {
            final GalleryComment copiedComment = new GalleryComment();
            ItemCopierUtil.copyProperties(comment, copiedComment);
            copiedComment.setFilledForm(copiedFilledForm);
            copiedFilledForm.addComment(copiedComment);
            persistance.putGalleryComment(copiedComment);
            copiedCommentsId.put(copiedComment.getCommentId(), copiedComment.getGallery().getId());
        }
        return new CopyFilledFormResponse(copiedCommentsId, copiedFilledForm);
    }

    private int copyFormFile(final FormFile formFile, final Site site) {
        if (formFile != null) {
            final FormFile copiedFormFile = new FormFile();
            ItemCopierUtil.copyProperties(formFile, copiedFormFile);
            copiedFormFile.setSiteId(site.getSiteId());
            persistance.putFormFile(copiedFormFile);
            fileSystem.setResourceStream(copiedFormFile, fileSystem.getResourceStream(formFile));
            return copiedFormFile.getFormFileId();
        }
        return -1;
    }

    private class CopyFilledFormResponse {

        private CopyFilledFormResponse(Map<Integer, Integer> copiedCommentsId, FilledForm copiedFilledForm) {
            this.copiedCommentsId = copiedCommentsId;
            this.copiedFilledForm = copiedFilledForm;
        }

        public Map<Integer, Integer> getCopiedCommentsId() {
            return copiedCommentsId;
        }

        public FilledForm getCopiedFilledForm() {
            return copiedFilledForm;
        }

        private final Map<Integer, Integer> copiedCommentsId;

        private final FilledForm copiedFilledForm;
    }

    public static final SimpleDateFormat filledFormDateFormat = new SimpleDateFormat("MMMMM;dd;yyyy;HH;mm", Locale.US);
    private static final Logger logger = Logger.getLogger(FilledFormManager.class.getName());
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final FilledForm filledForm;
}
