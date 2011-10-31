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
package com.shroggle.presentation.site.render;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderPatternFooterTest {

    @Test
    public void execute() {
        final Site site = new Site();
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.ON_USER_PAGES);
        final HttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><p>Powered by " +
                "<a href=\"http://testApplicationUrl/\" target=\"_blank\"><img src=\"/images/wdfooter-logo-small.png\" " +
                "alt=\"\"></a>. Website creation tools for professionals</p></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    @Test
    public void executeWithoutSpecialTag() {
        final Site site = new Site();
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.ON_USER_PAGES);
        final HttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><p>Powered by " +
                "<a href=\"http://testApplicationUrl/\" target=\"_blank\"><img src=\"/images/wdfooter-logo-small.png\" " +
                "alt=\"\"></a>. Website creation tools for professionals</p></div></body>",
                listener.execute(context, "</body>"));
    }    

    @Test
    public void executeForChildSite() {
        Site parentSite = TestUtil.createSite();
        parentSite.setCustomUrl("www.custom-url.com");

        WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(parentSite);
        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);

        //todo: why there are two childSiteRegistrations ???
        Site site = new Site();
        persistance.putItem(workChildSiteRegistration);

        site.setChildSiteSettings(childSiteSettings);
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.ON_USER_PAGES);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><a href=\"http://www.custom-url.com/\" target=\"_blank\" externalurl=\"true\"><img src=\"/images/wdfooter-logo-small.png\" border=\"0\" alt=\"Child Site Registration1\"></a> <a href=\"http://www.custom-url.com\">Child Site Registration1</a></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    @Test
    public void executeForChildSiteWithImage() {
        Site parentSite = TestUtil.createSite();
        parentSite.setCustomUrl("www.custom-url.com");

        final Image image = new Image();
        persistance.putImage(image);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setFooterImageId(image.getImageId());
        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        draftChildSiteRegistration.setSiteId(parentSite.getSiteId());

        draftChildSiteRegistration.setFooterImageId(image.getImageId());
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        final Site site = new Site();
        persistance.putItem(workChildSiteRegistration);

        site.setChildSiteSettings(childSiteSettings);
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.ON_USER_PAGES);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><a href=\"http://www.custom-url.com/\" target=\"_blank\" externalurl=\"true\"><img src=\"/resourceGetter.action?resourceId=1&resourceSizeId=0&resourceSizeAdditionId=0&resourceGetterType=FOOTER_IMAGE&resourceVersion=0&resourceDownload=false\" border=\"0\" alt=\"\"></a> <a href=\"http://www.custom-url.com\"></a></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    @Test
    public void executeForChildSiteWithCustomText() {
        Site parentSite = TestUtil.createSite();
        parentSite.setCustomUrl("www.custom-url.com");

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setFooterText("MMMF");

        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        workChildSiteRegistration.setSiteId(parentSite.getSiteId());
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        final Site site = new Site();
        persistance.putItem(workChildSiteRegistration);

        site.setChildSiteSettings(childSiteSettings);
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.OUTSIDE_APP);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><a href=\"http://www.custom-url.com/\" target=\"_blank\" externalurl=\"true\"><img src=\"/images/wdfooter-logo-small.png\" border=\"0\" alt=\"MMMF\"></a> <a href=\"http://www.custom-url.com\">MMMF</a></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    @Test
    public void executeForChildSiteName() {
        Site parentSite = TestUtil.createSite();
        parentSite.setCustomUrl("www.custom-url.com");

        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(draftChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        final Site site = new Site();

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setSiteId(parentSite.getSiteId());
        workChildSiteRegistration.setName("!!!");
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        site.setChildSiteSettings(childSiteSettings);
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.OUTSIDE_APP);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><a href=\"http://www.custom-url.com/\" target=\"_blank\" externalurl=\"true\"><img src=\"/images/wdfooter-logo-small.png\" border=\"0\" alt=\"!!!\"></a> <a href=\"http://www.custom-url.com\">!!!</a></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    @Test
    public void executeForChildSiteWithCustomUrl() {
        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setName("!!!");
        workChildSiteRegistration.setFooterUrl("FFFFB");

        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        final Site site = new Site();
        persistance.putItem(workChildSiteRegistration);

        site.setChildSiteSettings(childSiteSettings);
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.OUTSIDE_APP);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><a href=\"http://FFFFB/\" target=\"_blank\" externalurl=\"true\"><img src=\"/images/wdfooter-logo-small.png\" border=\"0\" alt=\"!!!\"></a> <a href=\"http://FFFFB\">!!!</a></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    @Test
    public void executeForChildSiteOnEditPage() {
        Site parentSite = TestUtil.createSite();
        parentSite.setCustomUrl("www.custom-url.com");

        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        draftChildSiteRegistration.setSiteId(parentSite.getSiteId());

        //todo: Why do we have these two copies here again ???
//        persistance.putItem(draftChildSiteRegistration);

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setChildSiteRegistration(draftChildSiteRegistration);
        final Site site = new Site();

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setName("!!!");
        workChildSiteRegistration.setFooterImageId(1);
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        site.setChildSiteSettings(childSiteSettings);
        final RenderPatternListener listener = new RenderPatternFooter(site, SiteShowOption.INSIDE_APP);
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        final RenderContext context = new RenderContext(request, null, null, null, false);

        Assert.assertEquals("<div class=\"shroggleLogo\"><a href=\"http://www.custom-url.com/\" target=\"_blank\" externalurl=\"true\"><img src=\"/images/wdfooter-logo-small.png\" border=\"0\" alt=\"\"></a> <a href=\"http://www.custom-url.com\"></a></div><!-- FOOTER -->",
                listener.execute(context, "<!-- FOOTER -->"));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}