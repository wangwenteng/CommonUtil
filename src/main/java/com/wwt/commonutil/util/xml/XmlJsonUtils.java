package com.wwt.commonutil.util.xml;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import com.alibaba.fastjson.JSONObject;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alany on 2018/7/10.
 */
public class XmlJsonUtils {
    public static JSONObject xml2Json(String xmlStr) {
        JSONObject json = new JSONObject();
        try {
            if (StringUtils.isEmpty(xmlStr)) {
                return null;
            }
            xmlStr = xmlStr.replaceAll("\\\n", "");
            byte[] xml = xmlStr.getBytes("UTF-8");
            InputStream is = new ByteArrayInputStream(xml);
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            Element root = doc.getRootElement();
            json.put(root.getName(), iterateElement(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    private static JSONObject iterateElement(Element element) {
        List<Element> node = element.getChildren();
        JSONObject obj = new JSONObject();
        List list = null;
        for (Element child : node) {
            list = new LinkedList();
            String text = child.getTextTrim();
            if (StringUtils.isBlank(text)) {
                if (child.getChildren().size() == 0) {
                    continue;
                }
                if (obj.containsKey(child.getName())) {
                    list = (List) obj.get(child.getName());
                }
                list.add(iterateElement(child)); //遍历child的子节点
                obj.put(child.getName(), list);
            } else {
                if (obj.containsKey(child.getName())) {
                    Object value = obj.get(child.getName());
                    try {
                        list = (List) value;
                    } catch (ClassCastException e) {
                        list.add(value);
                    }
                }
                if (child.getChildren().size() == 0) { //child无子节点时直接设置text
                    obj.put(child.getName(), text);
                } else {
                    list.add(text);
                    obj.put(child.getName(), list);
                }
            }
        }
        return obj;
    }

    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Response><SenderID>FC9-FDDA-4312-923E-175C</SenderID><Method><Name>QueryBzMhDz</Name><Security Algorithm=\"\"></Security><Items><Item><Name>ResultInfo</Name><Value Type=\"Fields\">\n" +
                "<Data type=\"string\">PAS_CHN_NM</Data><Data type=\"date\">ADDTIME</Data></Value></Item><Item><Name>Result</Name><Value Type=\"Records\"><Records><Record><Data>郭*</Data><Data>2018-07-26 17:44:34.0</Data></Record><Record><Data>姚*</Data><Data>2018-07-26 17:44:34.0</Data></Record><Record><Data>黄*</Data><Data>2018-07-26 17:44:34.0</Data></Record><Record><Data>*</Data><Data>2018-07-26 17:44:34.0</Data></Record><Record><Data>熊*</Data><Data>2018-07-26 17:44:34.0</Data></Record></Records></Value></Item><Item><Name>Total</Name><Value/></Item></Items></Method></Response>\n";
        JSONObject jsonObject = xml2Json(xml);
        System.out.println(jsonObject.toJSONString());
    }
}