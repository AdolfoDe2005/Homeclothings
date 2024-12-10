package adolfo.homeclothings.Servlets;

import adolfo.homeclothings.Conexion.BD;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AgregarAlCarrito")
public class AgregarAlCarritoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la sesión y el id_usuario
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("IniciarUsuario.jsp"); // Redirigir al login si no hay sesión
            return;
        }
        
        int idUsuario = (int) session.getAttribute("id_usuario");
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        try (Connection conn = BD.getConnection()) {
            // Verificar si el producto ya está en el carrito
            String checkSql = "SELECT * FROM carrito WHERE id_usuario = ? AND id_producto = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, idUsuario);
            checkStmt.setInt(2, idProducto);
            
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Si el producto ya existe, actualizamos la cantidad
                String updateSql = "UPDATE carrito SET cantidad = cantidad + ? WHERE id_usuario = ? AND id_producto = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, cantidad);
                updateStmt.setInt(2, idUsuario);
                updateStmt.setInt(3, idProducto);
                updateStmt.executeUpdate();
            } else {
                // Si no existe, insertamos el producto en el carrito
                String insertSql = "INSERT INTO carrito (id_usuario, id_producto, cantidad) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, idUsuario);
                insertStmt.setInt(2, idProducto);
                insertStmt.setInt(3, cantidad);
                insertStmt.executeUpdate();
            }

            
            response.sendRedirect("MostrarCarrito");

        } catch (SQLException e) {
            throw new ServletException("Error al agregar producto al carrito", e);
        }
    }
}