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
package com.shroggle.presentation;

import com.shroggle.exception.ImageWriteException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author Stasuk Artem
 */
public class GetNoBotImageCommand {

    public void execute() {
        String templateString = configStorage.get().getNoBotImageTemplates();
        if (templateString == null || templateString.isEmpty()) {
            templateString = DEFAULT_TEMPLATE;
        }

        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(templateString.charAt(random.nextInt(templateString.length())));
        }
        noBotCode = stringBuilder.toString();

        final BufferedImage noBotImage = new BufferedImage(100, 30, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2d = noBotImage.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setColor(Color.WHITE);
        graphics2d.fillRect(0, 0, 100, 30);
        graphics2d.setColor(Color.darkGray);

        graphics2d.setFont(new Font("Serif", 0, 24));
        graphics2d.drawString(noBotCode, 10, 20);
        graphics2d.dispose();

        try {
            ImageIO.setUseCache(false);
            ImageIO.write(noBotImage, "png", outputStream);
        } catch (IOException exception) {
            throw new ImageWriteException(exception);
        }

    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getNoBotCode() {
        return noBotCode;
    }

    public static final String DEFAULT_TEMPLATE = "aaaaaa";
    private ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private OutputStream outputStream;
    private String noBotCode;

}