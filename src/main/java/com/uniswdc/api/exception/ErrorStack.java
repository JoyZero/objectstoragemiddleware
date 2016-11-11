/*
 * file name: ErrorStack.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.exception;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ErrorStack {
    private ExceptionCode exceptionCode;
    
    private List errors = new ArrayList();
    
    private static final long serialVersionUID = -6092572408784667752L;
    
    public ErrorStack(EngineRuntimeException ere) {
        this.exceptionCode = ere.getExceptionCode();
        Throwable parent = null;
        for (Throwable exception = ere; exception != null; exception = exception
                .getCause()) {
            this.errors.add(new ErrorRecord(parent, exception));
            if ((exception instanceof EngineRuntimeException)) {
                ErrorStack stack = ((EngineRuntimeException) exception)
                        .getErrorStack();
                if (stack != null) {
                    this.errors.addAll(stack.errors);
                    break;
                }
            }
            parent = exception;
        }
    }
    
    public ErrorStack(String errorName, ErrorRecord[] recs) {
        this.exceptionCode = ExceptionCode.getExceptionCode(errorName);
        if (this.exceptionCode == null) {
            this.exceptionCode = ExceptionCode.E_SERVER_ERROR;
        }
        this.errors.addAll(Arrays.asList(recs));
    }
    
    public ErrorRecord[] getErrorRecords() {
        return (ErrorRecord[]) this.errors
                .toArray(new ErrorRecord[this.errors.size()]);
    }
    
    public ExceptionCode getExceptionCode() {
        return this.exceptionCode;
    }
    
    public String getMessage() {
        if (this.errors.size() > 0) {
            return ((ErrorRecord) this.errors.get(0)).getDescription();
        }
        return null;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer(bufferSize(this));
        toString(buffer);
        return buffer.toString();
    }
    
    void toString(StringBuffer buffer) {
        int start = buffer.length();
        int size = this.errors.size();
        for (int i = 0; i < size; i++) {
            ErrorRecord record = (ErrorRecord) this.errors.get(i);
            if (record == null) {
                buffer.append('\n');
            }
            else {
                if (buffer.length() > start) {
                    buffer.append("Caused by: ");
                }
                record.toString(buffer);
            }
        }
        if (buffer.length() == start) {
            buffer.append('\n');
        }
    }
    
    static int bufferSize(ErrorStack stack) {
        int count = stack == null ? 0 : stack.errors.size();
        return (count + 1) * 8192;
    }
    
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(this.exceptionCode);
        int size = this.errors.size();
        s.writeInt(size);
        for (int lp = 0; lp < size; lp++) {
            s.writeObject(this.errors.get(lp));
        }
    }
    
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        this.errors = new ArrayList();
        this.exceptionCode = ((ExceptionCode) s.readObject());
        int size = s.readInt();
        for (int lp = 0; lp < size; lp++) {
            this.errors.add(s.readObject());
        }
    }
}
