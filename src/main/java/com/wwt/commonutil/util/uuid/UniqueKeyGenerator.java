package com.wwt.commonutil.util.uuid;

import java.util.UUID;

public class UniqueKeyGenerator {
    /**
     * 生成token
     * @return
     */
    public static String generateToken() {
        return UUID.randomUUID ().toString ().replaceAll ("-", "");
    }
}