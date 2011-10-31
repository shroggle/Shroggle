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

package com.shroggle;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.start.Alter;
import com.shroggle.logic.statistics.DateInterval;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceListener;

import java.util.*;

public class PersistanceMock implements Persistance {

    public Site getSiteBySubDomain(String subDomain) {
        if (subDomain == null)
            return null;

        for (Site site : sites) {
            if (site.getSubDomain() != null && site.getSubDomain().equals(subDomain)) {
                return site;
            }
        }
        return null;
    }

    public List<Site> searchSites(final String siteTitle, final String siteUrlPrefix) {
        final List<Site> findSites = new ArrayList<Site>();
        for (final Site site : sites) {
            if (site.getTitle().contains(siteTitle)
                    && site.getSubDomain().contains(siteUrlPrefix)) {
                findSites.add(site);
            }
        }
        return findSites;
    }

    public List<FilledForm> getFilledFormsByFormId(final int formId) {
        final List<FilledForm> foundFilledForms = new ArrayList<FilledForm>();
        for (final FilledForm filledForm : filledForms) {
            if (filledForm.getFormId() == formId) {
                foundFilledForms.add(filledForm);
            }
        }
        return foundFilledForms;
    }

    @Override
    public int getFilledFormsCountByFormId(int formId) {
        return getFilledFormsByFormId(formId).size();
    }

    @Override
    public Map<Integer, Integer> getFilledFormsCountByFormsId(Set<Integer> formsId) {
        final Map<Integer, Integer> formsIdWithFilledFormsCount = new HashMap<Integer, Integer>();
        for (Integer formId : formsId) {
            formsIdWithFilledFormsCount.put(formId, getFilledFormsCountByFormId(formId));
        }
        return formsIdWithFilledFormsCount;
    }

    public List<FilledForm> getFilledFormsByFormAndUserId(final int formId, final int userId) {
        final List<FilledForm> foundFilledForms = new ArrayList<FilledForm>();
        for (final FilledForm filledForm : filledForms) {
            if (filledForm.getUser() == null) {
                continue;
            }
            if (filledForm.getFormId() == formId && filledForm.getUser().getUserId() == userId) {
                foundFilledForms.add(filledForm);
            }
        }
        return foundFilledForms;
    }

    public long getFilledFormsNumberByFormId(final int formId) {
        int filledFormsNumber = 0;
        for (final FilledForm filledForm : filledForms) {
            if (filledForm.getFormId() == formId) {
                filledFormsNumber++;
            }
        }
        return filledFormsNumber;
    }

    public Integer getMaxFilledFormIdByFormId(final int formId) {
        int maxFilledFormId = -1;
        for (final FilledForm filledForm : filledForms) {
            if (filledForm.getFormId() == formId && filledForm.getFilledFormId() > maxFilledFormId) {
                maxFilledFormId = filledForm.getFilledFormId();
            }
        }
        return maxFilledFormId;
    }

    public void putSite(final Site site) {
        putAccessibleSettings(site.getAccessibleSettings());
        site.setSiteId(0);
        for (Site tempSite : sites) {
            if (site.getSiteId() < tempSite.getSiteId()) {
                site.setSiteId(tempSite.getSiteId());
            }
        }
        site.setSiteId(site.getSiteId() + 1);
        sites.add(site);

        fireUpdate(Site.class, site);
    }

    private void fireUpdate(final Class entityClass, final Object id) {
        for (final PersistanceListener listener : updateListeners) {
            listener.execute(entityClass, id);
        }
    }

    private void fireRemove(final Class entityClass, final Object id) {
        for (final PersistanceListener listener : removeListeners) {
            listener.execute(entityClass, id);
        }
    }

    public int getThreadVotesCount(int threadId, int answerNumber) {
        int count = 0;
        for (ForumPollVote vote : forumVotes) {
            if (vote.getAnswerNumber() == answerNumber && vote.getThread().getThreadId() == threadId) {
                count++;
            }
        }
        return count;
    }

    public BlogPost getLastBlogPost(int blogId) {
        BlogPost lastBlogPost = null;
        for (BlogPost blogPost : blogPosts) {
            if (blogPost.getBlog().getId() == blogId) {
                if (lastBlogPost == null || blogPost.getCreationDate().after(lastBlogPost.getCreationDate())) {
                    lastBlogPost = blogPost;
                }
            }
        }
        return lastBlogPost;
    }

    @Override
    public Map<Integer, Date> getLastBlogPostsDate(Set<Integer> blogsId) {
        final Map<Integer, Date> forumsIdWithSubforumsCount = new HashMap<Integer, Date>();
        for (Integer blogId : blogsId) {
            BlogPost blogPost = getLastBlogPost(blogId);
            forumsIdWithSubforumsCount.put(blogId, blogPost != null ? blogPost.getCreationDate() : null);
        }
        return forumsIdWithSubforumsCount;
    }

    public ForumPost getLastForumPost(int forumId) {
        ForumPost lastForumPost = null;
        for (ForumPost forumPost : forumPosts) {
            if (forumPost.getThread().getSubForum().getForum().getId() == forumId) {
                if (lastForumPost == null || forumPost.getDateCreated().after(lastForumPost.getDateCreated())) {
                    lastForumPost = forumPost;
                }
            }
        }
        return lastForumPost;
    }

    @Override
    public Map<Integer, Date> getLastForumPosts(Set<Integer> forumsId) {
        final Map<Integer, Date> forumsIdWithSubforumsCount = new HashMap<Integer, Date>();
        for (Integer forumId : forumsId) {
            forumsIdWithSubforumsCount.put(forumId, getLastForumPost(forumId).getDateCreated());
        }
        return forumsIdWithSubforumsCount;
    }

    @Override
    public List<BlogPost> getBlogPosts(
            final int blogId, final Integer visitorId,
            final Integer blogPostId, final int start, final int count, Date notOlderThan) {
        Collections.sort(blogPosts, new Comparator<BlogPost>() {

            @Override
            public int compare(final BlogPost o1, final BlogPost o2) {
                return o2.getCreationDate().compareTo(o1.getCreationDate());
            }

        });
        final List<BlogPost> findBlogPosts = new ArrayList<BlogPost>();
        if (blogPostId == null) {
            for (final BlogPost blogPost : blogPosts) {
                if (blogPost.getBlog().getId() == blogId && notOlderThan == null ||
                        (blogPost.getCreationDate() != null && (blogPost.getCreationDate().after(notOlderThan) || blogPost.getCreationDate().equals(notOlderThan)))) {
                    findBlogPosts.add(blogPost);
                }
            }
        } else {
            final int index = blogPosts.indexOf(getBlogPostById(blogPostId));
            final List<BlogPost> tempBlogPosts = blogPosts.subList(index, blogPosts.size());
            for (final BlogPost tempBlogPost : tempBlogPosts) {
                if (tempBlogPost.getBlog().getId() == blogId && notOlderThan == null ||
                        (tempBlogPost.getCreationDate() != null && (tempBlogPost.getCreationDate().after(notOlderThan) || tempBlogPost.getCreationDate().equals(notOlderThan)))) {
                    findBlogPosts.add(tempBlogPost);
                }
            }
        }
        if (count > 0) {
            return findBlogPosts.subList(0, Math.min(count, findBlogPosts.size()));
        } else {
            return findBlogPosts;
        }
    }

    @Override
    public int getBlogPostsBeforeByBlogAndUserId(int blogId, Integer visitorId, int blogPostId) {
        return 0;
    }

    @Override
    public int getPrevOrNextBlogPostId(int blogId, Integer visitorId, int blogPostId, boolean prev) {
        final List<BlogPost> blogPosts = getBlogPostsByBlog(blogId);
        Collections.sort(blogPosts, new Comparator<BlogPost>() {
            @Override
            public int compare(BlogPost o1, BlogPost o2) {
                return o1.getCreationDate().compareTo(o2.getCreationDate());
            }
        });
        for (int i = 0; i < blogPosts.size(); i++) {
            final BlogPost blogPost = blogPosts.get(i);
            if (blogPost.getBlogPostId() == blogPostId) {
                if (prev) {
                    if (i == 0) {
                        return 0;
                    }

                    return blogPosts.get(i - 1).getBlogPostId();
                } else {
                    if (i == blogPosts.size() - 1) {
                        return 0;
                    }

                    return blogPosts.get(i + 1).getBlogPostId();
                }
            }
        }

        return 0;
    }

    @Override
    public int getBlogPostsAfterByBlogAndUserId(
            final int blogId, final Integer visitorId, final int blogPostId) {
        final BlogPost blogPost = getBlogPostById(blogPostId);
        int count = 0;
        for (final BlogPost tempBlogPost : blogPosts) {
            if (tempBlogPost.getBlog() == blogPost.getBlog()) {
                if (tempBlogPost.getCreationDate().after(blogPost.getCreationDate())) {
                    count++;
                }
            }
        }
        return count;
    }

    public IncomeSettings getIncomeSettingsById(int incomeSettingsId) {
        for (IncomeSettings incomeSetting : incomeSettings) {
            if (incomeSetting.getIncomeSettingsId() == incomeSettingsId) {
                return incomeSetting;
            }
        }
        return null;
    }

    public SitePaymentSettings getSitePaymentSettingsById(int sitePaymentSettingsId) {
        for (SitePaymentSettings sitePaymentSetting : sitePaymentSettings) {
            if (sitePaymentSetting.getSitePaymentSettingsId() == sitePaymentSettingsId) {
                return sitePaymentSetting;
            }
        }
        return null;
    }

    public DraftFormItem getFormItemById(Integer formItemId) {
        if (formItemId == null) {
            return null;
        }
        for (DraftFormItem tempFormItem : formItems) {
            if (tempFormItem.getFormItemId() == formItemId) {
                return tempFormItem;
            }
        }
        return null;
    }

    public boolean isRegistrationUsedOnAnySiteAsDefault(Integer formId) {
        if (formId == null) {
            return false;
        }

        for (Site site : sites) {
            if (site.getDefaultFormId() == formId) {
                return true;
            }
        }

        return false;
    }

    public CreditCard getCreditCardById(Integer creditCardId) {
        if (creditCardId == null) {
            return null;
        }
        for (CreditCard creditCard : creditCards) {
            if (creditCard.getCreditCardId() == creditCardId) {
                return creditCard;
            }
        }
        return null;
    }


    public KeywordsGroup getKeywordsGroupById(int keywordsGroupId) {
        for (KeywordsGroup keywordsGroup : keywordsGroups) {
            if (keywordsGroup.getKeywordsGroupId() == keywordsGroupId) {
                return keywordsGroup;
            }
        }
        return null;
    }

    public DraftContactUs getContactUsById(final int contactUsId) {
        for (final DraftItem contactUs : draftItems) {
            if (contactUs.getId() == contactUsId) {
                return (DraftContactUs) contactUs;
            }
        }
        return null;
    }

    private List<SiteOnItem> getSiteOnItemRightsBySiteIdAndItemId(final int siteId, final int itemId) {
        List<SiteOnItem> rights = new ArrayList<SiteOnItem>();
        for (SiteOnItem right : siteOnItems) {
            if (right.getSite().getSiteId() == siteId && right.getItem().getId() == itemId) {
                rights.add(right);
            }
        }
        return rights;
    }

    public List<Integer> getSitesThatContainGalleriesWithEcommerce(List<Integer> siteIds) {
        final List<Integer> sites = new ArrayList<Integer>();
        for (DraftItem item : draftItems) {
            if (item.getItemType() == ItemType.GALLERY && siteIds.contains(item.getSiteId())
                    && ((DraftGallery) item).getPaypalSettings().isEnable()) {
                sites.add(item.getSiteId());
            }
        }
        return sites;
    }

    public List<UserOnSiteRight> getNotActiveUserOnSiteRightsByUserAndInvitedUser(
            final int userId, final int invitedUserId) {
        final List<Site> sites = getSites(userId, SiteAccessLevel.getUserAccessLevels());
        final List<UserOnSiteRight> userOnSiteRights = new ArrayList<UserOnSiteRight>();
        for (final Site site : sites) {
            for (final UserOnSiteRight userOnSiteRight : site.getUserOnSiteRights()) {
                if (!userOnSiteRight.isActive() && userOnSiteRight.getId().getUser().getUserId() == invitedUserId) {
                    userOnSiteRights.add(userOnSiteRight);
                }
            }
        }
        return userOnSiteRights;
    }

    public List<FilledForm> getUserPurchasesOnSite(int userId, int siteId) {
        throw new UnsupportedOperationException();
    }

    public List<Site> getAllSites() {
        return sites;
    }

    @Override
    public List<Site> getSitesWithNotEmptyIncomeSettings() {
        List<Site> sites = new ArrayList<Site>();
        for (Site site : this.sites) {
            if (site.getIncomeSettings() != null && site.getIncomeSettings().getSum() > 0) {
                sites.add(site);
            }
        }
        return sites;
    }

    public List<Site> getChildSites() {
        List<Site> sites = new ArrayList<Site>();
        for (Site site : this.sites) {
            if (site.getChildSiteSettings() != null) {
                sites.add(site);
            }
        }
        return sites;
    }

    public List<Site> getSitesConnectedToCreditCard(int creditCardId) {
        final List<Site> connectedSites = new ArrayList<Site>();
        for (Site site : sites) {
            if (site.getSitePaymentSettings().getCreditCard() != null &&
                    site.getSitePaymentSettings().getCreditCard().getCreditCardId() == creditCardId) {
                connectedSites.add(site);
            }
        }
        return connectedSites;
    }

    public void putSiteOnItem(final SiteOnItem siteOnItemRight) {
        siteOnItems.add(siteOnItemRight);
    }

    public SiteOnItem getSiteOnItemRightBySiteIdItemIdAndType(
            final int siteId, final int itemId, final ItemType type) {
        for (final SiteOnItem siteOnItemRight : siteOnItems) {
            if (siteOnItemRight.getItem() != null
                    && siteOnItemRight.getItem().getId() == itemId
                    && siteOnItemRight.getSite().getSiteId() == siteId) {
                return siteOnItemRight;
            }
        }
        return null;
    }

    public void removeSiteOnItemRight(final SiteOnItem siteOnItemRight) {
        siteOnItems.remove(siteOnItemRight);
    }


    public int getChildSiteSettingsCountByRegistrationId(final int childSiteRegistrationId) {
        int count = 0;
        for (final ChildSiteSettings childSiteSetting : childSiteSettings) {
            if (childSiteSetting.getChildSiteRegistration().getFormId() == childSiteRegistrationId) {
                count++;
            }
        }
        return count;
    }

