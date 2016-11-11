/*
 * file name: UserContext.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.security.auth.Subject;

import com.uniswdc.api.core.Connection;
import com.uniswdc.api.exception.EngineRuntimeException;
import com.uniswdc.api.exception.ExceptionCode;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class UserContext {
    private Locale loc;
    
    private ArrayList subjects = new ArrayList();
    
    private int creatorThreadIdentity;
    
    public UserContext() {
        this.creatorThreadIdentity = System
                .identityHashCode(Thread.currentThread());
    }
    
    public Subject getSubject() {
        return this.subjects.isEmpty() ? null
                : (Subject) this.subjects.get(this.subjects.size() - 1);
    }
    
    public synchronized void pushSubject(Subject sub) {
        if (sub == null) {
            throw new EngineRuntimeException(
                    ExceptionCode.E_NULL_OR_INVALID_PARAM_VALUE, "Subject");
        }
        this.subjects.add(sub);
    }
    
    public synchronized Subject popSubject() {
        return this.subjects.isEmpty() ? null
                : (Subject) this.subjects.remove(this.subjects.size() - 1);
    }
    
    public void setLocale(Locale locale) {
        this.loc = locale;
    }
    
    public Locale getLocale() {
        return this.loc == null ? Locale.getDefault() : this.loc;
    }
    
    private int getCreatorThreadIdentity() {
        return this.creatorThreadIdentity;
    }
    
    private synchronized UserContext cloneContext() {
        UserContext uc = new UserContext();
        uc.loc = this.loc;
        uc.subjects = ((ArrayList) this.subjects.clone());
        return uc;
    }
    
    private static ThreadLocal tl = new ThreadLocal();
    
    public static Subject createSubject(Connection conn, String user,
            String password, String optionalJAASStanzaName) {
        conn.connect(user, password);
        return new Subject();
    }
    
    public static UserContext get() {
        UserContext cntx = (UserContext) tl.get();
        if (cntx == null) {
            cntx = new UserContext();
            tl.set(cntx);
        }
        return cntx;
    }
    
    public static void set(UserContext uc) {
    }
    
    private static String getPrincipal(Subject sub) {
        if ((sub == null) || (sub.getPrincipals() == null)
                || (sub.getPrincipals().isEmpty())) {
            return "";
        }
        StringBuffer principals = new StringBuffer();
        int i = 0;
        Iterator iter = sub.getPrincipals().iterator();
        while (iter.hasNext()) {
            if (i > 0) {
                principals.append("; ");
            }
            Principal principal = (Principal) iter.next();
            principals.append("Principal[" + i++ + "]=" + principal.toString());
        }
        return principals.toString();
    }
}
