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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.*;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.groups.GroupsTimeManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.start.Alter;
import com.shroggle.logic.statistics.DateInterval;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.CollectionUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceListener;
import org.hibernate.Session;

import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"unchecked", "JpaQlInspection"})
public class HibernatePersistance implements Persistance {

    public HibernatePersistance() {
        interceptor = new HibernateInterceptor();
        HibernateManager.configuration(interceptor);
    }

    public User getUserByEmail(final String email) {
        final Query query = HibernateManager.get().createQuery(
                "select user from users as user where user.email = :email").setParameter("email", email);
        final List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public List<Site> searchSites(final String siteTitle, final String siteUrlPrefix) {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where" +
                        " site.subDomain like '%'||:siteUrlPrefix||'%' and site.title like '%'||:siteTitle||'%'");
        query.setParameter("siteUrlPrefix", siteUrlPrefix);
        query.setParameter("siteTitle", siteTitle);
        return (List<Site>) query.getResultList();
    }

    public List<FilledForm> getFilledFormsByFormId(final int formId) {
        final Query query = HibernateManager.get().createQuery(
                "select filledForm from filledForms as filledForm where" +
                        " filledForm.formId = :formId");
        query.setParameter("formId", formId);
        return (List<FilledForm>) query.getResultList();
    }

    @Override
    public int getFilledFormsCountByFormId(int formId) {
        return ((BigInteger) HibernateManager.get().createNativeQuery("select count(formId) from filledForms where formId = " + formId).getSingleResult()).intValue();
    }

    @Override
    public Map<Integer, Integer> getFilledFormsCountByFormsId(Set<Integer> formsId) {
        if (formsId.isEmpty()) {
            return Collections.emptyMap();
        }
        final List<Object[]> formsIdWithFilledFormsCount = HibernateManager.get().createNativeQuery("select formId, count(formId) from filledForms where formId in(" + formsId.toString().replace("[", "").replace("]", "") + ") group  by formId;").getResultList();
        final Map<Integer, Integer> blogsIdWithBlogPostsCount = new HashMap<Integer, Integer>();
        for (Object[] object : formsIdWithFilledFormsCount) {
            blogsIdWithBlogPostsCount.put((Integer) object[0], ((BigInteger) object[1]).intValue());
        }
        return blogsIdWithBlogPostsCount;
    }

    public List<FilledForm> getFilledFormsByFormAndUserId(final int formId, final int userId) {
        final Query query = HibernateManager.get().createQuery(
                "select filledForm from filledForms as filledForm where" +
                        " filledForm.formId = :formId and filledForm.user.userId =:userId");
        query.setParameter("formId", formId);
        query.setParameter("userId", userId);
        return (List<FilledForm>) query.getResultList();
    }

    @Override
    public void putPage(final Page page) {
        HibernateManager.get().persist(page);
    }

    @Override
    public Widget getWidgetByCrossWidgetsId(Integer crossWidgetId, SiteShowOption siteShowOption) {
        final List<Widget> widgets = getWidgetsByCrossWidgetsId(Arrays.asList(crossWidgetId), siteShowOption);
        if (widgets != null && !widgets.isEmpty()) {
            return widgets.get(0);
        }
        return null;
    }

    @Override
    public List<Widget> getWidgetsByCrossWidgetsId(List<Integer> crossWidgetId, SiteShowOption siteShowOption) {
        if (siteShowOption == null) {
            return Collections.emptyList();
        }
        if (siteShowOption.isWork()) {
            return getWorkWidgetsByCrossWidgetsId(crossWidgetId);
        } else {
            return getDraftWidgetsByCrossWidgetsId(crossWidgetId);
        }
    }

