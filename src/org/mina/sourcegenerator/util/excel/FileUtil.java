package org.mina.sourcegenerator.util.excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	/**
	 * 创建目录
	 * 
	 * @param fileName
	 */
	public static void createDir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 以行为单位读取文件
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String content = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			content = "";
			int line = 1;

			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				content = content + tempString + "\r\n";
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return content;
	}

	public static String writeFile(String fname, String content)
			throws Exception {
		return writeFile(fname, content, false);
	}

	public static String writeFile(String fname, String content, boolean append)
			throws Exception {

		File file = new File(fname);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!append) {
			// 非追加模式，如果原文件存在就删除
			if (file.exists()) {
				file.delete();
			}
		}
		file.createNewFile();

		FileWriter fw = new FileWriter(file, append);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.flush();
		bw.close();
		fw.close();

		return "OK";
	}
}
