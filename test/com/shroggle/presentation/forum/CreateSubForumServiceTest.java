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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.SubForum;
import com.shroggle.entity.User;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.CannotFindSubForumException;
import com.shroggle.exception.ForumNotFoundException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

@RunWith(value = TestRunnerWithMockServices.class)
public class CreateSubForumServiceTest {

    private CreateSubForumService createSubForumService = new CreateSubForumService();
    private CreateSubForumRequest createSubForumRequest = new CreateSubForumRequest();
    private Persistance persistance = ServiceLocator.getPersistance();
    private SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

    @Test
    public void show() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();

        MockWebContext mockWebContext = (MockWebContext) createSubForumService.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        Assert.assertEquals("/forum/showCreateSubForumForm.jsp",
                createSubForumService.showCreateSubForumForm(widgetForum.getDraftItem().getId(), -1, false));
    }

    @Test(expected = ForumNotFoundException.class)
    public void executeWithoutWidget() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        createSubForumRequest.setSubForumName("SF name");
        createSubForumRequest.setSubForumDescription("SF desc");
        createSubForumService.execute(createSubForumRequest, 0, -1, false);
    }

    @Test
    public void excecuteWithoutForumName() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();

        createSubForumRequest.setSubForumDescription("Forum descritpion");
        Assert.assertEquals("SubForumWithNullOrEmptyNameException",
                createSubForumService.execute(createSubForumRequest, widgetForum.getWidgetId(), -1, false));
    }

    @Test
    public void executeWitoutForumDescription() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();

        createSubForumRequest.setSubForumName("Subforum Name");
        Assert.assertEquals("SubForumWithNullOrEmptyDescriptionException",
                createSubForumService.execute(createSubForumRequest, widgetForum.getWidgetId(), -1, false));
    }

    @Test
    public void executeWithCorrectData() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        WidgetItem widgetForum = (WidgetItem) TestUtil.createForumWidget();

        createSubForumRequest.setSubForumName("Subforum Name");
        createSubForumRequest.setSubForumDescription("Subforum Description");
        Assert.assertEquals("/forum/widgetForum.action?forumId=" + widgetForum.getDraftItem().getId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                createSubForumService.execute(createSubForumRequest, widgetForum.getDraftItem().getId(), -1, false));
        SubForum subForum = persistance.getSubForumsByForumId(widgetForum.getDraftItem().getId()).get(0);
        Assert.assertEquals(subForum.getSubForumName(), "Subforum Name");
        Assert.assertEquals(subForum.getSubForumDescription(), "Subforum Description");
    }

    @Test(expected = CannotFindSubForumException.class)
    public void renameWithoutSubForumId() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        RenameSubForumRequest renameSubForumRequest = new RenameSubForumRequest();
        renameSubForumRequest.setSubForumName("new name");
        renameSubForumRequest.setSubForumDescription("new desc");

        createSubForumService.rename(renameSubForumRequest, -1, false);
    }

    @Test
    public void renameWithoutSubForumName() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        SubForum subForum = TestUtil.createSubForum();

        RenameSubForumRequest renameSubForumRequest = new RenameSubForumRequest();
        renameSubForumRequest.setSubForumDescription("new desc");
        renameSubForumRequest.setSubForumId(subForum.getSubForumId());

        Assert.assertEquals("EmptySubForumNameException", createSubForumService.rename(renameSubForumRequest, -1, false));
    }

    @Test
    public void renameWithoutSubForumDesc() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        SubForum subForum = TestUtil.createSubForum();

        RenameSubForumRequest renameSubForumRequest = new RenameSubForumRequest();
        renameSubForumRequest.setSubForumName("new name");
        renameSubForumRequest.setSubForumId(subForum.getSubForumId());

        Assert.assertEquals("EmptySubForumDescriptionException", createSubForumService.rename(renameSubForumRequest, -1, false));
    }

    @Test
    public void renameCorrectData() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        SubForum subForum = TestUtil.createSubForum();

        RenameSubForumRequest renameSubForumRequest = new RenameSubForumRequest();
        renameSubForumRequest.setSubForumName("new name");
        renameSubForumRequest.setSubForumDescription("new desc");
        renameSubForumRequest.setSubForumId(subForum.getSubForumId());

        Assert.assertEquals("/forum/widgetForum.action?forumId=" + subForum.getForum().getId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                createSubForumService.rename(renameSubForumRequest, -1, false));
        Assert.assertEquals("new name", subForum.getSubForumName());
        Assert.assertEquals("new desc", subForum.getSubForumDescription());
    }

    @Test(expected = CannotFindSubForumException.class)
    public void deleteWithoutSubForumId() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        createSubForumService.deleteSubForum(-1, -1, false);
    }

    @Test(expected = ForumNotFoundException.class)
    public void deleteWithoutForum() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        SubForum subForum = new SubForum();
        subForum.setSubForumDescription("desc");
        subForum.setSubForumName("name");
        persistance.putSubForum(subForum);

        createSubForumService.deleteSubForum(subForum.getSubForumId(), -1, false);
    }

    @Test
    public void deleteCorrectData() throws ServletException, IOException {
        TestUtil.createUserAndLogin();
        User account = new User();
        persistance.putUser(account);

        SubForum subForum = TestUtil.createSubForum();

        Assert.assertEquals("/forum/widgetForum.action?forumId=" + subForum.getForum().getId() + "&widgetId=-1" + "&isShowOnUserPages=false",
                createSubForumService.deleteSubForum(subForum.getSubForumId(), -1, false));
        Assert.assertTrue(persistance.getSubForumsByForumId(subForum.getForum().getId()).isEmpty());
    }
}
