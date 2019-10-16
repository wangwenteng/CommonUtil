package com.wwt.commonUtil.util.xml;

import com.sun.mail.iap.ByteArray;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

public class XmlConverUtil {

	private static final Logger logger = LoggerFactory.getLogger("");

	public static List<Element> getData(String xml) {
		if(StringUtils.isEmpty(xml)){
			return null;
		}
		return readDocument(xml);
	}

	public static Document getDocument(String xml) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static List<Element> readDocument(String xml) {
		List elements = getDocument(xml).getRootElement().elements();
		return getElement(elements);
	}

	public static List<Element> getElement(List<Element> sonElement) {
		List<Element> rows = null;
		for (Element element : sonElement) {
			if (element.getName().equals("Method")) {
				List elements = element.elements();
				// 获取items节点
				Element items = (Element) elements.get(1);
				// 获取item节点
				Element item = (Element) items.elements().get(0);
				// 获取value节点
				Element value = (Element) item.elements().get(0);
				// 获取row节点
				if (value.elements().size() > 2) {
					rows= value.elements();
					rows.remove(0);
					rows.remove(0);
				}
			}
		}
		return rows;
	}

	/**
	 * 用于将资源服务返回的xml解析为对应的Bean
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> parseXml(String xml, Class<T> clazz) throws Exception {
		List<T> result = new ArrayList<T>();
		Document doc = DocumentHelper.parseText(xml);
		// 获取数据节点
		List<Element> list = (List<Element>) doc.selectNodes("RBSPMessage/Method/Items/Item/Value/Row");

		if (list != null && list.size() > 0) {
			Element stateElement = (Element) list.get(0);
			// 获取返回数据状态
			String state = ((Element) stateElement.elements().get(0)).getText();

			if ("000".equals(state)) {
				if (list.size() < 2) {
					return null;
				} else {
					List<Element> fieldNames = ((Element) list.get(1)).elements();
					for (int i = 2; i < list.size(); i++) {
						Element data = (Element) list.get(i);
						List<Element> fields = data.elements();
						T o = clazz.newInstance();
						for (int j = 0; j < fields.size(); j++) {
							// System.out.println(e.getText());
							Element e = fields.get(j);
							String fieldName = fieldNames.get(j).getText();
							if (e.getText() != null && e.getText().length() > 0) {
								if ("户政性别代码字典".equals(fieldName)) {
									fieldName = "XBDM";
								}
								setBeanProperty(o, fieldName, e.getText());
							}
						}
						result.add(o);
					}
				}
			} else {
				System.out.println("发生异常，错误码：" + state);
				// logger.error("发生异常，错误码："+state);
				throw new Exception("error code is " + state);
			}
		}

		return result;
	}

	private static void setBeanProperty(Object object, String prop, Object propValue) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(prop, object.getClass());
			Method md = pd.getWriteMethod();
			Class<?>[] types = md.getParameterTypes();
			if (types[0].equals(String.class)) {
				md.invoke(object, propValue);
			} else if (types[0].equals(byte[].class) || types[0].equals(ByteArray.class)) {
				// blob字段需要Base64解码
				md.invoke(object, Base64.getDecoder().decode(propValue.toString()));
			} else if (types[0].equals(int.class) || types[0].equals(Integer.class)) {
				md.invoke(object, Integer.valueOf(propValue.toString()));
			} else if (types[0].equals(long.class) || types[0].equals(Long.class)) {
				md.invoke(object, Long.valueOf(propValue.toString()));
			} else if (types[0].equals(boolean.class) || types[0].equals(Boolean.class)) {
				md.invoke(object, Boolean.valueOf(propValue.toString()));
			} else {
				md.invoke(object, propValue);
			}

		} catch (Exception e) {
			logger.error("setBeanProperty is error :" + e.getMessage());
		}
	}

	// 遍历当前节点下的所有节点
	public static void listNodes(Element node) {
		System.out.println("当前节点的名称：" + node.getName());
		// 首先获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attribute : list) {
			System.out.println("属性" + attribute.getName() + ":" + attribute.getValue());
		}
		// 如果当前节点内容不为空，则输出
		if (!(node.getTextTrim().equals(""))) {
			System.out.println(node.getName() + "：" + node.getText());
		}
		// 同时迭代当前节点下面的所有子节点
		// 使用递归
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e);
		}
	}
}
