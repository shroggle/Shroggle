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

package com.shroggle.logic.user.items;

import com.shroggle.entity.DraftGallery;
import com.shroggle.entity.ItemType;
import com.shroggle.exception.UnknownUserItemsSortTypeException;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class UserItemsSorter {

    public List<ItemManager> execute(final List<ItemManager> items, UserItemsSortType sortType, final boolean DESC) {
//        TimeCounter timeCounter;
        if (sortType != null) {
            final Comparator<ItemManager> comparator;
            switch (sortType) {
                case NAME: {
//                    timeCounter = ServiceLocator.getTimeCounterCreator().create("UserItemsSorter/name");
                    comparator = new NameComparator();
                    break;
                }
                case DATE_CREATED: {
//                    timeCounter = ServiceLocator.getTimeCounterCreator().create("UserItemsSorter/date created");
                    comparator = new DateCreatedComparator();
                    break;
                }
                case DATE_UPDATED: {
//                    timeCounter = ServiceLocator.getTimeCounterCreator().create("UserItemsSorter/date updated");
                    comparator = new DateUpdatedComparator(items);
                    break;
                }
                case RECORDS_NUMBER: {
//                    timeCounter = ServiceLocator.getTimeCounterCreator().create("UserItemsSorter/records number");
                    comparator = new RecordsNumberComparator(items);
                    break;
                }
                case ITEM_TYPE: {
//                    timeCounter = ServiceLocator.getTimeCounterCreator().create("UserItemsSorter/item type");
                    comparator = new ItemTypeComparator();
                    break;
                }
                case SITE: {
//                    timeCounter = ServiceLocator.getTimeCounterCreator().create("UserItemsSorter/site name");
                    comparator = new OwnerSiteComparator(items);
                    break;
                }
                default: {
                    throw new UnknownUserItemsSortTypeException("Unknown sort type: " + sortType);
                }
            }

            Collections.sort(items, comparator);
//            timeCounter.stop();
            if (DESC) {
                Collections.reverse(items);
            }
        } else {
            return null;
        }

        return items;
    }

    private class NameComparator implements Comparator<ItemManager> {

        public int compare(ItemManager item1, ItemManager item2) {
            return item1.getName().compareToIgnoreCase(item2.getName());
        }

    }

    private class OwnerSiteComparator implements Comparator<ItemManager> {

        private OwnerSiteComparator(List<ItemManager> itemManagers) {
            final Set<Integer> sitesId = new HashSet<Integer>();
            for (ItemManager itemManager : itemManagers) {
                sitesId.add(itemManager.getSiteId());
            }
            sitesNamesWithId = ServiceLocator.getPersistance().getSitesIdWithTitles(sitesId);
        }

        private String getSiteName(ItemManager itemManager) {
            String siteName = sitesNamesWithId.get(itemManager.getSiteId());
            if (siteName == null) {
                siteName = "none";
            }
            return siteName;
        }

        private final Map<Integer, String> sitesNamesWithId;


        public int compare(ItemManager item1, ItemManager item2) {
            return getSiteName(item1).compareTo(getSiteName(item2));
        }
    }

    private class ItemTypeComparator implements Comparator<ItemManager> {

        public int compare(ItemManager item1, ItemManager item2) {
            final ItemTypeManager manager1 = new ItemTypeManager(item1.getType());
            final ItemTypeManager manager2 = new ItemTypeManager(item2.getType());
            return manager1.getName().compareTo(manager2.getName());
        }

    }

    private class DateCreatedComparator implements Comparator<ItemManager> {

        public int compare(ItemManager item1, ItemManager item2) {
            return item1.getItemCreatedDate().compareTo(item2.getItemCreatedDate());
        }

    }

    private class RecordsNumberComparator implements Comparator<ItemManager> {

        private RecordsNumberComparator(List<ItemManager> itemManagers) {
            final Set<Integer> blogsId = new HashSet<Integer>();
            final Set<Integer> forumsId = new HashSet<Integer>();
            final Set<Integer> formsId = new HashSet<Integer>();
            for (ItemManager itemManager : itemManagers) {
                if (itemManager.getType() == ItemType.BLOG) {
                    blogsId.add(itemManager.getId());
                } else if (itemManager.getType() == ItemType.FORUM) {
                    forumsId.add(itemManager.getId());
                } else if (itemManager.getType().isFormType()) {
                    formsId.add(itemManager.getId());
                } else if (itemManager.getType() == ItemType.GALLERY) {
                    formsId.add(((DraftGallery) itemManager.getDraftItem()).getFormId1());
                }
            }

            final Persistance persistance = ServiceLocator.getPersistance();

            itemsRecordsCount.putAll(persistance.getBlogPostsCountByBlogs(blogsId));
            itemsRecordsCount.putAll(persistance.getSubForumsCountByForumsId(forumsId));
            itemsRecordsCount.putAll(persistance.getFilledFormsCountByFormsId(formsId));
        }

        private final Map<Integer, Integer> itemsRecordsCount = new HashMap<Integer, Integer>();


        private Integer getRecordsCount(ItemManager itemManager) {
            Integer recordsCount = null;
            if (itemManager.getType() == ItemType.BLOG || itemManager.getType() == ItemType.FORUM || itemManager.getType().isFormType()) {
                recordsCount = itemsRecordsCount.get(itemManager.getId());
            } else if (itemManager.getType() == ItemType.GALLERY) {
                recordsCount = itemsRecordsCount.get(((DraftGallery) itemManager.getDraftItem()).getFormId1());
            }
            if (recordsCount == null) {
                recordsCount = 0;
            }
            return recordsCount;
        }

        public int compare(ItemManager item1, ItemManager item2) {
            return getRecordsCount(item1).compareTo(getRecordsCount(item2));
        }

    }

    private class DateUpdatedComparator implements Comparator<ItemManager> {

        private DateUpdatedComparator(List<ItemManager> itemManagers) {
            final Set<Integer> blogsId = new HashSet<Integer>();
            final Set<Integer> forumsId = new HashSet<Integer>();
            final Set<Integer> formsId = new HashSet<Integer>();
            for (ItemManager itemManager : itemManagers) {
                if (itemManager.getType() == ItemType.BLOG) {
                    blogsId.add(itemManager.getId());
                } else if (itemManager.getType() == ItemType.FORUM) {
                    forumsId.add(itemManager.getId());
                } else if (itemManager.getType().isFormType()) {
                    formsId.add(itemManager.getId());
                } else if (itemManager.getType() == ItemType.GALLERY) {
                    formsId.add(((DraftGallery) itemManager.getDraftItem()).getFormId1());
                }
            }

            final Persistance persistance = ServiceLocator.getPersistance();

            itemsUpdateDate.putAll(persistance.getLastBlogPostsDate(blogsId));
            itemsUpdateDate.putAll(persistance.getLastForumPosts(forumsId));
            itemsUpdateDate.putAll(persistance.getLastFillFormDateByFormsId(formsId));
        }

        private final Map<Integer, Date> itemsUpdateDate = new HashMap<Integer, Date>();


        private Date getUpdateDate(ItemManager itemManager) {
            Date updateDate = null;
            if (itemManager.getType() == ItemType.BLOG || itemManager.getType() == ItemType.FORUM || itemManager.getType().isFormType()) {
                updateDate = itemsUpdateDate.get(itemManager.getId());
            } else if (itemManager.getType() == ItemType.GALLERY) {
                updateDate = itemsUpdateDate.get(((DraftGallery) itemManager.getDraftItem()).getFormId1());
            }
            if (updateDate == null) {
                updateDate = itemManager.getItemCreatedDate();
            }
            return updateDate;
        }

        public int compare(ItemManager item1, ItemManager item2) {
            return getUpdateDate(item1).compareTo(getUpdateDate(item2));
        }

    }

}
