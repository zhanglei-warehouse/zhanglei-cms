<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<form class="form-inline">
	  <div class="form-group mx-sm-3 mb-2">
		 <input type="text" name="text" value="" class="form-control" placeholder="请输入网站名称">  
 	  </div>
	  <div class="form-group mx-sm-3 mb-2">
	  </div>
	  <input type="hidden" name="pageNum" >
	  <button type="button" class="btn btn-primary mb-2" onclick="query()">查询</button>
	</form>
<table class="table">
  <thead>
    <tr>
      <th scope="col"><input  type="checkbox" value="" id="chkALL" name="chkALL"></th>
      <th scope="col">#</th>
      <th scope="col">域名</th>
      <th scope="col">网址</th>
      <th scope="col">操作</th>
      
    </tr>
  </thead>
  <tbody>
	<c:forEach items="${list }" var="link">
	  	<tr>
    	  <th><input type="checkbox" value="${link.id }" name="chk_list"></th>
	      <td scope="row">${link.id }</td>
	      <td>${link.text }</td>
	      <td>${link.url }</td>
	      <td>
	      	<input type="button" class="form-control" value="编辑" onclick="updateLinks(${linkid})">
	      </td>
	      
	    </tr>
  	</c:forEach>
  </tbody>
</table>
<input type="button" class="form-control" value="添加" onclick="add()">
<jsp:include page="../common/page.jsp"></jsp:include>
<script src="/public/js/checkbox.js"></script>

 <script type="text/javascript">
	function gotoPage(pageNo){
		$("[name=pageNum]").val(pageNo);
		query();
	}
	function query(){
		var params = $("form").serialize();
		reload(params);
	}
	
</script>