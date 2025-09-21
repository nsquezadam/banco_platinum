<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.platinum.model.Ejecutivo"%>
<%
String uri = request.getRequestURI();
boolean esLogin = uri.endsWith("login.jsp");

Ejecutivo ejecutivo = (Ejecutivo) session.getAttribute("ejecutivo");

if (ejecutivo == null && !esLogin) {
	response.sendRedirect("login.jsp");
	return;
}
%>

<div class="header">
	<h1>Banco Platinum</h1>

	<%
	if (ejecutivo != null) {
	%>
	<p>
		Bienvenido, <b><%=ejecutivo.getNombre()%></b> (RUT:
		<%=ejecutivo.getRutEjecutivo()%>
		| Depto:
		<%=ejecutivo.getDepartamento()%>)
	</p>
	<%
	}
	%>

	<%
	if (!esLogin) {
	%>
	<ul class="menu">
		<li><a href="index.jsp">Inicio</a></li>
		<li><a href="registro-cliente.jsp">Registro de Cliente</a></li>
		<li><a href="transferencia.jsp">Transferencias</a></li>
		<li><a href="logout.jsp">Cerrar Sesión</a></li>
	</ul>
	<%
	}
	%>

	<hr>
</div>
