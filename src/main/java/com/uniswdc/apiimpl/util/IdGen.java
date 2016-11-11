/*
 * file name: IdGen.java copyright: Unis Cloud Information Technology Co., Ltd.
 * Copyright 2016, All rights reserved description: <description> mofidy staff:
 * Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.apiimpl.util;

import java.security.SecureRandom;

import com.uniswdc.api.util.Id;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class IdGen {
    private static final ThreadLocal<SecureRandom> tlsRandom = new ThreadLocal();
    
    public static Id createId() {
        // SecureRandom random = (SecureRandom) tlsRandom.get();
        // if (random == null) {
        // random = new SecureRandom();
        // tlsRandom.set(random);
        // }
        // byte[] bytes = new byte[16];
        // random.nextBytes(bytes);
        // byte[] tmp42_39 = bytes;
        // tmp42_39[7] = ((byte) (tmp42_39[7] & 0xF));
        // byte[] tmp52_49 = bytes;
        // tmp52_49[7] = ((byte) (tmp52_49[7] | 0x40));
        // byte[] tmp62_59 = bytes;
        // tmp62_59[8] = ((byte) (tmp62_59[8] & 0x3F));
        // byte[] tmp72_69 = bytes;
        // tmp72_69[8] = ((byte) (tmp72_69[8] | 0x80));
        // return new Id(bytes);
        return null;
    }
}
