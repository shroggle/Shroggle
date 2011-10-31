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

package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class SendContactUsInfoResponse {

    @RemoteProperty
    private int filledFormId;

    @RemoteProperty
    private String nextPageHtml;

    @RemoteProperty
    private boolean showSuccessfullSubmitMessage;

    public boolean isShowSuccessfullSubmitMessage() {
        return showSuccessfullSubmitMessage;
    }

    public void setShowSuccessfullSubmitMessage(boolean showSuccessfullSubmitMessage) {
        this.showSuccessfullSubmitMessage = showSuccessfullSubmitMessage;
    }

    public String getNextPageHtml() {
        return nextPageHtml;
    }

    public void setNextPageHtml(String nextPageHtml) {
        this.nextPageHtml = nextPageHtml;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

}