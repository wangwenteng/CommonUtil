package com.java.spring.Service;

import com.java.spring.annatation.Lazy;
import com.java.spring.annatation.Service;

@Service("sysLogService")
@Lazy(false)
public class SysLogService {

    public SysLogService() {
        System.out.println("SysLogService");
    }
}
