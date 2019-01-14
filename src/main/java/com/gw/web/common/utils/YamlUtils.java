package com.gw.web.common.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.gw.web.userCenter.model.FieldName;
import com.gw.web.userCenter.model.Permission;

public class YamlUtils {

	public static List<Permission> permissionList = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static List<Permission> getPermissionList(String fileName) {
		List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
		Yaml yaml = new Yaml();
		InputStream in = YamlUtils.class.getClassLoader().getResourceAsStream(fileName);
		if (in != null) {
			listMaps = (List<Map<String, Object>>) yaml.loadAs(in, List.class).get(0);
			for (Map<String, Object> map : listMaps) {
				getChildrens(map);
			}
			for (int j = 0; j< permissionList.size(); j++) {
				System.out.println(permissionList.get(j));
			}
		}
		return permissionList;
	}

	@SuppressWarnings("unchecked")
	public static void getChildrens(@SuppressWarnings("rawtypes") Map map) {
		Permission permission = null;
		if (map.containsKey("childrens")) {
			@SuppressWarnings("rawtypes")
			List<Map<String, Object>> subListMaps = (List) map.get("childrens");
			permission = map2Bean(map, Permission.class);
			permissionList.add(permission);
			for (int i = 0; i < subListMaps.size(); i++) {
				getChildrens(subListMaps.get(i));
			}
		} else {
			permission = map2Bean(map, Permission.class);
			permissionList.add(permission);
		}
	}

	/**
	 * map转bean
	 * 
	 * @param source   map属性
	 * @param instance 要转换成的备案
	 * @return 该bean
	 */
	public static <T> T map2Bean(Map<String, Object> source, Class<T> instance) {
		try {
			T object = instance.newInstance();
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				FieldName fieldName = field.getAnnotation(FieldName.class);
				if (fieldName != null) {
					field.set(object, source.get(fieldName.value()));
				} else {
					field.set(object, source.get(field.getName()));
				}
			}
			return object;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
