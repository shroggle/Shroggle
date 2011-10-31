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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockSessionStorage;
import com.shroggle.presentation.lastAccessTime.CheckLoginedUserService;
import com.shroggle.presentation.lastAccessTime.CheckLoginedUserResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.MD5;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(TestRunnerWithMockServices.class)
public class CheckLoginedUserServiceTest {

    @Test
    public void execute_forTheFirstTime() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        final long lastAccessTimeByService = System.currentTimeMillis();
        final long lastAccessTime = lastAccessTimeByService - (20 * 60 * 1000);// current time - 20 minutes
        sessionStorage.setLastAccessedTime(lastAccessTime);



        final CheckLoginedUserResponse response = service.execute("");



        Assert.assertNotNull(response);
        Assert.assertEquals(MD5.crypt(String.valueOf((lastAccessTimeByService / 1000) / 60)), response.getCurrentAccessTimeMd5());

        final long expectedDelay = (15 * 60 * 1000);// expected delay is 15 minutes because last access to server was 20
        // minutes ago and server life time = 30 minutes (I add 5 minutes just in case)

        final long realDelay = response.getDelay();
        final long differenceBetweenExpectedAndRealResults = expectedDelay - realDelay;

        Assert.assertTrue(differenceBetweenExpectedAndRealResults <= 1000);
    }

    @Test
    public void execute_forTheSecondTime_withAccessByUser_lastAccessTimeNotEqual() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        final long currentTime = System.currentTimeMillis();
        final long lastAccessTimeByService = currentTime - (20 * 60 * 1000);// current time - 20 minutes
        sessionStorage.setLastAccessedTime(lastAccessTimeByService);


        final long lastAccessTimeByUser = currentTime - (10 * 60 * 1000);// current time - 10 minutes
        final String lastAccessTimeByUserMd5 = MD5.crypt(String.valueOf(((lastAccessTimeByUser / 1000) / 60)));

        final CheckLoginedUserResponse response = service.execute(lastAccessTimeByUserMd5);



        Assert.assertNotNull(response);
        Assert.assertEquals(MD5.crypt(String.valueOf((currentTime / 1000) / 60)), response.getCurrentAccessTimeMd5());

        final long expectedDelay = (15 * 60 * 1000);// expected delay is 15 minutes because last access to server was 20
        // minutes ago and server life time = 30 minutes (I add 5 minutes just in case)

        final long realDelay = response.getDelay();
        final long differenceBetweenExpectedAndRealResults = expectedDelay - realDelay;

        Assert.assertTrue(differenceBetweenExpectedAndRealResults <= 100);
    }

    @Test
    public void execute_forTheFirstTime_andForTheSecondTime_inOneMethod() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        final long lastAccessTimeByService = System.currentTimeMillis();
        final long lastAccessTime = lastAccessTimeByService - (20 * 60 * 1000);// current time - 20 minutes
        sessionStorage.setLastAccessedTime(lastAccessTime);



        final CheckLoginedUserResponse response = service.execute("");// First time.


        boolean exceptionThrown = false;
        try{
            service.execute(response.getCurrentAccessTimeMd5());// Second time. Without modifying last access time.
        } catch(UserNotLoginedException e){
             exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
    }
    
    @Test(expected = UserNotLoginedException.class)
    public void execute_forTheSecondTime_withEqualLastAccessTimeMd5() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        final long currentTime = System.currentTimeMillis();
        final long lastAccessTime = currentTime - (20 * 60 * 1000);// current time - 20 minutes
        sessionStorage.setLastAccessedTime(lastAccessTime);
        final String lastAccessTimeMd5 = MD5.crypt(String.valueOf(((lastAccessTime / 1000) / 60)));



        service.execute(lastAccessTimeMd5);
    }

    @Test(expected = UserNotLoginedException.class)
    public void execute_forTheSecondTime_withTimeWithoutAccessMoreThanSessionLifeTime() throws ServletException, IOException {
        TestUtil.createUserAndLogin();

        final long currentTime = System.currentTimeMillis();
        final long lastAccessTime = currentTime - (40 * 60 * 1000);// current time - 40 minutes (and session life time = 30 minutes)
        sessionStorage.setLastAccessedTime(lastAccessTime);


        service.execute("adsfasrearawerq");
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws ServletException, IOException {
        service.execute("");
    }

    private final MockSessionStorage sessionStorage = (MockSessionStorage) ServiceLocator.getSessionStorage();
    private final CheckLoginedUserService service = new CheckLoginedUserService();

}