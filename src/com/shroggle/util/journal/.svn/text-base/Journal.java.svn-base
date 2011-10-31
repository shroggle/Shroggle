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
package com.shroggle.util.journal;

import com.shroggle.entity.JournalItem;

import java.util.List;

/**
 * Format standart journal output:<br>
 * Date time | module | class | visitorId | userId (may be deprecated) | message
 * 2008-10-12 12:33:44:0001 login LoginInBlankAction 6788 none Start process.
 * 2008-10-12 12:33:44:0001 login LoginInBlankAction 6788 789 Sucesseful process.
 *
 * @author Artem Stasuk
 */
public interface Journal {

    void add(final List<JournalItem> journalItems);

    void destroy();

}
