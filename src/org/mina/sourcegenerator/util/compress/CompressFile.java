package org.mina.sourcegenerator.util.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class CompressFile {
	/**
	 * 压缩文件或文件夹
	 * 
	 * @param os
	 * @param paths
	 * @throws Exception
	 * 2016-11-21 上午08:43:12
	 */
	public static void compress(OutputStream os, String... paths)
			throws Exception {
		ZipOutputStream zos = new ZipOutputStream(os);
		for (String path : paths) {
			if (path.equals(""))
				continue;
			File file = new File(path);
			if (file.exists()) {
				if (file.isDirectory()) {
					zipDirectory(zos, file.getPath(), file.getName()
							+ File.separator);
				} else {
					zipFile(zos, file.getPath(), "");
				}
			}
		}
		zos.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @param zos
	 * @param filename
	 * @param basePath
	 * @throws Exception
	 * 2016-11-15 下午05:33:38
	 */
	public static void zipFile(ZipOutputStream zos, String filename,
			String basePath) throws Exception {
		File file = new File(filename);
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(filename);
			ZipEntry ze = new ZipEntry(basePath + file.getName());
			zos.putNextEntry(ze);
			ze.setUnixMode(644);//压缩文件设置644，解决linux乱码
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, count);
			}
			fis.close();
		}
	}

	/**
	 * 压缩文件夹
	 * 
	 * @param zos
	 * @param dirName
	 * @param basePath
	 * @throws Exception
	 * 2016-11-15 下午05:33:55
	 */
	public static void zipDirectory(ZipOutputStream zos, String dirName,
			String basePath) throws Exception {
		zos.setEncoding("utf-8");
		File dir = new File(dirName);
		if (dir.exists()) {
			File files[] = dir.listFiles();
			if (files.length > 0) {
				for (File file : files) {
					if (file.isDirectory()) {
						String name=file.getName();
						zipDirectory(zos, file.getPath(), basePath
								+ name.substring(name.lastIndexOf(File.separator) + 1)+ File.separator);
					} else
						zipFile(zos, file.getPath(), basePath);
				}
			} else {
				ZipEntry ze = new ZipEntry(basePath);
				zos.putNextEntry(ze);
				ze.setUnixMode(644);
			}
		}
	}

	/**
	 * sourceFile 被压缩的源文件 fileName 压缩后的文件名 path 压缩路径
	 */
	public static void setFileToZip(File sourceFile, String zipPath)
			throws Exception {
		// 最后压缩的文件名
		File zipFile = new File(zipPath);
		Project prj = new Project();

		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(zipFile);
		zip.setEncoding("utf-8");

		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(sourceFile);
		// fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹
		// eg:zip.setIncludes("*.java");
		// fileSet.setExcludes(...); //排除哪些文件或文件夹
		zip.addFileset(fileSet);
		zip.execute();
	}
}
