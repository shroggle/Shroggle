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
package com.shroggle.logic.visitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.visitor.SearchVisitorManager;

import java.util.List;
import java.util.Date;

import junit.framework.Assert;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SearchVisitorManagerTest {

    final SearchVisitorManager searchVisitorManager = new SearchVisitorManager();

    @Test
    public void getVisitorBySiteIdAndStatus() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final User reg = TestUtil.createVisitorForSite(site);
        final User inv = TestUtil.createVisitorForSite(site, true);
        final User exp = TestUtil.createVisitorForSite(site, false, VisitorStatus.EXPIRED);
        final User pend = TestUtil.createVisitorForSite(site, false, VisitorStatus.PENDING);

        List<User> returnList = searchVisitorManager.searchVisitorsByStatus(RegistrantFilterType.SHOW_ALL, site.getSiteId());
        Assert.assertEquals(4, returnList.size());
        Assert.assertTrue(returnList.contains(reg));
        Assert.assertTrue(returnList.contains(inv));
        Assert.assertTrue(returnList.contains(exp));
        Assert.assertTrue(returnList.contains(pend));

        returnList = searchVisitorManager.searchVisitorsByStatus(RegistrantFilterType.REGISTERED, site.getSiteId());
        Assert.assertEquals(2, returnList.size());
        Assert.assertTrue(returnList.contains(reg));

        returnList = searchVisitorManager.searchVisitorsByStatus(RegistrantFilterType.INVITED, site.getSiteId());
        Assert.assertEquals(1, returnList.size());
        Assert.assertTrue(returnList.contains(inv));

        returnList = searchVisitorManager.searchVisitorsByStatus(RegistrantFilterType.EXPIRED, site.getSiteId());
        Assert.assertEquals(1, returnList.size());
        Assert.assertTrue(returnList.contains(exp));

        returnList = searchVisitorManager.searchVisitorsByStatus(RegistrantFilterType.PENDING, site.getSiteId());
        Assert.assertEquals(1, returnList.size());
        Assert.assertTrue(returnList.contains(pend));
    }

    @Test
    public void search() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftForm form = TestUtil.createRegistrationForm(site.getSiteId());
        FilledForm filledForm = TestUtil.createFilledForm(form);

        final User user = TestUtil.createVisitorForSite(site, false, VisitorStatus.REGISTERED, filledForm);
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("e");
        user.setRegistrationDate(new Date());

        form = TestUtil.createRegistrationForm(site.getSiteId());
        filledForm = new FilledForm();
        filledForm.setFormId(form.getFormId());
        filledForm.setType(FormType.REGISTRATION);
        filledForm.setFilledFormItems(TestUtil.createDefaultRegistrationFilledFormItems("Visitor2FN",
                "Visitor2LN", "Visitor2SN", "Visitor2TN", "Visitor2E"));
        ServiceLocator.getPersistance().putFilledForm(filledForm);

        final User visitor2 = TestUtil.createVisitorForSite(site, false, VisitorStatus.REGISTERED, filledForm);
        visitor2.setFirstName("fn2");
        visitor2.setLastName("ln2");
        visitor2.setEmail("e2");
        visitor2.setRegistrationDate(new Date());

        List<User> foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "", site.getSiteId());
        Assert.assertEquals(2, foundUsers.size());
        Assert.assertEquals((int) user.getUserId(), foundUsers.get(0).getUserId());
        Assert.assertEquals((int) visitor2.getUserId(), foundUsers.get(1).getUserId());

        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "2F", site.getSiteId());
        Assert.assertEquals(1, foundUsers.size());
        Assert.assertEquals((int) visitor2.getUserId(), foundUsers.get(0).getUserId());

        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.PENDING, "2F", site.getSiteId());
        Assert.assertTrue(foundUsers.isEmpty());

        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "User", site.getSiteId());
        Assert.assertEquals(1, foundUsers.size());
        // Assert.assertEquals(visitor2.getUserId(), registeredVisitorInfos.get(0).getUserId());
        // Assert.assertEquals(user.getUserId(), registeredVisitorInfos.get(1).getUserId());
    }

    @Test
    public void search_withTwoSearchKeys() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftForm form = TestUtil.createRegistrationForm(site.getSiteId());
        FilledForm filledForm = TestUtil.createFilledForm(form);

        final User user = TestUtil.createVisitorForSite(site, false, VisitorStatus.REGISTERED, filledForm);
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("e");
        user.setRegistrationDate(new Date());

        form = TestUtil.createRegistrationForm(site.getSiteId());
        filledForm = new FilledForm();
        filledForm.setFormId(form.getFormId());
        filledForm.setType(FormType.REGISTRATION);
        filledForm.setFilledFormItems(TestUtil.createDefaultRegistrationFilledFormItems("Visitor2FN",
                "Visitor2LN 2", "Visitor2SN", "Visitor2TN", "Visitor2E"));
        ServiceLocator.getPersistance().putFilledForm(filledForm);


        final User visitor2 = TestUtil.createVisitorForSite(site, false, VisitorStatus.REGISTERED, filledForm);
        visitor2.setFirstName("fn2");
        visitor2.setLastName("ln2");
        visitor2.setEmail("e2");
        visitor2.setRegistrationDate(new Date());

        List<User> foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "", site.getSiteId());
        Assert.assertEquals(2, foundUsers.size());
        Assert.assertEquals((int) user.getUserId(), foundUsers.get(0).getUserId());
        Assert.assertEquals((int) visitor2.getUserId(), foundUsers.get(1).getUserId());

        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "Visitor2LN", site.getSiteId());
        Assert.assertEquals(1, foundUsers.size());
        Assert.assertEquals((int) visitor2.getUserId(), foundUsers.get(0).getUserId());


        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "Visitor2LN 2", site.getSiteId());
        Assert.assertEquals(1, foundUsers.size());
        Assert.assertEquals((int) visitor2.getUserId(), foundUsers.get(0).getUserId());

        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.PENDING, "2F", site.getSiteId());
        Assert.assertTrue(foundUsers.isEmpty());

        foundUsers = searchVisitorManager.searchVisitorsByStatusAndSearchKey(RegistrantFilterType.SHOW_ALL, "User Last", site.getSiteId());
        Assert.assertEquals(1, foundUsers.size());
        //Assert.assertEquals(user.getUserId(), registeredVisitorInfos.get(0).getUserId());
        //Assert.assertEquals(visitor2.getUserId(), registeredVisitorInfos.get(1).getUserId());
    }

}
