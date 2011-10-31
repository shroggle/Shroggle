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
package com.shroggle.logic.countries.states;

import com.shroggle.entity.State;
import com.shroggle.entity.States_CA;
import com.shroggle.entity.States_US;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class StateManager {

    public StateManager(State state) {
        if (state == null) {
            throw new IllegalArgumentException("Unable to create StateManager without State.");
        }
        this.state = state;
        international = ServiceLocator.getInternationStorage().get("states" + state.getCountry().toString(), Locale.US);
    }

    public String getName() {
        return international.get(state.toString());
    }

    public static State parseState(final String state) { // todo. Review this method. It`s ok for two states, but it will be horrible if we add a lot of states. Tolik
        try {
            return States_US.valueOf(state);
        } catch (Exception e) {
            try {
                return States_CA.valueOf(state);
            } catch (Exception e1) {
                return null;
            }
        }
    }

    private final State state;
    private final International international;
}
