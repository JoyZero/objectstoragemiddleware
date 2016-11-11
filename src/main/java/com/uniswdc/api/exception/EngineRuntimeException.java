/*
 * file name: EngineRuntimeException.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.exception;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamField;
import java.util.Locale;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.uniswdc.api.util.UserContext;
import com.uniswdc.apiimpl.exception.ExceptionContext;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class EngineRuntimeException extends RuntimeException
        implements Externalizable {
    private ExceptionCode exceptionCode;
    
    private Object[] codeArgs;
    
    private ExceptionContext context;
    
    private Object[] ctxArgs;
    
    private ErrorStack errorStack;
    
    private Locale localeWhenThrown = null;
    
    private static ThreadLocal inGetAsErrorStack = new ThreadLocal();
    
    private static final long serialVersionUID = -6559592357494191011L;
    
    public EngineRuntimeException() {
        commonInit();
    }
    
    public EngineRuntimeException(ErrorStack errStack) {
        this.errorStack = errStack;
        commonInit();
    }
    
    public EngineRuntimeException(ExceptionCode code, Object[] codeArgs) {
        this.exceptionCode = code;
        this.codeArgs = (codeArgs == null ? new Object[0] : codeArgs);
        commonInit();
    }
    
    public EngineRuntimeException(ExceptionCode code) {
        this.exceptionCode = code;
        this.codeArgs = new Object[0];
        commonInit();
    }
    
    public EngineRuntimeException(ExceptionCode code, Object codeArg0) {
        this.exceptionCode = code;
        this.codeArgs = new Object[] {
                codeArg0 == null ? new Object[] { "(null)" } : codeArg0 };
        commonInit();
    }
    
    public EngineRuntimeException(Throwable cause, ExceptionCode code,
            Object[] codeArgs) {
        super(cause);
        this.exceptionCode = code;
        this.codeArgs = (codeArgs == null ? new Object[0] : codeArgs);
        commonInit();
    }
    
    public EngineRuntimeException(ExceptionCode code, Object[] codeArgs,
            ExceptionContext ctx, Object[] ctxArgs) {
        this.exceptionCode = code;
        this.codeArgs = (codeArgs == null ? new Object[0] : codeArgs);
        this.context = ctx;
        this.ctxArgs = (ctxArgs == null ? new Object[0] : ctxArgs);
        commonInit();
    }
    
    public EngineRuntimeException(Throwable cause, ExceptionCode code,
            Object[] codeArgs, ExceptionContext ctx, Object[] ctxArgs) {
        super(cause);
        this.exceptionCode = code;
        this.codeArgs = (codeArgs == null ? new Object[0] : codeArgs);
        this.context = ctx;
        this.ctxArgs = (ctxArgs == null ? new Object[0] : ctxArgs);
        commonInit();
    }
    
    public EngineRuntimeException(EngineRuntimeException cause,
            ExceptionContext ctx, Object[] ctxArgs) {
        super(cause);
        this.exceptionCode = cause.getExceptionCode();
        this.codeArgs = cause.getCodeArguments();
        this.context = ctx;
        this.ctxArgs = (ctxArgs == null ? new Object[0] : ctxArgs);
        commonInit();
    }
    
    /**
     * <default constructor>
     */
    public EngineRuntimeException(MultiObjectDeleteException ex) {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * <default constructor>
     */
    public EngineRuntimeException(AmazonServiceException ex) {
        // TODO Auto-generated constructor stub
    }
    
    private void commonInit() {
        this.localeWhenThrown = UserContext.get().getLocale();
        // if (ConnectionImpl.isSaveLastERE()) {
        // ConnectionImpl.setLastERE(this);
        // }
    }
    
    public ExceptionCode getExceptionCode() {
        return this.errorStack == null ? this.exceptionCode
                : this.errorStack.getExceptionCode();
    }
    
    public String getMessage() {
        return getMessageHelper(UserContext.get().getLocale());
    }
    
    public String getLocalizedMessage() {
        return getMessageHelper(UserContext.get().getLocale());
    }
    
    public ErrorStack getAsErrorStack() {
        return getAsErrorStack(null);
    }
    
    private ErrorStack getAsErrorStack(Locale locale) {
        if (this.errorStack != null) {
            return this.errorStack;
        }
        inGetAsErrorStack.set(this);
        UserContext origUC = UserContext.get();
        UserContext tempUC = new UserContext();
        try {
            if (locale != null) {
                tempUC.setLocale(locale);
                UserContext.set(tempUC);
            }
            this.errorStack = new ErrorStack(this);
            return this.errorStack;
        }
        finally {
            UserContext.set(origUC);
            inGetAsErrorStack.set(null);
        }
    }
    
    ErrorStack getErrorStack() {
        return this.errorStack;
    }
    
    private Object[] getCodeArguments() {
        return this.codeArgs;
    }
    
    private String getMessageHelper(Locale le) {
        StringBuffer buf = new StringBuffer();
        if (this.errorStack == null) {
            if (this.exceptionCode != null) {
                buf.append(this.exceptionCode.toString(le, this.codeArgs));
            }
            if ((inGetAsErrorStack.get() == null)
                    || (this.exceptionCode != ExceptionCode.E_ACCESS_DENIED)) {
                if (this.context != null) {
                    if (this.exceptionCode != null) {
                        buf.append(" ");
                    }
                    // buf.append(this.context.toString(le, this.ctxArgs));
                }
            }
        }
        else {
            return this.errorStack.getMessage();
        }
        return buf.toString();
    }
    
    public String toString() {
        StringBuffer buffer;
        if (this.errorStack == null) {
            buffer = new StringBuffer(256);
            String source = getClass().getName();
            ExceptionCode exCode = getExceptionCode();
            String code = exCode == null ? null : exCode.getKey();
            String ibmCode = exCode == null ? null : exCode.getErrorId();
            String description = getLocalizedMessage();
            ErrorRecord.toString(buffer, source, ibmCode, code, description);
        }
        else {
            buffer = new StringBuffer(ErrorStack.bufferSize(this.errorStack));
            this.errorStack.toString(buffer);
            int index = buffer.indexOf("\n");
            if (index == 0) {
                buffer.insert(0, "errorStack={").append('}');
            }
            else if (index > 0) {
                buffer.insert(index, " errorStack={").append('}');
            }
        }
        return buffer.toString();
    }
    
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    
    public void writeExternal(ObjectOutput s) throws IOException {
        s.writeShort(5);
        s.writeObject(getAsErrorStack(this.localeWhenThrown));
    }
    
    public void readExternal(ObjectInput s)
            throws IOException, ClassNotFoundException {
        s.readShort();
        this.errorStack = ((ErrorStack) s.readObject());
    }
    
    public static Throwable mergeStackTraces(Throwable current,
            Throwable backGround) {
        Throwable t = backGround;
        try {
            if ((backGround instanceof EngineRuntimeException)) {
                EngineRuntimeException ert = (EngineRuntimeException) backGround;
                t = new EngineRuntimeException(ert.getExceptionCode(),
                        ert.getCodeArguments());
            }
            else {
                t = (Throwable) backGround.getClass().newInstance();
            }
            if (t == null) {
                return t;
            }
            StackTraceElement[] currentThreadStack = current.getStackTrace();
            StackTraceElement[] backGroundThreadStack = backGround
                    .getStackTrace();
            StackTraceElement[] newStack = new StackTraceElement[currentThreadStack.length
                    + backGroundThreadStack.length];
            System.arraycopy(backGroundThreadStack,
                    0,
                    newStack,
                    0,
                    backGroundThreadStack.length);
            System.arraycopy(currentThreadStack,
                    0,
                    newStack,
                    backGroundThreadStack.length,
                    currentThreadStack.length);
            t.setStackTrace(newStack);
            t.printStackTrace();
        }
        catch (Exception e) {
        }
        return t;
    }
}
