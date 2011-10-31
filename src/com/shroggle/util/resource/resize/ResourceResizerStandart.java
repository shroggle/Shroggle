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
package com.shroggle.util.resource.resize;

import com.shroggle.entity.ResourceSize;
import com.shroggle.util.IOUtil;
import com.shroggle.util.ResourceSizeCreator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Artem Stasuk
 */
public class ResourceResizerStandart implements ResourceResizer {

    @Override
    public void execute(
            final InputStream source, final OutputStream destination,
            final String extension, final ResourceSize destinationSize) {
        if (extension == null) {
            throw new ResourceResizeException("Can't resize with null ext!");
        }

        if ("gif".equals(extension.toLowerCase().trim())) {
            try {
                IOUtil.copyStream(source, destination);
                return;
            } catch (final Exception exception) {
                throw new ResourceResizeException(exception);
            }
        }

        try {
            final BufferedImageWraper sourceData = new BufferedImageWraper(ImageIO.read(source));
            final com.shroggle.util.Dimension dimension = ResourceSizeCreator.execute(
                    destinationSize.getWidth(), destinationSize.getHeight(),
                    sourceData.get().getWidth(), sourceData.get().getHeight(), destinationSize.isSaveRatio());

            final BufferedImageWraper destinationData =
                    new BufferedImageWraper(sourceData, dimension.getWidth(), dimension.getHeight());
            final Graphics2D g2 = destinationData.get().createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            final AffineTransform xform = AffineTransform.getScaleInstance(dimension.getScaleX(), dimension.getScaleY());
            g2.drawRenderedImage(sourceData.get(), xform);
            g2.dispose();

            if (!ImageIO.write(destinationData.get(), extension, destination)) {
                throw new ResourceResizeException("Can't resize image, may be incorrect ext " + extension);
            }
        } catch (final ResourceResizeException exception) {
            throw exception;
        } catch (final Exception exception) {
            throw new ResourceResizeException(exception);
        }
    }

}
