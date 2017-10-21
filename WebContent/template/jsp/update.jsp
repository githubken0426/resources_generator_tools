<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/jsp/common/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.flushBuffer();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改</title>
	<link rel="stylesheet" href="<%=path %>/css/pubmain.css" />
	<link href="<%=request.getContextPath() %>/css/commen.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath() %>/css/global.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="<%=path %>/js/jquery1.9.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/system.cms.js"></script>
   	
<script type="text/javascript">

	##input_check_var##
	
	$(function(){		
		##input_check_required_length##	
  	});
  
   	// 更新
	function updateSubmit(){
		##input_check_submit##		
	}
   
   	// 返回
  	function turnBack(){
  		$("#backForm").submit();
  	}
</script>
</head>
  
<body>
<div id="middle">
	<div class="right"  id="mainFrame">
		<form action="${pageContext.request.contextPath}/##base_name##Action!updateData.action" method="post" id="updateForm">
			<div class="content-box">
				<div class="content-box-header">
					<span class="now_location">当前位置:</span>修改
				    <div class="clear"></div>
			    </div>
				    
			    <div style="margin:0 auto; margin:10px;">
			   		<table  class="margin-bottom-20 table  no-border" style="width:300px;margin-top:20px;">
					 	<tr>
							<td class="text-center">
								<input type="button" value="保存" class="btn btn-info " style="width:80px;" onclick="updateSubmit()" />
							</td>
							<td align="left">
								<input type="button" value="返回" class="btn btn-info " style="width:80px;"  onclick="turnBack()"/>
						   	</td>
					   		<td></td>
					   	</tr>
					</table>
			    
	            	<table class="table table-bordered" >
	            		<input type="hidden" id="##pk_name##" name="entity.##pk_name##" value="${entity.##pk_name## }" />
	            	
	            		##listData_update##
	            	  	
	            	  	<!-- 
	            	  	<tr>
			                <td width="200px" align="right" nowrap="nowrap" bgcolor="#f1f1f1" height="40px">orgid：</td>
			                <td>
			    				<input type="hidden" id="id" name="config.id" value="${config.id }" />
			                	<input type="text" id="orgid" name="config.orgid" value="${config.orgid }" tabindex="1" style="width:400px;margin-left:30px;"/>
			                	<span id="orgidMsg" style="margin-left:15px;"></span>
			                </td>
		              	</tr>
						-->
	            	</table>
	            	
	         	</div>	         	
			</div>
		</form>

		<!-- 返回，记录列表页数据 -->
		<form id="backForm" method="post" action="${pageContext.request.contextPath}/##base_name##Action!listData.action">
			<input type="hidden" name="pno" value="${currentIndex}" />
			<input type="hidden" name="groupId" value="${groupId}" />
		</form>
	</div>
</div>
</body>
</html>
