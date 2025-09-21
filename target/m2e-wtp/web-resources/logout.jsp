<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Invalida la sesión del usuario
    if (session != null) {
        session.invalidate();
    }

    // Redirige al login con un mensaje
    request.setAttribute("mensajeError", "Sesión cerrada correctamente");
    request.getRequestDispatcher("login.jsp").forward(request, response);
%>
