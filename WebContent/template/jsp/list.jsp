<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/jsp/common/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
	<title>##table_name_chinese##</title>
	<link rel="stylesheet" href="<%=path%>/css/pubmain.css" />
	<link href="<%=request.getContextPath() %>/css/commen.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath() %>/css/global.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="<%=path%>/js/cms/kkpager/kkpager_blue.css"/>
	<script type="text/javascript" src="<%=path %>/js/jquery1.9.0.min.js"></script>
	<script type="text/javascript" src="<%=path%>/js/cms/kkpager/kkpager.js"></script>
   	
  <script type="text/javascript">
  
	function getParameter(name) { 
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null)
			return unescape(r[2]); 
		return null;
	}

  	$(function(){
		//分页开始
  		var totalPage = ${totalPages};
  		var totalRecords = ${totalCount};
  		var pageNo = getParameter('pno');
  		$("#backPageNo").val(pageNo);
  		if(!pageNo){
  			if('${currentIndex}'!=null && '${currentIndex}'!=''){
  				pageNo='${currentIndex}';
  			}else{
  				pageNo = 1;
  			}
  		}
  		//初始化分页控件
  		kkpager.init({
  			pno : pageNo,
  			total : totalPage,			 //总页码
  			totalRecords : totalRecords, //总数据条数
  			hrefFormer : '${pageContext.request.contextPath}/##base_name##Action!listData',//链接前部
  			hrefLatter : '.action',		 //链接尾部
  			getLink : function(n){
  				return this.hrefFormer + this.hrefLatter + "?pno="+n; //+"&groupId="+groupId;
  			},
  			lang : {
  				prePageText : '上一页',
  				nextPageText : '下一页',
  				totalPageBeforeText : '共',
  				totalPageAfterText : '页',
  				totalRecordsAfterText : '条数据',
  				gopageBeforeText : '转到',
  				gopageButtonOkText : '确定',
  				gopageAfterText : '页',
  				buttonTipBeforeText : '第',
  				buttonTipAfterText : '页'
  			}
  		});
  		//生成
  		kkpager.generPageHtml();
  		//全选全不选
  		$("#isSelectAll").bind({
  			click:function(){
  				var checkboxs=$("input:checkbox[name=##pk_name##]");
  				if($("#isSelectAll").is(":checked")){
  					for(var i=0;i<checkboxs.length;i++){
  					  if(!(checkboxs[i].checked)){
						  	checkboxs[i].checked=true;
						}
  					}
  				}else{
  					for(var i=0;i<checkboxs.length;i++){
  					  if((checkboxs[i].checked)){
						  	checkboxs[i].checked=false;
						}
  					}
  				}
  			}
  		});
  	});

	// 功能按钮区域
	
	//按条件查询
  	function query(){
  		$("#totalForm").attr("action","${pageContext.request.contextPath}/##base_name##Action!listData.action");
	  	$("#totalForm").submit();
  	}
	
  	//重置
  	function clean(){
		// 条件检索区域重置
  		// $("#xxxx").attr("value","");

  		##listData_query_clean##
  	}
  	
  	//添加
  	function addDataPage(){  		
  		$("#totalForm").attr("action","${pageContext.request.contextPath}/##base_name##Action!addDataPage.action");
	  	$("#totalForm").submit();
  	}

	//删除	
  	function deleteData(){  		
		var checkboxs=$("input:checkbox[name=##pk_name##]");
		if(checkboxs.is(":checked")){
			if(confirm("确定要删除所选中项吗?")){
				$("#totalForm").attr("action","${pageContext.request.contextPath}/##base_name##Action!deleteData.action");
				$("#totalForm").submit();
			}
		}else{
			alert("请选择删除项！");
		}
	}
	
  	//修改
  	function updateDataPage(){
  		var checkboxs=$("input:checkbox[name=##pk_name##]");
  		var ret = 0;  // 选中的记录数
  		var idx = 0;  // 被选中的数据索引号
  		for(var i=0; i<checkboxs.length; i++){ 
  			if(checkboxs[i].checked) {
  				ret = ret + 1;
  				idx = i;
  			}
 		} 
  		
  		if (ret == 0) {
  			alert("请选择修改数据！");
  			return;
  		} else if (ret > 1) {
  			alert("请只选择一条数据！");
  			return;
  		} else if (ret == 1) {
			$("###pk_name##").val(checkboxs[idx].value);			 
			$("#totalForm").attr("action","${pageContext.request.contextPath}/##base_name##Action!updateDataPage.action");
			$("#totalForm").submit();	
  		}
  	}
  	
  	//批量修改状态
  	function changeStatus(status){
  		var checkboxs=$("input:checkbox[name=##pk_name##]");
  		if(checkboxs.is(":checked")){
  			$("#userStatus").val(status);
	  		$("#totalForm").attr("action","${pageContext.request.contextPath}/##base_name##Action!updateDataBatch.action");
	  		$("#totalForm").submit();
  		}else{
  			alert("请选择修改项！");
  		}
  	}
  	
  </script>
  <style type="">
  	.footer{margin-top:0px;}
  </style>
  </head>
  
