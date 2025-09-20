<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Transferencias - Banco Platinum</title>
<link rel="stylesheet" type="text/css" href="css/estilos.css">
</head>
<body>
	<%@ include file="header.jsp"%>
	<h1>Banco Platinum</h1>
	<h2>Realizar Transferencia</h2>

	<form method="post" action="transferencia">
		<label>RUT Cliente (origen):</label> <input type="text"
			name="rutCliente" placeholder="Ej: 22222222-2" required> <label>ID
			Cuenta Origen:</label> <input type="number" name="idCuenta" required>

		<label>RUT Dueño Cuenta Destino:</label> <input type="text"
			name="rutDueno" placeholder="Ej: 33333333-3" required> <label>N°
			Cuenta Destino:</label> <input type="text" name="cuentaTransferencia"
			required> <label>Tipo de Cuenta:</label> <select
			name="tipoCuenta" required>
			<option value="CC">Cuenta Corriente</option>
			<option value="CA">Cuenta Ahorro</option>
			<option value="VISTA">Cuenta Vista</option>
			<option value="OTRA">Otra</option>
		</select> <label>Monto a Transferir:</label> <input type="number" name="monto"
			required>

		<button type="submit">Ejecutar Transferencia</button>
	</form>

	<p>
		<%=request.getAttribute("mensaje") != null ? request.getAttribute("mensaje") : ""%>
	</p>
</body>
</html>
