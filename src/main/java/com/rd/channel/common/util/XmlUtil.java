package com.rd.channel.common.util;

import org.apache.commons.lang3.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

public class XmlUtil {
	 public static Object getObjectFromXML(String xml,String alias , Class<?> tClass) {
		//将从API返回的XML数据映射到Java对象
       XStream xStreamForResponseData = new XStream();
       if(StringUtils.isNotBlank(alias)){
    	   xStreamForResponseData.alias(alias, tClass);
       }
       xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
       return xStreamForResponseData.fromXML(xml);
	 }
	 
	 public static String getXMLFromObject(String alias, Object object){
		 XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		 //将要提交给API的数据对象转换成XML格式数据Post给API
		 xStreamForRequestPostData.alias(alias, object.getClass());
		 return xStreamForRequestPostData.toXML(object);
	 }

}
