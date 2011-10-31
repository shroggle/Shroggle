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

package com.shroggle.logic.site.payment;

/**
 * @author dmitry.solomadin
 * SENT_UNCONFIRMED - request sent, direct response not yet received.
 * SENT_CONFIRMED - request sent, direct response received and it's ok.
 * PROCESSING (actual only for Mass payment) - got response from IPN that transaction is in processing. Actual for PayPal only.
 * COMPLETED - got response from IPN that transaction has been successfully processed and completed. Actual for PayPal only.
 * FAILED - indicates that there was an error, search for more info in logs.
 */
public enum TransactionStatus {
    SENT_UNCONFIRMED, SENT_CONFIRMED, PROCESSING, COMPLETED, FAILED
}
