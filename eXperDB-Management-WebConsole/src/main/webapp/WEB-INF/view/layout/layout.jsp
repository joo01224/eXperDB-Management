<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="../cmmn/commonLocale.jsp"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>eXperDB for Management</title>
<link rel="shortcut icon" type="image/x-icon" href="../images/logo.ico" />
<link rel="stylesheet" type="text/css" href="/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/css/common.css">
<link rel="stylesheet" type="text/css" href="/css/calendar.css">

<link rel = "stylesheet" type="text/css" media="screen" href="<c:url value='/css/dt/jquery.dataTables.min.css'/>"/>
<link rel = "stylesheet" type="text/css" media="screen" href="<c:url value='/css/dt/dataTables.jqueryui.min.css'/>"/> 
<link rel = "stylesheet" type="text/css" href="<c:url value='/css/dt/dataTables.colVis.css'/>"/>
<link rel = "stylesheet" type="text/css" href="<c:url value='/css/dt/dataTables.checkboxes.css'/>"/>

<script src ="/js/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src ="/js/jquery/jquery-ui.js" type="text/javascript"></script>
<script src ="/js/jquery/jquery.form.min.js" type="text/javascript"></script>
<script src="/js/jquery/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="/js/dt/dataTables.select.min.js" type="text/javascript"></script>
<script src="/js/dt/dataTables.jqueryui.min.js" type="text/javascript"></script>
<script src="/js/dt/dataTables.colResize.js" type="text/javascript"></script>
<script src="/js/dt/dataTables.checkboxes.min.js" type="text/javascript"></script>	
<script src="/js/dt/dataTables.colVis.js" type="text/javascript"></script>	
<script type="text/javascript" src="/js/common.js"></script>

<link rel="stylesheet" href="<c:url value='/css/treeview/jquery.treeview.css'/>" />
<script src="/js/treeview/jquery.cookie.js" type="text/javascript"></script>
<script src="/js/treeview/jquery.treeview.js" type="text/javascript"></script>


<!-- 달력을 사용 script -->
<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/calendar.js"></script>
</head>

<body>
	<div id="wrap">
			<tiles:insertAttribute name="topMenu" />
		<!-- container -->
		<div id="container">			
			<tiles:insertAttribute name="treeMenu" />
			<tiles:insertAttribute name="contents" />
		</div>
	</div>
</body>
</html>