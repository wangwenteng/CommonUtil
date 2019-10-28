package com.java.spring.factory;

import com.java.spring.annatation.ComponentScan;
import com.java.spring.annatation.Lazy;
import com.java.spring.annatation.Service;
import com.java.spring.config.SpringConfig;
import com.java.spring.vo.BeanDefinition;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationCongihApplicationContext {
    /**
     * 通过此map存储bean的实例
     */
    private Map<String, Object> instanceMap = new ConcurrentHashMap<>();

    /**
     * 通过beanMap存储配置文件中定义的bean对象的配置信息
     */

    private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<>();

    public AnnotationCongihApplicationContext(Class<?> configClas) throws Exception{
        // 1、读取配置类指定包名
        ComponentScan componentScan = configClas.getDeclaredAnnotation(ComponentScan.class);
        String pkg = componentScan.value();
        // 2、扫描指定包
        String classPath = pkg.replace(".","/");
//        System.out.println(classPath);
        URL resource = configClas.getClassLoader().getResource(classPath);
//        System.out.println(resource);
//        System.out.println(resource.getPath());
//        System.out.println(resource.getPath().replace("%20", " "));
//        System.out.println(resource.getPath().replace("%20", " ").substring(1));
        File fileDir = new File(resource.getPath().replace("%20", " ").substring(1));
        File[] classFile = fileDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
//                System.out.println(file.getName());
                return file.isFile()&&file.getName().endsWith("class");
            }
        });
//        for (File f :classFile){
//            System.out.println(f.getName());
//        }
        // 3、封装文件信息
        processClassFiles(pkg,classFile);
    }

    private void processClassFiles(String pkg,File[] classFile) throws Exception{
        for (File f: classFile){
            String pkgClass = pkg +"."+f.getName().substring(0,f.getName().lastIndexOf("."));
            System.out.println(pkgClass);
            Class<?> targetCls = Class.forName(pkgClass);
            if(targetCls.isAnnotationPresent(Service.class)) {
                continue;
            }
            Service service = targetCls.getDeclaredAnnotation(Service.class);
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(service.value());
            beanDefinition.setPkgClass(pkgClass);
            Lazy lazy = targetCls.getDeclaredAnnotation(Lazy.class);
            if(lazy!=null){
                beanDefinition.setLazy(lazy.value());
            }
            System.out.println(beanDefinition);
            beanMap.put(beanDefinition.getId(),beanDefinition);
            if(!beanDefinition.getLazy()){
                Object obj= newBeanInstance(beanDefinition.getPkgClass());
                instanceMap.put(beanDefinition.getId(),obj);
            }
        }
    }

    private Object newBeanInstance(String pkgClass) throws Exception{
        Class<?> cls = Class.forName(pkgClass);
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        return con.newInstance();
    }

    public static void main(String[] args) throws Exception{
        AnnotationCongihApplicationContext context = new AnnotationCongihApplicationContext(SpringConfig.class);
        //获取配置类
//        context.getBean(key,cls);
    }
}
