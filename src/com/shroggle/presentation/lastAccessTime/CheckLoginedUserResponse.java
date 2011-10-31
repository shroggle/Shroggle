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
package com.shroggle.presentation.lastAccessTime;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class CheckLoginedUserResponse {

    public CheckLoginedUserResponse() {
    }

    public CheckLoginedUserResponse(String currentAccessTime, long delay) {
        this.currentAccessTimeMd5 = currentAccessTime;
        this.delay = delay;
    }

    @RemoteProperty
    private String currentAccessTimeMd5;

    @RemoteProperty
    private long delay;

    public String getCurrentAccessTimeMd5() {
        return currentAccessTimeMd5;
    }

    public void setCurrentAccessTimeMd5(String currentAccessTimeMd5) {
        this.currentAccessTimeMd5 = currentAccessTimeMd5;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
