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
package com.shroggle.logic.site.item;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.Copier;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutor;
import com.shroggle.util.persistance.Persistance;

import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class CopierStackExecutorItemFull implements CopierStackExecutor {

    public CopierStackExecutorItemFull(
            final ItemNaming itemNaming, final int siteId,
            final Map<Integer, Integer> pageIds) {
        executorItem = new CopierStackExecutorItem(itemNaming, siteId, pageIds);
    }

    @Override
    public void copy(final CopierStack stack, final Object original) {
        executorItem.copy(stack, original);

        final Object copy = stack.copy(original);

        if (original instanceof DraftBlog) {
            final DraftBlog draftBlog = (DraftBlog) original;
            final DraftBlog copiedDraftBlog = (DraftBlog) copy;
            for (final BlogPost blogPost : draftBlog.getBlogPosts()) {
                final BlogPost copiedBlogPost = new BlogPost();
                copier.execute(blogPost, copiedBlogPost, "BlogPostId", "Comments", "Blog");
                copiedDraftBlog.addBlogPost(copiedBlogPost);
                persistance.putBlogPost(copiedBlogPost);

                for (final Comment comment : blogPost.getComments()) {
                    if (comment.getParentComment() == null) {
                        copyComment(copiedBlogPost, comment, null);
                    }
                }
            }
        }

        if (original instanceof DraftForm) {
            final DraftForm draftForm = (DraftForm) original;
            final DraftForm copiedDraftForm = (DraftForm) copy;
            final List<FilledForm> filledForms = persistance.getFilledFormsByFormId(draftForm.getId());

            for (final FilledForm filledForm : filledForms) {
                final FilledForm copiedFilledForm = new FilledForm();
                copier.execute(filledForm, copiedFilledForm,
                        "FilledFormId", "PageVisitor", "FilledFormItems", "Comments", "User");
                copiedFilledForm.setFormId(copiedDraftForm.getId());
                persistance.putFilledForm(copiedFilledForm);

                for (final FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
                    final FilledFormItem copiedFilledFormItem = new FilledFormItem();
                    copier.execute(filledFormItem, copiedFilledFormItem, "ItemId", "FilledForm", "FormItemId");
                    final FormItem formItem = persistance.getFormItemById(filledFormItem.getFormItemId());
                    copiedFilledFormItem.setFormItemId(stack.copy(formItem).getFormItemId());
                    copiedFilledFormItem.setValue(filledFormItem.getValue());
                    copiedFilledForm.addFilledFormItem(copiedFilledFormItem);
                    persistance.putFilledFormItem(copiedFilledFormItem);
                }
            }
        }

        if (original instanceof DraftForum) {
            final DraftForum draftForum = (DraftForum) original;
            final DraftForum copiedDraftForum = (DraftForum) copy;
            for (final SubForum subForum : draftForum.getSubForums()) {
                final SubForum copiedSubForum = new SubForum();
                copier.execute(subForum, copiedSubForum, "SubForumId", "ForumThreads", "Forum");
                copiedDraftForum.addSubForum(copiedSubForum);
                persistance.putSubForum(copiedSubForum);

                for (final ForumThread forumThread : subForum.getForumThreads()) {
                    final ForumThread copiedForumThread = new ForumThread();
                    copier.execute(forumThread, copiedForumThread,
                            "SubForum", "ThreadId", "ForumPosts", "Author", "PollVotes");
                    copiedForumThread.setAuthor(forumThread.getAuthor());
                    copiedSubForum.addForumThread(copiedForumThread);
                    persistance.putForumThread(copiedForumThread);

                    for (final ForumPost forumPost : forumThread.getForumPosts()) {
                        final ForumPost copiedForumPost = new ForumPost();
                        copier.execute(forumPost, copiedForumPost, "ForumPostId", "Thread", "Author", "PageVisitor");
                        copiedForumPost.setAuthor(forumPost.getAuthor());
                        copiedForumPost.setPageVisitor(forumPost.getPageVisitor());
                        copiedForumThread.addForumPost(copiedForumPost);
                        persistance.putForumPost(copiedForumPost);
                    }

                    for (final ForumPollVote forumPollVote : forumThread.getPollVotes()) {
                        final ForumPollVote copiedForumPollVote = new ForumPollVote();
                        copier.execute(forumPollVote, copiedForumPollVote, "Thread", "VoteId");
                        copiedForumThread.addPollVote(copiedForumPollVote);
                        persistance.putVote(copiedForumPollVote);
                    }
                }
            }
        }
    }

    private void copyComments(
            final BlogPost copiedBlogPost, final List<Comment> comments,
            final Comment copiedQuestionComment) {
        for (final Comment comment : comments) {
            copyComment(copiedBlogPost, comment, copiedQuestionComment);
        }
    }

    private void copyComment(
            final BlogPost copiedBlogPost, final Comment comment, final Comment copiedQuestionComment) {
        final Comment copiedComment = new Comment();
        copier.execute(comment, copiedComment,
                "BlogPost", "QuestionComment", "AnswerComments", "CommentId");
        copiedBlogPost.addComment(copiedComment);
        if (copiedQuestionComment != null) {
            copiedQuestionComment.addChildComment(copiedComment);
        }
        persistance.putComment(copiedComment);

        copyComments(copiedBlogPost, comment.getAnswerComments(), copiedComment);
    }

    private final CopierStackExecutorItem executorItem;
    private final Copier copier = ServiceLocator.getCopier();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
