<table width="100%" border="0" cellpadding="8" cellspacing="4" class="tableMenuBg" bgcolor="#ccccee">
	<tr>
		<td align="left" width="900">
			<a href="/"><s:message code="menu.mainPage"/></>
		</td>
		
		<td align="right">
		<sec:authorize access="hasRole('ANONYMOUS')">
			<a href="/login"><s:message code="menu.login"/></a>&nbsp;
			<a href="/register"><s:message code="menu.register"/></a>&nbsp;
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<a href="/logout"><s:message code="menu.logout"/></a>	
		</sec:authorize>
		</td>	
	</tr>
</table>