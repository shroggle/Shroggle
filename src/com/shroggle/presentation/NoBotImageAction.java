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
import com.shroggle.util.WithoutCacheResolution;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.filesystem.FileSystemException;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.DefaultHandler;

import javax.servlet.ServletException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

@UrlBinding("/noBotImage.action")
public class NoBotImageAction extends Action {

    @DefaultHandler
    public Resolution execute() throws IOException, ServletException {
        final ByteArrayOutputStream noBotImageOutput = new ByteArrayOutputStream();
        command.setOutputStream(noBotImageOutput);

        try {
            command.execute();
        } catch (ImageWriteException exception) {
            return new StreamingResolution("text/html", new StringReader("Error get image."));
        } catch (FileSystemException e) {
            return new StreamingResolution("text/html", new StringReader("Error get image, incorrect config."));
        }

        // Set generated code to context for future use in validate create site/account etc.
        sessionStorage.setNoBotCode(this, noBotPrefix, command.getNoBotCode());

        return new WithoutCacheResolution(
                new StreamingResolution(
                        "image/png", new ByteArrayInputStream(noBotImageOutput.toByteArray())));
    }

    public void setNoBotPrefix(final String noBotPrefix) {
        this.noBotPrefix = noBotPrefix;
    }

    private String noBotPrefix;
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final GetNoBotImageCommand command = new GetNoBotImageCommand();

}