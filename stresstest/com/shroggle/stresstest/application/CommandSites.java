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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
class CommandSites implements Command {

    public CommandSites(final String server, final String email, final String password) {
        this.email = email;
        this.password = password;
        this.server = server;
    }

    @Override
    public void execute() throws Exception {
        final Context context = new Context();
        new CommandHttpGet(server + "/account/dashboard.action", context).execute();
        new CommandDwrCall(server, "LoginUserService", "execute", context,
                "c0-e1=string:" + email, "c0-e2=string:" + password,
                "c0-param0=Object_Object:{email:reference:c0-e1, password:reference:c0-e2}").execute();

        final CommandDashboard commandDashboard = new CommandDashboard(server, context);
        commandDashboard.execute();

        for (final String siteUrl : commandDashboard.getPages().keySet()) {
            final Map<String, String> headers = new HashMap<String, String>();
            headers.put("Host", siteUrl.replace("http://", ""));

            for (final String pageUrlPrefix : commandDashboard.getPages().get(siteUrl)) {
                new CommandHttpGet(siteUrl + "/" + pageUrlPrefix, context, headers).execute();
            }
        }

        new CommandHttpGet(server + "/user/manageItems.action?itemType=BLOG", context).execute();
        new CommandHttpGet(server + "/user/manageItems.action?itemType=FORUM", context).execute();
        new CommandHttpGet(server + "/user/manageItems.action?itemType=ALL_FORMS", context).execute();
        new CommandHttpGet(server + "/user/manageItems.action?itemType=GALLERY", context).execute();
    }

    private final String email;
    private final String password;
    private final String server;

}