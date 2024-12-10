<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="carrito.css">
</head>
<body>

    <h1>Carrito de Compras</h1>

    <table>
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio</th>
            <th>Total</th>
            <th>Acciones</th>
        </tr>

        
        <c:if test="${empty carrito}">
            <tr>
                <td colspan="5">Tu carrito está vacío.</td>
            </tr>
        </c:if>

        
        <c:forEach var="producto" items="${carrito}">
            <tr>
                <td>${producto.nombre}</td>
                <td>${producto.cantidad}</td>
                <td>$<fmt:formatNumber value="${producto.precio}" type="currency" /></td>
                <td>$<fmt:formatNumber value="${producto.cantidad * producto.precio}" type="currency" /></td>
                <td>
                   
                    <form action="EliminarDelCarrito" method="post" style="display: inline;">
                        <input type="hidden" name="idProducto" value="${producto.idProducto}">
                        <button type="submit" class="btn2">Eliminar</button>
                    </form>

                  
                    <form action="PagarProducto" method="post" style="display: inline;">
                        <input type="hidden" name="idProducto" value="${producto.idProducto}">
                        <input type="hidden" name="cantidad" value="${producto.cantidad}">
                        <input type="hidden" name="precio" value="${producto.precio}">
                        <button type="submit" class="btn3">Pagar</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
