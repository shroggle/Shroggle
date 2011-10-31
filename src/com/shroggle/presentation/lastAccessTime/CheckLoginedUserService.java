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

import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.MD5;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.exception.UserNotLoginedException;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;


@RemoteProxy
public class CheckLoginedUserService extends AbstractService {

    @RemoteMethod
    public CheckLoginedUserResponse execute(final String lastAccessTimeByThisServiceMd5) {
        new UsersManager().getLogined();

        final long lastAccessedTimeToSession = ServiceLocator.getSessionStorage().getLastAccessedTime(this);

        // If last access to session was through this service - it means that in given interval of
        // time (usually 30 minutes) user has no any activities. So we must show him login window.
        if (lastAccessTimeByThisServiceMd5.equals(createMd5(lastAccessedTimeToSession))) {
            throw new UserNotLoginedException("Your session has been expired!");
        }

        final long currentTime = System.currentTimeMillis();
        final long timeWithoutAcces = (currentTime - lastAccessedTimeToSession);

        // Now we have time without access to server and we know ssession life time so we can calculate when session
        // will be closed.
        if(timeWithoutAcces >= SESSION_LIFE_TIME){
            throw new UserNotLoginedException("Time without access to server is more than session life time, " +
                    "but session was not closed. Please, check SESSION_LIFE_TIME parameter in " +
                    "CheckLoginedUserService and compare it to real session life time.");
        }

        // I`ve added five minutes to be sure that session will be closed.
        final long sessionWillBeClosedAfter = (SESSION_LIFE_TIME - timeWithoutAcces) + FIVE_MINUTES;
        final String currentAccessTimeHash = createMd5(currentTime);

        return new CheckLoginedUserResponse(currentAccessTimeHash, sessionWillBeClosedAfter);
    }

    private String createMd5(final long lastAccessTime) {
        // I am rounding milliseconds to minutes.
        final long lastAccessTimeInMinutes = (lastAccessTime / 1000) / 60;
        // And creating md5 by this minutes.
        return MD5.crypt(String.valueOf(lastAccessTimeInMinutes));
    }

    private final static int SESSION_LIFE_TIME = 30 * 60 * 1000;
    private final static int FIVE_MINUTES = 5 * 60 * 1000;
}