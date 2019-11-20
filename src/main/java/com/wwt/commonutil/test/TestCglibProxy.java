package com.wwt.commonutil.test;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestCglibProxy {
    public static void main(String[] args) {

       while (true){
           Enhancer enhancer = new Enhancer();
           enhancer.setSuperclass(ProxyObject.class);
           enhancer.setUseCache(false);
           enhancer.setCallback(new MethodInterceptor() {
               @Override
               public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                   System.out.println("I am proxy");
                   return methodProxy.invokeSuper(o,objects);
               }
           });
           ProxyObject proxy = (ProxyObject) enhancer.create();
           proxy.greet();
       }
    }

    static class ProxyObject{
        public String greet(){
            return "thanks for you";
        }
    }
}
