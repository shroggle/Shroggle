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
package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.accessibility.UserOnSiteRightsCreator;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.SiteFormsManager;
import com.shroggle.logic.payment.PaymentSettingsOwnerManager;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.logic.site.item.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackSimple;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public class SiteManager {

    public SiteManager(final Site site) {
        this.site = site;
    }

    public SiteManager(final Integer siteId) {
        this.site = ServiceLocator.getPersistance().getSite(siteId);
    }

    public void publishBlueprint(final String description, final Map<Integer, Integer> pageScreenShots, final BlueprintCategory blueprintCategory) {
        final UserManager userManager = new UsersManager().getLogined();
        // Saving settings.
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            @Override
            public void run() {
                final PublicBlueprintsSettingsManager manager = getPublicBlueprintsSettings();
                manager.setDescription(description);
                manager.setBlueprintCategory(blueprintCategory);
                for (PageManager pageManager : getPages()) {
                    pageManager.setScreenShotId(pageScreenShots.get(pageManager.getId()));
                }
                getPublicBlueprintsSettings().publish();

                UserOnSiteRightsCreator.createRightsForAppAdmins(site);
            }
        });

        // Sending emails to admins.
        final MailSender mailSender = ServiceLocator.getMailSender();
        final International international = ServiceLocator.getInternationStorage().get("publishBlueprint", Locale.US);
        final String subject = international.get("reviewBlueprintEmailRequest");
        final String text = international.get(
                "publishBlueprintEmailBody",
                getName(),
                description,
                userManager.getFirstLastNamePair(),
                new BlueprintCategoryManager(blueprintCategory).getInternationalValue(),
                ServiceLocator.getConfigStorage().get().getApplicationUrl(),
                String.valueOf(DashboardSiteInfo.newInstance(site, DashboardSiteType.PUBLISHED_BLUEPRINT).hashCode()),
                userManager.getEmail()
        );
        for (String email : ServiceLocator.getConfigStorage().get().getAdminEmails()) {
            mailSender.send(new Mail(email, text, subject));
        }
    }

    public SiteManager activateBlueprint(
            final String description, final Map<Integer, Integer> pageScreenShots,
            final BlueprintCategory blueprintCategory) {
        final SiteManager self = this;
        return ServiceLocator.getPersistanceTransaction().execute(new PersistanceTransactionContext<SiteManager>() {

            @Override
            public SiteManager execute() {
                // Set published to null in original, so it can be published again.
                self.getPublicBlueprintsSettings().removePublishing();

                // Copying site from the original one
                final Site blueprint = new Site();
                blueprint.setThemeId(site.getThemeId());
                blueprint.setTitle(site.getTitle());
                blueprint.setType(SiteType.BLUEPRINT);
                persistance.putSite(blueprint);

                final List<Page> pages = PagesWithoutSystem.get(self.getSite().getPages());
                final Map<Integer, Integer> pageToCopiedPageIds = new HashMap<Integer, Integer>();
                final List<Page> copiedPages = SiteCopierUtil.copyPages(
                        pages, true, blueprint, pageToCopiedPageIds, new WidgetCrossIdSetterNone());


                /*--------------------------------------------Copying menu--------------------------------------------*/
                final DraftMenu copiedDraftMenu = ItemCopierUtil.copyMenu(self.getSite().getMenu());
                copiedDraftMenu.setSiteId(blueprint.getSiteId());
                blueprint.setMenu(copiedDraftMenu);
                ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                        pageToCopiedPageIds, self.getSite().getMenu().getMenuItems(), blueprint.getMenu(), null);
                persistance.putMenu(copiedDraftMenu);
                /*--------------------------------------------Copying menu--------------------------------------------*/

                final CopierStack copierStack = new CopierStackSimple(new CopierStackExecutorItemFull(
                        new ItemNamingSameAsInOriginal(), blueprint.getId(), pageToCopiedPageIds));

                final DraftForm defaultForm = persistance.getDraftItem(site.getDefaultFormId());
                blueprint.setDefaultFormId(copierStack.copy(defaultForm).getId());

                for (final Page page : copiedPages) {
                    for (final Widget widget : new PageManager(page).getWidgets()) {
                        if (!widget.isWidgetItem()) {
                            continue;
                        }

                        final WidgetItem widgetItem = (WidgetItem) widget;
                        if (widgetItem.getDraftItem() == null) {
                            continue;
                        }

                        final DraftItem draftItem = widgetItem.getDraftItem();
                        final DraftItem copiedDraftItem = copierStack.copy(draftItem);
                        widgetItem.setDraftItem(copiedDraftItem);
                    }

                    new PageManager(page).publish();
                }

                final SiteManager copy = new SiteManager(blueprint);

                // Saving publishing settings to copied site
                final PublicBlueprintsSettingsManager manager = copy.getPublicBlueprintsSettings();
                manager.setDescription(description);
                manager.setBlueprintCategory(blueprintCategory);

                for (Map.Entry<Integer, Integer> pageToCopiedPageId : pageToCopiedPageIds.entrySet()) {
                    final Page copiedPage = persistance.getPage(pageToCopiedPageId.getValue());
                    if (copiedPage != null) {
                        copiedPage.setScreenShotId(pageScreenShots.get(pageToCopiedPageId.getKey()));
                    }
                }

                // Activating copied site
                copy.getPublicBlueprintsSettings().activate();

                UserOnSiteRightsCreator.createRightsForAppAdmins(copy.getSite());

                return copy;
            }

        });
    }

    public void clearIncomeSettings() {
        final IncomeSettings incomeSettings = site.getIncomeSettings();
        if (incomeSettings == null) {
            return;
        }
        try {
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                    IncomeSettings.class, SynchronizeMethod.WRITE, incomeSettings.getIncomeSettingsId());
            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                        public void run() {
                            incomeSettings.setSum(0);
                            incomeSettings.setPaymentDetails(null);
                        }
                    });
                    return null;
                }
            });
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't zeroize site sum.", exception);
        }
    }

    public UserOnSiteRight getActiveRightForUser(final int userId) {
        for (final UserOnSiteRight userOnSiteRight : getActiveRightsOnUsers()) {
            if (userOnSiteRight.getId().getUser().getUserId() == userId) {
                return userOnSiteRight;
            }
        }
        return null;
    }

    public List<UserOnSiteRight> getActiveAdministratorRightsOnUsers() {
        final List<UserOnSiteRight> needRights = new ArrayList<UserOnSiteRight>();
        for (final UserOnSiteRight userOnSiteRight : getActiveRightsOnUsers()) {
            if (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                needRights.add(userOnSiteRight);
            }
        }
        return needRights;
    }

    public boolean isBlueprint() {
        return site.getType() == SiteType.BLUEPRINT;
    }

    public String getName() {
        return site.getTitle();
    }

    public int getDefaultFormId() {
        return site.getDefaultFormId();
    }

    public Page getFirstSitePage() {
        for (Page page : site.getPages()) {
            if (!page.isSystem()) {
                return page;
            }
        }

        return null;
    }

    public int getSiteId() {
        return getSite().getSiteId();
    }

    public String getPublicUrl() {
        return new SiteUrlGetter().get(site);
    }

    public static String getPublicUrl(final String customUrl, final String subDomain) {
        final String url;
        if (customUrl != null) {
            url = customUrl;
        } else {
            url = subDomain + "." + ServiceLocator.getConfigStorage().get().getUserSitesUrl();
        }

        // todo: I was thinking about adding a port from mail domain here, but then I gave up.
        if (url.toLowerCase().startsWith("www")) {
            return "http://" + url;
        } else {
            return "http://www." + url;
        }
    }

    public SiteFormsManager getForms() {
        return new SiteFormsManager(this);
    }

    /**
     * @return - list all pages from site without system
     */
    public List<PageManager> getPages() {
        // I`m getting site from the DB because we use lazy initialization for it`s "pages" List and if session
        // is closed here we`ll have an exception. Tolik
        final Site site = persistance.getSite(this.site.getSiteId());
        return PageManager.convertToPageManagers(PagesWithoutSystem.get(site.getPages()));
    }

    public PublicBlueprintsSettingsManager getPublicBlueprintsSettings() {
        if (site.getPublicBlueprintsSettings() == null) {
            site.setPublicBlueprintsSettings(new PublicBlueprintsSettings());
        }
        return new PublicBlueprintsSettingsManager(site.getPublicBlueprintsSettings());
    }

    public String getHisNetworkUrl() {
        final ChildSiteSettings childSiteSettings = site.getChildSiteSettings();
        if (childSiteSettings == null) {
            return "http://" + ServiceLocator.getConfigStorage().get().getApplicationUrl();
        } else {
            return new SiteManager(childSiteSettings.getParentSite()).getPublicUrl();
        }
    }

    public String getHisNetworkName() {
        final ChildSiteSettings childSiteSettings = site.getChildSiteSettings();
        if (childSiteSettings == null) {
            return ServiceLocator.getConfigStorage().get().getApplicationName();
        } else {
            return new ChildSiteSettingsManager(childSiteSettings).getNetworkName();
        }
    }

    /**
     * This method must be call when child site admin not network admin want disconnect
     * his site from network. By spec email about notification by this event must receive only
     * network admins.
     *
     * @link http://jira.web-deva.com/browse/SW-3270
     */
    public void optOutFromNetwork() {
        final String networkName = getHisNetworkName();
        final String fromEmail = !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail()) ? site.getChildSiteSettings().getFromEmail() : "";
        final List<UserOnSiteRight> userOnSiteRights = getActiveNetworkAdministratorRightsOnUsers();

        disconnectFromNetwork();

        final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        final International international = internationalStorage.get("siteLogic", Locale.US);
        final MailSender mailSender = ServiceLocator.getMailSender();
        for (final UserOnSiteRight right : userOnSiteRights) {
            final Mail mail = new Mail(
                    right.getId().getUser().getEmail(),
                    international.get("optOutFromNetworkMail", getName(), networkName),
                    international.get("optOutFromNetworkMailSubject", getName(), networkName));
            mail.setFrom(fromEmail);
            mailSender.send(mail);
        }
    }

    public void disconnectFromNetwork() {
        // I`m getting site from the DB because we use lazy initialization for it`s "userOnSiteRights" List and if session
        // is closed here we`ll have an exception. Tolik
        final Site site = persistance.getSite(this.site.getSiteId());
        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            @Override
            public Void execute() {
                disconnectFromBlueprint();

                if (site.getSitePaymentSettings() != null) {
                    final double newPrice = new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice();
                    // todo. Set correct charge type. Tolik
                    site.getSitePaymentSettings().setPrice(newPrice);
                    if (site.getSitePaymentSettings().getPaymentMethod().equals(PaymentMethod.PAYPAL)) {
                        PaymentSettingsOwnerManager paymentSettingsOwnerManager = new PaymentSettingsOwnerManager(site);
                        final PayPal paymentSystem = (PayPal) paymentSettingsOwnerManager.getAppropriatePaymentSystem();
                        if (paymentSystem.isShrogglePaymentSystem()) {
                            // If our payment system was used I`m just changing the price. Tolik
                            paymentSystem.updateProfilePrice(site.getSitePaymentSettings().getRecurringPaymentId(),
                                    newPrice, "Return site price to a standard one.");
                        } else {
                            // If this child site used network site`s owner`s own payment system I don`t know what to do.
                            // Maybe we must cancel old profile and activate new one in our system, but I`m not sure that
                            // it`ll work because PayPal need token and token which was registered in old paypal
                            // account may not work in our account. So I`m just deactivating this child site. Tolik
                            try {
                                new PaymentSettingsOwnerManager(site).deactivate();
                            } catch (Exception e) {
                                throw new RuntimeException("Unable to deactivate child site`s recurring profile!", e);
                            }
                        }
                    }
                }
                final FilledForm filledForm = persistance.getFilledFormById(site.getChildSiteFilledFormId());
                if (filledForm != null) {
                    filledForm.setChildSiteSettingsId(null);
                }
                ServiceLocator.getPersistance().removeChildSiteSettings(site.getChildSiteSettings());
                site.setChildSiteSettings(null);
                final List<UserOnSiteRight> rights = new ArrayList<UserOnSiteRight>(site.getUserOnSiteRights());
                for (final UserOnSiteRight right : rights) {
                    if (right.isFromNetwork()) {
                        persistance.removeUserOnSiteRight(right);
                    }
                }
                return null;
            }

        });
    }

    public void removeFromNetwork() {
        final String networkName = getHisNetworkName();
        final ChildSiteSettings childSiteSettings = site.getChildSiteSettings();
        final String fromEmail = !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail()) ? site.getChildSiteSettings().getFromEmail() : "";

        disconnectFromNetwork();

        final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        final International international = internationalStorage.get("siteLogic", Locale.US);
        final MailSender mailSender = ServiceLocator.getMailSender();
        final String applicationUrl = ServiceLocator.getConfigStorage().get().getApplicationUrl();
        for (final UserOnSiteRight right : getActiveAdministratorRightsOnUsers()) {
            final Mail mail = new Mail(
                    right.getId().getUser().getEmail(),
                    international.get("removeFromNetworkMail", getName(), networkName,
                            childSiteSettings.getPrice250mb(), applicationUrl),
                    international.get("removeFromNetworkMailSubject", getName(), networkName));
            mail.setFrom(fromEmail);
            mailSender.send(mail);
        }
    }

    public void disconnectFromBlueprint() {
        if (site.getBlueprintParent() == null) {
            return;
        }

        site.getBlueprintParent().removeBlueprintChild(site);
        site.setType(SiteType.COMMON);

        final List<SiteOnItem> itemRights = persistance.getSiteOnItemsBySite(site.getSiteId());
        copyChildSiteRegistrationFormsWithItsUsages(itemRights);
        removeBlueprintItemRights(itemRights);
    }

    public int getId() {
        return site.getSiteId();
    }

    public IncomeSettings getOrCreateIncomeSettings() {
        if (site.getIncomeSettings() == null) {
            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    IncomeSettings incomeSettings = new IncomeSettings();
                    site.setIncomeSettings(incomeSettings);
                    persistance.putIncomeSettings(incomeSettings);
                }

            });
        }
        return site.getIncomeSettings();
    }

    /**
     * @param parentSitePrice    - Price, saved in the parent sites "Child Site Registration".
     * @param chargeType         - Charge type (Web-Deva standard annual billing; site builder standard monthly billing;
     *                           One time fee from child site registration or monthly fee from child site registration).
     * @param infoAboutChildSite - Information for log about child site or child site settings (Site site title, siteId, or childSiteSettingsId).
     *                           In this method we calculate difference between our standard price and price from the
     *                           "Child Site Registration". If price from "Child Site Registration" more than our standard
     *                           price - we have to return this difference to parent site owner.
     *                           todo. Fix this method for case then our price more than price from "Child Site Registration"
     *                           todo. (I think that parent site owner has to pay us this difference).
     */
    public void addDifferenceToIncomeSettings(final double parentSitePrice, final ChargeType chargeType, final String infoAboutChildSite) {
        final IncomeSettings incomeSettings = getOrCreateIncomeSettings();
        final double shrogglePrice = new ChargeTypeManager(chargeType).getPrice();
        if (parentSitePrice > shrogglePrice) {
            persistanceTransaction.execute(new Runnable() {
                public strictfp void run() {
                    final double sum = (parentSitePrice - shrogglePrice);
                    incomeSettings.setSum(incomeSettings.getSum() + sum);
                    final String paymentDetails = "Sending money to parent site (" + site.getTitle() +
                            ", siteId = " + site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = " + sum + "$";
                    incomeSettings.addPaymentDetails(paymentDetails);
                }
            });
        }
    }

    public List<String> getAdminsEmails() {
        final List<String> emails = new ArrayList<String>();
        for (final User user : getAdmins()) {
            emails.add(user.getEmail());
        }
        return emails;
    }

    public List<User> getAdmins() {
        final List<User> users = new ArrayList<User>();
        for (final UserOnSiteRight right : getActiveAdministratorRightsOnUsers()) {
            users.add(right.getId().getUser());
        }
        return users;
    }

    public boolean isPendingChildSite() {
        return site.getChildSiteSettings() != null && !isActive();
    }

    public PublishingInfoResponse checkChildSiteStartDate() {
        final PublishingInfoResponse response = new PublishingInfoResponse();
        if (site != null && site.getChildSiteSettings() != null) {
            return new ChildSiteSettingsManager(site.getChildSiteSettings()).getPublishingInfo();
        }
        return response;
    }

    /**
     * @return - site entity
     */
    public Site getSite() {
        return site;
    }

    public void connectToBlueprint(final SiteManager blueprint, boolean publish) {
        new SiteCopierFromBlueprintReal().execute(blueprint.getSite(), site, publish);
    }

    public boolean isActive() {
        return getSiteStatus() == SiteStatus.ACTIVE;
    }

    public boolean isInactive() {
        return !isActive();
    }

    public boolean isAddPagesRestrictedByBlueprint() {
        return site.getBlueprintParent() != null && site.getBlueprintParent().getBlueprintRightType() == SiteBlueprintRightType.CANNOT_ADD_PAGE;
    }

    public Date getLastUpdatedDate() {
        return persistance.getLastUpdatedDate(site.getSiteId());
    }

    public SiteStatus getSiteStatus() {
        return site.getSitePaymentSettings().getSiteStatus();
    }

    public void setSiteStatus(SiteStatus siteStatus) {
        site.getSitePaymentSettings().setSiteStatus(siteStatus);
    }

    // todo. add tests. Tolik

    public void copyChildSiteRegistrationFormsWithItsUsages(final List<SiteOnItem> siteOnItems) {
        if (site.getChildSiteSettings() != null) {
            final FilledForm filledForm = persistance.getFilledFormById(site.getChildSiteFilledFormId());
            final DraftItem oldForm = persistance.getDraftItem(filledForm != null ? filledForm.getFormId() : -1);
            // Copying child site registration forms and add the to appropriate galleries
            if (oldForm instanceof DraftChildSiteRegistration) {
                try {

                    final ItemCopierContext context = new ItemCopierContext();
                    context.setCopiedSite(site);
                    context.setItemNaming(new ItemNamingNextFreeName());
                    final ItemCopyResult result = new ItemCopierSimple().execute(context, oldForm, null);
                    // Copying child site registration form to another csr form
                    final DraftChildSiteRegistration copiedForm = (DraftChildSiteRegistration) result.getDraftItem();
                    final Map<Integer, Integer> formItemsIdWithCopiedEquivalents = result.getFormItemsIdWithCopiedEquivalents();
                    // If this old childSiteRegistration had workItem we should post copied form too.
                    if (new ItemManager(oldForm).getWorkItem() != null) {
                        new ItemPosterReal().publish(copiedForm);
                    }
                    // Copying filled form
                    final Map<Integer, Integer> commentsIdWithGalleriesId = new FilledFormManager(filledForm).copyFilledForm(copiedForm, site, formItemsIdWithCopiedEquivalents);

                    // Selecting galleries from blueprint, copying them and setting copied CSR id.
                    for (DraftGallery oldGallery : getGalleriesWithFormUsagesFromBlueprint(siteOnItems, oldForm.getId())) {
                        final DraftGallery copiedGallery = new DraftGallery();
                        ItemCopierUtil.copyProperties(oldGallery, copiedGallery);
                        copiedGallery.setSiteId(site.getSiteId());
                        copiedGallery.setId(0);
                        copiedGallery.setName(SiteItemsManager.getNextDefaultName(copiedGallery.getItemType(), site.getSiteId(), copiedGallery.getName() + "_copy", false));
                        copiedGallery.setItems(new ArrayList<GalleryItem>());
                        copiedGallery.setLabels(new ArrayList<GalleryLabel>());
                        persistance.putItem(copiedGallery);

                        ItemCopierUtil.copyStyles(oldGallery, copiedGallery);
                        replaceOldFormUsagesInGallery(copiedGallery, oldForm.getId(), copiedForm, formItemsIdWithCopiedEquivalents,
                                oldGallery.getLabels(), oldGallery.getItems());

                        // If this old gallery had workItem we should post copied gallery too.
                        if (new ItemManager(oldGallery).getWorkItem() != null) {
                            new ItemPosterReal().publish(copiedGallery);
                        }
                        replaceAllGalleryUsagesInWidgets(oldGallery, copiedGallery);

                        // Setting new galleryId to copied GalleryComments (from filledForm) if needed.
                        if (commentsIdWithGalleriesId.containsValue(oldGallery.getId())) {
                            for (Integer commentId : commentsIdWithGalleriesId.keySet()) {
                                if (commentsIdWithGalleriesId.get(commentId) == oldGallery.getId()) {
                                    final GalleryComment copiedComment = persistance.getGalleryCommentById(commentId);
                                    copiedComment.setGallery(copiedGallery);
                                }
                            }
                        }
                        // Copying shared dataDisplayItem which connected to current gallery.
                        updateGalleryDataCrossWidgetId(copiedGallery, siteOnItems);
                    }
                    // Selecting galleries not from blueprint but with CSR usages and changing formId in them.
                    for (DraftGallery draftGallery : getGalleriesWithFormUsagesForCurrentSite(siteOnItems, oldForm.getId())) {
                        replaceOldFormUsagesInGallery(draftGallery, oldForm.getId(), copiedForm, formItemsIdWithCopiedEquivalents,
                                draftGallery.getLabels(), draftGallery.getItems());

                        // If this gallery had workItem we should repost it with new formIds
                        if (new ItemManager(draftGallery).getWorkItem() != null) {
                            new ItemPosterReal().publish(draftGallery);
                        }

                        // Copying shared dataDisplayItem which connected to current gallery.
                        updateGalleryDataCrossWidgetId(draftGallery, siteOnItems);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to copy childSiteRegistration with id = " + oldForm.getId(), e);
                }
            }
        }
    }

    private void updateGalleryDataCrossWidgetId(final DraftGallery copiedGallery, final List<SiteOnItem> siteOnItems) throws Exception {
        DraftGalleryData copiedDataItemCopy = null;
        Integer newDataCrossWidgetIdForGallery = null;
        if (copiedGallery.getDataCrossWidgetId() != null) {
            for (WidgetItem widget : getWidgetsWithGalleryData()) {
                if (copiedGallery.getDataCrossWidgetId().equals(widget.getParentCrossWidgetId())) {
                    final DraftGalleryData galleryData = (DraftGalleryData) ((WidgetItem) widget).getDraftItem();
                    if (galleryData != null && isFromBlueprint(siteOnItems, galleryData)) {
                        if (copiedDataItemCopy == null) {
                            final ItemCopierContext context = new ItemCopierContext();
                            context.setCopiedSite(site);
                            context.setItemNaming(new ItemNamingNextFreeName());
                            copiedDataItemCopy = (DraftGalleryData) new ItemCopierSimple().execute(context, galleryData, null).getDraftItem();
                            // If this old galleryData had workItem we should post copied galleryData too.
                            if (new ItemManager(galleryData).getWorkItem() != null) {
                                new ItemPosterReal().publish(copiedDataItemCopy);
                            }
                        }
                        ((WidgetItem) persistance.getWidget(widget.getWidgetId())).setDraftItem(copiedDataItemCopy);
                    }
                    newDataCrossWidgetIdForGallery = widget.getCrossWidgetId();
                    persistance.getWidget(widget.getWidgetId()).setParentCrossWidgetId(newDataCrossWidgetIdForGallery);
                }
            }
        }
        if (newDataCrossWidgetIdForGallery != null) {
            copiedGallery.setDataCrossWidgetId(newDataCrossWidgetIdForGallery);
        }
    }

    private List<WidgetItem> getWidgetsWithGalleryData() {
        Set<WidgetItem> widgetItems = new HashSet<WidgetItem>();
        for (Page page : site.getPages()) {
            final PageManager pageManager = new PageManager(page);
            widgetItems.addAll(selectGalleryDataWidgetItems(new PageSettingsManager(pageManager.getDraftPageSettings()).getWidgets()));
            widgetItems.addAll(selectGalleryDataWidgetItems(new PageSettingsManager(pageManager.getWorkPageSettings()).getWidgets()));
        }
        return new ArrayList<WidgetItem>(widgetItems);
    }

    private Set<WidgetItem> selectGalleryDataWidgetItems(final List<Widget> widgets) {
        final Set<WidgetItem> widgetItems = new HashSet<WidgetItem>();
        for (Widget widget : widgets) {
            if (widget.isWidgetItem() && widget.getItemType() == ItemType.GALLERY_DATA) {
                final DraftGalleryData galleryData = (DraftGalleryData) ((WidgetItem) widget).getDraftItem();
                if (galleryData != null) {
                    widgetItems.add((WidgetItem) widget);
                }
            }
        }
        return widgetItems;
    }


    private void replaceAllGalleryUsagesInWidgets(final Gallery oldGallery, final DraftGallery newGallery) {
        for (Page page : site.getPages()) {
            final PageManager pageManager = new PageManager(page);
            final List<Widget> widgets = new ArrayList<Widget>();
            widgets.addAll(selectGalleryDataWidgetItems(new PageSettingsManager(pageManager.getDraftPageSettings()).getWidgets()));
            widgets.addAll(selectGalleryDataWidgetItems(new PageSettingsManager(pageManager.getWorkPageSettings()).getWidgets()));
            for (Widget widget : widgets) {
                if (widget.isWidgetItem() && ((WidgetItem) widget).getDraftItem().getId() == oldGallery.getId()) {
                    Widget tempWidget = persistance.getWidget(widget.getWidgetId());
                    if (tempWidget != null) {
                        ((WidgetItem) tempWidget).setDraftItem(newGallery);
                    }
                }
            }
        }
    }

    private void replaceOldFormUsagesInGallery(final Gallery gallery, final int oldFormId, final DraftForm copiedForm,
                                               final Map<Integer, Integer> formItemsIdWithCopiedEquivalents,
                                               final List<GalleryLabel> labels, final List<GalleryItem> items) {
        gallery.setFormId1(copiedForm.getFormId());

        final List<GalleryLabel> copiedLabels = new ArrayList<GalleryLabel>();
        for (final GalleryLabel label : labels) {
            if (formItemsIdWithCopiedEquivalents.containsKey(label.getId().getFormItemId())) {
                final DraftGalleryLabel copiedLabel = new DraftGalleryLabel();
                ItemCopierUtil.copyProperties(label, copiedLabel);
                copiedLabel.getId().setFormItemId(formItemsIdWithCopiedEquivalents.get(label.getId().getFormItemId()));
                copiedLabel.getId().setGallery(gallery);
                copiedLabels.add(copiedLabel);
                persistance.putGalleryLabel(copiedLabel);
            }
        }
        gallery.setLabels(copiedLabels);

        final List<GalleryItem> copiedItems = new ArrayList<GalleryItem>();
        for (final GalleryItem item : items) {
            if (formItemsIdWithCopiedEquivalents.containsKey(item.getId().getFormItemId())) {
                final DraftGalleryItem copiedItem = new DraftGalleryItem();
                ItemCopierUtil.copyProperties(item, copiedItem);
                copiedItem.getId().setFormItemId(formItemsIdWithCopiedEquivalents.get(item.getId().getFormItemId()));
                copiedItem.getId().setGallery(gallery);
                copiedItems.add(copiedItem);
                persistance.putGalleryItem(copiedItem);
            }
        }
        gallery.setItems(copiedItems);

        final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
        if (formFilter != null && formFilter.getForm().getId() == oldFormId) {
            final DraftFormFilter copiedFormFilter = ItemCopierUtil.copyFormFilter(formFilter);
            copiedForm.addFilter(copiedFormFilter);
            persistance.putFormFilter(copiedFormFilter);
            for (final DraftFormFilterRule filterRule : formFilter.getRules()) {
                final DraftFormFilterRule copiedFilterRule = ItemCopierUtil.copyFilterRule(filterRule);
                copiedFormFilter.addRule(copiedFilterRule);
                copiedFilterRule.setFormItemId(formItemsIdWithCopiedEquivalents.get(filterRule.getFormItemId()));
                persistance.putFormFilterRule(copiedFilterRule);
            }
            gallery.setFormFilterId(copiedFormFilter.getFormFilterId());
        }
    }

    private List<DraftGallery> getGalleriesWithFormUsagesFromBlueprint(final List<SiteOnItem> siteOnItems, final int formId) {
        final Set<DraftGallery> galleries = new HashSet<DraftGallery>();
        for (SiteOnItem siteOnItem : siteOnItems) {
            if (siteOnItem.getItem() instanceof DraftGallery) {
                final DraftGallery gallery = (DraftGallery) siteOnItem.getItem();
                if (gallery.getFormId1() == formId && siteOnItem.isFromBlueprint()) {
                    galleries.add(gallery);
                }
                final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
                if (formFilter != null && formFilter.getForm().getId() == formId && siteOnItem.isFromBlueprint()) {
                    galleries.add(gallery);
                }
            }
        }
        // Getting galleries connected to galleryData.
        for (WidgetItem widgetItem : getWidgetsWithGalleryData()) {
            for (final DraftGallery gallery : persistance.getGalleriesByDataCrossWidgetIds(widgetItem.getCrossWidgetId(),
                    widgetItem.getParentCrossWidgetId())) {
                // If this gallery is from blueprint or user has no rights to it - we should copy it.
                if ((isFromBlueprint(siteOnItems, gallery) || !isFromRightsOrSiteIsOwner(siteOnItems, gallery))) {
                    galleries.add(gallery);
                }
            }
        }
        return new ArrayList<DraftGallery>(galleries);
    }

    private List<DraftGallery> getGalleriesWithFormUsagesForCurrentSite(final List<SiteOnItem> siteOnItems, final int formId) {
        final Set<DraftGallery> galleries = new HashSet<DraftGallery>();

        final Set<DraftGallery> galleriesUsedOnThisSite = new HashSet<DraftGallery>();
        galleriesUsedOnThisSite.addAll(persistance.getGalleriesBySiteId(site.getSiteId()));
        for (SiteOnItem siteOnItem : siteOnItems) {
            if (siteOnItem.getItem() instanceof DraftGallery && !siteOnItem.isFromBlueprint()) {
                galleriesUsedOnThisSite.add((DraftGallery) siteOnItem.getItem());
            }
        }

        for (DraftGallery gallery : galleriesUsedOnThisSite) {
            if (gallery.getFormId1() == formId) {
                galleries.add(gallery);
            }
            final DraftFormFilter formFilter = persistance.getFormFilterById(gallery.getFormFilterId());
            if (formFilter != null && formFilter.getForm().getId() == formId) {
                galleries.add(gallery);
            }
        }
        return new ArrayList<DraftGallery>(galleries);
    }

    private boolean isFromBlueprint(final List<SiteOnItem> itemRights, final DraftItem draftItem) {
        for (SiteOnItem siteOnItem : itemRights) {
            if (siteOnItem.getItem().getId() == draftItem.getId() && siteOnItem.isFromBlueprint()) {
                return true;
            }
        }
        return false;
    }

    private boolean isFromRightsOrSiteIsOwner(final List<SiteOnItem> itemRights, final DraftItem draftItem) {
        if (site.getSiteId() == draftItem.getSiteId()) {
            return true;
        }
        for (SiteOnItem siteOnItem : itemRights) {
            if (siteOnItem.getItem().getId() == draftItem.getId()) {
                return true;
            }
        }
        return false;
    }


    private List<UserOnSiteRight> getActiveRightsOnUsers() {
        final List<UserOnSiteRight> needRights = new ArrayList<UserOnSiteRight>();
        final Site site = persistance.getSite(this.site.getSiteId());
        for (final UserOnSiteRight userOnSiteRight : site.getUserOnSiteRights()) {
            if (userOnSiteRight.isActive() && userOnSiteRight.getId().getUser().getActiveted() != null) {
                needRights.add(userOnSiteRight);
            }
        }
        return needRights;
    }

    private List<UserOnSiteRight> getActiveNetworkRightsOnUsers() {
        final List<UserOnSiteRight> needRights = new ArrayList<UserOnSiteRight>();
        final Site parentSite = site.getChildSiteSettings().getParentSite();
        final List<UserOnSiteRight> userOnSiteRights = parentSite.getUserOnSiteRights();
        for (final UserOnSiteRight userOnSiteRight : userOnSiteRights) {
            if (userOnSiteRight.isActive() && userOnSiteRight.getId().getUser().getActiveted() != null
                    && userOnSiteRight.getId().getUser().getRegistrationDate() != null) {
                needRights.add(userOnSiteRight);
            }
        }
        return needRights;
    }

    private List<UserOnSiteRight> getActiveNetworkAdministratorRightsOnUsers() {
        final List<UserOnSiteRight> needRights = new ArrayList<UserOnSiteRight>();
        for (final UserOnSiteRight userOnSiteRight : getActiveNetworkRightsOnUsers()) {
            if (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                needRights.add(userOnSiteRight);
            }
        }
        return needRights;
    }

    private void removeBlueprintItemRights(List<SiteOnItem> itemRights) {
        final List<SiteOnItem> tempItemRights = new ArrayList<SiteOnItem>(itemRights);
        for (final SiteOnItem itemRight : tempItemRights) {
            if (itemRight.isFromBlueprint()) {
                persistance.removeSiteOnItemRight(itemRight);
            }
        }
    }

    private final Site site;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
