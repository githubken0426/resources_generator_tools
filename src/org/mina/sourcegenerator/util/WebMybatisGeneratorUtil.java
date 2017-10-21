package org.mina.sourcegenerator.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mina.sourcegenerator.entity.GeneratorConfig;
import org.mina.sourcegenerator.main.SourceGeneratorConst;
import org.mina.sourcegenerator.util.excel.ExcelReader;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * MBG工具类
 * 
 * @author ken 2016-12-1 下午05:31:33
 */
public final class WebMybatisGeneratorUtil {
	private static String confFilePath = "E:\\MyEclipse_WS2016\\GtercnResourceTools\\WebRoot\\mybatis-generator\\generatorCarhome.xml";
	private static String path="E:\\Tomcat\\HTTPS_Tomcat\\apache-tomcat-6.0.29\\webapps" +
			"\\GtercnResourceTools\\resources\\67b1feada00943bc9b82a1c94886693d\\generatorConfig.xml";
	// 测试方法
	public static void main(String[] args) {
		try {
			generatorTemplate(confFilePath);
//			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
//					new File(SourceGeneratorConst.EXCEL)));
//			GeneratorConfig config = new GeneratorConfig();
//			config.setExcelPath(SourceGeneratorConst.EXCEL);
//			config.setTargetProject(SourceGeneratorConst.OUTPUT);
//			config.setConnectionURL("http:localhost");
//			config.setUserId("admin");
//			config.setPassword("123456");
//			generatorConfigXML(confFilePath, config,workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * step 1 动态生成MBG配置文件
	 * 
	 * @param xmlPath 读取模板MGB XML
	 * @param config 配置属性 2016-12-2 上午11:11:05
	 * @throws ParserConfigurationException 
	 * @throws Exception 
	 * @throws SAXException 
	 * @throws Exception
	 */
	
	public static void generatorConfigXML(String xmlPath,GeneratorConfig config, HSSFWorkbook workbook)
						throws ParserConfigurationException, SAXException, Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				InputStream is = getClass().getResourceAsStream("/mybatis-generator-config_1_0.dtd");
				return new InputSource(is);
			}
		});
		Document document = builder.parse(new File(xmlPath));
		document.normalize();
		Node root = document.getDocumentElement();
		if (root.hasChildNodes()) {// 判断是否有根元素
			String basePackage = (String) (ExcelReader.readData(config.getExcelPath(), 2, "J10"));// 项目包名
			NodeList ftpnodes = root.getChildNodes();
			// 循环取得所有节点
			for (int i = 0; i < ftpnodes.getLength(); i++) {
				//配置驱动路径
				Node classPathEntry=ftpnodes.item(i);
				if (classPathEntry.getNodeType() == Node.ELEMENT_NODE
						&& "classPathEntry".equals(classPathEntry.getNodeName())) {
					NamedNodeMap map = classPathEntry.getAttributes();
					Node connectionURL = map.getNamedItem("location");
					connectionURL.setNodeValue(config.getBasePath()+"mybatis-generator"+File.separator+"mysql-connector-java-5.1.5-bin.jar");
				}
				NodeList ftplist = ftpnodes.item(i).getChildNodes();
				for (int k = 0; k < ftplist.getLength(); k++) {
					Node subnode = ftplist.item(k);
					String nodeName = subnode.getNodeName();
					int nodeType = subnode.getNodeType();
					// 设置 数据库链接信息
					if (nodeType == Node.ELEMENT_NODE
							&& "jdbcConnection".equals(nodeName)) {
						NamedNodeMap map = subnode.getAttributes();
						Node connectionURL = map.getNamedItem("connectionURL");
						connectionURL.setNodeValue(config.getConnectionURL());
						Node userId = map.getNamedItem("userId");
						userId.setNodeValue(config.getUserId());
						Node password = map.getNamedItem("password");
						password.setNodeValue(config.getPassword());
					}
					if (nodeType == Node.ELEMENT_NODE// 设置 entity
							&& "javaModelGenerator".equals(nodeName)) {
						NamedNodeMap map = subnode.getAttributes();
						Node targetPackage = map.getNamedItem("targetPackage");
						targetPackage.setNodeValue(basePackage + ".entity");
						Node targetProject = map.getNamedItem("targetProject");
						targetProject.setNodeValue(config.getTargetProject());
					}
					if (nodeType == Node.ELEMENT_NODE// 设置 mapper
							&& "sqlMapGenerator".equals(nodeName)) {
						NamedNodeMap map = subnode.getAttributes();
						Node targetPackage = map.getNamedItem("targetPackage");
						targetPackage.setNodeValue(basePackage + ".mapper");
						Node targetProject = map.getNamedItem("targetProject");
						targetProject.setNodeValue(config.getTargetProject());
					}
					if (nodeType == Node.ELEMENT_NODE// 设置 dao
							&& "javaClientGenerator".equals(nodeName)) {
						NamedNodeMap map = subnode.getAttributes();
						Node targetPackage = map.getNamedItem("targetPackage");
						targetPackage.setNodeValue(basePackage + ".dao");
						Node targetProject = map.getNamedItem("targetProject");
						targetProject.setNodeValue(config.getTargetProject());
					}
				}
				// context跟节点，用于追加子节点table
				Node contextNode = ftpnodes.item(i);
				if (contextNode.getNodeType() == Node.ELEMENT_NODE
						&& contextNode.getNodeName().equals("context")) {
					// 读取上传的DB模板
					for (int n = 2; n < workbook.getNumberOfSheets(); n++) {
						// 需要生成的实体类名
						String entityName = (String) (ExcelReader.readData(config.getExcelPath(), n, "Q6"));
						// 表名
						String tbName = (String) (ExcelReader.readData(config.getExcelPath(), n, "J7"));
						// 创建新的节点 table
						Element newNode = document.createElement("table");
						newNode.setAttribute("domainObjectName", entityName);
						newNode.setAttribute("tableName", tbName);
						newNode.setAttribute("enableCountByExample", "false");
						newNode.setAttribute("enableUpdateByExample", "false");
						newNode.setAttribute("enableDeleteByExample", "false");
						newNode.setAttribute("enableSelectByExample", "false");
						contextNode.appendChild(newNode);
					}
				}
			}
		}
		writeXML(document, config.getTargetProject()+File.separator+"generatorConfig.xml");
	}

	/**
	 * step 2 写入xml
	 * 
	 * @param document
	 * @param filename
	 * @return
	 * @throws TransformerException
	 *  2016-12-2 下午02:44:12
	 */
	public static boolean writeXML(Document document, String filename) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			// 编码
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filename));
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, document.getDoctype().getPublicId());
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId()); 
            transformer.transform(source, result);
			System.out.println("成功生成xml");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * step 3 
	 * 调用MBG API生成entity dao mapper模板
	 * 
	 * @param confFilePath
	 * @throws XMLParserException 
	 * @throws IOException 
	 * @throws InvalidConfigurationException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws Exception
	 * 2016-12-2 下午04:32:56
	 */
	public static void generatorTemplate(String confFilePath) throws Exception, SQLException{
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(confFilePath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	}
}
