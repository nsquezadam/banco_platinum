<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.platinum.model.Ejecutivo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Banco Platinum - Menú Principal</title>
</head>
<body>
	<h1>Banco Platinum</h1>

	<c:choose>
		<c:when test="${not empty sessionScope.ejecutivo}">
			<%
                Ejecutivo ejecutivo = (Ejecutivo) session.getAttribute("ejecutivo");
            %>
			<!-- Mensaje de bienvenida -->
			<h2 id="mensajeBienvenida">
				Bienvenido
				<%= ejecutivo.getNombre() %>
				(Depto:
				<%= ejecutivo.getDepartamento() %>)
			</h2>
			<br>
			<a href="logout">Cerrar sesión</a>
		</c:when>
		<c:otherwise>
			<p>
				No hay sesión activa. <a href="login.jsp">Volver a login</a>
			</p>
		</c:otherwise>
	</c:choose>
</body>
</html>
