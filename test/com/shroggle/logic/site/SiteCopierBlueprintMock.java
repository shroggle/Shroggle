package com.shroggle.logic.site;

import com.shroggle.entity.Site;

/**
 * @author Artem Stasuk
 */
public class SiteCopierBlueprintMock implements SiteCopierBlueprint {

    @Override
    public Site execute(final Site blueprint) {
        called = true;
        this.blueprint = blueprint;
        return null;
    }

    public boolean isCalled() {
        return called;
    }

    public Site getBlueprint() {
        return blueprint;
    }

    private boolean called;
    private Site blueprint;

}
