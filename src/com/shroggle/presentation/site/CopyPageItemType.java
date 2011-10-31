package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author Artem Stasuk
 */
@DataTransferObject(converter = EnumConverter.class)
public enum CopyPageItemType {

    COPY, SHARE

}
