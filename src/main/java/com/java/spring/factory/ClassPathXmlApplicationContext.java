package com.java.spring.factory;

import com.java.spring.vo.BeanDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassPathXmlApplicationContext {
    /**
     * 通过此map存储bean的实例
     */
    private Map<String, Object> instanceMap = new ConcurrentHashMap<>();

    /**
     * 通过beanMap存储配置文件中定义的bean对象的配置信息
     */

    private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<>();

    public ClassPathXmlApplicationContext(String file) throws Exception{
        // 1、读取配置文件
        // 2、解析文件
        // 3、封装数据
        InputStream in = getClass().getClassLoader().getResourceAsStream(file);
        parse(in);
    }

    /**
     * 本次的xml解析基于dom实现
     * 市场主流的xml解析：dom,dom4j,sax pul,....
     *
     * @param in
     */
    private void parse(InputStream in) throws Exception {
        //1、创建解析器对象 负责读取xml文件内容
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //2、解析流对象
        Document doc = builder.parse(in);
        //3、处理document
        processDocument(doc);

    }

    private void processDocument(Document doc) throws Exception{
        // 1、获取所有bean元素
        NodeList list = doc.getElementsByTagName("bean");
        // 2、迭代bean元素，对其配置信息封装
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            NamedNodeMap attributes = node.getAttributes();
//            System.out.println(node.getNodeName());
            BeanDefinition beanDefinition = new BeanDefinition();
            String id = attributes.getNamedItem("id").getNodeValue();
//            System.out.println("id:\t"+id);
            beanDefinition.setId(id);
            String classValue = attributes.getNamedItem("class").getNodeValue();
//            System.out.println("classValue:\t"+classValue);
            beanDefinition.setPkgClass(classValue);
            String lazy = attributes.getNamedItem("lazy").getNodeValue();
//            System.out.println("lazy:\t"+lazy);
            beanDefinition.setLazy(Boolean.parseBoolean(lazy));
            beanMap.put(beanDefinition.getId(),beanDefinition);
            //基于配置信息中的lazy属性的值，判断此时的实例是否延迟加载
            if(!beanDefinition.getLazy()){
                Object obj= newBeanInstance(beanDefinition.getPkgClass());
                instanceMap.put(beanDefinition.getId(),obj);
            }
        }
        System.out.println(beanMap);
        System.out.println(instanceMap);
    }

    private Object newBeanInstance(String pkgClass) throws Exception{
        Class<?> cls = Class.forName(pkgClass);
        Constructor<?> con = cls.getDeclaredConstructor();
        con.setAccessible(true);
        return con.newInstance();
    }

    public <T> T getBean(String key, Class<T> t) throws Exception{
        // 1、判断当前instanceMap中是否有bean的实例
        Object obj = instanceMap.get(key);
        if(obj!=null){
            return (T) obj;
        }
        obj = newBeanInstance(t.getName());
        instanceMap.put(key,obj);
        return (T) obj;
    }

    public static void main(String[] args) throws Exception{
        // 1、初始化spring容器
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-configs.xml");
        // 2、从spring容器中获取bean实例
        Date date = ctx.getBean("date", Date.class);
        Object obj = ctx.getBean("obj", Object.class);
    }
}






