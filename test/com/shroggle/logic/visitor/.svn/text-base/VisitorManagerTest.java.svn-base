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

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Assert;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.VisitorManagerInitException;
import com.shroggle.exception.VisitorOnSiteRightNotFoundException;
import com.shroggle.entity.*;

import java.util.List;
import java.util.Date;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class VisitorManagerTest {

    @Test(expected = VisitorManagerInitException.class)
    public void testBadInitialization() {
        new VisitorManager(null);
    }

    @Test
    public void testRemoveWithExistingRights() {
        final Site site = TestUtil.createSite();
        final User visitor = TestUtil.createVisitorForSite(site);

        new VisitorManager(visitor).removeVisitor();

        Assert.assertNotNull(persistance.getUserById(visitor.getUserId()));
    }

    @Test
    public void testRemove() {
        final User visitor = TestUtil.createUser();
        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        pageVisitor.setUserId(visitor.getUserId());

        final Site site = TestUtil.createSite();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm, visitor);

        ServiceLocator.getContextStorage().get().setUserId(visitor.getUserId());

        //Add some forum activity
        final DraftForum forum = TestUtil.createForum(site);
        final SubForum subForum = TestUtil.createSubForum(forum);
        final ForumThread forumThread = TestUtil.createForumThread(subForum, visitor);
        final ForumPost forumPost = TestUtil.createForumPost(forumThread, visitor);

        //Removing visitor
        new VisitorManager(visitor).removeVisitor();

        Assert.assertNull(persistance.getUserById(visitor.getUserId()));
        Assert.assertNull(persistance.getPageVisitorById(pageVisitor.getPageVisitorId()).getUserId());
        Assert.assertNull(persistance.getFilledFormById(filledForm.getFilledFormId()));
        Assert.assertNull(persistance.getForumThreadById(forumThread.getThreadId()).getAuthor());
        Assert.assertNull(persistance.getForumPostById(forumPost.getForumPostId()).getAuthor());
        Assert.assertNull(ServiceLocator.getContextStorage().get().getUserId());
    }

    @Test
    public void testCreateFilledRegistrationForm() {
        final User visitor = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);

        final List<FilledFormItem> filledItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        filledItems.get(0).setValue("new_first_name");
        filledItems.get(1).setValue("new_last_name");

        final FilledForm filledForm = new VisitorManager(visitor).createOrUpadateFilledRegistrationForm(filledItems, registrationForm, site);

        Assert.assertEquals(2, filledForm.getFilledFormItems().size());
        Assert.assertEquals("new_first_name", filledForm.getFilledFormItems().get(0).getValues().get(0));
        Assert.assertEquals("new_last_name", filledForm.getFilledFormItems().get(1).getValues().get(0));
        Assert.assertEquals("new_first_name", visitor.getFirstName());
        Assert.assertEquals("new_last_name", visitor.getLastName());
    }

    @Test
    public void testUpdateFilledRegistrationForm() {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        //Create filled form with 5 defautl items witch includes First and Last names.
        final FilledForm rightsFilledForm = TestUtil.createFilledForm(registrationForm);
        final User visitor = TestUtil.createVisitorForSite(site, false, VisitorStatus.REGISTERED, rightsFilledForm);

        final List<FilledFormItem> filledItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);
        filledItems.get(0).setValue("new_first_name");
        filledItems.get(1).setValue("new_last_name");

        final FilledForm filledForm = new VisitorManager(visitor).createOrUpadateFilledRegistrationForm(filledItems, registrationForm, site);

        Assert.assertEquals(6, filledForm.getFilledFormItems().size());
        Assert.assertEquals("new_first_name", visitor.getFirstName());
        Assert.assertEquals("new_last_name", visitor.getLastName());
    }

    @Test
    public void testActivateVisitor() {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final FilledForm rightsFilledForm = TestUtil.createFilledForm(registrationForm);
        final Date creationDate = new Date();
        final User visitor = TestUtil.createVisitorForSite(site, true, VisitorStatus.PENDING, rightsFilledForm);
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(visitor, site));
        visitorOnSiteRight.setActive(false);

        new VisitorManager(visitor).activateVisitor(site, "qwe");

        Assert.assertTrue(visitorOnSiteRight.isActive());
        Assert.assertTrue(visitorOnSiteRight.isInvited());
        Assert.assertEquals(VisitorStatus.REGISTERED, visitorOnSiteRight.getVisitorStatus());
        Assert.assertEquals("qwe", visitor.getPassword());
        Assert.assertTrue(visitor.getActiveted().getTime() >= creationDate.getTime());
    }

    @Test
    public void testAddVisitorOnSiteRight() {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final FilledForm rightsFilledForm = TestUtil.createFilledForm(registrationForm);
        final User visitor = TestUtil.createUser();

        new VisitorManager(visitor).addVisitorOnSiteRight(site, rightsFilledForm);
        final UserOnSiteRight visitorOnSiteRight = persistance.getUserOnSiteRightById(new UserOnSiteRightId(visitor, site));

        Assert.assertNotNull(visitorOnSiteRight);
        Assert.assertTrue(visitorOnSiteRight.isActive());
        Assert.assertEquals(VisitorStatus.REGISTERED, visitorOnSiteRight.getVisitorStatus());
        Assert.assertEquals(SiteAccessLevel.VISITOR, visitorOnSiteRight.getSiteAccessType());
    }

    @Test
    public void testRemoveVisitorOnSiteRight() {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final FilledForm rightsFilledForm = TestUtil.createFilledForm(registrationForm);
        final User visitor = TestUtil.createVisitorForSite(site, true, VisitorStatus.PENDING, rightsFilledForm);

        new VisitorManager(visitor).removeVisitorOnSiteRight(site.getSiteId());

        Assert.assertNull(persistance.getUserOnSiteRightById(new UserOnSiteRightId(visitor, site)));
        Assert.assertNull(persistance.getFilledFormById(rightsFilledForm.getFilledFormId()));
    }

    @Test(expected = VisitorOnSiteRightNotFoundException.class)
    public void testRemoveVisitorOnSiteRightWithoutRights() {
        final Site site = TestUtil.createSite();
        final User visitor = TestUtil.createUser();

        new VisitorManager(visitor).removeVisitorOnSiteRight(site.getSiteId());
    }

    @Test
    public void testGetVisitorUpdateDate() {
        final Site site = TestUtil.createSite();

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm(site);
        final FilledForm rightsFilledForm = TestUtil.createFilledForm(registrationForm);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final User visitor = TestUtil.createVisitorForSite(site, true, VisitorStatus.PENDING, rightsFilledForm);

        final Date d1 = new Date();
        final Date d2 = new Date(d1.getTime() + 100);
        final Date d3 = new Date(d2.getTime() + 100);
        final Date d4 = new Date(d3.getTime() + 100);

        filledForm.setFillDate(d1);
        filledForm.setUpdatedDate(d2);
        rightsFilledForm.setFillDate(d3);
        rightsFilledForm.setUpdatedDate(d4);

        final Date updateDate = new VisitorManager(visitor).getVisitorUpdateDate();
        Assert.assertEquals(d4, updateDate);
    }

    @Test
    public void testAddFilledFormToVisitor() {
        final Site site = TestUtil.createSite();
        final User visitor = TestUtil.createUser();

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        customForm.setDescription("asd");
        final List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.FIRST_NAME, FormItemName.LAST_NAME);

        new VisitorManager(visitor).addFilledFormToVisitor(filledFormItems, customForm);
        
        Assert.assertEquals(1, visitor.getFilledForms().size());
        Assert.assertEquals(customForm.getFormId(), visitor.getFilledForms().get(0).getFormId());
        Assert.assertEquals(filledFormItems, visitor.getFilledForms().get(0).getFilledFormItems());
        Assert.assertEquals(visitor, visitor.getFilledForms().get(0).getUser());
        Assert.assertEquals(customForm.getType(), visitor.getFilledForms().get(0).getType());
        Assert.assertEquals("asd", visitor.getFilledForms().get(0).getFormDescription());
    }

    final private Persistance persistance = ServiceLocator.getPersistance();
}
