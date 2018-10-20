<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
<title><s:message code="menu.users"/></title>
<script type="text/javascript">
function changeTrBg(row){
	row.style.backgroundColor = "#e6e6e6";
}

function defaultTrBg(row){
	row.style.backgroundColor = "white";
}

function startSerach(pParam){
	var searchWord = document.getElementById('searchString').value;
	var page = parseInt(document.getElementById('cp').value) + parseInt(pParam);
	if (pParam == 0) {
		if(searchWord.length < 1){
			document.getElementById("errorSearch").innerHTML = "<s:message code="error.searchString.toShort"/>";
			return false;
		}
	} else {
		document.getElementById("errorSearch").innerHTML = "";
		var searchLink = '${pageContext.request.contextPath}/admin/users/search/' + searchWord + '/' + page;
		window.location.href=searchLink;
	}
}
</script>
</head>
<body>
<%@include file="/WEB-INF/incl/menu.app" %>
<h2><s:message code="menu.users"/></h2>
<c:set var="licznik" value="${recordStartCounter }"/>
<div align="center">
	<div align="right" style="width: 1000px; padding: 2px;">
		<input type="hidden" name="cp" id="cp" value="${currentPage}"/>
		<input type="text" id="searchString" value="${searchWord}" readonly="readonly"/>&nbsp;&nbsp;
		<input type="button" value="<s:message code="button.search"/>"
				onclick="startSerach(0);"/><br/>
		<span id="errorSearch" style="color: red;"></span>
	</div>
<table width="1000" border="0" cellpadding="6" cellspacing="2">
	<tr bgcolor="#ffddcc">
		<td width="40" align="center"></td>
		<td width="40" align="center"><b><s:message code="admin.user.id"/></b></td>
		<td width="200" align="center"><b><s:message code="register.name"/></b></td>
		<td width="200" align="center"><b><s:message code="register.lastName"/></b></td>
		<td width="220" align="center"><b><s:message code="register.email"/></b></td>
		<td width="100" align="center"><b><s:message code="profil.czyAktywny"/></b></td>
		<td width="200" align="center"><b><s:message code="profil.rola"/></b></td>
	</tr>
	<c:forEach var="u" items="${userList }">
		<c:set var="licznik" value="${licznik+1}"/>
		<tr onmouseover="changeTrBg(this)" onmouseout="defaultTrBg(this)">
			<td align="right"><c:out value="${licznik }"/></td>
			<td align="right"><a href="../edit/${u.id }"><c:out value="${u.id }" /></a></td>
			<td align="left"><a href="../edit/${u.id }"><c:out value="${u.name }" /></a></td>
			<td align="left"><a href="../edit/${u.id }"><c:out value="${u.lastName }" /></a></td>
			<td align="center"><a href="../edit/${u.id }"><c:out value="${u.email }" /></a></td>
			<td align="center">
				<c:choose>
					<c:when test="${u.active == 1 }">
						<font color="green"><s:message code="word.tak"/></font>
					</c:when>
					<c:otherwise>
						<font color="red"><s:message code="word.nie"/></font>
					</c:otherwise>
				</c:choose>
			</td>
			<td align="center">
			<c:choose>
				<c:when test="${u.nrRoli == 1 }">
					<font color="green"><s:message code="word.admin"/></font>
				</c:when>
				<c:otherwise>
					<s:message code="word.user"/>
				</c:otherwise>
			</c:choose>
			</td>
		</tr>
	</c:forEach>
</table>
<table width="1000" border="0" cellpadding="6" cellspacing="0" bgcolor="#ffddcc">
	<tr>
		<td width="300" align="left">
			<s:message code="info.page"/> ${currentPage} <s:message code="info.from"/> ${totalPages}
		</td>
		<td align="right">
			
			<c:if test="${currentPage > 1}">
				<input type="button" onclick="startSerach(-1);" value="<s:message code="link.poprzedni"/>"/>&nbsp;&nbsp;
			</c:if>
			
			<c:if test="${currentPage < totalPages}">
				<input type="button" onclick="startSerach(1);" value="<s:message code="link.nastepny"/>"/>
			</c:if>
			
		</td>
	</tr>
</table>

</div>
</body>
</html>