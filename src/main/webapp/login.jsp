<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Banco Platinum</title>
</head>
<body>
    <h2>Banco Platinum</h2>
    <form action="login" method="post">
        <label for="rut">RUT:</label>
        <input type="text" name="rut" id="rut" required><br><br>

        <label for="departamento">Departamento:</label>
        <input type="text" name="departamento" id="departamento" required><br><br>

        <button type="submit">Ingresar</button>
    </form>

    <!-- Mensaje de error -->
    <c:if test="${not empty error}">
        <p id="mensajeError" style="color: red;">${error}</p>
    </c:if>
</body>
</html>
