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
package com.shroggle.logic.start;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class AlterTest {

    @Test
    public void testNewInstanceADD() {
        final String alterValue = "alter table menus add column menuOrientation varchar(15) default 'HORIZONTAL' not null";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.ADD, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceADD_withSemicolon() {
        final String alterValue = "alter table menus add column menuOrientation varchar(15) default 'HORIZONTAL' not null;";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.ADD, alter.getType());
        Assert.assertEquals(alterValue, alter.getValue());
    }

    @Test
    public void testNewInstanceCHANGE() {
        final String alterValue = "alter table filledForms change type type varchar(30) DEFAULT NULL";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.CHANGE, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceDELETE() {
        final String alterValue = "delete from userOnSiteRights";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.DELETE, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceDROP() {
        final String alterValue = "alter table sites drop column accountId";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.DROP, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceINSERT() {
        final String alterValue = "INSERT INTO bordersBackgrounds\n" +
                "(borderBackgroundId, borderColor, name, siteId, marginValue, paddingValue, borderMargin, borderPadding, borderStyle, borderWidth) select\n" +
                "borderId, color, name, siteId, 0, 0, margin, padding, style, width\n" +
                "from borders";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.INSERT, alter.getType());
        Assert.assertEquals(alterValue.replace("\n", " ") + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceINSERT2() {
        final String alterValue = "INSERT INTO bordersBackgrounds\r" +
                "(borderBackgroundId, borderColor, name, siteId, marginValue, paddingValue, borderMargin, borderPadding, borderStyle, borderWidth) select\r" +
                "borderId, color, name, siteId, 0, 0, margin, padding, style, width\r" +
                "from borders";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.INSERT, alter.getType());
        Assert.assertEquals(alterValue.replace("\r", " ") + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceUPDATE() {
        final String alterValue = "update bordersBackgrounds set backgroundColor = '#000000' where borderBackgroundId = 2";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.UPDATE, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceCOMMENT() {
        final String alterValue = "--update bordersBackgrounds set backgroundColor = '#000000' where borderBackgroundId = 2";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNull(alter);
    }

    @Test
    public void testNewInstanceWithCommentAndAlter() {
        final String alterValue = "-- 2008-11-13 igor\n" +
                "drop table visits_referrerURLs";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.DROP, alter.getType());
        Assert.assertEquals("drop table visits_referrerURLs;", alter.getValue());
    }

    @Test
    public void testNewInstanceMODIFY() {
        final String alterValue = "alter table widgetImages modify column version int not null default 0";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.MODIFY, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceCREATE() {
        final String alterValue = "CREATE TABLE visitorOnSiteRights (\n" +
                "`invited` BIT(1) NOT NULL DEFAULT 0,\n" +
                "`visitorStatus` INTEGER(11) NOT NULL,\n" +
                "`visitorId` INTEGER(11) NOT NULL,\n" +
                "`siteId` INTEGER(11) NOT NULL,\n" +
                "`filledFormId` INTEGER(11) DEFAULT NULL,\n" +
                "PRIMARY KEY (`siteId`, `visitorId`),\n" +
                "KEY `visitorOnSiteRightVisitorId` (`visitorId`),\n" +
                "KEY `visitorOnSiteRightFilledFormId` (`filledFormId`),\n" +
                "KEY `visitorOnSiteRightSiteId` (`siteId`),\n" +
                "CONSTRAINT `visitorOnSiteRightSiteId` FOREIGN KEY (`siteId`) REFERENCES `sites` (`siteId`),\n" +
                "CONSTRAINT `visitorOnSiteRightFilledFormId` FOREIGN KEY (`filledFormId`) REFERENCES `filledForms` (`filledFormId`),\n" +
                "CONSTRAINT `visitorOnSiteRightVisitorId` FOREIGN KEY (`visitorId`) REFERENCES `visitors` (`visitorId`)\n" +
                ") ENGINE=InnoDB";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.CREATE, alter.getType());
        Assert.assertEquals(alterValue.replace("\n", " ") + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceSET() {
        final String alterValue = "SET foreign_key_checks = 0";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.SET, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceSTART() {
        final String alterValue = "start transaction";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.START, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceSELECT() {
        final String alterValue = "select @maxUserId := max(userId) from users";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.SELECT, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceCOMMIT() {
        final String alterValue = "commit";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.COMMIT, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }

    @Test
    public void testNewInstanceRENAME() {
        final String alterValue = "alter table childSiteRegistrations_templatesId rename to childSiteRegistrations_blueprintsId";
        final Alter alter = Alter.newInstance(alterValue);
        Assert.assertNotNull(alter);
        Assert.assertEquals(AlterType.RENAME, alter.getType());
        Assert.assertEquals(alterValue + ";", alter.getValue());
    }
}
