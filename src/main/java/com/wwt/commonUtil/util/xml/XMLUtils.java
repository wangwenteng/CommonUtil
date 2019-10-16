package com.wwt.commonUtil.util.xml;

import com.sun.mail.iap.ByteArray;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 资源服务放回数据解析（数据格式 XML）
 * @author ytao
 *
 */
public class XMLUtils {
	private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);
	public static final String XML_FLAF_FALSE = "false";
	
	public static final String XML_FLAF = "RBSPMessage/Method/Items/Item/Error/Flag";
	public static final String XML_ERRID = "RBSPMessage/Method/Items/Item/Error/ErrID";
	public static final String XML_ERRMSG = "RBSPMessage/Method/Items/Item/Error/Msg";
	public static final String XML_ROW = "RBSPMessage/Method/Items/Item/Value/ROW";
	
	public static final String XML_FLAF_CK = "CKRequest/Method/Items/Item/Error/Flag";
	public static final String XML_ERRID_CK = "CKRequest/Method/Items/Item/Error/ErrID";
	public static final String XML_ERRMSG_CK = "CKRequest/Method/Items/Item/Error/Msg";
	private static final Random random = new Random();
	/**
	 * 最多23位id加前缀
	 * @return
	 */
	public static synchronized String JZuuID(){
		long t = System.currentTimeMillis();//最多14位
		return String.valueOf("AR"+t+"-"+random.nextInt(99999));
	}
	
	private static String getColumnName(PropertyDescriptor p){
		String[] columnName = StringUtils.splitByCharacterTypeCamelCase(p.getName());
		String column = "";
        for(int i=0 ; i <columnName.length ; i++ ) {
        	column+=StringUtils.upperCase(columnName[i]);
            if(i != columnName.length-1){
            	column+="_";
            }
        }
        return column;
	}
	
    /**
     * 用于将资源服务返回的xml解析为对应的Bean
     * @param xml  
     * @param clazz 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> parseXml(String xml,Class<T> clazz) throws Exception {
		List<T> result = new ArrayList<T>();
		Document doc = DocumentHelper.parseText(xml);
		//获取数据节点
		List<Element> list = (List<Element>)doc.selectNodes("RBSPMessage/Method/Items/Item/Value/Row");
		
		if (list != null && list.size() > 0) {
			Element stateElement = (Element)list.get(0);
			//获取返回数据状态
			String state = ((Element)stateElement.elements().get(0)).getText();	
			
			if("000".equals(state)){
				if(list.size()<2){
					return null;
				}else{
					List<Element> fieldNames = ((Element)list.get(1)).elements();
					for (int i = 2; i < list.size(); i++) {
						Element data = (Element) list.get(i);
						List<Element> fields = data.elements();
						T o = clazz.newInstance();
						for (int j = 0; j < fields.size(); j++) {
							//System.out.println(e.getText());
							Element e  =  fields.get(j);
							String fieldName = fieldNames.get(j).getText();
							if(e.getText()!=null && e.getText().length()>0){
								if("户政性别代码字典".equals(fieldName)){
									fieldName="XBDM";
								}
								setBeanProperty(o, fieldName, e.getText());
							}
						}
						result.add(o);
					}
				}
			}else{
				System.out.println("发生异常，错误码："+state);
				//logger.error("发生异常，错误码："+state);
				throw new Exception("error code is "+state);
			}
		}
		
		return result;
	}
    	
	public static <T extends Entity> List<T> parseXml(String xml,Class<T> clazz,String[] fieldNames) throws Exception {
		List<T> result = new ArrayList<T>();
		Document doc = DocumentHelper.parseText(xml);
		Element flag = (Element)doc.selectSingleNode(XML_FLAF);
		if(!flag.getText().equals(XML_FLAF_FALSE)){
			Element errID = (Element)doc.selectSingleNode(XML_ERRID);
			Element errMsg = (Element)doc.selectSingleNode(XML_ERRMSG);
			throw new Exception(errID.getText()+":"+errMsg.getText());// 将状态码抛出
		}
		
		List<Element> list = doc.selectNodes(XML_ROW);
		if (list != null && list.size() > 1) {
			// 找到表头，将信息装入集合
			// 遍历集合，组装模型
			for (int i = 1; i < list.size(); i++) {
				Element data = (Element) list.get(i);
				List<Element> fields = data.elements();
				T o = clazz.newInstance();
				for (int j = 0; j < fields.size(); j++) {
					//System.out.println(e.getText());
					Element e  =  fields.get(j);
					String fieldName = fieldNames[j];
					if(e.getText()!=null && e.getText().length()>0){
						if("户政性别代码字典".equals(fieldName)){
							continue;
						}
						setBeanProperty(o, fieldName, e.getText());
					}
				}
				result.add(o);
			}
		}
		
		return result;
	}
	public static String[] getColumns(Class<?> beanClass) throws Exception{
		PropertyDescriptor[] props = propertyDescriptor(beanClass);
		List<String> list = new ArrayList<String>();
		for (int i = 0 ; i < props.length ; i++) {
			PropertyDescriptor p  = props[i];
			if (!p.getName().equals("class")) {
				//System.out.println();
				list.add(p.getName());
			}
		}
		return list.toArray(new String[list.size()]);
	}
	public static PropertyDescriptor[] propertyDescriptor(Class<?> beanClass) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();
		return props;
	}
	private static void setBeanProperty(Object object, String prop, Object propValue) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(prop, object.getClass());
			Method md = pd.getWriteMethod();
			Class<?>[] types = md.getParameterTypes();
			if (types[0].equals(String.class)) {
				md.invoke(object, propValue);
			}else if (types[0].equals(byte[].class) || types[0].equals(ByteArray.class) ) {
				//blob字段需要Base64解码
				md.invoke(object, java.util.Base64.getDecoder().decode(propValue.toString()));
			} else if (types[0].equals(int.class) || types[0].equals(Integer.class)) {
				md.invoke(object, Integer.valueOf(propValue.toString()));
			} else if (types[0].equals(long.class) || types[0].equals(Long.class) ) {
				md.invoke(object, Long.valueOf(propValue.toString()));
			} else if (types[0].equals(boolean.class) || types[0].equals(Boolean.class) ) {
				md.invoke(object, Boolean.valueOf(propValue.toString()));
			} else {
				md.invoke(object, propValue);
			}

		} catch (Exception e) {
			logger.error("setBeanProperty is error :" + e.getMessage());
		}
	}
}
