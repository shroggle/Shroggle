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
import com.shroggle.logic.countries.CountryManager;
import com.shroggle.logic.countries.states.StateManager;
import com.shroggle.logic.form.filter.FilterFormItemInfo;
import com.shroggle.logic.groups.SubscriptionTimeType;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class FormItemManager {

    public static <T extends FormItem> T createFormItemByFilledFormItem(final FilledFormItem filledFormItem, final DraftForm form) {
        if (filledFormItem == null) {
            throw new IllegalArgumentException("filledFormItem cannot be null");
        }

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(filledFormItem.getFormItemName());
        formItem.setItemName(filledFormItem.getItemName());
        formItem.setFormItemId(filledFormItem.getFormItemId());
        formItem.setDraftForm(form);
        formItem.setPosition(filledFormItem.getPosition());

        if (filledFormItem.getFormItemName() == FormItemName.LINKED) {
            final FormItem realFormItem =
                    ServiceLocator.getPersistance().getFormItemById(filledFormItem.getFormItemId());

            if (realFormItem != null) {
                formItem.setLinkedFormItemId(realFormItem.getLinkedFormItemId());
                formItem.setFormItemDisplayType(realFormItem.getFormItemDisplayType());
            }
        }

        return (T) formItem;
    }

    /**
     * <b>Please, pay attention to this method. We must NOT persist created item, because it's
     * used in some places as simple to show fields without real form!</b>
     *
     * @param formItemName - FormItemName of created item.
     * @param position     - position in form of created item.
     * @param required     - determines if item is required to fill in form.
     * @return created form item.
     */
    public static DraftFormItem createFormItemByName(final FormItemName formItemName, final int position, final boolean required) {
        return createFormItemByName(formItemName, position, required, true);
    }

    public static DraftFormItem createFormItemByName(final FormItemName formItemName, final int position, final boolean required, final boolean setName) {
        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(formItemName);
        formItem.setPosition(position);
        formItem.setRequired(required);
        if (setName) {
            formItem.setItemName(FormManager.getFormInternational().get(formItemName + "_FN"));
        }
        return formItem;
    }

    //todo: change all calls to this method to getItemOptionListForPickList method.

    @Deprecated
    public static List<String> getItemOptionsList(final FormItem formItem) {
        final List<String> returnList = new ArrayList<String>();
        final FormItemName formItemName = formItem.getFormItemName();

        if (formItemName == FormItemName.LINKED) {
            return LinkedFormManager.getLinkedOptions(formItem);
        }

        if (formItemName.getType() == FormItemType.PICK_LIST_AND_TEXT_FIELD ||
                formItemName.getType() == FormItemType.MULITSELECT ||
                formItemName.getType() == FormItemType.SELECT ||
                formItemName.getType() == FormItemType.RADIOBUTTON) {
            if (formItemName == FormItemName.EMIGRATED_TO) {
                returnList.addAll(CountryManager.getCountriesStringValues());
                return returnList;
            }

            boolean lastOption = false;
            int i = 1;
            do {
                try {
                    returnList.add(FormManager.getFormInternational().get(formItemName.toString() + "." + i));
                } catch (MissingResourceException e) {
                    lastOption = true;
                }
                i++;
            } while (!lastOption);

            return returnList;
        } else {
            return Collections.emptyList();
        }
    }

    // Get's available options for item. Used while displaying form.

    public static Map<Integer, List<String>> getItemOptionListForPickList(final FormItem formItem) {
        final Map<Integer, List<String>> returnMap = new HashMap<Integer, List<String>>();

        if (formItem.getFormItemName() == FormItemName.LINKED) {
            returnMap.put(1, LinkedFormManager.getLinkedOptions(formItem));
            return returnMap;
        }

        if (formItem.getFormItemName().getType() == FormItemType.SELECT ||
                formItem.getFormItemName().getType() == FormItemType.MULITSELECT ||
                formItem.getFormItemName().getType() == FormItemType.TWO_PICK_LISTS ||
                formItem.getFormItemName().getType() == FormItemType.THREE_PICK_LISTS ||
                formItem.getFormItemName().getType() == FormItemType.FIVE_PICK_LISTS ||
                formItem.getFormItemName().getType() == FormItemType.SINGLE_CHOICE_OPTION_LIST ||
                formItem.getFormItemName().getType() == FormItemType.RADIOBUTTON) {
            if (formItem.getFormItemName() == FormItemName.COUNTRY
                    || formItem.getFormItemName() == FormItemName.COUNTRY_BORN
                    || formItem.getFormItemName() == FormItemName.COUNTRY_DIED
                    || formItem.getFormItemName() == FormItemName.COUNTRY_WHERE_MARRIED) {
                returnMap.put(1, CountryManager.getCountriesStringValues());
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.SPECIES) {
                returnMap.put(1, ServiceLocator.getFileSystem().getSpecies());
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.GENUS) {
                returnMap.put(1, ServiceLocator.getFileSystem().getGenus());
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.FAMILY) {
                returnMap.put(1, ServiceLocator.getFileSystem().getFamily());
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.SUBSCRIPTION_BILLING_PERIOD){
                final List<String> subscriptionTimeTypes = new ArrayList<String>();
                for (SubscriptionTimeType subscriptionTimeType : SubscriptionTimeType.values()){
                    subscriptionTimeTypes.add(subscriptionTimeType.getText());
                }
                returnMap.put(1, subscriptionTimeTypes);
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.INTERNAL_LINK) {
                final int siteId = formItem.getForm().getSiteId();
                final Site site = ServiceLocator.getPersistance().getSite(siteId);
                if (site != null) {
                    final List<Page> pages = site.getPages();
                    final List<String> pageNames = new ArrayList<String>();

                    for (Page page : pages) {
                        if (page.isSystem()) {
                            continue;
                        }
                        pageNames.add(new PageSettingsManager(page.getPageSettings()).getPublicUrl());
                    }
                    returnMap.put(1, pageNames);
                }
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.PRODUCT_TAX_RATE) {
                final int siteId = formItem.getForm().getSiteId();
                final Site site = ServiceLocator.getPersistance().getSite(siteId);
                if (site != null) {
                    final List<DraftItem> draftItems = ServiceLocator.getPersistance().getDraftItemsBySiteId(siteId, ItemType.TAX_RATES);
                    final List<String> taxRatesIds = new ArrayList<String>();
                    final List<String> taxRatesNames = new ArrayList<String>();

                    for (final DraftItem draftItem : draftItems) {
                        taxRatesIds.add("" + draftItem.getId());
                        taxRatesNames.add(draftItem.getName());
                    }

                    returnMap.put(1, taxRatesIds);
                    returnMap.put(2, taxRatesNames);
                }
                return returnMap;
            } else if (formItem.getFormItemName() == FormItemName.STATE) {
                final List<String> stateCode = new ArrayList<String>();
                final List<String> statesNames = new ArrayList<String>();

                final List<States_US> states = Arrays.asList(States_US.values());
                for (States_US state : states){
                    stateCode.add(state.toString());
                    statesNames.add(new StateManager(state).getName());
                }

                returnMap.put(1, stateCode);
                returnMap.put(2, statesNames);
                return returnMap;
            }

            for (int i = 1; i <= formItem.getFormItemName().getType().getPickListCount(); i++) {
                //For every pick list trying to get: 1. Normal options. 2. Range options, if "1." failed.
                boolean lastOption = false;
                int j = 1;
                List<String> returnList = new ArrayList<String>();
                do {
                    try {
                        returnList.add(FormManager.getFormInternational().get(formItem.getFormItemName().toString() + "." + i + "." + j));
                    } catch (MissingResourceException e) {
                        lastOption = true;
                    }
                    j++;
                } while (!lastOption);

                if (returnList.isEmpty()) {
                    lastOption = false;
                    j = 1;
                    do {
                        try {
                            String option = FormManager.getFormInternational().get(formItem.getFormItemName().toString() + "." + j);
                            if (formItem.getFormItemName().equals(FormItemName.STATUS)) {
                                final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy @ HH:mm:ss");
                                option = option + " " + format.format(new Date());
                            }

                            returnList.add(option);
                        } catch (MissingResourceException e) {
                            lastOption = true;
                        }
                        j++;
                    } while (!lastOption);
                }

                if (returnList.isEmpty()) {
                    int rangeStart, rangeEnd;

                    rangeStart = Integer.parseInt(FormManager.getFormInternational().get(formItem.getFormItemName().toString() + "." + i + ".RANGE_START"));
                    rangeEnd = Integer.parseInt(FormManager.getFormInternational().get(formItem.getFormItemName().toString() + "." + i + ".RANGE_END"));

                    final boolean isMinutes = formItem.getFormItemName().isDate() && rangeEnd == 59;

                    if (rangeStart > rangeEnd) {
                        for (int k = rangeStart; k >= rangeEnd; k--) {
                            returnList.add((isMinutes && k < 10 ? "0" : "") + k);
                        }
                    } else {
                        for (int k = rangeStart; k <= rangeEnd; k++) {
                            returnList.add((isMinutes && k < 10 ? "0" : "") + k);
                        }
                    }

                    returnMap.put(i, returnList);
                } else {
                    returnMap.put(i, returnList);
                }
            }

            return returnMap;
        } else {
            return new HashMap<Integer, List<String>>();
        }
    }

    public static String getPickListDefaultOption(final FormItemName formItemName, final int i) {
        try {
            if (formItemName == null) {
                throw new IllegalArgumentException("formItemName cannot be null");
            }

            return FormManager.getFormInternational().get(formItemName.toString() + "_DO." + i);
        } catch (MissingResourceException e) {
            return "";
        }
    }

    public static String getItemDefaultInstruction(final FormItemName formItemName) {
        try {
            return FormManager.getFormInternational().get(formItemName.toString() + "_DEFAULT_INSTR");
        } catch (MissingResourceException e) {
            return "";
        }
    }

    public static String getItemDefaultInstruction(final FormItemName formItemName, final International international) {
        try {
            return international.get(formItemName.toString() + "_DEFAULT_INSTR");
        } catch (MissingResourceException e) {
            return "";
        }
    }

    public static String getItemDesc(final FormItemName formItemName) {
        return getItemDesc(formItemName, FormManager.getFormInternational());
    }

    public static String getItemDesc(final FormItemName formItemName, final International international) {
        try {
            // Trying to get default desc field for item
            return international.get(formItemName.toString() + "_DESC");
        } catch (MissingResourceException e) {
            // If default desc not found then we will check if this item SINLGE/MULTISELECT
            // And then return options as desc otherwise return empty string
            return getItemOptionsForDescription(formItemName);
        }
    }

    // Return's string like opt1, opt2, opt3,... etc.

    protected static String getItemOptionsForDescription(final FormItemName formItemName) {
        return getItemOptionsForDescription(formItemName, FormManager.getFormInternational());
    }

    protected static String getItemOptionsForDescription(final FormItemName formItemName, final International international) {
        if (formItemName.getType() == FormItemType.PICK_LIST_AND_TEXT_FIELD ||
                formItemName.getType() == FormItemType.SELECT ||
                formItemName.getType() == FormItemType.MULITSELECT) {
            boolean lastOption = false;
            int i = 1;
            String returnString = "";
            do {
                try {
                    returnString = returnString + (i == 1 ? "" : ", ")
                            + international.get(formItemName.toString() + "." + i);
                } catch (MissingResourceException e) {
                    lastOption = true;
                }
                i++;
            } while (!lastOption);
            return returnString;
        } else {
            return "";
        }
    }

    public static String getItemFieldType(final FormItemName formItemName) {
        return getItemFieldType(formItemName, FormManager.getFormInternational());
    }

    public static String getItemFieldType(final FormItemName formItemName, final International international) {
        if (formItemName == null) {
            throw new IllegalArgumentException("formItemName cannot be null");
        }

        if (formItemName == FormItemName.STATUS) {
            return international.get(formItemName.toString() + "_FT");
        }

        if (formItemName == FormItemName.LINKED) {
            return international.get(formItemName.toString() + "_FT");
        }

        return international.get(formItemName.getType().toString() + "_FT");
    }

    public FilterFormItemInfo getItemInfo(int formItemId) {
        final FilterFormItemInfo itemInfo = new FilterFormItemInfo();
        final FormItem formItem = persistance.getFormItemById(formItemId);

        itemInfo.setFormItemId(formItem.getFormItemId());
        itemInfo.setFormItemText(formItem.getItemName());
        itemInfo.setFormItemType(formItem.getFormItemName().getType());

        if (formItem.getFormItemName().getType() == FormItemType.PICK_LIST_AND_TEXT_FIELD ||
                formItem.getFormItemName().getType() == FormItemType.RADIOBUTTON ||
                formItem.getFormItemName().getType() == FormItemType.MULITSELECT) {
            final Map<Integer, List<String>> options = new HashMap<Integer, List<String>>();
            options.put(1, getItemOptionsList(formItem));
            itemInfo.setItemOptions(options);
        } else if (formItem.getFormItemName().getType() == FormItemType.SELECT ||
                formItem.getFormItemName().getType() == FormItemType.TWO_PICK_LISTS ||
                formItem.getFormItemName().getType() == FormItemType.THREE_PICK_LISTS ||
                formItem.getFormItemName().getType() == FormItemType.FIVE_PICK_LISTS ||
                formItem.getFormItemName().getType() == FormItemType.SINGLE_CHOICE_OPTION_LIST) {
            itemInfo.setItemOptions(getItemOptionListForPickList(formItem));
        }

        return itemInfo;
    }

    public static boolean isCorrectFormItemForFilter(final FormItem formItem) {
        return formItem.getFormItemName().getType() != FormItemType.SPECIAL &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD_RETYPE;
    }

    public static boolean isCorrectFormItemForAdvancedSearch(final FormItem formItem) {
        return formItem.getFormItemName().getType() != FormItemType.SPECIAL &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD_RETYPE &&
                formItem.getFormItemName().getType() != FormItemType.FILE_UPLOAD;
    }

    public static boolean isCorrectFormItemForManageYourVotes(final FormItem formItem) {
        return formItem.getFormItemName().getType() != FormItemType.SPECIAL &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD_RETYPE &&
                formItem.getFormItemName().getType() != FormItemType.FILE_UPLOAD;
    }

    public static boolean isCorrectFormItemForLinked(final FormItem formItem) {
        return formItem.getFormItemName().getType() != FormItemType.SPECIAL &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD &&
                formItem.getFormItemName() != FormItemName.REGISTRATION_PASSWORD_RETYPE;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
