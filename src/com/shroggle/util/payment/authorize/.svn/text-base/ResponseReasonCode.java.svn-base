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
package com.shroggle.util.payment.authorize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class ResponseReasonCode {

    public static String getResponseTextByCode(final int code) {
        return responseReasonCodes.get(code);
    }

    private final static Map<Integer, String> responseReasonCodes = new HashMap<Integer, String>();

    static {
        responseReasonCodes.put(1, "This transaction has been approved.");
        responseReasonCodes.put(2, "This transaction has been declined.");
        responseReasonCodes.put(3, "This transaction has been declined.");
        responseReasonCodes.put(4, "This transaction has been declined.");
        responseReasonCodes.put(5, "A valid amount is required.");
        responseReasonCodes.put(6, "The credit card number is invalid.");
        responseReasonCodes.put(7, "The credit card expiration date is invalid.");
        responseReasonCodes.put(8, "The credit card has expired.");
        responseReasonCodes.put(9, "The ABA code is invalid.");
        responseReasonCodes.put(10, "The account number is invalid.");
        responseReasonCodes.put(11, "A duplicate transaction has been submitted.");
        responseReasonCodes.put(12, "An authorization code is required but not present.");
        responseReasonCodes.put(13, "The merchant API Login ID is invalid or the account is inactive.");
        responseReasonCodes.put(14, "The Referrer or Relay Response URL is invalid.");
        responseReasonCodes.put(15, "The transaction ID is invalid.");
        responseReasonCodes.put(16, "The transaction was not found.");
        responseReasonCodes.put(17, "The merchant does not accept this type of credit card.");
        responseReasonCodes.put(18, "ACH transactions are not accepted by this merchant.");
        responseReasonCodes.put(19, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(20, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(21, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(22, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(23, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(24, "The Nova Bank Number or Terminal ID is incorrect. Call Merchant Service Provider.");
        responseReasonCodes.put(25, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(26, "An error occurred during processing. Please try again in 5 minutes.");
        responseReasonCodes.put(27, "The transaction resulted in an AVS mismatch. The address provided does not match billing address of cardholder.");
        responseReasonCodes.put(28, "The merchant does not accept this type of credit card.");
        responseReasonCodes.put(29, "The Paymentech identification numbers are incorrect. Call Merchant Service Provider.");
        responseReasonCodes.put(30, "The configuration with the processor is invalid. Call Merchant Service Provider.");
        responseReasonCodes.put(31, "The FDC Merchant ID or Terminal ID is incorrect. Call Merchant Service Provider.");
        responseReasonCodes.put(32, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(33, "FIELD cannot be left blank.");
        responseReasonCodes.put(34, "The VITAL identification numbers are incorrect. Call Merchant Service Provider.");
        responseReasonCodes.put(35, "An error occurred during processing. Call Merchant Service Provider.");
        responseReasonCodes.put(36, "The authorization was approved, but settlement failed.");
        responseReasonCodes.put(37, "The credit card number is invalid.");
        responseReasonCodes.put(38, "The Global Payment System identification numbers are incorrect. Call Merchant Service Provider.");
        responseReasonCodes.put(40, "This transaction must be encrypted.");
        responseReasonCodes.put(41, "This transaction has been declined.");
        responseReasonCodes.put(43, "The merchant was incorrectly set up at the processor. Call your Merchant Service Provider.");
        responseReasonCodes.put(44, "This transaction has been declined.");
        responseReasonCodes.put(45, "This transaction has been declined.");
        responseReasonCodes.put(46, "Your session has expired or does not exist. You must log in to continue working.");
        responseReasonCodes.put(47, "The amount requested for settlement may not be greater than the original amount authorized.");
        responseReasonCodes.put(48, "This processor does not accept partial reversals.");
        responseReasonCodes.put(49, "A transaction amount greater than $[amount] will not be accepted.");
        responseReasonCodes.put(50, "This transaction is awaiting settlement and cannot be refunded.");
        responseReasonCodes.put(51, "The sum of all credits against this transaction is greater than the original transaction amount.");
        responseReasonCodes.put(52, "The transaction was authorized, but the client could not be notified; the transaction will not be settled.");
        responseReasonCodes.put(53, "The transaction type was invalid for ACH transactions.");
        responseReasonCodes.put(54, "The referenced transaction does not meet the criteria for issuing a credit.");
        responseReasonCodes.put(55, "The sum of credits against the referenced transaction would exceed the original debit amount.");
        responseReasonCodes.put(56, "This merchant accepts ACH transactions only; no credit card transactions are accepted.");
        responseReasonCodes.put(57, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(58, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(59, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(60, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(61, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(62, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(63, "An error occurred in processing. Please try again in 5 minutes.");
        responseReasonCodes.put(65, "This transaction has been declined.");
        responseReasonCodes.put(66, "This transaction cannot be accepted for processing.");
        responseReasonCodes.put(68, "The version parameter is invalid.");
        responseReasonCodes.put(69, "The transaction type is invalid.");
        responseReasonCodes.put(70, "The transaction method is invalid.");
        responseReasonCodes.put(71, "The bank account type is invalid.");
        responseReasonCodes.put(72, "The authorization code is invalid.");
        responseReasonCodes.put(73, "The driver's license date of birth is invalid.");
        responseReasonCodes.put(74, "The duty amount is invalid.");
        responseReasonCodes.put(75, "The freight amount is invalid.");
        responseReasonCodes.put(76, "The tax amount is invalid.");
        responseReasonCodes.put(77, "The SSN or tax ID is invalid.");
        responseReasonCodes.put(78, "The Card Code (CVV2/CVC2/CID) is invalid.");
        responseReasonCodes.put(79, "The driver's license number is invalid.");
        responseReasonCodes.put(80, "The driver's license state is invalid.");
        responseReasonCodes.put(81, "The requested form type is invalid.");
        responseReasonCodes.put(82, "Scripts are only supported in version 2.5.");
        responseReasonCodes.put(83, "The requested script is either invalid or no longer supported.");
        responseReasonCodes.put(84, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(85, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(86, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(87, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(88, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(89, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(90, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(91, "Version 2.5 is no longer supported.");
        responseReasonCodes.put(92, "The gateway no longer supports the requested method of integration.");
        responseReasonCodes.put(97, "This transaction cannot be accepted.");
        responseReasonCodes.put(98, "This transaction cannot be accepted.");
        responseReasonCodes.put(99, "This transaction cannot be accepted.");
        responseReasonCodes.put(100, "The eCheck.Net type is invalid.");
        responseReasonCodes.put(101, "The given name on the account and/or the account type does not match the actual account.");
        responseReasonCodes.put(102, "This request cannot be accepted.");
        responseReasonCodes.put(103, "This transaction cannot be accepted.");
        responseReasonCodes.put(104, "This transaction is currently under review.");
        responseReasonCodes.put(105, "This transaction is currently under review.");
        responseReasonCodes.put(106, "This transaction is currently under review.");
        responseReasonCodes.put(107, "This transaction is currently under review.");
        responseReasonCodes.put(108, "This transaction is currently under review.");
        responseReasonCodes.put(109, "This transaction is currently under review.");
        responseReasonCodes.put(110, "This transaction is currently under review.");
        responseReasonCodes.put(116, "The authentication indicator is invalid.");
        responseReasonCodes.put(117, "The cardholder authentication value is invalid.");
        responseReasonCodes.put(118, "The combination of authentication indicator and cardholder authentication value is invalid.");
        responseReasonCodes.put(119, "Transactions having cardholder authentication values cannot be marked as recurring.");
        responseReasonCodes.put(120, "An error occurred during processing. Please try again.");
        responseReasonCodes.put(121, "An error occurred during processing. Please try again.");
        responseReasonCodes.put(122, "An error occurred during processing. Please try again.");
        responseReasonCodes.put(123, "This account has not been given the permission(s) required for this request.");
        responseReasonCodes.put(127, "The transaction resulted in an AVS mismatch. The address provided does not match billing address of cardholder.");
        responseReasonCodes.put(128, "This transaction cannot be processed.");
        responseReasonCodes.put(130, "This payment gateway account has been closed.");
        responseReasonCodes.put(131, "This transaction cannot be accepted at this time.");
        responseReasonCodes.put(132, "This transaction cannot be accepted at this time.");
        responseReasonCodes.put(141, "This transaction has been declined.");
        responseReasonCodes.put(145, "This transaction has been declined.");
        responseReasonCodes.put(152, "The transaction was authorized, but the client could not be notified; the transaction will not be settled.");
        responseReasonCodes.put(165, "This transaction has been declined.");
        responseReasonCodes.put(170, "An error occurred during processing. Please contact the merchant.");
        responseReasonCodes.put(171, "An error occurred during processing. Please contact the merchant.");
        responseReasonCodes.put(172, "An error occurred during processing. Please contact the merchant.");
        responseReasonCodes.put(173, "An error occurred during processing. Please contact the merchant.");
        responseReasonCodes.put(174, "The transaction type is invalid. Please contact the merchant.");
        responseReasonCodes.put(175, "The processor does not allow voiding of credits.");
        responseReasonCodes.put(180, "An error occurred during processing. Please try again.");
        responseReasonCodes.put(181, "An error occurred during processing. Please try again.");
        responseReasonCodes.put(185, "This reason code is reserved or not applicable to this API.");
        responseReasonCodes.put(193, "The transaction is currently under review.");
        responseReasonCodes.put(200, "This transaction has been declined.");
        responseReasonCodes.put(201, "This transaction has been declined.");
        responseReasonCodes.put(202, "This transaction has been declined.");
        responseReasonCodes.put(203, "This transaction has been declined.");
        responseReasonCodes.put(204, "This transaction has been declined.");
        responseReasonCodes.put(205, "This transaction has been declined.");
        responseReasonCodes.put(206, "This transaction has been declined.");
        responseReasonCodes.put(207, "This transaction has been declined.");
        responseReasonCodes.put(208, "This transaction has been declined.");
        responseReasonCodes.put(209, "This transaction has been declined.");
        responseReasonCodes.put(210, "This transaction has been declined.");
        responseReasonCodes.put(211, "This transaction has been declined.");
        responseReasonCodes.put(212, "This transaction has been declined.");
        responseReasonCodes.put(213, "This transaction has been declined.");
        responseReasonCodes.put(214, "This transaction has been declined.");
        responseReasonCodes.put(215, "This transaction has been declined.");
        responseReasonCodes.put(216, "This transaction has been declined.");
        responseReasonCodes.put(217, "This transaction has been declined.");
        responseReasonCodes.put(218, "This transaction has been declined.");
        responseReasonCodes.put(219, "This transaction has been declined.");
        responseReasonCodes.put(220, "This transaction has been declined.");
        responseReasonCodes.put(221, "This transaction has been declined.");
        responseReasonCodes.put(222, "This transaction has been declined.");
        responseReasonCodes.put(223, "This transaction has been declined.");
        responseReasonCodes.put(224, "This transaction has been declined.");
        responseReasonCodes.put(243, "Recurring billing is not allowed for this eCheck.Net type.");
        responseReasonCodes.put(244, "This eCheck.Net type is not allowed for this Bank Account Type.");
        responseReasonCodes.put(245, "This eCheck.Net type is not allowed when using the payment gateway hosted payment form.");
        responseReasonCodes.put(246, "This eCheck.Net type is not allowed.");
        responseReasonCodes.put(247, "This eCheck.Net type is not allowed.");
        responseReasonCodes.put(248, "The check number is invalid.");
        responseReasonCodes.put(250, "This transaction has been declined.");
        responseReasonCodes.put(251, "This transaction has been declined.");
        responseReasonCodes.put(252, "Your order has been received. Thank you for your business!");
        responseReasonCodes.put(253, "Your order has been received. Thank you for your business!");
        responseReasonCodes.put(254, "Your transaction has been declined.");
        responseReasonCodes.put(261, "An error occurred during processing. Please try again.");
        responseReasonCodes.put(270, "The line item [item number] is invalid.");
        responseReasonCodes.put(271, "The number of line items submitted is not allowed. A maximum of 30 line items can be submitted.");
        responseReasonCodes.put(288, "Merchant is not registered as a Cardholder Authentication participant. This transaction cannot be accepted.");
        responseReasonCodes.put(289, "This processor does not accept zero dollar authorization for this card type.");
        responseReasonCodes.put(290, "One or more required AVS values for zero dollar authorization were not submitted.");
        responseReasonCodes.put(300, "The device ID is invalid.");
        responseReasonCodes.put(301, "The device batch ID is invalid.");
        responseReasonCodes.put(302, "The reversal flag is invalid.");
        responseReasonCodes.put(303, "The device batch is full. Please close the batch.");
        responseReasonCodes.put(304, "The original transaction is in a closed batch.");
        responseReasonCodes.put(305, "The merchant is configured for auto-close.");
        responseReasonCodes.put(306, "The batch is already closed.");
        responseReasonCodes.put(307, "The reversal was processed successfully.");
        responseReasonCodes.put(308, "Original transaction for reversal not found.");
        responseReasonCodes.put(309, "The device has been disabled.");
        responseReasonCodes.put(310, "This transaction has already been voided.");
        responseReasonCodes.put(311, "This transaction has already been captured");
        responseReasonCodes.put(315, "The credit card number is invalid.");
        responseReasonCodes.put(316, "The credit card expiration date is invalid.");
        responseReasonCodes.put(317, "The credit card has expired.");
        responseReasonCodes.put(318, "A duplicate transaction has been submitted.");
        responseReasonCodes.put(319, "The transaction cannot be found.");
    }
}
