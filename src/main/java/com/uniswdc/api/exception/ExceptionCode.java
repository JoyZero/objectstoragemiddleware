/*
 * file name: ExceptionCode.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.uniswdc.apiimpl.util.LString;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ExceptionCode extends LString {
    private static final String RES_BASE_NAME = "res/ExceptionCode";
    
    private static Map mapNameToExceptionCodeInstance = new HashMap();
    
    private transient boolean isFatal = false;
    
    private transient ErrorLoggingLevel mErrorLoggingLevel = ErrorLoggingLevel.Required;
    
    public static final Locale stDefaultLocale = Locale.getDefault();
    
    private String id;
    
    private ExceptionCode(String key, String englishString, String eid) {
        this.mKey = key;
        this.mEnglishString = englishString;
        this.id = eid;
        mapNameToExceptionCodeInstance.put(key, this);
    }
    
    private ExceptionCode(String key, String englishString, String eid,
            boolean isFatal, ErrorLoggingLevel errLogLevel) {
        this(key, englishString, eid);
    }
    
    public boolean isFatal() {
        return this.isFatal;
    }
    
    public ErrorLoggingLevel getErrorLoggingLevel() {
        return this.mErrorLoggingLevel;
    }
    
    public String getKey() {
        return this.mKey;
    }
    
    public int getId() {
        int idInt = 0;
        try {
            String idTemp = this.id;
            idTemp = idTemp.replaceAll("[a-zA-Z]", "");
            idTemp = idTemp.trim();
            idInt = Integer.parseInt(idTemp);
        }
        catch (Exception e) {
        }
        return idInt;
    }
    
    public String getErrorId() {
        return this.id;
    }
    
    public String getDefaultEnglishString() {
        return this.mEnglishString;
    }
    
    public String toString(Object[] args) {
        return toStringHelper("res/ExceptionCode", stDefaultLocale, args);
    }
    
    public String toString(Locale le, Object[] args) {
        return toStringHelper("res/ExceptionCode", le, args);
    }
    
    public static ExceptionCode getExceptionCode(String key) {
        return (ExceptionCode) mapNameToExceptionCodeInstance.get(key);
    }
    
    private synchronized String toStringHelper(String BaseName, Locale le,
            Object[] args) {
        String patternString;
        try {
            if ((BaseName == null) || (le == null) || (this.mKey == null)) {
                patternString = this.mEnglishString;
            }
            else {
                ResourceBundle bundle = ResourceBundle.getBundle(BaseName, le);
                patternString = bundle.getString(this.mKey);
            }
        }
        catch (MissingResourceException ex) {
            patternString = this.mEnglishString;
            le = Locale.US;
        }
        String formattedString;
        try {
            MessageFormat form = new MessageFormat(patternString, le);
            formattedString = form.format(args);
        }
        catch (Exception ex) {
            formattedString = this.mEnglishString;
        }
        return formattedString;
    }
    
    /**
     * An error was returned from the server.
     */
    public static final ExceptionCode E_SERVER_ERROR = new ExceptionCode(
            "E_SERVER_ERROR", "An error was returned from the server.",
            "FNRCE0062", false, ErrorLoggingLevel.Required);
    
    /**
     * Parameter {0} is either null or has an invalid value
     */
    public static final ExceptionCode E_NULL_OR_INVALID_PARAM_VALUE = new ExceptionCode(
            "E_NULL_OR_INVALID_PARAM_VALUE",
            "Parameter {0} is either null or has an invalid value.",
            "FNRCE0046", false, ErrorLoggingLevel.Required);
    
    /**
     * Invalid input parameter: Name={0}, Value={1}.
     */
    public static final ExceptionCode E_BAD_PARAMETER = new ExceptionCode(
            "E_BAD_PARAMETER", "Invalid input parameter: Name={0}, Value={1}.",
            "FNRCE0007", false, ErrorLoggingLevel.Required);
    
    /**
     * The requester has insufficient access rights to perform the requested
     * operation.
     */
    public static final ExceptionCode E_ACCESS_DENIED = new ExceptionCode(
            "E_ACCESS_DENIED",
            "The requester has insufficient access rights to perform the requested operation.",
            "FNRCE0001", false, ErrorLoggingLevel.Required);
    
    /**
     * Custom exception code constant and this exception happens when One or
     * more of the objects couldn't be deleted.
     */
    public static final ExceptionCode E_MultiObject_Delete = new ExceptionCode(
            "E_MultiObject_Delete",
            "One or more of the objects couldn't be deleted.", "FNRCE0012",
            false, ErrorLoggingLevel.Required);
    
    /**
     * Custom exception code constant and this exception happens when Some
     * errors occurred in Amazon S3 while processing the request.
     */
    public static final ExceptionCode E_AmazonService = new ExceptionCode(
            "E_AmazonService",
            " Some errors occurred in Amazon S3 while processing the request.",
            "FNRCE0013", false, ErrorLoggingLevel.Required);
    
    /**
     * Custom exception code constant and this exception happens when the bucket
     * with specified name does not exist
     */
    public static final ExceptionCode E_BUCKET_NOT_EXIST = new ExceptionCode(
            "E_BUCKET_NOT_EXIST", "The bucket does not exist", "FNRCE0002",
            false, ErrorLoggingLevel.Required);
    
    /**
     * Custom exception code constant and this exception happens when meet a
     * IOException with the inputstream of the file
     */
    public static final ExceptionCode E_INPUTSTREAM_IOECEPTION = new ExceptionCode(
            "E_INPUTSTREAM_IOECEPTION", "input stream ioException", "FNRCE0003",
            false, ErrorLoggingLevel.Required);
    
    /**
     * Custom exception code constant and this exception happens when meet a
     * IOException with the inputstream of the file
     */
    public static final ExceptionCode E_NAME_ILLEGAL = new ExceptionCode(
            "E_NAME_ILLEGAL",
            "'objectStore name' or 'documnet name' or 'file name' illegal",
            "FNRCE0003", false, ErrorLoggingLevel.Required);
}
