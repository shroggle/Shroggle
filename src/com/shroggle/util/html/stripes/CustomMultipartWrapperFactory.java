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
package com.shroggle.util.html.stripes;

import com.shroggle.logic.stripes.MaxPostSizeCreator;
import net.sourceforge.stripes.controller.multipart.MultipartWrapper;
import net.sourceforge.stripes.controller.multipart.MultipartWrapperFactory;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.exception.StripesRuntimeException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 *         Date: 29.09.2009
 */
public class CustomMultipartWrapperFactory  implements MultipartWrapperFactory {
    /**
     * The configuration key used to lookup the implementation of MultipartWrapper.
     */
    public static final String WRAPPER_CLASS_NAME = "MultipartWrapper.Class";

    /**
     * The names of the MultipartWrapper classes that will be tried if no other is specified.
     */
    public static final String[] BUNDLED_IMPLEMENTATIONS = {
            "net.sourceforge.stripes.controller.multipart.CommonsMultipartWrapper",
            "net.sourceforge.stripes.controller.multipart.CosMultipartWrapper"};

    private static final Logger logger = Logger.getLogger(CustomMultipartWrapperFactory.class.getName());

    // Instance level fields
    private Class<? extends MultipartWrapper> multipartClass;
    private File temporaryDirectory;

    /**
     * Invoked directly after instantiation to allow the configured component to perform one time
     * initialization.  Components are expected to fail loudly if they are not going to be in a
     * valid state after initialization.
     *
     * @param config the Configuration object being used by Stripes
     * @throws Exception should be thrown if the component cannot be configured well enough to use.
     */
    @SuppressWarnings("unchecked")
    public void init(Configuration config) throws Exception {
        // Determine which class we're using
        this.multipartClass = config.getBootstrapPropertyResolver().getClassProperty(WRAPPER_CLASS_NAME, MultipartWrapper.class);

        if (this.multipartClass == null) {
            // It wasn't defined in web.xml so we'll try the bundled MultipartWrappers
            for (String className : BUNDLED_IMPLEMENTATIONS) {
                try {
                    this.multipartClass = ((Class<? extends MultipartWrapper>) Class
                            .forName(className));
                    break;
                } catch (Throwable t) {
                    logger.log(Level.SEVERE, getClass().getSimpleName() + " not using ", className +
                            " because it failed to load. This likely means the supporting " +
                            "file upload library is not present on the classpath.");
                }
            }
        }

        // Log the name of the class we'll be using or a warning if none could be loaded
        if (this.multipartClass == null) {
            logger.log(Level.WARNING, "No " + MultipartWrapper.class.getSimpleName() + " implementation could be loaded");
        } else {
            logger.log(Level.INFO, "Using " + this.multipartClass.getName() + " as " + MultipartWrapper.class.getSimpleName() + " implementation.");
        }

        // Figure out where the temp directory is, and store that info
        File tempDir = (File) config.getServletContext().getAttribute("javax.servlet.context.tempdir");
        if (tempDir != null) {
            this.temporaryDirectory = tempDir;
        } else {
            this.temporaryDirectory = new File(System.getProperty("java.io.tmpdir")).getAbsoluteFile();
        }
    }

    /**
     * Wraps the request in an appropriate implementation of MultipartWrapper that is capable of
     * providing access to request parameters and any file parts contained within the request.
     *
     * @param request an active HttpServletRequest
     * @return an implementation of the appropriate wrapper
     * @throws java.io.IOException if encountered when consuming the contents of the request
     * @throws net.sourceforge.stripes.controller.FileUploadLimitExceededException
     *                             if the post size of the request exceeds any
     *                             configured limits
     */
    public MultipartWrapper wrap(HttpServletRequest request) throws IOException, FileUploadLimitExceededException {
        try {
            MultipartWrapper wrapper = this.multipartClass.newInstance();
            wrapper.build(request, this.temporaryDirectory, MaxPostSizeCreator.createMaxPostSizeInBytes());
            return wrapper;
        }
        catch (IOException ioe) {
            throw ioe;
        }
        catch (FileUploadLimitExceededException fulee) {
            throw fulee;
        }
        catch (Exception e) {
            throw new StripesRuntimeException
                    ("Could not construct a MultipartWrapper for the current request.", e);
        }
    }
}
