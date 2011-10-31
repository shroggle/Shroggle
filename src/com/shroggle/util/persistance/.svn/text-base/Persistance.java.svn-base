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

package com.shroggle.util.persistance;

import com.shroggle.entity.*;
import com.shroggle.logic.start.Alter;
import com.shroggle.logic.statistics.DateInterval;

import java.util.*;

/**
 * @author Stasuk Artem
 */
public interface Persistance {

    void putPageVisitor(PageVisitor pageVisitor);

    void putAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption);

    void putVisit(Visit visit);

    void putIPNLog(IPNLog ipnLog);

    void putVisitReferrer(VisitReferrer visitReferrer);

    void putIncomeSettings(IncomeSettings incomeSettings);

    void putSitePaymentSettings(SitePaymentSettings sitePaymentSettings);

    SitePaymentSettings getSitePaymentSettingsById(int sitePaymentSettingsId);

    IncomeSettings getIncomeSettingsById(int incomeSettingsId);

    DraftFormItem getFormItemById(Integer formItemId);

    boolean isRegistrationUsedOnAnySiteAsDefault(Integer formId);

    void putCreditCard(CreditCard creditCard);

    void putManageVotesGallerySettings(DraftManageVotesSettings manageVotesGallerySettings);

    void putFormFilterRule(DraftFormFilterRule formFilterRule);

    void putMenu(Menu menu);

    void putFormFilter(DraftFormFilter formFilter);

    List<BlogPost> getBlogPostsByBlog(int blogId);

    int getBlogPostsCountByBlog(int blogId);

    Map<Integer, Integer> getBlogPostsCountByBlogs(Set<Integer> blogsId);

    long getHitsForPages(List<Integer> pageIds, DateInterval dateInterval);

    Map<Integer, Long> getHitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval);

    //

    Map<Integer, Long> getUniqueVisitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval);

    //

    long getUniqueVisitsCountForPages(List<Integer> pageIds, DateInterval dateInterval);

    //

    Map<String, Integer> getRefUrlsByPages(List<Integer> pageIds, DateInterval dateInterval);

    //

    Map<String, Integer> getRefSearchTermsByPages(List<Integer> pageIds, DateInterval dateInterval);

    //

    long getOverallTimeForPages(List<Integer> pageIds, DateInterval dateInterval);

    Map<Integer, Long> getOverallTimeForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval);

    PageVisitor getPageVisitorById(Integer pageVisitorId);

    DraftAdvancedSearchOption getAdvancedSearchOptionById(int advancedSearchOptionId);

    List<PageVisitor> getPageVisitorsByUserId(int userId);

    DraftForm getFormById(Integer formId);

    DraftFormFilterRule getFormFilterRuleById(int formFilterRuleId);

    DraftFormFilter getFormFilterById(Integer filterId);

    DraftFormFilter getFormFilterByNameAndUserId(String name, int userId);

    DraftFormFilter getFormFilterByNameAndFormId(String name, int formId);

    Visit getVisitByPageIdAndUserId(int pageId, int userId);

    User getUserByEmail(String email);

    List<DraftFormFilter> getFormFiltersByUserId(int userId);

    List<Site> searchSites(String siteTitle, String siteUrlPrefix);

    List<FilledForm> getFilledFormsByFormId(int formId);

    int getFilledFormsCountByFormId(int formId);

    Map<Integer, Integer> getFilledFormsCountByFormsId(Set<Integer> formsId);

    List<FilledForm> getFilledFormsByFormAndUserId(int formId, int userId);

    long getFilledFormsNumberByFormId(int formId);

    Integer getMaxFilledFormIdByFormId(int formId);

    void putEmailUpdateRequest(EmailUpdateRequest emailUpdateRequest);

    EmailUpdateRequest getEmailUpdateRequestById(String emailUpdateRequestId);

    void putUser(User user);

    void putFormItem(DraftFormItem formItem);

    void putSlideShowImage(DraftSlideShowImage slideShowImage);

    void putWorkSlideShowImage(WorkSlideShowImage slideShowImage);

    FilledForm getFilledFormById(Integer filledFormId);

    FilledFormItem getFilledFormItemById(Integer filledFormItemId);

    List<FilledFormItem> getFilledFormItemByFormItemId(Integer formItemId);

    void putFilledForm(FilledForm filledForm);

    void putFilledFormItem(FilledFormItem filledFormItem);

    Date getLastFillFormDateByFormId(int formId);

    Map<Integer, Date> getLastFillFormDateByFormsId(Set<Integer> blogsId);

    void putRegistrationForm(DraftRegistrationForm registrationForm);

    void putCustomForm(DraftCustomForm customForm);

    void putSubForum(SubForum subForum);

    void putForumThread(ForumThread forumThread);

    void putForumPost(ForumPost forumPost);

    User getUserById(Integer userId);

    UserOnSiteRight getUserOnSiteRightById(UserOnSiteRightId userOnSiteRightId);

    UserOnSiteRight getUserOnSiteRightByUserAndSiteId(Integer userId, Integer siteId);

    UserOnSiteRight getUserOnSiteRightByUserAndFormId(User user, Integer formId);

    FilledForm getFilledRegistrationFormByUserAndFormId(User user, Integer formId);

    ForumPost getForumPostById(int postId);

    /**
     * Now this method not provide subDomain normalize
     * features for use it see on SiteByUrlGetter
     *
     * @param subDomain - sub domain
     * @return - site or null
     * @see com.shroggle.logic.site.SiteByUrlGetter
     */
    Site getSiteBySubDomain(String subDomain);

    void putSite(Site site);

    int getThreadVotesCount(int threadId, int answerNumber);

    List<User> getVisitorsBySiteId(int siteId);

    BlogPost getLastBlogPost(int blogId);

    Map<Integer, Date> getLastBlogPostsDate(Set<Integer> blogsId);

    /**
     * @param blogId
     * @param userId       - if visitor anonym use Integer.MIN_VALUE, if is owner use null
     * @param blogPostId
     * @param start
     * @param count        - if null return all result items
     * @param notOlderThan - Date. return blogs with creationDate after or equal to this parameter
     * @return
     */
    List<BlogPost> getBlogPosts(int blogId, Integer userId, Integer blogPostId, int start, int count, Date notOlderThan);

    /**
     * @param blogId     -blog id
     * @param userId     - if visitor anonym use Integer.MIN_VALUE, if is owner use null
     * @param blogPostId - blogPostId
     * @return - count blog posts before it's
     */
    int getBlogPostsBeforeByBlogAndUserId(
            int blogId, Integer userId, int blogPostId);

    int getPrevOrNextBlogPostId(
            int blogId, Integer userId, int blogPostId, boolean prev);


    /**
     * @param blogId
     * @param userId-    if visitor anonym use Integer.MIN_VALUE, if is owner use null
     * @param blogPostId
     * @return -true if blog post has one or more blog posts after it's
     */
    int getBlogPostsAfterByBlogAndUserId(
            int blogId, Integer userId, int blogPostId);

    CreditCard getCreditCardById(Integer creditCardId);

    DraftContactUs getContactUsById(int contactUsId);

    DraftContactUs getContactUsByNameAndSiteId(String name, int siteId);

    void putContactUs(DraftContactUs contactUs);

    void putBlogPost(BlogPost blogPost);

    BlogPost getBlogPostById(int blogPostId);

    Comment getCommentById(int commentId);

    void putComment(Comment comment);

    List<SubForum> getSubForumsByForumId(int forumId);

    int getSubForumsCountByForumId(int forumId);

    Map<Integer, Integer> getSubForumsCountByForumsId(Set<Integer> forumsId);

    List<ForumThread> getForumThreads(int subForumId);

    List<ForumThread> getForumThreadsByUserId(int userId);

    List<ForumPost> getForumPostsByUserId(int userId);

    List<ForumPost> getForumPosts(int forumThreadId);

    void putVote(ForumPollVote forumPollVote);

    ForumPollVote getForumThreadVoteByRespondentIdAndThreadId(String respondentId, int threadId);

    Site getSite(Integer siteId);

    SlideShowImage getSlideShowImageById(Integer slideShowImageId);

    String getSiteTitleBySiteId(int siteId);

    DraftRegistrationForm getRegistrationFormById(int formId);

    DraftCustomForm getCustomFormById(int formId);

    DraftForum getForumByNameAndSiteId(String forumName, int siteId);

    DraftRegistrationForm getRegistrationFormByNameAndSiteId(String name, int siteId);

    DraftSlideShow getSlideShowByNameAndSiteId(String name, int siteId);

    DraftMenu getMenuByNameAndSiteId(String name, int siteId);

    DraftShoppingCart getShoppingCartByNameAndSiteId(String name, int siteId);

    DraftPurchaseHistory getPurchaseHistoryByNameAndSiteId(String name, int siteId);

    DraftAdvancedSearch getDraftAdvancedSearch(String name, int siteId);

    DraftChildSiteRegistration getChildSiteRegistrationFormByNameAndSiteId(String name, int siteId);

    DraftCustomForm getCustomFormByNameAndSiteId(String name, int siteId);

    DraftBlogSummary getBlogSummaryByNameAndSiteId(String blogSummaryName, int siteId);

    DraftAdminLogin getAdminLoginByNameAndSiteId(String name, int siteId);

    DraftTaxRatesUS getTaxRatesByNameAndSiteId(String taxRateName, int siteId);

    List<DraftMenu> getMenusBySiteId(int siteId);

    List<DraftMenu> getMenusWithDefaultStructureBySiteId(int siteId);

    DraftMenu getMenuById(Integer menuId);

    DraftBlog getBlogByNameAndSiteId(String blogName, int siteId);

    SubForum getSubForumById(int subForumId);

    ForumThread getForumThreadById(int threadId);

    void removePost(ForumPost forumPost);

    void removeFormFilter(DraftFormFilter formFilter);

    void removeFormFilterRule(DraftFormFilterRule formFilterRule);

    void removeThread(ForumThread forumThread);

    void removeFormItem(DraftFormItem formItem);

    void removeFilledForm(FilledForm form);

    void removeSubForum(SubForum subForum);

    void removeSlideShowImage(SlideShowImage slideShowImage);

    List<DraftGallery> getGalleriesByFormId(Integer formId);

    Image getImageById(Integer imageId);

    BackgroundImage getBackgroundImageById(int backgroundImageId);

    MenuImage getMenuImageById(Integer menuImageId);

    ImageForVideo getImageForVideoById(int imageId);

    void putFormFile(FormFile formFile);

    FormFile getFormFileById(Integer formFileId);

    List<FormFile> getFormFiles();

    void removeFormFile(FormFile formFile);

    void putImageFile(ImageFile imageFile);

    ImageFile getImageFileById(int imageFileId);

    List<ImageFile> getImageFilesBySiteId(int siteId);

    List<ImageFile> getImageFilesByTypeAndSiteId(ImageFileType type, Integer siteId);

    void removeImageFile(ImageFile imageFile);

    void removeFilledFormItem(FilledFormItem filledFormItem);

    void putImage(Image image);

    void putBackgroundImage(BackgroundImage image);

    void putMenuImage(MenuImage image);

    void putImageForVideo(ImageForVideo image);

    void putStyle(Style style);

    Style getStyleById(int styleId);

    void removeStyle(Style style);

    void putBorder(Border borderBackground);

    void putBackground(Background borderBackground);

    Border getBorder(Integer borderId);

    Background getBackground(Integer backgroundId);

    void removeBorderBackground(Border borderBackground);

    void putVideo(Video video);

    void putFlvVideo(FlvVideo flvVideo);

    Video getVideoById(Integer videoId);

    FlvVideo getFlvVideo(Integer flvVideoId);

    FlvVideo getFlvVideo(Integer sourceVideoId, Integer width, Integer height, Integer quality);

    /**
     * This method return all draftItems(by they itemType), what available for user over
     * his sites. Of course it find items what related to site as owner and
     * sites what related to site across siteOnItemRight. This method remove
     * duplicate blogs from result list.
     *
     * @param userId   - user`s id
     * @param itemType - type of items to be returned
     * @return - list of blogs
     */
    List<DraftItem> getDraftItemsByUserId(int userId, ItemType itemType);

    List<DraftItem> getDraftItemsBySiteId(int siteId, ItemType itemType, ItemType... excludedItemTypes);

    List<Site> getSites(int userId, SiteAccessLevel[] accessLevels, SiteType... siteTypes);

    List<User> getUsersWithActiveRights(Integer siteId, SiteAccessLevel[] accessLevels);

    List<User> getUsersWithRightsToSite(Integer siteId, SiteAccessLevel[] accessLevels);

    List<UserOnSiteRight> getUserOnSiteRights(Integer siteId, SiteAccessLevel[] accessLevels);

    List<BackgroundImage> getBackgroundImagesBySiteId(Integer siteId);

    List<ImageForVideo> getImagesForVideoBySiteId(Integer siteId);

    List<Video> getVideosBySiteId(Integer siteId);

    List<Video> getVideos();

    void putCssParameterValue(CssParameterValue cssParameterValue);

    void putCssValue(CssValue cssValue);

    void removeCssParameterValue(CssParameterValue cssParameterValue);

    void removeBlogPost(BlogPost blogPost);

    void removeVote(Vote vote);

    void removeVotesByFilledFormId(Integer filledFormId);

    void removeManageVotesGallerySettings(ManageVotesSettings manageVotesGallerySettings);

    /**
     * This method reset to null field author in blog and forum posts.
     *
     * @param user - user
     */
    void removeUser(User user);

    void removeUserOnSiteRight(UserOnSiteRight userOnSiteRight);

    void removeAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption);

    void removeComment(Comment comment);

    void removeSite(Site site);

    ForumPost getLastThreadPost(int forumThreadId);

    ForumPost getLastForumPost(int forumId);

    Map<Integer, Date> getLastForumPosts(Set<Integer> forumsId);

    ForumPost getLastSubForumPost(int subForumId);

    Content getContentById(ContentId contentId);

    void putContent(Content content);

    int getMaxContentClientId();

    void putKeywordsGroup(KeywordsGroup keywordsGroup);

    KeywordsGroup getKeywordsGroupById(int keywordsGroupId);

    /**
     * Attenshion. Before remove keywordsGroup this method
     * remove all pageKeywordsGroup what referens on this keywordsGroup
     *
     * @param keywordsGroup - removed item
     */
    void removeKeywordsGroup(KeywordsGroup keywordsGroup);

    void removeContent(Content content);

    void destroy();

    void removeImage(Image image);

    Gallery getGalleryByOrderFormId(Integer orderFormId);

    void putUserOnSiteRight(UserOnSiteRight userOnSiteRight);

    void removeDraftItem(DraftItem draftItem);

    void removeCreditCard(CreditCard creditCard);

    <R> R inContext(PersistanceContext<R> persistanceContext);

    Site getSiteByCustomUrl(String customUrl);

    <T extends DraftItem> T getDraftItem(Integer itemId);

    <T extends WorkItem> T getWorkItem(Integer workItemId);

    int getUsersCount();

    void addUpdateListener(PersistanceListener listener);

    void addRemoveListener(PersistanceListener listener);

    void putJournalItem(JournalItem journalItem);

    /**
     * @param registeredTo - date
     * @param countUsers   - method not return all users, it return first n users.
     * @return - return users with registered date less registered to. This list sorted by registered date "abc"
     */
    List<User> getNotActivatedUsers(Date registeredTo, int countUsers);

    DraftChildSiteRegistration getChildSiteRegistrationById(Integer childSiteRegistrationId);

    ChildSiteSettings getChildSiteSettingsById(Integer childSiteSettingsId);

    void putChildSiteSettings(ChildSiteSettings childSiteSettings);

    void removeChildSiteSettings(ChildSiteSettings childSiteSettings);

    List<ChildSiteSettings> getChildSiteSettingsByUserId(int userId);

    List<Image> getImagesByOwnerSiteId(int siteId);

    Image getImageByNameAndSiteId(String name, int siteId);

    List<Integer> getSitesThatContainGalleriesWithEcommerce(List<Integer> siteIds);

    /**
     * @param userId        - user what want has sites
     * @param invitedUserId - user what has sites
     * @return - return all not active user on site rights what create invited user on their sites for user.
     */
    List<UserOnSiteRight> getNotActiveUserOnSiteRightsByUserAndInvitedUser(int userId, int invitedUserId);

    List<FilledForm> getUserPurchasesOnSite(int userId, int siteId);

    /**
     * @return - return all sites in system
     */
    List<Site> getAllSites();

    /**
     * @return - return all sites with "IncomeSettings" and with sum in "IncomeSettings" > 0
     */
    List<Site> getSitesWithNotEmptyIncomeSettings();

    /**
     * @return - return all child sites (where childSiteSettings != null)
     */
    List<Site> getChildSites();

    /**
     * @param creditCardId - Credit Card Id.
     * @return - return all sites connected to credit card (site.sitePaymentSettings.creditCardId = creditcardId)
     */
    List<Site> getSitesConnectedToCreditCard(int creditCardId);

    /**
     * @param siteOnItem - to persist
     * @link http://jira.web-deva.com/browse/SW-1478
     */
    void putSiteOnItem(SiteOnItem siteOnItem);

    SiteOnItem getSiteOnItemRightBySiteIdItemIdAndType(int siteId, int itemId, ItemType type);

    /**
     * This method remove right and reset for all widget in site that loss access
     * this item an example widgetBlog.setBlogId(null)
     *
     * @param siteOnItemRight - right
     * @link http://jira.web-deva.com/browse/SW-1865
     * @link http://jira.web-deva.com/browse/SW-2110
     */
    void removeSiteOnItemRight(SiteOnItem siteOnItemRight);

    int getChildSiteSettingsCountByRegistrationId(int childSiteRegistrationId);

    DraftGallery getGalleryByNameAndSiteId(String name, int siteId);

    DraftGallery getGalleryById(Integer galleryId);

    void putGalleryLabel(DraftGalleryLabel label);

    void putGalleryItem(DraftGalleryItem item);

    DraftGalleryItem getGalleryItemById(int galleryId, int formItemId);

    void removeGalleryLabel(DraftGalleryLabel label);

    void removeGalleryItem(DraftGalleryItem item);

    void putItem(Item siteItem);

    List<GalleryVideoRange> getGalleryVideoRangesByUserId(int userId);

    List<GalleryVideoRange> getGalleryVideoRanges(List<Integer> videoRangeIds);

    void putGalleryVideoRange(GalleryVideoRange galleryVideoRange);

    DraftTellFriend getTellFriendById(int tellFriendId);

    void putTellFriend(DraftTellFriend tellFriend);

    List<DraftTellFriend> getTellFriendsBySiteId(int siteId);

    List<DraftManageVotes> getManageVotesListBySiteId(int siteId);

    List<DraftGallery> getGalleriesBySiteId(int siteId);

    DraftTellFriend getTellFriendByNameAndSiteId(String name, int siteId);

    DraftManageVotes getManageVotesByNameAndSiteId(String name, int siteId);

    void putVote(Vote vote);

    Vote getVoteById(Integer voteId);

    void setAllWinnerVotesToFalse(Integer userId, Integer galleryId);

    List<Vote> getVotesByStartEndDates(Integer userId, Integer galleryId, Date startDate, Date endDate, Integer... filledFormIds);

    List<Vote> getVotesByTimeInterval(Integer userId, Integer galleryId, Date startDate, Date endDate, Integer... filledFormIds);

    void putGalleryComment(GalleryComment galleryComment);

    List<GalleryComment> getGalleryCommentsByFilledFormAndGallery(
            int filledFormId, int galleryId, Integer userId, Date start, Date finish);

    GalleryComment getGalleryCommentById(int galleryCommentId);

    void removeGalleryComment(GalleryComment galleryComment);

    void removeGalleryVideoRange(GalleryVideoRange galleryVideoRange);

    void putFormVideo(FormVideo formVideo);

    FormVideo getFormVideoById(Integer formVideoId);

    List<FormVideo> getAllFormVideos();

    List<DraftForm> getAllForms();

    List<DraftChildSiteRegistration> getAllChildSiteRegistrations();

    void putPaymentLog(PaymentLog paymentLog);

    PaymentLog getPaymentLogById(int logId);

    List<PaymentLog> getPaymentLogsByUsersId(int userId);

    List<PaymentLog> getAllPaymentLogs();

    List<PaymentLog> getPaymentLogsByChildSiteSettingsId(int childSiteSettingsId);

    List<Integer> getPageParentIds(int siteId, Integer parentId);

    void putMenuItem(MenuItem menuItem);

    /**
     * @param menuItem - NewMenuItem
     */
    void removeMenuItem(MenuItem menuItem);

    DraftMenuItem getDraftMenuItem(Integer menuItemId);

    WorkMenuItem getWorkMenuItem(Integer menuItemId);

    List<DraftMenuItem> getMenuItems(Integer pageId);

    void setMenuItemsIncludeInMenu(List<Integer> menuItemsIds, boolean includeInMenu);

    Coordinate getCoordinate(String zip, Country country);

    void putCoordinate(Coordinate coordinate);

    void executeAlter(Alter alter);

    void executeAlters(List<Alter> alters);

    List<SiteOnItem> getSiteOnItemsByItem(int siteItemId);

    List<SiteOnItem> getSiteOnItemsBySite(int siteId);

    PaymentSettingsOwner getPaymentSettingsOwner(Integer id, PaymentSettingsOwnerType type);

    void removeWorkFormItems(Integer formId);

    void removeWorkFilters(Integer formId);

    void putWorkFilter(WorkFormFilter workFilter);

    void putWorkFormItem(WorkFormItem workFormItem);

    void removeWorkGalleryItems(Integer galleryId);

    void removeWorkGalleryLabels(Integer galleryId);

    void putWorkGalleryItem(WorkGalleryItem workItem);

    void putWorkGalleryLabel(WorkGalleryLabel workItem);

    void removeDraftItemCssValues(Integer draftItemId);

    void removeWidgetCssValues(Integer widgetId);

    void removeWorkCssValues(Integer workItemId);

    List<Integer> getDraftItemIds();

    void putGroup(Group group);

    void removeGroup(Group group);

    void removeGroup(int groupId);

    Group getGroup(Integer groupId);

    List<Group> getGroups(List<Integer> groupIds);

    List<User> getUsersWithAccessToGroup(int groupId);

    List<User> getUsersByUsersId(List<Integer> usersId);

    <T extends DraftItem> Map<T, List<WidgetItem>> getItemsBySiteAndUser(
            int userId, int siteId, ItemType type, boolean onlyCurrentSite);

    int getUsersCountWithAccessToGroup(Integer groupId);

    void removeDraftItem(Integer itemId);

    void removeWorkAdvancedSearchOptions(Integer itemId);

    void putWorkAdvancedSearchOption(WorkAdvancedSearchOption workAdvancedSearchOption);

    void removeWorkManageVotesSettings(Integer itemId);

    void putWorkManageVotesSettings(WorkManageVotesSettings workManageVotesSettings);

    AccessibleForRender getAccessibleElement(Integer id, AccessibleElementType type);

    void putAccessibleSettings(AccessibleSettings accessibleSettings);

    AccessibleSettings getAccessibleSettings(Integer accessibleSettingsId);

    void removeWorkItem(WorkItem workItem);

    void removeWorkItem(Integer workItemId);

    PurchaseMailLog getPurchaseMailLog(Integer purchaseMailLogId);

    void putPurchaseMailLog(PurchaseMailLog purchaseMailLog);

    List<PurchaseMailLog> getAllPurchaseMailLogs();

    List<FormFileShouldBeCopied> getAllFormFileShouldBeCopied();

    List<VideoShouldBeCopied> getAllVideoShouldBeCopied();

    void putVideoShouldBeCopied(VideoShouldBeCopied videoShouldBeCopied);

    void putFormFileShouldBeCopied(FormFileShouldBeCopied formFileShouldBeCopied);

    UsersGroup getUsersGroup(UsersGroupId usersGroupId);

    void putUsersGroup(UsersGroup usersGroup);

    void removeUsersGroup(UsersGroup usersGroup);

    List<User> getAllUsers();

    public Map<DraftItem, List<WidgetItem>> getItems(List<Integer> siteIds, ItemType itemType, SiteShowOption siteShowOption);

    List<DraftGallery> getGalleriesByDataCrossWidgetIds(Integer... crossWidgetId);

    List<WidgetItem> getWidgetItemsByGalleriesId(Collection<Integer> galleriesId);

    void putPageSettings(final PageSettings pageSettings);

    DraftPageSettings getDraftPageSettings(final Integer pageSettingsId);

    WorkPageSettings getWorkPageSettings(final Integer pageSettingsId);

    void removePageSettings(final PageSettings pageSettings);

    /*-----------------------------------------------------Pages------------------------------------------------------*/

    List<KeywordsGroup> getKeywordsGroups(List<Integer> keywordsGroupsId);

    List<SiteTitlePageName> getWorkSiteTitlePageNames(List<Integer> siteIds);

    List<SiteTitlePageName> getSiteTitlePageNamesByUserId(final int userId);

    List<SiteTitlePageName> getSiteTitlePageNamesBySiteId(final int siteId);

    Page getPageByOwnDomainName(String ownDomainName);

    Page getPageByNameAndSite(String name, int siteId);

    Page getPageByUrlAndAndSiteIgnoreUrlCase(String url, int siteId);

    Page getPage(Integer pageId);

    void putPage(Page page);

    void removePage(Page page);
    /*-----------------------------------------------------Pages------------------------------------------------------*/

    /*-----------------------------------------------------Widgets----------------------------------------------------*/

    void putWidget(Widget widget);

    Widget getWidget(Integer widgetId);

    void removeWidget(Widget widget);

    List<Widget> getWidgets(List<Integer> widgetsId);

    Widget getWidgetByCrossWidgetsId(Integer crossWidgetId, SiteShowOption siteShowOption);

    List<Widget> getWidgetsByCrossWidgetsId(List<Integer> crossWidgetId, SiteShowOption siteShowOption);

    List<Widget> getWorkWidgetsByCrossWidgetsId(List<Integer> crossWidgetId);

    List<Widget> getDraftWidgetsByCrossWidgetsId(List<Integer> crossWidgetId);

    List<Widget> getWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption);

    Widget getFirstWidgetByItemId(int itemId);

    List<WidgetItem> getGalleryDataWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption);
    /*-----------------------------------------------------Widgets----------------------------------------------------*/

    Icon getIcon(final Integer iconId);

    void putIcon(final Icon icon);

    void removeIcon(final Icon icon);

    void removeIcon(final Integer iconId);

    void putCSVDataExport(CSVDataExport csvDataExport);

    CSVDataExport getCSVDataExport(Integer id);

    void putCSVDataExportField(CSVDataExportField field);

    CSVDataExportField getCSVDataExportField(Integer fieldId);

    void removeCSVDataExportField(CSVDataExportField field);

    void putCustomizeManageRecords(CustomizeManageRecords customizeManageRecords);

    CustomizeManageRecords getCustomizeManageRecords(Integer customizeManageRecordsId);

    CustomizeManageRecords getCustomizeManageRecords(Integer formId, Integer userId);

    void putCustomizeManageRecordsField(CustomizeManageRecordsField field);

    CustomizeManageRecordsField getCustomizeManageRecordsField(Integer fieldId);

    void removeCustomizeManageRecordsField(CustomizeManageRecordsField field);

    void removeAccessibleSettings(AccessibleSettings accessibleSettings);

    void putTaxRate(final TaxRateUS taxRate);

    void putItemSize(ItemSize itemSize);

    ItemSize getItemSize(final Integer itemSizeId);

    void putFontsAndColors(FontsAndColors fontsAndColors);

    void removeFontsAndColors(FontsAndColors fontsAndColors);

    void removeBackground(Background background);

    void removeBorder(Border border);

    FontsAndColors getFontsAndColors(Integer fontsAndColorsId);

    void putFontsAndColorsValue(FontsAndColorsValue fontsAndColorsValue);

    Map<Integer, String> getSitesIdWithTitles(Set<Integer> sitesId);

    Date getLastUpdatedDate(final int siteId);

    FormExportTask getFormExportTask(final Integer formExportTaskId);

    void putFormExportTask(final FormExportTask formExportTask);

    void removeFormExportTask(final FormExportTask formExportTask);

    List<FormExportTask> getFormExportTasksByFormId(final int formId);

    List<FormExportTask> getFormExportTasksBySiteId(final int siteId);

    List<FormExportTask> getAllFormExportTasks();

    Site getSiteByBrandedSubDomain(String brandedSubDomain, String brandedDomain);

    List<String> getImagesKeywordsBySite(Integer siteId);

    List<String> getImagesNamesBySite(final Integer siteId);

    List<Image> getImagesByKeywords(Integer siteId, List<String> keywords);

    void putHtml(Html html);

    List<Site> getPublishedBlueprints();

    List<Site> getActiveBlueprints(BlueprintCategory blueprintCategory);

    GoogleBaseDataExportMappedByFilledFormId getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(final Integer filledFormId);

    void putGoogleBaseDataExportMappedByFilledFormId(GoogleBaseDataExportMappedByFilledFormId dataExportMappedByFilledFormId);

    public List<PageSettings> getPageSettingsWithHtmlOrCss();
}
