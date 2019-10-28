package com.java.spring.Service;

import com.java.spring.annatation.Lazy;
import com.java.spring.annatation.Service;

@Lazy(false)
@Service("sysUserService")
public class SysUserService {
    public SysUserService() {
        System.out.println("SysUserService");
    }
}
