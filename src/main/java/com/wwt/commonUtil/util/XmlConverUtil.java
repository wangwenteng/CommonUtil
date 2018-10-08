package com.wwt.commonUtil.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlConverUtil {
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
