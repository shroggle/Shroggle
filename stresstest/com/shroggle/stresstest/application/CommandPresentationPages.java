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
package com.shroggle.stresstest.application;

import com.shroggle.util.process.timecounter.TimeCounterCreator;

/**
 * @author Stasuk Artem
 */
class CommandPresentationPages implements Command {

    public CommandPresentationPages(final String server) {
        this.server = server;
    }

    public void execute() throws Exception {
        final Context context = new Context();
        new CommandHttpGet(server, context).execute();
        new CommandHttpGet(server + "/account/createUser.action", context).execute();
        new CommandHttpGet(server + "/noBotImage.action?noBotPrefix=createUser", context).execute();
        new CommandDwrCall(server, "UploadStatesService", "getStates", context,
                "c0-param0=string:--%20USA%3A").execute();
    }

    private final String server;

}