    @Override
    public List<Widget> getWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption) {
        if (siteShowOption == null || sitesId == null || sitesId.isEmpty()) {
            return Collections.emptyList();
        }

        if (siteShowOption.isWork()) {
            return HibernateManager.get().createQuery("select widget from widgets as widget where " +
                    "widget.workPageSettings.page.site.siteId in (:sitesId)").setParameter("sitesId", sitesId).getResultList();
        } else {
            return HibernateManager.get().createQuery("select widget from widgets as widget where " +
                    "widget.draftPageSettings.page.site.siteId in (:sitesId)").setParameter("sitesId", sitesId).getResultList();
        }
    }

    @Override
    public List<Widget> getWorkWidgetsByCrossWidgetsId(List<Integer> crossWidgetId) {
        if (crossWidgetId == null || crossWidgetId.isEmpty()) {
            return Collections.emptyList();
        }
        return HibernateManager.get().createQuery("select widget from widgets as widget where widget.crossWidgetId in " +
                "(:crossWidgetId) and widget.workPageSettings is not null").setParameter("crossWidgetId", crossWidgetId).getResultList();
    }

    @Override
    public List<Widget> getDraftWidgetsByCrossWidgetsId(List<Integer> crossWidgetId) {
        if (crossWidgetId == null || crossWidgetId.isEmpty()) {
            return Collections.emptyList();
        }
        return HibernateManager.get().createQuery("select widget from widgets as widget where widget.crossWidgetId in " +
                "(:crossWidgetId) and widget.draftPageSettings is not null").setParameter("crossWidgetId", crossWidgetId).getResultList();
    }

    public List<WidgetItem> getGalleryDataWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption) {
        final List<Widget> widgetItems = getWidgetsBySitesId(sitesId, siteShowOption);
        final List<WidgetItem> galleryDataItems = new ArrayList<WidgetItem>();
        for (Widget widget : widgetItems) {
            if (widget.isWidgetItem() && ((WidgetItem) widget).getDraftItem() != null &&
                    ((WidgetItem) widget).getDraftItem().getItemType() == ItemType.GALLERY_DATA) {
                galleryDataItems.add((WidgetItem) widget);
            }
        }
        return galleryDataItems;
    }

    @Override
    public Icon getIcon(Integer iconId) {
        if (iconId == null) {
            return null;
        }
        return HibernateManager.get().find(Icon.class, iconId);
    }

    @Override
    public void putIcon(Icon icon) {
        if (icon == null) {
            return;
        }
        HibernateManager.get().persist(icon);
    }

    @Override
    public void removeIcon(Icon icon) {
        if (icon == null) {
            return;
        }
        final List<Site> sites = HibernateManager.get().createQuery("select site from sites as site where site.icon = :icon").setParameter("icon", icon).getResultList();
        for (Site site : sites) {
            site.setIcon(null);
        }
        HibernateManager.get().remove(icon);
    }

    @Override
    public void removeIcon(Integer iconId) {
        removeIcon(getIcon(iconId));
    }

    @Override
    public void putCSVDataExport(CSVDataExport csvDataExport) {
        HibernateManager.get().persist(csvDataExport);
        for (CSVDataExportField field : csvDataExport.getFields()) {
            if (field.getId() <= 0) {
                putCSVDataExportField(field);
            }
        }
    }

    @Override
    public CSVDataExport getCSVDataExport(Integer customizeDataExportId) {
        if (customizeDataExportId == null) {
            return null;
        }
        return HibernateManager.get().find(CSVDataExport.class, customizeDataExportId);
    }

    @Override
    public void putCSVDataExportField(CSVDataExportField field) {
        HibernateManager.get().persist(field);
    }

    @Override
    public CSVDataExportField getCSVDataExportField(Integer fieldId) {
        if (fieldId == null) {
            return null;
        }
        return HibernateManager.get().find(CSVDataExportField.class, fieldId);
    }

    @Override
    public void removeCSVDataExportField(CSVDataExportField field) {
        HibernateManager.get().remove(field);
    }

    @Override
    public void putCustomizeManageRecords(CustomizeManageRecords customizeManageRecords) {
        HibernateManager.get().persist(customizeManageRecords);
        for (CustomizeManageRecordsField field : customizeManageRecords.getFields()) {
            if (field.getId() <= 0) {
                putCustomizeManageRecordsField(field);
            }
        }
    }

    @Override
    public CustomizeManageRecords getCustomizeManageRecords(Integer customizeManageRecordsId) {
        if (customizeManageRecordsId == null) {
            return null;
        }
        return HibernateManager.get().find(CustomizeManageRecords.class, customizeManageRecordsId);
    }

    @Override
    public CustomizeManageRecords getCustomizeManageRecords(Integer formId, Integer userId) {
        final Query query = HibernateManager.get().createQuery("select customizeManageRecord from customizeManageRecords as " +
                "customizeManageRecord where customizeManageRecord.formId = :formId and customizeManageRecord.userId = " +
                ":userId").setParameter("formId", formId).setParameter("userId", userId);
        final List<CustomizeManageRecords> customizeManageRecordsList = query.getResultList();
        return customizeManageRecordsList.isEmpty() ? null : customizeManageRecordsList.get(0);
    }

    @Override
    public void putCustomizeManageRecordsField(CustomizeManageRecordsField field) {
        HibernateManager.get().persist(field);
    }

    @Override
    public CustomizeManageRecordsField getCustomizeManageRecordsField(Integer fieldId) {
        if (fieldId == null) {
            return null;
        }
        return HibernateManager.get().find(CustomizeManageRecordsField.class, fieldId);
    }

    @Override
    public void removeCustomizeManageRecordsField(final CustomizeManageRecordsField field) {
        HibernateManager.get().remove(field);
    }

    @Override
    public void putTaxRate(TaxRateUS taxRate) {
        HibernateManager.get().persist(taxRate);
    }

    @Override
    public void putItemSize(ItemSize itemSize) {
        HibernateManager.get().persist(itemSize);
    }

    @Override
    public ItemSize getItemSize(Integer itemSizeId) {
        if (itemSizeId == null) {
            return null;
        }
        return HibernateManager.get().find(ItemSize.class, itemSizeId);
    }

    @Override
    public void putFontsAndColors(FontsAndColors fontsAndColors) {
        HibernateManager.get().persist(fontsAndColors);
    }

    @Override
    public void removeFontsAndColors(FontsAndColors fontsAndColors) {
        if (fontsAndColors != null) {
            HibernateManager.get().remove(fontsAndColors);
        }
    }

    @Override
    public void removeBackground(Background background) {
        if (background != null) {
            HibernateManager.get().remove(background);
        }
    }

    @Override
    public void removeBorder(Border border) {
        if (border != null) {
            HibernateManager.get().remove(border);
        }
    }

    @Override
    public FontsAndColors getFontsAndColors(Integer fontsAndColorsId) {
        if (fontsAndColorsId == null) {
            return null;
        }
        return HibernateManager.get().find(FontsAndColors.class, fontsAndColorsId);
    }

    @Override
    public void putFontsAndColorsValue(FontsAndColorsValue fontsAndColorsValue) {
        HibernateManager.get().persist(fontsAndColorsValue);
    }

    @Override
    public Map<Integer, String> getSitesIdWithTitles(Set<Integer> sitesId) {
        final List<Object[]> sitesIdWithNamesRaw = HibernateManager.get().createNativeQuery("select siteId, title from sites where siteId in(" + sitesId.toString().replace("[", "").replace("]", "") + ");").getResultList();
        final Map<Integer, String> sitesIdWithNames = new HashMap<Integer, String>();
        for (Object[] objects : sitesIdWithNamesRaw) {
            sitesIdWithNames.put((Integer) objects[0], (String) objects[1]);
        }
        return sitesIdWithNames;
    }

    @Override
    public Date getLastUpdatedDate(int siteId) {
        final Date lastUpdatedDate = (Date) HibernateManager.get().createQuery("select max(updated) from draftPageSettings as" +
                " draftPageSettings where draftPageSettings.page.site.siteId = :siteId").setParameter("siteId", siteId).getSingleResult();
        return lastUpdatedDate != null ? lastUpdatedDate : getSite(siteId).getCreationDate();
    }

    public FormExportTask getFormExportTask(Integer formExportTaskId) {
        if (formExportTaskId == null) {
            return null;
        }
        return HibernateManager.get().find(FormExportTask.class, formExportTaskId);
    }

    public void putFormExportTask(FormExportTask formExportTask) {
        HibernateManager.get().persist(formExportTask);
    }

    public void removeFormExportTask(FormExportTask formExportTask) {
        HibernateManager.get().remove(formExportTask);
    }

    public List<FormExportTask> getFormExportTasksByFormId(int formId) {
        return HibernateManager.get().createQuery("select formExportTask from formExportTasks as formExportTask where formExportTask.formId = :formId").setParameter("formId", formId).getResultList();
    }

    public List<FormExportTask> getFormExportTasksBySiteId(int siteId) {
        return HibernateManager.get().createQuery("select formExportTask from formExportTasks as formExportTask," +
                " siteItems as item where item.siteId = :siteId and formExportTask.formId = item.id").setParameter("siteId", siteId).getResultList();
    }

    public List<FormExportTask> getAllFormExportTasks() {
        return HibernateManager.get().createQuery("select formExportTask from formExportTasks as formExportTask").getResultList();
    }

    @Override
    public Site getSiteByBrandedSubDomain(final String brandedSubDomain, final String brandedDomain) {
        final Query query = HibernateManager.get().createQuery(
                "select childSiteRegistration.id from workChildSiteRegistrations as childSiteRegistration " +
                        "where childSiteRegistration.brandedUrl = :brandedDomain");
        query.setParameter("brandedDomain", brandedDomain);

        final List<Integer> workChildSiteRegistrationIds = query.getResultList();

        if (workChildSiteRegistrationIds.size() > 0) {
            final Query siteQuery = HibernateManager.get().createQuery("select site from sites as site " +
                    "where site.brandedSubDomain = :brandedSubDomain " +
                    "and site.childSiteSettings.childSiteRegistration.id = :childSiteRegistrationId");
            siteQuery.setParameter("brandedSubDomain", brandedSubDomain);
            siteQuery.setParameter("childSiteRegistrationId", workChildSiteRegistrationIds.get(0));
            final List<Site> sites = siteQuery.getResultList();
            if (sites.size() > 0) {
                return sites.get(0);
            }
        }

        return null;
    }

    @Override
    public List<String> getImagesKeywordsBySite(final Integer siteId) {
        final Set<String> keywords = new TreeSet<String>();

        if (siteId != null) {
            final Query query = HibernateManager.get().createQuery(
                    "select image.keywords from images as image where image.siteId = :siteId order by image.keywords");
            query.setParameter("siteId", siteId);
            final List<String> imagesKeywords = query.getResultList();

            for (final String imageKeywords : imagesKeywords) {
                keywords.addAll(Arrays.asList(imageKeywords.split(",")));
            }
        }

        return new ArrayList<String>(keywords);
    }

    @Override
    public List<String> getImagesNamesBySite(final Integer siteId) {
        if (siteId != null) {
            final Query query = HibernateManager.get().createQuery(
                    "select image.name from images as image where image.siteId = :siteId");
            query.setParameter("siteId", siteId);
            return query.getResultList();
        }

        return new ArrayList<String>();
    }

    @Override
    public List<Image> getImagesByKeywords(final Integer siteId, final List<String> keywords) {
        final Set<Image> images = new TreeSet<Image>(new Comparator<Image>() {

            @Override
            public int compare(Image o1, Image o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

        });

        if (siteId != null) {
            if (keywords == null || keywords.isEmpty()) {
                images.addAll(getImagesByOwnerSiteId(siteId));
            } else {

                for (final String keyword : keywords) {
                    final Query query = HibernateManager.get().createQuery(
                            "select image from images as image where image.siteId = :siteId " +
                                    "and image.keywords like :keyword order by image.imageId");
                    query.setParameter("siteId", siteId);
                    query.setParameter("keyword", "%" + keyword + "%");

                    images.addAll(query.getResultList());
                }

            }
        }

        return new ArrayList<Image>(images);
    }

    @Override
    public void putHtml(final Html html) {
        HibernateManager.get().persist(html);
    }

    public List<Site> getPublishedBlueprints() {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.publicBlueprintsSettings.published is not null order by site.title");
        return query.getResultList();
    }

    public List<Site> getActiveBlueprints(BlueprintCategory blueprintCategory) {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.publicBlueprintsSettings.activated is not null" +
                        (blueprintCategory != null ? " and site.publicBlueprintsSettings.blueprintCategory = :blueprintCategory" : "") +
                        " order by site.title");
        if (blueprintCategory != null) {
            query.setParameter("blueprintCategory", blueprintCategory);
        }
        return query.getResultList();
    }


    public GoogleBaseDataExportMappedByFilledFormId getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(Integer filledFormId) {
        if (filledFormId == null) {
            return null;
        }
        final Query query = HibernateManager.get().createQuery(
                "select data from googleBaseDataExportMappedByFilledFormId as data where data.filledFormId = :filledFormId"
        );
        query.setParameter("filledFormId", filledFormId);
        final List<GoogleBaseDataExportMappedByFilledFormId> galleryies = query.getResultList();
        return galleryies.isEmpty() ? null : galleryies.get(0);
    }

    public void putGoogleBaseDataExportMappedByFilledFormId(GoogleBaseDataExportMappedByFilledFormId dataExportMappedByFilledFormId) {
        HibernateManager.get().persist(dataExportMappedByFilledFormId);
    }

    @Override
    public void removeAccessibleSettings(final AccessibleSettings accessibleSettings) {
        HibernateManager.get().remove(accessibleSettings);
    }

    public Page getPage(final Integer pageId) {
        if (pageId == null) {
            return null;
        }
        return HibernateManager.get().find(Page.class, pageId);
    }

    public Gallery getGalleryByOrderFormId(final Integer orderFormId) {
        if (orderFormId == null) {
            return null;
        }
        final Query query = HibernateManager.get().createQuery(
                "select gallery from galleries as gallery where gallery.paypalSetings.ordersFormId = :orderFormId"
        );
        query.setParameter("orderFormId", orderFormId);
        final List<Gallery> galleryies = query.getResultList();
        return galleryies.isEmpty() ? null : galleryies.get(0);
    }

    public void putUserOnSiteRight(final UserOnSiteRight userOnSiteRight) {
        HibernateManager.get().persist(userOnSiteRight);
    }

    public void removeCreditCard(final CreditCard creditCard) {
        creditCard.getUser().getCreditCards().remove(creditCard);
        final Query query = HibernateManager.get().createNativeQuery("update sitePaymentSettings set " +
                "creditCardId = null where creditCardId = " + creditCard.getCreditCardId());
        query.executeUpdate();
        HibernateManager.get().remove(creditCard);
    }

    public void putEmailUpdateRequest(EmailUpdateRequest emailUpdateRequest) {
        HibernateManager.get().persist(emailUpdateRequest);
    }

    public EmailUpdateRequest getEmailUpdateRequestById(String id) {
        return HibernateManager.get().find(EmailUpdateRequest.class, id);
    }

    public void putUser(User user) {
        HibernateManager.get().persist(user);
    }

    public void putFormItem(DraftFormItem formItem) {
        HibernateManager.get().persist(formItem);
    }

    public void putSlideShowImage(DraftSlideShowImage formItem) {
        HibernateManager.get().persist(formItem);
    }

    public void putWorkSlideShowImage(WorkSlideShowImage formItem) {
        HibernateManager.get().persist(formItem);
    }

    public void putFilledForm(FilledForm filledForm) {
        HibernateManager.get().persist(filledForm);
    }

    public void putFilledFormItem(FilledFormItem filledFormItem) {
        HibernateManager.get().persist(filledFormItem);
    }

    public Date getLastFillFormDateByFormId(final int formId) {
        final Query query = HibernateManager.get().createQuery(
                "select max(filledForm.fillDate) from filledForms as filledForm where filledForm.formId = :formId");
        query.setParameter("formId", formId);
        return (Date) query.getSingleResult();
    }

    @Override
    public Map<Integer, Date> getLastFillFormDateByFormsId(Set<Integer> blogsId) { // todo. Check this method. Tolik
        if (blogsId.isEmpty()) {
            return Collections.emptyMap();
        }
        final List<Object[]> filledFormsIdWithMaxFillDateRaw = HibernateManager.get().createNativeQuery("select formId, max(fillDate) from filledForms where formId in(" + blogsId.toString().replace("[", "").replace("]", "") + ") group  by formId;").getResultList();
        final Map<Integer, Date> filledFormsIdWithMaxFillDate = new HashMap<Integer, Date>();
        for (Object[] object : filledFormsIdWithMaxFillDateRaw) {
            filledFormsIdWithMaxFillDate.put((Integer) object[0], (Date) object[1]);
        }
        return filledFormsIdWithMaxFillDate;
    }

    public void putRegistrationForm(DraftRegistrationForm registrationForm) {
        HibernateManager.get().persist(registrationForm);
    }

    public void putCustomForm(DraftCustomForm customForm) {
        HibernateManager.get().persist(customForm);
    }

    public void putSubForum(SubForum subForum) {
        HibernateManager.get().persist(subForum);
    }

    public void putForumThread(ForumThread forumThread) {
        HibernateManager.get().persist(forumThread);
    }

    public void putForumPost(ForumPost forumPost) {
        HibernateManager.get().persist(forumPost);
    }

    public User getUserById(Integer userId) {
        if (userId == null) {
            return null;
        }
        return HibernateManager.get().find(User.class, userId);
    }

    public UserOnSiteRight getUserOnSiteRightById(final UserOnSiteRightId userOnSiteRightId) {
        return HibernateManager.get().find(UserOnSiteRight.class, userOnSiteRightId);
    }

    public UserOnSiteRight getUserOnSiteRightByUserAndSiteId(final Integer userId, final Integer siteId) {
        if (userId == null || siteId == null) {
            return null;
        }
        final Query query = HibernateManager.get().createQuery("select userOnSiteRights from " +
                "userOnSiteRights as userOnSiteRights where userOnSiteRights.id.site.siteId = :siteId and userOnSiteRights.id.user.userId = :userId");
        query.setParameter("userId", userId);
        query.setParameter("siteId", siteId);
        final List<UserOnSiteRight> userOnSiteRights = query.getResultList();
        return !userOnSiteRights.isEmpty() ? userOnSiteRights.get(0) : null;
    }


    public UserOnSiteRight getUserOnSiteRightByUserAndFormId(final User user, final Integer formId) {
        if (user == null || formId == null) {
            return null;
        }
        for (final UserOnSiteRight userOnUserRight : user.getUserOnSiteRights()) {
            for (Integer filledRegistrationFormId : userOnUserRight.getFilledRegistrationFormIds()) {
                final FilledForm filledRegistrationForm = getFilledFormById(filledRegistrationFormId);

                if (filledRegistrationForm.getFormId() == formId) {
                    return userOnUserRight;
                }
            }
        }
        return null;
    }

    public FilledForm getFilledRegistrationFormByUserAndFormId(final User user, final Integer formId) {
        if (user == null || formId == null) {
            return null;
        }
        for (final UserOnSiteRight userOnUserRight : user.getUserOnSiteRights()) {
            for (Integer filledRegistrationFormId : userOnUserRight.getFilledRegistrationFormIds()) {
                final FilledForm filledRegistrationForm = getFilledFormById(filledRegistrationFormId);

                if (filledRegistrationForm.getFormId() == formId) {
                    return filledRegistrationForm;
                }
            }
        }
        return null;
    }

    public ForumPost getForumPostById(int postId) {
        return HibernateManager.get().find(ForumPost.class, postId);
    }

    public void putPageVisitor(PageVisitor pageVisitor) {
        HibernateManager.get().persist(pageVisitor);
    }

    public void putAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption) {
        HibernateManager.get().persist(advancedSearchOption);
    }

    public void putVisit(Visit visit) {
        HibernateManager.get().persist(visit);
    }

    public void putIPNLog(IPNLog ipnLog) {
        HibernateManager.get().persist(ipnLog);
    }

    public void putVisitReferrer(VisitReferrer visitReferrer) {
        HibernateManager.get().persist(visitReferrer);
    }

    public void putIncomeSettings(IncomeSettings incomeSettings) {
        HibernateManager.get().persist(incomeSettings);
    }

    public void putSitePaymentSettings(SitePaymentSettings sitePaymentSettings) {
        HibernateManager.get().persist(sitePaymentSettings);
    }

    public SitePaymentSettings getSitePaymentSettingsById(int sitePaymentSettingsId) {
        return HibernateManager.get().find(SitePaymentSettings.class, sitePaymentSettingsId);
    }

    public void putCreditCard(CreditCard creditCard) {
        HibernateManager.get().persist(creditCard);
    }

    public void putManageVotesGallerySettings(DraftManageVotesSettings manageVotesGallerySettings) {
        HibernateManager.get().persist(manageVotesGallerySettings);
    }

    public void putFormFilterRule(DraftFormFilterRule formFilterRule) {
        HibernateManager.get().persist(formFilterRule);
    }

    public void putMenu(final Menu menu) {
        HibernateManager.get().persist(menu);
    }

    public void putFormFilter(DraftFormFilter formFilter) {
        HibernateManager.get().persist(formFilter);
    }

    @Override
    public List<BlogPost> getBlogPostsByBlog(final int blogId) {
        final Query query = HibernateManager.get().createQuery(
                "select blogPost from blogPosts blogPost where blogPost.blog.id = :blogId");
        return query.setParameter("blogId", blogId).getResultList();
    }

    @Override
    public int getBlogPostsCountByBlog(int blogId) {
        return ((BigInteger) HibernateManager.get().createNativeQuery("select count(blogId) from blogPosts where blogId = " + blogId).getSingleResult()).intValue();
    }

    @Override
    public Map<Integer, Integer> getBlogPostsCountByBlogs(Set<Integer> blogsId) {
        if (blogsId.isEmpty()) {
            return Collections.emptyMap();
        }
        final List<Object[]> blogsIdWithBlogPostsCountRaw = HibernateManager.get().createNativeQuery("select blogId, count(blogId) from blogPosts where blogId in(" + blogsId.toString().replace("[", "").replace("]", "") + ") group  by blogId;").getResultList();
        final Map<Integer, Integer> blogsIdWithBlogPostsCount = new HashMap<Integer, Integer>();
        for (Object[] object : blogsIdWithBlogPostsCountRaw) {
            blogsIdWithBlogPostsCount.put((Integer) object[0], ((BigInteger) object[1]).intValue());
        }
        return blogsIdWithBlogPostsCount;
    }

    public PageVisitor getPageVisitorById(Integer pageVisitorId) {
        if (pageVisitorId == null) {
            return null;
        }
        return HibernateManager.get().find(PageVisitor.class, pageVisitorId);
    }

    public DraftAdvancedSearchOption getAdvancedSearchOptionById(int advancedSearchOptionId) {
        return HibernateManager.get().find(DraftAdvancedSearchOption.class, advancedSearchOptionId);
    }

    public DraftForm getFormById(Integer formId) {
        if (formId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftForm.class, formId);
    }

    public DraftFormFilterRule getFormFilterRuleById(int formFilterRule) {
        return HibernateManager.get().find(DraftFormFilterRule.class, formFilterRule);
    }

    public DraftFormFilter getFormFilterById(Integer filterId) {
        if (filterId != null) {
            return HibernateManager.get().find(DraftFormFilter.class, filterId);
        } else {
            return null;
        }
    }

    public DraftFormFilter getFormFilterByNameAndUserId(String name, int userId) {
        final Query queryByUserRights = HibernateManager.get().createQuery(
                "select formFilter from formFilters as formFilter, forms as form, userOnSiteRights as userOnSiteRights " +
                        "where form = formFilter.form and userOnSiteRights.id.site.siteId = form.siteId " +
                        "and userOnSiteRights.active = true " +
                        "and userOnSiteRights.id.user.userId = :userId " +
                        "and formFilter.name = :name");
        queryByUserRights.setParameter("userId", userId);
        queryByUserRights.setParameter("name", name);

        List<DraftFormFilter> returnList = queryByUserRights.getResultList();
        return returnList.size() > 0 ? returnList.get(0) : null;
    }

    public DraftFormFilter getFormFilterByNameAndFormId(String name, int formId) {
        final Query queryByUserRights = HibernateManager.get().createQuery(
                "select formFilter from formFilters as formFilter " +
                        "where formFilter.form.id = :formId " +
                        "and formFilter.name = :name");
        queryByUserRights.setParameter("formId", formId);
        queryByUserRights.setParameter("name", name);

        List<DraftFormFilter> returnList = queryByUserRights.getResultList();
        return returnList.size() > 0 ? returnList.get(0) : null;
    }

    public FilledForm getFilledFormById(Integer filledFormId) {
        if (filledFormId == null) {
            return null;
        }
        return HibernateManager.get().find(FilledForm.class, filledFormId);
    }

    public FilledFormItem getFilledFormItemById(Integer filledFormItemId) {
        if (filledFormItemId == null) {
            return null;
        }
        return HibernateManager.get().find(FilledFormItem.class, filledFormItemId);
    }

    public List<FilledFormItem> getFilledFormItemByFormItemId(Integer formItemId) {
        if (formItemId == null) {
            return new ArrayList<FilledFormItem>();
        }
        Query query = HibernateManager.get().createQuery("select filledFormItem from filledFormItems as filledFormItem " +
                "where filledFormItem.formItemId = :formItemId").setParameter("formItemId", formItemId);
        return query.getResultList();
    }

    public Visit getVisitByPageIdAndUserId(final int pageId, final int pageVisitorId) {
        final Query query = HibernateManager.get().createQuery(
                "select visit from visits as visit " +
                        "where pageVisitor.pageVisitorId = :pageVisitorId and visitedPage.pageId = :pageId");
        query.setParameter("pageVisitorId", pageVisitorId);
        query.setParameter("pageId", pageId);
        final List<Visit> visits = query.getResultList();
        return visits.size() > 0 ? visits.get(0) : null;
    }

    public long getHitsForPages(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return 0;
        }

        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select sum(visit.visitCount) from visits as visit" +
                        " where visit.visitedPage.pageId in (:pageIds)" +
                        startDateQuery + endDateQuery);
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        return query.getSingleResult() != null ? (Long) query.getSingleResult() : 0;
    }

    public Map<Integer, Long> getHitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return new HashMap<Integer, Long>();
        }

        final Map<Integer, Long> returnMap = new HashMap<Integer, Long>();
        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select visit.visitedPage.pageId, sum(visit.visitCount) from visits as visit" +
                        " where visit.visitedPage.pageId in (:pageIds) " +
                        startDateQuery + endDateQuery +
                        " group by visit.visitedPage.pageId");
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        for (Object returnElement : query.getResultList()) {
            Object[] keyValuePair = (Object[]) returnElement;
            returnMap.put((Integer) keyValuePair[0], (Long) keyValuePair[1]);
        }

        return returnMap;
    }

    public Map<Integer, Long> getUniqueVisitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return new HashMap<Integer, Long>();
        }

        final Map<Integer, Long> returnMap = new HashMap<Integer, Long>();
        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select  visit.visitedPage.pageId, count(distinct visit.pageVisitor) from visits as visit" +
                        " where visit.visitedPage.pageId in (:pageIds)" +
                        startDateQuery + endDateQuery +
                        " group by visit.visitedPage.pageId");
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        for (Object returnElement : query.getResultList()) {
            Object[] keyValuePair = (Object[]) returnElement;
            returnMap.put((Integer) keyValuePair[0], (Long) keyValuePair[1]);
        }

        return returnMap;
    }

    public Map<String, Integer> getRefUrlsByPages(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return new HashMap<String, Integer>();
        }

        Map<String, Integer> returnMap = new HashMap<String, Integer>();
        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select visitReferrer.termOrUrl, visitReferrer.visitCount from visits as visit, visitReferrers as visitReferrer " +
                        "where visit.visitedPage.pageId in (:pageIds) and visitReferrer.visit.visitId = visit.visitId and visitReferrerType='URL'" +
                        startDateQuery + endDateQuery);
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        for (Object returnElement : query.getResultList()) {
            Object[] keyValuePair = (Object[]) returnElement;
            if (returnMap.containsKey((String) keyValuePair[0])) {
                returnMap.put((String) keyValuePair[0], returnMap.get((String) keyValuePair[0]) + (Integer) keyValuePair[1]);
                continue;
            }
            returnMap.put((String) keyValuePair[0], (Integer) keyValuePair[1]);
        }
        return returnMap;
    }

    public Map<String, Integer> getRefSearchTermsByPages(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return new HashMap<String, Integer>();
        }

        Map<String, Integer> returnMap = new HashMap<String, Integer>();
        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select visitReferrer.termOrUrl, visitReferrer.visitCount from visits as visit, visitReferrers as visitReferrer " +
                        "where visit.visitedPage.pageId in (:pageIds) and visitReferrer.visit.visitId = visit.visitId and visitReferrerType='SEARCH_TERM'" +
                        startDateQuery + endDateQuery);
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        for (Object returnElement : query.getResultList()) {
            Object[] keyValuePair = (Object[]) returnElement;
            if (returnMap.containsKey((String) keyValuePair[0])) {
                returnMap.put((String) keyValuePair[0], returnMap.get((String) keyValuePair[0]) + (Integer) keyValuePair[1]);
                continue;
            }
            returnMap.put((String) keyValuePair[0], (Integer) keyValuePair[1]);
        }
        return returnMap;
    }

    public long getUniqueVisitsCountForPages(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return 0;
        }

        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select count(distinct visit.pageVisitor) from visits as visit " +
                        "where visit.visitedPage.pageId in (:pageIds)" +
                        startDateQuery + endDateQuery);
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }
        return (Long) query.getSingleResult();
    }

    public long getOverallTimeForPages(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return 0;
        }

        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select sum(visit.overallTimeOfVisit) from visits as visit " +
                        "where visit.visitedPage.pageId in (:pageIds)" +
                        startDateQuery + endDateQuery);
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        return query.getSingleResult() != null ? (Long) query.getSingleResult() : 0;
    }

    public Map<Integer, Long> getOverallTimeForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        if (pageIds.size() == 0) {
            return new HashMap<Integer, Long>();
        }

        final Map<Integer, Long> returnMap = new HashMap<Integer, Long>();
        final String startDateQuery = dateInterval.getStartDate() != null ? " and visit.visitCreationDate >= :startDate " : "";
        final String endDateQuery = dateInterval.getEndDate() != null ? " and visit.visitCreationDate <= :endDate " : "";
        final Query query = HibernateManager.get().createQuery(
                "select visit.visitedPage.pageId, sum(visit.overallTimeOfVisit) from visits as visit" +
                        " where visit.visitedPage.pageId in (:pageIds)" +
                        startDateQuery + endDateQuery +
                        " group by visit.visitedPage.pageId");
        query.setParameter("pageIds", pageIds);
        if (dateInterval.getStartDate() != null) {
            query.setParameter("startDate", dateInterval.getStartDate());
        }
        if (dateInterval.getEndDate() != null) {
            query.setParameter("endDate", dateInterval.getEndDate());
        }

        for (Object returnElement : query.getResultList()) {
            Object[] keyValuePair = (Object[]) returnElement;
            returnMap.put((Integer) keyValuePair[0], (Long) keyValuePair[1]);
        }

        return returnMap;
    }

    public Site getSiteBySubDomain(final String subDomain) {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.subDomain = :subDomain").setParameter("subDomain", subDomain);
        List<Site> sites = query.getResultList();
        if (sites.isEmpty()) {
            return null;
        }
        return sites.get(0);
    }

    public int getThreadVotesCount(int threadId, int answerNumber) {
        final Query query = HibernateManager.get().createQuery(
                "select vote from forumVotes as vote where vote.thread.threadId = :threadId" +
                        " and vote.answerNumber = :answerNumber")
                .setParameter("threadId", threadId).setParameter("answerNumber", answerNumber);
        return query.getResultList().size();
    }

    public void putSite(Site site) {
        putAccessibleSettings(site.getAccessibleSettings());
        putSitePaymentSettings(site.getSitePaymentSettings());
        HibernateManager.get().persist(site);
    }

    public List<User> getVisitorsBySiteId(int siteId) {
        final Site site = getSite(siteId);
        final List<User> users = new ArrayList<User>();
        if (site != null) {
            for (final UserOnSiteRight visitorOnSiteRight : site.getUserOnSiteRights()) {
                if (visitorOnSiteRight.getFilledRegistrationFormIds() != null &&
                        !visitorOnSiteRight.getFilledRegistrationFormIds().isEmpty()) {
                    users.add(visitorOnSiteRight.getId().getUser());
                }
            }
        }
        return users;
    }

    public BlogPost getLastBlogPost(int blogId) {
        final Query query = HibernateManager.get().createQuery(
                "select blogPost from blogPosts as blogPost where blogPost.blog.id = :blogId and" +
                        " blogPost.creationDate = (select max(lastBlogPost.creationDate) from blogPosts as lastBlogPost where" +
                        " lastBlogPost.blog = blogPost.blog)");
        query.setParameter("blogId", blogId);
        List<BlogPost> lastBlogPosts = query.getResultList();
        return lastBlogPosts.isEmpty() ? null : lastBlogPosts.get(0);
    }

    @Override
    public Map<Integer, Date> getLastBlogPostsDate(Set<Integer> blogsId) {// todo. Check this method. Tolik
        if (blogsId.isEmpty()) {
            return Collections.emptyMap();
        }
        final List<Object[]> blogsIdWithMaxBlogPostDate = HibernateManager.get().createNativeQuery("select blogId, max(creationDate) from blogPosts where blogId in(" + blogsId.toString().replace("[", "").replace("]", "") + ") group  by blogId;").getResultList();
        final Map<Integer, Date> forumsIdWithBlogPostsCount = new HashMap<Integer, Date>();
        for (Object[] object : blogsIdWithMaxBlogPostDate) {
            forumsIdWithBlogPostsCount.put((Integer) object[0], (Date) object[1]);
        }
        return forumsIdWithBlogPostsCount;
    }

    public ForumPost getLastForumPost(int forumId) {
        final Query query = HibernateManager.get().createQuery(
                "select forumPost from forumPosts as forumPost where forumPost.thread.subForum.forum.id = :forumId and" +
                        " forumPost.dateCreated = (select max(lastForumPost.dateCreated) from forumPosts as lastForumPost where" +
                        " lastForumPost.thread.subForum.forum = forumPost.thread.subForum.forum)");
        query.setParameter("forumId", forumId);
        List<ForumPost> lastForumPosts = query.getResultList();
        return lastForumPosts.isEmpty() ? null : lastForumPosts.get(0);
    }

    @Override
    public Map<Integer, Date> getLastForumPosts(Set<Integer> forumsId) {// todo. Check this method. Tolik
        if (forumsId.isEmpty()) {
            return Collections.emptyMap();
        }
        final List<ForumPost> forumPosts = HibernateManager.get().createQuery(
                "select forumPost from forumPosts as forumPost where forumPost.thread.subForum.forum.id in(:forumsId) and" +
                        " forumPost.dateCreated = (select max(lastForumPost.dateCreated) from forumPosts as lastForumPost where" +
                        " lastForumPost.thread.subForum.forum = forumPost.thread.subForum.forum) group by forumPost.thread.subForum.forum.id").setParameter("forumsId", forumsId).getResultList();
        final Map<Integer, Date> forumsIdWithBlogPostsCount = new HashMap<Integer, Date>();
        for (ForumPost forumPost : forumPosts) {
            forumsIdWithBlogPostsCount.put(forumPost.getThread().getSubForum().getForum().getFormId(), forumPost.getDateCreated());
        }
        return forumsIdWithBlogPostsCount;
    }

    @Override
    public List<BlogPost> getBlogPosts(
            final int blogId, final Integer visitorId, final Integer blogPostId,
            final int start, final int count, Date notOlderThan) {
        final String queryByDate = notOlderThan != null ? "and blogPost.creationDate >= :notOlderThan " : "";
        final Query query;
        if (blogPostId == null) {
            query = HibernateManager.get().createQuery(
                    "select blogPost from blogPosts as blogPost " +
                            "where blogPost.blog.id = :blogId " +
                            "and (:visitorId is null " +
                            "or blogPost.text is not null " +
                            "or blogPost.visitorId = :visitorId) " +
                            queryByDate +
                            "order by blogPost.creationDate desc");
        } else {
            query = HibernateManager.get().createQuery(
                    "select blogPost from blogPosts as blogPost " +
                            "where blogPost.blog.id = :blogId " +
                            "and (:visitorId is null or " +
                            "blogPost.text is not null " +
                            "or blogPost.visitorId = :visitorId) " +
                            "and (blogPost.blogPostId <= :blogPostId) " +
                            queryByDate +
                            "order by blogPost.creationDate desc");
            query.setParameter("blogPostId", blogPostId);
        }
        query.setParameter("blogId", blogId);
        query.setParameter("visitorId", visitorId);
        if (notOlderThan != null) {
            query.setParameter("notOlderThan", notOlderThan);
        }
        query.setFirstResult(start);
        if (count > 0) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public int getBlogPostsBeforeByBlogAndUserId(
            final int blogId, final Integer visitorId, final int blogPostId) {
        final Query query = HibernateManager.get().createQuery(
                "select count(blogPost) from blogPosts as blogPost " +
                        "where blogPost.blog.id = :blogId " +
                        "and (:visitorId is null or " +
                        "blogPost.text is not null " +
                        "or blogPost.visitorId = :visitorId) " +
                        "and (blogPost.creationDate < (select thisBlogPost.creationDate " +
                        "from blogPosts as thisBlogPost " +
                        "where thisBlogPost.blogPostId = :blogPostId))");
        query.setParameter("blogPostId", blogPostId);
        query.setParameter("blogId", blogId);
        query.setParameter("visitorId", visitorId);
        final Long count = (Long) query.getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    @Override
    public int getPrevOrNextBlogPostId(
            final int blogId, final Integer visitorId, final int blogPostId, final boolean prev) {
        final Query query = HibernateManager.get().createQuery(
                "select " + (prev ? "max(blogPost.blogPostId)" : "min(blogPost.blogPostId)") + " from blogPosts as blogPost " +
                        "where blogPost.blog.id = :blogId " +
                        "and (:visitorId is null or " +
                        "blogPost.text is not null " +
                        "or blogPost.visitorId = :visitorId) " +
                        "and (blogPost.creationDate " + (prev ? "<" : ">") + " (select thisBlogPost.creationDate " +
                        "from blogPosts as thisBlogPost " +
                        "where thisBlogPost.blogPostId = :blogPostId))");
        query.setParameter("blogPostId", blogPostId);
        query.setParameter("blogId", blogId);
        query.setParameter("visitorId", visitorId);
        final Integer count = (Integer) query.getSingleResult();
        return count != null ? count : 0;
    }

    @Override
    public int getBlogPostsAfterByBlogAndUserId(int blogId, Integer visitorId, int blogPostId) {
        final Query query = HibernateManager.get().createQuery(
                "select count(blogPost) from blogPosts as blogPost " +
                        "where blogPost.blog.id = :blogId " +
                        "and (:visitorId is null or " +
                        "blogPost.text is not null " +
                        "or blogPost.visitorId = :visitorId) " +
                        "and (blogPost.creationDate > (select thisBlogPost.creationDate " +
                        "from blogPosts as thisBlogPost " +
                        "where thisBlogPost.blogPostId = :blogPostId))");
        query.setParameter("blogPostId", blogPostId);
        query.setParameter("blogId", blogId);
        query.setParameter("visitorId", visitorId);
        final Long count = (Long) query.getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    public IncomeSettings getIncomeSettingsById(int incomeSettingsId) {
        return HibernateManager.get().find(IncomeSettings.class, incomeSettingsId);
    }

    public DraftFormItem getFormItemById(Integer formItemId) {
        if (formItemId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftFormItem.class, formItemId);
    }

    public boolean isRegistrationUsedOnAnySiteAsDefault(Integer formId) {
        if (formId == null) {
            return false;
        }

        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site " +
                        "where site.defaultFormId = :formId");
        query.setParameter("formId", formId);
        final int sites = query.getResultList().size();
        return sites > 0;
    }

    public CreditCard getCreditCardById(Integer creditCardId) {
        if (creditCardId == null) {
            return null;
        }
        return HibernateManager.get().find(CreditCard.class, creditCardId);
    }

    public DraftContactUs getContactUsById(final int contactUsId) {
        return HibernateManager.get().find(DraftContactUs.class, contactUsId);
    }

    public DraftChildSiteRegistration getChildSiteRegistrationById(final Integer childSiteRegistrationId) {
        if (childSiteRegistrationId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftChildSiteRegistration.class, childSiteRegistrationId);
    }

    public ChildSiteSettings getChildSiteSettingsById(Integer childSiteSettingsId) {
        if (childSiteSettingsId == null) {
            return null;
        }
        return HibernateManager.get().find(ChildSiteSettings.class, childSiteSettingsId);
    }

    public List<Image> getImagesByOwnerSiteId(int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select image from images as image where image.siteId = :siteId");
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    public Image getImageByNameAndSiteId(String name, int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select image from images as image " +
                        "where image.name = :name and image.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            //Do not change to get single result we may got a couple of images with the same name.
            return (Image) query.getResultList().get(0);
        }
        return null;
    }

    public List<Integer> getSitesThatContainGalleriesWithEcommerce(final List<Integer> siteIds) {
        if (siteIds == null || siteIds.isEmpty()) {
            return new ArrayList<Integer>();
        }

        final Query query = HibernateManager.get().createQuery(
                "select distinct absractItem.siteId from galleries as absractItem where absractItem.siteId in (:siteIds) and" +
                        " absractItem.paypalSetings.enable = true");
        query.setParameter("siteIds", siteIds);
        return query.getResultList();
    }

    public List<UserOnSiteRight> getNotActiveUserOnSiteRightsByUserAndInvitedUser(
            final int userId, final int invitedUserId) {
        final Query query = HibernateManager.get().createQuery(
                "select invitedUserOnSiteRight from userOnSiteRights as invitedUserOnSiteRight " +
                        "where invitedUserOnSiteRight.active = false " +
                        "and invitedUserOnSiteRight.id.user.userId = :invitedUserId " +
                        "and invitedUserOnSiteRight.id.site.siteId in (" +
                        "select site from sites as site, userOnSiteRights as userOnSiteRight " +
                        "where userOnSiteRight.id.site.siteId = site.siteId " +
                        "and userOnSiteRight.active = true " +
                        "and userOnSiteRight.id.user.userId = :userId)");
        query.setParameter("userId", userId);
        query.setParameter("invitedUserId", invitedUserId);
        return query.getResultList();
    }

    public List<FilledForm> getUserPurchasesOnSite(int userId, int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select filledForm from filledForms as filledForm, customForms as customForm " +
                        "where filledForm.user.userId = :userId " +
                        "and customForm.siteId = :siteId " +
                        "and customForm.id = filledForm.formId " +
                        "and filledForm.type = 'ORDER_FORM'");
        query.setParameter("userId", userId);
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    public List<Site> getAllSites() {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site");
        return query.getResultList();
    }

    @Override
    public List<Site> getSitesWithNotEmptyIncomeSettings() {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.incomeSettings is not null and site.incomeSettings.sum > 0");
        return query.getResultList();
    }

    public List<Site> getChildSites() {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.childSiteSettings is not null");
        return query.getResultList();
    }

    public List<Site> getSitesConnectedToCreditCard(int creditCardId) {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.sitePaymentSettings.creditCard is not null and " +
                        "site.sitePaymentSettings.creditCard.creditCardId = :creditCardId");
        query.setParameter("creditCardId", creditCardId);
        return query.getResultList();
    }

    public void putSiteOnItem(final SiteOnItem siteOnItem) {
        HibernateManager.get().persist(siteOnItem);
    }

    public SiteOnItem getSiteOnItemRightBySiteIdItemIdAndType(
            final int siteId, final int itemId, final ItemType type) {
        final Query query = HibernateManager.get().createQuery(
                "select siteOnItemRight from siteOnSiteItems as siteOnItemRight " +
                        "where siteOnItemRight.id.site.siteId = :siteId " +
                        "and siteOnItemRight.id.item.id = :itemId");
        query.setParameter("siteId", siteId);
        query.setParameter("itemId", itemId);
        if (query.getResultList().size() > 0) {
            return (SiteOnItem) query.getSingleResult();
        }
        return null;
    }

    /**
     * Why we don't use single hql with or? Because it's bug in current hibernate version
     * it's change hql (e1 and e2) or (e3 and e4) on sql e1 and e2 or e3 and e4, and it's bad.
     *
     * @param siteOnItemRight - right for remove
     */
    public void removeSiteOnItemRight(final SiteOnItem siteOnItemRight) {
        final List<Integer> widgetItemIds = new ArrayList<Integer>();

        widgetItemIds.addAll(HibernateManager.get().createQuery(
                "select widgetItem.widgetId from widgetItems as widgetItem " +
                        "where widgetItem.draftItem = :draftItem and " +
                        "widgetItem.draftPageSettings is not null and widgetItem.draftPageSettings.page.site = :site")
                .setParameter("draftItem", siteOnItemRight.getItem())
                .setParameter("site", siteOnItemRight.getSite()).getResultList());

        widgetItemIds.addAll(HibernateManager.get().createQuery(
                "select widgetItem.widgetId from widgetItems as widgetItem " +
                        "where widgetItem.draftItem = :draftItem and " +
                        "widgetItem.workPageSettings is not null and widgetItem.workPageSettings.page.site = :site")
                .setParameter("draftItem", siteOnItemRight.getItem())
                .setParameter("site", siteOnItemRight.getSite()).getResultList());

        if (widgetItemIds.size() > 0) {
            HibernateManager.get().createQuery(
                    "delete from widgets as widget where widget.widgetId in (:widgetItemIds)")
                    .setParameter("widgetItemIds", widgetItemIds).executeUpdate();
        }

        HibernateManager.get().remove(siteOnItemRight);
    }

    public void putChildSiteSettings(ChildSiteSettings childSiteSettings) {
        putSitePaymentSettings(childSiteSettings.getSitePaymentSettings());
        HibernateManager.get().persist(childSiteSettings);
    }

    public void putBlogPost(BlogPost blogPost) {
        HibernateManager.get().persist(blogPost);
    }

    public void putContactUs(DraftContactUs contactUs) {
        HibernateManager.get().persist(contactUs);
    }

    public DraftContactUs getContactUsByNameAndSiteId(final String contactUsName, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select contactUs from contactUs as contactUs " +
                        "where contactUs.name = :name and contactUs.siteId = :siteId")
                .setParameter("name", contactUsName)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftContactUs) query.getSingleResult();
        }
        return null;
    }

    public BlogPost getBlogPostById(final int blogPostId) {
        return HibernateManager.get().find(BlogPost.class, blogPostId);
    }

    public Comment getCommentById(final int commentId) {
        return HibernateManager.get().find(Comment.class, commentId);
    }

    public void putComment(final Comment comment) {
        HibernateManager.get().persist(comment);
    }

    public List<SubForum> getSubForumsByForumId(final int forumId) {
        final Query query = HibernateManager.get().createQuery(
                "select subForum from subforums subForum where subForum.forum.id = :forumId");
        return query.setParameter("forumId", forumId).getResultList();
    }

    @Override
    public int getSubForumsCountByForumId(int forumId) {
        return ((BigInteger) HibernateManager.get().createNativeQuery("select count(forumId) from subforums where forumId = " + forumId).getSingleResult()).intValue();
    }

    @Override
    public Map<Integer, Integer> getSubForumsCountByForumsId(Set<Integer> forumsId) {
        if (forumsId.isEmpty()) {
            return Collections.emptyMap();
        }
        final List<Object[]> forumsWithBlogPostsCountRaw = HibernateManager.get().createNativeQuery("select forumId, count(forumId) from subforums where forumId in(" + forumsId.toString().replace("[", "").replace("]", "") + ") group  by forumId;").getResultList();
        final Map<Integer, Integer> forumsIdWithBlogPostsCount = new HashMap<Integer, Integer>();
        for (Object[] object : forumsWithBlogPostsCountRaw) {
            forumsIdWithBlogPostsCount.put((Integer) object[0], ((BigInteger) object[1]).intValue());
        }
        return forumsIdWithBlogPostsCount;
    }

    public List<ForumThread> getForumThreads(final int subForumId) {
        return HibernateManager.get().find(SubForum.class, subForumId).getForumThreads();
    }

    public List<ForumPost> getForumPosts(int forumThreadId) {
        return HibernateManager.get().find(ForumThread.class, forumThreadId).getForumPosts();
    }

    public void putVote(ForumPollVote vote) {
        HibernateManager.get().persist(vote);
    }

    public ForumPollVote getForumThreadVoteByRespondentIdAndThreadId(String respondentId, int threadId) {
        Query query = HibernateManager.get().createQuery(
                "select vote from forumVotes as vote where vote.respondentId = :respondentId and threadId = :threadId")
                .setParameter("respondentId", respondentId)
                .setParameter("threadId", threadId);
        if (!query.getResultList().isEmpty()) {
            return (ForumPollVote) query.getSingleResult();
        }
        return null;
    }

    public void putWidget(final Widget widget) {
        HibernateManager.get().persist(widget);
    }

    public Site getSite(final Integer siteId) {
        if (siteId == null) {
            return null;
        }
        return HibernateManager.get().find(Site.class, siteId);
    }

    public SlideShowImage getSlideShowImageById(final Integer slideShowImageId) {
        if (slideShowImageId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftSlideShowImage.class, slideShowImageId);
    }

    @Override
    public String getSiteTitleBySiteId(int siteId) {
        final List<String> siteName = HibernateManager.get().createNativeQuery("select title from sites where siteId = " + siteId).getResultList();
        return !siteName.isEmpty() && siteName.get(0) != null ? siteName.get(0) : "none";
    }

    public DraftRegistrationForm getRegistrationFormById(final int formId) {
        return HibernateManager.get().find(DraftRegistrationForm.class, formId);
    }

    public DraftCustomForm getCustomFormById(final int formId) {
        return HibernateManager.get().find(DraftCustomForm.class, formId);
    }

    public DraftForum getForumByNameAndSiteId(final String forumName, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select forum from forums as forum where forum.name = :name and forum.siteId = :siteId")
                .setParameter("name", forumName)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftForum) query.getSingleResult();
        }
        return null;
    }

    public DraftMenu getMenuByNameAndSiteId(String name, int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select menu from menus as menu where menu.name = :name and menu.siteId = :siteId")
                .setParameter("name", name).setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftMenu) query.getSingleResult();
        }
        return null;
    }

    public DraftRegistrationForm getRegistrationFormByNameAndSiteId(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select registrationForm from registrationForms as registrationForm " +
                        "where registrationForm.name = :name and registrationForm.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftRegistrationForm) query.getSingleResult();
        }
        return null;
    }

    public DraftSlideShow getSlideShowByNameAndSiteId(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select slideShow from slideShows as slideShow " +
                        "where slideShow.name = :name and slideShow.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftSlideShow) query.getSingleResult();
        }
        return null;
    }

    public DraftShoppingCart getShoppingCartByNameAndSiteId(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select shoppingCart from draftShoppingCarts as shoppingCart " +
                        "where shoppingCart.name = :name and shoppingCart.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftShoppingCart) query.getResultList().get(0);
        }
        return null;
    }

    public DraftPurchaseHistory getPurchaseHistoryByNameAndSiteId(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select purchaseHistory from draftPurchaseHistory as purchaseHistory " +
                        "where purchaseHistory.name = :name and purchaseHistory.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftPurchaseHistory) query.getSingleResult();
        }
        return null;
    }

    public DraftAdvancedSearch getDraftAdvancedSearch(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select advancedSearch from advancedSearches as advancedSearch " +
                        "where advancedSearch.name = :name and advancedSearch.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftAdvancedSearch) query.getSingleResult();
        }
        return null;
    }

    public DraftChildSiteRegistration getChildSiteRegistrationFormByNameAndSiteId(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select childSiteRegistration from childSiteRegistrations as childSiteRegistration " +
                        "where childSiteRegistration.name = :name and childSiteRegistration.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftChildSiteRegistration) query.getSingleResult();
        }
        return null;
    }

    public DraftCustomForm getCustomFormByNameAndSiteId(final String name, final int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select customForm from customForms as customForm " +
                        "where customForm.name = :name and customForm.siteId = :siteId")
                .setParameter("name", name)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftCustomForm) query.getSingleResult();
        }
        return null;
    }

    public DraftBlogSummary getBlogSummaryByNameAndSiteId(String name, int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select blogSummary from blogSummaries as blogSummary " +
                        "where blogSummary.name = :name and blogSummary.siteId = :siteId");
        query.setParameter("name", name);
        query.setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftBlogSummary) query.getSingleResult();
        }
        return null;
    }

    public DraftAdminLogin getAdminLoginByNameAndSiteId(String name, int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select login from adminLogins as login " +
                        "where login.name = :name and login.siteId = :siteId");
        query.setParameter("name", name);
        query.setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftAdminLogin) query.getSingleResult();
        }
        return null;
    }

    @Override
    public DraftTaxRatesUS getTaxRatesByNameAndSiteId(String taxRateName, int siteId) {
        Query query = HibernateManager.get().createQuery(
                "select taxRate from draftTaxRates as taxRate " +
                        "where taxRate.name = :name and taxRate.siteId = :siteId");
        query.setParameter("name", taxRateName);
        query.setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftTaxRatesUS) query.getSingleResult();
        }
        return null;
    }


    public List<DraftMenu> getMenusBySiteId(int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select menu from menus as menu where menu.siteId = :siteId");
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    public List<DraftMenu> getMenusWithDefaultStructureBySiteId(int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select menu from menus as menu where menu.siteId = :siteId and menu.menuStructure = :menuStructure");
        query.setParameter("siteId", siteId);
        query.setParameter("menuStructure", MenuStructureType.DEFAULT);
        return query.getResultList();
    }

    public DraftMenu getMenuById(Integer menuId) {
        if (menuId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftMenu.class, menuId);
    }

    public DraftBlog getBlogByNameAndSiteId(final String blogName, final int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select blog from blogs as blog where blog.name = :name and blog.siteId = :siteId")
                .setParameter("name", blogName)
                .setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftBlog) query.getSingleResult();
        }
        return null;
    }

    public SubForum getSubForumById(int subForumId) {
        return HibernateManager.get().find(SubForum.class, subForumId);
    }

    public ForumThread getForumThreadById(int threadId) {
        return HibernateManager.get().find(ForumThread.class, threadId);
    }

    @Override
    public Page getPageByOwnDomainName(final String ownDomainName) {
        final List<WorkPageSettings> workPageSettingsList = HibernateManager.get().createQuery("select settings from " +
                "workPageSettings as settings where settings.ownDomainName = :ownDomainName").setParameter("ownDomainName", ownDomainName).getResultList();
        final WorkPageSettings workPageSettings = workPageSettingsList.isEmpty() ? null : workPageSettingsList.get(0);
        if (workPageSettings != null) {
            return workPageSettings.getPage();
        }
        final List<DraftPageSettings> draftPageSettingsList = HibernateManager.get().createQuery("select settings from " +
                "draftPageSettings as settings where settings.ownDomainName = :ownDomainName").setParameter("ownDomainName", ownDomainName).getResultList();
        final DraftPageSettings draftPageSettings = draftPageSettingsList.isEmpty() ? null : draftPageSettingsList.get(0);
        if (draftPageSettings != null) {
            return draftPageSettings.getPage();
        }
        return null;
    }

    @Override
    public Page getPageByUrlAndAndSiteIgnoreUrlCase(String url, final int siteId) {
        final String query = "select settings from pageSettingsTable as settings where settings.page.site.siteId = :siteId and lower(settings.url) = lower(:url)";

        final List<WorkPageSettings> workPageSettingsList = HibernateManager.get().createQuery(query.replace("pageSettingsTable", "workPageSettings"))
                .setParameter("url", url).setParameter("siteId", siteId).getResultList();
        final WorkPageSettings workPageSettings = workPageSettingsList.isEmpty() ? null : workPageSettingsList.get(0);
        if (workPageSettings != null) {
            return workPageSettings.getPage();
        }
        final List<DraftPageSettings> draftPageSettingsList = HibernateManager.get().createQuery(query.replace("pageSettingsTable", "draftPageSettings"))
                .setParameter("url", url).setParameter("siteId", siteId).getResultList();
        final DraftPageSettings draftPageSettings = draftPageSettingsList.isEmpty() ? null : draftPageSettingsList.get(0);
        if (draftPageSettings != null) {
            return draftPageSettings.getPage();
        }

        return null;
    }

    public Page getPageByNameAndSite(final String name, final int siteId) {
        final List<WorkPageSettings> workPageSettingsList = HibernateManager.get().createQuery("select settings from " +
                "workPageSettings as settings where settings.name = :name and " +
                "settings.page.site.siteId = :siteId").setParameter("name", name).setParameter("siteId", siteId).getResultList();
        final WorkPageSettings workPageSettings = workPageSettingsList.isEmpty() ? null : workPageSettingsList.get(0);
        if (workPageSettings != null) {
            return workPageSettings.getPage();
        }
        final List<DraftPageSettings> draftPageSettingsList = HibernateManager.get().createQuery("select settings from " +
                "draftPageSettings as settings where settings.name = :name and " +
                "settings.page.site.siteId = :siteId").setParameter("name", name).setParameter("siteId", siteId).getResultList();
        final DraftPageSettings draftPageSettings = draftPageSettingsList.isEmpty() ? null : draftPageSettingsList.get(0);
        if (draftPageSettings != null) {
            return draftPageSettings.getPage();
        }
        return null;
    }

    public Widget getWidget(Integer widgetId) {
        if (widgetId == null) {
            return null;
        }
        return HibernateManager.get().find(Widget.class, widgetId);
    }


    private Widget selectWidgetByCrossWidgetId(final List<Widget> widgets, final int crossWidgetId) {
        if (widgets != null && widgets.size() > 0) {
            for (Widget widget : widgets) {
                if (widget.getCrossWidgetId() == crossWidgetId) {
                    return widget;
                }
            }
        }
        return null;
    }

    public Widget getFirstWidgetByItemId(int itemId) {
        final Query query = HibernateManager.get().createQuery(
                "select widget from widgets as widget where widget.draftItem is not null and " +
                        "widget.draftItem.id = :itemId");
        query.setParameter("itemId", itemId);

        return query.getResultList().isEmpty() ? null : (Widget) query.getResultList().get(0);
    }

    public void removeWidget(Widget widget) {
        HibernateManager.get().remove(widget);
    }

    public void removePost(ForumPost forumPost) {
        forumPost.getThread().getForumPosts().remove(forumPost);
        HibernateManager.get().remove(forumPost);
    }

    public void removeFormFilter(DraftFormFilter formFilter) {
        HibernateManager.get().remove(formFilter);
    }

    public void removeFormFilterRule(DraftFormFilterRule formFilterRule) {
        formFilterRule.getFormFilter().removeRule(formFilterRule);
        HibernateManager.get().remove(formFilterRule);
    }

    public void removeThread(ForumThread forumThread) {
        forumThread.getSubForum().getForumThreads().remove(forumThread);
        HibernateManager.get().remove(forumThread);
    }

    public void removeSubForum(SubForum subForum) {
        subForum.getForum().getSubForums().remove(subForum);
        HibernateManager.get().remove(subForum);
    }

    public void removeSlideShowImage(SlideShowImage slideShowImage) {
        slideShowImage.getSlideShow().getImages().remove(slideShowImage);
        HibernateManager.get().remove(slideShowImage);
    }

    public void removeFormItem(DraftFormItem formItem) {
        formItem.getForm().getFormItems().remove(formItem);
        HibernateManager.get().remove(formItem);
    }

    public void removeFilledForm(FilledForm filledForm) {
        HibernateManager.get().remove(filledForm);
    }

    public Image getImageById(Integer imageId) {
        if (imageId == null) {
            return null;
        }
        return HibernateManager.get().find(Image.class, imageId);
    }

    public List<DraftGallery> getGalleriesByFormId(Integer formId) {
        if (formId == null) {
            return new ArrayList<DraftGallery>();
        }
        final Query query = HibernateManager.get().createQuery("select gallery from galleries as gallery" +
                " where gallery.formId1 = :formId").setParameter("formId", formId);
        return query.getResultList();
    }

    public BackgroundImage getBackgroundImageById(int backgroundImageId) {
        return HibernateManager.get().find(BackgroundImage.class, backgroundImageId);
    }

    public MenuImage getMenuImageById(Integer menuImageId) {
        if (menuImageId == null) {
            return null;
        }
        return HibernateManager.get().find(MenuImage.class, menuImageId);
    }

    public ImageForVideo getImageForVideoById(int imageId) {
        return HibernateManager.get().find(ImageForVideo.class, imageId);
    }

    public FormFile getFormFileById(Integer formFileId) {
        if (formFileId == null) {
            return null;
        }
        return HibernateManager.get().find(FormFile.class, formFileId);
    }

    public List<FormFile> getFormFiles() {
        Query query = HibernateManager.get().createQuery("select files from formFiles as files");
        return query.getResultList();
    }

    public void putFormFile(FormFile formFile) {
        HibernateManager.get().persist(formFile);
    }

    public void putImageFile(ImageFile imageFile) {
        HibernateManager.get().persist(imageFile);
    }

    public ImageFile getImageFileById(int imageFileId) {
        return HibernateManager.get().find(ImageFile.class, imageFileId);
    }

    public List<ImageFile> getImageFilesByTypeAndSiteId(final ImageFileType type, final Integer siteId) {
        final List<ImageFile> imageFiles = new ArrayList<ImageFile>();
        if (type == null || siteId == null) {
            return imageFiles;
        }
        final List<ImageFile> tempImageFiles = getImageFilesBySiteId(siteId);
        for (ImageFile imageFile : tempImageFiles) {
            if (imageFile.getImageFileType().equals(type)) {
                imageFiles.add(imageFile);
            }
        }
        return imageFiles;
    }

    public List<ImageFile> getImageFilesBySiteId(final int siteId) {
        final Query qery = HibernateManager.get().createQuery("select imageFile from imageFiles as imageFile where " +
                "imageFile.siteId = :siteId").setParameter("siteId", siteId);
        return qery.getResultList();
    }

    public void removeImageFile(ImageFile imageFile) {
        HibernateManager.get().remove(imageFile);
    }

    public void removeFormFile(final FormFile formFile) {
        HibernateManager.get().remove(formFile);
    }

    public void removeFilledFormItem(FilledFormItem filledFormItem) {
        HibernateManager.get().remove(filledFormItem);
    }

    public void putImage(Image image) {
        HibernateManager.get().persist(image);
    }

    public void putBackgroundImage(BackgroundImage image) {
        HibernateManager.get().persist(image);
    }

    public void putMenuImage(MenuImage image) {
        HibernateManager.get().persist(image);
    }

    public void putImageForVideo(ImageForVideo image) {
        HibernateManager.get().persist(image);
    }


    public List<BackgroundImage> getBackgroundImagesBySiteId(Integer siteId) {
        final List<BackgroundImage> backgroundImages = new ArrayList<BackgroundImage>();
        if (siteId != null) {
            final Query query = HibernateManager.get().createQuery(
                    "select backgroundImage from backgroundImages as backgroundImage where backgroundImage.siteId = :siteId");
            query.setParameter("siteId", siteId);
            backgroundImages.addAll(query.getResultList());
        }
        return backgroundImages;
    }

    public List<ImageForVideo> getImagesForVideoBySiteId(final Integer siteId) {
        final List<ImageForVideo> userImagesForVideo = new ArrayList<ImageForVideo>();
        if (siteId != null) {
            final Query query = HibernateManager.get().createQuery(
                    "select imageForVideo from imagesForVideo as imageForVideo where imageForVideo.siteId = :siteId");
            query.setParameter("siteId", siteId);
            userImagesForVideo.addAll(query.getResultList());
        }
        return userImagesForVideo;
    }

    public void putStyle(Style style) {
        if (style != null) {
            HibernateManager.get().persist(style);
        }
    }

    public Style getStyleById(int styleId) {
        return HibernateManager.get().find(Style.class, styleId);
    }

    public void removeStyle(Style style) {
        if (style != null) {
            HibernateManager.get().remove(style);
        }
    }

    public void putBorder(Border border) {
        if (border.getBorderWidth().getStyleId() <= 0) {
            putStyle(border.getBorderWidth());
        }
        if (border.getBorderStyle().getStyleId() <= 0) {
            putStyle(border.getBorderStyle());
        }
        if (border.getBorderColor().getStyleId() <= 0) {
            putStyle(border.getBorderColor());
        }
        if (border.getBorderPadding().getStyleId() <= 0) {
            putStyle(border.getBorderPadding());
        }
        if (border.getBorderMargin().getStyleId() <= 0) {
            putStyle(border.getBorderMargin());
        }
        HibernateManager.get().persist(border);
    }

    @Override
    public void putBackground(Background background) {
        HibernateManager.get().persist(background);
    }

    public Border getBorder(Integer borderId) {
        if (borderId == null) {
            return null;
        }
        return HibernateManager.get().find(Border.class, borderId);
    }

    @Override
    public Background getBackground(Integer backgroundId) {
        if (backgroundId == null) {
            return null;
        }
        return HibernateManager.get().find(Background.class, backgroundId);
    }

    public void removeBorderBackground(Border borderBackground) {
        HibernateManager.get().remove(borderBackground);
    }

    public int getChildSiteSettingsCountByRegistrationId(final int childSiteRegistrationId) {
        final Query query = HibernateManager.get().createQuery(
                "select count(childSiteSetting) from childSiteSettings as childSiteSetting " +
                        "where childSiteSetting.childSiteRegistration.id = :childSiteRegistrationId");
        query.setParameter("childSiteRegistrationId", childSiteRegistrationId);
        final Long count = (Long) query.getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    @Override
    public DraftGallery getGalleryByNameAndSiteId(final String name, final int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select gallery from galleries as gallery where gallery.name = :name and gallery.siteId = :siteId");
        query.setParameter("name", name);
        query.setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftGallery) query.getSingleResult();
        }
        return null;
    }

    @Override
    public DraftGallery getGalleryById(Integer galleryId) {
        if (galleryId == null) {
            return null;
        }

        return HibernateManager.get().find(DraftGallery.class, galleryId);
    }

    @Override
    public void putGalleryLabel(final DraftGalleryLabel label) {
        HibernateManager.get().persist(label);
    }

    @Override
    public void putGalleryItem(final DraftGalleryItem item) {
        HibernateManager.get().persist(item);
    }

    @Override
    public DraftGalleryItem getGalleryItemById(final int galleryId, final int formItemId) {
        final Query query = HibernateManager.get().createQuery(
                "select galleryItem from galleryItems as galleryItem " +
                        "where galleryItem.id.gallery.id = :galleryId and galleryItem.id.formItemId = :formItemId");
        query.setParameter("galleryId", galleryId);
        query.setParameter("formItemId", formItemId);
        if (!query.getResultList().isEmpty()) {
            return (DraftGalleryItem) query.getSingleResult();
        }
        return null;
    }

    @Override
    public void removeGalleryLabel(final DraftGalleryLabel label) {
        HibernateManager.get().remove(label);
    }

    @Override
    public void removeGalleryItem(final DraftGalleryItem item) {
        HibernateManager.get().remove(item);
    }

    @Override
    public void putItem(final Item siteItem) {
        HibernateManager.get().persist(siteItem);
    }

    @Override
    public void putGalleryVideoRange(final GalleryVideoRange galleryVideoRange) {
        HibernateManager.get().persist(galleryVideoRange);
    }

    public List<GalleryVideoRange> getGalleryVideoRanges(final List<Integer> videoRangeIds) {
        if (videoRangeIds == null || videoRangeIds.isEmpty()) {
            return null;
        }
        final Query query = HibernateManager.get().createQuery(
                "select galleryVideoRange from galleryVideoRanges as galleryVideoRange " +
                        "where galleryVideoRange.rangeId in (:videoRangeIds)");
        query.setParameter("videoRangeIds", videoRangeIds);
        return query.getResultList();
    }

    @Override
    public DraftTellFriend getTellFriendById(final int tellFriendId) {
        return HibernateManager.get().find(DraftTellFriend.class, tellFriendId);
    }

    @Override
    public void putTellFriend(final DraftTellFriend tellFriend) {
        HibernateManager.get().persist(tellFriend);
    }

    @Override
    public List<DraftTellFriend> getTellFriendsBySiteId(final int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select tellFriend from tellFriends as tellFriend " +
                        "where tellFriend.siteId = :siteId order by tellFriend.name");
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    @Override
    public List<DraftManageVotes> getManageVotesListBySiteId(final int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select manageVote from manageVotes as manageVote " +
                        "where manageVote.siteId = :siteId");
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    @Override
    public List<DraftGallery> getGalleriesBySiteId(int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select gallery from galleries as gallery " +
                        "where gallery.siteId = :siteId");
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    @Override
    public DraftTellFriend getTellFriendByNameAndSiteId(final String name, final int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select tellFriend from tellFriends as tellFriend " +
                        "where tellFriend.name = :name and tellFriend.siteId = :siteId");
        query.setParameter("name", name);
        query.setParameter("siteId", siteId);
        if (query.getResultList().size() > 0) {
            return (DraftTellFriend) query.getResultList().get(0);
        }
        return null;
    }

    @Override
    public DraftManageVotes getManageVotesByNameAndSiteId(String name, int siteId) {
        final Query query = HibernateManager.get().createQuery(
                "select manageVote from manageVotes as manageVote where manageVote.name = :name and manageVote.siteId = :siteId");
        query.setParameter("name", name);
        query.setParameter("siteId", siteId);
        if (!query.getResultList().isEmpty()) {
            return (DraftManageVotes) query.getSingleResult();
        }
        return null;
    }

    public List<Video> getVideosBySiteId(Integer siteId) {
        final List<Video> videos = new ArrayList<Video>();
        if (siteId != null) {
            final Query queryByUserRights = HibernateManager.get().createQuery(
                    "select video from videos as video where video.siteId = :siteId");
            queryByUserRights.setParameter("siteId", siteId);
            videos.addAll(queryByUserRights.getResultList());
        }
        return videos;
    }

    public List<Video> getVideos() {
        final Query queryByUserRights = HibernateManager.get().createQuery("select video from videos as video");
        return queryByUserRights.getResultList();
    }

    public List<Site> getSites(final int userId, SiteAccessLevel[] accessLevels, SiteType... siteTypes) {
        if (siteTypes.length == 0) {
            siteTypes = SiteType.values();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site, userOnSiteRights as userOnSiteRight " +
                        "where userOnSiteRight.id.user.userId = :userId " +
                        "and userOnSiteRight.siteAccessType in (:accessLevels) " +
                        "and userOnSiteRight.active = true " +
                        "and site.siteId = userOnSiteRight.id.site.siteId  " +
                        "and site.type in (:siteTypes) " +
                        "order by title");
        query.setParameter("userId", userId);
        query.setParameter("siteTypes", Arrays.asList(siteTypes));
        query.setParameter("accessLevels", Arrays.asList(accessLevels));
        return query.getResultList();
    }

    public List<User> getUsersWithActiveRights(Integer siteId, SiteAccessLevel[] accessLevels) {
        if (siteId == null) {
            return Collections.emptyList();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        final Query query = HibernateManager.get().createQuery(
                "select user from users as user, userOnSiteRights as userOnSiteRight " +
                        "where userOnSiteRight.id.site.siteId = :siteId " +
                        "and userOnSiteRight.id.user.userId = user.userId " +
                        "and userOnSiteRight.siteAccessType in (:accessLevels) " +
                        "and userOnSiteRight.active = true");
        query.setParameter("siteId", siteId);
        query.setParameter("accessLevels", Arrays.asList(accessLevels));
        return query.getResultList();
    }

    @Override
    public List<User> getUsersWithRightsToSite(Integer siteId, SiteAccessLevel[] accessLevels) {
        if (siteId == null) {
            return Collections.emptyList();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        final Query query = HibernateManager.get().createQuery(
                "select user from users as user, userOnSiteRights as userOnSiteRight " +
                        "where userOnSiteRight.id.site.siteId = :siteId " +
                        "and userOnSiteRight.id.user.userId = user.userId " +
                        "and userOnSiteRight.siteAccessType in (:accessLevels)");
        query.setParameter("siteId", siteId);
        query.setParameter("accessLevels", Arrays.asList(accessLevels));
        return query.getResultList();
    }

    public List<UserOnSiteRight> getUserOnSiteRights(Integer siteId, SiteAccessLevel[] accessLevels) {
        if (siteId == null) {
            return Collections.emptyList();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        final Query query = HibernateManager.get().createQuery(
                "select userOnSiteRight from userOnSiteRights as userOnSiteRight " +
                        "where userOnSiteRight.id.site.siteId = :siteId " +
                        "and userOnSiteRight.siteAccessType in (:accessLevels) " +
                        "and userOnSiteRight.active = true");
        query.setParameter("siteId", siteId);
        query.setParameter("accessLevels", Arrays.asList(accessLevels));
        return query.getResultList();
    }

    public void putVideo(final Video video) {
        HibernateManager.get().persist(video);
    }

    public void putFlvVideo(final FlvVideo flvVideo) {
        HibernateManager.get().persist(flvVideo);
    }

    public Video getVideoById(final Integer videoId) {
        if (videoId == null) {
            return null;
        }
        return HibernateManager.get().find(Video.class, videoId);
    }

    public FlvVideo getFlvVideo(final Integer flvVideoId) {
        if (flvVideoId == null) {
            return null;
        }
        return HibernateManager.get().find(FlvVideo.class, flvVideoId);
    }

    @Override
    public FlvVideo getFlvVideo(Integer sourceVideoId, Integer width, Integer height, Integer quality) {
        if (sourceVideoId == null || quality == null) {
            return null;
        }
        StringBuilder queryString = new StringBuilder("select flvVideo from flvVideos as flvVideo where flvVideo.sourceVideoId = :sourceVideoId and flvVideo.quality = :quality");
        if (width != null) {
            queryString.append(" and flvVideo.width = :width");
        }
        if (height != null) {
            queryString.append(" and flvVideo.height = :height");
        }
        final Query query = HibernateManager.get().createQuery(queryString.toString());
        query.setParameter("sourceVideoId", sourceVideoId);
        if (width != null) {
            query.setParameter("width", width);
        }
        if (height != null) {
            query.setParameter("height", height);
        }
        query.setParameter("quality", quality);
        List<FlvVideo> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void putCssParameterValue(final CssParameterValue cssParameterValue) {
        HibernateManager.get().persist(cssParameterValue);
    }

    @Override
    public void putCssValue(final CssValue cssValue) {
        HibernateManager.get().persist(cssValue);
    }

    /**
     * @param cssParameterValue - delet widget or page version css
     */
    public void removeCssParameterValue(final CssParameterValue cssParameterValue) {
        HibernateManager.get().remove(cssParameterValue);
    }

    public void removeBlogPost(final BlogPost blogPost) {
        HibernateManager.get().remove(blogPost);
    }

    public void removeVote(final Vote vote) {
        HibernateManager.get().remove(vote);
    }

    public void removeVotesByFilledFormId(Integer filledFormId) {
        if (filledFormId != null) {
            final Query query = HibernateManager.get().createQuery(
                    "delete from votes " +
                            "where filledFormId = :filledFormId");
            query.setParameter("filledFormId", filledFormId);
            query.executeUpdate();
        }
    }

    protected Long getVotesCount() {
        return (Long) HibernateManager.get().createQuery("select count(voteId) from votes as vote").getSingleResult();
    }

    public void removeManageVotesGallerySettings(final ManageVotesSettings manageVotesGallerySettings) {
        manageVotesGallerySettings.getManageVotes().removeManageVotesGallerySettings(manageVotesGallerySettings);
        manageVotesGallerySettings.setManageVotes(null);
        HibernateManager.get().remove(manageVotesGallerySettings);
    }

    public void removeUser(final User user) {
        final Query resetForumPostsQuery = HibernateManager.get().createQuery(
                "update forumPosts set visitorId = null where visitorId = :userId");
        resetForumPostsQuery.setParameter("userId", user.getUserId());
        resetForumPostsQuery.executeUpdate();

        final Query resetForumThreadQuery = HibernateManager.get().createQuery(
                "update forumThreads set visitorId = null where visitorId = :userId");
        resetForumThreadQuery.setParameter("userId", user.getUserId());
        resetForumThreadQuery.executeUpdate();

        final Query resetCommentQuery = HibernateManager.get().createQuery(
                "update comments set visitorId = null where visitorId = :userId");
        resetCommentQuery.setParameter("userId", user.getUserId());
        resetCommentQuery.executeUpdate();

        final Query resetBlogPostQuery = HibernateManager.get().createQuery(
                "update blogPosts set visitorId = null where visitorId = :userId");
        resetBlogPostQuery.setParameter("userId", user.getUserId());
        resetBlogPostQuery.executeUpdate();

        HibernateManager.get().remove(user);
    }

    public void removeUserOnSiteRight(UserOnSiteRight userOnSiteRight) {
        if (userOnSiteRight == null) {
            return;
        }
        userOnSiteRight.getId().getSite().getUserOnSiteRights().remove(userOnSiteRight);
        userOnSiteRight.getId().getUser().getUserOnSiteRights().remove(userOnSiteRight);
        HibernateManager.get().remove(userOnSiteRight);
    }

    public void removeAdvancedSearchOption(final DraftAdvancedSearchOption advancedSearchOption) {
        advancedSearchOption.getAdvancedSearch().getAdvancedSearchOptions().remove(advancedSearchOption);
        HibernateManager.get().remove(advancedSearchOption);
    }

    public void removeComment(final Comment comment) {
        HibernateManager.get().remove(comment);
    }

    public void removePage(final Page page) {
        page.getSite().removePage(page);
        final WorkPageSettings workPageSettings = new PageManager(page).getWorkPageSettings();
        if (workPageSettings != null) {
            removePageSettings(workPageSettings);
        }
        removePageSettings(page.getPageSettings());
        HibernateManager.get().remove(page);
    }

    public void removeSite(final Site site) {
        if (site == null) {
            return;
        }
        // I remove groups manually because we have to clear all occurrences of group in users. Cascading can`t do it. Tolik.
        for (Group group : new ArrayList<Group>(site.getOwnGroups())) {
            removeGroup(group);
        }
        final Query query1 = HibernateManager.get().createQuery(
                "select site from sites site " +
                        "where site.childSiteSettings.parentSite.siteId = :parentSiteId");
        query1.setParameter("parentSiteId", site.getSiteId());
        final List<Site> childSites = query1.getResultList();
        for (final Site childSite : childSites) {
            new SiteManager(childSite).disconnectFromNetwork();
        }

        final Query query2 = HibernateManager.get().createQuery(
                "delete childSiteSettings as settings where settings.parentSite.siteId = :parentSiteId");
        query2.setParameter("parentSiteId", site.getSiteId());
        query2.executeUpdate();

        HibernateManager.get()
                .createQuery("update siteItems set siteId = null where siteId = :siteId")
                .setParameter("siteId", site.getSiteId()).executeUpdate();
        HibernateManager.get()
                .createQuery("delete siteOnSiteItems where id.site.siteId = :siteId")
                .setParameter("siteId", site.getSiteId()).executeUpdate();
        HibernateManager.get()
                .createQuery("update keywordsGroups set siteId = null where siteId = :siteId")
                .setParameter("siteId", site.getSiteId()).executeUpdate();
        HibernateManager.get()
                .createQuery("update sites set blueprintParentId = null where blueprintParentId = :siteId")
                .setParameter("siteId", site.getSiteId()).executeUpdate();

        /**
         * Why we use manual remove right before remove all site? We add cascade in site for useronsiteright.
         * If you remove site before load right for it, all will be good, but if you use right
         * before remove you load their and when hib remove right it see that user entity for right
         * contains right and say you "can't remove entity added to persist", for fix this situation
         * we must manual remove it and detached from user in session.  
         */
        final List<UserOnSiteRight> rights = new ArrayList<UserOnSiteRight>(site.getUserOnSiteRights());
        for (final UserOnSiteRight right : rights) {
            HibernateManager.get().remove(right);
            right.getId().getUser().removeUserOnSiteRight(right);
            site.removeUserOnSiteRight(right);
        }

        HibernateManager.get().createNativeQuery("delete from childSiteRegistrations_blueprintsId where element = " + site.getSiteId()).executeUpdate();
        HibernateManager.get().createNativeQuery("delete from workChildSiteRegistrations_blueprintsId where element = " + site.getSiteId()).executeUpdate();

        for (Page page : new ArrayList<Page>(site.getPages())) {
            removePage(page);
        }
        HibernateManager.get().remove(site);
    }

    public void removeChildSiteSettings(final ChildSiteSettings childSiteSettings) {
        if (childSiteSettings.getSite() != null) {
            childSiteSettings.getSite().setChildSiteSettings(null);
        }
        HibernateManager.get().createNativeQuery("delete from users_childSiteSettingsId where element = " + childSiteSettings.getId()).executeUpdate();
        HibernateManager.get().remove(childSiteSettings);
    }

    public ForumPost getLastThreadPost(final int forumThreadId) {
        final Query query = HibernateManager.get().createQuery(
                "select post from forumPosts as post where post.thread.threadId = :threadId and" +
                        " post.dateCreated = (select max(maxPost.dateCreated) from forumPosts as maxPost where maxPost.thread = post.thread and maxPost.draftText is null)");
        query.setParameter("threadId", forumThreadId);
        final List<ForumPost> lastPosts = query.getResultList();
        return lastPosts.isEmpty() ? null : lastPosts.get(0);
    }

    public ForumPost getLastSubForumPost(final int subForumId) {
        final Query query = HibernateManager.get().createQuery(
                "select post from forumPosts as post where post.thread.subForum.subForumId = :subForumId and" +
                        " post.dateCreated = (select max(maxPost.dateCreated) from forumPosts as maxPost where" +
                        " maxPost.thread.subForum = post.thread.subForum)");
        query.setParameter("subForumId", subForumId);
        List<ForumPost> lastPosts = query.getResultList();
        return lastPosts.isEmpty() ? null : lastPosts.get(0);
    }

    public Content getContentById(final ContentId contentId) {
        return HibernateManager.get().find(Content.class, contentId);
    }

    public void putContent(final Content content) {
        HibernateManager.get().persist(content);
    }

    public int getMaxContentClientId() {
        final Query query = HibernateManager.get().createQuery(
                "select max(content.id.clientId) from contents as content");
        final List<Integer> maxClientIds = query.getResultList();
        if (maxClientIds.isEmpty() || maxClientIds.get(0) == null) {
            return 0;
        }
        return maxClientIds.get(0);
    }

    public void putKeywordsGroup(final KeywordsGroup keywordsGroup) {
        HibernateManager.get().persist(keywordsGroup);
    }

    public KeywordsGroup getKeywordsGroupById(final int keywordsGroupId) {
        return HibernateManager.get().find(KeywordsGroup.class, keywordsGroupId);
    }

    public void removeKeywordsGroup(final KeywordsGroup keywordsGroup) {
        HibernateManager.get().remove(keywordsGroup);
    }

    public void removeContent(final Content content) {
        HibernateManager.get().remove(content);
    }


    public void destroy() {
        HibernateManager.close();
    }

    public void removeImage(final Image image) {
        HibernateManager.get().remove(image);
    }

    public <R> R inContext(final PersistanceContext<R> persistanceContext) {
        return HibernateManager.inSession(persistanceContext);
    }

    public Site getSiteByCustomUrl(final String customUrl) {
        final Query query = HibernateManager.get().createQuery(
                "select site from sites as site where site.customUrl = :customUrl");
        query.setParameter("customUrl", customUrl);
        final List<Site> sites = query.getResultList();
        return sites.isEmpty() ? null : sites.get(0);
    }

    public <T extends DraftItem> T getDraftItem(final Integer siteItemId) {
        if (siteItemId == null) {
            return null;
        }

        return (T) HibernateManager.get().find(DraftItem.class, siteItemId);
    }

    public int getUsersCount() {
        final Query query = HibernateManager.get().createQuery(
                "select count(user.userId) from users as user");
        return ((Long) query.getSingleResult()).intValue();
    }

    public void addUpdateListener(final PersistanceListener listener) {
        interceptor.addUpdateListener(listener);
    }

    public void addRemoveListener(final PersistanceListener listener) {
        interceptor.addRemoveListener(listener);
    }

    public void putJournalItem(final JournalItem journalItem) {
        HibernateManager.get().persist(journalItem);
    }

    public List<User> getNotActivatedUsers(final Date registeredTo, final int countUsers) {
        final Query query = HibernateManager.get().createQuery(
                "select user from users as user " +
                        "where user.registrationDate < :registeredTo and user.activeted is null " +
                        "order by user.registrationDate");
        query.setParameter("registeredTo", registeredTo);
        query.setMaxResults(countUsers);
        return query.getResultList();
    }

    public long getFilledFormsNumberByFormId(final int formId) {
        final Query query = HibernateManager.get().createQuery(
                "select count(filledFormId) from filledForms as filledForm where" +
                        " filledForm.formId = :formId");
        query.setParameter("formId", formId);
        return (Long) query.getSingleResult();
    }

    public Integer getMaxFilledFormIdByFormId(final int formId) {
        final Query query = HibernateManager.get().createQuery(
                "select max(filledFormId) from filledForms as filledForm where" +
                        " filledForm.formId = :formId");
        query.setParameter("formId", formId);
        return (Integer) query.getSingleResult();
    }


    @Override
    public Vote getVoteById(Integer voteId) {
        if (voteId == null) {
            return null;
        }
        return HibernateManager.get().find(Vote.class, voteId);
    }

    @Override
    public void putVote(Vote vote) {
        HibernateManager.get().persist(vote);
    }

    @Override
    public void setAllWinnerVotesToFalse(Integer userId, Integer galleryId) {
        if (userId == null || galleryId == null) {
            return;
        }
        final Query query = HibernateManager.get().createQuery("update votes as vote set vote.winner = false where " +
                "vote.galleryId = :galleryId and vote.userId = :userId");
        query.setParameter("galleryId", galleryId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public List<Vote> getVotesByStartEndDates(Integer userId, Integer galleryId, final Date startDate, final Date endDate, Integer... filledFormIds) {
        if (userId == null || galleryId == null || filledFormIds.length == 0) {
            return Collections.emptyList();
        }
        final String startDateSelect = (startDate == null) ? " and vote.startDate is null " : " and vote.startDate = :startDate ";
        final String endDateSelect = (endDate == null) ? " and vote.endDate is null " : " and vote.endDate = :endDate ";
        final Query query = HibernateManager.get().createQuery(
                "select vote from votes as vote " +
                        "where vote.galleryId = :galleryId and vote.userId = :userId " +
                        startDateSelect + endDateSelect +
                        "and vote.filledFormId in (:filledFormIds)");
        query.setParameter("userId", userId);
        query.setParameter("galleryId", galleryId);
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        query.setParameter("filledFormIds", Arrays.asList(filledFormIds));
        return query.getResultList();
    }


    public List<Vote> getVotesByTimeInterval(Integer userId, Integer galleryId, final Date startDate, final Date endDate, Integer... filledFormIds) {
        if (userId == null || galleryId == null || filledFormIds.length == 0) {
            return Collections.emptyList();
        }
        final String startDateSelect = (startDate != null) ? " and vote.voteDate >= :startDate " : " ";
        final String endDateSelect = (endDate != null) ? " and vote.voteDate <= :endDate " : " ";
        final Query query = HibernateManager.get().createQuery(
                "select vote from votes as vote " +
                        "where vote.galleryId = :galleryId and vote.userId = :userId " +
                        startDateSelect + endDateSelect +
                        "and vote.filledFormId in (:filledFormIds)");
        query.setParameter("userId", userId);
        query.setParameter("galleryId", galleryId);
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        query.setParameter("filledFormIds", Arrays.asList(filledFormIds));
        return query.getResultList();
    }

    @Override
    public void putGalleryComment(final GalleryComment galleryComment) {
        HibernateManager.get().persist(galleryComment);
    }

    @Override
    public List<GalleryComment> getGalleryCommentsByFilledFormAndGallery(
            final int filledFormId, final int galleryId, final Integer userId,
            final Date start, final Date finish) {
        final Query query = HibernateManager.get().createQuery(
                "select galleryComment from galleryComments as galleryComment " +
                        "where galleryComment.gallery.id = :galleryId " +
                        "and galleryComment.filledForm.filledFormId = :filledFormId " +
                        (userId != null ? "and galleryComment.userId = :userId " : "") +
                        (start != null ? "and galleryComment.created >= :start and galleryComment.created <= :finish " : "") +
                        "order by galleryComment.created");
        query.setParameter("filledFormId", filledFormId);
        query.setParameter("galleryId", galleryId);
        if (start != null) {
            query.setParameter("start", start);
            query.setParameter("finish", finish);
        }
        if (userId != null) {
            query.setParameter("userId", userId);
        }
        return query.getResultList();
    }

    @Override
    public GalleryComment getGalleryCommentById(final int galleryCommentId) {
        return HibernateManager.get().find(GalleryComment.class, galleryCommentId);
    }

    @Override
    public void removeGalleryComment(final GalleryComment galleryComment) {
        HibernateManager.get().remove(galleryComment);
    }

    @Override
    public void removeGalleryVideoRange(final GalleryVideoRange galleryVideoRange) {
        galleryVideoRange.getUser().getVideoRanges().remove(galleryVideoRange);
        HibernateManager.get().remove(galleryVideoRange);
    }

    @Override
    public void putFormVideo(FormVideo formVideo) {
        HibernateManager.get().persist(formVideo);
    }

    @Override
    public void putPaymentLog(PaymentLog paymentLog) {
        HibernateManager.get().persist(paymentLog);
    }

    @Override
    public List<PaymentLog> getPaymentLogsByUsersId(final int userId) {
        final Query query = HibernateManager.get().createQuery(
                "select paymentLog from paymentLogs as paymentLog " +
                        "where paymentLog.userId in (:userId)");
        query.setParameter("userId", Arrays.asList(userId));
        return query.getResultList();
    }

    @Override
    public List<PaymentLog> getAllPaymentLogs() {
        final Query query = HibernateManager.get().createQuery(
                "select paymentLog from paymentLogs as paymentLog");
        return query.getResultList();
    }

    @Override
    public List<PaymentLog> getPaymentLogsByChildSiteSettingsId(int childSiteSettingsId) {
        final Query query = HibernateManager.get().createQuery(
                "select paymentLog from paymentLogs as paymentLog " +
                        "where paymentLog.childSiteSettingsId = :childSiteSettingsId");
        query.setParameter("childSiteSettingsId", childSiteSettingsId);
        return query.getResultList();
    }

    @Override
    public List<Integer> getPageParentIds(final int siteId, final Integer parentId) {
        final Query query;
        if (parentId == null) {
            query = HibernateManager.get().createNativeQuery(
                    "select pageId from pages where siteId = :siteId and parentId is null " +
                            "and position is not null order by position");
        } else {
            query = HibernateManager.get().createNativeQuery(
                    "select pageId from pages where siteId = :siteId and parentId = :parentId " +
                            "and position is not null order by position");
            query.setParameter("parentId", parentId);
        }
        query.setParameter("siteId", siteId);
        return query.getResultList();
    }

    public void putMenuItem(MenuItem menuItem) {
        HibernateManager.get().persist(menuItem);
    }

    public void removeMenuItem(MenuItem menuItem) {
        if (menuItem != null) {
            if (menuItem.getParent() != null) {
                menuItem.getParent().removeChild(menuItem);
            } else {
                menuItem.getMenu().removeChild(menuItem);
            }
            HibernateManager.get().remove(menuItem);
        }
    }

    public DraftMenuItem getDraftMenuItem(Integer menuItemId) {
        if (menuItemId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftMenuItem.class, menuItemId);
    }

    @Override
    public WorkMenuItem getWorkMenuItem(Integer menuItemId) {
        if (menuItemId == null) {
            return null;
        }
        return HibernateManager.get().find(WorkMenuItem.class, menuItemId);
    }

    public List<DraftMenuItem> getMenuItems(Integer pageId) {
        if (pageId == null) {
            return Collections.emptyList();
        }
        final Query query = HibernateManager.get().createQuery("select item from newMenuItems as item where item.pageId = :pageId");
        query.setParameter("pageId", pageId);
        return query.getResultList();
    }

    public void setMenuItemsIncludeInMenu(List<Integer> menuItemsIds, boolean includeInMenu) {
        if (menuItemsIds.isEmpty()) {
            return;
        }
        final Query query = HibernateManager.get().createQuery("update newMenuItems as item set " +
                "item.includeInMenu = :includeInMenu where item.id in (:menuItemsIds)");
        query.setParameter("menuItemsIds", menuItemsIds);
        query.setParameter("includeInMenu", includeInMenu);
        query.executeUpdate();
    }

    @Override
    public PaymentLog getPaymentLogById(int logId) {
        return HibernateManager.get().find(PaymentLog.class, logId);
    }

    @Override
    public FormVideo getFormVideoById(Integer formVideoId) {
        if (formVideoId == null) {
            return null;
        }
        return HibernateManager.get().find(FormVideo.class, formVideoId);
    }

    @Override
    public List<FormVideo> getAllFormVideos() {
        return HibernateManager.get().createQuery("select formVideo from formVideos as formVideo").getResultList();
    }

    @Override
    public List<DraftForm> getAllForms() {
        return HibernateManager.get().createQuery("select form from forms as form").getResultList();
    }

    @Override
    public List<DraftGallery> getGalleriesByDataCrossWidgetIds(Integer... crossWidgetIds) {
        final Query query = HibernateManager.get().createQuery(
                "select gallery from galleries as gallery " +
                        "where gallery.dataCrossWidgetId in (:crossWidgetIds) order by gallery.name");
        query.setParameter("crossWidgetIds", Arrays.asList(crossWidgetIds));
        return query.getResultList();
    }

    @Override
    public List<WidgetItem> getWidgetItemsByGalleriesId(final Collection<Integer> galleriesId) {
        if (galleriesId == null || galleriesId.isEmpty()) {
            return Collections.emptyList();
        }
        final Query query = HibernateManager.get().createQuery(
                "select distinct widget from widgetItems as widget " +
                        "where widget.draftItem.id in (:galleryIds) " +
                        "and widget.draftPageSettings is not null " +
                        "order by widget.draftPageSettings.name");
        query.setParameter("galleryIds", galleriesId);
        return query.getResultList();
    }


    public List<DraftChildSiteRegistration> getAllChildSiteRegistrations() {
        Query query = HibernateManager.get().createQuery("select registration from childSiteRegistrations as registration");
        return (List<DraftChildSiteRegistration>) query.getResultList();
    }


    public Coordinate getCoordinate(final String zip, final Country country) {
        if (zip == null || country == null) {
            return null;
        }
        final Query query = HibernateManager.get().createQuery("select coordinate from coordinates as coordinate " +
                "where zip = :zip and country = :country");
        query.setParameter("zip", zip);
        query.setParameter("country", country);
        if (!query.getResultList().isEmpty()) {
            return (Coordinate) query.getSingleResult();
        }
        return null;
    }

    public void putCoordinate(Coordinate coordinate) {
        HibernateManager.get().persist(coordinate);
    }

    public void executeAlter(Alter alter) {
        alter.logBefore();
        final Query query = HibernateManager.get().createNativeQuery(alter.getValue());
        final int changedRows = query.executeUpdate();
        alter.logAfter(changedRows);
    }

    @Override
    public void executeAlters(List<Alter> alters) {
        final Session session = (Session) HibernateManager.get().getDelegate();
        for (Alter alter : alters) {
            try {
                alter.logBefore();
                final int changedRows = session.connection().prepareStatement(alter.getValue()).executeUpdate();
                alter.logAfter(changedRows);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<SiteOnItem> getSiteOnItemsByItem(final int itemId) {
        return HibernateManager.get().createQuery(
                "select siteOnItem from siteOnSiteItems as siteOnItem " +
                        "where siteOnItem.id.item.id = :itemId").setParameter("itemId", itemId).getResultList();
    }

    @Override
    public List<SiteOnItem> getSiteOnItemsBySite(final int siteId) {
        return HibernateManager.get().createQuery(
                "select siteOnItem from siteOnSiteItems as siteOnItem " +
                        "where siteOnItem.id.site.siteId = :siteId").setParameter("siteId", siteId).getResultList();
    }

    @Override
    public <T extends WorkItem> T getWorkItem(final Integer workSiteItemId) {
        if (workSiteItemId != null) {
            return (T) HibernateManager.get().find(WorkItem.class, workSiteItemId);
        }
        return null;
    }

    @Override
    public PaymentSettingsOwner getPaymentSettingsOwner(Integer id, PaymentSettingsOwnerType type) {
        if (id == null || type == null) {
            return null;
        }
        switch (type) {
            case SITE: {
                return getSite(id);
            }
            case CHILD_SITE_SETTINGS: {
                return getChildSiteSettingsById(id);
            }
            default: {
                throw new IllegalArgumentException("Unknown PaymentSettingsOwnerType = " + type);
            }
        }
    }

    @Override
    public void removeWorkFormItems(Integer formId) {
        HibernateManager.get().createQuery(
                "delete from workFormItems as workFormItem " +
                        "where workFormItem.form.id = :formId").setParameter("formId", formId).executeUpdate();
    }

    @Override
    public void removeWorkFilters(Integer formId) {
        HibernateManager.get().createQuery(
                "delete from workFormFilters as workFormFilter " +
                        "where workFormFilter.form.id = :formId").setParameter("formId", formId).executeUpdate();
    }

    @Override
    public void putWorkFilter(WorkFormFilter workFilter) {
        HibernateManager.get().persist(workFilter);
    }

    @Override
    public void putWorkFormItem(WorkFormItem workFormItem) {
        HibernateManager.get().persist(workFormItem);
    }

    @Override
    public void removeWorkGalleryItems(Integer galleryId) {
        HibernateManager.get().createQuery(
                "delete from workGalleryItems as workGalleryItem " +
                        "where workGalleryItem.id.gallery.id = :galleryId").setParameter("galleryId", galleryId).executeUpdate();
    }

    @Override
    public void removeWorkGalleryLabels(Integer galleryId) {
        HibernateManager.get().createQuery(
                "delete from workGalleryLabels as workGalleryLabel " +
                        "where workGalleryLabel.id.gallery.id = :galleryId").setParameter("galleryId", galleryId).executeUpdate();
    }

    @Override
    public void putWorkGalleryItem(WorkGalleryItem workItem) {
        HibernateManager.get().persist(workItem);
    }

    @Override
    public void putWorkGalleryLabel(WorkGalleryLabel workLabel) {
        HibernateManager.get().persist(workLabel);
    }

    @Override
    public void removeDraftItemCssValues(final Integer draftItemId) {
        HibernateManager.get().createQuery(
                "delete from draftCssValues where id.item.id = :draftItemId")
                .setParameter("draftItemId", draftItemId).executeUpdate();
    }

    @Override
    public void removeWidgetCssValues(final Integer widgetId) {
        HibernateManager.get().createQuery(
                "delete from widgetCssValues where id.widget.widgetId = :widgetId")
                .setParameter("widgetId", widgetId).executeUpdate();
    }

    @Override
    public void removeWorkCssValues(final Integer workItemId) {
        HibernateManager.get().createQuery(
                "delete from draftCssValues where id.item.id = :workItemId")
                .setParameter("workItemId", workItemId).executeUpdate();
    }

    @Override
    public List<Integer> getDraftItemIds() {
        return HibernateManager.get().createQuery(
                "select draftItem.id from siteItems as draftItem").getResultList();
    }


    @Override
    public Map<DraftItem, List<WidgetItem>> getItems(List<Integer> siteIds, ItemType itemType, SiteShowOption siteShowOption) {
        final StringBuilder queryBySiteOwnerString = new StringBuilder();
        queryBySiteOwnerString.append("select item from ");
        queryBySiteOwnerString.append(itemType.getTableName());
        queryBySiteOwnerString.append(" as item where siteId in (:siteIds) ");
        if (itemType == ItemType.ORDER_FORM) {// todo. Review this (maybe we should add new table?). Tolik
            queryBySiteOwnerString.append("and item.formType = :orderFormType");
        }
        final Query query = HibernateManager.get().createQuery(queryBySiteOwnerString.toString());
        query.setParameter("siteIds", siteIds);
        if (itemType == ItemType.ORDER_FORM) {// todo. Review this (maybe we should add new table?). Tolik
            query.setParameter("orderFormType", FormType.ORDER_FORM);
        }

        final List<DraftItem> draftItems = query.getResultList();

        final List<Widget> widgets = getWidgetsBySitesId(siteIds, siteShowOption);

        final Map<DraftItem, List<WidgetItem>> result = new HashMap<DraftItem, List<WidgetItem>>();
        for (final DraftItem draftItem : draftItems) {
            final List<WidgetItem> widgetItems = new ArrayList<WidgetItem>();
            for (final Widget widget : widgets) {
                if (widget.isWidgetItem()) {
                    final WidgetItem widgetItem = (WidgetItem) widget;
                    if (widgetItem.getDraftItem() != null && widgetItem.getDraftItem().getId() == draftItem.getId()) {
                        widgetItems.add(widgetItem);
                    }
                }
            }

            result.put(draftItem, widgetItems);
        }

        return result;
    }

    public void putGroup(Group group) {
        HibernateManager.get().persist(group);
    }

    public void removeGroup(Group group) {
        if (group == null) {
            return;
        }
        group.getOwner().removeGroup(group);
        for (User user : getUsersWithAccessToGroup(group.getGroupId())) {
            new UsersGroupManager(user).removeAccessToGroup(group.getGroupId());
        }
        final Query query = HibernateManager.get().createQuery("select registrationForm from registrationForms as registrationForm" +
                " where registrationForm.groupsWithTime like :groupId");
        query.setParameter("groupId", ("%" + group.getGroupId() + "%"));
        final List<DraftRegistrationForm> registrationForms = query.getResultList();
        for (DraftRegistrationForm form : registrationForms) {
            removeGroupId(form, group.getGroupId());
            removeGroupId((RegistrationForm) new ItemManager(form).getWorkItem(), group.getGroupId());
        }
        HibernateManager.get().remove(group);
    }

    private void removeGroupId(final RegistrationForm form, final int groupId) {
        if (form == null) {
            return;
        }
        final List<GroupsTime> groupsTimes = GroupsTimeManager.valueOf(form.getGroupsWithTime());
        final Iterator<GroupsTime> iterator = groupsTimes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getGroupId() == groupId) {
                iterator.remove();
            }
        }
        form.setGroupsWithTime(GroupsTimeManager.valueOf(groupsTimes));
    }

    public void removeGroup(int groupId) {
        removeGroup(getGroup(groupId));
    }

    public Group getGroup(Integer groupId) {
        if (groupId == null) {
            return null;
        }
        return HibernateManager.get().find(Group.class, groupId);
    }

    public List<Group> getGroups(List<Integer> groupIds) {
        if (groupIds.isEmpty()) {
            return Collections.emptyList();
        }
        final Query query = HibernateManager.get().createQuery("select group from groups as group where group.groupId in (:groupIds)");
        query.setParameter("groupIds", groupIds);
        return query.getResultList();
    }

    public List<User> getUsersWithAccessToGroup(int groupId) {
        final List<Integer> userIdsWithAccess = getUserIdsWithAccesToGroup(groupId);
        if (userIdsWithAccess.isEmpty()) {
            return Collections.emptyList();
        }
        final Query query = HibernateManager.get().createQuery("select user from users as user where user.userId in (:userIdsWithAccess)");
        query.setParameter("userIdsWithAccess", userIdsWithAccess);
        return query.getResultList();
    }

    public List<User> getUsersByUsersId(final List<Integer> usersId) {
        if (usersId.isEmpty()) {
            return Collections.emptyList();
        }
        final Query query = HibernateManager.get().createQuery("select user from users as user where user.userId in (:usersId)");
        query.setParameter("usersId", usersId);
        return query.getResultList();
    }

    @Override
    public <T extends DraftItem> Map<T, List<WidgetItem>> getItemsBySiteAndUser(
            final int userId, final int siteId, final ItemType type, final boolean onlyCurrentSite) {
        final Map<T, List<WidgetItem>> result = new HashMap<T, List<WidgetItem>>();

        final List<Integer> siteIds = new ArrayList<Integer>();

        if (!onlyCurrentSite) {
            siteIds.addAll(HibernateManager.get().createQuery(
                    "select userOnSite.id.site.siteId from userOnSiteRights as userOnSite " +
                            "where userOnSite.id.user.userId = :userId " +
                            "and userOnSite.siteAccessType in (:siteAccessType) " +
                            "and userOnSite.active = :true")
                    .setParameter("userId", userId)
                    .setParameter("true", true)
                    .setParameter("siteAccessType", Arrays.asList(SiteAccessLevel.getUserAccessLevels()))
                    .getResultList());
        }

        siteIds.add(siteId);

        final List<T> draftItems = new ArrayList<T>();
        draftItems.addAll(HibernateManager.get().createQuery(
                "select draftItem from " + type.getTableName() + " as draftItem where draftItem.siteId in (:siteIds)")
                .setParameter("siteIds", siteIds).getResultList());

        if (!onlyCurrentSite) {
            draftItems.addAll(HibernateManager.get().createQuery(
                    "select draftItem from " + type.getTableName() + " as draftItem, siteOnSiteItems as siteOnItem " +
                            "where draftItem.id = siteOnItem.id.item.id " +
                            "and siteOnItem.acceptDate is not null " +
                            "and siteOnItem.id.site.siteId = :siteId")
                    .setParameter("siteId", siteId).getResultList());
        }

        final List<Widget> widgets = getWidgetsBySitesId(
                new ArrayList<Integer>(siteIds), SiteShowOption.getDraftOption());

        for (final T draftItem : draftItems) {
            // We check item class instead of itemType because type may be E-COMMERCE_STORE i.e and
            // draftItem.getItemType() in that case will return GALLERY(SW-6167). In fact this check is redundant
            // I left this just in case something goes wrong.
            if (draftItem.getItemType().getItemClass() == type.getItemClass()) {
                final List<WidgetItem> widgetItems = new ArrayList<WidgetItem>();
                for (final Widget widget : widgets) {
                    if (widget.isWidgetItem()) {
                        final WidgetItem widgetItem = (WidgetItem) widget;
                        if (widgetItem.getDraftItem() != null && widgetItem.getDraftItem().getId() == draftItem.getId()) {
                            widgetItems.add(widgetItem);
                        }
                    }
                }

                result.put(draftItem, widgetItems);
            }
        }

        return result;
    }

    public int getUsersCountWithAccessToGroup(Integer groupId) {
        return getUserIdsWithAccesToGroup(groupId).size();
    }

    @Override
    public void removeDraftItem(final Integer draftItemId) {
        HibernateManager.get().createQuery("delete from siteItems as draftItem where id = :id")
                .setParameter("id", draftItemId).executeUpdate();
    }

    @Override
    public void removeWorkAdvancedSearchOptions(final Integer itemId) {
        HibernateManager.get().createQuery(
                "delete from workAdvancedSearchOptions as workAdvancedSearchOption " +
                        "where workAdvancedSearchOption.advancedSearch.id = :itemId")
                .setParameter("itemId", itemId).executeUpdate();
    }

    @Override
    public void putWorkAdvancedSearchOption(final WorkAdvancedSearchOption workAdvancedSearchOption) {
        HibernateManager.get().persist(workAdvancedSearchOption);
    }

    @Override
    public void removeWorkManageVotesSettings(final Integer itemId) {
        HibernateManager.get().createQuery(
                "delete from workVotingModules as workVotingModule " +
                        "where workVotingModule.manageVotes.id = :itemId")
                .setParameter("itemId", itemId).executeUpdate();
    }

    @Override
    public void putWorkManageVotesSettings(final WorkManageVotesSettings workManageVotesSettings) {
        HibernateManager.get().persist(workManageVotesSettings);
    }

    private List<Integer> getUserIdsWithAccesToGroup(int groupId) {
        final Query query = HibernateManager.get().createQuery("select usersGroup.id.user from usersGroup as usersGroup " +
                "where usersGroup.id.group.groupId = :groupId");
        query.setParameter("groupId", groupId);
        final List<Integer> usersId = new ArrayList<Integer>();
        for (Object user : query.getResultList()) {
            if (new UsersGroupManager((User) user).hasAccessToGroup(groupId)) {
                usersId.add(((User) user).getUserId());
            }
        }
        return usersId;
    }

    public AccessibleForRender getAccessibleElement(Integer id, AccessibleElementType type) {
        if (id == null || type == null) {
            return null;
        }
        if (type == AccessibleElementType.PAGE) {
            return new PageManager((Page) HibernateManager.get().find(type.getEntityClass(), id));
        }
        if (type == AccessibleElementType.WIDGET) {
            return new WidgetManager((WidgetItem) HibernateManager.get().find(type.getEntityClass(), id));
        }
        if (type == AccessibleElementType.ITEM) {
            return new ItemManager(id);
        }
        return (AccessibleForRender) HibernateManager.get().find(type.getEntityClass(), id);
    }


    public void putAccessibleSettings(AccessibleSettings accessibleSettings) {
        HibernateManager.get().persist(accessibleSettings);
    }

    @Override
    public AccessibleSettings getAccessibleSettings(Integer accessibleSettingsId) {
        if (accessibleSettingsId == null) {
            return null;
        }
        return HibernateManager.get().find(AccessibleSettings.class, accessibleSettingsId);
    }

    @Override
    public void removeWorkItem(final WorkItem workItem) {
//        if (HibernateManager.get().find(workItem.getClass(), workItem.getId()) != null)
//            HibernateManager.get().refresh(workItem);
        removeMenuItems(workItem);
        HibernateManager.get().remove(workItem);
    }

    public void removeWorkItem(Integer workItemId) {
        if (workItemId == null) {
            return;
        }
        HibernateManager.get().createNativeQuery("delete from workSiteItems where id = " + workItemId).executeUpdate();
    }

    public void removeDraftItem(final DraftItem draftItem) {
        removeMenuItems(draftItem);

        HibernateManager.get().createQuery(
                "delete siteOnSiteItems as siteOnItem where siteOnItem.id.item = :item")
                .setParameter("item", draftItem).executeUpdate();
        List<Widget> list = HibernateManager.get().createQuery(
                "select widget from widgets as widget where widget.draftItem.id = :itemId")
                .setParameter("itemId", draftItem.getId()).getResultList();

        HibernateManager.get().remove(draftItem);
        for (Widget widget : list) {
            new WidgetManager(widget).remove();
        }
    }

    private void removeMenuItems(final Item item) {
        if (item instanceof Menu) {
            final Menu menu = (Menu) item;
            for (MenuItem menuItem : new ArrayList<MenuItem>(menu.getMenuItems())) {
                removeMenuItem(menuItem);
            }
        }
    }

    @Override
    public PurchaseMailLog getPurchaseMailLog(Integer purchaseMailLogId) {
        if (purchaseMailLogId == null) {
            return null;
        }
        return HibernateManager.get().find(PurchaseMailLog.class, purchaseMailLogId);
    }

    @Override
    public void putPurchaseMailLog(PurchaseMailLog purchaseMailLog) {
        HibernateManager.get().persist(purchaseMailLog);
    }

    @Override
    public List<PurchaseMailLog> getAllPurchaseMailLogs() {
        return HibernateManager.get().createQuery("select purchaseMailLog from purchaseMailLogs as purchaseMailLog").getResultList();
    }


    @Override
    public List<FormFileShouldBeCopied> getAllFormFileShouldBeCopied() {
        return HibernateManager.get().createQuery("select formFileShouldBeCopied from formFileShouldBeCopied as formFileShouldBeCopied").getResultList();
    }

    @Override
    public List<VideoShouldBeCopied> getAllVideoShouldBeCopied() {
        return HibernateManager.get().createQuery("select videoShouldBeCopied from videoShouldBeCopied as videoShouldBeCopied").getResultList();
    }

    @Override
    public void putVideoShouldBeCopied(VideoShouldBeCopied videoShouldBeCopied) {
        HibernateManager.get().persist(videoShouldBeCopied);
    }

    @Override
    public void putFormFileShouldBeCopied(FormFileShouldBeCopied formFileShouldBeCopied) {
        HibernateManager.get().persist(formFileShouldBeCopied);
    }

    @Override
    public UsersGroup getUsersGroup(UsersGroupId usersGroupId) {
        if (usersGroupId != null) {
            return HibernateManager.get().find(UsersGroup.class, usersGroupId);
        }
        return null;
    }

    @Override
    public void putUsersGroup(UsersGroup usersGroup) {
        HibernateManager.get().persist(usersGroup);
    }

    @Override
    public void removeUsersGroup(UsersGroup usersGroup) {
        new UsersGroupManager(usersGroup.getId().getUser()).removeAccessToGroup(usersGroup.getGroupId());
        HibernateManager.get().remove(usersGroup);
    }

    @Override
    public List<User> getAllUsers() {
        return HibernateManager.get().createQuery("select user from users as user").getResultList();
    }

    @Override
    public void putPageSettings(PageSettings pageSettings) {
        HibernateManager.get().persist(pageSettings);
    }

    @Override
    public DraftPageSettings getDraftPageSettings(Integer pageSettingsId) {
        if (pageSettingsId == null) {
            return null;
        }
        return HibernateManager.get().find(DraftPageSettings.class, pageSettingsId);
    }

    @Override
    public WorkPageSettings getWorkPageSettings(Integer pageSettingsId) {
        if (pageSettingsId == null) {
            return null;
        }
        return HibernateManager.get().find(WorkPageSettings.class, pageSettingsId);
    }

    @Override
    public void removePageSettings(PageSettings pageSettings) {
        if (pageSettings == null) {
            return;
        }
        if (pageSettings instanceof DraftPageSettings) {
            pageSettings.getPage().setPageSettings(null);
        }
        HibernateManager.get().remove(pageSettings);
    }

    @Override
    public List<Widget> getWidgets(List<Integer> widgetsId) {
        if (widgetsId == null || widgetsId.isEmpty()) {
            return Collections.emptyList();
        }
        return HibernateManager.get().createQuery("select widget from widgets as widget where widgetId in " +
                "(:widgetsId)").setParameter("widgetsId", widgetsId).getResultList();
    }

    @Override
    public List<KeywordsGroup> getKeywordsGroups(List<Integer> keywordsGroupsId) {
        if (keywordsGroupsId == null || keywordsGroupsId.isEmpty()) {
            return Collections.emptyList();
        }
        return HibernateManager.get().createQuery("select keywordsGroup from keywordsGroups as keywordsGroup where keywordsGroupId in " +
                "(:keywordsGroupsId)").setParameter("keywordsGroupsId", keywordsGroupsId).getResultList();
    }

    @Override
    public List<SiteTitlePageName> getWorkSiteTitlePageNames(final List<Integer> siteIds) {
        String hql = "select " +
                "settings.page.pageId, settings.name, settings.page.site.siteId, settings.page.site.title, " +
                "settings.page.site.customUrl, settings.page.site.subDomain, settings.url " +
                "from workPageSettings as settings " +
                "where settings.page.system = :false and settings.page.site.sitePaymentSettings.siteStatus = 'ACTIVE' " +
                "and settings.page.site.type = 'COMMON'";

        if (siteIds != null && !siteIds.isEmpty()) {
            hql += " and settings.page.site.siteId in (:siteIds)";
        }

        hql += " order by settings.creationDate";

        final Query query = HibernateManager.get().createQuery(hql);
        query.setParameter("false", false);
        if (siteIds != null && !siteIds.isEmpty()) {
            query.setParameter("siteIds", siteIds);
        }

        final List objects = query.getResultList();

        return objectsToSiteTitlePageNames(objects);
    }

    private List<SiteTitlePageName> objectsToSiteTitlePageNames(final List objects) {
        final List<SiteTitlePageName> result = new ArrayList<SiteTitlePageName>(objects.size());
        for (final Object object : objects) {
            final SiteTitlePageName resultItem = objectToSiteTitlePageName(object);
            result.add(resultItem);
        }
        return result;
    }

    private SiteTitlePageName objectToSiteTitlePageName(final Object object) {
        final Object[] objectArray = (Object[]) object;
        final SiteTitlePageName resultItem = new SiteTitlePageName();
        resultItem.setPageId((Integer) objectArray[0]);
        resultItem.setPageName((String) objectArray[1]);
        resultItem.setPageUrl((String) objectArray[6]);

        resultItem.setSiteId((Integer) objectArray[2]);
        resultItem.setSiteCustomUrl((String) objectArray[4]);
        resultItem.setSiteTitle((String) objectArray[3]);
        resultItem.setSiteSubDomain((String) objectArray[5]);
        return resultItem;
    }

    @Override
    public List<SiteTitlePageName> getSiteTitlePageNamesByUserId(final int userId) {
        final List<Integer> siteIds = new ArrayList<Integer>();
        for (Site site : getSites(userId, SiteAccessLevel.getUserAccessLevels())) {
            siteIds.add(site.getSiteId());
        }
        return getSiteTitlePageNames(siteIds);
    }

    @Override
    public List<SiteTitlePageName> getSiteTitlePageNamesBySiteId(final int siteId) {
        return getSiteTitlePageNames(Arrays.asList(siteId));
    }

    private List<SiteTitlePageName> getSiteTitlePageNames(final List<Integer> siteIds) {
        if (siteIds == null || siteIds.isEmpty()) {
            return Collections.emptyList();
        }
        String hql = "select settings.page.pageId, settings.name, settings.page.site.siteId, settings.page.site.title, " +
                "settings.page.site.customUrl, settings.page.site.subDomain, settings.url from draftPageSettings as settings " +
                "where settings.page.system = :false and settings.page.site.siteId in (:siteIds)";

        final Query query = HibernateManager.get().createQuery(hql);
        query.setParameter("false", false);
        query.setParameter("siteIds", siteIds);

        final List objects = query.getResultList();

        final List<SiteTitlePageName> result = new ArrayList<SiteTitlePageName>(objects.size());
        final Set<Integer> pageIds = new HashSet<Integer>();
        for (final Object object : objects) {
            final SiteTitlePageName draftResultItem = objectToSiteTitlePageName(object);
            result.add(draftResultItem);
            pageIds.add(draftResultItem.getPageId());
        }

        for (final SiteTitlePageName workResultItem : getWorkSiteTitlePageNames(siteIds)) {
            if (!pageIds.contains(workResultItem.getPageId())) {
                result.add(workResultItem);
            }
        }

        Collections.sort(result, new Comparator<SiteTitlePageName>() {

            @Override
            public int compare(SiteTitlePageName o1, SiteTitlePageName o2) {
                final int result = o1.getSiteTitle().compareTo(o2.getSiteTitle());
                if (result == 0) {
                    if (o1.getPageName() != null && o2.getPageName() != null) {
                        return o1.getPageName().compareTo(o2.getPageName());
                    }
                }
                return result;
            }

        });

        return result;
    }

    /*----------------------------------------------Get Items By UserId-----------------------------------------------*/

    public List<DraftItem> getDraftItemsByUserId(final int userId, final ItemType itemType) {
        return getDraftItemsByUserIdInternal(userId, itemType);
    }

    private List<DraftItem> getDraftItemsByUserIdInternal(final int userId, final ItemType itemType) {
        final List<DraftItem> siteItems = new ArrayList<DraftItem>();
        if (itemType == ItemType.ALL_ITEMS) {// Getting items from each table separately in two times faster than getting all items from DraftItem`s table in one query. Tolik
            for (ItemType tempItemType : ItemType.getDraftItemsTypeWithoutDuplicates()) {
                siteItems.addAll(getDraftItemsByUserIdInternal(userId, tempItemType));
            }
        } else if (itemType == ItemType.ALL_FORMS) {// Same as for items
            for (ItemType tempItemType : ItemType.getFormItems()) {
                siteItems.addAll(getDraftItemsByUserIdInternal(userId, tempItemType));
            }
        } else {
            final String tableName = itemType.getTableName();

            final StringBuilder queryBySiteOwnerString = new StringBuilder();
            queryBySiteOwnerString.append("select item from ");
            queryBySiteOwnerString.append(tableName);
            queryBySiteOwnerString.append(" as item, userOnSiteRights as userOnSiteRights ");
            queryBySiteOwnerString.append("where userOnSiteRights.id.user.userId = :userId ");
            queryBySiteOwnerString.append("and userOnSiteRights.id.site.siteId = item.siteId ");
            queryBySiteOwnerString.append("and userOnSiteRights.active = true ");
            queryBySiteOwnerString.append("and userOnSiteRights.siteAccessType in (:accessLevels) ");
            if (itemType == ItemType.ORDER_FORM) {
                queryBySiteOwnerString.append("and item.formType = :orderFormType ");
            }
            if (itemType == ItemType.MENU) {// We should not show default site menus
                queryBySiteOwnerString.append("and item.defaultSiteMenu = false ");
            }
            final Query queryBySiteOwner = HibernateManager.get().createQuery(queryBySiteOwnerString.toString());
            queryBySiteOwner.setParameter("userId", userId);
            queryBySiteOwner.setParameter("accessLevels", Arrays.asList(SiteAccessLevel.getUserAccessLevels()));
            if (itemType == ItemType.ORDER_FORM) {
                queryBySiteOwner.setParameter("orderFormType", FormType.ORDER_FORM);
            }

            final StringBuilder queryBySiteOnItemRightsString = new StringBuilder();
            queryBySiteOnItemRightsString.append("select item from ");
            queryBySiteOnItemRightsString.append(tableName);
            queryBySiteOnItemRightsString.append(" as item, userOnSiteRights as userOnSiteRights, siteOnSiteItems as siteOnItem ");
            queryBySiteOnItemRightsString.append("where userOnSiteRights.id.site.siteId = siteOnItem.id.site.siteId ");
            if (itemType == ItemType.ORDER_FORM) {
                queryBySiteOnItemRightsString.append("and item.formType = :orderFormType ");
            }
            if (itemType == ItemType.MENU) {// We should not show default site menus
                queryBySiteOnItemRightsString.append("and item.defaultSiteMenu = false ");
            }
            queryBySiteOnItemRightsString.append("and siteOnItem.acceptDate is not null ");
            queryBySiteOnItemRightsString.append("and siteOnItem.id.item = item ");
            queryBySiteOnItemRightsString.append("and userOnSiteRights.active = true and userOnSiteRights.id.user.userId = :userId");
            final Query queryBySiteOnItemRights = HibernateManager.get().createQuery(queryBySiteOnItemRightsString.toString());
            queryBySiteOnItemRights.setParameter("userId", userId);
            if (itemType == ItemType.ORDER_FORM) {
                queryBySiteOnItemRights.setParameter("orderFormType", FormType.ORDER_FORM);
            }
            final Set<DraftItem> draftItems = new HashSet<DraftItem>();
            draftItems.addAll(queryBySiteOwner.getResultList());
            draftItems.addAll(queryBySiteOnItemRights.getResultList());
            siteItems.addAll(draftItems);
            Collections.sort(siteItems, new Comparator<DraftItem>() {
                @Override
                public int compare(final DraftItem o1, final DraftItem o2) {
                    int result = o1.getName().compareTo(o2.getName());
                    // If items have same names with addition sort it by item id.
                    if (result == 0) {
                        return o1.getId() - o2.getId();
                    }
                    return result;
                }
            });
        }
        return siteItems;
    }

    @Override
    public List<DraftItem> getDraftItemsBySiteId(int siteId, ItemType itemType, ItemType... excludedItemTypes) {
        return getDraftItemsBySiteIdInternal(siteId, itemType, excludedItemTypes);
    }

    private List<DraftItem> getDraftItemsBySiteIdInternal(final int siteId, final ItemType itemType, ItemType... excludedItemTypes) {
        final List<DraftItem> siteItems = new ArrayList<DraftItem>();
        if (excludedItemTypes != null && Arrays.asList(excludedItemTypes).contains(itemType)) {
            return siteItems;
        }
        if (itemType == ItemType.ALL_ITEMS) {// Getting items from each table separately in two times faster than getting all items from DraftItem`s table in one query. Tolik
            for (ItemType tempItemType : ItemType.getDraftItemsTypeWithoutDuplicates()) {
                siteItems.addAll(getDraftItemsBySiteIdInternal(siteId, tempItemType, excludedItemTypes));
            }
        } else if (itemType == ItemType.ALL_FORMS) {// Same as for items
            for (ItemType tempItemType : ItemType.getFormItems()) {
                siteItems.addAll(getDraftItemsBySiteIdInternal(siteId, tempItemType, excludedItemTypes));
            }
        } else {
            final String tableName = itemType.getTableName();

            final StringBuilder queryBySiteOwnerString = new StringBuilder();
            queryBySiteOwnerString.append("select item from ");
            queryBySiteOwnerString.append(tableName);
            queryBySiteOwnerString.append(" as item ");
            queryBySiteOwnerString.append("where item.siteId = :siteId ");
            if (itemType == ItemType.ORDER_FORM) {// todo. Review this (maybe we should add new table?). Tolik
                queryBySiteOwnerString.append("and item.formType = :orderFormType ");
            }
            if (itemType == ItemType.MENU) {// We should not show default site menus
                queryBySiteOwnerString.append("and item.defaultSiteMenu = false ");
            }
            final Query queryBySiteOwner = HibernateManager.get().createQuery(queryBySiteOwnerString.toString());
            queryBySiteOwner.setParameter("siteId", siteId);
            if (itemType == ItemType.ORDER_FORM) {// todo. Review this (maybe we should add new table?). Tolik
                queryBySiteOwner.setParameter("orderFormType", FormType.ORDER_FORM);
            }

            final StringBuilder queryBySiteOnItemRightsString = new StringBuilder();
            queryBySiteOnItemRightsString.append("select item from ");
            queryBySiteOnItemRightsString.append(tableName);
            queryBySiteOnItemRightsString.append(" as item, siteOnSiteItems as siteOnItem ");
            queryBySiteOnItemRightsString.append("where siteOnItem.id.site.siteId = :siteId ");
            queryBySiteOnItemRightsString.append("and siteOnItem.acceptDate is not null ");
            queryBySiteOnItemRightsString.append("and siteOnItem.id.item = item ");
            if (itemType == ItemType.ORDER_FORM) {// todo. Review this (maybe we should add new table?). Tolik
                queryBySiteOnItemRightsString.append("and item.formType = :orderFormType ");
            }
            if (itemType == ItemType.MENU) {// We should not show default site menus
                queryBySiteOnItemRightsString.append("and item.defaultSiteMenu = false ");
            }
            final Query queryBySiteOnItemRights = HibernateManager.get().createQuery(queryBySiteOnItemRightsString.toString());
            queryBySiteOnItemRights.setParameter("siteId", siteId);
            if (itemType == ItemType.ORDER_FORM) {// todo. Review this (maybe we should add new table?). Tolik
                queryBySiteOnItemRights.setParameter("orderFormType", FormType.ORDER_FORM);
            }
            final Set<DraftItem> draftItems = new HashSet<DraftItem>();
            draftItems.addAll(queryBySiteOwner.getResultList());
            draftItems.addAll(queryBySiteOnItemRights.getResultList());
            siteItems.addAll(draftItems);
            Collections.sort(siteItems, new Comparator<DraftItem>() {
                @Override
                public int compare(final DraftItem o1, final DraftItem o2) {
                    int result = o1.getName().compareTo(o2.getName());
                    // If items have same names with addition sort it by item id.
                    if (result == 0) {
                        return o1.getId() - o2.getId();
                    }
                    return result;
                }
            });
        }
        return siteItems;
    }

    public List<GalleryVideoRange> getGalleryVideoRangesByUserId(final int userId) {
        final Query query = HibernateManager.get().createQuery(
                "select galleryVideoRange from galleryVideoRanges as galleryVideoRange " +
                        "where galleryVideoRange.user.userId = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<ForumThread> getForumThreadsByUserId(final int userId) {
        Query query = HibernateManager.get().createQuery(
                "select forumThread from forumThreads as forumThread " +
                        "where forumThread.author.userId = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<ForumPost> getForumPostsByUserId(final int userId) {
        Query query = HibernateManager.get().createQuery(
                "select forumPost from forumPosts as forumPost " +
                        "where forumPost.author.userId = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<ChildSiteSettings> getChildSiteSettingsByUserId(final int userId) {
        Query query = HibernateManager.get().createQuery(
                "select childSiteSettings from childSiteSettings as childSiteSettings " +
                        "where childSiteSettings.userId = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<PageVisitor> getPageVisitorsByUserId(final int userId) {
        final Query query = HibernateManager.get().createQuery(
                "select pageVisitor from pageVisitors as pageVisitor where pageVisitor.userId = :userId");
        return query.setParameter("userId", userId).getResultList();
    }

    public List<DraftFormFilter> getFormFiltersByUserId(final int userId) {
        final List<DraftFormFilter> userFormFilters = new ArrayList<DraftFormFilter>();
        final Query queryByUserRights = HibernateManager.get().createQuery(
                "select formFilter from formFilters as formFilter, forms as form, userOnSiteRights as userOnSiteRights " +
                        "where form.id = formFilter.form.id and userOnSiteRights.id.site.siteId = form.siteId " +
                        "and userOnSiteRights.active = true " +
                        "and userOnSiteRights.id.user.userId = :userId");
        queryByUserRights.setParameter("userId", userId);
        userFormFilters.addAll(queryByUserRights.getResultList());
        return userFormFilters;
    }

    /*----------------------------------------------Get Items By UserId-----------------------------------------------*/
    private final HibernateInterceptor interceptor;

    public List<PageSettings> getPageSettingsWithHtmlOrCss() {
        final List<PageSettings> pageSettings = new ArrayList<PageSettings>();
        pageSettings.addAll(HibernateManager.get().createQuery("select settings from " +
                "draftPageSettings as settings where settings.html is not null or settings.css is not null").getResultList());

        pageSettings.addAll(HibernateManager.get().createQuery("select settings from " +
                "workPageSettings as settings where settings.html is not null or settings.css is not null").getResultList());

        return pageSettings;
    }
}