<body>
<div id="middle">
	<div class="right"  id="mainFrame">    	
		<form action="" method="post" id="totalForm" >

			<!-- 查询条件区域 -->
			<div class="content-box">
			    <div class="content-box-header">
			    	<span class="now_location">当前位置:</span>[##table_name_chinese##] - [##table_name##]
			        <div class="clear"></div>
			    </div>
			    <div class=" margin-bottom-5 mt10">
			    
			    	##listData_query_input##

			   		<span style="float:right;">
			   			<input onclick="clean()" type="button" value="重置" class="btn btn-info" style="width:100px;margin-right:8px;" />
			   			<input onclick="query()" type="button" value="查询" class="btn btn-info" style="width:100px;margin-right:8px;" />
			   	 	</span>
			   	 	<div style="clear:both"></div>			   		
			   </div>
			</div>

			<!-- 检索结果区域 -->	
			<div class="content-box" style="overflow:scroll;">
			  	<div class="content-box-header"></div>
			    <table class="table table-bordered table-striped table-hover">
		      	<tbody>
			        <tr align="center">
			       	 	<td nowrap="nowrap" width="40px"><input type="checkbox" id="isSelectAll"/></td>
			       	 	<!-- 
						<td nowrap="nowrap" width="220px"><strong>id</strong></td>
						-->
						##listData_table_title##
	       			</tr>
		       		
		       		<c:forEach var="o" items="${##base_name2##List}" varStatus="s">					
					<tr align="center">
						<td><input type="checkbox" name="##pk_name##" value="${o.##pk_name##}"/></td>
						##listData_table_content##
					</tr>					
					</c:forEach>
		     	  </tbody>
			   </table>
			   
			   <!-- 功能按钮区域 -->
			   <div class=" margin-left-20">
			   		<span style="font-size:14px;">操作:</span>
			   		<span class=" margin-left-10">			   	
				   		<input onclick="addDataPage();"     type="button" value="增加" class="btn btn-info" style="width:80px;margin-right:8px;margin-bottom:8px;" />
				   		<input onclick="deleteData();"      type="button" value="删除" class="btn btn-info" style="width:80px;margin-right:8px;margin-bottom:8px;" />
				   		<input onclick="updateDataPage()"   type="button" value="修改" class="btn btn-info" style="width:80px;margin-right:8px;margin-bottom:8px;" />
				   		<input type="hidden" id= "backPageNo" name="backPageNo" />
				   	</span>
			   	</div>
			   	
			</div>
			<!-- 给修改用的 -->
			<input type="hidden" id= "##pk_name##" name="##pk_name##" />
		</form>
		
		<div class="pkp">
            <div id="kkpager"></div>
    	</div>
	</div>
</div>
</body>
</html>
