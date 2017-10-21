package org.mina.sourcegenerator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mina.sourcegenerator.entity.GeneratorConfig;
import org.mina.sourcegenerator.entity.Param;
import org.mina.sourcegenerator.main.SourceGeneratorConst;
import org.mina.sourcegenerator.util.PropertiesConfigUtil;
import org.mina.sourcegenerator.util.StringUtil;
import org.mina.sourcegenerator.util.WebMybatisGeneratorUtil;
import org.mina.sourcegenerator.util.WebSourceGeneratorUtil;
import org.mina.sourcegenerator.util.compress.CompressFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

@Controller
public class FileUploadController {
	private Logger logger = Logger.getLogger(FileUploadController.class);

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 *             2016-11-15 下午01:06:50
	 */
	@ResponseBody
	@RequestMapping(value = { "/upload.do" }, produces = "text/html; charset=UTF-8")
	public void upLoad(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		PrintWriter writer = response.getWriter();
		try {
			String uid = StringUtil.getUUID();
			// 获取根路径
			String sourcePath = request.getSession().getServletContext()
					.getRealPath("/");
			String targetPath = PropertiesConfigUtil.getTargetPath()
					+ File.separator + uid + File.separator;// 文件存放路径
			// 创建文件
			File dirPath = new File(targetPath);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}
			// 获得文件名：
			String realFileName = file.getOriginalFilename();

			GeneratorConfig config = new GeneratorConfig();
			config.setBasePath(sourcePath);
			config.setTargetProject(targetPath);
			String projectName = request.getParameter("projectName");// 项目名
			config.setProjectName(projectName);
			String author = request.getParameter("author");// 上传者
			config.setAuthor(author);
			String projectType = request.getParameter("projectType");// 1:java src 0:sql
																		
			int mode = (projectType == null || projectType == "") ? -1
					: Integer.valueOf(projectType);
			config.setProjectType(mode);
			String connectionURL = request.getParameter("connectionURL");// 数据库连接url
			config.setConnectionURL(connectionURL);
			String userId = request.getParameter("userId");// 数据库账号
			config.setUserId(userId);
			String password = request.getParameter("password");// 数据库密码
			config.setPassword(password);

			String filePath = targetPath + realFileName;// 保存数据库excel模板到服务器
			config.setExcelPath(filePath);

			File uploadFile = new File(filePath);
			FileCopyUtils.copy(file.getBytes(), uploadFile);
			logger.debug("上传文件:" + realFileName);
			// 读取上传的excel，动态写入MBG配置文件
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					new File(filePath)));
			if (mode == SourceGeneratorConst.MODE_SRC) {
				// 生成generatorConfig.xml文件
				String confFilePath = sourcePath + "mybatis-generator"
						+ File.separator + "generatorConfig.xml";
				WebMybatisGeneratorUtil.generatorConfigXML(confFilePath,
						config, workbook);
				// 根据generatorConfig.xml生成entity、dao、mapper模板
				WebMybatisGeneratorUtil.generatorTemplate(targetPath
						+ File.separator + "generatorConfig.xml");
			}
			// 根据projectType生成java文件或SQL建表语句
			int sheetNum = workbook.getNumberOfSheets();
			Param param = new Param(filePath, targetPath, sourcePath,
					(sheetNum - 2));
			WebSourceGeneratorUtil.execute(sheetNum, mode, param);

			String debugType = (mode == 1) ? "Java src" : " DB Sql";
			String info = "项目名:" + projectName + ",上传者:" + author + ",数据库连接信息:"
					+ connectionURL + ",账号:" + userId + ",密码:" + password
					+ ",生成类型:" + debugType + ",创建时间:"
					+ format.format(new Date());
			logger.debug(info);
			long current = System.currentTimeMillis();
			
			// 压缩文件
			String zipName = current + ".zip";
			FileOutputStream fos = new FileOutputStream(targetPath + zipName); // 输出的ZIP文件流
			
			CompressFile.compress(fos, targetPath
					+ SourceGeneratorConst.COMPRESS_JAVA_PATH, targetPath
					+ SourceGeneratorConst.COMPRESS_CONFIG_PATH, targetPath
					+ SourceGeneratorConst.COMPRESS_JSP_PATH, targetPath
					+ SourceGeneratorConst.COMPRESS_DB_PATH);
			
			JSONObject josn = JSONObject.fromObject("{zipName:" + '"' + zipName
					+ '"' + ",zipDir:" + '"' + uid + '"' + "}");
			writer.write(josn.toString());
		} catch (SQLException e) {
			writer.write("-1");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			writer.write("-2");
			e.printStackTrace();
		} catch (SAXException e) {
			writer.write("-2");
			e.printStackTrace();
		} catch (Exception e) {
			writer.write("-2");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		System.out.println(format.format(new Date()));
	}
}
