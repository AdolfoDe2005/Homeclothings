<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial de Compras</title>
    <link rel="stylesheet" href="compras.css">
</head>
<body>
    <h1>Historial de Compras</h1>

    <%
       
        String dbUrl = "jdbc:mysql://localhost:5151/Login";
        String dbUser = "root";
        String dbPassword = "Batcountry2005";
        int idUsuario = (int) session.getAttribute("id_usuario");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "SELECT p.nombre AS productoNombre, c.cantidad, c.precio, c.direccion_envio, c.ciudad, c.codigo_postal, c.metodo_pago, c.fecha, c.estado, c.id_producto FROM pagos c JOIN productos p ON c.id_producto = p.id WHERE c.id_usuario = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();
    %>
            <table border="1">
                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio</th>
                    <th>Dirección de Envío</th>
                    <th>Ciudad</th>
                    <th>Código Postal</th>
                    <th>Método de Pago</th>
                    <th>Fecha</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
                
                <%
                    while (rs.next()) {
                %>
                <tr>
                    <td><%= rs.getString("productoNombre") %></td>
                    <td><%= rs.getInt("cantidad") %></td>
                    <td><%= rs.getDouble("precio") %></td>
                    <td><%= rs.getString("direccion_envio") %></td>
                    <td><%= rs.getString("ciudad") %></td>
                    <td><%= rs.getString("codigo_postal") %></td>
                    <td><%= rs.getString("metodo_pago") %></td>
                    <td><%= rs.getTimestamp("fecha") %></td>
                    <td><%= rs.getString("estado") %></td>
                    <td>
                        <form action="EliminarCompra" method="post">
                            <input type="hidden" name="id_producto" value="<%= rs.getInt("id_producto") %>">
                            <button type="submit">Eliminar</button>
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            
             <a href="Homeclothings" class="btn-5">Volver</a>
             
    <%
        } catch (SQLException e) {
            e.printStackTrace();
    %>
            <p>Error al cargar el historial de compras: <%= e.getMessage() %></p>
    <%
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    %>
</body>
</html>
