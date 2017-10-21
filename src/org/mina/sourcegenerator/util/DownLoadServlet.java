package org.mina.sourcegenerator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(DownLoadServlet.class);

	public DownLoadServlet() {
		super.destroy();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String uid = request.getParameter("uid");
		
		logger.debug("FileName-> " + fileName);
		String filepath = "";
		InputStream inputStream = null;
		try {
			filepath = PropertiesConfigUtil.getTargetPath()+ File.separator + 
						uid + File.separator;//文件存放路径
			System.out.println(getServletContext().getMimeType(fileName));
			
			response.setContentType(getServletContext().getMimeType(fileName)); 
			//response.setContentType("text/plain");
			response.setHeader("Location", fileName);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.flushBuffer();
			OutputStream outputStream = response.getOutputStream();
			inputStream = new FileInputStream(filepath+ fileName);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
//			outputStream.close();
		} catch (FileNotFoundException e1) {
			logger.debug("没有找到您要的文件");
			logger.error(e1.getMessage(), e1);
			e1.printStackTrace();
		} catch (Exception e) {
			logger.debug("系统错误，请及时与管理员联系");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (inputStream != null) 
				inputStream.close();
		}
	}
	
}