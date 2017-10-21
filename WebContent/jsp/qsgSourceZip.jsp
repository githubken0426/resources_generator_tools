<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/jsp/common/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.flushBuffer();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Quickly Source Generator</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/global.css" />
	<script src="<%=request.getContextPath() %>/resources/js/jquery1.9.0.min.js"></script> 
	<script src="<%=request.getContextPath() %>/resources/js/table.js"></script> 
<style  type="text/css">
#alternatecolor td{font-size:14px;font-weight:bold;}
#alternatecolor2 td{font-size:14px;font-weight:bold;}

#alternatecolor input{padding:4px;}
</style>
<script type="text/javascript">
	//上传文件
	function submitForm(){
		var projectName=$.trim($("#projectName").val());
		if(!projectName){
			alert("请输入项目名");
			return false;
		}
		var author=$.trim($("#author").val());
		if(!author){
			alert("请输入姓名");
			return false;
		}
		var connectionURL=$.trim($("#connectionURL").val());
		if(!connectionURL){
			alert("请输入数据库链接");
			return false;
		}
		var userId=$.trim($("#userId").val());
		if(!userId){
			alert("请输入数据库账号");
			return false;
		}
		var password=$.trim($("#password").val());
		if(!password){
			alert("请输入数据库密码");
			return false;
		}
		var file=document.getElementById("file").value;
		if(!file){
			alert("请选择文件");
			return false;
		}
		//ie不支持formData var formData = new FormData($("#uploadForm")[0]);
		var formData = new FormData($("#uploadForm")[0]);
		$.ajax({
	        url:"${pageContext.request.contextPath}/upload.do",  
	        type:"POST",  
	        dataType:"json",  
	        data:formData,
	        async:true,
	        cache: false,
	        processData:false,//因为data值是FormData对象，不需要对数据做处理
	        /* 
	        	contentType设置为false，不设置contentType值，因为是由<form>表单构造的FormData对象，
	     		且已经声明了属性enctype="multipart/form-data"，所以这里设置为false
	        */
	        contentType: false,
	        success:function(data,textStatus,jqXHR){
	    	    if(data=="-1"){
		    	    alert("Access denied！数据库账号密码错误！");
	    	    }else if(data=="-2"){
		    	    alert("生成失败！");
	    	    }else{
	    	    	alert("生成成功！");
	    	    }
	    	    $("#downZip").text(data.zipName);
	    	    $("#fileName").val(data.zipName);
	    	    $("#uid").val(data.zipDir);
		        console.log(data)
		        console.log(textStatus)
		        console.log(jqXHR)
		    },
		    error:function(data,xhr,textStatus){
			    alert("出现错误");
		        console.log('错误')
		        console.log(xhr)
		        console.log(textStatus)
		    }	
	    });
	}
	//下载zip
	function downLoadZip(){
		$("#downForm").submit();
	}

<%--
	//ajax无法执行下载
	function downLoadZip_Deprecated(){
		var fileName=$("#fileName").val();
		var url=encodeURI("${pageContext.request.contextPath}/servlet/downLoadServlet");
		$.ajax({
	        url:url,  
	        type:"POST",  
	        //dataType:"json",  
	        data:{fileName:fileName},
	        async:true,
	        success:function(data){
	    	   alert(data);
		    },
		    error:function(data,xhr,textStatus){
		    	alert(data);
		    }	
	    });
	}
	 window.onload=function(){
	       altRows('alternatecolor');
	       altRows('alternatecolor2');
	     }
--%>
</script>
  </head>
  
  <body>
  <div class="container">
	<form method="post"  id="uploadForm" enctype="multipart/form-data">
		 <table width="100%" class="altrowstable" id="alternatecolor" style="color:gray;">
	        <tr>
	            <th colspan="2" height="25" style="font-size:14px;">盖特尔快速源码生成工具<br/>Quickly Source Generator(QSG V1.0)</th>
	        </tr>
	        <tr>
	        	<td width="30%">1.数据库设计书<br/>(注:OfficeExcel 2003)</td>
	        	<td align="center"><a href="${pageContext.request.contextPath}/resources/file/QSG_db_template.xls">模板下载</a></td>
	        </tr>
	        <tr>
	        	<td>2.填写相应信息</td>
	        	<td align="center">
	        		项目名:<input type="text" name="projectName" id="projectName" placeholder="请输入项目名" style="margin-bottom:5px;"/>
	        		姓&nbsp;&nbsp;名：<input type="text" name="author" id="author" placeholder="请输入姓名"style="width:100px;"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td>3.生成类型</td>
	        	<td align="center">
	        		<label style="margin-right:60px;cursor:pointer;">
	        			<input type="radio" value="1" name="projectType" checked="checked"/>&nbsp;Java Src
	        		</label>
	        		<label  style="cursor:pointer;">
	        			<input type="radio" value="0" name="projectType"/>&nbsp;sql
	        		</label>
	        	</td>
	        </tr>
	        <tr>
	        	<td>4.数据库信息</td>
	        	<td align="center">
	        		连&nbsp;接：<input type="text" name="connectionURL" id="connectionURL" value="jdbc:mysql://192.168.1.75/car_home";
	        			placeholder="jdbc:mysql://hostname/projectname" style="margin-bottom:5px;width:320px;"/>
	        		<br/>
	        		<span>
	        		用&nbsp;户 ：<input type="text" name="userId"id="userId" style="width:130px;" placeholder="数据库账号"/>
	        		</span>
	        		<span >
	        		密&nbsp;码：<input type="text" name="password" id="password"style="width:130px;"  placeholder="密码"/>
	        		</span>
	        	</td>
	        </tr>
	        <tr>
	        	<td>5.选择模板并上传</td>
	            <td align="center" height="25">
	            	<input type="file" name="file" id="file"/>
	            	<input type="button" onclick="submitForm()" value="提&nbsp;交"/>
	            </td>
	        </tr>
        </table>
	</form>
	<table width="100%" class="altrowstable" id="alternatecolor2" style="color:gray;">
	        <tr>
	        	<td width="30%">6.下载生成的源码(ZIP格式)</td>
	            <td  align="center" height="25">
	            	<form action="${pageContext.request.contextPath}/servlet/downLoadServlet" method="post" id="downForm">
	            		<input type="hidden" id="fileName" name="fileName"/>
	            		<input type="hidden" id="uid" name="uid"/>
	            		<a href="javascript:void(0);" id="downZip" onclick="downLoadZip()"></a>
	            	</form>
	            </td>
	        </tr>
	        <tr>
	            <td>7.参考：模板</td>
	            <td align="center" height="25">
	            	<a href="${pageContext.request.contextPath}/resources/file/QSG_db_template.xls">模1、DB设计书(模板)</a>&nbsp;
	            	<a href="${pageContext.request.contextPath}/resources/file/QSG-CMS.war">模2、QSG-CMS.war</a>&nbsp;
	            	<a href="${pageContext.request.contextPath}/resources/file/QSG-API.war">模3、QSG-API.war</a>
	            </td>
	        </tr>
	</table>
	  </div>
  </body>
</html>
