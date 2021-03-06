package org.mina.sourcegenerator.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * 读取属性文件
 * @author Administrator
 *
 * 2016-7-29 上午08:44:37
 */
public class PropertiesConfigUtil {
	private static Map<String, Properties> propMap = new HashMap<String, Properties>();

	public static Object getProperty(String file, String key) {
		Properties prop = getProperties(file);
		if ((prop != null) && (prop.get(key) != null)) {
			return prop.get(key);
		}
		return null;
	}

	public static Properties getProperties(String file) {
		try {
			if (propMap.get(file) == null) {
				Properties prop = new Properties();
				prop.load(PropertiesConfigUtil.class.getClassLoader()
						.getResourceAsStream(file));
				propMap.put(file, prop);
				return prop;
			}
			return (Properties) propMap.get(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateProperties(Properties prop, String filePath) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			URI fileUri = PropertiesConfigUtil.class.getClassLoader()
					.getResource(filePath).toURI();
			File file = new File(fileUri);

			Properties tmpProp = new Properties();
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			tmpProp.load(bis);

			FileOutputStream fos = new FileOutputStream(file);
			for (Iterator<Object> localIterator = prop.keySet().iterator(); localIterator
					.hasNext();) {
				Object key = localIterator.next();
				tmpProp.setProperty(String.valueOf(key), String.valueOf(prop
						.get(key)));
			}
			tmpProp.store(fos, null);
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取配置文件,获取存放文件路径
	 * @return
	 * 2016-12-7 下午02:45:22
	 */
	public static String getTargetPath(){
		String targetPath="";
		String os = (String) getProperty("OS.properties", "OS");
		if("WINDOWS".equals(os)){
			targetPath=(String) getProperty("OS.properties", "win_path");
		}else{
			targetPath=(String) getProperty("OS.properties", "lin_path");
		}
		return targetPath;
	}
}