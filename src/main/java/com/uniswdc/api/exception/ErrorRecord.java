/*
 * file name: ErrorRecord.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.exception;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ErrorRecord {
    public static final String DIAGNOSTIC_STACK_TRACE = "stackTrace";
    
    public static final String DIAGNOSTIC_EXCEPTION_CODE = "exceptionCode";
    
    public static final String DIAGNOSTIC_FAILED_BATCH_ITEM = "failedBatchItem";
    
    private String source;
    
    private String description;
    
    private LinkedHashMap diagnosticTypes = new LinkedHashMap();
    
    static final int BUFFER_SIZE = 8192;
    
    private static final long serialVersionUID = -6784395321929473459L;
    
    public ErrorRecord(Throwable erte) {
        this(null, erte);
    }
    
    ErrorRecord(Throwable parent, Throwable exception) {
        this.source = exception.getClass().getName();
        this.description = exception.getLocalizedMessage();
        if ((exception instanceof EngineRuntimeException)) {
            String codeKey = ((EngineRuntimeException) exception)
                    .getExceptionCode().getKey();
            this.diagnosticTypes.put("exceptionCode", codeKey);
        }
        this.diagnosticTypes.put("stackTrace",
                getStackTraceString(parent, exception));
    }
    
    public ErrorRecord(String source, String description,
            HashMap diagnosticTypes) {
        this.source = source;
        this.description = description;
        this.diagnosticTypes.putAll(diagnosticTypes);
    }
    
    private String getStackTraceString(Throwable parent, Throwable exception) {
        StringBuffer buffer = new StringBuffer(8192);
        buffer.append('\n');
        StackTraceElement[] parents = parent == null ? null
                : parent.getStackTrace();
        StackTraceElement[] elements = exception.getStackTrace();
        int last = elements.length - 1;
        int more = 0;
        if (parents != null) {
            for (int i = parents.length - 1; (i >= 0) && (last >= 0); last--) {
                if (!elements[last].equals(parents[i])) {
                    break;
                }
                i--;
            }
            more = elements.length - 1 - last;
        }
        for (int i = 0; i <= last; i++) {
            buffer.append("\tat ").append(elements[i]).append('\n');
        }
        if (more > 0) {
            buffer.append("\t... ").append(more).append(" more\n");
        }
        return buffer.toString();
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public HashMap getDiagnosticTypes() {
        return this.diagnosticTypes;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer(8192);
        toString(buffer);
        return buffer.toString();
    }
    
    void toString(StringBuffer buffer) {
        int start = buffer.length();
        String code = textValue(this.diagnosticTypes.get("exceptionCode"));
        ExceptionCode ec = ExceptionCode.getExceptionCode(code);
        String ibmCode = ec == null ? null : ec.getErrorId();
        String stack = textValue(this.diagnosticTypes.get("stackTrace"));
        toString(buffer, this.source, ibmCode, code, this.description);
        for (Iterator i = this.diagnosticTypes.entrySet().iterator(); i
                .hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            Object key = entry.getKey();
            if ((key != null) && (!key.equals("exceptionCode"))
                    && (!key.equals("stackTrace"))) {
                String value = textValue(entry.getValue());
                if (buffer.length() > start) {
                    buffer.append(' ');
                }
                buffer.append(key).append('=').append(value);
            }
        }
        if (stack.length() > 0) {
            if (stack.charAt(0) != '\n') {
                buffer.append('\n');
            }
            buffer.append(stack);
        }
        if ((buffer.length() == start)
                || (buffer.charAt(buffer.length() - 1) != '\n')) {
            buffer.append('\n');
        }
    }
    
    static void toString(StringBuffer buffer, String source, String ibmCode,
            String code, String description) {
        int start = buffer.length();
        appendSummary(buffer, start, source);
        if (ibmCode != null) {
            char lastChar = ibmCode.charAt(ibmCode.length() - 1);
            if (Character.isDigit(lastChar)) {
                ibmCode = ibmCode + "E";
            }
            appendSummary(buffer, start, ibmCode);
        }
        appendSummary(buffer, start, code);
        appendSummary(buffer, start, description);
    }
    
    private static void appendSummary(StringBuffer buffer, int start,
            String value) {
        if ((value != null) && ((value = value.trim()).length() > 0)) {
            if (buffer.length() > start) {
                buffer.append(": ");
            }
            buffer.append(value);
        }
    }
    
    private static String textValue(Object value) {
        String text = value == null ? "" : value.toString();
        return text == null ? "" : text;
    }
    
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(this.source);
        s.writeObject(this.description);
        Set entries = this.diagnosticTypes.entrySet();
        Iterator iter = entries.iterator();
        s.writeInt(entries.size());
        while (iter.hasNext()) {
            Map.Entry ent = (Map.Entry) iter.next();
            s.writeObject(ent.getKey());
            s.writeObject(ent.getValue());
        }
    }
    
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        this.source = ((String) s.readObject());
        this.description = ((String) s.readObject());
        this.diagnosticTypes = new LinkedHashMap();
        int size = s.readInt();
        for (int lp = 0; lp < size; lp++) {
            Object key = s.readObject();
            Object val = s.readObject();
            this.diagnosticTypes.put(key, val);
        }
    }
}
