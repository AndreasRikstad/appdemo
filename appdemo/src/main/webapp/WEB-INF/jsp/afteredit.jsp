<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
<title><s:message code="profilEdit.pageName"/></title>

<script type="text/javascript">
	function leave() {
		window.location = '${pageContext.request.contextPath}/logout';
	}
	setTimeout("leave()", 3000);
</script>

</head>
<body>
<%@include file="/WEB-INF/incl/menu.app" %>
<p align="center">
	<font face="sans-serif" size="5">
		<c:out value="${message }" />
	</font>
</p>
</body>
</html>