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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounter;

import java.util.*;

/**
 * @author Stasuk Artem
 */
public class PersistanceTimeCounter implements Persistance {

    public PersistanceTimeCounter(final Persistance persistance) {
        this.persistance = persistance;
    }

    public void putPageVisitor(PageVisitor pageVisitor) {
        final TimeCounter timeCounter = createTimeCounter("putPageVisitor");
        try {
            persistance.putPageVisitor(pageVisitor);
        } finally {
            timeCounter.stop();
        }
    }

    public void putAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption) {
        final TimeCounter timeCounter = createTimeCounter("putAdvancedSearchOption");
        try {
            persistance.putAdvancedSearchOption(advancedSearchOption);
        } finally {
            timeCounter.stop();
        }
    }

    public void putVisit(Visit visit) {
        final TimeCounter timeCounter = createTimeCounter("putVisit");
        try {
            persistance.putVisit(visit);
        } finally {
            timeCounter.stop();
        }
    }

    public void putIPNLog(IPNLog ipnLog) {
        final TimeCounter timeCounter = createTimeCounter("putIPNLog");
        try {
            persistance.putIPNLog(ipnLog);
        } finally {
            timeCounter.stop();
        }
    }

    public void putVisitReferrer(VisitReferrer visitReferrer) {
        final TimeCounter timeCounter = createTimeCounter("putVisitReferrer");
        try {
            persistance.putVisitReferrer(visitReferrer);
        } finally {
            timeCounter.stop();
        }
    }

    public void putIncomeSettings(IncomeSettings incomeSettings) {
        final TimeCounter timeCounter = createTimeCounter("putIncomeSettings");
        try {
            persistance.putIncomeSettings(incomeSettings);
        } finally {
            timeCounter.stop();
        }
    }

    public void putSitePaymentSettings(SitePaymentSettings sitePaymentSettings) {
        final TimeCounter timeCounter = createTimeCounter("putSitePaymentSettings");
        try {
            persistance.putSitePaymentSettings(sitePaymentSettings);
        } finally {
            timeCounter.stop();
        }
    }

    public SitePaymentSettings getSitePaymentSettingsById(int sitePaymentSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getSitePaymentSettingsById");
        try {
            return persistance.getSitePaymentSettingsById(sitePaymentSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    public IncomeSettings getIncomeSettingsById(int incomeSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getIncomeSettingsById");
        try {
            return persistance.getIncomeSettingsById(incomeSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftFormItem getFormItemById(Integer formItemId) {
        final TimeCounter timeCounter = createTimeCounter("getFormItemById");
        try {
            return persistance.getFormItemById(formItemId);
        } finally {
            timeCounter.stop();
        }
    }

    public boolean isRegistrationUsedOnAnySiteAsDefault(Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("isRegistrationUsedOnAnySiteAsDefault");
        try {
            return persistance.isRegistrationUsedOnAnySiteAsDefault(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putCreditCard(CreditCard creditCard) {
        final TimeCounter timeCounter = createTimeCounter("putCreditCard");
        try {
            persistance.putCreditCard(creditCard);
        } finally {
            timeCounter.stop();
        }
    }

    public void putManageVotesGallerySettings(DraftManageVotesSettings manageVotesGallerySettings) {
        final TimeCounter timeCounter = createTimeCounter("putManageVotesGallerySettings");
        try {
            persistance.putManageVotesGallerySettings(manageVotesGallerySettings);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFormFilterRule(DraftFormFilterRule formFilterRule) {
        final TimeCounter timeCounter = createTimeCounter("putFormFilterRule");
        try {
            persistance.putFormFilterRule(formFilterRule);
        } finally {
            timeCounter.stop();
        }
    }

    public void putMenu(Menu menu) {
        final TimeCounter timeCounter = createTimeCounter("putMenu");
        try {
            persistance.putMenu(menu);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFormFilter(DraftFormFilter formFilter) {
        final TimeCounter timeCounter = createTimeCounter("putFormFilter");
        try {
            persistance.putFormFilter(formFilter);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<BlogPost> getBlogPostsByBlog(int blogId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostsByBlog");
        try {
            return persistance.getBlogPostsByBlog(blogId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public int getBlogPostsCountByBlog(int blogId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostsCountByBlog");
        try {
            return persistance.getBlogPostsCountByBlog(blogId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, Integer> getBlogPostsCountByBlogs(Set<Integer> blogsId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostsCountByBlogs");
        try {
            return persistance.getBlogPostsCountByBlogs(blogsId);
        } finally {
            timeCounter.stop();
        }
    }

    public long getHitsForPages(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getHitsForPages");
        try {
            return persistance.getHitsForPages(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public Map<Integer, Long> getUniqueVisitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getUniqueVisitsForPagesSeparately");
        try {
            return persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public Map<Integer, Long> getHitsForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getHitsForPagesSeparately");
        try {
            return persistance.getHitsForPagesSeparately(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public long getUniqueVisitsCountForPages(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getUniqueVisitsCountForPages");
        try {
            return persistance.getUniqueVisitsCountForPages(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public Map<String, Integer> getRefUrlsByPages(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getRefUrlsByPages");
        try {
            return persistance.getRefUrlsByPages(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public Map<String, Integer> getRefSearchTermsByPages(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getRefSearchTermsByPages");
        try {
            return persistance.getRefSearchTermsByPages(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public long getOverallTimeForPages(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getOverallTimeForPages");
        try {
            return persistance.getOverallTimeForPages(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public Map<Integer, Long> getOverallTimeForPagesSeparately(List<Integer> pageIds, DateInterval dateInterval) {
        final TimeCounter timeCounter = createTimeCounter("getOverallTimeForPagesSeparately");
        try {
            return persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval);
        } finally {
            timeCounter.stop();
        }
    }

    public PageVisitor getPageVisitorById(Integer pageVisitorId) {
        final TimeCounter timeCounter = createTimeCounter("getPageVisitorById");
        try {
            return persistance.getPageVisitorById(pageVisitorId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftAdvancedSearchOption getAdvancedSearchOptionById(int advancedSearchOptionId) {
        final TimeCounter timeCounter = createTimeCounter("getAdvancedSearchOptionById");
        try {
            return persistance.getAdvancedSearchOptionById(advancedSearchOptionId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<PageVisitor> getPageVisitorsByUserId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getPageVisitorsByUserId");
        try {
            return persistance.getPageVisitorsByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftForm getFormById(Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("getFormById");
        try {
            return persistance.getFormById(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftFormFilterRule getFormFilterRuleById(int formFilterRuleId) {
        final TimeCounter timeCounter = createTimeCounter("getFormFilterRuleById");
        try {
            return persistance.getFormFilterRuleById(formFilterRuleId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftFormFilter getFormFilterById(Integer filterId) {
        final TimeCounter timeCounter = createTimeCounter("getFormFilterById");
        try {
            return persistance.getFormFilterById(filterId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftFormFilter getFormFilterByNameAndUserId(String name, int userId) {
        final TimeCounter timeCounter = createTimeCounter("getFormFilterByNameAndUserId");
        try {
            return persistance.getFormFilterByNameAndUserId(name, userId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftFormFilter getFormFilterByNameAndFormId(String name, int formId) {
        final TimeCounter timeCounter = createTimeCounter("getFormFilterByNameAndFormId");
        try {
            return persistance.getFormFilterByNameAndFormId(name, formId);
        } finally {
            timeCounter.stop();
        }
    }

    public Visit getVisitByPageIdAndUserId(int pageId, int userId) {
        final TimeCounter timeCounter = createTimeCounter("getVisitByPageIdAndUserId");
        try {
            return persistance.getVisitByPageIdAndUserId(pageId, userId);
        } finally {
            timeCounter.stop();
        }
    }

    public User getUserByEmail(String email) {
        final TimeCounter timeCounter = createTimeCounter("getUserByEmail");
        try {
            return persistance.getUserByEmail(email);
        } finally {
            timeCounter.stop();
        }
    }

    public List<DraftFormFilter> getFormFiltersByUserId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getFormFiltersByUserId");
        try {
            return persistance.getFormFiltersByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Site> searchSites(String siteTitle, String siteUrlPrefix) {
        final TimeCounter timeCounter = createTimeCounter("searchSites");
        try {
            return persistance.searchSites(siteTitle, siteUrlPrefix);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FilledForm> getFilledFormsByFormId(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormsByFormId");
        try {
            return persistance.getFilledFormsByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public int getFilledFormsCountByFormId(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormsCountByFormId");
        try {
            return persistance.getFilledFormsCountByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, Integer> getFilledFormsCountByFormsId(Set<Integer> formsId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormsCountByFormsId");
        try {
            return persistance.getFilledFormsCountByFormsId(formsId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FilledForm> getFilledFormsByFormAndUserId(int formId, int userId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormsByFormAndUserId");
        try {
            return persistance.getFilledFormsByFormAndUserId(formId, userId);
        } finally {
            timeCounter.stop();
        }
    }

    public long getFilledFormsNumberByFormId(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormsNumberByFormId");
        try {
            return persistance.getFilledFormsNumberByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public Integer getMaxFilledFormIdByFormId(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getMaxFilledFormIdByFormId");
        try {
            return persistance.getMaxFilledFormIdByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putEmailUpdateRequest(EmailUpdateRequest emailUpdateRequest) {
        final TimeCounter timeCounter = createTimeCounter("putEmailUpdateRequest");
        try {
            persistance.putEmailUpdateRequest(emailUpdateRequest);
        } finally {
            timeCounter.stop();
        }
    }

    public EmailUpdateRequest getEmailUpdateRequestById(String emailUpdateRequestId) {
        final TimeCounter timeCounter = createTimeCounter("getEmailUpdateRequestById");
        try {
            return persistance.getEmailUpdateRequestById(emailUpdateRequestId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putUser(User user) {
        final TimeCounter timeCounter = createTimeCounter("putUser");
        try {
            persistance.putUser(user);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFormItem(DraftFormItem formItem) {
        final TimeCounter timeCounter = createTimeCounter("putFormItem");
        try {
            persistance.putFormItem(formItem);
        } finally {
            timeCounter.stop();
        }
    }

    public void putSlideShowImage(DraftSlideShowImage slideShowImage) {
        final TimeCounter timeCounter = createTimeCounter("putSlideShowImage");
        try {
            persistance.putSlideShowImage(slideShowImage);
        } finally {
            timeCounter.stop();
        }
    }

    public void putWorkSlideShowImage(WorkSlideShowImage slideShowImage) {
        final TimeCounter timeCounter = createTimeCounter("putWorkSlideShowImage");
        try {
            persistance.putWorkSlideShowImage(slideShowImage);
        } finally {
            timeCounter.stop();
        }
    }

    public FilledForm getFilledFormById(Integer filledFormId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormById");
        try {
            return persistance.getFilledFormById(filledFormId);
        } finally {
            timeCounter.stop();
        }
    }

    public FilledFormItem getFilledFormItemById(Integer filledFormItemId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormItemById");
        try {
            return persistance.getFilledFormItemById(filledFormItemId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FilledFormItem> getFilledFormItemByFormItemId(Integer formItemId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledFormItemByFormItemId");
        try {
            return persistance.getFilledFormItemByFormItemId(formItemId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFilledForm(FilledForm filledForm) {
        final TimeCounter timeCounter = createTimeCounter("putFilledForm");
        try {
            persistance.putFilledForm(filledForm);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFilledFormItem(FilledFormItem filledFormItem) {
        final TimeCounter timeCounter = createTimeCounter("putFilledFormItem");
        try {
            persistance.putFilledFormItem(filledFormItem);
        } finally {
            timeCounter.stop();
        }
    }

    public Date getLastFillFormDateByFormId(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getLastFillFormDateByFormId");
        try {
            return persistance.getLastFillFormDateByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, Date> getLastFillFormDateByFormsId(Set<Integer> blogsId) {
        final TimeCounter timeCounter = createTimeCounter("getLastFillFormDateByFormsId");
        try {
            return persistance.getLastFillFormDateByFormsId(blogsId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putRegistrationForm(DraftRegistrationForm registrationForm) {
        final TimeCounter timeCounter = createTimeCounter("putRegistrationForm");
        try {
            persistance.putRegistrationForm(registrationForm);
        } finally {
            timeCounter.stop();
        }
    }

    public void putCustomForm(DraftCustomForm customForm) {
        final TimeCounter timeCounter = createTimeCounter("putCustomForm");
        try {
            persistance.putCustomForm(customForm);
        } finally {
            timeCounter.stop();
        }
    }

    public void putSubForum(SubForum subForum) {
        final TimeCounter timeCounter = createTimeCounter("putSubForum");
        try {
            persistance.putSubForum(subForum);
        } finally {
            timeCounter.stop();
        }
    }

    public void putForumThread(ForumThread forumThread) {
        final TimeCounter timeCounter = createTimeCounter("putForumThread");
        try {
            persistance.putForumThread(forumThread);
        } finally {
            timeCounter.stop();
        }
    }

    public void putForumPost(ForumPost forumPost) {
        final TimeCounter timeCounter = createTimeCounter("putForumPost");
        try {
            persistance.putForumPost(forumPost);
        } finally {
            timeCounter.stop();
        }
    }

    public User getUserById(Integer userId) {
        final TimeCounter timeCounter = createTimeCounter("getUserById");
        try {
            return persistance.getUserById(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public UserOnSiteRight getUserOnSiteRightById(UserOnSiteRightId userOnSiteRightId) {
        final TimeCounter timeCounter = createTimeCounter("getUserOnSiteRightById");
        try {
            return persistance.getUserOnSiteRightById(userOnSiteRightId);
        } finally {
            timeCounter.stop();
        }
    }

    public UserOnSiteRight getUserOnSiteRightByUserAndSiteId(Integer userId, Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getUserOnSiteRightByUserAndSiteId");
        try {
            return persistance.getUserOnSiteRightByUserAndSiteId(userId, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public UserOnSiteRight getUserOnSiteRightByUserAndFormId(final User user, final Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("getUserOnSiteRightByUserAndFormId");
        try {
            return persistance.getUserOnSiteRightByUserAndFormId(user, formId);
        } finally {
            timeCounter.stop();
        }
    }

    public FilledForm getFilledRegistrationFormByUserAndFormId(final User user, final Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("getFilledRegistrationFormByUserAndFormId");
        try {
            return persistance.getFilledRegistrationFormByUserAndFormId(user, formId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public ForumPost getForumPostById(int postId) {
        final TimeCounter timeCounter = createTimeCounter("getForumPostById");
        try {
            return persistance.getForumPostById(postId);
        } finally {
            timeCounter.stop();
        }
    }

    public Site getSiteBySubDomain(String subDomain) {
        final TimeCounter timeCounter = createTimeCounter("getSiteBySubDomain");
        try {
            return persistance.getSiteBySubDomain(subDomain);
        } finally {
            timeCounter.stop();
        }
    }

    public void putSite(Site site) {
        final TimeCounter timeCounter = createTimeCounter("putSite");
        try {
            persistance.putSite(site);
        } finally {
            timeCounter.stop();
        }
    }

    public int getThreadVotesCount(int threadId, int answerNumber) {
        final TimeCounter timeCounter = createTimeCounter("getThreadVotesCount");
        try {
            return persistance.getThreadVotesCount(threadId, answerNumber);
        } finally {
            timeCounter.stop();
        }
    }

    public List<User> getVisitorsBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getVisitorsBySiteId");
        try {
            return persistance.getVisitorsBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public BlogPost getLastBlogPost(int blogId) {
        final TimeCounter timeCounter = createTimeCounter("getLastBlogPost");
        try {
            return persistance.getLastBlogPost(blogId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, Date> getLastBlogPostsDate(Set<Integer> blogsId) {
        final TimeCounter timeCounter = createTimeCounter("getLastBlogPostsDate");
        try {
            return persistance.getLastBlogPostsDate(blogsId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<BlogPost> getBlogPosts(int blogId, Integer userId, Integer blogPostId, int start, int count, Date notOlderThan) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostsByBlogAndUserId");
        try {
            return persistance.getBlogPosts(blogId, userId, blogPostId, start, count, notOlderThan);
        } finally {
            timeCounter.stop();
        }
    }

    public int getBlogPostsBeforeByBlogAndUserId(int blogId, Integer userId, int blogPostId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostsBeforeByBlogAndUserId");
        try {
            return persistance.getBlogPostsBeforeByBlogAndUserId(blogId, userId, blogPostId);
        } finally {
            timeCounter.stop();
        }
    }

    public int getPrevOrNextBlogPostId(int blogId, Integer userId, int blogPostId, boolean prev) {
        final TimeCounter timeCounter = createTimeCounter("getPrevOrNextBlogPostId");
        try {
            return persistance.getPrevOrNextBlogPostId(blogId, userId, blogPostId, prev);
        } finally {
            timeCounter.stop();
        }
    }

    public int getBlogPostsAfterByBlogAndUserId(int blogId, Integer userId, int blogPostId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostsAfterByBlogAndUserId");
        try {
            return persistance.getBlogPostsAfterByBlogAndUserId(blogId, userId, blogPostId);
        } finally {
            timeCounter.stop();
        }
    }

    public CreditCard getCreditCardById(Integer creditCardId) {
        if (creditCardId == null) {
            return null;
        }
        final TimeCounter timeCounter = createTimeCounter("getCreditCardById");
        try {
            return persistance.getCreditCardById(creditCardId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftContactUs getContactUsById(int contactUsId) {
        final TimeCounter timeCounter = createTimeCounter("getContactUsById");
        try {
            return persistance.getContactUsById(contactUsId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftContactUs getContactUsByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getContactUsByNameAndSiteId");
        try {
            return persistance.getContactUsByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public void putContactUs(DraftContactUs contactUs) {
        final TimeCounter timeCounter = createTimeCounter("putContactUs");
        try {
            persistance.putContactUs(contactUs);
        } finally {
            timeCounter.stop();
        }
    }


    public void putBlogPost(BlogPost blogPost) {
        final TimeCounter timeCounter = createTimeCounter("putBlogPost");
        try {
            persistance.putBlogPost(blogPost);
        } finally {
            timeCounter.stop();
        }
    }


    public BlogPost getBlogPostById(int blogPostId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogPostById");
        try {
            return persistance.getBlogPostById(blogPostId);
        } finally {
            timeCounter.stop();
        }
    }


    public Comment getCommentById(int commentId) {
        final TimeCounter timeCounter = createTimeCounter("getCommentById");
        try {
            return persistance.getCommentById(commentId);
        } finally {
            timeCounter.stop();
        }
    }


    public void putComment(Comment comment) {
        final TimeCounter timeCounter = createTimeCounter("putComment");
        try {
            persistance.putComment(comment);
        } finally {
            timeCounter.stop();
        }
    }


    public List<SubForum> getSubForumsByForumId(int forumId) {
        final TimeCounter timeCounter = createTimeCounter("getSubForumsByForumId");
        try {
            return persistance.getSubForumsByForumId(forumId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public int getSubForumsCountByForumId(int forumId) {
        final TimeCounter timeCounter = createTimeCounter("getSubForumsCountByForumId");
        try {
            return persistance.getSubForumsCountByForumId(forumId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, Integer> getSubForumsCountByForumsId(Set<Integer> forumsId) {
        final TimeCounter timeCounter = createTimeCounter("getSubForumsCountByForumsId");
        try {
            return persistance.getSubForumsCountByForumsId(forumsId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<ForumThread> getForumThreads(int subForumId) {
        final TimeCounter timeCounter = createTimeCounter("getForumThreads");
        try {
            return persistance.getForumThreads(subForumId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<ForumThread> getForumThreadsByUserId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getForumThreadsByUserId");
        try {
            return persistance.getForumThreadsByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<ForumPost> getForumPostsByUserId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getForumPostsByUserId");
        try {
            return persistance.getForumPostsByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<ForumPost> getForumPosts(int forumThreadId) {
        final TimeCounter timeCounter = createTimeCounter("getForumPosts");
        try {
            return persistance.getForumPosts(forumThreadId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putVote(ForumPollVote forumPollVote) {
        final TimeCounter timeCounter = createTimeCounter("putVote");
        try {
            persistance.putVote(forumPollVote);
        } finally {
            timeCounter.stop();
        }
    }

    public ForumPollVote getForumThreadVoteByRespondentIdAndThreadId(String respondentId, int threadId) {
        final TimeCounter timeCounter = createTimeCounter("getForumThreadVoteByRespondentIdAndThreadId");
        try {
            return persistance.getForumThreadVoteByRespondentIdAndThreadId(respondentId, threadId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putWidget(Widget widget) {
        final TimeCounter timeCounter = createTimeCounter("putWidget");
        try {
            persistance.putWidget(widget);
        } finally {
            timeCounter.stop();
        }
    }

    public Site getSite(Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getSite");
        try {
            return persistance.getSite(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public SlideShowImage getSlideShowImageById(Integer slideShowImageId) {
        final TimeCounter timeCounter = createTimeCounter("getSlideShowImageById");
        try {
            return persistance.getSlideShowImageById(slideShowImageId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getSiteTitleBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getSiteTitleBySiteId");
        try {
            return persistance.getSiteTitleBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftRegistrationForm getRegistrationFormById(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getRegistrationFormById");
        try {
            return persistance.getRegistrationFormById(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftCustomForm getCustomFormById(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getCustomFormById");
        try {
            return persistance.getCustomFormById(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftForum getForumByNameAndSiteId(String forumName, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getForumByNameAndSiteId");
        try {
            return persistance.getForumByNameAndSiteId(forumName, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftRegistrationForm getRegistrationFormByNameAndSiteId(final String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getRegistrationFormByNameAndSiteId");
        try {
            return persistance.getRegistrationFormByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftSlideShow getSlideShowByNameAndSiteId(final String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getSlideShowByNameAndSiteId");
        try {
            return persistance.getSlideShowByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftMenu getMenuByNameAndSiteId(final String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getMenuByNameAndSiteId");
        try {
            return persistance.getMenuByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftShoppingCart getShoppingCartByNameAndSiteId(final String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getShoppingCartByNameAndSiteId");
        try {
            return persistance.getShoppingCartByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftPurchaseHistory getPurchaseHistoryByNameAndSiteId(final String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getPurchaseHistoryByNameAndSiteId");
        try {
            return persistance.getPurchaseHistoryByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftAdvancedSearch getDraftAdvancedSearch(final String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getDraftAdvancedSearch");
        try {
            return persistance.getDraftAdvancedSearch(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftChildSiteRegistration getChildSiteRegistrationFormByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getChildSiteRegistrationFormByNameAndSiteId");
        try {
            return persistance.getChildSiteRegistrationFormByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftCustomForm getCustomFormByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getCustomFormByNameAndSiteId");
        try {
            return persistance.getCustomFormByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftBlogSummary getBlogSummaryByNameAndSiteId(String blogSummaryName, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogSummaryByNameAndSiteId");
        try {
            return persistance.getBlogSummaryByNameAndSiteId(blogSummaryName, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftAdminLogin getAdminLoginByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getAdminLoginByNameAndSiteId");
        try {
            return persistance.getAdminLoginByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public DraftTaxRatesUS getTaxRatesByNameAndSiteId(String taxRateName, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getTaxRatesByNameAndSiteId");
        try {
            return persistance.getTaxRatesByNameAndSiteId(taxRateName, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<DraftMenu> getMenusBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getMenusBySiteId");
        try {
            return persistance.getMenusBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<DraftMenu> getMenusWithDefaultStructureBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getMenusWithDefaultStructureBySiteId");
        try {
            return persistance.getMenusWithDefaultStructureBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftMenu getMenuById(Integer menuId) {
        final TimeCounter timeCounter = createTimeCounter("getMenuById");
        try {
            return persistance.getMenuById(menuId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftBlog getBlogByNameAndSiteId(String blogName, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getBlogByNameAndSiteId");
        try {
            return persistance.getBlogByNameAndSiteId(blogName, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public SubForum getSubForumById(int subForumId) {
        final TimeCounter timeCounter = createTimeCounter("getSubForumById");
        try {
            return persistance.getSubForumById(subForumId);
        } finally {
            timeCounter.stop();
        }
    }


    public ForumThread getForumThreadById(int threadId) {
        final TimeCounter timeCounter = createTimeCounter("getForumThreadById");
        try {
            return persistance.getForumThreadById(threadId);
        } finally {
            timeCounter.stop();
        }
    }


    public Page getPageByOwnDomainName(String ownDomainName) {
        final TimeCounter timeCounter = createTimeCounter("getPageByOwnDomainName");
        try {
            return persistance.getPageByOwnDomainName(ownDomainName);
        } finally {
            timeCounter.stop();
        }
    }


    public Page getPageByNameAndSite(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getPageByNameAndSite");
        try {
            return persistance.getPageByNameAndSite(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public Page getPageByUrlAndAndSiteIgnoreUrlCase(String url, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getPageByUrlAndAndSiteIgnoreUrlCase");
        try {
            return persistance.getPageByUrlAndAndSiteIgnoreUrlCase(url, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public Widget getWidget(Integer widgetId) {
        final TimeCounter timeCounter = createTimeCounter("getWidget");
        try {
            return persistance.getWidget(widgetId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<WidgetItem> getGalleryDataWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryDataWidgetsBySitesId");
        try {
            return persistance.getGalleryDataWidgetsBySitesId(sitesId, siteShowOption);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Icon getIcon(Integer iconId) {
        final TimeCounter timeCounter = createTimeCounter("getIcon");
        try {
            return persistance.getIcon(iconId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putIcon(Icon icon) {
        final TimeCounter timeCounter = createTimeCounter("putIcon");
        try {
            persistance.putIcon(icon);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeIcon(Icon icon) {
        final TimeCounter timeCounter = createTimeCounter("removeIcon");
        try {
            persistance.removeIcon(icon);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeIcon(Integer iconId) {
        final TimeCounter timeCounter = createTimeCounter("removeIcon");
        try {
            persistance.removeIcon(iconId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putCSVDataExport(CSVDataExport csvDataExport) {
        final TimeCounter timeCounter = createTimeCounter("putCSVDataExport");
        try {
            persistance.putCSVDataExport(csvDataExport);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public CSVDataExport getCSVDataExport(Integer customizeDataExportId) {
        final TimeCounter timeCounter = createTimeCounter("getCsvDataExport");
        try {
            return persistance.getCSVDataExport(customizeDataExportId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putCSVDataExportField(CSVDataExportField field) {
        final TimeCounter timeCounter = createTimeCounter("putCSVDataExportField");
        try {
            persistance.putCSVDataExportField(field);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public CSVDataExportField getCSVDataExportField(Integer fieldId) {
        final TimeCounter timeCounter = createTimeCounter("getCSVDataExportField");
        try {
            return persistance.getCSVDataExportField(fieldId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeCSVDataExportField(CSVDataExportField field) {
        final TimeCounter timeCounter = createTimeCounter("removeCSVDataExportField");
        try {
            persistance.removeCSVDataExportField(field);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putCustomizeManageRecords(CustomizeManageRecords customizeManageRecords) {
        final TimeCounter timeCounter = createTimeCounter("putCustomizeManageRecords");
        try {
            persistance.putCustomizeManageRecords(customizeManageRecords);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public CustomizeManageRecords getCustomizeManageRecords(Integer customizeManageRecordsId) {
        final TimeCounter timeCounter = createTimeCounter("getCustomizeManageRecords");
        try {
            return persistance.getCustomizeManageRecords(customizeManageRecordsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public CustomizeManageRecords getCustomizeManageRecords(Integer formId, Integer userId) {
        final TimeCounter timeCounter = createTimeCounter("getCustomizeManageRecords");
        try {
            return persistance.getCustomizeManageRecords(formId, userId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putCustomizeManageRecordsField(CustomizeManageRecordsField field) {
        final TimeCounter timeCounter = createTimeCounter("putCustomizeManageRecordsField");
        try {
            persistance.putCustomizeManageRecordsField(field);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public CustomizeManageRecordsField getCustomizeManageRecordsField(Integer fieldId) {
        final TimeCounter timeCounter = createTimeCounter("getCustomizeManageRecordsField");
        try {
            return persistance.getCustomizeManageRecordsField(fieldId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeCustomizeManageRecordsField(CustomizeManageRecordsField field) {
        final TimeCounter timeCounter = createTimeCounter("removeCustomizeManageRecordsField");
        try {
            persistance.removeCustomizeManageRecordsField(field);
        } finally {
            timeCounter.stop();
        }
    }


    @Override
    public void putTaxRate(TaxRateUS taxRate) {
        final TimeCounter timeCounter = createTimeCounter("putTaxRate");
        try {
            persistance.putTaxRate(taxRate);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putItemSize(ItemSize itemSize) {
        final TimeCounter timeCounter = createTimeCounter("putItemSize");
        try {
            persistance.putItemSize(itemSize);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public ItemSize getItemSize(Integer itemSizeId) {
        final TimeCounter timeCounter = createTimeCounter("getItemSize");
        try {
            return persistance.getItemSize(itemSizeId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putFontsAndColors(FontsAndColors fontsAndColors) {
        final TimeCounter timeCounter = createTimeCounter("putFontsAndColors");
        try {
            persistance.putFontsAndColors(fontsAndColors);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeFontsAndColors(FontsAndColors fontsAndColors) {
        final TimeCounter timeCounter = createTimeCounter("removeFontsAndColors");
        try {
            persistance.removeFontsAndColors(fontsAndColors);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeBackground(Background background) {
        final TimeCounter timeCounter = createTimeCounter("removeBackground");
        try {
            persistance.removeBackground(background);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeBorder(Border border) {
        final TimeCounter timeCounter = createTimeCounter("removeBorder");
        try {
            persistance.removeBorder(border);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public FontsAndColors getFontsAndColors(Integer fontsAndColorsId) {
        final TimeCounter timeCounter = createTimeCounter("getExistingFontsAndColorsOrCreateNew");
        try {
            return persistance.getFontsAndColors(fontsAndColorsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putFontsAndColorsValue(FontsAndColorsValue fontsAndColorsValue) {
        final TimeCounter timeCounter = createTimeCounter("putFontsAndColorsValue");
        try {
            persistance.putFontsAndColorsValue(fontsAndColorsValue);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, String> getSitesIdWithTitles(Set<Integer> sitesId) {
        final TimeCounter timeCounter = createTimeCounter("getSitesIdWithTitles");
        try {
            return persistance.getSitesIdWithTitles(sitesId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Date getLastUpdatedDate(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getLastUpdatedDate");
        try {
            return persistance.getLastUpdatedDate(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public FormExportTask getFormExportTask(Integer formExportTaskId) {
        final TimeCounter timeCounter = createTimeCounter("getFormExportTask");
        try {
            return persistance.getFormExportTask(formExportTaskId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFormExportTask(FormExportTask formExportTask) {
        final TimeCounter timeCounter = createTimeCounter("putFormExportTask");
        try {
            persistance.putFormExportTask(formExportTask);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeFormExportTask(FormExportTask formExportTask) {
        final TimeCounter timeCounter = createTimeCounter("removeFormExportTask");
        try {
            persistance.removeFormExportTask(formExportTask);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FormExportTask> getFormExportTasksByFormId(int formId) {
        final TimeCounter timeCounter = createTimeCounter("getFormExportTasksByFormId");
        try {
            return persistance.getFormExportTasksByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FormExportTask> getFormExportTasksBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getFormExportTasksBySiteId");
        try {
            return persistance.getFormExportTasksBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FormExportTask> getAllFormExportTasks() {
        final TimeCounter timeCounter = createTimeCounter("getAllFormExportTasks");
        try {
            return persistance.getAllFormExportTasks();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Site getSiteByBrandedSubDomain(final String brandedSubDomain, final String brandedDomain) {
        final TimeCounter timeCounter = createTimeCounter("getSiteByBrandedSubDomain");
        try {
            return persistance.getSiteByBrandedSubDomain(brandedSubDomain, brandedDomain);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<String> getImagesKeywordsBySite(final Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImagesKeywordsBySite");
        try {
            return persistance.getImagesKeywordsBySite(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<String> getImagesNamesBySite(final Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImagesNamesBySite");
        try {
            return persistance.getImagesNamesBySite(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Image> getImagesByKeywords(final Integer siteId, final List<String> keywords) {
        final TimeCounter timeCounter = createTimeCounter("getImagesByKeywords");
        try {
            return persistance.getImagesByKeywords(siteId, keywords);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putHtml(final Html html) {
        final TimeCounter timeCounter = createTimeCounter("putHtml");
        try {
            persistance.putHtml(html);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Site> getPublishedBlueprints() {
        final TimeCounter timeCounter = createTimeCounter("getPublishedBlueprints");
        try {
            return persistance.getPublishedBlueprints();
        } finally {
            timeCounter.stop();
        }
    }

    public List<Site> getActiveBlueprints(BlueprintCategory blueprintCategory) {
        final TimeCounter timeCounter = createTimeCounter("getActiveBlueprints");
        try {
            return persistance.getActiveBlueprints(blueprintCategory);
        } finally {
            timeCounter.stop();
        }
    }

    public GoogleBaseDataExportMappedByFilledFormId getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(Integer filledFormId) {
        final TimeCounter timeCounter = createTimeCounter("getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId");
        try {
            return persistance.getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(filledFormId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putGoogleBaseDataExportMappedByFilledFormId(GoogleBaseDataExportMappedByFilledFormId dataExportMappedByFilledFormId) {
        final TimeCounter timeCounter = createTimeCounter("putGoogleBaseDataExportMappedByFilledFormId");
        try {
            persistance.putGoogleBaseDataExportMappedByFilledFormId(dataExportMappedByFilledFormId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<PageSettings> getPageSettingsWithHtmlOrCss() {
        final TimeCounter timeCounter = createTimeCounter("getPageSettingsWithHtmlOrCss");
        try {
            return persistance.getPageSettingsWithHtmlOrCss();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeAccessibleSettings(final AccessibleSettings accessibleSettings) {
        final TimeCounter timeCounter = createTimeCounter("removeAccessibleSettings");
        try {
            persistance.removeAccessibleSettings(accessibleSettings);
        } finally {
            timeCounter.stop();
        }
    }

    public Widget getWidgetByCrossWidgetsId(Integer crossWidgetId, SiteShowOption siteShowOption) {
        final TimeCounter timeCounter = createTimeCounter("getWidgetByCrossWidgetsId");
        try {
            return persistance.getWidgetByCrossWidgetsId(crossWidgetId, siteShowOption);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Widget> getWidgetsByCrossWidgetsId(List<Integer> crossWidgetId, SiteShowOption siteShowOption) {
        final TimeCounter timeCounter = createTimeCounter("getWidgetsByCrossWidgetsId");
        try {
            return persistance.getWidgetsByCrossWidgetsId(crossWidgetId, siteShowOption);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Widget> getWorkWidgetsByCrossWidgetsId(List<Integer> crossWidgetId) {
        final TimeCounter timeCounter = createTimeCounter("getWorkWidgetsByCrossWidgetsId");
        try {
            return persistance.getWorkWidgetsByCrossWidgetsId(crossWidgetId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Widget> getDraftWidgetsByCrossWidgetsId(List<Integer> crossWidgetId) {
        final TimeCounter timeCounter = createTimeCounter("getDraftWidgetsByCrossWidgetsId");
        try {
            return persistance.getDraftWidgetsByCrossWidgetsId(crossWidgetId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Widget> getWidgetsBySitesId(List<Integer> sitesId, SiteShowOption siteShowOption) {
        final TimeCounter timeCounter = createTimeCounter("getWidgetsBySitesId");
        try {
            return persistance.getWidgetsBySitesId(sitesId, siteShowOption);
        } finally {
            timeCounter.stop();
        }
    }

    public Widget getFirstWidgetByItemId(int itemId) {
        final TimeCounter timeCounter = createTimeCounter("getFirstWidgetByItemId");
        try {
            return persistance.getFirstWidgetByItemId(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeWidget(Widget widget) {
        final TimeCounter timeCounter = createTimeCounter("removeWidget");
        try {
            persistance.removeWidget(widget);
        } finally {
            timeCounter.stop();
        }
    }

    public void removePost(ForumPost forumPost) {
        final TimeCounter timeCounter = createTimeCounter("removePost");
        try {
            persistance.removePost(forumPost);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeFormFilter(DraftFormFilter formFilter) {
        final TimeCounter timeCounter = createTimeCounter("removeFormFilter");
        try {
            persistance.removeFormFilter(formFilter);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeFormFilterRule(DraftFormFilterRule formFilterRule) {
        final TimeCounter timeCounter = createTimeCounter("removeFormFilterRule");
        try {
            persistance.removeFormFilterRule(formFilterRule);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeThread(ForumThread forumThread) {
        final TimeCounter timeCounter = createTimeCounter("removeThread");
        try {
            persistance.removeThread(forumThread);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeFormItem(DraftFormItem formItem) {
        final TimeCounter timeCounter = createTimeCounter("removeFormItem");
        try {
            persistance.removeFormItem(formItem);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeFilledForm(FilledForm form) {
        final TimeCounter timeCounter = createTimeCounter("removeFilledForm");
        try {
            persistance.removeFilledForm(form);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeSubForum(SubForum subForum) {
        final TimeCounter timeCounter = createTimeCounter("removeSubForum");
        try {
            persistance.removeSubForum(subForum);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeSlideShowImage(SlideShowImage slideShowImage) {
        final TimeCounter timeCounter = createTimeCounter("removeSlideShowImage");
        try {
            persistance.removeSlideShowImage(slideShowImage);
        } finally {
            timeCounter.stop();
        }
    }

    public List<DraftGallery> getGalleriesByFormId(Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleriesByFormId");
        try {
            return persistance.getGalleriesByFormId(formId);
        } finally {
            timeCounter.stop();
        }
    }

    public Image getImageById(Integer imageId) {
        final TimeCounter timeCounter = createTimeCounter("getImageById");
        try {
            return persistance.getImageById(imageId);
        } finally {
            timeCounter.stop();
        }
    }

    public BackgroundImage getBackgroundImageById(int backgroundImageId) {
        final TimeCounter timeCounter = createTimeCounter("getBackgroundImageById");
        try {
            return persistance.getBackgroundImageById(backgroundImageId);
        } finally {
            timeCounter.stop();
        }
    }

    public MenuImage getMenuImageById(Integer menuImageId) {
        final TimeCounter timeCounter = createTimeCounter("getMenuImageById");
        try {
            return persistance.getMenuImageById(menuImageId);
        } finally {
            timeCounter.stop();
        }
    }

    public ImageForVideo getImageForVideoById(int imageId) {
        final TimeCounter timeCounter = createTimeCounter("getImageForVideoById");
        try {
            return persistance.getImageForVideoById(imageId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFormFile(FormFile formFile) {
        final TimeCounter timeCounter = createTimeCounter("putFormFile");
        try {
            persistance.putFormFile(formFile);
        } finally {
            timeCounter.stop();
        }
    }

    public FormFile getFormFileById(Integer formFileId) {
        final TimeCounter timeCounter = createTimeCounter("getFormFileById");
        try {
            return persistance.getFormFileById(formFileId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FormFile> getFormFiles() {
        final TimeCounter timeCounter = createTimeCounter("getFormFiles");
        try {
            return persistance.getFormFiles();
        } finally {
            timeCounter.stop();
        }
    }

    public void removeFormFile(FormFile formFile) {
        final TimeCounter timeCounter = createTimeCounter("removeFormFile");
        try {
            persistance.removeFormFile(formFile);
        } finally {
            timeCounter.stop();
        }
    }


    public void putImageFile(ImageFile imageFile) {
        final TimeCounter timeCounter = createTimeCounter("putImageFile");
        try {
            persistance.putImageFile(imageFile);
        } finally {
            timeCounter.stop();
        }
    }


    public ImageFile getImageFileById(int imageFileId) {
        final TimeCounter timeCounter = createTimeCounter("getImageFileById");
        try {
            return persistance.getImageFileById(imageFileId);
        } finally {
            timeCounter.stop();
        }
    }


    public List<ImageFile> getImageFilesBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImageFilesBySiteId");
        try {
            return persistance.getImageFilesBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public List<ImageFile> getImageFilesByTypeAndSiteId(ImageFileType type, Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImageFilesByTypeAndSiteId");
        try {
            return persistance.getImageFilesByTypeAndSiteId(type, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeImageFile(ImageFile imageFile) {
        final TimeCounter timeCounter = createTimeCounter("removeImageFile");
        try {
            persistance.removeImageFile(imageFile);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeFilledFormItem(FilledFormItem filledFormItem) {
        final TimeCounter timeCounter = createTimeCounter("removeFilledFormItem");
        try {
            persistance.removeFilledFormItem(filledFormItem);
        } finally {
            timeCounter.stop();
        }
    }


    public void putImage(Image image) {
        final TimeCounter timeCounter = createTimeCounter("putImage");
        try {
            persistance.putImage(image);
        } finally {
            timeCounter.stop();
        }
    }


    public void putBackgroundImage(BackgroundImage image) {
        final TimeCounter timeCounter = createTimeCounter("putBackgroundImage");
        try {
            persistance.putBackgroundImage(image);
        } finally {
            timeCounter.stop();
        }
    }

    public void putMenuImage(MenuImage image) {
        final TimeCounter timeCounter = createTimeCounter("putMenuImage");
        try {
            persistance.putMenuImage(image);
        } finally {
            timeCounter.stop();
        }
    }


    public void putImageForVideo(ImageForVideo image) {
        final TimeCounter timeCounter = createTimeCounter("putImageForVideo");
        try {
            persistance.putImageForVideo(image);
        } finally {
            timeCounter.stop();
        }
    }


    public void putStyle(Style style) {
        final TimeCounter timeCounter = createTimeCounter("putStyle");
        try {
            persistance.putStyle(style);
        } finally {
            timeCounter.stop();
        }
    }


    public Style getStyleById(int styleId) {
        final TimeCounter timeCounter = createTimeCounter("getStyleById");
        try {
            return persistance.getStyleById(styleId);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeStyle(Style style) {
        final TimeCounter timeCounter = createTimeCounter("removeStyle");
        try {
            persistance.removeStyle(style);
        } finally {
            timeCounter.stop();
        }
    }


    public void putBorder(Border borderBackground) {
        final TimeCounter timeCounter = createTimeCounter("putBorder");
        try {
            persistance.putBorder(borderBackground);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putBackground(final Background background) {
        final TimeCounter timeCounter = createTimeCounter("putBackground");
        try {
            persistance.putBackground(background);
        } finally {
            timeCounter.stop();
        }
    }

    public Border getBorder(Integer borderId) {
        final TimeCounter timeCounter = createTimeCounter("getBorder");
        try {
            return persistance.getBorder(borderId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Background getBackground(Integer backgroundId) {
        final TimeCounter timeCounter = createTimeCounter("getBackground");
        try {
            return persistance.getBackground(backgroundId);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeBorderBackground(Border borderBackground) {
        final TimeCounter timeCounter = createTimeCounter("removeBorderBackground");
        try {
            persistance.removeBorderBackground(borderBackground);
        } finally {
            timeCounter.stop();
        }
    }


    public void putVideo(Video video) {
        final TimeCounter timeCounter = createTimeCounter("putVideo");
        try {
            persistance.putVideo(video);
        } finally {
            timeCounter.stop();
        }
    }


    public void putFlvVideo(FlvVideo flvVideo) {
        final TimeCounter timeCounter = createTimeCounter("putFlvVideo");
        try {
            persistance.putFlvVideo(flvVideo);
        } finally {
            timeCounter.stop();
        }
    }


    public Video getVideoById(Integer videoId) {
        final TimeCounter timeCounter = createTimeCounter("getVideoById");
        try {
            return persistance.getVideoById(videoId);
        } finally {
            timeCounter.stop();
        }
    }

    public FlvVideo getFlvVideo(Integer flvVideoId) {
        final TimeCounter timeCounter = createTimeCounter("getFlvVideo");
        try {
            return persistance.getFlvVideo(flvVideoId);
        } finally {
            timeCounter.stop();
        }
    }

    public FlvVideo getFlvVideo(Integer sourceVideoId, Integer width, Integer height, Integer quality) {
        final TimeCounter timeCounter = createTimeCounter("getFlvVideo");
        try {
            return persistance.getFlvVideo(sourceVideoId, width, height, quality);
        } finally {
            timeCounter.stop();
        }
    }


    public List<DraftItem> getDraftItemsByUserId(int userId, ItemType itemType) {
        final TimeCounter timeCounter = createTimeCounter("getDraftItemsByUserId");
        try {
            return persistance.getDraftItemsByUserId(userId, itemType);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<DraftItem> getDraftItemsBySiteId(int siteId, ItemType itemType, ItemType... excludedItemTypes) {
        final TimeCounter timeCounter = createTimeCounter("getDraftItemsBySiteId");
        try {
            return persistance.getDraftItemsBySiteId(siteId, itemType, excludedItemTypes);
        } finally {
            timeCounter.stop();
        }
    }


    public List<Site> getSites(int userId, SiteAccessLevel[] accessLevels, SiteType... siteTypes) {
        final TimeCounter timeCounter = createTimeCounter("getSites");
        try {
            return persistance.getSites(userId, accessLevels, siteTypes);
        } finally {
            timeCounter.stop();
        }
    }

    public List<User> getUsersWithActiveRights(Integer siteId, SiteAccessLevel[] accessLevels) {
        final TimeCounter timeCounter = createTimeCounter("getUsersWithActiveRights");
        try {
            return persistance.getUsersWithActiveRights(siteId, accessLevels);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<User> getUsersWithRightsToSite(Integer siteId, SiteAccessLevel[] accessLevels) {
        final TimeCounter timeCounter = createTimeCounter("getUsersWithRightsToSite");
        try {
            return persistance.getUsersWithRightsToSite(siteId, accessLevels);
        } finally {
            timeCounter.stop();
        }
    }

    public List<UserOnSiteRight> getUserOnSiteRights(Integer siteId, SiteAccessLevel[] accessLevels) {
        final TimeCounter timeCounter = createTimeCounter("getUserOnSiteRights");
        try {
            return persistance.getUserOnSiteRights(siteId, accessLevels);
        } finally {
            timeCounter.stop();
        }
    }

    public List<BackgroundImage> getBackgroundImagesBySiteId(Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getBackgroundImagesBySiteId");
        try {
            return persistance.getBackgroundImagesBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public List<ImageForVideo> getImagesForVideoBySiteId(Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImagesForVideoBySiteId");
        try {
            return persistance.getImagesForVideoBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Video> getVideosBySiteId(Integer siteId) {
        final TimeCounter timeCounter = createTimeCounter("getVideosBySiteId");
        try {
            return persistance.getVideosBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public List<Video> getVideos() {
        final TimeCounter timeCounter = createTimeCounter("getVideos");
        try {
            return persistance.getVideos();
        } finally {
            timeCounter.stop();
        }
    }


    public void putCssParameterValue(CssParameterValue cssParameterValue) {
        final TimeCounter timeCounter = createTimeCounter("putCssParameterValue");
        try {
            persistance.putCssParameterValue(cssParameterValue);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putCssValue(final CssValue cssValue) {
        final TimeCounter timeCounter = createTimeCounter("putCssValue");
        try {
            persistance.putCssValue(cssValue);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeCssParameterValue(CssParameterValue cssParameterValue) {
        final TimeCounter timeCounter = createTimeCounter("removeCssParameterValue");
        try {
            persistance.removeCssParameterValue(cssParameterValue);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeBlogPost(BlogPost blogPost) {
        final TimeCounter timeCounter = createTimeCounter("removeBlogPost");
        try {
            persistance.removeBlogPost(blogPost);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeVote(Vote vote) {
        final TimeCounter timeCounter = createTimeCounter("removeVote");
        try {
            persistance.removeVote(vote);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeVotesByFilledFormId(Integer filledFormId) {
        final TimeCounter timeCounter = createTimeCounter("removeVotesByFilledFormId");
        try {
            persistance.removeVotesByFilledFormId(filledFormId);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeManageVotesGallerySettings(ManageVotesSettings manageVotesGallerySettings) {
        final TimeCounter timeCounter = createTimeCounter("removeManageVotesGallerySettings");
        try {
            persistance.removeManageVotesGallerySettings(manageVotesGallerySettings);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeUser(User user) {
        final TimeCounter timeCounter = createTimeCounter("removeUser");
        try {
            persistance.removeUser(user);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeUserOnSiteRight(UserOnSiteRight userOnSiteRight) {
        final TimeCounter timeCounter = createTimeCounter("removeUserOnSiteRight");
        try {
            persistance.removeUserOnSiteRight(userOnSiteRight);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeAdvancedSearchOption(DraftAdvancedSearchOption advancedSearchOption) {
        final TimeCounter timeCounter = createTimeCounter("removeAdvancedSearchOption");
        try {
            persistance.removeAdvancedSearchOption(advancedSearchOption);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeComment(Comment comment) {
        final TimeCounter timeCounter = createTimeCounter("removeComment");
        try {
            persistance.removeComment(comment);
        } finally {
            timeCounter.stop();
        }
    }

    public void removePage(Page page) {
        final TimeCounter timeCounter = createTimeCounter("removePage");
        try {
            persistance.removePage(page);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeSite(Site site) {
        final TimeCounter timeCounter = createTimeCounter("removeSite");
        try {
            persistance.removeSite(site);
        } finally {
            timeCounter.stop();
        }
    }

    public ForumPost getLastThreadPost(int forumThreadId) {
        final TimeCounter timeCounter = createTimeCounter("getLastThreadPost");
        try {
            return persistance.getLastThreadPost(forumThreadId);
        } finally {
            timeCounter.stop();
        }
    }

    public ForumPost getLastForumPost(int forumId) {
        final TimeCounter timeCounter = createTimeCounter("getLastForumPost");
        try {
            return persistance.getLastForumPost(forumId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<Integer, Date> getLastForumPosts(Set<Integer> forumsId) {
        final TimeCounter timeCounter = createTimeCounter("getLastForumPosts");
        try {
            return persistance.getLastForumPosts(forumsId);
        } finally {
            timeCounter.stop();
        }
    }

    public ForumPost getLastSubForumPost(int subForumId) {
        final TimeCounter timeCounter = createTimeCounter("getLastSubForumPost");
        try {
            return persistance.getLastSubForumPost(subForumId);
        } finally {
            timeCounter.stop();
        }
    }

    public Content getContentById(ContentId contentId) {
        final TimeCounter timeCounter = createTimeCounter("getContentById");
        try {
            return persistance.getContentById(contentId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putContent(Content content) {
        final TimeCounter timeCounter = createTimeCounter("putContent");
        try {
            persistance.putContent(content);
        } finally {
            timeCounter.stop();
        }
    }


    public int getMaxContentClientId() {
        final TimeCounter timeCounter = createTimeCounter("getMaxContentClientId");
        try {
            return persistance.getMaxContentClientId();
        } finally {
            timeCounter.stop();
        }
    }


    public void putKeywordsGroup(KeywordsGroup keywordsGroup) {
        final TimeCounter timeCounter = createTimeCounter("putKeywordsGroup");
        try {
            persistance.putKeywordsGroup(keywordsGroup);
        } finally {
            timeCounter.stop();
        }
    }


    public KeywordsGroup getKeywordsGroupById(int keywordsGroupId) {
        final TimeCounter timeCounter = createTimeCounter("getKeywordsGroupById");
        try {
            return persistance.getKeywordsGroupById(keywordsGroupId);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeKeywordsGroup(KeywordsGroup keywordsGroup) {
        final TimeCounter timeCounter = createTimeCounter("removeKeywordsGroup");
        try {
            persistance.removeKeywordsGroup(keywordsGroup);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeContent(Content content) {
        final TimeCounter timeCounter = createTimeCounter("removeContent");
        try {
            persistance.removeContent(content);
        } finally {
            timeCounter.stop();
        }
    }

    public void destroy() {
        persistance.destroy();
    }

    public void removeImage(Image image) {
        final TimeCounter timeCounter = createTimeCounter("removeImage");
        try {
            persistance.removeImage(image);
        } finally {
            timeCounter.stop();
        }
    }

    public void putPage(Page page) {
        final TimeCounter timeCounter = createTimeCounter("putPage");
        try {
            persistance.putPage(page);
        } finally {
            timeCounter.stop();
        }
    }

    public Page getPage(Integer pageId) {
        final TimeCounter timeCounter = createTimeCounter("getPage");
        try {
            return persistance.getPage(pageId);
        } finally {
            timeCounter.stop();
        }
    }

    public Gallery getGalleryByOrderFormId(Integer orderFormId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryByOrderFormId");
        try {
            return persistance.getGalleryByOrderFormId(orderFormId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putUserOnSiteRight(UserOnSiteRight userOnSiteRight) {
        final TimeCounter timeCounter = createTimeCounter("putUserOnSiteRight");
        try {
            persistance.putUserOnSiteRight(userOnSiteRight);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeDraftItem(DraftItem draftItem) {
        final TimeCounter timeCounter = createTimeCounter("removeDraftItem");
        try {
            persistance.removeDraftItem(draftItem);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeCreditCard(CreditCard creditCard) {
        final TimeCounter timeCounter = createTimeCounter("removeCreditCard");
        try {
            persistance.removeCreditCard(creditCard);
        } finally {
            timeCounter.stop();
        }
    }

    public <R> R inContext(PersistanceContext<R> persistanceContext) {
        final TimeCounter timeCounter = createTimeCounter("inContext");
        try {
            return persistance.inContext(persistanceContext);
        } finally {
            timeCounter.stop();
        }
    }

    public Site getSiteByCustomUrl(String customUrl) {
        final TimeCounter timeCounter = createTimeCounter("getSiteByAliaseUrl");
        try {
            return persistance.getSiteByCustomUrl(customUrl);
        } finally {
            timeCounter.stop();
        }
    }

    @SuppressWarnings({"unchecked"})
    public <T extends DraftItem> T getDraftItem(final Integer siteItemId) {
        final TimeCounter timeCounter = createTimeCounter("getSiteItemByIdAndType");
        try {
            return (T) persistance.getDraftItem(siteItemId);
        } finally {
            timeCounter.stop();
        }
    }

    public int getUsersCount() {
        final TimeCounter timeCounter = createTimeCounter("getUsersCount");
        try {
            return persistance.getUsersCount();
        } finally {
            timeCounter.stop();
        }
    }

    public void addUpdateListener(PersistanceListener listener) {
        final TimeCounter timeCounter = createTimeCounter("addUpdateListener");
        try {
            persistance.addUpdateListener(listener);
        } finally {
            timeCounter.stop();
        }
    }

    public void addRemoveListener(PersistanceListener listener) {
        final TimeCounter timeCounter = createTimeCounter("addRemoveListener");
        try {
            persistance.addRemoveListener(listener);
        } finally {
            timeCounter.stop();
        }
    }

    public void putJournalItem(JournalItem journalItem) {
        final TimeCounter timeCounter = createTimeCounter("putJournalItem");
        try {
            persistance.putJournalItem(journalItem);
        } finally {
            timeCounter.stop();
        }
    }

    public List<User> getNotActivatedUsers(Date registeredTo, int countUsers) {
        final TimeCounter timeCounter = createTimeCounter("getNotActivatedUsers");
        try {
            return persistance.getNotActivatedUsers(registeredTo, countUsers);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftChildSiteRegistration getChildSiteRegistrationById(Integer childSiteRegistrationId) {
        final TimeCounter timeCounter = createTimeCounter("getChildSiteRegistrationById");
        try {
            return persistance.getChildSiteRegistrationById(childSiteRegistrationId);
        } finally {
            timeCounter.stop();
        }
    }

    public ChildSiteSettings getChildSiteSettingsById(Integer childSiteSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getChildSiteSettingsById");
        try {
            return persistance.getChildSiteSettingsById(childSiteSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    public void putChildSiteSettings(ChildSiteSettings childSiteSettings) {
        final TimeCounter timeCounter = createTimeCounter("putChildSiteSettings");
        try {
            persistance.putChildSiteSettings(childSiteSettings);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeChildSiteSettings(ChildSiteSettings childSiteSettings) {
        final TimeCounter timeCounter = createTimeCounter("removeChildSiteSettings");
        try {
            persistance.removeChildSiteSettings(childSiteSettings);
        } finally {
            timeCounter.stop();
        }
    }

    public List<ChildSiteSettings> getChildSiteSettingsByUserId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getChildSiteSettingsByUserId");
        try {
            return persistance.getChildSiteSettingsByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Image> getImagesByOwnerSiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImagesByOwnerSiteId");
        try {
            return persistance.getImagesByOwnerSiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public Image getImageByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getImageByNameAndSiteId");
        try {
            return persistance.getImageByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Integer> getSitesThatContainGalleriesWithEcommerce(List<Integer> siteIds) {
        final TimeCounter timeCounter = createTimeCounter("getSitesThatContainGalleriesWithEcommerce");
        try {
            return persistance.getSitesThatContainGalleriesWithEcommerce(siteIds);
        } finally {
            timeCounter.stop();
        }
    }

    public List<UserOnSiteRight> getNotActiveUserOnSiteRightsByUserAndInvitedUser(int userId, int invitedUserId) {
        final TimeCounter timeCounter = createTimeCounter("getNotActiveUserOnSiteRightsByUserAndInvitedUser");
        try {
            return persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(userId, invitedUserId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FilledForm> getUserPurchasesOnSite(int userId, int invitedUserId) {
        final TimeCounter timeCounter = createTimeCounter("getUserPurchasesOnSite");
        try {
            return persistance.getUserPurchasesOnSite(userId, invitedUserId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Site> getAllSites() {
        final TimeCounter timeCounter = createTimeCounter("getAllSites");
        try {
            return persistance.getAllSites();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Site> getSitesWithNotEmptyIncomeSettings() {
        final TimeCounter timeCounter = createTimeCounter("getSitesWithNotEmptyIncomeSettings");
        try {
            return persistance.getSitesWithNotEmptyIncomeSettings();
        } finally {
            timeCounter.stop();
        }
    }

    public List<Site> getChildSites() {
        final TimeCounter timeCounter = createTimeCounter("getChildSites");
        try {
            return persistance.getChildSites();
        } finally {
            timeCounter.stop();
        }
    }

    public List<Site> getSitesConnectedToCreditCard(int creditCardId) {
        final TimeCounter timeCounter = createTimeCounter("getSitesConnectedToCreditCard");
        try {
            return persistance.getSitesConnectedToCreditCard(creditCardId);
        } finally {
            timeCounter.stop();
        }
    }


    public void putSiteOnItem(SiteOnItem siteOnItemRight) {
        final TimeCounter timeCounter = createTimeCounter("putSiteOnItem");
        try {
            persistance.putSiteOnItem(siteOnItemRight);
        } finally {
            timeCounter.stop();
        }
    }


    public SiteOnItem getSiteOnItemRightBySiteIdItemIdAndType(int siteId, int itemId, ItemType type) {
        final TimeCounter timeCounter = createTimeCounter("getSiteOnItemRightBySiteIdItemIdAndType");
        try {
            return persistance.getSiteOnItemRightBySiteIdItemIdAndType(siteId, itemId, type);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeSiteOnItemRight(SiteOnItem siteOnItemRight) {
        final TimeCounter timeCounter = createTimeCounter("removeSiteOnItemRight");
        try {
            persistance.removeSiteOnItemRight(siteOnItemRight);
        } finally {
            timeCounter.stop();
        }
    }


    public int getChildSiteSettingsCountByRegistrationId(int childSiteRegistrationId) {
        final TimeCounter timeCounter = createTimeCounter("getChildSiteSettingsCountByRegistrationId");
        try {
            return persistance.getChildSiteSettingsCountByRegistrationId(childSiteRegistrationId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftGallery getGalleryByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryByNameAndSiteId");
        try {
            return persistance.getGalleryByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftGallery getGalleryById(Integer galleryId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryById");
        try {
            return persistance.getGalleryById(galleryId);
        } finally {
            timeCounter.stop();
        }
    }


    public void putGalleryLabel(DraftGalleryLabel label) {
        final TimeCounter timeCounter = createTimeCounter("putGalleryLabel");
        try {
            persistance.putGalleryLabel(label);
        } finally {
            timeCounter.stop();
        }
    }


    public void putGalleryItem(DraftGalleryItem item) {
        final TimeCounter timeCounter = createTimeCounter("putGalleryItem");
        try {
            persistance.putGalleryItem(item);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public DraftGalleryItem getGalleryItemById(final int galleryId, final int formItemId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryItemById");
        try {
            return persistance.getGalleryItemById(galleryId, formItemId);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeGalleryLabel(DraftGalleryLabel label) {
        final TimeCounter timeCounter = createTimeCounter("removeGalleryLabel");
        try {
            persistance.removeGalleryLabel(label);
        } finally {
            timeCounter.stop();
        }
    }


    public void removeGalleryItem(DraftGalleryItem item) {
        final TimeCounter timeCounter = createTimeCounter("removeGalleryItem");
        try {
            persistance.removeGalleryItem(item);
        } finally {
            timeCounter.stop();
        }
    }


    public void putItem(Item siteItem) {
        final TimeCounter timeCounter = createTimeCounter("putSiteItem");
        try {
            persistance.putItem(siteItem);
        } finally {
            timeCounter.stop();
        }
    }


    public void putGalleryVideoRange(GalleryVideoRange galleryVideoRange) {
        final TimeCounter timeCounter = createTimeCounter("putGalleryVideoRange");
        try {
            persistance.putGalleryVideoRange(galleryVideoRange);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftTellFriend getTellFriendById(int tellFriendId) {
        final TimeCounter timeCounter = createTimeCounter("getTellFriendById");
        try {
            return persistance.getTellFriendById(tellFriendId);
        } finally {
            timeCounter.stop();
        }
    }


    public void putTellFriend(DraftTellFriend tellFriend) {
        final TimeCounter timeCounter = createTimeCounter("putTellFriend");
        try {
            persistance.putTellFriend(tellFriend);
        } finally {
            timeCounter.stop();
        }
    }


    public List<DraftTellFriend> getTellFriendsBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getTellFriendsBySiteId");
        try {
            return persistance.getTellFriendsBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public List<DraftManageVotes> getManageVotesListBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getManageVotesListBySiteId");
        try {
            return persistance.getManageVotesListBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<DraftGallery> getGalleriesBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleriesBySiteId");
        try {
            return persistance.getGalleriesBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftTellFriend getTellFriendByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getTellFriendByNameAndSiteId");
        try {
            return persistance.getTellFriendByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public DraftManageVotes getManageVotesByNameAndSiteId(String name, int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getManageVotesByNameAndSiteId");
        try {
            return persistance.getManageVotesByNameAndSiteId(name, siteId);
        } finally {
            timeCounter.stop();
        }
    }


    public void putVote(Vote vote) {
        final TimeCounter timeCounter = createTimeCounter("putVote");
        try {
            persistance.putVote(vote);
        } finally {
            timeCounter.stop();
        }
    }


    public Vote getVoteById(Integer voteId) {
        final TimeCounter timeCounter = createTimeCounter("getVoteById");
        try {
            return persistance.getVoteById(voteId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void setAllWinnerVotesToFalse(Integer userId, Integer galleryId) {
        final TimeCounter timeCounter = createTimeCounter("setAllWinnerVotesToFalse");
        try {
            persistance.setAllWinnerVotesToFalse(userId, galleryId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Vote> getVotesByStartEndDates(Integer userId, Integer galleryId, Date startDate, Date endDate, Integer... filledFormIds) {
        final TimeCounter timeCounter = createTimeCounter("getVotesByStartEndDates");
        try {
            return persistance.getVotesByStartEndDates(userId, galleryId, startDate, endDate, filledFormIds);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Vote> getVotesByTimeInterval(Integer userId, Integer galleryId, final Date startDate, final Date endDate, Integer... filledFormIds) {
        final TimeCounter timeCounter = createTimeCounter("getVotesByTimeInterval");
        try {
            return persistance.getVotesByTimeInterval(userId, galleryId, startDate, endDate, filledFormIds);
        } finally {
            timeCounter.stop();
        }
    }


    public void putGalleryComment(GalleryComment galleryComment) {
        final TimeCounter timeCounter = createTimeCounter("putGalleryComment");
        try {
            persistance.putGalleryComment(galleryComment);
        } finally {
            timeCounter.stop();
        }
    }


    public List<GalleryComment> getGalleryCommentsByFilledFormAndGallery(int filledFormId, int galleryId, Integer userId, Date start, Date finish) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryCommentsByFilledFormAndGallery");
        try {
            return persistance.getGalleryCommentsByFilledFormAndGallery(filledFormId, galleryId, userId, start, finish);
        } finally {
            timeCounter.stop();
        }
    }


    public GalleryComment getGalleryCommentById(int galleryCommentId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryCommentById");
        try {
            return persistance.getGalleryCommentById(galleryCommentId);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeGalleryComment(GalleryComment galleryComment) {
        final TimeCounter timeCounter = createTimeCounter("removeGalleryComment");
        try {
            persistance.removeGalleryComment(galleryComment);
        } finally {
            timeCounter.stop();
        }
    }

    public void putFormVideo(FormVideo formVideo) {
        final TimeCounter timeCounter = createTimeCounter("putFormVideo");
        try {
            persistance.putFormVideo(formVideo);
        } finally {
            timeCounter.stop();
        }
    }

    public void putPaymentLog(PaymentLog paymentLog) {
        final TimeCounter timeCounter = createTimeCounter("putPaymentLog");
        try {
            persistance.putPaymentLog(paymentLog);
        } finally {
            timeCounter.stop();
        }
    }

    public PaymentLog getPaymentLogById(int logId) {
        final TimeCounter timeCounter = createTimeCounter("getPaymentLogById");
        try {
            return persistance.getPaymentLogById(logId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<PaymentLog> getPaymentLogsByUsersId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getPaymentLogsByUsersId");
        try {
            return persistance.getPaymentLogsByUsersId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<PaymentLog> getAllPaymentLogs() {
        final TimeCounter timeCounter = createTimeCounter("getAllPaymentLogs");
        try {
            return persistance.getAllPaymentLogs();
        } finally {
            timeCounter.stop();
        }
    }

    public List<PaymentLog> getPaymentLogsByChildSiteSettingsId(int childSiteSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getPaymentLogsByChildSiteSettingsId");
        try {
            return persistance.getPaymentLogsByChildSiteSettingsId(childSiteSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Integer> getPageParentIds(final int siteId, final Integer parentId) {
        final TimeCounter timeCounter = createTimeCounter("getPageParentIds");
        try {
            return persistance.getPageParentIds(siteId, parentId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putMenuItem(MenuItem menuItem) {
        final TimeCounter timeCounter = createTimeCounter("putMenuItem");
        try {
            persistance.putMenuItem(menuItem);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeMenuItem(MenuItem menuItem) {
        final TimeCounter timeCounter = createTimeCounter("removeMenuItem");
        try {
            persistance.removeMenuItem(menuItem);
        } finally {
            timeCounter.stop();
        }
    }

    public DraftMenuItem getDraftMenuItem(Integer menuItemId) {
        final TimeCounter timeCounter = createTimeCounter("getDraftMenuItem");
        try {
            return persistance.getDraftMenuItem(menuItemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public WorkMenuItem getWorkMenuItem(Integer menuItemId) {
        final TimeCounter timeCounter = createTimeCounter("getWorkMenuItem");
        try {
            return persistance.getWorkMenuItem(menuItemId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<DraftMenuItem> getMenuItems(Integer pageId) {
        final TimeCounter timeCounter = createTimeCounter("getMenuItems");
        try {
            return persistance.getMenuItems(pageId);
        } finally {
            timeCounter.stop();
        }
    }

    public void setMenuItemsIncludeInMenu(List<Integer> menuItemsIds, boolean includeInMenu) {
        final TimeCounter timeCounter = createTimeCounter("setMenuItemsIncludeInMenu");
        try {
            persistance.setMenuItemsIncludeInMenu(menuItemsIds, includeInMenu);
        } finally {
            timeCounter.stop();
        }
    }

    public FormVideo getFormVideoById(Integer formVideoId) {
        final TimeCounter timeCounter = createTimeCounter("getFormVideoById");
        try {
            return persistance.getFormVideoById(formVideoId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<FormVideo> getAllFormVideos() {
        final TimeCounter timeCounter = createTimeCounter("getAllFormVideos");
        try {
            return persistance.getAllFormVideos();
        } finally {
            timeCounter.stop();
        }
    }


    public List<DraftForm> getAllForms() {
        final TimeCounter timeCounter = createTimeCounter("getAllForms");
        try {
            return persistance.getAllForms();
        } finally {
            timeCounter.stop();
        }
    }


    public List<DraftGallery> getGalleriesByDataCrossWidgetIds(Integer... crossWidgetIds) {
        final TimeCounter timeCounter = createTimeCounter("getGalleriesByDataCrossWidgetIds");
        try {
            return persistance.getGalleriesByDataCrossWidgetIds(crossWidgetIds);
        } finally {
            timeCounter.stop();
        }
    }


    public List<WidgetItem> getWidgetItemsByGalleriesId(final Collection<Integer> galleriesId) {
        final TimeCounter timeCounter = createTimeCounter("getPageVersionsByGalleryIds");
        try {
            return persistance.getWidgetItemsByGalleriesId(galleriesId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<DraftChildSiteRegistration> getAllChildSiteRegistrations() {
        final TimeCounter timeCounter = createTimeCounter("getAllChildSiteRegistrations");
        try {
            return persistance.getAllChildSiteRegistrations();
        } finally {
            timeCounter.stop();
        }
    }

    public void removeGalleryVideoRange(final GalleryVideoRange galleryVideoRange) {
        final TimeCounter timeCounter = createTimeCounter("removeGalleryVideoRange");
        try {
            persistance.removeGalleryVideoRange(galleryVideoRange);
        } finally {
            timeCounter.stop();
        }
    }

    public List<GalleryVideoRange> getGalleryVideoRangesByUserId(final int userId) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryVideoRangesByUserId");
        try {
            return persistance.getGalleryVideoRangesByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<GalleryVideoRange> getGalleryVideoRanges(List<Integer> videoRangeIds) {
        final TimeCounter timeCounter = createTimeCounter("getGalleryVideoRanges");
        try {
            return persistance.getGalleryVideoRanges(videoRangeIds);
        } finally {
            timeCounter.stop();
        }
    }

    private TimeCounter createTimeCounter(final String name) {
        return ServiceLocator.getTimeCounterCreator().create("persistance://" + name);
    }

    public Coordinate getCoordinate(final String zip, final Country country) {
        final TimeCounter timeCounter = createTimeCounter("getCoordinate");
        try {
            return persistance.getCoordinate(zip, country);
        } finally {
            timeCounter.stop();
        }
    }

    public void putCoordinate(Coordinate coordinate) {
        final TimeCounter timeCounter = createTimeCounter("putCoordinate");
        try {
            persistance.putCoordinate(coordinate);
        } finally {
            timeCounter.stop();
        }
    }

    public void executeAlter(Alter alter) {
        final TimeCounter timeCounter = createTimeCounter("executeAlter");
        try {
            persistance.executeAlter(alter);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void executeAlters(List<Alter> alters) {
        final TimeCounter timeCounter = createTimeCounter("executeAlters");
        try {
            persistance.executeAlters(alters);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<SiteOnItem> getSiteOnItemsByItem(final int siteItemId) {
        final TimeCounter timeCounter = createTimeCounter("getSiteOnItemsByItem");
        try {
            return persistance.getSiteOnItemsByItem(siteItemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<SiteOnItem> getSiteOnItemsBySite(final int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getSiteOnItemsBySite");
        try {
            return persistance.getSiteOnItemsBySite(siteId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public <T extends WorkItem> T getWorkItem(final Integer workSiteItemId) {
        final TimeCounter timeCounter = createTimeCounter("putWorkItem");
        try {
            return (T) persistance.getWorkItem(workSiteItemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public PaymentSettingsOwner getPaymentSettingsOwner(Integer id, PaymentSettingsOwnerType type) {
        final TimeCounter timeCounter = createTimeCounter("getPaymentSettingsOwner");
        try {
            return persistance.getPaymentSettingsOwner(id, type);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkFormItems(Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkFormItems");
        try {
            persistance.removeWorkFormItems(formId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkFilters(Integer formId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkFilters");
        try {
            persistance.removeWorkFilters(formId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putWorkFilter(WorkFormFilter workFilter) {
        final TimeCounter timeCounter = createTimeCounter("putWorkFilter");
        try {
            persistance.putWorkFilter(workFilter);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putWorkFormItem(WorkFormItem workFormItem) {
        final TimeCounter timeCounter = createTimeCounter("putWorkFormItem");
        try {
            persistance.putWorkFormItem(workFormItem);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkGalleryItems(Integer itemId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkGalleryItems");
        try {
            persistance.removeWorkGalleryItems(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkGalleryLabels(Integer itemId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkGalleryLabels");
        try {
            persistance.removeWorkGalleryLabels(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putWorkGalleryItem(WorkGalleryItem workItem) {
        final TimeCounter timeCounter = createTimeCounter("putWorkGalleryItem");
        try {
            persistance.putWorkGalleryItem(workItem);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putWorkGalleryLabel(WorkGalleryLabel workItem) {
        final TimeCounter timeCounter = createTimeCounter("putWorkGalleryLabel");
        try {
            persistance.putWorkGalleryLabel(workItem);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeDraftItemCssValues(final Integer itemId) {
        final TimeCounter timeCounter = createTimeCounter("removeDraftItemCssValues");
        try {
            persistance.removeDraftItemCssValues(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWidgetCssValues(final Integer widgetId) {
        final TimeCounter timeCounter = createTimeCounter("removeWidgetCssValues");
        try {
            persistance.removeWidgetCssValues(widgetId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkCssValues(final Integer workItemId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkCssValues");
        try {
            persistance.removeWorkCssValues(workItemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Integer> getDraftItemIds() {
        final TimeCounter timeCounter = createTimeCounter("getDraftItemIds");
        try {
            return persistance.getDraftItemIds();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public <T extends DraftItem> Map<T, List<WidgetItem>> getItemsBySiteAndUser(
            final int userId, int siteId, final ItemType type, final boolean onlyCurrentSite) {
        final TimeCounter timeCounter = createTimeCounter("getItemsBySiteAndUser");
        try {
            return persistance.getItemsBySiteAndUser(userId, siteId, type, onlyCurrentSite);
        } finally {
            timeCounter.stop();
        }
    }

    public void putGroup(Group group) {
        final TimeCounter timeCounter = createTimeCounter("putGroup");
        try {
            persistance.putGroup(group);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeGroup(Group group) {
        final TimeCounter timeCounter = createTimeCounter("removeGroup");
        try {
            persistance.removeGroup(group);
        } finally {
            timeCounter.stop();
        }
    }

    public void removeGroup(int groupId) {
        final TimeCounter timeCounter = createTimeCounter("removeGroup");
        try {
            persistance.removeGroup(groupId);
        } finally {
            timeCounter.stop();
        }
    }

    public Group getGroup(Integer groupId) {
        final TimeCounter timeCounter = createTimeCounter("getGroup");
        try {
            return persistance.getGroup(groupId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<Group> getGroups(List<Integer> groupIds) {
        final TimeCounter timeCounter = createTimeCounter("getGroups");
        try {
            return persistance.getGroups(groupIds);
        } finally {
            timeCounter.stop();
        }
    }

    public List<User> getUsersWithAccessToGroup(int groupId) {
        final TimeCounter timeCounter = createTimeCounter("getUsersWithAccessToGroup");
        try {
            return persistance.getUsersWithAccessToGroup(groupId);
        } finally {
            timeCounter.stop();
        }
    }

    public List<User> getUsersByUsersId(List<Integer> usersId) {
        final TimeCounter timeCounter = createTimeCounter("getUsersByUsersId");
        try {
            return persistance.getUsersByUsersId(usersId);
        } finally {
            timeCounter.stop();
        }
    }

    public int getUsersCountWithAccessToGroup(Integer groupId) {
        final TimeCounter timeCounter = createTimeCounter("getUsersCountWithAccessToGroup");
        try {
            return persistance.getUsersCountWithAccessToGroup(groupId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeDraftItem(final Integer itemId) {
        final TimeCounter timeCounter = createTimeCounter("removeDraftItem");
        try {
            persistance.removeDraftItem(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkAdvancedSearchOptions(final Integer itemId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkAdvancedSearchOptions");
        try {
            persistance.removeWorkAdvancedSearchOptions(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putWorkAdvancedSearchOption(final WorkAdvancedSearchOption workAdvancedSearchOption) {
        final TimeCounter timeCounter = createTimeCounter("putWorkAdvancedSearchOption");
        try {
            persistance.putWorkAdvancedSearchOption(workAdvancedSearchOption);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkManageVotesSettings(final Integer itemId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkManageVotesSettings");
        try {
            persistance.removeWorkManageVotesSettings(itemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putWorkManageVotesSettings(final WorkManageVotesSettings workManageVotesSettings) {
        final TimeCounter timeCounter = createTimeCounter("putWorkManageVotesSettings");
        try {
            persistance.putWorkManageVotesSettings(workManageVotesSettings);
        } finally {
            timeCounter.stop();
        }
    }

    public AccessibleForRender getAccessibleElement(Integer id, AccessibleElementType type) {
        final TimeCounter timeCounter = createTimeCounter("getAccessibleElement");
        try {
            return persistance.getAccessibleElement(id, type);
        } finally {
            timeCounter.stop();
        }
    }

    public void putAccessibleSettings(AccessibleSettings accessibleSettings) {
        final TimeCounter timeCounter = createTimeCounter("putAccessibleSettings");
        try {
            persistance.putAccessibleSettings(accessibleSettings);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public AccessibleSettings getAccessibleSettings(Integer accessibleSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getAccessibleSettings");
        try {
            return persistance.getAccessibleSettings(accessibleSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkItem(final WorkItem workItem) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkItem");
        try {
            persistance.removeWorkItem(workItem);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeWorkItem(Integer workItemId) {
        final TimeCounter timeCounter = createTimeCounter("removeWorkItem");
        try {
            persistance.removeWorkItem(workItemId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public PurchaseMailLog getPurchaseMailLog(Integer purchaseMailLogId) {
        final TimeCounter timeCounter = createTimeCounter("getPurchaseMailLog");
        try {
            return persistance.getPurchaseMailLog(purchaseMailLogId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putPurchaseMailLog(PurchaseMailLog purchaseMailLog) {
        final TimeCounter timeCounter = createTimeCounter("putPurchaseMailLog");
        try {
            persistance.putPurchaseMailLog(purchaseMailLog);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<PurchaseMailLog> getAllPurchaseMailLogs() {
        final TimeCounter timeCounter = createTimeCounter("getAllPurchaseMailLogs");
        try {
            return persistance.getAllPurchaseMailLogs();
        } finally {
            timeCounter.stop();
        }
    }


    @Override
    public List<FormFileShouldBeCopied> getAllFormFileShouldBeCopied() {
        final TimeCounter timeCounter = createTimeCounter("getAllFormFileShouldBeCopied");
        try {
            return persistance.getAllFormFileShouldBeCopied();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<VideoShouldBeCopied> getAllVideoShouldBeCopied() {
        final TimeCounter timeCounter = createTimeCounter("getAllVideoShouldBeCopied");
        try {
            return persistance.getAllVideoShouldBeCopied();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putVideoShouldBeCopied(VideoShouldBeCopied videoShouldBeCopied) {
        final TimeCounter timeCounter = createTimeCounter("putVideoShouldBeCopied");
        try {
            persistance.putVideoShouldBeCopied(videoShouldBeCopied);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putFormFileShouldBeCopied(FormFileShouldBeCopied formFileShouldBeCopied) {
        final TimeCounter timeCounter = createTimeCounter("putFormFileShouldBeCopied");
        try {
            persistance.putFormFileShouldBeCopied(formFileShouldBeCopied);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public UsersGroup getUsersGroup(UsersGroupId usersGroupId) {
        final TimeCounter timeCounter = createTimeCounter("getUsersGroup");
        try {
            return persistance.getUsersGroup(usersGroupId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putUsersGroup(UsersGroup usersGroup) {
        final TimeCounter timeCounter = createTimeCounter("putUsersGroup");
        try {
            persistance.putUsersGroup(usersGroup);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeUsersGroup(UsersGroup usersGroup) {
        final TimeCounter timeCounter = createTimeCounter("removeUsersGroup");
        try {
            persistance.removeUsersGroup(usersGroup);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<User> getAllUsers() {
        final TimeCounter timeCounter = createTimeCounter("getAllUsers");
        try {
            return persistance.getAllUsers();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Map<DraftItem, List<WidgetItem>> getItems(List<Integer> siteIds, ItemType itemType, SiteShowOption siteShowOption) {
        final TimeCounter timeCounter = createTimeCounter("getItems");
        try {
            return persistance.getItems(siteIds, itemType, siteShowOption);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void putPageSettings(PageSettings pageSettings) {
        final TimeCounter timeCounter = createTimeCounter("putPageSettings");
        try {
            persistance.putPageSettings(pageSettings);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public DraftPageSettings getDraftPageSettings(Integer pageSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getDraftPageSettings");
        try {
            return persistance.getDraftPageSettings(pageSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public WorkPageSettings getWorkPageSettings(Integer pageSettingsId) {
        final TimeCounter timeCounter = createTimeCounter("getWorkPageSettings");
        try {
            return persistance.getWorkPageSettings(pageSettingsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removePageSettings(PageSettings pageSettings) {
        final TimeCounter timeCounter = createTimeCounter("removePageSettings");
        try {
            persistance.removePageSettings(pageSettings);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Widget> getWidgets(List<Integer> widgetsId) {
        final TimeCounter timeCounter = createTimeCounter("getWidgetByCrossWidgetsId");
        try {
            return persistance.getWidgets(widgetsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<KeywordsGroup> getKeywordsGroups(List<Integer> keywordsGroupsId) {
        final TimeCounter timeCounter = createTimeCounter("getKeywordsGroups");
        try {
            return persistance.getKeywordsGroups(keywordsGroupsId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<SiteTitlePageName> getWorkSiteTitlePageNames(List<Integer> siteIds) {
        final TimeCounter timeCounter = createTimeCounter("getWorkSiteTitlePageNames");
        try {
            return persistance.getWorkSiteTitlePageNames(siteIds);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<SiteTitlePageName> getSiteTitlePageNamesByUserId(int userId) {
        final TimeCounter timeCounter = createTimeCounter("getSiteTitlePageNamesByUserId");
        try {
            return persistance.getSiteTitlePageNamesByUserId(userId);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<SiteTitlePageName> getSiteTitlePageNamesBySiteId(int siteId) {
        final TimeCounter timeCounter = createTimeCounter("getSiteTitlePageNamesBySiteId");
        try {
            return persistance.getSiteTitlePageNamesBySiteId(siteId);
        } finally {
            timeCounter.stop();
        }
    }


    private final Persistance persistance;
}
