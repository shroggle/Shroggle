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
package com.shroggle.entity;

/**
 * @author dmitry.solomadin
 */
public enum SlideShowTransitionEffectType {
    FADE_SLOW(1250), FADE_FAST(750), SLIDE_SLOW(1250), SLIDE_FAST(750);

    SlideShowTransitionEffectType(final int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public String toJQueryEffect() {
        if (this == FADE_FAST || this == FADE_SLOW) {
            return "fade";
        } else if (this == SLIDE_SLOW || this == SLIDE_FAST) {
            return "slide";
        }

        throw new IllegalArgumentException();
    }

    private int speed;

}
