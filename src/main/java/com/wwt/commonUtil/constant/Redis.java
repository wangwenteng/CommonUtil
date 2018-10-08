package com.wwt.commonUtil.constant;

import lombok.Getter;

/**
 * @Author th
 * @Date 2018年08月01日 下午15:01
 */
public enum Redis {
	
	STORAGE_SESSION_ID_PREFIX("redis.storage.session.id"),
	
	STORAGE_USER_ID_PREFIX("redis.storage.user.id");

    @Getter
    private String key;

    Redis(String s) {
        this.key = s;
    }

	public String getKey() {
		return key;
	}

}
