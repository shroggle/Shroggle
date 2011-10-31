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
package com.shroggle.logic.site;

import com.shroggle.entity.PublicBlueprintsSettings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class PublicBlueprintsSettingsManagerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_withoutPublicBlueprintsSettings() throws Exception {
        new PublicBlueprintsSettingsManager(null);
    }

    @Test
    public void testGetDescription() throws Exception {
        final PublicBlueprintsSettings publicBlueprintsSettings = new PublicBlueprintsSettings();
        publicBlueprintsSettings.setDescription("description");

        Assert.assertEquals("description", new PublicBlueprintsSettingsManager(publicBlueprintsSettings).getDescription());
    }

    @Test
    public void testGetPublished() throws Exception {
        final PublicBlueprintsSettings publicBlueprintsSettings = new PublicBlueprintsSettings();
        final Date published = new Date();
        publicBlueprintsSettings.setPublished(published);

        Assert.assertEquals(published, new PublicBlueprintsSettingsManager(publicBlueprintsSettings).getPublished());
    }

    @Test
    public void testGetActivated() throws Exception {
        final PublicBlueprintsSettings publicBlueprintsSettings = new PublicBlueprintsSettings();
        final Date activated = new Date();
        publicBlueprintsSettings.setActivated(activated);

        Assert.assertEquals(activated, new PublicBlueprintsSettingsManager(publicBlueprintsSettings).getActivated());
    }

    @Test
    public void testSetDescription() throws Exception {
        final PublicBlueprintsSettings publicBlueprintsSettings = new PublicBlueprintsSettings();
        final PublicBlueprintsSettingsManager manager = new PublicBlueprintsSettingsManager(publicBlueprintsSettings);
        manager.setDescription("desc");

        Assert.assertEquals("desc", manager.getDescription());
    }

    @Test
    public void testPublish() throws Exception {
        final PublicBlueprintsSettingsManager manager = new PublicBlueprintsSettingsManager(new PublicBlueprintsSettings());

        Assert.assertNull(manager.getPublished());
        manager.publish();
        Assert.assertNotNull(manager.getPublished());
    }

    @Test
    public void testRemovePublishing() throws Exception {
        final PublicBlueprintsSettingsManager manager = new PublicBlueprintsSettingsManager(new PublicBlueprintsSettings());

        Assert.assertNull(manager.getPublished());
        manager.publish();
        Assert.assertNotNull(manager.getPublished());
        
        manager.removePublishing();
        Assert.assertNull(manager.getPublished());
    }

    @Test
    public void testActivate() throws Exception {
        final PublicBlueprintsSettingsManager manager = new PublicBlueprintsSettingsManager(new PublicBlueprintsSettings());

        Assert.assertNull(manager.getActivated());
        manager.activate();
        Assert.assertNotNull(manager.getActivated());
    }
}
