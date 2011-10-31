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
package com.shroggle.logic.manageRegistrants;

import com.shroggle.presentation.site.RegisteredVisitorInfo;
import com.shroggle.exception.UnknownUserItemsSortTypeException;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;

/**
 * @author dmitry.solomadin
 */
public class ManageRegistrantsSorter {

    public List<RegisteredVisitorInfo> execute(final List<RegisteredVisitorInfo> items,
                                               final ManageRegistrantsSortType sortType, final boolean desc) {
        if (sortType != null && items != null) {
            final Comparator<RegisteredVisitorInfo> comparator;
            switch (sortType) {
                case FIRST_NAME: {
                    comparator = new FirstNameComparator();
                    break;
                }
                case LAST_NAME: {
                    comparator = new LastNameComparator();
                    break;
                }
                case EMAIL: {
                    comparator = new EmailComparator();
                    break;
                }
                case CREATED_DATE: {
                    comparator = new CreatedDateComparator();
                    break;
                }
                case UPDATED_DATE: {
                    comparator = new UpdatedDateComparator();
                    break;
                }
                case FORM_NAME: {
                    comparator = new FormNameComparator();
                    break;
                }
                case STATUS: {
                    comparator = new StatusComparator();
                    break;
                }
                case GROUP: {
                    comparator = new GroupComparator();
                    break;
                }
                default: {
                    throw new UnknownUserItemsSortTypeException("Unknown sort type: " + sortType);
                }
            }

            Collections.sort(items, comparator);

            if (desc) {
                Collections.reverse(items);
            }
        } else {
            return null;
        }

        return items;
    }

    private class FirstNameComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getFirstName().compareTo(item2.getFirstName());
        }

    }

    private class LastNameComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getLastName().compareTo(item2.getLastName());
        }

    }

    private class EmailComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getEmail().compareTo(item2.getEmail());
        }

    }

    private class CreatedDateComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getCreatedDateRaw().compareTo(item2.getCreatedDateRaw());
        }

    }

    private class UpdatedDateComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getUpdatedDateRaw().compareTo(item2.getUpdatedDateRaw());
        }

    }

    private class FormNameComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getForms().get(0).getName().compareTo(item2.getForms().get(0).getName());
        }

    }

    private class StatusComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getStatus().compareTo(item2.getStatus());
        }

    }

    private class GroupComparator implements Comparator<RegisteredVisitorInfo> {

        public int compare(RegisteredVisitorInfo item1, RegisteredVisitorInfo item2) {
            return item1.getGroupsNames().compareTo(item2.getGroupsNames());
        }

    }

}
