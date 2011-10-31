package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteCopierBlueprintRealTest {

    @Test
    public void executeCheckUserRights() {
        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setDefaultFormId(TestUtil.createCustomForm(blueprint).getId());
        final User user1 = TestUtil.createUserAndUserOnSiteRight(blueprint);
        final User user2 = TestUtil.createUserAndUserOnSiteRight(blueprint);
        final User user3 = TestUtil.createUserAndUserOnSiteRight(blueprint, SiteAccessLevel.VISITOR);
        final User user4 = TestUtil.createUserAndUserOnSiteRight(blueprint);
        user4.getUserOnSiteRights().get(0).setActive(false);

        siteCopierBlueprint.execute(blueprint);

        Assert.assertEquals("We should copy user rights!", 2, user1.getUserOnSiteRights().size());
        Assert.assertEquals("We should copy user rights!", 2, user2.getUserOnSiteRights().size());
        Assert.assertEquals("We should not copy user rights for visitors!", 1, user3.getUserOnSiteRights().size());
        Assert.assertEquals("We should not copy user rights not active!", 1, user4.getUserOnSiteRights().size());
    }

    @Test
    public void executeCheckTitle() {
        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setTitle("fff");
        blueprint.setDefaultFormId(TestUtil.createCustomForm(blueprint).getId());

        final Site copiedBlueprint = siteCopierBlueprint.execute(blueprint);

        Assert.assertEquals("fff_copy", copiedBlueprint.getTitle());
    }

    @Test
    public void executeCheckDraftPages() {
        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setDefaultFormId(TestUtil.createCustomForm(blueprint).getId());
        final Page page1 = TestUtil.createPage(blueprint);
        blueprint.getMenu().addChild(new DraftMenuItem());
        blueprint.getMenu().getMenuItems().get(0).setPageId(page1.getPageId());
        blueprint.getMenu().getMenuItems().get(0).setName("ff");

        final Site copiedBlueprint = siteCopierBlueprint.execute(blueprint);

        Assert.assertEquals(1, copiedBlueprint.getPages().size());
        final Page copiedPage1 = copiedBlueprint.getPages().get(0);
        Assert.assertNotSame(blueprint.getDefaultFormId(), copiedBlueprint.getDefaultFormId());
        Assert.assertNotNull(persistance.getDraftItem(copiedBlueprint.getDefaultFormId()));
        Assert.assertNotSame(page1, copiedPage1);
        Assert.assertNotSame(blueprint.getMenu(), copiedBlueprint.getMenu());
        Assert.assertEquals(1, copiedBlueprint.getMenu().getMenuItems().size());
        TestUtil.assertIntAndBigInt(copiedPage1.getPageId(), copiedBlueprint.getMenu().getMenuItems().get(0).getPageId());
        Assert.assertEquals("ff", copiedBlueprint.getMenu().getMenuItems().get(0).getName());
    }

    @Test
    public void executeWithGallery() {
        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setDefaultFormId(TestUtil.createCustomForm(blueprint).getId());
        final Page page1 = TestUtil.createPage(blueprint);

        final WidgetItem widgetItem = new WidgetItem();
        page1.getPageSettings().addWidget(widgetItem);
        persistance.putWidget(widgetItem);

        final DraftGallery draftGallery = new DraftGallery();
        draftGallery.setName("ff");
        draftGallery.setSiteId(blueprint.getId());
        draftGallery.setFormId1(blueprint.getDefaultFormId());
        persistance.putItem(draftGallery);
        widgetItem.setDraftItem(draftGallery);

        final Site copiedBlueprint = siteCopierBlueprint.execute(blueprint);

        Assert.assertEquals(1, copiedBlueprint.getPages().size());
        final Page copiedPage1 = copiedBlueprint.getPages().get(0);
        Assert.assertEquals(1, copiedPage1.getPageSettings().getWidgets().size());

        final WidgetItem copiedWidgetItem = (WidgetItem) copiedPage1.getPageSettings().getWidgets().get(0);
        Assert.assertNotSame(widgetItem, copiedWidgetItem);
        Assert.assertNotSame(widgetItem.getDraftItem(), copiedWidgetItem.getDraftItem());
        Assert.assertEquals("ff_copy1", copiedWidgetItem.getDraftItem().getName());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SiteCopierBlueprint siteCopierBlueprint = new SiteCopierBlueprintReal();

}
