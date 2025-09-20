<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Registro de Cliente - Banco Platinum</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>Banco Platinum</h1>
	<h2>Registro de Cliente</h2>

	<form method="post" action="registroCliente">
		<label>RUT:</label> <input type="text" name="rut"
			placeholder="Ej: 22222222-2" required> <label>Nombre:</label>
		<input type="text" name="nombre" required> <label>Apellido:</label>
		<input type="text" name="apellido" required> <label>Dirección:</label>
		<input type="text" name="direccion"> <label>Correo:</label> <input
			type="email" name="correo"> <label>Teléfono:</label> <input
			type="text" name="telefono"> <label>Nombre Mascota:</label> <input
			type="text" name="nombreMascota"> <label>Usuario
			(login):</label> <input type="text" name="usuario" required> <label>Password:</label>
		<input type="password" name="password" required>

		<button type="submit">Registrar Cliente</button>
	</form>

	<p>
		<%=request.getAttribute("mensaje") != null ? request.getAttribute("mensaje") : ""%>
	</p>
</body>
</html>
