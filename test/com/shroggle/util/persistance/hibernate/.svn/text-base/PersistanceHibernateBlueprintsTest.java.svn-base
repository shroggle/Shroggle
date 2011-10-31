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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.TestUtil;
import com.shroggle.entity.BlueprintCategory;
import com.shroggle.entity.Site;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class PersistanceHibernateBlueprintsTest extends HibernatePersistanceTestBase {

    @Test
    public void testGetPublished() throws Exception {
        final Site site1 = TestUtil.createBlueprint();
        final Site site2 = TestUtil.createBlueprint();
        final Site site3 = TestUtil.createBlueprint();
        final Site site4 = TestUtil.createBlueprint();

        site1.getPublicBlueprintsSettings().setPublished(new Date());
        site4.getPublicBlueprintsSettings().setPublished(new Date());


        final List<Site> publishedSites = persistance.getPublishedBlueprints();
        Assert.assertEquals(2, publishedSites.size());

        Assert.assertTrue(publishedSites.contains(site1));
        Assert.assertFalse(publishedSites.contains(site2));
        Assert.assertFalse(publishedSites.contains(site3));
        Assert.assertTrue(publishedSites.contains(site4));
    }

    @Test
    public void testGetActiveBlueprints_withNullCategory() throws Exception {
        final Site site1 = TestUtil.createBlueprint();
        final Site site2 = TestUtil.createBlueprint();
        final Site site3 = TestUtil.createBlueprint();
        final Site site4 = TestUtil.createBlueprint();

        site1.getPublicBlueprintsSettings().setActivated(new Date());
        site4.getPublicBlueprintsSettings().setActivated(new Date());

        site1.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.ALTERNATIVE_THERAPISTS);
        site4.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.BUSINESS_SERVICES);

        final List<Site> publishedSites = persistance.getActiveBlueprints(null);
        Assert.assertEquals(2, publishedSites.size());

        Assert.assertTrue(publishedSites.contains(site1));
        Assert.assertFalse(publishedSites.contains(site2));
        Assert.assertFalse(publishedSites.contains(site3));
        Assert.assertTrue(publishedSites.contains(site4));
    }

    @Test
    public void testGetActiveBlueprints_withNullCategory_BUSINESS_SERVICES() throws Exception {
        final Site site1 = TestUtil.createBlueprint();
        final Site site2 = TestUtil.createBlueprint();
        final Site site3 = TestUtil.createBlueprint();
        final Site site4 = TestUtil.createBlueprint();

        site1.getPublicBlueprintsSettings().setActivated(new Date());
        site4.getPublicBlueprintsSettings().setActivated(new Date());

        site1.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.ALTERNATIVE_THERAPISTS);
        site4.getPublicBlueprintsSettings().setBlueprintCategory(BlueprintCategory.BUSINESS_SERVICES);

        final List<Site> publishedSites = persistance.getActiveBlueprints(BlueprintCategory.BUSINESS_SERVICES);
        Assert.assertEquals(1, publishedSites.size());

        Assert.assertFalse(publishedSites.contains(site1));
        Assert.assertFalse(publishedSites.contains(site2));
        Assert.assertFalse(publishedSites.contains(site3));
        Assert.assertTrue(publishedSites.contains(site4));
    }

}