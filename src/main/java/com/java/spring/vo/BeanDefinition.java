package com.java.spring.vo;

import java.io.Serializable;

/**
 *VO(Value Object)
 * 基于此对象存储Bean的配置信息
 */
public class BeanDefinition implements Serializable {

    private static final long serialVersionUID = -2289637660459785939L;
    private String id;
    private String pkgClass;//包名
    private Boolean lazy;//延迟加载

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPkgClass() {
        return pkgClass;
    }

    public void setPkgClass(String pkgClass) {
        this.pkgClass = pkgClass;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "id='" + id + '\'' +
                ", pkgClass='" + pkgClass + '\'' +
                ", lazy=" + lazy +
                '}';
    }

}
