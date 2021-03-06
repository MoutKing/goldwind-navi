package com.gw.web.common.utils;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

	public static String string2Json(String s) {
		StringBuilder sb = new StringBuilder(s.length() + 20);
		sb.append('\"');
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
			}
		}
		sb.append('\"');
		return sb.toString();
	}

	public static String number2Json(Number number) {
		return number.toString();
	}

	public static String boolean2Json(Boolean bool) {
		return bool.toString();
	}

	public static String date2Json(java.util.Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String array2Json(Object[] array) {
		if (array.length == 0)
			return "[]";
		StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (Object o : array) {
			sb.append(toJson(o));
			sb.append(',');
		}
		// 将最后添加的 ',' 变为 ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String map2Json(Map<String, Object> map) {
		if (map.isEmpty())
			return "{}";
		StringBuilder sb = new StringBuilder(map.size() << 4);
		sb.append('{');
		Set<String> keys = map.keySet();
		for (String key : keys) {
			Object value = map.get(key);
			sb.append('\"');
			sb.append(key);
			sb.append('\"');
			sb.append(':');
			sb.append(toJson(value));
			sb.append(',');
		}
		// 将最后的 ',' 变为 '}':
		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public static String toJson(Object o) {
		if (o == null)
			return "null";
		if (o instanceof String)
			return string2Json((String)o);
		if (o instanceof Boolean)
			return boolean2Json((Boolean)o);
		if (o instanceof Number)
			return number2Json((Number)o);
		if (o instanceof java.util.Date)
			return date2Json((java.util.Date)o);
		if (o instanceof Map)
			return map2Json((Map<String, Object>)o);
		if (o instanceof List)
			return listToJson((List<?>)o);
		if (o instanceof Object[])
			return array2Json((Object[])o);

		return beanToJson(o);
	}

	public static String beanToJson(Object obj) {
		//System.out.println("obj class=" + obj.getClass().getName());
		StringBuilder sb = new StringBuilder();
		try {

			if (obj == null) {
				return "{}";
			}
			sb.append('{');
			Field[] fields = obj.getClass().getDeclaredFields();
			ArrayList<String> fnList = new ArrayList<String>();
			ArrayList<String> fnLowList = new ArrayList<String>();
			for(int i=0; i<fields.length;i++ ){
				fnList.add(fields[i].getName());
				fnLowList.add(fields[i].getName().toLowerCase());
			}
			
			PropertyDescriptor[] voPds = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
			for (int i = 0; i < voPds.length; i++) {
				PropertyDescriptor voDesc = voPds[i];
				String propName = voDesc.getName();
				if(fnLowList.contains(propName.toLowerCase())){
					propName = fnList.get(fnLowList.indexOf(propName.toLowerCase()));
				}
				Method method = voDesc.getReadMethod();
				if (method == null) {
					continue;
				}
				Object object = voDesc.getReadMethod().invoke(obj);
				if (object != null && !object.getClass().getName().equals("java.lang.Class")) {
					sb.append('\"');
					sb.append(propName);
					sb.append('\"');
					sb.append(':');
					sb.append(toJson(object));
					sb.append(',');
				}

			}
			if (sb.length() > 1) {
				sb.setCharAt(sb.length() - 1, '}');
			} else {
				sb.append('}');
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	public static String objectToJson(Object object) {
		StringBuilder json = new StringBuilder();
		if (object == null) {
			json.append("\"\"");
		} else if (object instanceof String || object instanceof Integer) {
			json.append("\"").append(object.toString()).append("\"");
		} else if (object instanceof Map) {
			json.append(map2Json((Map<String, Object>)object));
		} else {
			json.append(beanToJson(object));
		}
		return json.toString();
	}

	public static String listToJson(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(objectToJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static void main(String[] args) {
//		List<Fengji> list = new ArrayList<Fengji>();
//		Fengji f1 = new Fengji();
//		f1.setP("YELUN");
//		f1.setN("叶轮系统");
//		ArrayList<String> l1 = new ArrayList<String>();
//		l1.add("FENYE01");
//		l1.add("BLADE_001");
//		l1.add("BLADE_002");
//		l1.add("BLADE_003");
//		l1.add("LUNGU002");
//		f1.setT(l1);
//		list.add(f1);
//		
//		Fengji f2 = new Fengji();
//		f2.setP("JICANG");
//		f2.setN("机舱系统");
//		ArrayList<String> l2 = new ArrayList<String>();
//		l2.add("DIZUO007");
//		l2.add("FADIANDINGZI004");
//		l2.add("FADIANZHUANZI008");
//		l2.add("TISHENGJI006");
//		f2.setT(l2);
//		list.add(f2);
//		
//		Fengji f3 = new Fengji();
//		f3.setP("TOWER");
//		f3.setN("塔筒");
//		ArrayList<String> l3 = new ArrayList<String>();
//		l3.add("TOWER");
//		f3.setT(l3);
//		list.add(f3);
//		
//		Fengji f4 = new Fengji();
//		f4.setP("CEFENXITONG005");
//		f4.setN("测风系统");
//		ArrayList<String> l4 = new ArrayList<String>();
//		l4.add("CEFENXITONG005");
//		f4.setT(l4);
//		list.add(f4);
//		
//		Fengji f5 = new Fengji();
//		f5.setP("DIANKONGGUI");
//		f5.setN("电控柜");
//		ArrayList<String> l5 = new ArrayList<String>();
//		l5.add("BUTTOM_BOX1");
//		l5.add("BUTTOM_BOX2");
//		f5.setT(l5);
//		list.add(f5);
//		
//		Fengji f6 = new Fengji();
//		f6.setP("FENYE01");
//		f6.setN("风叶");
//		ArrayList<String> l6 = new ArrayList<String>();
//		l6.add("FENYE01");
//		f6.setT(l6);
//		list.add(f6);
//		
//		Fengji f7 = new Fengji();
//		f7.setP("BLADE_001");
//		f7.setN("叶片1");
//		ArrayList<String> l7 = new ArrayList<String>();
//		l7.add("BLADE_001");
//		f7.setT(l7);
//		list.add(f7);
//		
//		Fengji f8 = new Fengji();
//		f8.setP("BLADE_002");
//		f8.setN("叶片2");
//		ArrayList<String> l8 = new ArrayList<String>();
//		l8.add("BLADE_002");
//		f8.setT(l8);
//		list.add(f8);
//		
//		Fengji f9 = new Fengji();
//		f9.setP("BLADE_003");
//		f9.setN("叶片3");
//		ArrayList<String> l9 = new ArrayList<String>();
//		l9.add("BLADE_003");
//		f9.setT(l9);
//		list.add(f9);
//		
//		Fengji f10 = new Fengji();
//		f10.setP("LUNGU002");
//		f10.setN("轮毂");
//		ArrayList<String> l10 = new ArrayList<String>();
//		l10.add("LUNGU002");
//		f10.setT(l10);
//		list.add(f10);
//		
//		Fengji f11 = new Fengji();
//		f11.setP("DIZUO007");
//		f11.setN("底座");
//		ArrayList<String> l11 = new ArrayList<String>();
//		l11.add("DIZUO007");
//		f11.setT(l11);
//		list.add(f11);
//		
//		Fengji f12 = new Fengji();
//		f12.setP("FADIANDINGZI004");
//		f12.setN("发电机定子");
//		ArrayList<String> l12 = new ArrayList<String>();
//		l12.add("FADIANDINGZI004");
//		f12.setT(l12);
//		list.add(f12);
//		
//		Fengji f13 = new Fengji();
//		f13.setP("FADIANZHUANZI008");
//		f13.setN("发电机转子");
//		ArrayList<String> l13 = new ArrayList<String>();
//		l13.add("FADIANZHUANZI008");
//		f13.setT(l13);
//		list.add(f13);
//		
//		Fengji f14 = new Fengji();
//		f14.setP("TISHENGJI006");
//		f14.setN("提升机");
//		ArrayList<String> l14 = new ArrayList<String>();
//		l14.add("TISHENGJI006");
//		f14.setT(l14);
//		list.add(f14);
//		
//		Fengji f15 = new Fengji();
//		f15.setP("BUTTOM_BOX1");
//		f15.setN("按钮1");
//		ArrayList<String> l15 = new ArrayList<String>();
//		l15.add("BUTTOM_BOX1");
//		f15.setT(l15);
//		list.add(f15);
//		
//		Fengji f16 = new Fengji();
//		f16.setP("BUTTOM_BOX2");
//		f16.setN("按钮2");
//		ArrayList<String> l16 = new ArrayList<String>();
//		l16.add("BUTTOM_BOX2");
//		f16.setT(l16);
//		list.add(f16);
//		
//		System.out.println(listToJson(list));
	}

}
