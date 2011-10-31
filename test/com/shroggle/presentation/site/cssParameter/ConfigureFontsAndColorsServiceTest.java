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

package com.shroggle.presentation.site.cssParameter;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;


@RunWith(TestRunnerWithMockServices.class)
public class ConfigureFontsAndColorsServiceTest {

    @Test
    public void execute() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        CssParameter cssParameter = new CssParameter();
        cssParameter.setName("test");
        cssParameter.setSelector("tt");
        fileSystemMock.addCssParameter(ItemType.TEXT, cssParameter);

        DraftText text = new DraftText();
        persistance.putItem(text);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(text);
        widgetItem.setFontsAndColorsId(null);
        pageVersion.addWidget(widgetItem);

        
        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);
        final FontsAndColorsValue value = new FontsAndColorsValue();
        fontsAndColors.addCssValue(value);
        value.setName("test");
        value.setValue("bbb");
        value.setSelector("tt");
        value.setDescription("description");
        text.setFontsAndColorsId(fontsAndColors.getId());


        Border borderBackground = new Border();
        persistance.putBorder(borderBackground);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(widgetItem.getWidgetId(), null);
        assertNotNull(service.getCssParameters());
        assertEquals(1, service.getCssParameters().size());
        assertEquals(cssParameter, service.getCssParameters().get(0));
        assertEquals("test", service.getCssParameters().get(0).getName());
        assertEquals("bbb", service.getCssParameters().get(0).getValue());
    }

    @Test
    public void execute_withItemId() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        CssParameter cssParameter = new CssParameter();
        cssParameter.setName("test");
        cssParameter.setSelector("tt");
        fileSystemMock.addCssParameter(ItemType.TEXT, cssParameter);

        DraftText text = new DraftText();
        persistance.putItem(text);

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setDraftItem(text);
        widgetItem.setFontsAndColorsId(null);
        pageVersion.addWidget(widgetItem);


        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);
        final FontsAndColorsValue value = new FontsAndColorsValue();
        fontsAndColors.addCssValue(value);
        value.setName("test");
        value.setValue("bbb");
        value.setSelector("tt");
        value.setDescription("description");
        text.setFontsAndColorsId(fontsAndColors.getId());


        Border borderBackground = new Border();
        persistance.putBorder(borderBackground);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(null, text.getId());
        assertNotNull(service.getCssParameters());
        assertEquals(1, service.getCssParameters().size());
        assertEquals(cssParameter, service.getCssParameters().get(0));
        assertEquals("test", service.getCssParameters().get(0).getName());
        assertEquals("bbb", service.getCssParameters().get(0).getValue());
    }
    
    @Test
    public void testCompare_Normal() {
        CssParameter cssParameter1 = new CssParameter();
        cssParameter1.setDescription("bbbbb");

        CssParameter cssParameter2 = new CssParameter();
        cssParameter2.setDescription("aaaaa");

        assertTrue(cssParameter1.compareTo(cssParameter2) > 0);
        assertFalse(cssParameter2.compareTo(cssParameter1) > 0);
    }

    @Test
    public void testCompare_NullDescription() {
        CssParameter cssParameter1 = new CssParameter();
        cssParameter1.setDescription("bbbbb");

        CssParameter cssParameter2 = new CssParameter();
        cssParameter2.setDescription(null);

        assertTrue(cssParameter1.compareTo(cssParameter2) > 0);
        assertFalse(cssParameter2.compareTo(cssParameter1) > 0);
    }

    private final ConfigureFontsAndColorsService service = new ConfigureFontsAndColorsService();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();

}