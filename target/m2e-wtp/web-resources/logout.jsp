<%@ page contentType="text/html; charset=UTF-8"%>
<%
// Invalidar la sesión
session.invalidate();

// Agregar un mensaje para mostrar en login.jsp
request.setAttribute("msg", "Sesión cerrada correctamente");

// Redirigir al login con mensaje
request.getRequestDispatcher("login.jsp").forward(request, response);
%>