    @Override
    public DraftGallery getGalleryByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem gallery : draftItems) {
            if (gallery.getSiteId() > 0 && gallery.getSiteId() == siteId && gallery.getItemType() == ItemType.GALLERY) {
                if (gallery.getName().equals(name)) {
                    return (DraftGallery) gallery;
                }
            }
        }
        return null;
    }

    @Override
    public DraftGallery getGalleryById(Integer galleryId) {
        if (galleryId == null) {
            return null;
        }
        for (final DraftItem gallery : draftItems) {
            if (gallery.getId() == galleryId && gallery.getItemType() == ItemType.GALLERY) {
                return (DraftGallery) gallery;
            }
        }
        return null;
    }


    @Override
    public void putGalleryLabel(final DraftGalleryLabel label) {
    }

    @Override
    public void putGalleryItem(final DraftGalleryItem item) {
    }

    @Override
    public DraftGalleryItem getGalleryItemById(final int galleryId, final int formItemId) {
        return null;
    }

    @Override
    public void removeGalleryLabel(final DraftGalleryLabel label) {
    }

    @Override
    public void removeGalleryItem(final DraftGalleryItem item) {
    }

    public DraftChildSiteRegistration getChildSiteRegistrationById(final Integer childSiteRegistrationId) {
        if (childSiteRegistrationId == null) {
            return null;
        }
        for (DraftItem childSiteRegistration : draftItems) {
            if (childSiteRegistration.getId() == childSiteRegistrationId) {
                return (DraftChildSiteRegistration) childSiteRegistration;
            }
        }
        return null;
    }

    public ChildSiteSettings getChildSiteSettingsById(Integer childSiteSettingsId) {
        if (childSiteSettingsId == null) {
            return null;
        }
        for (ChildSiteSettings childSiteSetting : childSiteSettings) {
            if (childSiteSetting.getChildSiteSettingsId() == childSiteSettingsId) {
                return childSiteSetting;
            }
        }
        return null;
    }


    public void putChildSiteSettings(ChildSiteSettings childSiteSetting) {
        childSiteSetting.setChildSiteSettingsId(0);
        for (ChildSiteSettings tempChildSiteSettings : childSiteSettings) {
            if (childSiteSetting.getChildSiteSettingsId() < tempChildSiteSettings.getChildSiteSettingsId()) {
                childSiteSetting.setChildSiteSettingsId(tempChildSiteSettings.getChildSiteSettingsId());
            }
        }
        childSiteSetting.setChildSiteSettingsId(childSiteSetting.getChildSiteSettingsId() + 1);
        childSiteSettings.add(childSiteSetting);
    }

    public void removeChildSiteSettings(final ChildSiteSettings childSiteSettings) {
        if (childSiteSettings == null) {
            throw new RuntimeException("Can`t delete null entity");
        }
        this.childSiteSettings.remove(childSiteSettings);
    }

    public List<ChildSiteSettings> getChildSiteSettingsByUserId(final int userId) {
        throw new UnsupportedOperationException();
    }

    public void putContactUs(DraftContactUs contactUs) {
        contactUs.setId(0);
        for (DraftItem tempContactUs : draftItems) {
            if (contactUs.getFormId() < tempContactUs.getId()) {
                contactUs.setId(tempContactUs.getId());
            }
        }
        contactUs.setId(contactUs.getFormId() + 1);
        draftItems.add(contactUs);
    }

    public DraftContactUs getContactUsByNameAndSiteId(String name, int siteId) {
        List<DraftForm> accountContactUsList = new ArrayList<DraftForm>();
        for (DraftItem contactUs : draftItems) {
            if (contactUs.getSiteId() == siteId && contactUs.getItemType() == ItemType.CONTACT_US) {
                accountContactUsList.add((DraftForm) contactUs);
            }
        }
        for (DraftForm contactUs : accountContactUsList) {
            if (contactUs.getName().equals(name)) {
                return ((DraftContactUs) contactUs);
            }
        }
        return null;
    }

    public void putBlogPost(final BlogPost blogPost) {
        blogPost.setBlogPostId(0);
        for (BlogPost tempBlogPost : blogPosts) {
            if (blogPost.getBlogPostId() < tempBlogPost.getBlogPostId()) {
                blogPost.setBlogPostId(tempBlogPost.getBlogPostId());
            }
        }
        blogPost.setBlogPostId(blogPost.getBlogPostId() + 1);
        blogPosts.add(blogPost);
    }

    public BlogPost getBlogPostById(int blogPostId) {
        for (BlogPost blogPost : blogPosts) {
            if (blogPost.getBlogPostId() == blogPostId) {
                return blogPost;
            }
        }
        return null;
    }

    public Comment getCommentById(final int commentId) {
        for (final Comment comment : comments) {
            if (comment.getCommentId() == commentId) {
                return comment;
            }
        }
        return null;
    }

    public void putComment(final Comment comment) {
        comment.setCommentId(0);
        for (Comment tempComment : comments) {
            if (comment.getCommentId() < tempComment.getCommentId()) {
                comment.setCommentId(tempComment.getCommentId());
            }
        }
        comment.setCommentId(comment.getCommentId() + 1);
        comments.add(comment);
    }

    public List<SubForum> getSubForumsByForumId(int forumId) {
        return subForums;
    }

    @Override
    public int getSubForumsCountByForumId(int forumId) {
        return getSubForumsByForumId(forumId).size();
    }

    @Override
    public Map<Integer, Integer> getSubForumsCountByForumsId(Set<Integer> forumsId) {
        final Map<Integer, Integer> forumsIdWithSubforumsCount = new HashMap<Integer, Integer>();
        for (Integer forumId : forumsId) {
            forumsIdWithSubforumsCount.put(forumId, getSubForumsCountByForumId(forumId));
        }
        return forumsIdWithSubforumsCount;
    }

    public List<ForumThread> getForumThreads(int subForumId) {
        return threads;
    }

    public List<ForumThread> getForumThreadsByUserId(int visitorId) {
        final List<ForumThread> returnList = new ArrayList<ForumThread>();

        for (ForumThread forumThread : threads) {
            if (forumThread.getAuthor().getUserId() == visitorId) {
                returnList.add(forumThread);
            }
        }

        return returnList;
    }

    public List<ForumPost> getForumPostsByUserId(int visitorId) {
        final List<ForumPost> returnList = new ArrayList<ForumPost>();

        for (ForumPost forumPost : forumPosts) {
            if (forumPost.getAuthor().getUserId() == visitorId) {
                returnList.add(forumPost);
            }
        }

        return returnList;
    }

    public List<ForumPost> getForumPosts(int forumThreadId) {
        return forumPosts;
    }

    public void putVote(ForumPollVote vote) {
        vote.setVoteId(0);
        for (ForumPollVote tempVote : forumVotes) {
            if (vote.getVoteId() < tempVote.getVoteId()) {
                vote.setVoteId(tempVote.getVoteId());
            }
        }
        vote.setVoteId(vote.getVoteId() + 1);
        forumVotes.add(vote);

    }

    public ForumPollVote getForumThreadVoteByRespondentIdAndThreadId(String respondentId, int threadId) {
        for (ForumPollVote vote : forumVotes) {
            if (vote.getRespondentId().equals(respondentId) && vote.getThread().getThreadId() == threadId) {
                return vote;
            }
        }
        return null;
    }

    public void putWidget(Widget widget) {
        widget.setWidgetId(1000);
        for (Widget tempWidget : widgets) {
            if (widget.getWidgetId() < tempWidget.getWidgetId()) {
                widget.setWidgetId(tempWidget.getWidgetId());
            }
        }
        widget.setWidgetId(widget.getWidgetId() + 1);
        if (widget.getCrossWidgetId() < 1) {
            widget.setCrossWidgetId(widget.getWidgetId());
        }
        widgets.add(widget);
    }

    public Site getSite(final Integer siteId) {
        if (siteId == null) {
            return null;
        }
        for (Site site : sites) {
            if (site.getSiteId() == siteId) {
                return site;
            }
        }
        return null;
    }

    public SlideShowImage getSlideShowImageById(final Integer slideShowImageId) {
        if (slideShowImageId == null) {
            return null;
        }
        for (SlideShowImage slideShowImage : slideShowImages) {
            if (slideShowImage.getSlideShowImageId() == slideShowImageId) {
                return slideShowImage;
            }
        }
        return null;
    }

    @Override
    public String getSiteTitleBySiteId(int siteId) {
        if (siteId > 0) {
            final Site site = getSite(siteId);
            if (site != null) {
                return site.getTitle();
            }
        }
        return "none";
    }

    public DraftRegistrationForm getRegistrationFormById(final int formId) {
        for (final DraftItem registrationForm : draftItems) {
            if (registrationForm.getId() == formId) {
                return ((DraftRegistrationForm) registrationForm);
            }
        }
        return null;
    }

    public DraftCustomForm getCustomFormById(final int formId) {
        for (final DraftItem registrationForm : draftItems) {
            if (registrationForm.getId() == formId) {
                return ((DraftCustomForm) registrationForm);
            }
        }
        return null;
    }

    public DraftForum getForumByNameAndSiteId(String name, int siteId) {
        for (DraftItem forum : draftItems) {
            if (forum.getSiteId() > 0
                    && forum.getSiteId() == siteId
                    && forum.getName().equals(name)
                    && forum.getItemType().equals(ItemType.FORUM)) {
                return (DraftForum) forum;
            }
        }
        return null;
    }

    public DraftRegistrationForm getRegistrationFormByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem registrationForm : draftItems) {
            if (registrationForm.getSiteId() > 0
                    && registrationForm.getSiteId() == siteId
                    && registrationForm.getName().equals(name)
                    && registrationForm.getItemType().equals(ItemType.REGISTRATION)) {
                return ((DraftRegistrationForm) registrationForm);
            }
        }
        return null;
    }

    public DraftSlideShow getSlideShowByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem slideShow : draftItems) {
            if (slideShow.getSiteId() > 0
                    && slideShow.getSiteId() == siteId
                    && slideShow.getName().equals(name)
                    && slideShow.getItemType().equals(ItemType.SLIDE_SHOW)) {
                return ((DraftSlideShow) slideShow);
            }
        }
        return null;
    }

    public DraftMenu getMenuByNameAndSiteId(String name, int siteId) {
        for (final DraftItem menu : draftItems) {
            if (menu.getSiteId() > 0 && menu.getSiteId() == siteId
                    && menu.getName().equals(name) && menu.getItemType().equals(ItemType.MENU)) {
                return ((DraftMenu) menu);
            }
        }
        return null;
    }

    public DraftShoppingCart getShoppingCartByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem shoppingCart : draftItems) {
            if (shoppingCart.getSiteId() > 0
                    && shoppingCart.getSiteId() == siteId
                    && shoppingCart.getName().equals(name)
                    && shoppingCart.getItemType().equals(ItemType.SHOPPING_CART)) {
                return ((DraftShoppingCart) shoppingCart);
            }
        }
        return null;
    }

    public DraftPurchaseHistory getPurchaseHistoryByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem purchaseHistory : draftItems) {
            if (purchaseHistory.getSiteId() > 0
                    && purchaseHistory.getSiteId() == siteId
                    && purchaseHistory.getName().equals(name)
                    && purchaseHistory.getItemType().equals(ItemType.PURCHASE_HISTORY)) {
                return ((DraftPurchaseHistory) purchaseHistory);
            }
        }
        return null;
    }

    public DraftAdvancedSearch getDraftAdvancedSearch(final String name, final int siteId) {
        for (final DraftItem draftItem : draftItems) {
            if (draftItem instanceof DraftAdvancedSearch && draftItem.getSiteId() > 0
                    && draftItem.getSiteId() == siteId && draftItem.getName().equals(name)) {
                return ((DraftAdvancedSearch) draftItem);
            }
        }
        return null;
    }

    public DraftChildSiteRegistration getChildSiteRegistrationFormByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem childSiteRegistration : draftItems) {
            if (childSiteRegistration.getSiteId() > 0
                    && childSiteRegistration.getSiteId() == siteId
                    && childSiteRegistration.getName().equals(name)
                    && childSiteRegistration.getItemType().equals(ItemType.CHILD_SITE_REGISTRATION)) {
                return ((DraftChildSiteRegistration) childSiteRegistration);
            }
        }
        return null;
    }

    public DraftCustomForm getCustomFormByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem customForm : draftItems) {
            if (customForm.getSiteId() == siteId && customForm.getName().equals(name) && customForm.getItemType().equals(ItemType.CUSTOM_FORM)) {
                return ((DraftCustomForm) customForm);
            }
        }
        return null;
    }

    public DraftBlogSummary getBlogSummaryByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem customForm : draftItems) {
            if (customForm.getSiteId() == siteId && customForm.getName().equals(name) && customForm.getItemType().equals(ItemType.BLOG_SUMMARY)) {
                return ((DraftBlogSummary) customForm);
            }
        }
        return null;
    }

    public DraftAdminLogin getAdminLoginByNameAndSiteId(String name, int siteId) {
        for (final DraftItem customForm : draftItems) {
            if (customForm.getItemType() == ItemType.ADMIN_LOGIN && customForm.getSiteId() == siteId && customForm.getName().equals(name)) {
                return ((DraftAdminLogin) customForm);
            }
        }
        return null;
    }

    @Override
    public DraftTaxRatesUS getTaxRatesByNameAndSiteId(String taxRateName, int siteId) {
        for (final DraftItem customForm : draftItems) {
            if (customForm.getSiteId() == siteId && customForm.getName().equals(taxRateName) && customForm.getItemType().equals(ItemType.TAX_RATES)) {
                return ((DraftTaxRatesUS) customForm);
            }
        }
        return null;
    }

    public DraftMenu getMenuById(Integer menuId) {
        if (menuId == null) {
            return null;
        }
        for (DraftItem menu : draftItems) {
            if (menu.getId() == menuId) {
                return (DraftMenu) menu;
            }
        }
        return null;
    }


    public List<DraftMenu> getMenusBySiteId(int siteId) {
        final List<DraftMenu> availableMenus = new ArrayList<DraftMenu>();
        for (final DraftItem menu : draftItems) {
            if (menu.getSiteId() == siteId && menu.getItemType() == ItemType.MENU) {
                availableMenus.add((DraftMenu) menu);
            }
        }
        return availableMenus;
    }

    public List<DraftMenu> getMenusWithDefaultStructureBySiteId(int siteId) {
        final List<DraftMenu> availableMenus = new ArrayList<DraftMenu>();
        for (final DraftItem menu : draftItems) {
            if (menu.getSiteId() == siteId && menu.getItemType() == ItemType.MENU && ((DraftMenu) menu).getMenuStructure()
                    == MenuStructureType.DEFAULT) {
                availableMenus.add((DraftMenu) menu);
            }
        }
        return availableMenus;
    }


    public DraftBlog getBlogByNameAndSiteId(String name, int siteId) {
        List<DraftBlog> blogList = new ArrayList<DraftBlog>();
        for (DraftItem blog : draftItems) {
            if (blog.getSiteId() == siteId && blog.getItemType().equals(ItemType.BLOG)) {
                blogList.add((DraftBlog) blog);
            }
        }
        for (DraftBlog blog : blogList) {
            if (blog.getName().equals(name)) {
                return blog;
            }
        }
        return null;
    }

    public SubForum getSubForumById(int subForumId) {
        for (SubForum subForum : subForums) {
            if (subForum.getSubForumId() == subForumId) {
                return subForum;
            }
        }
        return null;
    }

    public ForumThread getForumThreadById(int threadId) {
        for (ForumThread thread : threads) {
            if (thread.getThreadId() == threadId) {
                return thread;
            }
        }
        return null;
    }

    @Override
    public Page getPageByOwnDomainName(final String ownDomainName) {
        if (ownDomainName == null) {
            return null;
        }
        for (final Page page : pages) {
            if (ownDomainName.equals(page.getPageSettings().getOwnDomainName())) {
                return page;
            }
            final WorkPageSettings workPageSettings = new PageManager(page).getWorkPageSettings();
            if (workPageSettings != null && ownDomainName.equals(workPageSettings.getOwnDomainName())) {
                return page;
            }
        }
        return null;
    }

    @Override
    public Page getPageByNameAndSite(final String name, final int siteId) {
        if (name == null) {
            return null;
        }
        for (final Page page : pages) {
            if (page.getSite().getSiteId() == siteId) {
                if (name.equals(page.getPageSettings().getName())) {
                    return page;
                }
                final WorkPageSettings workPageSettings = new PageManager(page).getWorkPageSettings();
                if (workPageSettings != null && name.equals(workPageSettings.getName())) {
                    return page;
                }
            }
        }
        return null;
    }

    @Override
    public Page getPageByUrlAndAndSiteIgnoreUrlCase(String url, final int siteId) {
        if (url == null) {
            return null;
        }
        url = url.startsWith("/") ? url : ("/" + url);
        for (final Page page : pages) {
            if (page.getSite().getSiteId() == siteId) {
                if (url.equalsIgnoreCase(new PageManager(page, SiteShowOption.getDraftOption()).getUrl())) {
                    return page;
                }
                if (url.equalsIgnoreCase(new PageManager(page, SiteShowOption.getWorkOption()).getUrl())) {
                    return page;
                }
            }
        }
        return null;
    }

    public Widget getWidget(Integer widgetId) {
        if (widgetId == null) {
            return null;
        }
        for (Widget widget : widgets) {
            if (widget.getWidgetId() == widgetId) {
                return widget;
            }
        }
        return null;
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

    private List<Widget> getAllWidgetsByCrossWidgetId(final int crossWidgetId) {
        final List<Widget> widgets = new ArrayList<Widget>();
        for (Widget widget : this.widgets) {
            if (widget.getCrossWidgetId() == crossWidgetId) {
                widgets.add(widget);
            }
        }
        return widgets;
    }

    public Widget getFirstWidgetByItemId(int itemId) {
        return null;
    }

    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    public void removePost(ForumPost forumPost) {
        forumPosts.remove(forumPost);
    }

    public void removeFormFilter(DraftFormFilter formFilter) {
        formFilters.remove(formFilter);
    }

    public void removeFormFilterRule(final DraftFormFilterRule formFilterRule) {
        formFilterRule.getFormFilter().removeRule(formFilterRule);
        formFilterRules.remove(formFilterRule);
    }

    public void removeThread(ForumThread forumThread) {
        threads.remove(forumThread);
    }

    public void removeFormItem(DraftFormItem formItem) {
    }

    public void removeFilledForm(FilledForm filledForm) {
        FilledForm filledFormToDelete = null;
        for (FilledForm tempFilledForm : filledForms) {
            if (tempFilledForm.getFilledFormId() == filledForm.getFilledFormId()) {
                filledFormToDelete = tempFilledForm;
            }
        }

        if (filledFormToDelete != null) {
            filledForms.remove(filledFormToDelete);
        }
    }

    public void removeSubForum(SubForum subForum) {
        subForums.remove(subForum);
    }

    public void removeSlideShowImage(SlideShowImage slideShowImage) {
        slideShowImage.getSlideShow().getImages().remove(slideShowImage);
        slideShowImages.remove(slideShowImage);
    }

    public Image getImageById(Integer imageId) {
        if (imageId == null) {
            return null;
        }
        for (Image image : images) {
            if (image.getImageId() == imageId) {
                return image;
            }
        }
        return null;
    }

    public List<DraftGallery> getGalleriesByFormId(Integer formId) {
        final List<DraftGallery> galleries = new ArrayList<DraftGallery>();
        if (formId != null) {
            for (DraftItem tempGallery : this.draftItems) {
                if (tempGallery.getItemType() == ItemType.GALLERY && ((DraftGallery) tempGallery).getFormId1() == formId) {
                    galleries.add((DraftGallery) tempGallery);
                }
            }
        }
        return galleries;
    }

    public BackgroundImage getBackgroundImageById(int backgroundImageId) {
        for (BackgroundImage backgroundImage : backgroundImages) {
            if (backgroundImage.getBackgroundImageId() == backgroundImageId) {
                return backgroundImage;
            }
        }
        return null;
    }

    public MenuImage getMenuImageById(Integer menuImageId) {
        if (menuImageId == null) {
            return null;
        }
        for (MenuImage menuImage : menuImages) {
            if (menuImage.getMenuImageId() == menuImageId) {
                return menuImage;
            }
        }
        return null;
    }

    public ImageForVideo getImageForVideoById(int imageId) {
        for (ImageForVideo imageForVideo : imagesForVideo) {
            if (imageForVideo.getImageForVideoId() == imageId) {
                return imageForVideo;
            }
        }
        return null;
    }

    public FormFile getFormFileById(Integer formFileId) {
        if (formFileId == null) {
            return null;
        }
        for (FormFile formFile : formFiles) {
            if (formFile.getFormFileId() == formFileId) {
                return formFile;
            }
        }
        return null;
    }

    public List<FormFile> getFormFiles() {
        return formFiles;
    }

    public ImageFile getImageFileById(int imageFileId) {
        for (ImageFile imageFile : imageFiles) {
            if (imageFile.getImageFileId() == imageFileId) {
                return imageFile;
            }
        }
        return null;
    }

    public List<ImageFile> getImageFilesBySiteId(final int siteId) {
        final List<ImageFile> imageFiles = new ArrayList<ImageFile>();
        for (ImageFile imageFile : this.imageFiles) {
            if (imageFile.getSiteId() == siteId) {
                imageFiles.add(imageFile);
            }
        }
        return imageFiles;
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


    public void putFormFile(FormFile formFile) {
        formFile.setFormFileId(0);
        for (FormFile tempFile : formFiles) {
            if (formFile.getFormFileId() < tempFile.getFormFileId()) {
                formFile.setFormFileId(tempFile.getFormFileId());
            }
        }
        formFile.setFormFileId(formFile.getFormFileId() + 1);
        formFiles.add(formFile);
    }

    public void putImageFile(ImageFile imageFile) {
        imageFile.setImageFileId(0);
        for (ImageFile tempFile : imageFiles) {
            if (imageFile.getImageFileId() < tempFile.getImageFileId()) {
                imageFile.setImageFileId(tempFile.getImageFileId());
            }
        }
        imageFile.setImageFileId(imageFile.getImageFileId() + 1);
        imageFiles.add(imageFile);
    }

    public void removeImageFile(ImageFile imageFile) {
        imageFiles.remove(imageFile);
    }

    public void removeFormFile(FormFile formFile) {
        formFiles.remove(formFile);
    }

    public void removeFilledFormItem(FilledFormItem filledFormItem) {
        filledFormItems.remove(filledFormItem);
    }

    public void putImage(Image image) {
        image.setImageId(0);
        for (Image tempImage : images) {
            if (image.getImageId() < tempImage.getImageId()) {
                image.setImageId(tempImage.getImageId());
            }
        }
        image.setImageId(image.getImageId() + 1);
        images.add(image);
    }

    public void putBackgroundImage(BackgroundImage backgroundImage) {
        backgroundImage.setBackgroundImageId(0);
        for (BackgroundImage tempImage : backgroundImages) {
            if (backgroundImage.getBackgroundImageId() < tempImage.getBackgroundImageId()) {
                backgroundImage.setBackgroundImageId(tempImage.getBackgroundImageId());
            }
        }
        backgroundImage.setBackgroundImageId(backgroundImage.getBackgroundImageId() + 1);
        backgroundImages.add(backgroundImage);
    }

    public void putMenuImage(MenuImage image) {
        image.setMenuImageId(0);
        for (MenuImage tempImage : menuImages) {
            if (image.getMenuImageId() < tempImage.getMenuImageId()) {
                image.setMenuImageId(tempImage.getMenuImageId());
            }
        }
        image.setMenuImageId(image.getMenuImageId() + 1);
        menuImages.add(image);
    }

    public void putImageForVideo(ImageForVideo imageForVideo) {
        imageForVideo.setImageForVideoId(0);
        for (ImageForVideo tempImage : imagesForVideo) {
            if (imageForVideo.getImageForVideoId() < tempImage.getImageForVideoId()) {
                imageForVideo.setImageForVideoId(tempImage.getImageForVideoId());
            }
        }
        imageForVideo.setImageForVideoId(imageForVideo.getImageForVideoId() + 1);
        imagesForVideo.add(imageForVideo);
    }

    public void putStyle(Style style) {
        styles.add(style);
    }

    public Style getStyleById(int styleId) {
        for (final Style style : styles) {
            if (style.getStyleId() == styleId) {
                return style;
            }
        }
        return null;
    }

    public void removeStyle(Style style) {
        styles.remove(style);
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
        border.setId(0);
        for (Border tempBorder : borders) {
            if (border.getId() < tempBorder.getId()) {
                border.setId(tempBorder.getId());
            }
        }
        border.setId(border.getId() + 1);
        borders.add(border);
    }

    @Override
    public void putBackground(Background background) {
        background.setId(0);
        for (Background tempBackground : backgrounds) {
            if (background.getId() < tempBackground.getId()) {
                background.setId(tempBackground.getId());
            }
        }
        background.setId(background.getId() + 1);
        backgrounds.add(background);
    }

    public Border getBorder(Integer borderId) {
        if (borderId == null) {
            return null;
        }
        for (final Border border : borders) {
            if (border.getId() == borderId) {
                return border;
            }
        }
        return null;
    }

    @Override
    public Background getBackground(Integer backgroundId) {
        if (backgroundId == null) {
            return null;
        }
        for (final Background background : backgrounds) {
            if (background.getId() == backgroundId) {
                return background;
            }
        }
        return null;
    }

    public void removeBorderBackground(Border borderBackground) {
        draftItems.remove(borderBackground);
    }

    public List<Image> getImagesByUser(int accountId) {
        List<Image> imagesByUser = new ArrayList<Image>();
        for (Image image : images) {
            if (image.getSiteId() == accountId) {
                imagesByUser.add(image);
            }
        }
        return imagesByUser;
    }

    public List<Image> getImagesByOwnerSiteId(int siteId) {
        final List<Image> availableImages = new ArrayList<Image>();
        for (final Image image : images) {
            if (image.getSiteId() == siteId) {
                availableImages.add(image);
            }
        }
        return availableImages;
    }

    public Image getImageByNameAndSiteId(String name, int siteId) {
        for (final Image image : images) {
            if (image.getSiteId() == siteId && image.getName().equals(name)) {
                return image;
            }
        }

        return null;
    }

    // I add this method according to:
    //2. We can allow site admins to see and operate with any visual items from available accounts.
    private boolean isAvailableBySiteForUser(final int siteId, final int userId) {
        final User user = getUserById(userId);
        for (final UserOnSiteRight userOnSiteRight : user.getUserOnSiteRights()) {
            if (userOnSiteRight.isActive() && userOnSiteRight.getId().getSite().getSiteId() == siteId) {
                return true;
            }
        }
        return false;
    }

    public List<BackgroundImage> getBackgroundImagesBySiteId(Integer siteId) {
        final List<BackgroundImage> availableBackgroundImages = new ArrayList<BackgroundImage>();
        if (siteId != null) {
            for (final BackgroundImage backgroundImage : backgroundImages) {
                if (backgroundImage.getSiteId() == siteId) {
                    availableBackgroundImages.add(backgroundImage);
                }
            }
        }
        return availableBackgroundImages;
    }

    public List<ImageForVideo> getImagesForVideoBySiteId(final Integer siteId) {
        final List<ImageForVideo> availableImagesForVideo = new ArrayList<ImageForVideo>();
        if (siteId != null) {
            for (final ImageForVideo imageForVideo : imagesForVideo) {
                if (imageForVideo.getSiteId() == siteId) {
                    availableImagesForVideo.add(imageForVideo);
                }
            }
        }
        return availableImagesForVideo;
    }

    private boolean isAvailableForUser(final DraftItem siteItem, final int userId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        for (final SiteOnItem siteOnItemRight : persistance.getSiteOnItemsByItem(siteItem.getId())) {
            if (siteOnItemRight.getAcceptDate() == null) continue;
            for (final UserOnSiteRight userOnSiteRight : siteOnItemRight.getSite().getUserOnSiteRights()) {
                if (!userOnSiteRight.isActive() || userId != userOnSiteRight.getId().getUser().getUserId()) continue;
                return true;
            }
        }
        return false;
    }


    private Set<Integer> getAvailableSiteIdsForUser(final int userId) {
        final Set<Integer> availableSiteIds = new HashSet<Integer>();
        final User user = getUserById(userId);
        if (user != null) {
            for (final UserOnSiteRight userOnSiteRight : user.getUserOnSiteRights()) {
                if (userOnSiteRight.isActive()) {
                    availableSiteIds.add(userOnSiteRight.getId().getSite().getSiteId());
                }
            }
        }
        return availableSiteIds;
    }


    public List<DraftItem> getDraftItemsByUserId(final int userId, ItemType itemType) {
        List<DraftItem> availableItems = new ArrayList<DraftItem>();
        final Set<Integer> availableSiteIds = getAvailableSiteIdsForUser(userId);
        for (DraftItem item : draftItems) {
            if (item.getItemType() == itemType ||
                    itemType == ItemType.ALL_ITEMS ||
                    (itemType == ItemType.ALL_FORMS && ItemType.getFormItems().contains(item.getItemType())) ||
                    (itemType == ItemType.ORDER_FORM && item.getItemType() == ItemType.CUSTOM_FORM && ((DraftForm) item).getType() == FormType.ORDER_FORM)) {
                if (availableSiteIds.contains(item.getSiteId()) || hasRights(item, availableSiteIds)) {
                    availableItems.add(item);
                }
            }
        }
        return availableItems;
    }

    @Override
    public List<DraftItem> getDraftItemsBySiteId(int siteId, ItemType itemType, ItemType... excludedItemTypes) {
        List<DraftItem> availableItems = new ArrayList<DraftItem>();
        final Set<Integer> availableSiteIds = new HashSet<Integer>(Arrays.asList(siteId));
        final List<ItemType> excludedItems = excludedItemTypes != null ? Arrays.asList(excludedItemTypes) : Collections.<ItemType>emptyList();
        for (DraftItem item : draftItems) {
            if (excludedItems.contains(item.getItemType())) {
                continue;
            }
            if (item.getItemType() == itemType ||
                    itemType == ItemType.ALL_ITEMS ||
                    (itemType == ItemType.ALL_FORMS && ItemType.getFormItems().contains(item.getItemType())) ||
                    (itemType == ItemType.ORDER_FORM && item.getItemType() == ItemType.CUSTOM_FORM && ((DraftForm) item).getType() == FormType.ORDER_FORM)) {
                if (availableSiteIds.contains(item.getSiteId()) || hasRights(item, availableSiteIds)) {
                    availableItems.add(item);
                }
            }
        }
        return availableItems;
    }

    private boolean hasRights(final DraftItem draftItem, final Set<Integer> availableSiteIds) {
        for (SiteOnItem siteOnItem : siteOnItems) {
            if (availableSiteIds.contains(siteOnItem.getSite().getSiteId()) && siteOnItem.getItem().getId() == draftItem.getId()) {
                return true;
            }
        }
        return false;
    }


    public List<Video> getVideosBySiteId(final Integer siteId) {
        final List<Video> availableVideos = new ArrayList<Video>();
        if (siteId != null) {
            for (final Video video : videos) {
                if (video.getSiteId() == siteId) {
                    availableVideos.add(video);
                }
            }
        }
        return availableVideos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Site> getSites(final int userId, SiteAccessLevel[] accessLevels, SiteType... siteTypes) {
        if (siteTypes.length == 0) {
            siteTypes = SiteType.values();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        List<SiteType> siteTypesList = Arrays.asList(siteTypes);
        List<SiteAccessLevel> accessLevelsList = Arrays.asList(accessLevels);
        final List<Site> userSites = new ArrayList<Site>();
        UserRightManager rights = new UserRightManager(getUserById(userId));
        for (final Site site : sites) {
            if (siteTypesList.contains(site.getType()) && rights.toSite(site) != null
                    && accessLevelsList.contains(rights.toSite(site).getSiteAccessType())) {
                userSites.add(site);
            }
        }

        Collections.sort(userSites, new Comparator<Site>() {

            @Override
            public int compare(Site o1, Site o2) {
                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
            }

        });
        return userSites;
    }

    public List<User> getUsersWithActiveRights(Integer siteId, SiteAccessLevel[] accessLevels) {
        if (siteId == null) {
            return Collections.emptyList();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        List<SiteAccessLevel> accessLevelsList = Arrays.asList(accessLevels);
        final List<User> users = new ArrayList<User>();
        for (final UserOnSiteRight right : getSite(siteId).getUserOnSiteRights()) {
            if (right.isActive() && accessLevelsList.contains(right.getSiteAccessType())) {
                users.add(right.getId().getUser());
            }
        }
        return users;
    }

    @Override
    public List<User> getUsersWithRightsToSite(Integer siteId, SiteAccessLevel[] accessLevels) {
        if (siteId == null) {
            return Collections.emptyList();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        List<SiteAccessLevel> accessLevelsList = Arrays.asList(accessLevels);
        final List<User> users = new ArrayList<User>();
        for (final UserOnSiteRight right : getSite(siteId).getUserOnSiteRights()) {
            if (accessLevelsList.contains(right.getSiteAccessType())) {
                users.add(right.getId().getUser());
            }
        }
        return users;
    }

    public List<UserOnSiteRight> getUserOnSiteRights(Integer siteId, SiteAccessLevel[] accessLevels) {
        if (siteId == null) {
            return Collections.emptyList();
        }
        if (accessLevels == null || accessLevels.length == 0) {
            accessLevels = SiteAccessLevel.values();
        }
        List<SiteAccessLevel> accessLevelsList = Arrays.asList(accessLevels);
        final List<UserOnSiteRight> userOnSiteRights = new ArrayList<UserOnSiteRight>();
        for (final UserOnSiteRight right : getSite(siteId).getUserOnSiteRights()) {
            if (accessLevelsList.contains(right.getSiteAccessType())) {
                userOnSiteRights.add(right);
            }
        }
        return userOnSiteRights;
    }

    public void putVideo(Video video) {
        video.setVideoId(0);
        for (Video tempVideo : videos) {
            if (video.getVideoId() < tempVideo.getVideoId()) {
                video.setVideoId(tempVideo.getVideoId());
            }
        }
        video.setVideoId(video.getVideoId() + 1);
        videos.add(video);
    }

    public void putFlvVideo(final FlvVideo flvVideo) {
        flvVideo.setFlvVideoId(0);
        for (FlvVideo tempFlvVideo : flvVideos) {
            if (flvVideo.getFlvVideoId() < tempFlvVideo.getFlvVideoId()) {
                flvVideo.setFlvVideoId(tempFlvVideo.getFlvVideoId());
            }
        }
        flvVideo.setFlvVideoId(flvVideo.getFlvVideoId() + 1);
        flvVideos.add(flvVideo);
    }

    public Video getVideoById(Integer videoId) {
        if (videoId == null) {
            return null;
        }
        for (Video video : videos) {
            if (video.getVideoId() == videoId) {
                return video;
            }
        }
        return null;
    }


    public FlvVideo getFlvVideo(final Integer flvVideoId) {
        if (flvVideoId == null) {
            return null;
        }
        for (FlvVideo flvVideo : flvVideos) {
            if (flvVideo.getFlvVideoId() == flvVideoId) {
                return flvVideo;
            }
        }
        return null;
    }

    public FlvVideo getFlvVideo(Integer sourceVideoId, Integer width, Integer height, Integer quality) {
        if (sourceVideoId == null || quality == null) {
            return null;
        }
        for (FlvVideo flvVideo : flvVideos) {
            if (flvVideo.getSourceVideoId() == sourceVideoId && flvVideo.getQuality() == quality) {
                boolean widthEquals = true;
                boolean heightEquals = true;
                if (width == null && height == null) {
                    return flvVideo;
                } else if (width != null && flvVideo.getWidth() != null && !flvVideo.getWidth().equals(width)) {
                    widthEquals = false;
                } else if (height != null && flvVideo.getHeight() != null && !flvVideo.getHeight().equals(height)) {
                    heightEquals = false;
                }
                if (widthEquals && heightEquals) {
                    return flvVideo;
                }
            }
        }
        return null;
    }

    public void putCssParameterValue(final CssParameterValue cssParameterValue) {

    }

    @Override
    public void putCssValue(CssValue cssValue) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeCssParameterValue(final CssParameterValue cssParameterValue) {

    }

    public void removeBlogPost(BlogPost blogPost) {
        blogPosts.remove(blogPost);
    }

    public void removeVote(Vote vote) {
        votes.remove(vote);
    }

    public void removeVotesByFilledFormId(Integer filledFormId) {
        if (filledFormId != null) {
            for (Vote vote : votes) {
                if (vote.getFilledFormId() == filledFormId) {
                    votes.remove(vote);
                }
            }
        }
    }

    public void removeManageVotesGallerySettings(ManageVotesSettings manageVotesGallerySettings) {
        manageVotesGallerySettings.getManageVotes().removeManageVotesGallerySettings(manageVotesGallerySettings);
        manageVotesGallerySettingsList.remove(manageVotesGallerySettings);
    }

    public void removeUser(final User user) {
        List<UserOnSiteRight> userOnUserRightsList = user.getUserOnSiteRights();
        for (UserOnSiteRight tempUserOnSiteRight : userOnUserRightsList) {
            tempUserOnSiteRight.getId().getSite().getUserOnSiteRights().remove(tempUserOnSiteRight);
            userOnSiteRights.remove(tempUserOnSiteRight);
        }
        users.remove(user);
    }

    public void removeUserOnSiteRight(UserOnSiteRight userOnSiteRight) {
        if (userOnSiteRight == null) {
            return;
        }
        userOnSiteRight.getId().getSite().getUserOnSiteRights().remove(userOnSiteRight);
        userOnSiteRight.getId().getUser().getUserOnSiteRights().remove(userOnSiteRight);
        userOnSiteRights.remove(userOnSiteRight);
    }

    public void removeAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption) {
        advancedSearchOption.getAdvancedSearch().getAdvancedSearchOptions().remove(advancedSearchOption);
        advancedSearchOptions.remove(advancedSearchOption);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public void removePage(final Page page) {
        page.getSite().removePage(page);
        final WorkPageSettings workPageSettings = new PageManager(page).getWorkPageSettings();
        if (workPageSettings != null) {
            this.workPageSettings.remove(workPageSettings);
        }
        pages.remove(page);
        draftPageSettings.remove(page.getPageSettings());
    }

    public void removeSite(Site site) {
        if (site == null) {
            return;
        }
        sites.remove(site);

        fireRemove(Site.class, site.getId());
    }

    public ForumPost getLastThreadPost(int forumThreadId) {
        return null;
    }

    public ForumPost getLastSubForumPost(int subForumId) {
        return null;
    }

    public Content getContentById(ContentId contentId) {
        for (final Content content : contents) {
            if (content.getId().equals(contentId)) {
                return content;
            }
        }
        return null;
    }

    public void putContent(Content content) {
        contents.add(content);
    }

    public int getMaxContentClientId() {
        int maxClientId = 0;
        for (Content content : contents) {
            if (content.getId().getClientId() > maxClientId) {
                maxClientId = content.getId().getClientId();
            }
        }
        return maxClientId;
    }

    public void putKeywordsGroup(KeywordsGroup keywordsGroup) {
        int maxKeywordsGroupId = 0;
        for (KeywordsGroup tempKeywordsGroup : keywordsGroups) {
            if (tempKeywordsGroup.getKeywordsGroupId() > maxKeywordsGroupId) {
                maxKeywordsGroupId = tempKeywordsGroup.getKeywordsGroupId();
            }
        }
        keywordsGroup.setKeywordsGroupId(maxKeywordsGroupId + 1);
        keywordsGroups.add(keywordsGroup);
    }

    public void removeKeywordsGroup(KeywordsGroup keywordsGroup) {

    }

    public void removeContent(Content content) {
        contents.remove(content);
    }

    public void destroy() {
        clear();
    }

    public void removeImage(Image image) {
        images.remove(image);
    }

    public void putPage(final Page page) {
        int maxPageId = 0;
        for (final Page tempPage : pages) {
            if (tempPage.getPageId() > maxPageId) {
                maxPageId = tempPage.getPageId();
            }
        }
        page.setPageId(maxPageId + 1);
        pages.add(page);
    }

    public void putManageVotesGallerySettings(final DraftManageVotesSettings manageVotesGallerySettings) {
        int maxId = 0;
        for (final DraftManageVotesSettings tempManageVotesGallerySettings : manageVotesGallerySettingsList) {
            if (tempManageVotesGallerySettings.getId() > maxId) {
                maxId = tempManageVotesGallerySettings.getId();
            }
        }
        manageVotesGallerySettings.setId(maxId + 1);
        manageVotesGallerySettingsList.add(manageVotesGallerySettings);
    }

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
    public List<Widget> getWorkWidgetsByCrossWidgetsId(List<Integer> crossWidgetId) {
        if (crossWidgetId == null || crossWidgetId.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Widget> widgets = new ArrayList<Widget>();
        for (Widget widget : this.widgets) {
            if (crossWidgetId.contains(widget.getCrossWidgetId()) && widget.getWorkPageSettings() != null) {
                widgets.add(widget);
            }
        }
        return widgets;
    }

    @Override
    public List<Widget> getDraftWidgetsByCrossWidgetsId(List<Integer> crossWidgetId) {
        if (crossWidgetId == null || crossWidgetId.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Widget> widgets = new ArrayList<Widget>();
        for (Widget widget : this.widgets) {
            if (crossWidgetId.contains(widget.getCrossWidgetId()) && widget.getDraftPageSettings() != null) {
                widgets.add(widget);
            }
        }
        return widgets;
    }

    @Override
    public List<Widget> getWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption) {
        if (siteShowOption == null || sitesId == null || sitesId.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Widget> widgets = new ArrayList<Widget>();
        for (Widget widget : this.widgets) {
            if (sitesId.contains(widget.getSite().getSiteId())) {
                if ((siteShowOption.isWork() && widget.getWorkPageSettings() != null) ||
                        (!siteShowOption.isWork() && widget.getDraftPageSettings() != null))
                    widgets.add(widget);
            }
        }
        return widgets;
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
        for (Icon icon : icons) {
            if (icon.getIconId() == iconId) {
                return icon;
            }
        }
        return null;
    }

    @Override
    public void putIcon(Icon icon) {
        icon.setIconId(0);
        for (Icon tempIcon : icons) {
            if (icon.getIconId() < tempIcon.getIconId()) {
                icon.setIconId(tempIcon.getIconId());
            }
        }
        icon.setIconId(icon.getIconId() + 1);
        icons.add(icon);
    }

    @Override
    public void removeIcon(Icon icon) {
        for (Site site : sites) {
            if (site.getIcon().getIconId() == icon.getIconId()) {
                site.setIcon(null);
            }
        }
        icons.remove(icon);
    }

    @Override
    public void removeIcon(Integer iconId) {
        removeIcon(getIcon(iconId));
    }

    @Override
    public void putCSVDataExport(CSVDataExport csvDataExport) {
        csvDataExport.setId(0);
        for (CSVDataExport tempCSVDataExport : CSVDataExports) {
            if (csvDataExport.getId() < tempCSVDataExport.getId()) {
                csvDataExport.setId(tempCSVDataExport.getId());
            }
        }
        csvDataExport.setId(csvDataExport.getId() + 1);
        CSVDataExports.add(csvDataExport);
    }

    @Override
    public CSVDataExport getCSVDataExport(Integer customizeDataExportId) {
        if (customizeDataExportId == null) {
            return null;
        }
        for (CSVDataExport tempCSVDataExport : CSVDataExports) {
            if (tempCSVDataExport.getId() == customizeDataExportId) {
                return tempCSVDataExport;
            }
        }
        return null;
    }

    @Override
    public void putCSVDataExportField(CSVDataExportField field) {
        field.setId(0);
        for (CSVDataExportField tempField : CSVDataExportFields) {
            if (field.getId() < tempField.getId()) {
                field.setId(tempField.getId());
            }
        }
        field.setId(field.getId() + 1);
        CSVDataExportFields.add(field);

    }

    @Override
    public CSVDataExportField getCSVDataExportField(Integer fieldId) {
        if (fieldId == null) {
            return null;
        }
        for (CSVDataExportField tempField : CSVDataExportFields) {
            if (tempField.getId() == fieldId) {
                return tempField;
            }
        }
        return null;
    }

    @Override
    public void removeCSVDataExportField(CSVDataExportField field) {
        this.CSVDataExportFields.remove(field);
    }

    @Override
    public void putCustomizeManageRecords(CustomizeManageRecords customizeManageRecords) {
        customizeManageRecords.setId(0);
        for (CustomizeManageRecords tempCustomizeManageExport : this.customizeManageRecords) {
            if (customizeManageRecords.getId() < tempCustomizeManageExport.getId()) {
                customizeManageRecords.setId(tempCustomizeManageExport.getId());
            }
        }
        customizeManageRecords.setId(customizeManageRecords.getId() + 1);
        this.customizeManageRecords.add(customizeManageRecords);
    }

    @Override
    public CustomizeManageRecords getCustomizeManageRecords(Integer customizeManageRecordsId) {
        if (customizeManageRecordsId == null) {
            return null;
        }
        for (CustomizeManageRecords tempCustomizeManageExport : this.customizeManageRecords) {
            if (tempCustomizeManageExport.getId() == customizeManageRecordsId) {
                return tempCustomizeManageExport;
            }
        }
        return null;
    }

    @Override
    public CustomizeManageRecords getCustomizeManageRecords(Integer formId, Integer userId) {
        if (formId == null || userId == null) {
            return null;
        }
        for (CustomizeManageRecords manageRecords : this.customizeManageRecords) {
            if (manageRecords.getFormId() == formId && manageRecords.getUserId() == userId) {
                return manageRecords;
            }
        }
        return null;
    }

    @Override
    public void putCustomizeManageRecordsField(CustomizeManageRecordsField field) {
        field.setId(0);
        for (CustomizeManageRecordsField tempField : this.customizeManageRecordsFields) {
            if (field.getId() < tempField.getId()) {
                field.setId(tempField.getId());
            }
        }
        field.setId(field.getId() + 1);
        this.customizeManageRecordsFields.add(field);
    }

    @Override
    public CustomizeManageRecordsField getCustomizeManageRecordsField(Integer fieldId) {
        if (fieldId == null) {
            return null;
        }
        for (CustomizeManageRecordsField field : this.customizeManageRecordsFields) {
            if (field.getId() == fieldId) {
                return field;
            }
        }
        return null;
    }

    @Override
    public void removeCustomizeManageRecordsField(CustomizeManageRecordsField field) {
        this.customizeManageRecordsFields.remove(field);
    }

    @Override
    public void removeAccessibleSettings(final AccessibleSettings accessibleSettings) {
        this.accessibleSettings.remove(accessibleSettings);
    }

    @Override
    public void putTaxRate(TaxRateUS taxRate) {
        taxRate.setId(0);
        for (TaxRateUS tempTaxRate : this.taxRates) {
            if (taxRate.getId() < tempTaxRate.getId()) {
                taxRate.setId(tempTaxRate.getId());
            }
        }
        taxRate.setId(taxRate.getId() + 1);
        this.taxRates.add(taxRate);
    }

    @Override
    public void putItemSize(ItemSize itemSize) {
        itemSize.setId(0);
        for (ItemSize tempItemSize : this.itemSizes) {
            if (itemSize.getId() < tempItemSize.getId()) {
                itemSize.setId(tempItemSize.getId());
            }
        }
        itemSize.setId(itemSize.getId() + 1);
        this.itemSizes.add(itemSize);
    }

    @Override
    public ItemSize getItemSize(Integer itemSizeId) {
        if (itemSizeId == null) {
            return null;
        }
        for (ItemSize itemSize : this.itemSizes) {
            if (itemSize.getId() == itemSizeId) {
                return itemSize;
            }
        }
        return null;
    }

    @Override
    public void putFontsAndColors(FontsAndColors fontsAndColors) {
        fontsAndColors.setId(0);
        for (FontsAndColors tempFontsAndColors : this.fontsAndColorsList) {
            if (fontsAndColors.getId() < tempFontsAndColors.getId()) {
                fontsAndColors.setId(tempFontsAndColors.getId());
            }
        }
        fontsAndColors.setId(fontsAndColors.getId() + 1);
        this.fontsAndColorsList.add(fontsAndColors);
    }

    @Override
    public void removeFontsAndColors(FontsAndColors fontsAndColors) {
        if (fontsAndColors != null) {
            fontsAndColorsList.remove(getFontsAndColors(fontsAndColors.getId()));
        }
    }

    @Override
    public void removeBackground(Background background) {
        if (background != null) {
            backgrounds.remove(getBackground(background.getId()));
        }
    }

    @Override
    public void removeBorder(Border border) {
        if (border != null) {
            borders.remove(getBorder(border.getId()));
        }
    }

    @Override
    public FontsAndColors getFontsAndColors(Integer fontsAndColorsId) {
        if (fontsAndColorsId == null) {
            return null;
        }
        for (FontsAndColors fontsAndColors : this.fontsAndColorsList) {
            if (fontsAndColors.getId() == fontsAndColorsId) {
                return fontsAndColors;
            }
        }
        return null;
    }

    @Override
    public void putFontsAndColorsValue(FontsAndColorsValue fontsAndColorsValue) {

    }

    @Override
    public Map<Integer, String> getSitesIdWithTitles(Set<Integer> sitesId) {
        final Map<Integer, String> sitesIdWithNames = new HashMap<Integer, String>();
        for (Integer siteId : sitesId) {
            sitesIdWithNames.put(siteId, getSiteTitleBySiteId(siteId));
        }
        return sitesIdWithNames;
    }

    @Override
    public Date getLastUpdatedDate(int siteId) {
        Date lastUpdatedDate = null;
        for (Page page : pages) {
            if (page.getSite() != null && page.getSite().getSiteId() == siteId && (lastUpdatedDate == null || lastUpdatedDate.before(page.getPageSettings().getUpdated()))) {
                lastUpdatedDate = page.getPageSettings().getUpdated();
            }
        }
        return lastUpdatedDate != null ? lastUpdatedDate : getSite(siteId).getCreationDate();
    }

    public FormExportTask getFormExportTask(Integer formExportTaskId) {
        if (formExportTaskId == null) {
            return null;
        }
        for (FormExportTask formExportTask : formExportTasks) {
            if (formExportTask.getId() == formExportTaskId) {
                return formExportTask;
            }
        }
        return null;
    }

    public void putFormExportTask(FormExportTask formExportTask) {
        formExportTask.setId(0);
        for (FormExportTask tempFormExportTask : this.formExportTasks) {
            if (formExportTask.getId() < tempFormExportTask.getId()) {
                formExportTask.setId(tempFormExportTask.getId());
            }
        }
        formExportTask.setId(formExportTask.getId() + 1);
        this.formExportTasks.add(formExportTask);
    }

    public void removeFormExportTask(FormExportTask formExportTask) {
        formExportTasks.remove(getFormExportTask(formExportTask.getId()));
    }

    public List<FormExportTask> getFormExportTasksByFormId(int formId) {
        final List<FormExportTask> formExportTasks = new ArrayList<FormExportTask>();
        for (FormExportTask formExportTask : this.formExportTasks) {
            if (formExportTask.getFormId() == formId) {
                formExportTasks.add(formExportTask);
            }
        }
        return formExportTasks;
    }

    public List<FormExportTask> getFormExportTasksBySiteId(int siteId) {
        final List<DraftItem> draftItems = getDraftItemsBySiteId(siteId, ItemType.ALL_FORMS);
        final List<Integer> formsId = new ArrayList<Integer>();
        for (DraftItem draftItem : draftItems) {
            formsId.add(draftItem.getId());
        }
        final List<FormExportTask> formExportTasks = new ArrayList<FormExportTask>();
        for (FormExportTask formExportTask : this.formExportTasks) {
            if (formsId.contains(formExportTask.getFormId())) {
                formExportTasks.add(formExportTask);
            }
        }
        return formExportTasks;
    }

    public List<FormExportTask> getAllFormExportTasks() {
        return formExportTasks;
    }

    @Override
    public Site getSiteByBrandedSubDomain(final String brandedSubDomain, final String brandedDomain) {
        return siteByNetworkSubDomains.get(brandedSubDomain + brandedDomain);
    }

    @Override
    public List<String> getImagesKeywordsBySite(final Integer siteId) {
        final List<String> keywords = imagesKeywordsBySites.get(siteId);
        return keywords == null ? Collections.<String>emptyList() : keywords;
    }

    @Override
    public List<String> getImagesNamesBySite(final Integer siteId) {
        final List<String> imageNames = new ArrayList<String>();
        for (Image image : images) {
            imageNames.add(image.getName());
        }

        return imageNames;
    }

    @Override
    public List<Image> getImagesByKeywords(final Integer siteId, final List<String> keywords) {
        if (keywords.isEmpty()) {
            return getImagesByOwnerSiteId(siteId);
        }
        final List<Image> images = imagesByKeywords.get(siteId + "" + keywords);
        return images == null ? new ArrayList<Image>() : images;
    }

    @Override
    public void putHtml(final Html html) {

    }

    public List<Site> getPublishedBlueprints() {
        final List<Site> sites = new ArrayList<Site>();
        for (Site site : this.sites) {
            if (site.getPublicBlueprintsSettings().getPublished() != null) {
                sites.add(site);
            }
        }
        return sites;
    }

    public List<Site> getActiveBlueprints(final BlueprintCategory blueprintCategory) {
        final List<Site> sites = new ArrayList<Site>();
        for (Site site : this.sites) {
            final PublicBlueprintsSettings settings = site.getPublicBlueprintsSettings();
            if (settings.getActivated() != null &&
                    (blueprintCategory == null || blueprintCategory == settings.getBlueprintCategory())) {
                sites.add(site);
            }
        }
        return sites;
    }

    public GoogleBaseDataExportMappedByFilledFormId getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(Integer filledFormId) {
        if (filledFormId == null) {
            return null;
        }
        for (GoogleBaseDataExportMappedByFilledFormId exportMappedByFilledFormId : dataExportMappedByFilledFormIds) {
            if (exportMappedByFilledFormId.getFilledFormId() == filledFormId) {
                return exportMappedByFilledFormId;
            }
        }
        return null;
    }

    public void putGoogleBaseDataExportMappedByFilledFormId(GoogleBaseDataExportMappedByFilledFormId dataExportMappedByFilledFormId) {
        dataExportMappedByFilledFormId.setId(0);
        for (GoogleBaseDataExportMappedByFilledFormId tempSiteItem : dataExportMappedByFilledFormIds) {
            if (dataExportMappedByFilledFormId.getId() < tempSiteItem.getId()) {
                dataExportMappedByFilledFormId.setId(tempSiteItem.getId());
            }
            dataExportMappedByFilledFormId.setId(dataExportMappedByFilledFormId.getId() + 1);
        }
        dataExportMappedByFilledFormIds.add(dataExportMappedByFilledFormId);
    }

    @Override
    public List<PageSettings> getPageSettingsWithHtmlOrCss() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void putSiteByBrandedSubDomain(final String networkSubDomain, final String networkDomain, final Site site) {
        siteByNetworkSubDomains.put(networkSubDomain + networkDomain, site);
    }

    @Override
    public Page getPage(final Integer pageId) {
        if (pageId == null) {
            return null;
        }
        for (final Page page : pages) {
            if (page.getPageId() == pageId) {
                return page;
            }
        }
        return null;
    }

    @Override
    public Gallery getGalleryByOrderFormId(final Integer orderFormId) {
        if (orderFormId == null) {
            return null;
        }

        for (DraftItem siteItem : draftItems) {
            if (siteItem instanceof Gallery) {
                if (((Gallery) siteItem).getPaypalSettings().getOrdersFormId() != null
                        && ((Gallery) siteItem).getPaypalSettings().getOrdersFormId() == orderFormId) {
                    return (Gallery) siteItem;
                }
            }
        }

        return null;
    }

    public void putUserOnSiteRight(final UserOnSiteRight userOnSiteRight) {
        userOnSiteRights.add(userOnSiteRight);
    }

    @SuppressWarnings({"SuspiciousMethodCalls"})
    public void removeDraftItem(final DraftItem draftItem) {
        draftItems.remove(draftItem);
    }

    public void putItem(final Item siteItem) {
        if (siteItem instanceof DraftItem) {
            siteItem.setId(100004);
            for (Item tempSiteItem : draftItems) {
                if (siteItem.getId() < tempSiteItem.getId()) {
                    siteItem.setId(tempSiteItem.getId());
                }
                siteItem.setId(siteItem.getId() + 1);
            }
            draftItems.add((DraftItem) siteItem);
        } else {
            workItems.add((WorkItem) siteItem);
        }
    }

    public List<GalleryVideoRange> getGalleryVideoRangesByUserId(final int userId) {
        final List<GalleryVideoRange> videoRanges = new ArrayList<GalleryVideoRange>();
        for (GalleryVideoRange videoRange : galleryVideoRanges) {
            if (videoRange.getUser().getUserId() == userId) {
                videoRanges.add(videoRange);
            }
        }
        return videoRanges;
    }

    public List<GalleryVideoRange> getGalleryVideoRanges(final List<Integer> videoRangeIds) {
        final List<GalleryVideoRange> videoRanges = new ArrayList<GalleryVideoRange>();
        for (GalleryVideoRange videoRange : galleryVideoRanges) {
            if (videoRangeIds.contains(videoRange.getRangeId())) {
                videoRanges.add(videoRange);
            }
        }
        return videoRanges;
    }

    @Override
    public void putGalleryVideoRange(final GalleryVideoRange galleryVideoRange) {
        galleryVideoRange.setRangeId(1);
        for (GalleryVideoRange videoRange : galleryVideoRanges) {
            if (galleryVideoRange.getRangeId() < videoRange.getRangeId()) {
                galleryVideoRange.setRangeId(videoRange.getRangeId());
            }
        }
        galleryVideoRange.setRangeId(galleryVideoRange.getRangeId() + 1);
        galleryVideoRanges.add(galleryVideoRange);
    }

    @Override
    public DraftTellFriend getTellFriendById(final int tellFriendId) {
        for (final DraftItem tellFriend : draftItems) {
            if (tellFriend.getId() == tellFriendId) {
                return (DraftTellFriend) tellFriend;
            }
        }
        return null;
    }

    @Override
    public void putTellFriend(final DraftTellFriend tellFriend) {
        tellFriend.setId(0);
        for (DraftItem tempTellFriend : draftItems) {
            if (tellFriend.getId() < tempTellFriend.getId()) {
                tellFriend.setId(tempTellFriend.getId());
            }
        }
        tellFriend.setId(tellFriend.getId() + 1);
        draftItems.add(tellFriend);
    }

    @Override
    public List<DraftTellFriend> getTellFriendsBySiteId(final int siteId) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public List<DraftManageVotes> getManageVotesListBySiteId(final int siteId) {
        final List<DraftManageVotes> returnList = new ArrayList<DraftManageVotes>();
        for (DraftItem item : draftItems) {
            if (item.getItemType() == ItemType.MANAGE_VOTES && item.getSiteId() == siteId) {
                returnList.add((DraftManageVotes) item);
            }
        }

        return returnList;
    }

    @Override
    public List<DraftGallery> getGalleriesBySiteId(int siteId) {
        final List<DraftGallery> returnList = new ArrayList<DraftGallery>();
        for (DraftItem item : draftItems) {
            if (item.getItemType() == ItemType.GALLERY && item.getSiteId() == siteId) {
                returnList.add((DraftGallery) item);
            }
        }

        return returnList;
    }

    @Override
    public DraftTellFriend getTellFriendByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem tellFriend : draftItems) {
            if (tellFriend.getSiteId() == siteId && tellFriend.getName().equals(name)) {
                return (DraftTellFriend) tellFriend;
            }
        }
        return null;
    }

    @Override
    public DraftManageVotes getManageVotesByNameAndSiteId(final String name, final int siteId) {
        for (final DraftItem item : draftItems) {
            if (item.getItemType() == ItemType.MANAGE_VOTES && item.getSiteId() > 0 && item.getSiteId() == siteId
                    && item.getName().equals(name)) {
                return (DraftManageVotes) item;
            }
        }
        return null;
    }

    public void removeCreditCard(final CreditCard creditCard) {
        creditCard.getUser().getCreditCards().remove(creditCard);
        for (Site site : sites) {
            if (site.getSitePaymentSettings().getCreditCard() != null &&
                    site.getSitePaymentSettings().getCreditCard().getCreditCardId() == creditCard.getCreditCardId()) {
                site.getSitePaymentSettings().setCreditCard(null);
            }
        }
        for (ChildSiteSettings childSiteSettings : this.childSiteSettings) {
            if (childSiteSettings.getSitePaymentSettings().getCreditCard() != null &&
                    childSiteSettings.getSitePaymentSettings().getCreditCard().getCreditCardId() == creditCard.getCreditCardId()) {
                childSiteSettings.getSitePaymentSettings().setCreditCard(null);
            }
        }
        creditCards.remove(creditCard);
    }

    public <R> R inContext(final PersistanceContext<R> persistanceContext) {
        inContext = true;
        try {
            return persistanceContext.execute();
        } finally {
            inContext = false;
        }
    }

    public Site getSiteByCustomUrl(final String customUrl) {
        for (final Site site : sites) {
            if (site.getCustomUrl() != null && site.getCustomUrl().equals(customUrl)) {
                return site;
            }
        }
        return null;
    }

    @SuppressWarnings({"unchecked"})
    public <T extends DraftItem> T getDraftItem(final Integer itemId) {
        if (itemId != null) {
            for (DraftItem item : this.draftItems) {
                if (item.getId() == itemId) {
                    return (T) item;
                }
            }
        }
        return null;
    }

    public int getUsersCount() {
        return users.size();
    }

    public void addUpdateListener(final PersistanceListener listener) {
        updateListeners.add(listener);
    }

    public void addRemoveListener(final PersistanceListener listener) {
        removeListeners.add(listener);
    }

    public void notifyRemoveListeners(final Class entityClass, final Object entityId) {
        for (final PersistanceListener listener : removeListeners) {
            listener.execute(entityClass, entityId);
        }
    }

    public void notifyUpdateListeners(final Class entityClass, final Object entityId) {
        for (final PersistanceListener listener : updateListeners) {
            listener.execute(entityClass, entityId);
        }
    }

    public void clear() {
        galleryComments.clear();
        siteOnItems.clear();
        journalItems.clear();
        removeListeners.clear();
        updateListeners.clear();
        pages.clear();
        videos.clear();
        sites.clear();
        keywordsGroups.clear();
        draftItems.clear();
        contents.clear();
        blogPosts.clear();
        comments.clear();
        widgets.clear();
        images.clear();
        users.clear();
        subForums.clear();
        threads.clear();
        forumPosts.clear();
        forumVotes.clear();
        visits.clear();
        pageVisitors.clear();
        filledForms.clear();
        formItems.clear();
        filledFormItems.clear();
        formFilterRules.clear();
        sitePaymentSettings.clear();
        manageVotesGallerySettingsList.clear();
        paymentLogs.clear();
        advancedSearchOptions.clear();
        ipnLogs.clear();
        slideShowImages.clear();
    }

    public void putPageVisitor(PageVisitor pageVisitor) {
        pageVisitor.setPageVisitorId(0);
        for (PageVisitor tempPageVisitor : pageVisitors) {
            if (pageVisitor.getPageVisitorId() < tempPageVisitor.getPageVisitorId()) {
                pageVisitor.setPageVisitorId(tempPageVisitor.getPageVisitorId());
            }
        }
        pageVisitor.setPageVisitorId(pageVisitor.getPageVisitorId() + 1);
        pageVisitors.add(pageVisitor);
    }

    public void putAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption) {
        advancedSearchOption.setAdvancedSearchOptionId(0);
        for (DraftAdvancedSearchOption tempAdvancedSearchOption : advancedSearchOptions) {
            if (advancedSearchOption.getAdvancedSearchOptionId() < tempAdvancedSearchOption.getAdvancedSearchOptionId()) {
                advancedSearchOption.setAdvancedSearchOptionId(tempAdvancedSearchOption.getAdvancedSearchOptionId());
            }
        }
        advancedSearchOption.setAdvancedSearchOptionId(advancedSearchOption.getAdvancedSearchOptionId() + 1);
        advancedSearchOptions.add(advancedSearchOption);
    }

    public List<User> getVisitorsBySiteId(final int siteId) {
        final Site site = getSite(siteId);
        final List<User> returnList = new ArrayList<User>();

        if (site != null) {
            for (UserOnSiteRight userOnSiteRight : site.getUserOnSiteRights()) {
                if (!userOnSiteRight.getFilledRegistrationFormIds().isEmpty()) {
                    returnList.add(userOnSiteRight.getId().getUser());
                }
            }
        }

        return returnList;
    }

    public void putVisit(Visit visit) {
        visit.setVisitId(0);
        for (Visit tempVisit : visits) {
            if (visit.getVisitId() < tempVisit.getVisitId()) {
                visit.setVisitId(tempVisit.getVisitId());
            }
        }
        visit.setVisitId(visit.getVisitId() + 1);
        visits.add(visit);
    }

    public void putIPNLog(IPNLog ipnLog) {
        ipnLog.setLogId(0);
        for (IPNLog tempIpnLog : ipnLogs) {
            if (ipnLog.getLogId() < tempIpnLog.getLogId()) {
                ipnLog.setLogId(tempIpnLog.getLogId());
            }
        }
        ipnLog.setLogId(ipnLog.getLogId() + 1);
        ipnLogs.add(ipnLog);
    }

    public void putVisitReferrer(VisitReferrer visitReferrer) {
        visitReferrer.setVisitReferrerId(0);
        for (VisitReferrer tempVisitReferrer : visitReferrers) {
            if (visitReferrer.getVisitReferrerId() < tempVisitReferrer.getVisitReferrerId()) {
                visitReferrer.setVisitReferrerId(tempVisitReferrer.getVisitReferrerId());
            }
        }
        visitReferrer.setVisitReferrerId(visitReferrer.getVisitReferrerId() + 1);
        visitReferrers.add(visitReferrer);
    }

    public void putIncomeSettings(IncomeSettings incomeSetting) {
        incomeSetting.setIncomeSettingsId(0);
        for (IncomeSettings tempIncomeSettings : incomeSettings) {
            if (incomeSetting.getIncomeSettingsId() < tempIncomeSettings.getIncomeSettingsId()) {
                incomeSetting.setIncomeSettingsId(tempIncomeSettings.getIncomeSettingsId());
            }
        }
        incomeSetting.setIncomeSettingsId(incomeSetting.getIncomeSettingsId() + 1);
        incomeSettings.add(incomeSetting);
    }

    public void putSitePaymentSettings(SitePaymentSettings settings) {
        settings.setSitePaymentSettingsId(0);
        for (SitePaymentSettings tempSitePaymentSettings : sitePaymentSettings) {
            if (settings.getSitePaymentSettingsId() < tempSitePaymentSettings.getSitePaymentSettingsId()) {
                settings.setSitePaymentSettingsId(tempSitePaymentSettings.getSitePaymentSettingsId());
            }
        }
        settings.setSitePaymentSettingsId(settings.getSitePaymentSettingsId() + 1);
        sitePaymentSettings.add(settings);
    }

    public void putCreditCard(CreditCard creditCard) {
        creditCard.setCreditCardId(100004);
        for (CreditCard tempCreditCard : creditCards) {
            if (creditCard.getCreditCardId() < tempCreditCard.getCreditCardId()) {
                creditCard.setCreditCardId(tempCreditCard.getCreditCardId());
            }
        }
        creditCard.setCreditCardId(creditCard.getCreditCardId() + 1);
        creditCards.add(creditCard);
    }

    public long getHitsForPages(List<Integer> pageIds, DateInterval dateInterval) {
        long hirs = 0;
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    hirs += visit.getVisitCount();
                }
            }
        }
        return hirs;
    }

    public Map<Integer, Long> getHitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        Map<Integer, Long> returnMap = new HashMap<Integer, Long>();
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            long hits = 0;
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    hits += visit.getVisitCount();
                }
            }
            returnMap.put(pageId, hits);
        }
        return returnMap;
    }

    public Map<Integer, Long> getUniqueVisitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        Map<Integer, Long> returnMap = new HashMap<Integer, Long>();
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            List<Integer> uniqueVisitIdsList = new ArrayList<Integer>();
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate())) &&
                        !uniqueVisitIdsList.contains(visit.getPageVisitor().getPageVisitorId())) {
                    uniqueVisitIdsList.add(visit.getPageVisitor().getPageVisitorId());
                }
            }
            returnMap.put(pageId, (long) uniqueVisitIdsList.size());
        }
        return returnMap;
    }

    public Map<String, Integer> getRefUrlsByPages(List<Integer> pageIds, DateInterval dateInterval) {
        final Map<String, Integer> refUrlsByPages = new HashMap<String, Integer>();
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    for (VisitReferrer refUrl : visit.getReferrerURLs()) {
                        if (refUrlsByPages.containsKey(refUrl.getTermOrUrl())) {
                            refUrlsByPages.put(refUrl.getTermOrUrl(), refUrlsByPages.get(refUrl.getTermOrUrl()) + refUrl.getVisitCount());
                        }
                        refUrlsByPages.put(refUrl.getTermOrUrl(), refUrl.getVisitCount());
                    }
                }
            }
        }
        return refUrlsByPages;
    }

    public Map<String, Integer> getRefSearchTermsByPages(List<Integer> pageIds, DateInterval dateInterval) {
        final Map<String, Integer> refSearchTermsByPages = new HashMap<String, Integer>();
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    for (VisitReferrer searchTerm : visit.getReferrerSearchTerms()) {
                        if (refSearchTermsByPages.containsKey(searchTerm.getTermOrUrl())) {
                            refSearchTermsByPages.put(searchTerm.getTermOrUrl(), refSearchTermsByPages.get(searchTerm.getTermOrUrl()) + searchTerm.getVisitCount());
                        }
                        refSearchTermsByPages.put(searchTerm.getTermOrUrl(), searchTerm.getVisitCount());
                    }
                }
            }
        }
        return refSearchTermsByPages;
    }

    public long getUniqueVisitsCountForPages(List<Integer> pageIds, DateInterval dateInterval) {
        List<Visit> allVisits = new ArrayList<Visit>();
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    allVisits.add(visit);
                }
            }
        }

        List<Integer> uniqueVisitorIds = new ArrayList<Integer>();
        for (Visit visit : allVisits) {
            if (!uniqueVisitorIds.contains(visit.getPageVisitor().getPageVisitorId())) {
                uniqueVisitorIds.add(visit.getPageVisitor().getPageVisitorId());
            }
        }

        return uniqueVisitorIds.size();
    }

    public long getOverallTimeForPages(List<Integer> pageIds, DateInterval dateInterval) {
        long time = 0;
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    time += visit.getOverallTimeOfVisit();
                }
            }
        }
        return time;
    }

    public Map<Integer, Long> getOverallTimeForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        Map<Integer, Long> returnMap = new HashMap<Integer, Long>();
        for (int pageId : pageIds) {
            Page page = getPage(pageId);
            long time = 0;
            for (Visit visit : page.getPageVisits()) {
                if ((dateInterval.getStartDate() == null || visit.getVisitCreationDate().after(dateInterval.getStartDate())) &&
                        (dateInterval.getEndDate() == null || visit.getVisitCreationDate().before(dateInterval.getEndDate()))) {
                    time += visit.getOverallTimeOfVisit();
                }
            }
            returnMap.put(pageId, time);
        }
        return returnMap;
    }

    public PageVisitor getPageVisitorById(Integer pageVisitorId) {
        if (pageVisitorId == null) {
            return null;
        }
        for (PageVisitor pageVisitor : pageVisitors) {
            if (pageVisitor.getPageVisitorId() == pageVisitorId) {
                return pageVisitor;
            }
        }
        return null;
    }

    public DraftAdvancedSearchOption getAdvancedSearchOptionById(int advancedSearchOptionId) {
        for (DraftAdvancedSearchOption advancedSearchOption : advancedSearchOptions) {
            if (advancedSearchOption.getAdvancedSearchOptionId() == advancedSearchOptionId) {
                return advancedSearchOption;
            }
        }
        return null;
    }

    public List<PageVisitor> getPageVisitorsByUserId(int visitorId) {
        final List<PageVisitor> returnList = new ArrayList<PageVisitor>();
        for (PageVisitor pageVisitor : pageVisitors) {
            if (pageVisitor.getUserId() == visitorId) {
                returnList.add(pageVisitor);
            }
        }
        return returnList;
    }

    public DraftForm getFormById(Integer formId) {
        if (formId == null) {
            return null;
        }
        for (final DraftItem form : draftItems) {
            if (form.getId() == formId && (form instanceof DraftForm)) {
                return (DraftForm) form;
            }
        }

        return null;
    }

    public DraftFormFilterRule getFormFilterRuleById(int formFilterRuleId) {
        for (DraftFormFilterRule formFilterRule : formFilterRules) {
            if (formFilterRule.getFormFilterRuleId() == formFilterRuleId) {
                return formFilterRule;
            }
        }

        return null;
    }

    public DraftFormFilter getFormFilterById(Integer filterId) {
        if (filterId == null) {
            return null;
        }
        for (DraftItem siteItem : draftItems) {
            if (siteItem instanceof DraftForm) {
                final DraftForm form = (DraftForm) siteItem;
                for (final DraftFormFilter formFilter : form.getDraftFilters()) {
                    if (formFilter.getFormFilterId() == filterId) {
                        return formFilter;
                    }
                }
            }
        }
        return null;
    }

    public DraftFormFilter getFormFilterByNameAndUserId(String name, int userId) {
        List<DraftFormFilter> userFilters = getFormFiltersByUserId(userId);
        for (DraftFormFilter formFilter : userFilters) {
            if (formFilter.getName().equals(name)) {
                return formFilter;
            }
        }

        return null;
    }

    public DraftFormFilter getFormFilterByNameAndFormId(String name, int formId) {
        for (DraftFormFilter formFilter : formFilters) {
            if (formFilter.getName().equals(name)
                    && formFilter.getForm().getId() == formId) {
                return formFilter;
            }
        }

        return null;
    }

    public FilledForm getFilledFormById(Integer filledFormId) {
        if (filledFormId == null) {
            return null;
        }
        for (FilledForm filledForm : filledForms) {
            if (filledForm.getFilledFormId() == filledFormId) {
                return filledForm;
            }
        }
        return null;
    }

    public FilledFormItem getFilledFormItemById(Integer filledFormItemId) {
        if (filledFormItemId == null) {
            return null;
        }
        for (FilledFormItem filledFormItem : filledFormItems) {
            if (filledFormItem.getItemId() == filledFormItemId) {
                return filledFormItem;
            }
        }
        return null;
    }

    public List<FilledFormItem> getFilledFormItemByFormItemId(Integer formItemId) {
        List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        if (formItemId == null) {
            return filledFormItems;
        }
        for (FilledFormItem filledFormItem : this.filledFormItems) {
            if (filledFormItem.getFormItemId() == formItemId) {
                filledFormItems.add(filledFormItem);
            }
        }
        return filledFormItems;
    }

    public Visit getVisitByPageIdAndUserId(final int pageId, final int visitorId) {
        for (Visit visit : visits) {
            if (visit.getVisitedPage().getPageId() == pageId && visit.getPageVisitor().getPageVisitorId() == visitorId) {
                return visit;
            }
        }
        return null;
    }

    public void putFormFilterRule(DraftFormFilterRule formFilterRule) {
        formFilterRule.setFormFilterRuleId(0);
        for (DraftFormFilterRule tempFormFilterRule : formFilterRules) {
            if (formFilterRule.getFormFilterRuleId() < tempFormFilterRule.getFormFilterRuleId()) {
                formFilterRule.setFormFilterRuleId(tempFormFilterRule.getFormFilterRuleId());
            }
        }
        formFilterRule.setFormFilterRuleId(formFilterRule.getFormFilterRuleId() + 1);
        formFilterRules.add(formFilterRule);
    }

    public void putFormFilter(final DraftFormFilter formFilter) {
        formFilter.setFormFilterId(0);
        for (DraftItem siteItem : draftItems) {
            if (siteItem instanceof DraftForm) {
                final DraftForm form = (DraftForm) siteItem;
                for (final DraftFormFilter tempFormFilter : form.getDraftFilters()) {
                    if (formFilter.getFormFilterId() < tempFormFilter.getFormFilterId()) {
                        formFilter.setFormFilterId(tempFormFilter.getFormFilterId());
                    }
                }
            }
        }
        formFilter.setFormFilterId(formFilter.getFormFilterId() + 1);
        formFilters.add(formFilter);
    }

    @Override
    public List<BlogPost> getBlogPostsByBlog(final int blogId) {
        final List<BlogPost> result = new ArrayList<BlogPost>();
        for (final BlogPost blogPost : blogPosts) {
            if (blogPost.getBlog().getFormId() == blogId) {
                result.add(blogPost);
            }
        }
        return result;
    }

    @Override
    public int getBlogPostsCountByBlog(int blogId) {
        return getBlogPostsByBlog(blogId).size();
    }

    @Override
    public Map<Integer, Integer> getBlogPostsCountByBlogs(Set<Integer> blogsId) {
        final Map<Integer, Integer> blogsIdWithBlogPostsCount = new HashMap<Integer, Integer>();
        for (Integer blogId : blogsId) {
            blogsIdWithBlogPostsCount.put(blogId, getBlogPostsCountByBlog(blogId));
        }
        return blogsIdWithBlogPostsCount;
    }

    public void putMenu(Menu menu) {
        menu.setId(0);
        for (Item tempMenu : (menu instanceof DraftMenu) ? draftItems : workItems) {
            if (menu.getId() < tempMenu.getId()) {
                menu.setId(tempMenu.getId());
            }
        }
        menu.setId(menu.getId() + 1);
        if (menu instanceof DraftMenu) {
            draftItems.add((DraftMenu) menu);
        } else {
            workItems.add((WorkMenu) menu);
        }
    }

    public User getUserByEmail(final String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public List<DraftFormFilter> getFormFiltersByUserId(final int userId) {
        final List<DraftFormFilter> availableFormFilters = new ArrayList<DraftFormFilter>();
        for (final DraftItem form : getDraftItemsByUserId(userId, ItemType.ALL_FORMS)) {
            availableFormFilters.addAll(((DraftForm) form).getDraftFilters());
        }
        return availableFormFilters;
    }

    public void putEmailUpdateRequest(EmailUpdateRequest emailUpdateRequest) {
        emailUpdateRequests.add(emailUpdateRequest);
    }

    public EmailUpdateRequest getEmailUpdateRequestById(String updateId) {
        for (EmailUpdateRequest emailUpdateRequest : emailUpdateRequests) {
            if (emailUpdateRequest.getUpdateId().equals(updateId)) {
                return emailUpdateRequest;
            }
        }
        return null;
    }

    public void putUser(User user) {
        user.setUserId(0);
        for (User tempUser : users) {
            if (user.getUserId() < tempUser.getUserId()) {
                user.setUserId(tempUser.getUserId());
            }
        }
        user.setUserId(user.getUserId() + 1);
        users.add(user);
    }

    public void putFormItem(DraftFormItem formItem) {
        formItem.setFormItemId(0);
        for (DraftFormItem tempFormItem : formItems) {
            if (formItem.getFormItemId() < tempFormItem.getFormItemId()) {
                formItem.setFormItemId(tempFormItem.getFormItemId());
            }
        }
        formItem.setFormItemId(formItem.getFormItemId() + 1);
        formItems.add(formItem);
    }

    public void putSlideShowImage(DraftSlideShowImage slideShowImage) {
        slideShowImage.setSlideShowImageId(0);
        for (DraftSlideShowImage tempSlideShowImage : slideShowImages) {
            if (slideShowImage.getSlideShowImageId() < tempSlideShowImage.getSlideShowImageId()) {
                slideShowImage.setSlideShowImageId(tempSlideShowImage.getSlideShowImageId());
            }
        }
        slideShowImage.setSlideShowImageId(slideShowImage.getSlideShowImageId() + 1);
        slideShowImages.add(slideShowImage);
    }

    public void putWorkSlideShowImage(WorkSlideShowImage slideShowImage) {
        slideShowImage.setSlideShowImageId(0);
        for (DraftSlideShowImage tempSlideShowImage : slideShowImages) {
            if (slideShowImage.getSlideShowImageId() < tempSlideShowImage.getSlideShowImageId()) {
                slideShowImage.setSlideShowImageId(tempSlideShowImage.getSlideShowImageId());
            }
        }
        slideShowImage.setSlideShowImageId(slideShowImage.getSlideShowImageId() + 1);
        workSlideShowImages.add(slideShowImage);
    }

    public void putFilledForm(FilledForm filledForm) {
        filledForm.setFilledFormId(0);
        for (FilledForm tempFilledForm : filledForms) {
            if (filledForm.getFilledFormId() < tempFilledForm.getFilledFormId()) {
                filledForm.setFilledFormId(tempFilledForm.getFilledFormId());
            }
        }
        filledForm.setFilledFormId(filledForm.getFilledFormId() + 1);
        filledForms.add(filledForm);
    }

    public void putFilledFormItem(FilledFormItem filledFormItem) {
        filledFormItem.setItemId(0);
        for (FilledFormItem tempFilledFormItem : filledFormItems) {
            if (filledFormItem.getItemId() < tempFilledFormItem.getItemId()) {
                filledFormItem.setItemId(tempFilledFormItem.getItemId());
            }
        }
        filledFormItem.setItemId(filledFormItem.getItemId() + 1);
        filledFormItems.add(filledFormItem);
    }

    public Date getLastFillFormDateByFormId(final int formId) {
        return null;
    }

    @Override
    public Map<Integer, Date> getLastFillFormDateByFormsId(Set<Integer> blogsId) {
        final Map<Integer, Date> forumsIdWithSubforumsCount = new HashMap<Integer, Date>();
        for (Integer formId : blogsId) {
            forumsIdWithSubforumsCount.put(formId, getLastFillFormDateByFormId(formId));
        }
        return forumsIdWithSubforumsCount;
    }

    public void putRegistrationForm(DraftRegistrationForm registrationForm) {
        registrationForm.setId(0);
        for (DraftItem tempRegistrationForm : draftItems) {
            if (registrationForm.getFormId() < tempRegistrationForm.getId()) {
                registrationForm.setId(tempRegistrationForm.getId());
            }
        }
        registrationForm.setId(registrationForm.getFormId() + 1);
        draftItems.add(registrationForm);
    }

    public void putCustomForm(DraftCustomForm customForm) {
        customForm.setId(456);
        for (DraftItem tempCustomForm : draftItems) {
            if (customForm.getFormId() < tempCustomForm.getId()) {
                customForm.setId(tempCustomForm.getId());
            }
        }
        customForm.setId(customForm.getFormId() + 1);
        draftItems.add(customForm);
    }

    public void putSubForum(SubForum subForum) {
        subForum.setSubForumId(0);
        for (SubForum tempSubForum : subForums) {
            if (subForum.getSubForumId() < tempSubForum.getSubForumId()) {
                subForum.setSubForumId(tempSubForum.getSubForumId());
            }
        }
        subForum.setSubForumId(subForum.getSubForumId() + 1);
        subForums.add(subForum);
    }

    public void putForumThread(ForumThread forumThread) {
        forumThread.setThreadId(0);
        for (ForumThread tempForumThread : threads) {
            if (forumThread.getThreadId() < tempForumThread.getThreadId()) {
                forumThread.setThreadId(tempForumThread.getThreadId());
            }
        }
        forumThread.setThreadId(forumThread.getThreadId() + 1);
        threads.add(forumThread);
    }

    public void putForumPost(ForumPost forumPost) {
        forumPost.setForumPostId(0);
        for (ForumPost tempForumPost : forumPosts) {
            if (forumPost.getForumPostId() < tempForumPost.getForumPostId()) {
                forumPost.setForumPostId(tempForumPost.getForumPostId());
            }
        }
        forumPost.setForumPostId(forumPost.getForumPostId() + 1);
        forumPosts.add(forumPost);
    }

    public User getUserById(Integer userId) {
        if (userId == null) {
            return null;
        }
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public UserOnSiteRight getUserOnSiteRightById(final UserOnSiteRightId userOnSiteRightId) {
        for (final UserOnSiteRight userOnSiteRight : userOnSiteRights) {
            if (userOnSiteRight.getId().equals(userOnSiteRightId)) {
                return userOnSiteRight;
            }
        }
        return null;
    }

    public UserOnSiteRight getUserOnSiteRightByUserAndSiteId(final Integer userId, final Integer siteId) {
        if (userId == null || siteId == null || getUserById(userId) == null) {
            return null;
        }
        for (final UserOnSiteRight userOnUserRight : getUserById(userId).getUserOnSiteRights()) {
            if (userOnUserRight.getId().getSite().getSiteId() == siteId) {
                return userOnUserRight;
            }
        }
        return null;
    }

    public UserOnSiteRight getUserOnSiteRightByUserAndFormId(final User user, final Integer formId) {
        if (user == null || formId == null) {
            return null;
        }
        for (final UserOnSiteRight userOnUserRight : user.getUserOnSiteRights()) {
            for (Integer filledRegistrationFormId : userOnUserRight.getFilledRegistrationFormIds()) {
                final FilledForm filledRegistrationForm = getFilledFormById(filledRegistrationFormId);

                if (filledRegistrationForm != null && filledRegistrationForm.getFormId() == formId) {
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

                if (filledRegistrationForm != null && filledRegistrationForm.getFormId() == formId) {
                    return filledRegistrationForm;
                }
            }
        }
        return null;
    }

    public ForumPost getForumPostById(int postId) {
        for (ForumPost post : forumPosts) {
            if (post.getForumPostId() == postId) {
                return post;
            }
        }
        return null;
    }

    public List<JournalItem> getJournalItems() {
        return journalItems;
    }

    public void putJournalItem(final JournalItem journalItem) {
        journalItem.setJournalItemId(0);
        for (JournalItem tempJournalItem : journalItems) {
            if (journalItem.getJournalItemId() < tempJournalItem.getJournalItemId()) {
                journalItem.setJournalItemId(tempJournalItem.getJournalItemId());
            }
        }
        journalItem.setJournalItemId(journalItem.getJournalItemId() + 1);
        journalItems.add(journalItem);
    }

    public List<User> getNotActivatedUsers(final Date notActivateTo, final int countUsers) {
        final List<User> notActivatedUsers = new ArrayList<User>();
        for (final User user : users) {
            if (user.getActiveted() == null && user.getRegistrationDate().before(notActivateTo)) {
                notActivatedUsers.add(user);
            }
            if (notActivatedUsers.size() == countUsers) break;
        }
        return notActivatedUsers;
    }

    public void checkInContext() {
        if (!inContext) throw new UnsupportedOperationException("Can't use persistance methow without context!");
    }

    @Override
    public Vote getVoteById(Integer voteId) {
        if (voteId == null) {
            return null;
        }
        for (Vote vote : votes) {
            if (vote.getVoteId() == voteId) {
                return vote;
            }
        }
        return null;
    }

    @Override
    public void putVote(Vote vote) {
        vote.setVoteId(0);
        for (Vote tempVote : votes) {
            if (vote.getVoteId() < tempVote.getVoteId()) {
                vote.setVoteId(tempVote.getVoteId());
            }
        }
        vote.setVoteId(vote.getVoteId() + 1);
        votes.add(vote);
    }

    @Override
    public void setAllWinnerVotesToFalse(Integer userId, Integer galleryId) {
        if (userId == null || galleryId == null) {
            return;
        }
        for (Vote vote : votes) {
            if (vote.getUserId().equals(userId) && vote.getGalleryId() == galleryId) {
                vote.setWinner(false);
            }
        }
    }

    @Override
    public List<Vote> getVotesByStartEndDates(Integer userId, Integer galleryId, final Date startDate, final Date endDate, Integer... filledFormIds) {
        if (userId == null || galleryId == null || filledFormIds.length == 0) {
            return Collections.emptyList();
        }
        List<Vote> votes = new ArrayList<Vote>();
        List<Integer> filledFormId = Arrays.asList(filledFormIds);
        for (Vote vote : this.votes) {
            if (vote.getUserId().equals(userId) && vote.getGalleryId() == galleryId && vote.getStartDate() == startDate &&
                    vote.getEndDate() == endDate &&
                    filledFormId.contains(vote.getFilledFormId())) {
                votes.add(vote);
            }
        }
        return votes;
    }

    @Override
    public List<Vote> getVotesByTimeInterval(Integer userId, Integer galleryId, final Date startDate, final Date endDate, Integer... filledFormIds) {
        if (userId == null || galleryId == null || filledFormIds.length == 0) {
            return Collections.emptyList();
        }
        List<Vote> votes = new ArrayList<Vote>();
        List<Integer> filledFormId = Arrays.asList(filledFormIds);
        for (Vote vote : this.votes) {
            if (vote.getUserId().equals(userId) && vote.getGalleryId() == galleryId &&
                    (startDate != null && vote.getVoteDate() != null && (vote.getVoteDate().equals(startDate) || vote.getVoteDate().after(startDate))) &&
                    (endDate != null && vote.getVoteDate() != null && (vote.getVoteDate().equals(endDate) || vote.getVoteDate().before(endDate))) &&
                    filledFormId.contains(vote.getFilledFormId())) {
                votes.add(vote);
            }
        }
        return votes;
    }

    @Override
    public void putGalleryComment(final GalleryComment galleryComment) {
        galleryComment.setCommentId(0);
        for (GalleryComment tempGalleryComment : galleryComments) {
            if (galleryComment.getCommentId() < tempGalleryComment.getCommentId()) {
                galleryComment.setCommentId(tempGalleryComment.getCommentId());
            }
        }
        galleryComment.setCommentId(galleryComment.getCommentId() + 1);
        galleryComments.add(galleryComment);
    }

    @Override
    public List<GalleryComment> getGalleryCommentsByFilledFormAndGallery(
            final int filledFormId, final int galleryId, Integer userId, Date start, Date finish) {
        final List<GalleryComment> result = new ArrayList<GalleryComment>();
        for (final GalleryComment galleryComment : galleryComments) {
            if (galleryComment.getFilledForm().getFilledFormId() == filledFormId
                    && galleryComment.getGallery().getId() == galleryId) {
                if (userId == null || userId.equals(galleryComment.getUserId())) {
                    if (start == null || (start.before(galleryComment.getCreated())
                            && finish.after(galleryComment.getCreated()))) {
                        result.add(galleryComment);
                    }
                }
            }
        }

        Collections.sort(result, new Comparator<GalleryComment>() {

            @Override
            public int compare(final GalleryComment o1, final GalleryComment o2) {
                return o1.getCreated().compareTo(o2.getCreated());
            }

        });
        return result;
    }

    @Override
    public GalleryComment getGalleryCommentById(final int galleryCommentId) {
        for (final GalleryComment galleryComment : galleryComments) {
            if (galleryComment.getCommentId() == galleryCommentId) {
                return galleryComment;
            }
        }
        return null;
    }

    @Override
    public void removeGalleryComment(final GalleryComment galleryComment) {
        galleryComments.remove(galleryComment);
    }

    public void removeGalleryVideoRange(final GalleryVideoRange galleryVideoRange) {
        //
    }

    public void putPaymentLog(final PaymentLog paymentLog) {
        paymentLog.setLogId(0);
        for (PaymentLog tempPaymentLog : paymentLogs) {
            if (paymentLog.getLogId() < tempPaymentLog.getLogId()) {
                paymentLog.setLogId(tempPaymentLog.getLogId());
            }
        }
        paymentLog.setLogId(paymentLog.getLogId() + 1);
        paymentLogs.add(paymentLog);
    }

    @Override
    public PaymentLog getPaymentLogById(int paymentLogId) {
        for (final PaymentLog paymentLog : paymentLogs) {
            if (paymentLog.getLogId() == paymentLogId) {
                return paymentLog;
            }
        }
        return null;
    }

    @Override
    public List<PaymentLog> getPaymentLogsByUsersId(int usersId) {
        final List<PaymentLog> returnList = new ArrayList<PaymentLog>();
        for (final PaymentLog paymentLog : paymentLogs) {
            if (Arrays.asList(usersId).contains(paymentLog.getUserId())) {
                returnList.add(paymentLog);
            }
        }

        return returnList;
    }

    @Override
    public List<PaymentLog> getAllPaymentLogs() {
        return paymentLogs;
    }

    @Override
    public List<PaymentLog> getPaymentLogsByChildSiteSettingsId(int childSiteSettingsId) {
        final List<PaymentLog> returnList = new ArrayList<PaymentLog>();
        for (final PaymentLog paymentLog : paymentLogs) {
            if (paymentLog.getChildSiteSettingsId() == childSiteSettingsId) {
                returnList.add(paymentLog);
            }
        }

        return returnList;
    }

    @Override
    public List<Integer> getPageParentIds(final int siteId, final Integer parentId) {
        throw new UnsupportedOperationException();
    }

    public void putMenuItem(MenuItem menuItem) {
        if (menuItem instanceof WorkMenuItem) {
            putWorkMenuItem((WorkMenuItem) menuItem);
        } else {
            putDraftMenuItem((DraftMenuItem) menuItem);
        }
    }

    private void putWorkMenuItem(final WorkMenuItem menuItem) {
        menuItem.setId(0);
        for (MenuItem tempMenuItem : workMenuItems) {
            if (menuItem.getId() < tempMenuItem.getId()) {
                menuItem.setId(tempMenuItem.getId());
            }
        }
        menuItem.setId(menuItem.getId() + 1);
        workMenuItems.add(menuItem);
    }

    private void putDraftMenuItem(final DraftMenuItem menuItem) {
        menuItem.setId(0);
        for (DraftMenuItem tempMenuItem : menuItems) {
            if (menuItem.getId() < tempMenuItem.getId()) {
                menuItem.setId(tempMenuItem.getId());
            }
        }
        menuItem.setId(menuItem.getId() + 1);
        menuItems.add(menuItem);
    }

    public void removeMenuItem(MenuItem menuItem) {
        if (menuItem != null) {
            if (menuItem.getParent() != null) {
                menuItem.getParent().removeChild(menuItem);
            } else {
                menuItem.getMenu().removeChild(menuItem);
            }
            if (menuItem instanceof DraftMenuItem) {
                menuItems.remove((DraftMenuItem) menuItem);
                menuItems.removeAll(getAllChildren(menuItem));
            } else {
                workMenuItems.remove((WorkMenuItem) menuItem);
                workMenuItems.removeAll(getAllChildren(menuItem));
            }
        }
    }

    private List<MenuItem> getAllChildren(MenuItem menuItem) {
        final List<MenuItem> allChildren = new ArrayList<MenuItem>();
        for (MenuItem child : menuItem.getChildren()) {
            allChildren.add(child);
            if (!child.getChildren().isEmpty()) {
                allChildren.addAll(getAllChildren(child));
            }
        }
        return allChildren;
    }

    public DraftMenuItem getDraftMenuItem(Integer menuItemId) {
        if (menuItemId == null) {
            return null;
        }
        for (DraftMenuItem tempMenuItem : menuItems) {
            if (tempMenuItem.getId() == menuItemId) {
                return tempMenuItem;
            }
        }
        return null;
    }

    @Override
    public WorkMenuItem getWorkMenuItem(Integer menuItemId) {
        if (menuItemId == null) {
            return null;
        }
        for (WorkMenuItem tempMenuItem : workMenuItems) {
            if (tempMenuItem.getId() == menuItemId) {
                return tempMenuItem;
            }
        }
        return null;
    }

    public List<DraftMenuItem> getMenuItems(Integer pageId) {
        if (pageId == null) {
            return Collections.emptyList();
        }
        final List<DraftMenuItem> newMenuItems = new ArrayList<DraftMenuItem>();
        for (DraftMenuItem tempMenuItem : menuItems) {
            if (tempMenuItem.getPageId() == pageId) {
                newMenuItems.add(tempMenuItem);
            }
        }
        return newMenuItems;
    }

    public void setMenuItemsIncludeInMenu(List<Integer> menuItemsIds, boolean includeInMenu) {
        for (DraftMenuItem menuItem : menuItems) {
            if (menuItemsIds.contains(menuItem.getId())) {
                menuItem.setIncludeInMenu(includeInMenu);
            }
        }
    }

    @Override
    public void putFormVideo(FormVideo formVideo) {
        formVideo.setFormVideoId(0);
        for (FormVideo tempFormVideo : formVideos) {
            if (formVideo.getFormVideoId() < tempFormVideo.getFormVideoId()) {
                formVideo.setFormVideoId(tempFormVideo.getFormVideoId());
            }
        }
        formVideo.setFormVideoId(formVideo.getFormVideoId() + 1);
        formVideos.add(formVideo);
    }

    @Override
    public FormVideo getFormVideoById(Integer formVideoId) {
        for (final FormVideo formVideo : formVideos) {
            if (formVideo.getFormVideoId() == formVideoId) {
                return formVideo;
            }
        }
        return null;
    }

    @Override
    public List<FormVideo> getAllFormVideos() {
        return formVideos;
    }

    @Override
    public List<DraftForm> getAllForms() {
        List<DraftForm> forms = new ArrayList<DraftForm>();
        for (DraftItem item : draftItems) {
            if (item instanceof DraftForm) {
                forms.add((DraftForm) item);
            }
        }
        return forms;
    }

    public List<UserOnSiteRight> getAllUserOnSiteRights() {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<DraftGallery> getGalleriesByDataCrossWidgetIds(Integer... crossWidgetIds) {
        final List<DraftGallery> galleries = new ArrayList<DraftGallery>();
        for (Integer crossWidgetId : crossWidgetIds) {
            galleries.addAll(getGalleriesByDataCrossWidgetId(crossWidgetId));
        }
        return galleries;
    }

    private List<DraftGallery> getGalleriesByDataCrossWidgetId(final Integer crossWidgetId) {
        if (crossWidgetId == null) {
            return new ArrayList<DraftGallery>();
        }
        List<DraftGallery> galleries = new ArrayList<DraftGallery>();
        for (DraftItem siteItem : draftItems) {
            if (siteItem.getItemType() == ItemType.GALLERY && ((DraftGallery) siteItem).getDataCrossWidgetId().equals(crossWidgetId)) {
                galleries.add((DraftGallery) siteItem);
            }
        }
        return galleries;
    }

    @Override
    public List<WidgetItem> getWidgetItemsByGalleriesId(final Collection<Integer> galleriesId) {
        throw new UnsupportedOperationException();
    }

    public List<DraftChildSiteRegistration> getAllChildSiteRegistrations() {
        List<DraftChildSiteRegistration> childSiteRegistrations = new ArrayList<DraftChildSiteRegistration>();
        for (final DraftItem childSiteRegistration : draftItems) {
            if (childSiteRegistration.getItemType().equals(ItemType.CHILD_SITE_REGISTRATION)) {
                childSiteRegistrations.add((DraftChildSiteRegistration) childSiteRegistration);
            }
        }
        return childSiteRegistrations;
    }

    public Coordinate getCoordinate(final String zip, final Country country) {
        if (zip == null || country == null) {
            return null;
        }
        for (Coordinate coordinate : coordinates) {
            if (coordinate.getZip().equals(zip) && coordinate.getCountry() == country) {
                return coordinate;
            }
        }
        return null;
    }

    public void putCoordinate(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public List<Coordinate> getAllCoordinates() {
        return coordinates;
    }

    public void executeAlter(Alter alter) {

    }

    @Override
    public void executeAlters(List<Alter> alters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SiteOnItem> getSiteOnItemsByItem(final int itemId) {
        final List<SiteOnItem> result = new ArrayList<SiteOnItem>();
        for (final SiteOnItem siteOnItem : siteOnItems) {
            if (siteOnItem.getItem().getId() == itemId) {
                result.add((SiteOnItem) siteOnItem);
            }
        }
        return result;
    }

    @Override
    public List<SiteOnItem> getSiteOnItemsBySite(final int siteId) {
        final List<SiteOnItem> result = new ArrayList<SiteOnItem>();
        for (final SiteOnItem siteOnItem : siteOnItems) {
            if (siteOnItem.getSite().getSiteId() == siteId) {
                result.add((SiteOnItem) siteOnItem);
            }
        }
        return result;
    }

    @Override
    public <T extends WorkItem> T getWorkItem(final Integer workSiteItemId) {
        for (WorkItem workItem : workItems) {
            if (workItem.getId() == workSiteItemId) {
                return (T) workItem;
            }
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

    }

    @Override
    public void removeWorkFilters(Integer formId) {

    }

    @Override
    public void putWorkFilter(WorkFormFilter workFilter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putWorkFormItem(WorkFormItem workFormItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeWorkGalleryItems(Integer itemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeWorkGalleryLabels(Integer itemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putWorkGalleryItem(WorkGalleryItem workItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putWorkGalleryLabel(WorkGalleryLabel workItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeDraftItemCssValues(Integer draftItemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeWidgetCssValues(Integer widgetId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeWorkCssValues(Integer workItemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Integer> getDraftItemIds() {
        throw new UnsupportedOperationException("Only for use in StartUpdate!");
    }

    @Override
    public <T extends DraftItem> Map<T, List<WidgetItem>> getItemsBySiteAndUser(
            final int userId, int siteId, final ItemType type, final boolean onlyCurrentSite) {
        final Map<T, List<WidgetItem>> result = new HashMap<T, List<WidgetItem>>();

        final Set<Integer> siteIds = getAvailableSiteIdsForUser(userId);
        final List<DraftItem> items = new ArrayList<DraftItem>();

        for (SiteOnItem siteOnItem : siteOnItems) {
            if (siteIds.contains(siteOnItem.getId().getSite().getSiteId())) {
                items.add(siteOnItem.getItem());
            }
        }

        for (DraftItem draftItem : draftItems) {
            if (siteIds.contains(draftItem.getSiteId())) {
                items.add(draftItem);
            }
        }

        List<Widget> widgets = getWidgetsBySitesId(new ArrayList<Integer>(siteIds), SiteShowOption.getDraftOption());
        for (DraftItem draftItem : items) {
            if (draftItem.getItemType() == type) {
                final List<WidgetItem> widgetItems = new ArrayList<WidgetItem>();
                for (final Widget widget : widgets) {
                    if (widget.isWidgetItem()) {
                        final WidgetItem widgetItem = (WidgetItem) widget;
                        if (widgetItem.getDraftItem() != null && widgetItem.getDraftItem().getId() == draftItem.getId()) {
                            widgetItems.add(widgetItem);
                        }
                    }
                }

                result.put((T) draftItem, widgetItems);
            }
        }

        return result;
    }

    public void putGroup(Group group) {
        group.setGroupId(0);
        for (Group tempGroup : groups) {
            if (group.getGroupId() < tempGroup.getGroupId()) {
                group.setGroupId(tempGroup.getGroupId());
            }
        }
        group.setGroupId(group.getGroupId() + 1);
        groups.add(group);
    }

    public void removeGroup(Group group) {
        if (group == null) {
            return;
        }
        group.getOwner().removeGroup(group);
        for (User user : getUsersWithAccessToGroup(group.getGroupId())) {
            new UsersGroupManager(user).removeAccessToGroup(group.getGroupId());
        }
        for (final DraftItem item : draftItems) {
            if (item.getItemType() == ItemType.REGISTRATION) {
//                ((DraftRegistrationForm) item).removeGroupId(group.getGroupId());
            }
        }
        groups.remove(group);
    }

    public void removeGroup(int groupId) {
        removeGroup(getGroup(groupId));
    }

    public Group getGroup(Integer groupId) {
        if (groupId == null) {
            return null;
        }
        for (Group group : groups) {
            if (group.getGroupId() == groupId) {
                return group;
            }
        }
        return null;
    }

    public List<Group> getGroups(List<Integer> groupIds) {
        if (groupIds.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Group> groups = new ArrayList<Group>();
        for (Group group : this.groups) {
            if (groupIds.contains(group.getGroupId())) {
                groups.add(group);
            }
        }
        return groups;
    }

    public List<User> getUsersWithAccessToGroup(int groupId) {
        final List<User> users = new ArrayList<User>();
        for (User user : this.users) {
            if (new UsersGroupManager(user).hasAccessToGroup(groupId)) {
                users.add(user);
            }
        }
        return users;
    }

    public List<User> getUsersByUsersId(List<Integer> usersId) {
        final List<User> users = new ArrayList<User>();
        for (User user : this.users) {
            if (usersId.contains(user.getUserId())) {
                users.add(user);
            }
        }
        return users;
    }

    public int getUsersCountWithAccessToGroup(Integer groupId) {
        return getUsersWithAccessToGroup(groupId).size();
    }

    @Override
    public void removeDraftItem(final Integer draftItemId) {
        throw new UnsupportedOperationException("Use only in StartUpdate");
    }

    @Override
    public void removeWorkAdvancedSearchOptions(Integer itemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putWorkAdvancedSearchOption(WorkAdvancedSearchOption workAdvancedSearchOption) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeWorkManageVotesSettings(Integer itemId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putWorkManageVotesSettings(WorkManageVotesSettings workManageVotesSettings) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public AccessibleForRender getAccessibleElement(Integer id, AccessibleElementType type) {
        if (id == null || type == null) {
            return null;
        }
        switch (type) {
            case SITE: {
                return getSite(id);
            }
            case PAGE: {
                return new PageManager(getPage(id));
            }
            case WIDGET: {
                return new WidgetManager(getWidget(id));
            }
            case ITEM: {
                return new ItemManager(id);
            }
            default: {
                throw new IllegalArgumentException("Unknown accessible for render type!");
            }
        }
    }

    public void putAccessibleSettings(AccessibleSettings accessibleSettings) {
        accessibleSettings.setAccessibleSettingsId(0);
        for (AccessibleSettings tempSettings : this.accessibleSettings) {
            if (accessibleSettings.getAccessibleSettingsId() < tempSettings.getAccessibleSettingsId()) {
                accessibleSettings.setAccessibleSettingsId(tempSettings.getAccessibleSettingsId());
            }
        }
        accessibleSettings.setAccessibleSettingsId(accessibleSettings.getAccessibleSettingsId() + 1);
        this.accessibleSettings.add(accessibleSettings);
    }

    @Override
    public AccessibleSettings getAccessibleSettings(Integer accessibleSettingsId) {
        if (accessibleSettingsId == null) {
            return null;
        }
        for (AccessibleSettings accessibleSettings : this.accessibleSettings) {
            if (accessibleSettings.getAccessibleSettingsId() == accessibleSettingsId) {
                return accessibleSettings;
            }
        }
        return null;
    }

    @Override
    public void removeWorkItem(final WorkItem workItem) {
        workItems.remove(workItem);
    }

    @Override
    public void removeWorkItem(Integer workItemId) {
        workItems.remove(getWorkItem(workItemId));
    }

    @Override
    public PurchaseMailLog getPurchaseMailLog(Integer purchaseMailLogId) {
        if (purchaseMailLogId == null) {
            return null;
        }
        for (PurchaseMailLog log : purchaseMailLogs) {
            if (log.getId() == purchaseMailLogId) {
                return log;
            }
        }
        return null;
    }

    @Override
    public void putPurchaseMailLog(PurchaseMailLog purchaseMailLog) {
        purchaseMailLog.setId(0);
        for (PurchaseMailLog tempLog : this.purchaseMailLogs) {
            if (purchaseMailLog.getId() < tempLog.getId()) {
                purchaseMailLog.setId(tempLog.getId());
            }
        }
        purchaseMailLog.setId(purchaseMailLog.getId() + 1);
        this.purchaseMailLogs.add(purchaseMailLog);
    }

    public List<PurchaseMailLog> getAllPurchaseMailLogs() {
        return purchaseMailLogs;
    }


    @Override
    public List<FormFileShouldBeCopied> getAllFormFileShouldBeCopied() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<VideoShouldBeCopied> getAllVideoShouldBeCopied() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putVideoShouldBeCopied(VideoShouldBeCopied videoShouldBeCopied) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putFormFileShouldBeCopied(FormFileShouldBeCopied formFileShouldBeCopied) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UsersGroup getUsersGroup(UsersGroupId usersGroupId) {
        for (UsersGroup usersGroup : this.usersGroups) {
            if (usersGroup.getId().equals(usersGroupId)) {
                return usersGroup;
            }
        }
        return null;
    }

    @Override
    public void putUsersGroup(UsersGroup usersGroup) {
        usersGroups.add(usersGroup);
    }

    @Override
    public void removeUsersGroup(UsersGroup usersGroup) {
        new UsersGroupManager(usersGroup.getId().getUser()).removeAccessToGroup(usersGroup.getGroupId());
        usersGroups.remove(usersGroup);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Map<DraftItem, List<WidgetItem>> getItems(List<Integer> siteIds, ItemType itemType, SiteShowOption siteShowOption) {
        final Set<Integer> siteIdsSet = new HashSet<Integer>(siteIds);
        final List<Widget> widgets = getWidgetsBySitesId(siteIds, siteShowOption);
        final Map<DraftItem, List<WidgetItem>> result = new HashMap<DraftItem, List<WidgetItem>>();
        for (final DraftItem draftItem : draftItems) {
            if (draftItem.getItemType() == itemType && siteIdsSet.contains(draftItem.getSiteId())) {
                final List<WidgetItem> widgetItems = new ArrayList<WidgetItem>();
                for (final Widget widget : widgets) {
                    final int siteId = widget.getSite().getSiteId();
                    if (widget.isWidgetItem() && siteIdsSet.contains(siteId)) {
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

    @Override
    public void putPageSettings(PageSettings pageSettings) {
        if (pageSettings instanceof DraftPageSettings) {
            pageSettings.setPageSettingsId(0);
            for (PageSettings tempPageSettings : this.draftPageSettings) {
                if (pageSettings.getPageSettingsId() < tempPageSettings.getPageSettingsId()) {
                    pageSettings.setPageSettingsId(tempPageSettings.getPageSettingsId());
                }
            }
            pageSettings.setPageSettingsId(pageSettings.getPageSettingsId() + 1);
            this.draftPageSettings.add((DraftPageSettings) pageSettings);
        } else {
            this.workPageSettings.add((WorkPageSettings) pageSettings);
        }
    }

    @Override
    public DraftPageSettings getDraftPageSettings(Integer pageSettingsId) {
        for (DraftPageSettings draftPageSettings : this.draftPageSettings) {
            if (draftPageSettings.getPageSettingsId() == pageSettingsId) {
                return draftPageSettings;
            }
        }
        return null;
    }

    @Override
    public WorkPageSettings getWorkPageSettings(Integer pageSettingsId) {
        for (WorkPageSettings workPageSettings : this.workPageSettings) {
            if (workPageSettings.getPageSettingsId() == pageSettingsId) {
                return workPageSettings;
            }
        }
        return null;
    }

    @Override
    public void removePageSettings(PageSettings pageSettings) {
        if (pageSettings == null) {
            return;
        }
        if (pageSettings instanceof DraftPageSettings) {
            pageSettings.getPage().setPageSettings(null);
            draftPageSettings.remove(pageSettings);
        } else {
            workPageSettings.remove((WorkPageSettings) pageSettings);
        }
    }

    @Override
    public List<Widget> getWidgets(List<Integer> widgetsId) {
        final List<Widget> widgets = new ArrayList<Widget>();
        for (Integer widgetId : widgetsId) {
            final Widget widget = getWidget(widgetId);
            if (widget != null) {
                widgets.add(widget);
            }
        }
        return widgets;
    }

    @Override
    public List<KeywordsGroup> getKeywordsGroups(List<Integer> keywordsGroupsId) {
        final List<KeywordsGroup> keywordsGroups = new ArrayList<KeywordsGroup>();
        for (Integer keywordsGroupId : keywordsGroupsId) {
            final KeywordsGroup keywordsGroup = getKeywordsGroupById(keywordsGroupId);
            if (keywordsGroup != null) {
                keywordsGroups.add(keywordsGroup);
            }
        }
        return keywordsGroups;
    }

    @Override
    public List<SiteTitlePageName> getWorkSiteTitlePageNames(List<Integer> siteIds) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public List<SiteTitlePageName> getSiteTitlePageNamesByUserId(int userId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<SiteTitlePageName> getSiteTitlePageNamesBySiteId(int siteId) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<FlvVideo> getAllFlvVideo() {
        return flvVideos;
    }

    public void setImagesKeywords(final int siteId, final List<String> keywords) {
        imagesKeywordsBySites.put(siteId, keywords);
    }

    public void setKeywordImages(final int siteId, final String keyword, final List<Image> images) {
        imagesByKeywords.put(siteId + keyword, images);
    }

    private final List<DraftPageSettings> draftPageSettings = new ArrayList<DraftPageSettings>();
    private final List<WorkPageSettings> workPageSettings = new ArrayList<WorkPageSettings>();
    private final List<UsersGroup> usersGroups = new ArrayList<UsersGroup>();
    private final List<PurchaseMailLog> purchaseMailLogs = new ArrayList<PurchaseMailLog>();
    private final List<AccessibleSettings> accessibleSettings = new ArrayList<AccessibleSettings>();
    private List<Group> groups = new ArrayList<Group>();
    private final List<Coordinate> coordinates = new ArrayList<Coordinate>();
    private final List<DraftItem> draftItems = new ArrayList<DraftItem>();
    private final List<WorkItem> workItems = new ArrayList<WorkItem>();
    private final List<GoogleBaseDataExportMappedByFilledFormId> dataExportMappedByFilledFormIds = new ArrayList<GoogleBaseDataExportMappedByFilledFormId>();
    private final List<FormVideo> formVideos = new ArrayList<FormVideo>();
    private boolean inContext = false;
    private final List<Page> pages = new ArrayList<Page>();
    private final List<Image> images = new ArrayList<Image>();
    private final List<Vote> votes = new ArrayList<Vote>();
    private final List<BackgroundImage> backgroundImages = new ArrayList<BackgroundImage>();
    private final List<MenuImage> menuImages = new ArrayList<MenuImage>();
    private final List<ImageForVideo> imagesForVideo = new ArrayList<ImageForVideo>();
    private final List<FormFile> formFiles = new ArrayList<FormFile>();
    private final List<ImageFile> imageFiles = new ArrayList<ImageFile>();
    private final List<KeywordsGroup> keywordsGroups = new ArrayList<KeywordsGroup>();
    private final List<Style> styles = new ArrayList<Style>();
    private final List<Site> sites = new ArrayList<Site>();
    private final List<User> users = new ArrayList<User>();
    private final List<ForumPost> forumPosts = new ArrayList<ForumPost>();
    private final List<ForumThread> threads = new ArrayList<ForumThread>();
    private final List<SubForum> subForums = new ArrayList<SubForum>();
    private final List<Widget> widgets = new ArrayList<Widget>();
    private final List<CreditCard> creditCards = new ArrayList<CreditCard>();
    private final List<Comment> comments = new ArrayList<Comment>();
    private final List<UserOnSiteRight> userOnSiteRights = new ArrayList<UserOnSiteRight>();
    private final List<BlogPost> blogPosts = new ArrayList<BlogPost>();
    private final List<Video> videos = new ArrayList<Video>();
    private final List<FlvVideo> flvVideos = new ArrayList<FlvVideo>();
    private final List<ForumPollVote> forumVotes = new ArrayList<ForumPollVote>();
    private final List<Content> contents = new ArrayList<Content>();
    private final List<EmailUpdateRequest> emailUpdateRequests = new ArrayList<EmailUpdateRequest>();
    private final List<Visit> visits = new ArrayList<Visit>();
    private final List<IncomeSettings> incomeSettings = new ArrayList<IncomeSettings>();
    private final List<PageVisitor> pageVisitors = new ArrayList<PageVisitor>();
    private final List<PersistanceListener> removeListeners = new ArrayList<PersistanceListener>();
    private final List<PersistanceListener> updateListeners = new ArrayList<PersistanceListener>();
    private final List<JournalItem> journalItems = new ArrayList<JournalItem>();
    private final List<SiteOnItem> siteOnItems = new ArrayList<SiteOnItem>();
    private final List<ChildSiteSettings> childSiteSettings = new ArrayList<ChildSiteSettings>();
    private final List<FilledForm> filledForms = new ArrayList<FilledForm>();
    private final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
    private final List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();
    private final List<DraftFormFilterRule> formFilterRules = new ArrayList<DraftFormFilterRule>();
    private final List<SitePaymentSettings> sitePaymentSettings = new ArrayList<SitePaymentSettings>();
    private final List<GalleryComment> galleryComments = new ArrayList<GalleryComment>();
    private final List<DraftManageVotesSettings> manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>();
    private final List<DraftFormFilter> formFilters = new ArrayList<DraftFormFilter>();
    private final List<PaymentLog> paymentLogs = new ArrayList<PaymentLog>();
    private final List<VisitReferrer> visitReferrers = new ArrayList<VisitReferrer>();
    private final List<DraftAdvancedSearchOption> advancedSearchOptions = new ArrayList<DraftAdvancedSearchOption>();
    private List<GalleryVideoRange> galleryVideoRanges = new ArrayList<GalleryVideoRange>();
    private final List<DraftMenuItem> menuItems = new ArrayList<DraftMenuItem>();
    private final List<WorkMenuItem> workMenuItems = new ArrayList<WorkMenuItem>();
    private final List<IPNLog> ipnLogs = new ArrayList<IPNLog>();
    private final List<Icon> icons = new ArrayList<Icon>();
    private final List<CSVDataExport> CSVDataExports = new ArrayList<CSVDataExport>();
    private final List<CSVDataExportField> CSVDataExportFields = new ArrayList<CSVDataExportField>();
    private final List<CustomizeManageRecords> customizeManageRecords = new ArrayList<CustomizeManageRecords>();
    private final List<CustomizeManageRecordsField> customizeManageRecordsFields = new ArrayList<CustomizeManageRecordsField>();
    private final List<TaxRateUS> taxRates = new ArrayList<TaxRateUS>();
    private final List<ItemSize> itemSizes = new ArrayList<ItemSize>();
    private final List<FontsAndColors> fontsAndColorsList = new ArrayList<FontsAndColors>();
    private final List<Background> backgrounds = new ArrayList<Background>();
    private final List<Border> borders = new ArrayList<Border>();
    private final List<FormExportTask> formExportTasks = new ArrayList<FormExportTask>();
    private final List<DraftSlideShowImage> slideShowImages = new ArrayList<DraftSlideShowImage>();
    private final List<WorkSlideShowImage> workSlideShowImages = new ArrayList<WorkSlideShowImage>();
    private final Map<String, Site> siteByNetworkSubDomains = new HashMap<String, Site>();
    private final Map<Integer, List<String>> imagesKeywordsBySites = new HashMap<Integer, List<String>>();
    private final Map<String, List<Image>> imagesByKeywords = new HashMap<String, List<Image>>();
    private final List<Site> publishedBlueprints = new ArrayList<Site>();
    private final List<String> blueprintKeywords = new ArrayList<String>();

}
