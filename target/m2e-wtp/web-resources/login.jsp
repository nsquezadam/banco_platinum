<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Login Ejecutivo - Banco Platinum</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body>

	<h1>Banco Platinum</h1>
	<h2>Ingreso de Ejecutivo</h2>

	<form action="login" method="post">
		RUT Ejecutivo: <input type="text" name="rut"> Departamento: <input
			type="text" name="departamento">
		<button type="submit">Ingresar</button>
	</form>


	<!-- Mostrar mensaje de error si existe -->
	<p style="color: red;">
		<%=request.getAttribute("error") != null ? request.getAttribute("error") : ""%>
	</p>

	<!-- Mostrar mensaje de logout si existe -->
	<p style="color: green;">
		<%=request.getAttribute("msg") != null ? request.getAttribute("msg") : ""%>
	</p>

</body>
</html>
