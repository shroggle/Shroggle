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

package com.shroggle.presentation.forum;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

@DataTransferObject
public class CreateThreadRequest {

    @RemoteProperty
    private String threadName;

    @RemoteProperty
    private String threadDescription;

    @RemoteProperty
    private String forumPostText;

    @RemoteProperty
    private String pollQuestion;

    @RemoteProperty
    private List<String> pollAnswers;

    @RemoteProperty
    private boolean isPoll;

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getThreadDescription() {
        return threadDescription;
    }

    public void setThreadDescription(String threadDescription) {
        this.threadDescription = threadDescription;
    }

    public String getForumPostText() {
        return forumPostText;
    }

    public void setForumPostText(String forumPostText) {
        this.forumPostText = forumPostText;
    }

    public void setPollAnswers(List<String> pollAnswers) {
        this.pollAnswers = pollAnswers;
    }

    public List<String> getPollAnswers() {
        return pollAnswers;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public boolean isPoll() {
        return isPoll;
    }

    public void setPoll(boolean poll) {
        isPoll = poll;
    }
}
